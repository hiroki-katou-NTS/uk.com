var qpp018;
(function (qpp018) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.yearMonth = ko.observable('2016/05');
                    this.checked = ko.observable(true);
                    this.isEqual = ko.observable(true);
                    this.isDeficent = ko.observable(true);
                    this.isRedundant = ko.observable(true);
                    this.insuranceOffice = ko.observableArray([]);
                    this.columns = ko.observableArray([
                        { headerText: 'コード', prop: 'code', width: 100 },
                        { headerText: '名称 ', prop: 'name', width: 100 }
                    ]);
                    this.selectedOfficeList = ko.observableArray([]);
                    this.exportDataDetails = ko.observable('Something');
                }
                ScreenModel.prototype.openPrintSettingScr = function () {
                    nts.uk.ui.windows.setShared("socialInsuranceFeeChecklist", null);
                    nts.uk.ui.windows.sub.modal("/view/qpp/018/c/index.xhtml", { title: "印刷の設定" }).onClosed(function () {
                        var returnValue = nts.uk.ui.windows.getShared("printSettingValue");
                        alert("Accepted: " + returnValue);
                    });
                };
                ScreenModel.prototype.exportData = function () {
                    if ((this.yearMonth() == '') || (this.selectedOfficeList() == null) || (!(this.isEqual()) && !(this.isDeficent()) && !(this.isRedundant()))) {
                        alert("Something is not right");
                        return false;
                    }
                    else {
                        nts.uk.ui.windows.setShared("exportDataDetails", this.exportDataDetails(), true);
                        nts.uk.ui.windows.close();
                        alert("Exported: " + this.exportDataDetails());
                        return true;
                    }
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
})(qpp018 || (qpp018 = {}));
