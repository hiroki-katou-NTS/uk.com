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
                                    self.showDeliveryNoticeAmount = ko.observable(false);
                                    self.showCategoryInsuranceItem = ko.observable(false);
                                    self.showOffice = ko.observable(false);
                                    self.showTotal = ko.observable(false);
                                    self.healthInsuranceItems = ko.observableArray([
                                        { code: "indicate", name: "表示する" },
                                        { code: "hide", name: "表示しない" }
                                    ]);
                                    self.selectedHealthInsuranceItem = ko.observable("indicate");
                                    self.showDetail = ko.computed(function () {
                                        if (self.selectedHealthInsuranceItem() == 'indicate') {
                                            return true;
                                        }
                                        return false;
                                    }, self);
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
                                    var command = self.toJSObject();
                                    c.service.saveCheckListPrintSetting(command).done(function (res) {
                                        dfd.resolve(res);
                                    }).fail(function (res) {
                                        dfd.reject(res);
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.closeDialog = function () {
                                    nts.uk.ui.windows.close();
                                };
                                ScreenModel.prototype.initUI = function (res) {
                                    var self = this;
                                    if (res) {
                                        self.setData(res);
                                    }
                                    else {
                                        self.defaultValue();
                                    }
                                };
                                ScreenModel.prototype.setData = function (dto) {
                                    var self = this;
                                    if (dto.showDetail) {
                                        self.selectedHealthInsuranceItem("indicate");
                                    }
                                    else {
                                        self.selectedHealthInsuranceItem("hide");
                                    }
                                    self.showDeliveryNoticeAmount(dto.showDeliveryNoticeAmount);
                                    self.showCategoryInsuranceItem(dto.showDetail);
                                    self.showOffice(dto.showOffice);
                                    self.showTotal(dto.showTotal);
                                };
                                ScreenModel.prototype.defaultValue = function () {
                                    var self = this;
                                    self.selectedHealthInsuranceItem("indicate");
                                    self.showCategoryInsuranceItem(false);
                                    self.showOffice(false);
                                    self.showTotal(false);
                                    self.showDeliveryNoticeAmount(false);
                                };
                                ScreenModel.prototype.toJSObject = function () {
                                    var self = this;
                                    var command = {};
                                    var checkListPrintSetting = {};
                                    checkListPrintSetting.showDetail = self.showDetail();
                                    checkListPrintSetting.showCategoryInsuranceItem = self.showCategoryInsuranceItem();
                                    checkListPrintSetting.showOffice = self.showOffice();
                                    checkListPrintSetting.showTotal = self.showTotal();
                                    checkListPrintSetting.showDeliveryNoticeAmount = self.showDeliveryNoticeAmount();
                                    command.checkListPrintSettingDto = checkListPrintSetting;
                                    return command;
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
                    })(c = qpp018.c || (qpp018.c = {}));
                })(qpp018 = view.qpp018 || (view.qpp018 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
