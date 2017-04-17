var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm008;
                (function (qmm008) {
                    var _0;
                    (function (_0) {
                        var common;
                        (function (common) {
                            var service;
                            (function (service) {
                                var paths = {
                                    getHealthAvgEarnLimitList: "ctx/pr/core/insurance/avgearnmaster/health/find",
                                    getPensionAvgEarnLimitList: "ctx/pr/core/insurance/avgearnmaster/pension/find"
                                };
                                function getHealthAvgEarnLimitList() {
                                    var dfd = $.Deferred();
                                    nts.uk.request.ajax(paths.getHealthAvgEarnLimitList)
                                        .done(function (res) {
                                        dfd.resolve(convertToHealthAvgEarnLimitModel(res));
                                    })
                                        .fail(function (res) {
                                        dfd.reject(res);
                                    });
                                    return dfd.promise();
                                }
                                service.getHealthAvgEarnLimitList = getHealthAvgEarnLimitList;
                                function getPensionAvgEarnLimitList() {
                                    var dfd = $.Deferred();
                                    nts.uk.request.ajax(paths.getPensionAvgEarnLimitList)
                                        .done(function (res) {
                                        dfd.resolve(convertToHealthAvgEarnLimitModel(res));
                                    })
                                        .fail(function (res) {
                                        dfd.reject(res);
                                    });
                                    return dfd.promise();
                                }
                                service.getPensionAvgEarnLimitList = getPensionAvgEarnLimitList;
                                function convertToHealthAvgEarnLimitModel(listDto) {
                                    var salMin = 0;
                                    for (var i_1 in listDto) {
                                        var dto = listDto[i_1];
                                        dto.salMin = salMin;
                                        salMin = dto.salLimit;
                                    }
                                    return listDto;
                                }
                            })(service = common.service || (common.service = {}));
                        })(common = _0.common || (_0.common = {}));
                    })(_0 = qmm008._0 || (qmm008._0 = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=service.js.map