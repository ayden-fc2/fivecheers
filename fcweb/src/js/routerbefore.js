import router from "@/router/router";
import { checkManager } from './jshelper';
import { message } from 'ant-design-vue';
/**
 * 路由前检查函数
 * @param path
 * @param next
 */
export const routerbefore = (path,next)=>{
    //检查是否管理员
    if(!checkManager()){
        const managerPathes = ['/mybooks', '/dead', '/leaveMsg']
        if(managerPathes.includes(path)){
            message.warn('权限不足')
            return
        }
    }
    //检查是手机还是电脑并跳转
    if (path.match('/desktop/*') || path.match('/phone/*')){
        next()
    }else{
        const devicetype = (window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth) < 768 ? 'phone' : 'desktop';
        router.replace(`/${devicetype}${path}`)
    }
}