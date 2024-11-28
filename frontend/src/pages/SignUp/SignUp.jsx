import { useEffect, useMemo, useState } from "react";
import { signup } from "./components/api";
import { Input } from "./components/Input";
import { useTranslation } from "react-i18next";
import { Alert } from "../../shared/components/Alert";
import { Spinner } from "../../shared/components/Spinner";

export function SignUp() {
  const [username, setUsername] = useState();
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();
  const [passwordRepeat, setPasswordRepeat] = useState();
  //   apiProgress ard arda data göndermesini engellemek için butona eklenen kod bloğudur
  const [apiProgress, setApiProgress] = useState(false);
  //   kullanıcı oluşturuldu mesajı dönmek için
  const [successMessage, setSuccessMessage] = useState();
  // hata mesajları için
  const [errors, setErrors] = useState({});
  // genel hatalar için
  const [generalError, setGeneralError] = useState();
  //   dil desteği olan i18n kütüphanesinden gelen fonksiyon. bunu kullanarak metinlerdin dillerini çevirebiliyoruz
  const { t } = useTranslation();

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

  const onSubmit = async (event) => {
    event.preventDefault();
    setApiProgress(true);
    setSuccessMessage();
    setGeneralError();
    try {
      const response = await signup({
        username,
        email,
        password,
      });
      setSuccessMessage(response.data.message);
    } catch (axiosError) {
      if (axiosError.response?.data) {
        if (axiosError.response.data.status === 400) {
          setErrors(axiosError.response.data.validationErrors);
        }else {
            setGeneralError(axiosError.response.data.message)
        }
      } else {
        setGeneralError(t("generalError"));
      }
    } finally {
      setApiProgress(false);
    }
  };

  const passwordRepeatError = useMemo(() => {
    if (passwordRepeat && password !== passwordRepeat) {
      return t("passwordMismatch");
    }
    return "";
  }, [password, passwordRepeat]);

  return (
    <div className="container mt-5">
      <div className="col-lg-6 offset-lg-3">
        <form className="card">
          <div className="text-center card-header">
            <h1> {t("signUp")} </h1>
          </div>
          <div className="card-body">
            <Input
              id="username"
              label={t("username")}
              error={errors.username}
              onChange={(event) => setUsername(event.target.value)}
            />
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
            <Input
              id="passwordRepeat"
              label={t("passwordRepeat")}
              type="password"
              error={passwordRepeatError}
              onChange={(event) => setPasswordRepeat(event.target.value)}
            />

            <div className="text-center">
              {successMessage && (
                <Alert>{successMessage}</Alert>
              )}
              {generalError && (
                <Alert styleType={"danger"}>{generalError}</Alert>
              )}
              <button
                className="btn btn-primary"
                disabled={
                  apiProgress || !password || password !== passwordRepeat
                }
                onClick={onSubmit}
              >
                {apiProgress && (
                  <Spinner sm />
                )}
                {t("signUp")}
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
}
