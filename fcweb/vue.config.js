const { defineConfig } = require('@vue/cli-service')
const packageJson = require('./package.json')
const TerserPlugin = require('terser-webpack-plugin')
const CompressionWebpackPlugin = require('compression-webpack-plugin')

const productionGzipExtensions = ['js', 'css', 'html', 'svg']

module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 118,
    headers: {
      'Cache-Control': 'no-cache',
      'X-Cache-Version': packageJson.version
    }
  },
  configureWebpack: (config) => {
    config.plugins.push(
      new TerserPlugin({
        parallel: true,
        terserOptions: {
          warnings: false,
          compress: {
            drop_console: true,
            drop_debugger: true
          }
        },
      }),
      new CompressionWebpackPlugin({
        filename: '[path][base].gz[query]',
        algorithm: 'gzip',
        test: new RegExp('\\.(' + productionGzipExtensions.join('|') + ')$'),
        threshold: 10240,
        minRatio: 0.8,
      })
    )

    config.mode = 'production'

    const optimization = {
      runtimeChunk: 'single',
      splitChunks: {
        chunks: 'all',
        maxInitialRequests: Infinity,
        minSize: 20000,
        cacheGroups: {
          vendor: {
            test: /[\\/]node_modules[\\/]/,
            name: (module) => {
              const match = module.context.match(/[\\/]node_modules[\\/](.*?)([\\/]|$)/);
              if (match) {
                const packageName = match[1];
                return `npm.${packageName.replace('@', '')}`;
              }
              return null; // 或者返回一个默认值
            }
          }
        }
      }
    }
    Object.assign(config, {
      optimization
    })
  }
})
