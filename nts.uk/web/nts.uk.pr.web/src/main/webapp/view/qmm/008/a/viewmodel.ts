module nts.uk.pr.view.qmm008.a {
    export module viewmodel {
        import InsuranceOfficeItem = service.model.finder.InsuranceOfficeItemDto;
        import RoundingDto = service.model.finder.RoundingDto;
        import RoundingItemDto = service.model.finder.RoundingItemDto;
        import Enum = service.model.finder.Enum;
        import HealthInsuranceRateDto = service.model.finder.HealthInsuranceRateDto;
        import OfficeItemDto = service.model.finder.OfficeItemDto;
        import HealthInsuranceRateItemDto = service.model.finder.HealthInsuranceRateItemDto;
        import ChargeRateItemDto = service.model.finder.ChargeRateItemDto;
        import PensionRateDto = service.model.finder.PensionRateDto;
        import PensionRateItemDto = service.model.finder.PensionRateItemDto;
        import FundRateItemDto = service.model.finder.FundRateItemDto;

        export class ScreenModel {
            //Health insurance rate Model
            healthModel: KnockoutObservable<HealthInsuranceRateModel>;
            pensionModel: KnockoutObservable<PensionRateModel>

            healthInsuranceOfficeList: KnockoutObservableArray<InsuranceOfficeItem>;
            pensionInsuranceOfficeList: KnockoutObservableArray<InsuranceOfficeItem>;

            selectedInsuranceOfficeId: KnockoutObservable<string>;
            searchKey: KnockoutObservable<string>;

            //list rounding options
            roundingList: KnockoutObservableArray<Enum>;
            //healthTimeInput options
            timeInputOptions: any;
            //moneyInputOptions
            moneyInputOptions: any;
            //numberInputOptions
            Rate2: any;
            Rate3: any;

            healthFilteredData: any;
            pensionFilteredData: any;

            healthOfficeSelectedCode: KnockoutObservable<string>;
            healthCurrentParentCode: KnockoutObservable<string>;
            healthCurrentChildCode: KnockoutObservable<string>;

            pensionOfficeSelectedCode: KnockoutObservable<string>;
            pensionCurrentParentCode: KnockoutObservable<string>;
            pensionCurrentChildCode: KnockoutObservable<string>;

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
            isClickHealthHistory: KnockoutObservable<boolean>;
            isClickPensionHistory: KnockoutObservable<boolean>;

            startMonthTemp: KnockoutObservable<string>;
            endMonthTemp: KnockoutObservable<string>;
            constructor() {
                var self = this;

                //init model
                self.healthModel = ko.observable(new HealthInsuranceRateModel());
                self.pensionModel = ko.observable(new PensionRateModel());

                // init insurance offices list
                self.healthInsuranceOfficeList = ko.observableArray<InsuranceOfficeItem>([]);
                self.pensionInsuranceOfficeList = ko.observableArray<InsuranceOfficeItem>([]);

                self.healthOfficeSelectedCode = ko.observable('');
                self.healthCurrentParentCode = ko.observable('');
                self.healthCurrentChildCode = ko.observable('');
                self.pensionOfficeSelectedCode = ko.observable('');
                self.pensionCurrentParentCode = ko.observable('');
                self.pensionCurrentChildCode = ko.observable('');

                self.healthFilteredData = ko.observableArray(nts.uk.util.flatArray(self.healthInsuranceOfficeList(), "childs"));
                self.pensionFilteredData = ko.observableArray(nts.uk.util.flatArray(self.pensionInsuranceOfficeList(), "childs"));

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

                self.Rate2 = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                    grouplength: 3,
                    decimallength: 2
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
                self.selectedRuleCode = ko.observable(1);//
                //pension fund switch 
                self.pensionFundInputOptions = ko.observableArray([
                    { code: '1', name: '有' },
                    { code: '0', name: '無' }
                ]);
                //pension calculate switch 
                self.pensionCalculateOptions = ko.observableArray([
                    { code: '0', name: 'する' },
                    { code: '1', name: 'しない' }
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
                self.isClickHealthHistory = ko.observable(false);
                self.isClickPensionHistory = ko.observable(false);
                self.startMonthTemp = ko.observable('');
                self.endMonthTemp = ko.observable('');
                //subscribe change select office
                self.healthOfficeSelectedCode.subscribe(function(officeSelectedCode: string) {
                    if (officeSelectedCode.length>1) {
                        //if click office item
                        if (self.healthCheckCode(officeSelectedCode)) {
                            self.healthCurrentParentCode(officeSelectedCode);
                            //reset data on view
                            var roudingList = new HealthInsuranceRoundingModel();
                            roudingList.healthSalaryPersonalComboBox(self.roundingList());
                            roudingList.healthSalaryCompanyComboBox(self.roundingList());
                            roudingList.healthBonusPersonalComboBox(self.roundingList());
                            roudingList.healthBonusCompanyComboBox(self.roundingList());
                            self.healthModel(new HealthInsuranceRateModel());
                            // enabled button add new history
                            self.isClickHealthHistory(false);
                        }
                        //if click history item
                        else {
                            self.healthCurrentChildCode(officeSelectedCode);
                            //disabled button add new history
                            self.isClickHealthHistory(true);
                            //officeSelectedCode = historyCode
                            //if is creat new history
                            if (officeSelectedCode.length > 10) {
                                $.when(self.loadHealth(officeSelectedCode)).done(function() {
                                    //load data success
                                }).fail(function(res) {
                                    //when load data error
                                });
                            }
                        }
                    }
                });

                //subscribe change select office
                self.pensionOfficeSelectedCode.subscribe(function(officeSelectedCode: string) {
                    if (officeSelectedCode.length>1) {
                        //if click office item
                        if (self.pensionCheckCode(officeSelectedCode)) {
                            self.pensionCurrentParentCode(officeSelectedCode);
                            //TODO reset data on view
                            //                            self.pensionModel(new PensionRateModel("code", 1, 1, null, null, null, 35000, 1.5));
                            // enabled button add new history
                            self.isClickPensionHistory(false);
                        }
                        //if click history item
                        else {
                            self.pensionCurrentChildCode(officeSelectedCode);
                            //disabled button add new history
                            self.isClickPensionHistory(true);
                            //officeSelectedCode = historyCode
                            //if is creat new history
                            if (officeSelectedCode.length > 10) {
                                $.when(self.loadPension(officeSelectedCode)).done(function() {
                                    // load data success
                                }).fail(function(res) {
                                    // when load data error
                                });
                            }
                        }
                    }
                });

                self.pensionModel().fundInputApply.subscribe(function() {
                    //change select -> hide fund input table
                    if (self.pensionModel().fundInputApply() != 1) {
                        self.fundInputEnable(true);
                    } else {
                        self.fundInputEnable(false);
                    }
                });
            } //end constructor

            // Start
            public start(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                //first load
                self.loadHealthHistoryOfOffice().done(function() {
                    
                    // Resolve
                    dfd.resolve(null);
                });
                //first load
                self.loadPensionHistoryOfOffice().done(function() {
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
            public healthCheckCode(code: string) {
                var flag = false;
                this.healthInsuranceOfficeList().forEach(function(item, index) {
                    if (item.code == code) {
                        flag = true;
                    }
                });
                return flag;
            }

            //check code of parent or child
            public pensionCheckCode(code: string) {
                var flag = false;
                this.pensionInsuranceOfficeList().forEach(function(item, index) {
                    if (item.code == code) {
                        flag = true;
                    }
                });
                return flag;
            }

            //convert from OfficeItemDto to Tree list
            public covert2Tree(data: Array<OfficeItemDto>) {
                var returnData: Array<InsuranceOfficeItem> = [];
                data.forEach(function(item, index) {
                    var childData: Array<InsuranceOfficeItem> = [];
                    //convert child list
                    if (item.listHistory != null) {
                        item.listHistory.forEach(function(item2, index2) {
                            childData.push(new InsuranceOfficeItem(item.officeCode, item.officeName, item2.historyId, [], item2.startMonth.substring(0, 4) + "/" + item2.startMonth.substring(4, item2.startMonth.length) + "~" + item2.endMonth.substring(0, 4) + "/" + item2.endMonth.substring(4, item2.endMonth.length)));
                        });
                    }
                    else {
                        childData = [];
                    }
                    //push to array
                    returnData.push(new InsuranceOfficeItem(item.officeCode, item.officeName, item.officeCode, childData, item.officeCode + "\u00A0" + "\u00A0" + "\u00A0" + item.officeName));
                });
                return returnData;
            }

            //get all health history
            public loadHealthHistoryOfOffice(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.getAllHealthOfficeItem().done(function(data: Array<OfficeItemDto>) {
                    self.healthInsuranceOfficeList(self.covert2Tree(data));
                    if (self.healthInsuranceOfficeList().length > 0) {
                        if (self.healthInsuranceOfficeList()[0].childs.length > 0){
                        self.healthCurrentParentCode(self.healthInsuranceOfficeList()[0].code);
                            self.healthOfficeSelectedCode(self.healthInsuranceOfficeList()[0].childs[0].code);
                            }
                    }
                    else {
                        //Open register new office screen
                        self.OpenModalOfficeRegister();
                    }
                });
                // Return.
                return dfd.promise(null);
            }
        
            //get all pension history
            public loadPensionHistoryOfOffice(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.getAllPensionOfficeItem().done(function(data: Array<OfficeItemDto>) {
                    self.pensionInsuranceOfficeList(self.covert2Tree(data));
                    if (self.pensionInsuranceOfficeList().length > 0) {
                        if (self.pensionInsuranceOfficeList()[0].childs.length > 0)
                            self.pensionOfficeSelectedCode(self.pensionInsuranceOfficeList()[0].childs[0].code);
                        self.pensionCurrentParentCode(self.pensionInsuranceOfficeList()[0].code);
                    }
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
            public loadHealth(historyCode: string): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.getHealthInsuranceItemDetail(historyCode).done(function(data: HealthInsuranceRateDto) {
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
                    // Resolve
                    dfd.resolve();
                }).fail(function() {
                    //when load fail
                }).always(function(res) {
                });
                // Ret promise.
                return dfd.promise();
            }

            public loadPension(historyCode: string): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.getPensionItemDetail(historyCode).done(function(data: PensionRateDto) {
                    if (data == null) {
                        return;
                    }
                    //Set pension detail.
                    self.pensionModel().historyId = data.historyId;
                    self.pensionModel().companyCode = data.companyCode;
                    self.pensionModel().officeCode(data.officeCode);
                    self.pensionModel().startMonth(data.startMonth.substring(0, 4) + "/" + data.startMonth.substring(4, data.startMonth.length));
                    self.pensionModel().endMonth(data.endMonth.substring(0, 4) + "/" + data.endMonth.substring(4, data.endMonth.length));
                    self.pensionModel().autoCalculate(data.autoCalculate);
                    //TODO fundInputApply
                    //                    if (data.fundInputApply)
                    //                        self.pensionModel().fundInputApply(1);
                    //                    else
                    self.pensionModel().fundInputApply(0);

                    data.premiumRateItems.forEach(function(item, index) {
                        if (item.payType == PaymentType.SALARY && item.genderType == InsuranceGender.MALE) {
                            self.pensionModel().rateItems().pensionSalaryPersonalSon(item.personalRate);
                            self.pensionModel().rateItems().pensionSalaryCompanySon(item.companyRate);
                        }
                        if (item.payType == PaymentType.BONUS && item.genderType == InsuranceGender.MALE) {
                            self.pensionModel().rateItems().pensionBonusPersonalSon(item.personalRate);
                            self.pensionModel().rateItems().pensionBonusCompanySon(item.companyRate);
                        }
                        if (item.payType == PaymentType.SALARY && item.genderType == InsuranceGender.FEMALE) {
                            self.pensionModel().rateItems().pensionSalaryPersonalDaughter(item.personalRate);
                            self.pensionModel().rateItems().pensionSalaryCompanyDaughter(item.companyRate);
                        }
                        if (item.payType == PaymentType.BONUS && item.genderType == InsuranceGender.FEMALE) {
                            self.pensionModel().rateItems().pensionBonusPersonalDaughter(item.personalRate);
                            self.pensionModel().rateItems().pensionBonusCompanyDaughter(item.companyRate);
                        }
                        if (item.payType == PaymentType.SALARY && item.genderType == InsuranceGender.UNKNOW) {
                            self.pensionModel().rateItems().pensionSalaryPersonalUnknown(item.personalRate);
                            self.pensionModel().rateItems().pensionSalaryCompanyUnknown(item.companyRate);
                        }
                        if (item.payType == PaymentType.BONUS && item.genderType == InsuranceGender.UNKNOW) {
                            self.pensionModel().rateItems().pensionBonusPersonalUnknown(item.personalRate);
                            self.pensionModel().rateItems().pensionBonusCompanyUnknown(item.companyRate);
                        }
                    });

                    data.fundRateItems.forEach(function(item, index) {
                        if (item.payType == PaymentType.SALARY && item.genderType == InsuranceGender.MALE) {
                            self.pensionModel().fundRateItems().salaryPersonalSonBurden(item.burdenChargePersonalRate);
                            self.pensionModel().fundRateItems().salaryCompanySonBurden(item.burdenChargeCompanyRate);
                            self.pensionModel().fundRateItems().salaryPersonalSonExemption(item.exemptionChargePersonalRate);
                            self.pensionModel().fundRateItems().salaryCompanySonExemption(item.exemptionChargeCompanyRate);
                        }
                        if (item.payType == PaymentType.BONUS && item.genderType == InsuranceGender.MALE) {
                            self.pensionModel().fundRateItems().bonusPersonalSonBurden(item.burdenChargePersonalRate);
                            self.pensionModel().fundRateItems().bonusCompanySonBurden(item.burdenChargeCompanyRate);
                            self.pensionModel().fundRateItems().bonusPersonalSonExemption(item.exemptionChargePersonalRate);
                            self.pensionModel().fundRateItems().bonusCompanySonExemption(item.exemptionChargeCompanyRate);
                        }
                        if (item.payType == PaymentType.SALARY && item.genderType == InsuranceGender.FEMALE) {
                            self.pensionModel().fundRateItems().salaryPersonalDaughterBurden(item.burdenChargePersonalRate);
                            self.pensionModel().fundRateItems().salaryCompanyDaughterBurden(item.burdenChargeCompanyRate);
                            self.pensionModel().fundRateItems().salaryPersonalDaughterExemption(item.exemptionChargePersonalRate);
                            self.pensionModel().fundRateItems().salaryCompanyDaughterExemption(item.exemptionChargeCompanyRate);
                        }
                        if (item.payType == PaymentType.BONUS && item.genderType == InsuranceGender.FEMALE) {
                            self.pensionModel().fundRateItems().bonusPersonalDaughterBurden(item.burdenChargePersonalRate);
                            self.pensionModel().fundRateItems().bonusCompanyDaughterBurden(item.burdenChargeCompanyRate);
                            self.pensionModel().fundRateItems().bonusPersonalDaughterExemption(item.exemptionChargePersonalRate);
                            self.pensionModel().fundRateItems().bonusCompanyDaughterExemption(item.exemptionChargeCompanyRate);
                        }
                        if (item.payType == PaymentType.SALARY && item.genderType == InsuranceGender.UNKNOW) {
                            self.pensionModel().fundRateItems().salaryPersonalUnknownBurden(item.burdenChargePersonalRate);
                            self.pensionModel().fundRateItems().salaryCompanyUnknownBurden(item.burdenChargeCompanyRate);
                            self.pensionModel().fundRateItems().salaryPersonalUnknownExemption(item.exemptionChargePersonalRate);
                            self.pensionModel().fundRateItems().salaryCompanyUnknownExemption(item.exemptionChargeCompanyRate);
                        }
                        if (item.payType == PaymentType.BONUS && item.genderType == InsuranceGender.UNKNOW) {
                            self.pensionModel().fundRateItems().bonusPersonalUnknownBurden(item.burdenChargePersonalRate);
                            self.pensionModel().fundRateItems().bonusCompanyUnknownBurden(item.burdenChargeCompanyRate);
                            self.pensionModel().fundRateItems().bonusPersonalUnknownExemption(item.exemptionChargePersonalRate);
                            self.pensionModel().fundRateItems().bonusCompanyUnknownExemption(item.exemptionChargeCompanyRate);
                        }
                    });

                    //set rounding list
                    self.pensionModel().roundingMethods().pensionSalaryPersonalComboBox(self.roundingList());
                    self.pensionModel().roundingMethods().pensionSalaryCompanyComboBox(self.roundingList());
                    self.pensionModel().roundingMethods().pensionBonusPersonalComboBox(self.roundingList());
                    self.pensionModel().roundingMethods().pensionBonusCompanyComboBox(self.roundingList());

                    //Set selected rounding method
                    data.roundingMethods.forEach(function(item, index) {
                        if (item.payType == PaymentType.SALARY) {
                            self.pensionModel().roundingMethods().pensionSalaryPersonalComboBoxSelectedCode(self.convertRounding(item.roundAtrs.personalRoundAtr));
                            self.pensionModel().roundingMethods().pensionSalaryCompanyComboBoxSelectedCode(self.convertRounding(item.roundAtrs.companyRoundAtr));
                        }
                        else {
                            self.pensionModel().roundingMethods().pensionBonusPersonalComboBoxSelectedCode(self.convertRounding(item.roundAtrs.personalRoundAtr));
                            self.pensionModel().roundingMethods().pensionBonusCompanyComboBoxSelectedCode(self.convertRounding(item.roundAtrs.companyRoundAtr));
                        }
                    });

                    self.pensionModel().maxAmount(data.maxAmount);
                    self.pensionModel().childContributionRate(data.childContributionRate);
                    // Resolve
                    dfd.resolve();
                }).fail(function() {
                    //when load fail
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
                roundingMethods.push(new RoundingDto(PaymentType.SALARY, new RoundingItemDto(self.convertToRounding(self.healthModel().roundingMethods().healthSalaryPersonalComboBoxSelectedCode()), self.convertToRounding(self.healthModel().roundingMethods().healthSalaryCompanyComboBoxSelectedCode()))));
                roundingMethods.push(new RoundingDto(PaymentType.BONUS, new RoundingItemDto(self.convertToRounding(self.healthModel().roundingMethods().healthBonusPersonalComboBoxSelectedCode()), self.convertToRounding(self.healthModel().roundingMethods().healthBonusCompanyComboBoxSelectedCode()))));
                return new service.model.finder.HealthInsuranceRateDto(self.healthModel().historyId, self.healthModel().companyCode, self.healthCurrentParentCode(), self.healthModel().startMonth(), self.healthModel().endMonth(),self.healthModel().autoCalculate(), rateItems, roundingMethods, self.healthModel().maxAmount());
            }

            private pensionCollectData() {
                var self = this;
                var rates = self.pensionModel().rateItems();

                var rateItems: Array<PensionRateItemDto> = [];
                rateItems.push(new PensionRateItemDto(PaymentType.SALARY, InsuranceGender.MALE, rates.pensionSalaryPersonalSon(), rates.pensionSalaryCompanySon()));
                rateItems.push(new PensionRateItemDto(PaymentType.SALARY, InsuranceGender.FEMALE, rates.pensionSalaryPersonalDaughter(), rates.pensionSalaryCompanyDaughter()));
                rateItems.push(new PensionRateItemDto(PaymentType.SALARY, InsuranceGender.UNKNOW, rates.pensionSalaryPersonalUnknown(), rates.pensionSalaryCompanyUnknown()));
                rateItems.push(new PensionRateItemDto(PaymentType.BONUS, InsuranceGender.MALE, rates.pensionBonusPersonalSon(), rates.pensionBonusCompanySon()));
                rateItems.push(new PensionRateItemDto(PaymentType.BONUS, InsuranceGender.FEMALE, rates.pensionBonusPersonalDaughter(), rates.pensionBonusCompanyDaughter()));
                rateItems.push(new PensionRateItemDto(PaymentType.BONUS, InsuranceGender.UNKNOW, rates.pensionBonusPersonalUnknown(), rates.pensionBonusCompanyUnknown()));

                var fundRates = self.pensionModel().fundRateItems();
                var fundRateItems: Array<FundRateItemDto> = [];
                fundRateItems.push(new FundRateItemDto(PaymentType.SALARY, InsuranceGender.MALE, fundRates.salaryPersonalSonBurden(), fundRates.salaryCompanySonBurden(), fundRates.salaryPersonalSonExemption(), fundRates.salaryCompanySonExemption()));
                fundRateItems.push(new FundRateItemDto(PaymentType.SALARY, InsuranceGender.FEMALE, fundRates.salaryPersonalDaughterBurden(), fundRates.salaryCompanyDaughterBurden(), fundRates.salaryPersonalDaughterExemption(), fundRates.salaryCompanyDaughterExemption()));
                fundRateItems.push(new FundRateItemDto(PaymentType.SALARY, InsuranceGender.UNKNOW, fundRates.salaryPersonalUnknownBurden(), fundRates.salaryCompanyUnknownBurden(), fundRates.salaryPersonalUnknownExemption(), fundRates.salaryCompanyUnknownExemption()));
                fundRateItems.push(new FundRateItemDto(PaymentType.BONUS, InsuranceGender.MALE, fundRates.bonusPersonalSonBurden(), fundRates.bonusCompanySonBurden(), fundRates.bonusPersonalSonExemption(), fundRates.bonusCompanySonExemption()));
                fundRateItems.push(new FundRateItemDto(PaymentType.BONUS, InsuranceGender.FEMALE, fundRates.bonusPersonalDaughterBurden(), fundRates.bonusCompanyDaughterBurden(), fundRates.bonusPersonalDaughterExemption(), fundRates.bonusCompanyDaughterExemption()));
                fundRateItems.push(new FundRateItemDto(PaymentType.BONUS, InsuranceGender.UNKNOW, fundRates.bonusPersonalUnknownBurden(), fundRates.bonusCompanyUnknownBurden(), fundRates.bonusPersonalUnknownExemption(), fundRates.bonusCompanyUnknownExemption()));

                var roundingMethods: Array<RoundingDto> = [];
                var rounding = self.healthModel().roundingMethods();
                roundingMethods.push(new RoundingDto(PaymentType.SALARY, new RoundingItemDto(self.convertToRounding(self.pensionModel().roundingMethods().pensionSalaryPersonalComboBoxSelectedCode()), self.convertToRounding(self.pensionModel().roundingMethods().pensionSalaryCompanyComboBoxSelectedCode()))));
                roundingMethods.push(new RoundingDto(PaymentType.BONUS, new RoundingItemDto(self.convertToRounding(self.pensionModel().roundingMethods().pensionBonusPersonalComboBoxSelectedCode()), self.convertToRounding(self.pensionModel().roundingMethods().pensionBonusCompanyComboBoxSelectedCode()))));
                
                return new service.model.finder.PensionRateDto(self.pensionModel().historyId, self.pensionModel().companyCode, self.pensionCurrentParentCode(), self.pensionModel().startMonth(), self.pensionModel().endMonth(), self.pensionModel().autoCalculate(), true, rateItems, fundRateItems, roundingMethods, self.pensionModel().maxAmount(), self.pensionModel().childContributionRate());
            }

            //get current item office 
            public getDataOfHealthSelectedOffice(): InsuranceOfficeItem {
                var self = this;
                var saveVal = null;
                // Set parent value
                this.healthInsuranceOfficeList().forEach(function(item, index) {
                    if (self.healthCurrentParentCode() == item.code) {
                        saveVal = item;
                    }
                });
                return saveVal;
            }

            //get current item office 
            public getDataOfPensionSelectedOffice(): InsuranceOfficeItem {
                var self = this;
                var saveVal = null;
                // Set parent value
                this.pensionInsuranceOfficeList().forEach(function(item, index) {
                    if (self.pensionCurrentParentCode() == item.code) {
                        saveVal = item;
                    }
                });
                return saveVal;
            }

            public refreshOfficeList(returnValue: InsuranceOfficeItem) {
                var self = this;
                if ($('#healthInsuranceTabPanel').hasClass("active")) {
                    setTimeout(function() {
                        self.loadHealthHistoryOfOffice();
                        if(returnValue.childs.length>0)
                        {
                            self.healthOfficeSelectedCode(returnValue.childs[0].code);
                            self.healthModel().startMonth(returnValue.childs[0].codeName.substr(0, 7));
                            self.healthModel().endMonth(returnValue.childs[0].codeName.substr(9, returnValue.childs[0].codeName.length));
                        }
                    }, 1000);
                }
                else {
                    setTimeout(function() {
                        self.loadPensionHistoryOfOffice();
                        if (returnValue.childs.length > 0) {
                            self.pensionOfficeSelectedCode(returnValue.childs[0].code);
                            self.pensionModel().startMonth(returnValue.childs[0].codeName.substr(0, 7));
                            self.pensionModel().endMonth(returnValue.childs[0].codeName.substr(9, returnValue.childs[0].codeName.length));
                        }
                    }, 1000);
                }
            }

            public save() {
                var self = this;
                //update health
                if ($('#healthInsuranceTabPanel').hasClass("active")) {
                    service.updateHealthRate(self.healthCollectData()).done(function() {
                    });
                    //reload list office
                    self.loadHealthHistoryOfOffice();
                }
                else {
                    //update pension
                    service.updatePensionRate(self.pensionCollectData()).done(function() {
                    });
                    self.loadPensionHistoryOfOffice();
                }
            }

            // open dialog add history 
            public OpenModalAddHistory() {
                var self = this;
                var data;
                var isHealth = true;
                if ($('#healthInsuranceTabPanel').hasClass("active")) {
                    var sendOfficeItem = self.getDataOfHealthSelectedOffice();
                    data = self.healthCollectData();
                    //get history of first child office
                    self.healthInsuranceOfficeList().forEach(function(item, index) {
                        if (item.code == self.healthCurrentParentCode()) {
                            if (item.childs.length > 0) {
                                data.historyId = item.childs[0].code;
                                data.startMonth = item.childs[0].codeName.substr(0, 7);
                                data.endMonth = item.childs[0].codeName.substr(8, item.childs[0].codeName.length)
                            }
                        }
                    });
                    isHealth = true;
                } else {
                    var sendOfficeItem = self.getDataOfPensionSelectedOffice();
                    data = self.pensionCollectData();
                    //get history of first child office
                    self.pensionInsuranceOfficeList().forEach(function(item, index) {
                        if (item.code == self.pensionCurrentParentCode()) {
                            if (item.childs.length > 0) {
                                data.historyId = item.childs[0].code;
                                data.startMonth = item.childs[0].codeName.substr(0, 7);
                                data.endMonth = item.childs[0].codeName.substr(8, item.childs[0].codeName.length)
                            }
                        }
                    });
                    isHealth = false;
                }

                nts.uk.ui.windows.setShared("sendOfficeParentValue", sendOfficeItem);
                nts.uk.ui.windows.setShared("dataParentValue", data);
                nts.uk.ui.windows.setShared("isHealthParentValue", isHealth);
                nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                nts.uk.ui.windows.sub.modal("/view/qmm/008/b/index.xhtml", { title: "会社保険事業所の登録＞履歴の追加" }).onClosed(() => {
                    // Get child value return office Item
                    var returnValue = nts.uk.ui.windows.getShared("addHistoryChildValue");
                    if (returnValue != null) {
                        self.refreshOfficeList(returnValue);
                    }
                });
            }

            //open office register dialog
            public OpenModalOfficeRegister() {
                var self = this;
                // Set parent value
                nts.uk.ui.windows.setShared("officeCodeOfParentValue", self.healthOfficeSelectedCode());
                nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                nts.uk.ui.windows.sub.modal("/view/qmm/008/c/index.xhtml", { title: "会社保険事業所の登録＞事業所の登録" }).onClosed(() => {
                    //when close dialog -> reload office list
                    self.start();
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

            //open modal standard monthly price pension 
            public OpenModalStandardMonthlyPricePension() {
                // Set parent value
                nts.uk.ui.windows.setShared("dataOfSelectedOffice", this.getDataOfHealthSelectedOffice());
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
                var isHealth = true;
                if ($('#healthInsuranceTabPanel').hasClass("active")) {
                    var sendOfficeItem = self.getDataOfHealthSelectedOffice();
                    if(sendOfficeItem == null)
                    {
                            
                    }
                    isHealth = true;
                } else {
                    var sendOfficeItem = self.getDataOfPensionSelectedOffice();
                    isHealth = false;
                }
                // Set parent value
                nts.uk.ui.windows.setShared("sendOfficeParentValue", sendOfficeItem);
                nts.uk.ui.windows.setShared("isHealthParentValue", isHealth);
                nts.uk.ui.windows.setShared("currentChildCode", self.healthCurrentChildCode());
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
            constructor() {
                this.startMonth = ko.observable('');
                this.endMonth = ko.observable('');
                this.officeCode = ko.observable('');
                this.fundInputApply = ko.observable(0);
                this.autoCalculate = ko.observable(0);
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
