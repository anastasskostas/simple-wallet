# Simple Wallet App

The app is running here: [http://34.89.61.27:3000/#/login](http://34.89.61.27:3000/#/login).

with 4 endpoints:

`POST - /login` -> Create a user with default balance and default currency and return token

`GET - /balance` -> Get current balance and currency of the user

`GET - /transactions` -> Get all transactions order by date

`POST - /spend` -> Decrease balance of user passing as json: date, amount, description, currency

## Technologies
**UI**: Frontend is developed with React & React-Bootstrap

**API**: Backend is developed with Java8 & Ratpack

**Storage**: Redis is used for caching data

**Deployment**: Google Cloud - Docker implementation

## Running locally

### UI - steps
##### `cd ui`
##### `npm install`
##### `npm run start-dev` for integrating with api from localhost
##### `npm run start-prod` for integrating with api from server

### API - steps
##### `cd api`
##### `gradle clean`
##### `gradle fatJar` for building the jar file
##### `java -jar build/libs/api.jar` for integrating with api from server

NOTE: in case you run api locally, you need to do two things:
1. Update `hostIp="localhost"` in RedisPool.java
2. Run redis-server locally
