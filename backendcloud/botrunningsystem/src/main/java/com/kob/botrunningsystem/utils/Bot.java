package com.kob.botrunningsystem.utils;

import java.util.ArrayList;
import java.util.List;

public class Bot implements com.kob.botrunningsystem.utils.BotInterface{

    static class Cell{
        public int x, y;
        public Cell(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    private boolean check_tail_increasing(int step){ //检验当前回合，蛇的长度是否增加
        if(step<=10) return true;
        return step%3 == 1;
    }

    public List<Cell> getCells(int sx, int sy,String steps) {
        List<Cell> res =  new ArrayList<>();
        int [] dx = {-1,0,1,0} , dy = {0,1,0,-1};

        int x = sx , y = sy;
        res.add(new Cell(x,y));
        int step = 0;
        for(int i=1;i<steps.length()-1;i++){
            int d = steps.charAt(i) - '0';
            x = x + dx[d];
            y = y + dy[d];
            res.add(new Cell(x,y));
            step++;
            if(!check_tail_increasing(step)){
                res.remove(0);
            }
        }
        return res;
    }


    @Override
    public Integer nextMove(String input) {
        String[] strs = input.split("#");
        int[][] g = new int[13][14];
        for(int i = 0 ,k=0; i < 13; i++){
            for(int j = 0; j < 14 ; j++,k++){
               if(strs[0].charAt(k) == '1'){
                   g[i][j] = 1;
               }
               else g[i][j] = 0;
            }
        }

        int aSx = Integer.parseInt(strs[1]) , aSy = Integer.parseInt(strs[2]);
        int bSx = Integer.parseInt(strs[4]) , bSy = Integer.parseInt(strs[5]);


        List<Cell> aCells = getCells(aSx,aSy,strs[3]);
        List<Cell> bCells = getCells(bSx,bSy,strs[6]);

        for(Cell c:aCells) g[c.x][c.y] = 1;
        for(Cell c:bCells) g[c.x][c.y] = 1;

        int[] dx={-1,0,1,0}, dy={0,1,0,-1};
        for(int i = 0; i < 4; i++){
            int x = aCells.get(aCells.size()-1).x + dx[i];
            int y = aCells.get(aCells.size()-1).y+dy[i];
            if(x>=0&&x<13&&y>=0&&y<14&&g[x][y]==0){
                return i;
            }
        }
        return 0;
    }
}
