<template>
  <div style="padding: 10px">
    <div style="padding: 20px">
      <el-button type="primary" @click="dialogVisible = true;ruleForm.lableName=''">新增</el-button>
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
          prop="lableName"
          label="标签名"
          width="180"
      >
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
          <!--          <el-button
                        size="mini"
                        @click="handleEdit(scope.row)">编辑
                    </el-button>-->
          <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>


    <el-dialog
        title="新增标签"
        :visible.sync="dialogVisible"
        width="30%"
        :before-close="handleClose">

      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="标签名" prop="lableName">
          <el-input type="LabelName" v-model="ruleForm.lableName" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="resetForm('ruleForm')">重置</el-button>
        <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
  </span>
    </el-dialog>


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
/*引入axios */
import {formatUTC} from '@/utils/format'
export default {
  name: "index",
  data(){

    return{
      dialogVisible:false,
      input:'',
      tableData:[],
      current: 1,
      size: 10,
      totalCount: 50,
      dataListLoading: false,
      loadingText: '',
      ruleForm:{
        lableName:"",
      },
      rules: {
        lableName: [
          {required: true, message: '请输入名称', trigger: 'blur'},
          {min: 1, max: 15, message: '长度在 1 到 15 个字符', trigger: 'blur'}
        ]
      }
    }
  },
  methods:{

    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request.post("/lable/insert", this.ruleForm).then(response => {
            if (null != response.data) {
              this.dialogVisible = false;
              this.$message({
                message: '新增成功！',
                type: 'success'
              });
              this.getDataList();
            }
          }).catch(res=>{
            this.getDataList();
            this.dataListLoading=false
            this.$message({
              message: '新增失败！'+res.message,
              type: 'error'
            });
          })
        } else {
          console.log('error submit!!');
          this.dataListLoading=false
          return false;
        }
      });
    },

    handleDelete(row) {
      request.get("/lable/delete/"+row.id).then(res=>{
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

   //时间格式化方法
    dataFormat(row, column, value, index) {
      return formatUTC(value);
    },
    /*加载用户列表信息*/
    getDataList() {
      this.dataListLoading = true;
      this.loadingText = '正在查询数据...';
      request.post('/lable/queryPage', {
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
    handleClose() {
      this.dialogVisible = false;
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
  created() {
    this.getDataList();
  }
}
</script>

<style scoped>

</style>