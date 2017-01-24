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
                                    getAvgEarnLevelMasterSetting: "pr/proto/insurance/social/healthrate/getAvgEarnLevelMasterSetting",
                                };
                                function getAvgEarnLevelMasterSetting() {
                                    var dfd = $.Deferred();
                                    nts.uk.request.ajax(paths.getAvgEarnLevelMasterSetting)
                                        .done(function (res) {
                                        dfd.resolve(res);
                                    })
                                        .fail(function (res) {
                                        dfd.reject(res);
                                    });
                                    return dfd.promise();
                                }
                                service.getAvgEarnLevelMasterSetting = getAvgEarnLevelMasterSetting;
                                var model;
                                (function (model) {
                                    var AvgEarnLevelMasterSettingDto = (function () {
                                        function AvgEarnLevelMasterSettingDto() {
                                        }
                                        return AvgEarnLevelMasterSettingDto;
                                    }());
                                    model.AvgEarnLevelMasterSettingDto = AvgEarnLevelMasterSettingDto;
                                })(model = service.model || (service.model = {}));
                            })(service = common.service || (common.service = {}));
                        })(common = _0.common || (_0.common = {}));
                    })(_0 = qmm008._0 || (qmm008._0 = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
