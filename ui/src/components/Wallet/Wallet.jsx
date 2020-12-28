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



  render() {
    return (
      <WalletHtml
        data={this.state}
      />
    )
  }
}

export default WalletComponent;