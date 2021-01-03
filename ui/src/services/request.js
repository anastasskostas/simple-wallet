import axios from 'axios';
import { displayNotification } from '../utils/CallsInterceptor.jsx';


axios.interceptors.response.use(response => {
  //here can global handle response
  return response;
}, error => {
  //here can global handle error 
  return Promise.reject(error.response);
})


export function get(url) {
  return new Promise((resolve, reject) => {
    axios.get(url, {
      responseType: 'content-type',
      headers: { 'Authorization': getAuthorizationHeader() }
    }).then(response => {
      resolve(response);
    }).catch(error => {
      catchError(handleResponse(error), reject);
    });
  })
}


export function post(url, data) {
  return new Promise((resolve, reject) => {
    axios.post(url, data, {
      headers: {
        'Content-Type': `application/json`,
        'Authorization': getAuthorizationHeader()
      }
    }).then(response => {
      resolve(response);
      displayNotification(handleResponse(response), false);
    }).catch(error => {
      catchError(handleResponse(error), reject);
    });
  });
}

function handleResponse(response) {
  if (!response) return;
  return {
    status: response.status,
    statusText: (response.data && typeof response.data ==="string" && response.data.length <= 100) ? response.data : response.statusText
  }
}

function catchError(error, reject) {
  if (error && error.status === 401) {
    sessionStorage.removeItem("token");
    window.location.replace(window.location.origin + "/#/login");
  }
  reject(error);
  displayNotification(error, true);
}

function getAuthorizationHeader() {
  if (sessionStorage) {
    return sessionStorage.getItem('token');
  } else {
    return "session-storage-is-not-available";
  }
}
