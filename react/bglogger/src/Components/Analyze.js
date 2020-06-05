import React from 'react';
import DropZone from './DropZone'
import { BrowserRouter as Router, NavLink, Switch, Route } from 'react-router-dom';




class Analyze extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
    }

  }

  render() {

    return (
      <div>
        <DropZone />

      </div>
    );
  }
}

export default Analyze;
