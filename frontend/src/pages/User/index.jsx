import { getUser } from "./api";
import { Alert } from "../../shared/components/Alert";
import { Spinner } from "../../shared/components/Spinner";
import { useRouteParamApirequest } from "../../shared/hooks/useRouteParamApiRequest";
import { ProfileCard } from "./components/ProfileCard";

export function User() {
  const {
    apiProgess,
    data: user,
    error,
  } = useRouteParamApirequest("id", getUser);


  return (
    <>
      {apiProgess && (
        <Alert styleType={"secondary"} center>
          <Spinner />
        </Alert>
      )}
      {user && <ProfileCard user={user} />}
      {error && <Alert styleType={"danger"}>{error}</Alert>}
    </>
  );
}
