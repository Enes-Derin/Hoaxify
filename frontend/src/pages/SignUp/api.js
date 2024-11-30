
import http from "../../lib/http";
export function signup(body){
    return http.post('/api/v1/users',body)
}