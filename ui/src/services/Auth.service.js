import config from '../config/config';
import { post } from './request';

export const login = () => {
  return post(`${config.baseUrl}${config.loginEndpoint}`)
}
