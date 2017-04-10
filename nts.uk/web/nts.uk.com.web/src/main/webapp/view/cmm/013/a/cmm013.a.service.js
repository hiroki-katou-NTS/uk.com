var cmm013;
(function (cmm013) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                findAllPosition: "basic/organization/position/findallposition/",
                addPosition: "basic/organization/position/addPosition",
                deletePosition: "basic/organization/position/deletePosition",
                updatePosition: "basic/organization/position/updatePosition",
                getAllHistory: "basic/organization/position/getallhist",
                regitry: "basic/organization/position/registryPosition",
                updateHist: "basic/organization/position/updateHist",
                deleteHist: "basic/organization/position/deleteHist",
                getRef: "basic/organization/position/getalljobtitleref/",
                getAuthLevel: "basic/organization/position/getallauthlevel/",
                getAllUseKt: "ctx/proto/company/findByUseKtSet/",
                getAuth: "basic/organization/position/getalljobrefauth/"
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
            function addPosition(position) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.addPosition, position)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.addPosition = addPosition;
            function updatePosition(position) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.updatePosition, position).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.updatePosition = updatePosition;
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
            function registry(addHandler) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.regitry, addHandler)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.registry = registry;
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
            function getAllJobTitleAuth(historyId, jobCode) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.getAuth + historyId + "/" + jobCode)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllJobTitleAuth = getAllJobTitleAuth;
            var GetJobAuth = (function () {
                function GetJobAuth(historyId, jobCode, authCode, authName, referenceSettings, authScopeAtr) {
                    this.historyId = historyId;
                    this.jobCode = jobCode;
                    this.authCode = authCode;
                    this.authName = authName;
                    this.referenceSettings = referenceSettings;
                    this.authScopeAtr = authScopeAtr;
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
        })(service = a.service || (a.service = {}));
    })(a = cmm013.a || (cmm013.a = {}));
})(cmm013 || (cmm013 = {}));
//# sourceMappingURL=cmm013.a.service.js.map