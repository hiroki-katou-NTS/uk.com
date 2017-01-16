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
        errRangeHigh: KnockoutObservable<number> = ko.observable(0);
        checkUseLowError: KnockoutObservable<boolean> = ko.observable(false);
        errRangeLow: KnockoutObservable<number>= ko.observable(0);
        checkUseHighAlam: KnockoutObservable<boolean> = ko.observable(false);
        alamRangeHigh: KnockoutObservable<number>= ko.observable(0);
        checkUseLowAlam: KnockoutObservable<boolean> = ko.observable(false);
        alamRangeLow: KnockoutObservable<number>= ko.observable(0);
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
        selectedCode: KnockoutObservable<any>;
        selectedName: KnockoutObservable<any>;
        isEnable: KnockoutObservable<any>;
        selectedCodes: KnockoutObservableArray<any>;
        itemDtoSelected: KnockoutObservable<any> = ko.observable();
        listItemDto: Array<ItemDto>;
        taxAtr: KnockoutObservable<any>;
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
                        let item; 
                        if(data != null){
                            item = {
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
                    }else{     
                        let itemDto: any = _.find(self.listItemDto, function (item) {
                            return item.itemCode === currentItemCode;    
                        }) 
                            item = {
                            companyCode: ko.observable(null),
                            itemCode: ko.observable(itemDto.itemCode),
                            itemAbName: ko.observable(itemDto.itemAbName),
                            categoryAtr: ko.observable(itemDto.categoryAtr),
                            checkUseHighError: ko.observable(itemDto.checkUseHighError == 1),
                            errRangeHigh: ko.observable(itemDto.errRangeHigh),
                            checkUseLowError: ko.observable(itemDto.checkUseLowError == 1),
                            errRangeLow: ko.observable(itemDto.errRangeLow),
                            checkUseHighAlam: ko.observable(itemDto.checkUseHighAlam == 1),
                            alamRangeHigh: ko.observable(itemDto.alamRangeHigh),
                            checkUseLowAlam: ko.observable(itemDto.checkUseLowAlam == 1),
                            alamRangeLow: ko.observable(itemDto.alamRangeLow),
                            commuteAtr: ko.observable(itemDto.commuteAtr),
                            calculationMethod: ko.observable(itemDto.calculationMethod),
                            distributeSet: ko.observable(itemDto.distributeSet),
                            distributeWay: ko.observable(itemDto.distributeWay),
                            personalWageCode: ko.observable(itemDto.personalWageCode),
                            sumScopeAtr: ko.observable(itemDto.sumScopeAtr)
                        }
                    }
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
            self.selectedRuleCode = ko.observable(1);
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
        taxAtr: number;
        itemAtr: KnockoutObservable<string>= ko.observable('支給項目');
        queueSearchResult: Array<ItemModel> = [];
        textSearch: string = "";
        
        constructor(data) {
            var self = this;
            self.paramItemCode = data.itemCode;
            self.paramCategoryAtr = ko.observable(data.categoryId);
            self.paramIsUpdate = data.isUpdate;
            self.paramStmtCode = data.stmtCode;
            self.paramStartYm = data.startYm;
            self.taxAtr = data.taxAtr;
            if(self.paramCategoryAtr() === 0){
                self.itemAtr("支給項目");                    
            }else if(self.paramCategoryAtr() === 1){
                self.itemAtr('控除項目');    
            }else if(self.paramCategoryAtr() === 2){
                self.itemAtr('勤怠項目');    
            }else if(self.paramCategoryAtr() === 3){
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
            if(self.comboBoxCalcMethod !== undefined){
                self.switchButton().selectedRuleCode.subscribe(function(newValue){
                    //按分方法: 非活性: 項目区分が「支給項目」 or 「控除項目」＆「按分設定」が「按分しない」の場合
                    if(newValue == 0){
                        self.comboBoxDistributeWay().enable(false);
                    }else{
                        self.comboBoxDistributeWay().enable(true);
                    }
                })
            }            
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
                            if(data != null){
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
                                if(self.comboBoxSumScopeAtr !==undefined){
                                    self.comboBoxSumScopeAtr().selectedCode(data.sumScopeAtr);}
                                if(self.comboBoxCalcMethod !== undefined){
                                    self.comboBoxCalcMethod().selectedCode(data.calculationMethod);}
                                if(self.comboBoxDistributeWay !== undefined){
                                    self.comboBoxDistributeWay().selectedCode(data.distributeWay);}
                                if(self.switchButton !== undefined){
                                    self.switchButton().selectedRuleCode(data.distributeSet);}
                            }
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
            if(self.comboBoxCalcMethod !== undefined){
                self.comboBoxCalcMethod().selectedCode.subscribe(function(newValue){
                    //通勤区分: 非表示: 「計算方法」が「手入力」以外 or 選択している項目の課税区分(TAX_ATR)が 3 or 4 以外 の場合
                    var taxAtr = 0;
                    var itemCode = self.listBox().selectedCode;     
                    let itemDto: any = _.find(self.listBox().listItemDto, function (item) {
                        return item.itemCode === itemCode();    
                    }) 
                    
                    if(newValue != 0
                        ||itemDto.taxAtr === 3 
                        || itemDto.taxAtr ===4){
                        $('#calcMethod').hide();
                    }else{
                        $('#calcMethod').show();    
                    }
                })
            }
            
            return dfd.promise();
        }
        
        searchItem() {
            var self = this;
            var textSearch = $("#INP_001").val().trim();
            
            if (textSearch.length === 0){
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
                if (self.queueSearchResult.length === 0){
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
            var calculationMethod = 0;
            if(itemSelected().itemCode() === 'F309'){
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
                isUseHighError = itemSelected().checkUseHighError();
                errRangeHigh = itemSelected().errRangeHigh();
                isUseLowError = itemSelected().checkUseLowError();
                errRangeLow = itemSelected().errRangeLow();
                isUseHighAlam = itemSelected().checkUseHighAlam();
                alamRangeHigh = itemSelected().alamRangeHigh();
                isUseLowAlam = itemSelected().checkUseLowAlam();
                alamRangeLow = itemSelected().alamRangeLow();

                //入力値の整合チェックを行う
                //エラー上限値<アラーム上限値<アラーム下限値<エラー下限値　の場合
                if(isUseHighError && isUseHighAlam
                   && (+errRangeHigh < +alamRangeHigh)){
                    alert('範囲の指定が正しくありません。');    
                    $('#INP_002').focus();
                    return false;
                }
                //エラー上限値 < エラー下限値 || エラー上限値 = エラー下限値
                if(isUseHighError && isUseLowError
                    && (+errRangeHigh < +errRangeLow ||+errRangeHigh == +errRangeLow)){
                    alert('範囲の指定が正しくありません。');    
                    $('#INP_002').focus();
                    return false;
                }
                if(isUseLowError && isUseLowAlam
                    && +alamRangeLow < +errRangeLow){
                    alert('範囲の指定が正しくありません。');    
                    $('#INP_003').focus();
                    return false;
                }
                //アラーム上限値 < アラーム下限値 || アラーム上限値 = アラーム下限値
                if(isUseHighAlam && isUseLowAlam
                    && (+alamRangeHigh < +alamRangeLow || +alamRangeHigh == +alamRangeLow)){
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
            return self.comboBoxCalcMethod().selectedCode() == 1;
        }
        
        setItemDisplay() {
            var self = this;
            
        }
    }


}