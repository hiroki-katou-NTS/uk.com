module qmm019.f.viewmodel {

    export class ItemModel {
        id: any;
        name: any;
        constructor(id, name) {
            var self = this;
            this.id = id;
            this.name = name;
        }
    }

    //get the model from app
    export class ItemDto {
        companyCode: KnockoutObservable<String>;
        itemCode: KnockoutObservable<String>;
        categoryAtr: KnockoutObservable<number>;
        itemAbName: KnockoutObservable<String>;
        checkUseHighError: KnockoutObservable<boolean>;
        errRangeHigh: KnockoutObservable<number>;
        checkUseLowError: KnockoutObservable<boolean>;
        errRangeLow: KnockoutObservable<number>;
        checkUseHighAlam: KnockoutObservable<boolean>;
        alamRangeHigh: KnockoutObservable<number>;
        checkUseLowAlam: KnockoutObservable<boolean>;
        alamRangeLow: KnockoutObservable<number>;
        commuteAtr: KnockoutObservable<number> = ko.observable(0);
        calculationMethod: KnockoutObservable<number> = ko.observable(0);
        distributeSet: KnockoutObservable<number> = ko.observable(0);
        distributeWay: KnockoutObservable<number> = ko.observable(0);
        personalWageCode: KnockoutObservable<string> = ko.observable('');
        sumScopeAtr: KnockoutObservable<number> = ko.observable(0);
    }

    export class ListBox {
        itemList: KnockoutObservableArray<any>;
        itemName: KnockoutObservable<any>;
        currentCode: KnockoutObservable<any>;
        selectedCode: KnockoutObservable<any>;
        selectedName: KnockoutObservable<any>;
        isEnable: KnockoutObservable<any>;
        selectedCodes: KnockoutObservableArray<any>;
        itemDtoSelected: KnockoutObservable<any> = ko.observable();
        listItemDto: Array<ItemDto>;

        constructor(listItemDto, currentItemCode, isUpdate, stmtCode, startYm, categoryAtr) {
            var self = this;
            // set list item dto
            self.listItemDto = listItemDto;
            self.itemName = ko.observable('');
            if (isUpdate == false) {
                self.selectedCode = ko.observable(self.listItemDto[0].itemCode);
                // get item selected
                var item = ko.mapping.fromJS(self.getItemDtoSelected(self.selectedCode()));
                self.itemDtoSelected(ko.observable(item));
            } else {
                self.selectedCode = ko.observable(currentItemCode);
                service.getLayoutMasterDetail(stmtCode, 
                                startYm, categoryAtr, currentItemCode).done(function(data) {
                    var item = {
                        companyCode: ko.observable(null),
                        itemCode: ko.observable(data.itemCode),
                        itemAbName: ko.observable(self.selectedName()),
                        categoryAtr: ko.observable(data.categoryAtr),
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
            _.forEach(self.listItemDto, function(item) {
                self.itemList.push(new ItemModel(item.itemCode, item.itemAbName));
            });
            self.selectedName = ko.computed(function() {
                var item = _.find(self.itemList(), function(item) {
                    return item.id == self.selectedCode();
                });
                return item.name;
            });

            //subcribe list box's change
            self.selectedCode.subscribe(function(codeChange) {
                var item = ko.mapping.fromJS(self.getItemDtoSelected(codeChange));
                self.itemDtoSelected(ko.observable(item));
            });
        }

        getItemDtoSelected(codeChange): ItemDto {
            var self = this;
            var item = _.find(self.listItemDto, function(item) {
                return item.itemCode == codeChange;
            });
            return item;
        }

    }


    export class ComboBox {
        itemName: KnockoutObservable<any>;
        selectedCode: KnockoutObservable<number>;
        itemList: KnockoutObservableArray<any>;

        constructor(itemList) {
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
    }

    export class SwitchButton {
        distributeSet: KnockoutObservableArray<any>;
        selectedRuleCode: KnockoutObservable<any>;
        constructor() {
            var self = this;
            self.distributeSet = ko.observableArray([
                { code: 0, name: '按分しない' },
                { code: 1, name: '按分する' },
                { code: 2, name: '月１回支給' }
            ]);
            self.selectedRuleCode = ko.observable('1');
        }
    }

    export class ScreenModel {
        listBox: KnockoutObservable<ListBox>;
        comboBoxSumScopeAtr: KnockoutObservable<ComboBox>;
        comboBoxCalcMethod: KnockoutObservable<ComboBox>;
        comboBoxDistributeWay: KnockoutObservable<ComboBox>;
        comboBoxCommutingClassification: KnockoutObservable<ComboBox>;
        switchButton: KnockoutObservable<SwitchButton>;
        itemCode: KnockoutObservable<String>;
        paramItemCode: String;
        paramCategoryAtr: KnockoutObservable<number>;
        wageCode: KnockoutObservable<string> = ko.observable('');
        wageName: KnockoutObservable<string> = ko.observable('');
        paramIsUpdate: boolean;
        listItemDto: any;
        paramStmtCode: String;
        paramStartYm: number;

        constructor(data) {
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
            } else if (self.paramCategoryAtr() == 1) {
                self.comboBoxCalcMethod = ko.observable(new ComboBox(itemListCalcMethod1));
            }
            self.comboBoxDistributeWay = ko.observable(new ComboBox(itemListDistributeWay));
            self.comboBoxCommutingClassification = ko.observable(new ComboBox(itemListCommutingClassification));
            self.switchButton = ko.observable(new SwitchButton());
        }

        isCategoryAtrEqual0(): boolean {
            var self = this;
            return self.paramCategoryAtr() == 0;
        }

        isCategoryAtrEqual1(): boolean {
            var self = this;
            return self.paramCategoryAtr() == 1;
        }

        isCategoryAtrEqual2(): boolean {
            var self = this;
            return self.paramCategoryAtr() == 2;
        }

        isCategoryAtrEqual3(): boolean {
            var self = this;
            return self.paramCategoryAtr() == 3;
        }

        checkDisplayComboBoxCalcMethod(): boolean {
            var self = this;
            return (self.isCategoryAtrEqual0() || self.isCategoryAtrEqual1());
        }

        checkDisplayComboBoxCommutingClassification(): boolean {
            return false;
        }

        start(): JQueryPromise<any> {
            var self = this;
            // Page load dfd.
            var dfd = $.Deferred();

            // Resolve start page dfd after load all data.
            $.when(qmm019.f.service.getItemsByCategory(self.paramCategoryAtr())).done(function(data) {
                if (data !== null) {
                    self.listItemDto = data;
                    
                    var item = {};
                    if (self.paramIsUpdate == false) {
//                        self.listBox = ko.observable(new ListBox(self.listItemDto, self.paramItemCode, 
//                            self.paramIsUpdate, self.paramStmtCode, self.paramStartYm, self.paramCategoryAtr(), item));
                    } else {
                        service.getLayoutMasterDetail(self.paramStmtCode, 
                                    self.paramStartYm, self.paramCategoryAtr(), self.paramItemCode).done(function(data) {
                            item = {
                                companyCode: ko.observable(null),
                                itemCode: ko.observable(data.itemCode),
                                categoryAtr: ko.observable(data.categoryAtr),
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
                                        
                            self.comboBoxSumScopeAtr().selectedCode(data.sumScopeAtr);
                            self.comboBoxCalcMethod().selectedCode(data.calculationMethod);
                            self.comboBoxDistributeWay().selectedCode(data.distributeWay);
                            self.switchButton().selectedRuleCode(data.distributeSet);
                        });
                    }
                    self.listBox = ko.observable(new ListBox(self.listItemDto, self.paramItemCode, 
                        self.paramIsUpdate, self.paramStmtCode, self.paramStartYm, self.paramCategoryAtr()));
                }
                else {
                    self.listItemDto = ko.observableArray();
                }
                dfd.resolve();
            }).fail(function(res) {

            });

            return dfd.promise();
        }

        openHDialog() {
            var self = this;
            nts.uk.ui.windows.setShared('categoryAtr', self.paramCategoryAtr());
            nts.uk.ui.windows.sub.modal('/view/qmm/019/h/index.xhtml',{title: '明細レイアウトの作成＞個人金額コードの選択'}).onClosed(() => {
                self.wageCode(nts.uk.ui.windows.getShared('selectedCode'));
                self.wageName(nts.uk.ui.windows.getShared('selectedName'));
                return this;
            });
        }

        returnBackData() {
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
                itemAbName: self.listBox().selectedName(),
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
        }

        close() {
            nts.uk.ui.windows.setShared('itemResult', undefined);
            nts.uk.ui.windows.close();
        }

        checkManualInput(): boolean {
            var self = this;
            return self.comboBoxCalcMethod().selectedCode() == 0;
        }

        checkPersonalInformationReference(): boolean {
            var self = this;
            return self.comboBoxCalcMethod().selectedCode() == 1;
        }
    }


}