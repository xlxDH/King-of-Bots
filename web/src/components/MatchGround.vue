<template>
    <div class="matchground">
        <div class="row">
            <div class="col-6">
                <div class="user-userphoto">
                    <img :src="$store.state.user.photo" alt="">
                </div>
                <div class="user-username">
                    {{ $store.state.user.username }}
                </div>
            </div>
            <div class="col-6">
                <div class="user-userphoto">
                    <img :src="$store.state.pk.opponent_photo" alt="">
                </div>
                <div class="user-username">
                    {{ $store.state.pk.opponent_username }}
                </div>
            </div>
            <div class="col-12" style="text-align: center;">
                <button  @click="click_match_btn" type="button" class="btn btn-warning btn-lg" >{{ match_btn_info }}</button>
            </div>

        </div>

    </div>
</template>

<script>
import { ref } from 'vue'
import { useStore } from 'vuex'

export default {

    setup() {
        const store = useStore();
        let match_btn_info = ref("开始匹配");


        const click_match_btn = () => {
            if(match_btn_info.value==="开始匹配"){
                match_btn_info.value = "取消";
                store.state.pk.socket.send(JSON.stringify({
                    event: "start-matching",
                }));
            } else {
                match_btn_info.value = "开始匹配";
                 store.state.pk.socket.send(JSON.stringify({
                    event: "stop-matching",
                }));
            }
        }

        return {
            match_btn_info,
            click_match_btn,
        }
    }
}

</script>

<style scoped>
div.matchground {
    width: 80vw;
    height: 80vh;
    margin: 40px auto;
    background-color: rgba(50, 50, 50, 0.5);
}

div.user-userphoto {
    text-align: center;
    aspect-ratio: 1 / 1;    /* 确保容器为正方形 */ 
    padding-top: 10vh;
}

div.user-userphoto>img {
    width: 40vh;
    border-radius: 50%;
    object-fit: cover; 
    /* 完整显示图片，可能有留白 */
}

div.user-username {
    text-align: center;
    font-size: 24;
    font-weight: 600;
    color: white;
}
</style>