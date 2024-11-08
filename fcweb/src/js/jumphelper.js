import router from "@/router/router";
// import {postLog} from "@/js/apihelper";

export const jumphelper = {
    jumpToSelect:()=>{
        router.replace('/select')
        // postLog('页面跳转-主页')
    },
    jumpToDead:()=>{
        router.replace('/dead')
        // postLog('页面跳转-墓志铭')
    },
    jumpToMe:()=>{
        router.replace('/me')
        // postLog('页面跳转-我')
    },
    jumpToMyBooks:()=>{
        router.replace('/mybooks')
        // postLog('页面跳转-我的书架')
    },
    jumpToMyGit:()=>{
        router.replace('/mygit')
        // postLog('访问-我的开源')
    },
    jumpToMyMusic:()=>{
        router.replace('/mymusic')
        // postLog('访问-音乐')
    },
    jumpToMyPlan:()=>{
        router.replace('/myplan')
        // postLog('访问-年度计划')
    },
    jumpToMySpace:()=>{
        router.replace('/myspace')
        // postLog('访问-我的空间')
    },
    jumpToMyTravel:()=>{
        router.replace('/mytravel')
        // postLog('访问-旅行日志')
    },
    jumpToManagerPassword:()=>{
        router.replace('/phone/managerpassword')
        // postLog('页面跳转-管理员密码')
    },
    jumpToManagerSelect:()=>{
        router.replace('/phone/managerselect')
        // postLog('页面跳转-管理员选择页面')
    },
    jumpToMyBlog:()=>{
        router.push('/blog')
        // postLog('访问-我的博客')
    },
    jumpToHello:()=>{
        router.replace('/')
        // postLog('页面跳转-首页')
    },
    jumpToLeftAMsg:()=>{
        router.replace('/leaveMsg')
        // postLog('页面跳转-给我留言')
    }
}
