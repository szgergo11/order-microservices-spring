# Event-Driven Microservices with Kafka - Order System
A project to showcase asynchronous event-driven microservice communication using Kafka.

![architecture](https://github.com/szgergo11/order-microservices-spring/blob/master/assets/architecture.png)


## Services

### Order service
* Receive `POST /order` -> create and persist order -> publish `order.created`
* Receive `stock.reserve.confirmed` -> update order to pending -> publish `order.pending`
* Receive `payment.confirmed` → update order to completed -> publish `order.completed`

### Stock service
* Receive `order.created` -> check and reserve stock:
  * Publish `stock.reserve.confirmed` or `stock.reserve.failed`
* Receive `order.failed` -> release reserved stock
    
### Payment Service
* Receive `order.pending` -> process payment:
  * Publish `payment.confirmed` or `payment.failed`

### Auth Service
* Recieve `POST /login` -> issue access and refresh tokens
* Recieve `POST /revoke` -> revoke refresh token
* Recieve `POST /token` -> refresh access token and rotate refresh token


## Order Flow
![flow](https://github.com/szgergo11/order-microservices-spring/blob/master/assets/flow.png)


## Technologies
* Spring Boot
* Kafka
* Docker
* SQL Server
* Spring Data JPA
