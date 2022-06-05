# About

This repository has slightly modified the micropos system built in aw07 to illustrate the basic use of Spring Integration. 

![](Micropos.svg)

# How to Run

First download the code, install all dependencies and start up rabbitmq:

```shell
git clone https://github.com/sawork-2022/aw08-YiyangSunn.git

cd aw08-YiyangSunn

mvn clean install

sudo docker run -d -p 5672:5672 -p 15672:15672 -e RABBITMQ_DEFAULT_USER=root -e RABBITMQ_DEFAULT_PASS=Saw0rk_2o22 --name rabbitmq --hostname rabbitmq rabbitmq:3-management
```

Then start the microservices one by one, with `pos-discovery` been the first. The application can be visited at `http://localhost:8080`. I have prebuilt the frontend code and deployed it on `pos-gateway` as static resources, so the `pos-frontend` module need **not** be started anymore.

# Advantage

Enterprise Integration enables seamless collaboration, combining functionality and information exchange across multiple independent applications. Their interconnection helps simplify IT processes in a manner that makes life easier for users and organizations<sup><a href="https://www.ibm.com/cloud/blog/enterprise-integration">[1]</a></sup>.

I personally think it's more of a goal than a solution. We do integration because we want to do it, or need to do it, not better to do it: 

* We may want to reuse an existing application;

* We may want to share some critical data;

* We may want to collaborate with another enterprise;

* We may want to gain more profits by building a universal system;

* ......

Enterprise Integration is something driven by business needs, not technical evolution. It can always be done if enterprises want to do it, and can never be done if they don't want to. 
