import { GameObject } from "./GameObject";
import { Cell } from "./Cell";

export class Snake extends GameObject{
    constructor(info,gamemap){
        super();
        
        this.id = info.id;
        this.color = info.color;
        this.gamemap = gamemap;
        this.cells = [new Cell(info.r,info.c)]; //蛇身体
        this.next_cell = null; //下一步的目的地

        this.speed = 5;
        this.direction = -1; //-1无操作 , 0上 , 1右 , 2下 , 3左
        this.status = "idle"; //idle表示静止, move表示移动, die表示死亡

        this.dr = [-1,0,1,0];
        this.dc = [0,1,0,-1];

        this.step = 0; //回合数
        this.eps = 1e-2; //误差

        this.eye_direction = 0;
        if (this.id === 1) this.eye_direction = 2;

        this.eye_dx = [ //眼睛的偏移量
            [-1,1],
            [1,1],
            [1,-1],
            [-1,-1],
        ];

        this.eye_dy = [
            [-1,-1],
            [-1,1],
            [1,1],
            [1,-1],
        ];
    }

    start() {

    }

    set_direction(d) {
        this.direction = d;
    }

    check_tail_increasing() { //检测蛇长度是否增加
        if (this.step<=10) return true;
        if (this.step%3===1) return true;
        return false;
    }

    next_step() { //将蛇的状态变为走下一步
        const d = this.direction;
        this.eye_direction = d;
        this.next_cell = new Cell(this.cells[0].r+this.dr[d],this.cells[0].c+this.dc[d]);
        this.direction = -1;//清空方向
        this.status = "move";
        this.step ++;

        const k = this.cells.length;
        for (let i=k;i>0;i--){
            this.cells[i] = JSON.parse(JSON.stringify(this.cells[i-1]));
        }

   }

    update_move() {
        const dx = this.next_cell.x - this.cells[0].x;
        const dy = this.next_cell.y - this.cells[0].y;
        const direction = Math.sqrt(dx*dx+dy*dy);
        if (direction < this.eps) {
            this.cells[0] = this.next_cell;
            this.next_cell = null;
            this.status = "idle";

            if(!this.check_tail_increasing()){ //蛇不变长
                this.cells.pop();
            }
        } else {
            const move_distance = this.speed * this.timedelta / 1000;
            this.cells[0].x += move_distance * dx/direction;
            this.cells[0].y += move_distance * dy/direction;

            if(!this.check_tail_increasing()){
                const k = this.cells.length;
                const tail = this.cells[k-1] , tail_target = this.cells[k-2];
                const tail_dx = tail_target.x - tail.x;
                const tail_dy = tail_target.y - tail.y;
                const tail_distance = Math.sqrt(tail_dx*tail_dx+tail_dy*tail_dy);
                tail.x += move_distance * tail_dx/tail_distance;
                tail.y += move_distance * tail_dy/tail_distance;
            }
        }
    }

    update() {
        if (this.status==='move'){
            this.update_move();
        }
        this.render();
    }

    render() {
        const L = this.gamemap.L;
        const ctx = this.gamemap.ctx;
        const size = 0.6;

        ctx.fillStyle = this.color;

        if(this.status==="die"){
            ctx.fillStyle = "#F0F8FF";
        }

        for(const cell of this.cells){
            ctx.beginPath();
            ctx.arc(cell.x*L,cell.y*L,L/2*size,0,Math.PI*2);
            ctx.fill();
        }

        for (let i = 1;i<this.cells.length;i++){
            const a = this.cells[i-1];
            const b = this.cells[i];
            if(Math.abs(a.x-b.x)<this.eps&&Math.abs(a.y-b.y)<this.eps){
                continue;
            }
            if(Math.abs(a.x-b.x)<this.eps){
                ctx.fillRect((a.x-0.5 + (1-size)/2)*L,Math.min(a.y,b.y)*L,L*size,Math.abs(a.y-b.y)*L); 
            } else {
                ctx.fillRect(Math.min(a.x,b.x)*L,(a.y-0.5 +(1-size)/2)*L,Math.abs(a.x-b.x)*L,L*size);
            }
        }

        ctx.fillStyle = "white";
        for(let i=0;i<2;i++){
            const eye_x = (this.cells[0].x+this.eye_dx[this.eye_direction][i]*0.2)*L;
            const eye_y = (this.cells[0].y+this.eye_dy[this.eye_direction][i]*0.2)*L;
            ctx.beginPath();
            ctx.arc(eye_x,eye_y,L*size*0.2,0,Math.PI*2);
            ctx.fill()
        }
        ctx.fillStyle = "black";
        for(let i=0;i<2;i++){
            const eye_x = (this.cells[0].x+this.eye_dx[this.eye_direction][i]*0.2)*L;
            const eye_y = (this.cells[0].y+this.eye_dy[this.eye_direction][i]*0.2)*L;
            ctx.beginPath();
            ctx.arc(eye_x,eye_y,L*size*0.1,0,Math.PI*2);
            ctx.fill()
        }
    }
}