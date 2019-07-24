module.exports = class NotFound extends Error {
  constructor(message) {
    super(message);

    this.message = "not found: " + message;
    this.code = NotFound.code;
  }

  static get code() {
    return 404;
  }
};
