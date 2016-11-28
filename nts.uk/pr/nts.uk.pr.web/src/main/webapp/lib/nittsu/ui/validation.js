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
            })(validation = ui.validation || (ui.validation = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
