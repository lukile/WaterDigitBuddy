const Exception = require("../../exceptions");
const User = require("../../services/User");


module.exports = (strict = false) => {
    return async (request, response, next) => {
        try {
            const { deviceId } = User.identifyProvider(request.headers["x-access-deviceId"]);
            request.credentials = {
                deviceId: {
                    deviceid
                }
            };
        } catch (err) {
            if (err.name === "DeviceIdError") {
                return response.status(Exception.Unauthorized.code).json({ errors: ["deviceId: " + err.message] });
            } else {
                return response.status(Exception.Unauthorized.code).json({ errors: [err.message] });
            }
        }
        next();
    };
};
