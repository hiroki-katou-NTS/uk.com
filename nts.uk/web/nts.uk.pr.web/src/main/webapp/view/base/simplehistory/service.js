var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var base;
                (function (base) {
                    var simplehistory;
                    (function (simplehistory) {
                        var service;
                        (function (service) {
                            var BaseService = (function () {
                                function BaseService(path) {
                                    var self = this;
                                    self.path = path;
                                }
                                BaseService.prototype.loadMasterModelList = function () {
                                    var self = this;
                                    return nts.uk.request.ajax(self.path.historyMasterPath);
                                };
                                BaseService.prototype.createHistory = function (masterCode, startYearMonth, isCopyFromLatest) {
                                    var self = this;
                                    return nts.uk.request.ajax(self.path.createHisotyPath, {
                                        masterCode: masterCode,
                                        startYearMonth: startYearMonth,
                                        copyFromLatest: isCopyFromLatest
                                    });
                                };
                                BaseService.prototype.deleteHistory = function (masterCode, historyUuid) {
                                    var self = this;
                                    return nts.uk.request.ajax(self.path.deleteHistoryPath, {
                                        masterCode: masterCode,
                                        historyUuid: historyUuid
                                    });
                                };
                                return BaseService;
                            }());
                            service.BaseService = BaseService;
                        })(service = simplehistory.service || (simplehistory.service = {}));
                    })(simplehistory = base.simplehistory || (base.simplehistory = {}));
                })(base = view.base || (view.base = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=service.js.map