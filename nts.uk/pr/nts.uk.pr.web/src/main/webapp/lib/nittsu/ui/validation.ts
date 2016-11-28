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
     
     
    function isInteger(value: any, option?: any) {
        if(option !== undefined && option.groupseperator() !== undefined){
            value = this.isInteger(value) ? value : value.toString().replace(option.groupseperator(), '');
        }
        return !isNaN(value) && parseInt(value) == value && !isNaN(parseInt(value, 10));
    }
     
    function isDecimal(value: any, option?: any) {
        if(option !== undefined && option.groupseperator() !== undefined){
            value = this.isDecimal(value) ? value : value.toString().replace(option.groupseperator(), '');
        }
        return !isNaN(value) && parseFloat(value) == value && !isNaN(parseFloat(value));
    }
     
    export function isNumber(value: any, isDecimalValue?: boolean, option?: any){
        if(isDecimalValue){
            return isDecimal(value, option); 
        }else{
            return isInteger(value, option); 
        }
    }
     
    abstract class ParseResult{
        success: boolean;
        constructor(success) {
            this.success = success;
        }
        
        abstract format();
        
        abstract toValue();
    }
     
    export class ResultParseTime extends ParseResult{
        minus: boolean;
        hours: number;
        minutes: number;
        
        constructor(success, minus?, hours?, minutes?) {
            super(success);
            this.minus = minus;
            this.hours = hours;
            this.minutes = minutes;
        }
        
        static succeeded(minus, hours, minutes) {
            return new ResultParseTime(true, minus, hours, minutes);
        }
     
        static failed() {
            return new ResultParseTime(false);
        }
        
        format() {
            return (this.minus ? '-' : '') + this.hours + ':' + text.padLeft(String(this.minutes), '0', 2);
        }
        
        toValue() {
            return (this.minus ? -1 : 1) * (this.hours * 60 + this.minutes);
        }
    }
     
    export function parseTime(time: any, isMinutes?: boolean): ResultParseTime {
        if(isMinutes){
            var hoursX = Math.floor(time/60);
            time = hoursX + text.padLeft((time - hoursX*60).toString(), '0', 2);
        }
        if(!(time instanceof String)){
            time = time.toString();
        }
        if(time.length < 2 || time.split(':').length > 2 || time.split('-').length > 2
            || time.lastIndexOf('-') > 0){
            return ResultParseTime.failed();
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
            return ResultParseTime.failed();
        }
        
        return ResultParseTime.succeeded(minusNumber, parseInt(hours), parseInt(minutes));
    } 
    
    export class ResultParseYearMonth extends ParseResult{
        year: number;
        month: number;
        
        constructor(success, year?, month?) {
            super(success);
            this.year = year;
            this.month = month;
        }
        
        static succeeded(year, month) {
            return new ResultParseYearMonth(true, year, month);
        }
     
        static failed() {
            return new ResultParseYearMonth(false);
        }
        
        format() {
            return this.year + '/' + text.padLeft(String(this.month), '0', 2);
        }
        
        toValue() {
            return (this.year * 100 + this.month);
        }
    }
     
    export function parseYearMonth(yearMonth: any): ResultParseYearMonth{
        if(!(yearMonth instanceof String)){
            yearMonth = yearMonth.toString();
        }
        var stringLengh = yearMonth.length;
        if((stringLengh < 6 || stringLengh > 7) || yearMonth.split('/').length > 2){
            return ResultParseYearMonth.failed();
        }
        var indexOf = yearMonth.lastIndexOf('/');
        var year, month;
        if(indexOf > -1 && indexOf !== 4){
            return ResultParseYearMonth.failed();
        }else if(indexOf === 4) {
            year = yearMonth.split('/')[0];
            month = yearMonth.split('/')[1];
        }else if(indexOf　<= -1){
            year = yearMonth.substr(0, stringLengh - 2);
            month = yearMonth.substr(-2, 2);
        }
        if(!isInteger(month) || parseInt(month) > 12 || !isInteger(year)　|| parseInt(year) < 1900 ){
                return ResultParseYearMonth.failed();
        }
        
        return ResultParseYearMonth.succeeded(parseInt(year), parseInt(month));
    } 
}