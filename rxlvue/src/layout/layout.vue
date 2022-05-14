<template>
<div>
  <!--头部      -->
  <Header/>

  <!--侧边栏      -->
  <div style="display: flex">
    <Aside/>

    <!-- 主体区域       -->
    <router-view style="flex: 1"/>
  </div>

</div>
</template>

<script>

import Header from "@/components/Header";
import Aside from "@/components/Aside";

export default {
  name: "la",
  components: {
    Header,
    Aside
  }
}
</script>

<style scoped>

</style>