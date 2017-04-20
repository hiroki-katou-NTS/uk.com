/// <reference path="reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var time;
        (function (time_1) {
            var defaultInputFormat = ["YYYY/MM/DD", "YYYY-MM-DD", "YYYYMMDD", "YYYY/MM", "YYYY-MM", "YYYYMM", "HH:mm"];
            var listEmpire = {
                "明治": "1868/01/01",
                "大正": "1912/07/30",
                "昭和": "1926/12/25",
                "平成": "1989/01/08"
            };
            var dotW = ["日曜日", "月曜日", "火曜日", "水曜日", "木曜日", "金曜日", "土曜日"];
            function getYearMonthJapan(year, month) {
                if (month)
                    return year + "年 " + month + " 月";
                return year;
            }
            class JapanYearMonth {
                constructor(empire, year, month) {
                    this.empire = empire;
                    this.year = year;
                    this.month = month;
                }
                getEmpire() {
                    return this.empire;
                }
                getYear() {
                    return this.year;
                }
                getMonth() {
                    return this.month;
                }
                toString() {
                    return (this.empire === undefined ? "" : this.empire + " ")
                        + (this.year === undefined ? "" : this.year + " 年 ")
                        + (this.month === undefined ? "" : this.month + " 月");
                }
            }
            time_1.JapanYearMonth = JapanYearMonth;
            function yearInJapanEmpire(date) {
                let year = moment.utc(date).year();
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
            class JapanDateMoment {
                constructor(date, outputFormat) {
                    var MomentResult = parseMoment(date, outputFormat);
                    var year = MomentResult.momentObject.year();
                    var month = MomentResult.momentObject.month() + 1;
                }
                toString() {
                    return (this.empire === undefined ? "" : this.empire + " ")
                        + (this.year === undefined ? "" : this.year + " 年 ")
                        + (this.month === undefined ? "" : this.month + " 月")
                        + (this.day === undefined ? "" : this.day + " ");
                }
            }
            time_1.JapanDateMoment = JapanDateMoment;
            function dateInJapanEmpire(date) {
                return new JapanDateMoment(date);
            }
            time_1.dateInJapanEmpire = dateInJapanEmpire;
            /**
            * Format by pattern
            * @param  {number} [seconds]	  Input seconds
            * @param  {string} [formatOption] Format option
            * @return {string}				Formatted duration
            */
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
            /**
            * 日付をフォーマットする
            * @param  {Date}   date	 日付
            * @param  {String} [format] フォーマット
            * @return {String}		  フォーマット済み日付
            */
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
            /**
            * Format YearMonth
            * @param  {Number} [yearMonth]	Input Yearmonth
            * @return {String}				Formatted YearMonth
            */
            function formatYearMonth(yearMonth) {
                var result;
                var num = parseInt(String(yearMonth));
                var year = String(Math.floor(num / 100));
                var month = uk.text.charPadding(String(num % 100), '0', true, 2);
                result = year + '/' + month;
                return result;
            }
            time_1.formatYearMonth = formatYearMonth;
            /**
            * Format by pattern
            * @param  {Date}   [date]		 Input date
            * @param  {String} [inputFormat]  Input format
            * @param  {String} [outputFormat] Output format
            * @return {String}				Formatted date
            */
            function formatPattern(date, inputFormat, outputFormat) {
                outputFormat = uk.text.getISOFormat(outputFormat);
                var inputFormats = (inputFormat) ? inputFormat : defaultInputFormat;
                return moment.utc(date, inputFormats).format(outputFormat);
            }
            time_1.formatPattern = formatPattern;
            class ParseResult {
                constructor(success) {
                    this.success = success;
                }
            }
            class ResultParseTime extends ParseResult {
                constructor(success, minus, hours, minutes, msg) {
                    super(success);
                    this.minus = minus;
                    this.hours = hours;
                    this.minutes = minutes;
                    this.msg = msg || "invalid time format";
                }
                static succeeded(minus, hours, minutes) {
                    return new ResultParseTime(true, minus, hours, minutes);
                }
                static failed() {
                    return new ResultParseTime(false);
                }
                format() {
                    if (!this.success)
                        return "";
                    return (this.minus ? '-' : '') + this.hours + ':' + uk.text.padLeft(String(this.minutes), '0', 2);
                }
                toValue() {
                    if (!this.success)
                        return 0;
                    return (this.minus ? -1 : 1) * (this.hours * 60 + this.minutes);
                }
                getMsg() { return this.msg; }
            }
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
            class ResultParseYearMonth extends ParseResult {
                constructor(success, msg, year, month) {
                    super(success);
                    this.year = year;
                    this.month = month;
                    this.msg = msg || "must yyyymm or yyyy/mm format: year in [1900-9999] and month in [1-12] ";
                }
                static succeeded(year, month) {
                    return new ResultParseYearMonth(true, "", year, month);
                }
                static failed(msg) {
                    return new ResultParseYearMonth(false, msg);
                }
                format() {
                    if (!this.success) {
                        return "";
                    }
                    return this.year + '/' + uk.text.padLeft(String(this.month), '0', 2);
                }
                toValue() {
                    if (!this.success) {
                        return 0;
                    }
                    return (this.year * 100 + this.month);
                }
                getMsg() { return this.msg; }
            }
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
            class ResultParseTimeOfTheDay extends ParseResult {
                constructor(success, msg, hour, minute) {
                    super(success);
                    this.hour = hour;
                    this.minute = minute;
                    this.msg = msg || "time of the days must in format hh:mm with hour in range 0-23; minute in range 00-59";
                }
                static succeeded(hour, minute) {
                    return new ResultParseTimeOfTheDay(true, "", hour, minute);
                }
                static failed(msg) {
                    return new ResultParseTimeOfTheDay(false, msg);
                }
                format() {
                    if (!this.success) {
                        return "";
                    }
                    return this.hour + ':' + uk.text.padLeft(String(this.minute), '0', 2);
                }
                toValue() {
                    if (!this.success) {
                        return 0;
                    }
                    return (this.hour * 100 + this.minute);
                }
                getMsg() { return this.msg; }
            }
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
            class ResultParseYearMonthDate extends ParseResult {
                constructor(success, msg, year, month, date) {
                    super(success);
                    this.year = year;
                    this.month = month;
                    this.date = date;
                    this.msg = msg || "must yyyymm or yyyy/mm format: year in [1900-9999] and month in [1-12] ";
                }
                static succeeded(year, month, date) {
                    return new ResultParseYearMonthDate(true, "", year, month, date);
                }
                static failed(msg) {
                    return new ResultParseYearMonthDate(false, msg);
                }
                format() {
                    if (!this.success) {
                        return "";
                    }
                    return this.year + '/' + uk.text.padLeft(String(this.month), '0', 2) + uk.text.padLeft(String(this.date), '0', 2);
                }
                toValue() {
                    if (!this.success) {
                        return 0;
                    }
                    return (this.year * 10000 + this.month * 100 + this.date);
                }
                getMsg() { return this.msg; }
            }
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
            class MomentResult extends ParseResult {
                constructor(momentObject, outputFormat) {
                    super(true);
                    this.momentObject = momentObject;
                    this.outputFormat = uk.text.getISOFormat(outputFormat);
                }
                succeeded() {
                    this.success = true;
                }
                failed(msg) {
                    this.msg = (msg) ? msg : "Invalid format";
                    this.success = false;
                }
                format() {
                    if (!this.success)
                        return "";
                    return this.momentObject.format(this.outputFormat);
                }
                toValue() {
                    if (!this.success)
                        return null;
                    return this.momentObject;
                }
                toNumber(outputFormat) {
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
                    else {
                        return parseInt(this.momentObject.format(outputFormat).replace(/[^\d]/g, ""));
                    }
                }
                getMsg() { return this.msg; }
            }
            time_1.MomentResult = MomentResult;
            function parseMoment(datetime, outputFormat, inputFormat) {
                var inputFormats = (inputFormat) ? inputFormat : defaultInputFormat;
                var momentObject = moment.utc(datetime, inputFormats);
                var result = new MomentResult(momentObject, outputFormat);
                if (momentObject.isValid())
                    result.succeeded();
                else
                    result.failed();
                return result;
            }
            time_1.parseMoment = parseMoment;
            function UTCDate(year, month, date, hours, minutes, seconds, milliseconds) {
                // Return local time in UTC
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
        })(time = uk.time || (uk.time = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
