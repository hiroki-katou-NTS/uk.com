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
                            class ScreenModel {
                                constructor() {
                                    let self = this;
                                    self.yearMonth = ko.observable(null);
                                    self.isEqual = ko.observable(true);
                                    self.isDeficient = ko.observable(true);
                                    self.isRedundant = ko.observable(true);
                                    self.insuranceOffice = ko.observable(new InsuranceOfficeModel());
                                    self.japanYearmonth = ko.computed(() => {
                                        return nts.uk.time.yearmonthInJapanEmpire(self.yearMonth()).toString();
                                    });
                                }
                                /**
                                 * start page
                                 */
                                startPage() {
                                    let self = this;
                                    let dfd = $.Deferred();
                                    self.yearMonth(self.getCurrentYearMonth());
                                    $.when(self.insuranceOffice().findAllInsuranceOffice()).done(function () {
                                        if (self.insuranceOffice().items().length > 0) {
                                            self.insuranceOffice().selectFirst();
                                        }
                                        dfd.resolve();
                                    });
                                    return dfd.promise();
                                }
                                /**
                                 *  Export Data
                                 */
                                exportData() {
                                    let self = this;
                                    let dfd = $.Deferred();
                                    self.clearAllError();
                                    if (self.validate()) {
                                        return;
                                    }
                                    let command = self.toJSObjet();
                                    a.service.saveAsPdf(command).done(function () {
                                        dfd.resolve();
                                    }).fail(function (res) {
                                        nts.uk.ui.dialog.alert(res.message);
                                    });
                                }
                                /**
                                 * to JSon Object
                                 */
                                toJSObjet() {
                                    let self = this;
                                    let command = {};
                                    command.yearMonth = self.yearMonth();
                                    command.isEqual = self.isEqual();
                                    command.isDeficient = self.isDeficient();
                                    command.isRedundant = self.isRedundant();
                                    command.insuranceOffices = self.insuranceOffice().getSelectedOffice();
                                    return command;
                                }
                                /**
                                 *  Show dialog ChecklistPrintSetting
                                 */
                                showDialogChecklistPrintSetting() {
                                    nts.uk.ui.windows.setShared("socialInsuranceFeeChecklist", null);
                                    nts.uk.ui.windows.sub.modal("/view/qpp/018/c/index.xhtml", { title: "印刷の設定" });
                                }
                                /**
                                 * validate
                                 */
                                validate() {
                                    let self = this;
                                    let isError = false;
                                    // Validate year month
                                    $('#date-picker').ntsEditor('validate');
                                    if (!self.isEqual() && !self.isDeficient() && !self.isRedundant()) {
                                        // message ER001
                                        $('.extract-condition-error').ntsError('set', '出力条件が入力されていません。');
                                        isError = true;
                                    }
                                    if (self.insuranceOffice().selectedOfficeCodeList().length <= 0) {
                                        $('.grid-error').ntsError('set', '社会保険事業所が入力されていません。');
                                        isError = true;
                                    }
                                    return isError || !nts.uk.ui._viewModel.errors.isEmpty();
                                }
                                clearAllError() {
                                    $('#date-picker').ntsError('clear');
                                    $('.grid-error').ntsError('clear');
                                    $('.extract-condition-error').ntsError('clear');
                                }
                                /**
                                 * get year and month current.
                                 */
                                getCurrentYearMonth() {
                                    let today = new Date();
                                    let month = today.getMonth() + 1; //January is 0!
                                    let year = today.getFullYear();
                                    let yearMonth = year * 100 + month;
                                    return yearMonth;
                                }
                            }
                            viewmodel.ScreenModel = ScreenModel;
                            /**
                              * Class InsuranceOfficeModel
                              */
                            class InsuranceOfficeModel {
                                constructor() {
                                    let self = this;
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
                                findAllInsuranceOffice() {
                                    let self = this;
                                    let dfd = $.Deferred();
                                    a.service.findAllInsuranceOffice().done(function (res) {
                                        self.items(res);
                                        dfd.resolve();
                                    }).fail(function (res) {
                                        nts.uk.ui.dialog.alert(res.message);
                                    });
                                    return dfd.promise();
                                }
                                selectFirst() {
                                    let self = this;
                                    let code = self.items()[0].code;
                                    self.selectedOfficeCodeList().push(code);
                                }
                                getSelectedOffice() {
                                    let self = this;
                                    let offices = [];
                                    for (let i in self.selectedOfficeCodeList()) {
                                        let code = self.selectedOfficeCodeList()[i];
                                        let office = self.getOfficeByCode(code);
                                        if (office) {
                                            offices.push(office);
                                        }
                                    }
                                    return offices;
                                }
                                getOfficeByCode(code) {
                                    let self = this;
                                    for (let i in self.items()) {
                                        let office = self.items()[i];
                                        if (office.code == code) {
                                            return office;
                                        }
                                    }
                                    return null;
                                }
                            }
                            viewmodel.InsuranceOfficeModel = InsuranceOfficeModel;
                        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
                    })(a = qpp018.a || (qpp018.a = {}));
                })(qpp018 = view.qpp018 || (view.qpp018 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
