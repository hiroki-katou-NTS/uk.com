module nts.uk.pr.view.qmm007.a.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;

    export class ScreenModel {

        newHisId: KnockoutObservable<string> = ko.observable('');
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
        value: KnockoutObservable<string> = ko.observable('');
        //datepicker
        date: KnockoutObservable<string>;
        yearMonth: KnockoutObservable<number>;
        endYearMonth: KnockoutObservable<number> = ko.observable('9999/12');

        currencyeditor: any;
        //
        fixedWageClassList:  KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number> = ko.observable(0);

        enable: KnockoutObservable<boolean> ;
        //
        roundingRules: KnockoutObservableArray<any>;
        targetClassification: KnockoutObservableArray<any>;
        selectedRuleCode: any;

        selectedTargetClass: any;

        selectedClassification: any;
        selectedMonthSalaryPerDay: any;
        selectedADayPayee:any;
        selectedHourlyPay:any;
        selectedMonthlySalary: any;

        //
        multilineeditor: any;

        //
        payrollUnitPriceHistory:  KnockoutObservableArray<PayrollUnitPriceHistory> = ko.observableArray([]);
        constructor() {
            var self = this;

            self.createGridList().done(()=>{
                if(self.mode() === null && self.dataSource().length > 0){
                    self.singleSelectedCode(self.dataSource()[0].childs[0].code);
                }
            });

            self.singleSelectedCode.subscribe((o)=>{
                let fistHistory = self.payrollUnitPriceHistory()[0];
                let params = o.split('__');
                let code = params[0];
                let hisId = params[1];

                $("#A3_2").focus();

                if(hisId === ''){
                    self.enableCode(false);
                    self.enableName(false);
                    self.enableYearMonth(false);
                    self.enableTargetClassification(false);
                    self.enableSalary(false);
                    self.enableAmountOfMoney(false);
                    self.enableFixedWageClassList(false);
                    self.enableNotes(false);
                    self.enableButtonRegistration(false);


                    self.code(null);
                    self.name(null);
                    self.amountOfMoney(null);
                    self.yearMonth(null);
                    self.notes(null);
                    self.selectedId(0);
                    self.selectedClassification(0);
                    self.selectedMonthSalaryPerDay(0);
                    self.selectedADayPayee(0);
                    self.selectedHourlyPay(0);
                    self.selectedMonthlySalary(0);
                }else {
                    //

                    if(fistHistory && self.historyTakeover() === HISTORYTAKEOVER.EXTENDS_LAST_HISTORY && self.newHisId() && self.newHisId() === hisId){
                        hisId = fistHistory.hisId;
                        self.mode(MODE.ADD_HISTORY);
                    }else{
                        self.mode(MODE.UPDATE);
                    }

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
                    });
                    service.getPayrollUnitPriceHisById(code, hisId).done((data) => {
                        self.yearMonth(data ? data.startYearMonth : null);
                        self.endYearMonth(data ? data.endYearMonth : null);
                    });
                    service.getPayrollUnitPriceSettingById(hisId).done((data) => {
                        if (data != undefined) {
                            self.selectedId(data.targetClass);
                            self.amountOfMoney(data.amountOfMoney);
                            self.selectedMonthlySalary(data.monthlySalary);
                            self.selectedADayPayee(data.adayPayee);
                            self.selectedHourlyPay(data.hourlyPay)
                            self.notes(data.notes);
                        }
                    });

                    service.getPayrollUnitPriceHistoryByCidCode(code).done((listPayrollUnitPriceHistory: Array<PayrollUnitPriceHistory>) => {
                        self.payrollUnitPriceHistory(_.orderBy(listPayrollUnitPriceHistory, ['endYearMonth',], ['desc']));
                    });
                }
            });

            //
            self.multilineeditor = {
                value: self.notes,
                constraint: '',
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
            self.roundingRules = ko.observableArray([
                { code: '1', name: '四捨五入' },
                { code: '2', name: '切り上げ' },
            ]);
            self.targetClassification = ko.observableArray([
                { code: '0', name: '対象' },
                { code: '1', name: '対象外' },
            ]);
            self.selectedRuleCode = ko.observable(0);
            self.selectedClassification = ko.observable(0);
            self.selectedTargetClass = ko.observable(0);
            self.selectedMonthSalaryPerDay = ko.observable(0);
            self.selectedADayPayee = ko.observable(0);
            self.selectedHourlyPay = ko.observable(0);
            self.selectedMonthlySalary = ko.observable(0);

            self.fixedWageClassList = ko.observableArray([
                new BoxModel(0, getText('全員一律で指定する')),
                new BoxModel(1, getText('給与契約形態ごとに指定する'))
            ]);

            self.enable = ko.observable(true);
            //
            self.currencyeditor = {
                value: self.amountOfMoney,
                constraint: '',
                option: new nts.uk.ui.option.CurrencyEditorOption({
                    grouplength: 3,
                    decimallength: 2,
                    currencyformat: "JPY"
                }),
                required: ko.observable(false),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            //
            self.date = ko.observable('20000101');
            self.yearMonth = ko.observable(201801);
            //
            self.inline = ko.observable(true);
            self.required = ko.observable(true)
            self.enable = ko.observable(true);
            //
            self.texteditor = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "",
                    width: "50px",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
        }

        openBscreen(){
            let self = this;
            let params = self.singleSelectedCode().split('__');
            let fistHistory = self.payrollUnitPriceHistory()[0];
            let code = params[0];
            let name = params[2];
            setShared('QMM007_PARAMS_TO_SCREEN_B', {
                code: code,
                name: name,
                startYearMonth: fistHistory.startYearMonth,
            });
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

                    hisId = params.hisId[0];
                    self.newHisId(hisId);
                    startYearMonth = params.startYearMonth;
                    takeover = params.takeOver;
                    self.historyTakeover(takeover);

                    endYearMonth = Number(startYearMonth.toString().slice(4, 6)) == 1 ? (startYearMonth - 89) : (startYearMonth - 1);
                    node = new Node(code + '__' + hisId + '__' + name, '' + self.convertYearMonthToDisplayYearMonth(startYearMonth) + '～' + self.convertYearMonthToDisplayYearMonth('999912') , []);
                    tempNode = _.find(self.dataSource(), function(o) { return o.code.split('__')[0] === code; });
                    temp = tempNode.childs[0].name.split('～');
                    tempNode.childs[0].name = temp[0] + '～' + self.convertYearMonthToDisplayYearMonth(endYearMonth);
                    tempNode.childs[0].nodeText = tempNode.childs[0].name;
                    tempNode.childs.unshift(node);

                    self.dataSource(self.dataSource());

                    self.mode(MODE.ADD_HISTORY);
                    self.singleSelectedCode(code + '__' + hisId + '__' + name);


                }

                if(self.mode() === MODE.ADD_HISTORY){
                    if(takeover === HISTORYTAKEOVER.EXTENDS_LAST_HISTORY){
                        service.getPayrollUnitPriceById(self.code()).done((data)=>{
                            self.code(data.code);
                            self.name(data.name);
                        });
                        service.getPayrollUnitPriceHisById(self.code(),fistHistory.hisId).done((data)=>{
                            self.yearMonth(Number(startYearMonth));
                            self.endYearMonth(data.endYearMonth);
                        });
                        service.getPayrollUnitPriceSettingById(fistHistory.hisId).done((data) =>{
                            if(data != undefined){
                                self.amountOfMoney(data.amountOfMoney);
                                self.selectedId(data.targetClass);
                                self.selectedMonthlySalary(data.monthlySalary);
                                self.selectedADayPayee(data.adayPayee);
                                self.selectedHourlyPay(data.hourlyPay)
                                self.notes(data.notes);
                            }
                        });
                    }else{
                        self.amountOfMoney(null);
                        self.yearMonth(null);
                        self.notes(null);
                        self.selectedTargetClass(0);
                        self.selectedClassification(0);
                        self.selectedMonthSalaryPerDay(0);
                        self.selectedADayPayee(0);
                        self.selectedHourlyPay(0);
                        self.selectedMonthlySalary(0);
                    }
                }
            });
        }

        openCscreen(){
            let self = this;
            let params = self.singleSelectedCode().split('_');
            let index;
            index = _.findIndex(self.payrollUnitPriceHistory(), (o) =>{
                return o.hisId === params[1];
            });
            setShared('QMM007_PARAMS_TO_SCREEN_C', {
                code: params[0],
                hisId: params[1],
                name: params[2],
                startYearMonth: self.yearMonth(),
                endYearMonth: self.endYearMonth(),
                isFirst: index === 0 ? true : false,
            });
            modal("/view/qmm/007/c/index.xhtml").onClosed(function () {

            });

        }
        clear(){

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

            self.code('');
            self.name('');
            self.amountOfMoney(null);
            self.yearMonth(null);
            self.notes(null);
            self.selectedTargetClass(0);
            self.selectedClassification(0);
            self.selectedMonthSalaryPerDay(0);
            self.selectedADayPayee(0);
            self.selectedHourlyPay(0);
            self.selectedMonthlySalary(0);
            self.selectedId(0);

            self.mode(MODE.NEW);
        }
        convertYearMonthToDisplayYearMonth(yearMonth) {
            return nts.uk.time.formatYearMonth(yearMonth);
        }

        convertYearMonthToDisplayJpanYearMonth(yearMonth) {
            return nts.uk.time.yearmonthInJapanEmpire(yearMonth).toString().split(' ').join('');
        }
        createGridList(): JQueryPromise<any> {
                let self = this, dfd = $.Deferred();
                let displayGridList: Array<Node> = [];
                return service.getAllHistoryById().done((data) => {
                    if (data) {
                        _.each(data,(o)=>{
                            let node = new Node(o.code + '__' + '' + '__'+ o.name,o.code + ' ' + o.name,[]);
                            if(o.payrollUnitPriceHistoryDto){
                                let displayStart, displayEnd = "";
                                _.each(o.payrollUnitPriceHistoryDto,(dto)=>{
                                    displayStart = self.convertYearMonthToDisplayYearMonth(dto.startYearMonth);
                                    displayEnd = self.convertYearMonthToDisplayYearMonth(dto.endYearMonth);
                                    if(o.code === self.code()){
                                        self.currentSelected(o.code + '__' + dto.hisId + '__' + o.name);
                                    }
                                    node.childs.push(new Node(o.code + '__' + dto.hisId + '__' + o.name,displayStart + '～' + displayEnd,[] ));
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
            block.invisible();
            let data: any = {
                payrollUnitPriceCommand: new PayrollUnitPrice(
                    {'cId':'',
                        'code':self.code(),
                        'name': self.name()}
                ),
                payrollUnitPriceHistoryCommand: new PayrollUnitPriceHistory({'cId':'',
                    'code':self.code(),
                    'hisId': self.singleSelectedCode().split('__')[1],
                    'startYearMonth':self.yearMonth(),
                    'endYearMonth': self.mode() === MODE.NEW || self.mode() === MODE.ADD_HISTORY  ? 999912 : self.endYearMonth(),
                    'isMode': self.mode()
                }),
                payrollUnitPriceSettingCommand: new PayrollUnitPriceSetting({
                    historyId: self.singleSelectedCode().split('__')[1],
                    amountOfMoney: self.amountOfMoney(),
                    targetClass: self.selectedId(),
                    monthSalaryPerDay: self.selectedMonthSalaryPerDay(),
                    monthlySalary: self.selectedMonthlySalary(),
                    hourlyPay: self.selectedHourlyPay(),
                    aDayPayee: self.selectedADayPayee(),
                    setClassification: self.selectedClassification(),
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
                        self.newHisId(null);
                        let select = self.singleSelectedCode();
                        self.createGridList().done(()=>{
                            if(self.mode() === MODE.NEW){
                                self.singleSelectedCode(self.currentSelected());
                            }else{
                                self.singleSelectedCode(select);
                            }
                        });

                        dialog.info({ messageId: "Msg_15" }).then(() => {

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
        startYearMonth: string;
        endYearMonth: string
        nodeText: string;
        childs: any;
        constructor(code: string,name: string, childs: Array<Node>) {
            var self = this;
            self.code = code;
            self.name = name;
            self.nodeText = self.name;
            self.childs = childs;
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
    export enum HISTORY{
        NEW = '000'
    }
}