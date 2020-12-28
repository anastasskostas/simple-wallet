import React from 'react';
import { Card, Table } from 'react-bootstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { ConvertMillisecondsToDate } from '../../utils/DateFormat';

function TransactionsHtml(props) {
  const transactionsHeaders = ['Date', 'Description', 'Amount', 'Currency'];
  const displayTransactionsHeaders = transactionsHeaders.map(header => <th className="text-nowrap" key={header}>{header}</th>);

  const renderTransactionsTableData = () => {
    return (props.data.isLoadingTableData ?
      <tr>
        <td className="text-center" colSpan={transactionsHeaders.length}><FontAwesomeIcon icon={['fas', 'spinner']} className="fa-2x fa-spin" /></td>
      </tr>
      : (props.data.transactions.length > 0 ?
        props.data.transactions.map((info, index) => {
          const { date, description, amount, currency } = info; //destructuring
          return (
            <tr key={index}>
              <td>{ConvertMillisecondsToDate(date)}</td>
              <td>{description}</td>
              <td>{amount}</td>
              <td>{currency}</td>
            </tr>
          )
        })
        :
        <tr>
          <td className="text-center" colSpan={transactionsHeaders.length}>No data</td>
        </tr>
      )
    )
  }

  return (
    <Card>
      <Card.Header>
        <FontAwesomeIcon icon={['fas', 'align-justify']} className="mr-2" /><strong>Transactions</strong>
      </Card.Header>
      <Card.Body>
        <Table striped bordered responsive>
          <thead>
            <tr>
              {displayTransactionsHeaders}
            </tr>
          </thead>
          <tbody>
            {renderTransactionsTableData()}
          </tbody>
        </Table>
      </Card.Body>
    </Card>
  );
}

export default TransactionsHtml;