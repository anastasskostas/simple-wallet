import React, { Component } from "react";
import WalletHtml from "./Wallet.html";


class WalletComponent extends Component {

  constructor() {
    super();
    this.state = {
    }
  }

  componentDidMount() {
  }

  logout = () => {
    sessionStorage.removeItem("token");
    window.location.replace(window.location.origin + "/#/login");
  }

  render() {
    return (
      <WalletHtml
        data={this.state}
        logout={this.logout}
      />
    )
  }
}

export default WalletComponent;