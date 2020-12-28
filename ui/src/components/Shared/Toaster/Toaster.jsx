import React, { Component } from 'react';
import ToasterHtml from "./Toaster.html";

class ToasterComponent extends Component {

  constructor(props) {
    super(props);
    this.state = {
      content: props.content,
      isError: props.isError,
      showToast: true
    }
  }

  componentDidMount() {
    if (this.state.content) {

    }
  }

  UNSAFE_componentWillReceiveProps(nextProps) {
    if (nextProps.content.text) {
      this.setState({
        content: nextProps.content,
        isError: nextProps.isError,
        showToast: true
      })

    }
  }

  closeToast = () => {
    this.setState({
      showToast: false
    })
  }

  render() {
    return (
      <ToasterHtml
        data={this.state}
        closeToast={this.closeToast}
      />
    )
  }
}

export default ToasterComponent;