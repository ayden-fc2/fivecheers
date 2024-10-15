<script setup>
import {
  HomeOutlined,
} from '@ant-design/icons-vue';
import {jumphelper} from "@/js/jumphelper";
import {checkManager} from "@/js/jshelper";
import {onMounted, ref} from "vue";
import AddNewCostCom from "@/coms/cost/AddNewCostCom.vue";
import UpdateCostCom from "@/coms/cost/UpdateCostCom.vue";
import router from "@/router/router";
import DefaultBackground from "@/coms/all/DefaultBackground.vue";
import {getAllCostSumApi, getMonthAnalysisByMonthApi} from "@/js/apihelper";
import {message} from "ant-design-vue";

const windowHeight = document.body.clientHeight

onMounted(()=>{
  getMoney()
  getMonthCost()
})

const currentMode = ref(1)
const changeMode = (newMode)=>{
  currentMode.value = newMode;
  if (newMode === 3){
    router.push("/desktop/mybooks")
  }
}
const money = ref(0)
const monthCost = ref(0)
const getMoney = ()=>{
  const getResult = getAllCostSumApi()
  getResult.then(response=>{
    money.value = response.data
  }).catch((e)=>{
    console.log(e)
    message.error('获取余额失败')
  })
}
const getMonthCost = ()=>{
  const currentDate = new Date(); // 获取当前日期
  const year = currentDate.getFullYear(); // 获取当前年份
  const month = currentDate.getMonth() + 1;
  const getResult = getMonthAnalysisByMonthApi({
    year: year,
    month: month
  })
  getResult.then(res=>{
    monthCost.value = -1 * res.data.outNum
  }).catch((e)=>{
    console.log(e)
    message.error('获取月度统计失败')
  })
}

</script>

<template>
  <div :style="{height: windowHeight + 'px'}" class="all-container">
    <DefaultBackground/>
    <HomeOutlined style="color: #fff" @click="jumphelper.jumpToSelect()" class="home_icon"/>
    <!--展示窗口-->
    <div v-if="checkManager()" class="show-container">
      <AddNewCostCom @add-cost="getMoney" v-if="currentMode===1"/>
      <UpdateCostCom @add-cost="getMoney" v-if="currentMode===2"/>
    </div>
    <!--模式选择-->
    <div v-if="checkManager()" class="choose-mode-container">
      <a-button class="mode-btn" @click="changeMode(1)" :type="currentMode===1 ? 'primary' : 'default'">记账</a-button>
      <a-button class="mode-btn" @click="changeMode(2)" :type="currentMode===2 ? 'primary' : 'default'">查账</a-button>
      <a-button class="mode-btn" @click="changeMode(3)" :type="currentMode===3 ? 'primary' : 'default'">统计</a-button>
    </div>
    <div class="money-container" v-if="checkManager()">
      月花销：{{ monthCost }}  |  余额：{{money}}
    </div>
    <!--不展示-->
    <div class="other-container" v-else>
      权限不够哈，试试浏览其他页面吧！
    </div>
  </div>
</template>

<style scoped>
  .all-container{
    position: relative;
    width: 100%;
  }
  .show-container{
    position: absolute;
    width: 90%;
    height: 64%;
    background: linear-gradient(to right bottom, #f3fdfc66, #eaf0fd66, #fce9f566);
    backdrop-filter: blur(4px);
    border-radius: 12px;
    border: 1px solid #888;
    left: 50%;
    top: 40%;
    transform: translate(-50%, -50%);
  }
  .choose-mode-container{
    position: absolute;
    bottom: 15%;
    width: 90%;
    height: 20%;
    transform: translate(-50%, 50%);
    left: 50%;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-around;
  }
  .mode-btn{
    width: 100%;
    height: 25%;
    backdrop-filter: blur(4px);
  }
  .money-container{
    position: fixed;
    right: 5%;
    top: 14px;
    color: #fff;
  }
  .other-container{
    position: fixed;
    top: 50%;
    left: 50%;
    width: 90%;
    height: 20%;
    min-height: 120px;
    background: linear-gradient(to right bottom, #f3fdfc88, #eaf0fd88, #fce9f588);
    backdrop-filter: blur(4px);
    border-radius: 12px;
    border: 1px solid #888;
    transform: translate(-50%, -50%);
    display: flex;
    align-items: center;
    justify-content: center;
    animation: topDropAni .8s 1;
    color: #fff;
  }
</style>
