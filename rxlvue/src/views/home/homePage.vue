<template>
  <div style="padding: 20px">
    <div>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-card class="box-card" shadow="hover">
            <div style="height: 100px">
              <span style="font: 20px Extra large;">{{ this.articleNum }}</span>
              <p style="padding-top: 20px">文章</p>
            </div>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card class="box-card" shadow="hover">
            <div style="height: 100px">
              <span style="font: 20px Extra large;">{{ this.lablesNum }}</span>
              <p style="padding-top: 20px">标签</p>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="24">
          <el-card class="box-card" shadow="hover">
            <h4>技术社区</h4>
            <el-divider></el-divider>
            <el-row :gutter="20">
              <el-col :span="6">
                <el-card class="box-card" shadow="hover">
                  <div style="height: 100px"  onclick="window.open('https://www.cnblogs.com/')">
                    <img src="http://101.132.174.238:8084/images/cnblogs.png">
                    <p style="padding-top: 20px">博客园</p>
                  </div>
                </el-card>
              </el-col>
              <el-col :span="6">
                <el-card class="box-card" shadow="hover">
                  <div style="height: 100px" onclick="window.open('https://www.csdn.net/')">
                    <img src="http://101.132.174.238:8084/images/csdn.png">
                    <p style="padding-top: 20px">csdn</p>
                  </div>
                </el-card>
              </el-col>
              <el-col :span="6">
                <el-card class="box-card" shadow="hover">
                  <div style="height: 100px" onclick="window.open('https://toutiao.io/')">
                    <img src="http://101.132.174.238:8084/images/toutiao.png">
                    <p style="padding-top: 20px">开发者头条</p>
                  </div>
                </el-card>
              </el-col>
              <el-col :span="6">
                <el-card class="box-card" shadow="hover">
                  <div style="height: 100px" onclick="window.open('https://mo.fish/')">
                    <img src="http://101.132.174.238:8084/images/fish.ico">
                    <p style="padding-top: 20px">鱼塘热榜</p>
                  </div>
                </el-card>
              </el-col>
            </el-row>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "homePage",
  data() {
    return {
      articleNum: 520,
      lablesNum: 510,
      classificationNUm: 500,
      LeavingMessageNum: 499,
    }
  },
  methods:{
    //查询文章、标签数量
    init(){
        request.post('/home/query').then(data => {
          this.articleNum = data.data.articleCount;
          this.lablesNum = data.data.lableCount;
          this.dataListLoading = false
        }).catch(() => {
          this.$message({
            message: '查询数据失败！！',
            type: 'error'
          });
          this.dataListLoading = false
        })
    }

  },
  created() {
    this.init();
  },
}
</script>

<style scoped>
.el-row {
  margin-bottom: 20px;

&
:last-child {
  margin-bottom: 0;
}

}
.el-col {
  border-radius: 4px;
}

.bg-purple-dark {
  background: #99a9bf;
}

.bg-purple {
  background: #d3dce6;
}

.bg-purple-light {
  background: #e5e9f2;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}

.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}

</style>