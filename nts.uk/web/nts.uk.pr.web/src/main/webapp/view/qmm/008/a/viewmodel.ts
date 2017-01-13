module nts.uk.pr.view.qmm008.a {
    export module viewmodel {
        import InsuranceOfficeItem = service.model.finder.InsuranceOfficeItemDto

        export class ScreenModel {
            //Health insurance rate Model
            healthInsuranceRateModel: KnockoutObservable<HealthInsuranceRateModel>;
            InsuranceOfficeList: KnockoutObservableArray<InsuranceOfficeItem>;
            selectedInsuranceOfficeId:KnockoutObservable<string>;
            searchKey: KnockoutObservable<string>;
            //list rounding options
            roundingList: KnockoutObservableArray<RoundingItemModel>
            //health input options 
            healthInputOptions: any;
            
            index: number;
            dataSource: any;
            filteredData: any;
            singleSelectedCode: any;
            selectedCodes: any;
            headers: any;
            //        for health auto calculate switch button
            roundingRules: KnockoutObservableArray<any>;
            selectedRuleCode: any;
            enable: KnockoutObservable<boolean>;
            //        for pension fund switch button
            pensionFundInputOptions: KnockoutObservableArray<any>;
            pensionFundInputSelectedCode: any;
            pensionFundInputEnable: KnockoutObservable<boolean>;
            //        for pension auto calculate switch button
            pensionCalculateOptions: KnockoutObservableArray<any>;
            pensionCalculateSelectedCode: any;
            pensionCalculateEnable: KnockoutObservable<boolean>;
            //        for table
            itemList: KnockoutObservableArray<any>;
            date: KnockoutObservable<Date>;
            show: KnockoutObservable<boolean>;
            btnText: any;
            //        for add history dialog
            value: KnockoutObservable<string>;
            isTransistReturnData: KnockoutObservable<boolean>;
            //health history input
            healthTimeInput: any;
            //pension history input
            pensionTimeInput: any;
            //health inputs
            healthSalaryPersonalGeneral: KnockoutObservable<number>;
            healthSalaryPersonalNursing: any;
            healthSalaryPersonalBasic: any;
            healthSalaryPersonalSpecific: any;
            healthSalaryCompanyGeneral: any;
            healthSalaryCompanyNursing: KnockoutObservable<number>;
            healthSalaryCompanyBasic: KnockoutObservable<number>;
            healthSalaryCompanySpecific: KnockoutObservable<number>;

            healthBonusPersonalGeneral: any;
            healthBonusPersonalNursing: KnockoutObservable<number>;
            healthBonusPersonalBasic: KnockoutObservable<number>;
            healthBonusPersonalSpecific: KnockoutObservable<number>;
            healthBonusCompanyGeneral: any;
            healthBonusCompanyNursing: KnockoutObservable<number>;
            healthBonusCompanyBasic: KnockoutObservable<number>;
            healthBonusCompanySpecific: KnockoutObservable<number>;
            
            //pension inputs
            pension_inp_003: any;
            pension_inp_004: any;
            pension_inp_005: any;
            pension_inp_006: any;

            //for health rounding combobox
            comboBox1: KnockoutObservableArray<RoundingItemModel>;
            comboBox1ItemName: KnockoutObservable<string>;
            comboBox1CurrentCode: KnockoutObservable<number>
            comboBox1SelectedCode: KnockoutObservable<string>;

            comboBox2: KnockoutObservableArray<RoundingItemModel>;
            comboBox2ItemName: KnockoutObservable<string>;
            comboBox2CurrentCode: KnockoutObservable<number>
            comboBox2SelectedCode: KnockoutObservable<string>;

            comboBox3: KnockoutObservableArray<RoundingItemModel>;
            comboBox3ItemName: KnockoutObservable<string>;
            comboBox3CurrentCode: KnockoutObservable<number>
            comboBox3SelectedCode: KnockoutObservable<string>;

            comboBox4: KnockoutObservableArray<RoundingItemModel>;
            comboBox4ItemName: KnockoutObservable<string>;
            comboBox4CurrentCode: KnockoutObservable<number>
            comboBox4SelectedCode: KnockoutObservable<string>;

            //for pension rounding combobox
            comboBox5: KnockoutObservableArray<RoundingItemModel>;
            comboBox5ItemName: KnockoutObservable<string>;
            comboBox5CurrentCode: KnockoutObservable<number>
            comboBox5SelectedCode: KnockoutObservable<string>;

            comboBox6: KnockoutObservableArray<RoundingItemModel>;
            comboBox6ItemName: KnockoutObservable<string>;
            comboBox6CurrentCode: KnockoutObservable<number>
            comboBox6SelectedCode: KnockoutObservable<string>;

            comboBox7: KnockoutObservableArray<RoundingItemModel>;
            comboBox7ItemName: KnockoutObservable<string>;
            comboBox7CurrentCode: KnockoutObservable<number>
            comboBox7SelectedCode: KnockoutObservable<string>;

            comboBox8: KnockoutObservableArray<RoundingItemModel>;
            comboBox8ItemName: KnockoutObservable<string>;
            comboBox8CurrentCode: KnockoutObservable<number>
            comboBox8SelectedCode: KnockoutObservable<string>;

            //healthTotal
            healthTotal: any;
            //pensionCurrency
            pensionCurrency: any;
            pensionOwnerRate: any;
            constructor() {
                var self = this;
                self.healthInsuranceRateModel = new HealthInsuranceRateModel("code","name",true,null,null);
                //nhan mang cac cong ty bao hiem
                self.InsuranceOfficeList = ko.observableArray<InsuranceOfficeItem>([
                    new InsuranceOfficeItem('id01', 'A 事業所', 'code1',[
                        new InsuranceOfficeItem('child01', '~ 9999/12', '2016/04',[]),
                        new InsuranceOfficeItem('child02', '~ 9999/12', '2016/04',[])
                    ]),
                    new InsuranceOfficeItem('id02', 'B 事業所', 'code2',[])]);

                self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.InsuranceOfficeList(), "childs"));
                self.singleSelectedCode = ko.observable(null);
                self.selectedCodes = ko.observableArray([]);
                self.index = 0;
                self.headers = ko.observableArray(["Item Value Header", "Item Text Header", "Auto generated Field"]);
                //rounding list
                self.roundingList=ko.observableArray<RoundingItemModel>([
                    new RoundingItemModel('001', 'op1change'),
                    new RoundingItemModel('002', 'op2'),
                    new RoundingItemModel('003', 'op3')
                ]);
                //define input options
                self.healthInputOptions = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                    grouplength: 3,
                    decimallength: 2
                }));
                
                //health calculate switch
                self.enable = ko.observable(true);
                self.roundingRules = ko.observableArray([
                    { code: '1', name: 'する' },
                    { code: '2', name: 'しない' }
                ]);
                self.selectedRuleCode = ko.observable(1);
                //pension fund switch 
                self.pensionFundInputEnable = ko.observable(true);
                self.pensionFundInputOptions = ko.observableArray([
                    { code: '1', name: '有' },
                    { code: '2', name: '無' }
                ]);
                self.pensionFundInputSelectedCode = ko.observable(1);
                //pension calculate switch 
                self.pensionCalculateEnable = ko.observable(true);
                self.pensionCalculateOptions = ko.observableArray([
                    { code: '1', name: 'する' },
                    { code: '2', name: 'しない' }
                ]);
                self.pensionCalculateSelectedCode = ko.observable(1);

                // table
                self.date = ko.observable(new Date('2016/12/01'));
                self.show = ko.observable(true);
                self.itemList = ko.observableArray([]);
                self.btnText = ko.computed(function() { if (self.show()) return "-"; return "+"; });
                for (let i = 1; i <= 12; i++) {
                    self.itemList.push({ index: i });
                }
                // add history dialog
                self.value = ko.observable("Hello world!");
                self.isTransistReturnData = ko.observable(false);
                //health history input
                self.healthTimeInput = {
                    value: ko.observable('2016/04'),
                    constraint: 'ResidenceCode',
                    option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                        textmode: "text",
                        width: "100",
                        textalign: "center"
                    })),
                    required: ko.observable(false),
                };
                //pension history input
                self.pensionTimeInput = {
                    value: ko.observable('2016/04'),
                    constraint: 'ResidenceCode',
                    option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                        textmode: "text",
                        width: "100",
                        textalign: "center"
                    })),
                    required: ko.observable(false),
                };

                //health inputs
                self.healthSalaryPersonalGeneral= ko.observable(100000);
                self.healthSalaryCompanyGeneral= ko.observable(100000);
                self.healthBonusPersonalGeneral= ko.observable(100000);
                self.healthBonusCompanyGeneral= ko.observable(100000);
//                self.healthSalaryPersonalGeneral = {
//                    value: ko.observable(1),
//                    constraint: '',
//                    option: ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
//                        grouplength: 3,
//                        decimallength: 2
//                    })),
//                };
                
                //for health rounding combobox
                self.comboBox1 = ko.observableArray(self.roundingList());
                self.comboBox1ItemName = ko.observable('');
                self.comboBox1CurrentCode = ko.observable(3);
                self.comboBox1SelectedCode = ko.observable('002');

                self.comboBox2 = ko.observableArray(self.roundingList());
                self.comboBox2ItemName = ko.observable('');
                self.comboBox2CurrentCode = ko.observable(3);
                self.comboBox2SelectedCode = ko.observable('002');

                self.comboBox3 = ko.observableArray(self.roundingList());
                self.comboBox3ItemName = ko.observable('');
                self.comboBox3CurrentCode = ko.observable(3);
                self.comboBox3SelectedCode = ko.observable('002');

                self.comboBox4 = ko.observableArray(self.roundingList());
                self.comboBox4ItemName = ko.observable('');
                self.comboBox4CurrentCode = ko.observable(3);
                self.comboBox4SelectedCode = ko.observable('002');

                //for pension rounding combobox
                self.comboBox5 = ko.observableArray(self.roundingList());
                self.comboBox5ItemName = ko.observable('');
                self.comboBox5CurrentCode = ko.observable(3);
                self.comboBox5SelectedCode = ko.observable('002');

                self.comboBox6 = ko.observableArray(self.roundingList());
                self.comboBox6ItemName = ko.observable('');
                self.comboBox6CurrentCode = ko.observable(3);
                self.comboBox6SelectedCode = ko.observable('002');

                self.comboBox7 = ko.observableArray(self.roundingList());
                self.comboBox7ItemName = ko.observable('');
                self.comboBox7CurrentCode = ko.observable(3);
                self.comboBox7SelectedCode = ko.observable('002');

                self.comboBox8 = ko.observableArray(self.roundingList());
                self.comboBox8ItemName = ko.observable('');
                self.comboBox8CurrentCode = ko.observable(3);
                self.comboBox8SelectedCode = ko.observable('002');
                // Health CurrencyEditor
                self.healthTotal = {
                    value: ko.observable(5400000),
                    constraint: '',
                    option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                        grouplength: 3,
                        currencyformat: "JPY",
                        currencyposition: 'right'
                    })),
                    required: ko.observable(false),
                    enable: ko.observable(true),
                    readonly: ko.observable(false)
                };
                //Pension CurrencyEditor
                self.pensionCurrency = {
                    value: ko.observable(1500000),
                    constraint: '',
                    option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                        grouplength: 3,
                        currencyformat: "JPY",
                        currencyposition: 'right'
                    })),
                    required: ko.observable(false),
                    enable: ko.observable(true),
                    readonly: ko.observable(false)
                };
                //pension owner rate
                self.pensionOwnerRate = {
                    value: ko.observable(1.5),
                    constraint: '',
                    option: ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                        grouplength: 3,
                        decimallength: 2
                    })),
                    required: ko.observable(false),
                    enable: ko.observable(true),
                    readonly: ko.observable(false)
                };
            } //end construct
            
            testObservable(){
            this.healthSalaryPersonalGeneral(this.healthTimeInput.value());    
            }
            // Start
            public start(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                //first load
                self.loadAllInsuranceOffice().done(function() {
                    // Load first result.
                    if (self.InsuranceOfficeList().length > 0) {
                        self.selectedInsuranceOfficeId(_self.InsuranceOfficeList()[0].id);
                    } else {
                        //TODO Open register new office screen
                    }
                    // Resolve
                    dfd.resolve(null);
                });
                self.getAllRounding().done(function() {
                    // Resolve
                    dfd.resolve(null);
                });
                // Return.
                return dfd.promise();
            }
            public loadAllInsuranceOffice(): JQueryPromise<any> {
                var _self = this;
                var dfd = $.Deferred<any>();
                // Invoked service method
                service.findInsuranceOffice(_self.searchKey()).done(function(data: Array<InsuranceOfficeItem>) {
                    // Set list.
                    _self.InsuranceOfficeList(data);
                    dfd.resolve(null);
                });
                // Return.
                return dfd.promise();
            }
            public getAllRounding(): JQueryPromise<any> {
                var _self = this;
                var dfd = $.Deferred<any>();
                // Invoked service method
                service.findAllRounding().done(function(data: Array<RoundingItemModel>) {
                    // Set list.
                    _self.roundingList(data);
                    dfd.resolve(null);
                });
                // Return.
                return dfd.promise();
            }
            resetSelection(): void {
                var self = this;
                self.filteredData(self.dataSource());
                self.singleSelectedCode('0002');
                self.selectedCodes(['002']);
            }

            changeDataSource(): void {
                var self = this;
                var i = 0;
                var newArrays = new Array();
                while (i < 50) {
                    self.index++;
                    i++;
                    newArrays.push(new Node(self.index.toString(), 'Name ' + self.index.toString(), []));
                };
                self.dataSource(newArrays);
                self.filteredData(newArrays);
            }
            // toggle expand table
            toggle() {
                this.show(!this.show());
            }

            resize() {
                if ($("#tabs-complex").width() > 700)
                    $("#tabs-complex").width(700);
                else
                    $("#tabs-complex").width("auto");
            }
            //Chuyen tu data nhan ve chuyen thanh mang de hien thi ra list
            convertListToParentChilds(){
                
            }
            // open sub window 
            OpenModalSubWindow() {
                // Set parent value

                nts.uk.ui.windows.setShared("addHistoryParentValue", this.value());
                nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                nts.uk.ui.windows.sub.modal("/view/qmm/008/b/index.xhtml", { title: "会社保険事業所の登録＞履歴の追加" }).onClosed(() => {
                    // Get child value
                    var returnValue = nts.uk.ui.windows.getShared("addHistoryChildValue");
                });
            }

            OpenModalOfficeRegister() {
                // Set parent value
                nts.uk.ui.windows.setShared("listOfficeOfParentValue", this.value());
                nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                nts.uk.ui.windows.sub.modal("/view/qmm/008/c/index.xhtml", { title: "会社保険事業所の登録＞事業所の登録" }).onClosed(() => {
                    // Get child value
                    var returnValue = nts.uk.ui.windows.getShared("listOfficeOfChildValue");
                });
            }
            OpenModalStandardMonthlyPriceHealth() {
                // Set parent value
                nts.uk.ui.windows.setShared("dataParentValue", this.value());
                nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                nts.uk.ui.windows.sub.modal("/view/qmm/008/d/index.xhtml", { title: "会社保険事業所の登録＞標準報酬月額保険料額表" }).onClosed(() => {
                    // Get child value
                    var returnValue = nts.uk.ui.windows.getShared("listOfficeOfChildValue");
                });
            }
            OpenModalStandardMonthlyPricePension() {
                // Set parent value
                nts.uk.ui.windows.setShared("dataParentValue", this.value());
                nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                nts.uk.ui.windows.sub.modal("/view/qmm/008/e/index.xhtml", { title: "会社保険事業所の登録＞標準報酬月額保険料額表" }).onClosed(() => {
                    // Get child value
                    var returnValue = nts.uk.ui.windows.getShared("listOfficeOfChildValue");
                });
            }
            OpenModalConfigHistory() {
                // Set parent value
                nts.uk.ui.windows.setShared("addHistoryParentValue", this.value());
                nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                nts.uk.ui.windows.sub.modal("/view/qmm/008/f/index.xhtml", { title: "会社保険事業所の登録＞履歴の編集" }).onClosed(() => {
                    // Get child value
                    var returnValue = nts.uk.ui.windows.getShared("addHistoryChildValue");
                });
            }
        }

        export class Node {
            code: string;
            name: string;
            nodeText: string;
            custom: string;
            childs: any;
            constructor(code: string, name: string, childs: Array<Node>) {
                var self = this;
                self.code = code;
                self.name = name;
                self.nodeText = self.code + ' ' + self.name;
                self.childs = childs;
                self.custom = 'Random' + new Date().getTime();
            }
        }

        export class RoundingItemModel {
            code: string;
            name: string;
            label: string;

            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }

        export class HealthInsuranceRateModel {
            historyId: number;
            companyCode: KnockoutObservable<string>;
            officeCode: KnockoutObservable<string>;
            applyRange: string;
            autoCalculate: KnockoutObservable<boolean>;
            rateItems: KnockoutObservableArray<HealthInsuranceRateItemModel>;
            roundingMethods: KnockoutObservable<HealthInsuranceRounding>;
            maxAmount;
            constructor(companyCode : string,officeCode: string,autoCaculate: boolean,rateItems :Array<HealthInsuranceRateItemModel>,roundingMethods: Array<HealthInsuranceRounding> ){
                }

        }

        export class HealthInsuranceRateItemModel {
            chargeRate: KnockoutObservable<ChargeRateItem>;//value of person or company
            payType: KnockoutObservable<number>;//enum (salary,bonus)
            HealthInsuranceType: KnockoutObservable<number>;//enum (basic,nursing,special,general)
            //function get value from condition
        }
    }

    export class HealthInsuranceAvgearn {
        levelCode: KnockoutObservable<number>;
        personalAvg: KnockoutObservable<any>;
        companyAvg: KnockoutObservable<any>;
    }

    export class HealthInsuranceRounding {
        payType: KnockoutObservable<number>;
        roundAtrs: KnockoutObservable<number>;
    }
    export class ChargeRateItem {
        companyRate: KnockoutObservable<number>;
        personalRate: KnockoutObservable<number>;
    }
}
