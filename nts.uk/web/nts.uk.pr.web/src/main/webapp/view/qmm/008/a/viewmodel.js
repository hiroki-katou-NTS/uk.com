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
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.healthModel = ko.observable(new HealthInsuranceRateModel("code", 1, null, null, 15000));
                                    self.pensionModel = ko.observable(new PensionRateModel("code", 1, 2, null, null, null, 35000, 1.5));
                                    self.InsuranceOfficeList = ko.observableArray([]);
                                    self.officeSelectedCode = ko.observable('');
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
                                        { code: '1', name: 'する' },
                                        { code: '2', name: 'しない' }
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
                                    self.fundInputEnable = ko.observable(true);
                                    self.officeSelectedCode.subscribe(function (officeSelectedCode) {
                                        if (officeSelectedCode != null || officeSelectedCode != undefined) {
                                            $.when(self.load(officeSelectedCode)).done(function () {
                                            }).fail(function (res) {
                                            });
                                        }
                                    });
                                    self.pensionModel().funInputOption.subscribe(function (pensionFundInputOptions) {
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
                                        if (self.InsuranceOfficeList().length > 0) {
                                        }
                                        else {
                                        }
                                        dfd.resolve(null);
                                    });
                                    self.getAllRounding().done(function () {
                                        dfd.resolve(null);
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.loadAllInsuranceOffice = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    a.service.findInsuranceOffice(self.searchKey()).done(function (data) {
                                        data.forEach(function (item, index) {
                                            a.service.findHistoryByOfficeCode(item.code).done(function (data2) {
                                                var addData = new InsuranceOfficeItem(index + item.code, data2.name, index + data2.code, []);
                                                data[index].childs.push(addData);
                                            });
                                        });
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
                                ScreenModel.prototype.load = function (code) {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    a.service.getHealthInsuranceItemDetail(code).done(function (data) {
                                        if (data == null) {
                                            return;
                                        }
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
                                        dfd.resolve();
                                    }).fail(function () {
                                    }).always(function (res) {
                                    });
                                    a.service.getPensionItemDetail(code).done(function (data) {
                                        if (data == null) {
                                            return;
                                        }
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
                                        dfd.resolve();
                                    }).fail(function () {
                                    }).always(function (res) {
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.resize = function () {
                                    if ($("#tabs-complex").width() > 700)
                                        $("#tabs-complex").width(700);
                                    else
                                        $("#tabs-complex").width("auto");
                                };
                                ScreenModel.prototype.convertListToParentChilds = function () {
                                };
                                ScreenModel.prototype.getDataOfSelectedOffice = function () {
                                    var self = this;
                                    var saveVal = null;
                                    this.InsuranceOfficeList().forEach(function (item, index) {
                                        if (self.officeSelectedCode() == item.code) {
                                            saveVal = item;
                                        }
                                    });
                                    return saveVal;
                                };
                                ScreenModel.prototype.OpenModalSubWindow = function () {
                                    var self = this;
                                    var saveVal = self.getDataOfSelectedOffice();
                                    nts.uk.ui.windows.setShared("addHistoryParentValue", saveVal);
                                    nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                                    nts.uk.ui.windows.sub.modal("/view/qmm/008/b/index.xhtml", { title: "会社保険事業所の登録＞履歴の追加" }).onClosed(function () {
                                        var returnValue = nts.uk.ui.windows.getShared("addHistoryChildValue");
                                        var currentInsuranceOfficeList = self.InsuranceOfficeList();
                                        if (returnValue != undefined) {
                                            currentInsuranceOfficeList.forEach(function (item, index) {
                                                if (item.code == returnValue.code) {
                                                    currentInsuranceOfficeList[index] = returnValue;
                                                }
                                            });
                                        }
                                        self.InsuranceOfficeList([]);
                                        self.InsuranceOfficeList(currentInsuranceOfficeList);
                                    });
                                };
                                ScreenModel.prototype.OpenModalOfficeRegister = function () {
                                    var self = this;
                                    nts.uk.ui.windows.setShared("officeCodeOfParentValue", self.officeSelectedCode());
                                    nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                                    nts.uk.ui.windows.sub.modal("/view/qmm/008/c/index.xhtml", { title: "会社保険事業所の登録＞事業所の登録" }).onClosed(function () {
                                        var returnValue = nts.uk.ui.windows.getShared("listOfficeOfChildValue");
                                    });
                                };
                                ScreenModel.prototype.OpenModalStandardMonthlyPriceHealth = function () {
                                    nts.uk.ui.windows.setShared("dataParentValue", "");
                                    nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                                    nts.uk.ui.windows.sub.modal("/view/qmm/008/h/index.xhtml", { title: "会社保険事業所の登録＞標準報酬月額保険料額表" }).onClosed(function () {
                                        var returnValue = nts.uk.ui.windows.getShared("listOfficeOfChildValue");
                                    });
                                };
                                ScreenModel.prototype.OpenModalStandardMonthlyPricePension = function () {
                                    nts.uk.ui.windows.setShared("dataParentValue", "");
                                    nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                                    nts.uk.ui.windows.sub.modal("/view/qmm/008/i/index.xhtml", { title: "会社保険事業所の登録＞標準報酬月額保険料額表" }).onClosed(function () {
                                        var returnValue = nts.uk.ui.windows.getShared("listOfficeOfChildValue");
                                    });
                                };
                                ScreenModel.prototype.OpenModalConfigHistory = function () {
                                    var getVal = null;
                                    getVal = this.getDataOfSelectedOffice();
                                    nts.uk.ui.windows.setShared("updateHistoryParentValue", getVal);
                                    nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                                    nts.uk.ui.windows.sub.modal("/view/qmm/008/f/index.xhtml", { title: "会社保険事業所の登録＞履歴の編集" }).onClosed(function () {
                                        var returnValue = nts.uk.ui.windows.getShared("addHistoryChildValue");
                                    });
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var HealthInsuranceRateModel = (function () {
                                function HealthInsuranceRateModel(officeCode, autoCalculate, rateItems, roundingMethods, maxAmount) {
                                    this.healthDate = ko.observable('2016/04');
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
                                function PensionRateModel(officeCode, funInputOption, autoCalculate, rateItems, fundRateItems, roundingMethods, maxAmount, officeRate) {
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
                                return PensionRateModel;
                            }());
                            viewmodel.PensionRateModel = PensionRateModel;
                            var HealthInsuranceRateItemModel = (function () {
                                function HealthInsuranceRateItemModel() {
                                    this.healthSalaryPersonalGeneral = ko.observable(0);
                                    this.healthSalaryCompanyGeneral = ko.observable(0);
                                    this.healthBonusPersonalGeneral = ko.observable(0);
                                    this.healthBonusCompanyGeneral = ko.observable(0);
                                    this.healthSalaryPersonalNursing = ko.observable(40990);
                                    this.healthSalaryCompanyNursing = ko.observable(40990);
                                    this.healthBonusPersonalNursing = ko.observable(40990);
                                    this.healthBonusCompanyNursing = ko.observable(40990);
                                    this.healthSalaryPersonalBasic = ko.observable(40990);
                                    this.healthSalaryCompanyBasic = ko.observable(40990);
                                    this.healthBonusPersonalBasic = ko.observable(40990);
                                    this.healthBonusCompanyBasic = ko.observable(40990);
                                    this.healthSalaryPersonalSpecific = ko.observable(40990);
                                    this.healthSalaryCompanySpecific = ko.observable(40990);
                                    this.healthBonusPersonalSpecific = ko.observable(40990);
                                    this.healthBonusCompanySpecific = ko.observable(40990);
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
                    })(a = qmm008.a || (qmm008.a = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
