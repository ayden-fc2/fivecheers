<script setup>
import {bus} from "vue3-eventbus";
import {UploadOutlined} from '@ant-design/icons-vue'
import { onMounted } from "vue";
import {getAllDeadInfo, changeAllDeadInfo} from '@/js/apihelper'
import { message } from "ant-design-vue";
import { ref } from "vue";

const closeSelectCard = ()=>{
  bus.emit('closeSelectCard')
}
const getAllInfo = ()=>{
  const getResult = getAllDeadInfo()
  getResult.then(res=>{
    // console.log(res)
    allInfo.value.amIDead = res.data.amIDead
    allInfo.value.lastWord = res.data.lastWord
    allInfo.value.mapUrl = res.data.mapUrl
    allInfo.value.giftUrl = res.data.giftUrl
  }).catch(err=>{
    console.log(err)
    message.error('获取遗书信息失败')
  })
}

onMounted(()=>{
  getAllInfo()
})

const allInfo = ref({
  deadId: null,
  time: null,
  giftStill: null,
  amIDead: 10002,
  lastWord: '',
  mapUrl: '',
  giftUrl: '',
})

const downloadFile = (downloadFile)=>{
  window.location.href = downloadFile
}

const handleImgUploadChange1 = (info)=>{
  if (info.file.status === 'done') {
    message.success(`文件上传成功`);
    allInfo.value.mapUrl = info.file.response
  } else if (info.file.status === 'error') {
    message.error(`${info.file.name} 文件上传失败`);
  }
}

const handleImgUploadChange2 = (info)=>{
  if (info.file.status === 'done') {
    message.success(`文件上传成功`);
    allInfo.value.giftUrl = info.file.response
  } else if (info.file.status === 'error') {
    message.error(`${info.file.name} 文件上传失败`);
  }
}

const changeAllSubmit = ()=>{
  const postResult = changeAllDeadInfo(allInfo.value)
  postResult.then(res=>{
    if(res.data){
      message.success('更改成功')
      getAllInfo()
      return
    }
    message.error('更改失败')
  }).catch(err=>{
    console.log(err)
    message.error('更改失败')
  })
}
</script>

<template>
  <div class="select_com_background">
    <div @click="closeSelectCard()" style="position: fixed; left: 0; right: 0; top: 0; bottom: 0"></div>
    <h2>- 遗书与藏宝图 -</h2>
    <div class="select_com_card">
        <!--遗言展示-->
        <div class="card-line">
          <h5>遗言展示</h5>
          <a-textarea v-model:value="allInfo.lastWord" min-rows="5" :rows="6" placeholder="请输入您的遗言"></a-textarea>
        </div>
        <!--遗言文件-->
        <div class="card-line">
          <h5>遗言文件</h5>
          <div>
            <a-button @click="downloadFile(allInfo.mapUrl)">下载已有文件</a-button>
            <a-upload
            style="max-width: 50%;"
            action="http://www.fivecheers.com:1001/file/defaultUpLoadFile"
            @change="handleImgUploadChange1">
              <a-button type="primary">
                <upload-outlined></upload-outlined>
                更改文件
              </a-button>
            </a-upload>
          </div>
        </div>
        <!--藏宝图密码-->
        <div class="card-line">
          <h5>藏宝图密码</h5>
          <a-input-number v-model:value="allInfo.amIDead"/>
        </div>
        <!--藏宝图文件-->
        <div class="card-line">
          <h5>藏宝图文件</h5>
          <div>
            <a-button @click="downloadFile(allInfo.giftUrl)">下载已有文件</a-button>
            <a-upload
            type="primary"
            style="max-width: 50%;"
            action="http://www.fivecheers.com:1001/file/defaultUpLoadFile"
            @change="handleImgUploadChange2">
              <a-button type="primary">
                <upload-outlined></upload-outlined>
                更改文件
              </a-button>
            </a-upload>
          </div>
        </div>  
        <div>
          <a-button @click="changeAllSubmit" style="width: 240px; margin-top: 24px;" type="primary">确认更改</a-button>
        </div>
    </div>
  </div>
</template>

<style scoped>
.select_com_background{
  position: fixed;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  background: #00000044;
  animation: fade-in .5s 1;
}
.select_com_background h2{
  position: absolute;
  top: calc(25vh - 145px);
  left: 50%;
  transform: translateX(-50%);
  color: white;
  letter-spacing: 6px;
  font-size: 20px;
  white-space: nowrap;
}
.select_com_card{
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  max-height: 580px;
  height: calc(100% - 88px);
  background: #fff;
  border-radius: 8px;
  padding: 16px 12px;
  overflow-y: scroll;
  overflow-x: hidden;
  width: calc(100% - 12px);
  max-width: 600px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
  padding: 24px 8px;
}
.select_com_card .card-line{
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: start;
}
.select_com_card .card-line h5{
  margin-bottom: 12px;
}
.select_com_card .card-line div{
  display: flex;
  width: 100%;
  justify-content: space-between;
}
</style>
