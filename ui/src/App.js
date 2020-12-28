import React, { Component } from 'react';
import { HashRouter, Route, Switch } from 'react-router-dom';
import './App.scss';

const loading = () => <div className="animated fadeIn pt-3 text-center">Loading...</div>;
const Login = React.lazy(() => import('./components/Login/Login'))
const Wallet = React.lazy(() => import('./components/Wallet/Wallet'))


class App extends Component {

  constructor() {
    super()
    this.state = {}
  }

  render() {
    return (
      <HashRouter>
        <React.Suspense fallback={loading()}>
          <Switch>
            <Route exact path="/login" name="Login" render={props => <Login {...props} />} />
            <Route exact path="/wallet" name="Wallet" render={props => <Wallet {...props} />} />
          </Switch>
        </React.Suspense>
        <div id="toaster-notifications"></div>
      </HashRouter>
    );
  }
}

export default App;
