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
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.checklistPrintSettingModel = ko.observable(new ChecklistPrintSettingModel());
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.loadCheckListPrintSetting().done(function () {
                                        dfd.resolve(self);
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.loadCheckListPrintSetting = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    c.service.findCheckListPrintSetting().done(function (data) {
                                        self.initUI(data);
                                        dfd.resolve();
                                    }).fail(function (res) {
                                        nts.uk.ui.dialog.alert(res.message);
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.saveConfigSetting = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    if (self.validate()) {
                                        return;
                                    }
                                    var command = self.toJSObject();
                                    c.service.saveCheckListPrintSetting(command).done(function (res) {
                                        dfd.resolve(res);
                                    }).fail(function (res) {
                                        dfd.reject(res);
                                    }).always(function () {
                                        self.closeDialog();
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.validate = function () {
                                    var self = this;
                                    var isError = false;
                                    self.clearError();
                                    var checklistSetting = self.checklistPrintSettingModel();
                                    if (!checklistSetting.showDetail() && !checklistSetting.showOffice() && !checklistSetting.showTotal()
                                        && !checklistSetting.showDeliveryNoticeAmount()) {
                                        isError = true;
                                        $('#require-least-item').ntsError('set', '必須の入力項目が入力されていません。');
                                    }
                                    return isError;
                                };
                                ScreenModel.prototype.clearError = function () {
                                    $('#require-least-item').ntsError('clear');
                                };
                                ScreenModel.prototype.closeDialog = function () {
                                    nts.uk.ui.windows.close();
                                };
                                ScreenModel.prototype.initUI = function (res) {
                                    var self = this;
                                    var checklistSetting = self.checklistPrintSettingModel();
                                    if (res) {
                                        checklistSetting.setData(res);
                                    }
                                    else {
                                        checklistSetting.defaultValue();
                                    }
                                };
                                ScreenModel.prototype.toJSObject = function () {
                                    var self = this;
                                    var checklistSetting = self.checklistPrintSettingModel();
                                    var command = {};
                                    var checkListPrintSetting = {};
                                    checkListPrintSetting.showCategoryInsuranceItem = checklistSetting.showCategoryInsuranceItem();
                                    checkListPrintSetting.showDetail = checklistSetting.showDetail();
                                    checkListPrintSetting.showOffice = checklistSetting.showOffice();
                                    checkListPrintSetting.showTotal = checklistSetting.showTotal();
                                    checkListPrintSetting.showDeliveryNoticeAmount = checklistSetting.showDeliveryNoticeAmount();
                                    command.checkListPrintSettingDto = checkListPrintSetting;
                                    return command;
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var ChecklistPrintSettingModel = (function () {
                                function ChecklistPrintSettingModel() {
                                    var self = this;
                                    self.selectedHealthInsuranceItem = ko.observable("indicate");
                                    self.showCategoryInsuranceItem = ko.computed(function () {
                                        if (self.selectedHealthInsuranceItem() == 'indicate') {
                                            return true;
                                        }
                                        return false;
                                    }, self);
                                    self.showDetail = ko.observable(false);
                                    self.showOffice = ko.observable(false);
                                    self.showTotal = ko.observable(false);
                                    self.showDeliveryNoticeAmount = ko.observable(false);
                                    self.healthInsuranceItems = ko.observableArray([
                                        { code: "indicate", name: "表示する" },
                                        { code: "hide", name: "表示しない" }
                                    ]);
                                }
                                ChecklistPrintSettingModel.prototype.setData = function (dto) {
                                    var self = this;
                                    if (dto.showCategoryInsuranceItem) {
                                        self.selectedHealthInsuranceItem("indicate");
                                    }
                                    else {
                                        self.selectedHealthInsuranceItem("hide");
                                    }
                                    self.showDetail(dto.showDetail);
                                    self.showOffice(dto.showOffice);
                                    self.showTotal(dto.showTotal);
                                    self.showDeliveryNoticeAmount(dto.showDeliveryNoticeAmount);
                                };
                                ChecklistPrintSettingModel.prototype.defaultValue = function () {
                                    var self = this;
                                    self.selectedHealthInsuranceItem("indicate");
                                    self.showOffice(false);
                                    self.showTotal(false);
                                    self.showDeliveryNoticeAmount(false);
                                };
                                return ChecklistPrintSettingModel;
                            }());
                            viewmodel.ChecklistPrintSettingModel = ChecklistPrintSettingModel;
                        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
                    })(c = qpp018.c || (qpp018.c = {}));
                })(qpp018 = view.qpp018 || (view.qpp018 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
