import {GameObject} from "./GameObject";

export class Wall extends GameObject{
    constructor(r,c,gamemap){
        super();

        this.r = r;
        this.c = c;
        this.gamemap = gamemap;
        this.color = '#8a4530';
    }

    update(){
        this.render();
    }

    render(){
        const L = this.gamemap.L;
        const ctx = this.gamemap.ctx;

        ctx.fillStyle = this.color;
        ctx.fillRect(this.c*L,this.r*L,L,L);
    }
}