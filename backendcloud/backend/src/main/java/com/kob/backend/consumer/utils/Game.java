package com.kob.backend.consumer.utils;

import com.alibaba.fastjson2.JSONObject;
import com.kob.backend.consumer.WebSocketServer;
import com.kob.backend.pojo.Record;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Game extends Thread {
    final private Integer rows;
    final private Integer cols;
    final private Integer inner_walls_count;

    final private int[][] g;

    final private static int[] dx = {-1, 0, 1, 0};
    final private static int[] dy = {0, 1, 0, -1};

    final private Player playerA , playerB;

    private Integer nextStepA =null;
    private Integer nextStepB = null;

    private ReentrantLock lock = new ReentrantLock();
    private String status = "playing"; // playing -> finished
    private String loser = ""; // all:平局 , A , B

    public Game(Integer rows, Integer cols , Integer inner_walls_count ,Integer idA , Integer idB ) {
        this.rows = rows;
        this.cols = cols;
        this.inner_walls_count = inner_walls_count;
        this.g = new int[rows][cols];

        this.playerA = new Player(idA,this.rows-2,1,new ArrayList<>());
        this.playerB = new Player(idB,1,this.cols-2,new ArrayList<>());

    }

    public void setNextStepA(Integer nextStepA) {
        lock.lock();
        try {
            this.nextStepA = nextStepA;
        } finally {
            lock.unlock();
        }
    }
    public void setNextStepB(Integer nextStepB) {
        lock.lock();
        try {
            this.nextStepB = nextStepB;
        } finally {
            lock.unlock();
        }
    }

    public Player getPlayerA() {
        return playerA;
    }

    public Player getPlayerB() {
        return playerB;
    }

    public int[][] getG() {
        return this.g;
    }


    private boolean check_connectivity(int sx,int sy,int tx,int ty){
        if(sx==tx&&sy==ty) return true;
        g[sx][sy] = 1;

        for (int i=0;i<4;i++){
            int x = sx + dx[i] , y = sy + dy[i];
            if(x>=0&&x<this.rows&&y>=0&&y<this.cols&&g[x][y]==0) {
                if (this.check_connectivity(x, y, tx, ty)) {
                    g[sx][sy] = 0;
                    return true;
                }
            }
        }
        g[sx][sy] = 0;
        return false;
    }


    private boolean draw(){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                g[i][j] = 0;
            }
        }

        for (int r=0; r<this.rows; r++){
            g[r][0] = g[r][this.cols-1] = 1;
        }
        for (int c=0; c<this.cols; c++){
            g[0][c] = g[this.rows-1][c] = 1;
        }


        Random rand = new Random();

        for (int i=0;i<this.inner_walls_count/2;i++){
            for (int j=0;j<1000;j++){
                int r = rand.nextInt(this.rows);
                int c = rand.nextInt(this.cols);
                if(g[r][c]==1||g[this.rows-1-r][this.cols-1-c]==1) continue;
                if((r==this.rows-2&&c==1)||(c==this.cols-2&&r==1)) continue;

                g[r][c] = g[this.rows-1-r][this.cols-1-c] = 1;
                break;
            }
        }
        return check_connectivity(this.rows-2,1,1,this.cols-2);
    }

    public void creatMap(){
        for (int i = 0; i < 1000; i++) {
            if(draw()) break;

        }
    }

    private boolean nextStep() { // 玩家下一步操作
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        for(int i=0;i<50;i++){
            try {
                Thread.sleep(100);
                lock.lock();
                try {
                    if(nextStepA!=null && nextStepB!=null){
                        playerA.getSteps().add(nextStepA);
                        playerB.getSteps().add(nextStepB);
                        return true;
                    }
                }finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    private void setMove() { //向两个client传递移动信息
        lock.lock();
        try {
            JSONObject resp = new JSONObject();
            resp.put("event","move");
            resp.put("a_direction",nextStepA);
            resp.put("b_direction",nextStepB);
            sendALLMessage(resp.toJSONString());
            nextStepA = nextStepB = null;

        } finally {
            lock.unlock();
        }
    }

    private void sendALLMessage(String message){
        if(WebSocketServer.users.get(playerA.getId())!=null){
            WebSocketServer.users.get(playerA.getId()).sendMessage(message);
        }
        if(WebSocketServer.users.get(playerB.getId())!=null){
            WebSocketServer.users.get(playerB.getId()).sendMessage(message);
        }

    }

    private boolean check_valid(List<Cell> cellsA, List<Cell> cellsB){
        int n = cellsA.size();
        Cell cell = cellsA.get(n-1);
        if(g[cell.getX()][cell.getY()]==1) return false;

        for(int i=0;i<n-1;i++){
            if(cellsA.get(i).getX()==cell.getX() && cellsA.get(i).getY()==cell.getY()){
                return false;
            }
        }

        for(int i=0;i<n-1;i++){
            if(cellsB.get(i).getX()==cell.getX() && cellsB.get(i).getY()==cell.getY()){
                return false;
            }
        }

        return true;
    }

    private void judge() {//判断两名玩家下一步操作是否合法
        List<Cell> cellsA = playerA.getCells();
        List<Cell> cellsB = playerB.getCells();

        boolean validA = check_valid(cellsA,cellsB);
        boolean validB = check_valid(cellsB,cellsA);

        if(!validA || !validB){
            status = "finished";
            if(!validA&&!validB){
                loser = "all";
            } else if(!validA){
                loser = "A";
            } else{
                loser = "B";
            }
        }
    }

    private String getMapString(){
        StringBuilder res = new StringBuilder();
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                res.append(g[i][j]);
            }
        }
        return res.toString();
    }

    private  void saveToDatabase(){
        Record record = new Record(
                null,
                playerA.getId(),
                playerA.getSx(),
                playerA.getSy(),
                playerA.getStepsString(),
                playerB.getId(),
                playerB.getSx(),
                playerB.getSy(),
                playerB.getStepsString(),
                getMapString(),
                loser,
                new Date()
        );
        WebSocketServer.recordMapper.insert(record);
    }

    private void sendResult() { // 向两个client广播结果
        JSONObject resp = new JSONObject();
        resp.put("event","result");
        resp.put("loser",loser);
        saveToDatabase();
        sendALLMessage(resp.toJSONString());
    }

    @Override
    public void run() {
        for(int i=0;i<1000;i++){
            if(nextStep()){ //是否获取到用户操作
                judge();
                if(status.equals("playing")){
                    setMove();
                } else {
                    sendResult();
                    break;
                }
            } else{
                status = "finished";
                lock.lock();
                try {
                    if(nextStepA==null&&nextStepB==null){
                        loser = "all";
                    }
                    if(nextStepA==null&&nextStepB!=null){
                        loser = "A";
                    }
                    if(nextStepA!=null&&nextStepB==null){
                        loser = "B";
                    }
                } finally {
                    lock.unlock();
                }
                sendResult();
                break;
            }
        }
    }
}
