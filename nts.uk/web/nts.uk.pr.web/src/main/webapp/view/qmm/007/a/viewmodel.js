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
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.isNewMode = ko.observable(true);
                                    self.unitPriceHistoryModel = ko.observable(new UnitPriceHistoryModel());
                                    self.historyList = ko.observableArray();
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
                                        a.service.create(a.service.collectData(self.unitPriceHistoryModel()));
                                    }
                                    else {
                                        a.service.update(a.service.collectData(self.unitPriceHistoryModel()));
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
                                            var model = self.unitPriceHistoryModel();
                                            model.id = data.id;
                                            model.version = data.version;
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
                                            model.memo(data.memo);
                                        });
                                    }
                                };
                                ScreenModel.prototype.loadUnitPriceHistoryList = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    a.service.getUnitPriceHistoryList().done(function (data) {
                                        self.historyList(self.convertToTreeList(data));
                                        dfd.resolve();
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.convertToTreeList = function (unitPriceHistoryList) {
                                    var groupByCode = {};
                                    unitPriceHistoryList.forEach(function (item) {
                                        var c = item.unitPriceCode;
                                        groupByCode[c] = item;
                                    });
                                    var arr = Object.keys(groupByCode).map(function (key) { return groupByCode[key]; });
                                    var parentNodes = arr.map(function (item) { return new UnitPriceHistoryNode(item.id, item.unitPriceCode, item.unitPriceName, '', '', false, []); });
                                    var childNodes = unitPriceHistoryList.map(function (item) { return new UnitPriceHistoryNode(item.id, item.unitPriceCode, item.unitPriceName, item.startMonth, item.endMonth, true); });
                                    var treeList = parentNodes.map(function (parent) {
                                        childNodes.forEach(function (child) { return parent.childs.push(parent.unitPriceCode == child.unitPriceCode ? child : ''); });
                                        return parent;
                                    });
                                    return treeList;
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var UnitPriceHistoryModel = (function () {
                                function UnitPriceHistoryModel() {
                                    this.id = '';
                                    this.version = 0;
                                    this.unitPriceCode = ko.observable('');
                                    this.unitPriceName = ko.observable('');
                                    this.startMonth = ko.observable('');
                                    this.endMonth = ko.observable('（平成29年01月） ~');
                                    this.budget = ko.observable(null);
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
                            var UnitPriceHistoryNode = (function () {
                                function UnitPriceHistoryNode(id, unitPriceCode, unitPriceName, startMonth, endMonth, isChild, childs) {
                                    var self = this;
                                    self.isChild = isChild;
                                    self.unitPriceCode = unitPriceCode;
                                    self.unitPriceName = unitPriceName;
                                    self.startMonth = startMonth;
                                    self.endMonth = endMonth;
                                    self.id = self.isChild == true ? id : id + id;
                                    self.childs = childs;
                                    self.nodeText = self.isChild == true ? self.startMonth + ' ~ ' + self.endMonth : self.unitPriceCode + ' ' + self.unitPriceName;
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
