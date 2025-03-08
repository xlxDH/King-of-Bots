const GAME_OBJECT = [];

export class GameObject{
    constructor(){
        GAME_OBJECT.push(this);
        this.timedelta = 0;
        this.has_called_start = false;

    }

    start() { //只一次
    }

    update() { //一帧一次
    }

    on_destroy() { // 删除前执行
    }

    destroy() {
        this.on_destroy();

        for (let i in GAME_OBJECT){
            const obj = GAME_OBJECT[i];
            if (obj === this){
                GAME_OBJECT.splice(i);
                break;
            }
        }
    }
}

// 刷新
let last_timestamp;//上一帧的时刻
const step= timestamp =>{
    for (let obj of GAME_OBJECT){
        if (!obj.has_called_start){
            obj.has_called_start = true;
            obj.start();
        } else{
            obj.timedelta = timestamp - last_timestamp;
            obj.update();
        }
    }
    last_timestamp = timestamp;
    requestAnimationFrame(step)
}

requestAnimationFrame(step)