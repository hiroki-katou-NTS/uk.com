var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
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
                function isInteger(value, option) {
                    if (option !== undefined && option.groupseperator() !== undefined) {
                        value = this.isInteger(value) ? value : value.toString().replace(option.groupseperator(), '');
                    }
                    return !isNaN(value) && parseInt(value) == value && !isNaN(parseInt(value, 10));
                }
                function isDecimal(value, option) {
                    if (option !== undefined && option.groupseperator() !== undefined) {
                        value = this.isDecimal(value) ? value : value.toString().replace(option.groupseperator(), '');
                    }
                    return !isNaN(value) && parseFloat(value) == value && !isNaN(parseFloat(value));
                }
                function isNumber(value, isDecimalValue, option) {
                    if (isDecimalValue) {
                        return isDecimal(value, option);
                    }
                    else {
                        return isInteger(value, option);
                    }
                }
                validation.isNumber = isNumber;
                var ParseResult = (function () {
                    function ParseResult(success) {
                        this.success = success;
                    }
                    return ParseResult;
                }());
                var ResultParseTime = (function (_super) {
                    __extends(ResultParseTime, _super);
                    function ResultParseTime(success, minus, hours, minutes) {
                        _super.call(this, success);
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
                    ResultParseTime.prototype.toValue = function () {
                        return (this.minus ? -1 : 1) * (this.hours * 60 + this.minutes);
                    };
                    return ResultParseTime;
                }(ParseResult));
                validation.ResultParseTime = ResultParseTime;
                function parseTime(time, isMinutes) {
                    if (isMinutes) {
                        var hoursX = Math.floor(time / 60);
                        time = hoursX + uk.text.padLeft((time - hoursX * 60).toString(), '0', 2);
                    }
                    if (!(time instanceof String)) {
                        time = time.toString();
                    }
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
                    return ResultParseTime.succeeded(minusNumber, parseInt(hours), parseInt(minutes));
                }
                validation.parseTime = parseTime;
                var ResultParseYearMonth = (function (_super) {
                    __extends(ResultParseYearMonth, _super);
                    function ResultParseYearMonth(success, year, month) {
                        _super.call(this, success);
                        this.year = year;
                        this.month = month;
                    }
                    ResultParseYearMonth.succeeded = function (year, month) {
                        return new ResultParseYearMonth(true, year, month);
                    };
                    ResultParseYearMonth.failed = function () {
                        return new ResultParseYearMonth(false);
                    };
                    ResultParseYearMonth.prototype.format = function () {
                        return this.year + '/' + uk.text.padLeft(String(this.month), '0', 2);
                    };
                    ResultParseYearMonth.prototype.toValue = function () {
                        return (this.year * 100 + this.month);
                    };
                    return ResultParseYearMonth;
                }(ParseResult));
                validation.ResultParseYearMonth = ResultParseYearMonth;
                function parseYearMonth(yearMonth) {
                    if (!(yearMonth instanceof String)) {
                        yearMonth = yearMonth.toString();
                    }
                    var stringLengh = yearMonth.length;
                    if ((stringLengh < 6 || stringLengh > 7) || yearMonth.split('/').length > 2) {
                        return ResultParseYearMonth.failed();
                    }
                    var indexOf = yearMonth.lastIndexOf('/');
                    var year, month;
                    if (indexOf > -1 && indexOf !== 4) {
                        return ResultParseYearMonth.failed();
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
                        return ResultParseYearMonth.failed();
                    }
                    return ResultParseYearMonth.succeeded(parseInt(year), parseInt(month));
                }
                validation.parseYearMonth = parseYearMonth;
            })(validation = ui.validation || (ui.validation = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
