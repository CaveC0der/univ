const Router = require("express").Router;
const PRNSequenceGeneratorServlet = require("./PRNSequenceGeneratorServlet");

const router = new Router();

router.get("/non_const_increment_nlcg", PRNSequenceGeneratorServlet.nonConstIncrementNLCGService);
router.get("/icg", PRNSequenceGeneratorServlet.ICGService);

module.exports = router;
