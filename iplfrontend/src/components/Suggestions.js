import React from 'react'

const onItemPress=(r)=>{

}
const Suggestions = (props) => {
  var query= props.query;
  if(query.length<2)
    return <ul></ul>;
    var results= props.results.filter(function(f){
       return f.name.toLowerCase().includes(query.toLowerCase());
    });
  const options = results.map(r => (
    <li key={r.name} onClick={()=>onItemPress(r)}>
    <a href={r.type+"/"+r.name}>
      <div>
        <h4>{r.name}</h4>
        <h6>{r.type}</h6>
      </div>
    </a>

    </li>
  ))
  return <ul>{options}</ul>
}

export default Suggestions