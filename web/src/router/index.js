import { createRouter, createWebHistory } from 'vue-router'
import PkIndexView from '../views/pk/PkIndexView.vue'
import RanklistIndexView from '../views/ranklist/RanklistIndexView.vue'
import RecordIndexView from '../views/record/RecordIndexView.vue'
import UserBotIndexView from '../views/user/bot/UserBotIndexView.vue'
import NotFound from '../views/error/NotFound.vue'
import UserAccountIndex from '@/views/user/account/UserAccountIndex.vue'

const routes = [
  {
    path:"/",
    name:"home",
    redirect:"/pk/"
  },
  {
    path: "/pk/",
    name: "pk_index",
    component: PkIndexView,
  },  
  {
    path: "/ranklist/",
    name: "ranklist_index",
    component: RanklistIndexView,
  },
  {
    path: "/record/",
    name: "record_index",
    component: RecordIndexView,
  },
  {
    path: "/user/bot/",
    name: "user_bot_indx",
    component: UserBotIndexView,
  },
  {
    path: "/404/",
    name: "NotFound",
    component: NotFound,
  },
  {
    path: "/login/",
    name: "login",
    component: UserAccountIndex
  },
  {
    path: "/:catchAll(.*)",
    redirect: "/404/"
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
