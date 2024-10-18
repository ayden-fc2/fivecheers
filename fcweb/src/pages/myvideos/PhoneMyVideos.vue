<script setup>
import {
  HomeOutlined,
} from '@ant-design/icons-vue';
import {jumphelper} from "@/js/jumphelper";
import DefaultBackground from "@/coms/all/DefaultBackground.vue";
import { onMounted, ref } from 'vue';
import { getLogPageApi, getLogByUUID } from '@/js/apihelper'
import { message } from 'ant-design-vue';

onMounted(()=>{
  getPage();
})

const pageSelectParams = ref({
  pageSize: 10,
  page: 0,
})
const showingData = ref([])
const searchUUID = ref('')

const getByUUID = ()=>{
  const getResult = getLogByUUID(searchUUID.value)
  getResult.then((res)=>{
    if(res.data.length > 0){
      for(let i in res.data){
        const originalTime = res.data[i].log_time;
        const date = new Date(originalTime);
        // 将时间格式化为 YYYY-MM-DD HH:mm
        const year = date.getFullYear(); // 获取年份
        const month = String(date.getMonth() + 1).padStart(2, '0'); // 获取月份（注意：月份从0开始）
        const day = String(date.getDate()).padStart(2, '0'); // 获取日期
        const hours = String(date.getHours()).padStart(2, '0'); // 获取小时并填充为两位数
        const minutes = String(date.getMinutes()).padStart(2, '0'); // 获取分钟并填充为两位数
        res.data[i].log_time = `${year}-${month}-${day} ${hours}:${minutes}`;
        // 检查 log_ip_address 是否存在
        const logIpAddress = res.data[i].log_ip_address;
        if (logIpAddress) {
            // 使用正则表达式提取 location
            const locationMatch = logIpAddress.match(/location:(.*?) ip:/);
            
            if (locationMatch && locationMatch[1]) {
                res.data[i].log_ip_location = locationMatch[1].trim(); // 提取的 location
            } else {
                res.data[i].log_ip_location = '未知'; // 如果没有匹配，设置为“未知”
            }
        } else {
            res.data[i].log_ip_location = '未知'; // log_ip_address 不存在时设置为“未知”
        }
      }
      showingData.value = res.data;
    }else{
      message.error('没有找到相关数据')
    }
  }).catch((err)=>{
    console.log(err)
    message.error('获取数据失败')
  })
}

const getPage = ()=>{
  const getResult = getLogPageApi({
    page: pageSelectParams.value.page,
    pageSize: pageSelectParams.value.pageSize,
  })
  getResult.then((res)=>{
    for(let i in res.data){
      const originalTime = res.data[i].log_time;
      const date = new Date(originalTime);
      // 将时间格式化为 YYYY-MM-DD HH:mm
      const year = date.getFullYear(); // 获取年份
      const month = String(date.getMonth() + 1).padStart(2, '0'); // 获取月份（注意：月份从0开始）
      const day = String(date.getDate()).padStart(2, '0'); // 获取日期
      const hours = String(date.getHours()).padStart(2, '0'); // 获取小时并填充为两位数
      const minutes = String(date.getMinutes()).padStart(2, '0'); // 获取分钟并填充为两位数
      res.data[i].log_time = `${year}-${month}-${day} ${hours}:${minutes}`;
      // 检查 log_ip_address 是否存在
      const logIpAddress = res.data[i].log_ip_address;
      if (logIpAddress) {
          // 使用正则表达式提取 location
          const locationMatch = logIpAddress.match(/location:(.*?) ip:/);
          
          if (locationMatch && locationMatch[1]) {
              res.data[i].log_ip_location = locationMatch[1].trim(); // 提取的 location
          } else {
              res.data[i].log_ip_location = '未知'; // 如果没有匹配，设置为“未知”
          }
      } else {
          res.data[i].log_ip_location = '未知'; // log_ip_address 不存在时设置为“未知”
      }
    }
    showingData.value = res.data;
    console.log(showingData.value)
  }).catch((err)=>{
    console.log(err)
    message.error('获取数据失败')
  })
}

const getLastPage = ()=>{
  if(pageSelectParams.value.page > 0){
    pageSelectParams.value.page -= 1;
    getPage();
  }
}
const getNextPage = ()=>{
  pageSelectParams.value.page += 1;
  getPage();
}

</script>

<template>
  <div>
    <HomeOutlined @click="jumphelper.jumpToSelect()" class="home_icon" style="color: #fff"/>
    <DefaultBackground/>
    <!--操作-->
    <div class="op-container glass-container">
      <!--按页查询--->
      <div class="op-line">
        <a-input v-model:value="searchUUID" size="small" placeholder="请输入uuid" style="width: 200px"/>
        <a-button @click="getByUUID()" size="small">搜索</a-button>
      </div>
      <a-divider style="margin: 0"/>
      <!--按照id查询-->
      <div class="op-line">
        <a-select size="small" v-model:value="pageSelectParams.pageSize" style="width: 100px">
          <a-select-option :value="10">10条/页</a-select-option>
          <a-select-option :value="20">20条/页</a-select-option>
          <a-select-option :value="30">30条/页</a-select-option>
          <a-select-option :value="50">50条/页</a-select-option>
        </a-select>
        <a-button size="small" @click="getLastPage()">上一页</a-button>
       <div style="font-size: 14px;">第 {{ pageSelectParams.page + 1 }} 页</div>
        <a-button size="small" @click="getNextPage()">下一页</a-button>
      </div>
    </div>
    <!--数据-->
    <div class="data-container glass-container">
      <div class="data-line" v-for="item in showingData" :key="item.log_id">
        <div>
          <h3 style="color: #396be8;">{{ item.log_uuid }}</h3>
          <h4>{{ item.log_time }}</h4>
        </div>
        <div>
          <h3>{{ item.log_op }}</h3>
          <h4>{{ item.log_ip_location }}</h4>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
  .glass-container{
    background-color: #ffffffaa;
    border-radius: 12px;
    backdrop-filter: blur(10px);
  }
  .op-container {
    position: fixed;
    left: 20px;
    right: 20px;
    top: 48px;
    height: 128px;
    display: flex;
    align-items: center;
    flex-direction: column;
    justify-content: space-around;
    padding: 12px 0;
  }
  .op-container .op-line {
    width: 90%;
    height: 36px;
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
  .data-container {
    position: fixed;
    left: 20px;
    right: 20px;
    top: 192px;
    bottom: 20px;
    padding: 8px 0;
    overflow-y: scroll;
    overflow-x: hidden;
  }
  .data-container .data-line {
    margin: 8px 5% 12px 5%;
    width: 90%;
    height: 48px;
    display: flex;
    flex-direction: column;
    align-items: start;
    justify-content: space-around;
  }
  .data-container .data-line div{
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 100%;
  }
  .data-container .data-line h3{
    font-size: 15px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    max-width: 50%
  }
  .data-container .data-line h4{
    font-size: 13px;
  }

</style>