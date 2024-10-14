<script setup>
    import { onMounted, ref, reactive } from 'vue'
    import { UpOutlined, DownOutlined } from '@ant-design/icons-vue'
    import { useRoute } from 'vue-router'
    import { message } from 'ant-design-vue';
    import dayjs from 'dayjs';
    import moment from 'moment';
    import { cloneDeep } from 'lodash-es';
    import { addNewFocusApiByDate, getFocusByDateApi, deleteFocusApi, updateFocusApi } from '@/js/apihelper.js'

    // 收缩展开
    const clockOpened = ref(false)
    const changeClockOpened = (event)=>{
        event.stopPropagation();
        clockOpened.value = !clockOpened.value
    }
    //确认身份
    const identity = ref('')
    const route = useRoute()
    const confirmIdentity = ()=>{
        if(route.path === '/phone/blog' || route.path === '/desktop/blog'){
            identity.value = '时啟轩'
        }else{
            identity.value = localStorage.getItem('user') ? localStorage.getItem('user') : '请输入你的名字登陆。'
        }
        identity.value = identity.value === '时啟轩107080' ? '时啟轩' : identity.value
        // 根据时间判断问候语
        const currentHour = new Date().getHours();
        let greeting;
        if (currentHour < 12) {
            greeting = '早上好，';
        } else if (currentHour < 18) {
            greeting = '下午好，';
        } else {
            greeting = '晚上好，';
        }
        message.info(greeting+identity.value)
    }
    /**
     * Clock
     */
    const selectedFocusId = ref(null)
    const getSelectOptions = () => {
        const options = [];
        data.value.forEach(item => {
            options.push({
                label: item.focusName,
                value: item.focusId
            });
        });
        return options;
    }
    const restMode = ref(false)
    const clockSettings = ref({
        restLength: 30,
        workLength: 90
    })
    const clockNumber = ref(90)
    const allWorkTime = ref(0)
    const clockInterval = ref(null)
    const paused = ref(true)
    const changeClockNumber = ()=>{
        clockNumber.value = clockSettings.value.workLength
    }
    // 启动计时器
    const startClock = ()=>{
        if(clockInterval.value){
            clearInterval(clockInterval.value)
        }
        clockInterval.value = setInterval(()=>{
            if(clockNumber.value <= 0){
                restMode.value = !restMode.value
                clockNumber.value = restMode.value ? clockSettings.value.restLength : clockSettings.value.workLength
            }
            clockNumber.value -= 1
            if(!restMode.value){
                allWorkTime.value += 1
                if(selectedFocusId.value){
                data.value.forEach(item => {
                    if(item.focusId === selectedFocusId.value){
                        item.focusLength += 1
                    }
                });
                clockSave(selectedFocusId.value)
            }
            }
        },60000)
        paused.value = false
    }
    // 暂停计时器
    const pauseClock = ()=>{
        if(clockInterval.value){
            clearInterval(clockInterval.value)
        }
        paused.value = true
    }
    // 重置计时器
    const resetClock = ()=>{
        pauseClock()
        clockNumber.value = clockSettings.value.workLength
        restMode.value = false
        paused.value = true
    }
    /**
     * API TODO
     */
     const countDate = ref(dayjs(moment().format('YYYY-MM-DD'), 'YYYY-MM-DD'))
    const columns = [
        {
            title: '名称',
            dataIndex: 'focusName',
            key: 'focusName',
            fixed: 'left',
            width: 140,
        },
        {
            title: '时长(min)',
            dataIndex: 'focusLength',
            key: 'focusLength',
            width: 60,
        },
        {
            title: '细节',
            dataIndex: 'focusDetail',
            key: 'focusDetail',
            width: 200,
        },
        {
            title: '操作',
            dataIndex: 'action',
            key: 'action',
            width: 80,
        }
    ]
    const data = ref([])
    const editableData = reactive({});
    const edit = focusId => {
        editableData[focusId] = cloneDeep(data.value.filter(item => focusId === item.focusId)[0]);
    };
    const save = focusId => {
        const postRes = updateFocusApi(editableData[focusId])
        postRes.then(res=>{
            if(res.data){
                Object.assign(data.value.filter(item => focusId === item.focusId)[0], editableData[focusId]);
                delete editableData[focusId];
                return
            }
            message.error('修改TODO失败')
        }).catch(e=>{
            console.log(e)
            message.error('修改TODO失败')
        })
    };
    const clockSave = ()=>{
        const postRes = updateFocusApi(data.value.filter(item => selectedFocusId.value === item.focusId)[0])
        postRes.then(res=>{
            if(res.data){
                return
            }
            message.error('修改TODO失败')
        }).catch(e=>{
            console.log(e)
            message.error('修改TODO失败')
        })
    }
    const cancel = focusId => {
        delete editableData[focusId];
    };
    const deleteFocus = focusId => {
        const getRes = deleteFocusApi(focusId)
        getRes.then(res=>{
            if(res.data){
                data.value = data.value.filter(item => focusId !== item.focusId);
                return
            }
            message.error('删除TODO失败')
        }).catch(e=>{
            console.log(e)
            message.error('删除TODO失败')
        })
    }
    const getFocus = () => {
        const getRes = getFocusByDateApi({
            userName: identity.value,
            date: countDate.value.format('YYYY-MM-DD')
        })
        getRes.then(res=>{
            if(res.data){
                data.value = res.data
                return
            }
            message.error('获取TODO失败')
        }).catch(e=>{
            console.log(e)
            message.error('获取TODO失败')
        })
    }
    const addFocus = () => {
        const newFocus = {
            focusDate: countDate.value.format('YYYY-MM-DD'),
            focusName: '请输入目标名',
            focusDetail: '请输入目标细节',
            focusUser: identity.value,
            focusLength: 0,
        }
        const postRes = addNewFocusApiByDate(newFocus)
        postRes.then(res=>{
            if(res.data){
                getFocus()
                return
            }
            message.error('新增TODO失败')
        }).catch(e=>{
            console.log(e)
            message.error('新增TODO失败')
        })
    }
    const getAllWorkTime = () => {
        let allTimeMin = 0;
        data.value.forEach(item => {
            allTimeMin += item.focusLength;
        });

        const hours = Math.floor(allTimeMin / 60); // 计算总小时数
        const minutes = allTimeMin % 60; // 计算剩余分钟数

        return `${hours}时${minutes}分`;
    }
    
    onMounted(()=>{
        confirmIdentity()
        getFocus()
    })
</script>

<template>
    <div class="clock-all-container" 
      :class="clockOpened ?
        'clock-all-container-opened' : 
        'clock-all-container-closed'
       " >
       <!--收缩-->
       <div class="clock-closed-number" :style="restMode ? 'color: #739dec;' : 'color: #ab73ec;'">{{ clockNumber }}</div>
        <!--展开收缩-->
        <div class="clock-container">
            <div class="btn-container">
                <DownOutlined v-if="clockOpened" @click="changeClockOpened($event)"/>
                <UpOutlined v-else style="color: #fff; z-index: 999;" @click="changeClockOpened($event)"/>
            </div>
            <div class="other-container">
                <!--时钟-->
                <div class="clock-box">
                    <a-select
                        ref="select"
                        v-model:value="selectedFocusId"
                        style="width: 190px; margin-bottom: 4px"
                        :options="getSelectOptions()"
                        placeholder="选择专注TODO"
                        size="small"
                    ></a-select>
                    <a-input-number :onChange="changeClockNumber" class="input-work" size="small" v-model:value="clockSettings.workLength"/>
                    <a-input-number class="input-rest" size="small" v-model:value="clockSettings.restLength"/>
                    <div class="clock-number">
                        <div 
                        :class="paused ? '' : 'run-ani'"
                        :style="restMode ? 'color: #739dec;' : 'color: #ab73ec;'">{{ clockNumber }}</div>
                    </div>
                    <div>{{ allWorkTime }}</div>
                    <div class="btn-container">
                        <div v-if="paused" @click="startClock">开始</div>
                        <div v-else @click="pauseClock">暂停</div>
                        <div @click="resetClock">重置</div>
                    </div>
                </div>
                <!--TODO-->
                <div class="todo-box">
                    <a-date-picker @change="getFocus" size="small" class="date-input" v-model:value="countDate" />
                    <a-button @click="addFocus" size="small" class="add-btn">新增</a-button>
                    <div class="todo-title">{{ getAllWorkTime() }}</div>
                    <a-table :pagination="false" :scroll="{ x: 800, y: 176 }" borderd size="small" class="focus-table" :columns="columns" :data-source="data">
                        <template #bodyCell="{ column, text, record }">
                            <template v-if="column.key === 'focusName' || column.key === 'focusDetail'">
                                <div>
                                    <a-input
                                        size="small"
                                        v-if="editableData[record.focusId]"
                                        v-model:value="editableData[record.focusId][column.dataIndex]"
                                        style="margin: -5px 0"
                                    />
                                    <template v-else>
                                        {{ text }}
                                    </template>
                                </div>
                            </template>
                            <template v-else-if="column.key === 'focusLength'">
                                <div>
                                    <a-input-number
                                        size="small"
                                        v-if="editableData[record.focusId]"
                                        v-model:value="editableData[record.focusId][column.dataIndex]"
                                        style="margin: -5px 0"
                                    />
                                    <template v-else>
                                        {{ text }}
                                    </template>
                                </div>
                            </template>
                            <template v-else-if="column.key === 'action'">
                                <div class="editable-row-operations">
                                    <span v-if="editableData[record.focusId]">
                                        <a-typography-link @click="save(record.focusId)">保存</a-typography-link>
                                        <a-typography-link style="color: red;"  @click="cancel(record.focusId)">取消</a-typography-link>
                                    </span>
                                    <span v-else>
                                        <a @click="edit(record.focusId)">编辑</a>
                                        <a-popconfirm title="确定删除?" @confirm="deleteFocus(record.focusId)">
                                            <a style="color: red;">删除</a>
                                        </a-popconfirm>
                                        
                                    </span>
                                    </div>
                            </template>
                        </template>
                    </a-table>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
    .clock-all-container{
        position: fixed;
        z-index: 9999;
        color: #333;
    }
    .clock-all-container-opened{
        bottom: 0;
        right: 0;
        width: 100vw;
        height: 300px;
        transition: .6s;
        background: linear-gradient(to right bottom, #dbe1ecdd, #f1e4f1dd);
        border-top-left-radius: 12px;
        border-top-right-radius: 12px;
    }
    .clock-container{
        position: absolute;
        left: 0;
        right: 0;
        bottom: 0;
        top: 0;
        display: flex;
        align-items: center;
        justify-content: space-around;
        flex-direction: column;
    }
    .clock-container .btn-container{
        height: 30px;
        width: 100%;
        display: flex;
        align-items: center;
        border-bottom: 1px solid #fff;
        padding: 4px;
        justify-content: center;
    }
    .clock-container .btn-container div{
        margin: 0 8px;
        font-size: 14px;
    }
    .clock-container .other-container{
        flex: 1;
        width: 100%;
        padding: 4px 8px;
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
    .clock-container .other-container .clock-box{
        height: 100%;
        width: 40%;
        min-width: 200px;
        max-width: 280px;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
    }
    .clock-container .other-container .clock-box .input-work{
        position: relative;
        width: 80px;
        margin: 4px 0 4px 110px;
    }
    .clock-container .other-container .clock-box .input-work::before{
        content: "专注时间(min):";
        position: absolute;
        left: -110px;
        top: 0;
        color: #ab73ec;
    }
    .clock-container .other-container .clock-box .input-rest{
        margin: 4px 0 4px 110px;
        position: relative;
        width: 80px;
    }
    .clock-container .other-container .clock-box .input-rest::before{
        content: "休息时间(min):";
        position: absolute;
        left: -110px;
        top: 0;
        color: #739dec;
    }
    .clock-container .other-container .clock-box .clock-number{
        font-size: 32px;
        height: 90px;
        width: 90px;
        background-color: #fff;
        border-radius: 999px;
        display: flex;
        align-items: center;
        justify-content: center;
        margin: 8px;
        position: relative;
        overflow: hidden;
    }
    .clock-container .other-container .clock-box .clock-number div{
        display: flex;
        align-items: center;
        justify-content: center;
        white-space: nowrap;
        width: 0;
        height: 0;
        background-color: #dbe1ec;
        border-radius: 999px;
        opacity: .6;
    }
    .clock-container .other-container .clock-box .clock-number .run-ani{
        animation: clock-ani infinite linear 60s;
    }
    @keyframes clock-ani{
        100%{
            width: 100%;
            height: 100%;
            opacity: 1;
        }
    }
    .clock-container .other-container .clock-box .btn-container{
        width: 190px;
        height: 32px;
        display: flex;
    }
    .clock-container .other-container .clock-box .btn-container div{
        cursor: pointer;
    }
    .clock-container .other-container .todo-box{
        height: 100%;
        flex: 1;
        min-width: 200px;
        position: relative;
    }
    .clock-container .other-container .todo-box .todo-title{
        position: absolute;
        left: 64px;
        top: 14px;
        font-size: 13px;
    }
    .clock-container .other-container .todo-box .add-btn{
        position: absolute;
        left: 4px;
        top: 10px;
    }
    .clock-container .other-container .todo-box .date-input{
        position: absolute;
        right: 8px;
        top: 10px;
    }
    .clock-container .other-container .todo-box .focus-table{
        position: absolute;
        bottom: 8px;
        right: 8px;
        left: 4px;
        top: 44px;
    }
    .clock-container .other-container .todo-box .focus-table .editable-row-operations a{
        margin-right: 8px;
    }
    .clock-all-container-closed{
        right: 24px;
        bottom: 0;
        width: 36px;
        height: 24px;
        background: linear-gradient(to right bottom, #839cca, #e094dd);
        display: flex;
        align-items: center;
        justify-content: center;
        border-top-left-radius: 8px;
        border-top-right-radius: 8px;
        transition: .6s;
    }
    .clock-all-container-closed .btn-container{
        position: absolute;
        left: 0%;
        top: 0;
        bottom: 0;
        right: 0;
    }
    .clock-all-container-closed .other-container {
        opacity: 0;
    }
    .clock-closed-number{
        position: absolute;
        left: -48px;
        font-size: 13px;
        bottom: 4px;
    }
    .clock-closed-number::after{
        content: "min"
    }
</style>