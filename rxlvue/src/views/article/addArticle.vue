<template>
  <div style="padding: 20px">
    <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item>
        <el-button style="float: right" type="primary" @click="submitForm('ruleForm')">发布</el-button>
      </el-form-item>
      <el-form-item label="文章标题" prop="articleName">
        <el-input v-model="ruleForm.articleName" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="分类标签" prop="classificationLabel">

        <el-select v-model="ruleForm.classificationLabel" multiple placeholder="请选择"   style="width: 600px" >
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
          </el-option>
        </el-select>

      </el-form-item>

<!--        文件上传
      <el-form-item>
            <div class="app-container">
              <div class="inner-container">
                <el-upload
                    ref="uploadExcel"
                    :action="upLoadUrl"
                    :auto-upload="true"
                    :data="fileUploadParam"
                    :on-success="uploadSuccess"
                >
                  <el-button size="small" type="primary">选择上传 图片 或则 文件</el-button>
                </el-upload>
              </div>
            </div>
          </el-form-item>-->

      <el-form-item label="内容" prop="content">
        <mavon-editor v-model="ruleForm.content" ref=vm  @imgAdd="uploadImg" style="height: 60vh"/>
      </el-form-item>

    </el-form>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "addArticle",
  components: {
  },
  data() {
    return {
      editorOption: {},
      ruleForm: {
        articleName: '',
        classificationLabel:[],
        content:''
      },
      // 上传的地址
      upLoadUrl: 'api/file/upload',
      // 上传文件需要携带的其他参数，比如携带token
      fileUploadParam: {
        code: '其他参数',
        appId: '其他参数',
        token: '',
      },
      rules: {
        articleName: [
          {required: true, message: '请输入名称', trigger: 'blur'},
        ],
        classificationLabel: [
          { required: true, message: '请选择标签', trigger: 'change' }
        ],
      },
      options: [],
    }

  },
  methods: {

    uploadImg(pos, file) {
      var _this = this;
      var formData = new FormData();
      formData.append('file', file);
      request({
        url: 'http://localhost:8084/file/upload',
        method: "post",
        data: formData,
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }).then((url) => {
        //使用服务器返回的图片地址替换原图片地址
        _this.$refs.vm.$img2Url(pos, "http://localhost:8084/"+url.data);
      })
    },


    /*新增文章*/
    submitForm(formName){
      let articleLableDto={
        articleEntity:{
          title:this.ruleForm.articleName,
          context:this.ruleForm.content
        },
        lables:this.ruleForm.classificationLabel
      }
      this.$refs[formName].validate((vaild)=>{
        if(vaild){
         request.post("/article/insert",articleLableDto).then(res=>{
           if (null != res.data) {
             this.dialogVisible = false;
             this.$message({
               message: '新增成功！',
               type: 'success'
             });
             this.$router.push({path: '/'});
             const {href} = this.$router.resolve({
               path: '/article', // 这里写的是要跳转的路由地址
               query: {}// 页面参数
             })
             window.open(href, '_self')
           }
         }).catch(err=>{
           this.$message({
             message: '新增失败！',
             type: 'error'
           });
         })
        }
      })

    },
    getLables() {
      request.post("/lable/queryAll").then(res => {
        res.data.forEach(ite=>{
          let option = {
            value: '',
            label: ''
          };
          option.label=ite.lableName;
          option.value=ite.id;
          this.options.push(option);
        })
      }).catch(error=>{
        this.$message({
          message: '查询标签失败！！',
          type: 'error'
        });
      })
    },
/*    submit() {
      console.log(this.$refs.text.value)


    },*/
    // 上传文件成功回调
    uploadSuccess(response, file, fileList) {
      if (response.code !== 200) {
        this.$message.success(response.msg)
      } else {
        this.$message.success('操作成功')
      }
    },
  },
  mounted() {
    this.getLables();
  }
}
</script>

<style scoped>

</style>