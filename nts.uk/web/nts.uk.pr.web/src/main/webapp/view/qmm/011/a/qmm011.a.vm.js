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
                        var UnemployeeInsuranceHistoryUpdateDto = a.service.model.UnemployeeInsuranceHistoryUpdateDto;
                        var AccidentInsuranceHistoryUpdateDto = a.service.model.AccidentInsuranceHistoryUpdateDto;
                        var UnemployeeInsuranceDeleteDto = a.service.model.UnemployeeInsuranceDeleteDto;
                        var AccidentInsuranceRateDeleteDto = a.service.model.AccidentInsuranceRateDeleteDto;
                        var CareerGroupDto = a.service.model.CareerGroupDto;
                        var BusinessTypeEnumDto = a.service.model.BusinessTypeEnumDto;
                        var UnemployeeInsuranceRateCopyDto = a.service.model.UnemployeeInsuranceRateCopyDto;
                        var AccidentInsuranceRateCopyDto = a.service.model.AccidentInsuranceRateCopyDto;
                        var TypeActionInsuranceRate = a.service.model.TypeActionInsuranceRate;
                        var ScreenMode = nts.uk.pr.view.base.simplehistory.dialogbase.ScreenMode;
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
                                    self.accidentInsuranceRateModel = ko.observable(new AccIRModel(self.rateInputOptions, self.selectionRoundingMethod));
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
                                    self.messageList = ko.observableArray([
                                        { messageId: "ER001", message: "＊が入力されていません。" },
                                        { messageId: "ER005", message: "入力した＊は既に存在しています。\r\n ＊を確認してください。" },
                                        { messageId: "AL001", message: "変更された内容が登録されていません。\r\n よろしいですか。" },
                                        { messageId: "ER010", message: "対象データがありません。" },
                                        { messageId: "AL002", message: "データを削除します。\r\n よろしいですか？。" }
                                    ]);
                                    self.dirtyUnemployeeInsurance = new nts.uk.ui.DirtyChecker(self.unemployeeInsuranceRateModel);
                                    self.dirtyAccidentInsurance = new nts.uk.ui.DirtyChecker(self.accidentInsuranceRateModel);
                                    self.isShowDirtyUnemployeeInsurance = ko.observable(true);
                                    self.isShowDirtyActionAccidentInsurance = ko.observable(true);
                                    self.preSelectUnemployeeInsuranceRateHistory = ko.observable('');
                                    self.preSelectAccidentInsuranceRateHistory = ko.observable('');
                                }
                                ScreenModel.prototype.openEditUnemployeeInsuranceRateHistory = function () {
                                    var self = this;
                                    if (self.dirtyUnemployeeInsurance.isDirty()) {
                                        nts.uk.ui.dialog.confirm(self.messageList()[2].message).ifYes(function () {
                                            self.onShowEditUnemployeeInsuranceRateHistory();
                                        }).ifNo(function () {
                                        });
                                        return;
                                    }
                                    self.onShowEditUnemployeeInsuranceRateHistory();
                                };
                                ScreenModel.prototype.onShowEditUnemployeeInsuranceRateHistory = function () {
                                    var self = this;
                                    a.service.findUnemployeeInsuranceRateHistory(self.selectionUnemployeeInsuranceRateHistory()).done(function (data) {
                                        var history;
                                        var endMonth = data.endMonth;
                                        var name = '雇用保険料率';
                                        history = {
                                            uuid: self.selectionUnemployeeInsuranceRateHistory(),
                                            start: data.startMonth,
                                            end: endMonth
                                        };
                                        var newHistoryOptions = {
                                            screenMode: ScreenMode.MODE_HISTORY_ONLY,
                                            name: name,
                                            master: null,
                                            history: history,
                                            removeMasterOnLastHistoryRemove: false,
                                            onDeleteCallBack: function (data) {
                                                var unemployeeInsuranceDeleteDto;
                                                unemployeeInsuranceDeleteDto = new UnemployeeInsuranceDeleteDto();
                                                unemployeeInsuranceDeleteDto.code = data.historyId;
                                                unemployeeInsuranceDeleteDto.version = 0;
                                                a.service.deleteUnemployeeInsurance(unemployeeInsuranceDeleteDto).done(function (data) {
                                                    self.typeActionUnemployeeInsurance(TypeActionInsuranceRate.add);
                                                    self.reloadDataUnemployeeInsuranceRateByAction();
                                                }).fail(function (error) {
                                                    self.showMessageSaveUnemployeeInsurance(error.message);
                                                });
                                            },
                                            onUpdateCallBack: function (data) {
                                                var dfd = $.Deferred();
                                                var unemployeeInsuranceHistoryUpdateDto;
                                                unemployeeInsuranceHistoryUpdateDto = new UnemployeeInsuranceHistoryUpdateDto();
                                                unemployeeInsuranceHistoryUpdateDto.historyId = data.historyId;
                                                unemployeeInsuranceHistoryUpdateDto.startMonth = data.startYearMonth;
                                                unemployeeInsuranceHistoryUpdateDto.endMonth = endMonth;
                                                a.service.updateUnemployeeInsuranceRateHistory(unemployeeInsuranceHistoryUpdateDto).done(function () {
                                                    self.typeActionUnemployeeInsurance(TypeActionInsuranceRate.update);
                                                    self.reloadDataUnemployeeInsuranceRateByAction();
                                                    dfd.resolve();
                                                }).fail(function (error) {
                                                    dfd.reject(error);
                                                });
                                                return dfd.promise();
                                            }
                                        };
                                        nts.uk.ui.windows.setShared('options', newHistoryOptions);
                                        var ntsDialogOptions = {
                                            title: nts.uk.text.format('{0}の登録 > 履歴の編集', name),
                                            dialogClass: 'no-close'
                                        };
                                        nts.uk.ui.windows.sub.modal('/view/base/simplehistory/updatehistory/index.xhtml', ntsDialogOptions);
                                    });
                                };
                                ScreenModel.prototype.openAddUnemployeeInsuranceRateHistory = function () {
                                    var self = this;
                                    if (self.dirtyUnemployeeInsurance.isDirty() && !self.isEmptyUnemployee()) {
                                        nts.uk.ui.dialog.confirm(self.messageList()[2].message).ifYes(function () {
                                            self.onShowAddUnemployeeInsuranceRateHistory();
                                        }).ifNo(function () {
                                        });
                                        return;
                                    }
                                    self.onShowAddUnemployeeInsuranceRateHistory();
                                };
                                ScreenModel.prototype.onShowAddUnemployeeInsuranceRateHistory = function () {
                                    var self = this;
                                    var lastest;
                                    var name = '雇用保険料率';
                                    lastest = {
                                        uuid: self.selectionUnemployeeInsuranceRateHistory(),
                                        start: self.unemployeeInsuranceRateModel().unemployeeInsuranceHistoryModel.startMonth(),
                                        end: self.unemployeeInsuranceRateModel().unemployeeInsuranceHistoryModel.endMonth()
                                    };
                                    if (self.isEmptyUnemployee()) {
                                        lastest = undefined;
                                    }
                                    var unemployeeInsuranceRateCopyDto;
                                    unemployeeInsuranceRateCopyDto = new UnemployeeInsuranceRateCopyDto();
                                    unemployeeInsuranceRateCopyDto.historyIdCopy = self.selectionUnemployeeInsuranceRateHistory();
                                    var newHistoryOptions = {
                                        screenMode: ScreenMode.MODE_HISTORY_ONLY,
                                        name: name,
                                        master: null,
                                        lastest: lastest,
                                        onCopyCallBack: function (data) {
                                            var dfd = $.Deferred();
                                            unemployeeInsuranceRateCopyDto.startMonth = data.startYearMonth;
                                            unemployeeInsuranceRateCopyDto.addNew = false;
                                            a.service.copyUnemployeeInsuranceRate(unemployeeInsuranceRateCopyDto).done(function () {
                                                self.typeActionUnemployeeInsurance(TypeActionInsuranceRate.add);
                                                self.reloadDataUnemployeeInsuranceRateByAction();
                                                self.clearErrorSaveUnemployeeInsurance();
                                                dfd.resolve();
                                            }).fail(function (error) {
                                                dfd.reject(error);
                                            });
                                            return dfd.promise();
                                        },
                                        onCreateCallBack: function (data) {
                                            var dfd = $.Deferred();
                                            unemployeeInsuranceRateCopyDto.startMonth = data.startYearMonth;
                                            unemployeeInsuranceRateCopyDto.addNew = true;
                                            a.service.copyUnemployeeInsuranceRate(unemployeeInsuranceRateCopyDto).done(function () {
                                                self.typeActionUnemployeeInsurance(TypeActionInsuranceRate.add);
                                                self.reloadDataUnemployeeInsuranceRateByAction();
                                                self.clearErrorSaveUnemployeeInsurance();
                                                dfd.resolve();
                                            }).fail(function (error) {
                                                dfd.reject(error);
                                            });
                                            return dfd.promise();
                                        }
                                    };
                                    nts.uk.ui.windows.setShared('options', newHistoryOptions);
                                    var ntsDialogOptions = {
                                        title: nts.uk.text.format('{0}の登録 > 履歴の追加', name),
                                        dialogClass: 'no-close'
                                    };
                                    nts.uk.ui.windows.sub.modal('/view/base/simplehistory/newhistory/index.xhtml', ntsDialogOptions);
                                };
                                ScreenModel.prototype.openEditInsuranceBusinessType = function () {
                                    var self = this;
                                    a.service.findAllInsuranceBusinessType().done(function (data) {
                                        nts.uk.ui.windows.setShared("InsuranceBusinessTypeDto", data);
                                        nts.uk.ui.windows.sub.modal("/view/qmm/011/e/index.xhtml", { height: 630, width: 425, title: "事業種類の登録", dialogClass: 'no-close' }).onClosed(function () {
                                            var insuranceBusinessTypeUpdateModel = nts.uk.ui.windows.getShared("insuranceBusinessTypeUpdateModel");
                                            if (insuranceBusinessTypeUpdateModel) {
                                                a.service.findAllInsuranceBusinessType().done(function (data) {
                                                    self.updateInsuranceBusinessTypeAccidentInsuranceDto(data);
                                                });
                                            }
                                        });
                                    });
                                };
                                ScreenModel.prototype.openEditAccidentInsuranceRateHistory = function () {
                                    var self = this;
                                    if (self.dirtyAccidentInsurance.isDirty()) {
                                        nts.uk.ui.dialog.confirm(self.messageList()[2].message).ifYes(function () {
                                            self.onShowEditAccidentInsuranceRateHistory();
                                        }).ifNo(function () {
                                        });
                                        return;
                                    }
                                    self.onShowEditAccidentInsuranceRateHistory();
                                };
                                ScreenModel.prototype.onShowEditAccidentInsuranceRateHistory = function () {
                                    var self = this;
                                    a.service.findAccidentInsuranceRateHistory(self.selectionAccidentInsuranceRateHistory()).done(function (data) {
                                        var history;
                                        var endMonth = data.endMonth;
                                        var name = '労働保険料率';
                                        history = {
                                            uuid: data.historyId,
                                            start: data.startMonth,
                                            end: endMonth
                                        };
                                        var newHistoryOptions = {
                                            screenMode: ScreenMode.MODE_HISTORY_ONLY,
                                            name: name,
                                            master: null,
                                            history: history,
                                            removeMasterOnLastHistoryRemove: false,
                                            onDeleteCallBack: function (data) {
                                                var accidentInsuranceRateDeleteDto;
                                                accidentInsuranceRateDeleteDto = new AccidentInsuranceRateDeleteDto();
                                                accidentInsuranceRateDeleteDto.code = data.historyId;
                                                accidentInsuranceRateDeleteDto.version = 0;
                                                a.service.deleteAccidentInsuranceRate(accidentInsuranceRateDeleteDto).done(function (data) {
                                                    self.typeActionAccidentInsurance(TypeActionInsuranceRate.add);
                                                    self.reloadDataAccidentInsuranceRateByAction();
                                                }).fail(function (error) {
                                                    self.showMessageSaveAccidentInsurance(error.messageId);
                                                });
                                            },
                                            onUpdateCallBack: function (data) {
                                                var dfd = $.Deferred();
                                                var accidentInsuranceHistoryUpdateDto;
                                                accidentInsuranceHistoryUpdateDto = new AccidentInsuranceHistoryUpdateDto();
                                                accidentInsuranceHistoryUpdateDto.historyId = data.historyId;
                                                accidentInsuranceHistoryUpdateDto.startMonth = data.startYearMonth;
                                                accidentInsuranceHistoryUpdateDto.endMonth = endMonth;
                                                a.service.updateAccidentInsuranceRateHistory(accidentInsuranceHistoryUpdateDto).done(function () {
                                                    self.typeActionAccidentInsurance(TypeActionInsuranceRate.add);
                                                    self.reloadDataAccidentInsuranceRateByAction();
                                                    dfd.resolve();
                                                }).fail(function (error) {
                                                    dfd.reject(error);
                                                });
                                                return dfd.promise();
                                            }
                                        };
                                        nts.uk.ui.windows.setShared('options', newHistoryOptions);
                                        var ntsDialogOptions = {
                                            title: nts.uk.text.format('{0}の登録 > 履歴の編集', name),
                                            dialogClass: 'no-close'
                                        };
                                        nts.uk.ui.windows.sub.modal('/view/base/simplehistory/updatehistory/index.xhtml', ntsDialogOptions);
                                    });
                                };
                                ScreenModel.prototype.openAddAccidentInsuranceRateHistory = function () {
                                    var self = this;
                                    if (self.dirtyAccidentInsurance.isDirty() && !self.isEmptyAccident()) {
                                        nts.uk.ui.dialog.confirm(self.messageList()[2].message).ifYes(function () {
                                            self.onShowAddAccidentInsuranceRateHistory();
                                        }).ifNo(function () {
                                        });
                                        return;
                                    }
                                    self.onShowAddAccidentInsuranceRateHistory();
                                };
                                ScreenModel.prototype.onShowAddAccidentInsuranceRateHistory = function () {
                                    var self = this;
                                    var lastest;
                                    var name = '労働保険料率';
                                    lastest = {
                                        uuid: self.selectionAccidentInsuranceRateHistory(),
                                        start: self.accidentInsuranceRateModel().accidentInsuranceRateHistoryModel.startMonth(),
                                        end: self.accidentInsuranceRateModel().accidentInsuranceRateHistoryModel.endMonth()
                                    };
                                    if (self.isEmptyAccident()) {
                                        lastest = undefined;
                                    }
                                    var accidentInsuranceRateCopyDto;
                                    accidentInsuranceRateCopyDto = new AccidentInsuranceRateCopyDto();
                                    accidentInsuranceRateCopyDto.historyIdCopy = self.selectionAccidentInsuranceRateHistory();
                                    var newHistoryOptions = {
                                        screenMode: ScreenMode.MODE_HISTORY_ONLY,
                                        name: name,
                                        master: null,
                                        lastest: lastest,
                                        onCopyCallBack: function (data) {
                                            var dfd = $.Deferred();
                                            accidentInsuranceRateCopyDto.startMonth = data.startYearMonth;
                                            accidentInsuranceRateCopyDto.addNew = false;
                                            a.service.copyAccidentInsuranceRate(accidentInsuranceRateCopyDto).done(function (data) {
                                                self.typeActionAccidentInsurance(TypeActionInsuranceRate.add);
                                                self.reloadDataAccidentInsuranceRateByAction();
                                                self.clearErrorSaveAccidentInsurance();
                                                dfd.resolve();
                                            }).fail(function (error) {
                                                dfd.reject(error);
                                            });
                                            return dfd.promise();
                                        },
                                        onCreateCallBack: function (data) {
                                            var dfd = $.Deferred();
                                            accidentInsuranceRateCopyDto.startMonth = data.startYearMonth;
                                            accidentInsuranceRateCopyDto.addNew = true;
                                            a.service.copyAccidentInsuranceRate(accidentInsuranceRateCopyDto).done(function (data) {
                                                self.typeActionAccidentInsurance(TypeActionInsuranceRate.add);
                                                self.reloadDataAccidentInsuranceRateByAction();
                                                self.clearErrorSaveAccidentInsurance();
                                                dfd.resolve();
                                            }).fail(function (error) {
                                                dfd.reject(error);
                                            });
                                            return dfd.promise();
                                        }
                                    };
                                    nts.uk.ui.windows.setShared('options', newHistoryOptions);
                                    var ntsDialogOptions = {
                                        title: nts.uk.text.format('{0}の登録 > 履歴の追加', name),
                                        dialogClass: 'no-close'
                                    };
                                    nts.uk.ui.windows.sub.modal('/view/base/simplehistory/newhistory/index.xhtml', ntsDialogOptions);
                                };
                                ScreenModel.prototype.showchangeUnemployeeInsuranceHistory = function (selectionUnemployeeInsuranceRateHistory) {
                                    if (selectionUnemployeeInsuranceRateHistory != null
                                        && selectionUnemployeeInsuranceRateHistory != undefined
                                        && selectionUnemployeeInsuranceRateHistory != '') {
                                        var self = this;
                                        if (self.dirtyUnemployeeInsurance.isDirty() && self.isShowDirtyUnemployeeInsurance()) {
                                            if (selectionUnemployeeInsuranceRateHistory !== self.preSelectUnemployeeInsuranceRateHistory()
                                                && self.typeActionUnemployeeInsurance() == TypeActionInsuranceRate.update) {
                                                nts.uk.ui.dialog.confirm(self.messageList()[2].message).ifYes(function () {
                                                    self.isShowDirtyUnemployeeInsurance(false);
                                                    self.clearErrorSaveUnemployeeInsurance();
                                                    self.detailUnemployeeInsuranceRateHistory(selectionUnemployeeInsuranceRateHistory);
                                                }).ifNo(function () {
                                                    self.selectionUnemployeeInsuranceRateHistory(self.preSelectUnemployeeInsuranceRateHistory());
                                                });
                                            }
                                        }
                                        else {
                                            self.clearErrorSaveUnemployeeInsurance();
                                            self.detailUnemployeeInsuranceRateHistory(selectionUnemployeeInsuranceRateHistory);
                                        }
                                    }
                                };
                                ScreenModel.prototype.clearErrorSaveUnemployeeInsurance = function () {
                                    var self = this;
                                    $('.save-error').ntsError('clear');
                                    $('#btn_saveUnemployeeInsuranceHistory').ntsError('clear');
                                };
                                ScreenModel.prototype.showMessageSaveUnemployeeInsurance = function (messageId) {
                                    var self = this;
                                    if (self.messageList()[0].messageId === messageId) {
                                        var message = self.messageList()[0].message;
                                        if (!self.unemployeeInsuranceRateModel().unemployeeInsuranceRateItemAgroforestryModel) {
                                            $('#inp_code').ntsError('set', message);
                                        }
                                    }
                                    if (self.messageList()[1].messageId === messageId) {
                                        message = self.messageList()[1].message;
                                        $('#inp_code').ntsError('set', message);
                                    }
                                    if (self.messageList()[3].messageId === messageId) {
                                        message = self.messageList()[3].message;
                                        $('#btn_saveUnemployeeInsuranceHistory').ntsError('set', message);
                                    }
                                };
                                ScreenModel.prototype.clearErrorSaveAccidentInsurance = function () {
                                    var self = this;
                                    $('.save-error').ntsError('clear');
                                    $('#btn_saveAccidentInsuranceHistory').ntsError('clear');
                                };
                                ScreenModel.prototype.showMessageSaveAccidentInsurance = function (messageId) {
                                    var self = this;
                                    if (self.messageList()[0].messageId === messageId) {
                                        var message = self.messageList()[0].message;
                                        if (!self.unemployeeInsuranceRateModel().unemployeeInsuranceRateItemAgroforestryModel) {
                                            $('#inp_code').ntsError('set', message);
                                        }
                                    }
                                    if (self.messageList()[1].messageId === messageId) {
                                        message = self.messageList()[1].message;
                                        $('#inp_code').ntsError('set', message);
                                    }
                                    if (self.messageList()[3].messageId === messageId) {
                                        message = self.messageList()[3].message;
                                        $('#btn_saveAccidentInsuranceHistory').ntsError('set', message);
                                    }
                                };
                                ScreenModel.prototype.saveUnemployeeInsuranceHistory = function () {
                                    var self = this;
                                    if (self.typeActionUnemployeeInsurance() == TypeActionInsuranceRate.add) {
                                        a.service.addUnemployeeInsuranceRate(self.unemployeeInsuranceRateModel()).done(function () {
                                            self.reloadDataUnemployeeInsuranceRateByAction();
                                            self.clearErrorSaveUnemployeeInsurance();
                                        }).fail(function (res) {
                                            self.showMessageSaveUnemployeeInsurance(res.messageId);
                                            self.reloadDataUnemployeeInsuranceRateByAction();
                                        });
                                    }
                                    else {
                                        a.service.updateUnemployeeInsuranceRate(self.unemployeeInsuranceRateModel()).done(function () {
                                            self.reloadDataUnemployeeInsuranceRateByAction();
                                            self.clearErrorSaveUnemployeeInsurance();
                                        }).fail(function (res) {
                                            self.showMessageSaveUnemployeeInsurance(res.messageId);
                                            self.reloadDataUnemployeeInsuranceRateByAction();
                                        });
                                    }
                                    return true;
                                };
                                ScreenModel.prototype.saveAccidentInsuranceHistory = function () {
                                    var self = this;
                                    if (self.typeActionAccidentInsurance() == TypeActionInsuranceRate.add) {
                                        a.service.addAccidentInsuranceRate(self.accidentInsuranceRateModel()).done(function () {
                                            self.reloadDataAccidentInsuranceRateByAction();
                                            self.clearErrorSaveAccidentInsurance();
                                        }).fail(function (res) {
                                            self.showMessageSaveAccidentInsurance(res.messageId);
                                        });
                                    }
                                    else {
                                        a.service.updateAccidentInsuranceRate(self.accidentInsuranceRateModel()).done(function () {
                                            self.detailAccidentInsuranceRateHistory(self.accidentInsuranceRateModel().accidentInsuranceRateHistoryModel.historyId());
                                            self.clearErrorSaveAccidentInsurance();
                                        }).fail(function (res) {
                                            self.showMessageSaveAccidentInsurance(res.messageId);
                                        });
                                    }
                                };
                                ScreenModel.prototype.showchangeAccidentInsuranceHistory = function (selectionAccidentInsuranceRateHistory) {
                                    if (selectionAccidentInsuranceRateHistory && selectionAccidentInsuranceRateHistory != '') {
                                        var self = this;
                                        if (self.dirtyAccidentInsurance.isDirty() && self.isShowDirtyActionAccidentInsurance()) {
                                            if (selectionAccidentInsuranceRateHistory !== self.preSelectAccidentInsuranceRateHistory()
                                                && self.typeActionAccidentInsurance() == TypeActionInsuranceRate.update) {
                                                nts.uk.ui.dialog.confirm(self.messageList()[2].message).ifYes(function () {
                                                    self.isShowDirtyActionAccidentInsurance(false);
                                                    self.detailAccidentInsuranceRateHistory(selectionAccidentInsuranceRateHistory);
                                                    return;
                                                }).ifNo(function () {
                                                });
                                                self.selectionAccidentInsuranceRateHistory(self.preSelectAccidentInsuranceRateHistory());
                                                return;
                                            }
                                        }
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
                                            self.detailUnemployeeInsuranceRateHistory(data[0].historyId).done(function () {
                                                dfd.resolve();
                                            });
                                        }
                                        else {
                                            self.newmodelEmptyDataUnemployeeInsuranceRate();
                                            dfd.resolve();
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
                                    self.dirtyUnemployeeInsurance.reset();
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
                                            self.dirtyUnemployeeInsurance.reset();
                                            self.isShowDirtyUnemployeeInsurance(true);
                                            self.preSelectUnemployeeInsuranceRateHistory(historyId);
                                            dfd.resolve();
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
                                    self.dirtyAccidentInsurance.reset();
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
                                            self.dirtyAccidentInsurance.reset();
                                            dfd.resolve(null);
                                        });
                                    }
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.updateInsuranceBusinessTypeAccidentInsurance = function (insuranceBusinessTypeUpdateModel) {
                                    var self = this;
                                    self.accidentInsuranceRateModel()
                                        .biz1StModel
                                        .updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz1St());
                                    self.accidentInsuranceRateModel()
                                        .biz2NdModel
                                        .updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz2Nd());
                                    self.accidentInsuranceRateModel()
                                        .biz3RdModel
                                        .updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz3Rd());
                                    self.accidentInsuranceRateModel()
                                        .biz4ThModel
                                        .updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz4Th());
                                    self.accidentInsuranceRateModel()
                                        .biz5ThModel
                                        .updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz5Th());
                                    self.accidentInsuranceRateModel()
                                        .biz6ThModel
                                        .updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz6Th());
                                    self.accidentInsuranceRateModel()
                                        .biz7ThModel
                                        .updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz7Th());
                                    self.accidentInsuranceRateModel()
                                        .biz8ThModel
                                        .updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz8Th());
                                    self.accidentInsuranceRateModel()
                                        .biz9ThModel
                                        .updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz9Th());
                                    self.accidentInsuranceRateModel()
                                        .biz10ThModel
                                        .updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz10Th());
                                };
                                ScreenModel.prototype.updateInsuranceBusinessTypeAccidentInsuranceDto = function (InsuranceBusinessTypeDto) {
                                    var self = this;
                                    self.accidentInsuranceRateModel()
                                        .biz1StModel
                                        .updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz1St);
                                    self.accidentInsuranceRateModel()
                                        .biz2NdModel
                                        .updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz2Nd);
                                    self.accidentInsuranceRateModel()
                                        .biz3RdModel
                                        .updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz3Rd);
                                    self.accidentInsuranceRateModel()
                                        .biz4ThModel
                                        .updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz4Th);
                                    self.accidentInsuranceRateModel()
                                        .biz5ThModel
                                        .updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz5Th);
                                    self.accidentInsuranceRateModel()
                                        .biz6ThModel
                                        .updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz6Th);
                                    self.accidentInsuranceRateModel()
                                        .biz7ThModel
                                        .updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz7Th);
                                    self.accidentInsuranceRateModel()
                                        .biz8ThModel
                                        .updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz8Th);
                                    self.accidentInsuranceRateModel()
                                        .biz9ThModel
                                        .updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz9Th);
                                    self.accidentInsuranceRateModel()
                                        .biz10ThModel
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
                                };
                                return UnemployeeInsuranceRateModel;
                            }());
                            viewmodel.UnemployeeInsuranceRateModel = UnemployeeInsuranceRateModel;
                            var AccIRDetailModel = (function () {
                                function AccIRDetailModel(rateInputOptions, selectionRoundingMethod) {
                                    this.rateInputOptions = rateInputOptions;
                                    this.selectionRoundingMethod = selectionRoundingMethod;
                                    this.insuranceBusinessType = ko.observable('');
                                    this.insuRate = ko.observable(0);
                                    this.insuRound = ko.observable(0);
                                    this.isEnable = ko.observable(true);
                                }
                                AccIRDetailModel.prototype.updateInsuranceBusinessType = function (insuranceBusinessType) {
                                    if (this.insuranceBusinessType() != null && this.insuranceBusinessType() != undefined) {
                                        this.insuranceBusinessType(insuranceBusinessType);
                                    }
                                };
                                AccIRDetailModel.prototype.setItem = function (insuBizRateItem) {
                                    this.insuRate(insuBizRateItem.insuRate);
                                    this.insuRound(insuBizRateItem.insuRound);
                                };
                                AccIRDetailModel.prototype.resetValue = function () {
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
                                AccIRDetailModel.prototype.setEnable = function (isEnable) {
                                    this.isEnable(isEnable);
                                };
                                return AccIRDetailModel;
                            }());
                            viewmodel.AccIRDetailModel = AccIRDetailModel;
                            var AccIRHistoryModel = (function () {
                                function AccIRHistoryModel() {
                                    this.historyId = ko.observable('');
                                    this.startMonthRage = ko.observable('');
                                    this.endMonthRage = ko.observable('');
                                    this.endMonth = ko.observable(0);
                                    this.startMonth = ko.observable(0);
                                }
                                AccIRHistoryModel.prototype.resetValue = function () {
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
                                AccIRHistoryModel.prototype.updateData = function (historyDto) {
                                    this.resetValue();
                                    this.historyId(historyDto.historyId);
                                    this.startMonth(historyDto.startMonth);
                                    this.endMonth(historyDto.endMonth);
                                    this.startMonthRage(nts.uk.time.formatYearMonth(historyDto.startMonth)
                                        + ' (' + nts.uk.time.yearmonthInJapanEmpire(historyDto.startMonth).toString() + ') ');
                                    this.endMonthRage(nts.uk.time.formatYearMonth(historyDto.endMonth));
                                };
                                return AccIRHistoryModel;
                            }());
                            viewmodel.AccIRHistoryModel = AccIRHistoryModel;
                            var AccIRModel = (function () {
                                function AccIRModel(rateInputOptions, selectionRoundingMethod) {
                                    this.biz1StModel =
                                        new AccIRDetailModel(rateInputOptions, selectionRoundingMethod);
                                    this.biz2NdModel =
                                        new AccIRDetailModel(rateInputOptions, selectionRoundingMethod);
                                    this.biz3RdModel =
                                        new AccIRDetailModel(rateInputOptions, selectionRoundingMethod);
                                    this.biz4ThModel =
                                        new AccIRDetailModel(rateInputOptions, selectionRoundingMethod);
                                    this.biz5ThModel =
                                        new AccIRDetailModel(rateInputOptions, selectionRoundingMethod);
                                    this.biz6ThModel =
                                        new AccIRDetailModel(rateInputOptions, selectionRoundingMethod);
                                    this.biz7ThModel =
                                        new AccIRDetailModel(rateInputOptions, selectionRoundingMethod);
                                    this.biz8ThModel =
                                        new AccIRDetailModel(rateInputOptions, selectionRoundingMethod);
                                    this.biz9ThModel =
                                        new AccIRDetailModel(rateInputOptions, selectionRoundingMethod);
                                    this.biz10ThModel =
                                        new AccIRDetailModel(rateInputOptions, selectionRoundingMethod);
                                    this.accidentInsuranceRateHistoryModel = new AccIRHistoryModel();
                                }
                                AccIRModel.prototype.setListItem = function (lstInsuBizRateItem) {
                                    for (var _i = 0, lstInsuBizRateItem_1 = lstInsuBizRateItem; _i < lstInsuBizRateItem_1.length; _i++) {
                                        var rateItem = lstInsuBizRateItem_1[_i];
                                        if (rateItem.insuBizType == BusinessTypeEnumDto.Biz1St) {
                                            this.biz1StModel.setItem(rateItem);
                                        }
                                        if (rateItem.insuBizType == BusinessTypeEnumDto.Biz2Nd) {
                                            this.biz2NdModel.setItem(rateItem);
                                        }
                                        if (rateItem.insuBizType == BusinessTypeEnumDto.Biz3Rd) {
                                            this.biz3RdModel.setItem(rateItem);
                                        }
                                        if (rateItem.insuBizType == BusinessTypeEnumDto.Biz4Th) {
                                            this.biz4ThModel.setItem(rateItem);
                                        }
                                        if (rateItem.insuBizType == BusinessTypeEnumDto.Biz5Th) {
                                            this.biz5ThModel.setItem(rateItem);
                                        }
                                        if (rateItem.insuBizType == BusinessTypeEnumDto.Biz6Th) {
                                            this.biz6ThModel.setItem(rateItem);
                                        }
                                        if (rateItem.insuBizType == BusinessTypeEnumDto.Biz7Th) {
                                            this.biz7ThModel.setItem(rateItem);
                                        }
                                        if (rateItem.insuBizType == BusinessTypeEnumDto.Biz8Th) {
                                            this.biz8ThModel.setItem(rateItem);
                                        }
                                        if (rateItem.insuBizType == BusinessTypeEnumDto.Biz9Th) {
                                            this.biz9ThModel.setItem(rateItem);
                                        }
                                        if (rateItem.insuBizType == BusinessTypeEnumDto.Biz10Th) {
                                            this.biz10ThModel.setItem(rateItem);
                                        }
                                    }
                                };
                                AccIRModel.prototype.setVersion = function (version) {
                                    if (this.version == null || this.version == undefined) {
                                        this.version = ko.observable(version);
                                    }
                                    else {
                                        this.version(version);
                                    }
                                };
                                AccIRModel.prototype.setHistoryData = function (historyDto) {
                                    this.accidentInsuranceRateHistoryModel.updateData(historyDto);
                                };
                                AccIRModel.prototype.resetValue = function (rateInputOptions, selectionRoundingMethod) {
                                    if (this.biz1StModel == null
                                        || this.biz1StModel == undefined) {
                                        this.biz1StModel
                                            = new AccIRDetailModel(rateInputOptions, selectionRoundingMethod);
                                        this.biz1StModel.resetValue();
                                    }
                                    else {
                                        this.biz1StModel.resetValue();
                                    }
                                    if (this.biz2NdModel == null
                                        || this.biz2NdModel == undefined) {
                                        this.biz2NdModel
                                            = new AccIRDetailModel(rateInputOptions, selectionRoundingMethod);
                                        this.biz2NdModel.resetValue();
                                    }
                                    else {
                                        this.biz2NdModel.resetValue();
                                    }
                                    if (this.biz3RdModel == null
                                        || this.biz3RdModel == undefined) {
                                        this.biz3RdModel
                                            = new AccIRDetailModel(rateInputOptions, selectionRoundingMethod);
                                        this.biz3RdModel.resetValue();
                                    }
                                    else {
                                        this.biz3RdModel.resetValue();
                                    }
                                    if (this.biz4ThModel == null
                                        || this.biz4ThModel == undefined) {
                                        this.biz4ThModel
                                            = new AccIRDetailModel(rateInputOptions, selectionRoundingMethod);
                                        this.biz4ThModel.resetValue();
                                    }
                                    else {
                                        this.biz4ThModel.resetValue();
                                    }
                                    if (this.biz5ThModel == null
                                        || this.biz5ThModel == undefined) {
                                        this.biz5ThModel
                                            = new AccIRDetailModel(rateInputOptions, selectionRoundingMethod);
                                        this.biz5ThModel.resetValue();
                                    }
                                    else {
                                        this.biz5ThModel.resetValue();
                                    }
                                    if (this.biz6ThModel == null
                                        || this.biz6ThModel == undefined) {
                                        this.biz6ThModel
                                            = new AccIRDetailModel(rateInputOptions, selectionRoundingMethod);
                                        this.biz6ThModel.resetValue();
                                    }
                                    else {
                                        this.biz6ThModel.resetValue();
                                    }
                                    if (this.biz7ThModel == null
                                        || this.biz7ThModel == undefined) {
                                        this.biz7ThModel
                                            = new AccIRDetailModel(rateInputOptions, selectionRoundingMethod);
                                        this.biz7ThModel.resetValue();
                                    }
                                    else {
                                        this.biz7ThModel.resetValue();
                                    }
                                    if (this.biz8ThModel == null
                                        || this.biz8ThModel == undefined) {
                                        this.biz8ThModel
                                            = new AccIRDetailModel(rateInputOptions, selectionRoundingMethod);
                                        this.biz8ThModel.resetValue();
                                    }
                                    else {
                                        this.biz8ThModel.resetValue();
                                    }
                                    if (this.biz9ThModel == null
                                        || this.biz9ThModel == undefined) {
                                        this.biz9ThModel
                                            = new AccIRDetailModel(rateInputOptions, selectionRoundingMethod);
                                        this.biz9ThModel.resetValue();
                                    }
                                    else {
                                        this.biz9ThModel.resetValue();
                                    }
                                    if (this.biz10ThModel == null
                                        || this.biz10ThModel == undefined) {
                                        this.biz10ThModel
                                            = new AccIRDetailModel(rateInputOptions, selectionRoundingMethod);
                                        this.biz10ThModel.resetValue();
                                    }
                                    else {
                                        this.biz10ThModel.resetValue();
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
                                            = new AccIRHistoryModel();
                                    }
                                    else {
                                        this.accidentInsuranceRateHistoryModel.resetValue();
                                    }
                                };
                                AccIRModel.prototype.setEnable = function (isEnable) {
                                    this.biz1StModel.setEnable(isEnable);
                                    this.biz2NdModel.setEnable(isEnable);
                                    this.biz3RdModel.setEnable(isEnable);
                                    this.biz4ThModel.setEnable(isEnable);
                                    this.biz5ThModel.setEnable(isEnable);
                                    this.biz6ThModel.setEnable(isEnable);
                                    this.biz7ThModel.setEnable(isEnable);
                                    this.biz8ThModel.setEnable(isEnable);
                                    this.biz9ThModel.setEnable(isEnable);
                                    this.biz10ThModel.setEnable(isEnable);
                                };
                                return AccIRModel;
                            }());
                            viewmodel.AccIRModel = AccIRModel;
                        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
                    })(a = qmm011.a || (qmm011.a = {}));
                })(qmm011 = view.qmm011 || (view.qmm011 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qmm011.a.vm.js.map