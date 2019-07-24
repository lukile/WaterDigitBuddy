const axios = require("axios");
const Exception = require("../exceptions");
const SwimmingProgramModel = require("../models").SwimmingProgram;

module.exports = class SwimmingProgram {
  constructor(id, name, description) {
      this.id = id;
      this.name = name;
      this.description = description;
  }

  static get Instance() {
      return new SwimmingProgram();
  }

  find(criteria) { // == WHERE en sql
      return new Promise(async (resolve, reject) => {
          try {
              const program = await SwimmingProgramModel.findOne(criteria);
              resolve(program);
          } catch (e) { reject(e); }
      });
  }

  static findAll() { // * en sql sans conditions
      return new Promise(async (resolve, reject) => {
          try {
              const programs = await SwimmingProgramModel.find();
              resolve(programs);
          } catch (e) { reject(e); }
      });
  }

  save() {
    const self = this;

    return new Promise(async (resolve, reject) => {
        try {
            const swimmingProgramModel = new SwimmingProgramModel({
                name: self.name,
                description: self.description
            });

            const program = await swimmingProgramModel.save();
            resolve(program);
        } catch (e) { reject(e); }
    });
  }

};
