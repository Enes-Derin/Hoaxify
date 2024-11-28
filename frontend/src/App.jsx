import { Outlet } from "react-router-dom";
import { LanguageSelector } from "./shared/components/LanguageSelector";
import { Navbar } from "./shared/components/Navbar";

function App() {
  return (
    <>
      <div className="container mt-3">
        <Navbar />
        <Outlet />
        <LanguageSelector />
      </div>
    </>
  );
}

export default App;
