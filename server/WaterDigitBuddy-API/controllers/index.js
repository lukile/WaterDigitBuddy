const Exception = require("../exceptions")
const authRouter = require("./authentication");
const userRouter = require("./user");
const userPerformanceRouter = require("./userPerformance");
const swimmingProgramRouter = require("./swimmingProgram");


const RouterManager = function () { };

RouterManager.attach = function (app) {
    app.use("/authentication", authRouter);
    app.use("/users", userRouter);
    app.use("/performances", userPerformanceRouter);
    app.use("/programs", swimmingProgramRouter);
    
    app.use((request, response) => {
        try {
            throw new Exception.NotFound("this is not the route you are looking for");
        } catch (e) {
            response.status(e.code).json({ errors: [e.message] });
        }
    });
};


module.exports = RouterManager;
