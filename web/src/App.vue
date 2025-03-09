<template>
  <NavBar/>
  <router-view/>
</template>

<script>
import NavBar from './components/NavBar.vue'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"
//import $ from 'jquery'


export default{
  components:{
    NavBar
  },
  setup(){
    /**$.ajax({
      url:"http://127.0.0.1:3000/user/account/info/",
      type:"get",
      headers:{
        Authorization: "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxM2QzZTIzNzIwMjI0NGU1YTkzNTg3OGVlMDMxZDZkYSIsInN1YiI6IjEiLCJpc3MiOiJzZyIsImlhdCI6MTc0MTUzNzgxMSwiZXhwIjoxNzQyNzQ3NDExfQ.8L62WXFmoXzX0I4cjyfCJ93thlI19Vlm6a3wD3Dp18s"
      },
      success(resp) {
        console.log(resp);
      },
      error(resp){
        console.log(resp);
      }
    });

    $.ajax({
      url:"http://127.0.0.1:3000/user/account/register/",
      type:"post",
      data:{
        username:"zxy",
        password:"zxy5201018",
        confirmedPassword:"zxy5201018",
      },
      success(resp) {
        console.log(resp);
      },
      error(resp) {
        console.log(resp);
      }
    });
**/
  },
}
</script>

<style>
body{
  background-image: url("@/assets/images/background.jpg");
  background-size:cover;
}
</style>
