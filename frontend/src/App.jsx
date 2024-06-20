import { useState } from 'react'
import './App.css'

function App() {
  const [count, setCount] = useState(0);

  return (
    <>
      <div >Count {count}</div><br />
      <button onClick={()=>setCount((count)=>count+1)}>Arttır</button>   
      <button onClick={()=>setCount((0))}>Sıfırla</button>   
    </>
  )
}

export default App
