<template>
  <nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
      <router-link class="navbar-brand" :to="{ name: 'home' }">King of Bots</router-link>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText"
        aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarText">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <router-link :class="route_name == 'pk_index' ? 'nav-link active' : 'nav-link'" aria-current="page"
              :to="{ name: 'pk_index' }">对战</router-link>
          </li>
          <li class="nav-item">
            <router-link :class="route_name == 'record_index' ? 'nav-link active' : 'nav-link'"
              :to="{ name: 'record_index' }">对局列表</router-link>
          </li>
          <li class="nav-item">
            <router-link :class="route_name == 'ranklist_index' ? 'nav-link active' : 'nav-link'"
              :to="{ name: 'ranklist_index' }">排行榜</router-link>
          </li>
        </ul>
        <ul class="navbar-nav" v-if="$store.state.user.is_login">
          <li class="nav-item dropdown">
            <router-link class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
              aria-expanded="false">
              {{ $store.state.user.username }}
            </router-link>
            <ul class="dropdown-menu">
              <li><router-link class="dropdown-item" :to="{ name: 'user_bot_indx' }">我的Bots</router-link></li>
              <li>
                <hr class="dropdown-divider">
              </li>
              <li><button class="dropdown-item" href="#" @click="logout">退出</button></li>
            </ul>
          </li>
        </ul>
        <ul class="navbar-nav" v-else-if="!$store.state.user.pulling_info">
          <li class="nav-item">
            <router-link class="nav-link " :to="{name:'login'}" role="button">
              登录
            </router-link>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
import { useRoute } from 'vue-router';
import { computed } from 'vue';
import { useStore } from 'vuex';

export default {
  setup() {
    const store = useStore();
    const route = useRoute();
    let route_name = computed(() => route.name)

    const logout = () =>{
      store.dispatch("logout");
      location.reload();
    }
    return {
      route_name,
      logout,
    }
  }
}

</script>

<style scoped></style>