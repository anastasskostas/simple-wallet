import React from 'react';
import { Button, Card, Col, FormControl, InputGroup, Row } from 'react-bootstrap'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'

function BalanceHtml(props) {

  return (
    <Card>
      <Card.Header>
        <FontAwesomeIcon icon={['fas', 'align-justify']} className="mr-2" /><strong>Balance</strong>
      </Card.Header>
      <Card.Body>
        <div className="mb-3">
          <h4>Current balance: {props.data.currency} {props.data.currentBalance}</h4>
        </div>

        <Row>
          <Col md="6" xs="12">
            <InputGroup className="mb-3">
              <InputGroup.Prepend>
                <InputGroup.Text id="basic-addon1">{props.data.currency}</InputGroup.Text>
              </InputGroup.Prepend>
              <FormControl
                type="number"
                placeholder="Enter positive amount"
                aria-label="amount"
                aria-describedby="basic-addon1"
                value={props.data.amount}
                onChange={(event) => { props.handleInputChange(event, 'amount') }}
                isInvalid={props.data.insufficientBalance}
              />
            </InputGroup>
            {props.data.insufficientBalance && <p className="text-danger">Sorry. Your account does not have enough funds.</p>}
          </Col>
          <Col md="6" xs="12">
            <FormControl
              type="text"
              placeholder="Enter description"
              aria-label="description"
              aria-describedby="basic-addon2"
              value={props.data.description}
              onChange={(event) => { props.handleInputChange(event, 'description') }}
            />
          </Col>
          <Col>
            <Button className="mt-2" disabled={props.data.doesDecreaseBalance || props.data.amount <= 0 || props.data.description === ''} variant="primary" onClick={props.decreaseBalance}>Spend {props.data.doesDecreaseBalance ? <FontAwesomeIcon icon={['fas', 'spinner']} className="fa-pulse" /> : ''}</Button>
          </Col>
        </Row>

      </Card.Body>
    </Card>
  );
}
export default BalanceHtml;