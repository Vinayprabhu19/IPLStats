import React, { Component } from 'react';
import Paper from '@material-ui/core/Paper';
import LazyLoad from 'react-lazy-load';
import ReactCardFlip from 'react-card-flip';
import '../css/Card.css';
class CardLayout extends Component {
  constructor(props){
    super(props);
    this.state={
        title:props.title,
        mainInfo:props.mainInfo,
        backsideInfo:props.backsideInfo,
        isFlipped: false,
        color:props.color,
        turnBack:props.turnBack
    };
    
  }
  handleClick(e) {
    e.preventDefault();
    this.setState(prevState => ({ isFlipped: !prevState.isFlipped }));
  }

  render() {

    return (
    <Paper elevation={8} className="card" onClick={()=>this.setState({isFlipped:!this.state.isFlipped & this.state.turnBack})}>
      <ReactCardFlip isFlipped={this.state.isFlipped} flipDirection="horizontal">
        <div>
          <h3 className="cardTitle" style={{color:this.state.color}}>{this.state.title}</h3>
             <h5 className={this.state.mainInfo.value.length>7?"frontValueBig":"frontValue"}>{this.state.mainInfo.value}</h5>
            <h4 className="frontKey">{this.state.mainInfo.key}</h4>
        </div>
        <div>
         <div className="backsideContent">

         {
            this.props.backsideInfo.map(info => (
               <div key={info.key}>
                <h6 className="keyValue"><span class="key">{info.key}</span> - {info.value} </h6>
            </div>
            ))
          }
          </div>
        </div>
      </ReactCardFlip>
       </Paper>
    )
  }

}

export default CardLayout;