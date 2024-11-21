import axios from "axios";
import { useState } from "react";
import { signup } from "./api";

export function SignUp() {
  const [username, setUsername] = useState();
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();
  const [passwordRepeat, setPasswordRepeat] = useState();
  //   apiProgress ard arda data göndermesini engellemek için butona eklenen kod bloğudur
  const [apiProgress, setApiProgress] = useState(false);
  //   kullanıcı oluşturuldu mesajı dönmek için
  const [successMessage, setSuccessMessage] = useState();
  
  const onSubmit = (event) => {
    event.preventDefault();
    setApiProgress(true);
    setSuccessMessage();
    signup(username, email, password)
      .then((response) => {
        setSuccessMessage(response.data.message);
      })
      .finally(() => setApiProgress(false));
  };

  return (
    <div className="container mt-5">
      <div className="col-lg-6 offset-lg-3">
        <form className="card">
          <div className="text-center card-header">
            <h1> Sign Up </h1>
          </div>
          <div className="card-body">
            <div className="mb-3">
              <label htmlFor="username" className="form-label">
                Username
              </label>
              <input
                id="username"
                className="form-control"
                onChange={(event) => setUsername(event.target.value)}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="email" className="form-label">
                E-mail
              </label>
              <input
                id="email"
                className="form-control"
                onChange={(event) => setEmail(event.target.value)}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="password" className="form-label">
                Password
              </label>
              <input
                id="password"
                type="password"
                className="form-control"
                onChange={(event) => setPassword(event.target.value)}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="paswordRepeat" className="form-label">
                Password Repeat
              </label>
              <input
                id="paswordRepeat"
                type="password"
                className="form-control"
                onChange={(event) => setPasswordRepeat(event.target.value)}
              />
            </div>
            <div className="text-center">
              {successMessage && (
                <div className="alert alert-success">{successMessage}</div>
              )}
              <button
                className="btn btn-primary"
                disabled={
                  apiProgress || !password || password !== passwordRepeat
                }
                onClick={onSubmit}
              >
                {apiProgress && (
                  <span
                    className="spinner-border spinner-border-sm"
                    aria-hidden="true"
                  ></span>
                )}
                Submit
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
}
