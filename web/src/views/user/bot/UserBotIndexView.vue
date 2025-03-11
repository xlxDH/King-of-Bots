<template>
    <div class="container">
        <div class="row">
            <div class="col-3">
                <div class="card" style="margin-top: 5%;">
                    <div class="card-body">
                        <img :src="$store.state.user.photo" alt="" width=100%>
                    </div>
                </div>
            </div>

            <div class="col-9">
                <div class="card" style="margin-top: 5%;">
                    <div class="card-header">
                        <span style="font-size: 120%;">
                            我的Bot
                        </span>
                        <button type="button" class="btn btn-success float-end" data-bs-toggle="modal"
                            data-bs-target="#add-bot-btn">
                            创建Bot
                        </button>

                        <div class="modal fade" id="add-bot-btn" tabindex="-1" aria-labelledby="exampleModalLabel"
                            aria-hidden="true">
                            <div class="modal-dialog modal-xl">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h1 class="modal-title fs-5" id="exampleModalLabel">创建Bot</h1>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                            aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <form>
                                            <div class="mb-3">
                                                <label for="add-bot-title" class="form-label">名称</label>
                                                <input v-model="botadd.title" type="text" class="form-control"
                                                    id="add-bot-title" placeholder="请输入Bot名称">
                                            </div>
                                            <div class="mb-3">
                                                <label for="add-bot-discription" class="form-label">描述</label>
                                                <textarea v-model="botadd.discription" class="form-control"
                                                    id="add-bot-discription" rows="3" placeholder="请输入Bot简介"></textarea>
                                            </div>
                                            <div class="mb-3">
                                                <label for="add-bot-code" class="form-label">代码</label>
                                                <VAceEditor v-model:value="botadd.content" @init="editorInit"
                                                    lang="c_cpp" theme="textmate" style="height: 300px" :options="{
                                                        enableBasicAutocompletion: true, //启用基本自动完成
                                                        enableSnippets: true, // 启用代码段
                                                        enableLiveAutocompletion: true, // 启用实时自动完成
                                                        fontSize: 18, //设置字号
                                                        tabSize: 4, // 标签大小
                                                        showPrintMargin: false, //去除编辑器里的竖线
                                                        highlightActiveLine: true,
                                                    }" />
                                            </div>
                                        </form>
                                    </div>
                                    <div class="modal-footer">
                                        <div class="error-message">{{ botadd.error_message }}</div>
                                        <button type="button" class="btn btn-primary" @click="add_bot">创建</button>
                                        <button type="button" class="btn btn-secondary"
                                            data-bs-dismiss="modal">取消</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="card-body">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>名称</th>
                                    <th>创建时间</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="bot in bots" :key="bot.id">
                                    <td>{{ bot.title }}</td>
                                    <td>{{ bot.createtime }}</td>
                                    <td>
                                        <button type="button" class="btn btn-secondary" style="margin-right: 2%;"
                                            data-bs-toggle="modal"
                                            :data-bs-target="'#update-bot-btn-' + bot.id">修改</button>

                                        <div class="modal fade" :id="'update-bot-btn-' + bot.id" tabindex="-1"
                                            aria-labelledby="exampleModalLabel" aria-hidden="true">
                                            <div class="modal-dialog modal-xl">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h1 class="modal-title fs-5" id="exampleModalLabel">修改Bot</h1>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                            aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <form>
                                                            <div class="mb-3">
                                                                <label for="update-bot-title"
                                                                    class="form-label">名称</label>
                                                                <input v-model="bot.title" type="text"
                                                                    class="form-control" id="update-bot-title"
                                                                    placeholder="请输入Bot名称">
                                                            </div>
                                                            <div class="mb-3">
                                                                <label for="update-bot-discription"
                                                                    class="form-label">描述</label>
                                                                <textarea v-model="bot.discription" class="form-control"
                                                                    id="update-bot-discription" rows="3"
                                                                    placeholder="请输入Bot简介"></textarea>
                                                            </div>
                                                            <div class="mb-3">
                                                                <label for="update-bot-code"
                                                                    class="form-label">代码</label>
                                                                <VAceEditor v-model:value="bot.content"
                                                                    @init="editorInit" lang="c_cpp" theme="textmate"
                                                                    style="height: 300px" :options="{
                                                                        enableBasicAutocompletion: true, //启用基本自动完成
                                                                        enableSnippets: true, // 启用代码段
                                                                        enableLiveAutocompletion: true, // 启用实时自动完成
                                                                        fontSize: 18, //设置字号
                                                                        tabSize: 4, // 标签大小
                                                                        showPrintMargin: false, //去除编辑器里的竖线
                                                                        highlightActiveLine: true,
                                                                    }" />

                                                            </div>
                                                        </form>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <div class="error-message">{{ bot.error_message }}</div>
                                                        <button type="button" class="btn btn-primary"
                                                            @click="update_bot(bot)">更新</button>
                                                        <button type="button" class="btn btn-secondary"
                                                            data-bs-dismiss="modal">取消</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <button type="button" class="btn btn-danger" style="margin-right:2% ;"
                                            @click="remove_bot(bot)">删除</button>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
//import ContentField from '../../../components/ContentField.vue'
import { useStore } from 'vuex';
import { ref, reactive } from 'vue';
import $ from 'jquery';
import { Modal } from 'bootstrap/dist/js/bootstrap';
import { VAceEditor } from 'vue3-ace-editor';
import ace from 'ace-builds';
import 'ace-builds/src-noconflict/mode-c_cpp';
import 'ace-builds/src-noconflict/mode-json';
import 'ace-builds/src-noconflict/theme-chrome';
import 'ace-builds/src-noconflict/ext-language_tools';


export default {

    components: { VAceEditor },

    setup() {
        ace.config.set(
            "basePath",
            "https://cdn.jsdelivr.net/npm/ace-builds@" +
            require("ace-builds").version +
            "/src-noconflict/")

        const store = useStore();
        let bots = ref([]);

        const botadd = reactive({
            title: "",
            discription: "",
            content: "",
            error_message: "",
        });

        const botupdate = reactive({
            error_message: "",
        });

        const refresh_bots = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/user/bot/getlist/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    bots.value = resp
                },
                error(resp) {
                    console.log(resp);
                },
            });
        };

        refresh_bots();

        const add_bot = () => {
            botadd.error_message = "";
            $.ajax({
                url: "http://127.0.0.1:3000/user/bot/add/",
                type: "post",
                data: {
                    title: botadd.title,
                    discription: botadd.discription,
                    content: botadd.content,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        botadd.title = "";
                        botadd.discription = "";
                        botadd.content = "";
                        Modal.getInstance("#add-bot-btn").hide();
                        refresh_bots();
                    } else {
                        botadd.error_message = resp.error_message;
                    }
                },
            });
        };

        const remove_bot = (bot) => {
            $.ajax({
                url: "http://127.0.0.1:3000/user/bot/remove/",
                type: "delete",
                data: {
                    bot_id: bot.id,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        refresh_bots();
                    } else {
                        alert(resp.error_message);
                    }
                },
            });
        };


        const update_bot = (bot) => {
            botupdate.error_message = "";
            $.ajax({
                url: "http://127.0.0.1:3000/user/bot/update/",
                type: "post",
                data: {
                    bot_id: bot.id,
                    title: bot.title,
                    discription: bot.discription,
                    content: bot.content,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === "success") {

                        Modal.getInstance('#update-bot-btn-' + bot.id).hide();
                        refresh_bots();
                    } else {
                        bot.error_message = resp.error_message;
                        console.log(bot.error_message);
                    }
                },
            });
        };


        return {
            bots,
            botadd,
            botupdate,
            add_bot,
            update_bot,
            remove_bot,
        };

        /**$.ajax({
            url:"http://127.0.0.1:3000/user/bot/add/",
            type: "post",
            data: {
                title: "Bot的标题",
                discription: "Bot的描述",
                content:"Bot的代码",
            },
            headers:{
                Authorization: "Bearer " + store.state.user.token,
            },
            success (resp) {
                console.log(resp);
            },
            error(resp){
                console.log(resp);
            },  
        });**/
        /**$.ajax({
           url:"http://127.0.0.1:3000/user/bot/remove/",
           type: "delete",
           data: {
               bot_id: 3,
           },
           headers:{
               Authorization: "Bearer " + store.state.user.token,
           },
           success (resp) {
               console.log(resp);
           },
           error(resp){
               console.log(resp);
           },  
       });**/
        /**$.ajax({
           url:"http://127.0.0.1:3000/user/bot/update/",
           type: "post",
           data: {
               bot_id: 1,
               title: "新标题",
               discription: "新描述",
               content: "新代码",
           },
           headers:{
               Authorization: "Bearer " + store.state.user.token,
           },
           success (resp) {
               console.log(resp);
           },
           error(resp){
               console.log(resp);
           },  
       });**/
        /**$.ajax({
            url:"http://127.0.0.1:3000/user/bot/getlist/",
            type: "get",
            headers:{
                Authorization: "Bearer " + store.state.user.token,
            },
            success (resp) {
                console.log(resp);
            },
            error(resp){
                console.log(resp);
            },  
        });**/
    }
}
</script>

<style scoped>
div.error-message {
    color: #ff4444;
}
</style>