import { useEffect, useState } from "react";
import { Input } from "../../shared/components/Input";
import { Alert } from "../../shared/components/Alert";
import { useTranslation } from "react-i18next";
import { Button } from "../../shared/components/Button";
import { login } from "./api";

export function Login({onLoginSuccess}) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [apiProgress, setApiProgress] = useState(false);
  const [errors, setErrors] = useState({});
  const [generalError, setGeneralError] = useState(null);
  const { t } = useTranslation();

  useEffect(() => {
    setErrors((lastError) => ({
      ...lastError,
      email: undefined,
    }));
  }, [email]);

  useEffect(() => {
    setErrors((lastError) => ({
      ...lastError,
      password: undefined,
    }));
  }, [password]);

  const onSubmit = async (event) => {
    event.preventDefault();
    setApiProgress(true);
    setGeneralError(null);
    try {
      const response = await login({email,password});
      onLoginSuccess(response.data.user)
    } catch (axiosError) {
      if (axiosError.response?.data) {
        if (axiosError.response.data.status === 400) {
          setErrors(axiosError.response.data.validationErrors);
        } else {
          setGeneralError(axiosError.response.data.message);
        }
      } else {
        setGeneralError(t("generalError"));
      }
    } finally {
      setApiProgress(false);
    }
  };

  return (
    <div className="container mt-5">
      <div className="col-lg-6 offset-lg-3">
        <form className="card" onSubmit={onSubmit}>
          <div className="text-center card-header">
            <h1>{t("login")}</h1>
          </div>
          <div className="card-body">
            <Input
              id="email"
              label={t("email")}
              error={errors.email}
              onChange={(event) => setEmail(event.target.value)}
            />
            <Input
              id="password"
              label={t("password")}
              type="password"
              error={errors.password}
              onChange={(event) => setPassword(event.target.value)}
            />
            <div className="text-center">
              {generalError && <Alert styleType="danger">{generalError}</Alert>}
              <Button
                disabled={!password || !email}
                apiProgress={apiProgress}
              >
                {t("login")}
              </Button>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
}
