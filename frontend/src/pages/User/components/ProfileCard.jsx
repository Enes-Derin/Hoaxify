import defaultProfileImage from "../../../assets/profile.png";
export function ProfileCard({ user }) {
  return <>
        <div className="card">
            <div className="card-header text-center">
                <img src={defaultProfileImage} width={200} alt="profile image" className="img-fluid shadow-sm rounded-circle" />
            </div>
            <div className="card-body text-center">
                <span className="fs-3">{user.username}</span>
            </div>
        </div>
  </>;
}
