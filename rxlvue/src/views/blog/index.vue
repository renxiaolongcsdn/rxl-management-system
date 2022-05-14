<template>
  <div>
    <el-row v-loading="dataListLoading" :element-loading-text="loadingText">
      <el-col :span="24" v-for="(value,key,index) in this.cards" :key="index" style="padding-bottom: 10px ">
        <div class="grid-content bg-purple-light">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>文章标题 : {{ value.title }}</span>
              <el-button style="float: right; padding: 3px 0" type="text" @click="toInfo(value)">查看详情</el-button>
            </div>
            <el-tag type="success">创建时间 : {{ leaveTime(value.createTime) }}</el-tag>
            <el-button style="float: right; padding: 3px 0" type="text" @click="deleteArt(value)">删除</el-button>
          </el-card>
        </div>
      </el-col>
    </el-row>
    <!--   分页插件 -->
    <div class="page-container" style="margin: auto;padding-top: 30px">
      <el-pagination
          @size-change="sizeChangeHandle"
          @current-change="currentChangeHandle"
          :current-page="current"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="size"
          :total="totalCount"
          background
          layout="total, prev, pager, next,sizes,jumper">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import moment from "moment";
import request from "@/utils/request";

export default {
  name: "index",
  data() {
    return {
      current: 1,
      size: 10,
      totalCount: 50,
      dataListLoading: false,
      loadingText: '',
      cards: [1, 2],
      input:'',

    }
  },
  methods: {
    //过滤秒:格式化时间
    leaveTime(value) {
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
      }).catch(err => {
        this.$message({
          message: '查询数据失败！！',
          type: 'error'
        });
        this.dataListLoading = false
      })

    },

  },
  created() {
    this.getLoadList();
  }
}
</script>

<style scoped>

</style>