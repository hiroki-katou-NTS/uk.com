/// <reference path="../reference.ts"/>

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

    export function createValidator(constraintName: string, option?: any): IValidator {
        var constraint = getConstraint(constraintName);
        if (constraint === null) {
            return new NoValidator();
        }
        if (constraint.valueType === 'String') {
                return new StringValidator(constraintName);
        }
        if(option) {          
            if(option.inputFormat) {
                //If inputFormat presented, this is Date or Time Editor                 
                return new TimeValidator(constraintName, option);
            } else  {
                return new NumberValidator(constraintName, option);
                //currencyformat presented, this is CurrencyEditor
            } 
        }
        return new NoValidator();
    }


    export class StringValidator implements IValidator {
        constraint: any;
        charType: nts.uk.text.CharType;
        required: boolean;
        
        constructor(primitiveValueName: string, required?: boolean) {
            this.constraint = getConstraint(primitiveValueName);
            this.charType = text.getCharType(primitiveValueName);
            this.required = required;
        }
        
        validate(inputText: string): ValidationResult {
            var result = new ValidationResult();
            
            if (this.required !== undefined && this.required !== false) {
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
                inputText = text.replaceAll(inputText.toString(), this.option.groupseperator, '');    
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
            result.success(inputText === "0" ? inputText : text.removeFromStart(inputText, "0"));
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
            }else if(this.option.inputFormat === "timeofday") {
                parseResult = time.parseTimeOfTheDay(inputText);
            }else if(this.option.inputFormat === "yearmonthdate") {
                parseResult = time.parseYearMonthDate(inputText);
            }else {
                parseResult = time.ResultParseTime.failed();
            }
            if(parseResult.success){
                result.success(parseResult.toValue());
            }else{
                result.fail(parseResult.getMsg());
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