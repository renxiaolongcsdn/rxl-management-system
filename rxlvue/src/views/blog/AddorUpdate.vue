<template>
  <div>
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>{{ $route.meta.web_title }}</span>
        </div>
      </template>
      <el-row>
        <el-col :span="24">
          <Uedit v-model="content"></Uedit>
        </el-col>
        <el-col :span="24" style="margin-top:10px">
          <el-card class="box-card">
            <template #header>
              <div class="card-header">
                <span>编辑内容渲染</span>
              </div>
            </template>
            <div v-html="content" id="content"></div>
          </el-card>
        </el-col>
      </el-row>
    </el-card>

  </div>
</template>

<script>

export default {
  name: "AddorUpdate",
}
</script>

<style scoped>

</style>