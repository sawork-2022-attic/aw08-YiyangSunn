# MicroPOS Frontend

这里是 Micropos 的前端服务，运行此应用请使用：

```shell
# 切换到 pos-frontend 目录
cd pos-frontend
# 安装依赖
npm install
# 启动程序（首次启动要全部编译一遍，速度很慢）
npm start
```

这将会在 localhost:3000 运行一个前端服务器，而用户是通过访问 localhost:6001 的网关来访问前端页面的。也就是说，前端服务也是经过 Gateway 转发访问的（这样没有跨域问题，当然也可以用别的方式解决 ajax 跨域；建议先启动后端服务，Gateway 更新数据有一定延迟）。
