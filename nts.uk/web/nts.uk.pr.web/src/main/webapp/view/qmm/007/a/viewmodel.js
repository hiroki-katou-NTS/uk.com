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
                            var UnitPriceHistoryItemDto = a.service.model.UnitPriceHistoryItemDto;
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.isNewMode = ko.observable(true);
                                    var defaultHist = new UnitPriceHistoryDto();
                                    defaultHist.id = '';
                                    defaultHist.unitPriceCode = '';
                                    defaultHist.unitPriceName = '';
                                    defaultHist.startMonth = 2017;
                                    defaultHist.budget = 0;
                                    defaultHist.fixPaySettingType = 'Company';
                                    defaultHist.fixPayAtr = 'NotApply';
                                    defaultHist.fixPayAtrMonthly = 'NotApply';
                                    defaultHist.fixPayAtrDayMonth = 'NotApply';
                                    defaultHist.fixPayAtrDaily = 'NotApply';
                                    defaultHist.fixPayAtrHourly = 'NotApply';
                                    defaultHist.memo = '';
                                    self.unitPriceHistoryModel = ko.observable(new UnitPriceHistoryModel(defaultHist));
                                    self.historyList = ko.observableArray([]);
                                    self.switchButtonDataSource = ko.observableArray([
                                        { code: 'Apply', name: '対象' },
                                        { code: 'NotApply', name: '対象外' }
                                    ]);
                                    self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.historyList(), "childs"));
                                    self.selectedId = ko.observable('');
                                    self.selectedId.subscribe(function (id) {
                                        if (id) {
                                            self.isNewMode(false);
                                            $('.save-error').ntsError('clear');
                                            self.loadUnitPriceDetail(id);
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
                                    self.loadUnitPriceHistoryList().done(function () { return dfd.resolve(); });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.goToB = function () {
                                    nts.uk.ui.windows.setShared('unitPriceHistoryModel', this.unitPriceHistoryModel());
                                    nts.uk.ui.windows.sub.modal('/view/qmm/007/b/index.xhtml', { title: '会社一律金額 の 登録 > 履歴の追加', dialogClass: 'no-close', height: 360, width: 580 });
                                };
                                ScreenModel.prototype.goToC = function () {
                                    nts.uk.ui.windows.setShared('unitPriceHistoryModel', this.unitPriceHistoryModel());
                                    nts.uk.ui.windows.sub.modal('/view/qmm/007/c/index.xhtml', { title: '会社一律金額 の 登録 > 履歴の編集', dialogClass: 'no-close', height: 420, width: 580 });
                                };
                                ScreenModel.prototype.save = function () {
                                    var self = this;
                                    if (self.isNewMode()) {
                                        a.service.create(self.collectData(self.unitPriceHistoryModel()));
                                    }
                                    else {
                                        a.service.update(self.collectData(self.unitPriceHistoryModel()));
                                    }
                                };
                                ScreenModel.prototype.enableNewMode = function () {
                                    var self = this;
                                    $('.save-error').ntsError('clear');
                                    self.clearUnitPriceDetail();
                                    self.isNewMode(true);
                                };
                                ScreenModel.prototype.clearUnitPriceDetail = function () {
                                    var model = this.unitPriceHistoryModel();
                                    model.id = '';
                                    model.unitPriceCode('');
                                    model.unitPriceName('');
                                    model.startMonth('');
                                    model.budget(null);
                                    model.fixPaySettingType('Company');
                                    model.fixPayAtr('NotApply');
                                    model.fixPayAtrMonthly('NotApply');
                                    model.fixPayAtrDayMonth('NotApply');
                                    model.fixPayAtrDaily('NotApply');
                                    model.fixPayAtrHourly('NotApply');
                                    model.memo('');
                                };
                                ScreenModel.prototype.loadUnitPriceDetail = function (id) {
                                    var self = this;
                                    if (id) {
                                        a.service.find(id).done(function (data) {
                                            self.unitPriceHistoryModel(new UnitPriceHistoryModel(data));
                                        });
                                    }
                                };
                                ScreenModel.prototype.loadUnitPriceHistoryList = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    a.service.getUnitPriceHistoryList().done(function (data) {
                                        self.historyList(data.map(function (item, index) { return new UnitPriceHistoryNode(item); }));
                                        dfd.resolve();
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.collectData = function (unitPriceHistoryModel) {
                                    var dto = new UnitPriceHistoryDto();
                                    dto.id = unitPriceHistoryModel.id;
                                    dto.unitPriceCode = unitPriceHistoryModel.unitPriceCode();
                                    dto.unitPriceName = unitPriceHistoryModel.unitPriceName();
                                    dto.startMonth = unitPriceHistoryModel.startMonth();
                                    dto.endMonth = unitPriceHistoryModel.endMonth();
                                    dto.budget = unitPriceHistoryModel.budget();
                                    dto.fixPaySettingType = unitPriceHistoryModel.fixPaySettingType();
                                    dto.fixPayAtr = unitPriceHistoryModel.fixPayAtr();
                                    dto.fixPayAtrMonthly = unitPriceHistoryModel.fixPayAtrMonthly();
                                    dto.fixPayAtrDayMonth = unitPriceHistoryModel.fixPayAtrDayMonth();
                                    dto.fixPayAtrDaily = unitPriceHistoryModel.fixPayAtrDaily();
                                    dto.fixPayAtrHourly = unitPriceHistoryModel.fixPayAtrHourly();
                                    dto.memo = unitPriceHistoryModel.memo();
                                    return dto;
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var UnitPriceHistoryModel = (function () {
                                function UnitPriceHistoryModel(historyDto) {
                                    this.id = historyDto.id;
                                    this.unitPriceCode = ko.observable(historyDto.unitPriceCode);
                                    this.unitPriceName = ko.observable(historyDto.unitPriceName);
                                    this.startMonth = ko.observable(historyDto.startMonth + '');
                                    this.endMonth = ko.observable(historyDto.endMonth + '');
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
                                    self.id = "";
                                    if (item.histories !== undefined) {
                                        var unitPriceItemDto = item;
                                        self.nodeText = unitPriceItemDto.unitPriceCode + '~' + unitPriceItemDto.unitPriceName;
                                        self.childs = unitPriceItemDto.histories.map(function (item, index) { return new UnitPriceHistoryNode(item); });
                                    }
                                    if (item.histories === undefined) {
                                        let < UnitPriceHistoryItemDto > unitPriceHistoryItemDto;
                                        item;
                                        self.id = item.id;
                                        self.nodeText = unitPriceHistoryItemDto.startMonth + '~' + unitPriceHistoryItemDto.endMonth;
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
