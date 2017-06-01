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
        itemCode: KnockoutObservable<string>;
        categoryAtr: KnockoutObservable<number>;
        itemAbName: KnockoutObservable<string>;
        checkUseHighError: KnockoutObservable<boolean> = ko.observable(false);
        errRangeHigh: KnockoutObservable<number> = ko.observable(0);
        checkUseLowError: KnockoutObservable<boolean> = ko.observable(false);
        errRangeLow: KnockoutObservable<number> = ko.observable(0);
        checkUseHighAlam: KnockoutObservable<boolean> = ko.observable(false);
        alamRangeHigh: KnockoutObservable<number> = ko.observable(0);
        checkUseLowAlam: KnockoutObservable<boolean> = ko.observable(false);
        alamRangeLow: KnockoutObservable<number> = ko.observable(0);
        commuteAtr: KnockoutObservable<number> = ko.observable(0);
        calculationMethod: KnockoutObservable<number> = ko.observable(0);
        distributeSet: KnockoutObservable<number> = ko.observable(0);
        distributeWay: KnockoutObservable<number> = ko.observable(0);
        personalWageCode: KnockoutObservable<string> = ko.observable('');
        sumScopeAtr: KnockoutObservable<number> = ko.observable(0);
        taxAtr: KnockoutObservable<number> = ko.observable(0);
        constructor(itemCode: string, categoryAtr: number, itemAbName: string, checkUseHighError: boolean, errRangeHigh: number, checkUseLowError: boolean, errRangeLow: number,
            checkUseHighAlam: boolean, alamRangeHigh: number, checkUseLowAlam: boolean, alamRangeLow: number, commuteAtr: number, calculationMethod: number, distributeSet: number, distributeWay: number,
            personalWageCode: string, sumScopeAtr: number, taxAtr: number
        ) {
            this.itemCode = ko.observable(itemCode);
            this.categoryAtr = ko.observable(categoryAtr);
            this.itemAbName = ko.observable(itemAbName);
            this.checkUseHighError = ko.observable(checkUseHighError);
            this.errRangeHigh = ko.observable(errRangeHigh);
            this.checkUseLowError = ko.observable(checkUseLowError);
            this.errRangeLow = ko.observable(errRangeLow);
            this.checkUseHighAlam = ko.observable(checkUseHighAlam);
            this.alamRangeHigh = ko.observable(alamRangeHigh);
            this.checkUseLowAlam = ko.observable(checkUseLowAlam);
            this.alamRangeLow = ko.observable(alamRangeLow);
            this.commuteAtr = ko.observable(commuteAtr);
            this.calculationMethod = ko.observable(calculationMethod);
            this.distributeSet = ko.observable(distributeSet);
            this.distributeWay = ko.observable(distributeWay);
            this.personalWageCode = ko.observable(personalWageCode);
            this.sumScopeAtr = ko.observable(sumScopeAtr);
            this.taxAtr = ko.observable(taxAtr);
        }
    }

    export class ListBox {
        itemList: KnockoutObservableArray<any>;
        itemName: KnockoutObservable<any>;
        selectedCode: KnockoutObservable<any> = ko.observable('');
        selectedName: KnockoutObservable<any>;
        isEnable: KnockoutObservable<any>;
        selectedCodes: KnockoutObservableArray<any>;
        listItemDto: Array<service.model.ItemMasterDto>;
        screenMode: qmm019.f.ScreenModel;

        constructor(screenMode: qmm019.f.ScreenModel, listItemDto, currentItemCode, isUpdate, stmtCode, historyId, categoryAtr, isNotYetSave, objectNotYetSave) {
            var self = this;
            // set list item dto
            self.listItemDto = listItemDto;
            self.itemName = ko.observable('');
            self.screenMode = screenMode;
            //subcribe list box's change
            if (isUpdate == true) {
                if (isNotYetSave) {
                    self.selectedCode(currentItemCode);
                    self.setLayoutData(self.createItemDtoTypeKo(objectNotYetSave));
                } else {
                    self.selectedCode(currentItemCode);
                    service.getLayoutMasterDetail(stmtCode,
                        historyId, categoryAtr, currentItemCode).done(function(data) {
                            if (data != null) {
                                self.setLayoutData(self.createItemDto(data));
                            } else {
                                let itemDto: any = _.find(self.listItemDto, function(item) {
                                    return item.itemCode === currentItemCode;
                                });
                                service.getItem(itemDto.categoryAtr, itemDto.itemCode).done(function(res) {
                                    self.setLayoutData(self.createItemDtoWithFindResult(itemDto, res));
                                });
                            }
                        });
                }
            }
            self.selectedCode.subscribe(function(codeChange) {
                var item: service.model.ItemMasterDto = self.getItemDtoSelected(codeChange);
                self.changeSubItemData(item);
            });
            self.isEnable = ko.observable(true);
            self.selectedCodes = ko.observableArray([]);

            // bind list item dto to list item model
            self.itemList = ko.observableArray([]);
            _.forEach(self.listItemDto, function(item) {
                self.itemList.push(new ItemModel(item.itemCode, item.itemAbName));
            });
            self.selectedName = ko.computed(function() {
                if (self.selectedCode() !== '') {
                    var item = _.find(self.itemList(), function(item) {
                        return item.id == self.selectedCode();
                    });
                    return item.name;
                } else {
                    return '';
                }
            }).extend({ deferred: true });;


        }
        changeSubItemData(itemMaster: service.model.ItemMasterDto) {
            let self = this;
            switch (itemMaster.categoryAtr) {
                case 0:
                    self.screenMode.salaryItemScreen.changeSubItemData(itemMaster);
                    break;
                case 1:
                    self.screenMode.deductItemScreen.changeSubItemData(itemMaster);
                    break;
                case 2:
                    self.screenMode.attendItemScreen.changeSubItemData(itemMaster);
                    break;
                default:
                    self.setLayoutData(self.createItemDtoEmpty(itemMaster));
            }
        }
        createItemDtoTypeKo(item) {
            if (item) {
                return {
                    companyCode: ko.observable(null),
                    itemCode: ko.observable(item.itemCode()),
                    itemAbName: ko.observable(item.itemAbName()),
                    categoryAtr: ko.observable(item.categoryAtr()),
                    checkUseHighError: ko.observable(item.isUseHighError() == 1),
                    errRangeHigh: ko.observable(item.errRangeHigh()),
                    checkUseLowError: ko.observable(item.isUseLowError() == 1),
                    errRangeLow: ko.observable(item.errRangeLow()),
                    checkUseHighAlam: ko.observable(item.isUseHighAlam() == 1),
                    alamRangeHigh: ko.observable(item.alamRangeHigh()),
                    checkUseLowAlam: ko.observable(item.isUseLowAlam() == 1),
                    alamRangeLow: ko.observable(item.alamRangeLow()),
                    commuteAtr: ko.observable(item.commuteAtr()),
                    calculationMethod: ko.observable(item.calculationMethod()),
                    distributeSet: ko.observable(item.distributeSet()),
                    distributeWay: ko.observable(item.distributeWay()),
                    personalWageCode: ko.observable(item.personalWageCode()),
                    sumScopeAtr: ko.observable(item.sumScopeAtr()),
                    taxAtr: ko.observable(item.taxAtr)
                };
            }
        }
        createItemDto(item) {
            return {
                companyCode: ko.observable(null),
                itemCode: ko.observable(item.itemCode),
                itemAbName: ko.observable(item.itemAbName),
                categoryAtr: ko.observable(item.categoryAtr),
                checkUseHighError: ko.observable(item.isUseHighError == 1),
                errRangeHigh: ko.observable(item.errRangeHigh),
                checkUseLowError: ko.observable(item.isUseLowError == 1),
                errRangeLow: ko.observable(item.errRangeLow),
                formulaCode: ko.observable(item.formulaCode),
                wageTableCode: ko.observable(item.wageTableCode),
                checkUseHighAlam: ko.observable(item.isUseHighAlam == 1),
                alamRangeHigh: ko.observable(item.alamRangeHigh),
                checkUseLowAlam: ko.observable(item.isUseLowAlam == 1),
                alamRangeLow: ko.observable(item.alamRangeLow),
                commuteAtr: ko.observable(item.commuteAtr),
                calculationMethod: ko.observable(item.calculationMethod),
                distributeSet: ko.observable(item.distributeSet),
                distributeWay: ko.observable(item.distributeWay),
                personalWageCode: ko.observable(item.personalWageCode),
                sumScopeAtr: ko.observable(item.sumScopeAtr),
                taxAtr: ko.observable(item.taxAtr)
            };
        }
        createItemDtoWithFindResult(item, res) {
            let self = this;
            if (res) {
                return {
                    companyCode: ko.observable(null),
                    itemCode: ko.observable(item.itemCode),
                    itemAbName: ko.observable(item.itemAbName),
                    categoryAtr: ko.observable(item.categoryAtr),
                    checkUseHighError: ko.observable(res.errRangeHighAtr == 1),
                    errRangeHigh: ko.observable(res.errRangeHigh),
                    checkUseLowError: ko.observable(res.errRangeLowAtr == 1),
                    errRangeLow: ko.observable(res.errRangeLow),
                    checkUseHighAlam: ko.observable(res.alRangeHighAtr == 1),
                    alamRangeHigh: ko.observable(res.alRangeHigh),
                    checkUseLowAlam: ko.observable(res.alRangeLowAtr == 1),
                    alamRangeLow: ko.observable(res.alRangeLow),
                    commuteAtr: ko.observable(res.commuteAtr),
                    calculationMethod: ko.observable(res.calculationMethod),
                    distributeSet: ko.observable(res.distributeSet),
                    distributeWay: ko.observable(res.distributeWay),
                    personalWageCode: ko.observable(res.personalWageCode),
                    sumScopeAtr: ko.observable(res.sumScopeAtr),
                    taxAtr: ko.observable(res.taxAtr)
                };
            }
            return self.createItemDtoEmpty(item);
        }
        createItemDtoEmpty(item) {
            return {
                companyCode: ko.observable(null),
                itemCode: ko.observable(item.itemCode),
                itemAbName: ko.observable(item.itemAbName),
                categoryAtr: ko.observable(item.categoryAtr),
                checkUseHighError: ko.observable(false),
                errRangeHigh: ko.observable('0'),
                checkUseLowError: ko.observable(false),
                errRangeLow: ko.observable('0'),
                checkUseHighAlam: ko.observable(false),
                alamRangeHigh: ko.observable('0'),
                checkUseLowAlam: ko.observable(false),
                alamRangeLow: ko.observable('0'),
                commuteAtr: ko.observable(0),
                calculationMethod: ko.observable(0),
                distributeSet: ko.observable(0),
                distributeWay: ko.observable(0),
                personalWageCode: ko.observable('00'),
                sumScopeAtr: ko.observable(0),
                taxAtr: ko.observable(0)
            };
        }
        setLayoutData(itemDto) {
            let self = this;
            if (itemDto) {
                switch (itemDto.categoryAtr()) {
                    case 0:
                        self.screenMode.salaryItemScreen.setItemDtoSelected(itemDto);
                        break;
                    case 1:
                        self.screenMode.deductItemScreen.setItemDtoSelected(itemDto);
                        break;
                    case 2:
                        self.screenMode.attendItemScreen.setItemDtoSelected(itemDto);
                        break;
                    case 3:
                        self.screenMode.articleItemScreen.setItemDtoSelected(itemDto);
                        break;
                }
            }
        }
        getItemDtoSelected(codeChange): service.model.ItemMasterDto {
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
        enable: KnockoutObservable<Boolean>;
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
            self.enable = ko.observable(true);
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
            self.selectedRuleCode = ko.observable(0);
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
        paramStmtCode: string;
        paramHistoryId: string;
        taxAtr: number;
        itemAtr: KnockoutObservable<string> = ko.observable('支給項目');
        queueSearchResult: Array<ItemModel> = [];
        textSearch: string = "";

        paramIsNotYetSave: boolean;
        paramObjectNotYetSave: any;
        screenModel: qmm019.f.ScreenModel;
        constructor(screenModel: qmm019.f.ScreenModel, data) {
            var self = this;
            self.paramItemCode = data.itemCode;
            self.paramCategoryAtr = ko.observable(data.categoryId);
            self.paramIsUpdate = data.isUpdate;
            self.paramStmtCode = data.stmtCode;
            self.paramHistoryId = data.historyId;
            self.paramIsNotYetSave = data.isNotYetSave;
            self.paramObjectNotYetSave = data.objectNotYetSave;
            self.taxAtr = data.taxAtr;
            self.screenModel = screenModel;
            if (self.paramCategoryAtr() === 0) {
                self.itemAtr("支給項目");
            } else if (self.paramCategoryAtr() === 1) {
                self.itemAtr('控除項目');
            } else if (self.paramCategoryAtr() === 2) {
                self.itemAtr('勤怠項目');
            } else if (self.paramCategoryAtr() === 3) {
                self.itemAtr(' 記事項目');
            }


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
            qmm019.f.service.getItemsByCategory(self.paramCategoryAtr()).done(function(data1) {
                if (data1 !== null) {
                    self.listItemDto = data1;
                    self.listBox = ko.observable(new ListBox(self.screenModel, self.listItemDto, self.paramItemCode,
                        self.paramIsUpdate, self.paramStmtCode, self.paramHistoryId, self.paramCategoryAtr(), self.paramIsNotYetSave, self.paramObjectNotYetSave));
                }
                else {
                    self.listItemDto = ko.observableArray();
                }
                dfd.resolve();
            }).fail(function(res) {

            });
            return dfd.promise();
        }

        searchItem() {
            var self = this;
            var textSearch = $("#INP_001").val().trim();

            if (textSearch.length === 0) {
                nts.uk.ui.dialog.alert("コード/名称が入力されていません。");
            } else {
                if (self.textSearch !== textSearch) {
                    let searchResult = _.filter(self.listBox().itemList(), function(item) {
                        return _.includes(item.id, textSearch) || _.includes(item.name, textSearch);
                    });
                    self.queueSearchResult = [];
                    for (let item of searchResult) {
                        self.queueSearchResult.push(item);
                    }
                    self.textSearch = textSearch;
                }
                if (self.queueSearchResult.length === 0) {
                    nts.uk.ui.dialog.alert("対象データがありません。");
                } else {
                    let firstResult: ItemModel = _.first(self.queueSearchResult);
                    self.listBox().selectedCode(firstResult.id);
                    self.queueSearchResult.shift();
                    self.queueSearchResult.push(firstResult);
                }
            }
        }


        registerItemName() {
            let self = this;
            nts.uk.ui.windows.setShared('isDialog', true);
            nts.uk.ui.windows.sub.modal('/view/qmm/012/b/index.xhtml', { dialogClass: 'no-close', title: '項目名の登録', width: 1280, height: 690 }).onClosed(function(): any {
                self.start();
            });
        }
        getItemDtoSelected(): ItemDto {
            let self = this;
            let itemDto;
            switch (self.paramCategoryAtr()) {
                case 0:
                    itemDto = self.screenModel.salaryItemScreen.getItemDto();
                    break;
                case 1:
                    itemDto = self.screenModel.deductItemScreen.getItemDto();
                    break;
                case 2:
                    itemDto = self.screenModel.attendItemScreen.getItemDto();
                    break;
                default:
                    let itemMaster: service.model.ItemMasterDto = self.listBox().getItemDtoSelected(self.listBox().selectedCode());
                    itemDto = self.screenModel.attendItemScreen.createDefaltItemDto(itemMaster);
            }
            return itemDto;
        }
        returnBackData() {
            var self = this;
            var itemSelected = ko.mapping.toJS(self.getItemDtoSelected());
            var isUseHighError = null;
            var errRangeHigh = null;
            var isUseLowError = null;
            var errRangeLow = null;
            var isUseHighAlam = null;
            var alamRangeHigh = null;
            var isUseLowAlam = null;
            var alamRangeLow = null;
            if (self.paramCategoryAtr() == 0 || self.paramCategoryAtr() == 1 || self.paramCategoryAtr() == 2) {
                isUseHighError = itemSelected.checkUseHighError;
                errRangeHigh = itemSelected.errRangeHigh;
                isUseLowError = itemSelected.checkUseLowError;
                errRangeLow = itemSelected.errRangeLow;
                isUseHighAlam = itemSelected.checkUseHighAlam;
                alamRangeHigh = itemSelected.alamRangeHigh;
                isUseLowAlam = itemSelected.checkUseLowAlam;
                alamRangeLow = itemSelected.alamRangeLow;

                //入力値の整合チェックを行う
                //エラー上限値<アラーム上限値<アラーム下限値<エラー下限値　の場合
                if (isUseHighError && isUseHighAlam
                    && (+errRangeHigh < +alamRangeHigh)) {
                    alert('範囲の指定が正しくありません。');
                    $('#INP_002').focus();
                    return false;
                }
                //エラー上限値 < エラー下限値 || エラー上限値 = エラー下限値
                if (isUseHighError && isUseLowError
                    && (+errRangeHigh < +errRangeLow || +errRangeHigh == +errRangeLow)) {
                    alert('範囲の指定が正しくありません。');
                    $('#INP_002').focus();
                    return false;
                }
                if (isUseLowError && isUseLowAlam
                    && +alamRangeLow < +errRangeLow) {
                    alert('範囲の指定が正しくありません。');
                    $('#INP_003').focus();
                    return false;
                }
                //アラーム上限値 < アラーム下限値 || アラーム上限値 = アラーム下限値
                if (isUseHighAlam && isUseLowAlam
                    && (+alamRangeHigh < +alamRangeLow || +alamRangeHigh == +alamRangeLow)) {
                    alert('範囲の指定が正しくありません。');
                    $('#INP_004').focus();
                    return false;
                }
            }
            nts.uk.ui.windows.setShared('itemResult', itemSelected);
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
            if (self.comboBoxCalcMethod().selectedCode() == 1) {
                nts.uk.ui.windows.getSelf().setHeight(670);
                return true;
            } else {
                nts.uk.ui.windows.getSelf().setHeight(610);
                return false;
            }
        }

        setItemDisplay() {
            var self = this;

        }
    }


}