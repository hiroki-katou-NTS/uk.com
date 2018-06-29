var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var time;
        (function (time) {
            var minutesBased;
            (function (minutesBased) {
                minutesBased.MINUTES_IN_DAY = 24 * 60;
                function createBase(timeAsMinutes) {
                    if (!isFinite(timeAsMinutes)) {
                        throw new Error("invalid value: " + timeAsMinutes);
                    }
                    var mat = new Number(timeAsMinutes);
                    uk.util.accessor.defineInto(mat)
                        .get("asMinutes", function () { return timeAsMinutes; })
                        .get("isNegative", function () { return timeAsMinutes < 0; })
                        .get("minutePart", function () { return Math.abs(timeAsMinutes) % 60; })
                        .get("minutePartText", function () { return uk.text.padLeft(mat.minutePart.toString(), "0", 2); })
                        .get("typeName", function () { return "MinutesBasedTime"; });
                    return mat;
                }
                minutesBased.createBase = createBase;
            })(minutesBased = time.minutesBased || (time.minutesBased = {}));
        })(time = uk.time || (uk.time = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=minutesbased.js.map