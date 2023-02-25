import {createRoot} from "react-dom/client";
import {StrictMode} from "react";
import App from "./App";
import {createBrowserRouter, Navigate, RouterProvider} from "react-router-dom";
import "./index.scss";

const root = createRoot(
    document.getElementById("root") as HTMLElement
);

const router = createBrowserRouter([
    {
        path: "/",
        element: <App/>
    },
    {
        path: "*",
        element: <Navigate to="/" replace/>
    }
]);

root.render(
    <StrictMode>
        <RouterProvider router={router}/>
    </StrictMode>
);
