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
                        var HistoryUnemployeeInsuranceRate = a.service.model.HistoryUnemployeeInsuranceRate;
                        var MonthRange = a.service.model.MonthRange;
                        var YearMonth = a.service.model.YearMonth;
                        var UnemployeeInsuranceRateItemSetting = a.service.model.UnemployeeInsuranceRateItemSetting;
                        var UnemployeeInsuranceRateItem = a.service.model.UnemployeeInsuranceRateItem;
                        var CareerGroup = a.service.model.CareerGroup;
                        var HistoryAccidentInsuranceRate = a.service.model.HistoryAccidentInsuranceRate;
                        var InsuBizRateItem = a.service.model.InsuBizRateItem;
                        var BusinessTypeEnum = a.service.model.BusinessTypeEnum;
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    var selectionRoundingMethod = [new RoundingMethod(0, "切り捨て"),
                                        new RoundingMethod(1, "切り上げ"),
                                        new RoundingMethod(2, "四捨五入"),
                                        new RoundingMethod(3, "五捨六入"),
                                        new RoundingMethod(4, "五捨五超入")];
                                    var unemployeeInsuranceRateItemAgroforestry = new UnemployeeInsuranceRateItem(CareerGroup.Agroforestry, new UnemployeeInsuranceRateItemSetting(3, 55.59), new UnemployeeInsuranceRateItemSetting(1, 55.5));
                                    var unemployeeInsuranceRateItemContruction = new UnemployeeInsuranceRateItem(CareerGroup.Contruction, new UnemployeeInsuranceRateItemSetting(3, 8.59), new UnemployeeInsuranceRateItemSetting(1, 55.6));
                                    var unemployeeInsuranceRateItemOther = new UnemployeeInsuranceRateItem(CareerGroup.Other, new UnemployeeInsuranceRateItemSetting(3, 8.59), new UnemployeeInsuranceRateItemSetting(1, 65.5));
                                    self.lstUnemployeeInsuranceRateItem = [unemployeeInsuranceRateItemAgroforestry, unemployeeInsuranceRateItemContruction, unemployeeInsuranceRateItemOther];
                                    self.rateInputOptions = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                                        grouplength: 3,
                                        decimallength: 2
                                    }));
                                    self.unemployeeInsuranceRateItemMode = ko.observable(new UnemployeeInsuranceRateItemMode(self.lstUnemployeeInsuranceRateItem, self.rateInputOptions, selectionRoundingMethod));
                                    self.lstHistoryUnemployeeInsuranceRate = ko.observableArray([
                                        new HistoryUnemployeeInsuranceRateModel(new HistoryUnemployeeInsuranceRate('historyId006', 'companyCode001', new MonthRange(new YearMonth(2016, 4), new YearMonth(9999, 12)))),
                                        new HistoryUnemployeeInsuranceRateModel(new HistoryUnemployeeInsuranceRate('historyId005', 'companyCode001', new MonthRange(new YearMonth(2015, 10), new YearMonth(2016, 3)))),
                                        new HistoryUnemployeeInsuranceRateModel(new HistoryUnemployeeInsuranceRate('historyId004', 'companyCode001', new MonthRange(new YearMonth(2015, 4), new YearMonth(2015, 9)))),
                                        new HistoryUnemployeeInsuranceRateModel(new HistoryUnemployeeInsuranceRate('historyId003', 'companyCode001', new MonthRange(new YearMonth(2014, 9), new YearMonth(2015, 3)))),
                                        new HistoryUnemployeeInsuranceRateModel(new HistoryUnemployeeInsuranceRate('historyId002', 'companyCode001', new MonthRange(new YearMonth(2014, 4), new YearMonth(2014, 8)))),
                                        new HistoryUnemployeeInsuranceRateModel(new HistoryUnemployeeInsuranceRate('historyId001', 'companyCode001', new MonthRange(new YearMonth(2013, 4), new YearMonth(2014, 3))))
                                    ]);
                                    self.historyUnemployeeInsuranceRateStart = ko.observable('2016/04');
                                    self.historyUnemployeeInsuranceRateEnd = ko.observable('9999/12 終了年月');
                                    self.selectionHistoryUnemployeeInsuranceRate = ko.observable('');
                                    var insuBizRateItemBiz1St = new InsuBizRateItem(BusinessTypeEnum.Biz1St, 60, 2);
                                    var insuBizRateItemBiz2Nd = new InsuBizRateItem(BusinessTypeEnum.Biz2Nd, 3, 3);
                                    var insuBizRateItemBiz3Rd = new InsuBizRateItem(BusinessTypeEnum.Biz3Rd, 15, 0);
                                    var insuBizRateItemBiz4Th = new InsuBizRateItem(BusinessTypeEnum.Biz4Th, 6.5, 2);
                                    var insuBizRateItemBiz5Th = new InsuBizRateItem(BusinessTypeEnum.Biz5Th, 13, 3);
                                    var insuBizRateItemBiz6Th = new InsuBizRateItem(BusinessTypeEnum.Biz6Th, 49, 0);
                                    var insuBizRateItemBiz7Th = new InsuBizRateItem(BusinessTypeEnum.Biz7Th, 3, 2);
                                    var insuBizRateItemBiz8Th = new InsuBizRateItem(BusinessTypeEnum.Biz8Th, 7, 3);
                                    var insuBizRateItemBiz9Th = new InsuBizRateItem(BusinessTypeEnum.Biz9Th, 2.5, 0);
                                    var insuBizRateItemBiz10Th = new InsuBizRateItem(BusinessTypeEnum.Biz10Th, 3, 0);
                                    self.lstInsuBizRateItem = [insuBizRateItemBiz1St, insuBizRateItemBiz2Nd, insuBizRateItemBiz3Rd, insuBizRateItemBiz4Th, insuBizRateItemBiz5Th,
                                        insuBizRateItemBiz6Th, insuBizRateItemBiz7Th, insuBizRateItemBiz8Th, insuBizRateItemBiz9Th, insuBizRateItemBiz10Th];
                                    self.accidentInsuranceRateModel = ko.observable(new AccidentInsuranceRateModel(self.lstInsuBizRateItem, self.rateInputOptions, selectionRoundingMethod));
                                    self.lstHistoryAccidentInsuranceRate = ko.observableArray([
                                        new HistoryAccidentInsuranceRateModel(new HistoryAccidentInsuranceRate('historyId006', 'companyCode001', new MonthRange(new YearMonth(2016, 4), new YearMonth(9999, 12)))),
                                        new HistoryAccidentInsuranceRateModel(new HistoryAccidentInsuranceRate('historyId005', 'companyCode001', new MonthRange(new YearMonth(2015, 10), new YearMonth(2016, 3)))),
                                        new HistoryAccidentInsuranceRateModel(new HistoryAccidentInsuranceRate('historyId004', 'companyCode001', new MonthRange(new YearMonth(2015, 4), new YearMonth(2015, 9)))),
                                        new HistoryAccidentInsuranceRateModel(new HistoryAccidentInsuranceRate('historyId003', 'companyCode001', new MonthRange(new YearMonth(2014, 9), new YearMonth(2015, 3)))),
                                        new HistoryAccidentInsuranceRateModel(new HistoryAccidentInsuranceRate('historyId002', 'companyCode001', new MonthRange(new YearMonth(2014, 4), new YearMonth(2014, 8)))),
                                        new HistoryAccidentInsuranceRateModel(new HistoryAccidentInsuranceRate('historyId001', 'companyCode001', new MonthRange(new YearMonth(2013, 4), new YearMonth(2014, 3))))
                                    ]);
                                    self.historyAccidentInsuranceRateStart = ko.observable('2016/04');
                                    self.historyAccidentInsuranceRateEnd = ko.observable('9999/12 終了年月');
                                    self.selectionHistoryAccidentInsuranceRate = ko.observable('');
                                    var valueclst001 = ko.observableArray([
                                        new CItemModelLST001("2016/01:9999/12", "2016/01 ~ 9999/12"),
                                        new CItemModelLST001("2016/01:2015/12", "2016/01 ~ 2015/12"),
                                        new CItemModelLST001("2016/01:2015/03", "2016/01 ~ 2015/03"),
                                        new CItemModelLST001("2016/02:2015/11", "2016/02 ~ 2015/11")
                                    ]);
                                    var valuecsel001 = ko.observableArray([
                                        new CItemModelSEL001("0", "切り捨て"),
                                        new CItemModelSEL001("1", "切り上げ"),
                                        new CItemModelSEL001("2", "四捨五入"),
                                        new CItemModelSEL001("3", "五捨六入"),
                                        new CItemModelSEL001("4", "五捨五超入")
                                    ]);
                                    self.clst001 = valueclst001;
                                    self.csel001 = valuecsel001;
                                    self.csel0011 = valuecsel001;
                                    self.csel0012 = valuecsel001;
                                    self.csel0013 = valuecsel001;
                                    self.csel0014 = valuecsel001;
                                    self.csel0015 = valuecsel001;
                                    self.csel0016 = valuecsel001;
                                    self.csel0017 = valuecsel001;
                                    self.csel0018 = valuecsel001;
                                    self.csel0019 = valuecsel001;
                                    self.clstsel001 = ko.observableArray([]);
                                    self.csel001Code = ko.observable(null);
                                    self.csel0011Code = ko.observable(null);
                                    self.csel0012Code = ko.observable(null);
                                    self.csel0013Code = ko.observable(null);
                                    self.csel0014Code = ko.observable(null);
                                    self.csel0015Code = ko.observable(null);
                                    self.csel0016Code = ko.observable(null);
                                    self.csel0017Code = ko.observable(null);
                                    self.csel0018Code = ko.observable(null);
                                    self.csel0019Code = ko.observable(null);
                                    self.cinp001 = ko.observable(null);
                                    self.cinp002 = ko.observable('40.009');
                                    self.cinp003 = ko.observable('40.009');
                                    self.cinp004 = ko.observable('40.009');
                                    self.cinp005 = ko.observable('40.009');
                                    self.cinp006 = ko.observable('40.009');
                                    self.cinp007 = ko.observable('40.009');
                                    self.cinp008 = ko.observable('40.009');
                                    self.cinp009 = ko.observable('40.009');
                                    self.cinp010 = ko.observable('40.009');
                                    self.cinp011 = ko.observable('40.009');
                                    self.itemName = ko.observable('');
                                    self.currentCode = ko.observable(2);
                                    self.isEnable = ko.observable(true);
                                    self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                                }
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
                                    this.roundAtr = ko.observable(unemployeeInsuranceRateItemSetting.roundAtr);
                                    this.rate = ko.observable(unemployeeInsuranceRateItemSetting.rate);
                                }
                                return UnemployeeInsuranceRateItemSettingModel;
                            }());
                            viewmodel.UnemployeeInsuranceRateItemSettingModel = UnemployeeInsuranceRateItemSettingModel;
                            var HistoryUnemployeeInsuranceRateModel = (function () {
                                function HistoryUnemployeeInsuranceRateModel(historyUnemployeeInsuranceRate) {
                                    this.code = historyUnemployeeInsuranceRate.historyId;
                                    this.name = convertdata(historyUnemployeeInsuranceRate.monthRage.startMonth) + " ~ " + convertdata(historyUnemployeeInsuranceRate.monthRage.endMonth);
                                }
                                return HistoryUnemployeeInsuranceRateModel;
                            }());
                            viewmodel.HistoryUnemployeeInsuranceRateModel = HistoryUnemployeeInsuranceRateModel;
                            var HistoryAccidentInsuranceRateModel = (function () {
                                function HistoryAccidentInsuranceRateModel(historyAccidentInsuranceRate) {
                                    this.code = historyAccidentInsuranceRate.historyId;
                                    this.name = convertdata(historyAccidentInsuranceRate.monthRage.startMonth) + " ~ " + convertdata(historyAccidentInsuranceRate.monthRage.endMonth);
                                }
                                return HistoryAccidentInsuranceRateModel;
                            }());
                            viewmodel.HistoryAccidentInsuranceRateModel = HistoryAccidentInsuranceRateModel;
                            var UnemployeeInsuranceRateItemAgroforestryModel = (function () {
                                function UnemployeeInsuranceRateItemAgroforestryModel(companySetting, personalSetting, rateInputOptions, selectionRoundingMethod) {
                                    this.companySetting = new UnemployeeInsuranceRateItemSettingModel(companySetting);
                                    this.personalSetting = new UnemployeeInsuranceRateItemSettingModel(personalSetting);
                                    this.rateInputOptions = rateInputOptions;
                                    this.selectionRoundingMethod = ko.observableArray(selectionRoundingMethod);
                                }
                                return UnemployeeInsuranceRateItemAgroforestryModel;
                            }());
                            viewmodel.UnemployeeInsuranceRateItemAgroforestryModel = UnemployeeInsuranceRateItemAgroforestryModel;
                            var UnemployeeInsuranceRateItemContructionModel = (function () {
                                function UnemployeeInsuranceRateItemContructionModel(companySetting, personalSetting, rateInputOptions, selectionRoundingMethod) {
                                    this.companySetting = new UnemployeeInsuranceRateItemSettingModel(companySetting);
                                    this.personalSetting = new UnemployeeInsuranceRateItemSettingModel(personalSetting);
                                    this.rateInputOptions = rateInputOptions;
                                    this.selectionRoundingMethod = ko.observableArray(selectionRoundingMethod);
                                }
                                return UnemployeeInsuranceRateItemContructionModel;
                            }());
                            viewmodel.UnemployeeInsuranceRateItemContructionModel = UnemployeeInsuranceRateItemContructionModel;
                            var UnemployeeInsuranceRateItemOtherModel = (function () {
                                function UnemployeeInsuranceRateItemOtherModel(companySetting, personalSetting, rateInputOptions, selectionRoundingMethod) {
                                    this.companySetting = new UnemployeeInsuranceRateItemSettingModel(companySetting);
                                    this.personalSetting = new UnemployeeInsuranceRateItemSettingModel(personalSetting);
                                    this.rateInputOptions = rateInputOptions;
                                    this.selectionRoundingMethod = ko.observableArray(selectionRoundingMethod);
                                }
                                return UnemployeeInsuranceRateItemOtherModel;
                            }());
                            viewmodel.UnemployeeInsuranceRateItemOtherModel = UnemployeeInsuranceRateItemOtherModel;
                            var UnemployeeInsuranceRateItemMode = (function () {
                                function UnemployeeInsuranceRateItemMode(lstUnemployeeInsuranceRateItem, rateInputOptions, selectionRoundingMethod) {
                                    for (var index = 0; index < lstUnemployeeInsuranceRateItem.length; index++) {
                                        if (lstUnemployeeInsuranceRateItem[index].careerGroup == CareerGroup.Agroforestry) {
                                            this.unemployeeInsuranceRateItemAgroforestryModel = new UnemployeeInsuranceRateItemAgroforestryModel(lstUnemployeeInsuranceRateItem[index].companySetting, lstUnemployeeInsuranceRateItem[index].personalSetting, rateInputOptions, selectionRoundingMethod);
                                        }
                                        else if (lstUnemployeeInsuranceRateItem[index].careerGroup == CareerGroup.Contruction) {
                                            this.unemployeeInsuranceRateItemContructionModel = new UnemployeeInsuranceRateItemContructionModel(lstUnemployeeInsuranceRateItem[index].companySetting, lstUnemployeeInsuranceRateItem[index].personalSetting, rateInputOptions, selectionRoundingMethod);
                                        }
                                        else if (lstUnemployeeInsuranceRateItem[index].careerGroup == CareerGroup.Other) {
                                            this.unemployeeInsuranceRateItemOtherModel = new UnemployeeInsuranceRateItemOtherModel(lstUnemployeeInsuranceRateItem[index].companySetting, lstUnemployeeInsuranceRateItem[index].personalSetting, rateInputOptions, selectionRoundingMethod);
                                        }
                                    }
                                }
                                return UnemployeeInsuranceRateItemMode;
                            }());
                            viewmodel.UnemployeeInsuranceRateItemMode = UnemployeeInsuranceRateItemMode;
                            var AccidentInsuranceRateBiz1StModel = (function () {
                                function AccidentInsuranceRateBiz1StModel(insuBizRateItem, rateInputOptions, selectionRoundingMethod) {
                                    this.insuRate = ko.observable(insuBizRateItem.insuRate);
                                    this.insuRound = ko.observable(insuBizRateItem.insuRound);
                                    this.rateInputOptions = rateInputOptions;
                                    this.selectionRoundingMethod = ko.observableArray(selectionRoundingMethod);
                                }
                                return AccidentInsuranceRateBiz1StModel;
                            }());
                            viewmodel.AccidentInsuranceRateBiz1StModel = AccidentInsuranceRateBiz1StModel;
                            var AccidentInsuranceRateBiz2NdModel = (function () {
                                function AccidentInsuranceRateBiz2NdModel(insuBizRateItem, rateInputOptions, selectionRoundingMethod) {
                                    this.insuRate = ko.observable(insuBizRateItem.insuRate);
                                    this.insuRound = ko.observable(insuBizRateItem.insuRound);
                                    this.rateInputOptions = rateInputOptions;
                                    this.selectionRoundingMethod = ko.observableArray(selectionRoundingMethod);
                                }
                                return AccidentInsuranceRateBiz2NdModel;
                            }());
                            viewmodel.AccidentInsuranceRateBiz2NdModel = AccidentInsuranceRateBiz2NdModel;
                            var AccidentInsuranceRateBiz3RdModel = (function () {
                                function AccidentInsuranceRateBiz3RdModel(insuBizRateItem, rateInputOptions, selectionRoundingMethod) {
                                    this.insuRate = ko.observable(insuBizRateItem.insuRate);
                                    this.insuRound = ko.observable(insuBizRateItem.insuRound);
                                    this.rateInputOptions = rateInputOptions;
                                    this.selectionRoundingMethod = ko.observableArray(selectionRoundingMethod);
                                }
                                return AccidentInsuranceRateBiz3RdModel;
                            }());
                            viewmodel.AccidentInsuranceRateBiz3RdModel = AccidentInsuranceRateBiz3RdModel;
                            var AccidentInsuranceRateBiz4ThModel = (function () {
                                function AccidentInsuranceRateBiz4ThModel(insuBizRateItem, rateInputOptions, selectionRoundingMethod) {
                                    this.insuRate = ko.observable(insuBizRateItem.insuRate);
                                    this.insuRound = ko.observable(insuBizRateItem.insuRound);
                                    this.rateInputOptions = rateInputOptions;
                                    this.selectionRoundingMethod = ko.observableArray(selectionRoundingMethod);
                                }
                                return AccidentInsuranceRateBiz4ThModel;
                            }());
                            viewmodel.AccidentInsuranceRateBiz4ThModel = AccidentInsuranceRateBiz4ThModel;
                            var AccidentInsuranceRateBiz5ThModel = (function () {
                                function AccidentInsuranceRateBiz5ThModel(insuBizRateItem, rateInputOptions, selectionRoundingMethod) {
                                    this.insuRate = ko.observable(insuBizRateItem.insuRate);
                                    this.insuRound = ko.observable(insuBizRateItem.insuRound);
                                    this.rateInputOptions = rateInputOptions;
                                    this.selectionRoundingMethod = ko.observableArray(selectionRoundingMethod);
                                }
                                return AccidentInsuranceRateBiz5ThModel;
                            }());
                            viewmodel.AccidentInsuranceRateBiz5ThModel = AccidentInsuranceRateBiz5ThModel;
                            var AccidentInsuranceRateBiz6ThModel = (function () {
                                function AccidentInsuranceRateBiz6ThModel(insuBizRateItem, rateInputOptions, selectionRoundingMethod) {
                                    this.insuRate = ko.observable(insuBizRateItem.insuRate);
                                    this.insuRound = ko.observable(insuBizRateItem.insuRound);
                                    this.rateInputOptions = rateInputOptions;
                                    this.selectionRoundingMethod = ko.observableArray(selectionRoundingMethod);
                                }
                                return AccidentInsuranceRateBiz6ThModel;
                            }());
                            viewmodel.AccidentInsuranceRateBiz6ThModel = AccidentInsuranceRateBiz6ThModel;
                            var AccidentInsuranceRateBiz7ThModel = (function () {
                                function AccidentInsuranceRateBiz7ThModel(insuBizRateItem, rateInputOptions, selectionRoundingMethod) {
                                    this.insuRate = ko.observable(insuBizRateItem.insuRate);
                                    this.insuRound = ko.observable(insuBizRateItem.insuRound);
                                    this.rateInputOptions = rateInputOptions;
                                    this.selectionRoundingMethod = ko.observableArray(selectionRoundingMethod);
                                }
                                return AccidentInsuranceRateBiz7ThModel;
                            }());
                            viewmodel.AccidentInsuranceRateBiz7ThModel = AccidentInsuranceRateBiz7ThModel;
                            var AccidentInsuranceRateBiz8ThModel = (function () {
                                function AccidentInsuranceRateBiz8ThModel(insuBizRateItem, rateInputOptions, selectionRoundingMethod) {
                                    this.insuRate = ko.observable(insuBizRateItem.insuRate);
                                    this.insuRound = ko.observable(insuBizRateItem.insuRound);
                                    this.rateInputOptions = rateInputOptions;
                                    this.selectionRoundingMethod = ko.observableArray(selectionRoundingMethod);
                                }
                                return AccidentInsuranceRateBiz8ThModel;
                            }());
                            viewmodel.AccidentInsuranceRateBiz8ThModel = AccidentInsuranceRateBiz8ThModel;
                            var AccidentInsuranceRateBiz9ThModel = (function () {
                                function AccidentInsuranceRateBiz9ThModel(insuBizRateItem, rateInputOptions, selectionRoundingMethod) {
                                    this.insuRate = ko.observable(insuBizRateItem.insuRate);
                                    this.insuRound = ko.observable(insuBizRateItem.insuRound);
                                    this.rateInputOptions = rateInputOptions;
                                    this.selectionRoundingMethod = ko.observableArray(selectionRoundingMethod);
                                }
                                return AccidentInsuranceRateBiz9ThModel;
                            }());
                            viewmodel.AccidentInsuranceRateBiz9ThModel = AccidentInsuranceRateBiz9ThModel;
                            var AccidentInsuranceRateBiz10ThModel = (function () {
                                function AccidentInsuranceRateBiz10ThModel(insuBizRateItem, rateInputOptions, selectionRoundingMethod) {
                                    this.insuRate = ko.observable(insuBizRateItem.insuRate);
                                    this.insuRound = ko.observable(insuBizRateItem.insuRound);
                                    this.rateInputOptions = rateInputOptions;
                                    this.selectionRoundingMethod = ko.observableArray(selectionRoundingMethod);
                                }
                                return AccidentInsuranceRateBiz10ThModel;
                            }());
                            viewmodel.AccidentInsuranceRateBiz10ThModel = AccidentInsuranceRateBiz10ThModel;
                            var AccidentInsuranceRateModel = (function () {
                                function AccidentInsuranceRateModel(lstInsuBizRateItem, rateInputOptions, selectionRoundingMethod) {
                                    for (var index = 0; index < lstInsuBizRateItem.length; index++) {
                                        if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz1St) {
                                            this.accidentInsuranceRateBiz1StModel = new AccidentInsuranceRateBiz1StModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                                        }
                                        if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz2Nd) {
                                            this.accidentInsuranceRateBiz2NdModel = new AccidentInsuranceRateBiz2NdModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                                        }
                                        if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz3Rd) {
                                            this.accidentInsuranceRateBiz3RdModel = new AccidentInsuranceRateBiz3RdModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                                        }
                                        if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz4Th) {
                                            this.accidentInsuranceRateBiz4ThModel = new AccidentInsuranceRateBiz4ThModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                                        }
                                        if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz5Th) {
                                            this.accidentInsuranceRateBiz5ThModel = new AccidentInsuranceRateBiz5ThModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                                        }
                                        if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz6Th) {
                                            this.accidentInsuranceRateBiz6ThModel = new AccidentInsuranceRateBiz6ThModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                                        }
                                        if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz7Th) {
                                            this.accidentInsuranceRateBiz7ThModel = new AccidentInsuranceRateBiz7ThModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                                        }
                                        if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz8Th) {
                                            this.accidentInsuranceRateBiz8ThModel = new AccidentInsuranceRateBiz8ThModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                                        }
                                        if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz9Th) {
                                            this.accidentInsuranceRateBiz9ThModel = new AccidentInsuranceRateBiz9ThModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                                        }
                                        if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz10Th) {
                                            this.accidentInsuranceRateBiz10ThModel = new AccidentInsuranceRateBiz10ThModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                                        }
                                    }
                                }
                                return AccidentInsuranceRateModel;
                            }());
                            viewmodel.AccidentInsuranceRateModel = AccidentInsuranceRateModel;
                            var CItemModelLST001 = (function () {
                                function CItemModelLST001(code, name) {
                                    this.code = code;
                                    this.name = name;
                                }
                                return CItemModelLST001;
                            }());
                            viewmodel.CItemModelLST001 = CItemModelLST001;
                            var CItemModelSEL001 = (function () {
                                function CItemModelSEL001(code, name) {
                                    this.code = code;
                                    this.name = name;
                                }
                                return CItemModelSEL001;
                            }());
                            viewmodel.CItemModelSEL001 = CItemModelSEL001;
                        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
                    })(a = qmm011.a || (qmm011.a = {}));
                })(qmm011 = view.qmm011 || (view.qmm011 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
