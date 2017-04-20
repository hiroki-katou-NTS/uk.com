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
            /**
             * find all position
             */
            function findAllJobTitle(historyId) {
                var dfd = $.Deferred();
                return nts.uk.request.ajax("com", paths.findAllJobTitle + historyId);
            }
            service.findAllJobTitle = findAllJobTitle;
            /**
             * find by Use Kt
             */
            function findByUseKt() {
                var dfd = $.Deferred();
                return nts.uk.request.ajax("com", paths.getAllUseKt + "1");
            }
            service.findByUseKt = findByUseKt;
            /**
             * delete Position select
             */
            function deleteJobTitle(position) {
                var dfd = $.Deferred();
                return nts.uk.request.ajax("com", paths.deleteJobTitle, position);
            }
            service.deleteJobTitle = deleteJobTitle;
            /**
            * get all history
            */
            function getAllHistory() {
                var dfd = $.Deferred();
                return nts.uk.request.ajax("com", paths.getAllHistory);
            }
            service.getAllHistory = getAllHistory;
            /**
             * add & update position && add & update history
             */
            function registry(addHandler) {
                var dfd = $.Deferred();
                return nts.uk.request.ajax("com", paths.regitry, addHandler);
            }
            service.registry = registry;
            /**
             * get all jobtitle ref auth
             */
            function getAllJobTitleAuth(historyId, jobCode) {
                var dfd = $.Deferred();
                return nts.uk.request.ajax("com", paths.getAuth + historyId + "/" + jobCode);
                /**
                 * delete jobtitle ref auth
                 */
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
            class GetJobAuth {
                constructor(historyId, jobCode, authCode, authName, referenceSettings, authScopeAtr) {
                    this.historyId = historyId;
                    this.jobCode = jobCode;
                    this.authCode = authCode;
                    this.authName = authName;
                    this.referenceSettings = referenceSettings;
                    this.authScopeAtr = authScopeAtr;
                }
            }
            service.GetJobAuth = GetJobAuth;
        })(service = a.service || (a.service = {}));
    })(a = cmm013.a || (cmm013.a = {}));
})(cmm013 || (cmm013 = {}));
