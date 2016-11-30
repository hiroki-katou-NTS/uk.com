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
                function ItemDto(companyCode, itemCode, categoryAtr, itemAbName, isUseHighError, errRangeHigh, isUseLowError, errRangeLow, isUseHighAlam, alamRangeHigh, isUseLowAlam, alamRangeLow) {
                    var self = this;
                    self.companyCode = ko.observable(companyCode);
                    self.itemCode = ko.observable(itemCode);
                    self.categoryAtr = ko.observable(categoryAtr);
                    self.itemAbName = ko.observable(itemAbName);
                    self.isUseHighError = ko.observable(isUseHighError);
                    self.errRangeHigh = ko.observable(errRangeHigh);
                    self.isUseLowError = ko.observable(isUseLowError);
                    self.errRangeLow = ko.observable(errRangeLow);
                    self.isUseHighAlam = ko.observable(isUseHighAlam);
                    self.alamRangeHigh = ko.observable(alamRangeHigh);
                    self.isUseLowAlam = ko.observable(isUseLowAlam);
                    self.alamRangeLow = ko.observable(alamRangeLow);
                }
                return ItemDto;
            }());
            viewmodel.ItemDto = ItemDto;
            var ListBox = (function () {
                function ListBox(listItemDto, currentItemCode) {
                    var self = this;
                    self.itemName = ko.observable('');
                    self.selectedCode = ko.observable(currentItemCode);
                    self.currentCode = ko.observable(0);
                    self.isEnable = ko.observable(true);
                    self.selectedCodes = ko.observableArray([]);
                    // set list item dto
                    self.listItemDto = listItemDto;
                    // bind list item dto to list item model
                    self.itemList = ko.observableArray([]);
                    _.forEach(self.listItemDto, function (item) {
                        self.itemList.push(new ItemModel(item.itemCode, item.itemAbName));
                    });
                    // get item selected
                    self.itemDtoSelected = ko.computed(function () {
                        return _.find(self.listItemDto, function (item) {
                            return item.itemCode == self.selectedCode().toString();
                        });
                    });
                    //self.itemList = ko.observableArray([
                    //    new ItemModel('001', '名前１'),
                    //    new ItemModel('002', '名前2'),
                    //    new ItemModel('003', '名前3'),
                    //    new ItemModel('004', '名前4'),
                    //    new ItemModel('005', '名前5'),
                    //    new ItemModel('006', '名前6'),
                    //    new ItemModel('008', '名前8'),
                    //     new ItemModel('009', '名前9'),
                    //      new ItemModel('010', '名前10'),
                    //      new ItemModel('011', '名前1１'),
                    //     new ItemModel('012', '名前12'),
                    //       new ItemModel('013', '名前13'),
                    //     new ItemModel('014', '名前14'),
                    //      new ItemModel('015', '名前15'),
                    //  ]);
                    //subcribe list box's change
                    self.selectedCode.subscribe(function (codeChange) {
                    });
                }
                return ListBox;
            }());
            viewmodel.ListBox = ListBox;
            var ComboBox = (function () {
                function ComboBox(itemList) {
                    var self = this;
                    self.itemList = itemList;
                    self.itemName = ko.observable('');
                    self.currentCode = ko.observable(1);
                    self.selectedCode = ko.observable(null);
                }
                ComboBox.prototype.isManualInput = function () {
                    var self = this;
                    return self.selectedCode == ko.observable(0);
                };
                ComboBox.prototype.isPersonalinformationReference = function () {
                    var self = this;
                    return self.selectedCode == ko.observable(1);
                };
                return ComboBox;
            }());
            viewmodel.ComboBox = ComboBox;
            var SwitchButton = (function () {
                function SwitchButton() {
                    var self = this;
                    self.distributeSet = ko.observableArray([
                        { code: '0', name: '按分しない' },
                        { code: '1', name: '按分する' },
                        { code: '2', name: '月１回支給' }
                    ]);
                    self.selectedRuleCode = ko.observable('1');
                }
                return SwitchButton;
            }());
            viewmodel.SwitchButton = SwitchButton;
            var ScreenModel = (function () {
                function ScreenModel(data) {
                    var self = this;
                    self.paramItemCode = data.itemCode;
                    self.paramCategoryAtr = data.categoryId;
                    self.isUpdate = data.isUpdate;
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
                    self.comboBoxSumScopeAtr = new ComboBox(itemListSumScopeAtr);
                    self.comboBoxCalcMethod0 = new ComboBox(itemListCalcMethod0);
                    self.comboBoxCalcMethod1 = new ComboBox(itemListCalcMethod1);
                    self.comboBoxDistributeWay = new ComboBox(itemListDistributeWay);
                    self.comboBoxCommutingClassification = new ComboBox(itemListCommutingClassification);
                    self.switchButton = new SwitchButton();
                }
                ScreenModel.prototype.isCategoryAtrEqual0 = function () {
                    var self = this;
                    return self.paramCategoryAtr == 0;
                };
                ScreenModel.prototype.isCategoryAtrEqual1 = function () {
                    var self = this;
                    return self.paramCategoryAtr == 1;
                };
                ScreenModel.prototype.isCategoryAtrEqual2 = function () {
                    var self = this;
                    return self.paramCategoryAtr == 2;
                };
                ScreenModel.prototype.isCategoryAtrEqual3 = function () {
                    var self = this;
                    return self.paramCategoryAtr == 3;
                };
                ScreenModel.prototype.start = function () {
                    var self = this;
                    // Page load dfd.
                    var dfd = $.Deferred();
                    // Resolve start page dfd after load all data.
                    $.when(qmm019.f.service.getItemsByCategory(self.paramCategoryAtr)).done(function (data) {
                        self.listItemDto = data;
                        self.listBox = new ListBox(self.listItemDto, self.paramItemCode);
                        dfd.resolve();
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.openHDialog = function () {
                    var _this = this;
                    nts.uk.ui.windows.sub.modal('/view/qmm/019/h/index.xhtml').onClosed(function () {
                        var selectedCode = nts.uk.ui.windows.getShared('selectedCode');
                        alert(selectedCode);
                        return _this;
                    });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = f.viewmodel || (f.viewmodel = {}));
    })(f = qmm019.f || (qmm019.f = {}));
})(qmm019 || (qmm019 = {}));
