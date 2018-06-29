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
        (function (time) {
            var minutesBased;
            (function (minutesBased) {
                var duration;
                (function (duration_1) {
                    var ResultParseMiuntesBasedDuration = (function (_super) {
                        __extends(ResultParseMiuntesBasedDuration, _super);
                        function ResultParseMiuntesBasedDuration(success, minus, hours, minutes, msg) {
                            _super.call(this, success);
                            this.minus = minus;
                            this.hours = hours;
                            this.minutes = minutes;
                            this.msg = msg || "FND_E_TIME";
                        }
                        ResultParseMiuntesBasedDuration.prototype.format = function () {
                            if (!this.success)
                                return "";
                            return (this.minus ? '-' : '') + this.hours + ':' + uk.text.padLeft(String(this.minutes), '0', 2);
                        };
                        ResultParseMiuntesBasedDuration.prototype.toValue = function () {
                            if (!this.success)
                                return NaN;
                            return (this.minus ? -1 : 1) * (this.hours * 60 + this.minutes);
                        };
                        ResultParseMiuntesBasedDuration.prototype.getMsg = function () {
                            return this.msg;
                        };
                        ResultParseMiuntesBasedDuration.succeeded = function (minus, hours, minutes) {
                            return new ResultParseMiuntesBasedDuration(true, minus, hours, minutes);
                        };
                        ResultParseMiuntesBasedDuration.failed = function () {
                            return new ResultParseMiuntesBasedDuration(false);
                        };
                        return ResultParseMiuntesBasedDuration;
                    }(time.ParseResult));
                    duration_1.ResultParseMiuntesBasedDuration = ResultParseMiuntesBasedDuration;
                    function parseString(source) {
                        var isNegative = source.indexOf('-') === 0;
                        var hourPart;
                        var minutePart;
                        if (source.indexOf(':') !== -1) {
                            var parts = source.split(':');
                            if (parts.length !== 2) {
                                return ResultParseMiuntesBasedDuration.failed();
                            }
                            hourPart = Math.abs(Number(parts[0]));
                            minutePart = Number(parts[1]);
                            if (!nts.uk.ntsNumber.isNumber(hourPart, false, undefined, undefined)
                                || !nts.uk.ntsNumber.isNumber(minutePart, false, undefined, undefined)) {
                                return ResultParseMiuntesBasedDuration.failed();
                            }
                        }
                        else {
                            var integerized = Number(source);
                            if (isNaN(integerized)) {
                                return ResultParseMiuntesBasedDuration.failed();
                            }
                            var regularized = Math.abs(integerized);
                            if (!nts.uk.ntsNumber.isNumber(regularized, false, undefined, undefined)) {
                                return ResultParseMiuntesBasedDuration.failed();
                            }
                            hourPart = Math.floor(regularized / 100);
                            minutePart = regularized % 100;
                        }
                        if (!isFinite(hourPart) || !isFinite(minutePart)) {
                            return ResultParseMiuntesBasedDuration.failed();
                        }
                        if (minutePart >= 60) {
                            return ResultParseMiuntesBasedDuration.failed();
                        }
                        return ResultParseMiuntesBasedDuration.succeeded(isNegative, hourPart, minutePart);
                    }
                    duration_1.parseString = parseString;
                    function create(timeAsMinutes) {
                        var duration = minutesBased.createBase(timeAsMinutes);
                        uk.util.accessor.defineInto(duration)
                            .get("typeName", function () { return "DurationMinutesBasedTime"; })
                            .get("asHoursDouble", function () { return timeAsMinutes / 60; })
                            .get("asHoursInt", function () { return uk.ntsNumber.trunc(duration.asHoursDouble); })
                            .get("text", function () { return createText(duration); });
                        duration.formatById = function (formatId) {
                            switch (formatId) {
                                default: return createText(duration);
                            }
                        };
                        return duration;
                    }
                    duration_1.create = create;
                    function createText(duration) {
                        return (duration.isNegative ? "-" : "")
                            + duration.asHoursInt + ":" + duration.minutePartText;
                    }
                })(duration = minutesBased.duration || (minutesBased.duration = {}));
            })(minutesBased = time.minutesBased || (time.minutesBased = {}));
        })(time = uk.time || (uk.time = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=minutesbased_duration.js.map