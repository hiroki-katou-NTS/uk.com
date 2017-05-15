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
        checkUseHighError: KnockoutObservable<boolean> = ko.observable(false);
        errRangeHigh: KnockoutObservable<string> = ko.observable('0');
        checkUseLowError: KnockoutObservable<boolean> = ko.observable(false);
        errRangeLow: KnockoutObservable<string> = ko.observable('0');
        checkUseHighAlam: KnockoutObservable<boolean> = ko.observable(false);
        alamRangeHigh: KnockoutObservable<string> = ko.observable('0');
        checkUseLowAlam: KnockoutObservable<boolean> = ko.observable(false);
        alamRangeLow: KnockoutObservable<string> = ko.observable('0');
        commuteAtr: KnockoutObservable<number> = ko.observable(0);
        calculationMethod: KnockoutObservable<number> = ko.observable(0);
        distributeSet: KnockoutObservable<number> = ko.observable(0);
        distributeWay: KnockoutObservable<number> = ko.observable(0);
        personalWageCode: KnockoutObservable<string> = ko.observable('');
        sumScopeAtr: KnockoutObservable<number> = ko.observable(0);
        taxAtr: KnockoutObservable<number> = ko.observable(0);
    }

    export class ListBox {
        itemList: KnockoutObservableArray<any>;
        itemName: KnockoutObservable<any>;
        currentCode: KnockoutObservable<any>;
        selectedCode: KnockoutObservable<any> = ko.observable('');
        selectedName: KnockoutObservable<any>;
        isEnable: KnockoutObservable<any>;
        selectedCodes: KnockoutObservableArray<any>;
        itemDtoSelected: KnockoutObservable<any> = ko.observable(null);
        listItemDto: Array<ItemDto>;
        checkUseHighError: KnockoutObservable<boolean> = ko.observable(false);
        checkUseLowError: KnockoutObservable<boolean> = ko.observable(false);
        checkUseHighAlam: KnockoutObservable<boolean> = ko.observable(false);
        checkUseLowAlam: KnockoutObservable<boolean> = ko.observable(false);

        constructor(listItemDto, currentItemCode, isUpdate, stmtCode, historyId, categoryAtr, isNotYetSave, objectNotYetSave) {
            var self = this;
            // set list item dto
            self.listItemDto = listItemDto;
            self.itemName = ko.observable('');
            //subcribe list box's change
            self.selectedCode.subscribe(function(codeChange) {
                var item: ItemDto = self.getItemDtoSelected(codeChange);
                if (item) {
                    if (categoryAtr > -1 && categoryAtr < 3) {
                        service.getItem(item.categoryAtr, item.itemCode).done(function(res) {
                            self.setLayoutData(self.createItemDtoWithFindResult(item, res));
                        });
                    } else {
                        self.setLayoutData(self.createItemDtoEmpty(item));
                    }
                }
            });

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
            } else {
                self.selectedCode(self.listItemDto[0].itemCode);
            }
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
                personalWageCode: ko.observable('000'),
                sumScopeAtr: ko.observable(0),
                taxAtr: ko.observable(0)
            };
        }
        setLayoutData(item) {
            let self = this;
            self.itemDtoSelected(item);
            self.checkUseHighError(self.itemDtoSelected().checkUseHighError());
            self.checkUseLowError(self.itemDtoSelected().checkUseLowError());
            self.checkUseHighAlam(self.itemDtoSelected().checkUseHighAlam());
            self.checkUseLowAlam(self.itemDtoSelected().checkUseLowAlam());
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
        timeEditorOption: any;
        paramIsNotYetSave: boolean;
        paramObjectNotYetSave: any;

        constructor(data) {
            var self = this;
            self.paramItemCode = data.itemCode;
            self.paramCategoryAtr = ko.observable(data.categoryId);
            self.paramIsUpdate = data.isUpdate;
            self.paramStmtCode = data.stmtCode;
            self.paramHistoryId = data.historyId;
            self.paramIsNotYetSave = data.isNotYetSave;
            self.paramObjectNotYetSave = data.objectNotYetSave;
            self.taxAtr = data.taxAtr;
            if (self.paramCategoryAtr() === 0) {
                self.itemAtr("支給項目");
            } else if (self.paramCategoryAtr() === 1) {
                self.itemAtr('控除項目');
            } else if (self.paramCategoryAtr() === 2) {
                self.itemAtr('勤怠項目');
            } else if (self.paramCategoryAtr() === 3) {
                self.itemAtr(' 記事項目');
            }


            var itemListSumScopeAtr = ko.observableArray([
                new ItemModel(0, '合計対象外'),
                new ItemModel(1, '合計対象内'),
                new ItemModel(2, '合計対象外（現物）'),
                new ItemModel(3, '合計対象内（現物）')
            ]);
            var itemListSumScopeAtr1 = ko.observableArray([
                new ItemModel(0, '合計対象外'),
                new ItemModel(1, '合計対象内')
            ]);
            var itemListCalcMethod0 = ko.observableArray([
                new ItemModel(0, '手入力'),
                new ItemModel(1, '個人情報'),
                new ItemModel(2, '計算式'),
                new ItemModel(3, '賃金テーブル'),
                new ItemModel(4, '共通金額'),
                new ItemModel(9, 'システム計算')
            ]);
            var itemListCalcMethod1 = ko.observableArray([
                new ItemModel(0, '手入力'),
                new ItemModel(1, '個人情報'),
                new ItemModel(2, '計算式'),
                new ItemModel(3, '賃金テーブル'),
                new ItemModel(4, '共通金額'),
                new ItemModel(5, '支給相殺 '),
                new ItemModel(9, 'システム計算')
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
            if (self.paramCategoryAtr() == 0) {
                //計算方法
                self.comboBoxCalcMethod = ko.observable(new ComboBox(itemListCalcMethod0));
                //内訳区分
                //「合計対象内（現物）」と「合計対象外（現物）」は項目区分が「支給項目」の場合のみ表示
                self.comboBoxSumScopeAtr = ko.observable(new ComboBox(itemListSumScopeAtr));
            } else if (self.paramCategoryAtr() == 1) {
                //計算方法
                //6 支給相殺は項目区分が「控除項目」の場合のみ表示する。
                self.comboBoxCalcMethod = ko.observable(new ComboBox(itemListCalcMethod1));
                //内訳区分
                self.comboBoxSumScopeAtr = ko.observable(new ComboBox(itemListSumScopeAtr1));
            }
            self.comboBoxDistributeWay = ko.observable(new ComboBox(itemListDistributeWay));
            self.comboBoxCommutingClassification = ko.observable(new ComboBox(itemListCommutingClassification));
            self.switchButton = ko.observable(new SwitchButton());
            self.timeEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.TimeEditorOption({
                inputFormat: 'time',
                placeholder: "Enter a valid HH:mm",
                width: "",
                textalign: "left"
            }));
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
            if (self.comboBoxCalcMethod !== undefined) {
                self.switchButton().selectedRuleCode.subscribe(function(newValue) {
                    //按分方法: 非活性: 項目区分が「支給項目」 or 「控除項目」＆「按分設定」が「按分しない」の場合
                    if (newValue == 0) {
                        self.comboBoxDistributeWay().enable(false);
                    } else {
                        self.comboBoxDistributeWay().enable(true);
                    }
                });
            }
            // Resolve start page dfd after load all data.
            qmm019.f.service.getItemsByCategory(self.paramCategoryAtr()).done(function(data1) {
                if (data1 !== null) {
                    self.listItemDto = data1;
                    self.listBox = ko.observable(new ListBox(self.listItemDto, self.paramItemCode,
                        self.paramIsUpdate, self.paramStmtCode, self.paramHistoryId, self.paramCategoryAtr(), self.paramIsNotYetSave, self.paramObjectNotYetSave));
                    if (self.paramIsUpdate == false) {
                        dfd.resolve();
                    } else {
                        if (self.paramIsNotYetSave) {
                            if (self.comboBoxSumScopeAtr !== undefined) {
                                self.comboBoxSumScopeAtr().selectedCode(self.paramObjectNotYetSave.sumScopeAtr());
                            }
                            if (self.comboBoxCalcMethod !== undefined) {
                                self.comboBoxCalcMethod().selectedCode(self.paramObjectNotYetSave.calculationMethod());
                            }
                            if (self.comboBoxDistributeWay !== undefined) {
                                self.comboBoxDistributeWay().selectedCode(self.paramObjectNotYetSave.distributeWay());
                            }
                            if (self.switchButton !== undefined) {
                                self.switchButton().selectedRuleCode(self.paramObjectNotYetSave.distributeSet());
                            }
                            if (self.comboBoxCommutingClassification !== undefined) {
                                self.comboBoxCommutingClassification().selectedCode(self.paramObjectNotYetSave.commuteAtr());
                            }
                            self.wageCode(self.paramObjectNotYetSave.personalWageCode());
                            qmm019.f.service.getListPersonalWages(self.paramCategoryAtr()).done(function(lstPersonalWage) {
                                var tmp = _.find(lstPersonalWage, function(personalWage) { return personalWage.personalWageCode == self.paramObjectNotYetSave.personalWageCode() });
                                if (tmp !== undefined) {
                                    self.wageName(tmp.personalWageName);
                                } else {
                                    self.wageName('');
                                }
                                dfd.resolve();
                            });
                        } else {
                            qmm019.f.service.getLayoutMasterDetail(self.paramStmtCode,
                                self.paramHistoryId, self.paramCategoryAtr(), self.paramItemCode).done(function(data) {
                                    if (data != null) {
                                        if (self.comboBoxSumScopeAtr !== undefined) {
                                            self.comboBoxSumScopeAtr().selectedCode(data.sumScopeAtr);
                                        }
                                        if (self.comboBoxCalcMethod !== undefined) {
                                            self.comboBoxCalcMethod().selectedCode(data.calculationMethod);
                                        }
                                        if (self.comboBoxDistributeWay !== undefined) {
                                            self.comboBoxDistributeWay().selectedCode(data.distributeWay);
                                        }
                                        if (self.switchButton !== undefined) {
                                            self.switchButton().selectedRuleCode(data.distributeSet);
                                        }
                                        if (self.comboBoxCommutingClassification !== undefined) {
                                            self.comboBoxCommutingClassification().selectedCode(data.commuteAtr);
                                        }
                                        self.wageCode(data.personalWageCode);
                                        qmm019.f.service.getListPersonalWages(self.paramCategoryAtr()).done(function(lstPersonalWage) {
                                            var tmp = _.find(lstPersonalWage, function(personalWage) { return personalWage.personalWageCode == data.personalWageCode });
                                            if (tmp !== undefined) {
                                                self.wageName(tmp.personalWageName);
                                            } else {
                                                self.wageName('');
                                            }
                                            dfd.resolve();
                                        });
                                    } else {
                                        dfd.resolve();
                                    }
                                });
                        }
                    }
                }
                else {
                    self.listItemDto = ko.observableArray();
                }
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

        openHDialog() {
            var self = this;
            nts.uk.ui.windows.setShared('categoryAtr', self.paramCategoryAtr());
            nts.uk.ui.windows.sub.modal('/view/qmm/019/h/index.xhtml', { title: '明細レイアウトの作成＞個人金額コードの選択', dialogClass: 'no-close' }).onClosed(() => {
                self.wageCode(nts.uk.ui.windows.getShared('selectedCode'));
                self.wageName(nts.uk.ui.windows.getShared('selectedName'));
                return this;
            });
        }
        registerItemName() {
            nts.uk.ui.windows.setShared('isDialog', true);
            nts.uk.ui.windows.sub.modal('/view/qmm/012/b/index.xhtml', { dialogClass: 'no-close', title: '項目名の登録', width: 1280, height: 690 }).onClosed(function(): any {
            });
        }
        returnBackData() {
            var self = this;
            var itemSelected = self.listBox().itemDtoSelected;
            var sumScopeAtr = null;
            var commuteAtr = null;
            var calculationMethod = 0;
            if (itemSelected().itemCode() === 'F309') {
                calculationMethod = 9;
            }
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
            var strError = "";
            var strErrorLimit = "";
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
                isUseHighError = self.listBox().checkUseHighError();
                errRangeHigh = itemSelected().errRangeHigh();
                isUseLowError = self.listBox().checkUseLowError();
                errRangeLow = itemSelected().errRangeLow();
                isUseHighAlam = self.listBox().checkUseHighAlam();
                alamRangeHigh = itemSelected().alamRangeHigh();
                isUseLowAlam = self.listBox().checkUseLowAlam();
                alamRangeLow = itemSelected().alamRangeLow();

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