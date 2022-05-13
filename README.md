# aw07

This demo shows a MicroPOS System with an architecture illustrated in the figure below: 

![](10-pos.svg)

When an order is placed by a user, the order service sends out an event into RabbitMQ. The delivery service will be notified and a new delivery entry will be generated automatically. User can query the delivery status for his orders.

# How to Run

The code requires RabbitMQ to work properly: 

```shell
# you can visit http://localhost:15672 to check the data exchange
sudo docker run -d -p 5672:5672 -p 15672:15672 -e RABBITMQ_DEFAULT_USER=root -e RABBITMQ_DEFAULT_PASS=Saw0rk_2o22 --name rabbitmq --hostname rabbitmq rabbitmq:3-management
```

Then you can start up the backend in IDEA: 

```shell
# run this command first, under the root directory of this project
mvn clean install

# start up the microservices one by one
...
```

The repository also provides a frontend implementation, and you can check it by: 

```shell
cd pos-frontend
npm install
npm start
```

After the whole project is started, you can visit http://localhost:8080. Currently the page doesn't refresh by itself, so you may need to reenter the page by switching on the left menu.
