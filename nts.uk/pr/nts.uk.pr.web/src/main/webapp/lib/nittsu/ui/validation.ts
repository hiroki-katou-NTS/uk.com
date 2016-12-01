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
                return new StringValidator(constraintName, null);
        }

        return new NoValidator();
    }


    export class StringValidator implements IValidator {
        constraint: any;
        charType: text.CharType;
        required: boolean;
        
        constructor(primitiveValueName: string, required: boolean) {
            this.constraint = getConstraint(primitiveValueName);
            this.charType = text.getCharType(primitiveValueName);
            this.required = required;
        }
        
        validate(inputText: string): ValidationResult {
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
                if (text.countHalf(inputText) > this.constraint.maxLength) {
                    result.fail('Max length for this input is ' + this.constraint.maxLength);
                    return result;
                }
            }
            result.success(inputText);
            return result;
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
            var isDecimalNumber = false;
            if(this.option !== undefined){
                isDecimalNumber = (this.option.decimallength > 0)
                inputText = text.replaceAll(inputText, this.option.groupseperator, '');    
            }
            
            if (!ntsNumber.isNumber(inputText, isDecimalNumber)) {
                result.fail('invalid number');
                return result;
            }
            var value = isDecimalNumber ? 
                ntsNumber.getDecimal(inputText, this.option.decimallength) : parseInt(inputText);

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
            if (this.option.inputFormat === "yearmonth") {
                parseResult = time.parseYearMonth(inputText);
            } else if (this.option.inputFormat === "time") {
                parseResult = time.parseTime(inputText, false);
            }else {
                parseResult = time.ResultParseTime.failed();
            }
            if(parseResult.success){
                result.success(parseResult.toValue());
            }else{
                result.fail('invalid time');
            }
            return result;
        }
    }
     
    function checkRequired(value: any): boolean {
        if (value === undefined || value === null || value.length == 0) {
            return false;
        }
        return true;
    }
     
    export function getConstraint(primitiveValueName: string) {
        var constraint = __viewContext.primitiveValueConstraints[primitiveValueName];
        if (constraint === undefined)
            return null;
        else
            return __viewContext.primitiveValueConstraints[primitiveValueName];
    }
}