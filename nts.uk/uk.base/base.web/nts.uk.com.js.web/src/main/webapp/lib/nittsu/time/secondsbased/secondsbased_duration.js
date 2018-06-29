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
            var secondsBased;
            (function (secondsBased) {
                var duration;
                (function (duration_1) {
                    var ResultParseSecondsBasedDuration = (function (_super) {
                        __extends(ResultParseSecondsBasedDuration, _super);
                        function ResultParseSecondsBasedDuration(success, minus, hours, minutes, seconds, msg) {
                            _super.call(this, success);
                            this.minus = minus;
                            this.hours = hours;
                            this.minutes = minutes;
                            this.seconds = seconds;
                            this.msg = msg || "FND_E_TIME";
                        }
                        ResultParseSecondsBasedDuration.prototype.format = function () {
                            if (!this.success)
                                return "";
                            return (this.minus ? '-' : '') + this.hours + ':' + uk.text.padLeft(String(this.minutes), '0', 2)
                                + ':' + uk.text.padLeft(String(this.seconds), '0', 2);
                        };
                        ResultParseSecondsBasedDuration.prototype.toValue = function () {
                            if (!this.success)
                                return NaN;
                            return (this.minus ? -1 : 1) * (this.hours * 60 * 60 + this.minutes * 60 + this.seconds);
                        };
                        ResultParseSecondsBasedDuration.prototype.getMsg = function () {
                            return this.msg;
                        };
                        ResultParseSecondsBasedDuration.succeeded = function (minus, hours, minutes, seconds) {
                            return new ResultParseSecondsBasedDuration(true, minus, hours, minutes, seconds);
                        };
                        ResultParseSecondsBasedDuration.failed = function () {
                            return new ResultParseSecondsBasedDuration(false);
                        };
                        return ResultParseSecondsBasedDuration;
                    }(time.ParseResult));
                    duration_1.ResultParseSecondsBasedDuration = ResultParseSecondsBasedDuration;
                    function parseString(source) {
                        var isNegative = source.indexOf('-') === 0;
                        var hourPart;
                        var minutePart;
                        var secondPart;
                        if (isNegative) {
                            return ResultParseSecondsBasedDuration.failed();
                        }
                        if (source.indexOf(':') !== -1) {
                            var parts = source.split(':');
                            if (parts.length !== 3) {
                                return ResultParseSecondsBasedDuration.failed();
                            }
                            hourPart = Math.abs(Number(parts[0]));
                            minutePart = Number(parts[1]);
                            secondPart = Number(parts[2]);
                            if (!nts.uk.ntsNumber.isNumber(hourPart, false, undefined, undefined)
                                || !nts.uk.ntsNumber.isNumber(minutePart, false, undefined, undefined)
                                || !nts.uk.ntsNumber.isNumber(secondPart, false, undefined, undefined)) {
                                return ResultParseSecondsBasedDuration.failed();
                            }
                        }
                        else {
                            var integerized = Number(source);
                            if (isNaN(integerized)) {
                                return ResultParseSecondsBasedDuration.failed();
                            }
                            var regularized = Math.abs(integerized);
                            if (!nts.uk.ntsNumber.isNumber(regularized, false, undefined, undefined)) {
                                return ResultParseSecondsBasedDuration.failed();
                            }
                            hourPart = Math.floor(regularized / 10000);
                            minutePart = regularized / 100;
                            secondPart = regularized % 100;
                        }
                        if (!isFinite(hourPart) || !isFinite(minutePart) || !isFinite(secondPart)) {
                            return ResultParseSecondsBasedDuration.failed();
                        }
                        if (minutePart >= 60 || secondPart >= 60) {
                            return ResultParseSecondsBasedDuration.failed();
                        }
                        var result = ResultParseSecondsBasedDuration.succeeded(isNegative, hourPart, minutePart, secondPart);
                        var values = result.toValue();
                        var maxValue = 24 * 60 * 60;
                        if (values >= maxValue) {
                            return ResultParseSecondsBasedDuration.failed();
                        }
                        return result;
                    }
                    duration_1.parseString = parseString;
                    function create(timeAsSeconds) {
                        var duration = secondsBased.createBase(timeAsSeconds);
                        uk.util.accessor.defineInto(duration)
                            .get("typeName", function () { return "DurationSecondsBasedTime"; })
                            .get("asHoursDouble", function () { return timeAsSeconds / (60 * 60); })
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
                            + duration.asHoursInt + ":" + duration.minutePartText + ":" + duration.secondPartText;
                    }
                })(duration = secondsBased.duration || (secondsBased.duration = {}));
            })(secondsBased = time.secondsBased || (time.secondsBased = {}));
        })(time = uk.time || (uk.time = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=secondsbased_duration.js.map