import axios from 'axios';



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
      catchError(error, reject);
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
    }).catch(error => {
      catchError(error, reject);
    });
  });
}


function catchError(error, reject) {
  reject(handleError(error));
}

function handleError(error) {
  if (!error) return;
  let e = {
    status: error.status,
    text: error.statusText
  }
  if (e.status === 401) {
    sessionStorage.removeItem("token");
    window.location.replace(window.location.origin + "/#/login");
  }
  return e
}

function getAuthorizationHeader() {
  if (sessionStorage) {
    return sessionStorage.getItem('token');
  } else {
    return "session-storage-is-not-available";
  }
}
