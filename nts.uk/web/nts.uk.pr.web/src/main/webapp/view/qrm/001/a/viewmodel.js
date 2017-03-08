var qrm001;
(function (qrm001) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    var dfd = $.Deferred();
                    //Retirement Payment Process
                    self.bankTransferList = ko.observableArray([
                        new PersonBankAccount('bank1', 'branch1', '0001'),
                        new PersonBankAccount('bank2', 'branch2', '0002'),
                        new PersonBankAccount('bank3', 'branch3', '0003'),
                        new PersonBankAccount('bank4', 'branch4', '0004'),
                        new PersonBankAccount('bank5', 'branch5', '0005')
                    ]);
                    self.fullSetSelect = ko.observableArray([
                        { code: 0, name: '使用しない' },
                        { code: 1, name: '支給1' },
                        { code: 2, name: '支給2' },
                        { code: 3, name: '支給3' },
                        { code: 4, name: '支給4' },
                        { code: 5, name: '支給5' }]);
                    self.isUpdate = ko.observable(false);
                    self.retirementPaymentKeyList = ko.observableArray([new RetirementPaymentKey("A0001", "2016-12-28", 12)]);
                    self.retirementPaymentList = ko.observableArray([]);
                    self.retirementPaymentCurrent = ko.observable(new RetirementPayment(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
                    self.select6List = ko.observableArray(self.refreshSelect(self.fullSetSelect, self.retirementPaymentCurrent, 5));
                    self.select6Code = ko.observableArray([ko.observable(1), ko.observable(2), ko.observable(3), ko.observable(4), ko.observable(5)]);
                    self.select6Code().forEach(function (item, index) {
                        self.select6Code()[index].subscribe(function (value) {
                            self.retirementPaymentCurrent()["optionSet" + (index + 1)](value);
                            self.retirementPaymentCurrent()["money" + (index + 1)](value ? value : null);
                            self.select6List(self.refreshSelect(self.fullSetSelect, self.retirementPaymentCurrent, 5));
                        });
                    });
                    //Employee Process 
                    self.employeeList = ko.observableArray([
                        new EmployeeInfo('A0001', 'A'),
                        new EmployeeInfo('A0002', 'B'),
                        new EmployeeInfo('A0003', 'C')
                    ]);
                    self.personCom = ko.observable(new PersonCom('A', 0, 0, 4, 4, 9));
                    self.date = ko.observable("2016-12-28");
                    self.currentEmployeeIndex = ko.observable(0);
                    self.currentEmployeeIndexOld = ko.observable(0);
                    self.currentEmployeeCode = ko.observable('A0001');
                    self.currentEmployee = ko.observable(self.employeeList()[self.currentEmployeeIndex()]);
                    self.previous = ko.observable(false);
                    self.next = ko.observable(true);
                    self.currentEmployeeIndex.subscribe(function (value) {
                        self.previous((value === 0) ? false : true);
                        self.next((value === (self.employeeList().length - 1)) ? false : true);
                        self.currentEmployee(self.employeeList()[value]);
                        self.currentEmployeeCode(self.currentEmployee().personId());
                        if (self.dirty.isDirty()) {
                            nts.uk.ui.dialog.confirm("Do you want to say \"Hello World!\"?").ifYes(function () {
                                self.getRetirementPaymentByPersonId(self.currentEmployeeCode()).done(function () {
                                    dfd.resolve();
                                }).fail(function () {
                                });
                            }).ifNo(function () {
                            });
                        }
                        else {
                            self.getRetirementPaymentByPersonId(self.currentEmployeeCode()).done(function () {
                                dfd.resolve();
                            }).fail(function () {
                            });
                        }
                    });
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.getRetirementPaymentByPersonId(self.currentEmployeeCode()).done(function () {
                        $(document).delegate("#combo-box7", "igcomboselectionchanging", function (evt, ui) {
                            if (self.dirty.isDirty()) {
                                nts.uk.ui.dialog.confirm("Do you want to say \"Hello World!\"?").ifYes(function () {
                                    self.date(ui.items[0].data.payDate);
                                    self.retirementPaymentCurrent(_.find(self.retirementPaymentList(), function (o) { return o.payDate() == self.date(); }));
                                    self.dirty = new nts.uk.ui.DirtyChecker(self.retirementPaymentCurrent);
                                }).ifNo(function () {
                                    self.date(ui.currentItems[0].data.payDate);
                                    self.retirementPaymentCurrent(_.find(self.retirementPaymentList(), function (o) { return o.payDate() == self.date(); }));
                                });
                            }
                            else {
                                self.date(ui.items[0].data.payDate);
                                self.retirementPaymentCurrent(_.find(self.retirementPaymentList(), function (o) { return o.payDate() == self.date(); }));
                                self.dirty = new nts.uk.ui.DirtyChecker(self.retirementPaymentCurrent);
                            }
                        });
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
                ScreenModel.prototype.filter = function (dataset, currentItem, index) {
                    var self = this;
                    return _.filter(dataset(), function (o) {
                        return o.code == 0 ||
                            o.code == currentItem()["bankTransferOption" + (index + 1)]() ||
                            o.code !== currentItem().bankTransferOption1() &&
                                o.code !== currentItem().bankTransferOption2() &&
                                o.code !== currentItem().bankTransferOption3() &&
                                o.code !== currentItem().bankTransferOption4() &&
                                o.code !== currentItem().bankTransferOption5();
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
                ScreenModel.prototype.openDialog = function () {
                    var self = this;
                    console.log(self.retirementPaymentCurrent());
                    nts.uk.ui.windows.sub.modal('/view/qrm/001/b/index.xhtml', { title: '入力欄の背景色について', dialogClass: "no-close" });
                };
                ScreenModel.autoCaculator = function (retirementPayment) {
                    var self = retirementPayment;
                    var year; // LBL003
                    var reduction; // Khau tru
                    var totalPaymentMoney = parseInt(self.totalPaymentMoney());
                    var rs = (totalPaymentMoney - reduction) / 2;
                    var tax = (rs < 0) ? rs : 0; // Thue nghi viec
                    var payOption = self.retirementPayOption();
                    var deduction1 = parseInt(self.deduction1Money());
                    var deduction2 = parseInt(self.deduction2Money());
                    var deduction3 = parseInt(self.deduction3Money());
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
                    self.incomeTaxMoney(incomeTax);
                    self.cityTaxMoney(cityTax);
                    self.prefectureTaxMoney(prefectureTax);
                    self.totalDeclarationMoney(totalDeclarationMoney);
                    self.actualRecieveMoney(totalPaymentMoney - totalDeclarationMoney);
                };
                ScreenModel.manualCaculator = function (retirementPayment) {
                    var self = retirementPayment;
                    var totalPaymentMoney = parseInt(self.totalPaymentMoney());
                    var deduction1 = parseInt(self.deduction1Money());
                    var deduction2 = parseInt(self.deduction2Money());
                    var deduction3 = parseInt(self.deduction3Money());
                    var incomeTax = parseInt(self.incomeTaxMoney());
                    var cityTax = parseInt(self.cityTaxMoney());
                    var prefectureTax = parseInt(self.prefectureTaxMoney());
                    var totalDeclarationMoney = (deduction1 ? deduction1 : 0) +
                        (deduction2 ? deduction2 : 0) +
                        (deduction3 ? deduction3 : 0) +
                        (incomeTax ? incomeTax : 0) +
                        (cityTax ? cityTax : 0) +
                        (prefectureTax ? prefectureTax : 0);
                    self.totalDeclarationMoney(totalDeclarationMoney);
                    self.actualRecieveMoney(totalPaymentMoney - totalDeclarationMoney);
                };
                ScreenModel.prototype.getRetirementPaymentByPersonId = function (personId) {
                    var self = this;
                    var dfd = $.Deferred();
                    qrm001.a.service.getRetirementPaymentList(personId).done(function (data) {
                        if (!data.length) {
                            self.isUpdate(false);
                            self.retirementPaymentCurrent(new RetirementPayment(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
                            self.dirty = new nts.uk.ui.DirtyChecker(self.retirementPaymentCurrent);
                        }
                        else {
                            self.isUpdate(true);
                            self.retirementPaymentKeyList.removeAll();
                            self.retirementPaymentList.removeAll();
                            data.forEach(function (item) {
                                self.retirementPaymentList.push(new RetirementPayment(item.personId, item.payDate, item.trialPeriodSet, item.exclusionYears, item.additionalBoardYears, item.boardYears, item.totalPaymentMoney, item.deduction1Money, item.deduction2Money, item.deduction3Money, item.retirementPayOption, item.taxCalculationMethod, item.incomeTaxMoney, item.cityTaxMoney, item.prefectureTaxMoney, item.totalDeclarationMoney, item.actualRecieveMoney, item.bankTransferOption1, item.option1Money, item.bankTransferOption2, item.option2Money, item.bankTransferOption3, item.option3Money, item.bankTransferOption4, item.option4Money, item.bankTransferOption5, item.option5Money, item.withholdingMeno, item.statementMemo));
                                self.retirementPaymentKeyList.push(new RetirementPaymentKey(item.personId, item.payDate, item.totalDeclarationMoney));
                            });
                            self.retirementPaymentCurrent(_.first(self.retirementPaymentList()));
                            self.select6List(self.refreshSelect(self.fullSetSelect, self.retirementPaymentCurrent, 5));
                            self.select6Code().forEach(function (item, index) {
                                self.select6Code()[index](self.retirementPaymentCurrent()["bankTransferOption" + (index + 1)]());
                            });
                            self.date(_.first(self.retirementPaymentList()).payDate);
                            self.dirty = new nts.uk.ui.DirtyChecker(self.retirementPaymentCurrent);
                        }
                        dfd.resolve();
                    }).fail(function (res) {
                        dfd.resolve();
                    });
                    return dfd.promise();
                };
                ScreenModel.changeColor = function (value, retirementPayment) {
                    var self = this;
                    if (!value) {
                        self.autoCaculator(retirementPayment);
                        $(".caculator").css('background-color', '#ffc000');
                    }
                    else
                        $(".caculator").css('background-color', '#cee6ff');
                };
                ScreenModel.prototype.saveData = function (isUpdate) {
                    var self = this;
                    var dfd = $.Deferred();
                    self.retirementPaymentCurrent().payDate(self.date());
                    var command = ko.mapping.toJS(self.retirementPaymentCurrent());
                    if (isUpdate) {
                        qrm001.a.service.updateRetirementPaymentInfo(command).done(function (data) {
                            nts.uk.ui.dialog.alert("Update Success");
                            dfd.resolve();
                        }).fail(function (res) {
                            nts.uk.ui.dialog.alert("Update Fail");
                            dfd.resolve();
                        });
                    }
                    else {
                        qrm001.a.service.registerRetirementPaymentInfo(command).done(function (data) {
                            nts.uk.ui.dialog.alert("Register Success");
                            dfd.resolve();
                        }).fail(function (res) {
                            nts.uk.ui.dialog.alert("Register Fail");
                            dfd.resolve();
                        });
                    }
                    return dfd.promise();
                };
                ScreenModel.prototype.deleteData = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.retirementPaymentCurrent().payDate(self.date());
                    var command = ko.mapping.toJS(self.retirementPaymentCurrent());
                    if (self.dirty.isDirty()) {
                        nts.uk.ui.dialog.confirm("Do you want to Remove ?").ifYes(function () {
                            qrm001.a.service.removeRetirementPaymentInfo(command).done(function (data) {
                                nts.uk.ui.dialog.alert("Remove Success");
                                dfd.resolve();
                            }).fail(function (res) {
                                nts.uk.ui.dialog.alert("Remove Fail");
                                dfd.resolve();
                            });
                        }).ifNo(function () { });
                    }
                    else {
                        qrm001.a.service.removeRetirementPaymentInfo(command).done(function (data) {
                            nts.uk.ui.dialog.alert("Remove Success");
                            dfd.resolve();
                        }).fail(function (res) {
                            nts.uk.ui.dialog.alert("Remove Fail");
                            dfd.resolve();
                        });
                    }
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
            var PersonCom = (function () {
                function PersonCom(scd, startDate, endDate, adoptType, quitFiringAtr, quitFiringReason_atr) {
                    this.scd = ko.observable(scd);
                    this.startDate = ko.observable(startDate);
                    this.endDate = ko.observable(endDate);
                    this.adoptType = ko.observable(adoptType);
                    this.quitFiringAtr = ko.observable(quitFiringAtr);
                    this.quitFiringReason_atr = ko.observable(quitFiringReason_atr);
                }
                return PersonCom;
            }());
            var PersonBankAccount = (function () {
                function PersonBankAccount(bankName, branchName, accountNumber) {
                    this.bankName = ko.observable(bankName);
                    this.branchName = ko.observable(branchName);
                    this.accountNumber = ko.observable(accountNumber);
                }
                return PersonBankAccount;
            }());
            var RetirementPaymentKey = (function () {
                function RetirementPaymentKey(personId, payDate, totalDeclarationMoney) {
                    this.personId = ko.observable(personId);
                    this.payDate = payDate;
                    this.totalDeclarationMoney = ko.observable(totalDeclarationMoney);
                }
                return RetirementPaymentKey;
            }());
            var RetirementPayment = (function () {
                function RetirementPayment(personId, payDate, trialPeriodSet, exclusionYears, additionalBoardYears, boardYears, totalPaymentMoney, deduction1Money, deduction2Money, deduction3Money, retirementPayOption, taxCalculationMethod, incomeTaxMoney, cityTaxMoney, prefectureTaxMoney, totalDeclarationMoney, actualRecieveMoney, bankTransferOption1, option1Money, bankTransferOption2, option2Money, bankTransferOption3, option3Money, bankTransferOption4, option4Money, bankTransferOption5, option5Money, withholdingMeno, statementMemo) {
                    var self = this;
                    self.personId = ko.observable(personId);
                    self.payDate = ko.observable(payDate);
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
                    self.bankTransferOption1 = ko.observable(bankTransferOption1);
                    self.option1Money = ko.observable(self.bankTransferOption1() ? option1Money : null);
                    self.bankTransferOption2 = ko.observable(bankTransferOption2);
                    self.option2Money = ko.observable(self.bankTransferOption2() ? option2Money : null);
                    self.bankTransferOption3 = ko.observable(bankTransferOption3);
                    self.option3Money = ko.observable(self.bankTransferOption3() ? option3Money : null);
                    self.bankTransferOption4 = ko.observable(bankTransferOption4);
                    self.option4Money = ko.observable(self.bankTransferOption4() ? option4Money : null);
                    self.bankTransferOption5 = ko.observable(bankTransferOption5);
                    self.option5Money = ko.observable(self.bankTransferOption5() ? option5Money : null);
                    self.withholdingMeno = ko.observable(withholdingMeno);
                    self.statementMemo = ko.observable(statementMemo);
                    qrm001.a.viewmodel.ScreenModel.autoCaculator(self);
                    qrm001.a.viewmodel.ScreenModel.changeColor(self.taxCalculationMethod(), self);
                    self.taxCalculationMethod.subscribe(function (value) { qrm001.a.viewmodel.ScreenModel.changeColor(value, self); });
                    self.totalPaymentMoney.subscribe(function (valueinp5) { if (!self.taxCalculationMethod()) {
                        qrm001.a.viewmodel.ScreenModel.autoCaculator(self);
                    } });
                    self.deduction1Money.subscribe(function (valueinp6) { if (!self.taxCalculationMethod()) {
                        qrm001.a.viewmodel.ScreenModel.autoCaculator(self);
                    } });
                    self.deduction2Money.subscribe(function (valueinp7) { if (!self.taxCalculationMethod()) {
                        qrm001.a.viewmodel.ScreenModel.autoCaculator(self);
                    } });
                    self.deduction3Money.subscribe(function (valueinp8) { if (!self.taxCalculationMethod()) {
                        qrm001.a.viewmodel.ScreenModel.autoCaculator(self);
                    } });
                    self.retirementPayOption.subscribe(function (valuesel4) { if (!self.taxCalculationMethod()) {
                        qrm001.a.viewmodel.ScreenModel.autoCaculator(self);
                    } });
                }
                return RetirementPayment;
            }());
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qrm001.a || (qrm001.a = {}));
})(qrm001 || (qrm001 = {}));
