var qmm019;
(function (qmm019) {
    var f;
    (function (f) {
        var viewmodel;
        (function (viewmodel) {
            var ItemModel = (function () {
                function ItemModel(id, name) {
                    var self = this;
                    this.id = id;
                    this.name = name;
                }
                return ItemModel;
            }());
            viewmodel.ItemModel = ItemModel;
            //get the model from app
            var ItemDto = (function () {
                function ItemDto() {
                    this.commuteAtr = ko.observable(0);
                    this.calculationMethod = ko.observable(0);
                    this.distributeSet = ko.observable(0);
                    this.distributeWay = ko.observable(0);
                    this.personalWageCode = ko.observable('');
                    this.sumScopeAtr = ko.observable(0);
                }
                return ItemDto;
            }());
            viewmodel.ItemDto = ItemDto;
            var ListBox = (function () {
                function ListBox(listItemDto, currentItemCode, isUpdate, stmtCode, startYm, categoryAtr) {
                    this.itemDtoSelected = ko.observable();
                    var self = this;
                    // set list item dto
                    self.listItemDto = listItemDto;
                    self.itemName = ko.observable('');
                    if (isUpdate == false) {
                        self.selectedCode = ko.observable(self.listItemDto[0].itemCode);
                        // get item selected
                        var item = ko.mapping.fromJS(self.getItemDtoSelected(self.selectedCode()));
                        self.itemDtoSelected(ko.observable(item));
                    }
                    else {
                        self.selectedCode = ko.observable(currentItemCode);
                        f.service.getLayoutMasterDetail(stmtCode, startYm, categoryAtr, currentItemCode).done(function (data) {
                            var item = {
                                companyCode: ko.observable(null),
                                itemCode: ko.observable(data.itemCode),
                                categoryAtr: ko.observable(data.categoryAtr),
                                itemAbName: ko.observable(data.itemAbName),
                                checkUseHighError: ko.observable(data.isUseHighError == 1),
                                errRangeHigh: ko.observable(data.errRangeHigh),
                                checkUseLowError: ko.observable(data.isUseLowError == 1),
                                errRangeLow: ko.observable(data.errRangeLow),
                                checkUseHighAlam: ko.observable(data.isUseHighAlam == 1),
                                alamRangeHigh: ko.observable(data.alamRangeHigh),
                                checkUseLowAlam: ko.observable(data.isUseLowAlam == 1),
                                alamRangeLow: ko.observable(data.alamRangeLow),
                                commuteAtr: ko.observable(data.commuteAtr),
                                calculationMethod: ko.observable(data.calculationMethod),
                                distributeSet: ko.observable(data.distributeSet),
                                distributeWay: ko.observable(data.distributeWay),
                                personalWageCode: ko.observable(data.personalWageCode),
                                sumScopeAtr: ko.observable(data.sumScopeAtr)
                            };
                            self.itemDtoSelected(ko.observable(item));
                        });
                    }
                    self.currentCode = ko.observable(0);
                    self.isEnable = ko.observable(true);
                    self.selectedCodes = ko.observableArray([]);
                    // bind list item dto to list item model
                    self.itemList = ko.observableArray([]);
                    _.forEach(self.listItemDto, function (item) {
                        self.itemList.push(new ItemModel(item.itemCode, item.itemAbName));
                    });
                    self.selectedName = ko.computed(function () {
                        var item = _.find(self.itemList(), function (item) {
                            return item.id == self.selectedCode();
                        });
                        return item.name;
                    });
                    //subcribe list box's change
                    self.selectedCode.subscribe(function (codeChange) {
                        var item = ko.mapping.fromJS(self.getItemDtoSelected(codeChange));
                        self.itemDtoSelected(ko.observable(item));
                    });
                }
                ListBox.prototype.getItemDtoSelected = function (codeChange) {
                    var self = this;
                    var item = _.find(self.listItemDto, function (item) {
                        return item.itemCode == codeChange;
                    });
                    return item;
                };
                return ListBox;
            }());
            viewmodel.ListBox = ListBox;
            var ComboBox = (function () {
                function ComboBox(itemList) {
                    var self = this;
                    if (itemList !== null) {
                        self.itemList = itemList;
                    }
                    else {
                        self.itemList = ko.observableArray();
                    }
                    self.itemName = ko.observable('');
                    self.selectedCode = ko.observable(null);
                }
                return ComboBox;
            }());
            viewmodel.ComboBox = ComboBox;
            var SwitchButton = (function () {
                function SwitchButton() {
                    var self = this;
                    self.distributeSet = ko.observableArray([
                        { code: 0, name: '按分しない' },
                        { code: 1, name: '按分する' },
                        { code: 2, name: '月１回支給' }
                    ]);
                    self.selectedRuleCode = ko.observable('1');
                }
                return SwitchButton;
            }());
            viewmodel.SwitchButton = SwitchButton;
            var ScreenModel = (function () {
                function ScreenModel(data) {
                    this.wageCode = ko.observable('');
                    this.wageName = ko.observable('');
                    var self = this;
                    self.paramItemCode = data.itemCode;
                    self.paramCategoryAtr = ko.observable(data.categoryId);
                    self.paramIsUpdate = data.isUpdate;
                    self.paramStmtCode = data.stmtCode;
                    self.paramStartYm = data.startYm;
                    var itemListSumScopeAtr = ko.observableArray([
                        new ItemModel(0, '対象外'),
                        new ItemModel(1, '対象内'),
                        new ItemModel(2, '対象外（現物）'),
                        new ItemModel(3, '対象内（現物）')
                    ]);
                    var itemListCalcMethod0 = ko.observableArray([
                        new ItemModel(0, '手入力'),
                        new ItemModel(1, '個人情報'),
                        new ItemModel(2, '計算式'),
                        new ItemModel(3, '賃金テーブル'),
                        new ItemModel(4, '共通金額')
                    ]);
                    var itemListCalcMethod1 = ko.observableArray([
                        new ItemModel(0, '手入力'),
                        new ItemModel(1, '個人情報'),
                        new ItemModel(2, '計算式'),
                        new ItemModel(3, '賃金テーブル'),
                        new ItemModel(4, '共通金額'),
                        new ItemModel(5, '支給相殺 ')
                    ]);
                    var itemListDistributeWay = ko.observableArray([
                        new ItemModel(0, '割合で計算'),
                        new ItemModel(1, '日数控除'),
                        new ItemModel(2, '計算式')
                    ]);
                    var itemListCommutingClassification = ko.observableArray([
                        new ItemModel(0, '交通機関'),
                        new ItemModel(1, '交通用具')
                    ]);
                    self.comboBoxSumScopeAtr = ko.observable(new ComboBox(itemListSumScopeAtr));
                    if (self.paramCategoryAtr() == 0) {
                        self.comboBoxCalcMethod = ko.observable(new ComboBox(itemListCalcMethod0));
                    }
                    else if (self.paramCategoryAtr() == 1) {
                        self.comboBoxCalcMethod = ko.observable(new ComboBox(itemListCalcMethod1));
                    }
                    self.comboBoxDistributeWay = ko.observable(new ComboBox(itemListDistributeWay));
                    self.comboBoxCommutingClassification = ko.observable(new ComboBox(itemListCommutingClassification));
                    self.switchButton = ko.observable(new SwitchButton());
                }
                ScreenModel.prototype.isCategoryAtrEqual0 = function () {
                    var self = this;
                    return self.paramCategoryAtr() == 0;
                };
                ScreenModel.prototype.isCategoryAtrEqual1 = function () {
                    var self = this;
                    return self.paramCategoryAtr() == 1;
                };
                ScreenModel.prototype.isCategoryAtrEqual2 = function () {
                    var self = this;
                    return self.paramCategoryAtr() == 2;
                };
                ScreenModel.prototype.isCategoryAtrEqual3 = function () {
                    var self = this;
                    return self.paramCategoryAtr() == 3;
                };
                ScreenModel.prototype.checkDisplayComboBoxCalcMethod = function () {
                    var self = this;
                    return (self.isCategoryAtrEqual0() || self.isCategoryAtrEqual1());
                };
                ScreenModel.prototype.checkDisplayComboBoxCommutingClassification = function () {
                    return false;
                };
                ScreenModel.prototype.start = function () {
                    var self = this;
                    // Page load dfd.
                    var dfd = $.Deferred();
                    // Resolve start page dfd after load all data.
                    $.when(qmm019.f.service.getItemsByCategory(self.paramCategoryAtr())).done(function (data) {
                        if (data !== null) {
                            self.listItemDto = data;
                            self.listBox = ko.observable(new ListBox(self.listItemDto, self.paramItemCode, self.paramIsUpdate, self.paramStmtCode, self.paramStartYm, self.paramCategoryAtr));
                        }
                        else {
                            self.listItemDto = ko.observableArray();
                        }
                        dfd.resolve();
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.openHDialog = function () {
                    var _this = this;
                    var self = this;
                    nts.uk.ui.windows.setShared('categoryAtr', self.paramCategoryAtr());
                    nts.uk.ui.windows.sub.modal('/view/qmm/019/h/index.xhtml').onClosed(function () {
                        self.wageCode(nts.uk.ui.windows.getShared('selectedCode'));
                        self.wageName(nts.uk.ui.windows.getShared('selectedName'));
                        return _this;
                    });
                };
                ScreenModel.prototype.returnBackData = function () {
                    var self = this;
                    var itemSelected = self.listBox().itemDtoSelected();
                    var sumScopeAtr = null;
                    var commuteAtr = null;
                    var calculationMethod = null;
                    var distributeSet = null;
                    var distributeWay = null;
                    var personalWageCode = '';
                    var isUseHighError = null;
                    var errRangeHigh = null;
                    var isUseLowError = null;
                    var errRangeLow = null;
                    var isUseHighAlam = null;
                    var alamRangeHigh = null;
                    var isUseLowAlam = null;
                    var alamRangeLow = null;
                    if (self.paramCategoryAtr() == 0 || self.paramCategoryAtr() == 1) {
                        sumScopeAtr = self.comboBoxSumScopeAtr().selectedCode();
                        calculationMethod = self.comboBoxCalcMethod().selectedCode();
                        distributeSet = self.switchButton().selectedRuleCode();
                        distributeWay = self.comboBoxDistributeWay().selectedCode();
                    }
                    if (calculationMethod == 0 && self.paramCategoryAtr() == 0) {
                        commuteAtr = self.comboBoxCommutingClassification().selectedCode();
                    }
                    if (calculationMethod == 1 && (self.paramCategoryAtr() == 0 || self.paramCategoryAtr() == 1)) {
                        personalWageCode = self.wageCode();
                    }
                    if (self.paramCategoryAtr() == 0 || self.paramCategoryAtr() == 1 || self.paramCategoryAtr() == 2) {
                        isUseHighError = itemSelected().checkUseHighError();
                        errRangeHigh = itemSelected().errRangeHigh();
                        isUseLowError = itemSelected().checkUseLowError();
                        errRangeLow = itemSelected().errRangeLow();
                        isUseHighAlam = itemSelected().checkUseHighAlam();
                        alamRangeHigh = itemSelected().alamRangeHigh();
                        isUseLowAlam = itemSelected().checkUseLowAlam();
                        alamRangeLow = itemSelected().alamRangeLow();
                    }
                    var data = {
                        itemCode: itemSelected().itemCode(),
                        itemAbName: itemSelected().itemAbName(),
                        categoryAtr: self.paramCategoryAtr(),
                        sumScopeAtr: sumScopeAtr,
                        commuteAtr: commuteAtr,
                        calculationMethod: calculationMethod,
                        distributeSet: distributeSet,
                        distributeWay: distributeWay,
                        personalWageCode: personalWageCode,
                        setOffItemCode: '',
                        isUseHighError: isUseHighError,
                        errRangeHigh: errRangeHigh,
                        isUseLowError: isUseLowError,
                        errRangeLow: errRangeLow,
                        isUseHighAlam: isUseHighAlam,
                        alamRangeHigh: alamRangeHigh,
                        isUseLowAlam: isUseLowAlam,
                        alamRangeLow: alamRangeLow
                    };
                    nts.uk.ui.windows.setShared('itemResult', data);
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.close = function () {
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.checkManualInput = function () {
                    var self = this;
                    return self.comboBoxCalcMethod().selectedCode() == 0;
                };
                ScreenModel.prototype.checkPersonalInformationReference = function () {
                    var self = this;
                    return self.comboBoxCalcMethod().selectedCode() == 1;
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = f.viewmodel || (f.viewmodel = {}));
    })(f = qmm019.f || (qmm019.f = {}));
})(qmm019 || (qmm019 = {}));
