import React, { Component } from "react";
import LoginHtml from "./Login.html";
import { login } from "../../services/Auth.service";


class LoginComponent extends Component {

  constructor() {
    super();
    this.state = {
      doesLogin: false
    }
  }

  componentDidMount() {
  }

  login = () => {
    this.setState({ doesLogin: true });
    login().then((response) => {
      this.setState({
        doesLogin: false
      })
      sessionStorage.setItem("token", response.data.token)
      this.props.history.push('/wallet')
    }).catch(error => {
      this.setState({
        doesLogin: false
      })
    });
  }

  render() {
    return (
      <LoginHtml
        data={this.state}
        login={this.login}
      />
    )
  }
}

export default LoginComponent;