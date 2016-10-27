module nts.uk.ui.validation {

    export class CharType {
        width: number;
        validator: (text: string) => boolean;

        constructor(
            width: number,
            validator: (text: string) => boolean) {

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
    }

    export class ValidationResult {
        isValid: boolean;
        errorMessage = 'error';
    }

    export function getCharType(primitiveValueConstraint: string): CharType {
        var constraint = __viewContext.primitiveValueConstraints[primitiveValueConstraint];

        var charType = charTypes[constraint.charType];
        if (charType === undefined) {
            throw new Error('invalid charTypeName: ' + constraint.charType);
        }

        return charType;
    }

    export interface PrimitiveValueConstraint {
        valueType: string;
        charType: string;
        maxLength: number;
    }

    var charTypes: { [key: string]: CharType } = {
        AnyHalfWidth: new CharType(
            0.5,
            nts.uk.text.allHalf),
        AlphaNumeric: new CharType(
            0.5,
            nts.uk.text.allHalfAlphanumeric),
        Alphabet: new CharType(
            0.5,
            nts.uk.text.allHalfAlphabet),
        Numeric: new CharType(
            0.5,
            nts.uk.text.allHalfNumeric),
        Any: new CharType(
            1,
            nts.uk.util.alwaysTrue),
    };
}