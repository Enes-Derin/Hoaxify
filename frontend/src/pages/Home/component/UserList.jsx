import { useCallback, useEffect, useState } from "react";
import { loadUsers } from "./api";
import { Spinner } from "../../../shared/components/Spinner";
import { UserListItem } from "./UserListItem";

export function UserList() {
  const [apiProgress, setApiProgress] = useState(false);
  const [userPage, setUserPage] = useState({
    content: [],
    last: false,
    first: true,
    number: 0,
  });

  const getUsers = useCallback(async (page) => {
    setApiProgress(true);
    try {
      const response = await loadUsers(page);
      setUserPage(response.data);
    } finally {
      setApiProgress(false);
    }
  }, []);

  useEffect(() => {
    getUsers();
  },[]);

  return (
    <div className="card">
      <div className="card-header text-center fs-4">User List</div>
      <ul className="list-group list-group-flush">
        {userPage.content.map((user) => {
          return (
            <>
              <UserListItem key={user.id} user={user} />
            </>
          );
        })}
      </ul>
      <div className="card-footer text-center">
        {apiProgress && <Spinner />}
        {!apiProgress && !userPage.first && (
          <button
            className="btn btn-outline-secondary btn-sm float-start"
            onClick={() => getUsers(userPage.number - 1)}
          >
            previous
          </button>
        )}
        {!apiProgress && !userPage.last && (
          <button
            className="btn btn-outline-secondary btn-sm float-end"
            onClick={() => getUsers(userPage.number + 1)}
          >
            next
          </button>
        )}
      </div>
    </div>
  );
}
