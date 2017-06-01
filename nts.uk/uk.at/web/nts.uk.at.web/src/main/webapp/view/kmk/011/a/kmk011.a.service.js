var kmk011;
(function (kmk011) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                getAllDivTime: "at/record/divergencetime/getalldivtime",
                updateDivTime: "at/record/divergencetime/updatedivtime",
                updateTimeItemId: "at/record/divergencetime/updateTimeItemId",
                getAllAttItem: "at/share/attendanceType/getByType/",
                getItemSet: "at/record/divergencetime/getitemset/",
                getAllName: "at/share/attendanceitem/getPossibleAttendanceItem"
            };
            /**
            * get all item selected(item da duoc chon)
            */
            function getItemSelected(divTimeId) {
                return nts.uk.request.ajax("at", paths.getItemSet + divTimeId);
            }
            service.getItemSelected = getItemSelected;
            /**
            * get name(item da duoc chon)
            */
            function getNameItemSelected(lstItemId) {
                return nts.uk.request.ajax("at", paths.getAllName, lstItemId);
            }
            service.getNameItemSelected = getNameItemSelected;
            /**
            * get all attendance item id(id co the chon)
            */
            function getAllAttItem(divType) {
                return nts.uk.request.ajax("at", paths.getAllAttItem + divType);
            }
            service.getAllAttItem = getAllAttItem;
            /**
            * update time item id (da duoc chon lai)
            */
            function updateTimeItemId(lstItemId) {
                return nts.uk.request.ajax("at", paths.updateTimeItemId, lstItemId);
            }
            service.updateTimeItemId = updateTimeItemId;
            /**
            * get all divergence time
            */
            function getAllDivTime() {
                return nts.uk.request.ajax("at", paths.getAllDivTime);
            }
            service.getAllDivTime = getAllDivTime;
            /**
             * update divergence time
             */
            function updateDivTime(Object) {
                return nts.uk.request.ajax("at", paths.updateDivTime, Object);
            }
            service.updateDivTime = updateDivTime;
        })(service = a.service || (a.service = {}));
    })(a = kmk011.a || (kmk011.a = {}));
})(kmk011 || (kmk011 = {}));
//# sourceMappingURL=kmk011.a.service.js.map