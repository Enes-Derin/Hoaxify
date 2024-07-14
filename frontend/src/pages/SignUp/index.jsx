import { useEffect, useMemo, useState } from "react";
import { signUp } from "./api";
import { Input } from "./Components/Input";

export function SignUp() {
  const [username, setUsername] = useState();
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();
  const [repeatPassword, setRepeatPassword] = useState();
  const [apiProgress, setApiProgress] = useState(false);
  const [successMessage, setSuccessMessage] = useState();
  const [errors, setErrors] = useState({});
  const [generalError, setGeneralError] = useState();

  useEffect(() => {
    setErrors(function (lastError) {
      return {
        ...lastError,
        username: undefined, 
      };
    }); 
  }, [username]);

  useEffect(() => {
    setErrors(function (lastError) {
      return {
        ...lastError,
        email: undefined,
      };
    });
  }, [email]);

  useEffect(()=>{
    setErrors(function(lastError){
      return{
        ...lastError,
        password:undefined
      }
    })
  },[password])

  const onSubmit = async (event) => {
    event.preventDefault();
    setApiProgress(true);
    setSuccessMessage();
    setGeneralError();
    try {
      const response = await signUp({
        username,
        email,
        password,
      });
      setSuccessMessage(response.data.message);
    } catch (axiosError) {
      if (
        axiosError.response.data &&
        axiosError.response.data.status == 400 ||
        axiosError.response.data.status == 500
      ) {
        console.log(axiosError);
        setErrors(axiosError.response.data.validationErrors);
      } else {
        setGeneralError("Unexpected error occured. Please try again");
      }
    } finally {
      setApiProgress(false);
    }
  };

  const passwordRepeatError = useMemo(()=>{
    if(password && password != repeatPassword){
    return "Password Dissmatch";
    }
    return "";
  },[password,repeatPassword]);
  //deneme

  return (
    <div className="container mt-4">
      <div className="col-lg-6 offset-lg-3">
        <form className="card" onSubmit={onSubmit}>
          <h1 className="text-success text-center card-header">Sign Up</h1>
          <div className="p-4">
            <Input
              id="username"
              label="Username"
              error={errors.username}
              onChange={(event) => setUsername(event.target.value)}
            />

            <Input
              id="email"
              label="E-mail"
              error={errors.email}
              onChange={(event) => setEmail(event.target.value)}
            />
            <Input
              id="password"
              label="Password"
              error={errors.password}
              onChange={(event) => setPassword(event.target.value)}
              type="password"
            />
            <Input 
              id="repeatPassword"
              label="Repeat Pessword"
              error={passwordRepeatError}
              onChange={(event) => setRepeatPassword(event.target.value)}
              type="password"
            />
            {successMessage && (
              <div className="alert alert-success">{successMessage}</div>
            )}
            {generalError && (
              <div className="alert alert-danger">{generalError}</div>
            )}
            <div className="text-center">
              <button
                className="btn-primary btn"
                disabled={
                  apiProgress || !password || password != repeatPassword
                }
              >
                {apiProgress && (
                  <span
                    className="spinner-border spinner-border-sm"
                    aria-hidden="true"
                  ></span>
                )}
                Sign Up
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
}
