const invmod = require("mathjs").invmod;

class PRNSequenceGeneratorServlet {
    static async nonConstIncrementNLCGService(req, res, next) {
        try {
            console.log(`request.originalUrl: ${req.originalUrl}`);
            let {data} = req.query;
            data = data.map(value => parseInt(value));
            const gen = PRNSequenceGeneratorServlet.nonConstIncrementNLCG(...data);
            const sequence = [];
            for (let i = 0; i < data[data.length - 1]; i++)
                sequence.push(gen.next().value);
            res.json(sequence.join(" "));
        } catch (e) {
            next(e);
        }
    }

    static async ICGService(req, res, next) {
        try {
            console.log(`request.originalUrl: ${req.originalUrl}`);
            let {data} = req.query;
            data = data.map(value => parseInt(value));
            const gen = PRNSequenceGeneratorServlet.ICG(...data);
            const sequence = [];
            for (let i = 0; i < data[data.length - 1]; i++)
                sequence.push(gen.next().value);
            res.json(sequence.join(" "));
        } catch (e) {
            next(e);
        }
    }

    static* nonConstIncrementNLCG(seed, multiplier, increment, modulus) {
        let previousSeed;
        while (true) {
            previousSeed = seed;
            seed = (multiplier * seed + increment) % modulus;
            increment = Math.floor((multiplier * previousSeed + increment) / modulus);
            yield seed;
        }
    }

    static* ICG(seed, multiplier, increment, modulus) {
        let invMod;
        while (true) {
            if (isNaN(invMod = invmod(seed, modulus)))
                yield seed = increment;
            else
                yield seed = (multiplier * invMod + increment) % modulus;
        }
    }
}

module.exports = PRNSequenceGeneratorServlet;
