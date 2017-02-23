module nts.uk.pr.view.qmm008.a {
    export module viewmodel {
        import InsuranceOfficeItem = service.model.finder.InsuranceOfficeItemDto;
        import HistoryItem = service.model.finder.HistoryItemDto;
        import RoundingDto = service.model.finder.RoundingDto;
        import RoundingItemDto = service.model.finder.RoundingItemDto;
        import Enum = service.model.finder.Enum;
        import HealthInsuranceRateDto = service.model.finder.HealthInsuranceRateDto;
        import HealthInsuranceRateItemDto = service.model.finder.HealthInsuranceRateItemDto;
        import ChargeRateItemDto = service.model.finder.ChargeRateItemDto;
        import PensionRateDto = service.model.finder.PensionRateDto;
        export class ScreenModel {
            //Health insurance rate Model
            healthModel: KnockoutObservable<HealthInsuranceRateModel>;
            pensionModel: KnockoutObservable<PensionRateModel>
            InsuranceOfficeList: KnockoutObservableArray<InsuranceOfficeItem>;
            selectedInsuranceOfficeId: KnockoutObservable<string>;
            searchKey: KnockoutObservable<string>;

            //list rounding options
            roundingList: KnockoutObservableArray<Enum>;
            //healthTimeInput options
            timeInputOptions: any;
            //moneyInputOptions
            moneyInputOptions: any;
            //numberInputOptions
            numberInputOptions: any;

            filteredData: any;
            officeSelectedCode: KnockoutObservable<string>;
            currentParentCode: KnockoutObservable<string>;
            currentChildCode: KnockoutObservable<string>;

            //for health auto calculate switch button
            healthAutoCalculateOptions: KnockoutObservableArray<any>;
            selectedRuleCode: KnockoutObservable<number>;
            //for pension fund switch button
            pensionFundInputOptions: KnockoutObservableArray<any>;
            //for pension auto calculate switch button
            pensionCalculateOptions: KnockoutObservableArray<any>;
            pensionCalculateSelectedCode: KnockoutObservable<number>;

            //for control data after close dialog
            isTransistReturnData: KnockoutObservable<boolean>;
            //health history input
            healthDate: KnockoutObservable<string>;
            //pension history input
            pensionDate: KnockoutObservable<string>;
            //healthTotal
            healthTotal: KnockoutObservable<number>;
            //pensionCurrency
            pensionCurrency: KnockoutObservable<number>;
            pensionOwnerRate: KnockoutObservable<number>;

            fundInputEnable: KnockoutObservable<boolean>;
            isClickHistory: KnockoutObservable<boolean>;
            constructor() {
                var self = this;

                //init model
                self.healthModel = ko.observable(new HealthInsuranceRateModel("code", 1, null, null, 15000));
                self.pensionModel = ko.observable(new PensionRateModel("code", 1, 1, null, null, null, 35000, 1.5));

                // init insurance offices list
                self.InsuranceOfficeList = ko.observableArray<InsuranceOfficeItem>([]);
                self.officeSelectedCode = ko.observable('');
                self.currentParentCode = ko.observable('');
                self.currentChildCode = ko.observable('');

                self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.InsuranceOfficeList(), "childs"));
                self.searchKey = ko.observable('');

                //init rounding list
                self.roundingList = ko.observableArray<Enum>([]);

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
                    { code: '0', name: 'する' },
                    { code: '1', name: 'しない' }
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

                // add history dialog
                self.isTransistReturnData = ko.observable(false);
                // health history input
                self.healthDate = ko.observable('2016/04');
                //pension history input
                self.pensionDate = ko.observable("2016/04");

                // Health CurrencyEditor
                self.healthTotal = ko.observable(5400000);
                //Pension CurrencyEditor
                self.pensionCurrency = ko.observable(1500000);
                //pension owner rate
                self.pensionOwnerRate = ko.observable(1.5);

                self.fundInputEnable = ko.observable(false);
                self.isClickHistory = ko.observable(false);
                //subscribe change select office
                self.officeSelectedCode.subscribe(function(officeSelectedCode: string) {
                    if (officeSelectedCode != null || officeSelectedCode != undefined) {
                        //if click office item
                        if (self.checkCode(officeSelectedCode)) {
                            self.currentParentCode(officeSelectedCode);
                            //TODO reset current child code
                            self.loadHistoryOfOffice(officeSelectedCode);
                            //reset data on view
                            self.healthModel(new HealthInsuranceRateModel("code", 1, null, null, 15000));
                            //TODO enabled button add new history
                            self.isClickHistory(false);
                        }
                        //if click history item
                        else {
                            self.currentChildCode(officeSelectedCode);
                            //TODO reset current parent code
                            //disabled button add new history
                            self.isClickHistory(true);
                            //officeSelectedCode = historyCode
                            $.when(self.load(officeSelectedCode)).done(function() {
                                //TODO load data success
                            }).fail(function(res) {
                                //TODO when load data error
                            });
                        }
                    }
                });
                self.pensionModel().fundInputApply.subscribe(function(pensionFundInputOptions: any) {
                    //TODO change select -> disable fun input
                    if (self.fundInputEnable()) {
                        self.fundInputEnable(false);
                    } else {
                        self.fundInputEnable(true);
                    }
                });
            } //end constructor

            // Start
            public start(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                //first load
                self.loadAllInsuranceOffice().done(function(data) {
                    self.InsuranceOfficeList(data);
                    // Load first item.
                    if ((self.InsuranceOfficeList() != null) && (self.InsuranceOfficeList().length > 0)) {
                        //Load select first item of list
                        self.officeSelectedCode(self.InsuranceOfficeList()[0].code);
                    } else {
                        //Open register new office screen
                        self.OpenModalOfficeRegister();
                    }
                    // Resolve
                    dfd.resolve(null);
                });
                self.getAllRounding().done(function(data) {
                    // Resolve
                    dfd.resolve(null);
                });
                // Return.
                return dfd.promise();
            }

            //load all insurance office
            public loadAllInsuranceOffice(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                // find data from search key
                service.findInsuranceOffice(self.searchKey()).done(function(data: Array<InsuranceOfficeItem>) {
                    dfd.resolve(data);
                });
                // Return.
                return dfd.promise();
            }

            //load All rounding method
            public getAllRounding(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                // Invoked service method
                service.findAllRounding().done(function(data: Array<Enum>) {
                    // Set list.
                    self.roundingList(data);
                    dfd.resolve(data);
                });
                // Return.
                return dfd.promise();
            }
            //check code of parent or child
            public checkCode(code: string) {
                var flag = false;
                this.InsuranceOfficeList().forEach(function(item, index) {
                    if (item.code == code) {
                        flag = true;
                    }
                });
                return flag;
            }
            //load all history when click expand office
            public loadHistoryOfOffice(officeCode: string) {
                var self = this;
                var officeData = self.InsuranceOfficeList();
                //TODO add childs(history) for each item office
                officeData.forEach(function(item, index) {
                    //search item have office code
                    if ((item.code == officeCode) && (item.childs.length == 0)) {
                        service.getAllHealthInsuranceItem(item.code).done(function(data: Array<HealthInsuranceRateDto>) {
                            data.forEach(function(item2, index2) {
                                //push history into office
                                officeData[index].childs.push(new InsuranceOfficeItem(index + item.code, item2.officeCode, index + item2.historyId + index2, [], item2.startMonth.substring(0,4)+"/"+item2.startMonth.substring(4,item2.startMonth.length) + "~" + item2.endMonth.substring(0,4)+"/"+item2.endMonth.substring(4,item2.endMonth.length)));
                            });
                            self.InsuranceOfficeList(officeData);
                            //focus first history of office
                            if (self.InsuranceOfficeList()[0].childs.length > 0)
                                self.officeSelectedCode(self.InsuranceOfficeList()[0].childs[0].code);
                        });
                    }
                });
            }

            //load data of item by history code
            public load(historyCode: string): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();

                service.getHealthInsuranceItemDetail(historyCode).done(function(data: HealthInsuranceRateDto) {
                    if (data == null) {
                        return;
                    }
                    // TODO Set detail health.
                    self.healthModel().historyId = data.historyId;
                    self.healthModel().startMonth(data.startMonth.substring(0,4)+"/"+data.startMonth.substring(4,data.startMonth.length));
                    self.healthModel().endMonth(data.endMonth.substring(0,4)+"/"+data.endMonth.substring(4,data.endMonth.length));

                    self.healthModel().companyCode = data.companyCode;
                    self.healthModel().officeCode(data.officeCode);
                    self.healthModel().autoCalculate(data.autoCalculate);

                    self.healthModel().rateItems().healthSalaryPersonalGeneral(data.rateItems[0].chargeRate.companyRate);
                    self.healthModel().rateItems().healthSalaryCompanyGeneral(data.rateItems[0].chargeRate.companyRate);
                    self.healthModel().rateItems().healthBonusPersonalGeneral(data.rateItems[0].chargeRate.companyRate);
                    self.healthModel().rateItems().healthBonusCompanyGeneral(data.rateItems[0].chargeRate.companyRate);

                    self.healthModel().rateItems().healthSalaryPersonalNursing(data.rateItems[0].chargeRate.companyRate);
                    self.healthModel().rateItems().healthSalaryCompanyNursing(data.rateItems[0].chargeRate.companyRate);
                    self.healthModel().rateItems().healthBonusPersonalNursing(data.rateItems[0].chargeRate.companyRate);
                    self.healthModel().rateItems().healthBonusCompanyNursing(data.rateItems[0].chargeRate.companyRate);

                    self.healthModel().rateItems().healthSalaryPersonalBasic(data.rateItems[0].chargeRate.companyRate);
                    self.healthModel().rateItems().healthSalaryCompanyBasic(data.rateItems[0].chargeRate.companyRate);
                    self.healthModel().rateItems().healthBonusPersonalBasic(data.rateItems[0].chargeRate.companyRate);
                    self.healthModel().rateItems().healthBonusCompanyBasic(data.rateItems[0].chargeRate.companyRate);

                    self.healthModel().rateItems().healthSalaryPersonalSpecific(data.rateItems[0].chargeRate.companyRate);
                    self.healthModel().rateItems().healthSalaryCompanySpecific(data.rateItems[0].chargeRate.companyRate);
                    self.healthModel().rateItems().healthBonusPersonalSpecific(data.rateItems[0].chargeRate.companyRate);
                    self.healthModel().rateItems().healthBonusCompanySpecific(data.rateItems[0].chargeRate.companyRate);
                    //set rounding list
                    self.healthModel().roundingMethods().healthSalaryPersonalComboBox(self.roundingList());
                    self.healthModel().roundingMethods().healthSalaryCompanyComboBox(self.roundingList());
                    self.healthModel().roundingMethods().healthBonusPersonalComboBox(self.roundingList());
                    self.healthModel().roundingMethods().healthBonusCompanyComboBox(self.roundingList());

                    //TODO set selected rounding method                     
                    self.healthModel().roundingMethods().healthSalaryPersonalComboBoxSelectedCode("1");
                    self.healthModel().roundingMethods().healthSalaryCompanyComboBoxSelectedCode("1");
                    self.healthModel().roundingMethods().healthSalaryPersonalComboBoxSelectedCode("1");
                    self.healthModel().roundingMethods().healthBonusCompanyComboBoxSelectedCode("1");

                    self.healthModel().maxAmount(data.maxAmount);
                    // Resolve
                    dfd.resolve();
                }).fail(function() {
                }).always(function(res) {
                });

                service.getPensionItemDetail(historyCode).done(function(data: PensionRateDto) {
                    if (data == null) {
                        return;
                    }
                    //TODO Set detail pension.
                    self.pensionModel().historyId = data.historyId;
                    self.pensionModel().companyCode = data.companyCode;
                    self.pensionModel().officeCode(data.officeCode);
                    self.pensionModel().startMonth(data.startMonth.toString());
                    self.pensionModel().endMonth(data.endMonth.toString());
                    if (data.autoCalculate)
                        self.pensionModel().autoCalculate(1);
                    else
                        self.pensionModel().autoCalculate(2);

                    if (data.fundInputApply)
                        self.pensionModel().fundInputApply(1);
                    else
                        self.pensionModel().fundInputApply(2);
                    self.pensionModel().rateItems().pensionSalaryPersonalSon(data.premiumRateItems[0].companyRate);
                    self.pensionModel().rateItems().pensionSalaryCompanySon(data.premiumRateItems[0].companyRate);
                    self.pensionModel().rateItems().pensionBonusPersonalSon(data.premiumRateItems[0].companyRate);
                    self.pensionModel().rateItems().pensionBonusCompanySon(data.premiumRateItems[0].companyRate);

                    self.pensionModel().rateItems().pensionSalaryPersonalDaughter(data.premiumRateItems[0].companyRate);
                    self.pensionModel().rateItems().pensionSalaryCompanyDaughter(data.premiumRateItems[0].companyRate);
                    self.pensionModel().rateItems().pensionBonusPersonalDaughter(data.premiumRateItems[0].companyRate);
                    self.pensionModel().rateItems().pensionBonusCompanyDaughter(data.premiumRateItems[0].companyRate);

                    self.pensionModel().rateItems().pensionSalaryPersonalUnknown(data.premiumRateItems[0].companyRate);
                    self.pensionModel().rateItems().pensionSalaryCompanyUnknown(data.premiumRateItems[0].companyRate);
                    self.pensionModel().rateItems().pensionBonusPersonalUnknown(data.premiumRateItems[0].companyRate);
                    self.pensionModel().rateItems().pensionBonusCompanyUnknown(data.premiumRateItems[0].companyRate);

                    self.pensionModel().fundRateItems().salaryPersonalSonExemption(data.fundRateItems[0].burdenChargeCompanyRate);
                    self.pensionModel().fundRateItems().salaryCompanySonExemption(data.fundRateItems[0].burdenChargeCompanyRate);
                    self.pensionModel().fundRateItems().bonusPersonalSonExemption(data.fundRateItems[0].burdenChargeCompanyRate);
                    self.pensionModel().fundRateItems().bonusCompanySonExemption(data.fundRateItems[0].burdenChargeCompanyRate);

                    //set rounding list
                    self.pensionModel().roundingMethods().pensionSalaryPersonalComboBox(self.roundingList());
                    self.pensionModel().roundingMethods().pensionSalaryCompanyComboBox(self.roundingList());
                    self.pensionModel().roundingMethods().pensionBonusPersonalComboBox(self.roundingList());
                    self.pensionModel().roundingMethods().pensionBonusCompanyComboBox(self.roundingList());

                    self.pensionModel().maxAmount(data.maxAmount);
                    self.pensionModel().childContributionRate(data.childContributionRate);
                    // Resolve
                    dfd.resolve();
                }).fail(function() {
                }).always(function(res) {
                });

                // Ret promise.
                return dfd.promise();
            }

            private healthCollectData() {
                var self = this;
                var rates = self.healthModel().rateItems();

                var rateItems: Array<HealthInsuranceRateItemDto> = [];
                rateItems.push(new HealthInsuranceRateItemDto(PaymentType.SALARY, HealthInsuranceType.GENERAL, new ChargeRateItemDto(rates.healthSalaryCompanyGeneral(), rates.healthSalaryPersonalGeneral())));
                rateItems.push(new HealthInsuranceRateItemDto(PaymentType.SALARY, HealthInsuranceType.NURSING, new ChargeRateItemDto(rates.healthSalaryCompanyNursing(), rates.healthSalaryPersonalNursing())));
                rateItems.push(new HealthInsuranceRateItemDto(PaymentType.SALARY, HealthInsuranceType.BASIC, new ChargeRateItemDto(rates.healthSalaryCompanyBasic(), rates.healthSalaryPersonalBasic())));
                rateItems.push(new HealthInsuranceRateItemDto(PaymentType.SALARY, HealthInsuranceType.SPECIAL, new ChargeRateItemDto(rates.healthSalaryCompanySpecific(), rates.healthSalaryPersonalSpecific())));
                rateItems.push(new HealthInsuranceRateItemDto(PaymentType.BONUS, HealthInsuranceType.GENERAL, new ChargeRateItemDto(rates.healthBonusCompanyGeneral(), rates.healthBonusPersonalGeneral())));
                rateItems.push(new HealthInsuranceRateItemDto(PaymentType.BONUS, HealthInsuranceType.NURSING, new ChargeRateItemDto(rates.healthBonusCompanyNursing(), rates.healthBonusPersonalNursing())));
                rateItems.push(new HealthInsuranceRateItemDto(PaymentType.BONUS, HealthInsuranceType.BASIC, new ChargeRateItemDto(rates.healthBonusCompanyBasic(), rates.healthBonusPersonalBasic())));
                rateItems.push(new HealthInsuranceRateItemDto(PaymentType.BONUS, HealthInsuranceType.SPECIAL, new ChargeRateItemDto(rates.healthBonusCompanySpecific(), rates.healthBonusPersonalSpecific())));

                var roundingMethods: Array<RoundingDto> = [];
                var rounding = self.healthModel().roundingMethods();
                roundingMethods.push(new RoundingDto(PaymentType.SALARY, new RoundingItemDto(Rounding.ROUNDUP, Rounding.ROUNDUP)));
                roundingMethods.push(new RoundingDto(PaymentType.BONUS, new RoundingItemDto(Rounding.ROUNDUP, Rounding.ROUNDUP)));
                return new service.model.finder.HealthInsuranceRateDto("", "", self.currentParentCode(), self.healthModel().startMonth(), self.healthModel().endMonth(),1, rateItems, roundingMethods, self.healthModel().maxAmount());
            }
            
            //TODO delete
            public resize() {
                if ($("#tabs-complex").width() > 700)
                    $("#tabs-complex").width(700);
                else
                    $("#tabs-complex").width("auto");
            }

            public getDataOfSelectedOffice(): InsuranceOfficeItem {
                var self = this;
                var saveVal = null;
                // Set parent value
                this.InsuranceOfficeList().forEach(function(item, index) {
                    if (self.currentParentCode() == item.code) {
                        saveVal = item;
                    }
                });
                return saveVal;
            }
            // get index of history in list child
            public getIndexOfHistory(historyId: string) {
                var self = this;
                self.getDataOfSelectedOffice().childs.forEach(function(item, index) {
                    if (item.historyId == historyId) {
                        return index;
                    }
                });
                return null;
            }
            public refreshOfficeList(returnValue: InsuranceOfficeItem) {
                var self = this;
                var currentInsuranceOfficeList = self.InsuranceOfficeList();
                if (returnValue != undefined && returnValue != null) {
                    currentInsuranceOfficeList.forEach(function(item, index) {
                        if (item.code == returnValue.code) {
                            currentInsuranceOfficeList[index] = returnValue;
                        }
                    });
                }
                self.InsuranceOfficeList([]);
                self.InsuranceOfficeList(currentInsuranceOfficeList);
            }
            public save() {
                var self = this;
                //check update or create new 
                //save office
                //save history
                //save health
                service.registerHealthRate(self.healthCollectData()).done(function() {

                });
                //save pension
            }
            // open dialog add history 
            public OpenModalAddHistory() {
                var self = this;
                var sendOfficeItem = self.getDataOfSelectedOffice();
                //TODO get previous start month 
                var previousStartDate = "2016/04";

                nts.uk.ui.windows.setShared("sendOfficeParentValue", sendOfficeItem);

                nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                nts.uk.ui.windows.sub.modal("/view/qmm/008/b/index.xhtml", { title: "会社保険事業所の登録＞履歴の追加" }).onClosed(() => {
                    // Get child value return office Item
                    var returnValue = nts.uk.ui.windows.getShared("addHistoryChildValue");
                    self.refreshOfficeList(returnValue);
                });
            }

            //open office register dialog
            public OpenModalOfficeRegister() {
                var self = this;
                // Set parent value
                nts.uk.ui.windows.setShared("officeCodeOfParentValue", self.officeSelectedCode());
                nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                nts.uk.ui.windows.sub.modal("/view/qmm/008/c/index.xhtml", { title: "会社保険事業所の登録＞事業所の登録" }).onClosed(() => {
                    //when close dialog -> reload office list
                    self.start();
                    // Get child value
                    var returnValue = nts.uk.ui.windows.getShared("listOfficeOfChildValue");
                });
            }

            //open modal standard monthly price health
            public OpenModalStandardMonthlyPriceHealth() {
                // Set parent value
                nts.uk.ui.windows.setShared("dataOfSelectedOffice", this.getDataOfSelectedOffice());
                nts.uk.ui.windows.setShared("healthModel", this.healthModel());

                nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                nts.uk.ui.windows.sub.modal("/view/qmm/008/h/index.xhtml", { title: "会社保険事業所の登録＞標準報酬月額保険料額表" }).onClosed(() => {
                    // Get child value
                    var returnValue = nts.uk.ui.windows.getShared("listOfficeOfChildValue");
                });
            }

            //open modal standard monthly price pension 
            public OpenModalStandardMonthlyPricePension() {
                // Set parent value
                nts.uk.ui.windows.setShared("dataOfSelectedOffice", this.getDataOfSelectedOffice());
                nts.uk.ui.windows.setShared("pensionModel", this.pensionModel());

                nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                nts.uk.ui.windows.sub.modal("/view/qmm/008/i/index.xhtml", { title: "会社保険事業所の登録＞標準報酬月額保険料額表" }).onClosed(() => {
                    // Get child value
                    var returnValue = nts.uk.ui.windows.getShared("listOfficeOfChildValue");
                });
            }

            //open modal config history 
            public OpenModalConfigHistory() {
                var self = this;
                var sendOfficeParentValue = this.getDataOfSelectedOffice();
                // Set parent value
                nts.uk.ui.windows.setShared("sendOfficeParentValue", sendOfficeParentValue);
                nts.uk.ui.windows.setShared("currentChildCode", self.currentChildCode());
                nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                nts.uk.ui.windows.sub.modal("/view/qmm/008/f/index.xhtml", { title: "会社保険事業所の登録＞履歴の編集" }).onClosed(() => {

                    // Get child value return office Item
                    var returnValue = nts.uk.ui.windows.getShared("updateHistoryChildValue");
                    self.refreshOfficeList(returnValue);
                });
            }

        }

        export class HealthInsuranceRateModel {
            historyId: string;
            companyCode: string;
            officeCode: KnockoutObservable<string>;
            startMonth: KnockoutObservable<string>;
            endMonth: KnockoutObservable<string>;
            autoCalculate: KnockoutObservable<number>;
            rateItems: KnockoutObservable<HealthInsuranceRateItemModel>;
            roundingMethods: KnockoutObservable<HealthInsuranceRoundingModel>;
            maxAmount: KnockoutObservable<number>;
            constructor(officeCode: string, autoCalculate: number, rateItems: HealthInsuranceRateItemModel, roundingMethods: HealthInsuranceRoundingModel, maxAmount: number) {
                this.startMonth = ko.observable("2016/04");
                this.endMonth = ko.observable("2016/04");
                this.officeCode = ko.observable('');
                this.autoCalculate = ko.observable(autoCalculate);
                this.rateItems = ko.observable(new HealthInsuranceRateItemModel());
                this.roundingMethods = ko.observable(new HealthInsuranceRoundingModel());
                this.maxAmount = ko.observable(0);
            }

        }

        export class PensionRateModel {
            historyId: string;
            companyCode: string;
            officeCode: KnockoutObservable<string>;
            startMonth: KnockoutObservable<string>;
            endMonth: KnockoutObservable<string>;
            fundInputApply: KnockoutObservable<number>;
            autoCalculate: KnockoutObservable<number>;
            rateItems: KnockoutObservable<PensionRateItemModel>;
            fundRateItems: KnockoutObservable<FunRateItemModel>;
            roundingMethods: KnockoutObservable<PensionRateRoundingModel>;
            maxAmount: KnockoutObservable<number>;
            childContributionRate: KnockoutObservable<number>;
            constructor(officeCode: string, fundInputApply: number, autoCalculate: number, rateItems: PensionRateItemModel, fundRateItems: FunRateItemModel, roundingMethods: PensionRateRoundingModel, maxAmount: number, childContributionRate: number) {
                this.startMonth = ko.observable('2016/04');
                this.endMonth = ko.observable("2016/04");
                this.officeCode = ko.observable('');
                this.fundInputApply = ko.observable(fundInputApply);
                this.autoCalculate = ko.observable(autoCalculate);
                this.rateItems = ko.observable(new PensionRateItemModel());
                this.fundRateItems = ko.observable(new FunRateItemModel());
                this.roundingMethods = ko.observable(new PensionRateRoundingModel());
                this.maxAmount = ko.observable(0);
                this.childContributionRate = ko.observable(0);
            }

        }
        export class HealthInsuranceRateItemModel {
            healthSalaryPersonalGeneral: KnockoutObservable<number>;
            healthSalaryPersonalNursing: KnockoutObservable<number>;
            healthSalaryPersonalBasic: KnockoutObservable<number>;
            healthSalaryPersonalSpecific: KnockoutObservable<number>;
            healthSalaryCompanyGeneral: KnockoutObservable<number>;
            healthSalaryCompanyNursing: KnockoutObservable<number>;
            healthSalaryCompanyBasic: KnockoutObservable<number>;
            healthSalaryCompanySpecific: KnockoutObservable<number>;

            healthBonusPersonalGeneral: KnockoutObservable<number>;
            healthBonusPersonalNursing: KnockoutObservable<number>;
            healthBonusPersonalBasic: KnockoutObservable<number>;
            healthBonusPersonalSpecific: KnockoutObservable<number>;
            healthBonusCompanyGeneral: KnockoutObservable<number>;
            healthBonusCompanyNursing: KnockoutObservable<number>;
            healthBonusCompanyBasic: KnockoutObservable<number>;
            healthBonusCompanySpecific: KnockoutObservable<number>;
            constructor() {
                this.healthSalaryPersonalGeneral = ko.observable(0);
                this.healthSalaryCompanyGeneral = ko.observable(0);
                this.healthBonusPersonalGeneral = ko.observable(0);
                this.healthBonusCompanyGeneral = ko.observable(0);

                this.healthSalaryPersonalNursing = ko.observable(0);
                this.healthSalaryCompanyNursing = ko.observable(0);
                this.healthBonusPersonalNursing = ko.observable(0);
                this.healthBonusCompanyNursing = ko.observable(0);

                this.healthSalaryPersonalBasic = ko.observable(0);
                this.healthSalaryCompanyBasic = ko.observable(0);
                this.healthBonusPersonalBasic = ko.observable(0);
                this.healthBonusCompanyBasic = ko.observable(0);

                this.healthSalaryPersonalSpecific = ko.observable(0);
                this.healthSalaryCompanySpecific = ko.observable(0);
                this.healthBonusPersonalSpecific = ko.observable(0);
                this.healthBonusCompanySpecific = ko.observable(0);

            }
        }
        export class PensionRateItemModel {
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

        export class FunRateItemModel {
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

            constructor() {
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
            healthSalaryPersonalComboBox: KnockoutObservableArray<Enum>;
            healthSalaryPersonalComboBoxItemName: KnockoutObservable<string>;
            healthSalaryPersonalComboBoxCurrentCode: KnockoutObservable<number>
            healthSalaryPersonalComboBoxSelectedCode: KnockoutObservable<string>;

            healthSalaryCompanyComboBox: KnockoutObservableArray<Enum>;
            healthSalaryCompanyComboBoxItemName: KnockoutObservable<string>;
            healthSalaryCompanyComboBoxCurrentCode: KnockoutObservable<number>
            healthSalaryCompanyComboBoxSelectedCode: KnockoutObservable<string>;

            healthBonusPersonalComboBox: KnockoutObservableArray<Enum>;
            healthBonusPersonalComboBoxItemName: KnockoutObservable<string>;
            healthBonusPersonalComboBoxCurrentCode: KnockoutObservable<number>
            healthBonusPersonalComboBoxSelectedCode: KnockoutObservable<string>;

            healthBonusCompanyComboBox: KnockoutObservableArray<Enum>;
            healthBonusCompanyComboBoxItemName: KnockoutObservable<string>;
            healthBonusCompanyComboBoxCurrentCode: KnockoutObservable<number>
            healthBonusCompanyComboBoxSelectedCode: KnockoutObservable<string>;
            constructor() {
                this.healthSalaryPersonalComboBox = ko.observableArray<Enum>(null);
                this.healthSalaryPersonalComboBoxItemName = ko.observable('');
                this.healthSalaryPersonalComboBoxCurrentCode = ko.observable(1);
                this.healthSalaryPersonalComboBoxSelectedCode = ko.observable('');

                this.healthSalaryCompanyComboBox = ko.observableArray<Enum>(null);
                this.healthSalaryCompanyComboBoxItemName = ko.observable('');
                this.healthSalaryCompanyComboBoxCurrentCode = ko.observable(3);
                this.healthSalaryCompanyComboBoxSelectedCode = ko.observable('002');

                this.healthBonusPersonalComboBox = ko.observableArray<Enum>(null);
                this.healthBonusPersonalComboBoxItemName = ko.observable('');
                this.healthBonusPersonalComboBoxCurrentCode = ko.observable(3);
                this.healthBonusPersonalComboBoxSelectedCode = ko.observable('002');

                this.healthBonusCompanyComboBox = ko.observableArray<Enum>(null);
                this.healthBonusCompanyComboBoxItemName = ko.observable('');
                this.healthBonusCompanyComboBoxCurrentCode = ko.observable(3);
                this.healthBonusCompanyComboBoxSelectedCode = ko.observable('002');
            }
        }
        export class PensionRateRoundingModel {
            pensionSalaryPersonalComboBox: KnockoutObservableArray<Enum>;
            pensionSalaryPersonalComboBoxItemName: KnockoutObservable<string>;
            pensionSalaryPersonalComboBoxCurrentCode: KnockoutObservable<number>
            pensionSalaryPersonalComboBoxSelectedCode: KnockoutObservable<string>;

            pensionSalaryCompanyComboBox: KnockoutObservableArray<Enum>;
            pensionSalaryCompanyComboBoxItemName: KnockoutObservable<string>;
            pensionSalaryCompanyComboBoxCurrentCode: KnockoutObservable<number>
            pensionSalaryCompanyComboBoxSelectedCode: KnockoutObservable<string>;

            pensionBonusPersonalComboBox: KnockoutObservableArray<Enum>;
            pensionBonusPersonalComboBoxItemName: KnockoutObservable<string>;
            pensionBonusPersonalComboBoxCurrentCode: KnockoutObservable<number>
            pensionBonusPersonalComboBoxSelectedCode: KnockoutObservable<string>;

            pensionBonusCompanyComboBox: KnockoutObservableArray<Enum>;
            pensionBonusCompanyComboBoxItemName: KnockoutObservable<string>;
            pensionBonusCompanyComboBoxCurrentCode: KnockoutObservable<number>
            pensionBonusCompanyComboBoxSelectedCode: KnockoutObservable<string>;
            constructor() {
                this.pensionSalaryPersonalComboBox = ko.observableArray<Enum>(null);
                this.pensionSalaryPersonalComboBoxItemName = ko.observable('');
                this.pensionSalaryPersonalComboBoxCurrentCode = ko.observable(1);
                this.pensionSalaryPersonalComboBoxSelectedCode = ko.observable('');

                this.pensionSalaryCompanyComboBox = ko.observableArray<Enum>(null);
                this.pensionSalaryCompanyComboBoxItemName = ko.observable('');
                this.pensionSalaryCompanyComboBoxCurrentCode = ko.observable(3);
                this.pensionSalaryCompanyComboBoxSelectedCode = ko.observable('002');

                this.pensionBonusPersonalComboBox = ko.observableArray<Enum>(null);
                this.pensionBonusPersonalComboBoxItemName = ko.observable('');
                this.pensionBonusPersonalComboBoxCurrentCode = ko.observable(3);
                this.pensionBonusPersonalComboBoxSelectedCode = ko.observable('002');

                this.pensionBonusCompanyComboBox = ko.observableArray<Enum>(null);
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

    export class PaymentType {
        static SALARY = 'Salary';
        static BONUS = 'Bonus'
    }

    export class HealthInsuranceType {
        static GENERAL = 'General';
        static NURSING = 'Nursing';
        static BASIC = 'Basic';
        static SPECIAL = 'Special'
    }
    export class Rounding {
        static ROUNDUP = 'RoundUp';
        static TRUNCATION = 'Truncation';
        static ROUNDDOWN = 'RoundDown';
        static DOWN5_UP6 = 'Down5_Up6';
        static DOWN4_UP5 = 'Down4_Up5'
    }
}
