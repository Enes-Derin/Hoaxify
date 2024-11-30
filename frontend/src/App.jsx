import { Outlet } from "react-router-dom";
import { LanguageSelector } from "./shared/components/LanguageSelector";
import { Navbar } from "./shared/components/Navbar";
import { Login } from "./pages/login";
import { useState } from "react";

function App() {
  const [ authState, setAuthState] = useState({
    id: 0
  })

  const onLoginSuccess = (data) =>{
    setAuthState(data)
  }
  return (
    <>
      <div className="container mt-3">
        <Navbar authState={authState}/>
        <Login onLoginSuccess={onLoginSuccess}/>
        {/* <Outlet /> */}
        <LanguageSelector />
      </div>
    </>
  );
}

export default App;
