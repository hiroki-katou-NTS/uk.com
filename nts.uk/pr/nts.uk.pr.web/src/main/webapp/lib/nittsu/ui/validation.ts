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
     
    export function isInteger(value: any) {
        return !isNaN(value) && parseInt(value) == value && !isNaN(parseInt(value, 10));
    }
     
    export function isDecimal(value: any) {
        return !isNaN(value) && parseFloat(value) == value && !isNaN(parseFloat(value));
    }
     
    export function validateTime(time: string){
        if(time.length < 2 || time.split(':').length > 2 || time.split('-').length > 2
            || time.lastIndexOf('-') > 0){
            throw new Error('invalid time value: ' + time);
        }
        
        var minusNumber = time.charAt(0) === '-';
        if(minusNumber){
            time = time.split('-')[1];
        }
        var minutes;
        var hours;
        if(time.indexOf(':') > -1){
            var times = time.split(':');
            minutes = times[1];
            hours = times[0];
        }else{
            minutes = time.substr(-2,2);
            hours = time.substr(0, time.length - 2);
        }
        
        if(!isInteger(minutes) || parseInt(minutes) > 59 || !isInteger(hours)){
                throw new Error('invalid time value: ' + time);
        }
        
        return (minusNumber ? '-' : '') + hours + ":" + (minutes.length === 1 ? '0' + minutes : minutes);
    } 
     
    export function validateYearMonth(yearMonth: string){
        var stringLengh = yearMonth.length;
        if((stringLengh < 6 || stringLengh > 7) || yearMonth.split('/').length > 2){
            throw new Error('invalid year month value: ' + yearMonth);
        }
        var indexOf = yearMonth.lastIndexOf('/');
        var year, month;
        if(indexOf > -1 && indexOf !== 4){
            throw new Error('invalid year month value: ' + yearMonth);
        }else if(indexOf === 4) {
            year = yearMonth.split('/')[0];
            month = yearMonth.split('/')[1];
        }else if(indexOf　<= -1){
            year = yearMonth.substr(0, stringLengh - 2);
            month = yearMonth.substr(-2, 2);
        }
        if(!isInteger(month) || parseInt(month) > 12 || !isInteger(year)　|| parseInt(year) < 1900 ){
                throw new Error('invalid year month value: ' + yearMonth);
        }
        
        return year + "/" + (month.length === 1 ? '0' + month : month);
    } 
}