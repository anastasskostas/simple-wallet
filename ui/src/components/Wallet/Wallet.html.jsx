import React, { useState } from 'react';
import { Button, Tab, Tabs, } from 'react-bootstrap'
import TransactionsComponent from '../Transactions/Transactions';
import BalanceComponent from '../Balance/Balance';

function WalletHtml(props) {
  const [key, setKey] = useState('transactions');

  return (
    <>
      <div className="text-right mt-2 mr-2">
        <Button variant="danger" onClick={props.logout}>Logout</Button>
      </div>
      <Tabs
        id="controlled-tab-example"
        activeKey={key}
        onSelect={(k) => setKey(k)}
        mountOnEnter={true}
        unmountOnExit={true}
      >
        <Tab eventKey="transactions" title="Transactions">
          <TransactionsComponent />
        </Tab>
        <Tab eventKey="balance" title="Balance">
          <BalanceComponent />
        </Tab>
      </Tabs>
    </>
  );
}
export default WalletHtml;