module nts.uk.pr.view.qmm007.a.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import model = qmm007.share.model;
    import block = nts.uk.ui.block;

    export class ScreenModel {

        newHisId: KnockoutObservable<string> = ko.observable('');
        enableEditHistoryButton: KnockoutObservable<boolean> = ko.observable(true);
        enableAddHistoryButton: KnockoutObservable<boolean> = ko.observable(true);
        enableNotes:KnockoutObservable<boolean> = ko.observable(true);
        enableSalary: KnockoutObservable<boolean> = ko.observable(true);
        enableButtonRegistration: KnockoutObservable<boolean> = ko.observable(true);
        enableFixedWageClassList: KnockoutObservable<boolean> = ko.observable(true);
        enableAmountOfMoney: KnockoutObservable<boolean> = ko.observable(true);
        enableYearMonth: KnockoutObservable<boolean> = ko.observable(true);
        enableName: KnockoutObservable<boolean> = ko.observable(true);
        enableCode: KnockoutObservable<boolean> = ko.observable(true);
        isExist:  KnockoutObservable<boolean> = ko.observable(false);
        mode: KnockoutObservable<number> = ko.observable(null);
        currentSelected: KnockoutObservable<string> = ko.observable('');
        historyTakeover: KnockoutObservable<number> = ko.observable(0);
        amountOfMoney: KnockoutObservable<number> = ko.observable(null);
        notes: KnockoutObservable<string> = ko.observable('');
        dataSource: KnockoutObservableArray<Node> = ko.observableArray([]);
        singleSelectedCode: KnockoutObservable<string> = ko.observable('');

        //formlable
        inline: KnockoutObservable<boolean>;
        required: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        enableTargetClassification: KnockoutObservable<boolean>  = ko.observable(true);
        //
        texteditor: any;
        code: KnockoutObservable<string> = ko.observable('');
        name: KnockoutObservable<string> = ko.observable('');
        currentName: KnockoutObservable<string> = ko.observable('');
        value: KnockoutObservable<string> = ko.observable('');
        //datepicker
        date: KnockoutObservable<string>;
        yearMonth: KnockoutObservable<number> = ko.observable();
        newYearMonth: KnockoutObservable<number> = ko.observable();
        monthlyCalendar: KnockoutObservable<string> = ko.observable('');
        endYearMonth: KnockoutObservable<number> = ko.observable('999912');

        currencyeditor: any;
        //
        fixedWageClassList:  KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number> = ko.observable(0);

        enable: KnockoutObservable<boolean> ;
        //
        targetClassification: KnockoutObservableArray<any>;

        selectedTargetClass: KnockoutObservable<number> = ko.observable(0);
        selectedMonthSalaryPerDay: KnockoutObservable<number> = ko.observable(0);
        selectedADayPayee: KnockoutObservable<number> = ko.observable(0);
        selectedHourlyPay: KnockoutObservable<number> = ko.observable(0);
        selectedMonthlySalary: KnockoutObservable<number> = ko.observable(0);

        selectedCurrentTargetClass: KnockoutObservable<number> = ko.observable();
        selectedCurrentMonthSalaryPerDay : KnockoutObservable<number> = ko.observable();
        selectedCurrentMonthlySalary: KnockoutObservable<number> = ko.observable();
        selectedCurrentHourlyPay: KnockoutObservable<number> = ko.observable();
        selectedCurrentADayPayee: KnockoutObservable<number> = ko.observable();
        //
        multilineeditor: any;
        //
        payrollUnitPriceHistory:  KnockoutObservableArray<PayrollUnitPriceHistory> = ko.observableArray([]);
        constructor() {
            let  self = this;
            self.init();
            // init grid list
            self.createGridList().done((data)=>{
                if(self.mode() === null && self.dataSource().length > 0){
                    self.singleSelectedCode(self.dataSource()[0].childs[0].code);

                }else{
                    self.mode(MODE.NEW);
                    self.enableAddHistoryButton(false);
                    self.enableEditHistoryButton(false);
                    $("#A3_2").focus();
                }
            });
            //change value when select history
            self.checkSelectHistory();

            //init
            self.checkChangeYearMonth();

        }

        checkChangeYearMonth(){
            let self = this;
            self.yearMonth.subscribe((data) => {
                if(data){
                    self.monthlyCalendar(getText('QMM007_12', [nts.uk.time.yearmonthInJapanEmpire(data).toString().split(' ').join('')]));
                }else{
                    self.monthlyCalendar(null);
                }
            });
        }
        init(){
            let self = this;
            self.multilineeditor = {
                value: self.notes,
                constraint: 'Memo',
                option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                    resizeable: true,
                    placeholder: "",
                    width: "",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            //
            self.targetClassification = ko.observableArray([
                { code: '0', name: getText('QMM007_20')},
                { code: '1', name: getText('QMM007_21')}
            ]);

            self.fixedWageClassList = ko.observableArray([
                new BoxModel(0, getText('QMM007_17')),
                new BoxModel(1, getText('QMM007_18'))
            ]);

            //
            self.currencyeditor = {
                value: self.amountOfMoney,
                constraint: '',
                option: new nts.uk.ui.option.CurrencyEditorOption({
                    grouplength: 3,
                    decimallength: 2,
                    currencyformat: "JPY",
                    currencyposition: "right",
                }),
                required: ko.observable(false),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            //
            self.inline = ko.observable(true);
            self.required = ko.observable(true)
            self.enable = ko.observable(true);
        }
        checkSelectHistory(){
            let self = this;
            self.singleSelectedCode.subscribe((o)=>{
                if(o){
                nts.uk.ui.errors.clearAll();
                let fistHistory,code,hisId;
                let params = o.split('__');
                if(params){
                    code = params[0];
                    hisId = params[1];
                    service.getPayrollUnitPriceHistoryByCidCode(code).done((listPayrollUnitPriceHistory: Array<PayrollUnitPriceHistory>) => {
                        self.payrollUnitPriceHistory(_.orderBy(listPayrollUnitPriceHistory, ['endYearMonth',], ['desc']));
                        fistHistory = self.payrollUnitPriceHistory()[0];
                        if(fistHistory && self.historyTakeover() === HISTORYTAKEOVER.EXTENDS_LAST_HISTORY && self.newHisId() && self.newHisId() === hisId){
                            hisId = fistHistory.hisId;
                            self.mode(MODE.ADD_HISTORY);
                            self.enableEditHistoryButton(false);
                        }else if(self.historyTakeover() === HISTORYTAKEOVER.CREATE_NEW && self.newHisId() && self.newHisId() === hisId){
                            self.mode(MODE.ADD_HISTORY);
                            self.enableEditHistoryButton(false);
                        } else{
                            self.enableEditHistoryButton(true);
                            self.enableAddHistoryButton(true);
                            self.mode(MODE.UPDATE);
                        }
                        if(self.newHisId()){
                            self.enableAddHistoryButton(false);
                        }
                        if(hisId === undefined){
                            self.enableCode(false);
                            self.enableName(false);
                            self.enableYearMonth(false);
                            self.enableTargetClassification(false);
                            self.enableSalary(false);
                            self.enableAmountOfMoney(false);
                            self.enableFixedWageClassList(false);
                            self.enableNotes(false);
                            self.enableButtonRegistration(false);
                            self.enableEditHistoryButton(false);

                            self.code(null);
                            self.name(null);
                            self.amountOfMoney(null);
                            self.yearMonth(null);
                            self.monthlyCalendar(null);
                            self.notes(null);
                            self.selectedId(0);

                            self.selectedTargetClass(0);
                            self.selectedMonthSalaryPerDay(0);
                            self.selectedADayPayee(0);
                            self.selectedHourlyPay(0);
                            self.selectedMonthlySalary(0);
                        }else {
                            //

                            self.enableCode(false);
                            self.enableName(true);
                            self.enableYearMonth(true);
                            self.enableTargetClassification(true);
                            self.enableSalary(true);
                            self.enableAmountOfMoney(true);
                            self.enableFixedWageClassList(true);
                            self.enableNotes(true);
                            self.enableButtonRegistration(true);


                            service.getPayrollUnitPriceById(code).done((data) => {
                                self.code(data.code);
                                self.name(data.name);
                                self.currentName(data.name);
                            });
                            if(self.historyTakeover() === HISTORYTAKEOVER.EXTENDS_LAST_HISTORY && self.mode() === MODE.ADD_HISTORY || self.mode() === MODE.UPDATE) {
                                service.getPayrollUnitPriceHisById(code, hisId).done((data) => {
                                    if(self.mode() === MODE.UPDATE){
                                        self.yearMonth(data ? data.startYearMonth : null);
                                    }else if(self.mode() === MODE.ADD_HISTORY){
                                        self.yearMonth(self.newYearMonth());
                                    }
                                    self.endYearMonth(data ? data.endYearMonth : null);
                                    self.amountOfMoney(data.amountOfMoney);

                                    self.selectedId(data.setClassification);

                                    self.selectedTargetClass(data.targetClass === null  ? 0 : data.targetClass);
                                    self.selectedMonthlySalary(data.monthlySalary === null ? 0 : data.monthlySalary);
                                    self.selectedMonthSalaryPerDay(data.monthSalaryPerDay === null ? 0 : data.monthSalaryPerDay);
                                    self.selectedADayPayee(data.adayPayee === null ? 0 : data.adayPayee);
                                    self.selectedHourlyPay(data.hourlyPay === null ? 0 : data.hourlyPay);
                                    self.notes(data.notes);

                                    self.selectedCurrentTargetClass(data.targetClass);
                                    self.selectedCurrentMonthSalaryPerDay(data.monthSalaryPerDay);
                                    self.selectedCurrentMonthlySalary(data.monthlySalary);
                                    self.selectedCurrentHourlyPay(data.hourlyPay);
                                    self.selectedCurrentADayPayee(data.adayPayee);
                                    $("#A3_3").focus();
                                });
                            }
                            else{
                                self.yearMonth(self.newYearMonth());
                                self.endYearMonth(null);

                                self.amountOfMoney(null);

                                self.selectedId(0);
                                self.selectedTargetClass(0);
                                self.selectedMonthSalaryPerDay(0);
                                self.selectedMonthlySalary(0);
                                self.selectedADayPayee(0);
                                self.selectedHourlyPay(0)
                                self.notes(null);

                                $("#A3_3").focus();
                            }
                        }
                    });
                }
                }

            });

        }
        openBscreen(){
            let self = this;
            let code, name = self.currentName();
            let params = self.singleSelectedCode().split('__');
            if(params){
                code = params[0];
                let fistHistory = self.payrollUnitPriceHistory()[0];

                setShared('QMM007_PARAMS_TO_SCREEN_B', {
                    code: code,
                    name: name,
                    startYearMonth: fistHistory.startYearMonth,
                });
            }

            modal("/view/qmm/007/b/index.xhtml").onClosed( ()=> {
                let params = getShared('QMM007_B_PARAMS_OUTPUT');
                let hisId ;
                let startYearMonth ;
                let endYearMonth;
                let takeover
                let node;
                let tempNode;
                let temp;

                if(params){
                    self.enableAddHistoryButton(false);
                    self.enableEditHistoryButton(false);
                    hisId = params.hisId[0];
                    self.newHisId(hisId);
                    startYearMonth = params.startYearMonth;
                    self.newYearMonth(startYearMonth);
                    takeover = params.takeOver;
                    self.historyTakeover(takeover);

                    endYearMonth = Number(startYearMonth.toString().slice(4, 6)) == 1 ? (startYearMonth - 89) : (startYearMonth - 1);
                    node = new Node(code + '__' + hisId, '' + self.convertYearMonthToDisplayYearMonth(startYearMonth) + ' ～ ' + self.convertYearMonthToDisplayYearMonth('999912') , code,name,[]);
                    tempNode = _.find(self.dataSource(), function(o) { return o.code.split('__')[0] === code; });
                    temp = tempNode.childs[0].name.split(' ～ ');
                    tempNode.childs[0].name = temp[0] + ' ～ ' + self.convertYearMonthToDisplayYearMonth(endYearMonth);
                    tempNode.childs[0].nodeText = tempNode.childs[0].name;
                    tempNode.childs.unshift(node);

                    self.dataSource(self.dataSource());

                    self.mode(MODE.ADD_HISTORY);
                    self.singleSelectedCode(code + '__' + hisId);


                }
            });
        }

        openCscreen(){
            let self = this;
            let params = self.singleSelectedCode().split('__');
            let name = self.currentName();
            let code,hisId,index;
            if(params){
                code = params[0];
                hisId = params[1];
                service.getPayrollUnitPriceHistoryByCidCode(code).done((data)=>{
                    index = _.findIndex(data, (o) =>{
                        return o.hisId === params[1];
                    });
                    setShared('QMM007_PARAMS_TO_SCREEN_C', {
                        code: code,
                        hisId: hisId,
                        name: name,
                        startYearMonth: self.yearMonth(),
                        endYearMonth: self.endYearMonth(),
                        isFirst: index === 0 ? true : false,

                    });
                });
            }

            modal("/view/qmm/007/c/index.xhtml").onClosed(function () {
                let param = getShared('QMM007_C_PARAMS_OUTPUT');
                if(param){
                    self.createGridList().done(()=>{
                        if(param.methodEditing === EDIT_METHOD.DELETE){
                            if(self.dataSource().length == 0) {
                                self.create();
                            }
                            self.singleSelectedCode(self.dataSource()[0].childs[0].code);
                        }else{
                            self.newYearMonth(param.startYearMonth);
                            self.yearMonth(self.newYearMonth());
                        }
                    });
                    self.enableAddHistoryButton(true);
                }
            });

        }

        create(){
            let self = this;
            self.enableCode(true);
            self.enableName(true);
            self.enableYearMonth(true);
            self.enableTargetClassification(true);
            self.enableSalary(true);
            self.enableAmountOfMoney(true);
            self.enableFixedWageClassList(true);
            self.enableNotes(true);
            self.enableButtonRegistration(true);
            self.enableAddHistoryButton(false);
            self.enableEditHistoryButton(false);

            self.code('');
            self.name('');
            self.amountOfMoney(null);
            self.yearMonth(null);
            self.notes(null);
            self.selectedId(0);
            self.selectedTargetClass(0);
            self.selectedMonthSalaryPerDay(0);
            self.selectedADayPayee(0);
            self.selectedHourlyPay(0);
            self.selectedMonthlySalary(0);

            self.mode(MODE.NEW);

            $("#A3_2").focus();
        }
        convertYearMonthToDisplayYearMonth(yearMonth) {
            return nts.uk.time.formatYearMonth(yearMonth);
        }

        createGridList(): JQueryPromise<any> {
                let self = this, dfd = $.Deferred();
                let displayGridList: Array<Node> = [];
                return service.getAllHistoryById().done((data) => {
                    if (data) {
                        _.each(data,(o)=>{
                            let node = new Node(o.code,o.code + ' ' + o.name,o.code,o.name,[]);
                            if(o.payrollUnitPriceHistoryDto){
                                let displayStart, displayEnd = "";
                                _.each(o.payrollUnitPriceHistoryDto,(dto)=>{
                                    displayStart = self.convertYearMonthToDisplayYearMonth(dto.startYearMonth);
                                    displayEnd = self.convertYearMonthToDisplayYearMonth(dto.endYearMonth);
                                    if(o.code === self.code()){
                                        self.currentSelected(o.code + '__' + dto.hisId);
                                    }
                                    node.childs.push(new Node(o.code + '__' + dto.hisId,displayStart + ' ～ ' + displayEnd,'','',[] ));
                                });
                            }
                            displayGridList.push(node);
                        });
                        self.dataSource(displayGridList);
                    }

                }).fail(function (err) {
                    dialog.alertError(err.message);
                }).always(function () {
                });
        }
        register(){
            let self = this;
            let monthSalaryPerDay, monthlySalary,hourlyPay,aDayPayee,targetClass;
            nts.uk.ui.errors.clearAll();
            $("input").trigger("validate");
            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            block.invisible();
            if(self.mode() === MODE.NEW || self.mode() === MODE.ADD_HISTORY){
                if(self.selectedId() === 0){
                    targetClass = self.selectedTargetClass();
                    monthSalaryPerDay = null;
                    monthlySalary = null;
                    hourlyPay = null;
                    aDayPayee = null;

                }else{
                    monthSalaryPerDay = self.selectedMonthSalaryPerDay();
                    monthlySalary = self.selectedMonthlySalary();
                    hourlyPay = self.selectedHourlyPay();
                    aDayPayee = self.selectedADayPayee();
                    targetClass = null;
                }
            }else{
                if(self.selectedId() === 0){
                    targetClass = self.selectedTargetClass();
                    monthSalaryPerDay = self.selectedCurrentMonthSalaryPerDay();
                    monthlySalary = self.selectedCurrentMonthlySalary();
                    hourlyPay = self.selectedCurrentHourlyPay();
                    aDayPayee = self.selectedCurrentADayPayee();

                }else{
                    targetClass = self.selectedCurrentTargetClass();
                    monthSalaryPerDay = self.selectedMonthSalaryPerDay();
                    monthlySalary = self.selectedMonthlySalary();
                    hourlyPay = self.selectedHourlyPay();
                    aDayPayee = self.selectedADayPayee();
                }
            }
            let data: any = {
                payrollUnitPriceCommand: new PayrollUnitPrice(
                    {
                        'cId':'',
                        'code':self.code(),
                        'name': self.name()
                    }
                ),
                payrollUnitPriceHistoryCommand: new PayrollUnitPriceHistory({
                    'cId':'',
                    'code':self.code(),
                    'hisId': self.singleSelectedCode().split('__')[1],
                    'startYearMonth':self.yearMonth(),
                    'endYearMonth': self.mode() === MODE.NEW || self.mode() === MODE.ADD_HISTORY  ? 999912 : self.endYearMonth(),
                    'isMode': self.mode()
                }),
                payrollUnitPriceSettingCommand: new PayrollUnitPriceSetting({
                    historyId: self.singleSelectedCode().split('__')[1],
                    amountOfMoney: self.amountOfMoney(),
                    setClassification: self.selectedId(),

                    targetClass: targetClass,
                    monthSalaryPerDay: monthSalaryPerDay,
                    monthlySalary: monthlySalary,
                    hourlyPay: hourlyPay,
                    aDayPayee: aDayPayee,

                    notes: self.notes()
                })
            }

            service.getPayrollUnitPriceById(self.code()).done((o)=>{
                if(o != undefined){
                    self.isExist(true);
                }

                if(self.isExist() && self.mode() === MODE.NEW){
                    dialog.info({ messageId: "Msg_3" }).then(() => {
                        close();
                        block.clear();
                    });

                }else{
                    service.register(data).done(()=>{
                        dialog.info({ messageId: "Msg_15" }).then(() => {
                            self.newHisId(null);
                            let select = self.singleSelectedCode();
                            self.singleSelectedCode(null);
                            self.createGridList().done(()=>{
                                if(self.mode() === MODE.NEW){
                                    self.singleSelectedCode(self.currentSelected());
                                }else{
                                    self.singleSelectedCode(select);
                                }
                                self.mode(MODE.UPDATE);
                            });
                            self.enableAddHistoryButton(true);
                        });


                    }).fail(function(res: any) {
                        if (res)
                            dialog.alertError(res);
                    }).always(()=>{
                        block.clear();
                    });
                }
            });
            self.isExist(false);
        }
    }
    export interface IPayrollUnitPrice {
        cId: string;
        code: string;
        name: string;
    }
    export class PayrollUnitPrice {
        cId: string;
        code: string;
        name: string;
        constructor(params: IPayrollUnitPrice) {
            this.cId = params ? params.cId : null;
            this.code = params ? params.code : null;
            this.name = params ? params.name : null;
        }
    }

    export interface IPayrollUnitPriceHistory {
        cId: string;
        code: string;
        hisId: string;
        startYearMonth: number;
        endYearMonth: number;
        isMode: number;
    }
    export class PayrollUnitPriceHistory {
        cId: string;
        code: string;
        hisId: string;
        startYearMonth: number;
        endYearMonth: number;
        isMode: number;
        constructor(params: IPayrollUnitPriceHistory) {
            this.cId = params ? params.cId : null;
            this.code = params ? params.code : null;
            this.hisId = params ? params.hisId : null;
            this.startYearMonth = params ? params.startYearMonth : null;
            this.endYearMonth = params ? params.endYearMonth : null;
            this.isMode = params ? params.isMode : null;
        }
    }
    export interface IPayrollUnitPriceSetting {
        historyId: string;
        amountOfMoney: number;
        targetClass: number;
        monthSalaryPerDay: number;
        monthlySalary: number;
        hourlyPay: number;
        aDayPayee: number;
        setClassification: number;
        notes: string;
    }
    export class PayrollUnitPriceSetting {
        historyId: string;
        amountOfMoney: number;
        targetClass: number;
        monthSalaryPerDay: number;
        monthlySalary: number;
        hourlyPay: number;
        aDayPayee: number;
        setClassification: number;
        notes: string;
        constructor(params: IPayrollUnitPriceSetting) {
            this.historyId = params ? params.historyId : null;
            this.amountOfMoney = params ? params.amountOfMoney : null;
            this.targetClass = params ? params.targetClass : null;
            this.monthSalaryPerDay = params ? params.monthSalaryPerDay : null;
            this.monthlySalary = params ? params.monthlySalary : null;
            this.hourlyPay = params ? params.hourlyPay : null;
            this.aDayPayee = params ? params.aDayPayee : null;
            this.setClassification = params ? params.setClassification : null;
            this.notes = params ? params.notes : null;
        }
    }
    class Node {
        code: string;
        name: string;
        hisId: string;
        searchCode: string;
        searchName: string;
        nodeText: string;
        childs: any;
        constructor(code: string,name: string,searchCode: string, searchName: string, childs: Array<Node>) {
            this.code = code;
            this.name = name;
            this.searchCode = searchCode;
            this.searchName = searchName;
            this.nodeText = name;
            this.childs = childs;
        }
    }
    class ItemModel {
        code: string;
        name: string;
        description: string;
        other1: string;
        other2: string;

        constructor(code: string, name: string, description: string, other1?: string, other2?: string) {
            this.code = code;
            this.name = name;
            this.description = description;
            this.other1 = other1;
            this.other2 = other2 || other1;
        }
    }
    class BoxModel {
        id: number;
        name: string;
        constructor(id, name){
            var self = this;
            self.id = id;
            self.name = name;
        }
    }

    export enum FixedWageClass {
        DES_FOR_EACH_SALARY_CON_TYPE = 0,
        DES_BY_ALL_MEMBERS = 1
    }
    //給与契約形態ごとの対象区分
    export enum TargetClassification{
        NOT_COVERED = 0,
        OBJECT = 1
    }
    export enum MODE {
        NEW = 0,
        UPDATE = 1,
        ADD_HISTORY = 2
    }

    export  enum  HISTORYTAKEOVER{
        CREATE_NEW = 1,
        EXTENDS_LAST_HISTORY = 0
    }

    export enum EDIT_METHOD {
        DELETE = 0,
        UPDATE = 1
    }

    export function listSetting(): Array<model.ItemModel> {
        return [
            new model.ItemModel('0', getText('QMM007_20')),
            new model.ItemModel('1', getText('QMM007_21')),
        ];
    }
}