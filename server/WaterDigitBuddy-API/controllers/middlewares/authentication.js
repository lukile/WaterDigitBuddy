const Exception = require("../../exceptions");
const Authentication = require("../../services/Authentication");
const User = require("../../services/User");


module.exports = (strict = false) => {
    return async (request, response, next) => {
        try {
            const { email, userId } = Authentication.identifyProvider(request.headers["x-access-token"] || "");
            request.credentials = {
                token: {
                    userId,
                    email
                }
            };

            // overload .json() method to inject refresh token
            const send = response.json; // re génère un new token 
            response.json = function (response) {
                if (response.data) {
                    response.data.token = Authentication.refreshToken(request.headers["x-access-token"] || "");
                }

                send.call(this, response);
            };
        } catch (err) {
            if (err.name === "TokenExpiredError" || err.name === "JsonWebTokenError") {
                return response.status(Exception.Unauthorized.code).json({ errors: ["token: " + err.message] });
            } else {
                return response.status(Exception.Unauthorized.code).json({ errors: [err.message] });
            }
        }

        next();
    };
};
