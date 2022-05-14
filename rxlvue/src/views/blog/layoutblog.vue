<template>
  <div class="backtop" style="height: 100vh; width: 100vw;overflow-y: auto">
    <!--头部      -->
    <HeaderBlog/>

    <!--侧边栏      -->
    <div style="display: flex">

      <LeftAside style="padding: 30px"/>

      <!-- 主体区域       -->
      <router-view style="flex: 1;padding-top: 30px;"/>

      <LeftAside style="padding: 30px"/>

    </div>

    <!--
    实现回到最顶部 当长度唱过400 时就会触发
           target属性的值指向的就是外层你滚动区域的类名
           visibility-height需要的是一个number，所以需要v-bind绑定；
    -->
    <el-backtop target=".backtop" :visibility-height=300></el-backtop>
  </div>
</template>

<script>

import HeaderBlog from "@/views/blog/component/Header";
import LeftAside from "@/views/blog/component/LeftAside";
import RightAside from "@/views/blog/component/RightAside";


export default {
  name: "index",
  components: {
    HeaderBlog,
    LeftAside,
    RightAside
  },
  data() {
    return {}
  },
  methods: {

  },
}
</script>

<style scoped>
.el-header, .el-footer {
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
  text-align: center;
  line-height: 200px;
}

.el-main {
  background-color: #E9EEF3;
  color: #333;
  text-align: center;
  line-height: 160px;
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
</style>