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
                    NoValidator.prototype.validate = function (inputText) {
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
                function createValidator(constraintName) {
                    var constraint = getConstraint(constraintName);
                    if (constraint === null) {
                        return new NoValidator();
                    }
                    switch (constraint.valueType) {
                        case 'String':
                            return new StringValidator(constraintName, null);
                    }
                    return new NoValidator();
                }
                validation.createValidator = createValidator;
                var StringValidator = (function () {
                    function StringValidator(primitiveValueName, required) {
                        this.constraint = getConstraint(primitiveValueName);
                        this.charType = uk.text.getCharType(primitiveValueName);
                        this.required = required;
                    }
                    StringValidator.prototype.validate = function (inputText) {
                        var result = new ValidationResult();
                        if (this.required !== undefined) {
                            if (!checkRequired(inputText)) {
                                result.fail('This field is required');
                                return result;
                            }
                        }
                        if (this.charType !== null) {
                            if (!this.charType.validate(inputText)) {
                                result.fail('Invalid text');
                                return result;
                            }
                        }
                        if (this.constraint !== null && this.constraint.maxLength !== undefined) {
                            if (uk.text.countHalf(inputText) > this.constraint.maxLength) {
                                result.fail('Max length for this input is ' + this.constraint.maxLength);
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
                    function NumberValidator(primitiveValueName, option) {
                        this.constraint = getConstraint(primitiveValueName);
                        this.option = option;
                    }
                    NumberValidator.prototype.validate = function (inputText) {
                        var result = new ValidationResult();
                        var isDecimalNumber = false;
                        if (this.option !== undefined) {
                            isDecimalNumber = (this.option.decimallength > 0);
                            inputText = uk.text.replaceAll(inputText, this.option.groupseperator, '');
                        }
                        if (!uk.ntsNumber.isNumber(inputText, isDecimalNumber)) {
                            result.fail('invalid number');
                            return result;
                        }
                        var value = isDecimalNumber ?
                            uk.ntsNumber.getDecimal(inputText, this.option.decimallength) : parseInt(inputText);
                        if (this.constraint !== null) {
                            if (this.constraint.max !== undefined && value > this.constraint.max) {
                                result.fail('invalid number');
                                return result;
                            }
                            if (this.constraint.min !== undefined && value < this.constraint.min) {
                                result.fail('invalid number');
                                return result;
                            }
                        }
                        //            if(inputText.indexOf(this.option.decimalseperator()) >= 0){
                        //                var values = inputText.split(this.option.decimalseperator());
                        //                values[1] = text.splitString(values[1], this.option.decimallength(), '0');
                        //                inputText = values[0] + this.option.decimalseperator() + values[1];
                        //            }
                        result.success(value);
                        return result;
                    };
                    return NumberValidator;
                }());
                validation.NumberValidator = NumberValidator;
                var TimeValidator = (function () {
                    function TimeValidator(primitiveValueName, option) {
                        this.constraint = getConstraint(primitiveValueName);
                        this.option = option;
                    }
                    TimeValidator.prototype.validate = function (inputText) {
                        var result = new ValidationResult();
                        var parseResult;
                        if (this.option.inputFormat === "yearmonth") {
                            parseResult = uk.time.parseYearMonth(inputText);
                        }
                        else if (this.option.inputFormat === "time") {
                            parseResult = uk.time.parseTime(inputText, false);
                        }
                        else {
                            parseResult = uk.time.ResultParseTime.failed();
                        }
                        if (parseResult.success) {
                            result.success(parseResult.toValue());
                        }
                        else {
                            result.fail('invalid time');
                        }
                        return result;
                    };
                    return TimeValidator;
                }());
                validation.TimeValidator = TimeValidator;
                function checkRequired(value) {
                    if (value === undefined || value === null || value.length == 0) {
                        return false;
                    }
                    return true;
                }
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
