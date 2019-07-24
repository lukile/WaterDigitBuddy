const crypto = require("crypto");
const jsonwebtoken = require("jsonwebtoken");


const SECRET_TOKEN = require("../token");


module.exports = class Authentication {
    constructor(login, password) {
        this.login = login;
        this.password = password;
    }

    static identifyProvider(token) {
        return jsonwebtoken.verify(token, SECRET_TOKEN);
    }

    static getToken(data, unlimited = false) {
        if (unlimited) {
            return jsonwebtoken.sign(data, SECRET_TOKEN);
        } else {
            return jsonwebtoken.sign(data, SECRET_TOKEN, { expiresIn: "24h" }); // expire after 24h
        }
    }

    static refreshToken(token, unlimited = false) {
        const { email, userId } = Authentication.identifyProvider(token);
        return Authentication.getToken({ email, userId }, unlimited);
    }

    static hash(password) {
        return crypto.createHash("sha512").update(password).digest("hex"); // currently, use sha512 algorithm
    }
};
