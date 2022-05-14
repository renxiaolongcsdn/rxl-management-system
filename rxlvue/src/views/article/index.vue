<template>
  <div>
    <div style="padding: 20px" >
      <el-button type="primary" @click="addArticle">新增</el-button>
      <div style="padding: 10px;float:right;">
        <el-input style="width: 200px;" v-model="input" placeholder="请输入名称"></el-input>
        <el-button type="primary" @click="getLoadList">查询</el-button>
      </div>

      <el-row v-loading="dataListLoading" :element-loading-text="loadingText">
        <el-col :span="24" v-for="(value,key,index) in this.cards" :key="index" style="padding-bottom: 10px">
          <div class="grid-content bg-purple-light">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>文章标题 : {{ value.title }}</span>
                <el-button style="float: right; padding: 3px 0" type="text" @click="toInfo(value)">查看详情</el-button>
              </div>
              <el-tag type="success">创建时间 :  {{leaveTime(value.createTime)}}</el-tag>
              <el-button style="float: right; padding: 3px 0" type="text" @click="deleteArt(value)">删除</el-button>
            </el-card>
          </div>
        </el-col>
      </el-row>
    </div>


    <!--   分页插件 -->
    <div class="page-container">
      <el-pagination
          @size-change="sizeChangeHandle"
          @current-change="currentChangeHandle"
          :current-page="current"
          :page-sizes="[5,10, 20, 50, 100]"
          :page-size="size"
          :total="totalCount"
          background
          layout="total, prev, pager, next,sizes,jumper">
      </el-pagination>
    </div>

  </div>
</template>

<script>
/*引入axios */
import request from "@/utils/request";
import moment from 'moment';


export default {
  name: "index",
  data() {
    return {
      currentDate: new Date(),
      dialogVisible: false,
      input: '',
      count: 0,
      rules: {},
      current: 1,
      size: 5,
      totalCount: 50,
      dataListLoading: false,
      loadingText: '',
      cards: [1, 2],
    }
  },
  methods: {
    //过滤秒:格式化时间
    leaveTime(value)
    {
      return moment(value).format('YYYY-MM-DD HH:mm');
    },

    // 每页数
    sizeChangeHandle(val) {
      this.size = val
      this.current = 1
      this.getLoadList()
    },
    // 当前页
    currentChangeHandle(val) {
      this.current = val
      this.getLoadList()
    },
    handleClose() {
      this.dialogVisible = false;
    },
    //添加文章进行跳转
    addArticle() {
      this.$router.push({path: '/addarticle'});
      const {href} = this.$router.resolve({
        path: '/addarticle', // 这里写的是要跳转的路由地址
        query: {}// 页面参数
      })
      window.open(href, '_self')
    },

    /*加载文章列表*/
    getLoadList() {
      request.post("/article/queryPage", {
        'pageIndex': this.current,
        'pageSize': this.size,
        'searchkey': this.input
      }).then(res => {
        this.cards = res.data.records;
        this.totalCount = res.data.total;
        this.dataListLoading = false
      }).catch(err=>{
        this.$message({
          message: '查询数据失败！！',
          type: 'error'
        });
        this.dataListLoading = false
      })

    },
    /*跳转到*/
    toInfo(o){
      this.$router.push({path: '/infoArticle'});
      const {href} = this.$router.resolve({
        path: '/infoArticle', // 这里写的是要跳转的路由地址
        query: {articleId:o.id}// 页面参数
      })
      window.open(href, '_self')
    },
    deleteArt(o){
      request.post("article/delete/"+o.id).then(res=>{
        this.dataListLoading=true;
        if(null != res.data){
          this.$message({
            message: '删除成功！',
            type: 'success'
          });
          this.dataListLoading=false;
          this.getLoadList();
        }
      }).catch(err=>{
        this.$message({
          message: '删除失败！您没有操作删除权限！',
          type: 'error'
        });
        this.dataListLoading=false;
      })

    },
  },
  mounted() {
    this.getLoadList();
  }
}
</script>

<style scoped>
.bg-purple-light {
  background: #e5e9f2;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}

</style>