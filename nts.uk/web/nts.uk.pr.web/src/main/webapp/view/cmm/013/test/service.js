var cmmhoa013;
(function (cmmhoa013) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                getAllHistory: "basic/organization/position/getalljobhist",
                addJobHist: "basic/organization/position/addjobhist",
                updateJobHist: "basic/organization/position/updatejobhist",
                deleteJobHist: "basic/organization/position/deletejobhist"
            };
            /**
            * get all history
            */
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
            /**
             * add job history
             */
            function addJobHist(jobHist) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.addJobHist, jobHist)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.addJobHist = addJobHist;
            /**
             * update job history
             */
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
            /**
             * delete job history
             */
            function deleteJobHist(jobHist) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.deleteJobHist, jobHist)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.deleteJobHist = deleteJobHist;
            /**
             * model
             * historyDto
             * positionDto
             */
            var model;
            (function (model) {
                var historyDto = (function () {
                    function historyDto(startDate, endDate, historyId) {
                        this.startDate = startDate;
                        this.endDate = endDate;
                        this.historyId = historyId;
                    }
                    return historyDto;
                }());
                model.historyDto = historyDto;
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
                var ListPositionDto = (function () {
                    function ListPositionDto(code, name, presenceCheckScopeSet, memo) {
                        var self = this;
                        self.jobCode = code;
                        self.jobName = name;
                        self.presenceCheckScopeSet = presenceCheckScopeSet;
                        self.memo = memo;
                    }
                    return ListPositionDto;
                }());
                model.ListPositionDto = ListPositionDto;
                var ProcessPosition = (function () {
                    function ProcessPosition(historyId, jobCode, jobName, presenceCheckScopeSet, memo) {
                        var self = this;
                        self.historyId = historyId;
                        self.jobCode = jobCode;
                        self.jobName = jobName;
                        self.presenceCheckScopeSet = presenceCheckScopeSet;
                        self.memo = memo;
                    }
                    return ProcessPosition;
                }());
                model.ProcessPosition = ProcessPosition;
            })(model = service.model || (service.model = {}));
        })(service = a.service || (a.service = {}));
    })(a = cmmhoa013.a || (cmmhoa013.a = {}));
})(cmmhoa013 || (cmmhoa013 = {}));
