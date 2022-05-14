<template>
  <div>
    <el-menu
        style="width: 200px; min-height: calc(100vh - 50px)"
        default-active="index"
        background-color="#1B0E0E"
        text-color="#fff"
        class="el-menu-vertical-demo"
        :default-openeds="[1]"
        @open="handleOpen"
        @close="handleClose"
        :router="true">
        <el-menu-item index="index">首页</el-menu-item>
      <el-menu-item index="article">文章管理</el-menu-item>
      <el-menu-item index="lable">标签管理</el-menu-item>
      <el-submenu index="2">
        <template slot="title">菜单管理</template>
        <el-menu-item index="user">用户管理</el-menu-item>
        <el-menu-item index="auditLogIndex">审计日志</el-menu-item>
      </el-submenu>

    </el-menu>
  </div>
</template>

<script>
export default {
  name: "Aside",
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    }
  }
}
</script>

<style scoped>

</style>