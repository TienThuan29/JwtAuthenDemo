import React, { useEffect, useState } from 'react'
import { ACCESS_TOKEN_KEY, LOGIN_PAGE, REFRESH_TOKEN_KEY } from '../config/ConstantConfig';
import AuthenticationAPI from '../service/AuthenticationAPI';
import { useNavigate } from 'react-router-dom';

export default function Home() {
    
    const navigator = useNavigate();
    const [user, setUser] = useState({
        username: '', 
        fullname: ''
    });

    const access_token = localStorage.getItem(ACCESS_TOKEN_KEY);
    const refresh_token = localStorage.getItem(REFRESH_TOKEN_KEY);

    console.log("access_token: ",access_token);
    console.log("refesh_token: ",refresh_token);
    

    useEffect(()=> {
        if (access_token) {
            AuthenticationAPI.getInfo(access_token).then(
                (response) => {
                    setUser(response.data);
                }
            )
            .catch(
                (error) => {
                    console.log(error);
                }
            )
        }
    }, []);

    const logout = () => {
        localStorage.removeItem(ACCESS_TOKEN_KEY);
        navigator(LOGIN_PAGE);
    }


    return (
        <div>

            <nav className="navbar navbar-expand-lg navbar-light bg-light">
                <div className="container-fluid">
                    <span className="navbar-brand mb-0 h1">
                        Welcome {user.fullname}
                    </span>
                    <button onClick={logout} className="btn btn-danger" type="button">Logout</button>
                </div>
            </nav>

        </div>
    )
}
