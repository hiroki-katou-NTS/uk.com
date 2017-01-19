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
                        var option = nts.uk.ui.option;
                        var RoundingMethod = a.service.model.RoundingMethod;
                        var MonthRange = a.service.model.MonthRange;
                        var YearMonth = a.service.model.YearMonth;
                        var CareerGroup = a.service.model.CareerGroup;
                        var HistoryAccidentInsuranceRate = a.service.model.HistoryAccidentInsuranceRate;
                        var InsuBizRateItem = a.service.model.InsuBizRateItem;
                        var BusinessTypeEnum = a.service.model.BusinessTypeEnum;
                        var InsuranceBusinessType = a.service.model.InsuranceBusinessType;
                        var TypeHistory = a.service.model.TypeHistory;
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.selectionRoundingMethod = ko.observableArray([new RoundingMethod("RoundUp", "切り捨て"),
                                        new RoundingMethod("Truncation", "切り上げ"),
                                        new RoundingMethod("RoundDown", "四捨五入"),
                                        new RoundingMethod("Down5_Up6", "五捨六入"),
                                        new RoundingMethod("Down4_Up5", "五捨五超入")]);
                                    self.rateInputOptions = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                                        grouplength: 3,
                                        decimallength: 2
                                    }));
                                    self.historyUnemployeeInsuranceRateStart = ko.observable('');
                                    self.historyUnemployeeInsuranceRateEnd = ko.observable('');
                                    var insuBizRateItemBiz1St = new InsuBizRateItem(BusinessTypeEnum.Biz1St, 60, "RoundDown");
                                    var insuBizRateItemBiz2Nd = new InsuBizRateItem(BusinessTypeEnum.Biz2Nd, 3, "Down5_Up6");
                                    var insuBizRateItemBiz3Rd = new InsuBizRateItem(BusinessTypeEnum.Biz3Rd, 15, "RoundUp");
                                    var insuBizRateItemBiz4Th = new InsuBizRateItem(BusinessTypeEnum.Biz4Th, 6.5, "RoundDown");
                                    var insuBizRateItemBiz5Th = new InsuBizRateItem(BusinessTypeEnum.Biz5Th, 13, "Down5_Up6");
                                    var insuBizRateItemBiz6Th = new InsuBizRateItem(BusinessTypeEnum.Biz6Th, 49, "RoundUp");
                                    var insuBizRateItemBiz7Th = new InsuBizRateItem(BusinessTypeEnum.Biz7Th, 3, "RoundDown");
                                    var insuBizRateItemBiz8Th = new InsuBizRateItem(BusinessTypeEnum.Biz8Th, 7, "Down5_Up6");
                                    var insuBizRateItemBiz9Th = new InsuBizRateItem(BusinessTypeEnum.Biz9Th, 2.5, "RoundUp");
                                    var insuBizRateItemBiz10Th = new InsuBizRateItem(BusinessTypeEnum.Biz10Th, 3, "RoundUp");
                                    var insuranceBusinessTypeBiz1St = new InsuranceBusinessType(BusinessTypeEnum.Biz1St, "事業種類名1");
                                    var insuranceBusinessTypeBiz2Nd = new InsuranceBusinessType(BusinessTypeEnum.Biz2Nd, "事業種類名2");
                                    var insuranceBusinessTypeBiz3Rd = new InsuranceBusinessType(BusinessTypeEnum.Biz3Rd, "事業種類名3");
                                    var insuranceBusinessTypeBiz4Th = new InsuranceBusinessType(BusinessTypeEnum.Biz4Th, "事業種類名4");
                                    var insuranceBusinessTypeBiz5Th = new InsuranceBusinessType(BusinessTypeEnum.Biz5Th, "事業種類名5");
                                    var insuranceBusinessTypeBiz6Th = new InsuranceBusinessType(BusinessTypeEnum.Biz6Th, "事業種類名6");
                                    var insuranceBusinessTypeBiz7Th = new InsuranceBusinessType(BusinessTypeEnum.Biz7Th, "事業種類名7");
                                    var insuranceBusinessTypeBiz8Th = new InsuranceBusinessType(BusinessTypeEnum.Biz8Th, "事業種類名8");
                                    var insuranceBusinessTypeBiz9Th = new InsuranceBusinessType(BusinessTypeEnum.Biz9Th, "事業種類名9");
                                    var insuranceBusinessTypeBiz10Th = new InsuranceBusinessType(BusinessTypeEnum.Biz10Th, "事業種類名11");
                                    self.lstInsuranceBusinessType = [insuranceBusinessTypeBiz1St, insuranceBusinessTypeBiz2Nd,
                                        insuranceBusinessTypeBiz3Rd, insuranceBusinessTypeBiz4Th, insuranceBusinessTypeBiz5Th, insuranceBusinessTypeBiz6Th,
                                        insuranceBusinessTypeBiz7Th, insuranceBusinessTypeBiz8Th, insuranceBusinessTypeBiz9Th, insuranceBusinessTypeBiz10Th];
                                    self.lstInsuBizRateItem = [insuBizRateItemBiz1St, insuBizRateItemBiz2Nd, insuBizRateItemBiz3Rd, insuBizRateItemBiz4Th,
                                        insuBizRateItemBiz5Th, insuBizRateItemBiz6Th, insuBizRateItemBiz7Th, insuBizRateItemBiz8Th, insuBizRateItemBiz9Th,
                                        insuBizRateItemBiz10Th];
                                    self.accidentInsuranceRateModel = ko.observable(new AccidentInsuranceRateModel(self.lstInsuBizRateItem, self.lstInsuranceBusinessType, self.rateInputOptions, self.selectionRoundingMethod));
                                    var historyAccidentInsuranceRate006 = new HistoryAccidentInsuranceRate('historyId006', 'companyCode001', new MonthRange(new YearMonth(2016, 4), new YearMonth(9999, 12)));
                                    var historyAccidentInsuranceRate005 = new HistoryAccidentInsuranceRate('historyId005', 'companyCode001', new MonthRange(new YearMonth(2015, 10), new YearMonth(2016, 3)));
                                    var historyAccidentInsuranceRate004 = new HistoryAccidentInsuranceRate('historyId004', 'companyCode001', new MonthRange(new YearMonth(2015, 4), new YearMonth(2015, 9)));
                                    var historyAccidentInsuranceRate003 = new HistoryAccidentInsuranceRate('historyId003', 'companyCode001', new MonthRange(new YearMonth(2014, 9), new YearMonth(2015, 3)));
                                    var historyAccidentInsuranceRate002 = new HistoryAccidentInsuranceRate('historyId002', 'companyCode001', new MonthRange(new YearMonth(2014, 4), new YearMonth(2014, 8)));
                                    var historyAccidentInsuranceRate001 = new HistoryAccidentInsuranceRate('historyId001', 'companyCode001', new MonthRange(new YearMonth(2013, 4), new YearMonth(2014, 3)));
                                    self.lstHistoryAccidentInsurance = [
                                        historyAccidentInsuranceRate006,
                                        historyAccidentInsuranceRate005,
                                        historyAccidentInsuranceRate004,
                                        historyAccidentInsuranceRate003,
                                        historyAccidentInsuranceRate002,
                                        historyAccidentInsuranceRate001
                                    ];
                                    self.lstHistoryAccidentInsuranceRate = ko.observableArray([
                                        new HistoryAccidentInsuranceRateModel(historyAccidentInsuranceRate006),
                                        new HistoryAccidentInsuranceRateModel(historyAccidentInsuranceRate005),
                                        new HistoryAccidentInsuranceRateModel(historyAccidentInsuranceRate004),
                                        new HistoryAccidentInsuranceRateModel(historyAccidentInsuranceRate003),
                                        new HistoryAccidentInsuranceRateModel(historyAccidentInsuranceRate002),
                                        new HistoryAccidentInsuranceRateModel(historyAccidentInsuranceRate001)
                                    ]);
                                    self.selectionHistoryAccidentInsuranceRate = ko.observable(historyAccidentInsuranceRate006.historyId);
                                    self.historyAccidentInsuranceRateStart = ko.observable('');
                                    self.historyAccidentInsuranceRateEnd = ko.observable('');
                                    self.itemName = ko.observable('');
                                    self.currentCode = ko.observable(2);
                                    self.isEnable = ko.observable(true);
                                    self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                                    self.showchangeHistoryAccidentInsurance(historyAccidentInsuranceRate006.historyId);
                                    self.selectionHistoryAccidentInsuranceRate.subscribe(function (selectionHistoryAccidentInsuranceRate) {
                                        self.showchangeHistoryAccidentInsurance(selectionHistoryAccidentInsuranceRate);
                                    });
                                }
                                ScreenModel.prototype.openEditHistoryUnemployeeInsuranceRate = function () {
                                    var historyId = this.selectionHistoryUnemployeeInsuranceRate();
                                    nts.uk.ui.windows.setShared("historyId", historyId);
                                    nts.uk.ui.windows.setShared("type", TypeHistory.HistoryUnemployee);
                                    nts.uk.ui.windows.sub.modal("/view/qmm/011/f/index.xhtml", { height: 420, width: 500, title: "労働保険料率の登録>マスタ修正ログ" }).onClosed(function () {
                                    });
                                };
                                ScreenModel.prototype.openAddHistoryUnemployeeInsuranceRate = function () {
                                    nts.uk.ui.windows.sub.modal("/view/qmm/011/d/index.xhtml", { height: 380, width: 480, title: "労働保険料率の登録>履歴の追加" }).onClosed(function () {
                                    });
                                };
                                ScreenModel.prototype.openEditInsuranceBusinessType = function () {
                                    nts.uk.ui.windows.sub.modal("/view/qmm/011/e/index.xhtml", { height: 590, width: 425, title: "事業種類の登録" }).onClosed(function () {
                                    });
                                };
                                ScreenModel.prototype.openEditHistoryAccidentInsuranceRate = function () {
                                    var historyId = this.selectionHistoryAccidentInsuranceRate();
                                    nts.uk.ui.windows.setShared("historyId", historyId);
                                    nts.uk.ui.windows.setShared("lsthistoryValue", this.lstHistoryAccidentInsurance);
                                    nts.uk.ui.windows.setShared("type", TypeHistory.HistoryAccident);
                                    nts.uk.ui.windows.sub.modal("/view/qmm/011/f/index.xhtml", { height: 420, width: 500, title: "労働保険料率の登録>マスタ修正ログ" }).onClosed(function () {
                                    });
                                };
                                ScreenModel.prototype.openAddHistoryAccidentInsuranceRate = function () {
                                    nts.uk.ui.windows.sub.modal("/view/qmm/011/d/index.xhtml", { height: 500, width: 600, title: "労働保険料率の登録>履歴の追加" }).onClosed(function () {
                                    });
                                };
                                ScreenModel.prototype.showchangeHistoryUnemployeeInsurance = function (selectionHistoryUnemployeeInsuranceRate) {
                                    var self = this;
                                    self.findHisotryUnemployeeInsuranceRate(selectionHistoryUnemployeeInsuranceRate);
                                    self.detailHistoryUnemployeeInsuranceRate(selectionHistoryUnemployeeInsuranceRate);
                                };
                                ScreenModel.prototype.showchangeHistoryAccidentInsurance = function (selectionHistoryAccidentInsuranceRate) {
                                    var self = this;
                                    if (selectionHistoryAccidentInsuranceRate != null && selectionHistoryAccidentInsuranceRate != undefined) {
                                        for (var index = 0; index < self.lstHistoryAccidentInsurance.length; index++) {
                                            if (self.lstHistoryAccidentInsurance[index].historyId === selectionHistoryAccidentInsuranceRate) {
                                                self.historyAccidentInsuranceRateStart(new HistoryAccidentInsuranceRateModel(self.lstHistoryAccidentInsurance[index])
                                                    .getViewStartMonth(self.lstHistoryAccidentInsurance[index]));
                                                self.historyAccidentInsuranceRateEnd(new HistoryAccidentInsuranceRateModel(self.lstHistoryAccidentInsurance[index])
                                                    .getViewEndMonth(self.lstHistoryAccidentInsurance[index]));
                                            }
                                        }
                                    }
                                };
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.findAllHisotryUnemployeeInsuranceRate().done(function (data) {
                                        dfd.resolve(self);
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.findAllHisotryUnemployeeInsuranceRate = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    a.service.findAllHisotryUnemployeeInsuranceRate().done(function (data) {
                                        self.lstHistoryUnemployeeInsuranceRate = ko.observableArray(data);
                                        self.selectionHistoryUnemployeeInsuranceRate = ko.observable(data[0].historyId);
                                        self.historyUnemployeeInsuranceRateStart = ko.observable(data[0].startMonthRage);
                                        self.historyUnemployeeInsuranceRateEnd = ko.observable(data[0].endMonthRage);
                                        self.selectionHistoryUnemployeeInsuranceRate.subscribe(function (selectionHistoryUnemployeeInsuranceRate) {
                                            self.showchangeHistoryUnemployeeInsurance(selectionHistoryUnemployeeInsuranceRate);
                                        });
                                        self.detailHistoryUnemployeeInsuranceRate(data[0].historyId).done(function (data) {
                                            dfd.resolve(self);
                                        });
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.findHisotryUnemployeeInsuranceRate = function (historyId) {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    a.service.findHisotryUnemployeeInsuranceRate(historyId).done(function (data) {
                                        self.historyUnemployeeInsuranceRateStart(data.startMonthRage);
                                        self.historyUnemployeeInsuranceRateEnd(data.endMonthRage);
                                        dfd.resolve(null);
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.detailHistoryUnemployeeInsuranceRate = function (historyId) {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    a.service.detailHistoryUnemployeeInsuranceRate(historyId).done(function (data) {
                                        self.unemployeeInsuranceRateModel = ko.observable(new UnemployeeInsuranceRateModel(data, self.rateInputOptions, self.selectionRoundingMethod));
                                        dfd.resolve(null);
                                    });
                                    return dfd.promise();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            function convertdata(yearmonth) {
                                var viewmonth = '';
                                if (yearmonth.month < 10) {
                                    viewmonth = '0' + yearmonth.month;
                                }
                                else {
                                    viewmonth = '' + yearmonth.month;
                                }
                                return '' + yearmonth.year + '/' + viewmonth;
                            }
                            viewmodel.convertdata = convertdata;
                            var UnemployeeInsuranceRateItemSettingModel = (function () {
                                function UnemployeeInsuranceRateItemSettingModel(unemployeeInsuranceRateItemSetting) {
                                    this.roundAtr = ko.observable("RoundUp");
                                    this.rate = ko.observable(unemployeeInsuranceRateItemSetting.rate);
                                }
                                return UnemployeeInsuranceRateItemSettingModel;
                            }());
                            viewmodel.UnemployeeInsuranceRateItemSettingModel = UnemployeeInsuranceRateItemSettingModel;
                            var HistoryAccidentInsuranceRateModel = (function () {
                                function HistoryAccidentInsuranceRateModel(historyAccidentInsuranceRate) {
                                    this.code = historyAccidentInsuranceRate.historyId;
                                    this.name = convertdata(historyAccidentInsuranceRate.monthRage.startMonth)
                                        + " ~ " + convertdata(historyAccidentInsuranceRate.monthRage.endMonth);
                                }
                                HistoryAccidentInsuranceRateModel.prototype.getViewStartMonth = function (historyAccidentInsuranceRate) {
                                    return convertdata(historyAccidentInsuranceRate.monthRage.startMonth);
                                };
                                HistoryAccidentInsuranceRateModel.prototype.getViewEndMonth = function (historyAccidentInsuranceRate) {
                                    return convertdata(historyAccidentInsuranceRate.monthRage.endMonth);
                                };
                                return HistoryAccidentInsuranceRateModel;
                            }());
                            viewmodel.HistoryAccidentInsuranceRateModel = HistoryAccidentInsuranceRateModel;
                            var UnemployeeInsuranceRateItemModel = (function () {
                                function UnemployeeInsuranceRateItemModel(companySetting, personalSetting, rateInputOptions, selectionRoundingMethod) {
                                    this.companySetting = new UnemployeeInsuranceRateItemSettingModel(companySetting);
                                    this.personalSetting = new UnemployeeInsuranceRateItemSettingModel(personalSetting);
                                    this.rateInputOptions = rateInputOptions;
                                    this.selectionRoundingMethod = selectionRoundingMethod;
                                }
                                return UnemployeeInsuranceRateItemModel;
                            }());
                            viewmodel.UnemployeeInsuranceRateItemModel = UnemployeeInsuranceRateItemModel;
                            var UnemployeeInsuranceRateModel = (function () {
                                function UnemployeeInsuranceRateModel(unemployeeInsuranceRate, rateInputOptions, selectionRoundingMethod) {
                                    for (var index = 0; index < unemployeeInsuranceRate.rateItems.length; index++) {
                                        if (unemployeeInsuranceRate.rateItems[index].careerGroup === CareerGroup.Agroforestry) {
                                            this.unemployeeInsuranceRateItemAgroforestryModel =
                                                new UnemployeeInsuranceRateItemModel(unemployeeInsuranceRate.rateItems[index].companySetting, unemployeeInsuranceRate.rateItems[index].personalSetting, rateInputOptions, selectionRoundingMethod);
                                        }
                                        else if (unemployeeInsuranceRate.rateItems[index].careerGroup === CareerGroup.Contruction) {
                                            this.unemployeeInsuranceRateItemContructionModel =
                                                new UnemployeeInsuranceRateItemModel(unemployeeInsuranceRate.rateItems[index].companySetting, unemployeeInsuranceRate.rateItems[index].personalSetting, rateInputOptions, selectionRoundingMethod);
                                        }
                                        else if (unemployeeInsuranceRate.rateItems[index].careerGroup === CareerGroup.Other) {
                                            this.unemployeeInsuranceRateItemOtherModel =
                                                new UnemployeeInsuranceRateItemModel(unemployeeInsuranceRate.rateItems[index].companySetting, unemployeeInsuranceRate.rateItems[index].personalSetting, rateInputOptions, selectionRoundingMethod);
                                        }
                                    }
                                }
                                return UnemployeeInsuranceRateModel;
                            }());
                            viewmodel.UnemployeeInsuranceRateModel = UnemployeeInsuranceRateModel;
                            var AccidentInsuranceRateDetailModel = (function () {
                                function AccidentInsuranceRateDetailModel(insuBizRateItem, rateInputOptions, selectionRoundingMethod) {
                                    this.insuRate = ko.observable(insuBizRateItem.insuRate);
                                    this.insuRound = ko.observable(insuBizRateItem.insuRound);
                                    this.rateInputOptions = rateInputOptions;
                                    this.selectionRoundingMethod = selectionRoundingMethod;
                                    this.insuranceBusinessType = ko.observable('');
                                }
                                AccidentInsuranceRateDetailModel.prototype.setInsuranceBusinessType = function (insuranceBusinessType) {
                                    this.insuranceBusinessType = ko.observable(insuranceBusinessType);
                                };
                                return AccidentInsuranceRateDetailModel;
                            }());
                            viewmodel.AccidentInsuranceRateDetailModel = AccidentInsuranceRateDetailModel;
                            var AccidentInsuranceRateModel = (function () {
                                function AccidentInsuranceRateModel(lstInsuBizRateItem, lstInsuranceBusinessType, rateInputOptions, selectionRoundingMethod) {
                                    for (var index = 0; index < lstInsuBizRateItem.length; index++) {
                                        if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz1St) {
                                            this.accidentInsuranceRateBiz1StModel =
                                                new AccidentInsuranceRateDetailModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                                        }
                                        if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz2Nd) {
                                            this.accidentInsuranceRateBiz2NdModel =
                                                new AccidentInsuranceRateDetailModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                                        }
                                        if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz3Rd) {
                                            this.accidentInsuranceRateBiz3RdModel =
                                                new AccidentInsuranceRateDetailModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                                        }
                                        if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz4Th) {
                                            this.accidentInsuranceRateBiz4ThModel =
                                                new AccidentInsuranceRateDetailModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                                        }
                                        if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz5Th) {
                                            this.accidentInsuranceRateBiz5ThModel =
                                                new AccidentInsuranceRateDetailModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                                        }
                                        if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz6Th) {
                                            this.accidentInsuranceRateBiz6ThModel =
                                                new AccidentInsuranceRateDetailModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                                        }
                                        if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz7Th) {
                                            this.accidentInsuranceRateBiz7ThModel =
                                                new AccidentInsuranceRateDetailModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                                        }
                                        if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz8Th) {
                                            this.accidentInsuranceRateBiz8ThModel =
                                                new AccidentInsuranceRateDetailModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                                        }
                                        if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz9Th) {
                                            this.accidentInsuranceRateBiz9ThModel =
                                                new AccidentInsuranceRateDetailModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                                        }
                                        if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz10Th) {
                                            this.accidentInsuranceRateBiz10ThModel =
                                                new AccidentInsuranceRateDetailModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                                        }
                                    }
                                    for (var index = 0; index < lstInsuranceBusinessType.length; index++) {
                                        if (lstInsuranceBusinessType[index].bizOrder == BusinessTypeEnum.Biz1St) {
                                            this.accidentInsuranceRateBiz1StModel.setInsuranceBusinessType(lstInsuranceBusinessType[index].bizName);
                                        }
                                        if (lstInsuranceBusinessType[index].bizOrder == BusinessTypeEnum.Biz2Nd) {
                                            this.accidentInsuranceRateBiz2NdModel.setInsuranceBusinessType(lstInsuranceBusinessType[index].bizName);
                                        }
                                        if (lstInsuranceBusinessType[index].bizOrder == BusinessTypeEnum.Biz3Rd) {
                                            this.accidentInsuranceRateBiz3RdModel.setInsuranceBusinessType(lstInsuranceBusinessType[index].bizName);
                                        }
                                        if (lstInsuranceBusinessType[index].bizOrder == BusinessTypeEnum.Biz4Th) {
                                            this.accidentInsuranceRateBiz4ThModel.setInsuranceBusinessType(lstInsuranceBusinessType[index].bizName);
                                        }
                                        if (lstInsuranceBusinessType[index].bizOrder == BusinessTypeEnum.Biz5Th) {
                                            this.accidentInsuranceRateBiz5ThModel.setInsuranceBusinessType(lstInsuranceBusinessType[index].bizName);
                                        }
                                        if (lstInsuranceBusinessType[index].bizOrder == BusinessTypeEnum.Biz6Th) {
                                            this.accidentInsuranceRateBiz6ThModel.setInsuranceBusinessType(lstInsuranceBusinessType[index].bizName);
                                        }
                                        if (lstInsuranceBusinessType[index].bizOrder == BusinessTypeEnum.Biz7Th) {
                                            this.accidentInsuranceRateBiz7ThModel.setInsuranceBusinessType(lstInsuranceBusinessType[index].bizName);
                                        }
                                        if (lstInsuranceBusinessType[index].bizOrder == BusinessTypeEnum.Biz8Th) {
                                            this.accidentInsuranceRateBiz8ThModel.setInsuranceBusinessType(lstInsuranceBusinessType[index].bizName);
                                        }
                                        if (lstInsuranceBusinessType[index].bizOrder == BusinessTypeEnum.Biz9Th) {
                                            this.accidentInsuranceRateBiz9ThModel.setInsuranceBusinessType(lstInsuranceBusinessType[index].bizName);
                                        }
                                        if (lstInsuranceBusinessType[index].bizOrder == BusinessTypeEnum.Biz9Th) {
                                            this.accidentInsuranceRateBiz9ThModel.setInsuranceBusinessType(lstInsuranceBusinessType[index].bizName);
                                        }
                                        if (lstInsuranceBusinessType[index].bizOrder == BusinessTypeEnum.Biz10Th) {
                                            this.accidentInsuranceRateBiz10ThModel.setInsuranceBusinessType(lstInsuranceBusinessType[index].bizName);
                                        }
                                    }
                                }
                                return AccidentInsuranceRateModel;
                            }());
                            viewmodel.AccidentInsuranceRateModel = AccidentInsuranceRateModel;
                        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
                    })(a = qmm011.a || (qmm011.a = {}));
                })(qmm011 = view.qmm011 || (view.qmm011 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
