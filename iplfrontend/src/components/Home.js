import AppBar from '@material-ui/core/AppBar';
import Button from '@material-ui/core/Button';
import Hidden from '@material-ui/core/Hidden';
import React, { Suspense, lazy, Component } from 'react';
import '../css/Home.css';
import Suggestions from './Suggestions'
class Home extends Component {
  constructor(props) {
    super(props);
    // Don't call this.setState() here!

    this.state = {
      results:[],
      query:""
    };
    
  }
  
  componentDidMount() {
  
    fetch('http://localhost:8081/getItems')
      .then(response => response.json())
      .then(result => {
        this.setState({
            results:result
        });
      })
      .catch(error => {
        console.error(error);
      })
  }

 handleInputChange = () => {
    this.setState({
      query: this.search.value
    });
  }
  

  render() {
    return <div className="center-screen">
        <h1 className="title"><span style={{color:"blue"}}>IPL</span> STATS</h1>
        <input 
        className="searchBar"
          placeholder="Search for..."
          ref={input => this.search = input}
          onChange={this.handleInputChange}
        />
        <Suggestions results={this.state.results} query={this.state.query}/>
    </div>;
  }

}


export default Home;