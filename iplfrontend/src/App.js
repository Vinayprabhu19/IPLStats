import React, { Suspense, lazy } from 'react';
import {Redirect, Switch, BrowserRouter as Router, Route } from 'react-router-dom';

import CircularProgress from '@material-ui/core/CircularProgress';
function App() {
  const reload = () => window.location.reload();
  const Home = lazy(() => import('./components/Home'));
  const Player = lazy(() => import('./components/Player'));
  return (
    <Router>
      <Suspense fallback={ <CircularProgress color="inherit"/>}>
      <Switch>
      <Route path="/" exact component={Home} />
      <Route path="/Player/:player" exact component={Player} />
      <Redirect to="/404" />
    </Switch>
    </Suspense>
    </Router>
  );
}

export default App;
