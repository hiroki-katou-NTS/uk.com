var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qpp007;
                (function (qpp007) {
                    var c;
                    (function (c) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.isLoading = ko.observable(true);
                                    self.outputSettings = ko.observableArray([]);
                                    self.outputSettingSelectedCode = ko.observable('');
                                    self.outputSettingDetailModel = ko.observable(new OutputSettingDetailModel());
                                    self.reportItems = ko.observableArray([]);
                                    self.reportItemSelected = ko.observable('');
                                    self.outputSettingSelectedCode;
                                    for (var i = 1; i < 30; i++) {
                                        this.outputSettings.push(new ItemModel('00' + i, '基本給' + i, "name " + i, i % 3 === 0));
                                    }
                                    this.reportItemColumns = ko.observableArray([
                                        {
                                            headerText: '集約', prop: 'isAggregate', width: 40,
                                            formatter: function (isAggregate) {
                                                if (isAggregate == 'true') {
                                                    return '<div class="halign-center"><i class="icon icon-dot"></i></div>';
                                                }
                                                return '';
                                            }
                                        },
                                        { headerText: 'コード', prop: 'code', width: 100 },
                                        { headerText: '名称', prop: 'name', width: 100 },
                                    ]);
                                    self.outputSettingDetailModel().reloadReportItems = self.reloadReportItems.bind(self);
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    dfd.resolve();
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.reloadReportItems = function () {
                                    var self = this;
                                    var data = self.outputSettingDetailModel();
                                    if (!data || data.categorySettings().length == 0) {
                                        self.reportItems([]);
                                        return;
                                    }
                                    var reportItemList = [];
                                    data.categorySettings().forEach(function (setting) {
                                        var categoryName = setting.category;
                                        setting.outputItems().forEach(function (item) {
                                            reportItemList.push(new ReportItem(item.code, item.name, categoryName, item.isAggregateItem));
                                        });
                                    });
                                    self.reportItems(reportItemList);
                                };
                                ScreenModel.prototype.commonSettingBtnClick = function () {
                                    nts.uk.ui.windows.sub.modal('/view/qpp/007/j/index.xhtml', { title: '集計項目の設定', dialogClass: 'no-close' });
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var ItemModel = (function () {
                                function ItemModel(code, name, description, deletable) {
                                    this.code = code;
                                    this.name = name;
                                    this.description = description;
                                    this.deletable = deletable;
                                }
                                return ItemModel;
                            }());
                            viewmodel.ItemModel = ItemModel;
                            var OutputSettingDetailModel = (function () {
                                function OutputSettingDetailModel() {
                                    this.settingCode = ko.observable('code');
                                    this.settingName = ko.observable('name 123');
                                    this.categorySettings = ko.observableArray([]);
                                    this.categorySettings.push(new CategorySetting());
                                    this.categorySettings.push(new CategorySetting());
                                    this.categorySettings.push(new CategorySetting());
                                    this.categorySettings.push(new CategorySetting());
                                    for (var i = 1; i < 15; i++) {
                                        this.categorySettings()[2].outputItems.push({
                                            code: i,
                                            name: 'name' + i,
                                            isAggregateItem: false,
                                            removable: true
                                        });
                                        this.categorySettings()[3].outputItems.push({
                                            code: i + 1,
                                            name: 'name' + i + 1,
                                            isAggregateItem: false,
                                            removable: true
                                        });
                                    }
                                    ;
                                    this.categorySettingTabs = ko.observableArray([
                                        { id: SalaryCategory.PAYMENT, title: '支給', content: '#payment', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: SalaryCategory.DEDUCTION, title: '控除', content: '#deduction', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: SalaryCategory.ATTENDANCE, title: '勤怠', content: '#attendance', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: SalaryCategory.ARTICLE_OTHERS, title: '記事・その他', content: '#article-others', enable: ko.observable(true), visible: ko.observable(true) }
                                    ]);
                                    this.selectedCategory = ko.observable(SalaryCategory.PAYMENT);
                                    var self = this;
                                    self.categorySettings().forEach(function (setting) {
                                        setting.outputItems.subscribe(function (newValue) {
                                            self.reloadReportItems();
                                        });
                                    });
                                }
                                OutputSettingDetailModel.prototype.afterRender = function () {
                                    $('.master-table-label').width($('#swap-list-gridArea1').width());
                                    $('.sub-table-label').width($('#swap-list-gridArea2').width());
                                };
                                return OutputSettingDetailModel;
                            }());
                            viewmodel.OutputSettingDetailModel = OutputSettingDetailModel;
                            var CategorySetting = (function () {
                                function CategorySetting() {
                                    var self = this;
                                    self.aggregateItems = ko.observableArray([]);
                                    self.aggregateItemsSelected = ko.observableArray([]);
                                    self.masterItems = ko.observableArray([]);
                                    self.masterItemsSelected = ko.observableArray([]);
                                    self.outputItems = ko.observableArray([]);
                                    self.outputItemSelected = ko.observable(null);
                                    self.outputItemsSelected = ko.observableArray([]);
                                    for (var i = 1; i < 15; i++) {
                                        this.aggregateItems.push({ code: '00' + i, name: '基本給' + i, subsItem: [], taxDivision: 'Payment', value: i });
                                    }
                                    for (var i = 1; i < 15; i++) {
                                        this.masterItems.push({ code: '00' + i, name: '基本給' + i, paymentType: 'Salary', taxDivision: 'Deduction' });
                                    }
                                    this.outputItemColumns = ko.observableArray([
                                        {
                                            headerText: '集約', prop: 'isAggregateItem', width: 40,
                                            formatter: function (data) {
                                                if (data == 'true') {
                                                    return '<div class="halign-center"><i class="icon icon-dot"></i></div>';
                                                }
                                                return '';
                                            }
                                        },
                                        { headerText: 'コード', prop: 'code', width: 50 },
                                        { headerText: '名称', prop: 'name', width: 60 },
                                        {
                                            headerText: '削除', prop: 'code', width: 60,
                                            formatter: function (code) {
                                                return '<div class="halign-center"><button class="icon icon-close" id="' + code + '" >'
                                                    + '</button></div>';
                                            }
                                        }
                                    ]);
                                    ko.bindingHandlers.rended = {
                                        init: function (element, valueAccessor, allBindings, viewModel, bindingContext) { },
                                        update: function (element, valueAccessor, allBindings, viewModel, bindingContext) {
                                            var code = valueAccessor();
                                            viewModel.outputItems().forEach(function (item) {
                                                $('#' + item.code).on('click', function () {
                                                    code(item.code);
                                                    viewModel.remove();
                                                    code(null);
                                                });
                                            });
                                        }
                                    };
                                }
                                CategorySetting.prototype.moveMasterItem = function () {
                                    if (this.masterItemsSelected()[0]) {
                                        var self = this;
                                        var selectedItems = [];
                                        self.masterItemsSelected().forEach(function (selectedCode) {
                                            selectedItems.push(self.masterItems().filter(function (item) {
                                                return selectedCode == item.code;
                                            })[0]);
                                        });
                                        selectedItems.forEach(function (item) {
                                            self.masterItems.remove(item);
                                            self.outputItems.push({
                                                code: item.code,
                                                name: item.name,
                                                isAggregateItem: false,
                                                removable: true
                                            });
                                        });
                                        self.masterItemsSelected([]);
                                    }
                                };
                                CategorySetting.prototype.moveAggregateItem = function () {
                                    if (this.aggregateItemsSelected()[0]) {
                                        var self = this;
                                        var selectedItems = [];
                                        self.aggregateItemsSelected().forEach(function (selectedCode) {
                                            selectedItems.push(self.aggregateItems().filter(function (item) {
                                                return selectedCode == item.code;
                                            })[0]);
                                        });
                                        selectedItems.forEach(function (item) {
                                            self.aggregateItems.remove(item);
                                            self.outputItems.push({
                                                code: item.code,
                                                name: item.name,
                                                isAggregateItem: true,
                                                removable: true
                                            });
                                        });
                                        self.aggregateItemsSelected([]);
                                    }
                                };
                                CategorySetting.prototype.remove = function () {
                                    var self = this;
                                    var selectedItem = self.outputItems().filter(function (item) {
                                        return item.code == self.outputItemSelected();
                                    })[0];
                                    self.outputItems.remove(selectedItem);
                                    if (selectedItem.isAggregateItem) {
                                        self.aggregateItems.push({
                                            code: selectedItem.code,
                                            name: selectedItem.name,
                                            subsItem: [],
                                            taxDivision: 'Payment',
                                            value: 5
                                        });
                                        return;
                                    }
                                    self.masterItems.push({
                                        code: selectedItem.code,
                                        name: selectedItem.name,
                                        paymentType: 'Salary',
                                        taxDivision: 'Deduction'
                                    });
                                };
                                return CategorySetting;
                            }());
                            viewmodel.CategorySetting = CategorySetting;
                            var AggregateItem = (function () {
                                function AggregateItem() {
                                }
                                return AggregateItem;
                            }());
                            viewmodel.AggregateItem = AggregateItem;
                            var MasterItem = (function () {
                                function MasterItem() {
                                }
                                return MasterItem;
                            }());
                            viewmodel.MasterItem = MasterItem;
                            var OutputItem = (function () {
                                function OutputItem() {
                                }
                                return OutputItem;
                            }());
                            viewmodel.OutputItem = OutputItem;
                            var SalaryCategory = (function () {
                                function SalaryCategory() {
                                }
                                SalaryCategory.PAYMENT = 'Payment';
                                SalaryCategory.PAYMENT_TOTAL = 'PaymentTotal';
                                SalaryCategory.DEDUCTION = 'Deduction';
                                SalaryCategory.DEDUCTION_TABULATION = 'DeductionTabulation';
                                SalaryCategory.ATTENDANCE = 'Attendance';
                                SalaryCategory.ARTICLE_OTHERS = 'ArticleOthers';
                                return SalaryCategory;
                            }());
                            viewmodel.SalaryCategory = SalaryCategory;
                            var TaxDivision = (function () {
                                function TaxDivision() {
                                }
                                TaxDivision.PAYMENT = 'Payment';
                                TaxDivision.DEDUCTION = 'Deduction';
                                return TaxDivision;
                            }());
                            viewmodel.TaxDivision = TaxDivision;
                            var SalaryOutputDistinction = (function () {
                                function SalaryOutputDistinction() {
                                    this.HOURLY = 'Hourly';
                                    this.MINUTLY = 'Minutely';
                                }
                                return SalaryOutputDistinction;
                            }());
                            viewmodel.SalaryOutputDistinction = SalaryOutputDistinction;
                            var PaymentType = (function () {
                                function PaymentType() {
                                    this.SALARY = 'Salary';
                                    this.BONUS = 'Bonus';
                                }
                                return PaymentType;
                            }());
                            viewmodel.PaymentType = PaymentType;
                            var ReportItem = (function () {
                                function ReportItem(code, name, categoryName, isAggregate) {
                                    this.code = code;
                                    this.name = name;
                                    this.categoryName = categoryName;
                                    this.isAggregate = isAggregate;
                                }
                                return ReportItem;
                            }());
                            viewmodel.ReportItem = ReportItem;
                        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
                    })(c = qpp007.c || (qpp007.c = {}));
                })(qpp007 = view.qpp007 || (view.qpp007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
