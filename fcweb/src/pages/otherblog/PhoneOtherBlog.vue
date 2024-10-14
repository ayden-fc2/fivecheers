<template>
  <div>
    <LeftOutlined style="margin-left: -8px" @click="jumphelper.jumpToHello()" class="home_icon"/>
    <BuildingCom/>
  </div>
</template>

<script setup>
import BuildingCom from "@/coms/all/BuildingCom.vue";
import {
  LeftOutlined,
} from '@ant-design/icons-vue';
import {jumphelper} from "@/js/jumphelper";
import { onMounted } from "vue";
import { message } from "ant-design-vue";

onMounted(()=>{
  message.info('请使用电脑浏览，手机端暂不支持')
})

</script>

<style scoped>

</style>
