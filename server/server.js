const dotenv = require("dotenv");
const express = require("express");
const cors = require("cors");
const router = require("./router");

dotenv.config();

const PORT = process.env.PORT || 5000;
const HOST = process.env.HOST || "localhost";
const CLIENT_URL = process.env.CLIENT_URL || "*";

const app = express();

app.use(cors({
    origin: CLIENT_URL
}));
app.use(router);

const start = async () => {
    try {
        app.listen(PORT, HOST,() => {
            console.log(`PORT: ${PORT}`);
            console.log(`HOST: ${HOST}`);
        })
    } catch (e) {
        console.log(e);
    }
};

start().then();
