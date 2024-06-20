export function Input(props) {

    const {id,label,error,onChange, type}=props;

  return (
    <div className="mb-4">
    <label className="form-label" htmlFor={id}>
      {label}
    </label>
    <input
      className={error ? "form-control is-invalid" : "form-control"}
      id={id}
      onChange={onChange}
      type={type}
    />
    <div className="invalid-feedback">{error}</div>
  </div>
  )
}
