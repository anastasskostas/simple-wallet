import React from 'react';
import { Button } from 'react-bootstrap'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'

function LoginHtml(props) {


  return (
    <div className="text-center">
      <h3>Login</h3>
      <Button disabled={props.data.doesLogin} variant="primary" onClick={props.login}>Login {props.data.doesLogin ? <FontAwesomeIcon icon={['fas', 'spinner']} className="fa-pulse" /> : ''}</Button>
    </div>
  );
}
export default LoginHtml;