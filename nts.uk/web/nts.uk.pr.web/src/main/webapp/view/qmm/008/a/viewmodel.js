var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm008;
                (function (qmm008) {
                    var a;
                    (function (a) {
                        var viewmodel;
                        (function (viewmodel) {
                            var InsuranceOfficeItem = a.service.model.finder.InsuranceOfficeItemDto;
                            var RoundingDto = a.service.model.finder.RoundingDto;
                            var RoundingItemDto = a.service.model.finder.RoundingItemDto;
                            var HealthInsuranceRateItemDto = a.service.model.finder.HealthInsuranceRateItemDto;
                            var ChargeRateItemDto = a.service.model.finder.ChargeRateItemDto;
                            var PensionRateItemDto = a.service.model.finder.PensionRateItemDto;
                            var FundRateItemDto = a.service.model.finder.FundRateItemDto;
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.healthModel = ko.observable(new HealthInsuranceRateModel("code", 1, null, null, 15000));
                                    self.pensionModel = ko.observable(new PensionRateModel("code", 1, 1, null, null, null, 35000, 1.5));
                                    self.healthInsuranceOfficeList = ko.observableArray([]);
                                    self.pensionInsuranceOfficeList = ko.observableArray([]);
                                    self.healthOfficeSelectedCode = ko.observable('');
                                    self.healthCurrentParentCode = ko.observable('');
                                    self.healthCurrentChildCode = ko.observable('');
                                    self.pensionOfficeSelectedCode = ko.observable('');
                                    self.pensionCurrentParentCode = ko.observable('');
                                    self.pensionCurrentChildCode = ko.observable('');
                                    self.healthFilteredData = ko.observableArray(nts.uk.util.flatArray(self.healthInsuranceOfficeList(), "childs"));
                                    self.pensionFilteredData = ko.observableArray(nts.uk.util.flatArray(self.pensionInsuranceOfficeList(), "childs"));
                                    self.searchKey = ko.observable('');
                                    self.roundingList = ko.observableArray([]);
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
                                    self.healthAutoCalculateOptions = ko.observableArray([
                                        { code: '0', name: 'する' },
                                        { code: '1', name: 'しない' }
                                    ]);
                                    self.selectedRuleCode = ko.observable(1);
                                    self.pensionFundInputOptions = ko.observableArray([
                                        { code: '1', name: '有' },
                                        { code: '2', name: '無' }
                                    ]);
                                    self.pensionCalculateOptions = ko.observableArray([
                                        { code: '1', name: 'する' },
                                        { code: '2', name: 'しない' }
                                    ]);
                                    self.pensionCalculateSelectedCode = ko.observable(1);
                                    self.isTransistReturnData = ko.observable(false);
                                    self.healthDate = ko.observable('2016/04');
                                    self.pensionDate = ko.observable("2016/04");
                                    self.healthTotal = ko.observable(5400000);
                                    self.pensionCurrency = ko.observable(1500000);
                                    self.pensionOwnerRate = ko.observable(1.5);
                                    self.fundInputEnable = ko.observable(false);
                                    self.isClickHealthHistory = ko.observable(false);
                                    self.isClickPensionHistory = ko.observable(false);
                                    self.startMonthTemp = ko.observable('');
                                    self.endMonthTemp = ko.observable('');
                                    self.healthOfficeSelectedCode.subscribe(function (officeSelectedCode) {
                                        if (officeSelectedCode != null || officeSelectedCode != undefined) {
                                            if (self.healthCheckCode(officeSelectedCode)) {
                                                self.healthCurrentParentCode(officeSelectedCode);
                                                self.healthModel(new HealthInsuranceRateModel("code", 1, null, null, 15000));
                                                self.isClickHealthHistory(false);
                                            }
                                            else {
                                                self.healthCurrentChildCode(officeSelectedCode);
                                                self.isClickHealthHistory(true);
                                                if (officeSelectedCode.length > 10) {
                                                    $.when(self.loadHealth(officeSelectedCode)).done(function () {
                                                    }).fail(function (res) {
                                                    });
                                                }
                                            }
                                        }
                                    });
                                    self.pensionOfficeSelectedCode.subscribe(function (officeSelectedCode) {
                                        if (officeSelectedCode != null || officeSelectedCode != undefined) {
                                            if (self.pensionCheckCode(officeSelectedCode)) {
                                                self.pensionCurrentParentCode(officeSelectedCode);
                                                self.isClickPensionHistory(false);
                                            }
                                            else {
                                                self.pensionCurrentChildCode(officeSelectedCode);
                                                self.isClickPensionHistory(true);
                                                if (officeSelectedCode.length > 10) {
                                                    $.when(self.loadPension(officeSelectedCode)).done(function () {
                                                    }).fail(function (res) {
                                                    });
                                                }
                                            }
                                        }
                                    });
                                    self.pensionModel().fundInputApply.subscribe(function (pensionFundInputOptions) {
                                        if (self.pensionModel().fundInputApply() != 1) {
                                            self.fundInputEnable(true);
                                        }
                                        else {
                                            self.fundInputEnable(false);
                                        }
                                    });
                                }
                                ScreenModel.prototype.start = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.loadHealthHistoryOfOffice().done(function (data) {
                                        if ((self.healthInsuranceOfficeList() != null) && (self.healthInsuranceOfficeList().length > 0)) {
                                            self.healthOfficeSelectedCode(self.healthInsuranceOfficeList()[0].code);
                                        }
                                        else {
                                            self.OpenModalOfficeRegister();
                                        }
                                        dfd.resolve(null);
                                    });
                                    self.loadPensionHistoryOfOffice().done(function (data) {
                                        if ((self.pensionInsuranceOfficeList() != null) && (self.pensionInsuranceOfficeList().length > 0)) {
                                            self.pensionOfficeSelectedCode(self.pensionInsuranceOfficeList()[0].code);
                                        }
                                        else {
                                            self.OpenModalOfficeRegister();
                                        }
                                        dfd.resolve(null);
                                    });
                                    self.getAllRounding().done(function (data) {
                                        dfd.resolve(null);
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.getAllRounding = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    a.service.findAllRounding().done(function (data) {
                                        self.roundingList(data);
                                        dfd.resolve(data);
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.healthCheckCode = function (code) {
                                    var flag = false;
                                    this.healthInsuranceOfficeList().forEach(function (item, index) {
                                        if (item.code == code) {
                                            flag = true;
                                        }
                                    });
                                    return flag;
                                };
                                ScreenModel.prototype.pensionCheckCode = function (code) {
                                    var flag = false;
                                    this.pensionInsuranceOfficeList().forEach(function (item, index) {
                                        if (item.code == code) {
                                            flag = true;
                                        }
                                    });
                                    return flag;
                                };
                                ScreenModel.prototype.covert2Tree = function (data) {
                                    var returnData = [];
                                    data.forEach(function (item, index) {
                                        var childData = [];
                                        if (item.listHistory != null) {
                                            item.listHistory.forEach(function (item2, index2) {
                                                childData.push(new InsuranceOfficeItem(item.officeCode, item.officeName, item2.historyId, [], item2.startMonth.substring(0, 4) + "/" + item2.startMonth.substring(4, item2.startMonth.length) + "~" + item2.endMonth.substring(0, 4) + "/" + item2.endMonth.substring(4, item2.endMonth.length)));
                                            });
                                        }
                                        else {
                                            childData = [];
                                        }
                                        returnData.push(new InsuranceOfficeItem(item.officeCode, item.officeName, item.officeCode, childData, item.officeCode + "\u00A0" + "\u00A0" + "\u00A0" + item.officeName));
                                    });
                                    return returnData;
                                };
                                ScreenModel.prototype.loadHealthHistoryOfOffice = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    a.service.getAllHealthOfficeItem().done(function (data) {
                                        self.healthInsuranceOfficeList(self.covert2Tree(data));
                                        if (self.healthInsuranceOfficeList()[0].childs.length > 0)
                                            self.healthOfficeSelectedCode(self.healthInsuranceOfficeList()[0].childs[0].code);
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.loadPensionHistoryOfOffice = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    a.service.getAllPensionOfficeItem().done(function (data) {
                                        self.pensionInsuranceOfficeList(self.covert2Tree(data));
                                        if (self.pensionInsuranceOfficeList()[0].childs.length > 0)
                                            self.pensionOfficeSelectedCode(self.pensionInsuranceOfficeList()[0].childs[0].code);
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.convertRounding = function (stringRounding) {
                                    switch (stringRounding) {
                                        case Rounding.ROUNDUP: return "0";
                                        case Rounding.TRUNCATION: return "1";
                                        case Rounding.ROUNDDOWN: return "2";
                                        case Rounding.DOWN5_UP6: return "3";
                                        case Rounding.DOWN4_UP5: return "4";
                                        default: return "0";
                                    }
                                };
                                ScreenModel.prototype.loadHealth = function (historyCode) {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    a.service.getHealthInsuranceItemDetail(historyCode).done(function (data) {
                                        if (data == null) {
                                            return;
                                        }
                                        self.healthModel().historyId = data.historyId;
                                        self.healthModel().startMonth(data.startMonth.substring(0, 4) + "/" + data.startMonth.substring(4, data.startMonth.length));
                                        self.healthModel().endMonth(data.endMonth.substring(0, 4) + "/" + data.endMonth.substring(4, data.endMonth.length));
                                        self.healthModel().companyCode = data.companyCode;
                                        self.healthModel().officeCode(data.officeCode);
                                        self.healthModel().autoCalculate(data.autoCalculate);
                                        data.rateItems.forEach(function (item, index) {
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
                                        self.healthModel().roundingMethods().healthSalaryPersonalComboBox(self.roundingList());
                                        self.healthModel().roundingMethods().healthSalaryCompanyComboBox(self.roundingList());
                                        self.healthModel().roundingMethods().healthBonusPersonalComboBox(self.roundingList());
                                        self.healthModel().roundingMethods().healthBonusCompanyComboBox(self.roundingList());
                                        data.roundingMethods.forEach(function (item, index) {
                                            if (item.payType == PaymentType.SALARY) {
                                                self.healthModel().roundingMethods().healthSalaryPersonalComboBoxSelectedCode(self.convertRounding(item.roundAtrs.personalRoundAtr));
                                                self.healthModel().roundingMethods().healthSalaryCompanyComboBoxSelectedCode(self.convertRounding(item.roundAtrs.companyRoundAtr));
                                            }
                                            else {
                                                self.healthModel().roundingMethods().healthSalaryPersonalComboBoxSelectedCode(self.convertRounding(item.roundAtrs.personalRoundAtr));
                                                self.healthModel().roundingMethods().healthBonusCompanyComboBoxSelectedCode(self.convertRounding(item.roundAtrs.companyRoundAtr));
                                            }
                                        });
                                        self.healthModel().maxAmount(data.maxAmount);
                                        dfd.resolve();
                                    }).fail(function () {
                                    }).always(function (res) {
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.loadPension = function (historyCode) {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    a.service.getPensionItemDetail(historyCode).done(function (data) {
                                        if (data == null) {
                                            return;
                                        }
                                        self.pensionModel().historyId = data.historyId;
                                        self.pensionModel().companyCode = data.companyCode;
                                        self.pensionModel().officeCode(data.officeCode);
                                        self.pensionModel().startMonth(data.startMonth.substring(0, 4) + "/" + data.startMonth.substring(4, data.startMonth.length));
                                        self.pensionModel().endMonth(data.endMonth.substring(0, 4) + "/" + data.endMonth.substring(4, data.endMonth.length));
                                        self.pensionModel().autoCalculate(data.autoCalculate);
                                        data.premiumRateItems.forEach(function (item, index) {
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
                                        data.fundRateItems.forEach(function (item, index) {
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
                                        self.pensionModel().roundingMethods().pensionSalaryPersonalComboBox(self.roundingList());
                                        self.pensionModel().roundingMethods().pensionSalaryCompanyComboBox(self.roundingList());
                                        self.pensionModel().roundingMethods().pensionBonusPersonalComboBox(self.roundingList());
                                        self.pensionModel().roundingMethods().pensionBonusCompanyComboBox(self.roundingList());
                                        data.roundingMethods.forEach(function (item, index) {
                                            if (item.payType == PaymentType.SALARY) {
                                                self.pensionModel().roundingMethods().pensionSalaryPersonalComboBoxSelectedCode(self.convertRounding(item.roundAtrs.personalRoundAtr));
                                                self.pensionModel().roundingMethods().pensionSalaryCompanyComboBoxSelectedCode(self.convertRounding(item.roundAtrs.companyRoundAtr));
                                            }
                                            else {
                                                self.pensionModel().roundingMethods().pensionSalaryPersonalComboBoxSelectedCode(self.convertRounding(item.roundAtrs.personalRoundAtr));
                                                self.pensionModel().roundingMethods().pensionBonusCompanyComboBoxSelectedCode(self.convertRounding(item.roundAtrs.companyRoundAtr));
                                            }
                                        });
                                        self.pensionModel().maxAmount(data.maxAmount);
                                        self.pensionModel().childContributionRate(data.childContributionRate);
                                        dfd.resolve();
                                    }).fail(function () {
                                    }).always(function (res) {
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.healthCollectData = function () {
                                    var self = this;
                                    var rates = self.healthModel().rateItems();
                                    var rateItems = [];
                                    rateItems.push(new HealthInsuranceRateItemDto(PaymentType.SALARY, HealthInsuranceType.GENERAL, new ChargeRateItemDto(rates.healthSalaryCompanyGeneral(), rates.healthSalaryPersonalGeneral())));
                                    rateItems.push(new HealthInsuranceRateItemDto(PaymentType.SALARY, HealthInsuranceType.NURSING, new ChargeRateItemDto(rates.healthSalaryCompanyNursing(), rates.healthSalaryPersonalNursing())));
                                    rateItems.push(new HealthInsuranceRateItemDto(PaymentType.SALARY, HealthInsuranceType.BASIC, new ChargeRateItemDto(rates.healthSalaryCompanyBasic(), rates.healthSalaryPersonalBasic())));
                                    rateItems.push(new HealthInsuranceRateItemDto(PaymentType.SALARY, HealthInsuranceType.SPECIAL, new ChargeRateItemDto(rates.healthSalaryCompanySpecific(), rates.healthSalaryPersonalSpecific())));
                                    rateItems.push(new HealthInsuranceRateItemDto(PaymentType.BONUS, HealthInsuranceType.GENERAL, new ChargeRateItemDto(rates.healthBonusCompanyGeneral(), rates.healthBonusPersonalGeneral())));
                                    rateItems.push(new HealthInsuranceRateItemDto(PaymentType.BONUS, HealthInsuranceType.NURSING, new ChargeRateItemDto(rates.healthBonusCompanyNursing(), rates.healthBonusPersonalNursing())));
                                    rateItems.push(new HealthInsuranceRateItemDto(PaymentType.BONUS, HealthInsuranceType.BASIC, new ChargeRateItemDto(rates.healthBonusCompanyBasic(), rates.healthBonusPersonalBasic())));
                                    rateItems.push(new HealthInsuranceRateItemDto(PaymentType.BONUS, HealthInsuranceType.SPECIAL, new ChargeRateItemDto(rates.healthBonusCompanySpecific(), rates.healthBonusPersonalSpecific())));
                                    var roundingMethods = [];
                                    var rounding = self.healthModel().roundingMethods();
                                    roundingMethods.push(new RoundingDto(PaymentType.SALARY, new RoundingItemDto(Rounding.ROUNDUP, Rounding.ROUNDUP)));
                                    roundingMethods.push(new RoundingDto(PaymentType.BONUS, new RoundingItemDto(Rounding.ROUNDUP, Rounding.ROUNDUP)));
                                    return new a.service.model.finder.HealthInsuranceRateDto("", "", self.healthCurrentParentCode(), self.healthModel().startMonth(), self.healthModel().endMonth(), 1, rateItems, roundingMethods, self.healthModel().maxAmount());
                                };
                                ScreenModel.prototype.pensionCollectData = function () {
                                    var self = this;
                                    var rates = self.pensionModel().rateItems();
                                    var rateItems = [];
                                    rateItems.push(new PensionRateItemDto(PaymentType.SALARY, InsuranceGender.MALE, rates.pensionSalaryCompanySon(), rates.pensionSalaryPersonalSon()));
                                    rateItems.push(new PensionRateItemDto(PaymentType.SALARY, InsuranceGender.FEMALE, rates.pensionSalaryCompanyDaughter(), rates.pensionSalaryPersonalDaughter()));
                                    rateItems.push(new PensionRateItemDto(PaymentType.SALARY, InsuranceGender.UNKNOW, rates.pensionSalaryCompanyUnknown(), rates.pensionSalaryPersonalUnknown()));
                                    rateItems.push(new PensionRateItemDto(PaymentType.BONUS, InsuranceGender.MALE, rates.pensionBonusCompanySon(), rates.pensionBonusPersonalSon()));
                                    rateItems.push(new PensionRateItemDto(PaymentType.BONUS, InsuranceGender.FEMALE, rates.pensionBonusCompanyDaughter(), rates.pensionBonusPersonalDaughter()));
                                    rateItems.push(new PensionRateItemDto(PaymentType.BONUS, InsuranceGender.UNKNOW, rates.pensionBonusCompanyUnknown(), rates.pensionBonusPersonalUnknown()));
                                    var fundRates = self.pensionModel().fundRateItems();
                                    var fundRateItems = [];
                                    fundRateItems.push(new FundRateItemDto(PaymentType.SALARY, InsuranceGender.MALE, fundRates.salaryPersonalSonBurden(), fundRates.salaryCompanySonBurden(), fundRates.salaryPersonalSonExemption(), fundRates.salaryCompanySonExemption()));
                                    fundRateItems.push(new FundRateItemDto(PaymentType.SALARY, InsuranceGender.FEMALE, fundRates.salaryPersonalDaughterBurden(), fundRates.salaryCompanyDaughterBurden(), fundRates.salaryPersonalDaughterExemption(), fundRates.salaryCompanyDaughterExemption()));
                                    fundRateItems.push(new FundRateItemDto(PaymentType.SALARY, InsuranceGender.UNKNOW, fundRates.salaryPersonalUnknownBurden(), fundRates.salaryCompanyUnknownBurden(), fundRates.salaryPersonalUnknownExemption(), fundRates.salaryCompanyUnknownExemption()));
                                    fundRateItems.push(new FundRateItemDto(PaymentType.BONUS, InsuranceGender.MALE, fundRates.bonusPersonalSonBurden(), fundRates.bonusCompanySonBurden(), fundRates.bonusPersonalSonExemption(), fundRates.bonusCompanySonExemption()));
                                    fundRateItems.push(new FundRateItemDto(PaymentType.BONUS, InsuranceGender.FEMALE, fundRates.bonusPersonalDaughterBurden(), fundRates.bonusCompanyDaughterBurden(), fundRates.bonusPersonalDaughterExemption(), fundRates.bonusCompanyDaughterExemption()));
                                    fundRateItems.push(new FundRateItemDto(PaymentType.BONUS, InsuranceGender.UNKNOW, fundRates.bonusPersonalUnknownBurden(), fundRates.bonusCompanyUnknownBurden(), fundRates.bonusPersonalUnknownExemption(), fundRates.bonusCompanyUnknownExemption()));
                                    var roundingMethods = [];
                                    var rounding = self.healthModel().roundingMethods();
                                    roundingMethods.push(new RoundingDto(PaymentType.SALARY, new RoundingItemDto(Rounding.ROUNDUP, Rounding.ROUNDUP)));
                                    roundingMethods.push(new RoundingDto(PaymentType.BONUS, new RoundingItemDto(Rounding.ROUNDUP, Rounding.ROUNDUP)));
                                    return new a.service.model.finder.PensionRateDto("", "", self.pensionCurrentParentCode(), self.pensionModel().startMonth(), self.pensionModel().endMonth(), 1, true, rateItems, fundRateItems, roundingMethods, self.pensionModel().maxAmount(), self.pensionModel().childContributionRate());
                                };
                                ScreenModel.prototype.getDataOfHealthSelectedOffice = function () {
                                    var self = this;
                                    var saveVal = null;
                                    this.healthInsuranceOfficeList().forEach(function (item, index) {
                                        if (self.healthCurrentParentCode() == item.code) {
                                            saveVal = item;
                                        }
                                    });
                                    return saveVal;
                                };
                                ScreenModel.prototype.getDataOfPensionSelectedOffice = function () {
                                    var self = this;
                                    var saveVal = null;
                                    this.pensionInsuranceOfficeList().forEach(function (item, index) {
                                        if (self.pensionCurrentParentCode() == item.code) {
                                            saveVal = item;
                                        }
                                    });
                                    return saveVal;
                                };
                                ScreenModel.prototype.refreshOfficeList = function (returnValue) {
                                    var self = this;
                                    if ($('#healthInsuranceTabPanel').hasClass("active")) {
                                        var currentHealthInsuranceOfficeList = self.healthInsuranceOfficeList();
                                        if (returnValue != undefined && returnValue != null) {
                                            currentHealthInsuranceOfficeList.forEach(function (item, index) {
                                                if (item.code == returnValue.code) {
                                                    currentHealthInsuranceOfficeList[index] = returnValue;
                                                }
                                            });
                                        }
                                        self.healthInsuranceOfficeList([]);
                                        self.healthInsuranceOfficeList(currentHealthInsuranceOfficeList);
                                        self.healthOfficeSelectedCode(returnValue.childs[0].code);
                                        self.healthModel().startMonth(returnValue.childs[0].codeName.substr(0, 7));
                                        self.healthModel().endMonth(returnValue.childs[0].codeName.substr(9, returnValue.childs[0].codeName.length));
                                    }
                                    else {
                                        var currentPensionInsuranceOfficeList = self.pensionInsuranceOfficeList();
                                        if (returnValue != undefined && returnValue != null) {
                                            currentPensionInsuranceOfficeList.forEach(function (item, index) {
                                                if (item.code == returnValue.code) {
                                                    currentPensionInsuranceOfficeList[index] = returnValue;
                                                }
                                            });
                                        }
                                        self.pensionInsuranceOfficeList([]);
                                        self.pensionInsuranceOfficeList(currentPensionInsuranceOfficeList);
                                        self.pensionOfficeSelectedCode(returnValue.childs[0].code);
                                        self.pensionModel().startMonth(returnValue.childs[0].codeName.substr(0, 7));
                                        self.pensionModel().endMonth(returnValue.childs[0].codeName.substr(9, returnValue.childs[0].codeName.length));
                                    }
                                };
                                ScreenModel.prototype.save = function () {
                                    var self = this;
                                    if ($('#healthInsuranceTabPanel').hasClass("active")) {
                                        a.service.registerHealthRate(self.healthCollectData()).done(function () {
                                        });
                                    }
                                    else {
                                        a.service.registerPensionRate(self.pensionCollectData()).done(function () {
                                        });
                                    }
                                };
                                ScreenModel.prototype.OpenModalAddHistory = function () {
                                    var self = this;
                                    if ($('#healthInsuranceTabPanel').hasClass("active")) {
                                        var sendOfficeItem = self.getDataOfHealthSelectedOffice();
                                    }
                                    else {
                                        var sendOfficeItem = self.getDataOfPensionSelectedOffice();
                                    }
                                    nts.uk.ui.windows.setShared("sendOfficeParentValue", sendOfficeItem);
                                    nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                                    nts.uk.ui.windows.sub.modal("/view/qmm/008/b/index.xhtml", { title: "会社保険事業所の登録＞履歴の追加" }).onClosed(function () {
                                        var returnValue = nts.uk.ui.windows.getShared("addHistoryChildValue");
                                        if (returnValue != null) {
                                            self.refreshOfficeList(returnValue);
                                        }
                                    });
                                };
                                ScreenModel.prototype.OpenModalOfficeRegister = function () {
                                    var self = this;
                                    nts.uk.ui.windows.setShared("officeCodeOfParentValue", self.healthOfficeSelectedCode());
                                    nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                                    nts.uk.ui.windows.sub.modal("/view/qmm/008/c/index.xhtml", { title: "会社保険事業所の登録＞事業所の登録" }).onClosed(function () {
                                        self.start();
                                        var returnValue = nts.uk.ui.windows.getShared("listOfficeOfChildValue");
                                    });
                                };
                                ScreenModel.prototype.OpenModalStandardMonthlyPriceHealth = function () {
                                    nts.uk.ui.windows.setShared("dataOfSelectedOffice", this.getDataOfHealthSelectedOffice());
                                    nts.uk.ui.windows.setShared("healthModel", this.healthModel());
                                    nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                                    nts.uk.ui.windows.sub.modal("/view/qmm/008/h/index.xhtml", { title: "会社保険事業所の登録＞標準報酬月額保険料額表" }).onClosed(function () {
                                        var returnValue = nts.uk.ui.windows.getShared("listOfficeOfChildValue");
                                    });
                                };
                                ScreenModel.prototype.OpenModalStandardMonthlyPricePension = function () {
                                    nts.uk.ui.windows.setShared("dataOfSelectedOffice", this.getDataOfHealthSelectedOffice());
                                    nts.uk.ui.windows.setShared("pensionModel", this.pensionModel());
                                    nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                                    nts.uk.ui.windows.sub.modal("/view/qmm/008/i/index.xhtml", { title: "会社保険事業所の登録＞標準報酬月額保険料額表" }).onClosed(function () {
                                        var returnValue = nts.uk.ui.windows.getShared("listOfficeOfChildValue");
                                    });
                                };
                                ScreenModel.prototype.OpenModalConfigHistory = function () {
                                    var self = this;
                                    var sendOfficeParentValue = this.getDataOfHealthSelectedOffice();
                                    nts.uk.ui.windows.setShared("sendOfficeParentValue", sendOfficeParentValue);
                                    nts.uk.ui.windows.setShared("currentChildCode", self.healthCurrentChildCode());
                                    nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                                    nts.uk.ui.windows.sub.modal("/view/qmm/008/f/index.xhtml", { title: "会社保険事業所の登録＞履歴の編集" }).onClosed(function () {
                                        var returnValue = nts.uk.ui.windows.getShared("updateHistoryChildValue");
                                        self.refreshOfficeList(returnValue);
                                    });
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var HealthInsuranceRateModel = (function () {
                                function HealthInsuranceRateModel(officeCode, autoCalculate, rateItems, roundingMethods, maxAmount) {
                                    this.startMonth = ko.observable("2016/04");
                                    this.endMonth = ko.observable("2016/04");
                                    this.officeCode = ko.observable('');
                                    this.autoCalculate = ko.observable(autoCalculate);
                                    this.rateItems = ko.observable(new HealthInsuranceRateItemModel());
                                    this.roundingMethods = ko.observable(new HealthInsuranceRoundingModel());
                                    this.maxAmount = ko.observable(0);
                                }
                                return HealthInsuranceRateModel;
                            }());
                            viewmodel.HealthInsuranceRateModel = HealthInsuranceRateModel;
                            var PensionRateModel = (function () {
                                function PensionRateModel(officeCode, fundInputApply, autoCalculate, rateItems, fundRateItems, roundingMethods, maxAmount, childContributionRate) {
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
                                return PensionRateModel;
                            }());
                            viewmodel.PensionRateModel = PensionRateModel;
                            var HealthInsuranceRateItemModel = (function () {
                                function HealthInsuranceRateItemModel() {
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
                                return HealthInsuranceRateItemModel;
                            }());
                            viewmodel.HealthInsuranceRateItemModel = HealthInsuranceRateItemModel;
                            var PensionRateItemModel = (function () {
                                function PensionRateItemModel() {
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
                                return PensionRateItemModel;
                            }());
                            viewmodel.PensionRateItemModel = PensionRateItemModel;
                            var FunRateItemModel = (function () {
                                function FunRateItemModel() {
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
                                return FunRateItemModel;
                            }());
                            viewmodel.FunRateItemModel = FunRateItemModel;
                            var HealthInsuranceRoundingModel = (function () {
                                function HealthInsuranceRoundingModel() {
                                    this.healthSalaryPersonalComboBox = ko.observableArray(null);
                                    this.healthSalaryPersonalComboBoxItemName = ko.observable('');
                                    this.healthSalaryPersonalComboBoxCurrentCode = ko.observable(1);
                                    this.healthSalaryPersonalComboBoxSelectedCode = ko.observable('');
                                    this.healthSalaryCompanyComboBox = ko.observableArray(null);
                                    this.healthSalaryCompanyComboBoxItemName = ko.observable('');
                                    this.healthSalaryCompanyComboBoxCurrentCode = ko.observable(3);
                                    this.healthSalaryCompanyComboBoxSelectedCode = ko.observable('002');
                                    this.healthBonusPersonalComboBox = ko.observableArray(null);
                                    this.healthBonusPersonalComboBoxItemName = ko.observable('');
                                    this.healthBonusPersonalComboBoxCurrentCode = ko.observable(3);
                                    this.healthBonusPersonalComboBoxSelectedCode = ko.observable('002');
                                    this.healthBonusCompanyComboBox = ko.observableArray(null);
                                    this.healthBonusCompanyComboBoxItemName = ko.observable('');
                                    this.healthBonusCompanyComboBoxCurrentCode = ko.observable(3);
                                    this.healthBonusCompanyComboBoxSelectedCode = ko.observable('002');
                                }
                                return HealthInsuranceRoundingModel;
                            }());
                            viewmodel.HealthInsuranceRoundingModel = HealthInsuranceRoundingModel;
                            var PensionRateRoundingModel = (function () {
                                function PensionRateRoundingModel() {
                                    this.pensionSalaryPersonalComboBox = ko.observableArray(null);
                                    this.pensionSalaryPersonalComboBoxItemName = ko.observable('');
                                    this.pensionSalaryPersonalComboBoxCurrentCode = ko.observable(1);
                                    this.pensionSalaryPersonalComboBoxSelectedCode = ko.observable('');
                                    this.pensionSalaryCompanyComboBox = ko.observableArray(null);
                                    this.pensionSalaryCompanyComboBoxItemName = ko.observable('');
                                    this.pensionSalaryCompanyComboBoxCurrentCode = ko.observable(3);
                                    this.pensionSalaryCompanyComboBoxSelectedCode = ko.observable('002');
                                    this.pensionBonusPersonalComboBox = ko.observableArray(null);
                                    this.pensionBonusPersonalComboBoxItemName = ko.observable('');
                                    this.pensionBonusPersonalComboBoxCurrentCode = ko.observable(3);
                                    this.pensionBonusPersonalComboBoxSelectedCode = ko.observable('002');
                                    this.pensionBonusCompanyComboBox = ko.observableArray(null);
                                    this.pensionBonusCompanyComboBoxItemName = ko.observable('');
                                    this.pensionBonusCompanyComboBoxCurrentCode = ko.observable(3);
                                    this.pensionBonusCompanyComboBoxSelectedCode = ko.observable('002');
                                }
                                return PensionRateRoundingModel;
                            }());
                            viewmodel.PensionRateRoundingModel = PensionRateRoundingModel;
                        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
                        var HealthInsuranceAvgearn = (function () {
                            function HealthInsuranceAvgearn() {
                            }
                            return HealthInsuranceAvgearn;
                        }());
                        a.HealthInsuranceAvgearn = HealthInsuranceAvgearn;
                        var ChargeRateItem = (function () {
                            function ChargeRateItem() {
                            }
                            return ChargeRateItem;
                        }());
                        a.ChargeRateItem = ChargeRateItem;
                        var PaymentType = (function () {
                            function PaymentType() {
                            }
                            PaymentType.SALARY = 'Salary';
                            PaymentType.BONUS = 'Bonus';
                            return PaymentType;
                        }());
                        a.PaymentType = PaymentType;
                        var HealthInsuranceType = (function () {
                            function HealthInsuranceType() {
                            }
                            HealthInsuranceType.GENERAL = 'General';
                            HealthInsuranceType.NURSING = 'Nursing';
                            HealthInsuranceType.BASIC = 'Basic';
                            HealthInsuranceType.SPECIAL = 'Special';
                            return HealthInsuranceType;
                        }());
                        a.HealthInsuranceType = HealthInsuranceType;
                        var Rounding = (function () {
                            function Rounding() {
                            }
                            Rounding.ROUNDUP = 'RoundUp';
                            Rounding.TRUNCATION = 'Truncation';
                            Rounding.ROUNDDOWN = 'RoundDown';
                            Rounding.DOWN5_UP6 = 'Down5_Up6';
                            Rounding.DOWN4_UP5 = 'Down4_Up5';
                            return Rounding;
                        }());
                        a.Rounding = Rounding;
                        var InsuranceGender = (function () {
                            function InsuranceGender() {
                            }
                            InsuranceGender.MALE = "Male";
                            InsuranceGender.FEMALE = "Female";
                            InsuranceGender.UNKNOW = "Unknow";
                            return InsuranceGender;
                        }());
                        a.InsuranceGender = InsuranceGender;
                        var AutoCalculate = (function () {
                            function AutoCalculate() {
                            }
                            AutoCalculate.AUTO = "Auto";
                            AutoCalculate.MANUAL = "Manual";
                            return AutoCalculate;
                        }());
                        a.AutoCalculate = AutoCalculate;
                    })(a = qmm008.a || (qmm008.a = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
