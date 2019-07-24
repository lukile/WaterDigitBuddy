const mongoose = require("mongoose");
const express = require("express");
const cors = require("cors");
const dotenv = require('dotenv');

dotenv.config();

mongoose.Promise = global.Promise;
console.log(process.env.MONGO_DB_PWD);

if (process.env.DEVELOP === "true") {
  mongoose.connect("mongodb://localhost:27017/WaterDigitBuddy");
}else {
  mongoose.connect("mongodb+srv://" + process.env.MONGO_DB_USER + ":" + process.env.MONGO_DB_PWD + "@clusterwdb-zo6mn.mongodb.net/test?retryWrites=true&w=majority")

}


const RouterManager = require("./controllers");
const Exception = require("./exceptions");


const port = process.argv.length >= 3 && process.argv[2] === "dev" ? process.env.PORT : 5000; // process.env["DEV_PORT"] : process.env["PROD_PORT"];

const app = express();
app.use((error, request, response, next) => {
    try {
        if (!request.headers["content-type"]) {
            throw new Exception.InternalError("missing content type");
        } else if (request.headers["content-type"] !== "application/json") {
            throw new Exception.InternalError("only json is currently supported");
        }

        next();
    } catch (e) {
        response.status(e.code).json({ errors: [e.message] });
    }
});

RouterManager.attach(app);
app.use((error, request, response, next) => {
    try {
        if (error) {
            if (error instanceof SyntaxError) {
                throw new Exception.InternalError("invalid json");
            }

            throw new Exception.InternalError(e.message ? e.message : "error");
        }
    } catch (e) {
        response.status(e.code).json({ errors: [e.message] });
    }
});


app.listen(port, function () {
    console.log("Listening on port " + port + "!")
});
