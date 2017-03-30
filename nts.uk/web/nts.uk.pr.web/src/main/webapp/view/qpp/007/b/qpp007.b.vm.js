var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qpp007;
                (function (qpp007) {
                    var b;
                    (function (b) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.salaryPrintSettingModel = ko.observable();
                                    self.switchButtonDataSource = ko.observableArray([
                                        { code: 'Hourly', name: '時間' },
                                        { code: 'Minutely', name: '分' }
                                    ]);
                                    self.switchValue = ko.observable('Apply');
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.loadSalaryPrintSetting().done(function () {
                                        return dfd.resolve();
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.loadSalaryPrintSetting = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    b.service.findSalaryPrintSetting().then(function (res) {
                                        self.salaryPrintSettingModel(new SalaryPrintSettingModel('Minutely', false, true, true, false, true, true, false, true, false, true, false, true, false, true, false, false, false, true, false, true));
                                        dfd.resolve();
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.saveSetting = function () {
                                    var self = this;
                                    b.service.saveSalaryPrintSetting(ko.toJS(self.salaryPrintSettingModel));
                                };
                                ScreenModel.prototype.cancel = function () {
                                    nts.uk.ui.windows.close();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var SalaryPrintSettingModel = (function () {
                                function SalaryPrintSettingModel(outputDistinction, showDepartmentMonthlyAmount, showDetail, showDivisionMonthlyTotal, showDivisionTotal, showHierarchy1, showHierarchy2, showHierarchy3, showHierarchy4, showHierarchy5, showHierarchy6, showHierarchy7, showHierarchy8, showHierarchy9, showHierarchyAccumulation, showHierarchyMonthlyAccumulation, showMonthlyAmount, showPersonalMonthlyAmount, showPersonalTotal, showSectionalCalculation, showTotal) {
                                    this.outputDistinction = ko.observable(outputDistinction);
                                    this.showDepartmentMonthlyAmount = ko.observable(showDepartmentMonthlyAmount);
                                    this.showDetail = ko.observable(showDetail);
                                    this.showDivisionMonthlyTotal = ko.observable(showDivisionMonthlyTotal);
                                    this.showDivisionTotal = ko.observable(showDivisionTotal);
                                    this.showHierarchy1 = ko.observable(showHierarchy1);
                                    this.showHierarchy2 = ko.observable(showHierarchy2);
                                    this.showHierarchy3 = ko.observable(showHierarchy3);
                                    this.showHierarchy4 = ko.observable(showHierarchy4);
                                    this.showHierarchy5 = ko.observable(showHierarchy5);
                                    this.showHierarchy6 = ko.observable(showHierarchy6);
                                    this.showHierarchy7 = ko.observable(showHierarchy7);
                                    this.showHierarchy8 = ko.observable(showHierarchy8);
                                    this.showHierarchy9 = ko.observable(showHierarchy9);
                                    this.showHierarchyAccumulation = ko.observable(showHierarchyAccumulation);
                                    this.showHierarchyMonthlyAccumulation = ko.observable(showHierarchyMonthlyAccumulation);
                                    this.showMonthlyAmount = ko.observable(showMonthlyAmount);
                                    this.showPersonalMonthlyAmount = ko.observable(showPersonalMonthlyAmount);
                                    this.showPersonalTotal = ko.observable(showPersonalTotal);
                                    this.showSectionalCalculation = ko.observable(showSectionalCalculation);
                                    this.showTotal = ko.observable(showTotal);
                                }
                                return SalaryPrintSettingModel;
                            }());
                            viewmodel.SalaryPrintSettingModel = SalaryPrintSettingModel;
                        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
                    })(b = qpp007.b || (qpp007.b = {}));
                })(qpp007 = view.qpp007 || (view.qpp007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qpp007.b.vm.js.map