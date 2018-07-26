module nts.uk.com.view.cmf002.d.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import model = cmf002.share.model;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    export class ScreenModel {
        conditionSetCode: string = '001';
        conditionSetName: string = 'A社向け会計システム　テーブルA';
        categoryName: string = '個人情報';
        outputItemList: KnockoutObservableArray<IOutputItem> = ko.observableArray([]);
        categoryItemList: KnockoutObservableArray<IExternalOutputCategoryItemData> = ko.observableArray([]);
        selectionItemList: KnockoutObservableArray<IExternalOutputCategoryItemData> = ko.observableArray([]);
        selectedOutputItemCode: KnockoutObservable<any> = ko.observable('');
        //select
        selectedCategoryItemCodeList: KnockoutObservable<string> = ko.observable(null);
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


        // var to if else in index.xhtml
        selectedDataType: KnockoutObservable<number> = ko.observable(1);
        
        // all data returned by server
        allDataItem :KnockoutObservable<CtgItemDataCndDetailDto> = ko.observable(null);





        /**
         * Constructor.
         */
        constructor() {
            let self = this;
            self.selectedDataType(1);
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
                data = 1;
                let dataItemDetail:OutCndDetailItemDto = _.find(self.allDataItem().detaiItemList, { 'categoryItemNo': data });
                console.log(dataItemDetail.cid);
//                 self.categoryItemList.push(ko.toJS({ itemNo: i >= 10 ? '00' + i : '000' + i, itemName: 'カテゴリ項目一覧' }));
          
            });


            // get data from screen B
            let params = getShared('CMF002_D_PARAMS');
            if (params) {
                let categoryName = params.categoryName;
                let categoryId = params.categoryId;
                let cndSetCd = params.cndSetCd;
                let cndSetName = params.cndSetName;

                service.getListCtgItems(cndSetCd,categoryId).done(res => {
                    {
                        //setting combobox
                        let outputItemList: CtgItemDataCndDetailDto = res;
                        // push element table name 1
                          self.allDataItem = ko.observable(outputItemList);;
                            let arrDataTableName : Array<CtgItemDataTableDto> = outputItemList.ctgItemDataList;
                            let sizeOfArrayData:number = arrDataTableName.length;
                            for (let i = 1; i <= sizeOfArrayData; i++) {
                                console.log(arrDataTableName[i].tableName + '   ');
                                self.outputItemList.push(ko.toJS({ outputItemCode:i+'',
                                 outputItemName: arrDataTableName[i].tableName+'',
                                 itemNo:arrDataTableName[i].itemNo }));
                            }
                        console.log(outputItemList.detaiItemList.length);
                       
                      



                    }

                }).fail(res => {
                    console.log("getConditionSetting fail");
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


        //終了する
        closeDialog() {
            close();
        }

        btnRightClick() {
            let self = this;
            console.log(self.selectedDataType() + ' fdfsdf');
            let itemNo = self.selectedCategoryItemCodeList();
            self.selectedDataType(2);
            // item selected
            let objDataItemSelected = _.find(self.categoryItemList(), { 'itemNo': itemNo });
            // get data itemDetail
            self.itemDetailList.push(new ScreenItem(null, null, null));
            console.log(objDataItemSelected.itemNo + ' ' + objDataItemSelected.itemName);





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
        itemNo:string;
    }

    export class OutputItem {
        outputItemCode: KnockoutObservable<string> = ko.observable('');
        outputItemname: KnockoutObservable<string> = ko.observable('');
        constructor(param: IOutputItem) {
            let self = this;
            self.outputItemCode(param.outputItemCode || '');
            self.outputItemname(param.outputItemName || '');
        }
    }

    //外部出力カテゴリ項目データ
    export interface IExternalOutputCategoryItemData {
        itemNo: string;
        itemName: string;
        dateType: number;
    }

    export class ExternalOutputCategoryItemData {
        itemNo: KnockoutObservable<string> = ko.observable('');
        itemName: KnockoutObservable<string> = ko.observable('');
        constructor(param: IExternalOutputCategoryItemData) {
            let self = this;
            self.itemNo(param.itemNo || '');
            self.itemName(param.itemName || '');
        }
    }
}

class CtgItemDataCndDetailDto {
    ctgItemDataList:Array<CtgItemDataTableDto>;
    detaiItemList:Array<OutCndDetailItemDto>;


    constructor( ctgItemDataList:Array<CtgItemDataTableDto>,detaiItemList:Array<OutCndDetailItemDto>) {
        this.ctgItemDataList = ctgItemDataList;
        this.detaiItemList = detaiItemList;
    }
}
class CtgItemDataTableDto {
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

    constructor(categoryId: string, categoryItemNo: number, cid: string, userId: string,
        conditionSettingCd: string, conditionSymbol: number, searchNum: number, searchNumEndVal: number,
        searchNumStartVal: number, searchChar: string, searchCharEndVal: string, searchCharStartVal: string,
        searchDate: string, searchDateEnd: string, searchDateStart: string, searchClock: number,
        searchClockEndVal: number, searchClockStartVal: number, searchTime: number, searchTimeEndVal: number,
        searchTimeStartVal: number
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



    }
}
class ScreenItem {
    text: KnockoutObservable<string>;
    symbolItem: KnockoutObservable<string>;
    itemListCombobox: KnockoutObservableArray<ItemDetailModel>;
    selectedCode: KnockoutObservable<string>;
    // edit textbox
    texteditor: any;
    simpleValue: KnockoutObservable<string>;

    constructor(index: number, dataListCombobox: Array<ItemDetailModel>, line: number) {
        let self = this;
        self.text = ko.observable("abc");
        self.symbolItem = ko.observable("haha");
        self.itemListCombobox = ko.observableArray([new ItemDetailModel('1', 'đasadasdas')]);
        self.selectedCode = ko.observable('1');

        // edit textbox
        self.texteditor = {
            value: ko.observable('123456'),
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
}
class ItemDetailModel {
    code: string;
    name: string;

    constructor(code: string, name: string) {
        this.code = code;
        this.name = name;
    }
}
