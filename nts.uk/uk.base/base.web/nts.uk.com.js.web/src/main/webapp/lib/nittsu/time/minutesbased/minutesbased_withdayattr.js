var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var time;
        (function (time) {
            var minutesBased;
            (function (minutesBased) {
                var clock;
                (function (clock) {
                    var dayattr;
                    (function (dayattr) {
                        dayattr.MAX_VALUE = create(4319);
                        dayattr.MIN_VALUE = create(-720);
                        (function (DayAttr) {
                            DayAttr[DayAttr["THE_PREVIOUS_DAY"] = 0] = "THE_PREVIOUS_DAY";
                            DayAttr[DayAttr["THE_PRESENT_DAY"] = 1] = "THE_PRESENT_DAY";
                            DayAttr[DayAttr["THE_NEXT_DAY"] = 2] = "THE_NEXT_DAY";
                            DayAttr[DayAttr["TWO_DAY_LATER"] = 3] = "TWO_DAY_LATER";
                        })(dayattr.DayAttr || (dayattr.DayAttr = {}));
                        var DayAttr = dayattr.DayAttr;
                        function getDayAttrText(dayAttr) {
                            switch (dayAttr) {
                                case DayAttr.THE_PREVIOUS_DAY: return "前日";
                                case DayAttr.THE_PRESENT_DAY: return "当日";
                                case DayAttr.THE_NEXT_DAY: return "翌日";
                                case DayAttr.TWO_DAY_LATER: return "翌々日";
                                default: throw new Error("invalid value: " + dayAttr);
                            }
                        }
                        function getDaysOffset(dayAttr) {
                            switch (dayAttr) {
                                case DayAttr.THE_PREVIOUS_DAY: return -1;
                                case DayAttr.THE_PRESENT_DAY: return 0;
                                case DayAttr.THE_NEXT_DAY: return 1;
                                case DayAttr.TWO_DAY_LATER: return 2;
                                default: throw new Error("invalid value: " + dayAttr);
                            }
                        }
                        var ResultParseTimeWithDayAttr = (function () {
                            function ResultParseTimeWithDayAttr(success, asMinutes) {
                                this.success = success;
                                this.asMinutes = asMinutes;
                            }
                            ResultParseTimeWithDayAttr.succeeded = function (asMinutes) {
                                return new ResultParseTimeWithDayAttr(true, asMinutes);
                            };
                            ResultParseTimeWithDayAttr.failed = function () {
                                return new ResultParseTimeWithDayAttr(false);
                            };
                            return ResultParseTimeWithDayAttr;
                        }());
                        dayattr.ResultParseTimeWithDayAttr = ResultParseTimeWithDayAttr;
                        function parseString(source) {
                            var foundAttr = cutDayAttrTextIfExists(source);
                            if (foundAttr.found) {
                                var daysOffset = getDaysOffset(foundAttr.attr);
                                var parsedAsDuration = minutesBased.duration.parseString(foundAttr.clockPartText);
                                if (!parsedAsDuration.success) {
                                    return ResultParseTimeWithDayAttr.failed();
                                }
                                var asMinutes = parsedAsDuration.toValue() + minutesBased.MINUTES_IN_DAY * daysOffset;
                                return ResultParseTimeWithDayAttr.succeeded(asMinutes);
                            }
                            else {
                                var parsedAsDuration = minutesBased.duration.parseString(source);
                                if (!parsedAsDuration.success) {
                                    return ResultParseTimeWithDayAttr.failed();
                                }
                                if (parsedAsDuration.minus) {
                                    var asClock = -(parsedAsDuration.toValue()) - minutesBased.MINUTES_IN_DAY;
                                    if (asClock >= 0) {
                                        return ResultParseTimeWithDayAttr.failed();
                                    }
                                    return ResultParseTimeWithDayAttr.succeeded(asClock);
                                }
                                else {
                                    return ResultParseTimeWithDayAttr.succeeded(parsedAsDuration.toValue());
                                }
                            }
                        }
                        dayattr.parseString = parseString;
                        function create(minutesFromZeroOclock) {
                            var timeWithDayAttr = (clock.create(minutesFromZeroOclock));
                            uk.util.accessor.defineInto(timeWithDayAttr)
                                .get("dayAttr", function () { return getDayAttrFromDaysOffset(timeWithDayAttr.daysOffset); })
                                .get("fullText", function () { return timeWithDayAttr.formatById("ClockDay_Short_HM"); })
                                .get("shortText", function () { return timeWithDayAttr.formatById("Clock_Short_HM"); });
                            return timeWithDayAttr;
                        }
                        dayattr.create = create;
                        function getDayAttrFromDaysOffset(daysOffset) {
                            switch (daysOffset) {
                                case -1: return DayAttr.THE_PREVIOUS_DAY;
                                case 0: return DayAttr.THE_PRESENT_DAY;
                                case 1: return DayAttr.THE_NEXT_DAY;
                                case 2: return DayAttr.TWO_DAY_LATER;
                                default: throw new Error("invalid value: " + daysOffset);
                            }
                        }
                        var DAY_ATTR_TEXTS = [
                            { value: DayAttr.THE_PREVIOUS_DAY },
                            { value: DayAttr.THE_PRESENT_DAY },
                            { value: DayAttr.THE_NEXT_DAY },
                            { value: DayAttr.TWO_DAY_LATER }
                        ];
                        DAY_ATTR_TEXTS.forEach(function (e) { return e.text = getDayAttrText(e.value); });
                        function cutDayAttrTextIfExists(source) {
                            var foundAttr = _.find(DAY_ATTR_TEXTS, function (e) { return source.indexOf(e.text) === 0; });
                            var result = {
                                found: foundAttr !== undefined
                            };
                            if (result.found) {
                                result.attrText = foundAttr.text;
                                result.attr = foundAttr.value;
                                result.clockPartText = source.slice(foundAttr.text.length);
                            }
                            else {
                                result.clockPartText = source;
                            }
                            return result;
                        }
                    })(dayattr = clock.dayattr || (clock.dayattr = {}));
                })(clock = minutesBased.clock || (minutesBased.clock = {}));
            })(minutesBased = time.minutesBased || (time.minutesBased = {}));
        })(time = uk.time || (uk.time = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=minutesbased_withdayattr.js.map