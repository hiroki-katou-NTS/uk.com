module nts.uk.pr.view.qmm008.a {
    export module viewmodel {
        import InsuranceOfficeItem = service.model.finder.InsuranceOfficeItemDto
        import RoundingItem = service.model.finder.RoundingItemDto
        import HealthInsuranceRateDto = service.model.finder.HealthInsuranceRateDto;
        import PensionRateDto = service.model.finder.PensionRateDto;
        import PaymentType = service.model.PaymentType;
        import HealthInsuranceType = service.model.HealthInsuranceType;
        export class ScreenModel {
            //Health insurance rate Model
            healthModel: KnockoutObservable<HealthInsuranceRateModel>;
            pensionModel: KnockoutObservable<PensionRateModel>
            InsuranceOfficeList: KnockoutObservableArray<InsuranceOfficeItem>;
            selectedInsuranceOfficeId: KnockoutObservable<string>;
            searchKey: KnockoutObservable<string>;

            //list rounding options
            roundingList: KnockoutObservableArray<RoundingItem>;
            //health input options 
            healthInputOptions: any;
            //healthTimeInput options
            timeInputOptions: any;
            // moneyInputOptions
            moneyInputOptions: any;
            // numberInputOptions
            numberInputOptions: any;

            index: number;
            dataSource: any;
            filteredData: any;
            singleSelectedCode: any;
            officeSelectedCode: any;
            headers: any;
            //        for health auto calculate switch button
            healthAutoCalculateOptions: KnockoutObservableArray<any>;
            selectedRuleCode: any;//
            //        for pension fund switch button
            pensionFundInputOptions: KnockoutObservableArray<any>;
            //        for pension auto calculate switch button
            pensionCalculateOptions: KnockoutObservableArray<any>;
            pensionCalculateSelectedCode: any;
            //        for table
            itemList: KnockoutObservableArray<any>;
            date: KnockoutObservable<Date>;
            show: KnockoutObservable<boolean>;
            btnText: any;
            //        for add history dialog
            value: KnockoutObservable<string>;
            isTransistReturnData: KnockoutObservable<boolean>;
            //health history input
            healthDate : any;
            //pension history input
            pensionTimeInput: any;
            //healthTotal
            healthTotal: KnockoutObservable<number>;
            //pensionCurrency
            pensionCurrency: KnockoutObservable<number>;
            pensionOwnerRate: KnockoutObservable<number>;
            constructor() {
                var self = this;
                self.healthModel = ko.observable(new HealthInsuranceRateModel("code", 1, null, null,15000));
                self.pensionModel = ko.observable(new PensionRateModel("code",1,2,null,null,null,35000,1.5));
                // init insurance offices list
                self.InsuranceOfficeList = ko.observableArray<InsuranceOfficeItem>([]);

                self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.InsuranceOfficeList(), "childs"));
                self.singleSelectedCode = ko.observable(null);
                self.officeSelectedCode = ko.observable('');
                self.index = 0;
                self.headers = ko.observableArray(["Item Value Header", "Item Text Header", "Auto generated Field"]);

                self.searchKey = ko.observable('');
                //init rounding list
                self.roundingList = ko.observableArray<RoundingItem>([]);
                //define input options
                self.healthInputOptions = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                    grouplength: 3,
                    decimallength: 2
                }));
                //healthTimeInput options
                self.timeInputOptions = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    width: "100",
                    textalign: "center"
                }));
                self.moneyInputOptions = ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                    grouplength: 3,
                    currencyformat: "JPY",
                    currencyposition: 'right'
                }));
                self.numberInputOptions = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                    grouplength: 3,
                    decimallength: 2
                }));
                //health calculate switch
                self.healthAutoCalculateOptions = ko.observableArray([
                    { code: '1', name: 'する' },
                    { code: '2', name: 'しない' }
                ]);
                self.selectedRuleCode = ko.observable(1);//
                //pension fund switch 
                self.pensionFundInputOptions = ko.observableArray([
                    { code: '1', name: '有' },
                    { code: '2', name: '無' }
                ]);
                //pension calculate switch 
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
                // health history input
                self.healthDate= ko.observable('2016/04');
                //pension history input
                self.pensionTimeInput = ko.observable("time");
                
                // Health CurrencyEditor
                self.healthTotal = ko.observable(5400000);
                //Pension CurrencyEditor
                self.pensionCurrency = ko.observable(1500000);
                //pension owner rate
                self.pensionOwnerRate = ko.observable(1.5);

                self.officeSelectedCode.subscribe(function(officeSelectedCode: string) {
                    if (officeSelectedCode != null || officeSelectedCode != undefined) {
//                        alert(officeSelectedCode);
                        $.when(self.load(officeSelectedCode)).done(function() {
                            //load data success
                        }).fail(function(res) {
                            //TODO when load data error
                        });
                    }
                });
            } //end construct

            testObservable() {
            }
            // Start
            public start(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                //first load
                self.loadAllInsuranceOffice().done(function() {
                    // Load first result.
                    if (self.InsuranceOfficeList().length > 0) {
                        //                        self.selectedInsuranceOfficeId(self.InsuranceOfficeList()[0].id);
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
                var self = this;
                var dfd = $.Deferred<any>();
                // Invoked service method
                service.findInsuranceOffice(self.searchKey()).done(function(data: Array<InsuranceOfficeItem>) {
                    // Set list.
                    self.InsuranceOfficeList(data);
                    dfd.resolve(data);
                });
                // Return.
                return dfd.promise();
            }
            public getAllRounding(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                // Invoked service method
                service.findAllRounding().done(function(data: Array<RoundingItem>) {
                    // Set list.
                    self.roundingList(data);
                    dfd.resolve(data);
                });
                // Return.
                return dfd.promise();
            }
            //load data of item by code
            public load(code: string): JQueryPromise<any> {
                var self = this;

                var dfd = $.Deferred<any>();

                // Block UI with processing in long time.
                service.getHealthInsuranceItemDetail(code).done(function(data: HealthInsuranceRateDto) {
                    if (data == null) {
                        return;
                    }
                    // Set detail.
                    self.healthModel().historyId = data.historyId;
                    self.healthModel().companyCode = data.companyCode;
                    self.healthModel().officeCode(data.officeCode);
                    self.healthModel().autoCalculate(data.autoCalculate);
                    self.healthModel().rateItems().healthSalaryPersonalGeneral(data.rateItems[0].chargeRate);
                    self.healthModel().rateItems().healthSalaryCompanyGeneral(data.rateItems[1].chargeRate);
                    self.healthModel().rateItems().healthBonusPersonalGeneral(data.rateItems[2].chargeRate);
                    self.healthModel().rateItems().healthBonusCompanyGeneral(data.rateItems[3].chargeRate);
                    self.healthModel().roundingMethods().healthSalaryPersonalComboBox(data.roundingMethods[0].roundAtrs);
                    self.healthModel().roundingMethods().healthSalaryCompanyComboBox(data.roundingMethods[1].roundAtrs);
                    self.healthModel().roundingMethods().healthBonusPersonalComboBox(data.roundingMethods[1].roundAtrs);
                    self.healthModel().roundingMethods().healthBonusCompanyComboBox(data.roundingMethods[0].roundAtrs);
                    self.healthModel().maxAmount(data.maxAmount);
                    // Resolve
                    dfd.resolve();
                }).fail(function() {
                }).always(function(res) {
                });
                
                // Block UI with processing in long time.
                service.getPensionItemDetail(code).done(function(data: PensionRateDto) {
                    if (data == null) {
                        return;
                    }
                    // Set detail.
                    self.pensionModel().historyId = data.historyId;
                    self.pensionModel().companyCode = data.companyCode;
                    self.pensionModel().officeCode(data.officeCode);
                    self.pensionModel().autoCalculate(data.autoCalculate);
                    self.pensionModel().funInputOption(data.fundInputOption);
                    
                    self.pensionModel().rateItems().pensionSalaryPersonalSon(data.rateItems[0].chargeRate);
                    self.pensionModel().rateItems().pensionSalaryCompanySon(data.rateItems[0].chargeRate);
                    self.pensionModel().rateItems().pensionBonusPersonalSon(data.rateItems[0].chargeRate);
                    self.pensionModel().rateItems().pensionBonusCompanySon(data.rateItems[0].chargeRate);
                    
                    self.pensionModel().fundRateItems().salaryPersonalSonExemption(data.fundRateItems[0].chargeRate);
                    self.pensionModel().fundRateItems().salaryCompanySonExemption(data.fundRateItems[0].chargeRate);
                    self.pensionModel().fundRateItems().bonusPersonalSonExemption(data.fundRateItems[0].chargeRate);
                    self.pensionModel().fundRateItems().bonusCompanySonExemption(data.fundRateItems[0].chargeRate);
                    
                    self.pensionModel().roundingMethods().pensionSalaryPersonalComboBox(data.roundingMethods[0].roundAtrs);
                    self.pensionModel().roundingMethods().pensionSalaryCompanyComboBox(data.roundingMethods[1].roundAtrs);
                    self.pensionModel().roundingMethods().pensionBonusPersonalComboBox(data.roundingMethods[1].roundAtrs);
                    self.pensionModel().roundingMethods().pensionBonusCompanyComboBox(data.roundingMethods[0].roundAtrs);
                    
                    self.pensionModel().maxAmount(data.maxAmount);
                    self.pensionModel().officeRate(data.officeRate);
                    // Resolve
                    dfd.resolve();
                }).fail(function() {
                }).always(function(res) {
                });
                
                // Ret promise.
                return dfd.promise();
            }
            resetSelection(): void {
                var self = this;
                self.filteredData(self.dataSource());
                self.singleSelectedCode('0002');
                self.officeSelectedCode(['002']);
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
            convertListToParentChilds() {

            }
            getDataOfSelectedOffice(){
                var self= this;
                var saveVal=null;
                // Set parent value
                this.InsuranceOfficeList().forEach(function(item, index) {
                    if (self.officeSelectedCode() == item.code) {
                        saveVal = item;
                    }
                });
                return saveVal;
            }
            
            // open sub window 
            OpenModalSubWindow() {
                var self= this;
                var saveVal = self.getDataOfSelectedOffice();
                nts.uk.ui.windows.setShared("addHistoryParentValue", saveVal);
                nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                nts.uk.ui.windows.sub.modal("/view/qmm/008/b/index.xhtml", { title: "会社保険事業所の登録＞履歴の追加" }).onClosed(() => {
                    // Get child value return office Item
                    var returnValue = nts.uk.ui.windows.getShared("addHistoryChildValue");
                    var currentInsuranceOfficeList = self.InsuranceOfficeList();
                    currentInsuranceOfficeList.forEach(function(item, index){
                        if(item.code==returnValue.code)
                        {
                            currentInsuranceOfficeList[index] = returnValue;
                            }    
                    });
                    self.InsuranceOfficeList([]);
                    self.InsuranceOfficeList(currentInsuranceOfficeList);
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
                var getVal= null;
                getVal= this.getDataOfSelectedOffice();
                // Set parent value
                nts.uk.ui.windows.setShared("updateHistoryParentValue", getVal);
                nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                nts.uk.ui.windows.sub.modal("/view/qmm/008/f/index.xhtml", { title: "会社保険事業所の登録＞履歴の編集" }).onClosed(() => {
                    // Get child value
                    var returnValue = nts.uk.ui.windows.getShared("addHistoryChildValue");
                });
            }
        }

        export class HealthInsuranceRateModel {
            healthDate: any;
            historyId: number;
            companyCode: string;
            officeCode: KnockoutObservable<string>;
            applyRange: string;
            autoCalculate: KnockoutObservable<number>;
            rateItems: KnockoutObservable<HealthInsuranceRateItemModel>;
            roundingMethods: KnockoutObservable<HealthInsuranceRoundingModel>;
            maxAmount: KnockoutObservable<number>;
            constructor(officeCode: string, autoCalculate: number, rateItems: HealthInsuranceRateItemModel,roundingMethods: HealthInsuranceRoundingModel, maxAmount:number) {
                this.healthDate = ko.observable('2016/04');
                this.officeCode= ko.observable('');
                this.autoCalculate= ko.observable(autoCalculate);
                this.rateItems = ko.observable(new HealthInsuranceRateItemModel());
                this.roundingMethods= ko.observable(new HealthInsuranceRoundingModel());
                this.maxAmount= ko.observable(0);
            }

        }

        export class PensionRateModel {
            pensionDate: any;
            historyId: number;
            companyCode: string;
            officeCode: KnockoutObservable<string>;
            applyRange: string;
            funInputOption: KnockoutObservable<number>;
            autoCalculate: KnockoutObservable<number>;
            rateItems: KnockoutObservable<PensionRateItemModel>;
            fundRateItems: KnockoutObservable<FunRateItemModel>;
            roundingMethods: KnockoutObservable<PensionRateRoundingModel>;
            maxAmount: KnockoutObservable<number>;
            officeRate: KnockoutObservable<number>;
            constructor(officeCode: string, funInputOption:number,autoCalculate: number,rateItems: PensionRateItemModel, fundRateItems: FunRateItemModel, roundingMethods: PensionRateRoundingModel, maxAmount: number, officeRate: number) {
                this.pensionDate = ko.observable('2016/04');
                this.officeCode = ko.observable('');
                this.funInputOption = ko.observable(funInputOption);
                this.autoCalculate = ko.observable(autoCalculate);
                this.rateItems = ko.observable(new PensionRateItemModel());
                this.fundRateItems = ko.observable(new FunRateItemModel());
                this.roundingMethods = ko.observable(new PensionRateRoundingModel());
                this.maxAmount = ko.observable(0);
                this.officeRate = ko.observable(0);
            }

        }
        export class HealthInsuranceRateItemModel   {
            healthSalaryPersonalGeneral: KnockoutObservable<number>;
            healthSalaryPersonalNursing: KnockoutObservable<number>;
            healthSalaryPersonalBasic: KnockoutObservable<number>;
            healthSalaryPersonalSpecific: KnockoutObservable<number>;
            healthSalaryCompanyGeneral: KnockoutObservable<number>;
            healthSalaryCompanyNursing: KnockoutObservable<number>;
            healthSalaryCompanyBasic: KnockoutObservable<number>;
            healthSalaryCompanySpecific: KnockoutObservable<number>;

            healthBonusPersonalGeneral:  KnockoutObservable<number>;
            healthBonusPersonalNursing: KnockoutObservable<number>;
            healthBonusPersonalBasic: KnockoutObservable<number>;
            healthBonusPersonalSpecific: KnockoutObservable<number>;
            healthBonusCompanyGeneral:  KnockoutObservable<number>;
            healthBonusCompanyNursing: KnockoutObservable<number>;
            healthBonusCompanyBasic: KnockoutObservable<number>;
            healthBonusCompanySpecific: KnockoutObservable<number>;
            constructor()
            {
                this.healthSalaryPersonalGeneral=ko.observable(0);
                this.healthSalaryCompanyGeneral=ko.observable(0);
                this.healthBonusPersonalGeneral=ko.observable(0);
                this.healthBonusCompanyGeneral=ko.observable(0);
                
                this.healthSalaryPersonalNursing=ko.observable(40990);
                this.healthSalaryCompanyNursing=ko.observable(40990);
                this.healthBonusPersonalNursing=ko.observable(40990);
                this.healthBonusCompanyNursing=ko.observable(40990);
                
                this.healthSalaryPersonalBasic=ko.observable(40990);
                this.healthSalaryCompanyBasic=ko.observable(40990);
                this.healthBonusPersonalBasic=ko.observable(40990);
                this.healthBonusCompanyBasic=ko.observable(40990);
                
                this.healthSalaryPersonalSpecific=ko.observable(40990);
                this.healthSalaryCompanySpecific=ko.observable(40990);
                this.healthBonusPersonalSpecific=ko.observable(40990);
                this.healthBonusCompanySpecific=ko.observable(40990);
                
            }
        }
        export class PensionRateItemModel{
            pensionSalaryPersonalSon: KnockoutObservable<number>;
            pensionSalaryCompanySon: KnockoutObservable<number>;
            pensionBonusPersonalSon: KnockoutObservable<number>;
            pensionBonusCompanySon: KnockoutObservable<number>;
            
            pensionSalaryPersonalDaughter: KnockoutObservable<number>;
            pensionSalaryCompanyDaughter: KnockoutObservable<number>;
            pensionBonusPersonalDaughter: KnockoutObservable<number>;
            pensionBonusCompanyDaughter: KnockoutObservable<number>;
            
            pensionSalaryPersonalUnknown: KnockoutObservable<number>;
            pensionSalaryCompanyUnknown: KnockoutObservable<number>;
            pensionBonusPersonalUnknown: KnockoutObservable<number>;
            pensionBonusCompanyUnknown: KnockoutObservable<number>;
            constructor() {
                this.pensionSalaryPersonalSon = ko.observable(0);
                this.pensionSalaryCompanySon = ko.observable(0);
                this.pensionBonusPersonalSon = ko.observable(0);
                this.pensionBonusCompanySon = ko.observable(0);

                this.pensionSalaryPersonalDaughter = ko.observable(0);
                this.pensionSalaryCompanyDaughter = ko.observable(0);
                this.pensionBonusPersonalDaughter = ko.observable(0);
                this.pensionBonusCompanyDaughter = ko.observable(0);

                this.pensionSalaryPersonalUnknown = ko.observable(0);
                this.pensionSalaryCompanyUnknown = ko.observable(0);
                this.pensionBonusPersonalUnknown = ko.observable(0);
                this.pensionBonusCompanyUnknown = ko.observable(0);
           }
        }
        
        export class FunRateItemModel{
            salaryPersonalSonExemption: KnockoutObservable<number>;
            salaryCompanySonExemption: KnockoutObservable<number>;
            bonusPersonalSonExemption: KnockoutObservable<number>;
            bonusCompanySonExemption: KnockoutObservable<number>;
            
            salaryPersonalSonBurden: KnockoutObservable<number>;
            salaryCompanySonBurden: KnockoutObservable<number>;
            bonusPersonalSonBurden: KnockoutObservable<number>;
            bonusCompanySonBurden: KnockoutObservable<number>;
            
            salaryPersonalDaughterExemption: KnockoutObservable<number>;
            salaryCompanyDaughterExemption: KnockoutObservable<number>;
            bonusPersonalDaughterExemption: KnockoutObservable<number>;
            bonusCompanyDaughterExemption: KnockoutObservable<number>;
            
            salaryPersonalDaughterBurden: KnockoutObservable<number>;
            salaryCompanyDaughterBurden: KnockoutObservable<number>;
            bonusPersonalDaughterBurden: KnockoutObservable<number>;
            bonusCompanyDaughterBurden: KnockoutObservable<number>;
            
            salaryPersonalUnknownExemption: KnockoutObservable<number>;
            salaryCompanyUnknownExemption: KnockoutObservable<number>;
            bonusPersonalUnknownExemption: KnockoutObservable<number>;
            bonusCompanyUnknownExemption: KnockoutObservable<number>;
            
            salaryPersonalUnknownBurden: KnockoutObservable<number>;
            salaryCompanyUnknownBurden: KnockoutObservable<number>;
            bonusPersonalUnknownBurden: KnockoutObservable<number>;
            bonusCompanyUnknownBurden: KnockoutObservable<number>;
            
            constructor (){
                this.salaryPersonalSonExemption = ko.observable(0);
                this.salaryCompanySonExemption = ko.observable(0);
                this.bonusPersonalSonExemption = ko.observable(0);
                this.bonusCompanySonExemption = ko.observable(0);
                
                
                this.salaryPersonalSonBurden = ko.observable(0);
                this.salaryCompanySonBurden = ko.observable(0);
                this.bonusPersonalSonBurden = ko.observable(0);
                this.bonusCompanySonBurden = ko.observable(0);
                
                this.salaryPersonalDaughterExemption = ko.observable(0);
                this.salaryCompanyDaughterExemption = ko.observable(0);
                this.bonusPersonalDaughterExemption = ko.observable(0);
                this.bonusCompanyDaughterExemption = ko.observable(0);
                
                this.salaryPersonalDaughterBurden = ko.observable(0);
                this.salaryCompanyDaughterBurden = ko.observable(0);
                this.bonusPersonalDaughterBurden = ko.observable(0);
                this.bonusCompanyDaughterBurden = ko.observable(0);
                
                this.salaryPersonalUnknownExemption = ko.observable(0);
                this.salaryCompanyUnknownExemption = ko.observable(0);
                this.bonusPersonalUnknownExemption = ko.observable(0);
                this.bonusCompanyUnknownExemption = ko.observable(0);
                
                this.salaryPersonalUnknownBurden = ko.observable(0);
                this.salaryCompanyUnknownBurden = ko.observable(0);
                this.bonusPersonalUnknownBurden = ko.observable(0);
                this.bonusCompanyUnknownBurden = ko.observable(0);
            }
        }
        export class HealthInsuranceRoundingModel {
            healthSalaryPersonalComboBox: KnockoutObservableArray<RoundingItem>;
            healthSalaryPersonalComboBoxItemName: KnockoutObservable<string>;
            healthSalaryPersonalComboBoxCurrentCode: KnockoutObservable<number>
            healthSalaryPersonalComboBoxSelectedCode: KnockoutObservable<string>;

            healthSalaryCompanyComboBox: KnockoutObservableArray<RoundingItem>;
            healthSalaryCompanyComboBoxItemName: KnockoutObservable<string>;
            healthSalaryCompanyComboBoxCurrentCode: KnockoutObservable<number>
            healthSalaryCompanyComboBoxSelectedCode: KnockoutObservable<string>;

            healthBonusPersonalComboBox: KnockoutObservableArray<RoundingItem>;
            healthBonusPersonalComboBoxItemName: KnockoutObservable<string>;
            healthBonusPersonalComboBoxCurrentCode: KnockoutObservable<number>
            healthBonusPersonalComboBoxSelectedCode: KnockoutObservable<string>;

            healthBonusCompanyComboBox: KnockoutObservableArray<RoundingItem>;
            healthBonusCompanyComboBoxItemName: KnockoutObservable<string>;
            healthBonusCompanyComboBoxCurrentCode: KnockoutObservable<number>
            healthBonusCompanyComboBoxSelectedCode: KnockoutObservable<string>;
            constructor() {
                this.healthSalaryPersonalComboBox = ko.observableArray<RoundingItem>(null);
                this.healthSalaryPersonalComboBoxItemName = ko.observable('');
                this.healthSalaryPersonalComboBoxCurrentCode = ko.observable(1);
                this.healthSalaryPersonalComboBoxSelectedCode = ko.observable('');

                this.healthSalaryCompanyComboBox = ko.observableArray<RoundingItem>(null);
                this.healthSalaryCompanyComboBoxItemName = ko.observable('');
                this.healthSalaryCompanyComboBoxCurrentCode = ko.observable(3);
                this.healthSalaryCompanyComboBoxSelectedCode = ko.observable('002');

                this.healthBonusPersonalComboBox = ko.observableArray<RoundingItem>(null);
                this.healthBonusPersonalComboBoxItemName = ko.observable('');
                this.healthBonusPersonalComboBoxCurrentCode = ko.observable(3);
                this.healthBonusPersonalComboBoxSelectedCode = ko.observable('002');

                this.healthBonusCompanyComboBox = ko.observableArray<RoundingItem>(null);
                this.healthBonusCompanyComboBoxItemName = ko.observable('');
                this.healthBonusCompanyComboBoxCurrentCode = ko.observable(3);
                this.healthBonusCompanyComboBoxSelectedCode = ko.observable('002');
            }
        }
        export class PensionRateRoundingModel{
            pensionSalaryPersonalComboBox: KnockoutObservableArray<RoundingItem>;
            pensionSalaryPersonalComboBoxItemName: KnockoutObservable<string>;
            pensionSalaryPersonalComboBoxCurrentCode: KnockoutObservable<number>
            pensionSalaryPersonalComboBoxSelectedCode: KnockoutObservable<string>;

            pensionSalaryCompanyComboBox: KnockoutObservableArray<RoundingItem>;
            pensionSalaryCompanyComboBoxItemName: KnockoutObservable<string>;
            pensionSalaryCompanyComboBoxCurrentCode: KnockoutObservable<number>
            pensionSalaryCompanyComboBoxSelectedCode: KnockoutObservable<string>;

            pensionBonusPersonalComboBox: KnockoutObservableArray<RoundingItem>;
            pensionBonusPersonalComboBoxItemName: KnockoutObservable<string>;
            pensionBonusPersonalComboBoxCurrentCode: KnockoutObservable<number>
            pensionBonusPersonalComboBoxSelectedCode: KnockoutObservable<string>;

            pensionBonusCompanyComboBox: KnockoutObservableArray<RoundingItem>;
            pensionBonusCompanyComboBoxItemName: KnockoutObservable<string>;
            pensionBonusCompanyComboBoxCurrentCode: KnockoutObservable<number>
            pensionBonusCompanyComboBoxSelectedCode: KnockoutObservable<string>;
            constructor() {
                this.pensionSalaryPersonalComboBox = ko.observableArray<RoundingItem>(null);
                this.pensionSalaryPersonalComboBoxItemName = ko.observable('');
                this.pensionSalaryPersonalComboBoxCurrentCode = ko.observable(1);
                this.pensionSalaryPersonalComboBoxSelectedCode = ko.observable('');

                this.pensionSalaryCompanyComboBox = ko.observableArray<RoundingItem>(null);
                this.pensionSalaryCompanyComboBoxItemName = ko.observable('');
                this.pensionSalaryCompanyComboBoxCurrentCode = ko.observable(3);
                this.pensionSalaryCompanyComboBoxSelectedCode = ko.observable('002');

                this.pensionBonusPersonalComboBox = ko.observableArray<RoundingItem>(null);
                this.pensionBonusPersonalComboBoxItemName = ko.observable('');
                this.pensionBonusPersonalComboBoxCurrentCode = ko.observable(3);
                this.pensionBonusPersonalComboBoxSelectedCode = ko.observable('002');

                this.pensionBonusCompanyComboBox = ko.observableArray<RoundingItem>(null);
                this.pensionBonusCompanyComboBoxItemName = ko.observable('');
                this.pensionBonusCompanyComboBoxCurrentCode = ko.observable(3);
                this.pensionBonusCompanyComboBoxSelectedCode = ko.observable('002');
            }    
        }
    }

    export class HealthInsuranceAvgearn {
        levelCode: KnockoutObservable<number>;
        personalAvg: KnockoutObservable<any>;
        companyAvg: KnockoutObservable<any>;
    }

    export class ChargeRateItem {
        companyRate: KnockoutObservable<number>;
        personalRate: KnockoutObservable<number>;
    }
}
