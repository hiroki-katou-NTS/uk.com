module nts.uk.pr.view.qmm008.a1 {
    export module viewmodel{
        import InsuranceOfficeItem = service.model.finder.InsuranceOfficeItemDto;
        import RoundingDto = service.model.finder.RoundingDto;
        import RoundingItemDto = service.model.finder.RoundingItemDto;
        import Enum = service.model.finder.Enum;
        import HealthInsuranceRateDto = service.model.finder.HealthInsuranceRateDto;
        import OfficeItemDto = service.model.finder.OfficeItemDto;
        import HealthInsuranceRateItemDto = service.model.finder.HealthInsuranceRateItemDto;
        import ChargeRateItemDto = service.model.finder.ChargeRateItemDto;
        import ScreenBaseModel = base.simplehistory.viewmodel.ScreenBaseModel;
        export class ScreenModel extends ScreenBaseModel<service.model.Office, service.model.Health>{
            //Health insurance rate Model
            healthModel: KnockoutObservable<HealthInsuranceRateModel>;
            healthInsuranceOfficeList: KnockoutObservableArray<InsuranceOfficeItem>;

            selectedInsuranceOfficeId: KnockoutObservable<string>;
            searchKey: KnockoutObservable<string>;

            //list rounding options
            roundingList: KnockoutObservableArray<Enum>;
            //healthTimeInput options
            timeInputOptions: any;
            //moneyInputOptions
            moneyInputOptions: any;
            //numberInputOptions
            Rate3: any;

            healthFilteredData: any;

            //for health auto calculate switch button
            healthAutoCalculateOptions: KnockoutObservableArray<any>;
            selectedRuleCode: KnockoutObservable<number>;
            //for control data after close dialog
            isTransistReturnData: KnockoutObservable<boolean>;
            //healthTotal
            isClickHealthHistory: KnockoutObservable<boolean>;
            // Flags
            isLoading: KnockoutObservable<boolean>;
            currentOfficeCode : KnockoutObservable<string>;
            constructor() {
                super({
                    functionName: '社会保険事業所',
                    service: service.instance,
                    removeMasterOnLastHistoryRemove: false
                });
                var self = this;
                //init model
                self.healthModel = ko.observable(new HealthInsuranceRateModel());
                // init insurance offices list
                self.healthInsuranceOfficeList = ko.observableArray<InsuranceOfficeItem>([]);

                self.healthFilteredData = ko.observableArray(nts.uk.util.flatArray(self.healthInsuranceOfficeList(), "childs"));
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

                self.Rate3 = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                    grouplength: 3,
                    decimallength: 3
                }));
                //health calculate switch
                self.healthAutoCalculateOptions = ko.observableArray([
                    { code: '0', name: 'する' },
                    { code: '1', name: 'しない' }
                ]);
                // add history dialog
                self.isTransistReturnData = ko.observable(false);
                // Health CurrencyEditor
                self.isClickHealthHistory = ko.observable(false);
                self.isLoading = ko.observable(true);
                self.currentOfficeCode = ko.observable('');
            } //end constructor

            // Start
            public start(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                self.getAllRounding().done(function() {
                    // Resolve
                    dfd.resolve(null);
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

            //string rounding to value
            public convertRounding(stringRounding: string) {
                switch (stringRounding) {
                    case Rounding.ROUNDUP: return "0";
                    case Rounding.TRUNCATION: return "1";
                    case Rounding.ROUNDDOWN: return "2";
                    case Rounding.DOWN5_UP6: return "3";
                    case Rounding.DOWN4_UP5: return "4";
                    default: return "0";
                }
            }

            //value to string rounding
            public convertToRounding(stringValue: string) {
                switch (stringValue) {
                    case "0": return Rounding.ROUNDUP;
                    case "1": return Rounding.TRUNCATION;
                    case "2": return Rounding.ROUNDDOWN;
                    case "3": return Rounding.DOWN5_UP6;
                    case "4": return Rounding.DOWN4_UP5;
                    default: return Rounding.ROUNDUP;
                }
            }

            //load health data by history code
            public loadHealth(data: HealthInsuranceRateDto) {
                var self = this;
                    if (data == null) {
                        return;
                    }
                    //Set health detail.
                    self.healthModel().historyId = data.historyId;
                    self.healthModel().startMonth(data.startMonth.substring(0, 4) + "/" + data.startMonth.substring(4, data.startMonth.length));
                    self.healthModel().endMonth(data.endMonth.substring(0, 4) + "/" + data.endMonth.substring(4, data.endMonth.length));

                    self.healthModel().companyCode = data.companyCode;
                    self.healthModel().officeCode(data.officeCode);
                    self.healthModel().autoCalculate(data.autoCalculate);
                    data.rateItems.forEach(function(item, index) {
                        if (item.payType == PaymentType.SALARY && item.insuranceType == HealthInsuranceType.GENERAL) {
                            self.healthModel().rateItems().healthSalaryPersonalGeneral(item.chargeRate.personalRate);
                            self.healthModel().rateItems().healthSalaryCompanyGeneral(item.chargeRate.companyRate);
                        }
                        if (item.payType == PaymentType.BONUS && item.insuranceType == HealthInsuranceType.GENERAL) {
                            self.healthModel().rateItems().healthBonusPersonalGeneral(item.chargeRate.personalRate);
                            self.healthModel().rateItems().healthBonusCompanyGeneral(item.chargeRate.companyRate);
                        }
                        if (item.payType == PaymentType.SALARY && item.insuranceType == HealthInsuranceType.NURSING) {
                            self.healthModel().rateItems().healthSalaryPersonalNursing(item.chargeRate.personalRate);
                            self.healthModel().rateItems().healthSalaryCompanyNursing(item.chargeRate.companyRate);
                        }
                        if (item.payType == PaymentType.BONUS && item.insuranceType == HealthInsuranceType.NURSING) {
                            self.healthModel().rateItems().healthBonusPersonalNursing(item.chargeRate.personalRate);
                            self.healthModel().rateItems().healthBonusCompanyNursing(item.chargeRate.companyRate);
                        }
                        if (item.payType == PaymentType.SALARY && item.insuranceType == HealthInsuranceType.BASIC) {
                            self.healthModel().rateItems().healthSalaryPersonalBasic(item.chargeRate.personalRate);
                            self.healthModel().rateItems().healthSalaryCompanyBasic(item.chargeRate.companyRate);
                        }
                        if (item.payType == PaymentType.BONUS && item.insuranceType == HealthInsuranceType.BASIC) {
                            self.healthModel().rateItems().healthBonusPersonalBasic(item.chargeRate.personalRate);
                            self.healthModel().rateItems().healthBonusCompanyBasic(item.chargeRate.companyRate);
                        }
                        if (item.payType == PaymentType.SALARY && item.insuranceType == HealthInsuranceType.SPECIAL) {
                            self.healthModel().rateItems().healthSalaryPersonalSpecific(item.chargeRate.personalRate);
                            self.healthModel().rateItems().healthSalaryCompanySpecific(item.chargeRate.companyRate);
                        }
                        if (item.payType == PaymentType.BONUS && item.insuranceType == HealthInsuranceType.SPECIAL) {
                            self.healthModel().rateItems().healthBonusPersonalSpecific(item.chargeRate.personalRate);
                            self.healthModel().rateItems().healthBonusCompanySpecific(item.chargeRate.companyRate);
                        }
                    });
                    //set rounding list
                    self.healthModel().roundingMethods().healthSalaryPersonalComboBox(self.roundingList());
                    self.healthModel().roundingMethods().healthSalaryCompanyComboBox(self.roundingList());
                    self.healthModel().roundingMethods().healthBonusPersonalComboBox(self.roundingList());
                    self.healthModel().roundingMethods().healthBonusCompanyComboBox(self.roundingList());

                    //Set selected rounding method
                    data.roundingMethods.forEach(function(item, index) {
                        if (item.payType == PaymentType.SALARY) {
                            self.healthModel().roundingMethods().healthSalaryPersonalComboBoxSelectedCode(self.convertRounding(item.roundAtrs.personalRoundAtr));
                            self.healthModel().roundingMethods().healthSalaryCompanyComboBoxSelectedCode(self.convertRounding(item.roundAtrs.companyRoundAtr));
                        }
                        else {
                            self.healthModel().roundingMethods().healthBonusPersonalComboBoxSelectedCode(self.convertRounding(item.roundAtrs.personalRoundAtr));
                            self.healthModel().roundingMethods().healthBonusCompanyComboBoxSelectedCode(self.convertRounding(item.roundAtrs.companyRoundAtr));
                        }
                    });
                    self.healthModel().maxAmount(data.maxAmount);
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
                roundingMethods.push(new RoundingDto(PaymentType.SALARY, new RoundingItemDto(self.convertToRounding(self.healthModel().roundingMethods().healthSalaryPersonalComboBoxSelectedCode()), self.convertToRounding(self.healthModel().roundingMethods().healthSalaryCompanyComboBoxSelectedCode()))));
                roundingMethods.push(new RoundingDto(PaymentType.BONUS, new RoundingItemDto(self.convertToRounding(self.healthModel().roundingMethods().healthBonusPersonalComboBoxSelectedCode()), self.convertToRounding(self.healthModel().roundingMethods().healthBonusCompanyComboBoxSelectedCode()))));
                return new service.model.finder.HealthInsuranceRateDto(self.healthModel().historyId, self.healthModel().companyCode, self.currentOfficeCode(), self.healthModel().startMonth(), self.healthModel().endMonth(), self.healthModel().autoCalculate(), rateItems, roundingMethods, self.healthModel().maxAmount());
            }

            //get current item office 
            public getDataOfHealthSelectedOffice(): InsuranceOfficeItem {
                var self = this;
                var saveVal = null;
                // Set parent value
                self.healthInsuranceOfficeList().forEach(function(item, index) {
                    if (self.currentOfficeCode() == item.code) {
                        saveVal = item;
                    }
                });
                return saveVal;
            }

            public save() {
                var self = this;
                //update health
                service.updateHealthRate(self.healthCollectData()).done(function() {
                });
            }
            
            /**
             * Load UnitPriceHistory detail.
             */
            onSelectHistory(id: string): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                self.isLoading(true);
                self.isClickHealthHistory(true);
                self.currentOfficeCode(self.getCurrentOfficeCode(id));
                service.instance.findHistoryByUuid(id).done(dto => {
                    self.loadHealth(dto);
                    self.isLoading(false);
//                    nts.uk.ui.windows.setShared('unitPriceHistoryModel', ko.toJS(this.unitPriceHistoryModel()));
                    $('.save-error').ntsError('clear');
                    dfd.resolve();
                });
                return dfd.promise();
            }
            onSave(): JQueryPromise<void>  {
                var self = this;
                var dfd = $.Deferred<void>();
                self.save();
                return dfd.promise();
            }
            public getCurrentOfficeCode(childId: string) {
                var self = this;
                if (self.masterHistoryList.length > 0) {
                    self.masterHistoryList.forEach(function(parentItem) {
                        if (parentItem.historyList) {
                            parentItem.historyList.forEach(function(childItem) {
                                if (childItem.uuid == childId) {
                                    return parentItem.code;
                                }
                            });
                        } else {
                            return parentItem.code;
                        }
                    });
                }
                return "";
            }
            
//            /**
//             * Clear all input and switch to new mode.
//             */
//            onRegistNew(): void {
//                var self = this;
//                $('.save-error').ntsError('clear');
//                self.setUnitPriceHistoryModel(self.getDefaultUnitPriceHistory());
//            }

            //open office register dialog
            public OpenModalOfficeRegister() {
                var self = this;
                // Set parent value
                nts.uk.ui.windows.sub.modal("/view/qmm/008/c/index.xhtml", { title: "会社保険事業所の登録＞事業所の登録" }).onClosed(() => {
                    //when close dialog -> reload office list
                    self.startPage();
                    // Get child value
                    var returnValue = nts.uk.ui.windows.getShared("insuranceOfficeChildValue");
                });
            }

            //open modal standard monthly price health
            public OpenModalStandardMonthlyPriceHealth() {
                // Set parent value
                nts.uk.ui.windows.setShared("dataOfSelectedOffice", this.getDataOfHealthSelectedOffice());
                nts.uk.ui.windows.setShared("healthModel", this.healthModel());

                nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                nts.uk.ui.windows.sub.modal("/view/qmm/008/h/index.xhtml", { title: "会社保険事業所の登録＞標準報酬月額保険料額表" }).onClosed(() => {
                    // Get child value
                    var returnValue = nts.uk.ui.windows.getShared("listOfficeOfChildValue");
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
            constructor() {
                this.startMonth = ko.observable("");
                this.endMonth = ko.observable("");
                this.officeCode = ko.observable('');
                this.autoCalculate = ko.observable(1);
                this.rateItems = ko.observable(new HealthInsuranceRateItemModel());
                this.roundingMethods = ko.observable(new HealthInsuranceRoundingModel());
                this.maxAmount = ko.observable(0);
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
    export class InsuranceGender {
        static MALE = "Male";
        static FEMALE = "Female";
        static UNKNOW = "Unknow";
    }
    export class AutoCalculate {
        static AUTO = "Auto";
        static MANUAL = "Manual";
    }
}
