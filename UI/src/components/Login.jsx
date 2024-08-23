import React, { useState } from 'react'
import AuthenticationAPI from '../service/AuthenticationAPI';
import { useNavigate } from 'react-router-dom';
import { AUTH_TOKEN, HOME_PAGE, REGISTER_PAGE } from '../config/ConstantConfig';

export default function Login() {

    const navigator = useNavigate();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [errorResponse, setErrorResponse] = useState({
        httpStatus: '',
        message: ''
    });

    // Function handler
    const handleUsername = (e) => setUsername(e.target.value);

    const handlePassword = (e) => setPassword(e.target.value);

    const submit = (e) => {
        e.preventDefault();

        const authRequest = {
            username, password
        }
        // const errorResponseCopy = {
        //     ...errorResponse
        // }

        console.log(authRequest);


        if (authRequest.username && authRequest.password) {
            AuthenticationAPI.authenticate(authRequest).then(
                (response) => {
                    localStorage.setItem(AUTH_TOKEN, response.data.token);
                    navigator(HOME_PAGE);
                }
            )
            .catch(
                (error) => {
                    setErrorResponse(error.response.data);
                    console.log(error.response.data);
                }
            )
        }
    }


    return (
        <div className="container mt-5">
            <div className="row justify-content-center">
                <div className="col-md-4">
                    <h3 className="text-center mb-4">Login</h3>
                    <form onSubmit={submit}>
                        <div className="mb-3">
                            <label className="form-label">Username</label>
                            <input type="text" className="form-control" id="username"
                                onChange={handleUsername}
                                placeholder="Enter your username" required
                            />
                        </div>
                        <div className="mb-3">
                            <label className="form-label">Password</label>
                            <input type="password" className="form-control" id="password"
                                onChange={handlePassword}
                                placeholder="Enter your password" required />
                        </div>
                        {
                            errorResponse.message && <div className='text-danger mb-3'>{errorResponse.message}</div>
                        }
                        <div className='d-flex justify-content-between'>
                            <button type="submit" className="btn btn-primary">Login</button>
                            <div>Don't have account? <a href={REGISTER_PAGE}>Register here</a></div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    )
}
