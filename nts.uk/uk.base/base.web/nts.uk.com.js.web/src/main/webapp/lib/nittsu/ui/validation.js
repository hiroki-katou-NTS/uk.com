var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var validation;
            (function (validation) {
                var NoValidator = (function () {
                    function NoValidator() {
                    }
                    NoValidator.prototype.validate = function (inputText, option) {
                        var result = new ValidationResult();
                        result.isValid = true;
                        result.parsedValue = inputText;
                        return result;
                    };
                    return NoValidator;
                }());
                validation.NoValidator = NoValidator;
                var ValidationResult = (function () {
                    function ValidationResult() {
                        this.errorMessage = 'error message';
                    }
                    ValidationResult.prototype.fail = function (errorMessage) {
                        this.errorMessage = errorMessage;
                        this.isValid = false;
                    };
                    ValidationResult.prototype.success = function (parsedValue) {
                        this.parsedValue = parsedValue;
                        this.isValid = true;
                    };
                    return ValidationResult;
                }());
                validation.ValidationResult = ValidationResult;
                var StringValidator = (function () {
                    function StringValidator(name, primitiveValueName, option) {
                        this.name = name;
                        this.constraint = getConstraint(primitiveValueName);
                        this.charType = uk.text.getCharType(primitiveValueName);
                        this.required = option.required;
                    }
                    StringValidator.prototype.validate = function (inputText, option) {
                        var result = new ValidationResult();
                        if (this.required !== undefined && this.required !== false) {
                            if (uk.util.isNullOrEmpty(inputText)) {
                                result.fail(nts.uk.resource.getMessage('FND_E_REQ_INPUT', [this.name]));
                                return result;
                            }
                        }
                        var validateResult;
                        if (this.charType !== null && this.charType !== undefined) {
                            if (this.charType.viewName === '半角数字') {
                                inputText = uk.text.toOneByteAlphaNumberic(inputText);
                            }
                            else if (this.charType.viewName === '半角英数字') {
                                inputText = uk.text.toOneByteAlphaNumberic(uk.text.toUpperCase(inputText));
                            }
                            else if (this.charType.viewName === 'カタカナ') {
                                inputText = uk.text.oneByteKatakanaToTwoByte(inputText);
                            }
                            else if (this.charType.viewName === 'カナ') {
                                inputText = uk.text.hiraganaToKatakana(uk.text.oneByteKatakanaToTwoByte(inputText));
                            }
                            validateResult = this.charType.validate(inputText);
                            if (!validateResult.isValid) {
                                result.fail(nts.uk.resource.getMessage(validateResult.errorMessage, [this.name, !uk.util.isNullOrUndefined(this.constraint)
                                        ? (!uk.util.isNullOrUndefined(this.constraint.maxLength)
                                            ? this.constraint.maxLength : 9999) : 9999]));
                                return result;
                            }
                        }
                        if (this.constraint !== undefined && this.constraint !== null) {
                            if (this.constraint.maxLength !== undefined && uk.text.countHalf(inputText) > this.constraint.maxLength) {
                                result.fail(nts.uk.resource.getMessage(validateResult.errorMessage, [this.name, this.constraint.maxLength]));
                                return result;
                            }
                            if (!uk.util.isNullOrUndefined(option) && option.isCheckExpression === true) {
                                if (!uk.text.isNullOrEmpty(this.constraint.stringExpression) && !this.constraint.stringExpression.test(inputText)) {
                                    result.fail('This field is not valid with pattern!');
                                    return result;
                                }
                            }
                        }
                        result.success(inputText);
                        return result;
                    };
                    return StringValidator;
                }());
                validation.StringValidator = StringValidator;
                var NumberValidator = (function () {
                    function NumberValidator(name, primitiveValueName, option) {
                        this.name = name;
                        this.constraint = getConstraint(primitiveValueName);
                        this.option = option;
                    }
                    NumberValidator.prototype.validate = function (inputText) {
                        var result = new ValidationResult();
                        var isDecimalNumber = false;
                        if (this.option !== undefined) {
                            if (nts.uk.util.isNullOrUndefined(inputText) || inputText.trim().length <= 0) {
                                if (this.option['required'] === true && nts.uk.util.isNullOrEmpty(this.option['defaultValue'])) {
                                    result.fail(nts.uk.resource.getMessage('FND_E_REQ_INPUT', [this.name]));
                                    return result;
                                }
                                else {
                                    result.success(this.option['defaultValue']);
                                    return result;
                                }
                            }
                            isDecimalNumber = (this.option.decimallength > 0);
                            inputText = uk.text.replaceAll(inputText.toString(), this.option.groupseperator, '');
                        }
                        var message = {};
                        var validateFail = false, max = 99999999, min = 0, mantissaMaxLength;
                        if (this.constraint.valueType === "HalfInt") {
                            if (!uk.ntsNumber.isHalfInt(inputText, message))
                                validateFail = true;
                        }
                        else if (!uk.ntsNumber.isNumber(inputText, isDecimalNumber, undefined, message)) {
                            validateFail = true;
                        }
                        var value = isDecimalNumber ?
                            uk.ntsNumber.getDecimal(inputText, this.option.decimallength) : parseInt(inputText);
                        if (!uk.util.isNullOrUndefined(this.constraint)) {
                            if (!uk.util.isNullOrUndefined(this.constraint.max)) {
                                max = this.constraint.max;
                                if (value > this.constraint.max)
                                    validateFail = true;
                            }
                            if (!uk.util.isNullOrUndefined(this.constraint.min)) {
                                min = this.constraint.min;
                                if (value < this.constraint.min)
                                    validateFail = true;
                            }
                            if (!uk.util.isNullOrUndefined(this.constraint.mantissaMaxLength)) {
                                mantissaMaxLength = this.constraint.mantissaMaxLength;
                                var parts = String(value).split(".");
                                if (parts[1] !== undefined && parts[1].length > mantissaMaxLength)
                                    validateFail = true;
                            }
                        }
                        if (validateFail) {
                            result.fail(nts.uk.resource.getMessage(message.id, [this.name, min, max, mantissaMaxLength]));
                        }
                        else {
                            result.success(inputText === "0" ? inputText : uk.text.removeFromStart(inputText, "0"));
                        }
                        return result;
                    };
                    return NumberValidator;
                }());
                validation.NumberValidator = NumberValidator;
                var TimeValidator = (function () {
                    function TimeValidator(name, primitiveValueName, option) {
                        this.name = name;
                        this.constraint = getConstraint(primitiveValueName);
                        this.outputFormat = (option && option.outputFormat) ? option.outputFormat : "";
                        this.required = (option && option.required) ? option.required : false;
                        this.valueType = (option && option.valueType) ? option.valueType : "string";
                        this.mode = (option && option.mode) ? option.mode : "";
                    }
                    TimeValidator.prototype.validate = function (inputText) {
                        var result = new ValidationResult();
                        if (uk.util.isNullOrEmpty(inputText)) {
                            if (this.required === true) {
                                result.fail(nts.uk.resource.getMessage('FND_E_REQ_INPUT', [this.name]));
                                return result;
                            }
                            else {
                                result.success("");
                                return result;
                            }
                        }
                        var maxStr, minStr;
                        if (this.mode === "time") {
                            var timeParse = uk.time.parseTime(inputText, false);
                            if (timeParse.success) {
                                result.success(timeParse.toValue());
                            }
                            else {
                                result.fail();
                            }
                            if (!uk.util.isNullOrUndefined(this.constraint)) {
                                if (!uk.util.isNullOrUndefined(this.constraint.max)) {
                                    maxStr = this.constraint.max;
                                    var max = uk.time.parseTime(this.constraint.max);
                                    if (timeParse.success && (max.hours < timeParse.hours
                                        || (max.hours === timeParse.hours && max.minutes < timeParse.minutes))) {
                                        result.fail();
                                    }
                                }
                                if (!uk.util.isNullOrUndefined(this.constraint.min)) {
                                    minStr = this.constraint.min;
                                    var min = uk.time.parseTime(this.constraint.min);
                                    if (timeParse.success && (min.hours > timeParse.hours
                                        || (min.hours === timeParse.hours && min.minutes > timeParse.minutes))) {
                                        result.fail();
                                    }
                                }
                            }
                            if (!result.isValid && this.constraint.valueType === "Time") {
                                result.fail(nts.uk.resource.getMessage("FND_E_TIME", [this.name, minStr, maxStr]));
                            }
                            return result;
                        }
                        var parseResult = uk.time.parseMoment(inputText, this.outputFormat);
                        if (parseResult.success) {
                            if (this.valueType === "string")
                                result.success(parseResult.format());
                            else if (this.valueType === "number") {
                                result.success(parseResult.toNumber(this.outputFormat));
                            }
                            else if (this.valueType === "date") {
                                result.success(parseResult.toValue().toDate());
                            }
                            else if (this.valueType === "moment") {
                                result.success(parseResult.toValue());
                            }
                            else {
                                result.success(parseResult.format());
                            }
                        }
                        else {
                            result.fail(parseResult.getMsg());
                        }
                        if (this.outputFormat === "time") {
                            if (!uk.util.isNullOrUndefined(this.constraint)) {
                                var inputMoment = parseResult.toValue();
                                if (!uk.util.isNullOrUndefined(this.constraint.max)) {
                                    maxStr = this.constraint.max;
                                    var maxMoment = moment.duration(maxStr);
                                    if (parseResult.success && (maxMoment.hours() < inputMoment.hours()
                                        || (maxMoment.hours() === inputMoment.hours() && maxMoment.minutes() < inputMoment.minutes()))) {
                                        result.fail();
                                    }
                                }
                                if (!uk.util.isNullOrUndefined(this.constraint.min)) {
                                    minStr = this.constraint.min;
                                    var minMoment = moment.duration(minStr);
                                    if (parseResult.success && (minMoment.hours() > inputMoment.hours()
                                        || (minMoment.hours() === inputMoment.hours() && minMoment.minutes() > inputMoment.minutes()))) {
                                        result.fail();
                                    }
                                }
                            }
                            if (!result.isValid && this.constraint.valueType === "Clock") {
                                result.fail(nts.uk.resource.getMessage("FND_E_CLOCK", [this.name, minStr, maxStr]));
                            }
                        }
                        return result;
                    };
                    return TimeValidator;
                }());
                validation.TimeValidator = TimeValidator;
                function getConstraint(primitiveValueName) {
                    var constraint = __viewContext.primitiveValueConstraints[primitiveValueName];
                    if (constraint === undefined)
                        return null;
                    else
                        return __viewContext.primitiveValueConstraints[primitiveValueName];
                }
                validation.getConstraint = getConstraint;
            })(validation = ui.validation || (ui.validation = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=validation.js.map