const express = require("express");
const bodyParser = require("body-parser");
const cors = require("cors");


const Exception = require("../exceptions");
const middleware = require("./middlewares");
const SwimmingProgram = require("../services/swimmingProgram");


const endpoint = express.Router();

endpoint.use(bodyParser.json());
endpoint.use(bodyParser.urlencoded({ extended: false }));
endpoint.use(cors());

/**
 * Add a new swimming-pool program
 * external: GraphQL
 * POST /programs
 */
endpoint.post("/", [middleware.graphql("program.save"), middleware.authentication()], async (request, response) => {
    try {
        let program = new SwimmingProgram(
            null,
            request.body.program.name,
            request.body.program.description
        );

        program = await program.save();

        response.status(200).json({
            data: {
                program
            }
        });
    } catch (e) {
        response.status(e.code < 600 ? e.code : Exception.InternalError.code).json({ errors: [e.errmsg ? e.errmsg : e.message] });
    }
});


/**
 * Get all programs
 * external: JSON Web Token
 * GET /programs
 */
endpoint.get("/", [middleware.authentication()], async (request, response) => {
    try {
        response.status(200).json({
            data: {
                programs: await SwimmingProgram.findAll()
            }
        });
    } catch (e) {
        response.status(e.code < 600 ? e.code : Exception.InternalError.code).json({ errors: [e.message] });
    }
});

//  rer toutes les routes avec DELETE / PUT ou PATCH (modification)

module.exports = endpoint;
