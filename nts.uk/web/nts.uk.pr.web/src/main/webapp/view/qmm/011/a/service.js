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
                    var a;
                    (function (a) {
                        var service;
                        (function (service) {
                            var paths = {
                                findAllHisotryUnemployeeInsuranceRate: "pr/insurance/labor/unemployeerate/findallHistory",
                                findHisotryUnemployeeInsuranceRate: "pr/insurance/labor/unemployeerate/findHistory",
                                detailHistoryUnemployeeInsuranceRate: "pr/insurance/labor/unemployeerate/detailHistory",
                                findAllHistoryAccidentInsuranceRate: "pr/insurance/labor/accidentrate/findallHistory",
                                findHistoryAccidentInsuranceRate: "pr/insurance/labor/accidentrate/findHistory",
                                detailHistoryAccidentInsuranceRate: "pr/insurance/labor/accidentrate/detailHistory"
                            };
                            function findAllHisotryUnemployeeInsuranceRate() {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.findAllHisotryUnemployeeInsuranceRate)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.findAllHisotryUnemployeeInsuranceRate = findAllHisotryUnemployeeInsuranceRate;
                            function findHisotryUnemployeeInsuranceRate(historyId) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.findHisotryUnemployeeInsuranceRate + "/" + historyId)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.findHisotryUnemployeeInsuranceRate = findHisotryUnemployeeInsuranceRate;
                            function detailHistoryUnemployeeInsuranceRate(historyId) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.detailHistoryUnemployeeInsuranceRate + "/" + historyId)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.detailHistoryUnemployeeInsuranceRate = detailHistoryUnemployeeInsuranceRate;
                            function findAllHistoryAccidentInsuranceRate() {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.findAllHistoryAccidentInsuranceRate)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.findAllHistoryAccidentInsuranceRate = findAllHistoryAccidentInsuranceRate;
                            function findHistoryAccidentInsuranceRate(historyId) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.findHistoryAccidentInsuranceRate + "/" + historyId)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.findHistoryAccidentInsuranceRate = findHistoryAccidentInsuranceRate;
                            function detailHistoryAccidentInsuranceRate(historyId) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.detailHistoryAccidentInsuranceRate + "/" + historyId)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.detailHistoryAccidentInsuranceRate = detailHistoryAccidentInsuranceRate;
                            var model;
                            (function (model) {
                                var YearMonth = (function () {
                                    function YearMonth(year, month) {
                                        this.year = year;
                                        this.month = month;
                                    }
                                    return YearMonth;
                                }());
                                model.YearMonth = YearMonth;
                                var MonthRange = (function () {
                                    function MonthRange(startMonth, endMonth) {
                                        this.startMonth = startMonth;
                                        this.endMonth = endMonth;
                                    }
                                    return MonthRange;
                                }());
                                model.MonthRange = MonthRange;
                                var RoundingMethod = (function () {
                                    function RoundingMethod(code, name) {
                                        this.code = code;
                                        this.name = name;
                                    }
                                    return RoundingMethod;
                                }());
                                model.RoundingMethod = RoundingMethod;
                                var UnemployeeInsuranceRateItemSetting = (function () {
                                    function UnemployeeInsuranceRateItemSetting(roundAtr, rate) {
                                        this.roundAtr = roundAtr;
                                        this.rate = rate;
                                    }
                                    return UnemployeeInsuranceRateItemSetting;
                                }());
                                model.UnemployeeInsuranceRateItemSetting = UnemployeeInsuranceRateItemSetting;
                                var UnemployeeInsuranceRateItem = (function () {
                                    function UnemployeeInsuranceRateItem(careerGroup, companySetting, personalSetting) {
                                        this.careerGroup = careerGroup;
                                        this.companySetting = companySetting;
                                        this.personalSetting = personalSetting;
                                    }
                                    return UnemployeeInsuranceRateItem;
                                }());
                                model.UnemployeeInsuranceRateItem = UnemployeeInsuranceRateItem;
                                var HistoryInsuranceRateDto = (function () {
                                    function HistoryInsuranceRateDto(historyId, companyCode, monthRage, startMonthRage, endMonthRage) {
                                        this.historyId = historyId;
                                        this.companyCode = companyCode;
                                        this.monthRage = monthRage;
                                        this.startMonthRage = startMonthRage;
                                        this.endMonthRage = endMonthRage;
                                    }
                                    return HistoryInsuranceRateDto;
                                }());
                                model.HistoryInsuranceRateDto = HistoryInsuranceRateDto;
                                var HistoryUnemployeeInsuranceRateDto = (function (_super) {
                                    __extends(HistoryUnemployeeInsuranceRateDto, _super);
                                    function HistoryUnemployeeInsuranceRateDto() {
                                        _super.apply(this, arguments);
                                    }
                                    return HistoryUnemployeeInsuranceRateDto;
                                }(HistoryInsuranceRateDto));
                                model.HistoryUnemployeeInsuranceRateDto = HistoryUnemployeeInsuranceRateDto;
                                var UnemployeeInsuranceRateDto = (function () {
                                    function UnemployeeInsuranceRateDto() {
                                    }
                                    return UnemployeeInsuranceRateDto;
                                }());
                                model.UnemployeeInsuranceRateDto = UnemployeeInsuranceRateDto;
                                var HistoryAccidentInsuranceRateDto = (function (_super) {
                                    __extends(HistoryAccidentInsuranceRateDto, _super);
                                    function HistoryAccidentInsuranceRateDto() {
                                        _super.apply(this, arguments);
                                    }
                                    return HistoryAccidentInsuranceRateDto;
                                }(HistoryInsuranceRateDto));
                                model.HistoryAccidentInsuranceRateDto = HistoryAccidentInsuranceRateDto;
                                var AccidentInsuranceRateDto = (function () {
                                    function AccidentInsuranceRateDto() {
                                    }
                                    return AccidentInsuranceRateDto;
                                }());
                                model.AccidentInsuranceRateDto = AccidentInsuranceRateDto;
                                var InsuBizRateItem = (function () {
                                    function InsuBizRateItem(insuBizType, insuRate, insuRound) {
                                        this.insuBizType = insuBizType;
                                        this.insuRate = insuRate;
                                        this.insuRound = insuRound;
                                    }
                                    return InsuBizRateItem;
                                }());
                                model.InsuBizRateItem = InsuBizRateItem;
                                var InsuranceBusinessType = (function () {
                                    function InsuranceBusinessType(bizOrder, bizName) {
                                        this.bizOrder = bizOrder;
                                        this.bizName = bizName;
                                    }
                                    return InsuranceBusinessType;
                                }());
                                model.InsuranceBusinessType = InsuranceBusinessType;
                                (function (CareerGroup) {
                                    CareerGroup[CareerGroup["Agroforestry"] = "Agroforestry"] = "Agroforestry";
                                    CareerGroup[CareerGroup["Contruction"] = "Contruction"] = "Contruction";
                                    CareerGroup[CareerGroup["Other"] = "Other"] = "Other";
                                })(model.CareerGroup || (model.CareerGroup = {}));
                                var CareerGroup = model.CareerGroup;
                                (function (BusinessTypeEnum) {
                                    BusinessTypeEnum[BusinessTypeEnum["Biz1St"] = "Biz1St"] = "Biz1St";
                                    BusinessTypeEnum[BusinessTypeEnum["Biz2Nd"] = "Biz2Nd"] = "Biz2Nd";
                                    BusinessTypeEnum[BusinessTypeEnum["Biz3Rd"] = "Biz3Rd"] = "Biz3Rd";
                                    BusinessTypeEnum[BusinessTypeEnum["Biz4Th"] = "Biz4Th"] = "Biz4Th";
                                    BusinessTypeEnum[BusinessTypeEnum["Biz5Th"] = "Biz5Th"] = "Biz5Th";
                                    BusinessTypeEnum[BusinessTypeEnum["Biz6Th"] = "Biz6Th"] = "Biz6Th";
                                    BusinessTypeEnum[BusinessTypeEnum["Biz7Th"] = "Biz7Th"] = "Biz7Th";
                                    BusinessTypeEnum[BusinessTypeEnum["Biz8Th"] = "Biz8Th"] = "Biz8Th";
                                    BusinessTypeEnum[BusinessTypeEnum["Biz9Th"] = "Biz9Th"] = "Biz9Th";
                                    BusinessTypeEnum[BusinessTypeEnum["Biz10Th"] = "Biz10Th"] = "Biz10Th";
                                })(model.BusinessTypeEnum || (model.BusinessTypeEnum = {}));
                                var BusinessTypeEnum = model.BusinessTypeEnum;
                                (function (TypeHistory) {
                                    TypeHistory[TypeHistory["HistoryUnemployee"] = 1] = "HistoryUnemployee";
                                    TypeHistory[TypeHistory["HistoryAccident"] = 2] = "HistoryAccident";
                                })(model.TypeHistory || (model.TypeHistory = {}));
                                var TypeHistory = model.TypeHistory;
                            })(model = service.model || (service.model = {}));
                        })(service = a.service || (a.service = {}));
                    })(a = qmm011.a || (qmm011.a = {}));
                })(qmm011 = view.qmm011 || (view.qmm011 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
