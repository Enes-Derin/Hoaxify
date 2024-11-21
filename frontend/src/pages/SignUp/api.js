import axios from "axios";
export function signup(body){
    return axios.post('/api/v1/users',body)
}