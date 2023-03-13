import {FC, useState} from "react";
import "./PRNSequenceGenerator.scss";
import Button from "../UI/Button/Button";
import Input from "../UI/Input/Input";
import Modal from "../UI/Modal/Modal";
import axios from "axios";

const PRNSequenceGenerator: FC = () => {
    const [seed, setSeed] = useState<string>("7");
    const [multiplier, setMultiplier] = useState<string>("5");
    const [increment, setIncrement] = useState<string>("10");
    const [modulus, setModulus] = useState<string>("256");
    const [sequenceLength, setSequenceLength] = useState<string>("10");
    const [modal, setModal] = useState<boolean>(false);
    const [message, setMessage] = useState<JSX.Element>(<h1>Unexpected error!</h1>);

    const parseData = () => [seed, multiplier, increment, modulus, sequenceLength].map(value => parseFloat(value));

    const validateData = (data: number[]): boolean => {
        if (data.some(value => isNaN(value) || !Number.isInteger(value) || value <= 0)) {
            setMessage(<h3>One or more of numbers is NaN or not Integer or equal or less than 0!</h3>);
            setModal(true);
            return false;
        }
        return true;
    };

    return (
        <form className="prns-generator" onSubmit={event => event.preventDefault()}>
            {modal && <Modal close={() => setModal(false)}>{message}</Modal>}
            <h1>PRN Sequence Generator</h1>
            <div className="form-row">
                <label>Enter seed ( x<sub>0</sub> )</label>
                <Input
                    type="text"
                    value={seed}
                    onChange={event => setSeed(event.target.value)}
                />
            </div>
            <div className="form-row">
                <label>Enter multiplier ( a )</label>
                <Input
                    type="text"
                    value={multiplier}
                    onChange={event => setMultiplier(event.target.value)}
                />
            </div>
            <div className="form-row">
                <label>Enter increment ( c<sub>0</sub> )</label>
                <Input
                    type="text"
                    value={increment}
                    onChange={event => setIncrement(event.target.value)}
                />
            </div>
            <div className="form-row">
                <label>Enter modulus ( N )</label>
                <Input
                    type="text"
                    value={modulus}
                    onChange={event => setModulus(event.target.value)}
                />
            </div>
            <div className="form-row">
                <label>Enter sequence length ( l )</label>
                <Input
                    type="text"
                    value={sequenceLength}
                    onChange={event => setSequenceLength(event.target.value)}
                />
            </div>
            <div className="form-row buttons-row">
                <Button onClick={async () => {
                    const data = parseData();
                    if (validateData(data)) {
                        const response = await axios.get<string>(
                            "http://localhost:5000/non_const_increment_nlcg",
                            {params: {data}}
                        );
                        setMessage(<h3>Sequence: {response.data}</h3>);
                        setModal(true);
                    }
                }}>NLCG</Button>
                <Button className="question" onClick={() => {
                    setMessage(<h3 style={{textAlign: "left"}}>
                        Recommendations: modulus &lt; 0, 0 &lt; multiplier &lt; modulus, 0 &le; increment &lt; modulus,
                        0 &le; seed &lt; modulus<br/>
                        For NLCG: modulus should be 2<sup>a</sup>, GCD(multiplier, modulus) = 1, GCD(increment, modulus)
                        = 1<br/>
                        For ICG: modulus should be 2<sup>a</sup>, multiplier should be an odd number, increment should
                        be an even number, seed should be an odd number
                    </h3>);
                    setModal(true);
                }}>?</Button>
                <Button onClick={async () => {
                    const data = parseData();
                    if (validateData(data)) {
                        const response = await axios.get<string>(
                            "http://localhost:5000/icg",
                            {params: {data}}
                        );
                        setMessage(<h3>Sequence: {response.data}</h3>);
                        setModal(true);
                    }
                }}>ICG</Button>
            </div>
        </form>
    );
};

export default PRNSequenceGenerator;
