<script setup>
import {
  HomeOutlined,
} from '@ant-design/icons-vue';
import {jumphelper} from "@/js/jumphelper";
import BuildingCom from "@/coms/all/BuildingCom.vue";
import {onMounted} from "vue";
import {postLog} from "@/js/apihelper";

onMounted(() => {
  postLog('访问-手机旅行日志')
})

</script>

<template>
  <div>
    <HomeOutlined @click="jumphelper.jumpToSelect()" class="home_icon"/>
    <BuildingCom/>
  </div>
</template>

<style scoped>

</style>