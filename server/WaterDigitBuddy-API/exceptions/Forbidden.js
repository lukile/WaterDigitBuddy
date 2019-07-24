module.exports = class Forbidden extends Error {
  constructor(message) {
    super(message);

    this.message = "forbidden: " + message;
    this.code = Forbidden.code;
  }

  static get code() {
    return 403;
  }
};
