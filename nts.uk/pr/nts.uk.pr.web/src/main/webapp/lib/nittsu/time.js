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
                var month = nts.uk.text.charPadding(String(num % 100), '0', true, 2);
                result = year + '/' + month;
                return result;
            }
            time_1.formatYearMonth = formatYearMonth;
            function formatSeconds(seconds, formatOption) {
                seconds = parseInt(String(seconds));
                var ss = uk.text.padLeft(String(seconds % 60), '0', 2);
                var minutes = Math.floor(seconds / 60);
                var mm = uk.text.padLeft(String(minutes % 60), '0', 2);
                var hours = uk.ntsNumber.trunc(seconds / 60 / 60);
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
                function ResultParseTime(success, minus, hours, minutes, msg) {
                    _super.call(this, success);
                    this.minus = minus;
                    this.hours = hours;
                    this.minutes = minutes;
                    this.msg = msg || "invalid time format";
                }
                ResultParseTime.succeeded = function (minus, hours, minutes) {
                    return new ResultParseTime(true, minus, hours, minutes);
                };
                ResultParseTime.failed = function () {
                    return new ResultParseTime(false);
                };
                ResultParseTime.prototype.format = function () {
                    if (!this.success) {
                        return "";
                    }
                    return (this.minus ? '-' : '') + this.hours + ':' + uk.text.padLeft(String(this.minutes), '0', 2);
                };
                ResultParseTime.prototype.toValue = function () {
                    if (!this.success) {
                        return 0;
                    }
                    return (this.minus ? -1 : 1) * (this.hours * 60 + this.minutes);
                };
                ResultParseTime.prototype.getMsg = function () { return this.msg; };
                return ResultParseTime;
            }(ParseResult));
            time_1.ResultParseTime = ResultParseTime;
            function parseTime(time, isMinutes) {
                if (time === undefined || time === null) {
                    return ResultParseTime.failed();
                }
                if (isMinutes) {
                    var hoursX = uk.ntsNumber.trunc(time / 60);
                    time = hoursX + uk.text.padLeft((Math.abs(time - hoursX * 60)).toString(), '0', 2);
                }
                if (!(time instanceof String)) {
                    time = time.toString();
                }
                if (time.length < 1 || time.split(':').length > 2 || time.split('-').length > 2
                    || time.lastIndexOf('-') > 0 || (time.length == 1 && !uk.ntsNumber.isNumber(time.charAt(0)))) {
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
                    time = uk.ntsNumber.trunc(time);
                    time = uk.text.padLeft(time, "0", time.length > 4 ? time.length : 4);
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
                function ResultParseYearMonth(success, year, month, msg) {
                    _super.call(this, success);
                    this.year = year;
                    this.month = month;
                    this.msg = msg || "must yyyymm or yyyy/mm format: year in [1900-9999] and month in [1-12] ";
                }
                ResultParseYearMonth.succeeded = function (year, month) {
                    return new ResultParseYearMonth(true, year, month);
                };
                ResultParseYearMonth.failed = function (msg) {
                    return new ResultParseYearMonth(false, msg);
                };
                ResultParseYearMonth.prototype.format = function () {
                    if (!this.success) {
                        return "";
                    }
                    return this.year + '/' + uk.text.padLeft(String(this.month), '0', 2);
                };
                ResultParseYearMonth.prototype.toValue = function () {
                    if (!this.success) {
                        return 0;
                    }
                    return (this.year * 100 + this.month);
                };
                ResultParseYearMonth.prototype.getMsg = function () { return this.msg; };
                return ResultParseYearMonth;
            }(ParseResult));
            time_1.ResultParseYearMonth = ResultParseYearMonth;
            function parseYearMonth(yearMonth) {
                if (yearMonth === undefined || yearMonth === null) {
                    return ResultParseYearMonth.failed("yearmonth can not empty!");
                }
                if (!(yearMonth instanceof String)) {
                    yearMonth = yearMonth.toString();
                }
                var stringLengh = yearMonth.length;
                var values = yearMonth.split('/');
                if ((stringLengh < 6 || stringLengh > 7) || values.length > 2 || yearMonth.replace(/[0-9/]/g, "").length > 0) {
                    return ResultParseYearMonth.failed("invalid format. must be yyyymm or yyyy/mm!");
                }
                var indexOf = yearMonth.lastIndexOf('/');
                var year, month;
                if (indexOf > -1 && indexOf !== 4) {
                    return ResultParseYearMonth.failed('invalid format. must be yyyy/mm');
                }
                else if (indexOf === 4) {
                    year = values[0];
                    month = values[1];
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
            var ResultParseTimeOfTheDay = (function (_super) {
                __extends(ResultParseTimeOfTheDay, _super);
                function ResultParseTimeOfTheDay(success, msg, hour, minute) {
                    _super.call(this, success);
                    this.hour = hour;
                    this.minute = minute;
                    this.msg = msg || "time of the days must in format hh:mm with hour in range 0-23; minute in range 00-59";
                }
                ResultParseTimeOfTheDay.succeeded = function (hour, minute) {
                    return new ResultParseTimeOfTheDay(true, "", hour, minute);
                };
                ResultParseTimeOfTheDay.failed = function (msg) {
                    return new ResultParseTimeOfTheDay(false, msg);
                };
                ResultParseTimeOfTheDay.prototype.format = function () {
                    if (!this.success) {
                        return "";
                    }
                    return this.hour + ':' + uk.text.padLeft(String(this.minute), '0', 2);
                };
                ResultParseTimeOfTheDay.prototype.toValue = function () {
                    if (!this.success) {
                        return 0;
                    }
                    return (this.hour * 100 + this.minute);
                };
                ResultParseTimeOfTheDay.prototype.getMsg = function () { return this.msg; };
                return ResultParseTimeOfTheDay;
            }(ParseResult));
            time_1.ResultParseTimeOfTheDay = ResultParseTimeOfTheDay;
            function parseTimeOfTheDay(timeOfDay) {
                if (timeOfDay === undefined || timeOfDay === null) {
                    return ResultParseTimeOfTheDay.failed("time of the day cannot be empty!");
                }
                if (!(timeOfDay instanceof String)) {
                    timeOfDay = timeOfDay.toString();
                }
                timeOfDay = timeOfDay.replace(":", "");
                var checkNum = timeOfDay.replace(/[0-9]/g, "");
                var stringLength = timeOfDay.length;
                if (checkNum.length > 0)
                    return ResultParseTimeOfTheDay.failed("time of the day accept digits and ':' only");
                if (stringLength < 3 || stringLength > 4)
                    return ResultParseTimeOfTheDay.failed("invalid time of the day format");
                var hour = parseInt(timeOfDay.substring(0, stringLength - 2));
                var minute = parseInt(timeOfDay.substring(stringLength - 2));
                //console.log(checkNum.substring(0,stringLength-2));
                if (hour < 0 || hour > 23)
                    return ResultParseTimeOfTheDay.failed("invalid: hour must in range 0-23");
                if (minute < 0 || minute > 59)
                    return ResultParseTimeOfTheDay.failed("invalid: minute must in range 0-59");
                return ResultParseTimeOfTheDay.succeeded(hour, minute);
            }
            time_1.parseTimeOfTheDay = parseTimeOfTheDay;
            var ResultParseYearMonthDate = (function (_super) {
                __extends(ResultParseYearMonthDate, _super);
                function ResultParseYearMonthDate(success, msg, year, month, date) {
                    _super.call(this, success);
                    this.year = year;
                    this.month = month;
                    this.date = date;
                    this.msg = msg || "must yyyymm or yyyy/mm format: year in [1900-9999] and month in [1-12] ";
                }
                ResultParseYearMonthDate.succeeded = function (year, month, date) {
                    return new ResultParseYearMonthDate(true, year, month, date);
                };
                ResultParseYearMonthDate.failed = function (msg) {
                    return new ResultParseYearMonthDate(false, msg);
                };
                ResultParseYearMonthDate.prototype.format = function () {
                    if (!this.success) {
                        return "";
                    }
                    return this.year + '/' + uk.text.padLeft(String(this.month), '0', 2) + uk.text.padLeft(String(this.date), '0', 2);
                };
                ResultParseYearMonthDate.prototype.toValue = function () {
                    if (!this.success) {
                        return 0;
                    }
                    return (this.year * 10000 + this.month * 100 + this.date);
                };
                ResultParseYearMonthDate.prototype.getMsg = function () { return this.msg; };
                return ResultParseYearMonthDate;
            }(ParseResult));
            time_1.ResultParseYearMonthDate = ResultParseYearMonthDate;
            function parseYearMonthDate(yearMonthDate) {
                if (yearMonthDate === undefined || yearMonthDate === null) {
                    return ResultParseYearMonthDate.failed("full date can not empty!");
                }
                if (!(yearMonthDate instanceof String)) {
                    yearMonthDate = yearMonthDate.toString();
                }
                yearMonthDate = yearMonthDate.replace("/", "");
                yearMonthDate = yearMonthDate.replace("/", "");
                var checkNum = yearMonthDate.replace(/[0-9]/g, "");
                if (checkNum.length !== 0)
                    return ResultParseYearMonthDate.failed("full date must contain digits and slashes only");
                if (yearMonthDate.length != 8)
                    return ResultParseYearMonthDate.failed("full date format must be yyyy/mm/dd or yyyymmdd");
                var year = parseInt(yearMonthDate.substring(0, 4));
                if (year < 1900 || year > 9999) {
                    return ResultParseYearMonthDate.failed("invalid: year must in range 1900-9999");
                }
                var month = parseInt(yearMonthDate.substring(4, 6));
                if (month < 1 || month > 12)
                    return ResultParseYearMonthDate.failed("invalid: month must in range 1-12");
                var date = parseInt(yearMonthDate.substring(6));
                var maxDate = 30;
                switch (month) {
                    case 2:
                        if (year % 400 == 0) {
                            maxDate = 29;
                        }
                        else if (year % 4 == 0 && year % 25 != 0) {
                            maxDate = 29;
                        }
                        else {
                            maxDate = 28;
                        }
                        break;
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        maxDate = 31;
                        break;
                    default:
                        maxDate = 30;
                        break;
                }
                if (date < 1 || date > maxDate)
                    return ResultParseYearMonthDate.failed("invalid: month = " + month + ", so your date must in range 1-" + maxDate);
                return ResultParseYearMonthDate.succeeded(year, month, date);
            }
            time_1.parseYearMonthDate = parseYearMonthDate;
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
