let config;
if (process.env.REACT_APP_ENVIRONMENT === 'prod') {
  config = require(`./config.prod`).default
}
if (process.env.REACT_APP_ENVIRONMENT === 'dev') {
  config = require(`./config.dev`).default
}



const configuration = {
    getTransactionsEndpoint: "/transactions",
    getBalanceEndpoint: "/balance",
    updateBalanceEndpoint: "/spend",
    loginEndpoint: "/login",
    
    ...config
  };
  
  export default configuration;