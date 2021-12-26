FROM node:14.18.1-alpine3.11
EXPOSE 8080
ADD ./ /front
WORKDIR /front
RUN npm install
ENTRYPOINT ["npm", "run", "serve"]