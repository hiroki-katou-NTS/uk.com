var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var validation;
            (function (validation) {
                var util = nts.uk.util;
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
                    ValidationResult.prototype.fail = function (errorMessage, errorCode) {
                        this.errorCode = errorCode;
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
                var DepartmentCodeValidator = (function () {
                    function DepartmentCodeValidator(name, primitiveValueName, option) {
                        this.name = name;
                        this.constraint = getConstraint(primitiveValueName);
                        this.charType = uk.text.getCharType(primitiveValueName);
                        this.required = option.required;
                    }
                    DepartmentCodeValidator.prototype.validate = function (inputText, option) {
                        var result = new ValidationResult();
                        if (util.isNullOrEmpty(inputText)) {
                            if (this.required !== undefined && this.required !== false) {
                                result.fail(nts.uk.resource.getMessage('FND_E_REQ_INPUT', [this.name]), 'FND_E_REQ_INPUT');
                                return result;
                            }
                            result.success(inputText);
                            return result;
                        }
                        result = checkCharType(inputText, this.charType);
                        if (!result.isValid)
                            return result;
                        if (this.constraint !== undefined && this.constraint !== null) {
                            if (this.constraint.maxLength !== undefined && uk.text.countHalf(inputText) > this.constraint.maxLength) {
                                var maxLength = this.constraint.maxLength;
                                result.fail(nts.uk.resource.getMessage(result.errorMessage, [this.name, maxLength]), result.errorCode);
                                return result;
                            }
                            if (!util.isNullOrUndefined(option) && option.isCheckExpression === true) {
                                if (!uk.text.isNullOrEmpty(this.constraint.stringExpression) && !this.constraint.stringExpression.test(inputText)) {
                                    result.fail('This field is not valid with pattern!', '');
                                    return result;
                                }
                            }
                        }
                        result.success(inputText);
                        return result;
                    };
                    return DepartmentCodeValidator;
                }());
                validation.DepartmentCodeValidator = DepartmentCodeValidator;
                function checkCharType(inputText, charType) {
                    var result = new ValidationResult();
                    var validateResult;
                    if (!util.isNullOrUndefined(charType)) {
                        inputText = autoConvertText(inputText, charType);
                        validateResult = charType.validate(inputText);
                        if (!validateResult.isValid) {
                            result.fail(nts.uk.resource.getMessage(validateResult.errorMessage, [this.name, !util.isNullOrUndefined(this.constraint)
                                    ? (!util.isNullOrUndefined(this.constraint.maxLength)
                                        ? this.constraint.maxLength : 9999) : 9999]), validateResult.errorCode);
                            return result;
                        }
                        return validateResult;
                    }
                    result.success(inputText);
                    return result;
                }
                function autoConvertText(inputText, charType) {
                    if (charType.viewName === '半角英数字') {
                        inputText = uk.text.toUpperCase(inputText);
                    }
                    else if (charType.viewName === 'カタカナ') {
                        inputText = uk.text.oneByteKatakanaToTwoByte(inputText);
                    }
                    else if (charType.viewName === 'カナ') {
                        inputText = uk.text.hiraganaToKatakana(uk.text.oneByteKatakanaToTwoByte(inputText));
                    }
                    return inputText;
                }
                var WorkplaceCodeValidator = (function () {
                    function WorkplaceCodeValidator(name, primitiveValueName, option) {
                        this.name = name;
                        this.constraint = getConstraint(primitiveValueName);
                        this.charType = uk.text.getCharType(primitiveValueName);
                        this.required = option.required;
                    }
                    WorkplaceCodeValidator.prototype.validate = function (inputText, option) {
                        var result = new ValidationResult();
                        if (util.isNullOrEmpty(inputText)) {
                            if (this.required !== undefined && this.required !== false) {
                                result.fail(nts.uk.resource.getMessage('FND_E_REQ_INPUT', [this.name]), 'FND_E_REQ_INPUT');
                                return result;
                            }
                            result.success(inputText);
                            return result;
                        }
                        result = checkCharType(inputText, this.charType);
                        if (!result.isValid)
                            return result;
                        if (this.constraint !== undefined && this.constraint !== null) {
                            if (this.constraint.maxLength !== undefined && uk.text.countHalf(inputText) > this.constraint.maxLength) {
                                var maxLength = this.constraint.maxLength;
                                result.fail(nts.uk.resource.getMessage(result.errorMessage, [this.name, maxLength]), result.errorCode);
                                return result;
                            }
                            if (!util.isNullOrUndefined(option) && option.isCheckExpression === true) {
                                if (!uk.text.isNullOrEmpty(this.constraint.stringExpression) && !this.constraint.stringExpression.test(inputText)) {
                                    result.fail('This field is not valid with pattern!', '');
                                    return result;
                                }
                            }
                        }
                        return result;
                    };
                    return WorkplaceCodeValidator;
                }());
                validation.WorkplaceCodeValidator = WorkplaceCodeValidator;
                var PostCodeValidator = (function () {
                    function PostCodeValidator(name, primitiveValueName, option) {
                        this.name = name;
                        this.constraint = getConstraint(primitiveValueName);
                        this.charType = uk.text.getCharType(primitiveValueName);
                        this.required = option.required;
                    }
                    PostCodeValidator.prototype.validate = function (inputText, option) {
                        var result = new ValidationResult();
                        if (util.isNullOrEmpty(inputText)) {
                            if (this.required !== undefined && this.required !== false) {
                                result.fail(nts.uk.resource.getMessage('FND_E_REQ_INPUT', [this.name]), 'FND_E_REQ_INPUT');
                                return result;
                            }
                            result.success(inputText);
                            return result;
                        }
                        result = checkCharType(inputText, this.charType);
                        if (!result.isValid)
                            return result;
                        if (this.constraint !== undefined && this.constraint !== null) {
                            if (this.constraint.maxLength !== undefined && uk.text.countHalf(inputText) > this.constraint.maxLength) {
                                var maxLength = this.constraint.maxLength;
                                result.fail(nts.uk.resource.getMessage(result.errorMessage, [this.name, maxLength]), result.errorCode);
                                return result;
                            }
                            if (!util.isNullOrUndefined(option) && option.isCheckExpression === true) {
                                if (!uk.text.isNullOrEmpty(this.constraint.stringExpression) && !this.constraint.stringExpression.test(inputText)) {
                                    result.fail('This field is not valid with pattern!', '');
                                    return result;
                                }
                            }
                        }
                        result.success(inputText);
                        return result;
                    };
                    return PostCodeValidator;
                }());
                validation.PostCodeValidator = PostCodeValidator;
                var PunchCardNoValidator = (function () {
                    function PunchCardNoValidator(name, primitiveValueName, option) {
                        this.name = name;
                        this.constraint = getConstraint(primitiveValueName);
                        this.charType = uk.text.getCharType(primitiveValueName);
                        this.required = option.required;
                    }
                    PunchCardNoValidator.prototype.validate = function (inputText, option) {
                        var result = new ValidationResult();
                        if (util.isNullOrEmpty(inputText)) {
                            if (this.required !== undefined && this.required !== false) {
                                result.fail(nts.uk.resource.getMessage('FND_E_REQ_INPUT', [this.name]), 'FND_E_REQ_INPUT');
                                return result;
                            }
                            result.success(inputText);
                            return result;
                        }
                        result = checkCharType(inputText, this.charType);
                        if (!result.isValid)
                            return result;
                        if (this.constraint !== undefined && this.constraint !== null) {
                            if (this.constraint.maxLength !== undefined && uk.text.countHalf(inputText) > this.constraint.maxLength) {
                                var maxLength = this.constraint.maxLength;
                                result.fail(nts.uk.resource.getMessage(result.errorMessage, [this.name, maxLength]), result.errorCode);
                                return result;
                            }
                            if (!util.isNullOrUndefined(option) && option.isCheckExpression === true) {
                                if (!uk.text.isNullOrEmpty(this.constraint.stringExpression) && !this.constraint.stringExpression.test(inputText)) {
                                    result.fail(nts.uk.resource.getMessage('Msg_1285', [this.name]), 'Msg_1285');
                                    return result;
                                }
                            }
                        }
                        result.success(inputText);
                        return result;
                    };
                    return PunchCardNoValidator;
                }());
                validation.PunchCardNoValidator = PunchCardNoValidator;
                var EmployeeCodeValidator = (function () {
                    function EmployeeCodeValidator(name, options) {
                        var self = this;
                        this.name = name;
                        this.constraint = getConstraint("EmployeeCode");
                        this.charType = uk.text.getCharTypeByType("AlphaNumeric");
                        this.options = options;
                    }
                    EmployeeCodeValidator.prototype.validate = function (inputText) {
                        var self = this;
                        var result = new ValidationResult();
                        if (util.isNullOrEmpty(inputText)) {
                            if (self.options.required) {
                                result.fail(nts.uk.resource.getMessage('FND_E_REQ_INPUT', [this.name]), 'FND_E_REQ_INPUT');
                                return result;
                            }
                            result.success(inputText);
                            return result;
                        }
                        result = checkCharType.call(self, inputText.trim(), self.charType);
                        if (!result.isValid)
                            return result;
                        if (self.constraint && !util.isNullOrUndefined(self.constraint.maxLength)
                            && self.constraint.maxLength < uk.text.countHalf(inputText)) {
                            result.fail(nts.uk.resource.getMessage(result.errorMessage, [self.name, self.constraint.maxLength]), result.errorCode);
                            return result;
                        }
                        result.success(uk.text.toUpperCase(inputText));
                        return result;
                    };
                    return EmployeeCodeValidator;
                }());
                validation.EmployeeCodeValidator = EmployeeCodeValidator;
                var StringValidator = (function () {
                    function StringValidator(name, primitiveValueName, option) {
                        this.name = name;
                        this.constraint = getConstraint(primitiveValueName);
                        if (nts.uk.util.isNullOrUndefined(this.constraint)) {
                            this.constraint = {};
                        }
                        this.charType = uk.text.getCharType(primitiveValueName);
                        this.required = (!nts.uk.util.isNullOrUndefined(option.required) && option.required) || this.constraint.required;
                    }
                    StringValidator.prototype.validate = function (inputText, option) {
                        var result = new ValidationResult();
                        if (util.isNullOrEmpty(inputText)) {
                            if (this.required !== undefined && this.required !== false) {
                                result.fail(nts.uk.resource.getMessage('FND_E_REQ_INPUT', [this.name]), 'FND_E_REQ_INPUT');
                                return result;
                            }
                            result.success(inputText);
                            return result;
                        }
                        var validateResult;
                        if (!util.isNullOrUndefined(this.charType)) {
                            if (this.charType.viewName === '半角英数字') {
                                inputText = uk.text.toUpperCase(inputText);
                            }
                            else if (this.charType.viewName === 'カタカナ') {
                                inputText = uk.text.oneByteKatakanaToTwoByte(inputText);
                            }
                            else if (this.charType.viewName === 'カナ') {
                                inputText = uk.text.hiraganaToKatakana(uk.text.oneByteKatakanaToTwoByte(inputText));
                            }
                            validateResult = this.charType.validate(inputText);
                            if (!validateResult.isValid) {
                                result.fail(nts.uk.resource.getMessage(validateResult.errorMessage, [this.name, (!util.isNullOrUndefined(this.constraint.maxLength)
                                        ? this.charType.getViewLength(this.constraint.maxLength) : 9999)]), validateResult.errorCode);
                                return result;
                            }
                        }
                        else {
                            validateResult = result;
                        }
                        if (this.constraint.maxLength !== undefined && uk.text.countHalf(inputText) > this.constraint.maxLength) {
                            var maxLength = this.constraint.maxLength;
                            if (this.constraint.charType == "Any" || this.constraint.charType === "Kana")
                                maxLength = nts.uk.text.getCharTypeByType("Any").getViewLength(maxLength);
                            result.fail(nts.uk.resource.getMessage(validateResult.errorMessage, [this.name, maxLength]), validateResult.errorCode);
                            return result;
                        }
                        if (!util.isNullOrUndefined(option) && option.isCheckExpression === true) {
                            if (!uk.text.isNullOrEmpty(this.constraint.stringExpression) && !this.constraint.stringExpression.test(inputText)) {
                                result.fail('This field is not valid with pattern!', '');
                                return result;
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
                        if (util.isNullOrUndefined(this.constraint)) {
                            this.constraint = {};
                        }
                        this.option = option;
                    }
                    NumberValidator.prototype.validate = function (inputText) {
                        var result = new ValidationResult();
                        var isDecimalNumber = false;
                        if (this.option !== undefined) {
                            if (nts.uk.util.isNullOrUndefined(inputText) || inputText.trim().length <= 0) {
                                if ((this.option['required'] === true || this.constraint["required"] === true) && nts.uk.util.isNullOrEmpty(this.option['defaultValue'])) {
                                    result.fail(nts.uk.resource.getMessage('FND_E_REQ_INPUT', [this.name]), 'FND_E_REQ_INPUT');
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
                        inputText = inputText.trim();
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
                        if (!util.isNullOrUndefined(this.constraint.max)) {
                            max = this.constraint.max;
                            if (value > this.constraint.max)
                                validateFail = true;
                        }
                        if (!util.isNullOrUndefined(this.constraint.min)) {
                            min = this.constraint.min;
                            if (value < this.constraint.min)
                                validateFail = true;
                        }
                        if (!util.isNullOrUndefined(this.constraint.mantissaMaxLength)) {
                            mantissaMaxLength = this.constraint.mantissaMaxLength;
                            var parts = inputText.split(".");
                            if (parts[1] !== undefined && parts[1].length > mantissaMaxLength)
                                validateFail = true;
                        }
                        if (!(/^-?\d*(\.\d+)?$/).test(inputText)) {
                            validateFail = true;
                        }
                        if (validateFail) {
                            result.fail(nts.uk.resource.getMessage(message.id, [this.name, min, max, mantissaMaxLength]), message.id);
                        }
                        else {
                            var formated = value.toString() === "0" ? inputText : uk.text.removeFromStart(inputText, "0");
                            if (formated.indexOf(".") >= 0) {
                                formated = uk.text.removeFromEnd(formated, "0");
                            }
                            if (formated.charAt(0) === ".") {
                                formated = "0" + formated;
                            }
                            if (formated.charAt(formated.length - 1) === ".") {
                                formated = formated.substr(0, formated.length - 1);
                            }
                            result.success(formated);
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
                        if (nts.uk.util.isNullOrUndefined(this.constraint)) {
                            this.constraint = {};
                            if (option && option.min && option.max) {
                                this.constraint.min = option.min;
                                this.constraint.max = option.max;
                            }
                        }
                        this.outputFormat = (option && option.outputFormat) ? option.outputFormat : "";
                        this.required = ((option && option.required) ? option.required : false) || this.constraint.required === true;
                        this.valueType = (option && option.valueType) ? option.valueType : "string";
                        this.mode = (option && option.mode) ? option.mode : "";
                        this.acceptJapaneseCalendar = (option && option.acceptJapaneseCalendar) ? option.acceptJapaneseCalendar : true;
                        this.defaultValue = (option && option.defaultValue) ? option.defaultValue : "";
                    }
                    TimeValidator.prototype.validate = function (inputText) {
                        var result = new ValidationResult();
                        if (util.isNullOrEmpty(inputText) && !util.isNullOrEmpty(this.defaultValue)) {
                            inputText = this.defaultValue;
                        }
                        else if (util.isNullOrEmpty(inputText)) {
                            if (this.required === true) {
                                result.fail(nts.uk.resource.getMessage('FND_E_REQ_INPUT', [this.name]), 'FND_E_REQ_INPUT');
                                return result;
                            }
                            else {
                                result.success(null);
                                return result;
                            }
                        }
                        if (this.acceptJapaneseCalendar) {
                            inputText = uk.time.convertJapaneseDateToGlobal(inputText);
                        }
                        var maxStr, minStr;
                        if (this.mode === "time") {
                            var timeParse;
                            if (this.outputFormat.indexOf("s") >= 0) {
                                timeParse = uk.time.secondsBased.duration.parseString(inputText);
                            }
                            else {
                                timeParse = uk.time.minutesBased.duration.parseString(inputText);
                            }
                            if (timeParse.success) {
                                result.success(timeParse.toValue());
                            }
                            else {
                                var msgId = timeParse.getMsg();
                                var msg = nts.uk.resource.getMessage(msgId, [this.name, this.constraint.min, this.constraint.max]);
                                result.fail(msg, msgId);
                                return result;
                            }
                            if (!util.isNullOrUndefined(this.constraint.max)) {
                                maxStr = this.constraint.max;
                                var max = uk.time.parseTime(this.constraint.max);
                                if (timeParse.success && (max.toValue() < timeParse.toValue())) {
                                    var msg = nts.uk.resource.getMessage("FND_E_TIME", [this.name, this.constraint.min, this.constraint.max]);
                                    result.fail(msg, "FND_E_TIME");
                                    return result;
                                }
                            }
                            if (!util.isNullOrUndefined(this.constraint.min)) {
                                minStr = this.constraint.min;
                                var min = uk.time.parseTime(this.constraint.min);
                                if (timeParse.success && (min.toValue() > timeParse.toValue())) {
                                    var msg = nts.uk.resource.getMessage("FND_E_TIME", [this.name, this.constraint.min, this.constraint.max]);
                                    result.fail(msg, "FND_E_TIME");
                                    return result;
                                }
                            }
                            if (!result.isValid && this.constraint.valueType === "Time") {
                                result.fail(nts.uk.resource.getMessage("FND_E_TIME", [this.name, minStr, maxStr]), "FND_E_TIME");
                            }
                            return result;
                        }
                        var isMinuteTime = this.outputFormat === "time" ? inputText.charAt(0) === "-" : false;
                        if (isMinuteTime) {
                            inputText = inputText.substring(1, inputText.length);
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
                            result.fail(parseResult.getEmsg(this.name), parseResult.getMsgID());
                            return result;
                        }
                        if (this.outputFormat === "time") {
                            var inputMoment = parseResult.toNumber(this.outputFormat) * (isMinuteTime ? -1 : 1);
                            if (!util.isNullOrUndefined(this.constraint.max)) {
                                maxStr = this.constraint.max;
                                var maxMoment = moment.duration(maxStr);
                                if (parseResult.success && (maxMoment.hours() * 60 + maxMoment.minutes()) < inputMoment) {
                                    result.fail(nts.uk.resource.getMessage("FND_E_CLOCK", [this.name, minStr, maxStr]), "FND_E_CLOCK");
                                    return result;
                                }
                            }
                            if (!util.isNullOrUndefined(this.constraint.min)) {
                                minStr = this.constraint.min;
                                var minMoment = moment.duration(minStr);
                                if (parseResult.success && (minMoment.hours() * 60 + minMoment.minutes()) > inputMoment) {
                                    result.fail(nts.uk.resource.getMessage("FND_E_CLOCK", [this.name, minStr, maxStr]), "FND_E_CLOCK");
                                    return result;
                                }
                            }
                            if (!result.isValid && this.constraint.valueType === "Clock") {
                                result.fail(nts.uk.resource.getMessage("FND_E_CLOCK", [this.name, minStr, maxStr]), "FND_E_CLOCK");
                            }
                        }
                        return result;
                    };
                    return TimeValidator;
                }());
                validation.TimeValidator = TimeValidator;
                var TimeWithDayValidator = (function () {
                    function TimeWithDayValidator(name, primitiveValueName, option) {
                        this.name = name;
                        this.constraint = getConstraint(primitiveValueName);
                        if (nts.uk.util.isNullOrUndefined(this.constraint)) {
                            this.constraint = {};
                        }
                        this.required = (option && option.required) ? option.required : false;
                    }
                    TimeWithDayValidator.prototype.validate = function (inputText) {
                        var result = new ValidationResult();
                        if (util.isNullOrEmpty(inputText)) {
                            if (this.required === true) {
                                result.fail(nts.uk.resource.getMessage('FND_E_REQ_INPUT', [this.name]), 'FND_E_REQ_INPUT');
                                return result;
                            }
                            else {
                                result.success("");
                                return result;
                            }
                        }
                        var minValue = uk.time.minutesBased.clock.dayattr.MIN_VALUE;
                        var maxValue = uk.time.minutesBased.clock.dayattr.MAX_VALUE;
                        if (!util.isNullOrUndefined(this.constraint.min)) {
                            var minS = uk.time.minutesBased.clock.dayattr.parseString(this.constraint.min);
                            if (minS.success) {
                                minValue = uk.time.minutesBased.clock.dayattr.create(minS.asMinutes);
                            }
                            minValue = uk.time.minutesBased.clock.dayattr.create(uk.time.minutesBased.clock.dayattr.parseString(this.constraint.min).asMinutes);
                        }
                        if (!util.isNullOrUndefined(this.constraint.max)) {
                            var maxS = uk.time.minutesBased.clock.dayattr.parseString(this.constraint.max);
                            if (maxS.success) {
                                maxValue = uk.time.minutesBased.clock.dayattr.create(maxS.asMinutes);
                            }
                        }
                        var parsed = uk.time.minutesBased.clock.dayattr.parseString(inputText);
                        if (!parsed.success || parsed.asMinutes !== Math.round(parsed.asMinutes)
                            || parsed.asMinutes < minValue || parsed.asMinutes > maxValue) {
                            result.fail(nts.uk.resource.getMessage("FND_E_CLOCK", [this.name, minValue.fullText, maxValue.fullText]), "FND_E_CLOCK");
                        }
                        else {
                            result.success(parsed.asMinutes);
                        }
                        return result;
                    };
                    return TimeWithDayValidator;
                }());
                validation.TimeWithDayValidator = TimeWithDayValidator;
                function getConstraint(primitiveValueName) {
                    var constraint = __viewContext.primitiveValueConstraints[primitiveValueName];
                    if (constraint === undefined)
                        return null;
                    else
                        return __viewContext.primitiveValueConstraints[primitiveValueName];
                }
                validation.getConstraint = getConstraint;
                function writeConstraint(constraintName, constraint) {
                    __viewContext.primitiveValueConstraints[constraintName] = constraint;
                }
                validation.writeConstraint = writeConstraint;
                function writeConstraints(constraints) {
                    _.forEach(constraints, function (constraint) {
                        __viewContext.primitiveValueConstraints[constraint.itemCode] = constraint;
                    });
                }
                validation.writeConstraints = writeConstraints;
            })(validation = ui.validation || (ui.validation = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=validation.js.map