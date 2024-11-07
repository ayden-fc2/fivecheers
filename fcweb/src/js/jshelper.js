import { v4 as uuidv4 } from 'uuid';
import {getIpAddress} from "@/js/apihelper";

export const timeCorrect = (wrongTime)=>{
    {
        const utcDate = new Date(wrongTime);
        const options = {
            timeZone: 'Asia/Shanghai',
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit',
        };
        const formattedDate = new Intl.DateTimeFormat('zh-CN', options)
            .formatToParts(utcDate)
            .map(({ type, value }) => (type === 'literal' && value === '-' ? ' ' : value))
            .join('');
        return formattedDate;
    }
}

export const checkAmIDead = (getTime)=>{
    const targetDate = new Date(getTime);
    const currentDate = new Date();
    const timeDifference = currentDate.getTime() - targetDate.getTime();
    const daysDifference = timeDifference / (1000 * 3600 * 24);
    if (daysDifference <= 30) {
        return false;
    } else {
        return true;
    }
}

export const checkManager = ()=>{
    return localStorage.getItem('managerSecret') && localStorage.getItem('managerSecret') === 'nzt100years'
}

// utils/debounce.js
export function debounce(func, wait) {
    let timeout;
    return function(...args) {
        const context = this;
        clearTimeout(timeout);
        timeout = setTimeout(() => func.apply(context, args), wait);
    };
}

export const initAuth = async ()=>{
    //获取UUID，ip，time
    const UUID = localStorage.getItem('UUID')
    const ipAddress = localStorage.getItem('ipAddress')
    const visitTime = localStorage.getItem('visitTime')
    //没有UUID则赋予UUID
    if (!UUID){
        localStorage.setItem('UUID',uuidv4())
    }
    //没有visitTime则赋予visitTime
    if (!visitTime){
        setVisitTime()
    }
    //没有ipAddress则赋予ipAddress
    if (!ipAddress || visitTime !== getNowTime()){
        await setIpAddress()
        setVisitTime()
    }
}

const setIpAddress = async () => {
    try {
        const response = await getIpAddress(); // 假设 getIpAddress() 返回一个 Promise
        const ipAddress =
            'location:' + response.data.country + response.data.province + response.data.area + response.data.city +
            ' ip:' + response.data.ip +
            ' isp:' + response.data.isp;
        localStorage.setItem('ipAddress', ipAddress);
    } catch (e) {
        console.log('获取用户ip出错' + e);
        localStorage.setItem('ipAddress', e);
    }
}

const setVisitTime = ()=>{
    localStorage.setItem('visitTime',getNowTime())
}

const getNowTime = ()=>{
    const date = new Date()
    const year = date.getFullYear()
    const month = String(date.getMonth() + 1).padStart(2,'0')
    const day = String(date.getDate()).padStart(2, '0')
    const nowTime = `${year}-${month}-${day}`
    return nowTime
}