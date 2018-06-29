var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ntsNumber;
        (function (ntsNumber) {
            function isInteger(value, option) {
                if (option !== undefined && option.groupseperator !== undefined) {
                    value = isInteger(value) ? value : uk.text.replaceAll(value.toString(), option.groupseperator(), '');
                }
                return !isNaN(value) && parseInt(value) == value && !isNaN(parseInt(value, 10));
            }
            function isDecimal(value, option) {
                if (option !== undefined) {
                    var seperator = typeof option.groupseperator === 'function' ? option.groupseperator() : option.groupseperator;
                    value = isDecimal(value) || seperator === undefined ? value : uk.text.replaceAll(value.toString(), seperator, '');
                }
                return !isNaN(value) && parseFloat(value) == value && !isNaN(parseFloat(value));
            }
            function isNumber(value, isDecimalValue, option, message) {
                if (isDecimalValue) {
                    if (message !== undefined)
                        message.id = 'FND_E_REALNUMBER';
                    return isDecimal(value, option);
                }
                else {
                    if (message !== undefined)
                        message.id = 'FND_E_INTEGER';
                    return isInteger(value, option);
                }
            }
            ntsNumber.isNumber = isNumber;
            function isHalfInt(value, message) {
                var val = parseFloat(value);
                if (message !== undefined)
                    message.id = 'FND_E_HALFINT';
                if (val !== NaN && (val * 2) % 1 === 0)
                    return true;
                return false;
            }
            ntsNumber.isHalfInt = isHalfInt;
            ntsNumber.trunc = (typeof Math.trunc === 'function') ? Math.trunc : function (value) { return value > 0 ? Math.floor(value) : Math.ceil(value); };
            function getDecimal(value, scale) {
                var scaleX = Math.pow(10, scale);
                return ntsNumber.trunc(value * scaleX) / scaleX;
            }
            ntsNumber.getDecimal = getDecimal;
            function formatNumber(value, formatOption) {
                if (value === undefined || value === null || value.toString().trim().lenth <= 0) {
                    return value;
                }
                switch (formatOption.formatId) {
                    case 'Number_Separated':
                        formatOption.grouplength = 3;
                        break;
                }
                var groupSeperator = formatOption.groupseperator ? formatOption.groupseperator : ',';
                var groupLength = formatOption.grouplength ? formatOption.grouplength : 0;
                var decimalSeperator = formatOption.decimalseperator ? formatOption.decimalseperator : ".";
                var decimalLength = formatOption.decimallength ? formatOption.decimallength : 0;
                var formattedValue = "";
                var stringValue = uk.text.replaceAll(value.toString().trim(), groupSeperator, '');
                var isMinus = stringValue.charAt(0) === '-';
                var values = isMinus ? stringValue.split('-')[1].split(decimalSeperator) : stringValue.split(decimalSeperator);
                if (groupLength > 0) {
                    var x = values[0].split('').reverse().join('');
                    for (var i = 0; i < x.length;) {
                        formattedValue += x.substr(i, groupLength) + (x.length > i + groupLength ? groupSeperator : "");
                        i += groupLength;
                    }
                    formattedValue = formattedValue.split('').reverse().join('');
                }
                else {
                    formattedValue = values[0];
                }
                if (formattedValue.indexOf("0") >= 0) {
                    formattedValue = uk.text.removeFromStart(uk.text.removeFromStart(formattedValue, '0'), groupSeperator);
                    if (formattedValue === "") {
                        formattedValue = 0;
                    }
                }
                if (values[1] === undefined || decimalLength > values[1].length) {
                    values[1] = uk.text.padRight(values[1] ? values[1] : "", '0', values[1] ? decimalLength : decimalLength + 1);
                }
                else {
                    values[1] = values[1].substr(0, decimalLength);
                }
                values[1] = uk.text.splitOrPadRight(values[1], decimalLength, '0');
                return (isMinus ? '-' : '') + formattedValue + (decimalLength <= 0 ? '' : decimalSeperator + values[1]);
            }
            ntsNumber.formatNumber = formatNumber;
        })(ntsNumber = uk.ntsNumber || (uk.ntsNumber = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=number.js.map