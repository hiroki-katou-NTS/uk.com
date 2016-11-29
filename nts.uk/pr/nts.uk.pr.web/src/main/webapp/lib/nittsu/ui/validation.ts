module nts.uk.ui.validation {
     
    export interface IValidator {
        validate(inputText: string): ValidationResult;
    }

    export class ValidationResult {
        isValid: boolean;
        parsedValue: any;
        errorMessage = 'error message';
    }
     
    export function createValidator(constraintName: string): IValidator {
        var constraint = getConstraint(constraintName);
        
        switch (constraint.valueType) {
            case 'String':
                return new StringValidator(constraintName);
        }
    }
    
        
    export class StringValidator implements IValidator {
        
        charType: text.CharType;
        
        constructor(primitiveValueName: string) {
            this.charType = text.getCharType(primitiveValueName);
        }
        
        validate(inputText: string): ui.validation.ValidationResult {
            return this.charType.validate(inputText);
        }
    }
    
    class NumberValidator implements IValidator {
    }
     
    export function getConstraint(primitiveValueName: string) {
        var constraint = __viewContext.primitiveValueConstraints[primitiveValueName];
        if(constraint === undefined)
            return null;
        else
            return __viewContext.primitiveValueConstraints[primitiveValueName];
    }
}