const Router = require("express").Router;
const RowCalculatorServlet = require("./RowCalculatorServlet");

const router = new Router();

router.get("/row_calculator", RowCalculatorServlet.service);

module.exports = router;
