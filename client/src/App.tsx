import {FC} from "react";
import "./App.scss";
import RowCalculator from "./components/RowCalculator/RowCalculator";

const App: FC = () => {
    return (
        <div className="App grid-container">
            <RowCalculator/>
        </div>
    );
}

export default App;
