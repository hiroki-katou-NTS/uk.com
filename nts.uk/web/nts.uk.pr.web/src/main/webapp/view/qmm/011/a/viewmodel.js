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
                        var HistoryInsuranceInDto = a.service.model.HistoryInsuranceInDto;
                        var CareerGroupDto = a.service.model.CareerGroupDto;
                        var BusinessTypeEnumDto = a.service.model.BusinessTypeEnumDto;
                        var TypeHistory = a.service.model.TypeHistory;
                        var UnemployeeInsuranceRateCopyDto = a.service.model.UnemployeeInsuranceRateCopyDto;
                        var AccidentInsuranceHistoryDto = a.service.model.AccidentInsuranceHistoryDto;
                        var AccidentInsuranceRateCopyDto = a.service.model.AccidentInsuranceRateCopyDto;
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
                                    self.isEnable = ko.observable(true);
                                    self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                                    self.typeActionUnemployeeInsurance = ko.observable(TypeActionInsuranceRate.add);
                                    self.typeActionAccidentInsurance = ko.observable(TypeActionInsuranceRate.add);
                                    self.isEmptyUnemployee = ko.observable(true);
                                    self.isEmptyAccident = ko.observable(true);
                                    self.accidentInsuranceRateModel = ko.observable(new AccidentInsuranceRateModel(self.rateInputOptions, self.selectionRoundingMethod));
                                    self.unemployeeInsuranceRateModel = ko.observable(new UnemployeeInsuranceRateModel(self.rateInputOptions, self.selectionRoundingMethod));
                                    self.lstUnemployeeInsuranceRateHistory = ko.observableArray([]);
                                    self.lstAccidentInsuranceRateHistory = ko.observableArray([]);
                                    self.selectionUnemployeeInsuranceRateHistory = ko.observable('');
                                    self.selectionAccidentInsuranceRateHistory = ko.observable('');
                                    self.isEnableSaveUnemployeeInsurance = ko.observable(true);
                                    self.isEnableEditUnemployeeInsurance = ko.observable(true);
                                    self.isEnableSaveActionAccidentInsurance = ko.observable(true);
                                    self.isEnableEditActionAccidentInsurance = ko.observable(true);
                                    self.beginHistoryStartUnemployeeInsuranceRate = ko.observable('');
                                    self.beginHistoryStartAccidentInsuranceRate = ko.observable('');
                                }
                                ScreenModel.prototype.openEditUnemployeeInsuranceRateHistory = function () {
                                    var self = this;
                                    var historyId = self.selectionUnemployeeInsuranceRateHistory();
                                    a.service.findUnemployeeInsuranceRateHistory(historyId).done(function (data) {
                                        nts.uk.ui.windows.setShared("historyEnd", data.endMonth);
                                        nts.uk.ui.windows.setShared("historyStart", data.startMonth);
                                        nts.uk.ui.windows.setShared("historyId", data.historyId);
                                        nts.uk.ui.windows.setShared("type", TypeHistory.HistoryUnemployee);
                                        nts.uk.ui.windows.sub.modal('/view/qmm/011/f/index.xhtml', { title: '労働保険料率の登録>マスタ修正ログ', dialogClass: 'no-close' }).onClosed(function () {
                                            var updateHistoryInfoModel = nts.uk.ui.windows.getShared("updateHistoryInfoModel");
                                            if (updateHistoryInfoModel != null && updateHistoryInfoModel != undefined) {
                                                if (updateHistoryInfoModel.typeUpdate == 1) {
                                                    self.typeActionUnemployeeInsurance(TypeActionInsuranceRate.add);
                                                    self.reloadDataUnemployeeInsuranceRateByAction();
                                                }
                                                else {
                                                    self.typeActionUnemployeeInsurance(TypeActionInsuranceRate.update);
                                                    var UnemployeeInsuranceHistoryDto;
                                                    UnemployeeInsuranceHistoryDto = new HistoryInsuranceInDto();
                                                    UnemployeeInsuranceHistoryDto.historyId = updateHistoryInfoModel.historyId;
                                                    UnemployeeInsuranceHistoryDto.startMonth = updateHistoryInfoModel.historyStart;
                                                    UnemployeeInsuranceHistoryDto.endMonth = updateHistoryInfoModel.historyEnd;
                                                    self.unemployeeInsuranceRateModel().setHistoryData(UnemployeeInsuranceHistoryDto);
                                                }
                                            }
                                        });
                                    });
                                };
                                ScreenModel.prototype.openAddUnemployeeInsuranceRateHistory = function () {
                                    var self = this;
                                    nts.uk.ui.windows.setShared("isEmpty", self.isEmptyUnemployee());
                                    if (!self.isEmptyUnemployee()) {
                                        nts.uk.ui.windows.setShared("historyStart", self.beginHistoryStartUnemployeeInsuranceRate());
                                    }
                                    nts.uk.ui.windows.sub.modal('/view/qmm/011/d/index.xhtml', { title: '労働保険料率の登録>履歴の追加', dialogClass: 'no-close' }).onClosed(function () {
                                        var addHistoryInfoModel = nts.uk.ui.windows.getShared("addHistoryInfoModel");
                                        if (addHistoryInfoModel != null && addHistoryInfoModel != undefined) {
                                            var UnemployeeInsuranceHistoryDto;
                                            UnemployeeInsuranceHistoryDto = new HistoryInsuranceInDto();
                                            var unemployeeInsuranceRateCopyDto;
                                            unemployeeInsuranceRateCopyDto = new UnemployeeInsuranceRateCopyDto();
                                            unemployeeInsuranceRateCopyDto.historyIdCopy = self.selectionUnemployeeInsuranceRateHistory();
                                            unemployeeInsuranceRateCopyDto.startMonth = addHistoryInfoModel.starMonth;
                                            if (addHistoryInfoModel.typeModel == 2) {
                                                self.resetValueUnemployeeInsuranceRate();
                                                unemployeeInsuranceRateCopyDto.addNew = true;
                                                a.service.copyUnemployeeInsuranceRate(unemployeeInsuranceRateCopyDto).done(function (data) {
                                                    self.typeActionUnemployeeInsurance(TypeActionInsuranceRate.add);
                                                    self.reloadDataUnemployeeInsuranceRateByAction();
                                                    self.clearErrorSaveUnemployeeInsurance();
                                                }).fail(function (error) {
                                                    self.showMessageSaveUnemployeeInsurance(error.message);
                                                });
                                            }
                                            else {
                                                unemployeeInsuranceRateCopyDto.addNew = false;
                                                a.service.copyUnemployeeInsuranceRate(unemployeeInsuranceRateCopyDto).done(function (data) {
                                                    self.typeActionUnemployeeInsurance(TypeActionInsuranceRate.add);
                                                    self.reloadDataUnemployeeInsuranceRateByAction();
                                                }).fail(function (error) {
                                                    self.showMessageSaveUnemployeeInsurance(error.message);
                                                });
                                            }
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
                                ScreenModel.prototype.openEditAccidentInsuranceRateHistory = function () {
                                    var self = this;
                                    var historyId = self.selectionAccidentInsuranceRateHistory();
                                    a.service.findAccidentInsuranceRateHistory(historyId).done(function (data) {
                                        nts.uk.ui.windows.setShared("historyEnd", data.endMonth);
                                        nts.uk.ui.windows.setShared("historyStart", data.startMonth);
                                        nts.uk.ui.windows.setShared("historyId", data.historyId);
                                        nts.uk.ui.windows.setShared("type", TypeHistory.HistoryAccident);
                                        nts.uk.ui.windows.sub.modal('/view/qmm/011/f/index.xhtml', { title: '労働保険料率の登録>マスタ修正ログ', dialogClass: 'no-close' }).onClosed(function () {
                                            var updateHistoryInfoModel = nts.uk.ui.windows.getShared("updateHistoryInfoModel");
                                            if (updateHistoryInfoModel != null && updateHistoryInfoModel != undefined) {
                                                if (updateHistoryInfoModel.typeUpdate == 1) {
                                                    self.typeActionAccidentInsurance(TypeActionInsuranceRate.add);
                                                    self.reloadDataAccidentInsuranceRateByAction();
                                                }
                                                else {
                                                    self.typeActionAccidentInsurance(TypeActionInsuranceRate.update);
                                                    var accidentInsuranceHistoryDto;
                                                    accidentInsuranceHistoryDto = new AccidentInsuranceHistoryDto();
                                                    accidentInsuranceHistoryDto.historyId = updateHistoryInfoModel.historyId;
                                                    accidentInsuranceHistoryDto.startMonth = updateHistoryInfoModel.historyStart;
                                                    accidentInsuranceHistoryDto.endMonth = updateHistoryInfoModel.historyEnd;
                                                    self.accidentInsuranceRateModel().setHistoryData(accidentInsuranceHistoryDto);
                                                }
                                            }
                                        });
                                    });
                                };
                                ScreenModel.prototype.openAddAccidentInsuranceRateHistory = function () {
                                    var self = this;
                                    nts.uk.ui.windows.setShared("isEmpty", self.isEmptyAccident());
                                    if (!self.isEmptyAccident()) {
                                        nts.uk.ui.windows.setShared("historyStart", self.beginHistoryStartAccidentInsuranceRate());
                                    }
                                    nts.uk.ui.windows.sub.modal('/view/qmm/011/d/index.xhtml', { title: '労働保険料率の登録>履歴の追加', dialogClass: 'no-close' }).onClosed(function () {
                                        var addHistoryInfoModel = nts.uk.ui.windows.getShared("addHistoryInfoModel");
                                        if (addHistoryInfoModel != null && addHistoryInfoModel != undefined) {
                                            var accidentInsuranceRateCopyDto;
                                            accidentInsuranceRateCopyDto = new AccidentInsuranceRateCopyDto();
                                            accidentInsuranceRateCopyDto.historyIdCopy = self.selectionAccidentInsuranceRateHistory();
                                            accidentInsuranceRateCopyDto.startMonth = addHistoryInfoModel.starMonth;
                                            if (addHistoryInfoModel.typeModel == 2) {
                                                accidentInsuranceRateCopyDto.addNew = true;
                                            }
                                            else {
                                                accidentInsuranceRateCopyDto.addNew = false;
                                            }
                                            a.service.copyAccidentInsuranceRate(accidentInsuranceRateCopyDto).done(function (data) {
                                                self.typeActionAccidentInsurance(TypeActionInsuranceRate.add);
                                                self.reloadDataAccidentInsuranceRateByAction();
                                                self.clearErrorSaveAccidentInsurance();
                                            }).fail(function (error) {
                                                self.showMessageSaveAccidentInsurance(error.message);
                                            });
                                        }
                                    });
                                };
                                ScreenModel.prototype.showchangeUnemployeeInsuranceHistory = function (selectionUnemployeeInsuranceRateHistory) {
                                    if (selectionUnemployeeInsuranceRateHistory != null
                                        && selectionUnemployeeInsuranceRateHistory != undefined
                                        && selectionUnemployeeInsuranceRateHistory != '') {
                                        var self = this;
                                        self.detailUnemployeeInsuranceRateHistory(selectionUnemployeeInsuranceRateHistory);
                                    }
                                };
                                ScreenModel.prototype.clearErrorSaveUnemployeeInsurance = function () {
                                    var self = this;
                                    $('.save-error').ntsError('clear');
                                    $('#btn_saveUnemployeeInsuranceHistory').ntsError('clear');
                                };
                                ScreenModel.prototype.showMessageSaveUnemployeeInsurance = function (message) {
                                    $('#btn_saveUnemployeeInsuranceHistory').ntsError('set', message);
                                };
                                ScreenModel.prototype.clearErrorSaveAccidentInsurance = function () {
                                    var self = this;
                                    $('.save-error').ntsError('clear');
                                    $('#btn_saveAccidentInsuranceHistory').ntsError('clear');
                                };
                                ScreenModel.prototype.showMessageSaveAccidentInsurance = function (message) {
                                    $('#btn_saveAccidentInsuranceHistory').ntsError('set', message);
                                };
                                ScreenModel.prototype.saveUnemployeeInsuranceHistory = function () {
                                    var self = this;
                                    if (self.typeActionUnemployeeInsurance() == TypeActionInsuranceRate.add) {
                                        a.service.addUnemployeeInsuranceRate(self.unemployeeInsuranceRateModel()).done(function (data) {
                                            self.reloadDataUnemployeeInsuranceRateByAction();
                                            self.clearErrorSaveUnemployeeInsurance();
                                        }).fail(function (res) {
                                            self.showMessageSaveUnemployeeInsurance(res.message);
                                            self.reloadDataUnemployeeInsuranceRateByAction();
                                        });
                                    }
                                    else {
                                        a.service.updateUnemployeeInsuranceRate(self.unemployeeInsuranceRateModel()).done(function (data) {
                                            self.reloadDataUnemployeeInsuranceRateByAction();
                                            self.clearErrorSaveUnemployeeInsurance();
                                        }).fail(function (res) {
                                            self.showMessageSaveUnemployeeInsurance(res.message);
                                            self.reloadDataUnemployeeInsuranceRateByAction();
                                        });
                                    }
                                    return true;
                                };
                                ScreenModel.prototype.saveAccidentInsuranceHistory = function () {
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
                                ScreenModel.prototype.showchangeAccidentInsuranceHistory = function (selectionAccidentInsuranceRateHistory) {
                                    if (selectionAccidentInsuranceRateHistory != null
                                        && selectionAccidentInsuranceRateHistory != undefined
                                        && selectionAccidentInsuranceRateHistory != '') {
                                        var self = this;
                                        self.detailAccidentInsuranceRateHistory(selectionAccidentInsuranceRateHistory);
                                    }
                                };
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.findAllUnemployeeInsuranceRateHistory().done(function (data) {
                                        self.findAllAccidentInsuranceRateHistory().done(function (data) {
                                            dfd.resolve(self);
                                        });
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.findAllUnemployeeInsuranceRateHistory = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    a.service.findAllUnemployeeInsuranceRateHistory().done(function (data) {
                                        if (data != null && data.length > 0) {
                                            self.lstUnemployeeInsuranceRateHistory(data);
                                            self.selectionUnemployeeInsuranceRateHistory(data[0].historyId);
                                            if (self.isEmptyUnemployee()) {
                                                self.selectionUnemployeeInsuranceRateHistory.subscribe(function (selectionUnemployeeInsuranceRateHistory) {
                                                    self.showchangeUnemployeeInsuranceHistory(selectionUnemployeeInsuranceRateHistory);
                                                });
                                            }
                                            self.isEmptyUnemployee(false);
                                            self.detailUnemployeeInsuranceRateHistory(data[0].historyId).done(function (data) {
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
                                    a.service.findAllUnemployeeInsuranceRateHistory().done(function (data) {
                                        if (data != null && data.length > 0) {
                                            self.selectionUnemployeeInsuranceRateHistory('');
                                            self.lstUnemployeeInsuranceRateHistory([]);
                                            var historyId = self.unemployeeInsuranceRateModel().unemployeeInsuranceHistoryModel.historyId();
                                            if (self.typeActionUnemployeeInsurance() == TypeActionInsuranceRate.add) {
                                                historyId = data[0].historyId;
                                            }
                                            if (self.isEmptyUnemployee()) {
                                                self.selectionUnemployeeInsuranceRateHistory.subscribe(function (selectionUnemployeeInsuranceRateHistory) {
                                                    self.showchangeUnemployeeInsuranceHistory(self.selectionUnemployeeInsuranceRateHistory());
                                                });
                                                self.isEmptyUnemployee(false);
                                            }
                                            self.selectionUnemployeeInsuranceRateHistory(historyId);
                                            self.lstUnemployeeInsuranceRateHistory(data);
                                            self.detailUnemployeeInsuranceRateHistory(historyId).done(function (data) {
                                                self.unemployeeInsuranceRateModel().setEnable(true);
                                            });
                                            self.isEnableSaveUnemployeeInsurance(true);
                                            self.isEnableEditUnemployeeInsurance(true);
                                        }
                                        else {
                                            self.newmodelEmptyDataUnemployeeInsuranceRate();
                                        }
                                    });
                                };
                                ScreenModel.prototype.newmodelEmptyDataUnemployeeInsuranceRate = function () {
                                    var self = this;
                                    self.selectionUnemployeeInsuranceRateHistory('');
                                    self.lstUnemployeeInsuranceRateHistory([]);
                                    self.resetValueUnemployeeInsuranceRate();
                                    self.isEmptyUnemployee(true);
                                    self.isEnableSaveUnemployeeInsurance(false);
                                    self.isEnableEditUnemployeeInsurance(false);
                                };
                                ScreenModel.prototype.resetValueUnemployeeInsuranceRate = function () {
                                    var self = this;
                                    self.unemployeeInsuranceRateModel().resetValue(self.rateInputOptions, self.selectionRoundingMethod);
                                    self.unemployeeInsuranceRateModel().setEnable(false);
                                    self.typeActionUnemployeeInsurance(TypeActionInsuranceRate.add);
                                    self.selectionUnemployeeInsuranceRateHistory('');
                                };
                                ScreenModel.prototype.detailUnemployeeInsuranceRateHistory = function (historyId) {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    if (historyId != null && historyId != undefined && historyId != '') {
                                        a.service.detailUnemployeeInsuranceRateHistory(historyId).done(function (data) {
                                            self.unemployeeInsuranceRateModel().setListItem(data.rateItems);
                                            self.unemployeeInsuranceRateModel().setHistoryData(data.historyInsurance);
                                            self.typeActionUnemployeeInsurance(TypeActionInsuranceRate.update);
                                            self.beginHistoryStartUnemployeeInsuranceRate(nts.uk.time.formatYearMonth(data.historyInsurance.startMonth));
                                            dfd.resolve(null);
                                        });
                                    }
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.findAllAccidentInsuranceRateHistory = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    a.service.findAllAccidentInsuranceRateHistory().done(function (data) {
                                        if (data != null && data.length > 0) {
                                            self.lstAccidentInsuranceRateHistory = ko.observableArray(data);
                                            self.selectionAccidentInsuranceRateHistory(data[0].historyId);
                                            self.selectionAccidentInsuranceRateHistory.subscribe(function (selectionAccidentInsuranceRateHistory) {
                                                self.showchangeAccidentInsuranceHistory(selectionAccidentInsuranceRateHistory);
                                            });
                                            self.isEmptyAccident(false);
                                            self.detailAccidentInsuranceRateHistory(data[0].historyId).done(function (data) {
                                                a.service.findAllInsuranceBusinessType().done(function (data) {
                                                    self.updateInsuranceBusinessTypeAccidentInsuranceDto(data);
                                                    dfd.resolve(self);
                                                });
                                            });
                                            self.isEnableSaveActionAccidentInsurance(true);
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
                                    a.service.findAllAccidentInsuranceRateHistory().done(function (data) {
                                        if (data != null && data.length > 0) {
                                            self.selectionAccidentInsuranceRateHistory('');
                                            self.lstAccidentInsuranceRateHistory([]);
                                            var historyId = self.accidentInsuranceRateModel().accidentInsuranceRateHistoryModel.historyId();
                                            if (self.typeActionAccidentInsurance() == TypeActionInsuranceRate.add) {
                                                historyId = data[0].historyId;
                                            }
                                            self.selectionAccidentInsuranceRateHistory(historyId);
                                            self.lstAccidentInsuranceRateHistory(data);
                                            self.detailAccidentInsuranceRateHistory(historyId).done(function (data) {
                                                a.service.findAllInsuranceBusinessType().done(function (data) {
                                                    self.updateInsuranceBusinessTypeAccidentInsuranceDto(data);
                                                });
                                            });
                                            if (self.isEmptyAccident()) {
                                                self.selectionAccidentInsuranceRateHistory.subscribe(function (selectionAccidentInsuranceRateHistory) {
                                                    self.showchangeAccidentInsuranceHistory(selectionAccidentInsuranceRateHistory);
                                                });
                                                self.isEmptyAccident(false);
                                            }
                                            self.isEnableSaveActionAccidentInsurance(true);
                                            self.isEnableEditActionAccidentInsurance(true);
                                        }
                                        else {
                                            self.newmodelEmptyDataAccidentInsuranceRate();
                                        }
                                    });
                                };
                                ScreenModel.prototype.newmodelEmptyDataAccidentInsuranceRate = function () {
                                    var self = this;
                                    self.selectionAccidentInsuranceRateHistory('');
                                    self.lstAccidentInsuranceRateHistory([]);
                                    self.resetValueAccidentInsuranceRate();
                                    a.service.findAllInsuranceBusinessType().done(function (data) {
                                        self.updateInsuranceBusinessTypeAccidentInsuranceDto(data);
                                    });
                                    self.isEmptyAccident(true);
                                    self.isEnableSaveActionAccidentInsurance(false);
                                    self.isEnableEditActionAccidentInsurance(false);
                                    self.accidentInsuranceRateModel().setEnable(false);
                                };
                                ScreenModel.prototype.resetValueAccidentInsuranceRate = function () {
                                    var self = this;
                                    self.selectionAccidentInsuranceRateHistory('');
                                    self.accidentInsuranceRateModel().resetValue(self.rateInputOptions, self.selectionRoundingMethod);
                                    self.typeActionAccidentInsurance(TypeActionInsuranceRate.add);
                                };
                                ScreenModel.prototype.detailAccidentInsuranceRateHistory = function (historyId) {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    if (historyId != null && historyId != undefined && historyId != '') {
                                        a.service.findAccidentInsuranceRate(historyId).done(function (data) {
                                            self.accidentInsuranceRateModel().setListItem(data.rateItems);
                                            self.accidentInsuranceRateModel().setVersion(data.version);
                                            self.typeActionAccidentInsurance(TypeActionInsuranceRate.update);
                                            self.accidentInsuranceRateModel().setHistoryData(data.historyInsurance);
                                            self.accidentInsuranceRateModel().setEnable(true);
                                            self.beginHistoryStartAccidentInsuranceRate(nts.uk.time.formatYearMonth(data.historyInsurance.startMonth));
                                            dfd.resolve(null);
                                        });
                                    }
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.updateInsuranceBusinessTypeAccidentInsurance = function (insuranceBusinessTypeUpdateModel) {
                                    var self = this;
                                    self.accidentInsuranceRateModel()
                                        .accidentInsuranceRateBiz1StModel
                                        .updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz1St());
                                    self.accidentInsuranceRateModel()
                                        .accidentInsuranceRateBiz2NdModel
                                        .updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz2Nd());
                                    self.accidentInsuranceRateModel()
                                        .accidentInsuranceRateBiz3RdModel
                                        .updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz3Rd());
                                    self.accidentInsuranceRateModel()
                                        .accidentInsuranceRateBiz4ThModel
                                        .updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz4Th());
                                    self.accidentInsuranceRateModel()
                                        .accidentInsuranceRateBiz5ThModel
                                        .updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz5Th());
                                    self.accidentInsuranceRateModel()
                                        .accidentInsuranceRateBiz6ThModel
                                        .updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz6Th());
                                    self.accidentInsuranceRateModel()
                                        .accidentInsuranceRateBiz7ThModel
                                        .updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz7Th());
                                    self.accidentInsuranceRateModel()
                                        .accidentInsuranceRateBiz8ThModel
                                        .updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz8Th());
                                    self.accidentInsuranceRateModel()
                                        .accidentInsuranceRateBiz9ThModel
                                        .updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz9Th());
                                    self.accidentInsuranceRateModel()
                                        .accidentInsuranceRateBiz10ThModel
                                        .updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz10Th());
                                };
                                ScreenModel.prototype.updateInsuranceBusinessTypeAccidentInsuranceDto = function (InsuranceBusinessTypeDto) {
                                    var self = this;
                                    self.accidentInsuranceRateModel()
                                        .accidentInsuranceRateBiz1StModel
                                        .updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz1St);
                                    self.accidentInsuranceRateModel()
                                        .accidentInsuranceRateBiz2NdModel
                                        .updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz2Nd);
                                    self.accidentInsuranceRateModel()
                                        .accidentInsuranceRateBiz3RdModel
                                        .updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz3Rd);
                                    self.accidentInsuranceRateModel()
                                        .accidentInsuranceRateBiz4ThModel
                                        .updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz4Th);
                                    self.accidentInsuranceRateModel()
                                        .accidentInsuranceRateBiz5ThModel
                                        .updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz5Th);
                                    self.accidentInsuranceRateModel()
                                        .accidentInsuranceRateBiz6ThModel
                                        .updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz6Th);
                                    self.accidentInsuranceRateModel()
                                        .accidentInsuranceRateBiz7ThModel
                                        .updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz7Th);
                                    self.accidentInsuranceRateModel()
                                        .accidentInsuranceRateBiz8ThModel
                                        .updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz8Th);
                                    self.accidentInsuranceRateModel()
                                        .accidentInsuranceRateBiz9ThModel
                                        .updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz9Th);
                                    self.accidentInsuranceRateModel()
                                        .accidentInsuranceRateBiz10ThModel
                                        .updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz10Th);
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var UnemployeeInsuranceRateItemSettingModel = (function () {
                                function UnemployeeInsuranceRateItemSettingModel() {
                                    this.roundAtr = ko.observable(0);
                                    this.rate = ko.observable(0);
                                    this.isEnable = ko.observable(true);
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
                                    if (this.isEnable == null || this.isEnable == undefined) {
                                        this.isEnable = ko.observable(false);
                                    }
                                    else {
                                        this.isEnable(false);
                                    }
                                };
                                UnemployeeInsuranceRateItemSettingModel.prototype.setItem = function (unemployeeInsuranceRateItemSetting) {
                                    this.roundAtr(unemployeeInsuranceRateItemSetting.roundAtr);
                                    this.rate(unemployeeInsuranceRateItemSetting.rate);
                                };
                                UnemployeeInsuranceRateItemSettingModel.prototype.setEnable = function (isEnable) {
                                    this.isEnable(isEnable);
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
                                UnemployeeInsuranceRateItemModel.prototype.setEnable = function (isEnable) {
                                    this.companySetting.setEnable(isEnable);
                                    this.personalSetting.setEnable(isEnable);
                                };
                                return UnemployeeInsuranceRateItemModel;
                            }());
                            viewmodel.UnemployeeInsuranceRateItemModel = UnemployeeInsuranceRateItemModel;
                            var UnemployeeInsuranceHistoryModel = (function () {
                                function UnemployeeInsuranceHistoryModel() {
                                    this.historyId = ko.observable('');
                                    this.startJapStartMonth = ko.observable('');
                                    this.endMonthRage = ko.observable('');
                                    this.endMonth = ko.observable(0);
                                    this.startMonth = ko.observable(0);
                                }
                                UnemployeeInsuranceHistoryModel.prototype.resetValue = function () {
                                    if (this.historyId != null && this.historyId != undefined) {
                                        this.historyId('');
                                    }
                                    else {
                                        this.historyId = ko.observable('');
                                    }
                                    if (this.startJapStartMonth != null && this.startJapStartMonth != undefined) {
                                        this.startJapStartMonth('');
                                    }
                                    else {
                                        this.startJapStartMonth = ko.observable('');
                                    }
                                    if (this.endMonthRage != null && this.endMonthRage != undefined) {
                                        this.endMonthRage('9999/12');
                                    }
                                    else {
                                        this.endMonthRage = ko.observable('9999/12');
                                    }
                                    if (this.endMonth != null && this.endMonth != undefined) {
                                        this.endMonth(999912);
                                    }
                                    else {
                                        this.endMonth = ko.observable(999912);
                                    }
                                    if (this.startMonth != null && this.startMonth != undefined) {
                                        this.endMonth(0);
                                    }
                                    else {
                                        this.startMonth = ko.observable(0);
                                    }
                                };
                                UnemployeeInsuranceHistoryModel.prototype.updateData = function (unemployeeInsuranceHistory) {
                                    this.historyId(unemployeeInsuranceHistory.historyId);
                                    this.startJapStartMonth(nts.uk.time.formatYearMonth(unemployeeInsuranceHistory.startMonth)
                                        + ' (' + nts.uk.time.yearmonthInJapanEmpire(unemployeeInsuranceHistory.startMonth).toString() + ') ');
                                    this.endMonthRage(nts.uk.time.formatYearMonth(unemployeeInsuranceHistory.endMonth));
                                    this.startMonth(unemployeeInsuranceHistory.startMonth);
                                    this.endMonth(unemployeeInsuranceHistory.endMonth);
                                };
                                UnemployeeInsuranceHistoryModel.prototype.setMonthRage = function (startDate, endDate) {
                                    this.startMonth(startDate);
                                    this.endMonth(endDate);
                                };
                                return UnemployeeInsuranceHistoryModel;
                            }());
                            viewmodel.UnemployeeInsuranceHistoryModel = UnemployeeInsuranceHistoryModel;
                            var UnemployeeInsuranceRateModel = (function () {
                                function UnemployeeInsuranceRateModel(rateInputOptions, selectionRoundingMethod) {
                                    this.unemployeeInsuranceRateItemAgroforestryModel
                                        = new UnemployeeInsuranceRateItemModel(rateInputOptions, selectionRoundingMethod);
                                    this.unemployeeInsuranceRateItemContructionModel
                                        = new UnemployeeInsuranceRateItemModel(rateInputOptions, selectionRoundingMethod);
                                    this.unemployeeInsuranceRateItemOtherModel
                                        = new UnemployeeInsuranceRateItemModel(rateInputOptions, selectionRoundingMethod);
                                    this.version = ko.observable(0);
                                    this.isShowTable = ko.observable(false);
                                    this.unemployeeInsuranceHistoryModel = new UnemployeeInsuranceHistoryModel();
                                }
                                UnemployeeInsuranceRateModel.prototype.resetValue = function (rateInputOptions, selectionRoundingMethod) {
                                    if (this.unemployeeInsuranceRateItemAgroforestryModel == null
                                        || this.unemployeeInsuranceRateItemAgroforestryModel == undefined) {
                                        this.unemployeeInsuranceRateItemAgroforestryModel
                                            = new UnemployeeInsuranceRateItemModel(rateInputOptions, selectionRoundingMethod);
                                        this.unemployeeInsuranceRateItemAgroforestryModel.resetValue();
                                    }
                                    else {
                                        this.unemployeeInsuranceRateItemAgroforestryModel.resetValue();
                                    }
                                    if (this.unemployeeInsuranceRateItemContructionModel == null
                                        || this.unemployeeInsuranceRateItemContructionModel == undefined) {
                                        this.unemployeeInsuranceRateItemContructionModel
                                            = new UnemployeeInsuranceRateItemModel(rateInputOptions, selectionRoundingMethod);
                                        this.unemployeeInsuranceRateItemContructionModel.resetValue();
                                    }
                                    else {
                                        this.unemployeeInsuranceRateItemContructionModel.resetValue();
                                    }
                                    if (this.unemployeeInsuranceRateItemOtherModel == null
                                        || this.unemployeeInsuranceRateItemOtherModel == undefined) {
                                        this.unemployeeInsuranceRateItemOtherModel
                                            = new UnemployeeInsuranceRateItemModel(rateInputOptions, selectionRoundingMethod);
                                        this.unemployeeInsuranceRateItemOtherModel.resetValue();
                                    }
                                    else {
                                        this.unemployeeInsuranceRateItemOtherModel.resetValue();
                                    }
                                    if (this.unemployeeInsuranceHistoryModel == null
                                        || this.unemployeeInsuranceHistoryModel == undefined) {
                                        this.unemployeeInsuranceHistoryModel = new UnemployeeInsuranceHistoryModel();
                                    }
                                    else {
                                        this.unemployeeInsuranceHistoryModel.resetValue();
                                    }
                                };
                                UnemployeeInsuranceRateModel.prototype.setHistoryData = function (UnemployeeInsuranceHistoryDto) {
                                    this.unemployeeInsuranceHistoryModel.updateData(UnemployeeInsuranceHistoryDto);
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
                                UnemployeeInsuranceRateModel.prototype.setEnable = function (isEnable) {
                                    this.unemployeeInsuranceRateItemAgroforestryModel.setEnable(isEnable);
                                    this.unemployeeInsuranceRateItemContructionModel.setEnable(isEnable);
                                    this.unemployeeInsuranceRateItemOtherModel.setEnable(isEnable);
                                    this.isShowTable(!isEnable);
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
                                    this.isEnable = ko.observable(true);
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
                                    if (this.isEnable == null || this.isEnable == undefined) {
                                        this.isEnable = ko.observable(true);
                                    }
                                    else {
                                        this.isEnable(true);
                                    }
                                };
                                AccidentInsuranceRateDetailModel.prototype.setEnable = function (isEnable) {
                                    this.isEnable(isEnable);
                                };
                                return AccidentInsuranceRateDetailModel;
                            }());
                            viewmodel.AccidentInsuranceRateDetailModel = AccidentInsuranceRateDetailModel;
                            var AccidentInsuranceRateHistoryModel = (function () {
                                function AccidentInsuranceRateHistoryModel() {
                                    this.historyId = ko.observable('');
                                    this.startMonthRage = ko.observable('');
                                    this.endMonthRage = ko.observable('');
                                    this.endMonth = ko.observable(0);
                                    this.startMonth = ko.observable(0);
                                }
                                AccidentInsuranceRateHistoryModel.prototype.resetValue = function () {
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
                                    if (this.startMonth != null && this.startMonth != undefined) {
                                        this.startMonth = ko.observable(0);
                                    }
                                    else {
                                        this.startMonth(0);
                                    }
                                    if (this.endMonth != null && this.endMonth != undefined) {
                                        this.endMonth = ko.observable(0);
                                    }
                                    else {
                                        this.endMonth(0);
                                    }
                                };
                                AccidentInsuranceRateHistoryModel.prototype.updateData = function (historyDto) {
                                    this.resetValue();
                                    this.historyId(historyDto.historyId);
                                    this.startMonth(historyDto.startMonth);
                                    this.endMonth(historyDto.endMonth);
                                    this.startMonthRage(nts.uk.time.formatYearMonth(historyDto.startMonth)
                                        + ' (' + nts.uk.time.yearmonthInJapanEmpire(historyDto.startMonth).toString() + ') ');
                                    this.endMonthRage(nts.uk.time.formatYearMonth(historyDto.endMonth));
                                };
                                return AccidentInsuranceRateHistoryModel;
                            }());
                            viewmodel.AccidentInsuranceRateHistoryModel = AccidentInsuranceRateHistoryModel;
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
                                    this.accidentInsuranceRateHistoryModel = new AccidentInsuranceRateHistoryModel();
                                    this.isShowTable = ko.observable(true);
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
                                AccidentInsuranceRateModel.prototype.setHistoryData = function (historyDto) {
                                    this.accidentInsuranceRateHistoryModel.updateData(historyDto);
                                };
                                AccidentInsuranceRateModel.prototype.resetValue = function (rateInputOptions, selectionRoundingMethod) {
                                    if (this.accidentInsuranceRateBiz1StModel == null
                                        || this.accidentInsuranceRateBiz1StModel == undefined) {
                                        this.accidentInsuranceRateBiz1StModel
                                            = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                                        this.accidentInsuranceRateBiz1StModel.resetValue();
                                    }
                                    else {
                                        this.accidentInsuranceRateBiz1StModel.resetValue();
                                    }
                                    if (this.accidentInsuranceRateBiz2NdModel == null
                                        || this.accidentInsuranceRateBiz2NdModel == undefined) {
                                        this.accidentInsuranceRateBiz2NdModel
                                            = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                                        this.accidentInsuranceRateBiz2NdModel.resetValue();
                                    }
                                    else {
                                        this.accidentInsuranceRateBiz2NdModel.resetValue();
                                    }
                                    if (this.accidentInsuranceRateBiz3RdModel == null
                                        || this.accidentInsuranceRateBiz3RdModel == undefined) {
                                        this.accidentInsuranceRateBiz3RdModel
                                            = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                                        this.accidentInsuranceRateBiz3RdModel.resetValue();
                                    }
                                    else {
                                        this.accidentInsuranceRateBiz3RdModel.resetValue();
                                    }
                                    if (this.accidentInsuranceRateBiz4ThModel == null
                                        || this.accidentInsuranceRateBiz4ThModel == undefined) {
                                        this.accidentInsuranceRateBiz4ThModel
                                            = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                                        this.accidentInsuranceRateBiz4ThModel.resetValue();
                                    }
                                    else {
                                        this.accidentInsuranceRateBiz4ThModel.resetValue();
                                    }
                                    if (this.accidentInsuranceRateBiz5ThModel == null
                                        || this.accidentInsuranceRateBiz5ThModel == undefined) {
                                        this.accidentInsuranceRateBiz5ThModel
                                            = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                                        this.accidentInsuranceRateBiz5ThModel.resetValue();
                                    }
                                    else {
                                        this.accidentInsuranceRateBiz5ThModel.resetValue();
                                    }
                                    if (this.accidentInsuranceRateBiz6ThModel == null
                                        || this.accidentInsuranceRateBiz6ThModel == undefined) {
                                        this.accidentInsuranceRateBiz6ThModel
                                            = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                                        this.accidentInsuranceRateBiz6ThModel.resetValue();
                                    }
                                    else {
                                        this.accidentInsuranceRateBiz6ThModel.resetValue();
                                    }
                                    if (this.accidentInsuranceRateBiz7ThModel == null
                                        || this.accidentInsuranceRateBiz7ThModel == undefined) {
                                        this.accidentInsuranceRateBiz7ThModel
                                            = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                                        this.accidentInsuranceRateBiz7ThModel.resetValue();
                                    }
                                    else {
                                        this.accidentInsuranceRateBiz7ThModel.resetValue();
                                    }
                                    if (this.accidentInsuranceRateBiz8ThModel == null
                                        || this.accidentInsuranceRateBiz8ThModel == undefined) {
                                        this.accidentInsuranceRateBiz8ThModel
                                            = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                                        this.accidentInsuranceRateBiz8ThModel.resetValue();
                                    }
                                    else {
                                        this.accidentInsuranceRateBiz8ThModel.resetValue();
                                    }
                                    if (this.accidentInsuranceRateBiz9ThModel == null
                                        || this.accidentInsuranceRateBiz9ThModel == undefined) {
                                        this.accidentInsuranceRateBiz9ThModel
                                            = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                                        this.accidentInsuranceRateBiz9ThModel.resetValue();
                                    }
                                    else {
                                        this.accidentInsuranceRateBiz9ThModel.resetValue();
                                    }
                                    if (this.accidentInsuranceRateBiz10ThModel == null
                                        || this.accidentInsuranceRateBiz10ThModel == undefined) {
                                        this.accidentInsuranceRateBiz10ThModel
                                            = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
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
                                    if (this.accidentInsuranceRateHistoryModel == null
                                        || this.accidentInsuranceRateHistoryModel == undefined) {
                                        this.accidentInsuranceRateHistoryModel
                                            = new AccidentInsuranceRateHistoryModel();
                                    }
                                    else {
                                        this.accidentInsuranceRateHistoryModel.resetValue();
                                    }
                                };
                                AccidentInsuranceRateModel.prototype.setEnable = function (isEnable) {
                                    this.accidentInsuranceRateBiz1StModel.setEnable(isEnable);
                                    this.accidentInsuranceRateBiz2NdModel.setEnable(isEnable);
                                    this.accidentInsuranceRateBiz3RdModel.setEnable(isEnable);
                                    this.accidentInsuranceRateBiz4ThModel.setEnable(isEnable);
                                    this.accidentInsuranceRateBiz5ThModel.setEnable(isEnable);
                                    this.accidentInsuranceRateBiz6ThModel.setEnable(isEnable);
                                    this.accidentInsuranceRateBiz7ThModel.setEnable(isEnable);
                                    this.accidentInsuranceRateBiz8ThModel.setEnable(isEnable);
                                    this.accidentInsuranceRateBiz9ThModel.setEnable(isEnable);
                                    this.accidentInsuranceRateBiz10ThModel.setEnable(isEnable);
                                    this.isShowTable(!isEnable);
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
