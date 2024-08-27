import React, { useEffect, useState } from 'react'
import { ACCESS_TOKEN_KEY, HOME_PAGE, LOGIN_PAGE, REFRESH_TOKEN_KEY } from '../config/ConstantConfig';
import AuthenticationAPI from '../service/AuthenticationAPI';
import { useNavigate } from 'react-router-dom';

export default function Home() {
    
    const navigator = useNavigate();
    const [user, setUser] = useState({
        username: '', 
        fullname: ''
    });

    useEffect(() => {
        const access_token = localStorage.getItem(ACCESS_TOKEN_KEY);
        const refresh_token = localStorage.getItem(REFRESH_TOKEN_KEY);
    
        if (access_token) {
            AuthenticationAPI.getInfo(access_token)
                .then((response) => {
                    setUser(response.data);
                })
                .catch((error) => {
                    console.error("Access token invalid:", error);
                    if (refresh_token) {
                        AuthenticationAPI.refresh(refresh_token).then((response) => {
                                localStorage.setItem(ACCESS_TOKEN_KEY, response.data.access_token);
                                console.log("New Access token: ", response.data.access_token);
                                return AuthenticationAPI.getInfo(response.data.access_token);
                            })
                            .then((response) => {
                                console.log(response.data);
                                
                                setUser(response.data);
                            })
                            .catch((refreshError) => {
                                console.error("Error refreshing token:", refreshError);
                                window.location.reload();
                            });
                    } 
                    else {
                        navigator(LOGIN_PAGE)
                    }
                });
        } 
        else {
            navigator(LOGIN_PAGE)
        }
    }, []);

    
    const logout = () => {
        const access_token = localStorage.getItem(ACCESS_TOKEN_KEY);
        AuthenticationAPI.logout(access_token).then(
            (response) => {
                console.log(response);
            }
        )
        .catch(
            (error) => console.log(error)
        );
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
