import axios from "axios";

const REST_API_BASE = "http://localhost:8080/api/auth"
const REGISTER_URL = REST_API_BASE + "/register"
const AUTHEN_URL = REST_API_BASE + "/authenticate"
const GET_USER_INFO = "http://localhost:8080/api/users/info"


const AuthenticationAPI = {

    register(registerRequest) {
        return axios.post(REGISTER_URL, registerRequest);
    },

    authenticate(authenticationRequest) {
        return axios.post(AUTHEN_URL, authenticationRequest);
    },

    getInfo(token) {
        return axios.get(GET_USER_INFO, {
            headers: {
                "Authorization" : `Bearer ${token}`      
            },
            params: {
                token: token
            }
        });
    }
}

export default AuthenticationAPI;



