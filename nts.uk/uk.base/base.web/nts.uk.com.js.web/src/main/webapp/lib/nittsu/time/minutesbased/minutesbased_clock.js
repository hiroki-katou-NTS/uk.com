/// <reference path="../../reference.ts"/>
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
                        uk.util.accessor.defineInto(clock)
                            .get("typeName", function () { return "ClockMinutesBasedTime"; })
                            .get("daysOffset", function () { return daysOffset(); })
                            .get("hourPart", function () { return Math.floor((positivizedMinutes() % minutesBased.MINUTES_IN_DAY) / 60); })
                            .get("minutePart", function () { return positivizedMinutes() % 60; })
                            .get("clockTextInDay", function () { return clock.hourPart + ":" + uk.text.padLeft(clock.minutePart.toString(), "0", 2); });
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
                })(clock = minutesBased.clock || (minutesBased.clock = {}));
            })(minutesBased = time.minutesBased || (time.minutesBased = {}));
        })(time = uk.time || (uk.time = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
