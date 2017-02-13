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
                        { code: '0001' },
                        { code: '0002' },
                        { code: '0003' },
                    ]);
                    self.date = ko.observable(new Date());
                    self.currentEmployeeCode = ko.observable(0);
                    self.currentEmployee = ko.observable(self.employeeList()[self.currentEmployeeCode()].code);
                    self.previous = ko.observable(false);
                    self.next = ko.observable(true);
                    self.currentEmployeeCode.subscribe(function (value) {
                        self.previous((value === 0) ? false : true);
                        self.next((value === (self.employeeList().length - 1)) ? false : true);
                        self.currentEmployee(self.employeeList()[value].code);
                    });
                    self.employeeInfo = ko.observable(new EmployeeInfo('0001', 'asfas', '1', '1', 1, '2', '5', '6', '7', '8', '9', '3', '4', '5', '6', '7', 2, 1, '1', '2', '3', '4', '5', '6'));
                    self.paymentDateProcessingList = ko.observableArray([]);
                    self.selectedPaymentDate = ko.observable(null);
                    self.selectList1 = ko.observableArray([
                        { name: '試用期間なし' },
                        { name: '試用期間あり' }
                    ]);
                    self.selectList4 = ko.observableArray([
                        { name: 'あり（他からの退職金の支払いなし）' },
                        { name: 'あり（他からの退職金の支払いあり）' },
                        { name: '申告書なし' }
                    ]);
                    self.selectList5 = ko.observableArray([
                        { name: '計算しない' },
                        { name: '計算する' }
                    ]);
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
                    self.currentPaytransfer = self.fullPaytransfer;
                    self.currentPaytransfer.subscribe(function (value) { console.log(value); });
                    self.selectList10 = ko.observableArray([
                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 0),
                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 1),
                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 2),
                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 3),
                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 4),
                    ]);
                    console.log(self.selectList10());
                    self.currentSetSelect = ko.observableArray(_.filter(self.fullSetSelect(), function (o) {
                        return o.name !== self.currentPaytransfer()[0].dataSetName() &&
                            o.name !== self.currentPaytransfer()[1].dataSetName() &&
                            o.name !== self.currentPaytransfer()[2].dataSetName() &&
                            o.name !== self.currentPaytransfer()[3].dataSetName();
                    }));
                    console.log(self.currentSetSelect());
                    self.currentPaytransfer().forEach(function (item) {
                        item.dataSetName.subscribe(function (value) {
                            switch (value) {
                                case '支給1':
                                    qrm001.a.viewmodel.ScreenModel.setDataRow(item, self.fullPaytransfer, 0);
                                    console.log(self.currentPaytransfer());
                                    self.selectList10([
                                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 0),
                                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 1),
                                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 2),
                                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 3),
                                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 4),
                                    ]);
                                    console.log(self.selectList10());
                                    console.log(value);
                                    break;
                                case '支給2':
                                    qrm001.a.viewmodel.ScreenModel.setDataRow(item, self.fullPaytransfer, 1);
                                    console.log(self.currentPaytransfer());
                                    self.selectList10([
                                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 0),
                                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 1),
                                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 2),
                                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 3),
                                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 4),
                                    ]);
                                    console.log(self.selectList10());
                                    console.log(value);
                                    break;
                                case '支給3':
                                    qrm001.a.viewmodel.ScreenModel.setDataRow(item, self.fullPaytransfer, 2);
                                    console.log(self.currentPaytransfer());
                                    self.selectList10([
                                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 0),
                                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 1),
                                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 2),
                                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 3),
                                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 4),
                                    ]);
                                    console.log(self.selectList10());
                                    console.log(value);
                                    break;
                                case '支給4':
                                    qrm001.a.viewmodel.ScreenModel.setDataRow(item, self.fullPaytransfer, 3);
                                    console.log(self.currentPaytransfer());
                                    self.selectList10([
                                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 0),
                                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 1),
                                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 2),
                                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 3),
                                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 4),
                                    ]);
                                    console.log(self.selectList10());
                                    console.log(value);
                                    break;
                                case '支給5':
                                    qrm001.a.viewmodel.ScreenModel.setDataRow(item, self.fullPaytransfer, 4);
                                    console.log(self.currentPaytransfer());
                                    self.selectList10([
                                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 0),
                                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 1),
                                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 2),
                                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 3),
                                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 4),
                                    ]);
                                    console.log(self.selectList10());
                                    console.log(value);
                                    break;
                                default:
                                    qrm001.a.viewmodel.ScreenModel.setDataRow(item, self.fullPaytransfer, -1);
                                    console.log(self.currentPaytransfer());
                                    self.selectList10([
                                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 0),
                                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 1),
                                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 2),
                                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 3),
                                        qrm001.a.viewmodel.ScreenModel.prototype.filter(self.fullSetSelect, self.currentPaytransfer, 4),
                                    ]);
                                    console.log(self.selectList10());
                                    console.log(value);
                                    break;
                            }
                        });
                    });
                    self.employeeInfo().excludedYears.subscribe(function (newValue) {
                        console.log(self.employeeInfo().excludedYears());
                        console.log(self.employeeInfo());
                    });
                    self.employeeInfo().trialPeriod.subscribe(function (newValue) {
                        console.log(self.employeeInfo().trialPeriod());
                    });
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    qrm001.a.service.getPaymentDateProcessingList().done(function (data) {
                        self.paymentDateProcessingList(data);
                        dfd.resolve();
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.next_emp = function () {
                    var self = this;
                    self.currentEmployeeCode(self.currentEmployeeCode() + 1);
                    console.log(self.currentEmployeeCode());
                };
                ScreenModel.prototype.previous_emp = function () {
                    var self = this;
                    self.currentEmployeeCode(self.currentEmployeeCode() - 1);
                    console.log(self.currentEmployeeCode());
                };
                ScreenModel.prototype.loadData = function (src, dst) {
                    ko.utils.arrayForEach(src(), function (item) {
                        dst.push(item);
                    });
                };
                ScreenModel.prototype.openDialog = function () {
                    nts.uk.ui.windows.sub.modal('/view/qrm/001/b/index.xhtml', { title: '入力欄の背景色について', dialogClass: "no-close" });
                };
                ScreenModel.prototype.caculator = function () {
                    var self = this;
                    self.employeeInfo().totalDeductibleAmount(self.employeeInfo().retirementAllowanceDeduction1() +
                        self.employeeInfo().retirementAllowanceDeduction2() +
                        self.employeeInfo().retirementAllowanceDeduction3() +
                        self.employeeInfo().retirementIncome() +
                        self.employeeInfo().taxCalculationMethod());
                    console.log(self.currentPaytransfer());
                    console.log(self.selectList10());
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
                ScreenModel.setDataRow = function (item, dataSet, index) {
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
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var BankDataSet = (function () {
                function BankDataSet(dataSetName, bankName, branchName, accountNumber, transferMoney) {
                    var self = this;
                    self.dataSetName = ko.observable(dataSetName);
                    self.bankName = ko.observable(bankName);
                    self.branchName = ko.observable(branchName);
                    self.accountNumber = ko.observable(accountNumber);
                    self.transferMoney = ko.observable(transferMoney);
                }
                return BankDataSet;
            }());
            var EmployeeInfo = (function () {
                function EmployeeInfo(employeeCode, employeeName, jobEntryDate, retirementDate, trialPeriod, excludedYears, concurrentYears, officerYears, employmentClassification, retirementCategory, retirementReasonClassification, retirementAllowanceTotal, totalDeductibleAmount, retirementAllowanceDeduction1, retirementAllowanceDeduction2, retirementAllowanceDeduction3, retirementIncome, taxCalculationMethod, retirementAllowancePaymentAmount, incomeTaxAmount, cityTownTax, prefecturalTax, retirementPaymentTransferSetting, memo) {
                    this.employeeCode = ko.observable(employeeCode);
                    this.employeeName = ko.observable(employeeName);
                    this.jobEntryDate = ko.observable(jobEntryDate);
                    this.retirementDate = ko.observable(retirementDate);
                    this.trialPeriod = ko.observable(trialPeriod);
                    this.excludedYears = ko.observable(excludedYears);
                    this.concurrentYears = ko.observable(concurrentYears);
                    this.officerYears = ko.observable(officerYears);
                    this.employmentClassification = ko.observable(employmentClassification);
                    this.retirementCategory = ko.observable(retirementCategory);
                    this.retirementReasonClassification = ko.observable(retirementReasonClassification);
                    this.retirementAllowanceTotal = ko.observable(retirementAllowanceTotal);
                    this.totalDeductibleAmount = ko.observable(totalDeductibleAmount);
                    this.retirementAllowanceDeduction1 = ko.observable(retirementAllowanceDeduction1);
                    this.retirementAllowanceDeduction2 = ko.observable(retirementAllowanceDeduction2);
                    this.retirementAllowanceDeduction3 = ko.observable(retirementAllowanceDeduction3);
                    this.retirementIncome = ko.observable(retirementIncome);
                    this.taxCalculationMethod = ko.observable(taxCalculationMethod);
                    this.retirementAllowancePaymentAmount = ko.observable(retirementAllowancePaymentAmount);
                    this.incomeTaxAmount = ko.observable(incomeTaxAmount);
                    this.cityTownTax = ko.observable(cityTownTax);
                    this.prefecturalTax = ko.observable(prefecturalTax);
                    this.retirementPaymentTransferSetting = ko.observable(retirementPaymentTransferSetting);
                    this.memo = ko.observable(memo);
                }
                return EmployeeInfo;
            }());
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qrm001.a || (qrm001.a = {}));
})(qrm001 || (qrm001 = {}));
