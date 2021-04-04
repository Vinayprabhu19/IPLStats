import AppBar from '@material-ui/core/AppBar';
import Button from '@material-ui/core/Button';
import Hidden from '@material-ui/core/Hidden';
import Backdrop from '@material-ui/core/Backdrop';
import CircularProgress from '@material-ui/core/CircularProgress';
import Grid from '@material-ui/core/Grid';
import React, { Suspense, lazy, Component } from 'react';
import '../css/Home.css';
import GridList from '@material-ui/core/GridList';
const Card = lazy(() => import('./Card'));
class Player extends Component {
  constructor(props) {
    super(props);
    var player=this.props.match.params.player;

    this.state = {
      name:player,
      infoRecieved:false
    };
    
  }
 
  componentDidMount() {
  
    fetch('http://localhost:8081/getPlayerInfo/'+this.state.name)
      .then(response => response.json())
      .then(result => {
        this.setState({
        playerStats:result,
        infoRecieved:true
        });
      })
      .catch(error => {
        console.error(error);
      })

  }
  

  render() {
  if (!this.state.infoRecieved) {
      return <Backdrop open={!this.state.infoRecieved}>
         <CircularProgress color="inherit"/>
      </Backdrop>
    }
    return <div>
        <h1 className="itemTitle">{this.state.name}</h1>
        <GridList cols={5} className="cardGridList">
            <Card mainInfo={{key:"Runs",value:this.state.playerStats.batting.runs}} backsideInfo={this.state.playerStats.batting.runType} color="#e71d31" turnBack={true} title="Career Runs"/>
            <Card mainInfo={{key:"Strike Rate",value:this.state.playerStats.batting.strike_rate}} backsideInfo={this.state.playerStats.batting.stats}  turnBack={true} color="#e71d31"  title="Scoring"/>
            <Card mainInfo={this.state.playerStats.batting.favoriteStadium} backsideInfo={this.state.playerStats.batting.stadiumRuns} color="#e71d31"  turnBack={true} title="Favourite Ground"/>
            <Card mainInfo={this.state.playerStats.batting.strongestOpponent} backsideInfo={this.state.playerStats.batting.opponentInfo} color="#e71d31"  turnBack={true} title="Strongest Opponent"/>
            <Card mainInfo={this.state.playerStats.batting.frequentDismissalType} backsideInfo={this.state.playerStats.batting.dismissalInfo} color="#e71d31" turnBack={true} title="Dismissal Type"/>
            <Card mainInfo={{key:"Wickets",value:this.state.playerStats.bowling.wickets}} backsideInfo={this.state.playerStats.bowling.wicketsInfo} color="#1A9A33" turnBack={true} title="Career Wickets"/>
            <Card mainInfo={{key:"Average",value:this.state.playerStats.bowling.average}} backsideInfo={this.state.playerStats.bowling.stats} color="#1A9A33" title="Abilities" turnBack={true}/>
            <Card mainInfo={this.state.playerStats.bowling.favoriteStadium} backsideInfo={this.state.playerStats.bowling.stadiumWickets} color="#1A9A33" title="Favorite Stadium" turnBack={true}/>
            <Card mainInfo={this.state.playerStats.bowling.topFielding} backsideInfo={this.state.playerStats.bowling.fieldingInfo} color="#0E81E7" title="Fielding" turnBack={true}/>
        </GridList>
    </div>;
  }

}


export default Player;