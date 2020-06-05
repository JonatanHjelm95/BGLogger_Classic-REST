import React from 'react';
import { BrowserRouter as Router, NavLink, Switch, Route } from 'react-router-dom';
import Analyze from './Components/Analyze';

class Navigation extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
    }

  }

  render() {

    return (
      <div> 
        <Router>
          <div className="Header">
            <div className="header-buttons">
              <NavLink className="link" to="/" exact>
                <span>Analyze</span>
              </NavLink>
            </div>
          </div>
          <Switch>
            <Route path="/" exact component={Analyze} />
          </Switch>
        </Router>

      </div>
    );
  }
}

export default Navigation;
