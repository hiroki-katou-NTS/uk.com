module nts.uk.com.view.cmf002.d.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import model = cmf002.share.model;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import alertError = nts.uk.ui.dialog.alertError;
    export class ScreenModel {
        conditionSetCode: string = '001';
        conditionSetName: string = 'A社向け会計システム　テーブルA';
        categoryName: string = '個人情報';
        outputItemList: KnockoutObservableArray<DisplayTableName> = ko.observableArray([]);
        categoryItemList: KnockoutObservableArray<ItemDetail> = ko.observableArray([]);
        selectedOutputItemCode: KnockoutObservable<any> = ko.observable('');
        //select
        selectedCategoryItemCodeList: KnockoutObservable<ItemDetail> = ko.observable(null);
        selectedSelectionItemList: KnockoutObservableArray<string> = ko.observableArray([]);
        itemTypeItems: KnockoutObservableArray<model.ItemModel> = ko.observableArray(getItemType());
        selectedItemType: KnockoutObservable<string> = ko.observable('');
        // set up combobox
        itemList: KnockoutObservableArray<ItemModel>;
        selectedCode: KnockoutObservable<string>;
        selectedCode2: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        isRequired: KnockoutObservable<boolean>;
        selectFirstIfNull: KnockoutObservable<boolean>;
        // declare var of params screen B
        categoryName: string = '';
        categoryId: string = '';
        cndSetCd: string = '';
        cndSetName: string = '';
        // list add vào itemdetail 
        itemDetailList: KnockoutObservableArray<ScreenItem>;
        // list data get from server 


        // all data returned by server
        allDataItem: KnockoutObservable<CtgItemDataCndDetailDto> = ko.observable(null);
        // data to reg
        cndDetail: OutCndDetailDto = null;
        //
        categoryId: string;
        cndSetCd: string;





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
                    .ctgItemDataList, { 'displayTableName': data });
                _.forEach(objDataItemSelected, function(value) {
                    self.categoryItemList.push(ko.toJS({
                        itemNo: value.itemNo, itemName: value.itemName,
                        dataType: value.dataType, searchValueCd: value.searchValueCd, categoryId: value.categoryId
                    }));
                });


            });


            // get data from screen B
            let params = getShared('CMF002_D_PARAMS');
            if (params) {
                let categoryName = params.categoryName;
                self.categoryId = params.categoryId;
                self.cndSetCd = params.cndSetCd;
                let cndSetName = params.cndSetName;

                service.getListCtgItems(params.cndSetCd, params.categoryId).done(res => {
                    {

                        //get data return from server
                        let outputItemList: CtgItemDataCndDetailDto = res;
                        // setting display table name
                        self.allDataItem = ko.observable(outputItemList);
                        self.cndDetail = outputItemList.cndDetai;

                        let arrDataTableName: Array<string> = _.map(outputItemList.ctgItemDataList, 'displayTableName').reverse();

                        arrDataTableName = _.uniqBy(arrDataTableName, 'displayTableName');

                        _.forEach(arrDataTableName, function(value) {
                            self.outputItemList.push(ko.toJS({
                                displayTableName: value
                            }));
                        });
                        // setting  detail item

                        _.forEach(outputItemList.cndDetai.listOutCndDetailItem, function(value) {
                            let objDataItemSelected: CtgItemDataDto = _.find(self.allDataItem().ctgItemDataList, { 'itemNo': +value.categoryItemNo });
                            self.itemDetailList.push(new ScreenItem(objDataItemSelected, 1, value)),
                                self.itemDetailList.push(new ScreenItem(objDataItemSelected, 2, value))

                        });

                    }

                }).fail(res => {
                    alertError(res);
                });

            }


        }
        setDefault() {
            let self = this;
            nts.uk.util.value.reset($("#combo-box, #A_SEL_001"), self.defaultValue() !== '' ? self.defaultValue() : undefined);
        }

        validate() {
            $("#combo-box").trigger("validate");
        }

        setInvalidValue() {
            this.selectedCode('aaa');
        }

        initScreen() {
            let self = this;













            $("#fixed-table").ntsFixedTable({ height: 300 });
        }


        //終了する
        closeDialog() {
            close();
        }

        btnRightClick() {
            let self = this;
            let objDataSelectItemDetail = self.selectedCategoryItemCodeList();
            let objDataItemSelected: CtgItemDataDto = _.find(self.allDataItem().ctgItemDataList, { 'itemNo': +objDataSelectItemDetail });
            let objDataItemDetail: OutCndDetailItemDto = _.find(self.allDataItem().cndDetai.listOutCndDetailItem,
                { 'categoryItemNo': +objDataSelectItemDetail, 'categoryId': objDataItemSelected.categoryId });
            if (objDataItemDetail == null) {

                let ItemDetail: OutCndDetailItemDto = new OutCndDetailItemDto(self.categoryId, +objDataSelectItemDetail,
                    null, null, self.cndSetCd, 1, 0,0, 0, '', '','', '', '', '',0, 0, 0, 0,0, 0, null);
                self.cndDetail.listOutCndDetailItem.push(ItemDetail);
                self.itemDetailList.push(new ScreenItem(objDataItemSelected, 1, ItemDetail));
                self.itemDetailList.push(new ScreenItem(objDataItemSelected, 2, ItemDetail));

            }
            self.itemDetailList.push(new ScreenItem(objDataItemSelected, 1, objDataItemDetail));
            self.itemDetailList.push(new ScreenItem(objDataItemSelected, 2, objDataItemDetail));












        }

        btnLeftClick() {
        }

        start(): JQueryPromise<any> {
            let self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }
    }

    //項目型
    export function getItemType(): Array<model.ItemModel> {
        return [
            new model.ItemModel(0, getText("Enum_ItemType_NUMERIC")),
            new model.ItemModel(1, getText("Enum_ItemType_CHARACTER")),
            new model.ItemModel(2, getText("Enum_ItemType_DATE")),
            new model.ItemModel(3, getText("Enum_ItemType_TIME")),
            new model.ItemModel(4, getText("Enum_ItemType_INS_TIME")),
            new model.ItemModel(5, getText("Enum_ItemType_IN_SERVICE"))
        ];
    }

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

    }
    export class DisplayTableName {
        displayTableName: string;

        constructor(param: IDisplayTableName) {
            this.displayTableName = param.displayTableName;
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
}

class CtgItemDataCndDetailDto {
    ctgItemDataList: Array<CtgItemDataDto>;
    cndDetai: OutCndDetailDto;

    constructor(ctgItemDataList: Array<CtgItemDataDto>, cndDetaiList: OutCndDetailDto) {
        this.ctgItemDataList = ctgItemDataList;
        this.cndDetaiList = cndDetaiList;
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

    constructor(itemData: CtgItemDataDto, index: number, dataItemDetail: OutCndDetailItemDto) {
        let self = this;
        self.text = ko.observable("abc");
        self.symbolItem = itemData.itemName;
        self.itemListCombobox = ko.observableArray(self.checkConditionCombobox(itemData.searchValueCd, itemData.dataType));
        self.selectedCode = 1;
        self.selectedDataType = index;
        self.dataItemDetail = dataItemDetail;
        self.itemData = itemData;
        self.show();


    }
    show() {
        let self = this;
        let dataType: number = self.itemData.dataType;
        switch (dataType) {
            case nts.uk.com.view.cmf002.share.model.ITEM_TYPE.NUMERIC: {
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
                break;
            }
            case nts.uk.com.view.cmf002.share.model.ITEM_TYPE.TIME: {
                // edit textbox
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
            dataCombobox.push(new ItemDetailModel('1', '含む'));
        }
        else {
            if (searchValueCd == '') {
                dataCombobox.push(new ItemDetailModel('1', '範囲内'));
                dataCombobox.push(new ItemDetailModel('2', '同じ'));
                dataCombobox.push(new ItemDetailModel('3', '同じでない'));
                dataCombobox.push(new ItemDetailModel('4', 'より大きい'));
                dataCombobox.push(new ItemDetailModel('5', 'より小さい'));
                dataCombobox.push(new ItemDetailModel('6', '以上'));
                dataCombobox.push(new ItemDetailModel('7', '以下'));

            }
            else {
                dataCombobox.push(new ItemDetailModel('1', '同じ(複数)'));
                dataCombobox.push(new ItemDetailModel('2', '同じでない(複数)'));
            }

        }
        return dataCombobox;



    }

}
class ItemDetailModel {
    code: string;
    name: string;

    constructor(code: string, name: string) {
        this.code = code;
        this.name = name;
    }
}
