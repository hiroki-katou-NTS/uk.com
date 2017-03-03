var qrm001;
(function (qrm001) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    //Employee Process 
                    self.employeeList = ko.observableArray([
                        new EmployeeInfo('A0001', 'A'),
                        new EmployeeInfo('A0002', 'B'),
                        new EmployeeInfo('A0003', 'C')
                    ]);
                    self.currentEmployeeIndex = ko.observable(0);
                    self.currentEmployeeCode = ko.observable('A0001');
                    self.currentEmployee = ko.observable(self.employeeList()[self.currentEmployeeIndex()]);
                    self.previous = ko.observable(false);
                    self.next = ko.observable(true);
                    self.currentEmployeeIndex.subscribe(function (value) {
                        self.previous((value === 0) ? false : true);
                        self.next((value === (self.employeeList().length - 1)) ? false : true);
                        self.currentEmployee(self.employeeList()[value]);
                        self.currentEmployeeCode(self.currentEmployee().personId());
                        self.getRetirementPaymentByPersonId(self.currentEmployeeCode());
                    });
                    //Retirement Payment Process
                    self.fullSetSelect = ko.observableArray([
                        { code: 0, name: '使用しない' },
                        { code: 1, name: '支給1' },
                        { code: 2, name: '支給2' },
                        { code: 3, name: '支給3' },
                        { code: 4, name: '支給4' },
                        { code: 5, name: '支給5' }]);
                    self.bankTransferList = ko.observableArray([
                        new BankTransferInfo('bank1', 'branch1', '0001'),
                        new BankTransferInfo('bank2', 'branch2', '0002'),
                        new BankTransferInfo('bank3', 'branch3', '0003'),
                        new BankTransferInfo('bank4', 'branch4', '0004'),
                        new BankTransferInfo('bank5', 'branch5', '0005')
                    ]);
                    self.isUpdate = ko.observable(false);
                    self.SEL_007 = ko.observable();
                    self.INP_014 = ko.observable();
                    self.SEL_001 = ko.observable();
                    self.INP_002 = ko.observable();
                    self.INP_003 = ko.observable();
                    self.INP_004 = ko.observable();
                    self.INP_005 = ko.observable();
                    self.INP_006 = ko.observable();
                    self.INP_007 = ko.observable();
                    self.INP_008 = ko.observable();
                    self.SEL_004 = ko.observable(2);
                    self.SEL_005 = ko.observable();
                    self.INP_009 = ko.observable();
                    self.INP_010 = ko.observable();
                    self.INP_011 = ko.observable();
                    self.SEL_007.subscribe(function (value) {
                        var index = _.findIndex(self.retirementPaymentKeyList(), function (o) { return o.payDate == value; });
                        self.retirementPaymentKeyCurrent(self.retirementPaymentKeyList()[index]);
                        self.retirementPaymentInfoCurrent(self.retirementPaymentInfoList()[index]);
                        self.transferBankMoneyCurrent(self.transferBankMoneyList()[index]);
                        self.INP_014(value);
                        self.SEL_001(self.retirementPaymentInfoCurrent().trialPeriodSet());
                        self.INP_002(self.retirementPaymentInfoCurrent().exclusionYears());
                        self.INP_003(self.retirementPaymentInfoCurrent().additionalBoardYears());
                        self.INP_004(self.retirementPaymentInfoCurrent().boardYears());
                        self.INP_005(self.retirementPaymentInfoCurrent().totalPaymentMoney());
                        self.INP_006(self.retirementPaymentInfoCurrent().deduction1Money());
                        self.INP_007(self.retirementPaymentInfoCurrent().deduction2Money());
                        self.INP_008(self.retirementPaymentInfoCurrent().deduction3Money());
                        self.SEL_004(self.retirementPaymentInfoCurrent().retirementPayOption());
                        self.INP_009(self.retirementPaymentInfoCurrent().incomeTaxMoney());
                        self.INP_010(self.retirementPaymentInfoCurrent().cityTaxMoney());
                        self.INP_011(self.retirementPaymentInfoCurrent().prefectureTaxMoney());
                        self.SEL_005(self.retirementPaymentInfoCurrent().taxCalculationMethod());
                    });
                    self.SEL_005.subscribe(function (value) {
                        if (!value) {
                            self.autoCaculator();
                            $(".caculator").css('background-color', '#ffc000');
                        }
                        else
                            $(".caculator").css('background-color', '#cee6ff');
                    });
                    self.INP_005.subscribe(function (valueinp5) { if (!self.SEL_005()) {
                        self.autoCaculator();
                    } });
                    self.INP_006.subscribe(function (valueinp6) { if (!self.SEL_005()) {
                        self.autoCaculator();
                    } });
                    self.INP_007.subscribe(function (valueinp7) { if (!self.SEL_005()) {
                        self.autoCaculator();
                    } });
                    self.INP_008.subscribe(function (valueinp8) { if (!self.SEL_005()) {
                        self.autoCaculator();
                    } });
                    self.SEL_004.subscribe(function (valuesel4) { if (!self.SEL_005()) {
                        self.autoCaculator();
                    } });
                    self.retirementPaymentKeyList = ko.observableArray([]);
                    self.retirementPaymentKeyCurrent = ko.observable(new RetirementPaymentKey(null, null));
                    self.retirementPaymentInfoList = ko.observableArray([]);
                    self.retirementPaymentInfoCurrent = ko.observable(new RetirementPaymentInfo(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
                    self.transferBankMoneyList = ko.observableArray([[]]);
                    self.transferBankMoneyCurrent = ko.observableArray([
                        new TransferBankMoney(0, null), new TransferBankMoney(0, null), new TransferBankMoney(0, null), new TransferBankMoney(0, null), new TransferBankMoney(0, null)
                    ]);
                    self.selectList10 = ko.observableArray(self.refreshSelect(self.fullSetSelect, self.transferBankMoneyCurrent, 5));
                    self.transferBankMoneyCurrent().forEach(function (item) {
                        item.optionSet.subscribe(function (value) {
                            if (value) {
                                item.money(null);
                                self.selectList10(self.refreshSelect(self.fullSetSelect, self.transferBankMoneyCurrent, 5));
                            }
                            else {
                                item.money(null);
                                self.selectList10(self.refreshSelect(self.fullSetSelect, self.transferBankMoneyCurrent, 5));
                            }
                        });
                    });
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.getRetirementPaymentByPersonId(self.currentEmployeeCode()).done(function () {
                        dfd.resolve();
                    }).fail(function () {
                        dfd.resolve();
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.next_emp = function () {
                    var self = this;
                    self.currentEmployeeIndex(self.currentEmployeeIndex() + 1);
                };
                ScreenModel.prototype.previous_emp = function () {
                    var self = this;
                    self.currentEmployeeIndex(self.currentEmployeeIndex() - 1);
                };
                ScreenModel.prototype.filter = function (dataset, currentSet, index) {
                    var self = this;
                    return _.filter(dataset(), function (o) {
                        return o.code == 0 ||
                            o.code == currentSet()[index].optionSet() ||
                            o.code !== currentSet()[0].optionSet() &&
                                o.code !== currentSet()[1].optionSet() &&
                                o.code !== currentSet()[2].optionSet() &&
                                o.code !== currentSet()[3].optionSet() &&
                                o.code !== currentSet()[4].optionSet();
                    });
                };
                ScreenModel.prototype.refreshSelect = function (source1, source2, length) {
                    var self = this;
                    var array = [];
                    for (var i = 0; i < length; i++) {
                        array.push(self.filter(source1, source2, i));
                    }
                    return array;
                };
                ScreenModel.prototype.manualCaculator = function () {
                    var self = this;
                    var totalPaymentMoney = parseInt(self.INP_005());
                    var deduction1 = parseInt(self.INP_006());
                    var deduction2 = parseInt(self.INP_007());
                    var deduction3 = parseInt(self.INP_008());
                    var incomeTax = parseInt(self.INP_009());
                    var cityTax = parseInt(self.INP_010());
                    var prefectureTax = parseInt(self.INP_011());
                    var totalDeclarationMoney = (deduction1 ? deduction1 : 0) +
                        (deduction2 ? deduction2 : 0) +
                        (deduction3 ? deduction3 : 0) +
                        (incomeTax ? incomeTax : 0) +
                        (cityTax ? cityTax : 0) +
                        (prefectureTax ? prefectureTax : 0);
                    self.retirementPaymentInfoCurrent().totalDeclarationMoney(totalDeclarationMoney);
                    self.retirementPaymentInfoCurrent().actualRecieveMoney(totalPaymentMoney - totalDeclarationMoney);
                };
                ScreenModel.prototype.autoCaculator = function () {
                    var self = this;
                    var year; // LBL003
                    var reduction; // Khau tru
                    var totalPaymentMoney = parseInt(self.INP_005());
                    var rs = (totalPaymentMoney - reduction) / 2;
                    var tax = (rs < 0) ? rs : 0; // Thue nghi viec
                    var payOption = self.SEL_004();
                    var deduction1 = parseInt(self.INP_006());
                    var deduction2 = parseInt(self.INP_007());
                    var deduction3 = parseInt(self.INP_008());
                    var incomeTax;
                    if (payOption == 2) {
                        incomeTax = totalPaymentMoney * 20.42 / 100;
                    }
                    else {
                        switch (true) {
                            case (1000 <= totalPaymentMoney) && (totalPaymentMoney < 1950000):
                                incomeTax = totalPaymentMoney * 5 / 100 - 0;
                                break;
                            case (1950000 <= totalPaymentMoney) && (totalPaymentMoney < 3300000):
                                incomeTax = totalPaymentMoney * 10 / 100 - 97500;
                                break;
                            case (3300000 <= totalPaymentMoney) && (totalPaymentMoney < 6950000):
                                incomeTax = totalPaymentMoney * 20 / 100 - 427500;
                                break;
                            case (6950000 <= totalPaymentMoney) && (totalPaymentMoney < 9000000):
                                incomeTax = totalPaymentMoney * 23 / 100 - 636000;
                                break;
                            case (9000000 <= totalPaymentMoney) && (totalPaymentMoney < 18000000):
                                incomeTax = totalPaymentMoney * 33 / 100 - 1536000;
                                break;
                            case (18000000 <= totalPaymentMoney) && (totalPaymentMoney < 40000000):
                                incomeTax = totalPaymentMoney * 40 / 100 - 2796000;
                                break;
                            case (40000000 <= totalPaymentMoney):
                                incomeTax = totalPaymentMoney * 45 / 100 - 4796000;
                                break;
                            default:
                                incomeTax = totalPaymentMoney * 0 - 0;
                                break;
                        }
                    }
                    var cityTax = (tax * 6 / 100) * 90 / 100;
                    var prefectureTax = (tax * 4 / 100) * 90 / 100;
                    var totalDeclarationMoney = parseInt((deduction1 ? deduction1 : 0) +
                        (deduction2 ? deduction2 : 0) +
                        (deduction3 ? deduction3 : 0) +
                        (incomeTax ? incomeTax : 0) +
                        (cityTax ? cityTax : 0) +
                        (prefectureTax ? prefectureTax : 0));
                    self.INP_009(incomeTax);
                    self.INP_010(cityTax);
                    self.INP_011(prefectureTax);
                    self.retirementPaymentInfoCurrent().totalDeclarationMoney(totalDeclarationMoney);
                    self.retirementPaymentInfoCurrent().actualRecieveMoney(totalPaymentMoney - totalDeclarationMoney);
                };
                ScreenModel.prototype.openDialog = function () {
                    nts.uk.ui.windows.sub.modal('/view/qrm/001/b/index.xhtml', { title: '入力欄の背景色について', dialogClass: "no-close" });
                };
                ScreenModel.prototype.createCommand = function () {
                    var self = this;
                    var command = {
                        personId: self.retirementPaymentKeyCurrent().personId(),
                        payDate: self.isUpdate() ? self.SEL_007() : self.INP_014(),
                        trialPeriodSet: self.SEL_001(),
                        exclusionYears: self.INP_002(),
                        additionalBoardYears: self.INP_003(),
                        boardYears: self.INP_004(),
                        totalPaymentMoney: self.INP_005(),
                        deduction1Money: self.INP_006(),
                        deduction2Money: self.INP_007(),
                        deduction3Money: self.INP_008(),
                        retirementPayOption: self.SEL_004(),
                        taxCalculationMethod: self.SEL_005(),
                        incomeTaxMoney: self.INP_009(),
                        cityTaxMoney: self.INP_010(),
                        prefectureTaxMoney: self.INP_011(),
                        totalDeclarationMoney: self.retirementPaymentInfoCurrent().totalDeclarationMoney(),
                        actualRecieveMoney: self.retirementPaymentInfoCurrent().actualRecieveMoney(),
                        bankTransferOption1: self.transferBankMoneyCurrent()[0].optionSet(),
                        Option1Money: self.transferBankMoneyCurrent()[0].money(),
                        bankTransferOption2: self.transferBankMoneyCurrent()[1].optionSet(),
                        Option2Money: self.transferBankMoneyCurrent()[1].money(),
                        bankTransferOption3: self.transferBankMoneyCurrent()[2].optionSet(),
                        Option3Money: self.transferBankMoneyCurrent()[2].money(),
                        bankTransferOption4: self.transferBankMoneyCurrent()[3].optionSet(),
                        Option4Money: self.transferBankMoneyCurrent()[3].money(),
                        bankTransferOption5: self.transferBankMoneyCurrent()[4].optionSet(),
                        Option5Money: self.transferBankMoneyCurrent()[4].money(),
                        withholdingMeno: self.retirementPaymentInfoCurrent().withholdingMeno(),
                        statementMemo: self.retirementPaymentInfoCurrent().statementMemo()
                    };
                    return command;
                };
                ScreenModel.prototype.getRetirementPaymentByPersonId = function (personId) {
                    var self = this;
                    var dfd = $.Deferred();
                    qrm001.a.service.getRetirementPaymentList(personId).done(function (data) {
                        if (!data.length) {
                            self.isUpdate(false);
                            self.retirementPaymentKeyList([new RetirementPaymentKey(personId, '')]);
                            self.retirementPaymentInfoCurrent(new RetirementPaymentInfo(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, null));
                            self.transferBankMoneyCurrent([
                                new TransferBankMoney(0, null), new TransferBankMoney(0, null), new TransferBankMoney(0, null), new TransferBankMoney(0, null), new TransferBankMoney(0, null)
                            ]);
                            self.SEL_007('');
                            self.INP_014('');
                            self.SEL_001(0);
                            self.INP_002(0);
                            self.INP_003(0);
                            self.INP_004(0);
                            self.INP_005(0);
                            self.INP_006(0);
                            self.INP_007(0);
                            self.INP_008(0);
                            self.SEL_004(0);
                            self.SEL_005(0);
                            self.INP_009(0);
                            self.INP_010(0);
                            self.INP_011(0);
                            self.SEL_005.subscribe(function (value) {
                                if (!value) {
                                    self.autoCaculator();
                                    $(".caculator").css('background-color', '#ffc000');
                                }
                                else
                                    $(".caculator").css('background-color', '#cee6ff');
                            });
                            self.INP_005.subscribe(function (valueinp5) { if (!self.SEL_005()) {
                                self.autoCaculator();
                            } });
                            self.INP_006.subscribe(function (valueinp6) { if (!self.SEL_005()) {
                                self.autoCaculator();
                            } });
                            self.INP_007.subscribe(function (valueinp7) { if (!self.SEL_005()) {
                                self.autoCaculator();
                            } });
                            self.INP_008.subscribe(function (valueinp8) { if (!self.SEL_005()) {
                                self.autoCaculator();
                            } });
                            self.SEL_004.subscribe(function (valuesel4) { if (!self.SEL_005()) {
                                self.autoCaculator();
                            } });
                            self.selectList10(self.refreshSelect(self.fullSetSelect, self.transferBankMoneyCurrent, 5));
                        }
                        else {
                            self.isUpdate(true);
                            var length_1 = self.retirementPaymentKeyList().length;
                            self.retirementPaymentInfoList.removeAll();
                            self.transferBankMoneyList.removeAll();
                            data.forEach(function (item) {
                                self.retirementPaymentInfoList.push(new RetirementPaymentInfo(item.trialPeriodSet, item.exclusionYears, item.additionalBoardYears, item.boardYears, item.totalPaymentMoney, item.deduction1Money, item.deduction2Money, item.deduction3Money, item.retirementPayOption, item.taxCalculationMethod, item.incomeTaxMoney, item.cityTaxMoney, item.prefectureTaxMoney, item.totalDeclarationMoney, item.actualRecieveMoney, item.withholdingMeno, item.statementMemo));
                                self.transferBankMoneyList.push([
                                    new TransferBankMoney(item.bankTransferOption1, item.option1Money),
                                    new TransferBankMoney(item.bankTransferOption2, item.option2Money),
                                    new TransferBankMoney(item.bankTransferOption3, item.option3Money),
                                    new TransferBankMoney(item.bankTransferOption4, item.option4Money),
                                    new TransferBankMoney(item.bankTransferOption5, item.option5Money)]);
                                self.retirementPaymentKeyList.push(new RetirementPaymentKey(item.personId, item.payDate));
                            });
                            for (var i = 0; i < length_1; i++) {
                                self.retirementPaymentKeyList.remove(self.retirementPaymentKeyList()[i]);
                            }
                            self.SEL_007(_.first(self.retirementPaymentKeyList()).payDate);
                        }
                        dfd.resolve();
                    }).fail(function (res) {
                        dfd.resolve();
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.saveData = function (isUpdate) {
                    var self = this;
                    var dfd = $.Deferred();
                    var command = self.createCommand();
                    if (isUpdate) {
                        nts.uk.ui.dialog.confirm("Do you want to Update ?").ifYes(function () {
                            qrm001.a.service.updateRetirementPaymentInfo(command).done(function (data) {
                                nts.uk.ui.dialog.alert("Update Success");
                                dfd.resolve();
                            }).fail(function (res) {
                                nts.uk.ui.dialog.alert("Update Fail");
                                dfd.resolve();
                            });
                        }).ifNo(function () { });
                    }
                    else {
                        nts.uk.ui.dialog.confirm("Do you want to Register ?").ifYes(function () {
                            qrm001.a.service.registerRetirementPaymentInfo(command).done(function (data) {
                                nts.uk.ui.dialog.alert("Register Success");
                                dfd.resolve();
                            }).fail(function (res) {
                                nts.uk.ui.dialog.alert("Register Fail");
                                dfd.resolve();
                            });
                        }).ifNo(function () { });
                    }
                    return dfd.promise();
                };
                ScreenModel.prototype.deleteData = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    var command = self.createCommand();
                    nts.uk.ui.dialog.confirm("Do you want to Remove ?").ifYes(function () {
                        qrm001.a.service.removeRetirementPaymentInfo(command).done(function (data) {
                            nts.uk.ui.dialog.alert("Remove Success");
                            dfd.resolve();
                        }).fail(function (res) {
                            nts.uk.ui.dialog.alert("Remove Success");
                            dfd.resolve();
                        });
                    }).ifCancel(function () { });
                    return dfd.promise();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var EmployeeInfo = (function () {
                function EmployeeInfo(personId, name) {
                    this.personId = ko.observable(personId);
                    this.name = ko.observable(name);
                }
                return EmployeeInfo;
            }());
            var BankTransferInfo = (function () {
                function BankTransferInfo(bankName, branchName, accountNumber) {
                    this.bankName = ko.observable(bankName);
                    this.branchName = ko.observable(branchName);
                    this.accountNumber = ko.observable(accountNumber);
                }
                return BankTransferInfo;
            }());
            var RetirementPaymentKey = (function () {
                function RetirementPaymentKey(personId, payDate) {
                    this.personId = ko.observable(personId);
                    this.payDate = payDate;
                }
                return RetirementPaymentKey;
            }());
            var RetirementPaymentInfo = (function () {
                function RetirementPaymentInfo(trialPeriodSet, exclusionYears, additionalBoardYears, boardYears, totalPaymentMoney, deduction1Money, deduction2Money, deduction3Money, retirementPayOption, taxCalculationMethod, incomeTaxMoney, cityTaxMoney, prefectureTaxMoney, totalDeclarationMoney, actualRecieveMoney, withholdingMeno, statementMemo) {
                    var self = this;
                    self.trialPeriodSet = ko.observable(trialPeriodSet);
                    self.exclusionYears = ko.observable(exclusionYears);
                    self.additionalBoardYears = ko.observable(additionalBoardYears);
                    self.boardYears = ko.observable(boardYears);
                    self.totalPaymentMoney = ko.observable(totalPaymentMoney);
                    self.deduction1Money = ko.observable(deduction1Money);
                    self.deduction2Money = ko.observable(deduction2Money);
                    self.deduction3Money = ko.observable(deduction3Money);
                    self.retirementPayOption = ko.observable(retirementPayOption);
                    self.taxCalculationMethod = ko.observable(taxCalculationMethod);
                    self.incomeTaxMoney = ko.observable(incomeTaxMoney);
                    self.cityTaxMoney = ko.observable(cityTaxMoney);
                    self.prefectureTaxMoney = ko.observable(prefectureTaxMoney);
                    self.totalDeclarationMoney = ko.observable(totalDeclarationMoney);
                    self.actualRecieveMoney = ko.observable(actualRecieveMoney);
                    self.withholdingMeno = ko.observable(withholdingMeno);
                    self.statementMemo = ko.observable(statementMemo);
                }
                return RetirementPaymentInfo;
            }());
            var TransferBankMoney = (function () {
                function TransferBankMoney(optionSet, money) {
                    this.optionSet = ko.observable(optionSet);
                    this.money = ko.observable(money);
                }
                return TransferBankMoney;
            }());
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qrm001.a || (qrm001.a = {}));
})(qrm001 || (qrm001 = {}));
