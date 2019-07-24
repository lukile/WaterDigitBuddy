const fs = require("fs");
const path = require("path");
const { graphql, buildSchema } = require("graphql");


const schema = buildSchema(fs.readFileSync(path.resolve(__dirname, "../../graphql/schema.graphqls"), { encoding: "utf8" }));
const endpoints = {};
fs
    .readdirSync(path.resolve(__dirname, "../../graphql/endpoints"))
    .forEach(file => {
        endpoints[file] = fs.readFileSync(path.resolve(__dirname, "../../graphql/endpoints/" + file), { encoding: "utf8" })
    });


module.exports = graphqle => {
    return (request, response, next) => {
        graphql(
            schema,
            endpoints[graphqle + ".graphqle"],
            request.method === "GET" ? request.query : request.body
        ).then(r => {
            if (r.errors) {
                return response.status(401).json({ errors: r.errors.map(e => "graphql: " + e.message) });
            }

            if (request.method === "GET") {
                request.query = r.data;
            } else {
                request.body = r.data
            }

            next();
        });
    }
};
