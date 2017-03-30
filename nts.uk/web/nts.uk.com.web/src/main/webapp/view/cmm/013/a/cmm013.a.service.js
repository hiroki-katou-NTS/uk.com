var cmm013;
(function (cmm013) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                findAllPosition: "basic/position/findallposition/",
                addPosition: "basic/position/addPosition",
                deletePosition: "basic/position/deletePosition",
                updatePosition: "basic/position/updatePosition",
                getAllHistory: "basic/position/getallhist",
                addHist: "basic/position/addHist",
                updateHist: "basic/position/updateHist",
                deleteHist: "basic/position/deleteHist",
                getRef: "basic/position/getalljobtitleref/",
                getAuthLevel: "basic/organization/position/getallauthlevel/",
                getAllUseKt: "ctx/proto/company/findByUseKtSet/"
            };
            function findAllPosition(historyId) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.findAllPosition + historyId)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.findAllPosition = findAllPosition;
            function findByUseKt() {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.getAllUseKt + "1")
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.findByUseKt = findByUseKt;
            function deletePosition(position) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.deletePosition, position).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.deletePosition = deletePosition;
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
            function addHist(addJobHist) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.addHist, addJobHist)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.addHist = addHist;
            function getAllJobTitleRef(historyId, jobCode) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.getRef + historyId + "/" + jobCode)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllJobTitleRef = getAllJobTitleRef;
            function getAllAuthLevel(authCode) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.getAuthLevel + authCode)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllAuthLevel = getAllAuthLevel;
            var GetJobAuth = (function () {
                function GetJobAuth(historyId, jobCode, authCode, authName, refSet) {
                    this.historyId = historyId;
                    this.jobCode = jobCode;
                    this.authCode = authCode;
                    this.authName = authName;
                    this.refSet = refSet;
                }
                return GetJobAuth;
            }());
            service.GetJobAuth = GetJobAuth;
            function updateHist(update) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.updateHist, update)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.updateHist = updateHist;
            function deleteHist(jobHist) {
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
            service.deleteHist = deleteHist;
        })(service = a.service || (a.service = {}));
    })(a = cmm013.a || (cmm013.a = {}));
})(cmm013 || (cmm013 = {}));
//# sourceMappingURL=cmm013.a.service.js.map