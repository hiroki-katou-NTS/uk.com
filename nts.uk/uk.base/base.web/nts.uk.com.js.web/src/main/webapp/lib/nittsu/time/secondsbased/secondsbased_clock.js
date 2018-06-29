var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var time;
        (function (time) {
            var secondsBased;
            (function (secondsBased) {
                var clock;
                (function (clock_1) {
                    function create() {
                        var args = [];
                        for (var _i = 0; _i < arguments.length; _i++) {
                            args[_i - 0] = arguments[_i];
                        }
                        var timeAsSeconds = parseAsClock(args);
                        var clock = secondsBased.createBase(timeAsSeconds);
                        var positivizedSeconds = function () { return (timeAsSeconds >= 0)
                            ? timeAsSeconds
                            : timeAsSeconds + (1 + Math.floor(-timeAsSeconds / secondsBased.SECOND_IN_DAY)) * secondsBased.SECOND_IN_DAY; };
                        var daysOffset = function () { return uk.ntsNumber.trunc(clock.isNegative ? (timeAsSeconds + 1) / secondsBased.SECOND_IN_DAY - 1
                            : timeAsSeconds / secondsBased.SECOND_IN_DAY); };
                        var positiveSeconds = positivizedSeconds();
                        var secondStr = String(positiveSeconds);
                        var pointIndex = secondStr.indexOf('.');
                        var secondPart;
                        if (pointIndex > -1) {
                            var fraction = secondStr.substring(pointIndex + 1);
                            positiveSeconds = Math.floor(positiveSeconds);
                            secondStr = String(positiveSeconds % secondsBased.SECOND_IN_MINUTE) + "." + fraction;
                            secondPart = Number(secondStr);
                        }
                        else {
                            secondPart = positivizedSeconds() % secondsBased.SECOND_IN_MINUTE;
                        }
                        uk.util.accessor.defineInto(clock)
                            .get("typeName", function () { return "ClockSecondsBasedTime"; })
                            .get("daysOffset", daysOffset)
                            .get("secondPart", function () { return secondPart; })
                            .get("minutePart", function () { return uk.ntsNumber.trunc((positivizedSeconds() % secondsBased.SECOND_IN_DAY) / secondsBased.SECOND_IN_MINUTE); })
                            .get("hourPart", function () { return uk.ntsNumber.trunc(clock.minutePart / secondsBased.MINUTE_IN_HOUR); })
                            .get("clockTextInDay", function () { return format.clockTextInDay(clock); });
                        clock.formatById = function (formatId) {
                            return format.byId(formatId, clock);
                        };
                        return clock;
                    }
                    clock_1.create = create;
                    function parseAsClock(args) {
                        var result;
                        if (uk.types.matchArguments(args, ["number"])) {
                            result = args[0];
                        }
                        else if (uk.types.matchArguments(args, ["number", "number", "number", "number"])) {
                            var daysOffset = args[0];
                            var hourPart = args[1];
                            var minutePart = args[2];
                            var secondPart = args[3];
                            result = daysOffset * secondsBased.SECOND_IN_DAY + hourPart * secondsBased.SECOND_IN_HOUR + minutePart * secondsBased.SECOND_IN_MINUTE + secondPart;
                        }
                        return result;
                    }
                    var format;
                    (function (format) {
                        function byId(formatId, clock) {
                            switch (formatId) {
                                case "Clock_Short_HMS":
                                    return short.make(clock);
                                default:
                                    return "";
                            }
                        }
                        format.byId = byId;
                        function clockTextInDay(clock) {
                            return clock.hourPart + ":" + clock.minutePartText + ":" + clock.secondPartText;
                        }
                        format.clockTextInDay = clockTextInDay;
                        var short;
                        (function (short) {
                            function make(clock) {
                                return sign(clock) + hours(clock) + ":" + clock.minutePartText + ":" + clock.secondPartText;
                            }
                            short.make = make;
                            function sign(clock) {
                                return clock.daysOffset < 0 ? "-" : "";
                            }
                            function hours(clock) {
                                return clock.daysOffset < 0 ? clock.hourPart : clock.daysOffset * 24 + clock.hourPart;
                            }
                        })(short || (short = {}));
                    })(format || (format = {}));
                })(clock = secondsBased.clock || (secondsBased.clock = {}));
            })(secondsBased = time.secondsBased || (time.secondsBased = {}));
        })(time = uk.time || (uk.time = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=secondsbased_clock.js.map