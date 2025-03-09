<template>
    <div class="box">
        <!-- 滑动盒子 -->
        <div class="pre-box" :style="preBoxStyles">
            <h1>WELCOME</h1>
            <p>JOIN US!</p>
            <div class="img-box">
                <img :src="currentImage" alt="">
            </div>
        </div>

        <!-- 注册表单 -->
        <div class="register-form">
            <div class="title-box">
                <h1>注册</h1>
            </div>
            <div class="input-box">
                <input v-model="registerForm.username" type="text" placeholder="用户名">
                <input v-model="registerForm.password" type="password" placeholder="密码">
                <input v-model="registerForm.repassword" type="password" placeholder="确认密码">
            </div>
            <div class="btn-box">
                <button @click="handleRegister">注册</button>
                <p @click="toggleForm">已有账号?去登录</p>
            </div>
        </div>

        <!-- 登录表单 -->
        <div class="login-form">
            <div class="title-box">
                <h1>登录</h1>
            </div>
            <div class="input-box">
                <input v-model="loginForm.username" type="text" placeholder="用户名">
                <input v-model="loginForm.password" type="password" placeholder="密码">
            </div>
            <div class="btn-box">
                <button @click="login">登录</button>
                <p @click="toggleForm">没有账号?去注册</p>
            </div>
        </div>
    </div>
</template>

<script>
import { useStore } from 'vuex'
import { ref, reactive, computed, onMounted, onBeforeUnmount } from 'vue'
import { useRouter } from 'vue-router'

export default {
    name: 'LoginPage',
    setup() {
        const store = useStore()
        const router = useRouter()

        // 响应式状态
        const isLoginForm = ref(true)
        const loginForm = reactive({
            username: '',
            password: '',
        })
        const registerForm = reactive({
            username: '',
            password: '',
            repassword: ''
        })
        const error_message = ref('')
        const userAccounts = ref(JSON.parse(localStorage.getItem('userAccounts')) || [])
        const bubbleInterval = ref(null)

        const login = () => {
            error_message.value = '' // 清空错误信息
            
            store.dispatch("login", {
                username: loginForm.username,
                password: loginForm.password,
                success: (resp) => {
                    console.log('登录成功:', resp)
                    router.push('/') // 登录成功跳转
                },
                error: (err) => {
                    console.error('登录失败:', err)
                    error_message.value = err.message || '登录失败，请检查凭证'
                }
            })
        }

        // 计算属性
        const preBoxStyles = computed(() => ({
            transform: `translateX(${isLoginForm.value ? 0 : '100%'})`,
            backgroundColor: isLoginForm.value ? 'rgb(139,232,145)' : 'rgb(173,208,216)'
        }))

        const currentImage = computed(() =>
            isLoginForm.value
                ? require('@/assets/images/icon/regiestor.png')
                : require('@/assets/images/icon/login.png')
        )

        // 方法
        const toggleForm = () => {
            isLoginForm.value = !isLoginForm.value
            error_message.value = ''
        }

        const validateRegister = () => {
            if (registerForm.password !== registerForm.repassword) {
                error_message.value = '两次密码不一致！'
                return false
            }

            if (userAccounts.value.some(user => user.username === registerForm.username)) {
                error_message.value = '该用户已存在！'
                return false
            }

            return true
        }

        const handleRegister = () => {
            if (!validateRegister()) return

            userAccounts.value.push({
                username: registerForm.username,
                password: registerForm.password
            })

            localStorage.setItem('userAccounts', JSON.stringify(userAccounts.value))
            toggleForm()
            resetRegisterForm()
        }

        const resetRegisterForm = () => {
            Object.assign(registerForm, {
                username: '',
                password: '',
                repassword: ''
            })
        }

        // 气泡动画
        const initBubbles = () => {
            const createBubble = () => {
                const bubble = document.createElement('span')
                const size = Math.random() * 5 + 25

                bubble.style.cssText = `
                    width: ${size}px;
                    height: ${size}px;
                    left: ${Math.random() * innerWidth}px;
                `

                document.body.appendChild(bubble)
                setTimeout(() => bubble.remove(), 4000)
            }

            bubbleInterval.value = setInterval(createBubble, 200)
        }

        // 生命周期钩子
        onMounted(initBubbles)
        onBeforeUnmount(() => clearInterval(bubbleInterval.value))

        return {
            isLoginForm,
            loginForm,
            registerForm,
            error_message,
            preBoxStyles,
            currentImage,
            toggleForm,
            handleRegister,
            login,
        }
    }
}
</script>

<style scoped>
* {
    /* 去除浏览器默认内外边距 */
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

/* 去除input的轮廓 */
input {
    outline: none;
}

html,
body {
    height: 80%;
}

body {
    /* 溢出隐藏 */
    overflow-x: hidden;
    display: flex;
    /* 渐变方向从左到右 */
    background: linear-gradient(to right, rgb(119, 234, 107), rgb(187, 201, 244));
}



/* 最外层的大盒子 */
.box {
    width: 80vw;
    height: 90vh;
    display: flex;
    /* 相对定位 */
    position: relative;
    z-index: 2;
    margin: auto;
    /* 设置圆角 */
    border-radius: 8px;
    /* 设置边框 */
    border: 1px solid rgba(255, 255, 255, .6);
    /* 设置盒子阴影 */
    box-shadow: 2px 1px 19px rgba(0, 0, 0, .1);
}

/* 滑动的盒子 */
.pre-box {
    /* 宽度为大盒子的一半 */
    width: 50%;
    /* width: var(--width); */
    height: 100%;
    /* 绝对定位 */
    position: absolute;
    /* 距离大盒子左侧为0 */
    left: 0;
    /* 距离大盒子顶部为0 */
    top: 0;
    z-index: 99;
    border-radius: 4px;
    background-color: rgb(139, 232, 145);
    box-shadow: 2px 1px 19px rgba(0, 0, 0, .1);
    /* 动画过渡，先加速再减速 */
    transition: 0.5s ease-in-out;
}

/* 滑动盒子的标题 */
.pre-box h1 {
    margin-top: 150px;
    text-align: center;
    /* 文字间距 */
    letter-spacing: 5px;
    color: white;
    /* 禁止选中 */
    user-select: none;
    /* 文字阴影 */
    text-shadow: 4px 4px 3px rgba(0, 0, 0, 0.1);
}

/* 滑动盒子的文字 */
.pre-box p {
    height: 30px;
    line-height: 30px;
    text-align: center;
    margin: 20px 0;
    /* 禁止选中 */
    user-select: none;
    font-weight: bold;
    color: white;
    text-shadow: 4px 4px 3px rgba(0, 0, 0, .1);
}

/* 图片盒子 */
.img-box {
    width: 200px;
    height: 200px;
    margin: 20px auto;
    /* 设置为圆形 */
    border-radius: 50%;
    /* 设置用户禁止选中 */
    user-select: none;
    overflow: hidden;
    box-shadow: 4px 4px 3px rgba(0, 0, 0, .1);
}

/* 图片 */
.img-box img {
    width: 100%;
    transition: 0.5s;
}

/* 登录和注册盒子 */
.login-form,
.register-form {
    flex: 1;
    height: 100%;
}

/* 标题盒子 */
.title-box {
    height: 300px;
    line-height: 500px;

}

/* 标题 */
.title-box h1 {
    text-align: center;
    color: white;
    /* 禁止选中 */
    user-select: none;
    letter-spacing: 5px;
    text-shadow: 4px 4px 3px rgba(0, 0, 0, .1);

}

/* 输入框盒子 */
.input-box {
    display: flex;
    /* 纵向布局 */
    flex-direction: column;
    /* 水平居中 */
    align-items: center;
}

/* 输入框 */
input {
    width: 60%;
    height: 40px;
    margin-bottom: 20px;
    text-indent: 10px;
    border: 1px solid #058e25;
    background-color: rgba(255, 255, 255, 0.3);
    border-radius: 120px;
    /* 增加磨砂质感 */
    backdrop-filter: blur(10px);
    color: black;
}

input:focus {
    /* 光标颜色 */
    color: #101111;

}

/* 聚焦时隐藏文字 */
input:focus::placeholder {
    opacity: 0;
}

/* 按钮盒子 */
.btn-box {
    display: flex;
    justify-content: center;
}

/* 按钮 */
button {
    width: 100px;
    height: 30px;
    margin: 0 7px;
    line-height: 30px;
    border: none;
    border-radius: 4px;
    background-color: #69b3f0;
    color: white;
}

/* 按钮悬停时 */
button:hover {
    /* 鼠标小手 */
    cursor: pointer;
    /* 透明度 */
    opacity: .8;
}

/* 按钮文字 */
.btn-box p {
    height: 30px;
    line-height: 30px;
    /* 禁止选中 */
    user-select: none;
    font-size: 14px;
    color: white;

}

.btn-box p:hover {
    cursor: pointer;
    border-bottom: 1px solid white;
}
</style>