<template>
  <div style="height: 50px;line-height: 50px;border-bottom: 1px solid #ccc ;background-color: dodgerblue ;display: flex">
    <div style="width: 200px; padding-left: 30px; font-weight: bold;color: white">ren.xiaolong-demo</div>
    <div style="flex: 1"></div>
    <div style="width: 100px">
      <el-dropdown>
  <span class="el-dropdown-link" style="color: white">{{userInfo.username}}
    <i class="el-icon-arrow-down el-icon--right"></i>
  </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>个人信息</el-dropdown-item>
          <el-dropdown-item @click.native="logout">退出系统</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>

</template>

<script>
export default {
  name: "Header",
  data(){
    return{
      userInfo:{}

    }
  },
  methods:{
    /**
     * 退出登录
     */
    logout(){
      //清除用户信息
      sessionStorage.removeItem("userInfo")
      this.$router.push({path: '/login'});
      const {href} = this.$router.resolve({
        path: '/login', // 这里写的是要跳转的路由地址
        query: {}// 页面参数
      })
      window.open(href, '_self')
    }
  },
  created() {
     this.userInfo=JSON.parse(sessionStorage.getItem("userInfo"))
  }
}
</script>

<style scoped>
.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
}
.el-icon-arrow-down {
  font-size: 12px;
}
</style>