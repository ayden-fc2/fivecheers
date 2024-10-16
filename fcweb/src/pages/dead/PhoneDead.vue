<script setup>
import {
  HomeOutlined
} from '@ant-design/icons-vue';
import {jumphelper} from "@/js/jumphelper";
import DefaultBackground from '@/coms/all/DefaultBackground.vue';
import { onMounted, ref } from 'vue';
import { message } from 'ant-design-vue';
import { getBirthdayListApi, addNewBirthdayApi, deleteBirthdayApi } from '@/js/apihelper';
import { timeCorrect } from '@/js/jshelper';

onMounted(()=>{
  getAllBirthday()
})

const name = ref('')
const birthDay = ref()
const birthdayes = ref([])
const addBirthday = () => {
  console.log({
    birthId: null,
    birthName: name.value,
    birthDate: birthDay.value
  })
  const postResult = addNewBirthdayApi({
    birthId: null,
    birthName: name.value,
    birthDate: birthDay.value
  })
  postResult.then(res => {
    if(res.data){
      getAllBirthday()
      message.success('添加生日成功')
      name.value = ''
      birthDay.value = ''
    }
  }).catch(err => {
    console.log(err)
    message.error('添加生日失败')
  })
}
const getAllBirthday = () => {
  const getResult = getBirthdayListApi()
  getResult.then(res => {
    for(let i = 0; i < res.data.length; i++){
      res.data[i].birthDate = timeCorrect(res.data[i].birthDate)
      // 下个生日还有多少天
      const today = new Date();
      const nextBirthday = new Date(today.getFullYear(), new Date(res.data[i].birthDate).getMonth(), new Date(res.data[i].birthDate).getDate());
      if (nextBirthday < today) {
          nextBirthday.setFullYear(today.getFullYear() + 1);
      }
      res.data[i].leftDay = Math.ceil((nextBirthday - today) / (24 * 3600 * 1000)); // 转换成天数

      // 年龄
      const birthDate = new Date(res.data[i].birthDate); // 获取出生日期
      let age = today.getFullYear() - birthDate.getFullYear(); // 计算年龄年份差
      const monthDifference = today.getMonth() - birthDate.getMonth();
      if (monthDifference < 0 || (monthDifference === 0 && today.getDate() < birthDate.getDate())) {
          age--; // 如果当前日期在生日之前，年龄减去1
      }
      res.data[i].age = age;

      // 格式化生日
      res.data[i].birthDate = res.data[i].birthDate.replace(/-/g, '/').substring(5,10)
      // 排序
      res.data.sort((a, b) => a.leftDay - b.leftDay);
    }
    birthdayes.value = res.data
  }).catch(err => {
    console.log(err)
    message.error('获取生日列表失败')
  })
}
const deleteBirth = (birthId) => {
  console.log(birthId)
  const postResult = deleteBirthdayApi(birthId)
  postResult.then(res => {
    if(res.data){
      getAllBirthday()
      message.success('删除生日成功')
    }
  }).catch(err => {
    console.log(err)
    message.error('删除生日失败')
  })
}

</script>

<template>
  <div class="all-container">
    <DefaultBackground/>
    <HomeOutlined style="color: #fff;" @click="jumphelper.jumpToSelect()" class="home_icon"/>
    <!--增加一条-->
    <div class="add-container glass-card">
      <a-input style="margin-bottom: 12px;" placeholder="请输入姓名" v-model:value="name"/>
      <div class="date-container">
        <a-date-picker v-model:value="birthDay" type="date" placeholder="请选择生日"/>
        <a-button type="primary" @click="addBirthday">添加生日</a-button>
      </div>
    </div>
    <!--所有生日-->
    <div class="birth-container glass-card">
      <div class="birth-title">
        <div class="birth-column1">姓名</div>
        <div class="birth-column2">生日</div>
        <div class="birth-column3">年龄</div>
        <div class="birth-column4">距离</div>
        <div class="birth-column5">操作</div>
      </div>
      <div class="birth-body">
        <div class="birth-line" v-for="(item,index) in birthdayes" :key="index">
          <div class="birth-name birth-column1">{{item.birthName}}</div>
          <div class="birth-date birth-column2">{{item.birthDate}}</div>
          <div class="birth-age birth-column3">{{ item.age }}</div>
          <div class="birth-left birth-column4">{{ item.leftDay }}</div>
          <div class="birth-delete birth-column5" @click="deleteBirth(item.birthId)">删除</div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.all-container{
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
}
.glass-card{
  background-color: rgba(255,255,255,0.6);
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0,0,0,0.3);
  padding: 24px;
  position: relative;
  backdrop-filter: blur(10px);
}
.all-container .add-container{
  height: 120px;
  width: 90%;
  z-index: 100;
  margin-top: 24px;
}
.all-container .add-container .date-container{
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 4px;
}
.all-container .birth-container{
  height: calc(100vh - 240px);
  width: 90%;
  z-index: 100;
  margin: 12px 0 24px 0;
}
.all-container .birth-container .birth-title{
  right: calc(5% + 8px);
  position: absolute;
  left: 5%;
  top: 16px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}
.all-container .birth-container .birth-body{
  right: 5%;
  overflow-x: hidden;
  overflow-y: scroll;
  position: absolute;
  left: 5%;
  top: 50px;
  bottom: 16px;
}
.all-container .birth-container .birth-line{
  width: 100%;
  display: flex;
  height: 40px;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 6px;
}
.all-container .birth-container .birth-line .birth-name{
  
}
.all-container .birth-container .birth-line div{
  white-space: nowrap;
}
.all-container .birth-container .birth-line .birth-delete{
  color: #e14c53;
  cursor: pointer;
}
.all-container .birth-container .birth-column1{
  flex: 0.4;
  display: flex;
  justify-content: center;
  align-items: center;
}
.all-container .birth-container .birth-column2{
  flex: 0.4;
  display: flex;
  justify-content: center;
  align-items: center;
}
.all-container .birth-container .birth-column3{
  flex: 0.3;
  display: flex;
  justify-content: center;
  align-items: center;
}
.all-container .birth-container .birth-column4{
  flex: 0.3;
  display: flex;
  justify-content: center;
  align-items: center;
}
.all-container .birth-container .birth-column5{
  flex: 0.3;
  display: flex;
  justify-content: center;
  align-items: center;
}


</style>