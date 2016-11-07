declare module nts.uk.ui.validation {
    class CharType {
        width: number;
        validator: (text: string) => boolean;
        constructor(width: number, validator: (text: string) => boolean);
        validate(text: string): ValidationResult;
    }
    class ValidationResult {
        isValid: boolean;
        errorMessage: string;
    }
    function getCharType(primitiveValueConstraint: string): CharType;
    interface PrimitiveValueConstraint {
        valueType: string;
        charType: string;
        maxLength: number;
    }
}
