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
                var qmm011;
                (function (qmm011) {
                    var d;
                    (function (d) {
                        var HistoryInsuranceRateDto = nts.uk.pr.view.qmm011.a.service.model.HistoryInsuranceRateDto;
                        var service;
                        (function (service) {
                            var paths = {
                                addUnemployeeInsuranceRate: "pr/insurance/labor/unemployeerate/add"
                            };
                            function addHistoryInfoUnemployeeInsurance(historyInfo) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.addUnemployeeInsuranceRate, historyInfo)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.addHistoryInfoUnemployeeInsurance = addHistoryInfoUnemployeeInsurance;
                            var model;
                            (function (model) {
                                var HistoryInfoDto = (function (_super) {
                                    __extends(HistoryInfoDto, _super);
                                    function HistoryInfoDto(historyId, companyCode, monthRage, startMonthRage, endMonthRage, takeover) {
                                        _super.call(this, historyId, companyCode, monthRage, startMonthRage, endMonthRage);
                                        this.takeover = takeover;
                                    }
                                    return HistoryInfoDto;
                                }(HistoryInsuranceRateDto));
                                model.HistoryInfoDto = HistoryInfoDto;
                            })(model = service.model || (service.model = {}));
                        })(service = d.service || (d.service = {}));
                    })(d = qmm011.d || (qmm011.d = {}));
                })(qmm011 = view.qmm011 || (view.qmm011 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
