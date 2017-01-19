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
                            var UnitPriceHistoryModel = a.service.model.UnitPriceHistoryModel;
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
                                            self.loadUnitPriceDetail(id);
                                        }
                                    });
                                    self.isContractSettingEnabled = ko.observable(false);
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
                                        a.service.create(a.service.collectData(self.unitPriceHistoryModel()));
                                    }
                                    else {
                                        a.service.update(a.service.collectData(self.unitPriceHistoryModel()));
                                    }
                                };
                                ScreenModel.prototype.remove = function () {
                                    var self = this;
                                    a.service.remove(self.unitPriceHistoryModel().id);
                                };
                                ScreenModel.prototype.enableNewMode = function () {
                                    var self = this;
                                    self.clearUnitPriceDetail();
                                    self.isNewMode(true);
                                };
                                ScreenModel.prototype.clearUnitPriceDetail = function () {
                                    var model = this.unitPriceHistoryModel();
                                    model.id = '';
                                    model.unitPriceCode('');
                                    model.unitPriceName('');
                                    model.startMonth('2017/01');
                                    model.budget(0);
                                    model.fixPaySettingType('Company');
                                    model.fixPayAtr('NotApply');
                                    model.fixPayAtrMonthly('NotApply');
                                    model.fixPayAtrDayMonth('NotApply');
                                    model.fixPayAtrDaily('NotApply');
                                    model.fixPayAtrHourly('NotApply');
                                };
                                ScreenModel.prototype.loadUnitPriceDetail = function (id) {
                                    var self = this;
                                    a.service.getUnitPriceHistoryDetail(id).done(function (data) {
                                        console.log(data);
                                        var model = self.unitPriceHistoryModel();
                                        model.id = data.id;
                                        model.unitPriceCode(data.unitPriceCode);
                                        model.unitPriceName(data.unitPriceName);
                                        model.startMonth(data.startMonth);
                                        model.budget(data.budget);
                                        model.fixPaySettingType(data.fixPaySettingType);
                                        model.fixPayAtr(data.fixPayAtr);
                                        model.fixPayAtrMonthly(data.fixPayAtrMonthly);
                                        model.fixPayAtrDayMonth(data.fixPayAtrDayMonth);
                                        model.fixPayAtrDaily(data.fixPayAtrDaily);
                                        model.fixPayAtrHourly(data.fixPayAtrHourly);
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
                        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
                    })(a = qmm007.a || (qmm007.a = {}));
                })(qmm007 = view.qmm007 || (view.qmm007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
