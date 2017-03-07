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
                    var simlehistory;
                    (function (simlehistory) {
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
                                return BaseService;
                            }());
                            service.BaseService = BaseService;
                        })(service = simlehistory.service || (simlehistory.service = {}));
                    })(simlehistory = base.simlehistory || (base.simlehistory = {}));
                })(base = view.base || (view.base = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=service.js.map