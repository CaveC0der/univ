import {ButtonHTMLAttributes, FC} from "react";
import classes from "./Button.module.scss";

const Button: FC<ButtonHTMLAttributes<HTMLButtonElement>> = ({className, children, ...props}) => {
    return (
        <button className={className ? className + " " + classes._ : classes._} {...props}>
            {children}
        </button>
    );
};

export default Button;
