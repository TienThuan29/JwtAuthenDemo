import axios from "axios";
import { ACCESS_TOKEN_KEY } from "../config/ConstantConfig";

const REST_API_BASE = "http://localhost:8080/api/auth"
const REGISTER_URL = REST_API_BASE + "/register"
const AUTHEN_URL = REST_API_BASE + "/authenticate"
const GET_USER_INFO = "http://localhost:8080/api/users/info"
const REFRESH_TOKEN_URL = "http://localhost:8080/api/auth"+"/refresh-token"


const AuthenticationAPI = {

    register(registerRequest) {
        return axios.post(REGISTER_URL, registerRequest);
    },

    authenticate(authenticationRequest) {
        return axios.post(AUTHEN_URL, authenticationRequest);
    },

    refresh(refresh_token) {
        const refreshAction = axios.get(REFRESH_TOKEN_URL, {
            headers: {
                "Authorization" : `Bearer ${refresh_token}`
            }
        });

        refreshAction.then(
            (response) => {
                localStorage.setItem(ACCESS_TOKEN_KEY, response.data.access_token);
            }
        )
        .catch(
            (error) => {
                console.log(error);
            }
        )
    },

    getInfo(access_token) {
        return axios.get(GET_USER_INFO, {
            headers: {
                "Authorization" : `Bearer ${access_token}`      
            }
        });
    }
}

export default AuthenticationAPI;



