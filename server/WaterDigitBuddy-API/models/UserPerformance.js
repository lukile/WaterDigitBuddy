const mongoose = require("mongoose");


const UserPerformanceSchema = mongoose.Schema({
    /*_id: { // auto générer grâce au _
      type: mongoose.Schema.ObjectId,
      auto: true,
      index: true // controle d'intégrité, ne peut être supprimer si relation reliée à d'autres tables
    },*/
    datePerformance: {
        type: Date,
        required: true
    },
    speed: {
        type: Number,
        required: true
    },
    lengthType: {
        type: Number,
        enum: [ "25", "50" ], // si autre valeur insérer -> renvoie erreur
        required: false
    },
    startTime: { // recupérer le temps passé à la piscine heure de début et fin
      type: Date,
      required: true
    },
    endTime: {
      type: Date,
      required: true
    },
    distance: { // distance parcourue
      type: Number,
      required: true
    },
    programType: {
        type: mongoose.Schema.ObjectId,
        ref: "SwimmingProgram",
        required: false
    },
    user: {
        type: mongoose.Schema.ObjectId,
        ref: "User"
    },
    deviceid: {
      type: String,
      required: true
    }
});


module.exports = mongoose.model("UserPerformance", UserPerformanceSchema);
