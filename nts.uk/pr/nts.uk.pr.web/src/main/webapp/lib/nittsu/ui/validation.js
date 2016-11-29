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
                            return new StringValidator(constraintName);
                    }
                    return new NoValidator();
                }
                validation.createValidator = createValidator;
                var StringValidator = (function () {
                    function StringValidator(primitiveValueName) {
                        this.charType = uk.text.getCharType(primitiveValueName);
                    }
                    StringValidator.prototype.validate = function (inputText) {
                        return this.charType.validate(inputText);
                    };
                    return StringValidator;
                }());
                validation.StringValidator = StringValidator;
                var NumberValidator = (function () {
                    function NumberValidator() {
                    }
                    return NumberValidator;
                }());
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
