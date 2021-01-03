import React from 'react';
import { Toast } from 'react-bootstrap';

function ToasterHtml(props) {

  return (

    <div
      aria-live="polite"
      aria-atomic="true"
      style={{
        position: 'relative',
        minHeight: '300px',
      }}
    >
      <div
        style={{
          position: 'fixed',
          top: 0,
          left: '50%',
          width: 400,
          marginLeft: -200
        }}
      >

        <Toast
          key={'toast'}
          show={props.data.showToast}
          onClose={props.closeToast}
          delay={3000}
          autohide={true}
        >
          <Toast.Header className={props.data.isError ? 'text-danger' : 'text-success'}>
            <strong className="mr-auto">{props.data.isError ? 'Error' : 'Success'}</strong>
          </Toast.Header>
          <Toast.Body className={props.data.isError ? 'text-danger' : 'text-success'}>
            {props.data.content.statusText}
          </Toast.Body>
        </Toast>

      </div>
    </div>

  )

}

export default ToasterHtml