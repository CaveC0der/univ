import {FC, useState} from "react";
import "./RowCalculator.scss";
import Button from "../UI/Button/Button";
import Input from "../UI/Input/Input";
import Modal from "../UI/Modal/Modal";
import axios from "axios";

const RowCalculator: FC = () => {
    const [a, setA] = useState<string>("0.51");
    const [b, setB] = useState<string>("28");
    const [x, setX] = useState<string>("34");
    const [y, setY] = useState<string>("54");
    const [modal, setModal] = useState<boolean>(false);
    const [message, setMessage] = useState<string>("Unexpected error!");

    return (
        <form
            className="row-calculator"
            onSubmit={async event => {
                event.preventDefault();
                console.log(a, b, x, y);
                const data: number[] = [a, b, x, y].map(value => parseFloat(value));
                if (data.some(value => isNaN(value))) {
                    setMessage("One or more of number is NaN!");
                    setModal(true);
                    return;
                }
                if (data[2] <= 0) {
                    setMessage("X is 0 or less then 0!")
                    setModal(true);
                    return;
                }
                const result = await axios.get<number>("http://localhost:5000/row_calculator", {params: {data}});
                setMessage(`Result: ${result.data}`);
                setModal(true);
            }}
        >
            {modal && <Modal close={() => setModal(false)}><h3>{message}</h3></Modal>}
            <h1>Row Calculator</h1>
            <div className="form-row">
                <label>Enter a</label>
                <Input
                    type="text"
                    value={a}
                    onChange={event => setA(event.target.value)}
                />
            </div>
            <div className="form-row">
                <label>Enter b</label>
                <Input
                    type="text"
                    value={b}
                    onChange={event => setB(event.target.value)}
                />
            </div>
            <div className="form-row">
                <label>Enter x ( ln(x) will be used )</label>
                <Input
                    type="text"
                    value={x}
                    onChange={event => setX(event.target.value)}
                />
            </div>
            <div className="form-row">
                <label>Enter y</label>
                <Input
                    type="text"
                    value={y}
                    onChange={event => setY(event.target.value)}
                />
            </div>
            <Button>Send</Button>
        </form>
    );
};

export default RowCalculator;
