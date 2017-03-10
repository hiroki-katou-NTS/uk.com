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
                                    self.required = ko.observable(true);
                                    self.showHealthInsuranceType = ko.observableArray([
                                        new HealthInsuranceType('1', '表示する'),
                                        new HealthInsuranceType('2', '表示しない'),
                                    ]);
                                    self.selectedCode = ko.observable('1');
                                    self.enable = ko.observable(true);
                                    self.printSettingValue = ko.observable('PrintSetting Value');
                                    self.checkListPrintSettingModel = ko.observable(new CheckListPrintSettingModel());
                                }
                                ScreenModel.prototype.closePrintSetting = function () {
                                    nts.uk.ui.windows.setShared("printSettingValue", this.printSettingValue(), true);
                                    nts.uk.ui.windows.close();
                                };
                                ScreenModel.prototype.saveConfigurePrintSetting = function () {
                                    var self = this;
                                    if (!(self.checkListPrintSettingModel().showCategoryInsuranceItem())
                                        && !(self.checkListPrintSettingModel().showDeliveryNoticeAmount())
                                        && !(self.checkListPrintSettingModel().showDetail())
                                        && !(self.checkListPrintSettingModel().showOffice())) {
                                        alert("Something is not right");
                                    }
                                    else {
                                        nts.uk.ui.windows.setShared("printSettingValue", this.printSettingValue(), true);
                                        var command = {
                                            checkListPrintSettingDto: self.checkListPrintSettingModel().toDto()
                                        };
                                        c.service.saveCheckListPrintSetting(command).done(function (data) {
                                            alert("Configure successful.");
                                        }).fail(function (res) {
                                            alert("Configure fail.");
                                        });
                                        nts.uk.ui.windows.close();
                                    }
                                };
                                ScreenModel.prototype.start = function () {
                                    var dfd = $.Deferred();
                                    var self = this;
                                    self.loadAllCheckListPrintSetting().done(function (data) {
                                        dfd.resolve(self);
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.loadAllCheckListPrintSetting = function () {
                                    var dfd = $.Deferred();
                                    var self = this;
                                    c.service.findCheckListPrintSetting().done(function (data) {
                                        self.checkListPrintSettingModel().setData(data);
                                        dfd.resolve();
                                    }).fail(function (res) {
                                        nts.uk.ui.dialog.alert(res.message);
                                        dfd.reject();
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.saveCheckListPrintSetting = function () {
                                    var self = this;
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var HealthInsuranceType = (function () {
                                function HealthInsuranceType(code, name) {
                                    this.code = code;
                                    this.name = name;
                                }
                                return HealthInsuranceType;
                            }());
                            viewmodel.HealthInsuranceType = HealthInsuranceType;
                            var CheckListPrintSettingModel = (function () {
                                function CheckListPrintSettingModel() {
                                    this.showCategoryInsuranceItem = ko.observable(false);
                                    this.showDeliveryNoticeAmount = ko.observable(false);
                                    this.showDetail = ko.observable(false);
                                    this.showOffice = ko.observable(false);
                                }
                                CheckListPrintSettingModel.prototype.setData = function (checkListPrintSettingDto) {
                                    this.showCategoryInsuranceItem(checkListPrintSettingDto.showCategoryInsuranceItem);
                                    this.showDeliveryNoticeAmount(checkListPrintSettingDto.showDeliveryNoticeAmount);
                                    this.showDetail(checkListPrintSettingDto.showDetail);
                                    this.showOffice(checkListPrintSettingDto.showOffice);
                                };
                                CheckListPrintSettingModel.prototype.resetValue = function () {
                                    this.showCategoryInsuranceItem(false);
                                    this.showDeliveryNoticeAmount(false);
                                    this.showDetail(false);
                                    this.showOffice(false);
                                };
                                CheckListPrintSettingModel.prototype.toDto = function () {
                                    var checkListPrintSettingDto;
                                    checkListPrintSettingDto = new c.service.model.CheckListPrintSettingDto();
                                    checkListPrintSettingDto.showCategoryInsuranceItem = this.showCategoryInsuranceItem();
                                    checkListPrintSettingDto.showDeliveryNoticeAmount = this.showDeliveryNoticeAmount();
                                    checkListPrintSettingDto.showDetail = this.showDetail();
                                    checkListPrintSettingDto.showOffice = this.showOffice();
                                    return checkListPrintSettingDto;
                                };
                                return CheckListPrintSettingModel;
                            }());
                            viewmodel.CheckListPrintSettingModel = CheckListPrintSettingModel;
                        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
                    })(c = qpp018.c || (qpp018.c = {}));
                })(qpp018 = view.qpp018 || (view.qpp018 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
