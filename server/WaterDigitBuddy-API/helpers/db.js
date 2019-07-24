const mongoose = require("mongoose");
const models = require("../models");
const Authentication = require("../services/Authentication");


mongoose.Promise = global.Promise;
if (process.env.DEVELOP === "true") {
  mongoose.connect("mongodb://localhost:27017/xxxx");
}else {
  mongoose.connect("mongodb+srv://" + process.env.MONGO_DB_USER + ":" + process.env.MONGO_DB_PWD + ">@clusterwdb-zo6mn.mongodb.net/test?retryWrites=true&w=majority")
}


(async () => {
    try {
        const data = {
            users: {
                model: models.User,
                values: [
                    {
                        firstname: "Jean",
                        lastname: "Dupond",
                        email: "jean.dupond@gmail.com",
                        password: Authentication.hash("azerty"),
                        age: 45,
                        gender: "Homme",
                        deviceid: "WDB-0000A"
                    },
                    {
                        firstname: "Emma",
                        lastname: "Dubois",
                        email: "emma.dubois@gmail.com",
                        password: Authentication.hash("azerty"),
                        age: 25,
                        gender: "Femme",
                        deviceid: "WDB-0000B"
                    }
                ]
            }
        };

        // <<< clear
        await mongoose.connection.dropDatabase();
        // clear >>>

        for (const key of Object.keys(data)) {
            const schema = data[key];

            for (const value of schema.values) {
                const schemaModel = new schema.model(value);
                const data = await schemaModel.save();
                console.log(data);
            }
        }

        process.exit(0);
    } catch (e) {
        console.error(e);
    }
})();
