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
                                    self.hasSelected = ko.observable(false);
                                    self.hasSelected.subscribe(function (val) {
                                        if (val == true) {
                                        }
                                        else {
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
                                        if (id) {
                                            if (id.length < 4) {
                                                self.selectedId(self.getLatestHistoryId(id));
                                            }
                                            else {
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
                                    self.isLoading(true);
                                    self.loadUnitPriceHistoryList().done(function () {
                                        dfd.resolve();
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.openAddDialog = function () {
                                    var self = this;
                                    var currentModel = self.unitPriceHistoryModel();
                                    if (self.isLatest(currentModel.id, currentModel.unitPriceCode())) {
                                        self.openAddDialogThenReloadIfAdded();
                                    }
                                    else {
                                        self.selectedId(self.getLatestHistoryId(currentModel.unitPriceCode()));
                                        self.openAddDialogThenReloadIfAdded();
                                    }
                                };
                                ScreenModel.prototype.openAddDialogThenReloadIfAdded = function () {
                                    var self = this;
                                    nts.uk.ui.windows.sub.modal('/view/qmm/007/b/index.xhtml', { title: '会社一律金額 の 登録 > 履歴の追加', dialogClass: 'no-close' }).onClosed(function () {
                                        var isCreated = nts.uk.ui.windows.getShared('isCreated');
                                        if (isCreated) {
                                            self.loadUnitPriceHistoryList().done(function () {
                                                self.selectedId(self.getLatestHistoryId(self.unitPriceHistoryModel().unitPriceCode()));
                                            });
                                        }
                                    });
                                };
                                ScreenModel.prototype.openEditDialog = function () {
                                    var self = this;
                                    var currentModel = self.unitPriceHistoryModel();
                                    nts.uk.ui.windows.setShared('isLatestHistory', self.isLatest(currentModel.id, currentModel.unitPriceCode()));
                                    nts.uk.ui.windows.sub.modal('/view/qmm/007/c/index.xhtml', { title: '会社一律金額 の 登録 > 履歴の編集', dialogClass: 'no-close' }).onClosed(function () {
                                        var isUpdated = nts.uk.ui.windows.getShared('isUpdated');
                                        var isRemoved = nts.uk.ui.windows.getShared('isRemoved');
                                        if (isRemoved) {
                                            self.loadUnitPriceHistoryList().done(function () {
                                                var latestHistory = self.getLatestHistoryId(self.unitPriceHistoryModel().unitPriceCode());
                                                if (latestHistory) {
                                                    self.selectedId(latestHistory);
                                                }
                                                else {
                                                    self.setUnitPriceHistoryModel(self.getDefaultUnitPriceHistory());
                                                    self.selectedId(undefined);
                                                }
                                            });
                                        }
                                        if (isUpdated) {
                                            self.loadUnitPriceHistoryList().done(function () {
                                                self.loadUnitPriceDetail(self.selectedId());
                                            });
                                        }
                                    });
                                };
                                ScreenModel.prototype.save = function () {
                                    var self = this;
                                    if (self.isNewMode()) {
                                        a.service.create(ko.toJS(self.unitPriceHistoryModel())).done(function () {
                                            self.loadUnitPriceHistoryList().done(function () {
                                                self.selectedId(self.getLatestHistoryId(self.unitPriceHistoryModel().unitPriceCode()));
                                            });
                                        });
                                    }
                                    else {
                                        a.service.update(ko.toJS(self.unitPriceHistoryModel())).done(function () {
                                            self.loadUnitPriceHistoryList().done(function () {
                                                self.loadUnitPriceDetail(self.selectedId());
                                            });
                                        });
                                    }
                                };
                                ScreenModel.prototype.enableNewMode = function () {
                                    var self = this;
                                    $('.save-error').ntsError('clear');
                                    self.hasSelected(false);
                                    self.selectedId('');
                                    self.setUnitPriceHistoryModel(self.getDefaultUnitPriceHistory());
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
                                ScreenModel.prototype.isLatest = function (historyId, unitPriceCode) {
                                    var self = this;
                                    var latestHistoryId = self.getLatestHistoryId(unitPriceCode);
                                    return historyId == latestHistoryId ? true : false;
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
                                ScreenModel.prototype.loadUnitPriceDetail = function (id) {
                                    var _this = this;
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.isLoading(true);
                                    a.service.find(id).done(function (dto) {
                                        self.setUnitPriceHistoryModel(dto);
                                        self.isNewMode(false);
                                        self.hasSelected(true);
                                        self.isLoading(false);
                                        nts.uk.ui.windows.setShared('unitPriceHistoryModel', ko.toJS(_this.unitPriceHistoryModel()));
                                        dfd.resolve();
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.loadUnitPriceHistoryList = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.isLoading(true);
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
