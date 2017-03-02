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
                                    self.itemName = ko.observable('');
                                    self.currentCode = ko.observable(2);
                                    self.isEnable = ko.observable(true);
                                    self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                                    self.typeActionUnemployeeInsurance = ko.observable(TypeActionInsuranceRate.add);
                                    self.typeActionAccidentInsurance = ko.observable(TypeActionInsuranceRate.add);
                                    self.isEmptyUnemployee = ko.observable(true);
                                    self.isEmptyAccident = ko.observable(true);
                                    self.accidentInsuranceRateModel = ko.observable(new AccidentInsuranceRateModel(self.rateInputOptions, self.selectionRoundingMethod));
                                    self.unemployeeInsuranceRateModel = ko.observable(new UnemployeeInsuranceRateModel(self.rateInputOptions, self.selectionRoundingMethod));
                                    self.selectionHistoryUnemployeeInsuranceRate = ko.observable('');
                                    self.selectionHistoryAccidentInsuranceRate = ko.observable('');
                                }
                                ScreenModel.prototype.openEditHistoryUnemployeeInsuranceRate = function () {
                                    var self = this;
                                    var historyId = self.selectionHistoryUnemployeeInsuranceRate();
                                    a.service.findHisotryUnemployeeInsuranceRate(historyId).done(function (data) {
                                        nts.uk.ui.windows.setShared("historyEnd", data.endMonthRage);
                                        nts.uk.ui.windows.setShared("historyStart", data.startMonthRage);
                                        nts.uk.ui.windows.setShared("historyId", data.historyId);
                                        nts.uk.ui.windows.setShared("type", TypeHistory.HistoryUnemployee);
                                        self.typeActionUnemployeeInsurance(TypeActionInsuranceRate.update);
                                        nts.uk.ui.windows.sub.modal('/view/qmm/011/f/index.xhtml', { title: '労働保険料率の登録>マスタ修正ログ', dialogClass: 'no-close' }).onClosed(function () {
                                            var updateHistoryUnemployeeInsuranceDto = nts.uk.ui.windows.getShared("updateHistoryUnemployeeInsuranceDto");
                                            if (updateHistoryUnemployeeInsuranceDto != null && updateHistoryUnemployeeInsuranceDto != undefined) {
                                                self.unemployeeInsuranceRateModel().setHistoryData(updateHistoryUnemployeeInsuranceDto);
                                            }
                                        });
                                    });
                                };
                                ScreenModel.prototype.openAddHistoryUnemployeeInsuranceRate = function () {
                                    var self = this;
                                    nts.uk.ui.windows.setShared("type", TypeHistory.HistoryUnemployee);
                                    nts.uk.ui.windows.sub.modal('/view/qmm/011/d/index.xhtml', { title: '労働保険料率の登録>履歴の追加', dialogClass: 'no-close' }).onClosed(function () {
                                        var addHistoryUnemployeeInsuranceDto = nts.uk.ui.windows.getShared("addHistoryUnemployeeInsuranceDto");
                                        if (addHistoryUnemployeeInsuranceDto != null && addHistoryUnemployeeInsuranceDto != undefined) {
                                            self.unemployeeInsuranceRateModel().setHistoryData(addHistoryUnemployeeInsuranceDto);
                                            self.typeActionUnemployeeInsurance(TypeActionInsuranceRate.add);
                                        }
                                    });
                                };
                                ScreenModel.prototype.openEditInsuranceBusinessType = function () {
                                    var self = this;
                                    a.service.findAllInsuranceBusinessType().done(function (data) {
                                        nts.uk.ui.windows.setShared("InsuranceBusinessTypeDto", data);
                                        nts.uk.ui.windows.sub.modal("/view/qmm/011/e/index.xhtml", { height: 600, width: 425, title: "事業種類の登録" }).onClosed(function () {
                                            var insuranceBusinessTypeUpdateModel = nts.uk.ui.windows.getShared("insuranceBusinessTypeUpdateModel");
                                            if (insuranceBusinessTypeUpdateModel != null && insuranceBusinessTypeUpdateModel != undefined) {
                                                a.service.findAllInsuranceBusinessType().done(function (data) {
                                                    self.updateInsuranceBusinessTypeAccidentInsuranceDto(data);
                                                });
                                            }
                                        });
                                    });
                                };
                                ScreenModel.prototype.openEditHistoryAccidentInsuranceRate = function () {
                                    var self = this;
                                    var historyId = self.selectionHistoryAccidentInsuranceRate();
                                    a.service.findHistoryAccidentInsuranceRate(historyId).done(function (data) {
                                        nts.uk.ui.windows.setShared("historyEnd", data.endMonthRage);
                                        nts.uk.ui.windows.setShared("historyStart", data.startMonthRage);
                                        nts.uk.ui.windows.setShared("historyId", data.historyId);
                                        nts.uk.ui.windows.setShared("type", TypeHistory.HistoryAccident);
                                        self.typeActionAccidentInsurance(TypeActionInsuranceRate.update);
                                        nts.uk.ui.windows.sub.modal('/view/qmm/011/f/index.xhtml', { title: '労働保険料率の登録>マスタ修正ログ', dialogClass: 'no-close' }).onClosed(function () {
                                            var updateHistoryAccidentInsuranceDto = nts.uk.ui.windows.getShared("updateHistoryAccidentInsuranceDto");
                                            if (updateHistoryAccidentInsuranceDto != null && updateHistoryAccidentInsuranceDto != undefined) {
                                                self.accidentInsuranceRateModel().setHistoryData(updateHistoryAccidentInsuranceDto);
                                            }
                                        });
                                    });
                                };
                                ScreenModel.prototype.openAddHistoryAccidentInsuranceRate = function () {
                                    var self = this;
                                    nts.uk.ui.windows.setShared("type", TypeHistory.HistoryAccident);
                                    nts.uk.ui.windows.sub.modal('/view/qmm/011/d/index.xhtml', { title: '労働保険料率の登録>履歴の追加', dialogClass: 'no-close' }).onClosed(function () {
                                        var addHistoryAccidentInsuranceDto = nts.uk.ui.windows.getShared("addHistoryAccidentInsuranceDto");
                                        if (addHistoryAccidentInsuranceDto != null && addHistoryAccidentInsuranceDto != undefined) {
                                            self.accidentInsuranceRateModel().setHistoryData(addHistoryAccidentInsuranceDto);
                                            self.typeActionAccidentInsurance(TypeActionInsuranceRate.add);
                                        }
                                    });
                                };
                                ScreenModel.prototype.showchangeHistoryUnemployeeInsurance = function (selectionHistoryUnemployeeInsuranceRate) {
                                    if (selectionHistoryUnemployeeInsuranceRate != null && selectionHistoryUnemployeeInsuranceRate != undefined && selectionHistoryUnemployeeInsuranceRate != '') {
                                        var self = this;
                                        self.detailHistoryUnemployeeInsuranceRate(selectionHistoryUnemployeeInsuranceRate);
                                    }
                                };
                                ScreenModel.prototype.clearErrorSaveUnemployeeInsurance = function () {
                                    var self = this;
                                    $('.save-error').ntsError('clear');
                                    $('#btn_saveHistoryUnemployeeInsurance').ntsError('clear');
                                };
                                ScreenModel.prototype.showMessageSaveUnemployeeInsurance = function (message) {
                                    $('#btn_saveHistoryUnemployeeInsurance').ntsError('set', message);
                                };
                                ScreenModel.prototype.clearErrorSaveAccidentInsurance = function () {
                                    var self = this;
                                    $('.save-error').ntsError('clear');
                                    $('#btn_saveHistoryAccidentInsurance').ntsError('clear');
                                };
                                ScreenModel.prototype.showMessageSaveAccidentInsurance = function (message) {
                                    $('#btn_saveHistoryAccidentInsurance').ntsError('set', message);
                                };
                                ScreenModel.prototype.saveHistoryUnemployeeInsurance = function () {
                                    var self = this;
                                    if (self.typeActionUnemployeeInsurance() == TypeActionInsuranceRate.add) {
                                        a.service.addUnemployeeInsuranceRate(self.unemployeeInsuranceRateModel()).done(function (data) {
                                            self.reloadDataUnemployeeInsuranceRateByAction();
                                            self.clearErrorSaveUnemployeeInsurance();
                                        }).fail(function (res) {
                                            self.showMessageSaveUnemployeeInsurance(res.message);
                                        });
                                    }
                                    else {
                                        a.service.updateUnemployeeInsuranceRate(self.unemployeeInsuranceRateModel()).done(function (data) {
                                            self.reloadDataUnemployeeInsuranceRateByAction();
                                            self.clearErrorSaveUnemployeeInsurance();
                                        }).fail(function (res) {
                                            self.showMessageSaveUnemployeeInsurance(res.message);
                                        });
                                    }
                                    return true;
                                };
                                ScreenModel.prototype.saveHistoryAccidentInsurance = function () {
                                    var self = this;
                                    if (self.typeActionAccidentInsurance() == TypeActionInsuranceRate.add) {
                                        a.service.addAccidentInsuranceRate(self.accidentInsuranceRateModel()).done(function (data) {
                                            self.reloadDataAccidentInsuranceRateByAction();
                                            self.clearErrorSaveAccidentInsurance();
                                        }).fail(function (res) {
                                            self.showMessageSaveAccidentInsurance(res.message);
                                        });
                                    }
                                    else {
                                        a.service.updateAccidentInsuranceRate(self.accidentInsuranceRateModel()).done(function (data) {
                                            self.reloadDataAccidentInsuranceRateByAction();
                                            self.clearErrorSaveAccidentInsurance();
                                        }).fail(function (res) {
                                            self.showMessageSaveAccidentInsurance(res.message);
                                        });
                                    }
                                    return true;
                                };
                                ScreenModel.prototype.showchangeHistoryAccidentInsurance = function (selectionHistoryAccidentInsuranceRate) {
                                    if (selectionHistoryAccidentInsuranceRate != null && selectionHistoryAccidentInsuranceRate != undefined && selectionHistoryAccidentInsuranceRate != '') {
                                        var self = this;
                                        self.detailHistoryAccidentInsuranceRate(selectionHistoryAccidentInsuranceRate);
                                    }
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
                                            self.selectionHistoryUnemployeeInsuranceRate(data[0].historyId);
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
                                ScreenModel.prototype.reloadDataUnemployeeInsuranceRateByAction = function () {
                                    var self = this;
                                    a.service.findAllHisotryUnemployeeInsuranceRate().done(function (data) {
                                        if (data != null && data.length > 0) {
                                            self.selectionHistoryUnemployeeInsuranceRate('');
                                            self.lstHistoryUnemployeeInsuranceRate([]);
                                            var historyId = self.unemployeeInsuranceRateModel().historyUnemployeeInsuranceModel.historyId();
                                            if (self.typeActionUnemployeeInsurance() == TypeActionInsuranceRate.add) {
                                                historyId = data[0].historyId;
                                            }
                                            if (self.isEmptyUnemployee()) {
                                                self.selectionHistoryUnemployeeInsuranceRate.subscribe(function (selectionHistoryUnemployeeInsuranceRate) {
                                                    self.showchangeHistoryUnemployeeInsurance(self.selectionHistoryUnemployeeInsuranceRate());
                                                });
                                                self.isEmptyUnemployee(false);
                                            }
                                            self.selectionHistoryUnemployeeInsuranceRate(historyId);
                                            self.lstHistoryUnemployeeInsuranceRate(data);
                                            self.detailHistoryUnemployeeInsuranceRate(historyId).done(function (data) {
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
                                    self.unemployeeInsuranceRateModel = ko.observable(new UnemployeeInsuranceRateModel(self.rateInputOptions, self.selectionRoundingMethod));
                                    self.selectionHistoryUnemployeeInsuranceRate('');
                                    self.resetValueUnemployeeInsuranceRate();
                                    self.isEmptyUnemployee(true);
                                };
                                ScreenModel.prototype.resetValueUnemployeeInsuranceRate = function () {
                                    var self = this;
                                    self.unemployeeInsuranceRateModel().resetValue(self.rateInputOptions, self.selectionRoundingMethod);
                                    self.typeActionUnemployeeInsurance(TypeActionInsuranceRate.add);
                                    self.selectionHistoryUnemployeeInsuranceRate('');
                                };
                                ScreenModel.prototype.detailHistoryUnemployeeInsuranceRate = function (historyId) {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    if (historyId != null && historyId != undefined && historyId != '') {
                                        a.service.detailHistoryUnemployeeInsuranceRate(historyId).done(function (data) {
                                            self.unemployeeInsuranceRateModel().setListItem(data.rateItems);
                                            self.unemployeeInsuranceRateModel().setHistoryData(data.historyInsurance);
                                            self.typeActionUnemployeeInsurance(TypeActionInsuranceRate.update);
                                            dfd.resolve(null);
                                        });
                                    }
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.findAllHistoryAccidentInsuranceRate = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    a.service.findAllHistoryAccidentInsuranceRate().done(function (data) {
                                        if (data != null && data.length > 0) {
                                            self.lstHistoryAccidentInsuranceRate = ko.observableArray(data);
                                            self.selectionHistoryAccidentInsuranceRate = ko.observable(data[0].historyId);
                                            self.selectionHistoryAccidentInsuranceRate.subscribe(function (selectionHistoryAccidentInsuranceRate) {
                                                self.showchangeHistoryAccidentInsurance(selectionHistoryAccidentInsuranceRate);
                                            });
                                            self.detailHistoryAccidentInsuranceRate(data[0].historyId).done(function (data) {
                                                a.service.findAllInsuranceBusinessType().done(function (data) {
                                                    self.updateInsuranceBusinessTypeAccidentInsuranceDto(data);
                                                    dfd.resolve(self);
                                                });
                                            });
                                        }
                                        else {
                                            self.newmodelEmptyDataAccidentInsuranceRate();
                                            dfd.resolve(self);
                                        }
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.reloadDataAccidentInsuranceRateByAction = function () {
                                    var self = this;
                                    a.service.findAllHistoryAccidentInsuranceRate().done(function (data) {
                                        if (data != null && data.length > 0) {
                                            self.selectionHistoryAccidentInsuranceRate('');
                                            self.lstHistoryAccidentInsuranceRate([]);
                                            var historyId = self.accidentInsuranceRateModel().historyAccidentInsuranceRateModel.historyId();
                                            if (self.typeActionAccidentInsurance() == TypeActionInsuranceRate.add) {
                                                historyId = data[0].historyId;
                                            }
                                            self.selectionHistoryAccidentInsuranceRate(historyId);
                                            self.lstHistoryAccidentInsuranceRate(data);
                                            self.detailHistoryAccidentInsuranceRate(historyId).done(function (data) {
                                                a.service.findAllInsuranceBusinessType().done(function (data) {
                                                    self.updateInsuranceBusinessTypeAccidentInsuranceDto(data);
                                                });
                                            });
                                            if (self.isEmptyAccident()) {
                                                self.selectionHistoryAccidentInsuranceRate.subscribe(function (selectionHistoryAccidentInsuranceRate) {
                                                    self.showchangeHistoryAccidentInsurance(selectionHistoryAccidentInsuranceRate);
                                                });
                                                self.isEmptyAccident(false);
                                            }
                                        }
                                        else {
                                            self.newmodelEmptyDataAccidentInsuranceRate();
                                        }
                                    });
                                };
                                ScreenModel.prototype.newmodelEmptyDataAccidentInsuranceRate = function () {
                                    var self = this;
                                    if (self.lstHistoryAccidentInsuranceRate == null || self.lstHistoryAccidentInsuranceRate == undefined) {
                                        self.lstHistoryAccidentInsuranceRate = ko.observableArray([]);
                                    }
                                    else {
                                        self.lstHistoryAccidentInsuranceRate([]);
                                    }
                                    self.accidentInsuranceRateModel = ko.observable(new AccidentInsuranceRateModel(self.rateInputOptions, self.selectionRoundingMethod));
                                    self.selectionHistoryAccidentInsuranceRate = ko.observable('');
                                    self.resetValueAccidentInsuranceRate();
                                    a.service.findAllInsuranceBusinessType().done(function (data) {
                                        self.updateInsuranceBusinessTypeAccidentInsuranceDto(data);
                                    });
                                    self.isEmptyAccident(true);
                                };
                                ScreenModel.prototype.resetValueAccidentInsuranceRate = function () {
                                    var self = this;
                                    self.accidentInsuranceRateModel().resetValue(self.rateInputOptions, self.selectionRoundingMethod);
                                    self.typeActionAccidentInsurance(TypeActionInsuranceRate.add);
                                    self.selectionHistoryAccidentInsuranceRate('');
                                };
                                ScreenModel.prototype.detailHistoryAccidentInsuranceRate = function (historyId) {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    if (historyId != null && historyId != undefined && historyId != '') {
                                        a.service.findAccidentInsuranceRate(historyId).done(function (data) {
                                            self.accidentInsuranceRateModel().setListItem(data.rateItems);
                                            self.accidentInsuranceRateModel().setVersion(data.version);
                                            self.typeActionAccidentInsurance(TypeActionInsuranceRate.update);
                                            self.accidentInsuranceRateModel().setHistoryData(data.historyInsurance);
                                            dfd.resolve(null);
                                        });
                                    }
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
                                ScreenModel.prototype.updateInsuranceBusinessTypeAccidentInsuranceDto = function (InsuranceBusinessTypeDto) {
                                    var self = this;
                                    self.accidentInsuranceRateModel().accidentInsuranceRateBiz1StModel.updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz1St);
                                    self.accidentInsuranceRateModel().accidentInsuranceRateBiz2NdModel.updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz2Nd);
                                    self.accidentInsuranceRateModel().accidentInsuranceRateBiz3RdModel.updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz3Rd);
                                    self.accidentInsuranceRateModel().accidentInsuranceRateBiz4ThModel.updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz4Th);
                                    self.accidentInsuranceRateModel().accidentInsuranceRateBiz5ThModel.updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz5Th);
                                    self.accidentInsuranceRateModel().accidentInsuranceRateBiz6ThModel.updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz6Th);
                                    self.accidentInsuranceRateModel().accidentInsuranceRateBiz7ThModel.updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz7Th);
                                    self.accidentInsuranceRateModel().accidentInsuranceRateBiz8ThModel.updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz8Th);
                                    self.accidentInsuranceRateModel().accidentInsuranceRateBiz9ThModel.updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz9Th);
                                    self.accidentInsuranceRateModel().accidentInsuranceRateBiz10ThModel.updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz10Th);
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var UnemployeeInsuranceRateItemSettingModel = (function () {
                                function UnemployeeInsuranceRateItemSettingModel() {
                                    this.roundAtr = ko.observable(0);
                                    this.rate = ko.observable(0);
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
                                UnemployeeInsuranceRateItemSettingModel.prototype.setItem = function (unemployeeInsuranceRateItemSetting) {
                                    this.roundAtr(unemployeeInsuranceRateItemSetting.roundAtr);
                                    this.rate(unemployeeInsuranceRateItemSetting.rate);
                                };
                                return UnemployeeInsuranceRateItemSettingModel;
                            }());
                            viewmodel.UnemployeeInsuranceRateItemSettingModel = UnemployeeInsuranceRateItemSettingModel;
                            var UnemployeeInsuranceRateItemModel = (function () {
                                function UnemployeeInsuranceRateItemModel(rateInputOptions, selectionRoundingMethod) {
                                    this.rateInputOptions = rateInputOptions;
                                    this.selectionRoundingMethod = selectionRoundingMethod;
                                    this.companySetting = new UnemployeeInsuranceRateItemSettingModel();
                                    this.personalSetting = new UnemployeeInsuranceRateItemSettingModel();
                                }
                                UnemployeeInsuranceRateItemModel.prototype.resetValue = function () {
                                    if (this.companySetting == null || this.companySetting == undefined) {
                                        this.companySetting = new UnemployeeInsuranceRateItemSettingModel();
                                        this.companySetting.resetValue();
                                    }
                                    else {
                                        this.companySetting.resetValue();
                                    }
                                    if (this.personalSetting == null || this.personalSetting == undefined) {
                                        this.personalSetting = new UnemployeeInsuranceRateItemSettingModel();
                                        this.personalSetting.resetValue();
                                    }
                                    else {
                                        this.personalSetting.resetValue();
                                    }
                                };
                                UnemployeeInsuranceRateItemModel.prototype.setItem = function (unemployeeInsuranceRateItemDto) {
                                    this.companySetting.setItem(unemployeeInsuranceRateItemDto.companySetting);
                                    this.personalSetting.setItem(unemployeeInsuranceRateItemDto.personalSetting);
                                };
                                return UnemployeeInsuranceRateItemModel;
                            }());
                            viewmodel.UnemployeeInsuranceRateItemModel = UnemployeeInsuranceRateItemModel;
                            var HistoryUnemployeeInsuranceModel = (function () {
                                function HistoryUnemployeeInsuranceModel() {
                                    this.historyId = ko.observable('');
                                    this.startMonthRage = ko.observable('');
                                    this.endMonthRage = ko.observable('');
                                }
                                HistoryUnemployeeInsuranceModel.prototype.resetValue = function () {
                                    if (this.historyId != null && this.historyId != undefined) {
                                        this.historyId('');
                                    }
                                    else {
                                        this.historyId = ko.observable('');
                                    }
                                    if (this.startMonthRage != null && this.startMonthRage != undefined) {
                                        this.startMonthRage('');
                                    }
                                    else {
                                        this.startMonthRage = ko.observable('');
                                    }
                                    if (this.endMonthRage != null && this.endMonthRage != undefined) {
                                        this.endMonthRage('9999/12');
                                    }
                                    else {
                                        this.endMonthRage = ko.observable('9999/12');
                                    }
                                };
                                HistoryUnemployeeInsuranceModel.prototype.updateData = function (historyUnemployeeInsurance) {
                                    this.resetValue();
                                    this.historyId(historyUnemployeeInsurance.historyId);
                                    this.startMonthRage(historyUnemployeeInsurance.startMonthRage);
                                    this.endMonthRage(historyUnemployeeInsurance.endMonthRage);
                                };
                                return HistoryUnemployeeInsuranceModel;
                            }());
                            viewmodel.HistoryUnemployeeInsuranceModel = HistoryUnemployeeInsuranceModel;
                            var UnemployeeInsuranceRateModel = (function () {
                                function UnemployeeInsuranceRateModel(rateInputOptions, selectionRoundingMethod) {
                                    this.unemployeeInsuranceRateItemAgroforestryModel = new UnemployeeInsuranceRateItemModel(rateInputOptions, selectionRoundingMethod);
                                    this.unemployeeInsuranceRateItemContructionModel = new UnemployeeInsuranceRateItemModel(rateInputOptions, selectionRoundingMethod);
                                    this.unemployeeInsuranceRateItemOtherModel = new UnemployeeInsuranceRateItemModel(rateInputOptions, selectionRoundingMethod);
                                    this.version = ko.observable(0);
                                    this.historyUnemployeeInsuranceModel = new HistoryUnemployeeInsuranceModel();
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
                                    if (this.historyUnemployeeInsuranceModel == null || this.historyUnemployeeInsuranceModel == undefined) {
                                        this.historyUnemployeeInsuranceModel = new HistoryUnemployeeInsuranceModel();
                                    }
                                    else {
                                        this.historyUnemployeeInsuranceModel.resetValue();
                                    }
                                };
                                UnemployeeInsuranceRateModel.prototype.setHistoryData = function (historyUnemployeeInsuranceDto) {
                                    this.historyUnemployeeInsuranceModel.updateData(historyUnemployeeInsuranceDto);
                                };
                                UnemployeeInsuranceRateModel.prototype.setListItem = function (rateItems) {
                                    if (rateItems != null && rateItems.length > 0) {
                                        for (var _i = 0, rateItems_1 = rateItems; _i < rateItems_1.length; _i++) {
                                            var rateItem = rateItems_1[_i];
                                            if (rateItem.careerGroup == CareerGroupDto.Agroforestry) {
                                                this.unemployeeInsuranceRateItemAgroforestryModel.setItem(rateItem);
                                            }
                                            else if (rateItem.careerGroup == CareerGroupDto.Contruction) {
                                                this.unemployeeInsuranceRateItemContructionModel.setItem(rateItem);
                                            }
                                            else if (rateItem.careerGroup == CareerGroupDto.Other) {
                                                this.unemployeeInsuranceRateItemOtherModel.setItem(rateItem);
                                            }
                                        }
                                    }
                                };
                                UnemployeeInsuranceRateModel.prototype.setVersion = function (version) {
                                    this.version(version);
                                };
                                return UnemployeeInsuranceRateModel;
                            }());
                            viewmodel.UnemployeeInsuranceRateModel = UnemployeeInsuranceRateModel;
                            var AccidentInsuranceRateDetailModel = (function () {
                                function AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod) {
                                    this.rateInputOptions = rateInputOptions;
                                    this.selectionRoundingMethod = selectionRoundingMethod;
                                    this.insuranceBusinessType = ko.observable('');
                                    this.insuRate = ko.observable(0);
                                    this.insuRound = ko.observable(0);
                                }
                                AccidentInsuranceRateDetailModel.prototype.updateInsuranceBusinessType = function (insuranceBusinessType) {
                                    if (this.insuranceBusinessType() != null && this.insuranceBusinessType() != undefined) {
                                        this.insuranceBusinessType(insuranceBusinessType);
                                    }
                                };
                                AccidentInsuranceRateDetailModel.prototype.setItem = function (insuBizRateItem) {
                                    this.insuRate(insuBizRateItem.insuRate);
                                    this.insuRound(insuBizRateItem.insuRound);
                                };
                                AccidentInsuranceRateDetailModel.prototype.resetValue = function () {
                                    if (this.insuRate == null || this.insuRate == undefined) {
                                        this.insuRate = ko.observable(0);
                                    }
                                    else {
                                        this.insuRate(0);
                                    }
                                    if (this.insuRound == null || this.insuRound == undefined) {
                                        this.insuRound = ko.observable(0);
                                    }
                                    else {
                                        this.insuRate(0);
                                    }
                                };
                                return AccidentInsuranceRateDetailModel;
                            }());
                            viewmodel.AccidentInsuranceRateDetailModel = AccidentInsuranceRateDetailModel;
                            var HistoryAccidentInsuranceRateModel = (function () {
                                function HistoryAccidentInsuranceRateModel() {
                                    this.historyId = ko.observable('');
                                    this.startMonthRage = ko.observable('');
                                    this.endMonthRage = ko.observable('');
                                }
                                HistoryAccidentInsuranceRateModel.prototype.resetValue = function () {
                                    if (this.historyId != null && this.historyId != undefined) {
                                        this.historyId('');
                                    }
                                    else {
                                        this.historyId = ko.observable('');
                                    }
                                    if (this.startMonthRage != null && this.startMonthRage != undefined) {
                                        this.startMonthRage('');
                                    }
                                    else {
                                        this.startMonthRage = ko.observable('');
                                    }
                                    if (this.endMonthRage != null && this.endMonthRage != undefined) {
                                        this.endMonthRage('9999/12');
                                    }
                                    else {
                                        this.endMonthRage = ko.observable('9999/12');
                                    }
                                };
                                HistoryAccidentInsuranceRateModel.prototype.updateData = function (historyAccidentInsuranceRateDto) {
                                    this.resetValue();
                                    this.historyId(historyAccidentInsuranceRateDto.historyId);
                                    this.startMonthRage(historyAccidentInsuranceRateDto.startMonthRage);
                                    this.endMonthRage(historyAccidentInsuranceRateDto.endMonthRage);
                                };
                                return HistoryAccidentInsuranceRateModel;
                            }());
                            viewmodel.HistoryAccidentInsuranceRateModel = HistoryAccidentInsuranceRateModel;
                            var AccidentInsuranceRateModel = (function () {
                                function AccidentInsuranceRateModel(rateInputOptions, selectionRoundingMethod) {
                                    this.accidentInsuranceRateBiz1StModel =
                                        new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                                    this.accidentInsuranceRateBiz2NdModel =
                                        new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                                    this.accidentInsuranceRateBiz3RdModel =
                                        new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                                    this.accidentInsuranceRateBiz4ThModel =
                                        new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                                    this.accidentInsuranceRateBiz5ThModel =
                                        new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                                    this.accidentInsuranceRateBiz6ThModel =
                                        new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                                    this.accidentInsuranceRateBiz7ThModel =
                                        new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                                    this.accidentInsuranceRateBiz8ThModel =
                                        new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                                    this.accidentInsuranceRateBiz9ThModel =
                                        new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                                    this.accidentInsuranceRateBiz10ThModel =
                                        new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                                    this.historyAccidentInsuranceRateModel = new HistoryAccidentInsuranceRateModel();
                                }
                                AccidentInsuranceRateModel.prototype.setListItem = function (lstInsuBizRateItem) {
                                    for (var _i = 0, lstInsuBizRateItem_1 = lstInsuBizRateItem; _i < lstInsuBizRateItem_1.length; _i++) {
                                        var rateItem = lstInsuBizRateItem_1[_i];
                                        if (rateItem.insuBizType == BusinessTypeEnumDto.Biz1St) {
                                            this.accidentInsuranceRateBiz1StModel.setItem(rateItem);
                                        }
                                        if (rateItem.insuBizType == BusinessTypeEnumDto.Biz2Nd) {
                                            this.accidentInsuranceRateBiz2NdModel.setItem(rateItem);
                                        }
                                        if (rateItem.insuBizType == BusinessTypeEnumDto.Biz3Rd) {
                                            this.accidentInsuranceRateBiz3RdModel.setItem(rateItem);
                                        }
                                        if (rateItem.insuBizType == BusinessTypeEnumDto.Biz4Th) {
                                            this.accidentInsuranceRateBiz4ThModel.setItem(rateItem);
                                        }
                                        if (rateItem.insuBizType == BusinessTypeEnumDto.Biz5Th) {
                                            this.accidentInsuranceRateBiz5ThModel.setItem(rateItem);
                                        }
                                        if (rateItem.insuBizType == BusinessTypeEnumDto.Biz6Th) {
                                            this.accidentInsuranceRateBiz6ThModel.setItem(rateItem);
                                        }
                                        if (rateItem.insuBizType == BusinessTypeEnumDto.Biz7Th) {
                                            this.accidentInsuranceRateBiz7ThModel.setItem(rateItem);
                                        }
                                        if (rateItem.insuBizType == BusinessTypeEnumDto.Biz8Th) {
                                            this.accidentInsuranceRateBiz8ThModel.setItem(rateItem);
                                        }
                                        if (rateItem.insuBizType == BusinessTypeEnumDto.Biz9Th) {
                                            this.accidentInsuranceRateBiz9ThModel.setItem(rateItem);
                                        }
                                        if (rateItem.insuBizType == BusinessTypeEnumDto.Biz10Th) {
                                            this.accidentInsuranceRateBiz10ThModel.setItem(rateItem);
                                        }
                                    }
                                };
                                AccidentInsuranceRateModel.prototype.setVersion = function (version) {
                                    if (this.version == null || this.version == undefined) {
                                        this.version = ko.observable(version);
                                    }
                                    else {
                                        this.version(version);
                                    }
                                };
                                AccidentInsuranceRateModel.prototype.setHistoryData = function (historyAccidentInsuranceRateDto) {
                                    this.historyAccidentInsuranceRateModel.updateData(historyAccidentInsuranceRateDto);
                                };
                                AccidentInsuranceRateModel.prototype.resetValue = function (rateInputOptions, selectionRoundingMethod) {
                                    if (this.accidentInsuranceRateBiz1StModel == null || this.accidentInsuranceRateBiz1StModel == undefined) {
                                        this.accidentInsuranceRateBiz1StModel = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                                        this.accidentInsuranceRateBiz1StModel.resetValue();
                                    }
                                    else {
                                        this.accidentInsuranceRateBiz1StModel.resetValue();
                                    }
                                    if (this.accidentInsuranceRateBiz2NdModel == null || this.accidentInsuranceRateBiz2NdModel == undefined) {
                                        this.accidentInsuranceRateBiz2NdModel = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                                        this.accidentInsuranceRateBiz2NdModel.resetValue();
                                    }
                                    else {
                                        this.accidentInsuranceRateBiz2NdModel.resetValue();
                                    }
                                    if (this.accidentInsuranceRateBiz3RdModel == null || this.accidentInsuranceRateBiz3RdModel == undefined) {
                                        this.accidentInsuranceRateBiz3RdModel = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                                        this.accidentInsuranceRateBiz3RdModel.resetValue();
                                    }
                                    else {
                                        this.accidentInsuranceRateBiz3RdModel.resetValue();
                                    }
                                    if (this.accidentInsuranceRateBiz4ThModel == null || this.accidentInsuranceRateBiz4ThModel == undefined) {
                                        this.accidentInsuranceRateBiz4ThModel = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                                        this.accidentInsuranceRateBiz4ThModel.resetValue();
                                    }
                                    else {
                                        this.accidentInsuranceRateBiz4ThModel.resetValue();
                                    }
                                    if (this.accidentInsuranceRateBiz5ThModel == null || this.accidentInsuranceRateBiz5ThModel == undefined) {
                                        this.accidentInsuranceRateBiz5ThModel = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                                        this.accidentInsuranceRateBiz5ThModel.resetValue();
                                    }
                                    else {
                                        this.accidentInsuranceRateBiz5ThModel.resetValue();
                                    }
                                    if (this.accidentInsuranceRateBiz6ThModel == null || this.accidentInsuranceRateBiz6ThModel == undefined) {
                                        this.accidentInsuranceRateBiz6ThModel = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                                        this.accidentInsuranceRateBiz6ThModel.resetValue();
                                    }
                                    else {
                                        this.accidentInsuranceRateBiz6ThModel.resetValue();
                                    }
                                    if (this.accidentInsuranceRateBiz7ThModel == null || this.accidentInsuranceRateBiz7ThModel == undefined) {
                                        this.accidentInsuranceRateBiz7ThModel = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                                        this.accidentInsuranceRateBiz7ThModel.resetValue();
                                    }
                                    else {
                                        this.accidentInsuranceRateBiz7ThModel.resetValue();
                                    }
                                    if (this.accidentInsuranceRateBiz8ThModel == null || this.accidentInsuranceRateBiz8ThModel == undefined) {
                                        this.accidentInsuranceRateBiz8ThModel = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                                        this.accidentInsuranceRateBiz8ThModel.resetValue();
                                    }
                                    else {
                                        this.accidentInsuranceRateBiz8ThModel.resetValue();
                                    }
                                    if (this.accidentInsuranceRateBiz9ThModel == null || this.accidentInsuranceRateBiz9ThModel == undefined) {
                                        this.accidentInsuranceRateBiz9ThModel = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                                        this.accidentInsuranceRateBiz9ThModel.resetValue();
                                    }
                                    else {
                                        this.accidentInsuranceRateBiz9ThModel.resetValue();
                                    }
                                    if (this.accidentInsuranceRateBiz10ThModel == null || this.accidentInsuranceRateBiz10ThModel == undefined) {
                                        this.accidentInsuranceRateBiz10ThModel = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                                        this.accidentInsuranceRateBiz10ThModel.resetValue();
                                    }
                                    else {
                                        this.accidentInsuranceRateBiz10ThModel.resetValue();
                                    }
                                    if (this.version == null || this.version == undefined) {
                                        this.version = ko.observable(0);
                                    }
                                    else {
                                        this.version(0);
                                    }
                                    if (this.historyAccidentInsuranceRateModel == null || this.historyAccidentInsuranceRateModel == undefined) {
                                        this.historyAccidentInsuranceRateModel = new HistoryAccidentInsuranceRateModel();
                                    }
                                    else {
                                        this.historyAccidentInsuranceRateModel.resetValue();
                                    }
                                };
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
