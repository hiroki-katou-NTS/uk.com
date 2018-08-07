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
        selectedSearchTable: KnockoutObservable<any> = ko.observable(null);
        tableItemList: KnockoutObservableArray<TableItem> = ko.observableArray([]);
        selectedTable: KnockoutObservable<any> = ko.observable(null);

        selectedSearchItem: KnockoutObservable<any> = ko.observable(null);
        itemList: KnockoutObservableArray<CtgItemDataDto> = ko.observableArray([]);
        selectedItem: KnockoutObservable<any> = ko.observable(null);

        ctgItemDataList: KnockoutObservableArray<CtgItemDataDto> = ko.observableArray([]);
        cndDetai: KnockoutObservable<OutCndDetailDto> = ko.observable(null);

        selectedSeriNum: KnockoutObservable<any> = ko.observable(null);

        // declare var of params screen B
        categoryName: string = '';
        categoryId: string = '';
        cndSetCd: string = '';
        cndSetName: string = '';

        /**
        * Constructor.
        */
        constructor() {
            let self = this;

            self.selectedTable.subscribe(table => {
                let items = _.filter(self.ctgItemDataList(), { "tableName": table });
                self.itemList(items);
            })
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            var dfd = $.Deferred();
            block.invisible();
            // get data from screen B
            let params = getShared('CMF002_D_PARAMS');
            /*let params = {
                categoryId: 1,
                categoryName: "cate0010000000000000",
                cndSetCd: "001",
                cndSetName: "4444"
            }*/
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

        setDataInitDetailItem() {
            let self = this;
            _.each(self.cndDetai().listOutCndDetailItem(), function(item: OutCndDetailItemDto) {
                let ctgItem: CtgItemDataDto = self.getCtgItem(item.categoryItemNo());
                if (ctgItem) {
                    item.dataType = ctgItem.dataType;
                    item.searchValueCd = ctgItem.searchValueCd;
                    item.switchView(OutCndDetailItemDto.getSwitchView(item.dataType, item.conditionSymbol()));
                    item.clearData();
                } else {
                    item.dataType = null;
                }
            })
        }

        loadDetaiItemGrid() {
            let self = this;
            self.setTableItemList();
            self.setDataInitDetailItem();
            $("#fixed-table").ntsFixedTable({ height: 328 });
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

        getComboboxSource(searchValueCd, dataType) {
            let self = this;
            if (dataType != null) {
                return shareModel.getConditonSymbol(searchValueCd, dataType);
            }
            return [];
        }

        register() {
            let self = this;
            _.each(self.cndDetai().listOutCndDetailItem(), function(item: OutCndDetailItemDto) {
                item.validate();
            })
            if (nts.uk.ui.errors.hasError()) {
                return;
            }

            _.each(self.cndDetai().listOutCndDetailItem(), function(item: OutCndDetailItemDto) {
                if (item.switchView() != SWITCH_VIEW.SEARCH_CODE_LIST) return false;
                let listSearchCode = _.split(item.joinedSearchCodeList(), ',');
                _.each(listSearchCode, searchCode => {
                    let newSearchCode: SearchCodeListDto = new SearchCodeListDto(item.conditionSettingCd, item.categoryId,
                        item.categoryItemNo, item.seriNum, searchCode, self.getItemName(item.categoryItemNo()));
                    item.listSearchCodeList.push(newSearchCode);
                })
            })
            let command: OutCndDetailInfoCommand = new OutCndDetailInfoCommand(ko.toJS(self.cndDetai()), 1, 1);
            service.register(command).done(res => { }).fail(res => {
                alertError(res);
            });
        }
        //終了する
        closeDialog() {
            close();
        }

        btnRightClick() {
            let self = this;
            if (self.selectedItem() == null) {
                return;
            }
            let seriNum = 1;
            let item = self.getCtgItem(parseInt(self.selectedItem()));
            let itemDetail = _.maxBy(ko.toJS(self.cndDetai().listOutCndDetailItem()), 'seriNum');
            if (itemDetail) {
                seriNum = itemDetail.seriNum + 1;
            }
            let newItemDetail = new OutCndDetailItemDto(self.categoryId, item.itemNo, seriNum,
                self.cndSetCd, null);
            newItemDetail.dataType = item.dataType;
            newItemDetail.searchValueCd = item.searchValueCd;
            self.cndDetai().listOutCndDetailItem.push(newItemDetail);
        }

        btnLeftClick() {
            let self = this;
            if (self.selectedSeriNum() == null) {
                return;
            }
            self.cndDetai().listOutCndDetailItem.remove(function(item: OutCndDetailItemDto) {
                if (item.seriNum() == self.selectedSeriNum()) {
                    item.removeValidate();
                    return true;
                }
                return false;
            });
            self.selectedSeriNum(null);
        }

        selectDetailItem(seriNum) {
            let self = this;
            self.selectedSeriNum(seriNum);
            $("#fixed-table tr").removeClass("my-active-row");
            $("#fixed-table tr[data-id='" + seriNum + "']").addClass("my-active-row");
        }
    }

    class TableItem {
        tableName: string;
        displayTableName: string;
        constructor(tableName: string, displayTableName: string) {
            this.tableName = tableName;
            this.displayTableName = displayTableName;
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
        conditionSettingCd: KnockoutObservable<string>;
        conditionSymbol: KnockoutObservable<number>;
        searchNum: KnockoutObservable<number>;
        searchNumEndVal: KnockoutObservable<number>;
        searchNumStartVal: KnockoutObservable<number>;
        searchChar: KnockoutObservable<string>;
        searchCharEndVal: KnockoutObservable<string>;
        searchCharStartVal: KnockoutObservable<string>;
        searchDate: KnockoutObservable<string>;
        searchDateEnd: KnockoutObservable<string>;
        searchDateStart: KnockoutObservable<string>;
        searchClock: KnockoutObservable<number>;
        searchClockEndVal: KnockoutObservable<number>;
        searchClockStartVal: KnockoutObservable<number>;
        searchTime: KnockoutObservable<number>;
        searchTimeEndVal: KnockoutObservable<number>;
        searchTimeStartVal: KnockoutObservable<number>;
        listSearchCodeList: Array<SearchCodeListDto>;
        joinedSearchCodeList: KnockoutObservable<string>;

        itemName: string;
        dataType: shareModel.ITEM_TYPE;
        searchValueCd: string;
        switchView: KnockoutObservable<SWITCH_VIEW>;

        constructor(categoryId: string, categoryItemNo: number, seriNum: number,
            conditionSettingCd: string, conditionSymbol: number) {
            let self = this;
            self.categoryId = ko.observable(categoryId);
            self.categoryItemNo = ko.observable(categoryItemNo);
            self.seriNum = ko.observable(seriNum);
            self.conditionSettingCd = ko.observable(conditionSettingCd);
            self.conditionSymbol = ko.observable(conditionSymbol);
            self.searchNum = ko.observable(null);
            self.searchNumEndVal = ko.observable(null);
            self.searchNumStartVal = ko.observable(null);
            self.searchChar = ko.observable(null);
            self.searchCharEndVal = ko.observable(null);
            self.searchCharStartVal = ko.observable(null);
            self.searchDate = ko.observable(null);
            self.searchDateEnd = ko.observable(null);
            self.searchDateStart = ko.observable(null);
            self.searchClock = ko.observable(null);
            self.searchClockEndVal = ko.observable(null);
            self.searchClockStartVal = ko.observable(null);
            self.searchTime = ko.observable(null);
            self.searchTimeEndVal = ko.observable(null);
            self.searchTimeStartVal = ko.observable(null);
            self.listSearchCodeList = [];
            self.joinedSearchCodeList = ko.observable(null);

            self.switchView = ko.observable(SWITCH_VIEW.NONE);
            self.searchValueCd = "";
            self.conditionSymbol.subscribe(condSymbol => {
                self.switchView(OutCndDetailItemDto.getSwitchView(self.dataType, condSymbol));
            })
            self.switchView.subscribe(condSymbol => {
                self.clearData();
            })
        }

        clearError(control) {
            let self = this;
            $("#fixed-table tr[data-id='" + self.seriNum() + "'] ." + control).ntsError('clear');
        }

        checkError(control) {
            let self = this;
            $("#fixed-table tr[data-id='" + self.seriNum() + "'] ." + control).ntsError('check');
        }

        setError(control, messageId) {
            let self = this;
            $("#fixed-table tr[data-id='" + self.seriNum() + "'] ." + control).ntsError('set', { messageId: messageId });
        }

        clearData() {
            let self = this;
            // 文字型
            if (self.switchView() != SWITCH_VIEW.CHARACTER_NORMAL) {
                self.searchChar(null);
                self.clearError("D6_C4_1");
            }
            if (self.switchView() != SWITCH_VIEW.CHARACTER_PERIOD) {
                self.searchCharStartVal(null);
                self.searchCharEndVal(null);
                self.clearError("D6_C4_2");
                self.clearError("D6_C4_3");
            }

            // 数値型
            if (self.switchView() != SWITCH_VIEW.NUMERIC_NORMAL) {
                self.searchNum(null);
                self.clearError("D6_C4_4");
            }
            if (self.switchView() != SWITCH_VIEW.NUMERIC_PERIOD) {
                self.searchNumStartVal(null);
                self.searchNumEndVal(null);
                self.clearError("D6_C4_5");
                self.clearError("D6_C4_6");
            }

            // 日付型
            if (self.switchView() != SWITCH_VIEW.DATE_NORMAL) {
                self.searchDate(null);
                self.clearError("D6_C4_7");
            }
            if (self.switchView() != SWITCH_VIEW.DATE_PERIOD) {
                self.searchDateStart(null);
                self.searchDateEnd(null);
                self.clearError("D6_C4_8");
                self.clearError("D6_C4_9");
            }

            // 時間型
            if (self.switchView() != SWITCH_VIEW.TIME_NORMAL) {
                self.searchTime(null);
                self.clearError("D6_C4_10");
            }
            if (self.switchView() != SWITCH_VIEW.TIME_PERIOD) {
                self.searchTimeStartVal(null);
                self.searchTimeEndVal(null);
                self.clearError("D6_C4_11");
                self.clearError("D6_C4_12");
            }

            // 時刻型
            if (self.switchView() != SWITCH_VIEW.INS_TIME_NORMAL) {
                self.searchClock(null);
                self.clearError("D6_C4_13");
            }
            if (self.switchView() != SWITCH_VIEW.INS_TIME_PERIOD) {
                self.searchClockStartVal(null);
                self.searchClockEndVal(null);
                self.clearError("D6_C4_14");
                self.clearError("D6_C4_15");
            }

            if (self.switchView() != SWITCH_VIEW.SEARCH_CODE_LIST) {
                //self.listSearchCodeList = [];
                self.joinedSearchCodeList(null);
                self.clearError("D6_C4_16");
            }
        }

        validate() {
            let self = this;
            // 項目名が存在し、記号が指定されていない項目が1件以上ある場合
            if (_.isNil(self.conditionSymbol())) {
                self.setError("D6_C3_1", "Msg_656");
            }

            // 検索する値が入力されていない項目が１件以上ある場合（複数の場合は全ての項目が入力されていること）
            switch (self.switchView()) {
                case SWITCH_VIEW.CHARACTER_NORMAL:
                    if (_.isEmpty(self.searchChar())) {
                        self.setError("D6_C4_1", "Msg_656");
                    }
                    break;
                case SWITCH_VIEW.CHARACTER_PERIOD:
                    if (_.isEmpty(self.searchCharStartVal())) {
                        self.setError("D6_C4_2", "Msg_656");
                    }
                    if (_.isEmpty(self.searchCharEndVal())) {
                        self.setError("D6_C4_3", "Msg_656");
                    }
                    break;
                case SWITCH_VIEW.NUMERIC_NORMAL:
                    if (_.isEmpty(self.searchNum())) {
                        self.setError("D6_C4_4", "Msg_656");
                    }
                    break;
                case SWITCH_VIEW.NUMERIC_PERIOD:
                    if (_.isEmpty(self.searchNumStartVal())) {
                        self.setError("D6_C4_5", "Msg_656");
                    }
                    if (_.isEmpty(self.searchNumEndVal())) {
                        self.setError("D6_C4_6", "Msg_656");
                    }
                    break;
                case SWITCH_VIEW.DATE_NORMAL:
                    if (_.isEmpty(self.searchDate())) {
                        self.setError("D6_C4_7", "Msg_656");
                    }
                    break;
                case SWITCH_VIEW.DATE_PERIOD:
                    if (_.isEmpty(self.searchDateStart())) {
                        self.setError("D6_C4_8", "Msg_656");
                    }
                    if (_.isEmpty(self.searchDateEnd())) {
                        self.setError("D6_C4_9", "Msg_656");
                    }
                    break;
                case SWITCH_VIEW.TIME_NORMAL:
                    if (_.isEmpty(self.searchTime())) {
                        self.setError("D6_C4_10", "Msg_656");
                    }
                case SWITCH_VIEW.TIME_PERIOD:
                    if (_.isEmpty(self.searchTimeStartVal())) {
                        self.setError("D6_C4_11", "Msg_656");
                    }
                    if (_.isEmpty(self.searchTimeEndVal())) {
                        self.setError("D6_C4_12", "Msg_656");
                    }
                    break;
                case SWITCH_VIEW.INS_TIME_NORMAL:
                    if (_.isEmpty(self.searchClock())) {
                        self.setError("D6_C4_1", "Msg_656");
                    }
                    break;
                case SWITCH_VIEW.INS_TIME_PERIOD:
                    if (_.isEmpty(self.searchClockStartVal())) {
                        self.setError("D6_C4_14", "Msg_656");
                    }
                    if (_.isEmpty(self.searchClockEndVal())) {
                        self.setError("D6_C4_15", "Msg_656");
                    }
                    break;
                case SWITCH_VIEW.SEARCH_CODE_LIST:
                    if (_.isEmpty(self.joinedSearchCodeList())) {
                        self.setError("D6_C4_16", "Msg_656");
                    } else {
                        // 記号に「同じ(複数)」「同じでない(複数)」が選択されている場合
                        self.validateSearchCodeList("D6_C4_16");
                    }
                    break;
            }
        }

        /**
         * 外部出力条件登録チェック複数タイプ
         */
        validateSearchCodeList(control) {
            let self = this;
            let listSearchCode = _.split(self.joinedSearchCodeList(), ',')
            _.each(listSearchCode, item => {
                let searchCode = _.trim(item);
                // 検索コードがカテゴリ項目の型と同じ場合
                switch (self.dataType) {
                    case shareModel.ITEM_TYPE.CHARACTER:
                        break;
                    case shareModel.ITEM_TYPE.NUMERIC:
                        if (isNaN(searchCode)) {
                            self.setError(control, "Msg_760");
                            return false;
                        }
                        break;
                    case shareModel.ITEM_TYPE.DATE:
                        break;
                    case shareModel.ITEM_TYPE.TIME:
                        break;
                    case shareModel.ITEM_TYPE.INS_TIME:
                        break;
                }
                // 対象の値の桁数が「検索コード」の桁数より大きい場合
                if (searchCode.length > 20) {
                    self.setError(control, "Msg_1346");
                    return false;
                }
            })
        }

        removeValidate() {
            let self = this;
            switch (self.switchView()) {
                case SWITCH_VIEW.CHARACTER_NORMAL:
                    self.clearError("D6_C4_1");
                    break;
                case SWITCH_VIEW.CHARACTER_PERIOD:
                    self.clearError("D6_C4_2");
                    self.clearError("D6_C4_3");
                    break;
                case SWITCH_VIEW.NUMERIC_NORMAL:
                    self.clearError("D6_C4_4");
                    break;
                case SWITCH_VIEW.NUMERIC_PERIOD:
                    self.clearError("D6_C4_5");
                    self.clearError("D6_C4_6");
                    break;
                case SWITCH_VIEW.DATE_NORMAL:
                    self.clearError("D6_C4_7");
                    break;
                case SWITCH_VIEW.DATE_PERIOD:
                    self.clearError("D6_C4_8");
                    self.clearError("D6_C4_9");
                    break;
                case SWITCH_VIEW.TIME_NORMAL:
                    self.clearError("D6_C4_10");
                case SWITCH_VIEW.TIME_PERIOD:
                    self.clearError("D6_C4_11");
                    self.clearError("D6_C4_12");
                    break;
                case SWITCH_VIEW.INS_TIME_NORMAL:
                    self.clearError("D6_C4_13");
                    break;
                case SWITCH_VIEW.INS_TIME_PERIOD:
                    self.clearError("D6_C4_14");
                    self.clearError("D6_C4_15");
                    break;
                case SWITCH_VIEW.SEARCH_CODE_LIST:
                    self.clearError("D6_C4_16");
                    break;
            }
        }

        static getSwitchView(dataType: shareModel.ITEM_TYPE, conditionSymbol: shareModel.CONDITION_SYMBOL): SWITCH_VIEW {
            switch (dataType) {
                case shareModel.ITEM_TYPE.CHARACTER:
                    switch (conditionSymbol) {
                        case shareModel.CONDITION_SYMBOL.BETWEEN: return SWITCH_VIEW.CHARACTER_PERIOD;
                        case shareModel.CONDITION_SYMBOL.IN:
                        case shareModel.CONDITION_SYMBOL.NOT_IN: return SWITCH_VIEW.SEARCH_CODE_LIST;
                        default: return SWITCH_VIEW.CHARACTER_NORMAL;
                    }
                case shareModel.ITEM_TYPE.NUMERIC:
                    switch (conditionSymbol) {
                        case shareModel.CONDITION_SYMBOL.BETWEEN: return SWITCH_VIEW.NUMERIC_PERIOD;
                        case shareModel.CONDITION_SYMBOL.IN:
                        case shareModel.CONDITION_SYMBOL.NOT_IN: return SWITCH_VIEW.SEARCH_CODE_LIST;
                        default: return SWITCH_VIEW.NUMERIC_NORMAL;
                    }
                case shareModel.ITEM_TYPE.DATE:
                    switch (conditionSymbol) {
                        case shareModel.CONDITION_SYMBOL.BETWEEN: return SWITCH_VIEW.DATE_PERIOD;
                        case shareModel.CONDITION_SYMBOL.IN:
                        case shareModel.CONDITION_SYMBOL.NOT_IN: return SWITCH_VIEW.SEARCH_CODE_LIST;
                        default: return SWITCH_VIEW.DATE_NORMAL;
                    }
                case shareModel.ITEM_TYPE.TIME:
                    switch (conditionSymbol) {
                        case shareModel.CONDITION_SYMBOL.BETWEEN: return SWITCH_VIEW.TIME_PERIOD;
                        case shareModel.CONDITION_SYMBOL.IN:
                        case shareModel.CONDITION_SYMBOL.NOT_IN: return SWITCH_VIEW.SEARCH_CODE_LIST;
                        default: return SWITCH_VIEW.TIME_NORMAL;
                    }
                case shareModel.ITEM_TYPE.INS_TIME:
                    switch (conditionSymbol) {
                        case shareModel.CONDITION_SYMBOL.BETWEEN: return SWITCH_VIEW.INS_TIME_PERIOD;
                        case shareModel.CONDITION_SYMBOL.IN:
                        case shareModel.CONDITION_SYMBOL.NOT_IN: return SWITCH_VIEW.SEARCH_CODE_LIST;
                        default: return SWITCH_VIEW.INS_TIME_NORMAL;
                    }
            }
        }

        static fromApp(app) {
            let dto = new OutCndDetailItemDto(app.categoryId, app.categoryItemNo, app.seriNum,
                app.conditionSettingCd, app.conditionSymbol);
            dto.searchNum(app.searchNum);
            dto.searchNumEndVal(app.searchNumEndVal);
            dto.searchNumStartVal(app.searchNumStartVal);
            dto.searchChar(app.searchChar);
            dto.searchCharEndVal(app.searchCharEndVal);
            dto.searchCharStartVal(app.searchCharStartVal);
            dto.searchDate(app.searchDate);
            dto.searchDateEnd(app.searchDateEnd);
            dto.searchDateStart(app.searchDateStart);
            dto.searchClock(app.searchClock);
            dto.searchClockEndVal(app.searchClockEndVal);
            dto.searchClockStartVal(app.searchClockStartVal);
            dto.searchTime(app.searchTime);
            dto.searchTimeEndVal(app.searchTimeEndVal);
            dto.searchTimeStartVal(app.searchTimeStartVal);
            dto.joinedSearchCodeList(app.joinedSearchCodeList);
            return dto;
        }
    }
    class SearchCodeListDto {
        conditionSetCode: string;
        categoryId: number;
        categoryItemNo: number;
        seriNum: number;
        searchCode: string;
        searchItemName: string;
        constructor(conditionSetCode: string, categoryId: number, categoryItemNo: number,
            seriNum: number, searchCode: string, searchItemName: string) {
            let self = this;
            self.conditionSetCode = conditionSetCode;
            self.categoryId = categoryId;
            self.categoryItemNo = categoryItemNo;
            self.seriNum = seriNum;
            self.searchCode = searchCode;
            self.searchItemName = searchItemName;
        }
    }

    export enum SWITCH_VIEW {
        NONE = 0,
        CHARACTER_NORMAL = 1,
        CHARACTER_PERIOD = 2,
        //CHARACTER_LIST = 3,
        NUMERIC_NORMAL = 4,
        NUMERIC_PERIOD = 5,
        //NUMERIC_LIST = 6,
        DATE_NORMAL = 7,
        DATE_PERIOD = 8,
        //DATE_LIST = 9,
        TIME_NORMAL = 10,
        TIME_PERIOD = 11,
        //TIME_LIST = 12,
        INS_TIME_NORMAL = 13,
        INS_TIME_PERIOD = 14,
        //INS_TIME_LIST = 15
        SEARCH_CODE_LIST = 16
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