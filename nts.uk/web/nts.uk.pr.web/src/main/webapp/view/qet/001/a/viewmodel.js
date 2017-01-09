var qet001;
(function (qet001) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.targetYear = ko.observable(null);
                    this.isAggreatePreliminaryMonth = ko.observable(true);
                    this.layoutSelected = ko.observable(LayoutOutput.WAGE_LEDGER);
                    this.isPageBreakIndicator = ko.observable(false);
                    this.outputTypeSelected = ko.observable(OutputType.DETAIL_ITEM);
                    this.outputSettings = ko.observableArray([new OutputSetting('SETTING1', 'setting 1'),
                        new OutputSetting('SETTING2', 'setting 2')]);
                    this.outputSettingSelectedCode = ko.observable('SETTING2');
                }
                ScreenModel.prototype.start = function () {
                    var dfd = $.Deferred();
                    dfd.resolve();
                    return dfd.promise();
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
