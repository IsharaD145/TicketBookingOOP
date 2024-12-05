import React, { useState,useEffect,useRef } from 'react';
import Simlog from './Simlog';

function TicketConfigurationForm() {
  const [formData, setFormData] = useState({totalTicketsByVendor: '',ticketReleaseRate: '',totalTicketsByConsumer: '',customerRetreivalRate: '',maxTicketCapacity: ''});
const[errormessage, setErrormessage]=useState('');
const[errormessagee, setErrormessagee]=useState('');
const[formvalid,setfomrvalid]=useState(true)
const [simulationStarted, setSimulationStarted] = useState(false);

  const changedata = (e) => {
    if(e.target.name==="totalTicketsByVendor"){
        if(parseInt(e.target.value) > parseInt(formData.maxTicketCapacity)){
            setErrormessage("maximum tickets that could be added by the vendor should be less than the maximum capacity.")
            setfomrvalid(false)
        }else{
            setErrormessage("");
            setfomrvalid(true)
        }
    }
    if(e.target.name==="totalTicketsByConsumer"){
        if(parseInt(e.target.value) > parseInt(formData.maxTicketCapacity)){
            setErrormessagee("maximum tickets that could be bought by the consumer should be less than the maximum capacity.")
            setfomrvalid(false)
        }else{
            setErrormessagee("");
            setfomrvalid(true)
        }
    }
    setFormData({...formData,[e.target.name]:e.target.value})
}

  const handleKeyDown = (e) => { if (e.key === 'e' || e.key === 'E' || e.key === '-' || e.key === '+' || e.key === '.') {
    e.preventDefault();
  }}

  const submitData = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch('http://localhost:8000/start', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData),
      });
      if (!response.ok) {
        throw new Error('Something went wrong');
      }
  
      setSimulationStarted(true);   
    } catch (error) {
        setSimulationStarted(false);  
    }
  };

  const [logs, setLogs] = useState([]);
   // Set up WebSocket connection
   useEffect(() => {
    const socket = new WebSocket('http://localhost:8000/ticketLogs');

    socket.onmessage = (event) => {
      setLogs((prevLogs) => [...prevLogs, event.data]);  
    };

    return () => socket.close();  
  }, []);
  

  

  return (
    <div>
<div className="form-container">
      <h2>Ticket Configuration Information</h2>
      <form onSubmit={submitData}>

      <label>
          Maximum Ticket Capacity:
          <input type="number" name="maxTicketCapacity" value={formData.maxTicketCapacity} onChange={changedata} onKeyDown={handleKeyDown} required/>
        </label>

        <label>
          Maximum tickets added by vendor:
          <input type="number" name="totalTicketsByVendor" value={formData.totalTicketsByVendor} onChange={changedata} onKeyDown={handleKeyDown} required/>
          {errormessage && <p style={{ color: 'red' }}>{errormessage}</p>}
        </label>
       
        <label>
          Ticket Release Rate:
          <input type="number" name="ticketReleaseRate" value={formData.ticketReleaseRate} onChange={changedata} onKeyDown={handleKeyDown} required/>
        </label>

        <label>
          Maximum tickets bought by consumer:
          <input type="number" name="totalTicketsByConsumer" value={formData.totalTicketsByConsumer} onChange={changedata} onKeyDown={handleKeyDown} required/>
          {errormessagee && <p style={{ color: 'red' }}>{errormessagee}</p>}
        </label>

        <label>
          Ticket Retrieval Rate:
          <input type="number" name="customerRetreivalRate" value={formData.customerRetreivalRate} onChange={changedata} onKeyDown={handleKeyDown} required/>
        </label>


        <button type="submit" disabled={!formvalid} style={{ backgroundColor: formvalid ? 'rgb(13, 108, 185)' : 'grey', cursor: formvalid ? 'pointer' : 'not-allowed' }}>start Simmulation</button>

        <button type="button" id="stop-button" onClick={"#"}>Stop Simulation</button>

      </form>

      
    </div>

    <div className='simlog-container'>
        {simulationStarted ? (<h2 style={{ color: 'green' }}> Configuraition Loaded sucessfully </h2>):''}
    </div>
    <div className='log-show'>
        {logs.map((log, index) => (
          <p key={index}>{log}</p>  
        ))}
    </div>
    </div>
    
  );
}

export default TicketConfigurationForm;