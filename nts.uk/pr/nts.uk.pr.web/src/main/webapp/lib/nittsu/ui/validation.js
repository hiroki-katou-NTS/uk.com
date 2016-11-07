var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var validation;
            (function (validation) {
                var CharType = (function () {
                    function CharType(width, validator) {
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
                    return CharType;
                }());
                validation.CharType = CharType;
                var ValidationResult = (function () {
                    function ValidationResult() {
                        this.errorMessage = 'error';
                    }
                    return ValidationResult;
                }());
                validation.ValidationResult = ValidationResult;
                function getCharType(primitiveValueConstraint) {
                    var constraint = __viewContext.primitiveValueConstraints[primitiveValueConstraint];
                    var charType = charTypes[constraint.charType];
                    if (charType === undefined) {
                        throw new Error('invalid charTypeName: ' + constraint.charType);
                    }
                    return charType;
                }
                validation.getCharType = getCharType;
                var charTypes = {
                    AnyHalfWidth: new CharType(0.5, nts.uk.text.allHalf),
                    AlphaNumeric: new CharType(0.5, nts.uk.text.allHalfAlphanumeric),
                    Alphabet: new CharType(0.5, nts.uk.text.allHalfAlphabet),
                    Numeric: new CharType(0.5, nts.uk.text.allHalfNumeric),
                    Any: new CharType(1, nts.uk.util.alwaysTrue),
                };
            })(validation = ui.validation || (ui.validation = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
