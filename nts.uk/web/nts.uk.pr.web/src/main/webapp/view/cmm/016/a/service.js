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
                deleteJobHist: "basic/organization/position/deletejobhist",
                getAllJobTitle: "basic/organization/position/getalljobtitle/",
                addJobTitle: "basic/organization/position/addjobtitle",
                deleteJobTitle: "basic/organization/position/deletejobtitle"
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
            function addJobHist(addHandler) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.addJobHist, addHandler)
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
            function updateJobHist(updateHandler) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.updateJobHist, ko.mapping.toJS(updateHandler))
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
             * get all job title
             */
            function getAllJobTitle(historyId) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.getAllJobTitle + historyId)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllJobTitle = getAllJobTitle;
            /**
             * delete job title
             */
            function deleteJobTitle(jobTitle) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.deleteJobTitle, jobTitle)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.deleteJobTitle = deleteJobTitle;
            /**
             * model
             * historyDto
             * positionDto
             */
            var model;
            (function (model) {
                var AddHandler = (function () {
                    //add new and add old(3)/update(5)
                    function AddHandler(jHist, jTitle, checkAddJhist, checkAddJtitle) {
                        this.jobHist = jHist;
                        this.jobTitle = jTitle;
                        this.checkAddJhist = checkAddJhist;
                        this.checkAddJtitle = checkAddJtitle;
                    }
                    return AddHandler;
                }());
                model.AddHandler = AddHandler;
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
                var DeleteJobTitle = (function () {
                    function DeleteJobTitle(historyId, jobCode) {
                        this.historyId = historyId;
                        this.jobCode = jobCode;
                    }
                    return DeleteJobTitle;
                }());
                model.DeleteJobTitle = DeleteJobTitle;
            })(model = service.model || (service.model = {}));
        })(service = a.service || (a.service = {}));
    })(a = cmmhoa013.a || (cmmhoa013.a = {}));
})(cmmhoa013 || (cmmhoa013 = {}));
//# sourceMappingURL=service.js.map