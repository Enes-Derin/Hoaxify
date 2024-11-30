import { Link } from "react-router-dom";
import image from "../../../assets/profile.png";
export function UserListItem({ user }) {
  return (
    <>
      <Link
        className="list-group-item list-group-item-action"
        to={`/user/${user.id}`}
        style={{ textDecoration: "none" }}
      >
        <img
          className="me-2 img-fluid rounded-circle shadow-sm"
          src={image}
          width={30}
          alt="image"
        />
        {user.username}
      </Link>
    </>
  );
}
