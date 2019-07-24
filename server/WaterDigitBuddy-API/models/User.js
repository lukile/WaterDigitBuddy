const mongoose = require("mongoose");

const UserSchema = mongoose.Schema({
    /*_id: { // auto générer grâce au _
      type: mongoose.Schema.ObjectId,
      auto: true,
      index: true // controle d'intégrité, ne peut être supprimer si relation reliée à d'autres tables
    },*/
    firstname: {
        type: String,
        required: true
    },
    lastname: {
        type: String,
        required: true
    },
    email: {
        type: String,
        required: true,
        unique: true
    },
    password: {
        type: String,
        required: true
    },
    age: {
        type: Date,
        required: true
    },
    gender: {
        type: String,
        enum: ["Homme", "Femme"],
        required: true
    },
    deviceid: {
        type: String,
        required: true
    }/*,
    historiesPerformance: [{
        type: mongoose.Schema.ObjectId,
        ref: "UserPerformance"
    }]*/
});


module.exports = mongoose.model("User", UserSchema);
