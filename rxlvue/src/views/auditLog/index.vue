<template>
  <div style="padding: 10px">
    <div style="padding: 20px">
      <div style="padding: 10px;float:right;">
        <el-input style="width: 200px;" v-model="input" placeholder="请输入名称"></el-input>
        <el-button type="primary" @click="getDataList">查询</el-button>
      </div>
    </div>
    <el-table
        :data="tableData"
        border
        v-loading="dataListLoading"
        :element-loading-text="loadingText"
        style="width: 100%">
      <el-table-column
          prop="operationUser"
          label="操作人"
          width="180"
      >
      </el-table-column>
      <el-table-column
          prop="path"
          label="请求路径"
          width="180">
      </el-table-column>
      <el-table-column
          prop="time"
          label="方法执行时间">
      </el-table-column>
      <el-table-column
          prop="title"
          label="操作方法">
      </el-table-column>
      <el-table-column
          prop="parameter"
          label="方法入参">
      </el-table-column>
      <el-table-column
          prop="action"
          label="方法描述">
      </el-table-column>
      <el-table-column
          prop="opIp"
          label="操作主机ip">
      </el-table-column>
      <el-table-column
          prop="createTime"
          label="创建时间"
          :formatter="dataFormat"
          sortable
      >
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

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
import request from "@/utils/request";
import {formatUTC} from "@/utils/format";
export default {
  name: "index",
  data(){
    return{
      current: 1,
      size: 5,
      totalCount: 50,
      input:'',
      dataListLoading:false,
      tableData:[],
      loadingText:'',

    }
  },
  methods:{
    dataFormat(row, column, value, index) {
      return formatUTC(value);
    },
    getDataList() {
      this.dataListLoading = true;
      this.loadingText = '正在查询数据...';
      request.post('/sysLog/queryPage', {
        'pageIndex': this.current,
        'pageSize': this.size,
        'searchkey': this.input
      }).then(data => {
        this.tableData = data.data.records;
        this.totalCount = data.data.total;
        this.dataListLoading = false
      }).catch(() => {
        this.$message({
          message: '查询数据失败！！',
          type: 'error'
        });
        this.dataListLoading = false
      })
    },

    handleDelete(row){
      request.get("sysLog/deleteSysLog/"+row.id).then(res=>{
        this.dataListLoading=true;
        if(null != res.data){
          this.$message({
            message: '删除成功！',
            type: 'success'
          });
          this.dataListLoading=false;
          this.getDataList();
        }
      }).catch(err=>{
        this.$message({
          message: '删除失败！您没有操作删除权限！',
          type: 'error'
        });
        this.dataListLoading=false;
      })

    },
    // 每页数
    sizeChangeHandle(val) {
      this.size = val
      this.current = 1
      this.getDataList()
    },
    // 当前页
    currentChangeHandle(val) {
      this.current = val
      this.getDataList()
    },
  },
  mounted() {
    this.getDataList();
  }
}
</script>

<style scoped>

</style>