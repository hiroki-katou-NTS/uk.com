var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var time;
        (function (time) {
            var format;
            (function (format) {
                var DATE_FORMATS = {
                    Short_YMD: "yyyy/M/d",
                    Short_YMDW: "yyyy/M/d(D)",
                    Short_YM: "yyyy/M",
                    Short_MD: "M/d",
                    Short_D: "d",
                    Short_W: "D",
                    Short_MDW: "M/d(D)",
                    Long_YMD: "yyyy年M月d日",
                    Long_YMDW: "yyyy年M月d日(D)",
                    Long_YM: "yyyy年M月",
                    Long_MD: "M月d日",
                    Long_F: "yyyy年度"
                };
                function byId(formatId, value) {
                    switch (formatId) {
                        case "Clock_Short_HM":
                        case "ClockDay_Short_HM":
                            return time.minutesBased.clock.create(value).formatById(formatId);
                        case "Time_Short_HM":
                            return time.minutesBased.duration.create(value).formatById(formatId);
                        default:
                            throw new Error("not supported: " + formatId + " of " + value);
                    }
                }
                format.byId = byId;
            })(format = time.format || (time.format = {}));
        })(time = uk.time || (uk.time = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=timeformat.js.map