import React, { Component } from "react";
import BalanceHtml from "./Balance.html";
import { updateBalance, getBalance } from "../../services/Wallet.service";


class BalanceComponent extends Component {

  constructor() {
    super();
    this.state = {
      doesDecreaseBalance: false,
      currentBalance: 0,
      currency: 'N/A',
      amount: '',
      insufficientBalance: false,
      description: ''
    }
  }

  componentDidMount() {
    this.getBalance()
  }

  getBalance = () => {
    getBalance().then(response => {
      this.setState({
        currentBalance: response.data.balance,
        currency: response.data.currency
      })
    }).catch(error => {

    })
  }

  decreaseBalance = () => {
    const { amount, currentBalance, description, currency } = this.state;
    if (amount > currentBalance) {
      this.setState({ insufficientBalance: true })
      return;
    }
    this.setState({ doesDecreaseBalance: true })

    const data = {
      date: new Date(),
      description: description,
      amount: amount,
      currency: currency
    }
    updateBalance(data).then(() => {
      this.setState({
        currentBalance: currentBalance - amount,
        amount: '',
        description: ''
      })
    }).catch(error => {
    }).finally(() => {
      this.setState({
        doesDecreaseBalance: false
      })
    })
  }

  handleInputChange = (e, dropdownText) => {
    this.setState({
      [dropdownText]: e.target.value,
      insufficientBalance: false
    })
  }

  render() {
    return (
      <BalanceHtml
        data={this.state}
        decreaseBalance={this.decreaseBalance}
        handleInputChange={this.handleInputChange}
      />
    )
  }
}

export default BalanceComponent;