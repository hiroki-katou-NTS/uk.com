var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm007;
                (function (qmm007) {
                    var service;
                    (function (service) {
                        var paths = {
                            getUnitPriceHistoryList: "pr/proto/unitprice/findall",
                            getUnitPriceHistoryDetail: "pr/proto/unitprice/find",
                            createUnitPriceHistory: "pr/proto/unitprice/create",
                            updateUnitPriceHistory: "pr/proto/unitprice/update",
                            removeUnitPriceHistory: "pr/proto/unitprice/remove"
                        };
                        var Service = (function (_super) {
                            __extends(Service, _super);
                            function Service(path) {
                                _super.call(this, path);
                            }
                            Service.prototype.findHistoryByUuid = function (id) {
                                return nts.uk.request.ajax(paths.getUnitPriceHistoryDetail + "/" + id);
                            };
                            return Service;
                        }(view.base.simplehistory.service.BaseService));
                        service.Service = Service;
                        service.instance = new Service({
                            historyMasterPath: 'pr/proto/unitprice/masterhistory',
                            createHisotyPath: 'pr/proto/unitprice/history/create',
                            deleteHistoryPath: 'pr/proto/unitprice/history/delete'
                        });
                        function update(unitPriceHistory) {
                            return nts.uk.request.ajax(paths.updateUnitPriceHistory, unitPriceHistory);
                        }
                        service.update = update;
                        var model;
                        (function (model) {
                            ;
                        })(model = service.model || (service.model = {}));
                    })(service = qmm007.service || (qmm007.service = {}));
                })(qmm007 = view.qmm007 || (view.qmm007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=service.js.map