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
            var MINUTES_IN_DAY = 24 * 60;
            var defaultInputFormat = [
                "YYYY/M/D",
                "YYYY-M-D",
                "YYYYMMDD",
                "YYYY/M",
                "YYYY-M",
                "YYYYMM",
                "H:mm",
                "H:mm:ss",
                "Hmm",
                "YYYY"];
            var dotW = ["日曜日", "月曜日", "火曜日", "水曜日", "木曜日", "金曜日", "土曜日"];
            function getYearMonthJapan(year, month) {
                if (month)
                    return year + "年 " + month + " 月";
                return year;
            }
            var JapanYearMonth = (function () {
                function JapanYearMonth(empire, year, month) {
                    this.empire = empire;
                    this.year = year;
                    this.month = month;
                }
                JapanYearMonth.prototype.getEmpire = function () {
                    return this.empire;
                };
                JapanYearMonth.prototype.getYear = function () {
                    return this.year;
                };
                JapanYearMonth.prototype.getMonth = function () {
                    return this.month;
                };
                JapanYearMonth.prototype.toString = function () {
                    return (this.empire === undefined ? "" : this.empire + " ")
                        + (this.year === undefined ? "" : this.year + " 年 ")
                        + (this.month === undefined ? "" : this.month + " 月");
                };
                return JapanYearMonth;
            }());
            time_1.JapanYearMonth = JapanYearMonth;
            function yearInJapanEmpire(date) {
                var year = moment.utc(date, defaultInputFormat, true).year();
                if (year == 1868) {
                    return new JapanYearMonth("明治元年");
                }
                if (year <= 1912) {
                    var diff = year - 1867;
                    return new JapanYearMonth("明治 ", diff);
                }
                if (year <= 1926) {
                    var diff = year - 1911;
                    return new JapanYearMonth("大正 ", diff);
                }
                if (year < 1989) {
                    var diff = year - 1925;
                    return new JapanYearMonth("昭和 ", diff);
                }
                if (year == 1989) {
                    return new JapanYearMonth("平成元年 ", diff);
                }
                var diff = year - 1988;
                return new JapanYearMonth("平成 ", diff);
            }
            time_1.yearInJapanEmpire = yearInJapanEmpire;
            function yearmonthInJapanEmpire(yearmonth) {
                if (!(yearmonth instanceof String)) {
                    yearmonth = "" + yearmonth;
                }
                var nguyennien = "元年";
                yearmonth = yearmonth.replace("/", "");
                var year = parseInt(yearmonth.substring(0, 4));
                var month = parseInt(yearmonth.substring(4));
                if (year == 1868) {
                    return new JapanYearMonth("明治元年 ", undefined, month);
                }
                if (year < 1912) {
                    var diff = year - 1867;
                    return new JapanYearMonth("明治 ", diff, month);
                }
                if (year == 1912) {
                    if (month < 8)
                        return new JapanYearMonth("明治 ", 45, month);
                    return new JapanYearMonth("大正元年 ", undefined, month);
                }
                if (year < 1926) {
                    var diff = year - 1911;
                    return new JapanYearMonth("大正 ", diff, month);
                }
                if (year == 1926) {
                    if (month < 12)
                        return new JapanYearMonth("大正", 15, month);
                    return new JapanYearMonth("昭和元年 ", undefined, month);
                }
                if (year < 1989) {
                    var diff = year - 1925;
                    return new JapanYearMonth("昭和 ", diff, month);
                }
                if (year == 1989) {
                    return new JapanYearMonth("平成元年 ", undefined, month);
                }
                var diff = year - 1988;
                return new JapanYearMonth("平成 ", diff, month);
            }
            time_1.yearmonthInJapanEmpire = yearmonthInJapanEmpire;
            var JapanDateMoment = (function () {
                function JapanDateMoment(date, outputFormat) {
                    var MomentResult = parseMoment(date, outputFormat);
                    var year = MomentResult.momentObject.year();
                    var month = MomentResult.momentObject.month() + 1;
                }
                JapanDateMoment.prototype.toString = function () {
                    return (this.empire === undefined ? "" : this.empire + " ")
                        + (this.year === undefined ? "" : this.year + " 年 ")
                        + (this.month === undefined ? "" : this.month + " 月")
                        + (this.day === undefined ? "" : this.day + " ");
                };
                return JapanDateMoment;
            }());
            time_1.JapanDateMoment = JapanDateMoment;
            function dateInJapanEmpire(date) {
                return new JapanDateMoment(date);
            }
            time_1.dateInJapanEmpire = dateInJapanEmpire;
            function formatSeconds(seconds, formatOption) {
                seconds = parseInt(String(seconds));
                var ss = uk.text.padLeft(String(seconds % 60), '0', 2);
                var minutes = Math.floor(seconds / 60);
                var mm = uk.text.padLeft(String(minutes % 60), '0', 2);
                var hours = uk.ntsNumber.trunc(seconds / 60 / 60);
                var h = String(hours);
                return "h:mm:ss"
                    .replace(/h/g, h)
                    .replace(/mm/g, mm)
                    .replace(/ss/g, ss);
            }
            time_1.formatSeconds = formatSeconds;
            function formatDate(date, format) {
                if (!format)
                    format = 'yyyy-MM-dd hh:mm:ss.SSS';
                format = format.replace(/yyyy/g, date.getFullYear());
                format = format.replace(/yy/g, ('0' + (date.getFullYear() % 100)).slice(-2));
                format = format.replace(/MM/g, ('0' + (date.getMonth() + 1)).slice(-2));
                format = format.replace(/dd/g, ('0' + date.getDate()).slice(-2));
                if (format.indexOf("DDD") != -1) {
                    var daystr = "(" + dotW[date.getDay()] + ")";
                    format = format.replace("DDD", daystr);
                }
                else if (format.indexOf("D") != -1) {
                    var daystr = "(" + dotW[date.getDay()].substring(0, 1) + ")";
                    format = format.replace("D", daystr);
                }
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
            function formatYearMonth(yearMonth) {
                var result;
                var num = parseInt(String(yearMonth));
                var year = String(Math.floor(num / 100));
                var month = uk.text.charPadding(String(num % 100), '0', true, 2);
                result = year + '/' + month;
                return result;
            }
            time_1.formatYearMonth = formatYearMonth;
            function formatMonthDayLocalized(monthDay) {
                monthDay = String(monthDay);
                monthDay = uk.text.padLeft(monthDay, '0', 4);
                return moment.utc(monthDay, "MMDD").format("MMMDo");
            }
            time_1.formatMonthDayLocalized = formatMonthDayLocalized;
            function formatPattern(date, inputFormat, outputFormat) {
                outputFormat = uk.text.getISOFormat(outputFormat);
                var inputFormats = (inputFormat) ? inputFormat : defaultInputFormat;
                return moment.utc(date, inputFormats).format(outputFormat);
            }
            time_1.formatPattern = formatPattern;
            var ParseResult = (function () {
                function ParseResult(success) {
                    this.success = success;
                }
                return ParseResult;
            }());
            time_1.ParseResult = ParseResult;
            var ResultParseTime = (function (_super) {
                __extends(ResultParseTime, _super);
                function ResultParseTime(success, minus, hours, minutes, msg) {
                    _super.call(this, success);
                    this.minus = minus;
                    this.hours = hours;
                    this.minutes = minutes;
                    this.msg = msg || "FND_E_TIME";
                }
                ResultParseTime.succeeded = function (minus, hours, minutes) {
                    return new ResultParseTime(true, minus, hours, minutes);
                };
                ResultParseTime.failed = function () {
                    return new ResultParseTime(false);
                };
                ResultParseTime.prototype.format = function () {
                    if (!this.success)
                        return "";
                    return (this.minus ? '-' : '') + this.hours + ':' + uk.text.padLeft(String(this.minutes), '0', 2);
                };
                ResultParseTime.prototype.toValue = function () {
                    if (!this.success)
                        return 0;
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
                    time = (time < 0 && hoursX == 0 ? "-" : "") + hoursX + uk.text.padLeft((Math.abs(time - hoursX * 60)).toString(), '0', 2);
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
            var ResultParseTimeWithSecond = (function (_super) {
                __extends(ResultParseTimeWithSecond, _super);
                function ResultParseTimeWithSecond(success, minus, hours, minutes, second, msg) {
                    _super.call(this, success, minus, hours, minutes, msg);
                    this.second = second;
                }
                ResultParseTimeWithSecond.succeeded = function (minus, hours, minutes, second) {
                    return new ResultParseTimeWithSecond(true, minus, hours, minutes, second);
                };
                ResultParseTimeWithSecond.failed = function () {
                    return new ResultParseTimeWithSecond(false);
                };
                ResultParseTimeWithSecond.prototype.format = function () {
                    if (!this.success)
                        return "";
                    return (this.minus ? '-' : '') + this.hours + ':' + uk.text.padLeft(String(this.minutes), '0', 2)
                        + ':' + uk.text.padLeft(String(this.second), '0', 2);
                };
                ResultParseTimeWithSecond.prototype.toValue = function () {
                    if (!this.success)
                        return 0;
                    return (this.minus ? -1 : 1) * (this.hours * 60 * 60 + this.minutes * 60 + this.second);
                };
                ResultParseTimeWithSecond.prototype.getMsg = function () { return this.msg; };
                return ResultParseTimeWithSecond;
            }(ResultParseTime));
            time_1.ResultParseTimeWithSecond = ResultParseTimeWithSecond;
            function parseTimeWithSecond(time, isMinutes) {
                if (time === undefined || time === null) {
                    return ResultParseTimeWithSecond.failed();
                }
                if (isMinutes) {
                    var totalMinuteX = uk.ntsNumber.trunc(time / 60);
                    var secondX = uk.ntsNumber.trunc(time % 60);
                    var minuteX = uk.ntsNumber.trunc(totalMinuteX % 60);
                    var hoursX = uk.ntsNumber.trunc(totalMinuteX / 60);
                    time = (time < 0 ? "-" : "") + hoursX + ":" + uk.text.padLeft(minuteX.toString(), '0', 2)
                        + ":" + uk.text.padLeft(secondX.toString(), '0', 2);
                }
                if (!(time instanceof String)) {
                    time = time.toString();
                }
                if (time.length < 1 || time.split(':').length > 3 || time.split('-').length > 2
                    || time.lastIndexOf('-') > 0 || (time.length == 1 && !uk.ntsNumber.isNumber(time.charAt(0)))) {
                    return ResultParseTimeWithSecond.failed();
                }
                var minusNumber = time.charAt(0) === '-';
                if (minusNumber) {
                    time = time.split('-')[1];
                }
                var minutes;
                var hours;
                var seconds;
                if (time.indexOf(':') > -1) {
                    var times = time.split(':');
                    seconds = times[2];
                    minutes = times[1];
                    hours = times[0];
                }
                else {
                    time = uk.ntsNumber.trunc(time);
                    time = uk.text.padLeft(time, "0", time.length > 6 ? time.length : 6);
                    var mAS = time.substr(-2, 4);
                    seconds = mAS.substr(-2, 2);
                    minutes = mAS.substr(0, 2);
                    hours = time.substr(0, time.length - 4);
                }
                if (!uk.ntsNumber.isNumber(minutes, false) || parseInt(minutes) > 59 || !uk.ntsNumber.isNumber(hours, false)
                    || !uk.ntsNumber.isNumber(seconds, false) || parseInt(seconds) > 59) {
                    return ResultParseTimeWithSecond.failed();
                }
                return ResultParseTimeWithSecond.succeeded(minusNumber, parseInt(hours), parseInt(minutes), parseInt(seconds));
            }
            time_1.parseTimeWithSecond = parseTimeWithSecond;
            var ResultParseYearMonth = (function (_super) {
                __extends(ResultParseYearMonth, _super);
                function ResultParseYearMonth(success, msg, year, month) {
                    _super.call(this, success);
                    this.year = year;
                    this.month = month;
                    this.msg = msg || "must yyyymm or yyyy/mm format: year in [1900-9999] and month in [1-12] ";
                }
                ResultParseYearMonth.succeeded = function (year, month) {
                    return new ResultParseYearMonth(true, "", year, month);
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
                yearMonth = yearMonth.replace("/", "");
                yearMonth = yearMonth.replace("/", "");
                var checkNum = yearMonth.replace(/[0-9]/g, "");
                if (checkNum.length > 0)
                    return ResultParseYearMonth.failed("yearmonth must contain digits and slashes only!");
                if (yearMonth.length != 6 && yearMonth.length != 5)
                    return ResultParseYearMonth.failed("wrong yearmonth format: must be yyyy/mm or yyyymm");
                var year = parseInt(yearMonth.substring(0, 4));
                var month = parseInt(yearMonth.substring(4));
                if (year < 1900 || year > 9999)
                    return ResultParseYearMonth.failed("wrong year: year must in range 1900-9999");
                if (month < 1 || month > 12)
                    return ResultParseYearMonth.failed("wrong month: month must in range 1-12");
                return ResultParseYearMonth.succeeded(year, month);
            }
            time_1.parseYearMonth = parseYearMonth;
            var ResultParseTimeOfTheDay = (function (_super) {
                __extends(ResultParseTimeOfTheDay, _super);
                function ResultParseTimeOfTheDay(success, msg, hour, minute) {
                    _super.call(this, success);
                    this.hour = hour;
                    this.minute = minute;
                    this.msg = msg || nts.uk.resource.getMessage("FND_E_DATE_YMD");
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
                    return ResultParseTimeOfTheDay.failed(nts.uk.resource.getMessage("FND_E_DATE_YMD"));
                if (stringLength < 3 || stringLength > 4)
                    return ResultParseTimeOfTheDay.failed(nts.uk.resource.getMessage("FND_E_DATE_YMD"));
                var hour = parseInt(timeOfDay.substring(0, stringLength - 2));
                var minute = parseInt(timeOfDay.substring(stringLength - 2));
                if (hour < 0 || hour > 23)
                    return ResultParseTimeOfTheDay.failed(nts.uk.resource.getMessage("FND_E_DATE_YMD"));
                if (minute < 0 || minute > 59)
                    return ResultParseTimeOfTheDay.failed(nts.uk.resource.getMessage("FND_E_DATE_YMD"));
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
                    this.msg = msg || nts.uk.resource.getMessage("FND_E_DATE_YMD");
                }
                ResultParseYearMonthDate.succeeded = function (year, month, date) {
                    return new ResultParseYearMonthDate(true, "", year, month, date);
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
            var MomentResult = (function (_super) {
                __extends(MomentResult, _super);
                function MomentResult(momentObject, outputFormat) {
                    _super.call(this, true);
                    this.min = moment.utc("1900/01/01", "YYYY/MM/DD", true);
                    this.max = moment.utc("9999/12/31", "YYYY/MM/DD", true);
                    this.momentObject = momentObject;
                    this.outputFormat = uk.text.getISOFormat(outputFormat);
                }
                MomentResult.prototype.succeeded = function () {
                    this.success = true;
                };
                MomentResult.prototype.failed = function (msg) {
                    this.msg = (msg) ? msg : "Invalid format";
                    this.success = false;
                };
                MomentResult.prototype.failedWithMessegeId = function (msgID, params) {
                    this.msgID = msgID;
                    this.params = params;
                    this.success = false;
                };
                MomentResult.prototype.format = function () {
                    if (!this.success)
                        return "";
                    return this.momentObject.format(this.outputFormat);
                };
                MomentResult.prototype.toValue = function () {
                    if (!this.success)
                        return null;
                    return this.momentObject;
                };
                MomentResult.prototype.systemMin = function () {
                    return this.min;
                };
                MomentResult.prototype.systemMax = function () {
                    return this.max;
                };
                MomentResult.prototype.toNumber = function (outputFormat) {
                    var dateFormats = ["YYYY/MM/DD", "YYYY-MM-DD", "YYYYMMDD", "date"];
                    var yearMonthFormats = ["YYYY/MM", "YYYY-MM", "YYYYMM", "yearmonth"];
                    if (!this.success)
                        return null;
                    if (dateFormats.indexOf(outputFormat) != -1) {
                        return this.momentObject.year() * 10000 + (this.momentObject.month() + 1) * 100 + this.momentObject.date();
                    }
                    else if (yearMonthFormats.indexOf(outputFormat) != -1) {
                        return this.momentObject.year() * 100 + (this.momentObject.month() + 1);
                    }
                    else if (outputFormat === "time") {
                        return this.momentObject.hours() * 60 + this.momentObject.minutes();
                    }
                    else {
                        return parseInt(this.momentObject.format(outputFormat).replace(/[^\d]/g, ""));
                    }
                };
                MomentResult.prototype.getMsg = function () { return this.msg; };
                MomentResult.prototype.getEmsg = function (name) {
                    if (this.msgID === undefined) {
                        return this.msg;
                    }
                    else {
                        if (name !== undefined) {
                            this.params.unshift(name);
                        }
                        return nts.uk.resource.getMessage(this.msgID, this.params);
                    }
                };
                MomentResult.prototype.getMsgID = function () { return this.msgID === undefined ? "" : this.msgID; };
                return MomentResult;
            }(ParseResult));
            time_1.MomentResult = MomentResult;
            function parseMoment(datetime, outputFormat, inputFormat) {
                var inputFormats = (inputFormat) ? inputFormat : findFormat(outputFormat);
                var momentObject = moment.utc(datetime, inputFormats, true);
                var result = new MomentResult(momentObject, outputFormat);
                if (momentObject.isValid() && (momentObject.isSameOrBefore(result.systemMax()) && momentObject.isSameOrAfter(result.systemMin()))) {
                    result.succeeded();
                }
                else {
                    var parsedFormat = momentObject.creationData().format;
                    var isHasYear = (nts.uk.util.isNullOrEmpty(outputFormat) ? false : outputFormat.indexOf("Y") >= 0) || parsedFormat.indexOf("Y") >= 0;
                    var isHasMonth = (nts.uk.util.isNullOrEmpty(outputFormat) ? false : outputFormat.indexOf("M") >= 0) || parsedFormat.indexOf("M") >= 0;
                    var isHasDay = (nts.uk.util.isNullOrEmpty(outputFormat) ? false : outputFormat.indexOf("D") >= 0) || parsedFormat.indexOf("D") >= 0;
                    if (isHasDay && isHasMonth && isHasYear) {
                        result.failedWithMessegeId("FND_E_DATE_YMD", [result.systemMin().format("YYYY/MM/DD"), result.systemMax().format("YYYY/MM/DD")]);
                    }
                    else if (isHasMonth && isHasYear) {
                        result.failedWithMessegeId("FND_E_DATE_YM", [result.systemMin().format("YYYY/MM"), result.systemMax().format("YYYY/MM")]);
                    }
                    else {
                        result.failedWithMessegeId("FND_E_DATE_Y", [result.systemMin().format("YYYY"), result.systemMax().format("YYYY")]);
                    }
                }
                return result;
            }
            time_1.parseMoment = parseMoment;
            function findFormat(format) {
                if (nts.uk.util.isNullOrEmpty(format)) {
                    return defaultInputFormat;
                }
                if (format === "yearmonth") {
                    format = "YM";
                    format = "YM";
                }
                var uniqueFormat = _.uniq(format.split(""));
                var formats = _.filter(defaultInputFormat, function (dfFormat) {
                    return _.find(uniqueFormat, function (opFormat) {
                        return dfFormat.indexOf(opFormat) >= 0;
                    }) !== undefined;
                });
                return nts.uk.util.isNullOrEmpty(formats) ? defaultInputFormat : formats;
            }
            function UTCDate(year, month, date, hours, minutes, seconds, milliseconds) {
                if (uk.util.isNullOrUndefined(year)) {
                    var currentDate = new Date();
                    year = currentDate.getUTCFullYear();
                    month = (uk.util.isNullOrUndefined(month)) ? currentDate.getUTCMonth() : month;
                    date = (uk.util.isNullOrUndefined(date)) ? currentDate.getUTCDate() : date;
                    hours = (uk.util.isNullOrUndefined(hours)) ? currentDate.getUTCHours() : hours;
                    minutes = (uk.util.isNullOrUndefined(minutes)) ? currentDate.getUTCMinutes() : minutes;
                    seconds = (uk.util.isNullOrUndefined(seconds)) ? currentDate.getUTCSeconds() : seconds;
                    milliseconds = (uk.util.isNullOrUndefined(milliseconds)) ? currentDate.getUTCMilliseconds() : milliseconds;
                    return new Date(Date.UTC(year, month, date, hours, minutes, seconds, milliseconds));
                }
                else {
                    month = (uk.util.isNullOrUndefined(month)) ? 0 : month;
                    date = (uk.util.isNullOrUndefined(date)) ? 1 : date;
                    hours = (uk.util.isNullOrUndefined(hours)) ? 0 : hours;
                    minutes = (uk.util.isNullOrUndefined(minutes)) ? 0 : minutes;
                    seconds = (uk.util.isNullOrUndefined(seconds)) ? 1 : seconds;
                    milliseconds = (uk.util.isNullOrUndefined(milliseconds)) ? 0 : milliseconds;
                    return new Date(Date.UTC(year, month, date, hours, minutes, seconds, milliseconds));
                }
            }
            time_1.UTCDate = UTCDate;
            var DateTimeFormatter = (function () {
                function DateTimeFormatter() {
                    this.shortYmdPattern = /^\d{4}\/\d{1,2}\/\d{1,2}$/;
                    this.shortYmdwPattern = /^\d{4}\/\d{1,2}\/\d{1,2}\(\w+\)$/;
                    this.shortYmPattern = /^\d{4}\/\d{1,2}$/;
                    this.shortMdPattern = /^\d{1,2}\/\d{1,2}$/;
                    this.longYmdPattern = /^\d{4}年\d{1,2}月d{1,2}日$/;
                    this.longYmdwPattern = /^\d{4}年\d{1,2}月d{1,2}日\(\w+\)$/;
                    this.longFPattern = /^\d{4}年度$/;
                    this.longJmdPattern = /^\w{2}\d{1,3}年\d{1,2}月d{1,2}日$/;
                    this.longJmPattern = /^\w{2}\d{1,3}年\d{1,2}月$/;
                    this.fullDateTimeShortPattern = /^\d{4}\/\d{1,2}\/\d{1,2} \d+:\d{2}:\d{2}$/;
                    this.timeShortHmsPattern = /^\d+:\d{2}:\d{2}$/;
                    this.timeShortHmPattern = /^\d+:\d{2}$/;
                    this.days = ['日', '月', '火', '水', '木', '金', '土'];
                }
                DateTimeFormatter.prototype.shortYmd = function (date) {
                    var d = this.dateOf(date);
                    if (this.shortYmdPattern.test(d))
                        return this.format(d);
                };
                DateTimeFormatter.prototype.shortYmdw = function (date) {
                    var d = this.dateOf(date);
                    if (this.shortYmdwPattern.test(d))
                        return d;
                    if (this.shortYmdPattern.test(d)) {
                        var dayStr = this.days[new Date(d).getDay()];
                        return this.format(d) + '(' + dayStr + ')';
                    }
                };
                DateTimeFormatter.prototype.shortYm = function (date) {
                    var d = this.format(this.dateOf(date));
                    if (this.shortYmPattern.test(d))
                        return d;
                    if (this.shortYmdPattern.test(d)) {
                        var end = d.lastIndexOf("/");
                        if (end !== -1)
                            return d.substring(0, end);
                    }
                };
                DateTimeFormatter.prototype.shortMd = function (date) {
                    var d = this.format(this.dateOf(date));
                    if (this.shortMdPattern.test(d))
                        return d;
                    if (this.shortYmdPattern.test(d)) {
                        var start = d.indexOf("/");
                        if (start !== -1)
                            return d.substring(start + 1);
                    }
                };
                DateTimeFormatter.prototype.longYmd = function (date) {
                    var d = this.dateOf(date);
                    if (this.longYmdPattern.test(d))
                        return d;
                    if (this.shortYmdPattern.test(d)) {
                        var mDate = new Date(d);
                        return this.toLongJpDate(mDate);
                    }
                };
                DateTimeFormatter.prototype.longYmdw = function (date) {
                    var d = this.dateOf(date);
                    if (this.longYmdwPattern.test(d))
                        return d;
                    if (this.shortYmdPattern.test(d)) {
                        var mDate = new Date(d);
                        return this.toLongJpDate(mDate) + '(' + this.days[mDate.getDay()] + ')';
                    }
                };
                DateTimeFormatter.prototype.toLongJpDate = function (d) {
                    return d.getFullYear() + '年' + (d.getMonth() + 1) + '月' + d.getDate() + '日';
                };
                DateTimeFormatter.prototype.longF = function (date) {
                    var d = this.dateOf(date);
                    if (this.longFPattern.test(d))
                        return d;
                    if (this.shortYmdPattern.test(d)) {
                        var mDate = new Date(d);
                        return this.fiscalYearOf(mDate) + '年度';
                    }
                };
                DateTimeFormatter.prototype.longJmd = function (date) {
                    var d = this.dateOf(date);
                    if (this.longJmdPattern.test(d))
                        return d;
                    return this.fullJapaneseDateOf(d);
                };
                DateTimeFormatter.prototype.longJm = function (date) {
                    var d = this.dateOf(date);
                    if (this.longJmPattern.test(d))
                        return d;
                    var jpDate = this.fullJapaneseDateOf(d);
                    var start = jpDate.indexOf("月");
                    if (start !== -1) {
                        return jpDate.substring(0, start + 1);
                    }
                };
                DateTimeFormatter.prototype.fullJapaneseDateOf = function (date) {
                    if (this.shortYmdPattern.test(date)) {
                        var d = new Date(date);
                        return d.toLocaleDateString("ja-JP-u-ca-japanese", { era: 'short' });
                    }
                    return date;
                };
                DateTimeFormatter.prototype.fiscalYearOf = function (date) {
                    if (date < new Date(date.getFullYear(), 3, 1))
                        return date.getFullYear() - 1;
                    return date.getFullYear();
                };
                DateTimeFormatter.prototype.dateOf = function (dateTime) {
                    if (this.fullDateTimeShortPattern.test(dateTime)) {
                        return dateTime.split(" ")[0];
                    }
                    return dateTime;
                };
                DateTimeFormatter.prototype.timeOf = function (dateTime) {
                    if (this.fullDateTimeShortPattern.test(dateTime)) {
                        return dateTime.split(" ")[1];
                    }
                    return dateTime;
                };
                DateTimeFormatter.prototype.timeShortHm = function (time) {
                    var t = this.timeOf(time);
                    if (this.timeShortHmPattern.test(t))
                        return t;
                    if (this.timeShortHmsPattern.test(t)) {
                        return t.substring(0, t.lastIndexOf(":"));
                    }
                };
                DateTimeFormatter.prototype.timeShortHms = function (time) {
                    var t = this.timeOf(time);
                    if (this.timeShortHmsPattern.test(t))
                        return t;
                };
                DateTimeFormatter.prototype.clockShortHm = function (time) {
                    return this.timeShortHm(time);
                };
                DateTimeFormatter.prototype.fullDateTimeShort = function (dateTime) {
                    if (this.fullDateTimeShortPattern.test(dateTime))
                        return dateTime;
                };
                DateTimeFormatter.prototype.format = function (date) {
                    return date;
                };
                return DateTimeFormatter;
            }());
            time_1.DateTimeFormatter = DateTimeFormatter;
            function getFormatter() {
                switch (systemLanguage) {
                    case 'ja':
                        return new DateTimeFormatter();
                    case 'en':
                        return null;
                }
            }
            time_1.getFormatter = getFormatter;
            function applyFormat(format, dateTime, formatter) {
                if (formatter === undefined)
                    formatter = getFormatter();
                switch (format) {
                    case 'Short_YMD':
                        return formatter.shortYmd(dateTime);
                    case 'Short_YMDW':
                        return formatter.shortYmdw(dateTime);
                    case 'Short_YM':
                        return formatter.shortYm(dateTime);
                    case 'Short_MD':
                        return formatter.shortMd(dateTime);
                    case 'Long_YMD':
                        return formatter.longYmd(dateTime);
                    case 'Long_YMDW':
                        return formatter.longYmdw(dateTime);
                    case 'Long_F':
                        return formatter.longF(dateTime);
                    case 'Long_JMD':
                        return formatter.longJmd(dateTime);
                    case 'Long_JM':
                        return formatter.longJm(dateTime);
                    case 'Time_Short_HM':
                        return formatter.timeShortHm(dateTime);
                    case 'Time_Short_HMS':
                        return formatter.timeShortHms(dateTime);
                    case 'Clock_Short_HM':
                        return formatter.clockShortHm(dateTime);
                    case 'DateTime_Short_YMDHMS':
                        return formatter.fullDateTimeShort(dateTime);
                }
            }
            time_1.applyFormat = applyFormat;
            function isEndOfMonth(value, format) {
                var currentDate = moment(value, format);
                if (currentDate.isValid()) {
                    return currentDate.daysInMonth() === currentDate.date();
                }
                return false;
            }
            time_1.isEndOfMonth = isEndOfMonth;
            function convertJapaneseDateToGlobal(japaneseDate) {
                var inputDate = _.clone(japaneseDate);
                var endEraSymbolIndex = -1;
                var currentEra;
                var eraAcceptFormats = ["YYMMDD", "YY/MM/DD", "YY/M/DD", "YY/MM/D", "YY/M/D", "Y/MM/DD", "Y/M/DD", "Y/MM/D", "Y/M/D"];
                for (var _i = 0, _a = __viewContext.env.japaneseEras; _i < _a.length; _i++) {
                    var i = _a[_i];
                    if (inputDate.indexOf(i.name) >= 0) {
                        endEraSymbolIndex = inputDate.indexOf(i.name) + i.name.length;
                        currentEra = i;
                        break;
                    }
                    else if (inputDate.indexOf(i.symbol) >= 0) {
                        endEraSymbolIndex = inputDate.indexOf(i.symbol) + i.symbol.length;
                        currentEra = i;
                        break;
                    }
                }
                if (endEraSymbolIndex > -1) {
                    var startEraDate = moment(currentEra.start, "YYYY-MM-DD");
                    var inputEraDate = inputDate.substring(endEraSymbolIndex);
                    var tempEra = moment.utc(inputEraDate, eraAcceptFormats, true);
                    if (tempEra.isValid()) {
                        return startEraDate.add(tempEra.format("YY"), "Y")
                            .set({ 'month': tempEra.month(), "date": tempEra.date() })
                            .format("YYYY/MM/DD");
                    }
                }
                return japaneseDate;
            }
            time_1.convertJapaneseDateToGlobal = convertJapaneseDateToGlobal;
        })(time = uk.time || (uk.time = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=time.js.map