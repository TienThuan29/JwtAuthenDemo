import axios from "axios";
import { ACCESS_TOKEN_KEY } from "../config/ConstantConfig";

const REST_API_BASE = "http://localhost:8080/api/auth"
const REGISTER_URL = REST_API_BASE + "/register"
const AUTHEN_URL = REST_API_BASE + "/authenticate"
const GET_USER_INFO = "http://localhost:8080/api/users/info"
const REFRESH_TOKEN_URL = REST_API_BASE + "/refresh-token"
const LOGOUT_URL = REST_API_BASE + "/logout"
// http://localhost:8080/api/api/auth/logout

const AuthenticationAPI = {


    register(registerRequest) {
        return axios.post(REGISTER_URL, registerRequest);
    },


    authenticate(authenticationRequest) {
        return axios.post(AUTHEN_URL, authenticationRequest);
    },


    refresh(refresh_token) {
        return axios.post(REFRESH_TOKEN_URL, {} ,{
            headers: {
                "Authorization": `Bearer ${refresh_token}`
            }
        });
    },


    logout(access_token) {
        return axios.post(LOGOUT_URL,{} , {
            headers: {
                "Authorization": `Bearer ${access_token}`
            }
        })
    },


    getInfo(access_token) {
        return axios.get(GET_USER_INFO ,{
            headers: {
                "Authorization": `Bearer ${access_token}`
            }
        });
    }
}

export default AuthenticationAPI;



