import React, { Component } from "react";
import LoginHtml from "./Login.html";


class LoginComponent extends Component {

  constructor() {
    super();
    this.state = {
    }
  }

  componentDidMount() {
  }


  render() {
    return (
      <LoginHtml
        data={this.state}
      />
    )
  }
}

export default LoginComponent;