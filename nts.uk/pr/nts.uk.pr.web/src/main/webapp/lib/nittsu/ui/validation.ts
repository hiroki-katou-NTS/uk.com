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
        
        validate(inputText: string): ui.validation.ValidationResult {
            return this.charType.validate(inputText);
        }
    }
    
    export class NumberValidator implements IValidator {
    }
     
    export function getConstraint(primitiveValueName: string) {
        var constraint = __viewContext.primitiveValueConstraints[primitiveValueName];
        if(constraint === undefined)
            return null;
        else
            return __viewContext.primitiveValueConstraints[primitiveValueName];
    }
}