module nts.uk.ui.validation {

    /**
     * Type of characters
     */
    export class CharType {
        viewName: string;
        width: number;
        validator: (text: string) => boolean;

        constructor(
            viewName: string,
            width: number,
            validator: (text: string) => boolean) {

            this.viewName = viewName;
            this.width = width;
            this.validator = validator;
        }

        validate(text: string): ValidationResult {
            var result = new ValidationResult();
            if (this.validator(text)) {
                result.isValid = true;
            } else {
                result.isValid = false;
            }

            return result;
        }
        
        buildConstraintText(maxLength: number) {
            return this.viewName + this.getViewLength(maxLength) + '文字';
        }
        
        getViewLength(length) {
            return Math.floor(length / (this.width * 2));
        }
    }

    export class ValidationResult {
        isValid: boolean;
        errorMessage = 'error message';
    }

    export function getCharType(primitiveValueName: string): CharType {
        var constraint = __viewContext.primitiveValueConstraints[primitiveValueName];
        if(constraint === undefined) return null;
        if(constraint.charType === undefined)
            constraint.charType = "Any";
        var charType = charTypes[constraint.charType];
        if (charType === undefined) {
            throw new Error('invalid charTypeName: ' + constraint.charType);
        }

        return charType;
    }

    var charTypes: { [key: string]: CharType } = {
        AnyHalfWidth: new CharType(
            '半角',
            0.5,
            nts.uk.text.allHalf),
        AlphaNumeric: new CharType(
            '半角英数字',
            0.5,
            nts.uk.text.allHalfAlphanumeric),
        Alphabet: new CharType(
            '半角英字',
            0.5,
            nts.uk.text.allHalfAlphabet),
        Numeric: new CharType(
            '半角数字',
            0.5,
            nts.uk.text.allHalfNumeric),
        Any: new CharType(
            '全角',
            1,
            nts.uk.util.alwaysTrue),
        
    };
}