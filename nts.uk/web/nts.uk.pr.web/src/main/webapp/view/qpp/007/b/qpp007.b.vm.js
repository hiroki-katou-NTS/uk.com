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
                            class ScreenModel {
                                constructor() {
                                    var self = this;
                                    self.salaryPrintSettingModel = ko.observable();
                                }
                                /**
                                 * Start page.
                                 */
                                startPage() {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.loadSalaryPrintSetting().done(() => dfd.resolve());
                                    return dfd.promise();
                                }
                                /**
                                 * Call service to load salary print setting.
                                 */
                                loadSalaryPrintSetting() {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    b.service.findSalaryPrintSetting().done(function (res) {
                                        self.salaryPrintSettingModel(new SalaryPrintSettingModel(res));
                                        dfd.resolve();
                                    });
                                    return dfd.promise();
                                }
                                /**
                                 * Call service to save print setting.
                                 */
                                saveSetting() {
                                    var self = this;
                                    b.service.saveSalaryPrintSetting(ko.toJS(self.salaryPrintSettingModel));
                                    nts.uk.ui.windows.close();
                                }
                                /**
                                 * Cancel and close dialog.
                                 */
                                cancel() {
                                    nts.uk.ui.windows.close();
                                }
                            }
                            viewmodel.ScreenModel = ScreenModel;
                            class SalaryPrintSettingModel {
                                constructor(dto) {
                                    this.showPayment = ko.observable(dto.showPayment);
                                    this.sumPersonSet = ko.observable(dto.sumPersonSet);
                                    this.sumMonthPersonSet = ko.observable(dto.sumMonthPersonSet);
                                    this.sumEachDeprtSet = ko.observable(dto.sumEachDeprtSet);
                                    this.sumMonthDeprtSet = ko.observable(dto.sumMonthDeprtSet);
                                    this.sumDepHrchyIndexSet = ko.observable(dto.sumDepHrchyIndexSet);
                                    this.sumMonthDepHrchySet = ko.observable(dto.sumMonthDepHrchySet);
                                    this.hrchyIndex1 = ko.observable(dto.hrchyIndex1);
                                    this.hrchyIndex2 = ko.observable(dto.hrchyIndex2);
                                    this.hrchyIndex3 = ko.observable(dto.hrchyIndex3);
                                    this.hrchyIndex4 = ko.observable(dto.hrchyIndex4);
                                    this.hrchyIndex5 = ko.observable(dto.hrchyIndex5);
                                    this.hrchyIndex6 = ko.observable(dto.hrchyIndex6);
                                    this.hrchyIndex7 = ko.observable(dto.hrchyIndex7);
                                    this.hrchyIndex8 = ko.observable(dto.hrchyIndex8);
                                    this.hrchyIndex9 = ko.observable(dto.hrchyIndex9);
                                    this.totalSet = ko.observable(dto.totalSet);
                                    this.monthTotalSet = ko.observable(dto.monthTotalSet);
                                }
                            }
                            viewmodel.SalaryPrintSettingModel = SalaryPrintSettingModel;
                        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
                    })(b = qpp007.b || (qpp007.b = {}));
                })(qpp007 = view.qpp007 || (view.qpp007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
