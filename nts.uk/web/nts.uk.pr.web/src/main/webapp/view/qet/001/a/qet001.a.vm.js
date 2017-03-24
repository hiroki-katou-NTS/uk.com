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
                }
                ScreenModel.prototype.start = function () {
                    var dfd = $.Deferred();
                    var self = this;
                    $.when(self.loadAllOutputSetting()).done(function () {
                        dfd.resolve();
                    });
                    return dfd.promise();
                };
                /**
                 * Load all output setting.
                 */
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
                /**
                 * Go To Output setting dialog.
                 */
                ScreenModel.prototype.goToOutputSetting = function () {
                    nts.uk.ui.windows.setShared('selectedCode', this.outputSettingSelectedCode(), true);
                    nts.uk.ui.windows.setShared('outputSettings', this.outputSettings(), true);
                    var self = this;
                    nts.uk.ui.windows.sub.modal("/view/qet/001/b/index.xhtml", { title: "出カ項目の設定" }).onClosed(function () {
                        if (nts.uk.ui.windows.getShared('isHasUpdate')) {
                            // Reload output setting list.
                            self.loadAllOutputSetting();
                        }
                    });
                };
                /**
                 * Open Aggregate item dialog.
                 */
                ScreenModel.prototype.goToAggregateItemPage = function () {
                    nts.uk.ui.windows.sub.modeless("/view/qet/001/i/index.xhtml", { title: "明細書項目の集約設定" });
                };
                /**
                 * Print report.
                 */
                ScreenModel.prototype.print = function () {
                    // Validate.
                    var self = this;
                    if (self.targetYear() == null || self.targetYear().toString() == '') {
                        // TODO: Check employee list.
                        // TODO: Add error message '未入力エラー'.
                        nts.uk.ui.dialog.alert('未入力エラー');
                        return;
                    }
                    // Print.
                    a.service.printReport(self).done(function () { }).fail(function (res) {
                        nts.uk.ui.dialog.alert(res.message);
                    });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            /**
             * 登録済みの出力項目設定
             */
            var OutputSetting = (function () {
                function OutputSetting(code, name) {
                    this.code = code;
                    this.name = name;
                }
                return OutputSetting;
            }());
            viewmodel.OutputSetting = OutputSetting;
            /**
             * 出力するレイアウト.
             */
            var LayoutOutput = (function () {
                function LayoutOutput() {
                }
                /**
                 * 賃金台帳（A4横1ページ）を出力する
                 */
                LayoutOutput.ONE_PAGE = 'OnePage';
                /**
                 * 賃金一覧表を出力する => print with new layout.
                 */
                LayoutOutput.NEW_LAYOUT = 'NewLayout';
                return LayoutOutput;
            }());
            viewmodel.LayoutOutput = LayoutOutput;
            /**
             * 出力する項目の選択
             */
            var OutputType = (function () {
                function OutputType() {
                }
                /**
                 * 明細書項目を出力する
                 */
                OutputType.MASTER_ITEMS = 'MasterItems';
                /**
                 * 明細書の集約項目を出力する
                 */
                OutputType.OUTPUT_SETTING_ITEMS = 'OutputSettingItems';
                return OutputType;
            }());
            viewmodel.OutputType = OutputType;
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qet001.a || (qet001.a = {}));
})(qet001 || (qet001 = {}));
