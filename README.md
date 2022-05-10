# Amazon WebPOS

此分支基于 aw05 的微服务示例搭建前端页面，使用 Amazon Review 数据集作为后端数据来源，并提供分页查询。

# 运行方法

在运行此分支前请确保已经**用此仓库下的其他分支生成数据库**：

1. 在项目根目录下 mvn install

2. 启动 Eureka 服务器；

3. 启动 pos-products 和 pos-carts 两个服务；

4. 启动 pos-gateway

5. 启动 pos-frontend

前四步可以在 idea 中直接完成；启动前端请使用：

```shell
cd pos-frontend

# 安装依赖
npm install

# 启动前端服务
npm start
```

前端在开发环境启动是很慢的，以后发布构建好的版本就很快了。如果碰到问题，比如程序不能运行，可以直接在此仓库提 issue。
