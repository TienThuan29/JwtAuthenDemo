import React, { useState } from 'react'
import { DEFAULT_ENABLE, LOGIN_PAGE, ROLE_ADMIN, ROLE_USER } from '../config/ConstantConfig'
import { ERROR_FULLNAME_INVALID, ERROR_PASS_INVALID, ERROR_RE_PASS_INVALID, ERROR_SELECT_ROLE_INVALID, ERROR_USERNAME_INVALID, MESS_REGISTER_SUCCESS } from '../config/MessageConfig';
import AuthenticationAPI from '../service/AuthenticationAPI';

export default function Register() {

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [repeatPass, setRepeatPass] = useState('');
  const [fullname, setFullname] = useState('');
  const [roleCode, setRole] = useState(ROLE_USER);
  const [successMess, setSuccessMess] = useState('');

  // Function handler
  const handleUsername = (e) => setUsername(e.target.value);
  const handlePassword = (e) => setPassword(e.target.value);
  const handleRepeatPassword = (e) => setRepeatPass(e.target.value);
  const handleFullname = (e) => setFullname(e.target.value);
  const handleRole = (e) => setRole(e.target.value);

  const [error, setError] = useState({
    username: '',
    password: '',
    repeatPass: '',
    fullname: '',
    roleCode: ''
  });


  function validate() {
    let valid = true;
    const errorCopy = { ...error }
    errorCopy.username = username.trim() ? '' : ERROR_USERNAME_INVALID;
    errorCopy.password = password.trim() ? '' : ERROR_PASS_INVALID;
    errorCopy.repeatPass = (repeatPass.trim() && repeatPass == password) ? '' : ERROR_RE_PASS_INVALID;
    errorCopy.fullname = fullname.trim() ? '' : ERROR_FULLNAME_INVALID;
    errorCopy.roleCode = roleCode != null ? '' : ERROR_SELECT_ROLE_INVALID;
    valid = username.trim() && password.trim() && (repeatPass.trim() || repeatPass != password) && fullname.trim() && (roleCode != null);
    setError(errorCopy);
    //setSuccessMess( valid ? MESS_REGISTER_SUCCESS : '' );
    return valid;
  }


  const submit = (e) => {
    e.preventDefault();

    if(validate()) {
      const registerRequest = {
        username: username,
        password: password,
        fullname: fullname,
        enable: DEFAULT_ENABLE,
        roleCode: roleCode
      }

      console.log(registerRequest);

      AuthenticationAPI.register(registerRequest).then(
        (request) => {
          console.log(request.data);
          setSuccessMess(MESS_REGISTER_SUCCESS);
        }
      )
      .catch(
        (error) => {
          console.log(error);
        }
      )
    }

  }


  return (
    <div>

      <div className="container mt-5">
        <div className="row justify-content-center">
          <div className="col-md-6">
            <h2 className="text-center mb-4">Register</h2>

            <form onSubmit={submit}>

              <div className="mb-3">
                <label className="form-label">Username</label>
                <input type="text" className="form-control" id="username"
                  placeholder="Enter username" required
                  onChange={handleUsername}
                />
              </div>
              {
                error.username && <div className='text-danger mb-3'>{error.username}</div>
              }

              <div className="mb-3">
                <label className="form-label">Full name</label>
                <input type="text" className="form-control" id="fullname"
                  placeholder="Enter full name" required
                  onChange={handleFullname}
                />
              </div>
              {
                error.fullname && <div className='text-danger mb-3'>{error.fullname}</div>
              }

              <div className="mb-3">
                <label className="form-label">Password</label>
                <input type="password" className="form-control" id="password"
                  placeholder="Enter password" required
                  onChange={handlePassword}
                />
              </div>
              {
                error.password && <div className='text-danger mb-3'>{error.password}</div>
              }

              <div className="mb-3">
                <label className="form-label">Repeat Password</label>
                <input type="password" className="form-control" id="repeatPassword"
                  placeholder="Repeat password" required
                  onChange={handleRepeatPassword}
                />
              </div>
              {
                error.repeatPass && <div className='text-danger mb-3'>{error.repeatPass}</div>
              }

              <div className="mb-3">
                <label className="form-label">Role</label>
                <select className="form-select" id="roleCode" onChange={handleRole} required>
                  <option value={ROLE_USER}>USER</option>
                  <option value={ROLE_ADMIN}>ADMIN</option>
                </select>
              </div>
              {
                error.roleCode && <div className='text-danger mb-3'>{error.roleCode}</div>
              }

              {
                successMess && <div className='text-success mb-3'>{successMess}</div>
              }

              <div className='d-flex justify-content-between'>
                <button type="submit" className="btn btn-primary">Register</button>
                <div>Have an account? <a href={LOGIN_PAGE}>Signin</a></div>
              </div>
            </form>

          </div>
        </div>
      </div>

    </div>
  )
}
