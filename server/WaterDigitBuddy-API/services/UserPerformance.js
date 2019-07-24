const axios = require("axios");
const Exception = require("../exceptions");
const UserPerformanceModel = require("../models").UserPerformance;

module.exports = class UserPerformance {
  constructor(id, datePerformance, speed, lengthType, startTime, endTime, distance, programType, user, deviceid) {
      this.id = id;
      this.datePerformance = datePerformance;
      this.speed = speed;
      this.lengthType = lengthType;
      this.startTime = startTime;
      this.endTime = endTime;
      this.distance = distance;
      this.programType = programType;
      this.user = user;
      this.deviceid = deviceid;
  }

  static get Instance() {
      return new UserPerformance();
  }

  find(criteria) { // == WHERE en sql
      return new Promise(async (resolve, reject) => {
          try {
              const userPerformance = await UserPerformanceModel.findOne(criteria);
              resolve(userPerformance);
          } catch (e) { reject(e); }
      });
  }

  static findAll() { // * en sql sans conditions
      return new Promise(async (resolve, reject) => {
          try {
              const performances = await UserPerformanceModel.find();
              resolve(performances);
          } catch (e) { reject(e); }
      });
  }

  save() {
    const self = this;

    return new Promise(async (resolve, reject) => {
        try {
            const userPerformanceModel = new UserPerformanceModel({
                datePerformance: self.datePerformance,
                speed: self.speed,
                lengthType: self.lengthType,
                startTime: self.startTime,
                endTime: self.endTime,
                distance: self.distance,
                programType: self.programType,
                user: self.user,
                deviceid: self.deviceid
            });

            const userPerformance = await userPerformanceModel.save();
            resolve(userPerformance);
        } catch (e) { reject(e); }
    });
  }

};
