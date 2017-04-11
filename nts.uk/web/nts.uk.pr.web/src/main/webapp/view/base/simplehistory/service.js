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
                            /**
                             * Simple base service.
                             * Provide load master with path.
                             */
                            var BaseService = (function () {
                                function BaseService(path) {
                                    var self = this;
                                    self.path = path;
                                }
                                /**
                                 * Load master model list.
                                 */
                                BaseService.prototype.loadMasterModelList = function () {
                                    var self = this;
                                    return nts.uk.request.ajax(self.path.historyMasterPath);
                                };
                                /**
                                 * @see Service.
                                 */
                                BaseService.prototype.createHistory = function (masterCode, startYearMonth, isCopyFromLatest) {
                                    var self = this;
                                    return nts.uk.request.ajax(self.path.createHisotyPath, {
                                        masterCode: masterCode,
                                        startYearMonth: startYearMonth,
                                        copyFromLatest: isCopyFromLatest
                                    });
                                };
                                /**
                                 * @see Service.
                                 */
                                BaseService.prototype.deleteHistory = function (masterCode, historyUuid) {
                                    var self = this;
                                    return nts.uk.request.ajax(self.path.deleteHistoryPath, {
                                        historyId: historyUuid
                                    });
                                };
                                /**
                                 * @see Service
                                 */
                                BaseService.prototype.updateHistoryStart = function (masterCode, historyUuid, newStart) {
                                    var self = this;
                                    return nts.uk.request.ajax(self.path.updateHistoryStartPath, {
                                        historyId: historyUuid,
                                        newYearMonth: newStart
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
