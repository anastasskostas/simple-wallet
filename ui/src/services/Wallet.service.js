import config from '../config/config';
import { get, post } from './request';

export const getTransactions = () => {
  return get(`${config.baseUrl}${config.getTransactionsEndpoint}`)
}

export const getBalance = () => {
  return get(`${config.baseUrl}${config.getBalanceEndpoint}`)
}

export const updateBalance = (data) => {
  return post(`${config.baseUrl}${config.updateBalanceEndpoint}`, data)
}