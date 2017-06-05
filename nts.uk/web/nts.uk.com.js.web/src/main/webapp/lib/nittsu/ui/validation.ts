/// <reference path="../reference.ts"/>

module nts.uk.ui.validation {

    export interface IValidator {
        validate(inputText: string, option?: any): ValidationResult;
    }

    export class NoValidator {
        validate(inputText: string, option?: any): ValidationResult {
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

    export class StringValidator implements IValidator {
        constraint: any;
        charType: nts.uk.text.CharType;
        required: boolean;

        constructor(primitiveValueName: string, option?: any) {
            this.constraint = getConstraint(primitiveValueName);
            this.charType = text.getCharType(primitiveValueName);
            this.required = option.required;
        }

        validate(inputText: string, option?: any): ValidationResult {
            var result = new ValidationResult();
            // Check Required
            if (this.required !== undefined && this.required !== false) {
                if (util.isNullOrEmpty(inputText)) {
                    result.fail('This field is required');
                    return result;
                }
            }
            // Check CharType
            if (this.charType !== null && this.charType !== undefined) {
                if (this.charType.viewName === '半角数字') {
                    inputText = text.toOneByteAlphaNumberic(inputText);        
                } else if (this.charType.viewName === '半角英数字') {
                    inputText = text.toOneByteAlphaNumberic(text.toUpperCase(inputText));
                } else if (this.charType.viewName === 'カタカナ') {
                    inputText = text.oneByteKatakanaToTwoByte(inputText);    
                } else if (this.charType.viewName === 'カナ') {
                    inputText = text.hiraganaToKatakana(text.oneByteKatakanaToTwoByte(inputText));
                }
                if (!this.charType.validate(inputText)) {
                    result.fail('Invalid text');
                    return result;
                }
            }
            // Check Constraint
            if (this.constraint !== undefined && this.constraint !== null) {
                if (this.constraint.maxLength !== undefined && text.countHalf(inputText) > this.constraint.maxLength) {
                    result.fail('Max length for this input is ' + this.constraint.maxLength);
                    return result;
                }
                
                if(!util.isNullOrUndefined(option) && option.isCheckExpression === true){  
                    if (!text.isNullOrEmpty(this.constraint.stringExpression) && !this.constraint.stringExpression.test(inputText)) {
                        result.fail('This field is not valid with pattern!');
                        return result;
                    }  
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
            if (this.option !== undefined) {
                if(nts.uk.util.isNullOrUndefined(inputText) || inputText.trim().length <= 0){
                    if(this.option['required'] === true && nts.uk.util.isNullOrEmpty(this.option['defaultValue'])){    
                        result.fail('This field is required.');
                        return result;
                    } else {
                        result.success(this.option['defaultValue']);
                        return result;
                    }    
                }
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
                    result.fail('max value is: ' + this.constraint.max);
                    return result;
                }
                if (this.constraint.min !== undefined && value < this.constraint.min) {
                    result.fail('min value is: ' + this.constraint.min);
                    return result;
                }
            }
            result.success(inputText === "0" ? inputText : text.removeFromStart(inputText, "0"));
            return result; 
        }
    }

    export class TimeValidator implements IValidator {
        constraint: any;
        outputFormat: string;
        required: boolean;
        valueType: string;
        mode: string;
        constructor(primitiveValueName: string, option?: any) {
            this.constraint = getConstraint(primitiveValueName);
            this.outputFormat = (option && option.outputFormat) ? option.outputFormat : "";
            this.required = (option && option.required) ? option.required : false;
            this.valueType = (option && option.valueType) ? option.valueType : "string";
            this.mode = (option && option.mode) ? option.mode : "";
        }

        validate(inputText: string): any {
            var result = new ValidationResult();
            // Check required
            if (util.isNullOrEmpty(inputText)) {
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
            if(this.mode === "time"){
                var timeParse = time.parseTime(inputText, false) 
                if (timeParse.success) {
                    result.success(timeParse.toValue());
                } else {
                    result.fail(timeParse.getMsg()); 
                }
                return result;   
            }
            var parseResult = time.parseMoment(inputText, this.outputFormat);
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

    export function getConstraint(primitiveValueName: string) {
        var constraint = __viewContext.primitiveValueConstraints[primitiveValueName];
        if (constraint === undefined)
            return null;
        else
            return __viewContext.primitiveValueConstraints[primitiveValueName];
    }
}