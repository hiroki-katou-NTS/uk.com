/// <reference path="../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var validation;
            (function (validation) {
                class NoValidator {
                    validate(inputText, option) {
                        var result = new ValidationResult();
                        result.isValid = true;
                        result.parsedValue = inputText;
                        return result;
                    }
                }
                validation.NoValidator = NoValidator;
                class ValidationResult {
                    constructor() {
                        this.errorMessage = 'error message';
                    }
                    fail(errorMessage) {
                        this.errorMessage = errorMessage;
                        this.isValid = false;
                    }
                    success(parsedValue) {
                        this.parsedValue = parsedValue;
                        this.isValid = true;
                    }
                }
                validation.ValidationResult = ValidationResult;
                class StringValidator {
                    constructor(primitiveValueName, option) {
                        this.constraint = getConstraint(primitiveValueName);
                        this.charType = uk.text.getCharType(primitiveValueName);
                        this.required = option.required;
                    }
                    validate(inputText, option) {
                        var result = new ValidationResult();
                        // Check Required
                        if (this.required !== undefined && this.required !== false) {
                            if (uk.util.isNullOrEmpty(inputText)) {
                                result.fail('This field is required');
                                return result;
                            }
                        }
                        // Check CharType
                        if (this.charType !== null && this.charType !== undefined) {
                            if (this.charType.viewName === '半角数字' || this.charType.viewName === '半角英数字') {
                                inputText = uk.text.toOneByteAlphaNumberic(inputText);
                            }
                            else if (this.charType.viewName === 'カタカナ') {
                                inputText = uk.text.oneByteKatakanaToTwoByte(inputText);
                            }
                            if (!this.charType.validate(inputText)) {
                                result.fail('Invalid text');
                                return result;
                            }
                        }
                        // Check Constraint
                        if (this.constraint !== undefined && this.constraint !== null) {
                            if (this.constraint.maxLength !== undefined && uk.text.countHalf(inputText) > this.constraint.maxLength) {
                                result.fail('Max length for this input is ' + this.constraint.maxLength);
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
                    }
                }
                validation.StringValidator = StringValidator;
                class NumberValidator {
                    constructor(primitiveValueName, option) {
                        this.constraint = getConstraint(primitiveValueName);
                        this.option = option;
                    }
                    validate(inputText) {
                        var result = new ValidationResult();
                        var isDecimalNumber = false;
                        if (this.option !== undefined) {
                            isDecimalNumber = (this.option.decimallength > 0);
                            inputText = uk.text.replaceAll(inputText.toString(), this.option.groupseperator, '');
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
                        result.success(inputText === "0" ? inputText : uk.text.removeFromStart(inputText, "0"));
                        return result;
                    }
                }
                validation.NumberValidator = NumberValidator;
                class TimeValidator {
                    constructor(primitiveValueName, option) {
                        this.constraint = getConstraint(primitiveValueName);
                        this.outputFormat = (option && option.outputFormat) ? option.outputFormat : "";
                        this.required = (option && option.required) ? option.required : false;
                        this.valueType = (option && option.valueType) ? option.valueType : "string";
                    }
                    validate(inputText) {
                        var result = new ValidationResult();
                        // Check required
                        if (uk.util.isNullOrEmpty(inputText)) {
                            if (this.required === true) {
                                result.fail('This field is required');
                                return result;
                            }
                            else {
                                result.success("");
                                return result;
                            }
                        }
                        // Create Parser
                        var parseResult = uk.time.parseMoment(inputText, this.outputFormat);
                        // Parse
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
                        return result;
                    }
                }
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
