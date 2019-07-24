module.exports = class Unauthorized extends Error {
  constructor(message) {
    super(message);

    this.message = "unauthorized: " + message;
    this.code = Unauthorized.code;
  }

  static get code() {
    return 401;
  }
};
