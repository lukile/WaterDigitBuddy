const mongoose = require("mongoose");


const SwimmingProgramSchema = mongoose.Schema({
    name: {
        type: String,
        enum: [ "SummerBody", "SpringBody", "WinterBody" ],
        required: true,
        unique: true
    },
    description: {
      type: String,
      required: true
    }
});


module.exports = mongoose.model("SwimmingProgram", SwimmingProgramSchema);
