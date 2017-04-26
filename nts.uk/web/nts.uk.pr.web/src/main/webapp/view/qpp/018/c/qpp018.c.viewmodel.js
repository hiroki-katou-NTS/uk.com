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
                    var c;
                    (function (c) {
                        var viewmodel;
                        (function (viewmodel) {
                            class ScreenModel {
                                constructor() {
                                    let self = this;
                                    self.checklistPrintSettingModel = ko.observable(new ChecklistPrintSettingModel());
                                }
                                startPage() {
                                    let self = this;
                                    let dfd = $.Deferred();
                                    self.loadCheckListPrintSetting().done(function () {
                                        dfd.resolve(self);
                                    });
                                    return dfd.promise();
                                }
                                loadCheckListPrintSetting() {
                                    let self = this;
                                    let dfd = $.Deferred();
                                    c.service.findCheckListPrintSetting().done(function (data) {
                                        self.initUI(data);
                                        dfd.resolve();
                                    }).fail(function (res) {
                                        nts.uk.ui.dialog.alert(res.message);
                                    });
                                    return dfd.promise();
                                }
                                saveConfigSetting() {
                                    let self = this;
                                    let dfd = $.Deferred();
                                    if (self.validate()) {
                                        return;
                                    }
                                    c.service.saveCheckListPrintSetting(self).done(function (res) {
                                        dfd.resolve(res);
                                    }).fail(function (res) {
                                        dfd.reject(res);
                                    }).always(function () {
                                        self.closeDialog();
                                    });
                                    return dfd.promise();
                                }
                                validate() {
                                    let self = this;
                                    let isError = false;
                                    self.clearError();
                                    let checklistSetting = self.checklistPrintSettingModel();
                                    if (!checklistSetting.showDetail() && !checklistSetting.showOffice() && !checklistSetting.showTotal()
                                        && !checklistSetting.showDeliveryNoticeAmount()) {
                                        isError = true;
                                        $('#require-least-item').ntsError('set', '必須の入力項目が入力されていません。');
                                    }
                                    return isError;
                                }
                                clearError() {
                                    $('#require-least-item').ntsError('clear');
                                }
                                closeDialog() {
                                    nts.uk.ui.windows.close();
                                }
                                initUI(res) {
                                    let self = this;
                                    let checklistSetting = self.checklistPrintSettingModel();
                                    checklistSetting.setData(res);
                                }
                            }
                            viewmodel.ScreenModel = ScreenModel;
                            /**
                             * The ChecklistPrintSettingModel
                             */
                            class ChecklistPrintSettingModel {
                                constructor() {
                                    let self = this;
                                    self.selectedHealthInsuranceItem = ko.observable("indicate");
                                    self.showCategoryInsuranceItem = ko.computed(function () {
                                        if (self.selectedHealthInsuranceItem() == 'indicate') {
                                            return true;
                                        }
                                        return false;
                                    }, self);
                                    self.showDetail = ko.observable(true);
                                    self.showOffice = ko.observable(true);
                                    self.showTotal = ko.observable(true);
                                    self.showDeliveryNoticeAmount = ko.observable(true);
                                    self.healthInsuranceItems = ko.observableArray([
                                        { code: "indicate", name: "表示する" },
                                        { code: "hide", name: "表示しない" }
                                    ]);
                                }
                                setData(dto) {
                                    let self = this;
                                    var insuranceItemCode = dto.showCategoryInsuranceItem ? 'indicate' : 'hide';
                                    self.selectedHealthInsuranceItem(insuranceItemCode);
                                    self.showDetail(dto.showDetail);
                                    self.showOffice(dto.showOffice);
                                    self.showTotal(dto.showTotal);
                                    self.showDeliveryNoticeAmount(dto.showDeliveryNoticeAmount);
                                }
                            }
                            viewmodel.ChecklistPrintSettingModel = ChecklistPrintSettingModel;
                        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
                    })(c = qpp018.c || (qpp018.c = {}));
                })(qpp018 = view.qpp018 || (view.qpp018 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
