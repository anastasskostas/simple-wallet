FROM node:alpine

# cd /app
WORKDIR /app

COPY package*.json ./

RUN apk add --no-cache --virtual .gyp \
        python \
        make \
        g++ \
    && npm install \
    && apk del .gyp

# copy all files
COPY . .

EXPOSE 3000
CMD ["npm", "run", "start-prod"]