import React, { Component } from "react";
import TransactionsHtml from "./Transactions.html";
import { getTransactions } from "../../services/Wallet.service";


class TransactionsComponent extends Component {

  constructor() {
    super();
    this.state = {
      isLoadingTableData: false,
      transactions: []
    }
  }

  componentDidMount() {
    this.getTransactions();
  }

  getTransactions = () => {
    this.setState({ isLoadingTableData: true });
    getTransactions().then((response) => {
      this.setState({
        transactions: response.data,
        isLoadingTableData: false
      })
    }).catch(error => {

    }).finally(() => {
      this.setState({
        isLoadingTableData: false
      })
    })

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