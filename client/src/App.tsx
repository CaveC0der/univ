import {FC} from "react";
import "./App.scss";
import PRNSequenceGenerator from "./components/PRNSequenceGenerator/PRNSequenceGenerator";

const App: FC = () => {
    return (
        <div className="App grid-container">
            <PRNSequenceGenerator/>
        </div>
    );
}

export default App;
