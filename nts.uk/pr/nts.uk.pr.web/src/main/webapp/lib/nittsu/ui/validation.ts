module nts.uk.ui.validation {

    export interface IValidator {
        validate(inputText: string): ValidationResult;
    }

    export class NoValidator {
        validate(inputText: string): ValidationResult {
            var result = new ValidationResult();
            result.isValid = true;
            result.parsedValue = inputText;
            return result;
        }
    }

    export class ValidationResult {
        isValid: boolean;
        parsedValue: any;
        errorMessage = 'error message';

        fail(errorMessage: any) {
            this.errorMessage = errorMessage;
            this.isValid = false;
        }

        success(parsedValue: any) {
            this.parsedValue = parsedValue;
            this.isValid = true;
        }
    }

    export function createValidator(constraintName: string): IValidator {
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


    export class StringValidator implements IValidator {

        charType: text.CharType;

        constructor(primitiveValueName: string) {
            this.charType = text.getCharType(primitiveValueName);
        }

        validate(inputText: string): ValidationResult {
            return this.charType.validate(inputText);
        }
    }

    export class NumberValidator implements IValidator {
        constraint: any;
        option: any;

        constructor(primitiveValueName: string, option: any) {
            this.constraint = getConstraint(primitiveValueName);
            this.option = option;
        }

        validate(inputText: string): ValidationResult {
            var result = new ValidationResult();
            var isDecimalNumber = (this.option !== undefined && this.option.decimallength() > 0)
            if (!ntsNumber.isNumber(inputText, !isDecimalNumber)) {
                result.fail('invalid number');
                return result;
            }
            var value = isDecimalNumber ? parseFloat(inputText) : parseInt(inputText);

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
            result.success(value);
            return result;
        }
    }

    export class TimeValidator implements IValidator {
        constraint: any;
        option: any;

        constructor(primitiveValueName: string, option: any) {
            this.constraint = getConstraint(primitiveValueName);
            this.option = option;
        }

        validate(inputText: string): ValidationResult {
            var result = new ValidationResult();
            var parseResult;
            if (this.option.inputFormat() === "yearmonth") {
                parseResult = time.parseYearMonth(inputText);
            } else if (this.option.inputFormat() === "time") {
                parseResult = time.parseTime(inputText, false);
            }
            if(parseResult.success){
                result.success(parseResult.toValue());
            }else{
                result.fail('invalid time');
            }
            return result;
        }
    }
    export function getConstraint(primitiveValueName: string) {
        var constraint = __viewContext.primitiveValueConstraints[primitiveValueName];
        if (constraint === undefined)
            return null;
        else
            return __viewContext.primitiveValueConstraints[primitiveValueName];
    }
}