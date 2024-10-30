<script setup>
import {
  HomeOutlined
} from '@ant-design/icons-vue';
import {jumphelper} from "@/js/jumphelper";
import BuildingCom from "@/coms/all/BuildingCom.vue";
import {postLog} from "@/js/apihelper";
import {onMounted} from "vue";

onMounted(() => {
  postLog('访问-桌面旅行日志')
})


</script>

<template>
  <div class="plan_container">
    <HomeOutlined @click="jumphelper.jumpToSelect()" class="home_icon"/>
    <BuildingCom/>
  </div>
</template>

<style scoped>


</style>