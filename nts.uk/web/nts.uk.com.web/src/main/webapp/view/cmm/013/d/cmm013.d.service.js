var cmm013;
(function (cmm013) {
    var d;
    (function (d) {
        var service;
        (function (service) {
            var paths = {
                //xem lai duong dan
                updateHist: "basic/organization/position/updateHist",
                deleteHist: "basic/organization/position/deleteHist"
            };
            //xoa lich su position
            function deleteHistory(history) {
                history.oldStartDate = moment(history.oldStartDate).format("YYYY-MM-DD");
                history.newStartDate = moment(history.newStartDate).format("YYYY-MM-DD");
                return nts.uk.request.ajax("com", paths.deleteHist, history);
            }
            service.deleteHistory = deleteHistory;
            //update lich su position
            function updateHistory(history) {
                history.oldStartDate = moment(history.oldStartDate).format("YYYY-MM-DD");
                history.newStartDate = moment(history.newStartDate).format("YYYY-MM-DD");
                return nts.uk.request.ajax("com", paths.updateHist, history);
            }
            service.updateHistory = updateHistory;
        })(service = d.service || (d.service = {}));
    })(d = cmm013.d || (cmm013.d = {}));
})(cmm013 || (cmm013 = {}));
