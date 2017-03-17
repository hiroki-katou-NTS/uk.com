var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
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
                    var c;
                    (function (c) {
                        var viewmodel;
                        (function (viewmodel) {
                            var RoundingDto = c.service.model.finder.RoundingDto;
                            var RoundingItemDto = c.service.model.finder.RoundingItemDto;
                            var PensionRateItemDto = c.service.model.finder.PensionRateItemDto;
                            var FundRateItemDto = c.service.model.finder.FundRateItemDto;
                            var ScreenBaseModel = view.base.simplehistory.viewmodel.ScreenBaseModel;
                            var ScreenModel = (function (_super) {
                                __extends(ScreenModel, _super);
                                function ScreenModel() {
                                    _super.call(this, {
                                        functionName: '社会保険事業所',
                                        service: c.service.instance,
                                        removeMasterOnLastHistoryRemove: false
                                    });
                                    var self = this;
                                    self.pensionModel = ko.observable(new PensionRateModel());
                                    self.pensionInsuranceOfficeList = ko.observableArray([]);
                                    self.pensionFilteredData = ko.observableArray(nts.uk.util.flatArray(self.pensionInsuranceOfficeList(), "childs"));
                                    self.roundingList = ko.observableArray([]);
                                    self.Rate2 = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                                        grouplength: 3,
                                        decimallength: 2
                                    }));
                                    self.pensionFundInputOptions = ko.observableArray([
                                        { code: '1', name: '有' },
                                        { code: '0', name: '無' }
                                    ]);
                                    self.pensionCalculateOptions = ko.observableArray([
                                        { code: '0', name: 'する' },
                                        { code: '1', name: 'しない' }
                                    ]);
                                    self.isTransistReturnData = ko.observable(false);
                                    self.fundInputEnable = ko.observable(false);
                                    self.isLoading = ko.observable(true);
                                    self.currentOfficeCode = ko.observable('');
                                    self.japanYear = ko.observable('');
                                    self.sendOfficeData = ko.observable('');
                                    self.pensionModel().fundInputApply.subscribe(function () {
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
                                    self.getAllRounding().done(function () {
                                        dfd.resolve(null);
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.getAllRounding = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    c.service.findAllRounding().done(function (data) {
                                        self.roundingList(data);
                                        dfd.resolve(data);
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.convertRounding = function (stringRounding) {
                                    switch (stringRounding) {
                                        case Rounding.ROUNDUP: return "0";
                                        case Rounding.TRUNCATION: return "1";
                                        case Rounding.DOWN4_UP5: return "2";
                                        case Rounding.ROUNDDOWN: return "3";
                                        case Rounding.DOWN5_UP6: return "4";
                                        default: return "0";
                                    }
                                };
                                ScreenModel.prototype.convertToRounding = function (stringValue) {
                                    switch (stringValue) {
                                        case "0": return Rounding.ROUNDUP;
                                        case "1": return Rounding.TRUNCATION;
                                        case "2": return Rounding.DOWN4_UP5;
                                        case "3": return Rounding.ROUNDDOWN;
                                        case "4": return Rounding.DOWN5_UP6;
                                        default: return Rounding.ROUNDUP;
                                    }
                                };
                                ScreenModel.prototype.loadPension = function (data) {
                                    var self = this;
                                    if (data == null) {
                                        return;
                                    }
                                    self.pensionModel().historyId = data.historyId;
                                    self.pensionModel().companyCode = data.companyCode;
                                    self.pensionModel().officeCode(data.officeCode);
                                    self.pensionModel().startMonth(nts.uk.time.formatYearMonth(parseInt(data.startMonth)));
                                    self.pensionModel().endMonth(nts.uk.time.formatYearMonth(parseInt(data.endMonth)));
                                    self.japanYear("(" + nts.uk.time.yearmonthInJapanEmpire(data.startMonth).toString() + ")");
                                    self.pensionModel().autoCalculate(data.autoCalculate);
                                    self.pensionModel().fundInputApply(1);
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
                                            self.pensionModel().roundingMethods().pensionBonusPersonalComboBoxSelectedCode(self.convertRounding(item.roundAtrs.personalRoundAtr));
                                            self.pensionModel().roundingMethods().pensionBonusCompanyComboBoxSelectedCode(self.convertRounding(item.roundAtrs.companyRoundAtr));
                                        }
                                    });
                                    self.pensionModel().maxAmount(data.maxAmount);
                                    self.pensionModel().childContributionRate(data.childContributionRate);
                                };
                                ScreenModel.prototype.pensionCollectData = function () {
                                    var self = this;
                                    var rates = self.pensionModel().rateItems();
                                    var rateItems = [];
                                    rateItems.push(new PensionRateItemDto(PaymentType.SALARY, InsuranceGender.MALE, rates.pensionSalaryPersonalSon(), rates.pensionSalaryCompanySon()));
                                    rateItems.push(new PensionRateItemDto(PaymentType.SALARY, InsuranceGender.FEMALE, rates.pensionSalaryPersonalDaughter(), rates.pensionSalaryCompanyDaughter()));
                                    rateItems.push(new PensionRateItemDto(PaymentType.SALARY, InsuranceGender.UNKNOW, rates.pensionSalaryPersonalUnknown(), rates.pensionSalaryCompanyUnknown()));
                                    rateItems.push(new PensionRateItemDto(PaymentType.BONUS, InsuranceGender.MALE, rates.pensionBonusPersonalSon(), rates.pensionBonusCompanySon()));
                                    rateItems.push(new PensionRateItemDto(PaymentType.BONUS, InsuranceGender.FEMALE, rates.pensionBonusPersonalDaughter(), rates.pensionBonusCompanyDaughter()));
                                    rateItems.push(new PensionRateItemDto(PaymentType.BONUS, InsuranceGender.UNKNOW, rates.pensionBonusPersonalUnknown(), rates.pensionBonusCompanyUnknown()));
                                    var fundRates = self.pensionModel().fundRateItems();
                                    var fundRateItems = [];
                                    fundRateItems.push(new FundRateItemDto(PaymentType.SALARY, InsuranceGender.MALE, fundRates.salaryPersonalSonBurden(), fundRates.salaryCompanySonBurden(), fundRates.salaryPersonalSonExemption(), fundRates.salaryCompanySonExemption()));
                                    fundRateItems.push(new FundRateItemDto(PaymentType.SALARY, InsuranceGender.FEMALE, fundRates.salaryPersonalDaughterBurden(), fundRates.salaryCompanyDaughterBurden(), fundRates.salaryPersonalDaughterExemption(), fundRates.salaryCompanyDaughterExemption()));
                                    fundRateItems.push(new FundRateItemDto(PaymentType.SALARY, InsuranceGender.UNKNOW, fundRates.salaryPersonalUnknownBurden(), fundRates.salaryCompanyUnknownBurden(), fundRates.salaryPersonalUnknownExemption(), fundRates.salaryCompanyUnknownExemption()));
                                    fundRateItems.push(new FundRateItemDto(PaymentType.BONUS, InsuranceGender.MALE, fundRates.bonusPersonalSonBurden(), fundRates.bonusCompanySonBurden(), fundRates.bonusPersonalSonExemption(), fundRates.bonusCompanySonExemption()));
                                    fundRateItems.push(new FundRateItemDto(PaymentType.BONUS, InsuranceGender.FEMALE, fundRates.bonusPersonalDaughterBurden(), fundRates.bonusCompanyDaughterBurden(), fundRates.bonusPersonalDaughterExemption(), fundRates.bonusCompanyDaughterExemption()));
                                    fundRateItems.push(new FundRateItemDto(PaymentType.BONUS, InsuranceGender.UNKNOW, fundRates.bonusPersonalUnknownBurden(), fundRates.bonusCompanyUnknownBurden(), fundRates.bonusPersonalUnknownExemption(), fundRates.bonusCompanyUnknownExemption()));
                                    var roundingMethods = [];
                                    var rounding = self.pensionModel().roundingMethods();
                                    roundingMethods.push(new RoundingDto(PaymentType.SALARY, new RoundingItemDto(self.convertToRounding(self.pensionModel().roundingMethods().pensionSalaryPersonalComboBoxSelectedCode()), self.convertToRounding(self.pensionModel().roundingMethods().pensionSalaryCompanyComboBoxSelectedCode()))));
                                    roundingMethods.push(new RoundingDto(PaymentType.BONUS, new RoundingItemDto(self.convertToRounding(self.pensionModel().roundingMethods().pensionBonusPersonalComboBoxSelectedCode()), self.convertToRounding(self.pensionModel().roundingMethods().pensionBonusCompanyComboBoxSelectedCode()))));
                                    return new c.service.model.finder.PensionRateDto(self.pensionModel().historyId, self.pensionModel().companyCode, self.currentOfficeCode(), self.pensionModel().startMonth(), self.pensionModel().endMonth(), self.pensionModel().autoCalculate(), true, rateItems, fundRateItems, roundingMethods, self.pensionModel().maxAmount(), self.pensionModel().childContributionRate());
                                };
                                ScreenModel.prototype.getDataOfPensionSelectedOffice = function () {
                                    var self = this;
                                    var saveVal = null;
                                    self.pensionInsuranceOfficeList().forEach(function (item, index) {
                                        if (self.currentOfficeCode() == item.code) {
                                            saveVal = item;
                                        }
                                    });
                                    return saveVal;
                                };
                                ScreenModel.prototype.save = function () {
                                    var self = this;
                                    c.service.updatePensionRate(self.pensionCollectData()).done(function () {
                                    });
                                };
                                ScreenModel.prototype.onSelectHistory = function (id) {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.isLoading(true);
                                    self.isClickHistory(true);
                                    self.currentOfficeCode(self.getCurrentOfficeCode(id));
                                    c.service.instance.findHistoryByUuid(id).done(function (dto) {
                                        self.loadPension(dto);
                                        self.isLoading(false);
                                        $('.save-error').ntsError('clear');
                                        dfd.resolve();
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.onSave = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    if (nts.uk.ui._viewModel.errors.isEmpty()) {
                                        self.save();
                                    }
                                    else {
                                        alert('TODO has error!');
                                    }
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.onSelectMaster = function (code) {
                                    var self = this;
                                    self.isClickHistory(false);
                                };
                                ScreenModel.prototype.onRegistNew = function () {
                                    var self = this;
                                    self.OpenModalOfficeRegister();
                                };
                                ScreenModel.prototype.getCurrentOfficeCode = function (childId) {
                                    var self = this;
                                    var returnValue;
                                    if (self.masterHistoryList.length > 0) {
                                        self.masterHistoryList.forEach(function (parentItem) {
                                            if (parentItem.historyList) {
                                                parentItem.historyList.forEach(function (childItem) {
                                                    if (childItem.uuid == childId) {
                                                        self.sendOfficeData(parentItem.name);
                                                        returnValue = parentItem.code;
                                                    }
                                                });
                                            }
                                            else {
                                                return parentItem.code;
                                            }
                                        });
                                    }
                                    return returnValue;
                                };
                                ScreenModel.prototype.OpenModalOfficeRegister = function () {
                                    var self = this;
                                    nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                                    nts.uk.ui.windows.sub.modal("/view/qmm/008/e/index.xhtml", { title: "会社保険事業所の登録＞事業所の登録" }).onClosed(function () {
                                        self.start();
                                        var returnValue = nts.uk.ui.windows.getShared("insuranceOfficeChildValue");
                                    });
                                };
                                ScreenModel.prototype.OpenModalStandardMonthlyPricePension = function () {
                                    nts.uk.ui.windows.setShared("officeName", this.sendOfficeData());
                                    nts.uk.ui.windows.setShared("pensionModel", this.pensionModel());
                                    nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                                    nts.uk.ui.windows.sub.modal("/view/qmm/008/i/index.xhtml", { title: "会社保険事業所の登録＞標準報酬月額保険料額表" }).onClosed(function () {
                                        var returnValue = nts.uk.ui.windows.getShared("listOfficeOfChildValue");
                                    });
                                };
                                return ScreenModel;
                            }(ScreenBaseModel));
                            viewmodel.ScreenModel = ScreenModel;
                            var PensionRateModel = (function () {
                                function PensionRateModel() {
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
                                return PensionRateModel;
                            }());
                            viewmodel.PensionRateModel = PensionRateModel;
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
                        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
                        var HealthInsuranceAvgearn = (function () {
                            function HealthInsuranceAvgearn() {
                            }
                            return HealthInsuranceAvgearn;
                        }());
                        c.HealthInsuranceAvgearn = HealthInsuranceAvgearn;
                        var ChargeRateItem = (function () {
                            function ChargeRateItem() {
                            }
                            return ChargeRateItem;
                        }());
                        c.ChargeRateItem = ChargeRateItem;
                        var PaymentType = (function () {
                            function PaymentType() {
                            }
                            PaymentType.SALARY = 'Salary';
                            PaymentType.BONUS = 'Bonus';
                            return PaymentType;
                        }());
                        c.PaymentType = PaymentType;
                        var HealthInsuranceType = (function () {
                            function HealthInsuranceType() {
                            }
                            HealthInsuranceType.GENERAL = 'General';
                            HealthInsuranceType.NURSING = 'Nursing';
                            HealthInsuranceType.BASIC = 'Basic';
                            HealthInsuranceType.SPECIAL = 'Special';
                            return HealthInsuranceType;
                        }());
                        c.HealthInsuranceType = HealthInsuranceType;
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
                        c.Rounding = Rounding;
                        var InsuranceGender = (function () {
                            function InsuranceGender() {
                            }
                            InsuranceGender.MALE = "Male";
                            InsuranceGender.FEMALE = "Female";
                            InsuranceGender.UNKNOW = "Unknow";
                            return InsuranceGender;
                        }());
                        c.InsuranceGender = InsuranceGender;
                        var AutoCalculate = (function () {
                            function AutoCalculate() {
                            }
                            AutoCalculate.AUTO = "Auto";
                            AutoCalculate.MANUAL = "Manual";
                            return AutoCalculate;
                        }());
                        c.AutoCalculate = AutoCalculate;
                    })(c = qmm008.c || (qmm008.c = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qmm008.c.vm.js.map