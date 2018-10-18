module nts.uk.pr.view.qmm001.a.viewmodel {
    import service = nts.uk.pr.view.qmm001.a.service;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import errors = nts.uk.ui.errors;
    import modal = nts.uk.ui.windows.sub.modal;
    export class ScreenModel {
        date: KnockoutObservable<string>;
        yearMonth: KnockoutObservable<number>;
        //list code left
        listItems: KnockoutObservableArray<SalGenParaIdentification> = ko.observableArray([]);
        selectedSalGenParaIdent: KnockoutObservable<number> = ko.observable(null);
        listHistory: KnockoutObservableArray<any> = ko.observable([]);
        selectedSalGenParaHistory: KnockoutObservable<string> = ko.observable('');
        modeScreen: KnockoutObservable<number> = ko.observable(MODESCREEN.NEW);
        self :any= this;
        itemsSwitchParaTargetAtr: KnockoutObservableArray<ItemModel> = ko.observableArray([
            { code: PARATARGETATR.TARGET+'', name:  getText('QMM001_26') },
            { code: PARATARGETATR.NOT_COVERED+'', name: getText('QMM001_27') }
        ]);
        itemsSwitchParaAvailable: KnockoutObservableArray<ItemModel> = ko.observableArray([
            { code: SWITCH_EFF_CATEGORY.UNAVAILABLE+'', name:  getText('QMM001_20') },
            { code: SWITCH_EFF_CATEGORY.AVAILABLE+'', name: getText('QMM001_21') }
        ]);
        selectedSwitchParaAvai: KnockoutObservable<number> = ko.observable(SWITCH_EFF_CATEGORY.UNAVAILABLE);
        selectedSwitchParaTargetAtr: KnockoutObservable<number> = ko.observable(PARATARGETATR.TARGET);
        salGenParaIdent: KnockoutObservable<SalGenParaIdentification> = ko.observable(new SalGenParaIdentification() );
        salGenParaHistory:KnockoutObservable<any> = ko.observable(new SalGenParaYearMonthHistory());
        salGenParaValue:KnockoutObservable<SalGenParaValue> = ko.observable(null);
        modeLoadItems: KnockoutObservable<number> = ko.observable(null);
        salGenParamOptions: KnockoutObservable<SalGenParamOptions> = ko.observable(null);
        //data combo box
        listSalGenOptions: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        valueComboBox :KnockoutObservable<number> = ko.observable(0);
        modeHistory:KnockoutObservable<number> = ko.observable(MODEHISTORY.YEARMONTH);
        isDisplayHis:KnockoutObservable<boolean> = ko.observable(false);


        constructor() {
            let self = this;
            self.initView();
            self.selectedSalGenParaIdent.subscribe((data) => {
                self.itemSelectionProcess(data);
                self.salGenParaIdent(_.find(self.listItems(), {'paraNo': data}));
            });
            self.selectedSalGenParaHistory.subscribe((data) => {
                if(data == HIS_ID_TEMP)
                    return;
                self.getSalGenParaValue(data);
                self.salGenParaHistory(_.find(self.listHistory(), {'historyId': data}));
                console.log(self.selectedSwitchParaAvai());
            });
            self.selectedSwitchParaAvai.subscribe((data) => {
                if(data == SWITCH_EFF_CATEGORY.AVAILABLE){
                    errors.clearAll();
                }

            });
        }

        initView() {
            let self = this;

            service.getAllSalGenParaIdentification().done((itemList: Array<ISalGenParaIdentification>) => {
                if (itemList && itemList.length > 0) {
                    self.listItems(itemList);
                    self.selectedSalGenParaIdent(self.listItems()[0].paraNo);

                } else {
                    self.listItems(new Array<ISalGenParaIdentification>());
                }
            }).fail(error => {
                dialog.alertError(error);
            });
        }


        /*アルゴリズム「項目選択処理」を実行する*/
        itemSelectionProcess(paraNo: number) {
            let self = this;
            let dataSelected: SalGenParaIdentification = _.find(self.listItems(), {'paraNo': paraNo});
            if (dataSelected.attributeType == PARAATTRITYPE.SELECTION) {
                service.getSalGenParamOptions(dataSelected.paraNo).done((item: Array<ISalGenParamOptions>) => {
                    self.listSalGenOptions(self.getListSalGenOptions(item));
                    self.salGenParamOptions(item);
                }).fail(error => {
                    dialog.alertError(error);
                });
            }
            switch (dataSelected.historyAtr) {
                case PARAHISTORYATR.YMDHIST : {
                    service.getSalGenParaDateHistory(dataSelected.paraNo).done((itemList: Array<ISalGenParaDateHistory>) => {
                        self.modeHistory(MODEHISTORY.DATE);
                        if (itemList && itemList.length > 0) {
                            self.listHistory(SalGenParaDateHistory.convertToDisplayHis(itemList));
                            self.selectedSalGenParaHistory(self.listHistory()[0].historyId);
                            self.modeScreen(MODESCREEN.UPDATE);
                            self.isDisplayHis(true);
                        }
                        else{
                            self.listHistory(itemList);
                            self.selectedSalGenParaHistory(HIS_ID_TEMP);
                            self.modeScreen(MODESCREEN.NEW);
                            self.selectedSwitchParaAvai(SWITCH_EFF_CATEGORY.UNAVAILABLE);
                            self.isDisplayHis(false);
                        }
                    });
                    break;

                }
                case PARAHISTORYATR.YMHIST: {
                    service.getSalGenParaYearMonthHistory(dataSelected.paraNo).done((itemList: Array<ISalGenParaYearMonthHistory>) => {
                        self.modeHistory(MODEHISTORY.YEARMONTH);
                        if (itemList && itemList.length > 0) {
                            self.listHistory(SalGenParaYearMonthHistory.convertToDisplayHis(itemList));
                            self.selectedSalGenParaHistory(self.listHistory()[0].historyId);
                            self.modeScreen(MODESCREEN.UPDATE);
                            self.isDisplayHis(true);
                        }
                        else{
                            self.selectedSwitchParaAvai(SWITCH_EFF_CATEGORY.UNAVAILABLE);
                            self.selectedSalGenParaHistory(HIS_ID_TEMP);
                            self.listHistory(itemList);
                            self.modeScreen(MODESCREEN.NEW);
                            self.isDisplayHis(false);
                        }

                    });
                    break;

                }
                case PARAHISTORYATR.DONOTMANAGE: {
                    service.getSalGenParaYearMonthHistory(dataSelected.paraNo).done((itemList: Array<ISalGenParaYearMonthHistory>) => {
                        self.modeHistory(MODEHISTORY.YEARMONTH);
                        if (itemList && itemList.length > 0) {
                            self.listHistory(SalGenParaYearMonthHistory.convertToDisplayHis(itemList));
                            self.selectedSalGenParaHistory(self.listHistory()[0].historyId);
                            self.modeScreen(MODESCREEN.UPDATE);
                            self.isDisplayHis(true);
                        }
                        else{

                            self.selectedSwitchParaAvai(SWITCH_EFF_CATEGORY.UNAVAILABLE);
                            self.selectedSalGenParaHistory(HIS_ID_TEMP);
                            self.listHistory(itemList);
                            self.modeScreen(MODESCREEN.NEW);
                            self.isDisplayHis(false);
                        }
                    });
                    break;

                }
            }
        }
        isOpenDialogB(){
            let self = this;
            if(self.modeScreen() == MODESCREEN.ADD || self.salGenParaIdent().historyAtr == PARAHISTORYATR.DONOTMANAGE){
                return false;
            }
            if(self.modeScreen() == MODESCREEN.NEW){
                return true;
            }
            return true;
        }
        getHistory(){

        }

        getTextEnumParaAttriType(data) {
            let self = this;
            switch (data){
                case PARAATTRITYPE.NUMBER:{
                    return '数値';
                }
                case PARAATTRITYPE.SELECTION:{
                    return '選択肢';
                }
                case PARAATTRITYPE.TARGET_EXEMPT:{
                    return '対象/対象外';
                }
                case PARAATTRITYPE.TIME:{
                    return '時間';
                }
                case PARAATTRITYPE.TEXT:{
                    return '文字';
                }
            }
        }
        getListSalGenOptions(data:Array<ISalGenParamOptions>): Array<ItemModel> {
            let resulf : Array<ItemModel>  = [];
            _.forEach(data, function(value) {
                resulf.push(new ItemModel(value.optionNo, value.optionName));
            });
            return resulf;
        }


        getTextEnumParaHisAtr(data) {
            let self = this;
            switch (data){
                case PARAHISTORYATR.YMDHIST:{
                    return '年月日履歴';
                }
                case PARAHISTORYATR.YMHIST:{
                    return '年月履歴';
                }
                case PARAHISTORYATR.DONOTMANAGE:{
                    return '履歴管理しない';
                }
            }
        }
        convertGeneralDateToString(yearMonth: any) {
            let self = this;
            let year: string, month: string, day:string;
            yearMonth = yearMonth.toString();
            year = yearMonth.slice(0, 4);
            month = yearMonth.slice(4, 6);
            day = yearMonth.slice(6, 8);
            return year + "/" + month+"/"+day;
        }
        convertMonthYearToString(yearMonth: any) {
            let self = this;
            let year: string, month: string;
            yearMonth = yearMonth.toString();
            year = yearMonth.slice(0, 4);
            month = yearMonth.slice(4, 6);
            return year + "/" + month;
        }

        getSalGenParaValue(hisId: string) {
            let self  = this;
            service.getSalGenParaValue(hisId).done((item: ISalGenParaValue) => {
                if(item==null)
                    return;
                self.selectedSwitchParaAvai(item.availableAtr);
                self.selectedSwitchParaTargetAtr(item.targetAtr);
                self.salGenParaValue(item);
                self.valueComboBox(item.selection);
            }).fail(error => {
                dialog.alertError(error);
            });
        }

        register() {
            let self = this;
            if(errors.hasError()){
                return;
            }
            self.salGenParaValue().selection = self.valueComboBox();
            let data: any = {
                historyId: self.selectedSalGenParaHistory(),
                selection: self.salGenParaValue().selection,
                availableAtr: self.salGenParaValue().availableAtr,
                numValue: self.salGenParaValue().numValue,
                charValue: self.salGenParaValue().charValue,
                timeValue: self.salGenParaValue().timeValue,
                targetAtr: self.salGenParaValue().targetAtr,
                modeScreen: self.modeScreen()
            };
            service.addSelectionProcess(data).done(() => {
                if (self.modeScreen()== MODESCREEN.ADD){
                    self.initView();
                } else {

                }
            }).fail(error => {
                dialog.alertError(error);
            });


        }
        isEnableSwitchButton(){
            let self = this;
            if(MODESCREEN.UPDATE && self.salGenParaIdent()==PARAHISTORYATR.DONOTMANAGE){
                return false;
            }
            return true;
        }

        isOpenDiaLogC(){
            let self = this;
            if(self.modeScreen() == MODESCREEN.NEW || self.modeScreen() == MODESCREEN.ADD || self.salGenParaIdent().historyAtr ==PARAHISTORYATR.DONOTMANAGE){
                return false;
            }
            return true;
        }


        creatPdf() {

        }

        remove() {

        }
        openDialogB(){
            let self = this;
            modal("/view/qmm/001/b/index.xhtml").onClosed(() => {
                self.modeScreen(MODESCREEN.ADD);
            });
        }
        openDialogC(){
            let self = this;
            modal("/view/qmm/001/c/index.xhtml").onClosed(() => {
                self.modeScreen(MODESCREEN.ADD);
            });
        }
        removeHistory(){

        }
    }

    interface ISalGenParaIdentification {
        /**
         * パラメータNo
         */
        paraNo: string;

        /**
         * 会社ID
         */
        cID: string;

        /**
         * 名称
         */
        name: string;

        /**
         * 属性区分
         */
        attributeType: number;

        /**
         * 履歴区分
         */
        historyAtr: number;
        /**
         * 補足説明
         */
        explanation: number;

    }

    class SalGenParaIdentification {

        paraNo: KnockoutObservable<string> = ko.observable('');
        cID: KnockoutObservable<string> = ko.observable('');
        name: KnockoutObservable<string> = ko.observable('');

        attributeType: KnockoutObservable<number> = ko.observable(1);
        historyAtr: KnockoutObservable<number> = ko.observable(null);
        explanation: KnockoutObservable<number> = ko.observable(null);


        constructor() {

        }
    }

    interface ISalGenParaDateHistory {
        /**
         * パラメータNo
         */
        paraNo: string;

        /**
         * 会社ID
         */
        cID: string;

        /**
         * 履歴ID
         */
        historyId: string;

        /**
         * 開始日
         */
        startDate: Date;

        /**
         * 終了日
         */
        endDate: Date;
    }

    class SalGenParaDateHistory {

        paraNo: KnockoutObservable<string> = ko.observable('');
        cID: KnockoutObservable<string> = ko.observable('');
        historyId: KnockoutObservable<string> = ko.observable('');

        startDate: KnockoutObservable<Date> = ko.observable(0);
        endDate: KnockoutObservable<Date> = ko.observable(0);
        display: KnockoutObservable<string> = ko.observable('');

        constructor() {


        }
        static convertToDisplayHis(app) {
            let to :string = getText('QMM001_13');
            let listEmp = [];
            _.each(app, (item) => {
                let dto: SalGenParaDateHistory = new SalGenParaDateHistory();
                dto.paraNo = item.paraNo;
                dto.cID = item.cID;
                dto.historyId = item.historyId;
                dto.startDate = item.startDate;
                dto.endDate = item.endDate;
                dto.display =item.startDate+ " " + to + " "+item.endDate;
                listEmp.push(dto);
            })
            return listEmp;
        }
        static convertGeneralDateToString(yearMonth: any) {
            let self = this;
            let year: string, month: string, day:string;
            yearMonth = yearMonth.toString();
            year = yearMonth.slice(0, 4);
            month = yearMonth.slice(4, 6);
            day = yearMonth.slice(6, 8);
            return year + "/" + month+"/"+day;
        }
    }

    interface ISalGenParaYearMonthHistory {
        /**
         * パラメータNo
         */
        paraNo: string;

        /**
         * 会社ID
         */
        cID: string;

        /**
         * 履歴ID
         */
        historyId: string;

        /**
         * 開始日
         */
        startYearMonth: number;

        /**
         * 終了日
         */
        endYearMonth: number;
    }

    class SalGenParaYearMonthHistory {

        paraNo: KnockoutObservable<string> = ko.observable('');
        cID: KnockoutObservable<string> = ko.observable('');
        historyId: KnockoutObservable<string> = ko.observable('');

        startYearMonth: KnockoutObservable<number> = ko.observable(0);
        endYearMonth: KnockoutObservable<number> = ko.observable(0);

        display: KnockoutObservable<string> = ko.observable('');


        constructor() {

        }
        static convertToDisplayHis(app) {
            let to :string = getText('QMM001_13');
            let listEmp = [];
            _.each(app, (item) => {
                let dto: SalGenParaYearMonthHistory = new SalGenParaYearMonthHistory();
                dto.paraNo = item.paraNo;
                dto.cID = item.cID;
                dto.historyId = item.historyId;
                dto.startYearMonth = item.startYearMonth;
                dto.endYearMonth = item.endYearMonth;
                dto.display =this.convertMonthYearToString(item.startYearMonth) + " " + to + " "+this.convertMonthYearToString(item.endYearMonth);
                listEmp.push(dto);
            })
            return listEmp;
        }
        static convertMonthYearToString(yearMonth: any) {
            let self = this;
            let year: string, month: string;
            yearMonth = yearMonth.toString();
            year = yearMonth.slice(0, 4);
            month = yearMonth.slice(4, 6);
            return year + "/" + month;
        }
    }

    interface ISalGenParaValue {
        /**
         * 履歴ID
         */
        historyId: string;

        /**
         * 選択肢
         */
        selection: number;

        /**
         * 有効区分
         */
        availableAtr: number;

        /**
         * 値（数値）
         */
        numValue: string;

        /**
         * 値（文字）
         */
        charValue: string;

        /**
         * 値（時間）
         */
        timeValue: number;

        /**
         * 対象区分
         */
        targetAtr: number;
    }

    class SalGenParaValue {

        historyId: KnockoutObservable<string> = ko.observable('');
        numValue: KnockoutObservable<string> = ko.observable('');
        charValue: KnockoutObservable<string> = ko.observable('');

        selection: KnockoutObservable<number> = ko.observable(0);
        availableAtr: KnockoutObservable<number> = ko.observable(0);
        timeValue: KnockoutObservable<number> = ko.observable(0);
        targetAtr: KnockoutObservable<number> = ko.observable(0);

        constructor() {

        }
    }

    interface ISalGenParamOptions {
        /**
         * パラメータNo
         */
        paraNo: string;

        /**
         * 会社ID
         */
        cID: string;

        /**
         * 選択肢No
         */
        optionNo: number;

        /**
         * 選択肢名称
         */
        optionName: string;
    }

    class SalGenParamOptions {

        paraNo: KnockoutObservable<string> = ko.observable('');
        cID: KnockoutObservable<string> = ko.observable('');
        optionName: KnockoutObservable<string> = ko.observable('');

        optionNo: KnockoutObservable<number> = ko.observable(null);


        constructor(param: ISalGenParamOptions) {
            let self = this;
            self.paraNo(param.paraNo || '');
            self.cID(param.cID || '');
            self.optionName(param.optionName || '');

            self.optionNo(param.optionNo);

        }
    }

    class SalGenParaValueCommand {

        /**
         * 履歴ID
         */
        historyId: string;

        /**
         * 選択肢
         */
        selection: number;

        /**
         * 有効区分
         */
        availableAtr: number;

        /**
         * 値（数値）
         */
        numValue: string;

        /**
         * 値（文字）
         */
        charValue: string;

        /**
         * 値（時間）
         */
        timeValue: number;

        /**
         * 対象区分
         */
        targetAtr: number;

        modeScreen: number;
    }


    export enum PARAATTRITYPE {
        TEXT = 0,
        NUMBER = 1,
        TIME = 2,
        TARGET_EXEMPT = 3,
        SELECTION = 4
    }

    export enum PARAHISTORYATR {
        /*年月日履歴*/
        YMDHIST = 0,
        /*年月履歴*/
        YMHIST = 1,
        /*履歴管理しない*/
        DONOTMANAGE = 2
    }

    export enum MODESCREEN {
        UPDATE = 0,
        ADD = 1,
        NEW = 2
    }
    export enum PARAAVAILABLEVALUE {
        /*無効*/
        UNAVAILABLE= 0 ,
        /*有効*/
        AVAILABLE = 1
    }
    export class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    export enum MODEHISTORY {

        DATE= 0 ,

        YEARMONTH = 1
    }
    export enum SWITCH_EFF_CATEGORY {

        UNAVAILABLE = 0 ,

        AVAILABLE = 1
    }
    export enum PARATARGETATR {
        /*対象*/
        TARGET = 0,
        /*対象外*/
        NOT_COVERED = 1
    }


    export const HIS_ID_TEMP = "00000devphuc.tc";

}
