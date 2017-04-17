var cmm013;
(function (cmm013) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                findAllJobTitle: "basic/organization/position/findAllJobTitle/",
                deleteJobTitle: "basic/organization/position/deleteJobTitle",
                getAllHistory: "basic/organization/position/getAllHist",
                regitry: "basic/organization/position/registryPosition",
                getAllUseKt: "ctx/proto/company/findByUseKtSet/",
                getAuth: "basic/organization/position/getAllJobRefAuth/",
                deleteJobTitleRef: "basic/organization/position/deleteJobTitleRef/",
                findAuth: "basic/organization/position/findAuth/"
            };
            function findAllJobTitle(historyId) {
                var dfd = $.Deferred();
                return nts.uk.request.ajax("com", paths.findAllJobTitle + historyId);
            }
            service.findAllJobTitle = findAllJobTitle;
            function findByUseKt() {
                var dfd = $.Deferred();
                return nts.uk.request.ajax("com", paths.getAllUseKt + "1");
            }
            service.findByUseKt = findByUseKt;
            function deleteJobTitle(position) {
                var dfd = $.Deferred();
                return nts.uk.request.ajax("com", paths.deleteJobTitle, position);
            }
            service.deleteJobTitle = deleteJobTitle;
            function getAllHistory() {
                var dfd = $.Deferred();
                return nts.uk.request.ajax("com", paths.getAllHistory);
            }
            service.getAllHistory = getAllHistory;
            function registry(addHandler) {
                var dfd = $.Deferred();
                return nts.uk.request.ajax("com", paths.regitry, addHandler);
            }
            service.registry = registry;
            function getAllJobTitleAuth(historyId, jobCode) {
                var dfd = $.Deferred();
                return nts.uk.request.ajax("com", paths.getAuth + historyId + "/" + jobCode);
            }
            service.getAllJobTitleAuth = getAllJobTitleAuth;
            function deleteJobRefAuth(jobRefAuth) {
                var dfd = $.Deferred();
                return nts.uk.request.ajax("com", paths.deleteJobTitleRef, jobRefAuth);
            }
            service.deleteJobRefAuth = deleteJobRefAuth;
            function findAuth() {
                var dfd = $.Deferred();
                return nts.uk.request.ajax("com", paths.findAuth + "KT");
            }
            service.findAuth = findAuth;
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
        })(service = a.service || (a.service = {}));
    })(a = cmm013.a || (cmm013.a = {}));
})(cmm013 || (cmm013 = {}));
//# sourceMappingURL=cmm013.a.service.js.map