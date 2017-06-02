var kdl021;
(function (kdl021) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                getPossibleAttendanceItem: "at/share/attendanceitem/getPossibleAttendanceItem"
            };
            /**
            * get all divergence item id(id co the chon)
            */
            function getPossibleItem(arrPossible) {
                return nts.uk.request.ajax("at", paths.getPossibleAttendanceItem, arrPossible);
            }
            service.getPossibleItem = getPossibleItem;
            var model;
            (function (model) {
                var AttendanceItem = (function () {
                    function AttendanceItem() {
                    }
                    return AttendanceItem;
                }());
                model.AttendanceItem = AttendanceItem;
            })(model = service.model || (service.model = {}));
        })(service = a.service || (a.service = {}));
    })(a = kdl021.a || (kdl021.a = {}));
})(kdl021 || (kdl021 = {}));
//# sourceMappingURL=kdl021.a.service.js.map