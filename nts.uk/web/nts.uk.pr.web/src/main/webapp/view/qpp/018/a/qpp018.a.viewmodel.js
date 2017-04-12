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
                                    self.yearMonth = ko.observable(0);
                                    self.isEqual = ko.observable(true);
                                    self.isDeficient = ko.observable(true);
                                    self.isRedundant = ko.observable(true);
                                    self.insuranceOffice = ko.observable(new InsuranceOfficeModel());
                                }
                                /**
                                 * start page
                                 */
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.yearMonth(self.getCurrentYearMonth());
                                    $.when(self.insuranceOffice().findAllInsuranceOffice()).done(function () {
                                        if (self.insuranceOffice().items().length > 0) {
                                            self.insuranceOffice().selectFirst();
                                        }
                                        dfd.resolve();
                                    });
                                    return dfd.promise();
                                };
                                /**
                                 *  Export Data
                                 */
                                ScreenModel.prototype.exportData = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.clearAllError();
                                    if (self.validate()) {
                                        return;
                                    }
                                    var command = self.toJSObjet();
                                    a.service.saveAsPdf(command).done(function () {
                                        dfd.resolve();
                                    }).fail(function (res) {
                                        nts.uk.ui.dialog.alert(res.message);
                                    });
                                };
                                /**
                                 * to JSon Object
                                 */
                                ScreenModel.prototype.toJSObjet = function () {
                                    var self = this;
                                    var command = {};
                                    command.yearMonth = self.yearMonth();
                                    command.isEqual = self.isEqual();
                                    command.isDeficient = self.isDeficient();
                                    command.isRedundant = self.isRedundant();
                                    command.insuranceOffices = self.insuranceOffice().getSelectedOffice();
                                    return command;
                                };
                                /**
                                 *  Show dialog ChecklistPrintSetting
                                 */
                                ScreenModel.prototype.showDialogChecklistPrintSetting = function () {
                                    nts.uk.ui.windows.setShared("socialInsuranceFeeChecklist", null);
                                    nts.uk.ui.windows.sub.modal("/view/qpp/018/c/index.xhtml", { title: "印刷の設定" }).onClosed(function () {
                                        //                    let returnValue = nts.uk.ui.windows.getShared("printSettingValue");
                                    });
                                };
                                /**
                                 * validate
                                 */
                                ScreenModel.prototype.validate = function () {
                                    var self = this;
                                    var isError = false;
                                    if (self.insuranceOffice().selectedOfficeCodeList().length <= 0) {
                                        $('.grid-error').ntsError('set', 'You must choose at least item of grid');
                                        isError = true;
                                    }
                                    if (!self.isEqual() && !self.isDeficient() && !self.isRedundant()) {
                                        // message ER001
                                        $('.extract-condition-error').ntsError('set', '必須の入力項目が入力されていません。');
                                    }
                                    return isError;
                                };
                                ScreenModel.prototype.clearAllError = function () {
                                    $('.grid-error').ntsError('clear');
                                    $('.extract-condition-error').ntsError('clear');
                                };
                                /**
                                 * get year and month current.
                                 */
                                ScreenModel.prototype.getCurrentYearMonth = function () {
                                    var today = new Date();
                                    var month = today.getMonth() + 1; //January is 0!
                                    var year = today.getFullYear();
                                    var yearMonth = year * 100 + month;
                                    return yearMonth;
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            /**
                              * Class InsuranceOfficeModel
                              */
                            var InsuranceOfficeModel = (function () {
                                function InsuranceOfficeModel() {
                                    var self = this;
                                    self.items = ko.observableArray([]);
                                    self.selectedOfficeCodeList = ko.observableArray([]);
                                    self.columns = ko.observableArray([
                                        { headerText: 'コード', key: 'code', width: 200 },
                                        { headerText: '名称 ', key: 'name', width: 200 }
                                    ]);
                                }
                                /**
                                 * find list insurance office.
                                 */
                                InsuranceOfficeModel.prototype.findAllInsuranceOffice = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    a.service.findAllInsuranceOffice().done(function (res) {
                                        self.items(res);
                                        dfd.resolve();
                                    }).fail(function (res) {
                                        nts.uk.ui.dialog.alert(res.message);
                                    });
                                    return dfd.promise();
                                };
                                InsuranceOfficeModel.prototype.selectFirst = function () {
                                    var self = this;
                                    var code = self.items()[0].code;
                                    self.selectedOfficeCodeList().push(code);
                                };
                                InsuranceOfficeModel.prototype.getSelectedOffice = function () {
                                    var self = this;
                                    var offices = [];
                                    for (var i in self.selectedOfficeCodeList()) {
                                        var code = self.selectedOfficeCodeList()[i];
                                        var office = self.getOfficeByCode(code);
                                        if (office) {
                                            offices.push(office);
                                        }
                                    }
                                    return offices;
                                };
                                InsuranceOfficeModel.prototype.getOfficeByCode = function (code) {
                                    var self = this;
                                    for (var i in self.items()) {
                                        var office = self.items()[i];
                                        if (office.code == code) {
                                            return office;
                                        }
                                    }
                                    return null;
                                };
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
