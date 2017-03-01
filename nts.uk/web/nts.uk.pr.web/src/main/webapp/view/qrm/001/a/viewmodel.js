var qrm001;
(function (qrm001) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.employeeList = ko.observableArray([
                        new EmployeeInfo('0001', 'A'),
                        new EmployeeInfo('0002', 'B'),
                        new EmployeeInfo('0003', 'C')
                    ]);
                    self.value = ko.observable(1);
                    self.value.subscribe(function (value) {
                        console.log(value);
                    });
                    self.payDateList = ko.observableArray([]);
                    self.isUpdate = false;
                    self.date = ko.observable("2016/12/01");
                    self.currentEmployeeCode = ko.observable(0);
                    self.currentEmployeeName = ko.observable('0001');
                    self.currentEmployee = ko.observable(self.employeeList()[self.currentEmployeeCode()]);
                    self.previous = ko.observable(false);
                    self.next = ko.observable(true);
                    self.currentEmployeeCode.subscribe(function (value) {
                        self.previous((value === 0) ? false : true);
                        self.next((value === (self.employeeList().length - 1)) ? false : true);
                        self.currentEmployee(self.employeeList()[value]);
                        self.currentEmployeeName(self.currentEmployee().personId());
                    });
                    self.retirementPaymentInfo = ko.observable(new RetirementPaymentInfo('0001', '2', '3', '4', 5, '6', '7', '8', '9', '0', '1', 2, 0, '4', '5', '6', 7, 8, '9', '1'));
                    self.fullSetSelect = ko.observableArray([
                        { name: '使用しない' },
                        { name: '支給1' },
                        { name: '支給2' },
                        { name: '支給3' },
                        { name: '支給4' },
                        { name: '支給5' }]);
                    self.fullPaytransfer = ko.observableArray([
                        new BankDataSet('支給1', 'bank1', 'branch1', '0001', '1'),
                        new BankDataSet('使用しない', 'bank2', 'branch2', '0002', '2'),
                        new BankDataSet('使用しない', 'bank3', 'branch3', '0003', '3'),
                        new BankDataSet('使用しない', 'bank4', 'branch4', '0004', '4'),
                        new BankDataSet('使用しない', 'bank5', 'branch5', '0005', '5')
                    ]);
                    self.currentPaytransfer = ko.observableArray([
                        new BankDataSet('支給1', 'bank1', 'branch1', '0001', '1'),
                        new BankDataSet('使用しない', 'bank2', 'branch2', '0002', '2'),
                        new BankDataSet('使用しない', 'bank3', 'branch3', '0003', '3'),
                        new BankDataSet('使用しない', 'bank4', 'branch4', '0004', '4'),
                        new BankDataSet('使用しない', 'bank5', 'branch5', '0005', '5')
                    ]);
                    self.currentPaytransfer.subscribe(function (value) { console.log(value); });
                    self.selectList10 = ko.observableArray(self.refreshSelect(self.fullSetSelect, self.currentPaytransfer, 5));
                    self.currentSetSelect = ko.observableArray(_.filter(self.fullSetSelect(), function (o) {
                        return o.name !== self.currentPaytransfer()[0].dataSetName() &&
                            o.name !== self.currentPaytransfer()[1].dataSetName() &&
                            o.name !== self.currentPaytransfer()[2].dataSetName() &&
                            o.name !== self.currentPaytransfer()[3].dataSetName();
                    }));
                    self.currentPaytransfer().forEach(function (item) {
                        item.dataSetName.subscribe(function (value) {
                            if ((item.dataSetName() == '使用しない')) {
                                self.setDataRow(item, self.fullPaytransfer, -1);
                                self.selectList10(self.refreshSelect(self.fullSetSelect, self.currentPaytransfer, 5));
                            }
                            else {
                                var index = parseInt(item.dataSetName().substring(2, 3)) - 1;
                                self.setDataRow(item, self.fullPaytransfer, index);
                                self.selectList10(self.refreshSelect(self.fullSetSelect, self.currentPaytransfer, 5));
                            }
                        });
                        item.transferMoney.subscribe(function (value) {
                            if (!(item.dataSetName() == '使用しない')) {
                                self.fullPaytransfer()[parseInt(item.dataSetName().substring(2, 3)) - 1].transferMoney(value);
                            }
                        });
                    });
                    self.retirementPaymentInfo.subscribe(function () {
                        if (!self.retirementPaymentInfo().taxCalculationMethod()) {
                            self.autoCaculator();
                            $(".caculator").css('background-color', '#ffc000');
                        }
                        else
                            $(".caculator").css('background-color', '#cee6ff');
                    });
                    self.retirementPaymentInfo().totalPaymentMoney.subscribe(function (value) {
                        console.log(value);
                    });
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    var command = { personId: "1", dateTime: self.date() };
                    qrm001.a.service.getRetirementPaymentList('A0001').done(function (data) {
                        if (!data) {
                            self.isUpdate = false;
                        }
                        else {
                            self.payDateList.removeAll();
                            data.forEach(function (item) {
                                self.payDateList.push(new RetirementPaymentInfo(item.companyCode, item.personId, item.payDate, item.trialPeriodSet, item.exclusionYears, item.additionalBoardYears, item.boardYears, item.totalPaymentMoney, item.deduction1Money, item.deduction2Money, item.deduction3Money, item.retirementPayOption, item.taxCalculationMethod, item.incomeTaxMoney, item.cityTaxMoney, item.prefectureTaxMoney, item.totalDeclarationMoney, item.actualRecieveMoney, item.withholdingMeno, item.statementMemo));
                            });
                            self.date(_.first(self.payDateList()).payDate);
                            self.retirementPaymentInfo(_.first(self.payDateList()));
                            self.date.subscribe(function (value) {
                                var index = $("#combo-box7").igCombo("activeIndex");
                                self.retirementPaymentInfo(self.payDateList()[index]);
                            });
                            self.isUpdate = true;
                        }
                        dfd.resolve();
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.next_emp = function () {
                    var self = this;
                    self.currentEmployeeCode(self.currentEmployeeCode() + 1);
                };
                ScreenModel.prototype.previous_emp = function () {
                    var self = this;
                    self.currentEmployeeCode(self.currentEmployeeCode() - 1);
                };
                ScreenModel.prototype.loadData = function (src, dst) {
                    ko.utils.arrayForEach(src(), function (item) {
                        dst.push(item);
                    });
                };
                ScreenModel.prototype.filter = function (dataset, currentSet, index) {
                    var self = this;
                    return _.filter(dataset(), function (o) {
                        return o.name == '使用しない' ||
                            o.name == currentSet()[index].dataSetName() ||
                            o.name !== currentSet()[0].dataSetName() &&
                                o.name !== currentSet()[1].dataSetName() &&
                                o.name !== currentSet()[2].dataSetName() &&
                                o.name !== currentSet()[3].dataSetName() &&
                                o.name !== currentSet()[4].dataSetName();
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
                ScreenModel.prototype.setDataRow = function (item, dataSet, index) {
                    if (0 <= index) {
                        item.bankName(dataSet()[index].bankName());
                        item.branchName(dataSet()[index].branchName());
                        item.accountNumber(dataSet()[index].accountNumber());
                        item.transferMoney(dataSet()[index].transferMoney());
                    }
                    else {
                        item.bankName(null);
                        item.branchName(null);
                        item.accountNumber(null);
                        item.transferMoney(null);
                    }
                };
                ScreenModel.prototype.setPaytransfer = function (variable, dataSet) {
                    for (var i = 0; i < 5; i++) {
                        variable.push(new BankDataSet(dataSet["dataSetName" + 1], dataSet["bankName" + 1], dataSet["branchName" + 1], dataSet["accountNumber" + 1], dataSet["transferMoney" + 1]));
                    }
                };
                ScreenModel.prototype.manualCaculator = function () {
                    var self = this;
                    var totalPaymentMoney = parseInt(self.retirementPaymentInfo().totalPaymentMoney());
                    var deduction1 = parseInt(self.retirementPaymentInfo().deduction1Money());
                    var deduction2 = parseInt(self.retirementPaymentInfo().deduction2Money());
                    var deduction3 = parseInt(self.retirementPaymentInfo().deduction3Money());
                    var incomeTax = parseInt(self.retirementPaymentInfo().incomeTaxMoney());
                    var cityTax = parseInt(self.retirementPaymentInfo().cityTaxMoney());
                    var prefectureTax = parseInt(self.retirementPaymentInfo().prefectureTaxMoney());
                    var totalDeclarationMoney = (deduction1 ? deduction1 : 0) +
                        (deduction2 ? deduction2 : 0) +
                        (deduction3 ? deduction3 : 0) +
                        (incomeTax ? incomeTax : 0) +
                        (cityTax ? cityTax : 0) +
                        (prefectureTax ? prefectureTax : 0);
                    self.retirementPaymentInfo().totalDeclarationMoney(totalDeclarationMoney);
                    self.retirementPaymentInfo().actualRecieveMoney(totalPaymentMoney - totalDeclarationMoney);
                };
                ScreenModel.prototype.autoCaculator = function () {
                    var self = this;
                    var year; // LBL003
                    var reduction; // Khau tru
                    console.log(self.retirementPaymentInfo);
                    var totalPaymentMoney = parseInt(self.retirementPaymentInfo().totalPaymentMoney());
                    var rs = (totalPaymentMoney - reduction) / 2;
                    var tax = (rs < 0) ? rs : 0; // Thue nghi viec
                    var payOption = self.retirementPaymentInfo().retirementPayOption();
                    var deduction1 = parseInt(self.retirementPaymentInfo().deduction1Money());
                    var deduction2 = parseInt(self.retirementPaymentInfo().deduction2Money());
                    var deduction3 = parseInt(self.retirementPaymentInfo().deduction3Money());
                    var incomeTax = parseInt(self.retirementPaymentInfo().incomeTaxMoney());
                    if (payOption == 2) {
                        incomeTax = totalPaymentMoney * 20.42 / 100;
                    }
                    else if (payOption) {
                    }
                    else {
                    }
                    ;
                    var cityTax = (tax * 6 / 100) * 90 / 100;
                    var prefectureTax = (tax * 4 / 100) * 90 / 100;
                    var totalDeclarationMoney = (deduction1 ? deduction1 : 0) +
                        (deduction2 ? deduction2 : 0) +
                        (deduction3 ? deduction3 : 0) +
                        (incomeTax ? incomeTax : 0) +
                        (cityTax ? cityTax : 0) +
                        (prefectureTax ? prefectureTax : 0);
                    self.retirementPaymentInfo().incomeTaxMoney(incomeTax);
                    self.retirementPaymentInfo().cityTaxMoney(cityTax);
                    self.retirementPaymentInfo().prefectureTaxMoney(prefectureTax);
                    self.retirementPaymentInfo().totalDeclarationMoney(totalDeclarationMoney);
                    self.retirementPaymentInfo().actualRecieveMoney(totalPaymentMoney - totalDeclarationMoney);
                };
                ScreenModel.prototype.openDialog = function () {
                    nts.uk.ui.windows.sub.modal('/view/qrm/001/b/index.xhtml', { title: '入力欄の背景色について', dialogClass: "no-close" });
                };
                ScreenModel.prototype.saveData = function (isUpdate) {
                    var self = this;
                    var dfd = $.Deferred();
                    var command = ko.mapping.toJS(self.retirementPaymentInfo());
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
                };
                ScreenModel.prototype.deleteData = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    var command = ko.mapping.toJS(self.retirementPaymentInfo());
                    nts.uk.ui.dialog.confirm("Do you want to Remove ?").ifYes(function () {
                        qrm001.a.service.removeRetirementPaymentInfo(command).done(function (data) {
                            nts.uk.ui.dialog.alert("Remove Success");
                            dfd.resolve();
                        }).fail(function (res) {
                            nts.uk.ui.dialog.alert("Remove Success");
                            dfd.resolve();
                        });
                    }).ifCancel(function () { });
                };
                ScreenModel.prototype.checkEmpty = function () {
                    var self = this;
                    self.retirementPaymentInfo;
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var BankDataSet = (function () {
                function BankDataSet(dataSetName, bankName, branchName, accountNumber, transferMoney) {
                    this.dataSetName = ko.observable(dataSetName);
                    this.bankName = ko.observable(bankName);
                    this.branchName = ko.observable(branchName);
                    this.accountNumber = ko.observable(accountNumber);
                    this.transferMoney = ko.observable(transferMoney);
                }
                return BankDataSet;
            }());
            var RetirementPaymentInfo = (function () {
                function RetirementPaymentInfo(companyCode, personId, payDate, trialPeriodSet, exclusionYears, additionalBoardYears, boardYears, totalPaymentMoney, deduction1Money, deduction2Money, deduction3Money, retirementPayOption, taxCalculationMethod, incomeTaxMoney, cityTaxMoney, prefectureTaxMoney, totalDeclarationMoney, actualRecieveMoney, withholdingMeno, statementMemo) {
                    var self = this;
                    self.companyCode = ko.observable(companyCode);
                    self.personId = ko.observable(personId);
                    self.payDate = payDate;
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
                    self.totalPaymentMoney.subscribe(function (value) {
                        console.log(value);
                    });
                }
                return RetirementPaymentInfo;
            }());
            var EmployeeInfo = (function () {
                function EmployeeInfo(personId, name) {
                    this.personId = ko.observable(personId);
                    this.name = ko.observable(name);
                }
                return EmployeeInfo;
            }());
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qrm001.a || (qrm001.a = {}));
})(qrm001 || (qrm001 = {}));
