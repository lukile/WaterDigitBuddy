module.exports = class BadRequest extends Error {
  constructor(message) {
    super(message);

    this.message = "bad request: " + message;
    this.code = BadRequest.code;
  }

  static get code() {
    return 400;
  }
};
