module nts.uk.ntsNumber {
    
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
    
    export function formatNumber(value: number, formatOption: any){
        var groupseperator = formatOption.groupseperator() ? formatOption.groupseperator() : ',';
        var grouplength = formatOption.grouplength() ? formatOption.grouplength() : 0;
        var decimalseperator = formatOption.decimalseperator() ? formatOption.decimalseperator() : ".";
        var decimallength = formatOption.decimallength() ? formatOption.decimallength() : 0;
        var formattedValue = "";
        var stringValue = value.toString();
        var values = stringValue.split(decimalseperator);
        if(grouplength > 0){
            var x = values[0].split('').reverse().join('');
            for(var i = 0; i < x.length;){
                formattedValue += x.substr(i, grouplength) + (x.length > i + grouplength ? groupseperator : "");
                i += grouplength;
            }
            formattedValue = formattedValue.split('').reverse().join('');
        }else{
            formattedValue = values[0];
        }
        if(values[1] === undefined || decimallength > values[1].length){
            values[1] = text.padRight(values[1] ? values[1] : "", '0', values[1] ? decimallength : decimallength + 1);
        }else{
            values[1] = values[1].substr(0, decimallength);    
        }
         
        return formattedValue + decimalseperator + values[1];   
     }
}