<template>
  <div  class="m-container">
    <div class="mblog">
      <h2>{{ blog.title }}</h2>
<!--      <el-link icon="el-icon-edit" v-if="ownBlog"><router-link :to="{name: 'BlogEdit', params: {blogId: blog.id}}">编辑</router-link></el-link>-->
      <el-divider></el-divider>

      <div class="content markdown-body"  v-html="blog.content" style="  height: 80vh;overflow:auto; margin: auto"></div>
    </div>
  </div>
</template>

<script>

import request from "@/utils/request";
export default {
  name: "infoArticle",
  data(){
    return{
      blog: {
        // 上传的地址
        upLoadUrl: 'api/file/upload',
        userId: null,
        title: "",
        description: "",
        content: ""
      },
      ownBlog: false
    }
  },
  methods:{

    getBlog() {
      const blogId = this.$route.query.articleId
      const _this = this
      request.post('/article/info/' + blogId).then((res) => {
        _this.blog = res.data
        var MarkdownIt = require('markdown-it'),
            md = new MarkdownIt();
        var result = md.render(_this.blog.context);
        _this.blog.content = result
        // 判断是否是自己的文章，能否编辑
   /*     _this.ownBlog =  (_this.blog.userId === _this.$store.getters.getUser.id)*/
      });
    }
  },
  created() {
    this.getBlog();
  }
}
</script>

<style scoped>
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}

.box-card {
  width: 480px;
}
</style>