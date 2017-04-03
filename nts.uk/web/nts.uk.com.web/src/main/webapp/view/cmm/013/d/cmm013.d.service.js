var cmm013;
(function (cmm013) {
    var d;
    (function (d) {
        var service;
        (function (service) {
            var paths = {
                updateJobHist: "basic/organization/position/updatejobhist"
            };
            function updateJobHist(jobHist) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.updateJobHist, jobHist)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.updateJobHist = updateJobHist;
            var model;
            (function (model) {
                var UpdateHandler = (function () {
                    function UpdateHandler(jHist, checkUpdate, checkDelete) {
                        this.jHist = jHist;
                        this.checkUpdate = checkUpdate;
                        this.checkDelete = checkDelete;
                    }
                    return UpdateHandler;
                }());
                model.UpdateHandler = UpdateHandler;
                var JobHistDto = (function () {
                    function JobHistDto(companyCode, startDate, endDate, historyId) {
                        this.companyCode = companyCode;
                        this.startDate = startDate;
                        this.endDate = endDate;
                        this.historyId = historyId;
                    }
                    return JobHistDto;
                }());
                model.JobHistDto = JobHistDto;
            })(model = service.model || (service.model = {}));
        })(service = d.service || (d.service = {}));
    })(d = cmm013.d || (cmm013.d = {}));
})(cmm013 || (cmm013 = {}));
//# sourceMappingURL=cmm013.d.service.js.map