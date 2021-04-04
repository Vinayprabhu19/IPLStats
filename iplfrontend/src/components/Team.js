import AppBar from '@material-ui/core/AppBar';
import Button from '@material-ui/core/Button';
import Hidden from '@material-ui/core/Hidden';
import Backdrop from '@material-ui/core/Backdrop';
import CircularProgress from '@material-ui/core/CircularProgress';
import Grid from '@material-ui/core/Grid';
import Accordion from '@material-ui/core/Accordion';
import AccordionSummary from '@material-ui/core/AccordionSummary';
import AccordionDetails from '@material-ui/core/AccordionDetails';
import Typography from '@material-ui/core/Typography';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import React, { Suspense, lazy, Component } from 'react';
import '../css/Home.css';
import GridList from '@material-ui/core/GridList';
const Card = lazy(() => import('./Card'));
class Player extends Component {
  constructor(props) {
    super(props);
    var team=this.props.match.params.team;

    this.state = {
      name:team,
      infoRecieved:false
    };
    
  }
 
  componentDidMount() {
  
    fetch('http://localhost:8081/getTeamInfo/'+this.state.name)
      .then(response => response.json())
      .then(result => {
        this.setState({
        teamStats:result,
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
        <Card mainInfo={{key:"Total Matches",value:this.state.teamStats.totalMatches}} backsideInfo={[]} color="#e71d31" title="Profile" turnBack={false}/>
            <Card mainInfo={{key:this.state.teamStats.winMatchesTotal +" wins",value:this.state.teamStats.winPercentage+"%"}} backsideInfo={[]} turnBack={false} color="#e71d31" title="Win"/>
         
        </GridList>
        
        <Accordion>
            <AccordionSummary
                expandIcon={<ExpandMoreIcon />}
                aria-controls="panel1a-content"
                id="panel1a-header"
            >
                <h5 className="centerText">Players Info</h5>
            </AccordionSummary>
            <AccordionDetails>
                <Grid container justify = "center" style={{margin:"20px"}}>
            <table border={2} cellPadding={5}>
                <thead>
                    <tr>
                    <th>Player</th>
                    <th>Runs</th>
                    <th>Batting SR</th>
                    <th>Wickets</th>
                    <th>Bowling Avg</th>
                    <th>Bowling SR</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        this.state.teamStats.playerInfo.map(element =>(
                            <tr key={element.name}>
                            <td><a href={"/player/"+element.name}>{element.name}</a></td>
                            <td>{element.runs}</td>
                            <td>{element.strike_rate}</td>
                            <td>{element.wickets}</td>
                            <td>{element.averageBowling}</td>
                            <td>{element.strikerateBowling}</td>
                            </tr>
                        ))
                    }
                </tbody>
            </table>
            </Grid>
                    </AccordionDetails>
            </Accordion>
      <Accordion>
        <AccordionSummary
          expandIcon={<ExpandMoreIcon />}
          aria-controls="panel1a-content"
          id="panel1a-header"
        >
          <h5 className="centerText">{this.state.name} vs Teams</h5>
        </AccordionSummary>
        <AccordionDetails>
           <Grid container justify = "center" style={{margin:"20px"}}>
      <table border={2} cellPadding={5}>
           <thead>
              <tr>
                <th>Team</th>
                <th>Total Matches</th>
                <th>Total Wins</th>
                <th>Total Loses</th>
                <th>Win %</th>
              </tr>
           </thead>
           <tbody>
              {
                  this.state.teamStats.teamsTable.map(element =>(
                     <tr key={element.TeamName}>
                       <td><a href={"/team/"+element.TeamName}>{element.TeamName}</a></td>
                       <td>{element.TotalMatches}</td>
                       <td>{element.Win}</td>
                       <td>{element.TotalMatches-element.Win}</td>
                       <td>{element.WinPerc}</td>
                     </tr>
                  ))
              }
           </tbody>
        </table>
        </Grid>
              </AccordionDetails>
      </Accordion>
       
       <Accordion>
        <AccordionSummary
          expandIcon={<ExpandMoreIcon />}
          aria-controls="panel1a-content"
          id="panel1a-header"
        >
          <h5 className="centerText">{this.state.name} in Grounds</h5>
        </AccordionSummary>
        <AccordionDetails>
        <Grid container justify = "center" style={{margin:"20px"}}>
      <table border={2} cellPadding={5}>
           <thead>
              <tr>
                <th>Venue</th>
                <th>Total Matches</th>
                <th>Total Wins</th>
                <th>Total Loses</th>
                <th>Win %</th>
              </tr>
           </thead>
           <tbody>
              {
                  this.state.teamStats.venuesTable.map(element =>(
                     <tr key={element.venue}>
                       <td>{element.venue}</td>
                       <td>{element.TotalMatches}</td>
                       <td>{element.Win}</td>
                       <td>{element.TotalMatches-element.Win}</td>
                       <td>{element.WinPerc}</td>
                     </tr>
                  ))
              }
           </tbody>
        </table>
        </Grid>
         </AccordionDetails>
      </Accordion>
      <Accordion>
        <AccordionSummary
          expandIcon={<ExpandMoreIcon />}
          aria-controls="panel1a-content"
          id="panel1a-header"
        >
         <h5 className="centerText">Toss Stats</h5>
        </AccordionSummary>
        <AccordionDetails>
           <GridList cols={4} className="cardGridList">
            <Card mainInfo={{key:"% matches won after winning toss",value:this.state.teamStats.tossWinMatchWinPercentage+"%"}} backsideInfo={[]} turnBack={false} color="#e71d31" title="Toss Win Match Win"/>
              <Card mainInfo={{key:"% matches lost after winning toss",value:this.state.teamStats.tossWinMatchLosePercentage+"%"}} backsideInfo={[]} turnBack={false} color="#e71d31" title="Toss Win Match Lost"/>
        
<Card mainInfo={{key:"% matches lost after losing toss",value:this.state.teamStats.tossLoseMatchLosePercentage+"%"}} backsideInfo={[]} turnBack={false} color="#e71d31" title="Toss Lose Match Lost"/>

            <Card mainInfo={{key:"% matches won after losing toss",value:this.state.teamStats.tossLoseMatchWinPercentage+"%"}} backsideInfo={[]} turnBack={false} color="#e71d31" title="Toss Lose Match Win"/>
        </GridList>
              </AccordionDetails>
      </Accordion>

        
    </div>;
  }

}


export default Player;