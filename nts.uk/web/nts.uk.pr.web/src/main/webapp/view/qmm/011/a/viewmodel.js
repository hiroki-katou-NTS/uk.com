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
                        var RoundingMethodDto = a.service.model.RoundingMethodDto;
                        var CareerGroupDto = a.service.model.CareerGroupDto;
                        var BusinessTypeEnumDto = a.service.model.BusinessTypeEnumDto;
                        var TypeHistory = a.service.model.TypeHistory;
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.selectionRoundingMethod = ko.observableArray([new RoundingMethodDto(0, "切り捨て"),
                                        new RoundingMethodDto(1, "切り上げ"),
                                        new RoundingMethodDto(2, "四捨五入"),
                                        new RoundingMethodDto(3, "五捨六入"),
                                        new RoundingMethodDto(4, "五捨五超入")]);
                                    self.rateInputOptions = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                                        grouplength: 3,
                                        decimallength: 2
                                    }));
                                    self.historyUnemployeeInsuranceRateStart = ko.observable('');
                                    self.historyUnemployeeInsuranceRateEnd = ko.observable('');
                                    self.historyAccidentInsuranceRateStart = ko.observable('');
                                    self.historyAccidentInsuranceRateEnd = ko.observable('');
                                    self.itemName = ko.observable('');
                                    self.currentCode = ko.observable(2);
                                    self.isEnable = ko.observable(true);
                                    self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                                }
                                ScreenModel.prototype.openEditHistoryUnemployeeInsuranceRate = function () {
                                    var self = this;
                                    var historyId = self.selectionHistoryUnemployeeInsuranceRate();
                                    nts.uk.ui.windows.setShared("historyId", historyId);
                                    nts.uk.ui.windows.setShared("historyStart", self.historyUnemployeeInsuranceRateStart());
                                    nts.uk.ui.windows.setShared("historyEnd", self.historyUnemployeeInsuranceRateEnd());
                                    nts.uk.ui.windows.setShared("type", TypeHistory.HistoryUnemployee);
                                    nts.uk.ui.windows.sub.modal("/view/qmm/011/f/index.xhtml", { height: 420, width: 550, title: "労働保険料率の登録>マスタ修正ログ" }).onClosed(function () {
                                    });
                                };
                                ScreenModel.prototype.openAddHistoryUnemployeeInsuranceRate = function () {
                                    nts.uk.ui.windows.sub.modal("/view/qmm/011/d/index.xhtml", { height: 400, width: 560, title: "労働保険料率の登録>履歴の追加" }).onClosed(function () {
                                    });
                                };
                                ScreenModel.prototype.openEditInsuranceBusinessType = function () {
                                    var self = this;
                                    var historyId = self.selectionHistoryAccidentInsuranceRate();
                                    nts.uk.ui.windows.setShared("historyId", historyId);
                                    nts.uk.ui.windows.setShared("accidentInsuranceRateModel", self.accidentInsuranceRateModel);
                                    nts.uk.ui.windows.sub.modal("/view/qmm/011/e/index.xhtml", { height: 590, width: 425, title: "事業種類の登録" }).onClosed(function () {
                                    });
                                };
                                ScreenModel.prototype.openEditHistoryAccidentInsuranceRate = function () {
                                    var historyId = this.selectionHistoryAccidentInsuranceRate();
                                    nts.uk.ui.windows.setShared("historyId", historyId);
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
                                    self.findHistoryAccidentInsuranceRate(selectionHistoryAccidentInsuranceRate);
                                    self.detailHistoryAccidentInsuranceRate(selectionHistoryAccidentInsuranceRate);
                                };
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.findAllHisotryUnemployeeInsuranceRate().done(function (data) {
                                        self.findAllHistoryAccidentInsuranceRate().done(function (data) {
                                            dfd.resolve(self);
                                        });
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
                                ScreenModel.prototype.findHistoryAccidentInsuranceRate = function (historyId) {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    a.service.findHistoryAccidentInsuranceRate(historyId).done(function (data) {
                                        self.historyAccidentInsuranceRateStart(data.startMonthRage);
                                        self.historyAccidentInsuranceRateEnd(data.endMonthRage);
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
                                ScreenModel.prototype.findAllHistoryAccidentInsuranceRate = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    a.service.findAllHistoryAccidentInsuranceRate().done(function (data) {
                                        self.lstHistoryAccidentInsuranceRate = ko.observableArray(data);
                                        self.selectionHistoryAccidentInsuranceRate = ko.observable(data[0].historyId);
                                        self.historyAccidentInsuranceRateStart = ko.observable(data[0].startMonthRage);
                                        self.historyAccidentInsuranceRateEnd = ko.observable(data[0].endMonthRage);
                                        self.selectionHistoryAccidentInsuranceRate.subscribe(function (selectionHistoryAccidentInsuranceRate) {
                                            self.showchangeHistoryAccidentInsurance(selectionHistoryAccidentInsuranceRate);
                                        });
                                        self.detailHistoryAccidentInsuranceRate(data[0].historyId).done(function (data) {
                                            dfd.resolve(self);
                                        });
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.detailHistoryAccidentInsuranceRate = function (historyId) {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    a.service.detailHistoryAccidentInsuranceRate(historyId).done(function (data) {
                                        self.accidentInsuranceRateModel = ko.observable(new AccidentInsuranceRateModel(data, self.rateInputOptions, self.selectionRoundingMethod));
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
                                    this.roundAtr = ko.observable(unemployeeInsuranceRateItemSetting.roundAtr);
                                    this.rate = ko.observable(unemployeeInsuranceRateItemSetting.rate);
                                }
                                return UnemployeeInsuranceRateItemSettingModel;
                            }());
                            viewmodel.UnemployeeInsuranceRateItemSettingModel = UnemployeeInsuranceRateItemSettingModel;
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
                                    for (var _i = 0, _a = unemployeeInsuranceRate.rateItems; _i < _a.length; _i++) {
                                        var rateItem = _a[_i];
                                        if (rateItem.careerGroup == CareerGroupDto.Agroforestry) {
                                            this.unemployeeInsuranceRateItemAgroforestryModel =
                                                new UnemployeeInsuranceRateItemModel(rateItem.companySetting, rateItem.personalSetting, rateInputOptions, selectionRoundingMethod);
                                        }
                                        else if (rateItem.careerGroup == CareerGroupDto.Contruction) {
                                            this.unemployeeInsuranceRateItemContructionModel =
                                                new UnemployeeInsuranceRateItemModel(rateItem.companySetting, rateItem.personalSetting, rateInputOptions, selectionRoundingMethod);
                                        }
                                        else if (rateItem.careerGroup == CareerGroupDto.Other) {
                                            this.unemployeeInsuranceRateItemOtherModel =
                                                new UnemployeeInsuranceRateItemModel(rateItem.companySetting, rateItem.personalSetting, rateInputOptions, selectionRoundingMethod);
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
                                    this.insuranceBusinessType = ko.observable(insuBizRateItem.insuranceBusinessType);
                                }
                                return AccidentInsuranceRateDetailModel;
                            }());
                            viewmodel.AccidentInsuranceRateDetailModel = AccidentInsuranceRateDetailModel;
                            var AccidentInsuranceRateModel = (function () {
                                function AccidentInsuranceRateModel(accidentInsuranceRate, rateInputOptions, selectionRoundingMethod) {
                                    for (var _i = 0, _a = accidentInsuranceRate.rateItems; _i < _a.length; _i++) {
                                        var rateItem = _a[_i];
                                        if (rateItem.insuBizType == BusinessTypeEnumDto.Biz1St) {
                                            this.accidentInsuranceRateBiz1StModel =
                                                new AccidentInsuranceRateDetailModel(rateItem, rateInputOptions, selectionRoundingMethod);
                                        }
                                        if (rateItem.insuBizType == BusinessTypeEnumDto.Biz2Nd) {
                                            this.accidentInsuranceRateBiz2NdModel =
                                                new AccidentInsuranceRateDetailModel(rateItem, rateInputOptions, selectionRoundingMethod);
                                        }
                                        if (rateItem.insuBizType == BusinessTypeEnumDto.Biz3Rd) {
                                            this.accidentInsuranceRateBiz3RdModel =
                                                new AccidentInsuranceRateDetailModel(rateItem, rateInputOptions, selectionRoundingMethod);
                                        }
                                        if (rateItem.insuBizType == BusinessTypeEnumDto.Biz4Th) {
                                            this.accidentInsuranceRateBiz4ThModel =
                                                new AccidentInsuranceRateDetailModel(rateItem, rateInputOptions, selectionRoundingMethod);
                                        }
                                        if (rateItem.insuBizType == BusinessTypeEnumDto.Biz5Th) {
                                            this.accidentInsuranceRateBiz5ThModel =
                                                new AccidentInsuranceRateDetailModel(rateItem, rateInputOptions, selectionRoundingMethod);
                                        }
                                        if (rateItem.insuBizType == BusinessTypeEnumDto.Biz6Th) {
                                            this.accidentInsuranceRateBiz6ThModel =
                                                new AccidentInsuranceRateDetailModel(rateItem, rateInputOptions, selectionRoundingMethod);
                                        }
                                        if (rateItem.insuBizType == BusinessTypeEnumDto.Biz7Th) {
                                            this.accidentInsuranceRateBiz7ThModel =
                                                new AccidentInsuranceRateDetailModel(rateItem, rateInputOptions, selectionRoundingMethod);
                                        }
                                        if (rateItem.insuBizType == BusinessTypeEnumDto.Biz8Th) {
                                            this.accidentInsuranceRateBiz8ThModel =
                                                new AccidentInsuranceRateDetailModel(rateItem, rateInputOptions, selectionRoundingMethod);
                                        }
                                        if (rateItem.insuBizType == BusinessTypeEnumDto.Biz9Th) {
                                            this.accidentInsuranceRateBiz9ThModel =
                                                new AccidentInsuranceRateDetailModel(rateItem, rateInputOptions, selectionRoundingMethod);
                                        }
                                        if (rateItem.insuBizType == BusinessTypeEnumDto.Biz10Th) {
                                            this.accidentInsuranceRateBiz10ThModel =
                                                new AccidentInsuranceRateDetailModel(rateItem, rateInputOptions, selectionRoundingMethod);
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
