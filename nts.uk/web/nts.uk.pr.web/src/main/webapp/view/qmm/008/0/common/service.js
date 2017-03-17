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
                                    getAvgEarnLevelMasterSettingList: "ctx/pr/core/insurance/avgearnmaster/find",
                                };
                                function getAvgEarnLevelMasterSettingList() {
                                    var dfd = $.Deferred();
                                    nts.uk.request.ajax(paths.getAvgEarnLevelMasterSettingList)
                                        .done(function (res) {
                                        dfd.resolve(convertToAvgEarnLevelMasterSettingModel(res));
                                    })
                                        .fail(function (res) {
                                        dfd.reject(res);
                                    });
                                    return dfd.promise();
                                }
                                service.getAvgEarnLevelMasterSettingList = getAvgEarnLevelMasterSettingList;
                                function convertToAvgEarnLevelMasterSettingModel(listDto) {
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