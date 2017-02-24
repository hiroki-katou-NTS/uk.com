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
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.healthModel = ko.observable(new HealthInsuranceRateModel("code", 1, null, null, 15000));
                                    self.pensionModel = ko.observable(new PensionRateModel("code", 1, 1, null, null, null, 35000, 1.5));
                                    self.InsuranceOfficeList = ko.observableArray([]);
                                    self.officeSelectedCode = ko.observable('');
                                    self.currentParentCode = ko.observable('');
                                    self.currentChildCode = ko.observable('');
                                    self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.InsuranceOfficeList(), "childs"));
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
                                    self.numberInputOptions = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                                        grouplength: 3,
                                        decimallength: 2
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
                                    self.isClickHistory = ko.observable(false);
                                    self.officeSelectedCode.subscribe(function (officeSelectedCode) {
                                        if (officeSelectedCode != null || officeSelectedCode != undefined) {
                                            if (self.checkCode(officeSelectedCode)) {
                                                self.currentParentCode(officeSelectedCode);
                                                self.loadHistoryOfOffice(officeSelectedCode);
                                                self.healthModel(new HealthInsuranceRateModel("code", 1, null, null, 15000));
                                                self.isClickHistory(false);
                                            }
                                            else {
                                                self.currentChildCode(officeSelectedCode);
                                                self.isClickHistory(true);
                                                $.when(self.load(officeSelectedCode)).done(function () {
                                                }).fail(function (res) {
                                                });
                                            }
                                        }
                                    });
                                    self.pensionModel().fundInputApply.subscribe(function (pensionFundInputOptions) {
                                        if (self.fundInputEnable()) {
                                            self.fundInputEnable(false);
                                        }
                                        else {
                                            self.fundInputEnable(true);
                                        }
                                    });
                                }
                                ScreenModel.prototype.start = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.loadAllInsuranceOffice().done(function (data) {
                                        self.InsuranceOfficeList(data);
                                        if ((self.InsuranceOfficeList() != null) && (self.InsuranceOfficeList().length > 0)) {
                                            self.officeSelectedCode(self.InsuranceOfficeList()[0].code);
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
                                ScreenModel.prototype.loadAllInsuranceOffice = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    a.service.findInsuranceOffice(self.searchKey()).done(function (data) {
                                        dfd.resolve(data);
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
                                ScreenModel.prototype.checkCode = function (code) {
                                    var flag = false;
                                    this.InsuranceOfficeList().forEach(function (item, index) {
                                        if (item.code == code) {
                                            flag = true;
                                        }
                                    });
                                    return flag;
                                };
                                ScreenModel.prototype.loadHistoryOfOffice = function (officeCode) {
                                    var self = this;
                                    var officeData = self.InsuranceOfficeList();
                                    officeData.forEach(function (item, index) {
                                        if ((item.code == officeCode) && (item.childs.length == 0)) {
                                            a.service.getAllHealthInsuranceItem(item.code).done(function (data) {
                                                data.forEach(function (item2, index2) {
                                                    officeData[index].childs.push(new InsuranceOfficeItem(index + item.code, item2.officeCode, index + item2.historyId + index2, [], item2.startMonth.substring(0, 4) + "/" + item2.startMonth.substring(4, item2.startMonth.length) + "~" + item2.endMonth.substring(0, 4) + "/" + item2.endMonth.substring(4, item2.endMonth.length)));
                                                });
                                                self.InsuranceOfficeList(officeData);
                                                if (self.InsuranceOfficeList()[0].childs.length > 0)
                                                    self.officeSelectedCode(self.InsuranceOfficeList()[0].childs[0].code);
                                            });
                                        }
                                    });
                                };
                                ScreenModel.prototype.load = function (historyCode) {
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
                                        self.healthModel().roundingMethods().healthSalaryPersonalComboBox(self.roundingList());
                                        self.healthModel().roundingMethods().healthSalaryCompanyComboBox(self.roundingList());
                                        self.healthModel().roundingMethods().healthBonusPersonalComboBox(self.roundingList());
                                        self.healthModel().roundingMethods().healthBonusCompanyComboBox(self.roundingList());
                                        self.healthModel().roundingMethods().healthSalaryPersonalComboBoxSelectedCode("1");
                                        self.healthModel().roundingMethods().healthSalaryCompanyComboBoxSelectedCode("1");
                                        self.healthModel().roundingMethods().healthSalaryPersonalComboBoxSelectedCode("1");
                                        self.healthModel().roundingMethods().healthBonusCompanyComboBoxSelectedCode("1");
                                        self.healthModel().maxAmount(data.maxAmount);
                                        dfd.resolve();
                                    }).fail(function () {
                                    }).always(function (res) {
                                    });
                                    a.service.getPensionItemDetail(historyCode).done(function (data) {
                                        if (data == null) {
                                            return;
                                        }
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
                                        self.pensionModel().roundingMethods().pensionSalaryPersonalComboBox(self.roundingList());
                                        self.pensionModel().roundingMethods().pensionSalaryCompanyComboBox(self.roundingList());
                                        self.pensionModel().roundingMethods().pensionBonusPersonalComboBox(self.roundingList());
                                        self.pensionModel().roundingMethods().pensionBonusCompanyComboBox(self.roundingList());
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
                                    return new a.service.model.finder.HealthInsuranceRateDto("", "", self.currentParentCode(), self.healthModel().startMonth(), self.healthModel().endMonth(), 1, rateItems, roundingMethods, self.healthModel().maxAmount());
                                };
                                ScreenModel.prototype.resize = function () {
                                    if ($("#tabs-complex").width() > 700)
                                        $("#tabs-complex").width(700);
                                    else
                                        $("#tabs-complex").width("auto");
                                };
                                ScreenModel.prototype.getDataOfSelectedOffice = function () {
                                    var self = this;
                                    var saveVal = null;
                                    this.InsuranceOfficeList().forEach(function (item, index) {
                                        if (self.currentParentCode() == item.code) {
                                            saveVal = item;
                                        }
                                    });
                                    return saveVal;
                                };
                                ScreenModel.prototype.getIndexOfHistory = function (historyId) {
                                    var self = this;
                                    self.getDataOfSelectedOffice().childs.forEach(function (item, index) {
                                        if (item.historyId == historyId) {
                                            return index;
                                        }
                                    });
                                    return null;
                                };
                                ScreenModel.prototype.refreshOfficeList = function (returnValue) {
                                    var self = this;
                                    var currentInsuranceOfficeList = self.InsuranceOfficeList();
                                    if (returnValue != undefined && returnValue != null) {
                                        currentInsuranceOfficeList.forEach(function (item, index) {
                                            if (item.code == returnValue.code) {
                                                currentInsuranceOfficeList[index] = returnValue;
                                            }
                                        });
                                    }
                                    self.InsuranceOfficeList([]);
                                    self.InsuranceOfficeList(currentInsuranceOfficeList);
                                };
                                ScreenModel.prototype.save = function () {
                                    var self = this;
                                    a.service.registerHealthRate(self.healthCollectData()).done(function () {
                                    });
                                };
                                ScreenModel.prototype.OpenModalAddHistory = function () {
                                    var self = this;
                                    var sendOfficeItem = self.getDataOfSelectedOffice();
                                    var previousStartDate = "2016/04";
                                    nts.uk.ui.windows.setShared("sendOfficeParentValue", sendOfficeItem);
                                    nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                                    nts.uk.ui.windows.sub.modal("/view/qmm/008/b/index.xhtml", { title: "会社保険事業所の登録＞履歴の追加" }).onClosed(function () {
                                        var returnValue = nts.uk.ui.windows.getShared("addHistoryChildValue");
                                        self.refreshOfficeList(returnValue);
                                    });
                                };
                                ScreenModel.prototype.OpenModalOfficeRegister = function () {
                                    var self = this;
                                    nts.uk.ui.windows.setShared("officeCodeOfParentValue", self.officeSelectedCode());
                                    nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                                    nts.uk.ui.windows.sub.modal("/view/qmm/008/c/index.xhtml", { title: "会社保険事業所の登録＞事業所の登録" }).onClosed(function () {
                                        self.start();
                                        var returnValue = nts.uk.ui.windows.getShared("listOfficeOfChildValue");
                                    });
                                };
                                ScreenModel.prototype.OpenModalStandardMonthlyPriceHealth = function () {
                                    nts.uk.ui.windows.setShared("dataOfSelectedOffice", this.getDataOfSelectedOffice());
                                    nts.uk.ui.windows.setShared("healthModel", this.healthModel());
                                    nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                                    nts.uk.ui.windows.sub.modal("/view/qmm/008/h/index.xhtml", { title: "会社保険事業所の登録＞標準報酬月額保険料額表" }).onClosed(function () {
                                        var returnValue = nts.uk.ui.windows.getShared("listOfficeOfChildValue");
                                    });
                                };
                                ScreenModel.prototype.OpenModalStandardMonthlyPricePension = function () {
                                    nts.uk.ui.windows.setShared("dataOfSelectedOffice", this.getDataOfSelectedOffice());
                                    nts.uk.ui.windows.setShared("pensionModel", this.pensionModel());
                                    nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                                    nts.uk.ui.windows.sub.modal("/view/qmm/008/i/index.xhtml", { title: "会社保険事業所の登録＞標準報酬月額保険料額表" }).onClosed(function () {
                                        var returnValue = nts.uk.ui.windows.getShared("listOfficeOfChildValue");
                                    });
                                };
                                ScreenModel.prototype.OpenModalConfigHistory = function () {
                                    var self = this;
                                    var sendOfficeParentValue = this.getDataOfSelectedOffice();
                                    nts.uk.ui.windows.setShared("sendOfficeParentValue", sendOfficeParentValue);
                                    nts.uk.ui.windows.setShared("currentChildCode", self.currentChildCode());
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
                    })(a = qmm008.a || (qmm008.a = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
