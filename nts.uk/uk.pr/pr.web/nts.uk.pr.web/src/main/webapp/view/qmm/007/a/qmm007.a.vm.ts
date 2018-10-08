module nts.uk.pr.view.qmm007.a.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    export class ScreenModel {
        mode: KnockoutObservable<number> = ko.observable(0);
        amountOfMoney: KnockoutObservable<number> = ko.observable(null);
        notes: KnockoutObservable<string> = ko.observable('taitt');
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
        code: KnockoutObservable<string> = ko.observable('0001');
        name: KnockoutObservable<string> = ko.observable('taitt');
        value: KnockoutObservable<string> = ko.observable('0001');
        //datepicker
        date: KnockoutObservable<string>;
        yearMonth: KnockoutObservable<number>;
        endYearMonth: KnockoutObservable<number> = ko.observable('201909');

        //
        currencyeditor: any;
        //
        itemList: KnockoutObservableArray<any>;
        fixedWageClassList:  KnockoutObservableArray<any>;

        selectedId: KnockoutObservable<number>;

        enable: KnockoutObservable<boolean> ;
        //
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        //
        multilineeditor: any;

        //
        payrollUnitPriceHistory = [];
        payrollUnitPrice: Array<PayrollUnitPrice>;
        constructor() {
            var self = this;
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
            self.singleSelectedCode.subscribe((data)=>{
                let params = data.split(';');
                let code = params[0];
                let hisId = params[1];
                self.mode(MODE.UPDATE);
                service.getPayrollUnitPriceById(code).done((data)=>{
                    self.code(data.code);
                    self.name(data.name);
                });
                service.getPayrollUnitPriceHisById(code,hisId).done((data)=>{
                    self.yearMonth(data.startYearMonth);
                });
                service.getPayrollUnitPriceSettingById(hisId).done((data) =>{
                    if(data != undefined){
                        self.amountOfMoney(data.amountOfMoney);
                    }
                });
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
            self.selectedRuleCode = ko.observable(1);
            //
            self.itemList = ko.observableArray([
                new BoxModel(1, '全員一律で指定する'),
                new BoxModel(2, '給与契約形態ごとに指定する')
            ]);
            self.fixedWageClassList = ko.observableArray([
                new BoxModel(0, getText('FixedWageClass.DES_FOR_EACH_SALARY_CON_TYPE')),
                new BoxModel(1, getText('FixedWageClass.DES_BY_ALL_MEMBERS'))
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
            self.price = ko.observable(1000);
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

            //
            self.dataSource = ko.observableArray([new Node('0001', 'Hanoi Vietnam', []),
                new Node('0003', 'Bangkok Thailand', []),
                //new Node('0004', 'Tokyo Japan', []),
                //new Node('0005', 'Jakarta Indonesia', []),
                //new Node('0002', 'Seoul Korea', []),
                //new Node('0006', 'Paris France', []),
                //new Node({'code':'0007','name':'taitt'}, 'United States', []),
                //new Node('0010', 'Beijing China', []),
                //new Node('0011', 'London United Kingdom', []),
                //new Node('0012', '', [])]);
            self.dataSource2 = ko.observableArray([new Node('0001', 'Hanoi Vietnam', []),
                new Node('0003', 'Bangkok Thailand', []),
                new Node('0004', 'Tokyo Japan', []),
                new Node('0005', 'Jakarta Indonesia', []),
                new Node('0002', 'Seoul Korea', []),
                new Node('0006', 'Paris France', []),
                new Node('0007', 'United States', [new Node('0008', 'Washington US', []),new Node('0009', 'Newyork US', [])]),
                new Node('0010', 'Beijing China', []),
                new Node('0011', 'London United Kingdom', []),
                new Node('0012', '', [])]);
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


        openFscreen(){
            let self = this;
            let db = [];
            service.getPayrollUnitPriceHistoryByCidCode("002").done((listPayrollUnitPriceHistory: Array<PayrollUnitPriceHistory> )=>{
                console.dir(listPayrollUnitPriceHistory);
                self.payrollUnitPriceHistory = _.orderBy(listPayrollUnitPriceHistory, ['endYearMonth',], ['desc'])
            });
            service.getAllPayrollUnitPriceByCID().done((list: Array<PayrollUnitPrice> )=>{
                console.dir(list);
                self.payrollUnitPrice = list;
                _.each(self.payrollUnitPrice,(value)=>{
                    let node = new Node(value.code + ';' + '' + ';' + value.name, value.code + value.name, []);
                    service.getPayrollUnitPriceHistoryByCidCode(value.code).done((listPayrollUnitPriceHistory: Array<PayrollUnitPriceHistory>)=>{
                        if(listPayrollUnitPriceHistory.length > 0){
                            _.each(listPayrollUnitPriceHistory,(val)=>{
                                let childs = new Node(val.code + ';' + val.hisId + ';' + value.name, '' + val.startYearMonth + '～' + val.endYearMonth , []);
                                node.childs.push(childs);
                                self.dataSource(db);
                            });

                            self.payrollUnitPriceHistory.push(listPayrollUnitPriceHistory);
                            console.dir(self.payrollUnitPriceHistory);
                        }
                    });
                    db.push(node);
                    self.dataSource(db);
                });
            });

           /* _.each(self.payrollUnitPriceHistory,(value)=>{
                _.each(value,(val) =>{
                    let node = new Node(val.code, val.hisId, []);
                });
            });*/


        }

        openBscreen(){
            let self = this;

            modal("/view/qmm/007/b/index.xhtml").onClosed(function () {

            });
        }

        openCscreen(){
            let self = this;
            modal("/view/qmm/007/c/index.xhtml").onClosed(function () {
            });

        }
        clear(){

        }
        create(){
            let self = this;
            self.code('');
            self.name('');
            self.price(null);
            self.yearMonth(null);
            self.mode(0);
        }
        register(){
            let self = this;
            let data: any = {
                payrollUnitPriceCommand: new PayrollUnitPrice(
                    {'cId':'',
                        'code':self.code(),
                        'name': self.name()}
                ),
                payrollUnitPriceHistoryCommand: new PayrollUnitPriceHistory({'cId':'000000000000-0001',
                    'code':self.code(),
                    'hisId': self.singleSelectedCode().split(';')[1],
                    'startYearMonth':self.yearMonth(),
                    'endYearMonth': self.mode() === MODE.NEW || self.mode() === MODE.ADD_HISTORY  ? 999912 : self.endYearMonth(),
                    'isMode': self.mode()
                }),
                payrollUnitPriceSettingCommand: new PayrollUnitPriceSetting({
                    historyId: self.singleSelectedCode().split(';')[1],
                    amountOfMoney: self.amountOfMoney(),
                    targetClass: 1,
                    monthSalaryPerDay: 1,
                    monthlySalary: 1,
                    hourlyPay: 1,
                    aDayPayee: 1,
                    setClassification: 1,
                    notes: self.notes()
                })
            }

            service.register(data).done(()=>{
                dialog.info({ messageId: "Msg_15" }).then(() => {
                    close();
                });
            }).fail(function(res: any) {
                if (res)
                    dialog.alertError(res);
            });
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

    export enum MODE {
        NEW = 0,
        UPDATE = 1,
        ADD_HISTORY = 2;
    }
}