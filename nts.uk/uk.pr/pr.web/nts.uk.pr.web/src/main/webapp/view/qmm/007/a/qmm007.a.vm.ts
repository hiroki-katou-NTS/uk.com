module nts.uk.pr.view.qmm007.a.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel {
        tempDataSource: KnockoutObservableArray<Node> = ko.observableArray([]);
        isExist:  KnockoutObservable<boolean> = ko.observable(false);
        mode: KnockoutObservable<number> = ko.observable(null);
        currentSelected: KnockoutObservable<string> = ko.observable('');
        historyTakeover: KnockoutObservable<number> = ko.observable(0);
        amountOfMoney: KnockoutObservable<number> = ko.observable(null);
        notes: KnockoutObservable<string> = ko.observable('');
        dataSource: KnockoutObservableArray<Node> = ko.observableArray([]);
        dataSource2: any;
        singleSelectedCode: KnockoutObservable<string> = ko.observable('');
        singleSelectedCode2: any;
        selectedCodes: any;
        selectedCodes2: any;
        headers: any;
        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;

        //formlable
        constraint: string = 'LayoutCode';
        inline: KnockoutObservable<boolean>;
        required: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        selectedIdFixedWageClass: KnockoutObservable<number> = ko.observable(0);
        enableTargetClassification: KnockoutObservable<boolean>  = ko.observable(true);
        //
        texteditor: any;
        texteditor1: any;
        code: KnockoutObservable<string> = ko.observable('');
        name: KnockoutObservable<string> = ko.observable('');
        value: KnockoutObservable<string> = ko.observable('');
        //datepicker
        date: KnockoutObservable<string>;
        yearMonth: KnockoutObservable<number>;
        endYearMonth: KnockoutObservable<number> = ko.observable('9999/12');

        //
        currencyeditor: any;
        //
        itemList: KnockoutObservableArray<any>;
        fixedWageClassList:  KnockoutObservableArray<any>;

        selectedId: KnockoutObservable<number>;

        enable: KnockoutObservable<boolean> ;
        //
        roundingRules: KnockoutObservableArray<any>;
        targetClassBySalaryConType: KnockoutObservableArray<any>;
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
        payrollUnitPrice: KnockoutObservableArray<PayrollUnitPrice> = ko.observableArray([]);
        constructor() {
            var self = this;
            self.init();
            self.selectedId = ko.observable(1);
            self.selectedId.subscribe((data)=>{
                if(!data){
                    self.enable(true);
                    self.enableTargetClassification(false);
                }else{
                    self.enable(false);
                    self.enableTargetClassification(true);
                }
            });
            self.singleSelectedCode.subscribe((o)=>{
                let params = o.split('_');
                let code = params[0];
                let hisId = params[1];
                self.mode(MODE.UPDATE);
                $("#A3_2").focus();
                if(hisId === undefined){
                    self.singleSelectedCode(self.currentSelected());
                }else {
                    self.currentSelected(self.singleSelectedCode());
                    service.getPayrollUnitPriceById(code).done((data) => {
                        self.code(data.code);
                        self.name(data.name);
                    });
                    service.getPayrollUnitPriceHisById(code, hisId).done((data) => {
                        self.yearMonth(data.startYearMonth);
                        self.endYearMonth(data.endYearMonth);
                    });
                    service.getPayrollUnitPriceSettingById(hisId).done((data) => {
                        if (data != undefined) {
                            self.amountOfMoney(data.amountOfMoney);
                            self.selectedId(data.targetClass);
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
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                    resizeable: true,
                    placeholder: "Placeholder for text editor",
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
            self.targetClassBySalaryConType = ko.observableArray([
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
            //
            self.itemList = ko.observableArray([
                new BoxModel(1, '全員一律で指定する'),
                new BoxModel(2, '給与契約形態ごとに指定する')
            ]);
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
            self.yearMonth = ko.observable(200001);
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
                    placeholder: "Placeholder for text editor",
                    width: "50px",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };

            self.texteditor1 = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "Placeholder for text editor",
                    width: "150px",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            //self.singleSelectedCode = ko.observable(null);
            self.singleSelectedCode2 = ko.observable(null);
            self.selectedCodes = ko.observableArray([]);
            self.selectedCodes2 = ko.observableArray([]);
            self.headers = ko.observableArray(["Item Value Header","Item Text Header"]);

            this.items = ko.observableArray([]);
            var str = ['a0', 'b0', 'c0', 'd0'];
            for(var j = 0; j < 4; j++) {
                for(var i = 1; i < 51; i++) {
                    var code = i < 10 ? str[j] + '0' + i : str[j] + i;
                    this.items.push(new ItemModel(code,code,code,code));
                }
            }
            this.columns = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 100 },
                { headerText: '名称', prop: 'name', width: 230 },
                { headerText: '説明', prop: 'description', width: 150 },
                { headerText: '説明1', prop: 'other1', width: 150 },
                { headerText: '説明2', prop: 'other2', width: 150 }
            ]);
            this.currentCode = ko.observable();
            this.currentCodeList = ko.observableArray([]);
        }

        init(): JQueryPromise<any> {
            let self = this;
            let db = [];
            let code = '';
            let child = {};
            return service.getAllPayrollUnitPriceByCID().done((list: Array<PayrollUnitPrice>)=>{
                if(list.length > 0){
                    self.payrollUnitPrice(list);
                    _.each(self.payrollUnitPrice(),(value)=>{
                        let node = new Node(value.code, value.code + ' ' +value.name, []);
                        service.getPayrollUnitPriceHistoryByCidCode(value.code).done((listPayrollUnitPriceHistory: Array<PayrollUnitPriceHistory>)=>{
                            if(listPayrollUnitPriceHistory.length > 0){
                                listPayrollUnitPriceHistory = _.orderBy(listPayrollUnitPriceHistory, ['endYearMonth',], ['desc']);
                                _.each(listPayrollUnitPriceHistory,(val)=>{
                                    let childs = new Node(val.code + '_' + val.hisId + '_' + value.name, '' + val.startYearMonth + '～' + val.endYearMonth , []);
                                    node.childs.push(childs);
                                    if(self.code() == val.code){
                                        code = childs.code;
                                        self.currentSelected(code);
                                        self.singleSelectedCode(code);
                                    }
                                });
                                db.push(node);
                                self.dataSource(db);
                                if(self.mode() === null || self.mode() === MODE.ADD_HISTORY){
                                    self.tempDataSource(self.dataSource());
                                }
                                if(self.tempDataSource().length > 0 && self.tempDataSource()[0].childs.length > 0 && self.mode() == null){
                                    self.singleSelectedCode(self.tempDataSource()[0].childs[0].code);
                                }

                            }
                        });
                    });

                }else{
                    self.code(null);
                    self.name(null);
                    self.amountOfMoney(null);
                    self.yearMonth(null);
                    self.notes(null);
                    self.mode(MODE.NEW);
                }

            });
        }

        openBscreen(){
            let self = this;
            let fistHistory = self.payrollUnitPriceHistory()[0];
            setShared('QMM007_PARAMS_TO_SCREEN_B', {
                code: self.code(),
                name: self.name(),
                startYearMonth: fistHistory.startYearMonth,
            });
            modal("/view/qmm/007/b/index.xhtml").onClosed( ()=> {
                if(self.mode() === MODE.ADD_HISTORY){
                    if(self.historyTakeover() === HISTORYTAKEOVER.EXTENDS_LAST_HISTORY){
                        service.getPayrollUnitPriceById(self.code()).done((data)=>{
                            self.code(data.code);
                            self.name(data.name);
                        });
                        service.getPayrollUnitPriceHisById(self.code(),fistHistory.hisId).done((data)=>{
                            self.yearMonth(data.startYearMonth);
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
                    }
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
            self.mode(MODE.NEW);
        }
        test(){
            let self = this;
            let db = [];
            let code = '';
            let child = {};
            service.getAllPayrollUnitPriceByCID().then((data)=>{
                self.payrollUnitPrice(data);
                return data;
            }).then((rs)=>{
                _.each(rs,(value)=>{
                    let node = new Node(value.code + ';' + HISTORY.NEW + ';' + value.name, value.code + value.name, []);
                    service.getPayrollUnitPriceHistoryByCidCode(value.code).then((listPayrollUnitPriceHistory: Array<PayrollUnitPriceHistory>)=>{
                        if(listPayrollUnitPriceHistory.length > 0){
                            listPayrollUnitPriceHistory = _.orderBy(listPayrollUnitPriceHistory, ['endYearMonth',], ['desc']);
                            _.each(listPayrollUnitPriceHistory,(val)=>{
                                let childs = new Node(val.code + ';' + val.hisId + ';' + value.name, '' + val.startYearMonth + '～' + val.endYearMonth , []);
                                node.childs.push(childs);
                                if(self.code() == val.code){
                                    code = childs.code;
                                    self.currentSelected(code);
                                }
                            });
                            db.push(node);
                            self.dataSource(db);
                        }
                    }).then(()=>{
                        if(self.dataSource().length > 0 && self.dataSource()[0].childs.length > 0 && self.mode() == null){
                            self.singleSelectedCode(self.dataSource()[0].childs[0].code);
                        }
                    });
                });
            }).then((data)=>{
                if(self.dataSource().length > 0 && self.dataSource()[0].childs.length > 0 && self.mode() == null){
                    self.singleSelectedCode(self.dataSource()[0].childs[0].code);
                }
            });
        }
        register(){
            let self = this;
            let data: any = {
                payrollUnitPriceCommand: new PayrollUnitPrice(
                    {'cId':'',
                        'code':self.code(),
                        'name': self.name()}
                ),
                payrollUnitPriceHistoryCommand: new PayrollUnitPriceHistory({'cId':'',
                    'code':self.code(),
                    'hisId': self.singleSelectedCode().split('_')[1],
                    'startYearMonth':self.yearMonth(),
                    'endYearMonth': self.mode() === MODE.NEW || self.mode() === MODE.ADD_HISTORY  ? 999912 : self.endYearMonth(),
                    'isMode': self.mode()
                }),
                payrollUnitPriceSettingCommand: new PayrollUnitPriceSetting({
                    historyId: self.singleSelectedCode().split('_')[1],
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
                    });

                }else{
                    service.register(data).done((data)=>{
                        dialog.info({ messageId: "Msg_15" }).then(() => {

                        });
                        self.singleSelectedCode(self.singleSelectedCode());
                        self.init();
                    }).fail(function(res: any) {
                        if (res)
                            dialog.alertError(res);
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
        code: any;
        name: string;
        nodeText: string;
        childs: any;
        constructor(code: any, name: string, childs: Array<Node>) {
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
    export enum TargetClassBySalaryConType{
        NOT_COVERED = 0,
        OBJECT = 1
    }
    export enum MODE {
        NEW = 0,
        UPDATE = 1,
        ADD_HISTORY = 2
    }

    export  enum  HISTORYTAKEOVER{
        CREATE_NEW = 0,
        EXTENDS_LAST_HISTORY = 1
    }
    export enum HISTORY{
        NEW = '000'
    }
}