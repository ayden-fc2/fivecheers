<script setup>
  import Vditor from 'vditor'
  import 'vditor/dist/index.css';
  import {onBeforeUnmount, onMounted, ref, watch, defineExpose} from "vue";
  import {bus} from "vue3-eventbus";
  import {message} from "ant-design-vue";
  import {getDocDetailApiOther, updateDocDetailOther} from "@/js/apihelper";
  import ClockCom from '../ClockCom.vue';
  import { useRoute } from 'vue-router'
  import {postLog} from "@/js/apihelper";

  const route = useRoute()


  const contentLoaded = ref(false)
  const selectNodeHandler = async (nodeKey) => {
      await saveDocDetail();
      contentLoaded.value = false;
      getDocDetail(nodeKey);
  };

  const clearUser = () =>{
    localStorage.removeItem('user')
  }

  const user = ref(null)
  onMounted( async ()=>{
    postLog('访问-共享空间')
    user.value = localStorage.getItem('user')
    vditor.value = new Vditor('vditor',options.value)
    // 首次加载时检查并调用 selectNodeHandler
    const initialSelectedKey = route.query.selectedKey;
    if (initialSelectedKey) {
        selectNodeHandler(initialSelectedKey);
    }
    watch(() => route.query.selectedKey, (newSelectedKey) => {
      if (newSelectedKey) {
        selectNodeHandler(newSelectedKey); // 调用处理函数
      }
    });
    window.addEventListener('beforeunload', async (event) => {
        await saveDocDetail();
        event.returnValue = '文件已保存'; 
      });
  })

  onBeforeUnmount(()=>{
    bus.off('selectNode')
    vditor.value.destroy()
  })

  /**
   * 获取详细内容
   */
  const docNodeKey = ref()
  const getDocDetail = (nodeKey)=>{
    //编辑器未加载完成，延迟获取
    if(!loaded.value){
      setTimeout(()=>{
        getDocDetail(nodeKey)
      },500)
      return
    }
    //获取
    const contentResult = getDocDetailApiOther(nodeKey, user.value)
    contentResult.then(response=>{
      if(response.data.blogKey){
        docNodeKey.value = response.data.blogKey
        if(isManager){
          vditor.value.setValue(decodeURIComponent(response.data.content))
          contentLoaded.value = true
        }else{
          Vditor.preview(document.getElementById(`vditor`), decodeURIComponent(response.data.content), {});
          document.getElementById(`vditor`).style.padding = '0 24px'
        }
      }
    })
  }

  /**
   * 保存内容
   */
  const saveDocDetail = ()=>{
    try {
      if(!vditor.value || vditor.value.getValue() === '加载中...如果长时间未响应，请尝试刷新页面。' || !contentLoaded.value){
        return
      }
      const postResult = updateDocDetailOther({
        currentKey: docNodeKey.value,
        newContent: encodeURIComponent(vditor.value.getValue()),
        user: user.value
      })
      postResult.then(response=>{
        if(response.data){
          message.success('保存成功')
          return
        }
        message.error('保存失败')
      })
    } catch (error) {
      console.log(error)
    }
  }

  defineExpose({
    saveDocDetail,
  })


  /**
   * 获取当前文本内容
   */
  const getContent = ()=>{
    const tmp = "加载中...如果长时间未响应，请尝试刷新页面。"
    if(isManager){
      vditor.value.setValue(tmp)
    }else{
      Vditor.preview(document.getElementById(`vditor`), tmp, {});
      document.getElementById(`vditor`).style.padding = '0 24px'
    }
    loaded.value = true
  }

  /**
   * 编辑器设置
   */
  const isManager = true
  const vditor = ref()
  const loaded = ref(false)
  const options = ref({
    toolbar:[
       'upload', 'link', 'record', 'export', '|' ,
       'line' , 'quote', '|',
       'bold', 'italic', 'strike', '|',
       'table' , 'list', 'ordered-list', 'check' , 'code' , '|' ,
       'edit-mode', 'outline'
    ],
    preview:{
      maxWidth: 1400
    },
    toolbarConfig:{
      pin: true,
      hide: false
    },
    cache: {
      enable: false
    },
    after: getContent,
    value: 'loading...',
    upload: {
      url: 'http://www.fivecheers.com:1001/file/blogUpLoadFile'
    },
    counter: {
      enable: true
    },
    mode: 'wysiwyg',
  })


</script>

<template>
  <div class="content-container">
    <!--内容-->
    <div class="content-self" id="vditor">加载中...如果长时间未响应，请尝试刷新页面。</div>
    <div class="a-container">
      <a href="https://ld246.com/guide/markdown">使用教程</a>
      <a @click="clearUser" href="/">退出登录</a>
    </div>
    <!-- <a-button size="small" @click="saveDocDetail" v-if="isManager" type="default">保存</a-button> -->
    <ClockCom/>
  </div>
</template>

<style scoped>
  .content-container{
    width: 100%;
    display: flex;
    flex-direction: column;
  }
  .content-container .a-container{
    position: fixed;
    right: 48px;
    top: 8px;
    z-index: 999;
    min-width: 300px;
    display: flex;
    align-items: center;
    justify-content: end;
  }
  .content-container .a-container a{
    color: #91aefa;
    margin-left: 16px;
  }
  .content-container button{
    position: fixed;
    right: 16px;
    top: 44px;
    z-index: 999;
  }
  .content-container:deep(.vditor-reset){
    padding: 0 16px;
  }
  .content-container .content-self{
    min-height: 90vh;
    border: 0;
    position: relative;
    z-index: 999;
  }
  .content-container ::v-deep(.vditor-toolbar) {
    padding: 0;
    line-height: 0;
    border-radius: 0;
    border: 0;
    border-bottom: 1px solid #eee;
    background: linear-gradient(to right bottom, #edf0fc88, #faf3f988);
    backdrop-filter: blur(4px);
  }
</style>
