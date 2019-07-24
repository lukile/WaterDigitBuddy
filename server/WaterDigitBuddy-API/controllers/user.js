const express = require("express");
const bodyParser = require("body-parser");
const cors = require("cors");


const Exception = require("../exceptions");
const middleware = require("./middlewares");
const User = require("../services/User");


const endpoint = express.Router();

endpoint.use(bodyParser.json());
endpoint.use(bodyParser.urlencoded({ extended: false }));
endpoint.use(cors());

/**
 * Get all users
 * external: JSON Web Token
 * GET /users
 */
endpoint.get("/", async (request, response) => {
    try {
        response.status(200).json({
            data: {
                users: await User.findAll()
            }
        });
    } catch (e) {
        response.status(e.code < 600 ? e.code : Exception.InternalError.code).json({ errors: [e.message] });
    }
});

/**
 * Get current user
 * external: JSON Web Token
 * GET /users/current (current : l'utilisateur actuel)
 */
endpoint.get("/current", [middleware.authentication()], async (request, response) => {
    try {
        const user = new User(request.credentials.token.userId);

        response.status(200).json({
            data: {
                user: await user.find({ _id: user.id })
            }
        });
    } catch (e) {
        response.status(e.code < 600 ? e.code : Exception.InternalError.code).json({ errors: [e.message] });
    }
});

/**
 * Get device Id (ESP) for the current user
 * GET /user/current/deviceid (devideId = numero unique de l'esp)
 */
endpoint.get("/current/deviceid", async(request, response) => {
    try {
        let user = new User();

        response.status(200).json({
            data: {
                user: await user.findUserByDeviceId({ deviceid: request.headers["x-access-deviceid"] })
            }
        });
    } catch (e) {
        response.status(e.code < 600 ? e.code : Exception.InternalError.code).json({ error: [e.message] });
    }
});

/**
 * Update current user with performances data
 * external: JSON Web Token
 * PUT /users/current/histoPerf
 */
/*endpoint.put("/current/histoperf", [middleware.authentication(), middleware.graphql("user.update")], async (request, response) => {
    try {
        const user = new User(
            null,
            null,
            null,
            null,
            null,
            null
        );

        //await user.savePerformances(); // await remplace THEN

        response.status(200).json({
            data: {}
        });
    } catch (e) {
        response.status(e.code < 600 ? e.code : Exception.InternalError.code).json({ errors: [e.message] });
    }
});*/


module.exports = endpoint;
