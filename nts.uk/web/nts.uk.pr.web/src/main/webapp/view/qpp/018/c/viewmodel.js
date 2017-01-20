var qpp018;
(function (qpp018) {
    var c;
    (function (c) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.required = ko.observable(true);
                    this.isDetailed = ko.observable(true);
                    this.isTotalMonthlyTotal = ko.observable(true);
                    this.isOfficeMonthlyTotal = ko.observable(true);
                    this.isDeliveryNoticeAmount = ko.observable(true);
                    this.showHealthInsuranceType = ko.observableArray([
                        new HealthInsuranceType('1', '表示する'),
                        new HealthInsuranceType('2', '表示しない'),
                    ]);
                    this.selectedCode = ko.observable('1');
                    this.enable = ko.observable(true);
                    this.printSettingValue = ko.observable('PrintSetting Value');
                }
                ScreenModel.prototype.closePrintSetting = function () {
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.setupPrintSetting = function () {
                    if (!(this.isDetailed()) && !(this.isTotalMonthlyTotal()) && !(this.isOfficeMonthlyTotal())
                        && !(this.isDeliveryNoticeAmount())) {
                        alert("Something is not right");
                    }
                    else {
                        nts.uk.ui.windows.setShared("printSettingValue", this.printSettingValue(), true);
                        nts.uk.ui.windows.close();
                        alert("Yep");
                    }
                };
                ScreenModel.prototype.start = function () {
                    var dfd = $.Deferred();
                    dfd.resolve();
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
        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
    })(c = qpp018.c || (qpp018.c = {}));
})(qpp018 || (qpp018 = {}));
