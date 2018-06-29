var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var time;
        (function (time) {
            var secondsBased;
            (function (secondsBased) {
                secondsBased.SECOND_IN_MINUTE = 60;
                secondsBased.MINUTE_IN_HOUR = 60;
                secondsBased.HOUR_IN_DAY = 24;
                secondsBased.SECOND_IN_HOUR = secondsBased.MINUTE_IN_HOUR * secondsBased.SECOND_IN_MINUTE;
                secondsBased.SECOND_IN_DAY = secondsBased.HOUR_IN_DAY * secondsBased.SECOND_IN_HOUR;
                function createBase(timeAsSeconds) {
                    if (!isFinite(timeAsSeconds)) {
                        throw new Error("invalid value: " + timeAsSeconds);
                    }
                    var mat = new Number(timeAsSeconds);
                    uk.util.accessor.defineInto(mat)
                        .get("asSeconds", function () { return timeAsSeconds; })
                        .get("asMinutes", function () { return uk.ntsNumber.trunc(Math.abs(timeAsSeconds) / secondsBased.SECOND_IN_MINUTE); })
                        .get("isNegative", function () { return timeAsSeconds < 0; })
                        .get("minutePart", function () { return mat.asMinutes % secondsBased.MINUTE_IN_HOUR; })
                        .get("minutePartText", function () { return uk.text.padLeft(mat.minutePart.toString(), "0", 2); })
                        .get("secondPart", function () { return Math.abs(timeAsSeconds) % secondsBased.SECOND_IN_MINUTE; })
                        .get("secondPartText", function () { return uk.text.padLeft(mat.secondPart.toString(), "0", 2); })
                        .get("typeName", function () { return "SecondsBasedTime"; });
                    return mat;
                }
                secondsBased.createBase = createBase;
            })(secondsBased = time.secondsBased || (time.secondsBased = {}));
        })(time = uk.time || (uk.time = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=secondsbased.js.map