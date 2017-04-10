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
                    var b;
                    (function (b) {
                        var viewmodel;
                        (function (viewmodel) {
                            var RoundingDto = b.service.model.finder.RoundingDto;
                            var RoundingItemDto = b.service.model.finder.RoundingItemDto;
                            var HealthInsuranceRateItemDto = b.service.model.finder.HealthInsuranceRateItemDto;
                            var ChargeRateItemDto = b.service.model.finder.ChargeRateItemDto;
                            var ScreenBaseModel = view.base.simplehistory.viewmodel.ScreenBaseModel;
                            var ScreenModel = (function (_super) {
                                __extends(ScreenModel, _super);
                                function ScreenModel() {
                                    _super.call(this, {
                                        functionName: '健康保険',
                                        service: b.service.instance,
                                        removeMasterOnLastHistoryRemove: false
                                    });
                                    var self = this;
                                    self.healthModel = ko.observable(new HealthInsuranceRateModel());
                                    self.healthInsuranceOfficeList = ko.observableArray([]);
                                    self.healthFilteredData = ko.observableArray(nts.uk.util.flatArray(self.healthInsuranceOfficeList(), "childs"));
                                    self.roundingList = ko.observableArray([]);
                                    self.Rate3 = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                                        grouplength: 3,
                                        decimallength: 3
                                    }));
                                    self.Rate5 = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                                        grouplength: 3,
                                        decimallength: 5
                                    }));
                                    self.healthAutoCalculateOptions = ko.observableArray([
                                        { code: '0', name: 'する' },
                                        { code: '1', name: 'しない' }
                                    ]);
                                    self.isTransistReturnData = ko.observable(false);
                                    self.isLoading = ko.observable(true);
                                    self.currentOfficeCode = ko.observable('');
                                    self.sendOfficeData = ko.observable('');
                                    self.japanYear = ko.observable('');
                                    self.errorList = ko.observableArray([
                                        { messageId: "ER001", message: "＊が入力されていません。" },
                                        { messageId: "ER007", message: "＊が選択されていません。" },
                                        { messageId: "ER005", message: "入力した＊は既に存在しています。\r\n ＊を確認してください。" },
                                        { messageId: "ER008", message: "選択された＊は使用されているため削除できません。" },
                                        { messageId: "AL001", message: "変更された内容が登録されていません。\r\n よろしいですか。" }
                                    ]);
                                    self.dirty = new nts.uk.ui.DirtyChecker(ko.observable(''));
                                    self.backupDataDirty = ko.observable();
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
                                    b.service.findAllRounding().done(function (data) {
                                        self.roundingList(data);
                                        dfd.resolve(data);
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.convertRounding = function (stringRounding) {
                                    switch (stringRounding) {
                                        case Rounding.TRUNCATION: return "0";
                                        case Rounding.ROUNDUP: return "1";
                                        case Rounding.DOWN4_UP5: return "2";
                                        case Rounding.DOWN5_UP6: return "3";
                                        case Rounding.ROUNDDOWN: return "4";
                                        default: return "0";
                                    }
                                };
                                ScreenModel.prototype.convertToRounding = function (stringValue) {
                                    switch (stringValue) {
                                        case "0": return Rounding.TRUNCATION;
                                        case "1": return Rounding.ROUNDUP;
                                        case "2": return Rounding.DOWN4_UP5;
                                        case "3": return Rounding.DOWN5_UP6;
                                        case "4": return Rounding.ROUNDDOWN;
                                        default: return Rounding.TRUNCATION;
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
                                    return new b.service.model.finder.HealthInsuranceRateDto(self.healthModel().historyId, self.healthModel().companyCode, self.currentOfficeCode(), self.healthModel().startMonth(), self.healthModel().endMonth(), self.healthModel().autoCalculate(), rateItems, roundingMethods, self.healthModel().maxAmount());
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
                                    if (self.healthModel().autoCalculate() == AutoCalculateType.Auto) {
                                        nts.uk.ui.dialog.confirm("自動計算が行われます。登録しますか？").ifYes(function () {
                                            self.dirty = new nts.uk.ui.DirtyChecker(self.healthModel);
                                            b.service.updateHealthRate(self.healthCollectData()).done(function () {
                                                self.backupDataDirty(self.healthCollectData());
                                            }).fail();
                                        }).ifNo(function () {
                                        });
                                    }
                                    else {
                                        self.dirty = new nts.uk.ui.DirtyChecker(self.healthModel);
                                        b.service.updateHealthRate(self.healthCollectData()).done(function () {
                                            self.backupDataDirty(self.healthCollectData());
                                        }).fail();
                                    }
                                };
                                ScreenModel.prototype.onSelectHistory = function (id) {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.isLoading(true);
                                    self.isClickHistory(true);
                                    self.currentOfficeCode(self.getCurrentOfficeCode(id));
                                    b.service.instance.findHistoryByUuid(id).done(function (dto) {
                                        self.backupDataDirty(dto);
                                        self.loadHealth(dto);
                                        self.dirty = new nts.uk.ui.DirtyChecker(self.healthModel);
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
                                    }
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.onSelectMaster = function (code) {
                                    var self = this;
                                    self.isClickHistory(false);
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
                                ScreenModel.prototype.onRegistNew = function () {
                                    var self = this;
                                    self.OpenModalOfficeRegister();
                                };
                                ScreenModel.prototype.isDirty = function () {
                                    var self = this;
                                    return self.dirty.isDirty();
                                };
                                ScreenModel.prototype.OpenModalOfficeRegisterWithDirtyCheck = function () {
                                    var self = this;
                                    if (self.dirty.isDirty()) {
                                        nts.uk.ui.dialog.confirm(self.errorList()[4].message).ifYes(function () {
                                            self.loadHealth(self.backupDataDirty());
                                            self.OpenModalOfficeRegister();
                                            self.dirty.reset();
                                        }).ifCancel(function () {
                                        });
                                    }
                                    else {
                                        self.OpenModalOfficeRegister();
                                    }
                                };
                                ScreenModel.prototype.OpenModalOfficeRegister = function () {
                                    var self = this;
                                    nts.uk.ui.windows.sub.modal("/view/qmm/008/e/index.xhtml", { title: "会社保険事業所の登録＞事業所の登録", dialogClass: 'no-close' }).onClosed(function () {
                                        self.loadMasterHistory();
                                        var codeOfNewOffice = nts.uk.ui.windows.getShared("codeOfNewOffice");
                                    });
                                };
                                ScreenModel.prototype.OpenModalStandardMonthlyPriceHealthWithDirtyCheck = function () {
                                    var self = this;
                                    if (self.dirty.isDirty()) {
                                        nts.uk.ui.dialog.confirm(self.errorList()[4].message).ifYes(function () {
                                            self.loadHealth(self.backupDataDirty());
                                            self.OpenModalStandardMonthlyPriceHealth();
                                            self.dirty.reset();
                                        }).ifCancel(function () {
                                        });
                                    }
                                    else {
                                        self.OpenModalStandardMonthlyPriceHealth();
                                    }
                                };
                                ScreenModel.prototype.OpenModalStandardMonthlyPriceHealth = function () {
                                    var self = this;
                                    nts.uk.ui.windows.setShared("officeName", this.sendOfficeData());
                                    nts.uk.ui.windows.setShared("healthModel", this.healthModel());
                                    nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                                    nts.uk.ui.windows.sub.modal("/view/qmm/008/h/index.xhtml", { title: "会社保険事業所の登録＞標準報酬月額保険料額表", dialogClass: 'no-close' }).onClosed(function () {
                                        var returnValue = nts.uk.ui.windows.getShared("listOfficeOfChildValue");
                                    });
                                };
                                ScreenModel.prototype.goToPension = function () {
                                    nts.uk.request.jump("/view/qmm/008/c/index.xhtml");
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
                        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
                        var ChargeRateItem = (function () {
                            function ChargeRateItem() {
                            }
                            return ChargeRateItem;
                        }());
                        b.ChargeRateItem = ChargeRateItem;
                        var PaymentType = (function () {
                            function PaymentType() {
                            }
                            PaymentType.SALARY = 'Salary';
                            PaymentType.BONUS = 'Bonus';
                            return PaymentType;
                        }());
                        b.PaymentType = PaymentType;
                        var HealthInsuranceType = (function () {
                            function HealthInsuranceType() {
                            }
                            HealthInsuranceType.GENERAL = 'General';
                            HealthInsuranceType.NURSING = 'Nursing';
                            HealthInsuranceType.BASIC = 'Basic';
                            HealthInsuranceType.SPECIAL = 'Special';
                            return HealthInsuranceType;
                        }());
                        b.HealthInsuranceType = HealthInsuranceType;
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
                        b.Rounding = Rounding;
                        var InsuranceGender = (function () {
                            function InsuranceGender() {
                            }
                            InsuranceGender.MALE = "Male";
                            InsuranceGender.FEMALE = "Female";
                            InsuranceGender.UNKNOW = "Unknow";
                            return InsuranceGender;
                        }());
                        b.InsuranceGender = InsuranceGender;
                        var AutoCalculate = (function () {
                            function AutoCalculate() {
                            }
                            AutoCalculate.AUTO = "Auto";
                            AutoCalculate.MANUAL = "Manual";
                            return AutoCalculate;
                        }());
                        b.AutoCalculate = AutoCalculate;
                        (function (Number) {
                            Number[Number["Zero"] = 0] = "Zero";
                            Number[Number["One"] = 1] = "One";
                            Number[Number["Three"] = 3] = "Three";
                        })(b.Number || (b.Number = {}));
                        var Number = b.Number;
                        (function (AutoCalculateType) {
                            AutoCalculateType[AutoCalculateType["Auto"] = 0] = "Auto";
                            AutoCalculateType[AutoCalculateType["Manual"] = 1] = "Manual";
                        })(b.AutoCalculateType || (b.AutoCalculateType = {}));
                        var AutoCalculateType = b.AutoCalculateType;
                    })(b = qmm008.b || (qmm008.b = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qmm008.b.vm.js.map