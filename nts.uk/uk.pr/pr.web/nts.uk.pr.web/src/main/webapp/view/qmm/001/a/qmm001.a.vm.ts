module nts.uk.pr.view.qmm001.a.viewmodel {
    import service = nts.uk.pr.view.qmm001.a.service;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import errors = nts.uk.ui.errors;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
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
            { code: SWITCH_EFF_CATEGORY.AVAILABLE+'', name:  getText('QMM001_20') },
            { code: SWITCH_EFF_CATEGORY.UNAVAILABLE+'', name: getText('QMM001_21') }
        ]);
        selectedSwitchParaAvai: KnockoutObservable<number> = ko.observable(SWITCH_EFF_CATEGORY.AVAILABLE);
        selectedSwitchParaTargetAtr: KnockoutObservable<number> = ko.observable(PARATARGETATR.TARGET);
        salGenParaIdent: KnockoutObservable<SalGenParaIdentification> = ko.observable(new SalGenParaIdentification() );
        salGenParaHistory:KnockoutObservable<any> = ko.observable(new SalGenParaYearMonthHistory());
        salGenParaValue:KnockoutObservable<SalGenParaValue> = ko.observable(new SalGenParaValue());
        modeLoadItems: KnockoutObservable<number> = ko.observable(null);
        salGenParamOptions: KnockoutObservable<SalGenParamOptions> = ko.observable(null);
        //data combo box
        listSalGenOptions: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        valueComboBox :KnockoutObservable<number> = ko.observable(null);
        modeHistory:KnockoutObservable<number> = ko.observable(MODEHISTORY.YEARMONTH);
        isDisplayHis:KnockoutObservable<boolean> = ko.observable(false);

        // value edit text
        value: KnockoutObservable<number>= ko.observable(null);
        // Var check can copy hisstory befor
        isCopy :KnockoutObservable<number>= ko.observable(null);

        constructor() {
            let self = this;
            self.initView();
            self.selectedSalGenParaIdent.subscribe((data) => {
                self.itemSelectionProcess(data,FIRST);
                self.salGenParaIdent(_.find(self.listItems(), {'paraNo': data}));
                setTimeout(function () {
                    nts.uk.ui.errors.clearAll()
                }, 50)
            });
            self.selectedSalGenParaHistory.subscribe((data) => {
                if(data == null){
                    return;
                }
                self.getSalGenParaValue(data, self.isCopy());
                self.salGenParaHistory(_.find(self.listHistory(), {'historyId': data}));
                setTimeout(function () {
                    nts.uk.ui.errors.clearAll()
                }, 50)

            });
            self.selectedSwitchParaAvai.subscribe((data) => {
                if(data == SWITCH_EFF_CATEGORY.UNAVAILABLE){
                    errors.clearAll();
                    self.value(null);
                    return;
                }
            });


        }

        initView() {
            let self = this;

            service.getAllSalGenParaIdentification().done((itemList: Array<ISalGenParaIdentification>) => {
                if (itemList && itemList.length > 0) {
                    self.listItems(itemList);
                    self.selectedSalGenParaIdent(self.listItems()[0].paraNo);
                    $('#A4_2').focus();

                } else {
                    self.listItems(new Array<ISalGenParaIdentification>());
                }
            }).fail(error => {
                dialog.alertError(error);
            });
        }


        /*アルゴリズム「項目選択処理」を実行する*/
        itemSelectionProcess(paraNo: number,indexFocus:number) {
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
                            self.selectedSalGenParaHistory(null);
                            self.selectedSalGenParaHistory(self.listHistory()[indexFocus].historyId);
                            self.modeScreen(MODESCREEN.UPDATE);
                            self.isDisplayHis(true);
                        }
                        else{
                            self.value(null);
                            self.salGenParaValue(itemList);
                            self.listHistory(itemList);
                            self.modeScreen(MODESCREEN.NEW);
                            self.selectedSwitchParaAvai(SWITCH_EFF_CATEGORY.AVAILABLE);
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
                            self.selectedSalGenParaHistory(null);
                            self.selectedSalGenParaHistory(self.listHistory()[indexFocus].historyId);
                            self.modeScreen(MODESCREEN.UPDATE);
                            self.isDisplayHis(true);
                        }
                        else{
                            self.value(null);
                            self.salGenParaValue(itemList);
                            self.selectedSwitchParaAvai(SWITCH_EFF_CATEGORY.AVAILABLE);
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
                            self.selectedSalGenParaHistory(null);
                            self.selectedSalGenParaHistory(self.listHistory()[indexFocus].historyId);
                            self.modeScreen(MODESCREEN.UPDATE);
                            self.isDisplayHis(true);
                        }
                        else{
                            self.value(null);
                            self.salGenParaValue(itemList);
                            self.selectedSwitchParaAvai(SWITCH_EFF_CATEGORY.AVAILABLE);
                            self.listHistory(itemList);
                            self.modeScreen(MODESCREEN.NEW);
                            self.isDisplayHis(false);
                        }
                    });
                    break;

                }
            }
        }
        isEnableSubmit(){
            if(this.listHistory().length == 0 ){
                return false;

            }
            return true;
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
                    return getText('Enum_ParaAttributeType_NUMBER');
                }
                case PARAATTRITYPE.SELECTION:{
                    return getText('Enum_ParaAttributeType_TARGET_SELECTION');
                }
                case PARAATTRITYPE.TARGET_EXEMPT:{
                    return getText('Enum_ParaAttributeType_TARGET_EXEMPT');
                }
                case PARAATTRITYPE.TIME:{
                    return getText('Enum_ParaAttributeType_TIME');
                }
                case PARAATTRITYPE.TEXT:{
                    return getText('Enum_ParaAttributeType_TEXT');
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
                    return getText('Enum_ParaHistoryAtr_YMDHIST');
                }
                case PARAHISTORYATR.YMHIST:{
                    return getText('Enum_ParaHistoryAtr_YMHIST');
                }
                case PARAHISTORYATR.DONOTMANAGE:{
                    return getText('Enum_ParaHistoryAtr_DONOTMANAGE');
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

        getSalGenParaValue(hisId: string, type:number) {
            let self  = this;
            if(hisId != HIS_ID_TEMP){
                service.getSalGenParaValue(hisId).done((item: SalGenParaValue) => {
                    if(item==null){
                        return;
                    }
                    self.salGenParaValue(item);
                    switch (this.salGenParaIdent().attributeType){
                        case PARAATTRITYPE.TIME:{
                            self.value(item.timeValue);
                            break;
                        };
                        case PARAATTRITYPE.TEXT:{
                            self.value(item.charValue);
                            break;
                        };
                        case PARAATTRITYPE.NUMBER:{
                            self.value(item.numValue);
                            break;
                        };
                        default :{
                            self.value(null);
                        }
                    }
                    self.selectedSwitchParaAvai(item.availableAtr);
                    self.selectedSwitchParaTargetAtr((item.targetAtr == null)? PARATARGETATR.TARGET : item.targetAtr);
                    self.valueComboBox((item.selection == null)? null : item.selection);
                }).fail(error => {
                    dialog.alertError(error);
                });
            }
            else{
                let dataSalGenValueTemp : any = null;
                if(self.listHistory().length > 1){
                    if(type == TAKEOVER.DONTCOPY){
                        dataSalGenValueTemp  = {
                            historyId :hisId ,
                            selection :null ,
                            availableAtr: SWITCH_EFF_CATEGORY.AVAILABLE,
                            numValue :null,
                            charValue :null,
                            timeValue :null,
                            targetAtr: (self.salGenParaIdent().attributeType == nts.uk.pr.view.qmm001.a.viewmodel.PARAATTRITYPE.TARGET_EXEMPT) ? PARATARGETATR.TARGET : null
                        };
                        self.value(null);
                        self.selectedSwitchParaAvai(SWITCH_EFF_CATEGORY.AVAILABLE);
                    }
                    else{
                        dataSalGenValueTemp  ={
                            historyId :hisId ,
                            selection :self.salGenParaValue().selection ,
                            availableAtr: self.salGenParaValue().availableAtr,
                            numValue :self.salGenParaValue().numValue,
                            charValue :self.salGenParaValue().charValue ,
                            timeValue :self.salGenParaValue().timeValue,
                            targetAtr: self.salGenParaValue().targetAtr
                        };
                    }
                }
                else{
                    dataSalGenValueTemp  = {
                        historyId :hisId ,
                        selection :null ,
                        availableAtr: SWITCH_EFF_CATEGORY.AVAILABLE,
                        numValue :null,
                        charValue :null,
                        timeValue :null,
                        targetAtr: (self.salGenParaIdent().attributeType == nts.uk.pr.view.qmm001.a.viewmodel.PARAATTRITYPE.TARGET_EXEMPT) ? PARATARGETATR.TARGET : null
                    };
                    self.value(null);
                    self.isDisplayHis(true);
                }
                self.salGenParaValue(dataSalGenValueTemp);

            }

        }


        register() {
            let self = this;
            $('#A4_2').focus();
            if( self.selectedSwitchParaAvai() == SWITCH_EFF_CATEGORY.AVAILABLE ){
                self.validate();
            }

            if(errors.hasError()){
                return;
            }
            if(self.salGenParaValue() != null)
            {
                switch (self.salGenParaIdent().attributeType){
                    case PARAATTRITYPE.NUMBER:{
                        self.salGenParaValue().numValue = self.value();
                        break;
                    }
                    case PARAATTRITYPE.TIME :{
                        self.salGenParaValue().timeValue = self.value();
                        break;
                    }
                    case PARAATTRITYPE.TEXT :{
                        self.salGenParaValue().charValue = (self.value()=="")? null : self.value();
                        break;
                    }

                };
            }
            let dataHisValue: any = null;
            let valueCombobox : number = null;
            if(self.salGenParaIdent().attributeType == PARAATTRITYPE.SELECTION){
                valueCombobox =(self.valueComboBox() == null)? null : self.valueComboBox();
            }
            if(self.salGenParaIdent().attributeType != PARAATTRITYPE.TARGET_EXEMPT){
                self.selectedSwitchParaTargetAtr(null);
            }
            let dataValue: any = {
                historyId: self.selectedSalGenParaHistory(),
                selection: (self.selectedSwitchParaAvai() == SWITCH_EFF_CATEGORY.UNAVAILABLE ) ? null : valueCombobox,
                availableAtr: self.selectedSwitchParaAvai(),
                numValue: (self.salGenParaValue().numValue == null ? null : self.salGenParaValue().numValue),
                charValue: (self.salGenParaValue().charValue == null ? null : self.salGenParaValue().charValue),
                timeValue:(self.salGenParaValue().timeValue == null ? null : self.salGenParaValue().timeValue),
                targetAtr: (self.selectedSwitchParaAvai() == SWITCH_EFF_CATEGORY.UNAVAILABLE ) ? null : self.selectedSwitchParaTargetAtr(),
                modeScreen: self.modeScreen()
            };
            dataHisValue = {
                paraNo: "",
                startTime: "",
                endTime: "",
                modeHistory :self.modeHistory(),
                mSalGenParaValueCommand: dataValue
            };
            if(self.modeScreen() == MODESCREEN.ADD){
                self.valueComboBox(FIRST);
                if(self.modeHistory() == MODEHISTORY.YEARMONTH){
                    dataHisValue = {
                        paraNo: self.listHistory()[0].paraNo,
                        startTime: self.listHistory()[0].startYearMonth,
                        endTime: self.listHistory()[0].endYearMonth,
                        modeHistory : self.modeHistory(),
                        mSalGenParaValueCommand: dataValue
                    };                }
                else{
                    dataHisValue = {
                        paraNo: self.listHistory()[0].paraNo,
                        startTime: self.listHistory()[0].startDate,
                        endTime: self.listHistory()[0].endDate,
                        modeHistory : self.modeHistory(),
                        mSalGenParaValueCommand: dataValue
                    };
                }
            }
            service.addSelectionProcess(dataHisValue).done(() => {
                if (self.modeScreen()== MODESCREEN.ADD){
                    let paraNo : number =  self.selectedSalGenParaIdent();
                    self.itemSelectionProcess(paraNo,FIRST);
                    self.salGenParaIdent(_.find(self.listItems(), {'paraNo': paraNo}));
                }
                nts.uk.ui.dialog.info({ messageId: "Msg_15" });
            }).fail(error => {
                dialog.alertError(error);
            });


        }
        setFocusEditText(){
            let self = this;
            switch (this.salGenParaIdent().attributeType){
                case PARAATTRITYPE.TIME:{
                    $('#A4_8').focus();
                    break;
                };
                case PARAATTRITYPE.TEXT:{
                    $('#A4_10').focus();
                    break;
                };
                case PARAATTRITYPE.NUMBER:{
                    $('#A4_6').focus();
                    break;
                };
            }
        }
        addHistory(start: number){
            let self = this;
            let to :string = getText('QMM001_13');
            let list: Array<any> = self.listHistory();
            let newHistory :any = null;
            if(self.modeHistory() == MODEHISTORY.DATE){
                let year :string = start.toString().slice(0,4);
                let month:string = start.toString().slice(4,6);
                let day:string = start.toString().slice(6,8);
                let startDate :string = year+"-"+month+"-"+day;
                let data:any = {
                    paraNo : self.selectedSalGenParaIdent(),
                    cID :"",
                    historyId : self.selectedSalGenParaHistory(),
                    startDate :startDate,
                    endDate:"9999-12-31"
                };
                service.getListHistory(data).done((data) => {
                    self.listHistory(SalGenParaDateHistory.convertToDisplayHis(data));
                    self.listHistory(_.orderBy(self.listHistory(), ['startDate'], ['desc']));
                    self.selectedSalGenParaHistory(self.listHistory()[0].historyId);
                }).fail(error => {
                    dialog.alertError(error);
                });
            }
            else{
                newHistory = new SalGenParaYearMonthHistory();
                newHistory.paraNo = self.selectedSalGenParaIdent();
                newHistory.cID ='';
                newHistory.historyId = HIS_ID_TEMP;
                newHistory.startYearMonth = start;
                newHistory.endYearMonth = '999912';
                newHistory.display= this.convertMonthYearToString(newHistory.startYearMonth) + " " + to + " "+this.convertMonthYearToString(newHistory.endYearMonth);
                if (list && list.length > 0) {
                    let end = Number(start.toString().slice(4, 6)) == 1 ? (start - 89) : (start - 1);
                    list[FIRST].display = self.convertMonthYearToString(list[FIRST].startYearMonth) + " "+ to + " "+ self.convertMonthYearToString((end).toString());
                }
                self.listHistory().push(newHistory);
                self.listHistory(_.orderBy(self.listHistory(), ['startYearMonth'], ['desc']));
                self.selectedSalGenParaHistory(self.listHistory()[0].historyId);
            }

        }
        validate(){
            $("#A4_10").trigger("validate");
            $("#A4_6").trigger("validate");
            $("#A4_8").trigger("validate");
            $("#A4_17").trigger("validate");
        }
        isEnableSwitchButton(){
            let self = this;
            if(self.modeScreen() ==  MODESCREEN.UPDATE && self.salGenParaIdent().historyAtr ==PARAHISTORYATR.DONOTMANAGE){
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
            let  startDate :any = null;
            if(self.listHistory()[0] != null){
                if(self.salGenParaIdent().historyAtr == MODEHISTORY.YEARMONTH){
                    startDate = self.listHistory()[0].startYearMonth;
                }
                else{
                    startDate = self.listHistory()[0].startDate;
                }
            }
             let data:any = {
                start : startDate ,
                code: self.salGenParaIdent().paraNo,
                name: self.salGenParaIdent().name,
                historyAtr : self.salGenParaIdent().historyAtr
            };
            setShared('QMM001_PARAMS_TO_SCREEN_B', data);
            modal("/view/qmm/001/b/index.xhtml").onClosed(() => {
                let params = getShared('QMM011_A');
                let start :number = 0;
                if (params == null || params === undefined) {
                    return;
                }
                if(self.modeHistory()== MODEHISTORY.YEARMONTH){
                    start = params.startYearMonth;
                }
                else{
                    start = Number(params.startYearMonthDay);
                }
                self.isCopy(params.takeOver);
                self.addHistory(start);
                self.modeScreen(MODESCREEN.ADD);
                self.isDisplayHis(true);
            });
        }
        openDialogC(){
            let self = this;
            let historyId : string = self.selectedSalGenParaHistory();
            let isFirst :boolean = (_.findIndex(self.listHistory(), function(o) { return o.historyId == historyId; }) == 0 && self.listHistory().length > 1 ) ? true :false ;
            let index : number =_.findIndex(self.listHistory(), function(o) { return o.historyId == historyId; });
            let startLastYearMonth : any = 0;
            let startLastDate : any = 0;


            let data:any = null ;
            if(self.modeHistory() == MODEHISTORY.YEARMONTH){
                if(self.listHistory().length ==1 || index ==self.listHistory().length-1){
                    startLastYearMonth = 0;
                }
                else {
                    startLastYearMonth = self.listHistory()[index+1].startYearMonth;
                }
                data = {
                    start : self.listHistory()[index].startYearMonth ,
                    end : self.listHistory()[index].endYearMonth ,
                    code: self.salGenParaIdent().paraNo,
                    name: self.salGenParaIdent().name,
                    historyAtr : self.salGenParaIdent().historyAtr,
                    hisId : historyId,
                    isFirst : isFirst,
                    startLastYearMonth : startLastYearMonth

                };
            }
            else{
                if(self.listHistory().length ==1 || index ==self.listHistory().length-1){
                    startLastDate = 0;
                }
                else {
                    startLastDate = self.listHistory()[index+1].startDate;
                }
                data  = {
                    start : self.listHistory()[index].startDate ,
                    end : self.listHistory()[index].endDate ,
                    code: self.salGenParaIdent().paraNo,
                    name: self.salGenParaIdent().name,
                    historyAtr : self.salGenParaIdent().historyAtr,
                    hisId : historyId,
                    isFirst : isFirst,
                    startLastDate : startLastDate

                };
            }
            setShared('QMM001_PARAMS_TO_SCREEN_C', data);
            modal("/view/qmm/001/c/index.xhtml").onClosed(() => {
                let params = getShared('QMM001_C_PARAMS_OUTPUT');
                if (params == null || params === undefined) {
                    return;
                }
                let paraNo : number =  self.selectedSalGenParaIdent();
                self.itemSelectionProcess(paraNo,index);
                self.salGenParaIdent(_.find(self.listItems(), {'paraNo': paraNo}));
                self.modeScreen(MODESCREEN.ADD);
                self.isDisplayHis(true);

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

        historyId: KnockoutObservable<string>= ko.observable('');
        numValue: KnockoutObservable<string>= ko.observable('');
        charValue: KnockoutObservable<string>= ko.observable('');

        selection: KnockoutObservable<number>= ko.observable(null);
        availableAtr: KnockoutObservable<number>= ko.observable(0);
        timeValue: KnockoutObservable<number>= ko.observable(null);
        targetAtr: KnockoutObservable<number>= ko.observable(0);

        constructor() {

        }
        static fromapp(params:ISalGenParaValue){
            let dto: SalGenParaValue = new SalGenParaValue();
            dto.historyId = ko.observable(params.historyId);
            dto.numValue = ko.observable(params.numValue);
            dto.charValue = ko.observable(params.charValue);

            dto.selection = ko.observable(params.selection);
            dto.availableAtr = ko.observable(params.availableAtr);
            dto.timeValue = ko.observable(params.timeValue);
            dto.targetAtr = ko.observable(params.targetAtr);
            return dto;
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

    class SalGenParaDateParams {
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
        startDate: string;

        /**
         * 終了日
         */
        endDate: string;
        constructor(paraNo: string,cID: string,historyId: string,startDate: string,endDate: string) {
            this.paraNo = paraNo;
            this.cID = cID;
            this.historyId = historyId;
            this.startDate = startDate;
            this.endDate = endDate;
        }
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
    export enum TAKEOVER {

        COPY = 0,
        DONTCOPY = 1
    }


    export const HIS_ID_TEMP = "00000HISIDTEMP";
    export const FIRST = 0;

}
