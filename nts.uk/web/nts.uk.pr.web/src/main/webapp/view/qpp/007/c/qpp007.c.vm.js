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
                                    self.isNewMode = ko.observable(false);
                                    self.outputSettings = ko.observableArray([]);
                                    self.outputSettingSelectedCode = ko.observable('');
                                    self.outputSettingDetailModel = ko.observable(new OutputSettingDetailModel());
                                    self.reportItems = ko.observableArray([]);
                                    self.reportItemSelected = ko.observable('');
                                    for (var i = 1; i < 30; i++) {
                                        this.outputSettings.push(new OutputSettingHeader('00' + i, '基本給' + i));
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
                                    self.outputSettingDetailModel.subscribe(function (data) {
                                        self.reloadReportItems();
                                        data.reloadReportItems = self.reloadReportItems.bind(self);
                                    });
                                    self.outputSettingSelectedCode.subscribe(function (id) {
                                        self.onSelectOutputSetting(id);
                                    });
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.loadAllOutputSetting().done(function () {
                                        self.isLoading(false);
                                        dfd.resolve();
                                    });
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
                                        var categoryName = setting.categoryName;
                                        setting.outputItems().forEach(function (item) {
                                            reportItemList.push(new ReportItem(item.code, item.name, categoryName, item.isAggregateItem));
                                        });
                                    });
                                    self.reportItems(reportItemList);
                                };
                                ScreenModel.prototype.collectData = function () {
                                    var self = this;
                                    var model = self.outputSettingDetailModel();
                                    var data = new OutputSettingDto();
                                    data.code = model.settingCode();
                                    data.name = model.settingName();
                                    var settings = new Array();
                                    model.categorySettings().forEach(function (item) {
                                        settings.push(new CategorySettingDto(SalaryCategory.PAYMENT, item.outputItems()));
                                    });
                                    data.categorySettings = settings;
                                    return data;
                                };
                                ScreenModel.prototype.save = function () {
                                    var self = this;
                                    $('#inpCode').ntsError('clear');
                                    $('#inpName').ntsError('clear');
                                    var hasError = false;
                                    if (self.outputSettingDetailModel().settingCode() == '') {
                                        $('#inpCode').ntsError('set', '未入力エラー');
                                        hasError = true;
                                    }
                                    if (self.outputSettingDetailModel().settingName() == '') {
                                        $('#inpName').ntsError('set', '未入力エラー');
                                        hasError = true;
                                    }
                                    if (hasError) {
                                        return;
                                    }
                                    var data = self.collectData();
                                    if (self.isNewMode()) {
                                        data.createMode = true;
                                    }
                                    else {
                                        data.createMode = false;
                                    }
                                    c.service.save(data).done(function () {
                                        self.isNewMode(false);
                                        self.loadAllOutputSetting();
                                    });
                                };
                                ScreenModel.prototype.remove = function () {
                                    if (this.outputSettingSelectedCode) {
                                        c.service.remove(this.outputSettingSelectedCode());
                                    }
                                };
                                ScreenModel.prototype.commonSettingBtnClick = function () {
                                    nts.uk.ui.windows.sub.modal('/view/qpp/007/j/index.xhtml', { title: '集計項目の設定', dialogClass: 'no-close' });
                                };
                                ScreenModel.prototype.newModeBtnClick = function () {
                                    var self = this;
                                    self.outputSettingDetailModel(new OutputSettingDetailModel());
                                    self.outputSettingSelectedCode('');
                                    self.isNewMode(true);
                                };
                                ScreenModel.prototype.onSelectOutputSetting = function (id) {
                                    var self = this;
                                    $('.save-error').ntsError('clear');
                                    self.isNewMode(false);
                                    self.loadOutputSettingDetail(id).done(function () {
                                    });
                                };
                                ScreenModel.prototype.loadAllOutputSetting = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    c.service.findAllOutputSettings().done(function (data) {
                                        self.outputSettings(data);
                                        dfd.resolve();
                                    }).fail(function (res) {
                                        nts.uk.ui.dialog.alert(res);
                                        dfd.reject();
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.loadOutputSettingDetail = function (code) {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    c.service.findOutputSettingDetail(code).done(function (data) {
                                        self.outputSettingDetailModel(new OutputSettingDetailModel(data));
                                        dfd.resolve();
                                    }).fail(function (res) {
                                        nts.uk.ui.dialog.alert(res);
                                        dfd.reject();
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.loadAggregateItems = function () {
                                    var dfd = $.Deferred();
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.loadMasterItems = function () {
                                    var dfd = $.Deferred();
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.close = function () {
                                    nts.uk.ui.windows.close();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var OutputSettingHeader = (function () {
                                function OutputSettingHeader(code, name) {
                                    this.code = code;
                                    this.name = name;
                                }
                                return OutputSettingHeader;
                            }());
                            viewmodel.OutputSettingHeader = OutputSettingHeader;
                            var OutputSettingDto = (function () {
                                function OutputSettingDto() {
                                }
                                return OutputSettingDto;
                            }());
                            viewmodel.OutputSettingDto = OutputSettingDto;
                            var OutputSettingDetailModel = (function () {
                                function OutputSettingDetailModel(outputSetting) {
                                    this.settingCode = ko.observable(outputSetting != undefined ? outputSetting.code : '');
                                    this.settingName = ko.observable(outputSetting != undefined ? outputSetting.name : '');
                                    var settings = [];
                                    if (outputSetting == undefined) {
                                        settings = this.convertCategorySettings();
                                    }
                                    else {
                                        settings = this.convertCategorySettings(outputSetting.categorySettings);
                                    }
                                    console.log(settings);
                                    this.categorySettings = ko.observableArray(settings);
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
                                OutputSettingDetailModel.prototype.convertCategorySettings = function (categorySettings) {
                                    var settings = [];
                                    var test;
                                    if (categorySettings != undefined && categorySettings.length > 0) {
                                        test = categorySettings[0];
                                    }
                                    settings[0] = new CategorySetting(SalaryCategory.PAYMENT, test);
                                    settings[1] = new CategorySetting(SalaryCategory.DEDUCTION, test);
                                    settings[2] = new CategorySetting(SalaryCategory.ATTENDANCE, test);
                                    settings[3] = new CategorySetting(SalaryCategory.ARTICLE_OTHERS, test);
                                    return settings;
                                };
                                return OutputSettingDetailModel;
                            }());
                            viewmodel.OutputSettingDetailModel = OutputSettingDetailModel;
                            var CategorySettingDto = (function () {
                                function CategorySettingDto(category, outputItems) {
                                    this.category = category;
                                    this.outputItems = outputItems;
                                }
                                return CategorySettingDto;
                            }());
                            viewmodel.CategorySettingDto = CategorySettingDto;
                            var CategorySetting = (function () {
                                function CategorySetting(categoryName, categorySetting) {
                                    var self = this;
                                    self.categoryName = categoryName;
                                    self.aggregateItems = ko.observableArray([]);
                                    self.aggregateItemsSelected = ko.observableArray([]);
                                    self.masterItems = ko.observableArray([]);
                                    self.masterItemsSelected = ko.observableArray([]);
                                    self.outputItems = ko.observableArray(categorySetting != undefined ? categorySetting.outputItems : []);
                                    self.outputItemSelected = ko.observable(null);
                                    self.outputItemsSelected = ko.observableArray([]);
                                    for (var i = 1; i < 15; i++) {
                                        this.aggregateItems.push({ code: '00' + i, name: '基本給' + i, subItems: [], taxDivision: 'Payment', value: i });
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
                                            subItems: [],
                                            taxDivision: TaxDivision.PAYMENT,
                                            value: 5
                                        });
                                        return;
                                    }
                                    self.masterItems.push({
                                        code: selectedItem.code,
                                        name: selectedItem.name,
                                        paymentType: PaymentType.SALARY,
                                        taxDivision: TaxDivision.DEDUCTION
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
                                SalaryCategory.DEDUCTION = 'Deduction';
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
                                }
                                SalaryOutputDistinction.HOURLY = 'Hourly';
                                SalaryOutputDistinction.MINUTLY = 'Minutely';
                                return SalaryOutputDistinction;
                            }());
                            viewmodel.SalaryOutputDistinction = SalaryOutputDistinction;
                            var PaymentType = (function () {
                                function PaymentType() {
                                }
                                PaymentType.SALARY = 'Salary';
                                PaymentType.BONUS = 'Bonus';
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
//# sourceMappingURL=qpp007.c.vm.js.map