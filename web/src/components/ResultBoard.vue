<template>
    <div class="result-board"> 
        <div class="result-board-text-draw" v-if="$store.state.pk.loser === 'all'">
            Draw
        </div>
        <div class="result-board-text-lose" v-else-if="$store.state.pk.loser === 'A'&&$store.state.pk.a_id==$store.state.user.id">
            Lose
        </div>
        <div class="result-board-text-lose" v-else-if="$store.state.pk.loser === 'B'&&$store.state.pk.b_id==$store.state.user.id">
            Lose
        </div>
        <div class="result-board-text-win" v-else>
            Win
        </div>
        <div class="result-board-btn">
             <button @click="restart" type="button" class="btn btn-success">
                            再来一局!
                    </button>
        </div>
    </div>
</template>

<script>
import { useStore } from 'vuex';

    export default{
        setup() {
            const store = useStore();
            const restart = () => {
                store.commit("updateStatus","matching");
                store.commit("updateLoser","none");
                store.commit("updateOpponent",{
                username:"我的对手",
                photo:"https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png",
            });
            }
            return {
                restart
            };
        }
    }
</script>

<style scoped>
div.result-board{
    height: 30vh;
    width: 30vw;
    background-color: rgba(50,50,50,0.5);
    position: absolute;
    top: 30vh;
    left: 35vw;
}

div.result-board-text-win{
    text-align: center;
    color: rgb(255 ,215 ,0);
    font-size: 50px;
    font-weight: 600;
    font-style: italic;
    padding-top: 4vh;
}

div.result-board-text-draw{
    text-align: center;
    color: rgb(255, 255, 255);
    font-size: 50px;
    font-weight: 600;
    font-style: italic;
    padding-top: 4vh;
}

div.result-board-text-lose{
    text-align: center;
    color: rgb(214, 4, 0);
    font-size: 50px;
    font-weight: 600;
    font-style: italic;
    padding-top: 4vh;
}

div.result-board-btn{
    text-align: center;
    padding-top: 10vh;
}

</style>