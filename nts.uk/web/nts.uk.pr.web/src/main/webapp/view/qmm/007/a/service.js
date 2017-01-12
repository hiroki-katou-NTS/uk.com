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
                    var a;
                    (function (a) {
                        var service;
                        (function (service) {
                            var paths = {
                                getPaymentDateProcessingList: "pr/proto/paymentdatemaster/processing/findall"
                            };
                            function getPaymentDateProcessingList() {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.getPaymentDateProcessingList)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.getPaymentDateProcessingList = getPaymentDateProcessingList;
                            /**
                            * Model namespace.
                            */
                            var model;
                            (function (model) {
                                var UnitPriceDto = (function () {
                                    function UnitPriceDto() {
                                    }
                                    return UnitPriceDto;
                                }());
                                model.UnitPriceDto = UnitPriceDto;
                                var UnitPriceHistoryDto = (function () {
                                    function UnitPriceHistoryDto() {
                                    }
                                    return UnitPriceHistoryDto;
                                }());
                                model.UnitPriceHistoryDto = UnitPriceHistoryDto;
                                var DateTimeDto = (function () {
                                    function DateTimeDto() {
                                    }
                                    return DateTimeDto;
                                }());
                                model.DateTimeDto = DateTimeDto;
                                (function (SettingType) {
                                    SettingType[SettingType["Company"] = 0] = "Company";
                                    SettingType[SettingType["Contract"] = 1] = "Contract";
                                })(model.SettingType || (model.SettingType = {}));
                                var SettingType = model.SettingType;
                                (function (ApplySetting) {
                                    ApplySetting[ApplySetting["Apply"] = 1] = "Apply";
                                    ApplySetting[ApplySetting["NotApply"] = 0] = "NotApply";
                                })(model.ApplySetting || (model.ApplySetting = {}));
                                var ApplySetting = model.ApplySetting;
                            })(model = service.model || (service.model = {}));
                        })(service = a.service || (a.service = {}));
                    })(a = qmm007.a || (qmm007.a = {}));
                })(qmm007 = view.qmm007 || (view.qmm007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
