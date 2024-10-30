const { defineConfig } = require('@vue/cli-service')
const packageJson = require('./package.json')

module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 118,
    headers: {
      'Cache-Control': `no-cache`, // 禁用缓存
      'X-Cache-Version': packageJson.version // 设置版本号
    }
  }
})
