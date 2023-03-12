import {FC, ReactNode} from "react";
import classes from "./Modal.module.scss";

interface ModalProps {
    children: ReactNode | undefined;
    close: () => void;
}

const Modal: FC<ModalProps> = ({children, close}) => {
    return (
        <div className={"flex-container " + classes.container} onClick={() => close()}>
            <div className={"flex-container " + classes.content} onClick={event => event.stopPropagation()}>
                {children}
            </div>
        </div>
    );
};

export default Modal;
