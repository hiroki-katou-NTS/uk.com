var cmm013;
(function (cmm013) {
    var d;
    (function (d) {
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
            function updateJobHist(jobHist) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.updateHist, jobHist)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.updateJobHist = updateJobHist;
            function deleteJobHist(jobHist) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.deleteHist, jobHist)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.deleteJobHist = deleteJobHist;
        })(service = d.service || (d.service = {}));
    })(d = cmm013.d || (cmm013.d = {}));
})(cmm013 || (cmm013 = {}));
