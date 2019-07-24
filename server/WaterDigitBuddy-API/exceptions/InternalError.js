module.exports = class InternalError extends Error {
  constructor(message) {
    super(message);

    this.message = "internal error: " + message;
    this.code = InternalError.code;
  }

  static get code() {
    return 500;
  }
};
