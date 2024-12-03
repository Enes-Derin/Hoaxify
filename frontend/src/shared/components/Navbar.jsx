import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";
import logo from "../../assets/hoaxify.png";

export function Navbar({ authState }) {
  const { t } = useTranslation();

  return (
    <>
      <nav className="container navbar navbar-expand bg-body-tertiary shadow-sm">
        <div className="container-fluid">
          <Link className="navbar-brand" to={"/"}>
            <img src={logo} width={60} /> Hoaxify
          </Link>
          <ul className="navbar-nav">
            {authState.id === 0 && (
              <>
                <li className="nav-item">
                  <Link className="nav-link" to={"/login"}>
                    {t("login")}
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to={"/signup"}>
                    {t("signUp")}
                  </Link>
                </li>
              </>
            )}
            {authState.id > 0 && (
              <>
                <li className="nav-item">
                  <Link className="nav-link" to={`/myprofile/${authState.id}`}>
                    My Profile
                  </Link>
                </li>
                <li className="nav-item">
                  <span className="nav-link" role="button">Logout</span>
                </li>
              </>
            )}
          </ul>
        </div>
      </nav>
    </>
  );
}
