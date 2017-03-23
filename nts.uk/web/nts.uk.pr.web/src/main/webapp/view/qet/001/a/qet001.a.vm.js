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
                    this.layoutSelected = ko.observable(LayoutOutput.ONE_PAGE);
                    this.isPageBreakIndicator = ko.observable(false);
                    this.outputTypeSelected = ko.observable(OutputType.MASTER_ITEMS);
                    this.outputSettings = ko.observableArray([]);
                    this.outputSettingSelectedCode = ko.observable('');
                    var self = this;
                    self.japanTargetYear = ko.computed(function () {
                        return nts.uk.time.yearInJapanEmpire(self.targetYear()).toString();
                    });
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
                ScreenModel.prototype.goToAggregateItemPage = function () {
                    nts.uk.ui.windows.sub.modeless("/view/qet/001/i/index.xhtml", { title: "明細書項目の集約設定" });
                };
                ScreenModel.prototype.print = function () {
                    $('#target-year-input').ntsError('clear');
                    var self = this;
                    var hasError = false;
                    if (self.targetYear() == null || self.targetYear().toString() == '') {
                        $('#target-year-input').ntsError('set', '対象年度が入力されていません。');
                        hasError == true;
                    }
                    if (!hasError) {
                        return;
                    }
                    a.service.printReport(self).done(function () { }).fail(function (res) {
                        nts.uk.ui.dialog.alert(res.message);
                    });
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
                LayoutOutput.ONE_PAGE = 'OnePage';
                LayoutOutput.NEW_LAYOUT = 'NewLayout';
                return LayoutOutput;
            }());
            viewmodel.LayoutOutput = LayoutOutput;
            var OutputType = (function () {
                function OutputType() {
                }
                OutputType.MASTER_ITEMS = 'MasterItems';
                OutputType.OUTPUT_SETTING_ITEMS = 'OutputSettingItems';
                return OutputType;
            }());
            viewmodel.OutputType = OutputType;
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qet001.a || (qet001.a = {}));
})(qet001 || (qet001 = {}));
//# sourceMappingURL=qet001.a.vm.js.map