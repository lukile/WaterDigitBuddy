const Exception = require("../exceptions");
const UserModel = require("../models").User;
const Authencation = require("./Authentication");


module.exports = class User {
    constructor(id, firstname, lastname, email, password, age, gender, deviceid) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.deviceid = deviceid;
    }

    static get Instance() {
        return new User();
    }

    find(criteria) { // == WHERE en sql
        return new Promise(async (resolve, reject) => {
            try {
                const user = await UserModel.findOne(criteria);
                resolve(user);
            } catch (e) { reject(e); }
        });
    }

    static findAll() { // * en sql sans conditions
        return new Promise(async (resolve, reject) => {
            try {
                const users = await UserModel.find();
                resolve(users);
            } catch (e) { reject(e); }
        });
    }

    findUserByDeviceId(deviceid) {
        return new Promise(async (resolve, reject) => {
            try {
                const user = await UserModel.findOne(deviceid);
                //console.log(user);
                resolve(user);
            } catch (e) { reject(e); }
        });
    }

    static sign(email, password) {
        return new Promise(async (resolve, reject) => {
            try {
                const user = await User.Instance.find({
                    email,
                    password: Authencation.hash(password)
                });

                if (user) {
                    return resolve(user);
                }

                throw new Exception.NotFound("no users with this email have been registered")
            } catch (e) { reject(e); }
        });
    }

    save() {
        const self = this;

        return new Promise(async (resolve, reject) => {
            try {
                const userModel = new UserModel({
                    firstname: self.firstname,
                    lastname: self.lastname,
                    email: self.email,
                    password: Authencation.hash(self.password),
                    age: self.age,
                    gender: self.gender,
                    deviceid: self.deviceid
                });

                const user = await userModel.save();
                resolve(user);
            } catch (e) { reject(e); }
        });
    }
};
