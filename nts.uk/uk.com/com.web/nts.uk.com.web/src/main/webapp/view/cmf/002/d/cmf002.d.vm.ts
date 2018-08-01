module nts.uk.com.view.cmf002.d.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import model = cmf002.share.model;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import alertError = nts.uk.ui.dialog.alertError;
    import shareModel = cmf002.share.model;

    export class ScreenModel {
        selectedSearchTable: KnockoutObservable<string> = ko.observable('');
        tableItemList: KnockoutObservableArray<TableItem> = ko.observableArray([]);
        selectedTable: KnockoutObservable<string> = ko.observable('');

        selectedSearchItem: KnockoutObservable<string> = ko.observable('');
        itemList: KnockoutObservableArray<CtgItemDataDto> = ko.observableArray([]);
        selectedItem: KnockoutObservable<string> = ko.observable('');

        ctgItemDataList: KnockoutObservableArray<CtgItemDataDto> = ko.observableArray([]);
        cndDetai: KnockoutObservable<OutCndDetailDto> = ko.observable(null);

        categoryItemList: KnockoutObservableArray<ItemDetail> = ko.observableArray([]);
        selectedOutputItemCode: KnockoutObservable<any> = ko.observable('');
        selectedCategoryItemCodeList: KnockoutObservable<ItemDetail> = ko.observable(null);
        // set up combobox
        //itemList: KnockoutObservableArray<ItemModel>;
        selectedCode: KnockoutObservable<string>;
        selectedCode2: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        isRequired: KnockoutObservable<boolean>;
        selectFirstIfNull: KnockoutObservable<boolean>;
        // declare var of params screen B
        conditionSetName: string = '';
        conditionSetCode: string = '';
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
            self.selectedCode = ko.observable('1');
            self.selectedCode2 = ko.observable('2');
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);
            self.isRequired = ko.observable(true);
            self.selectFirstIfNull = ko.observable(true);

            self.selectedTable.subscribe(table => {
                let items = _.filter(self.ctgItemDataList(), { "tableName": table });
                self.itemList(items);
            })
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

        start(): JQueryPromise<any> {
            let self = this;
            var dfd = $.Deferred();
            block.invisible();
            // get data from screen B
            //let params = getShared('CMF002_D_PARAMS');
            let params = {
                categoryId: 102,
                categoryName: "cate0010000000000000",
                cndSetCd: "007",
                cndSetName: "4444"
            }
            if (params) {
                self.categoryName = params.categoryName;
                self.categoryId = params.categoryId;
                self.cndSetCd = params.cndSetCd;
                self.cndSetName = params.cndSetName;
                service.getListCtgItems(params.cndSetCd, params.categoryId).done(res => {
                    //get data return from server
                    let outputItemList: CtgItemDataCndDetailDto = res;
                    self.cndDetai(OutCndDetailDto.fromApp(res.cndDetai));
                    self.ctgItemDataList(res.ctgItemDataList);

                    self.loadDetaiItemGrid();
                    /*// setting display table name
                    self.allDataItem = ko.observable(outputItemList);
                    self.setTableItemList();
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
                    }*/
                    block.clear();
                    dfd.resolve();
                }).fail(res => {
                    alertError(res);
                    block.clear();
                    dfd.reject();
                });
            }
            return dfd.promise();
        }

        setTableItemList() {
            let self = this;
            self.tableItemList.removeAll();
            let tableUniq = _.uniqBy(self.ctgItemDataList(), 'tableName');
            _.each(tableUniq, item => {
                self.tableItemList.push(new TableItem(item.tableName, item.displayTableName));
            })
        }

        setDataType() {
            let self = this;
            _.each(self.cndDetai().listOutCndDetailItem(), item => {
                let ctgItem = self.getCtgItem(item.categoryItemNo());
                if (ctgItem) {
                    item.dataType = ctgItem.dataType;
                } else {
                    item.dataType = null;
                    item.swicthView = 0;
                }
            })
        }

        loadDetaiItemGrid() {
            let self = this;
            self.setTableItemList();
            self.setDataType();
            $("#fixed-table").ntsFixedTable({ height: 393 });
        }

        getCtgItem(itemNo) {
            let self = this;
            return _.find(self.ctgItemDataList(), { 'itemNo': itemNo });
        }

        getItemName(itemNo) {
            let self = this;
            let item = self.getCtgItem(itemNo);
            if (item) {
                return item.itemName;
            }
            return "";
        }

        getComboboxSource(dataType) {
            let self = this;
            if (dataType != null) {
                return shareModel.getConditonSymbol(dataType);
            }
            return [];
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

    }

    export class TableItem {
        tableName: string;
        displayTableName: string;
        constructor(tableName: string, displayTableName: string) {
            this.tableName = tableName;
            this.displayTableName = displayTableName;
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
        dataType: shareModel.ITEM_TYPE;
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
        conditionSettingCd: string;
        exterOutCdnSql: string;
        listOutCndDetailItem: KnockoutObservableArray<OutCndDetailItemDto>;
        constructor(conditionSettingCd: string,
            exterOutCdnSql: string, listOutCndDetailItem: Array<OutCndDetailItemDto>) {
            this.conditionSettingCd = conditionSettingCd;
            this.exterOutCdnSql = exterOutCdnSql;
            this.listOutCndDetailItem = ko.observableArray(listOutCndDetailItem);
        }

        static fromApp(app) {
            let listOutCndDetailItem = [];
            _.each(app.listOutCndDetailItem, item => {
                listOutCndDetailItem.push(OutCndDetailItemDto.fromApp(item));
            })
            return new OutCndDetailDto(app.conditionSettingCd, app.exterOutCdnSql, listOutCndDetailItem);
        }
    }
    class OutCndDetailItemDto {
        categoryId: KnockoutObservable<string>;
        categoryItemNo: KnockoutObservable<number>;
        seriNum: KnockoutObservable<number>;
        conditionSettingCd: KnockoutObservable<string>;;
        conditionSymbol: KnockoutObservable<number>
        searchNum: KnockoutObservable<number>;
        searchNumEndVal: KnockoutObservable<number>;
        searchNumStartVal: KnockoutObservable<number>;
        searchChar: KnockoutObservable<string>;;
        searchCharEndVal: KnockoutObservable<string>;;
        searchCharStartVal: KnockoutObservable<string>;;
        searchDate: KnockoutObservable<string>;;
        searchDateEnd: KnockoutObservable<string>;;
        searchDateStart: KnockoutObservable<string>;;
        searchClock: KnockoutObservable<number>;
        searchClockEndVal: KnockoutObservable<number>;
        searchClockStartVal: KnockoutObservable<number>;
        searchTime: KnockoutObservable<number>;
        searchTimeEndVal: KnockoutObservable<number>;
        searchTimeStartVal: KnockoutObservable<number>;
        listSearchCodeList: Array<SearchCodeListDto>;

        itemName: string;
        dataType: shareModel.ITEM_TYPE;
        constructor(categoryId: string, categoryItemNo: number, seriNum: number,
            conditionSettingCd: string, conditionSymbol: number,
            searchNum: number, searchNumEndVal: number, searchNumStartVal: number,
            searchChar: string, searchCharEndVal: string, searchCharStartVal: string,
            searchDate: string, searchDateEnd: string, searchDateStart: string,
            searchClock: number, searchClockEndVal: number, searchClockStartVal: number,
            searchTime: number, searchTimeEndVal: number, searchTimeStartVal: number,
            listSearchCodeList: Array<SearchCodeListDto>) {
            let self = this;
            self.categoryId = ko.observable(categoryId);
            self.categoryItemNo = ko.observable(categoryItemNo);
            self.seriNum = ko.observable(seriNum);
            self.conditionSettingCd = ko.observable(conditionSettingCd);
            self.conditionSymbol = ko.observable(conditionSymbol);
            self.searchNum = ko.observable(searchNum);
            self.searchNumEndVal = ko.observable(searchNumEndVal);
            self.searchNumStartVal = ko.observable(searchNumStartVal);
            self.searchChar = ko.observable(searchChar);
            self.searchCharEndVal = ko.observable(searchCharEndVal);
            self.searchCharStartVal = ko.observable(searchCharStartVal);
            self.searchDate = ko.observable(searchDate);
            self.searchDateEnd = ko.observable(searchDateEnd);
            self.searchDateStart = ko.observable(searchDateStart);
            self.searchClock = ko.observable(searchClock);
            self.searchClockEndVal = ko.observable(searchClockEndVal);
            self.searchClockStartVal = ko.observable(searchClockStartVal);
            self.searchTime = ko.observable(searchTime);
            self.searchTimeEndVal = ko.observable(searchTimeEndVal);
            self.searchTimeStartVal = ko.observable(searchTimeStartVal);
            self.listSearchCodeList = listSearchCodeList;
        }

        static fromApp(app) {
            return new OutCndDetailItemDto(app.categoryId, app.categoryItemNo, app.seriNum,
                app.conditionSettingCd, app.conditionSymbol,
                app.searchNum, app.searchNumEndVal, app.searchNumStartVal,
                app.searchChar, app.searchCharEndVal, app.searchCharStartVal,
                app.searchDate, app.searchDateEnd, app.searchDateStart,
                app.searchClock, app.searchClockEndVal, app.searchClockStartVal,
                app.searchTime, app.searchTimeEndVal, app.searchTimeStartVal,
                app.listSearchCodeList)
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
        date: any;
        yearMonth: any;

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
                    dataCombobox.push(new ItemDetailModel(CONDITION_SYMBOL.BETWEEN, getText('CMF002_373')));
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

