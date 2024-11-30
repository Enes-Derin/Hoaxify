import { activateUser } from "./api";
import { Alert } from "../../shared/components/Alert";
import { Spinner } from "../../shared/components/Spinner";
import { useRouteParamApirequest } from "../../shared/hooks/useRouteParamApiRequest";

export function ActivationPage() {
  const { apiProgess, data, error } = useRouteParamApirequest(
    "token",
    activateUser
  );

  return (
    <>
      {apiProgess && (
        <Alert styleType={"secondary"} center>
          <Spinner />
        </Alert>
      )}
      {data?.message && <Alert>{data.message}</Alert>}
      {error && <Alert styleType={"danger"}>{error}</Alert>}
    </>
  );
}
