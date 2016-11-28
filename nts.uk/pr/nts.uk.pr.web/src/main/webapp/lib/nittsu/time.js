var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var time;
        (function (time_1) {
            function formatYearMonth(yearMonth) {
                var result;
                var num = parseInt(String(yearMonth));
                var year = String(Math.floor(num / 100));
                var month = uk.text.charPadding(String(num % 100), '0', true, 2);
                result = year + '/' + month;
                return result;
            }
            time_1.formatYearMonth = formatYearMonth;
            function formatSeconds(seconds, formatOption) {
                seconds = parseInt(String(seconds));
                var ss = uk.text.padLeft(String(seconds % 60), '0', 2);
                var minutes = Math.floor(seconds / 60);
                var mm = uk.text.padLeft(String(minutes % 60), '0', 2);
                var hours = Math.floor(seconds / 60 / 60);
                var h = String(hours);
                // TODO: use formatOption
                return "h:mm:ss"
                    .replace(/h/g, h)
                    .replace(/mm/g, mm)
                    .replace(/ss/g, ss);
            }
            time_1.formatSeconds = formatSeconds;
            var ParseResult = (function () {
                function ParseResult(success) {
                    this.success = success;
                }
                return ParseResult;
            }());
            var ResultParseTime = (function (_super) {
                __extends(ResultParseTime, _super);
                function ResultParseTime(success, minus, hours, minutes) {
                    _super.call(this, success);
                    this.minus = minus;
                    this.hours = hours;
                    this.minutes = minutes;
                }
                ResultParseTime.succeeded = function (minus, hours, minutes) {
                    return new ResultParseTime(true, minus, hours, minutes);
                };
                ResultParseTime.failed = function () {
                    return new ResultParseTime(false);
                };
                ResultParseTime.prototype.format = function () {
                    return (this.minus ? '-' : '') + this.hours + ':' + uk.text.padLeft(String(this.minutes), '0', 2);
                };
                ResultParseTime.prototype.toValue = function () {
                    return (this.minus ? -1 : 1) * (this.hours * 60 + this.minutes);
                };
                return ResultParseTime;
            }(ParseResult));
            time_1.ResultParseTime = ResultParseTime;
            function parseTime(time, isMinutes) {
                if (isMinutes) {
                    var hoursX = Math.floor(time / 60);
                    time = hoursX + uk.text.padLeft((time - hoursX * 60).toString(), '0', 2);
                }
                if (!(time instanceof String)) {
                    time = time.toString();
                }
                if (time.length < 2 || time.split(':').length > 2 || time.split('-').length > 2
                    || time.lastIndexOf('-') > 0) {
                    return ResultParseTime.failed();
                }
                var minusNumber = time.charAt(0) === '-';
                if (minusNumber) {
                    time = time.split('-')[1];
                }
                var minutes;
                var hours;
                if (time.indexOf(':') > -1) {
                    var times = time.split(':');
                    minutes = times[1];
                    hours = times[0];
                }
                else {
                    minutes = time.substr(-2, 2);
                    hours = time.substr(0, time.length - 2);
                }
                if (!uk.ntsNumber.isNumber(minutes, false) || parseInt(minutes) > 59 || !uk.ntsNumber.isNumber(hours, false)) {
                    return ResultParseTime.failed();
                }
                return ResultParseTime.succeeded(minusNumber, parseInt(hours), parseInt(minutes));
            }
            time_1.parseTime = parseTime;
            var ResultParseYearMonth = (function (_super) {
                __extends(ResultParseYearMonth, _super);
                function ResultParseYearMonth(success, year, month) {
                    _super.call(this, success);
                    this.year = year;
                    this.month = month;
                }
                ResultParseYearMonth.succeeded = function (year, month) {
                    return new ResultParseYearMonth(true, year, month);
                };
                ResultParseYearMonth.failed = function () {
                    return new ResultParseYearMonth(false);
                };
                ResultParseYearMonth.prototype.format = function () {
                    return this.year + '/' + uk.text.padLeft(String(this.month), '0', 2);
                };
                ResultParseYearMonth.prototype.toValue = function () {
                    return (this.year * 100 + this.month);
                };
                return ResultParseYearMonth;
            }(ParseResult));
            time_1.ResultParseYearMonth = ResultParseYearMonth;
            function parseYearMonth(yearMonth) {
                if (!(yearMonth instanceof String)) {
                    yearMonth = yearMonth.toString();
                }
                var stringLengh = yearMonth.length;
                if ((stringLengh < 6 || stringLengh > 7) || yearMonth.split('/').length > 2) {
                    return ResultParseYearMonth.failed();
                }
                var indexOf = yearMonth.lastIndexOf('/');
                var year, month;
                if (indexOf > -1 && indexOf !== 4) {
                    return ResultParseYearMonth.failed();
                }
                else if (indexOf === 4) {
                    year = yearMonth.split('/')[0];
                    month = yearMonth.split('/')[1];
                }
                else if (indexOf <= -1) {
                    year = yearMonth.substr(0, stringLengh - 2);
                    month = yearMonth.substr(-2, 2);
                }
                if (!uk.ntsNumber.isNumber(month, false) || parseInt(month) > 12 || !uk.ntsNumber.isNumber(year, false)
                    || parseInt(year) < 1900) {
                    return ResultParseYearMonth.failed();
                }
                return ResultParseYearMonth.succeeded(parseInt(year), parseInt(month));
            }
            time_1.parseYearMonth = parseYearMonth;
            /**
            * 日付をフォーマットする
            * @param  {Date}   date     日付
            * @param  {String} [format] フォーマット
            * @return {String}          フォーマット済み日付
            */
            function formatDate(date, format) {
                if (!format)
                    format = 'yyyy-MM-dd hh:mm:ss.SSS';
                format = format.replace(/yyyy/g, date.getFullYear());
                format = format.replace(/yy/g, ('0' + (date.getFullYear() % 100)).slice(-2));
                format = format.replace(/MM/g, ('0' + (date.getMonth() + 1)).slice(-2));
                format = format.replace(/dd/g, ('0' + date.getDate()).slice(-2));
                format = format.replace(/hh/g, ('0' + date.getHours()).slice(-2));
                format = format.replace(/mm/g, ('0' + date.getMinutes()).slice(-2));
                format = format.replace(/ss/g, ('0' + date.getSeconds()).slice(-2));
                if (format.match(/S/g)) {
                    var milliSeconds = ('00' + date.getMilliseconds()).slice(-3);
                    var length = format.match(/S/g).length;
                    for (var i = 0; i < length; i++)
                        format = format.replace(/S/, milliSeconds.substring(i, i + 1));
                }
                return format;
            }
            time_1.formatDate = formatDate;
        })(time = uk.time || (uk.time = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
