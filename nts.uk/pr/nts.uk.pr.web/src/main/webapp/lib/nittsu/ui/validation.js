var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var validation;
            (function (validation) {
                /**
                 * Type of characters
                 */
                var CharType = (function () {
                    function CharType(viewName, width, validator) {
                        this.viewName = viewName;
                        this.width = width;
                        this.validator = validator;
                    }
                    CharType.prototype.validate = function (text) {
                        var result = new ValidationResult();
                        if (this.validator(text)) {
                            result.isValid = true;
                        }
                        else {
                            result.isValid = false;
                        }
                        return result;
                    };
                    CharType.prototype.buildConstraintText = function (maxLength) {
                        return this.viewName + this.getViewLength(maxLength) + '文字';
                    };
                    CharType.prototype.getViewLength = function (length) {
                        return Math.floor(length / (this.width * 2));
                    };
                    return CharType;
                }());
                validation.CharType = CharType;
                var ValidationResult = (function () {
                    function ValidationResult() {
                        this.errorMessage = 'error message';
                    }
                    return ValidationResult;
                }());
                validation.ValidationResult = ValidationResult;
                function getCharType(primitiveValueName) {
                    var constraint = __viewContext.primitiveValueConstraints[primitiveValueName];
                    if (constraint === undefined)
                        return null;
                    if (constraint.charType === undefined)
                        constraint.charType = "Any";
                    var charType = charTypes[constraint.charType];
                    if (charType === undefined) {
                        throw new Error('invalid charTypeName: ' + constraint.charType);
                    }
                    return charType;
                }
                validation.getCharType = getCharType;
                var charTypes = {
                    AnyHalfWidth: new CharType('半角', 0.5, nts.uk.text.allHalf),
                    AlphaNumeric: new CharType('半角英数字', 0.5, nts.uk.text.allHalfAlphanumeric),
                    Alphabet: new CharType('半角英字', 0.5, nts.uk.text.allHalfAlphabet),
                    Numeric: new CharType('半角数字', 0.5, nts.uk.text.allHalfNumeric),
                    Any: new CharType('全角', 1, nts.uk.util.alwaysTrue),
                };
                function isInteger(value) {
                    return !isNaN(value) && parseInt(value) == value && !isNaN(parseInt(value, 10));
                }
                validation.isInteger = isInteger;
                function isDecimal(value) {
                    return !isNaN(value) && parseFloat(value) == value && !isNaN(parseFloat(value));
                }
                validation.isDecimal = isDecimal;
                var ResultParseTime = (function () {
                    function ResultParseTime(success, minus, hours, minutes) {
                        this.success = success;
                        this.minus = minus;
                        this.hours = hours;
                        this.minutes = minutes;
                    }
                    ResultParseTime.succeeded = function (minus, hours, minutes) {
                        return new ResultParseTime(true, minus, hours, minutes);
                    };
                    ResultParseTime.failed = function () {
                        return new ResultParseTime(false);
                    };
                    ResultParseTime.prototype.format = function () {
                        return (this.minus ? '-' : '') + this.hours + ':' + uk.text.padLeft(String(this.minutes), '0', 2);
                    };
                    return ResultParseTime;
                }());
                validation.ResultParseTime = ResultParseTime;
                function parseTime(time) {
                    if (time.length < 2 || time.split(':').length > 2 || time.split('-').length > 2
                        || time.lastIndexOf('-') > 0) {
                        return ResultParseTime.failed();
                    }
                    var minusNumber = time.charAt(0) === '-';
                    if (minusNumber) {
                        time = time.split('-')[1];
                    }
                    var minutes;
                    var hours;
                    if (time.indexOf(':') > -1) {
                        var times = time.split(':');
                        minutes = times[1];
                        hours = times[0];
                    }
                    else {
                        minutes = time.substr(-2, 2);
                        hours = time.substr(0, time.length - 2);
                    }
                    if (!isInteger(minutes) || parseInt(minutes) > 59 || !isInteger(hours)) {
                        return ResultParseTime.failed();
                    }
                    return ResultParseTime.succeeded(minusNumber, Number(hours), Number(minutes));
                }
                validation.parseTime = parseTime;
                function validateYearMonth(yearMonth) {
                    var stringLengh = yearMonth.length;
                    if ((stringLengh < 6 || stringLengh > 7) || yearMonth.split('/').length > 2) {
                        throw new Error('invalid year month value: ' + yearMonth);
                    }
                    var indexOf = yearMonth.lastIndexOf('/');
                    var year, month;
                    if (indexOf > -1 && indexOf !== 4) {
                        throw new Error('invalid year month value: ' + yearMonth);
                    }
                    else if (indexOf === 4) {
                        year = yearMonth.split('/')[0];
                        month = yearMonth.split('/')[1];
                    }
                    else if (indexOf <= -1) {
                        year = yearMonth.substr(0, stringLengh - 2);
                        month = yearMonth.substr(-2, 2);
                    }
                    if (!isInteger(month) || parseInt(month) > 12 || !isInteger(year) || parseInt(year) < 1900) {
                        throw new Error('invalid year month value: ' + yearMonth);
                    }
                    return year + "/" + (month.length === 1 ? '0' + month : month);
                }
                validation.validateYearMonth = validateYearMonth;
            })(validation = ui.validation || (ui.validation = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
