<template>
  <div style="padding: 10px">
    <div style="padding: 20px">
      <el-button type="primary" @click="dialogVisible = true;">新增</el-button>
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
          prop="username"
          label="姓名"
          width="180"
      >
      </el-table-column>
      <el-table-column
          prop="password"
          label="密码"
          width="180">
      </el-table-column>
      <el-table-column
          prop="nickName"
          label="昵称">
      </el-table-column>
      <el-table-column
          prop="age"
          label="年龄">
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
        title="新增用户"
        :visible.sync="dialogVisible"
        width="30%"
        :before-close="handleClose">

      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="用户名" prop="username">
          <el-input type="username" v-model="ruleForm.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="昵称" prop="nickName">
          <el-input v-model="ruleForm.nickName"></el-input>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model.number="ruleForm.age"></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="ruleForm.address"></el-input>
        </el-form-item>
        <el-form-item>
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
// @ is an alias to /src

/*引入axios */
import request from "@/utils/request";
import {formatUTC} from '@/utils/format'
export default {
  name: 'home',
  components: {},
  data() {  /*声明变量的*/
    /*表单验证*/
    var checkAge = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('年龄不能为空'));
      }
      setTimeout(() => {
        if (!Number.isInteger(value)) {
          callback(new Error('请输入数字值'));
        } else {
          if (value < 18) {
            callback(new Error('必须年满18岁'));
          } else {
            callback();
          }
        }
      }, 1000);
    };
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.ruleForm.password !== '') {
          this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };

    return {
      tableData: [],
      dialogVisible: false,
      input: "",
      ruleForm: {
        username: '',
        password: 'qwe',
        checkPass:'qwe',
        nickName: "qwe",
        age: 20,
        address: "",
      },
      current: 1,
      size: 10,
      totalCount: 50,
      dataListLoading: false,
      loadingText: '',
      rules: {
        username: [
          {required: true, message: '请输入名称', trigger: 'blur'},
          {min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {validator: validatePass, trigger: 'blur'}
        ],
        checkPass: [
          {required: true, message: '请确认密码', trigger: 'blur'},
          {validator: validatePass2, trigger: 'blur'}
        ],
        age: [
          {validator: checkAge, trigger: 'blur'}
        ]
      }
    };
  },
  mounted() {
    this.getDataList();
  },
  methods: { /*声明方法的*/
    /*
    * 时间格式化方法
    * */
    dataFormat(row, column, value, index) {
      return formatUTC(value);
    },
    handleEdit() {

    },
    handleDelete(row) {
      request.get("/user/deleteById/"+row.id).then(res=>{
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
    handleClose() {
      this.dialogVisible = false;
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request.post("/user/save", this.ruleForm).then(response => {
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
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    /*加载用户列表信息*/
    getDataList() {
      this.dataListLoading = true;
      this.loadingText = '正在查询数据...';
      request.post('/user/queryPage', {
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
  }

}
</script>
