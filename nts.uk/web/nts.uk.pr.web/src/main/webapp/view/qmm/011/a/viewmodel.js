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
                        var HistoryUnemployeeInsuranceDto = a.service.model.HistoryUnemployeeInsuranceDto;
                        var UnemployeeInsuranceRateItemSettingDto = a.service.model.UnemployeeInsuranceRateItemSettingDto;
                        var CareerGroupDto = a.service.model.CareerGroupDto;
                        var BusinessTypeEnumDto = a.service.model.BusinessTypeEnumDto;
                        var TypeHistory = a.service.model.TypeHistory;
                        var UnemployeeInsuranceRateDto = a.service.model.UnemployeeInsuranceRateDto;
                        var HistoryAccidentInsuranceDto = a.service.model.HistoryAccidentInsuranceDto;
                        var HistoryAccidentInsuranceRateFindInDto = a.service.model.HistoryAccidentInsuranceRateFindInDto;
                        var TypeActionInsuranceRate = a.service.model.TypeActionInsuranceRate;
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
                                    self.typeActionUnemployeeInsurance = ko.observable(TypeActionInsuranceRate.update);
                                    self.typeActionAccidentInsurance = ko.observable(TypeActionInsuranceRate.update);
                                    self.isEmpty = ko.observable(true);
                                }
                                ScreenModel.prototype.openEditHistoryUnemployeeInsuranceRate = function () {
                                    var self = this;
                                    var historyId = self.selectionHistoryUnemployeeInsuranceRate();
                                    a.service.findHisotryUnemployeeInsuranceRate(historyId).done(function (data) {
                                        nts.uk.ui.windows.setShared("historyId", data.historyId);
                                        nts.uk.ui.windows.setShared("historyStart", data.startMonthRage);
                                        nts.uk.ui.windows.setShared("historyEnd", data.endMonthRage);
                                        nts.uk.ui.windows.setShared("type", TypeHistory.HistoryUnemployee);
                                        self.typeActionUnemployeeInsurance(TypeActionInsuranceRate.update);
                                        nts.uk.ui.windows.sub.modal("/view/qmm/011/f/index.xhtml", { height: 420, width: 550, title: "労働保険料率の登録>マスタ修正ログ" }).onClosed(function () {
                                            var updateHistoryUnemployeeInsuranceDto = nts.uk.ui.windows.getShared("updateHistoryUnemployeeInsuranceDto");
                                            if (updateHistoryUnemployeeInsuranceDto != null && updateHistoryUnemployeeInsuranceDto != undefined) {
                                                self.historyUnemployeeInsuranceRateStart(updateHistoryUnemployeeInsuranceDto.startMonthRage);
                                                self.historyUnemployeeInsuranceRateEnd(updateHistoryUnemployeeInsuranceDto.endMonthRage);
                                            }
                                        });
                                    });
                                };
                                ScreenModel.prototype.openAddHistoryUnemployeeInsuranceRate = function () {
                                    var self = this;
                                    nts.uk.ui.windows.setShared("type", TypeHistory.HistoryUnemployee);
                                    self.typeActionUnemployeeInsurance(TypeActionInsuranceRate.add);
                                    nts.uk.ui.windows.sub.modal("/view/qmm/011/d/index.xhtml", { height: 400, width: 560, title: "労働保険料率の登録>履歴の追加" }).onClosed(function () {
                                        var addHistoryUnemployeeInsuranceDto = nts.uk.ui.windows.getShared("addHistoryUnemployeeInsuranceDto");
                                        if (addHistoryUnemployeeInsuranceDto != null && addHistoryUnemployeeInsuranceDto != undefined) {
                                            self.historyUnemployeeInsuranceRateStart(addHistoryUnemployeeInsuranceDto.startMonthRage);
                                            self.historyUnemployeeInsuranceRateEnd(addHistoryUnemployeeInsuranceDto.endMonthRage);
                                        }
                                    });
                                };
                                ScreenModel.prototype.openEditInsuranceBusinessType = function () {
                                    var self = this;
                                    a.service.findAllInsuranceBusinessType("companyCode001").done(function (data) {
                                        nts.uk.ui.windows.setShared("insuranceBusinessTypeUpdateDto", data);
                                        nts.uk.ui.windows.sub.modal("/view/qmm/011/e/index.xhtml", { height: 590, width: 425, title: "事業種類の登録" }).onClosed(function () {
                                            var insuranceBusinessTypeUpdateModel = nts.uk.ui.windows.getShared("insuranceBusinessTypeUpdateModel");
                                            if (insuranceBusinessTypeUpdateModel != null && insuranceBusinessTypeUpdateModel != undefined) {
                                                self.updateInsuranceBusinessTypeAccidentInsurance(insuranceBusinessTypeUpdateModel);
                                            }
                                        });
                                    });
                                };
                                ScreenModel.prototype.openEditHistoryAccidentInsuranceRate = function () {
                                    var self = this;
                                    var historyId = self.selectionHistoryAccidentInsuranceRate();
                                    nts.uk.ui.windows.setShared("historyId", historyId);
                                    nts.uk.ui.windows.setShared("type", TypeHistory.HistoryAccident);
                                    nts.uk.ui.windows.setShared("historyStart", self.historyAccidentInsuranceRateStart());
                                    nts.uk.ui.windows.setShared("historyEnd", self.historyAccidentInsuranceRateEnd());
                                    self.typeActionAccidentInsurance(TypeActionInsuranceRate.update);
                                    nts.uk.ui.windows.sub.modal("/view/qmm/011/f/index.xhtml", { height: 420, width: 500, title: "労働保険料率の登録>マスタ修正ログ" }).onClosed(function () {
                                        var updateHistoryAccidentInsuranceDto = nts.uk.ui.windows.getShared("updateHistoryAccidentInsuranceDto");
                                        if (updateHistoryAccidentInsuranceDto != null && updateHistoryAccidentInsuranceDto != undefined) {
                                            self.historyAccidentInsuranceRateStart(updateHistoryAccidentInsuranceDto.startMonthRage);
                                            self.historyAccidentInsuranceRateEnd(updateHistoryAccidentInsuranceDto.endMonthRage);
                                        }
                                    });
                                };
                                ScreenModel.prototype.openAddHistoryAccidentInsuranceRate = function () {
                                    var self = this;
                                    nts.uk.ui.windows.setShared("type", TypeHistory.HistoryAccident);
                                    self.typeActionAccidentInsurance(TypeActionInsuranceRate.add);
                                    nts.uk.ui.windows.sub.modal("/view/qmm/011/d/index.xhtml", { height: 500, width: 600, title: "労働保険料率の登録>履歴の追加" }).onClosed(function () {
                                        var addHistoryAccidentInsuranceDto = nts.uk.ui.windows.getShared("addHistoryAccidentInsuranceDto");
                                        if (addHistoryAccidentInsuranceDto != null && addHistoryAccidentInsuranceDto != undefined) {
                                            self.historyAccidentInsuranceRateStart(addHistoryAccidentInsuranceDto.startMonthRage);
                                            self.historyAccidentInsuranceRateEnd(addHistoryAccidentInsuranceDto.endMonthRage);
                                        }
                                    });
                                };
                                ScreenModel.prototype.showchangeHistoryUnemployeeInsurance = function (selectionHistoryUnemployeeInsuranceRate) {
                                    var self = this;
                                    self.findHisotryUnemployeeInsuranceRate(selectionHistoryUnemployeeInsuranceRate);
                                    self.detailHistoryUnemployeeInsuranceRate(selectionHistoryUnemployeeInsuranceRate);
                                };
                                ScreenModel.prototype.saveHistoryUnemployeeInsurance = function () {
                                    var self = this;
                                    if (self.typeActionUnemployeeInsurance() == TypeActionInsuranceRate.add) {
                                        var historyUnemployeeInsuranceDto = new HistoryUnemployeeInsuranceDto(self.selectionHistoryUnemployeeInsuranceRate(), self.historyUnemployeeInsuranceRateStart(), self.historyUnemployeeInsuranceRateEnd());
                                        a.service.addUnemployeeInsuranceRate(self.unemployeeInsuranceRateModel(), historyUnemployeeInsuranceDto).done(function (data) {
                                            self.reloadDataUnemployeeInsuranceRateByAction(self.selectionHistoryUnemployeeInsuranceRate());
                                        });
                                    }
                                    else {
                                        var historyUnemployeeInsuranceDto = new HistoryUnemployeeInsuranceDto(self.selectionHistoryUnemployeeInsuranceRate(), self.historyUnemployeeInsuranceRateStart(), self.historyUnemployeeInsuranceRateEnd());
                                        a.service.updateUnemployeeInsuranceRate(self.unemployeeInsuranceRateModel(), historyUnemployeeInsuranceDto).done(function (data) {
                                            self.reloadDataUnemployeeInsuranceRateByAction(self.selectionHistoryUnemployeeInsuranceRate());
                                        });
                                    }
                                    return true;
                                };
                                ScreenModel.prototype.saveHistoryAccidentInsurance = function () {
                                    var self = this;
                                    if (self.typeActionAccidentInsurance() == TypeActionInsuranceRate.add) {
                                        var historyAccidentInsuranceDto = new HistoryAccidentInsuranceDto(self.selectionHistoryUnemployeeInsuranceRate(), self.historyAccidentInsuranceRateStart(), self.historyAccidentInsuranceRateEnd());
                                        a.service.addAccidentInsuranceRate(self.accidentInsuranceRateModel(), historyAccidentInsuranceDto, "companyCode001");
                                    }
                                    else {
                                        var historyAccidentInsuranceDto = new HistoryAccidentInsuranceDto(self.selectionHistoryUnemployeeInsuranceRate(), self.historyAccidentInsuranceRateStart(), self.historyAccidentInsuranceRateEnd());
                                        a.service.updateAccidentInsuranceRate(self.accidentInsuranceRateModel(), historyAccidentInsuranceDto, "companyCode001");
                                    }
                                    return true;
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
                                        if (data != null && data.length > 0) {
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
                                        }
                                        else {
                                            self.newmodelEmptyDataUnemployeeInsuranceRate();
                                            dfd.resolve(self);
                                        }
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.reloadDataUnemployeeInsuranceRateByAction = function (code) {
                                    var self = this;
                                    a.service.findAllHisotryUnemployeeInsuranceRate().done(function (data) {
                                        if (data != null && data.length > 0) {
                                            if (self.lstHistoryUnemployeeInsuranceRate == null || self.lstHistoryUnemployeeInsuranceRate == undefined) {
                                                self.lstHistoryUnemployeeInsuranceRate = ko.observableArray(data);
                                            }
                                            else {
                                                self.lstHistoryUnemployeeInsuranceRate(data);
                                            }
                                            var dataCode = code;
                                            if (code == null || code == undefined && code == '') {
                                                dataCode = data[0].historyId;
                                            }
                                            if (self.selectionHistoryUnemployeeInsuranceRate == null || self.selectionHistoryUnemployeeInsuranceRate == undefined) {
                                                self.selectionHistoryUnemployeeInsuranceRate = ko.observable(dataCode);
                                                self.selectionHistoryUnemployeeInsuranceRate.subscribe(function (selectionHistoryUnemployeeInsuranceRate) {
                                                    self.showchangeHistoryUnemployeeInsurance(selectionHistoryUnemployeeInsuranceRate);
                                                });
                                            }
                                            else {
                                                self.selectionHistoryUnemployeeInsuranceRate(dataCode);
                                            }
                                            self.detailHistoryUnemployeeInsuranceRate(dataCode).done(function (data) {
                                            });
                                        }
                                        else {
                                            self.newmodelEmptyDataUnemployeeInsuranceRate();
                                        }
                                    });
                                };
                                ScreenModel.prototype.newmodelEmptyDataUnemployeeInsuranceRate = function () {
                                    var self = this;
                                    if (self.lstHistoryUnemployeeInsuranceRate == null || self.lstHistoryUnemployeeInsuranceRate == undefined) {
                                        self.lstHistoryUnemployeeInsuranceRate = ko.observableArray([]);
                                    }
                                    else {
                                        self.lstHistoryUnemployeeInsuranceRate([]);
                                    }
                                    self.unemployeeInsuranceRateModel = ko.observable(new UnemployeeInsuranceRateModel(new UnemployeeInsuranceRateDto(), self.rateInputOptions, self.selectionRoundingMethod));
                                    self.selectionHistoryUnemployeeInsuranceRate = ko.observable('');
                                    self.resetValueUnemployeeInsuranceRate();
                                    self.historyUnemployeeInsuranceRateStart('');
                                    self.historyUnemployeeInsuranceRateEnd('9999/12');
                                    self.isEmpty(true);
                                };
                                ScreenModel.prototype.resetValueUnemployeeInsuranceRate = function () {
                                    var self = this;
                                    self.unemployeeInsuranceRateModel().resetValue(self.rateInputOptions, self.selectionRoundingMethod);
                                    self.typeActionUnemployeeInsurance(TypeActionInsuranceRate.add);
                                    self.selectionHistoryUnemployeeInsuranceRate('');
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
                                    var historyAccidentInsuranceRateFindInDto;
                                    historyAccidentInsuranceRateFindInDto = new HistoryAccidentInsuranceRateFindInDto();
                                    historyAccidentInsuranceRateFindInDto.historyId = historyId;
                                    historyAccidentInsuranceRateFindInDto.companyCode = "companyCode001";
                                    a.service.findHistoryAccidentInsuranceRate(historyAccidentInsuranceRateFindInDto).done(function (data) {
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
                                    a.service.findAllHistoryAccidentInsuranceRate("companyCode001").done(function (data) {
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
                                ScreenModel.prototype.updateInsuranceBusinessTypeAccidentInsurance = function (insuranceBusinessTypeUpdateModel) {
                                    var self = this;
                                    self.accidentInsuranceRateModel().accidentInsuranceRateBiz1StModel.updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz1St());
                                    self.accidentInsuranceRateModel().accidentInsuranceRateBiz2NdModel.updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz2Nd());
                                    self.accidentInsuranceRateModel().accidentInsuranceRateBiz3RdModel.updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz3Rd());
                                    self.accidentInsuranceRateModel().accidentInsuranceRateBiz4ThModel.updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz4Th());
                                    self.accidentInsuranceRateModel().accidentInsuranceRateBiz5ThModel.updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz5Th());
                                    self.accidentInsuranceRateModel().accidentInsuranceRateBiz6ThModel.updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz6Th());
                                    self.accidentInsuranceRateModel().accidentInsuranceRateBiz7ThModel.updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz7Th());
                                    self.accidentInsuranceRateModel().accidentInsuranceRateBiz8ThModel.updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz8Th());
                                    self.accidentInsuranceRateModel().accidentInsuranceRateBiz9ThModel.updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz9Th());
                                    self.accidentInsuranceRateModel().accidentInsuranceRateBiz10ThModel.updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz10Th());
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var UnemployeeInsuranceRateItemSettingModel = (function () {
                                function UnemployeeInsuranceRateItemSettingModel(unemployeeInsuranceRateItemSetting) {
                                    this.roundAtr = ko.observable(unemployeeInsuranceRateItemSetting.roundAtr);
                                    this.rate = ko.observable(unemployeeInsuranceRateItemSetting.rate);
                                }
                                UnemployeeInsuranceRateItemSettingModel.prototype.resetValue = function () {
                                    if (this.roundAtr == null || this.roundAtr == undefined) {
                                        this.roundAtr = ko.observable(0);
                                    }
                                    else {
                                        this.roundAtr(0);
                                    }
                                    if (this.rate == null || this.rate == undefined) {
                                        this.rate = ko.observable(0);
                                    }
                                    else {
                                        this.rate(0);
                                    }
                                };
                                return UnemployeeInsuranceRateItemSettingModel;
                            }());
                            viewmodel.UnemployeeInsuranceRateItemSettingModel = UnemployeeInsuranceRateItemSettingModel;
                            var UnemployeeInsuranceRateItemModel = (function () {
                                function UnemployeeInsuranceRateItemModel(rateInputOptions, selectionRoundingMethod) {
                                    this.rateInputOptions = rateInputOptions;
                                    this.selectionRoundingMethod = selectionRoundingMethod;
                                }
                                UnemployeeInsuranceRateItemModel.prototype.resetValue = function () {
                                    if (this.companySetting == null || this.companySetting == undefined) {
                                        this.companySetting = new UnemployeeInsuranceRateItemSettingModel(new UnemployeeInsuranceRateItemSettingDto(0, 0));
                                    }
                                    else {
                                        this.companySetting.resetValue();
                                    }
                                    if (this.personalSetting == null || this.personalSetting == undefined) {
                                        this.personalSetting = new UnemployeeInsuranceRateItemSettingModel(new UnemployeeInsuranceRateItemSettingDto(0, 0));
                                    }
                                    else {
                                        this.personalSetting.resetValue();
                                    }
                                };
                                UnemployeeInsuranceRateItemModel.prototype.setCompanySetting = function (companySetting) {
                                    this.companySetting = new UnemployeeInsuranceRateItemSettingModel(companySetting);
                                };
                                UnemployeeInsuranceRateItemModel.prototype.setPersonalSetting = function (personalSetting) {
                                    this.personalSetting = new UnemployeeInsuranceRateItemSettingModel(personalSetting);
                                };
                                return UnemployeeInsuranceRateItemModel;
                            }());
                            viewmodel.UnemployeeInsuranceRateItemModel = UnemployeeInsuranceRateItemModel;
                            var UnemployeeInsuranceRateModel = (function () {
                                function UnemployeeInsuranceRateModel(unemployeeInsuranceRate, rateInputOptions, selectionRoundingMethod) {
                                    if (unemployeeInsuranceRate.rateItems != null && unemployeeInsuranceRate.rateItems.length > 0) {
                                        for (var _i = 0, _a = unemployeeInsuranceRate.rateItems; _i < _a.length; _i++) {
                                            var rateItem = _a[_i];
                                            if (rateItem.careerGroup == CareerGroupDto.Agroforestry) {
                                                this.unemployeeInsuranceRateItemAgroforestryModel =
                                                    new UnemployeeInsuranceRateItemModel(rateInputOptions, selectionRoundingMethod);
                                                this.unemployeeInsuranceRateItemAgroforestryModel.resetValue();
                                                this.unemployeeInsuranceRateItemAgroforestryModel.setCompanySetting(rateItem.companySetting);
                                                this.unemployeeInsuranceRateItemAgroforestryModel.setPersonalSetting(rateItem.personalSetting);
                                            }
                                            else if (rateItem.careerGroup == CareerGroupDto.Contruction) {
                                                this.unemployeeInsuranceRateItemContructionModel =
                                                    new UnemployeeInsuranceRateItemModel(rateInputOptions, selectionRoundingMethod);
                                                this.unemployeeInsuranceRateItemContructionModel.resetValue();
                                                this.unemployeeInsuranceRateItemContructionModel.setCompanySetting(rateItem.companySetting);
                                                this.unemployeeInsuranceRateItemContructionModel.setPersonalSetting(rateItem.personalSetting);
                                            }
                                            else if (rateItem.careerGroup == CareerGroupDto.Other) {
                                                this.unemployeeInsuranceRateItemOtherModel =
                                                    new UnemployeeInsuranceRateItemModel(rateInputOptions, selectionRoundingMethod);
                                                this.unemployeeInsuranceRateItemOtherModel.resetValue();
                                                this.unemployeeInsuranceRateItemOtherModel.setCompanySetting(rateItem.companySetting);
                                                this.unemployeeInsuranceRateItemOtherModel.setPersonalSetting(rateItem.personalSetting);
                                            }
                                        }
                                    }
                                    else {
                                        this.unemployeeInsuranceRateItemAgroforestryModel =
                                            new UnemployeeInsuranceRateItemModel(rateInputOptions, selectionRoundingMethod);
                                        this.unemployeeInsuranceRateItemAgroforestryModel.resetValue();
                                        this.unemployeeInsuranceRateItemContructionModel =
                                            new UnemployeeInsuranceRateItemModel(rateInputOptions, selectionRoundingMethod);
                                        this.unemployeeInsuranceRateItemContructionModel.resetValue();
                                        this.unemployeeInsuranceRateItemOtherModel =
                                            new UnemployeeInsuranceRateItemModel(rateInputOptions, selectionRoundingMethod);
                                        this.unemployeeInsuranceRateItemContructionModel.resetValue();
                                    }
                                    this.version = ko.observable(unemployeeInsuranceRate.version);
                                }
                                UnemployeeInsuranceRateModel.prototype.resetValue = function (rateInputOptions, selectionRoundingMethod) {
                                    if (this.unemployeeInsuranceRateItemAgroforestryModel == null || this.unemployeeInsuranceRateItemAgroforestryModel == undefined) {
                                        this.unemployeeInsuranceRateItemAgroforestryModel = new UnemployeeInsuranceRateItemModel(rateInputOptions, selectionRoundingMethod);
                                        this.unemployeeInsuranceRateItemAgroforestryModel.resetValue();
                                    }
                                    else {
                                        this.unemployeeInsuranceRateItemAgroforestryModel.resetValue();
                                    }
                                    if (this.unemployeeInsuranceRateItemContructionModel == null || this.unemployeeInsuranceRateItemContructionModel == undefined) {
                                        this.unemployeeInsuranceRateItemContructionModel = new UnemployeeInsuranceRateItemModel(rateInputOptions, selectionRoundingMethod);
                                        this.unemployeeInsuranceRateItemContructionModel.resetValue();
                                    }
                                    else {
                                        this.unemployeeInsuranceRateItemContructionModel.resetValue();
                                    }
                                    if (this.unemployeeInsuranceRateItemOtherModel == null || this.unemployeeInsuranceRateItemOtherModel == undefined) {
                                        this.unemployeeInsuranceRateItemOtherModel = new UnemployeeInsuranceRateItemModel(rateInputOptions, selectionRoundingMethod);
                                        this.unemployeeInsuranceRateItemOtherModel.resetValue();
                                    }
                                    else {
                                        this.unemployeeInsuranceRateItemOtherModel.resetValue();
                                    }
                                };
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
                                AccidentInsuranceRateDetailModel.prototype.updateInsuranceBusinessType = function (insuranceBusinessType) {
                                    if (this.insuranceBusinessType() != null && this.insuranceBusinessType() != undefined) {
                                        this.insuranceBusinessType(insuranceBusinessType);
                                    }
                                };
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
                                        this.version = ko.observable(accidentInsuranceRate.version);
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
