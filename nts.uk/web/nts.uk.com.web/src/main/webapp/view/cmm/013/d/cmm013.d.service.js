var cmm013;
(function (cmm013) {
    var d;
    (function (d) {
        var service;
        (function (service) {
            var paths = {
                updateHist: "basic/organization/position/updateHist",
                deleteHist: "basic/organization/position/deleteHist"
            };
            function deleteHistory(history) {
                return nts.uk.request.ajax("com", paths.deleteHist, history);
            }
            service.deleteHistory = deleteHistory;
            function updateHistory(history) {
                return nts.uk.request.ajax("com", paths.updateHist, history);
            }
            service.updateHistory = updateHistory;
        })(service = d.service || (d.service = {}));
    })(d = cmm013.d || (cmm013.d = {}));
})(cmm013 || (cmm013 = {}));
//# sourceMappingURL=cmm013.d.service.js.map