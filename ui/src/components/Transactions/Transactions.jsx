import React, { Component } from "react";
import TransactionsHtml from "./Transactions.html";


class TransactionsComponent extends Component {

  constructor() {
    super();
    this.state = {
    }
  }

  componentDidMount() {
  }



  render() {
    return (
      <TransactionsHtml
        data={this.state}
      />
    )
  }
}

export default TransactionsComponent;