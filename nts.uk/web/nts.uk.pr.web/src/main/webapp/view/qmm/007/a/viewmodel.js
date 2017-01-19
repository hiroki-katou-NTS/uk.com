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
                                    self.unitPriceHistoryModel = ko.observable(new UnitPriceHistoryModel());
                                    self.historyList = ko.observableArray();
                                    self.switchButtonDataSource = ko.observableArray([
                                        { code: 'Apply', name: '対象' },
                                        { code: 'NotApply', name: '対象外' }
                                    ]);
                                    self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.historyList(), "childs"));
                                    self.selectedId = ko.observable('');
                                    self.selectedId.subscribe(function (id) {
                                        if (id != null || id != undefined) {
                                            self.isNewMode(false);
                                            self.loadUnitPriceDetail();
                                        }
                                    });
                                    self.isContractSettingEnabled = ko.observable(true);
                                    self.isNewMode = ko.observable(true);
                                    self.unitPriceHistoryModel().fixPaySettingType.subscribe(function (val) {
                                        val == 'Contract' ? self.isContractSettingEnabled(true) : self.isContractSettingEnabled(false);
                                    });
                                    self.textEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                        textmode: "text",
                                        placeholder: "",
                                        textalign: "left"
                                    }));
                                    self.currencyEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                                        grouplength: 3,
                                        decimallength: 2,
                                        currencyformat: "JPY",
                                        currencyposition: 'right'
                                    }));
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.loadUnitPriceHistoryList().done(function () { return dfd.resolve(null); });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.goToB = function () {
                                    nts.uk.ui.windows.setShared('code', this.unitPriceHistoryModel().unitPriceCode());
                                    nts.uk.ui.windows.sub.modal('/view/qmm/007/b/index.xhtml', { title: '会社一律金額 の 登録 > 履歴の追加', dialogClass: 'no-close', height: 350, width: 580 });
                                };
                                ScreenModel.prototype.goToC = function () {
                                    nts.uk.ui.windows.setShared('code', this.unitPriceHistoryModel().unitPriceCode());
                                    nts.uk.ui.windows.sub.modal('/view/qmm/007/c/index.xhtml', { title: '会社一律金額 の 登録 > 履歴の編集', dialogClass: 'no-close', height: 410, width: 580 });
                                };
                                ScreenModel.prototype.collectData = function () {
                                    var self = this;
                                    var data = new UnitPriceHistoryDto();
                                    data.unitPriceCode = self.unitPriceHistoryModel().unitPriceCode();
                                    data.unitPriceName = self.unitPriceHistoryModel().unitPriceName();
                                    data.startMonth = self.unitPriceHistoryModel().startMonth();
                                    data.endMonth = self.unitPriceHistoryModel().endMonth();
                                    data.budget = self.unitPriceHistoryModel().budget();
                                    data.fixPaySettingType = self.unitPriceHistoryModel().fixPaySettingType();
                                    data.fixPayAtr = self.unitPriceHistoryModel().fixPayAtr();
                                    data.fixPayAtrMonthly = self.unitPriceHistoryModel().fixPayAtrMonthly();
                                    data.fixPayAtrDayMonth = self.unitPriceHistoryModel().fixPayAtrDayMonth();
                                    data.fixPayAtrDaily = self.unitPriceHistoryModel().fixPayAtrDaily();
                                    data.fixPayAtrHourly = self.unitPriceHistoryModel().fixPayAtrHourly();
                                    data.memo = self.unitPriceHistoryModel().memo();
                                    return data;
                                };
                                ScreenModel.prototype.save = function () {
                                    var self = this;
                                    if (self.isNewMode()) {
                                        a.service.create(self.collectData());
                                    }
                                    else {
                                        a.service.update(self.collectData());
                                    }
                                };
                                ScreenModel.prototype.remove = function () {
                                    var self = this;
                                    a.service.remove(self.collectData().id);
                                };
                                ScreenModel.prototype.enableNewMode = function () {
                                    var self = this;
                                    self.isNewMode(true);
                                    self.unitPriceHistoryModel(new UnitPriceHistoryModel());
                                };
                                ScreenModel.prototype.loadUnitPriceDetail = function () {
                                    var self = this;
                                    a.service.getUnitPriceHistoryDetail(self.selectedId()).done(function (data) {
                                        console.log(data);
                                        self.unitPriceHistoryModel().id = data.id;
                                        self.unitPriceHistoryModel().unitPriceCode(data.unitPriceCode);
                                        self.unitPriceHistoryModel().unitPriceName(data.unitPriceName);
                                        self.unitPriceHistoryModel().startMonth(data.startMonth);
                                        self.unitPriceHistoryModel().budget(data.budget);
                                        self.unitPriceHistoryModel().fixPaySettingType(data.fixPaySettingType);
                                        self.unitPriceHistoryModel().fixPayAtr(data.fixPayAtr);
                                        self.unitPriceHistoryModel().fixPayAtrMonthly(data.fixPayAtrMonthly);
                                        self.unitPriceHistoryModel().fixPayAtrDayMonth(data.fixPayAtrDayMonth);
                                        self.unitPriceHistoryModel().fixPayAtrDaily(data.fixPayAtrDaily);
                                        self.unitPriceHistoryModel().fixPayAtrHourly(data.fixPayAtrHourly);
                                    });
                                };
                                ScreenModel.prototype.loadUnitPriceHistoryList = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    a.service.getUnitPriceHistoryList().done(function (data) {
                                        self.historyList(data);
                                        dfd.resolve(null);
                                    });
                                    return dfd.promise();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var UnitPriceHistoryModel = (function () {
                                function UnitPriceHistoryModel() {
                                    this.unitPriceCode = ko.observable('');
                                    this.unitPriceName = ko.observable('');
                                    this.startMonth = ko.observable('');
                                    this.endMonth = ko.observable('（平成29年01月） ~');
                                    this.budget = ko.observable(0);
                                    this.fixPaySettingType = ko.observable('Company');
                                    this.fixPayAtr = ko.observable('NotApply');
                                    this.fixPayAtrMonthly = ko.observable('NotApply');
                                    this.fixPayAtrDayMonth = ko.observable('NotApply');
                                    this.fixPayAtrDaily = ko.observable('NotApply');
                                    this.fixPayAtrHourly = ko.observable('NotApply');
                                    this.memo = ko.observable('');
                                }
                                return UnitPriceHistoryModel;
                            }());
                            viewmodel.UnitPriceHistoryModel = UnitPriceHistoryModel;
                        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
                    })(a = qmm007.a || (qmm007.a = {}));
                })(qmm007 = view.qmm007 || (view.qmm007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
