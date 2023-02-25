class RowCalculatorServlet {
    static async service(req, res, next) {
        try {
            console.log(`request.originalUrl: ${req.originalUrl}`);
            const {data} = req.query;
            res.json(RowCalculatorServlet.calculate(...data.map(value => parseFloat(value))));
        } catch (e) {
            next(e);
        }
    }

    static calculate(a, b, x, y) {
        let result = 1;
        x = Math.log(x);
        const numerator = Math.log(x) + b * y + a * Math.pow(x, 2);
        for (let i = 0; i <= 10; i++)
            result *= Math.pow(-1, i) * numerator / (RowCalculatorServlet._factorial(i) * Math.E);
        return result;
    }

    static _factorial(n) {
        if (n < 0)
            throw new Error("n < 0");
        let result = 1;
        for (let i = 1; i <= n; i++)
            result *= i;
        return result;
    }
}

module.exports = RowCalculatorServlet;
