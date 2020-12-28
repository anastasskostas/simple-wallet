import React from 'react';
import ReactDOM from 'react-dom';
import ToasterComponent from '../components/Shared/Toaster/Toaster';

export const displayNotification = (content, isError) => {
  ReactDOM.render(<ToasterComponent content={handleContent(content)} isError={isError} />, document.getElementById('toaster-notifications'));
}

const handleContent = (content) => {
  if (!content) return;
  return {
    status: content.status,
    text: content.statusText
  }
}