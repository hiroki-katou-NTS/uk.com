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
                    var a1;
                    (function (a1) {
                        var viewmodel;
                        (function (viewmodel) {
                            var RoundingDto = a1.service.model.finder.RoundingDto;
                            var RoundingItemDto = a1.service.model.finder.RoundingItemDto;
                            var HealthInsuranceRateItemDto = a1.service.model.finder.HealthInsuranceRateItemDto;
                            var ChargeRateItemDto = a1.service.model.finder.ChargeRateItemDto;
                            var ScreenBaseModel = view.base.simplehistory.viewmodel.ScreenBaseModel;
                            var ScreenModel = (function (_super) {
                                __extends(ScreenModel, _super);
                                function ScreenModel() {
                                    _super.call(this, {
                                        functionName: '社会保険事業所',
                                        service: a1.service.instance,
                                        removeMasterOnLastHistoryRemove: false
                                    });
                                    var self = this;
                                    self.healthModel = ko.observable(new HealthInsuranceRateModel());
                                    self.healthInsuranceOfficeList = ko.observableArray([]);
                                    self.healthFilteredData = ko.observableArray(nts.uk.util.flatArray(self.healthInsuranceOfficeList(), "childs"));
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
                                    self.Rate3 = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                                        grouplength: 3,
                                        decimallength: 3
                                    }));
                                    self.healthAutoCalculateOptions = ko.observableArray([
                                        { code: '0', name: 'する' },
                                        { code: '1', name: 'しない' }
                                    ]);
                                    self.isTransistReturnData = ko.observable(false);
                                    self.isLoading = ko.observable(true);
                                    self.currentOfficeCode = ko.observable('');
                                    self.sendOfficeData = ko.observable(null);
                                    self.japanYear = ko.observable('');
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
                                    a1.service.findAllRounding().done(function (data) {
                                        self.roundingList(data);
                                        dfd.resolve(data);
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
                                ScreenModel.prototype.convertToRounding = function (stringValue) {
                                    switch (stringValue) {
                                        case "0": return Rounding.ROUNDUP;
                                        case "1": return Rounding.TRUNCATION;
                                        case "2": return Rounding.ROUNDDOWN;
                                        case "3": return Rounding.DOWN5_UP6;
                                        case "4": return Rounding.DOWN4_UP5;
                                        default: return Rounding.ROUNDUP;
                                    }
                                };
                                ScreenModel.prototype.loadHealth = function (data) {
                                    var self = this;
                                    if (data == null) {
                                        return;
                                    }
                                    self.healthModel().historyId = data.historyId;
                                    self.healthModel().startMonth(nts.uk.time.formatYearMonth(parseInt(data.startMonth)));
                                    self.healthModel().endMonth(nts.uk.time.formatYearMonth(parseInt(data.endMonth)));
                                    self.japanYear("(" + nts.uk.time.yearmonthInJapanEmpire(data.startMonth).toString() + ")");
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
                                            self.healthModel().roundingMethods().healthBonusPersonalComboBoxSelectedCode(self.convertRounding(item.roundAtrs.personalRoundAtr));
                                            self.healthModel().roundingMethods().healthBonusCompanyComboBoxSelectedCode(self.convertRounding(item.roundAtrs.companyRoundAtr));
                                        }
                                    });
                                    self.healthModel().maxAmount(data.maxAmount);
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
                                    roundingMethods.push(new RoundingDto(PaymentType.SALARY, new RoundingItemDto(self.convertToRounding(self.healthModel().roundingMethods().healthSalaryPersonalComboBoxSelectedCode()), self.convertToRounding(self.healthModel().roundingMethods().healthSalaryCompanyComboBoxSelectedCode()))));
                                    roundingMethods.push(new RoundingDto(PaymentType.BONUS, new RoundingItemDto(self.convertToRounding(self.healthModel().roundingMethods().healthBonusPersonalComboBoxSelectedCode()), self.convertToRounding(self.healthModel().roundingMethods().healthBonusCompanyComboBoxSelectedCode()))));
                                    return new a1.service.model.finder.HealthInsuranceRateDto(self.healthModel().historyId, self.healthModel().companyCode, self.currentOfficeCode(), self.healthModel().startMonth(), self.healthModel().endMonth(), self.healthModel().autoCalculate(), rateItems, roundingMethods, self.healthModel().maxAmount());
                                };
                                ScreenModel.prototype.getDataOfHealthSelectedOffice = function () {
                                    var self = this;
                                    var saveVal = null;
                                    self.healthInsuranceOfficeList().forEach(function (item, index) {
                                        if (self.currentOfficeCode() == item.code) {
                                            saveVal = item;
                                        }
                                    });
                                    return saveVal;
                                };
                                ScreenModel.prototype.save = function () {
                                    var self = this;
                                    a1.service.updateHealthRate(self.healthCollectData()).done(function () {
                                    }).fail();
                                };
                                ScreenModel.prototype.onSelectHistory = function (id) {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.isLoading(true);
                                    self.currentOfficeCode(self.getCurrentOfficeCode(id));
                                    a1.service.instance.findHistoryByUuid(id).done(function (dto) {
                                        self.loadHealth(dto);
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
                                ScreenModel.prototype.getCurrentOfficeCode = function (childId) {
                                    var self = this;
                                    var returnValue;
                                    if (self.masterHistoryList.length > 0) {
                                        self.masterHistoryList.forEach(function (parentItem) {
                                            self.sendOfficeData(parentItem);
                                            if (parentItem.historyList) {
                                                parentItem.historyList.forEach(function (childItem) {
                                                    if (childItem.uuid == childId) {
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
                                ScreenModel.prototype.onRegistNew = function () {
                                    var self = this;
                                    self.OpenModalOfficeRegister();
                                };
                                ScreenModel.prototype.OpenModalOfficeRegister = function () {
                                    var self = this;
                                    nts.uk.ui.windows.sub.modal("/view/qmm/008/c/index.xhtml", { title: "会社保険事業所の登録＞事業所の登録" }).onClosed(function () {
                                        self.startPage();
                                        var returnValue = nts.uk.ui.windows.getShared("insuranceOfficeChildValue");
                                    });
                                };
                                ScreenModel.prototype.OpenModalStandardMonthlyPriceHealth = function () {
                                    nts.uk.ui.windows.setShared("dataOfSelectedOffice", this.sendOfficeData());
                                    nts.uk.ui.windows.setShared("healthModel", this.healthModel());
                                    nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                                    nts.uk.ui.windows.sub.modal("/view/qmm/008/h/index.xhtml", { title: "会社保険事業所の登録＞標準報酬月額保険料額表" }).onClosed(function () {
                                        var returnValue = nts.uk.ui.windows.getShared("listOfficeOfChildValue");
                                    });
                                };
                                return ScreenModel;
                            }(ScreenBaseModel));
                            viewmodel.ScreenModel = ScreenModel;
                            var HealthInsuranceRateModel = (function () {
                                function HealthInsuranceRateModel() {
                                    this.startMonth = ko.observable("");
                                    this.endMonth = ko.observable("");
                                    this.officeCode = ko.observable('');
                                    this.autoCalculate = ko.observable(1);
                                    this.rateItems = ko.observable(new HealthInsuranceRateItemModel());
                                    this.roundingMethods = ko.observable(new HealthInsuranceRoundingModel());
                                    this.maxAmount = ko.observable(0);
                                }
                                return HealthInsuranceRateModel;
                            }());
                            viewmodel.HealthInsuranceRateModel = HealthInsuranceRateModel;
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
                        })(viewmodel = a1.viewmodel || (a1.viewmodel = {}));
                        var HealthInsuranceAvgearn = (function () {
                            function HealthInsuranceAvgearn() {
                            }
                            return HealthInsuranceAvgearn;
                        }());
                        a1.HealthInsuranceAvgearn = HealthInsuranceAvgearn;
                        var ChargeRateItem = (function () {
                            function ChargeRateItem() {
                            }
                            return ChargeRateItem;
                        }());
                        a1.ChargeRateItem = ChargeRateItem;
                        var PaymentType = (function () {
                            function PaymentType() {
                            }
                            PaymentType.SALARY = 'Salary';
                            PaymentType.BONUS = 'Bonus';
                            return PaymentType;
                        }());
                        a1.PaymentType = PaymentType;
                        var HealthInsuranceType = (function () {
                            function HealthInsuranceType() {
                            }
                            HealthInsuranceType.GENERAL = 'General';
                            HealthInsuranceType.NURSING = 'Nursing';
                            HealthInsuranceType.BASIC = 'Basic';
                            HealthInsuranceType.SPECIAL = 'Special';
                            return HealthInsuranceType;
                        }());
                        a1.HealthInsuranceType = HealthInsuranceType;
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
                        a1.Rounding = Rounding;
                        var InsuranceGender = (function () {
                            function InsuranceGender() {
                            }
                            InsuranceGender.MALE = "Male";
                            InsuranceGender.FEMALE = "Female";
                            InsuranceGender.UNKNOW = "Unknow";
                            return InsuranceGender;
                        }());
                        a1.InsuranceGender = InsuranceGender;
                        var AutoCalculate = (function () {
                            function AutoCalculate() {
                            }
                            AutoCalculate.AUTO = "Auto";
                            AutoCalculate.MANUAL = "Manual";
                            return AutoCalculate;
                        }());
                        a1.AutoCalculate = AutoCalculate;
                    })(a1 = qmm008.a1 || (qmm008.a1 = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
