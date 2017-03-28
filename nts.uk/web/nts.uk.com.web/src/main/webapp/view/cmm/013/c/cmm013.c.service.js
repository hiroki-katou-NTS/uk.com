var cmm013;
(function (cmm013) {
    var c;
    (function (c) {
        var service;
        (function (service) {
            var paths = {
                findAllPosition: "basic/position/findallposition/",
                addPosition: "basic/position/addPosition",
                deletePosition: "basic/position/deletePosition",
                updatePosition: "basic/position/updatePosition",
                getAllHistory: "basic/position/getallhist",
                addHist: "basic/organization/position/addHist",
                updateHist: "basic/organization/position/updateHist",
                deleteHist: "basic/organization/position/deleteHist",
                findAllPosition2: "basic/position/findall"
            };
            function addJobHist(jobHist) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.addHist, jobHist)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.addJobHist = addJobHist;
            function getAllHistory() {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.getAllHistory)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllHistory = getAllHistory;
        })(service = c.service || (c.service = {}));
    })(c = cmm013.c || (cmm013.c = {}));
})(cmm013 || (cmm013 = {}));
//# sourceMappingURL=cmm013.c.service.js.map