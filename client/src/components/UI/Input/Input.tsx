import {FC, InputHTMLAttributes} from "react";
import classes from "./Input.module.scss";

const Input: FC<InputHTMLAttributes<HTMLInputElement>> = ({className, ...props}) => {
    return (
        <input className={className ? className + " " + classes._ : classes._} {...props}/>
    );
};

export default Input;
