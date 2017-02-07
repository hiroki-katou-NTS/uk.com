var qpp018;
(function (qpp018) {
    var c;
    (function (c) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.required = ko.observable(true);
                    this.showHealthInsuranceType = ko.observableArray([
                        new HealthInsuranceType('1', '表示する'),
                        new HealthInsuranceType('2', '表示しない'),
                    ]);
                    this.selectedCode = ko.observable('1');
                    this.enable = ko.observable(true);
                    this.printSettingValue = ko.observable('PrintSetting Value');
                    this.printSettingList = ko.observable();
                }
                ScreenModel.prototype.closePrintSetting = function () {
                    nts.uk.ui.windows.setShared("printSettingValue", this.printSettingValue(), true);
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.setupPrintSetting = function () {
                    if (!(this.printSettingList().showDetail) && !(this.printSettingList().showCategoryInsuranceItem) && !(this.printSettingList().showOffice)
                        && !(this.printSettingList().showDeliveryNoticeAmount)) {
                        alert("Something is not right");
                    }
                    else {
                        nts.uk.ui.windows.setShared("printSettingValue", this.printSettingValue(), true);
                        alert("Yep");
                        nts.uk.ui.windows.close();
                    }
                };
                ScreenModel.prototype.start = function () {
                    var dfd = $.Deferred();
                    var self = this;
                    $.when(self.loadAllCheckListPrintSetting()).done(function () {
                        dfd.resolve();
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.loadAllCheckListPrintSetting = function () {
                    var dfd = $.Deferred();
                    var self = this;
                    c.service.getallCheckListPrintSetting().done(function (data) {
                        self.printSettingList(data);
                        dfd.resolve();
                    }).fail(function (res) {
                        nts.uk.ui.dialog.alert(res.message);
                        dfd.reject();
                    });
                    return dfd.promise();
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
            var PrintSettingList = (function () {
                function PrintSettingList(showCategoryInsuranceItem, showDeliveryNoticeAmount, showDetail, showOffice) {
                    this.showCategoryInsuranceItem = showCategoryInsuranceItem;
                    this.showDeliveryNoticeAmount = showDeliveryNoticeAmount;
                    this.showDetail = showDetail;
                    this.showOffice = showOffice;
                }
                return PrintSettingList;
            }());
            viewmodel.PrintSettingList = PrintSettingList;
        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
    })(c = qpp018.c || (qpp018.c = {}));
})(qpp018 || (qpp018 = {}));
