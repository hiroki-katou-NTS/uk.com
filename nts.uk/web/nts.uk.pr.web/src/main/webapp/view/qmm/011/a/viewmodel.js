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
                                    self.isEmptyUnemployee = ko.observable(true);
                                    self.isEmptyAccident = ko.observable(true);
                                    self.accidentInsuranceRateModel = ko.observable(new AccidentInsuranceRateModel(self.rateInputOptions, self.selectionRoundingMethod));
                                    self.selectionHistoryUnemployeeInsuranceRate = ko.observable('');
                                    self.selectionHistoryAccidentInsuranceRate = ko.observable('');
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
                                    nts.uk.ui.windows.sub.modal("/view/qmm/011/d/index.xhtml", { height: 600, width: 560, title: "労働保険料率の登録>履歴の追加" }).onClosed(function () {
                                        var addHistoryUnemployeeInsuranceDto = nts.uk.ui.windows.getShared("addHistoryUnemployeeInsuranceDto");
                                        if (addHistoryUnemployeeInsuranceDto != null && addHistoryUnemployeeInsuranceDto != undefined) {
                                            self.resetValueUnemployeeInsuranceRate();
                                            self.historyUnemployeeInsuranceRateStart(addHistoryUnemployeeInsuranceDto.startMonthRage);
                                            self.historyUnemployeeInsuranceRateEnd(addHistoryUnemployeeInsuranceDto.endMonthRage);
                                            self.selectionHistoryUnemployeeInsuranceRate('');
                                        }
                                    });
                                };
                                ScreenModel.prototype.openEditInsuranceBusinessType = function () {
                                    var self = this;
                                    a.service.findAllInsuranceBusinessType().done(function (data) {
                                        nts.uk.ui.windows.setShared("InsuranceBusinessTypeDto", data);
                                        nts.uk.ui.windows.sub.modal("/view/qmm/011/e/index.xhtml", { height: 700, width: 425, title: "事業種類の登録" }).onClosed(function () {
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
                                    if (selectionHistoryUnemployeeInsuranceRate != null && selectionHistoryUnemployeeInsuranceRate != undefined && selectionHistoryUnemployeeInsuranceRate != '') {
                                        var self = this;
                                        self.detailHistoryUnemployeeInsuranceRate(selectionHistoryUnemployeeInsuranceRate);
                                    }
                                };
                                ScreenModel.prototype.saveHistoryUnemployeeInsurance = function () {
                                    var self = this;
                                    if (self.typeActionUnemployeeInsurance() == TypeActionInsuranceRate.add) {
                                        var historyUnemployeeInsuranceDto = new HistoryUnemployeeInsuranceDto(self.selectionHistoryUnemployeeInsuranceRate(), self.historyUnemployeeInsuranceRateStart(), self.historyUnemployeeInsuranceRateEnd());
                                        a.service.addUnemployeeInsuranceRate(self.unemployeeInsuranceRateModel(), historyUnemployeeInsuranceDto).done(function (data) {
                                            self.reloadDataUnemployeeInsuranceRateByAction('');
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
                                        var historyAccidentInsuranceDto = new HistoryAccidentInsuranceDto(self.selectionHistoryAccidentInsuranceRate(), self.historyAccidentInsuranceRateStart(), self.historyAccidentInsuranceRateEnd());
                                        a.service.addAccidentInsuranceRate(self.accidentInsuranceRateModel(), historyAccidentInsuranceDto).done(function (data) {
                                            self.reloadDataAccidentInsuranceRateByAction('');
                                        });
                                    }
                                    else {
                                        var historyAccidentInsuranceDto = new HistoryAccidentInsuranceDto(self.selectionHistoryAccidentInsuranceRate(), self.historyAccidentInsuranceRateStart(), self.historyAccidentInsuranceRateEnd());
                                        a.service.updateAccidentInsuranceRate(self.accidentInsuranceRateModel(), historyAccidentInsuranceDto).done(function (data) {
                                            self.reloadDataAccidentInsuranceRateByAction(self.selectionHistoryAccidentInsuranceRate());
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
                                            if (code == null || code == undefined || code == '') {
                                                dataCode = data[0].historyId;
                                            }
                                            if (self.selectionHistoryUnemployeeInsuranceRate == null || self.selectionHistoryUnemployeeInsuranceRate == undefined || self.selectionHistoryUnemployeeInsuranceRate() === '') {
                                                self.selectionHistoryUnemployeeInsuranceRate = ko.observable(dataCode);
                                                self.selectionHistoryUnemployeeInsuranceRate.subscribe(function (selectionHistoryUnemployeeInsuranceRate) {
                                                    self.showchangeHistoryUnemployeeInsurance(selectionHistoryUnemployeeInsuranceRate);
                                                });
                                            }
                                            else {
                                                self.selectionHistoryUnemployeeInsuranceRate(dataCode);
                                            }
                                            self.detailHistoryUnemployeeInsuranceRate(dataCode).done(function (data) {
                                                console.log("MISS YOU");
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
                                    self.isEmptyUnemployee(true);
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
                                    if (historyId != null && history != undefined && historyId != '') {
                                        a.service.detailHistoryUnemployeeInsuranceRate(historyId).done(function (data) {
                                            self.unemployeeInsuranceRateModel = ko.observable(new UnemployeeInsuranceRateModel(data, self.rateInputOptions, self.selectionRoundingMethod));
                                            dfd.resolve(null);
                                            self.selectionHistoryUnemployeeInsuranceRate(historyId);
                                            self.typeActionUnemployeeInsurance(TypeActionInsuranceRate.update);
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
                                            self.historyAccidentInsuranceRateStart = ko.observable(data[0].startMonthRage);
                                            self.historyAccidentInsuranceRateEnd = ko.observable(data[0].endMonthRage);
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
                                ScreenModel.prototype.reloadDataAccidentInsuranceRateByAction = function (code) {
                                    var self = this;
                                    a.service.findAllHistoryAccidentInsuranceRate().done(function (data) {
                                        if (data != null && data.length > 0) {
                                            if (self.lstHistoryAccidentInsuranceRate == null || self.lstHistoryAccidentInsuranceRate == undefined) {
                                                self.lstHistoryAccidentInsuranceRate = ko.observableArray(data);
                                            }
                                            else {
                                                self.lstHistoryAccidentInsuranceRate([]);
                                                self.lstHistoryAccidentInsuranceRate(data);
                                            }
                                            var dataCode = code;
                                            if (code == null || code == undefined || code == '') {
                                                dataCode = data[0].historyId;
                                            }
                                            if (self.selectionHistoryAccidentInsuranceRate == null || self.selectionHistoryAccidentInsuranceRate == undefined) {
                                                self.selectionHistoryAccidentInsuranceRate = ko.observable(dataCode);
                                                self.selectionHistoryAccidentInsuranceRate.subscribe(function (selectionHistoryAccidentInsuranceRate) {
                                                    self.showchangeHistoryAccidentInsurance(selectionHistoryAccidentInsuranceRate);
                                                });
                                            }
                                            else {
                                                self.selectionHistoryAccidentInsuranceRate = ko.observable(dataCode);
                                            }
                                            self.detailHistoryAccidentInsuranceRate(dataCode).done(function (data) {
                                                self.selectionHistoryAccidentInsuranceRate(dataCode);
                                            });
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
                                    self.historyUnemployeeInsuranceRateStart('');
                                    self.historyAccidentInsuranceRateEnd('9999/12');
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
                                            self.selectionHistoryAccidentInsuranceRate(historyId);
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
