module nts.uk.com.view.cmf002.d.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import model = cmf002.share.model;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import alertError = nts.uk.ui.dialog.alertError;
    export class ScreenModel {
        outputItemList: KnockoutObservableArray<DisplayTableName> = ko.observableArray([]);
        categoryItemList: KnockoutObservableArray<ItemDetail> = ko.observableArray([]);
        selectedOutputItemCode: KnockoutObservable<any> = ko.observable('');
        selectedCategoryItemCodeList: KnockoutObservable<ItemDetail> = ko.observable(null);
        // set up combobox
        itemList: KnockoutObservableArray<ItemModel>;
        selectedCode: KnockoutObservable<string>;
        selectedCode2: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        isRequired: KnockoutObservable<boolean>;
        selectFirstIfNull: KnockoutObservable<boolean>;
        // declare var of params screen B
        conditionSetName:string ='';
        conditionSetCode: string='';
        categoryName: string = '';
        categoryId: string = '';
        cndSetCd: string = '';
        cndSetName: string = '';
        // list add vào itemdetail 
        itemDetailList: KnockoutObservableArray<ScreenItem>;
        // all data returned by server
        allDataItem: KnockoutObservable<CtgItemDataCndDetailDto> = ko.observable(null);
        // setting date
        date: KnockoutObservable<string>;
        yearMonth: KnockoutObservable<number>;
        /**
        * Constructor.
        */
        constructor() {
            let self = this;
            self.itemDetailList = ko.observableArray([]);
            self.initScreen();
            self.selectedCode = ko.observable('1');
            self.selectedCode2 = ko.observable('2');
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);
            self.isRequired = ko.observable(true);
            self.selectFirstIfNull = ko.observable(true);
            // list table name listener
            self.selectedOutputItemCode.subscribe(function(data: any) {
                //取込情報を選択する
                let objDataItemSelected: Array<CtgItemDataDto> = _.filter(self.allDataItem()
                    .ctgItemDataList, { 'tableName': data });
                _.forEach(objDataItemSelected, function(value) {
                    self.categoryItemList.push(ko.toJS({
                        itemNo: value.itemNo, itemName: value.itemName,
                        dataType: value.dataType, searchValueCd: value.searchValueCd, categoryId: value.categoryId
                    }));
                });
            });
        }
        initScreen() {
            let self = this;
            $("#fixed-table").ntsFixedTable({ height: 300 });
        }
        register() {
            let self = this;
            $("#D6_C4_1").trigger("validate");
            $("#D6_C4_2").trigger("validate");
            $("#D6_C4_3").trigger("validate");
            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            let temp: OutCndDetailInfoCommand = new OutCndDetailInfoCommand(self.allDataItem().cndDetai, 1, 1);
            service.register(temp).done(res => { }).fail(res => {
                alertError(res);
            });
        }
        //終了する
        closeDialog() {
            close();
        }

        btnRightClick() {
            let self = this;
            let objDataSelectItemDetail = self.selectedCategoryItemCodeList();
            let objDataItemSelected: CtgItemDataDto = _.find(self.allDataItem().ctgItemDataList, { 'itemNo': +objDataSelectItemDetail });
            if (self.allDataItem().cndDetai == null) {
                self.allDataItem().cndDetai = new OutCndDetailDto('', self.cndSetCd, '', []);
            }
            let objDataItemDetail: OutCndDetailItemDto = _.find(self.allDataItem().cndDetai.listOutCndDetailItem,
                { 'categoryItemNo': +objDataSelectItemDetail, 'categoryId': objDataItemSelected.categoryId });
            let index: number = self.itemDetailList().length + 1;

            if (objDataItemDetail == null) {
                // get serinumber
                let seriNumber: number = _.maxBy(self.allDataItem().cndDetai.listOutCndDetailItem, function(item) { return item.seriNum; }) + 1;
                let itemDetail: OutCndDetailItemDto = new OutCndDetailItemDto(self.categoryId, +objDataSelectItemDetail,
                    null, null, self.cndSetCd, 1, 0, 0, 0, '', '', '', '', '', '', 0, 0, 0, 0, 0, 0, null, seriNumber);
                self.allDataItem().cndDetai.listOutCndDetailItem.push(itemDetail);
                objDataItemDetail = itemDetail;

            }
            self.itemDetailList.push(new ScreenItem(objDataItemSelected, 1, objDataItemDetail, index));
        }
        btnLeftClick() {
            let self = this;
            self.itemDetailList.pop();
        }
        start(): JQueryPromise<any> {
            let self = this;
            var dfd = $.Deferred();
            block.invisible();
            // get data from screen B
            let params = getShared('CMF002_D_PARAMS');
            if (params) {
                let categoryName = params.categoryName;
                self.categoryId = params.categoryId;
                self.cndSetCd = params.cndSetCd;
                let cndSetName = params.cndSetName;
                service.getListCtgItems(params.cndSetCd, params.categoryId).done(res => {
                    {
                        block.clear();
                        dfd.resolve();
                        //get data return from server
                        let outputItemList: CtgItemDataCndDetailDto = res;
                        // setting display table name
                        self.allDataItem = ko.observable(outputItemList);
                        let arrDataTableName: Array<string> = _.orderBy(outputItemList.ctgItemDataList, ['displayTableName', 'tableName'], ['asc', 'desc']);
                       _.forEach(arrDataTableName, function(value) {
                            self.outputItemList.push(ko.toJS({
                                displayTableName: value
                            }));
                        });
                        // setting  detail item
                        let index: number = self.itemDetailList().length + 1;
                        if (outputItemList.cndDetai != null) {
                            _.forEach(outputItemList.cndDetai.listOutCndDetailItem, function(value) {
                                let objDataItemSelected: CtgItemDataDto = _.find(self.allDataItem().ctgItemDataList, { 'itemNo': +value.categoryItemNo });
                                // check  type of control is  1 or 2 control
                                let typeControl: number = 0;
                                if (value.conditionSymbol == CONDITION_SYMBOL.BETWEEN) {
                                    typeControl = 2;
                                }
                                else {
                                    typeControl = 1;
                                }
                                self.itemDetailList.push(new ScreenItem(objDataItemSelected, typeControl, value, index));
                            });
                        }
                    }
                }).fail(res => {
                    alertError(res);
                    block.clear();
                    dfd.reject();
                });
            }
            return dfd.promise();
        }
    }

    //項目型
    //出力項目(定型/ユーザ)
    export interface IOutputItem {
        outputItemCode: string;
        outputItemName: string;
        itemNo: string;
    }
    //出力項目(定型/ユーザ)
    export class OutputItem {
        outputItemCode: KnockoutObservable<string> = ko.observable('');
        outputItemname: KnockoutObservable<string> = ko.observable('');
        constructor(param: IOutputItem) {
            let self = this;
            self.outputItemCode(param.outputItemCode || '');
            self.outputItemname(param.outputItemName || '');
        }
    }
    export interface IDisplayTableName {
        displayTableName: string;
        tableName:string;

    }
    export class DisplayTableName {
        displayTableName: string;
        tableName:string;

        constructor(param: IDisplayTableName) {
            this.displayTableName = param.displayTableName;
            this.tableName = param.tableName;
        }
    }
    export interface IItemDetail {
        itemNo: string;
        itemName: string;
        dateType: number;
        searchValueCd: string;
        categoryId: number;
    }

    //外部出力カテゴリ項目データ
    export class ItemDetail {
        itemNo: KnockoutObservable<string> = ko.observable('');
        itemName: KnockoutObservable<string> = ko.observable('');
        dateType: number;
        searchValueCd: string;
        categoryId: number;
        constructor(param: IItemDetail) {
            let self = this;
            self.itemNo(param.itemNo || '');
            self.itemName(param.itemName || '');
            this.dateType = param.dateType;
            this.searchValueCd = param.searchValueCd;
            this.categoryId = param.categoryId;


        }
    }
    export enum CONDITION_SYMBOL {
        CONTAIN = 0,// 含む
        BETWEEN = 1,// 範囲内
        IS = 2,// 同じ
        IS_NOT = 3,// 同じでない
        GREATER = 4,// より大きい
        LESS = 5,//より小さい
        GREATER_OR_EQUAL = 6,//以上
        LESS_OR_EQUAL = 7,//以下
        IN = 8,//同じ(複数)
        NOT_IN = 9 //同じでない(複数)
    }
    class CtgItemDataCndDetailDto {
        ctgItemDataList: Array<CtgItemDataDto>;
        cndDetai: OutCndDetailDto;

        constructor(ctgItemDataList: Array<CtgItemDataDto>, cndDetaiList: OutCndDetailDto) {
            this.ctgItemDataList = ctgItemDataList;
            this.cndDetai = cndDetaiList;
        }
    }
    class CtgItemDataDto {
        categoryId: number;
        itemNo: number;
        tableName: string;
        displayTableName: string;
        itemName: string;
        dataType: number;
        searchValueCd: string;

        constructor(categoryId: number, itemNo: number,
            tableName: string, displayTableName: string, itemName: string,
            dataType: number, searchValueCd: string) {
            this.categoryId = categoryId;
            this.itemNo = itemNo;
            this.tableName = tableName;
            this.displayTableName = displayTableName;
            this.itemName = itemName;
            this.dataType = dataType;
            this.searchValueCd = searchValueCd;
        }
    }


    class CtgItemDataDto {
        categoryId: number;
        itemNo: number;
        tableName: string;
        displayTableName: string;
        itemName: string;
        dataType: number;
        searchValueCd: string;

        constructor(categoryId: number, itemNo: number,
            tableName: string, displayTableName: string, itemName: string,
            dataType: number, searchValueCd: string) {
            this.categoryId = categoryId;
            this.itemNo = itemNo;
            this.tableName = tableName;
            this.displayTableName = displayTableName;
            this.itemName = itemName;
            this.dataType = dataType;
            this.searchValueCd = searchValueCd;
        }
    }


    class OutCndDetailDto {
        cid: string;
        conditionSettingCd: string;
        exterOutCdnSql: string;
        listOutCndDetailItem: Array<OutCndDetailItemDto>;
        constructor(cid: string, conditionSettingCd: string,
            exterOutCdnSql: string, listOutCndDetailItem: Array<OutCndDetailItemDto>) {
            this.cid = cid;
            this.conditionSettingCd = conditionSettingCd;
            this.exterOutCdnSql = exterOutCdnSql;
            this.listOutCndDetailItem = listOutCndDetailItem;

        }
    }
    class OutCndDetailItemDto {
        categoryId: string;
        categoryItemNo: number;
        cid: string;
        userId: string;
        conditionSettingCd: string;
        conditionSymbol: number;
        searchNum: number;
        searchNumEndVal: number;
        searchNumStartVal: number;
        searchChar: string;
        searchCharEndVal: string;
        searchCharStartVal: string;
        searchDate: string;
        searchDateEnd: string;
        searchDateStart: string;
        searchClock: number;
        searchClockEndVal: number;
        searchClockStartVal: number;
        searchTime: number;
        searchTimeEndVal: number;
        searchTimeStartVal: number;
        listSearchCodeList: Array<SearchCodeListDto>;
        seriNum: number;

        constructor(categoryId: string, categoryItemNo: number, cid: string, userId: string,
            conditionSettingCd: string, conditionSymbol: number, searchNum: number, searchNumEndVal: number,
            searchNumStartVal: number, searchChar: string, searchCharEndVal: string, searchCharStartVal: string,
            searchDate: string, searchDateEnd: string, searchDateStart: string, searchClock: number,
            searchClockEndVal: number, searchClockStartVal: number, searchTime: number, searchTimeEndVal: number,
            searchTimeStartVal: number, listSearchCodeList: Array<SearchCodeListDto>
        ) {
            this.categoryId = categoryId;
            this.categoryItemNo = categoryItemNo;
            this.cid = cid;
            this.userId = userId;
            this.conditionSettingCd = conditionSettingCd;
            this.conditionSymbol = conditionSymbol;
            this.searchNum = searchNum;
            this.searchNumEndVal = searchNumEndVal;
            this.searchNumStartVal = searchNumStartVal;
            this.searchChar = searchChar;
            this.searchCharEndVal = searchCharEndVal;
            this.searchCharStartVal = searchCharStartVal;
            this.searchDate = searchDate;
            this.searchDateEnd = searchDateEnd;
            this.searchDateStart = searchDateStart;
            this.searchClock = searchClock;
            this.searchClockEndVal = searchClockEndVal;
            this.searchClockStartVal = searchClockStartVal;
            this.searchTime = searchTime;
            this.searchTimeEndVal = searchTimeEndVal;
            this.searchTimeStartVal = searchTimeStartVal;
            this.listSearchCodeList = listSearchCodeList;
        }

    }
    class SearchCodeListDto {
        id: string;
        cid: string;
        conditionSetCode: string;
        categoryId: number;
        categoryItemNo: number;
        seriNum: number;
        searchCode: string;
        searchItemName: string;
        constructor(id: string, cid: string, conditionSetCode: string, categoryId: number, categoryItemNo: number, seriNum: number, searchCode: string
            , searchItemName: string) {
            let self = this;
            self.id = id;
            self.cid = cid;
            self.conditionSetCode = conditionSetCode;
            self.categoryId = categoryId;
            self.categoryItemNo = categoryItemNo;
            self.seriNum = seriNum;
            self.searchCode = searchCode;
            self.searchItemName = searchItemName;

        }
    }
    class ScreenItem {

        text: KnockoutObservable<string>;
        symbolItem: string;
        itemListCombobox: KnockoutObservableArray<ItemDetailModel>;
        selectedCode: number;
        // edit textbox
        texteditor: any;
        simpleValue: KnockoutObservable<string>;
        selectedDataType: number;
        dataItemDetail: OutCndDetailItemDto;
        itemData: CtgItemDataDto;
        isDateTime: boolean;
        index: number;
        // setting date
        date: KnockoutObservable<''>;
        yearMonth: KnockoutObservable<0>;

        constructor(itemData: CtgItemDataDto, selectedDataType: number, dataItemDetail: OutCndDetailItemDto, index: number) {
            let self = this;
            self.text = ko.observable(index + '');
            self.symbolItem = itemData.itemName;
            self.itemListCombobox = ko.observableArray(self.checkConditionCombobox(itemData.searchValueCd, itemData.dataType));
            self.selectedCode = 1;
            self.selectedDataType = selectedDataType;
            self.dataItemDetail = dataItemDetail;
            self.itemData = itemData;

            self.index = index;

            //
            self.show();


        }
        show() {
            let self = this;
            let dataType: number = self.itemData.dataType;
            switch (dataType) {
                case nts.uk.com.view.cmf002.share.model.ITEM_TYPE.NUMERIC: {
                    self.isDateTime = false;
                    // edit textbox
                    self.texteditor = {
                        value: ko.observable(self.dataItemDetail.searchNum),
                        constraint: 'ResidenceCode',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "Placeholder for text editor",
                            width: "100px",
                            textalign: "left"
                        })),
                        required: ko.observable(true),
                        enable: ko.observable(true),
                        readonly: ko.observable(false)
                    };
                    break;
                }
                case nts.uk.com.view.cmf002.share.model.ITEM_TYPE.CHARACTER: {
                    // edit textbox
                    self.isDateTime = false;
                    self.texteditor = {
                        value: ko.observable(self.dataItemDetail.searchChar),
                        constraint: 'ResidenceCode',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "Placeholder for text editor",
                            width: "100px",
                            textalign: "left"
                        })),
                        required: ko.observable(true),
                        enable: ko.observable(true),
                        readonly: ko.observable(false)
                    };
                    break;
                }
                case nts.uk.com.view.cmf002.share.model.ITEM_TYPE.DATE: {
                    // edit textbox
                    self.isDateTime = true;
                    if (self.selectedDataType == 2) {
                        //setting date time
                        self.date = ko.observable('20000101');
                        self.yearMonth = ko.observable(200001);
                    }
                    else {
                        self.texteditor = {
                            value: ko.observable(self.dataItemDetail.searchDate),
                            constraint: 'ResidenceCode',
                            option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                textmode: "text",
                                placeholder: "Placeholder for text editor",
                                width: "100px",
                                textalign: "left"
                            })),
                            required: ko.observable(true),
                            enable: ko.observable(true),
                            readonly: ko.observable(false)
                        };

                    }

                    break;
                }
                case nts.uk.com.view.cmf002.share.model.ITEM_TYPE.TIME: {
                    // edit textbox
                    self.isDateTime = false;
                    self.texteditor = {
                        value: ko.observable(self.dataItemDetail.searchTime),
                        constraint: 'ResidenceCode',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "Placeholder for text editor",
                            width: "100px",
                            textalign: "left"
                        })),
                        required: ko.observable(true),
                        enable: ko.observable(true),
                        readonly: ko.observable(false)
                    };
                    break;
                }
                case nts.uk.com.view.cmf002.share.model.ITEM_TYPE.INS_TIME: {
                    // edit textbox
                    self.isDateTime = false;
                    self.texteditor = {
                        value: ko.observable(self.dataItemDetail.searchClock),
                        constraint: 'ResidenceCode',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "Placeholder for text editor",
                            width: "100px",
                            textalign: "left"
                        })),
                        required: ko.observable(true),
                        enable: ko.observable(true),
                        readonly: ko.observable(false)
                    };
                    break;
                }
            }




        }
        checkConditionCombobox(searchValueCd: string, dateType: number): Array<ItemDetailModel> {
            let dataCombobox: Array<ItemDetailModel> = [];
            if (searchValueCd == '' && dateType == 1) {
                dataCombobox.push(new ItemDetailModel(CONDITION_SYMBOL.CONTAIN, getText('CMF002_372')));
            }
            else {
                if (searchValueCd == '') {
                    dataCombobox.push(new ItemDetailModel(CONDITION_SYMBOL.BETWEEN,getText('CMF002_373')));
                    dataCombobox.push(new ItemDetailModel(CONDITION_SYMBOL.IS, getText('CMF002_374')));
                    dataCombobox.push(new ItemDetailModel(CONDITION_SYMBOL.IS_NOT, getText('CMF002_375')));
                    dataCombobox.push(new ItemDetailModel(CONDITION_SYMBOL.GREATER, getText('CMF002_376')));
                    dataCombobox.push(new ItemDetailModel(CONDITION_SYMBOL.LESS, getText('CMF002_377')));
                    dataCombobox.push(new ItemDetailModel(CONDITION_SYMBOL.GREATER_OR_EQUAL, getText('CMF002_378')));
                    dataCombobox.push(new ItemDetailModel(CONDITION_SYMBOL.LESS_OR_EQUAL, getText('CMF002_379')));

                }
                else {
                    dataCombobox.push(new ItemDetailModel(CONDITION_SYMBOL.IN, getText('CMF002_380')));
                    dataCombobox.push(new ItemDetailModel(CONDITION_SYMBOL.NOT_IN, getText('CMF002_381')));
                }

            }
            return dataCombobox;



        }




    }

    class ItemDetailModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    class OutCndDetailInfoCommand {
        outCndDetail: OutCndDetailDto;
        standardAtr: number;
        registerMode: number;
        constructor(outCndDetail: OutCndDetailDto, standardAtr: number, registerMode: number) {
            this.outCndDetail = outCndDetail;
            this.standardAtr = standardAtr;
            this.registerMode = registerMode;
        }
    }




}

