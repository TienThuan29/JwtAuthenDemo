import React, { useEffect, useState } from 'react'
import { AUTH_TOKEN, LOGIN_PAGE } from '../config/ConstantConfig';
import AuthenticationAPI from '../service/AuthenticationAPI';
import { useNavigate } from 'react-router-dom';

export default function Home() {
    
    const navigator = useNavigate();
    const [user, setUser] = useState({
        username: '', 
        fullname: ''
    });

    const token = localStorage.getItem(AUTH_TOKEN);

    useEffect(()=> {
        if (token) {
            AuthenticationAPI.getInfo(token).then(
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
        localStorage.removeItem(AUTH_TOKEN);
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
