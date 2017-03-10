var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qpp018;
                (function (qpp018) {
                    var a;
                    (function (a) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    this.yearMonth = ko.observable(self.getCurrentYearMonth());
                                    this.checked = ko.observable(true);
                                    this.isEqual = ko.observable(true);
                                    this.isDeficent = ko.observable(true);
                                    this.isRedundant = ko.observable(true);
                                    this.insuranceOffice = ko.observableArray([]);
                                    this.columns = ko.observableArray([
                                        { headerText: 'コード', key: 'code', width: 100 },
                                        { headerText: '名称 ', key: 'name', width: 100 }
                                    ]);
                                    this.selectedOfficeList = ko.observableArray([]);
                                    this.exportDataDetails = ko.observable('Something');
                                }
                                ScreenModel.prototype.showDialogChecklistPrintSetting = function () {
                                    nts.uk.ui.windows.setShared("socialInsuranceFeeChecklist", null);
                                    nts.uk.ui.windows.sub.modal("/view/qpp/018/c/index.xhtml", { title: "印刷の設定" }).onClosed(function () {
                                        var returnValue = nts.uk.ui.windows.getShared("printSettingValue");
                                    });
                                };
                                ScreenModel.prototype.exportData = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    if ((this.yearMonth() == '') || (this.selectedOfficeList() == null) || (!(this.isEqual()) && !(this.isDeficent()) && !(this.isRedundant()))) {
                                        alert("Something is not right");
                                        return;
                                    }
                                    a.service.saveAsPdf().done(function () {
                                        dfd.resolve();
                                    }).fail(function (res) {
                                        nts.uk.ui.dialog.alert(res.message);
                                        dfd.reject();
                                    });
                                };
                                ScreenModel.prototype.start = function () {
                                    var dfd = $.Deferred();
                                    var self = this;
                                    $.when(self.loadAllInsuranceOffice()).done(function () {
                                        dfd.resolve();
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.loadAllInsuranceOffice = function () {
                                    var dfd = $.Deferred();
                                    var self = this;
                                    a.service.getAllInsuranceOffice().done(function (data) {
                                        self.insuranceOffice(data);
                                        dfd.resolve();
                                    }).fail(function (res) {
                                        nts.uk.ui.dialog.alert(res.message);
                                        dfd.reject();
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.getCurrentYearMonth = function () {
                                    var today = new Date();
                                    var month = today.getMonth() + 1;
                                    var year = today.getFullYear();
                                    var yearMonth = year + '/';
                                    if (month < 10) {
                                        yearMonth += '0' + month;
                                    }
                                    else {
                                        yearMonth += month.toLocaleString();
                                    }
                                    return yearMonth;
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var InsuranceOfficeModel = (function () {
                                function InsuranceOfficeModel(code, name) {
                                    this.code = code;
                                    this.name = name;
                                }
                                return InsuranceOfficeModel;
                            }());
                            viewmodel.InsuranceOfficeModel = InsuranceOfficeModel;
                        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
                    })(a = qpp018.a || (qpp018.a = {}));
                })(qpp018 = view.qpp018 || (view.qpp018 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
