import {createBrowserRouter } from "react-router-dom"
import { Home } from "../pages/Home"
import { SignUp } from "../pages/SignUp/SignUp"
import App from "../App"
import { ActivationPage } from "../pages/Activation"
import { User } from "../pages/User"
import { Login } from "../pages/login"


export default createBrowserRouter([
 {
    path:"/",
    Component: App,
    children:[
        {
            path:"/",
            index: true,
            Component : Home,
          },
          {
            path: "/signup",
            Component : SignUp
          },
          {
            path: "/activation/:token",
            Component : ActivationPage
          },
          {
            path: "/user/:id",
            Component: User
          },
          {
            path: "/login",
            Component: Login
          }
    ]
 }
])