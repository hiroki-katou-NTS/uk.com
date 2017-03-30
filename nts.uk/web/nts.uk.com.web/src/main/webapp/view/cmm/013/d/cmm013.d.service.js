var cmm013;
(function (cmm013) {
    var d;
    (function (d) {
        var service;
        (function (service) {
            var paths = {
                updateHist: "basic/organization/position/updateHist",
            };
            function updateHist(jobHist) {
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
            service.updateHist = updateHist;
        })(service = d.service || (d.service = {}));
    })(d = cmm013.d || (cmm013.d = {}));
})(cmm013 || (cmm013 = {}));
//# sourceMappingURL=cmm013.d.service.js.map