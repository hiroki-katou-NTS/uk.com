var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var time;
        (function (time) {
            var minutesBased;
            (function (minutesBased) {
                var clock;
                (function (clock_1) {
                    (function (DayAttr) {
                        DayAttr[DayAttr["THE_PREVIOUS_DAY"] = 0] = "THE_PREVIOUS_DAY";
                        DayAttr[DayAttr["THE_PRESENT_DAY"] = 1] = "THE_PRESENT_DAY";
                        DayAttr[DayAttr["THE_NEXT_DAY"] = 2] = "THE_NEXT_DAY";
                        DayAttr[DayAttr["TWO_DAY_LATER"] = 3] = "TWO_DAY_LATER";
                    })(clock_1.DayAttr || (clock_1.DayAttr = {}));
                    var DayAttr = clock_1.DayAttr;
                    var DayAttr;
                    (function (DayAttr) {
                        function fromValue(value) {
                            switch (value) {
                                case 0: return DayAttr.THE_PREVIOUS_DAY;
                                case 1: return DayAttr.THE_PRESENT_DAY;
                                case 2: return DayAttr.THE_NEXT_DAY;
                                case 3: return DayAttr.TWO_DAY_LATER;
                                default: new Error("invalid value: " + value);
                            }
                        }
                        DayAttr.fromValue = fromValue;
                        function fromDaysOffset(daysOffset) {
                            switch (daysOffset) {
                                case -1: return DayAttr.THE_PREVIOUS_DAY;
                                case 0: return DayAttr.THE_PRESENT_DAY;
                                case 1: return DayAttr.THE_NEXT_DAY;
                                case 2: return DayAttr.TWO_DAY_LATER;
                                default: new Error("invalid daysOffset: " + daysOffset);
                            }
                        }
                        DayAttr.fromDaysOffset = fromDaysOffset;
                        function toDaysOffset(dayAttr) {
                            switch (dayAttr) {
                                case DayAttr.THE_PREVIOUS_DAY: return -1;
                                case DayAttr.THE_PRESENT_DAY: return 0;
                                case DayAttr.THE_NEXT_DAY: return 1;
                                case DayAttr.TWO_DAY_LATER: return 2;
                                default: new Error("invalid dayAttr: " + dayAttr);
                            }
                        }
                        DayAttr.toDaysOffset = toDaysOffset;
                        function toText(dayAttr) {
                            switch (dayAttr) {
                                case DayAttr.THE_PREVIOUS_DAY: return "前日";
                                case DayAttr.THE_PRESENT_DAY: return "当日";
                                case DayAttr.THE_NEXT_DAY: return "翌日";
                                case DayAttr.TWO_DAY_LATER: return "翌々日";
                                default: new Error("invalid dayAttr: " + dayAttr);
                            }
                        }
                        DayAttr.toText = toText;
                    })(DayAttr = clock_1.DayAttr || (clock_1.DayAttr = {}));
                    function create() {
                        var args = [];
                        for (var _i = 0; _i < arguments.length; _i++) {
                            args[_i - 0] = arguments[_i];
                        }
                        var timeAsMinutes = parseAsClock(args);
                        var clock = minutesBased.createBase(timeAsMinutes);
                        var positivizedMinutes = function () { return (timeAsMinutes >= 0)
                            ? timeAsMinutes
                            : timeAsMinutes + (1 + Math.floor(-timeAsMinutes / minutesBased.MINUTES_IN_DAY)) * minutesBased.MINUTES_IN_DAY; };
                        var daysOffset = function () { return uk.ntsNumber.trunc(clock.isNegative ? (timeAsMinutes + 1) / minutesBased.MINUTES_IN_DAY - 1
                            : timeAsMinutes / minutesBased.MINUTES_IN_DAY); };
                        var positiveMinutes = positivizedMinutes();
                        var minuteStr = String(positiveMinutes);
                        var pointIndex = minuteStr.indexOf('.');
                        var minutePart;
                        if (pointIndex > -1) {
                            var fraction = minuteStr.substring(pointIndex + 1);
                            positiveMinutes = Math.floor(positiveMinutes);
                            minuteStr = String(positiveMinutes % 60) + "." + fraction;
                            minutePart = Number(minuteStr);
                        }
                        else {
                            minutePart = positivizedMinutes() % 60;
                        }
                        uk.util.accessor.defineInto(clock)
                            .get("typeName", function () { return "ClockMinutesBasedTime"; })
                            .get("daysOffset", daysOffset)
                            .get("hourPart", function () { return Math.floor((positivizedMinutes() % minutesBased.MINUTES_IN_DAY) / 60); })
                            .get("minutePart", function () { return minutePart; })
                            .get("dayAttr", function () { return DayAttr.fromDaysOffset(daysOffset()); })
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
                        else if (uk.types.matchArguments(args, ["number", "number", "number"])) {
                            var daysOffset = args[0];
                            var hourPart = args[1];
                            var minutePart = args[2];
                            result = daysOffset * minutesBased.MINUTES_IN_DAY + hourPart * 60 + minutePart;
                        }
                        return result;
                    }
                    var format;
                    (function (format) {
                        function byId(formatId, clock) {
                            switch (formatId) {
                                case "Clock_Short_HM":
                                    return short.make(clock);
                                case "ClockDay_Short_HM":
                                    return long.make(clock);
                            }
                        }
                        format.byId = byId;
                        function clockTextInDay(clock) {
                            return clock.hourPart + ":" + clock.minutePartText;
                        }
                        format.clockTextInDay = clockTextInDay;
                        var short;
                        (function (short) {
                            function make(clock) {
                                return sign(clock) + hours(clock) + ":" + clock.minutePartText;
                            }
                            short.make = make;
                            function sign(clock) {
                                return clock.daysOffset < 0 ? "-" : "";
                            }
                            function hours(clock) {
                                return clock.daysOffset < 0 ? clock.hourPart : clock.daysOffset * 24 + clock.hourPart;
                            }
                        })(short || (short = {}));
                        var long;
                        (function (long) {
                            function make(clock) {
                                return DayAttr.toText(clock.dayAttr) + clock.clockTextInDay;
                            }
                            long.make = make;
                        })(long || (long = {}));
                    })(format || (format = {}));
                })(clock = minutesBased.clock || (minutesBased.clock = {}));
            })(minutesBased = time.minutesBased || (time.minutesBased = {}));
        })(time = uk.time || (uk.time = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=minutesbased_clock.js.map