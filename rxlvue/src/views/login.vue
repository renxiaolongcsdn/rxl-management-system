<template>
  <div style="width: 100%; height: 100vh; overflow: hidden">
    <div style="width: 400px ;margin: 150px auto">

      <!--登录 和注册卡片切换  -->
      <el-tabs type="border-card" v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="登录" name="first">      <!--登录卡片-->

          <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="用户名" prop="username">
              <el-input prefix-icon="el-icon-s-custom" v-model="ruleForm.username"></el-input>
            </el-form-item>

            <el-form-item label="密码" prop="password">
              <el-input prefix-icon="el-icon-lock" type="password" v-model="ruleForm.password" autocomplete="off" show-password></el-input>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
              <el-button @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
          </el-form>

        </el-tab-pane>

        <el-tab-pane label="注册" name="second">   <!--注册卡片-->

          <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="用户名" prop="username">
              <el-input prefix-icon="el-icon-s-custom" v-model="ruleForm.username"></el-input>
            </el-form-item>

            <el-form-item label="密码" prop="password">
              <el-input prefix-icon="el-icon-lock" type="password" v-model="ruleForm.password" autocomplete="off" show-password></el-input>
            </el-form-item>

            <el-form-item label="昵称" prop="nickName">
              <el-input prefix-icon="el-icon-s-custom" type="text" v-model="ruleForm.nickName" autocomplete="off" ></el-input>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="submitForm('ruleForm')">注册</el-button>
              <el-button @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
          </el-form>

        </el-tab-pane>
      </el-tabs>
    </div>
  
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "login",
  data() {

    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        callback();
      }
    };
    return {
      activeName: 'first',
      ruleForm: {
        username: 'rxl',
        password: 'rxl',
        nickName:''
      },
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 3, max: 5, message: '长度在 3 到 15 个字符', trigger: 'blur'}
        ],
        password: [
          {validator: validatePass, trigger: 'blur'}
        ]
      }
    };
  },
  methods: {
    /*卡片切换*/
    handleClick(tab, event) {
      console.log(this.activeName=="first" ? "first" : "second")
    },
    /*登录或则注册*/
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request.post(this.activeName=="first" ? "/user/login" : "/user/save", this.ruleForm).then(response => {
            if (null != response.data) {
              if(this.activeName=="first"){
                this.$router.push({path: '/'});
                const {href} = this.$router.resolve({
                  path: '/', // 这里写的是要跳转的路由地址
                  query: {}// 页面参数
                })
                window.open(href, '_self')
                //登录成功后组要存取用户信息到sessionstorage 中
                console.log("存入用户信息"+JSON.stringify(response.data))
                sessionStorage.setItem("userInfo",JSON.stringify(response.data));
              }else{
                this.$message({
                  message: '注册成功！',
                  type: 'success'
                });
                this.activeName="first";
              }
            } else {
              if(this.activeName=="first"){
                this.$message({
                  message: '登录失败！',
                  type: 'warning'
                });
              }else{
                this.$message({
                  message: '注册失败！',
                  type: 'warning'
                });
              }
            }
          }).catch(res => {
            if(this.activeName=="first"){
              this.$message({
                message: '登录失败！',
                type: 'error'
              });
            }else{
              this.$message({
                message: '注册失败！',
                type: 'error'
              });
            }

          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>

<style scoped>
.path-0 {
  animation: pathAnim-0 4s;
  animation-timing-function: linear;
  animation-iteration-count: infinite;
}

@keyframes pathAnim-0 {
  0% {
    d: path("M 0,600 C 0,600 0,200 0,200 C 87.3205741626794,203.39712918660285 174.6411483253588,206.79425837320574 287,194 C 399.3588516746412,181.20574162679426 536.7559808612441,152.2200956937799 624,171 C 711.2440191387559,189.7799043062201 748.3349282296651,256.32535885167465 844,253 C 939.6650717703349,249.67464114832535 1093.9043062200958,176.47846889952154 1203,156 C 1312.0956937799042,135.52153110047846 1376.047846889952,167.76076555023923 1440,200 C 1440,200 1440,600 1440,600 Z");
  }
  25% {
    d: path("M 0,600 C 0,600 0,200 0,200 C 105.52153110047848,214.26794258373207 211.04306220095697,228.5358851674641 292,232 C 372.95693779904303,235.4641148325359 429.3492822966507,228.12440191387563 529,204 C 628.6507177033493,179.87559808612437 771.5598086124402,138.9665071770335 887,135 C 1002.4401913875598,131.0334928229665 1090.4114832535886,164.00956937799046 1178,181 C 1265.5885167464114,197.99043062200954 1352.7942583732056,198.99521531100476 1440,200 C 1440,200 1440,600 1440,600 Z");
  }
  50% {
    d: path("M 0,600 C 0,600 0,200 0,200 C 106.75598086124401,193.46411483253587 213.51196172248802,186.92822966507177 312,185 C 410.488038277512,183.07177033492823 500.708133971292,185.75119617224877 595,193 C 689.291866028708,200.24880382775123 787.6555023923444,212.06698564593302 879,228 C 970.3444976076556,243.93301435406698 1054.6698564593303,263.98086124401914 1147,260 C 1239.3301435406697,256.01913875598086 1339.6650717703349,228.00956937799043 1440,200 C 1440,200 1440,600 1440,600 Z");
  }
  75% {
    d: path("M 0,600 C 0,600 0,200 0,200 C 103.34928229665073,181.39712918660285 206.69856459330146,162.79425837320574 295,166 C 383.30143540669854,169.20574162679426 456.5550239234449,194.2200956937799 546,219 C 635.4449760765551,243.7799043062201 741.0813397129189,268.32535885167465 835,261 C 928.9186602870811,253.67464114832535 1011.1196172248804,214.47846889952154 1110,199 C 1208.8803827751196,183.52153110047846 1324.44019138756,191.76076555023923 1440,200 C 1440,200 1440,600 1440,600 Z");
  }
  100% {
    d: path("M 0,600 C 0,600 0,200 0,200 C 87.3205741626794,203.39712918660285 174.6411483253588,206.79425837320574 287,194 C 399.3588516746412,181.20574162679426 536.7559808612441,152.2200956937799 624,171 C 711.2440191387559,189.7799043062201 748.3349282296651,256.32535885167465 844,253 C 939.6650717703349,249.67464114832535 1093.9043062200958,176.47846889952154 1203,156 C 1312.0956937799042,135.52153110047846 1376.047846889952,167.76076555023923 1440,200 C 1440,200 1440,600 1440,600 Z");
  }
}

.path-1 {
  animation: pathAnim-1 4s;
  animation-timing-function: linear;
  animation-iteration-count: infinite;
}

@keyframes pathAnim-1 {
  0% {
    d: path("M 0,600 C 0,600 0,400 0,400 C 78.48803827751198,415.5598086124402 156.97607655502395,431.1196172248804 251,423 C 345.02392344497605,414.8803827751196 454.5837320574162,383.0813397129187 562,383 C 669.4162679425838,382.9186602870813 774.688995215311,414.555023923445 864,410 C 953.311004784689,405.444976076555 1026.6602870813397,364.6985645933014 1120,357 C 1213.3397129186603,349.3014354066986 1326.6698564593303,374.6507177033493 1440,400 C 1440,400 1440,600 1440,600 Z");
  }
  25% {
    d: path("M 0,600 C 0,600 0,400 0,400 C 77.25358851674642,379.1961722488038 154.50717703349284,358.3923444976076 256,376 C 357.49282296650716,393.6076555023924 483.22488038277527,449.6267942583733 572,473 C 660.7751196172247,496.3732057416267 712.5933014354065,487.1004784688995 820,468 C 927.4066985645935,448.8995215311005 1090.4019138755982,419.9712918660287 1203,407 C 1315.5980861244018,394.0287081339713 1377.7990430622008,397.0143540669857 1440,400 C 1440,400 1440,600 1440,600 Z");
  }
  50% {
    d: path("M 0,600 C 0,600 0,400 0,400 C 100.31578947368422,410.9569377990431 200.63157894736844,421.9138755980861 283,405 C 365.36842105263156,388.0861244019139 429.7894736842104,343.30143540669854 526,354 C 622.2105263157896,364.69856459330146 750.2105263157896,430.88038277511964 858,430 C 965.7894736842104,429.11961722488036 1053.3684210526317,361.17703349282294 1147,345 C 1240.6315789473683,328.82296650717706 1340.3157894736842,364.41148325358853 1440,400 C 1440,400 1440,600 1440,600 Z");
  }
  75% {
    d: path("M 0,600 C 0,600 0,400 0,400 C 100.07655502392345,363.52153110047846 200.1531100478469,327.04306220095697 300,351 C 399.8468899521531,374.95693779904303 499.46411483253587,459.3492822966507 605,455 C 710.5358851674641,450.6507177033493 821.9904306220095,357.55980861244024 904,346 C 986.0095693779905,334.44019138755976 1038.5741626794259,404.4114832535885 1123,427 C 1207.4258373205741,449.5885167464115 1323.712918660287,424.79425837320576 1440,400 C 1440,400 1440,600 1440,600 Z");
  }
  100% {
    d: path("M 0,600 C 0,600 0,400 0,400 C 78.48803827751198,415.5598086124402 156.97607655502395,431.1196172248804 251,423 C 345.02392344497605,414.8803827751196 454.5837320574162,383.0813397129187 562,383 C 669.4162679425838,382.9186602870813 774.688995215311,414.555023923445 864,410 C 953.311004784689,405.444976076555 1026.6602870813397,364.6985645933014 1120,357 C 1213.3397129186603,349.3014354066986 1326.6698564593303,374.6507177033493 1440,400 C 1440,400 1440,600 1440,600 Z");
  }
}
</style>