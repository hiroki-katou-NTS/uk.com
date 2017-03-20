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
                    var j;
                    (function (j) {
                        var SalaryAggregateItemInDto = j.service.model.SalaryAggregateItemInDto;
                        var SalaryAggregateItemSaveDto = j.service.model.SalaryAggregateItemSaveDto;
                        var SalaryItemDto = j.service.model.SalaryItemDto;
                        var TaxDivision = nts.uk.pr.view.qpp007.c.viewmodel.TaxDivision;
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.taxDivisionTab = ko.observableArray([
                                        { id: TaxDivision.PAYMENT, title: '支給集計', content: '#tab-payment', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: TaxDivision.DEDUCTION, title: '控除集計', content: '#tab-deduction', enable: ko.observable(true), visible: ko.observable(true) },
                                    ]);
                                    self.aggregateItemTab = ko.observableArray([
                                        { id: '001', title: '集計項目1', content: '#aggregate1', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: '002', title: '集計項目2', content: '#aggregate2', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: '003', title: '集計項目3', content: '#aggregate3', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: '004', title: '集計項目4', content: '#aggregate4', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: '005', title: '集計項目5', content: '#aggregate5', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: '006', title: '集計項目6', content: '#aggregate6', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: '007', title: '集計項目7', content: '#aggregate7', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: '008', title: '集計項目8', content: '#aggregate8', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: '009', title: '集計項目9', content: '#aggregate9', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: '010', title: '集計項目10', content: '#aggregate10', enable: ko.observable(true), visible: ko.observable(true) }
                                    ]);
                                    self.selectedDivision = ko.observable(TaxDivision.PAYMENT);
                                    self.selectedAggregateItem = ko.observable('001');
                                    self.salaryAggregateItemModel = ko.observable(new SalaryAggregateItemModel());
                                    self.columns = ko.observableArray([
                                        { headerText: 'コード', key: 'salaryItemCode', width: 100 },
                                        { headerText: '名称', key: 'salaryItemName', width: 150 }
                                    ]);
                                    self.selectedAggregateItem.subscribe(function (selectedAggregateItem) {
                                        self.onShowDataChange(self.selectedDivision(), selectedAggregateItem);
                                    });
                                    self.selectedDivision.subscribe(function (selectedDivision) {
                                        self.onShowDataChange(selectedDivision, self.selectedAggregateItem());
                                    });
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    return self.initData();
                                };
                                ScreenModel.prototype.initData = function () {
                                    var self = this;
                                    var salaryAggregateItemInDto;
                                    salaryAggregateItemInDto = new SalaryAggregateItemInDto();
                                    salaryAggregateItemInDto.taxDivision = 0;
                                    salaryAggregateItemInDto.aggregateItemCode = self.selectedAggregateItem();
                                    return self.showDataModel(salaryAggregateItemInDto);
                                };
                                ScreenModel.prototype.onShowDataChange = function (selectedDivision, selectedAggregateItem) {
                                    var self = this;
                                    var salaryAggregateItemInDto;
                                    salaryAggregateItemInDto = new SalaryAggregateItemInDto();
                                    if (selectedDivision === TaxDivision.PAYMENT) {
                                        salaryAggregateItemInDto.taxDivision = 0;
                                    }
                                    else {
                                        salaryAggregateItemInDto.taxDivision = 1;
                                    }
                                    salaryAggregateItemInDto.aggregateItemCode = selectedAggregateItem;
                                    self.showDataModel(salaryAggregateItemInDto);
                                };
                                ScreenModel.prototype.showDataModel = function (salaryAggregateItemInDto) {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    j.service.findSalaryAggregateItem(salaryAggregateItemInDto).done(function (data) {
                                        self.salaryAggregateItemModel().convertDtoToData(data);
                                        var fullItemCodes;
                                        fullItemCodes = [];
                                        for (var i = 1; i < 20; i++) {
                                            var salaryItemDto = new SalaryItemDto();
                                            salaryItemDto.salaryItemCode = '' + i;
                                            salaryItemDto.salaryItemName = '基本給 ' + i;
                                            fullItemCodes.push(salaryItemDto);
                                        }
                                        self.salaryAggregateItemModel().setFullItemCode(fullItemCodes);
                                        dfd.resolve(self);
                                    }).fail(function (error) {
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.closeDialogBtnClicked = function () {
                                    nts.uk.ui.windows.close();
                                };
                                ScreenModel.prototype.saveSalaryAggregateItem = function () {
                                    var self = this;
                                    self.convertModelToDto(0);
                                };
                                ScreenModel.prototype.convertModelToDto = function (taxDivision) {
                                    var self = this;
                                    var salaryAggregateItemSaveDto;
                                    salaryAggregateItemSaveDto = new SalaryAggregateItemSaveDto();
                                    salaryAggregateItemSaveDto.salaryAggregateItemCode =
                                        self.salaryAggregateItemModel().salaryAggregateItemCode();
                                    salaryAggregateItemSaveDto.salaryAggregateItemName =
                                        self.salaryAggregateItemModel().salaryAggregateItemName();
                                    salaryAggregateItemSaveDto.subItemCodes = [];
                                    for (var _i = 0, _a = self.salaryAggregateItemModel().subItemCodes(); _i < _a.length; _i++) {
                                        var itemModel = _a[_i];
                                        salaryAggregateItemSaveDto.subItemCodes.push(itemModel);
                                    }
                                    salaryAggregateItemSaveDto.taxDivision = taxDivision;
                                    salaryAggregateItemSaveDto.categoryCode = self.selectedAggregateItem();
                                    console.log(salaryAggregateItemSaveDto);
                                    j.service.saveSalaryAggregateItem(salaryAggregateItemSaveDto).done(function () {
                                    }).fail(function () {
                                    });
                                    return salaryAggregateItemSaveDto;
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var SalaryItemModel = (function () {
                                function SalaryItemModel() {
                                }
                                SalaryItemModel.prototype.convertDtoToData = function (salaryItemDto) {
                                    this.salaryItemCode = salaryItemDto.salaryItemCode;
                                    this.salaryItemName = salaryItemDto.salaryItemName;
                                };
                                return SalaryItemModel;
                            }());
                            viewmodel.SalaryItemModel = SalaryItemModel;
                            var SalaryAggregateItemModel = (function () {
                                function SalaryAggregateItemModel() {
                                    this.salaryAggregateItemCode = ko.observable('');
                                    this.salaryAggregateItemName = ko.observable('');
                                    this.subItemCodes = ko.observableArray([]);
                                    this.fullItemCodes = ko.observableArray([]);
                                }
                                SalaryAggregateItemModel.prototype.convertDtoToData = function (salaryAggregateItemFindDto) {
                                    if (this.salaryAggregateItemCode) {
                                        this.salaryAggregateItemCode(salaryAggregateItemFindDto.salaryAggregateItemCode);
                                    }
                                    else {
                                        this.salaryAggregateItemCode = ko.observable(salaryAggregateItemFindDto.salaryAggregateItemCode);
                                    }
                                    if (this.salaryAggregateItemName) {
                                        this.salaryAggregateItemName(salaryAggregateItemFindDto.salaryAggregateItemName);
                                    }
                                    else {
                                        this.salaryAggregateItemName = ko.observable(salaryAggregateItemFindDto.salaryAggregateItemName);
                                    }
                                    if (salaryAggregateItemFindDto.subItemCodes) {
                                        this.subItemCodes([]);
                                        var subItemCodes;
                                        subItemCodes = [];
                                        for (var _i = 0, _a = salaryAggregateItemFindDto.subItemCodes; _i < _a.length; _i++) {
                                            var item = _a[_i];
                                            var salaryItemModel;
                                            salaryItemModel = new SalaryItemModel();
                                            salaryItemModel.convertDtoToData(item);
                                            subItemCodes.push(salaryItemModel);
                                        }
                                        this.subItemCodes(subItemCodes);
                                    }
                                    else {
                                        this.subItemCodes([]);
                                    }
                                };
                                SalaryAggregateItemModel.prototype.setFullItemCode = function (fullItemCodes) {
                                    if (fullItemCodes && fullItemCodes.length > 0) {
                                        this.fullItemCodes([]);
                                        var fullItemCodesModel;
                                        fullItemCodesModel = [];
                                        for (var _i = 0, fullItemCodes_1 = fullItemCodes; _i < fullItemCodes_1.length; _i++) {
                                            var item = fullItemCodes_1[_i];
                                            var check;
                                            check = 1;
                                            for (var _a = 0, _b = this.subItemCodes(); _a < _b.length; _a++) {
                                                var itemSub = _b[_a];
                                                if (itemSub.salaryItemCode == item.salaryItemCode) {
                                                    check = 0;
                                                    break;
                                                }
                                            }
                                            if (check == 1) {
                                                var salaryItemModel;
                                                salaryItemModel = new SalaryItemModel();
                                                salaryItemModel.convertDtoToData(item);
                                                fullItemCodesModel.push(salaryItemModel);
                                            }
                                        }
                                        this.fullItemCodes(fullItemCodesModel);
                                    }
                                    else {
                                        this.fullItemCodes([]);
                                    }
                                };
                                return SalaryAggregateItemModel;
                            }());
                            viewmodel.SalaryAggregateItemModel = SalaryAggregateItemModel;
                        })(viewmodel = j.viewmodel || (j.viewmodel = {}));
                    })(j = qpp007.j || (qpp007.j = {}));
                })(qpp007 = view.qpp007 || (view.qpp007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qpp007.j.vm.js.map