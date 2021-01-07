import axios from 'axios';
import { displayNotification } from '../utils/CallsInterceptor.jsx';

// create a custom instance of axios
const instance = axios.create();

// Add a request interceptor
instance.interceptors.request.use(request => {
  // Add Authorization header
  request.headers.Authorization = getAuthorizationHeader();
  return request;
}, error => {
  return Promise.reject(error);
});

// Add a response interceptor
instance.interceptors.response.use(response => {
  // Do something with response data
  return response;
}, error => {
  // Any status codes that falls outside the range of 2xx cause this function to trigger
  return Promise.reject(error.response);
})


export function get(url) {
  return new Promise((resolve, reject) => {
    instance.get(url, {
      headers: {
        'Accept': 'application/json'
      }
    }).then(response => {
      resolve(response);
    }).catch(error => {
      catchError(handleResponse(error), reject);
    });
  })
}


export function post(url, data) {
  return new Promise((resolve, reject) => {
    instance.post(url, data, {
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
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
