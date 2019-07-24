module.exports = class Conflict extends Error {
  constructor(message) {
    super(message);

    this.message = "conflict: " + message;
    this.code = Conflict.code;
  }

  static get code() {
    return 409;
  }
};
