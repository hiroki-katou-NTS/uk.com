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
                                var HistoryUnemployeeInsuranceRate = (function () {
                                    function HistoryUnemployeeInsuranceRate(historyId, companyCode, monthRage) {
                                        this.historyId = historyId;
                                        this.companyCode = companyCode;
                                        this.monthRage = monthRage;
                                    }
                                    return HistoryUnemployeeInsuranceRate;
                                }());
                                model.HistoryUnemployeeInsuranceRate = HistoryUnemployeeInsuranceRate;
                                var HistoryAccidentInsuranceRate = (function () {
                                    function HistoryAccidentInsuranceRate(historyId, companyCode, monthRage) {
                                        this.historyId = historyId;
                                        this.companyCode = companyCode;
                                        this.monthRage = monthRage;
                                    }
                                    return HistoryAccidentInsuranceRate;
                                }());
                                model.HistoryAccidentInsuranceRate = HistoryAccidentInsuranceRate;
                                (function (CareerGroup) {
                                    CareerGroup[CareerGroup["Agroforestry"] = 0] = "Agroforestry";
                                    CareerGroup[CareerGroup["Contruction"] = 1] = "Contruction";
                                    CareerGroup[CareerGroup["Other"] = 2] = "Other";
                                })(model.CareerGroup || (model.CareerGroup = {}));
                                var CareerGroup = model.CareerGroup;
                            })(model = service.model || (service.model = {}));
                        })(service = a.service || (a.service = {}));
                    })(a = qmm011.a || (qmm011.a = {}));
                })(qmm011 = view.qmm011 || (view.qmm011 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
