var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm007;
                (function (qmm007) {
                    var a;
                    (function (a) {
                        var viewmodel;
                        (function (viewmodel) {
                            var UnitPriceHistoryDto = a.service.model.UnitPriceHistoryDto;
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.isNewMode = ko.observable(false);
                                    self.isLoading = ko.observable(true);
                                    self.isInputEnabled = ko.observable(false);
                                    self.isLatestHistory = ko.observable(false);
                                    self.isLatestHistory.subscribe(function (val) {
                                        if (val == true) {
                                            self.isInputEnabled(true);
                                        }
                                        else {
                                            self.isInputEnabled(false);
                                        }
                                    });
                                    self.unitPriceHistoryModel = ko.observable(new UnitPriceHistoryModel(self.getDefaultUnitPriceHistory()));
                                    self.historyList = ko.observableArray([]);
                                    self.switchButtonDataSource = ko.observableArray([
                                        { code: 'Apply', name: '対象' },
                                        { code: 'NotApply', name: '対象外' }
                                    ]);
                                    self.selectedId = ko.observable('');
                                    self.selectedId.subscribe(function (id) {
                                        console.log(id);
                                        if (id) {
                                            if (id.length < 4) {
                                                self.selectedId(self.getLatestHistoryId(id));
                                            }
                                            else {
                                                self.isLoading(true);
                                                $('.save-error').ntsError('clear');
                                                self.loadUnitPriceDetail(id);
                                            }
                                        }
                                    });
                                    self.isContractSettingEnabled = ko.observable(false);
                                    self.unitPriceHistoryModel().fixPaySettingType.subscribe(function (val) {
                                        val == 'Contract' ? self.isContractSettingEnabled(true) : self.isContractSettingEnabled(false);
                                    });
                                    self.textEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                        textmode: "text",
                                        placeholder: "",
                                        textalign: "left"
                                    }));
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.loadUnitPriceHistoryList().done(function () {
                                        dfd.resolve();
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.openAddDialog = function () {
                                    var self = this;
                                    nts.uk.ui.windows.setShared('unitPriceHistoryModel', ko.toJS(self.unitPriceHistoryModel()));
                                    nts.uk.ui.windows.sub.modal('/view/qmm/007/b/index.xhtml', { title: '会社一律金額 の 登録 > 履歴の追加', dialogClass: 'no-close' }).onClosed(function () {
                                        var startMonth = nts.uk.ui.windows.getShared('startMonth');
                                        if (startMonth) {
                                            self.unitPriceHistoryModel().endMonth(startMonth);
                                            a.service.update(ko.toJS(self.unitPriceHistoryModel())).done(function () {
                                                self.loadUnitPriceHistoryList().done(function () {
                                                    self.selectedId(self.getLatestHistoryId(self.unitPriceHistoryModel().unitPriceCode()));
                                                });
                                            });
                                        }
                                    });
                                };
                                ScreenModel.prototype.openEditDialog = function () {
                                    var self = this;
                                    nts.uk.ui.windows.setShared('unitPriceHistoryModel', ko.toJS(this.unitPriceHistoryModel()));
                                    nts.uk.ui.windows.setShared('isLatestHistory', self.isLatestHistory());
                                    nts.uk.ui.windows.sub.modal('/view/qmm/007/c/index.xhtml', { title: '会社一律金額 の 登録 > 履歴の編集', dialogClass: 'no-close' }).onClosed(function () {
                                        if (nts.uk.ui.windows.getShared('isRemoved')) {
                                            var secondLatestHistory = self.getSecondLatestHistoryId(self.unitPriceHistoryModel().unitPriceCode());
                                            if (secondLatestHistory) {
                                                a.service.find(secondLatestHistory).done(function (dto) {
                                                    self.setUnitPriceHistoryModel(dto);
                                                    self.unitPriceHistoryModel().endMonth('9999/12');
                                                    a.service.update(ko.toJS(self.unitPriceHistoryModel())).done(function () {
                                                        self.loadUnitPriceHistoryList().done(function () {
                                                            self.selectedId(self.getLatestHistoryId(self.unitPriceHistoryModel().unitPriceCode()));
                                                        });
                                                    });
                                                });
                                            }
                                            else {
                                                self.loadUnitPriceHistoryList().done(function () {
                                                    self.setUnitPriceHistoryModel(self.getDefaultUnitPriceHistory());
                                                    self.isInputEnabled(false);
                                                    self.selectedId(undefined);
                                                });
                                            }
                                        }
                                    });
                                };
                                ScreenModel.prototype.save = function () {
                                    var self = this;
                                    if (self.isNewMode()) {
                                        a.service.create(ko.toJS(self.unitPriceHistoryModel())).done(function () {
                                            self.loadUnitPriceHistoryList();
                                        });
                                    }
                                    else {
                                        a.service.update(ko.toJS(self.unitPriceHistoryModel())).done(function () {
                                            self.loadUnitPriceHistoryList();
                                        });
                                    }
                                };
                                ScreenModel.prototype.enableNewMode = function () {
                                    var self = this;
                                    $('.save-error').ntsError('clear');
                                    self.selectedId('');
                                    self.setUnitPriceHistoryModel(self.getDefaultUnitPriceHistory());
                                    self.isInputEnabled(true);
                                    self.isNewMode(true);
                                };
                                ScreenModel.prototype.setUnitPriceHistoryModel = function (dto) {
                                    var model = this.unitPriceHistoryModel();
                                    model.id = dto.id;
                                    model.unitPriceCode(dto.unitPriceCode);
                                    model.unitPriceName(dto.unitPriceName);
                                    model.startMonth(nts.uk.time.formatYearMonth(dto.startMonth));
                                    model.endMonth(nts.uk.time.formatYearMonth(dto.endMonth));
                                    model.budget(dto.budget);
                                    model.fixPaySettingType(dto.fixPaySettingType);
                                    model.fixPayAtr(dto.fixPayAtr);
                                    model.fixPayAtrMonthly(dto.fixPayAtrMonthly);
                                    model.fixPayAtrDayMonth(dto.fixPayAtrDayMonth);
                                    model.fixPayAtrDaily(dto.fixPayAtrDaily);
                                    model.fixPayAtrHourly(dto.fixPayAtrHourly);
                                    model.memo(dto.memo);
                                };
                                ScreenModel.prototype.getDefaultUnitPriceHistory = function () {
                                    var defaultHist = new UnitPriceHistoryDto();
                                    defaultHist.id = '';
                                    defaultHist.unitPriceCode = '';
                                    defaultHist.unitPriceName = '';
                                    defaultHist.startMonth = 201701;
                                    defaultHist.endMonth = 999912;
                                    defaultHist.budget = 0;
                                    defaultHist.fixPaySettingType = 'Company';
                                    defaultHist.fixPayAtr = 'NotApply';
                                    defaultHist.fixPayAtrMonthly = 'NotApply';
                                    defaultHist.fixPayAtrDayMonth = 'NotApply';
                                    defaultHist.fixPayAtrDaily = 'NotApply';
                                    defaultHist.fixPayAtrHourly = 'NotApply';
                                    defaultHist.memo = '';
                                    return defaultHist;
                                };
                                ScreenModel.prototype.isLatest = function (history) {
                                    var self = this;
                                    var latestHistoryId = self.getLatestHistoryId(history.unitPriceCode);
                                    return history.id == latestHistoryId ? true : false;
                                };
                                ScreenModel.prototype.getLatestHistoryId = function (code) {
                                    var self = this;
                                    var lastestHistoryId = '';
                                    self.historyList().some(function (node) {
                                        if (code == node.id) {
                                            lastestHistoryId = node.childs[0].id;
                                            return true;
                                        }
                                    });
                                    return lastestHistoryId;
                                };
                                ScreenModel.prototype.getSecondLatestHistoryId = function (code) {
                                    var self = this;
                                    var latestHistoryId = '';
                                    self.historyList().some(function (node) {
                                        if (code == node.id) {
                                            latestHistoryId = node.childs[1] ? node.childs[1].id : undefined;
                                            return true;
                                        }
                                    });
                                    return latestHistoryId;
                                };
                                ScreenModel.prototype.loadUnitPriceDetail = function (id) {
                                    var self = this;
                                    a.service.find(id).done(function (dto) {
                                        self.setUnitPriceHistoryModel(dto);
                                        self.isLatestHistory(self.isLatest(dto));
                                        self.isNewMode(false);
                                        self.isLoading(false);
                                    });
                                };
                                ScreenModel.prototype.loadUnitPriceHistoryList = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    a.service.getUnitPriceHistoryList().done(function (data) {
                                        self.historyList(data.map(function (item, index) { return new UnitPriceHistoryNode(item); }));
                                        self.isLoading(false);
                                        dfd.resolve();
                                    });
                                    return dfd.promise();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var UnitPriceHistoryModel = (function () {
                                function UnitPriceHistoryModel(historyDto) {
                                    this.id = historyDto.id;
                                    this.unitPriceCode = ko.observable(historyDto.unitPriceCode);
                                    this.unitPriceName = ko.observable(historyDto.unitPriceName);
                                    this.startMonth = ko.observable(nts.uk.time.formatYearMonth(historyDto.startMonth));
                                    this.endMonth = ko.observable(nts.uk.time.formatYearMonth(historyDto.endMonth));
                                    this.budget = ko.observable(historyDto.budget);
                                    this.fixPaySettingType = ko.observable(historyDto.fixPaySettingType);
                                    this.fixPayAtr = ko.observable(historyDto.fixPayAtr);
                                    this.fixPayAtrMonthly = ko.observable(historyDto.fixPayAtrMonthly);
                                    this.fixPayAtrDayMonth = ko.observable(historyDto.fixPayAtrDayMonth);
                                    this.fixPayAtrDaily = ko.observable(historyDto.fixPayAtrDaily);
                                    this.fixPayAtrHourly = ko.observable(historyDto.fixPayAtrHourly);
                                    this.memo = ko.observable(historyDto.memo);
                                }
                                return UnitPriceHistoryModel;
                            }());
                            viewmodel.UnitPriceHistoryModel = UnitPriceHistoryModel;
                            var UnitPriceHistoryNode = (function () {
                                function UnitPriceHistoryNode(item) {
                                    var self = this;
                                    if (item.histories !== undefined) {
                                        self.id = item.unitPriceCode;
                                        self.nodeText = item.unitPriceCode + ' ' + item.unitPriceName;
                                        self.childs = item.histories.map(function (item, index) { return new UnitPriceHistoryNode(item); });
                                    }
                                    if (item.histories === undefined) {
                                        self.id = item.id;
                                        self.nodeText = nts.uk.time.formatYearMonth(item.startMonth) + ' ~ ' + nts.uk.time.formatYearMonth(item.endMonth);
                                    }
                                }
                                return UnitPriceHistoryNode;
                            }());
                            viewmodel.UnitPriceHistoryNode = UnitPriceHistoryNode;
                        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
                    })(a = qmm007.a || (qmm007.a = {}));
                })(qmm007 = view.qmm007 || (view.qmm007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
