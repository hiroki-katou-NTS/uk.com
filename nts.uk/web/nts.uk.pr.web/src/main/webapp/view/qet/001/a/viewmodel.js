var qet001;
(function (qet001) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.targetYear = ko.observable(2016);
                    this.isAggreatePreliminaryMonth = ko.observable(true);
                    this.layoutSelected = ko.observable(LayoutOutput.WAGE_LEDGER);
                    this.isPageBreakIndicator = ko.observable(false);
                    this.outputTypeSelected = ko.observable(OutputType.DETAIL_ITEM);
                    this.outputSettings = ko.observableArray([]);
                    this.outputSettingSelectedCode = ko.observable('');
                }
                ScreenModel.prototype.start = function () {
                    var dfd = $.Deferred();
                    var self = this;
                    $.when(self.loadAllOutputSetting()).done(function () {
                        dfd.resolve();
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.loadAllOutputSetting = function () {
                    var dfd = $.Deferred();
                    var self = this;
                    a.service.findOutputSettings().done(function (data) {
                        self.outputSettings(data);
                        dfd.resolve();
                    }).fail(function (res) {
                        nts.uk.ui.dialog.alert(res.message);
                        dfd.reject();
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.goToOutputSetting = function () {
                    nts.uk.ui.windows.setShared('selectedCode', this.outputSettingSelectedCode(), true);
                    nts.uk.ui.windows.setShared('outputSettings', this.outputSettings(), true);
                    var self = this;
                    nts.uk.ui.windows.sub.modal("/view/qet/001/b/index.xhtml", { title: "出カ項目の設定" }).onClosed(function () {
                        if (nts.uk.ui.windows.getShared('isHasUpdate')) {
                            self.loadAllOutputSetting();
                        }
                    });
                };
                ScreenModel.prototype.print = function () {
                    var self = this;
                    if (self.targetYear() == null || self.targetYear().toString() == '') {
                        nts.uk.ui.dialog.alert('未入力エラー');
                        return;
                    }
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var OutputSetting = (function () {
                function OutputSetting(code, name) {
                    this.code = code;
                    this.name = name;
                }
                return OutputSetting;
            }());
            viewmodel.OutputSetting = OutputSetting;
            var LayoutOutput = (function () {
                function LayoutOutput() {
                }
                LayoutOutput.WAGE_LEDGER = 0;
                LayoutOutput.WAGE_LIST = 1;
                return LayoutOutput;
            }());
            viewmodel.LayoutOutput = LayoutOutput;
            var OutputType = (function () {
                function OutputType() {
                }
                OutputType.DETAIL_ITEM = 0;
                OutputType.SUMMARY_DETAIL_ITEMS = 1;
                return OutputType;
            }());
            viewmodel.OutputType = OutputType;
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qet001.a || (qet001.a = {}));
})(qet001 || (qet001 = {}));
