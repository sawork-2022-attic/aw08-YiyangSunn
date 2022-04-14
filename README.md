# RESTful MicroPoS 

This is a demo application for REST style and microservice architecture.

## 说明

由于本项目中数据传输对象和控制器接口都是 Swagger 自动生成的，并且不同模块需要使用同样的 DTO 类，所以我把根据 OpenAPI 规范自动生成的代码都放到 pos-api 模块里了。其他模块需要引入 pos-api 这个依赖，才能使用公共的接口。所以在运行 pos-products 和 pos-carts 模块之前需要先 `mvn install` 把 pos-api 模块安装到本地仓库（之前由于一些原因，pos-products 和 pos-carts 模块始终不能正常使用 pos-api 提供的类，现在不知道还有没有这个问题）。

## 服务注册与发现

本项目使用 Eureka 做服务管理，默认运行在 localhost:7001，并且是单机模式（没有配置集群）。

## 服务调用

本项目没有显式使用第三方服务调用库（例如 OpenFeign），而是通过 Spring 提供的 RestTemplate 和 LoadBalancer 进行服务调用。

## 服务网关

本项目使用 Spring Cloud Gateway 作为网关服务，默认运行在 localhost:6001，并且采用轮询方式分发请求（没有配置集群）；其他服务都应当通过 6001 端口来互相调用。

## 服务熔断

本项目使用 resilience4j 做服务熔断。由于 netflix 的 Hystrix 停止更新，目前 netflix 和 Spring Cloud 官方都推荐使用 resilience4j 来进行服务熔断、降级或限流。但非常不幸的是，resilience4j 是按照函数式编程设计的，用起来非常恶心；因此本项目中暂未使用服务限流或超时返回等功能。

## 配置中心

本项目未提供配置中心相关的整合。

## 链路追踪

本项目未提供链路追踪相关的整合。

## 评论

* Spring Cloud 目前是比较混乱的，整合了很多第三方库，但实际上都不是太好用（而且有一些库原来的公司已经不再更新了）；


* 有鉴于此，以及 Spring 团队本身可能也想为 Spring Cloud 正名，目前 Spring Cloud 有逐步替换第三方依赖的迹象；比如 netflix 的 Ribbon 被从 Spring Cloud 中拿掉了，替换它的是 LoadBalancer（尽管该组件似乎仍在开发中，功能还很有限）；


* Spring Cloud Alibaba 和 Spring Cloud 之间有很多版本问题，即使按照阿里给出的版本对照表也不一定能运行；而且阿里的 Nacos 和 Sentinel 都有自己的服务端做配置管理（Nacos 需要在命令行启动服务器），因为我只想包含 Java 代码（可以直接运行），所以没有使用阿里的组件；


* 目前的代码中既有客户端负载均衡，也有服务端负载均衡，但是这两者并不矛盾；客户端通过网关服务器访问具体服务，因此客户端的负载均衡是针对网关服务器集群的；而网关的负载均衡是针对具体某个服务的实例的。

## 计划

可能会在稍后提供一个简单的前端页面，或者加入更多的微服务基础设施（也可能提供 Docker 部署的支持，这样就可以方便的运行同一服务的多个实例）。
