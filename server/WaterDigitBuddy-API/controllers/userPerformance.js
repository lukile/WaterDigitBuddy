const express = require("express");
const bodyParser = require("body-parser");
const cors = require("cors");


const Exception = require("../exceptions");
const middleware = require("./middlewares");
const UserPerformanceService = require("../services/UserPerformance");
const UserService = require("../services/User");


const endpoint = express.Router();

endpoint.use(bodyParser.json());
endpoint.use(bodyParser.urlencoded({ extended: false }));
endpoint.use(cors());

/**
 * Add a new performances for the current user
 * external: GraphQL
 * access: all
 * POST /performances
 */
endpoint.post("/:userId", [middleware.graphql("performance.save")/*, middleware.authentication()*/], async (request, response) => {
    try {
        if(!!request.headers["x-access-deviceid"]) {  // inverse de null

          let user = await UserService.Instance.find({ _id: request.params.userId });
          //console.log(request.params.userId);

          if(!user) {
            throw new Error("User undefined");
          }

          let performance = new UserPerformanceService(
              null,
              request.body.performance.datePerformance,
              request.body.performance.speed,
              request.body.performance.lengthType,
              request.body.performance.startTime,
              request.body.performance.endTime,
              request.body.performance.distance,
              request.body.performance.programType,
              user,
              request.headers["x-access-deviceid"]
              //request.body.performance.deviceid
          );

          performance = await performance.save();

          response.status(200).json({
              data: {
                  performance: performance
              }
          });
        }
    } catch (e) {
        //console.log(e);
        response.status(e.code < 600 ? e.code : Exception.InternalError.code).json({ errors: [e.errmsg ? e.errmsg : e.message] });
    }
});


/**
 * Get all performances
 * external: JSON Web Token
 * GET /performances
 */
endpoint.get("/", [middleware.authentication()], async (request, response) => {
    try {
        response.status(200).json({
            data: {
                performances: await UserPerformanceService.findAll()
            }
        });
    } catch (e) {
        //console.log(e);
        response.status(e.code < 600 ? e.code : Exception.InternalError.code).json({ errors: [e.message] });
    }
});

//  rer toutes les routes avec POST (save) / DELETE / PUT ou PATCH (modification)

module.exports = endpoint;
