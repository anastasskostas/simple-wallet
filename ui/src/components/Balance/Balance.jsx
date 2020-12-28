import React, { Component } from "react";
import BalanceHtml from "./Balance.html";


class BalanceComponent extends Component {

  constructor() {
    super();
    this.state = {
    }
  }

  componentDidMount() {
  }


  render() {
    return (
      <BalanceHtml
        data={this.state}
      />
    )
  }
}

export default BalanceComponent;