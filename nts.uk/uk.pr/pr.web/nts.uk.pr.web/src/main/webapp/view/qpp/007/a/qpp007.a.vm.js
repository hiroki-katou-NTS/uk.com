var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qpp007;
                (function (qpp007) {
                    var a;
                    (function (a) {
                        var viewmodel;
                        (function (viewmodel) {
                            var NUMBER_OF_HIERACHY = 9;
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    this.isUsuallyAMonth = ko.observable(true);
                                    this.isPreliminaryMonth = ko.observable(false);
                                    this.startYearMonth = ko.observable(201612);
                                    this.endYearMonth = ko.observable(201703);
                                    this.selectedOutputFormat = ko.observable('1');
                                    this.outputFormatType = ko.observableArray([
                                        new SelectionModel('1', '明細一覧表'),
                                        new SelectionModel('2', '明細累計表')
                                    ]);
                                    this.outputSettings = ko.observableArray([]);
                                    this.selectedOutputSetting = ko.observable('1');
                                    this.isVerticalLine = ko.observable(false);
                                    this.isHorizontalRuledLine = ko.observable(false);
                                    this.pageBreakSetting = ko.observableArray([
                                        new SelectionModel('1', 'なし'),
                                        new SelectionModel('2', '社員毎'),
                                        new SelectionModel('3', '部門毎'),
                                        new SelectionModel('4', '部門階層')
                                    ]);
                                    this.selectedpageBreakSetting = ko.observable('1');
                                    this.departmentHierarchyList = ko.observableArray([]);
                                    for (var i = 0; i < NUMBER_OF_HIERACHY; i++) {
                                        this.departmentHierarchyList.push(new SelectionModel(i.toString(), (i + 1).toString()));
                                    }
                                    this.selectedHierachy = ko.observable('0');
                                    this.outputLanguage = ko.observableArray([
                                        new SelectionModel('1', '日本語'),
                                        new SelectionModel('2', '英語')
                                    ]);
                                    this.selectedOutputLanguage = ko.observable('1');
                                    var self = this;
                                    this.isDepartmentHierarchy = ko.computed(function () {
                                        return self.selectedpageBreakSetting() == '4';
                                    });
                                    self.hourMinuteOutputClassification = ko.observableArray([
                                        new SelectionModel('0', '時間'),
                                        new SelectionModel('1', '分')
                                    ]);
                                    self.selectedHourMinuteOutputClassification = ko.observable('0');
                                }
                                /**
                                 * Start srceen.
                                 */
                                ScreenModel.prototype.start = function () {
                                    var dfd = $.Deferred();
                                    var self = this;
                                    $.when(self.loadAllOutputSetting()).done(function () {
                                        dfd.resolve();
                                    });
                                    return dfd.promise();
                                };
                                /**
                                 * Load all OutputSetting.
                                 */
                                ScreenModel.prototype.loadAllOutputSetting = function () {
                                    var dfd = $.Deferred();
                                    var self = this;
                                    a.service.findAllSalaryOutputSetting().done(function (data) {
                                        self.outputSettings(data);
                                        dfd.resolve();
                                    }).fail(function (res) {
                                        nts.uk.ui.dialog.alert(res.message);
                                        dfd.reject();
                                    });
                                    return dfd.promise();
                                };
                                /**
                                 * Open PrintSetting dialog
                                 */
                                ScreenModel.prototype.openPrintSettingDialog = function () {
                                    nts.uk.ui.windows.sub.modal("/view/qpp/007/b/index.xhtml", { title: "印刷設定", dialogClass: 'no-close' });
                                };
                                /**
                                 * Open SalaryOuputSetting dialog
                                 */
                                ScreenModel.prototype.openSalaryOuputSettingDialog = function () {
                                    var self = this;
                                    // Set parent value
                                    nts.uk.ui.windows.setShared("selectedCode", self.selectedOutputSetting());
                                    nts.uk.ui.windows.setShared("outputSettings", self.outputSettings());
                                    nts.uk.ui.windows.sub.modal("/view/qpp/007/c/index.xhtml", { title: "出力項目の設定", dialogClass: 'no-close' }).onClosed(function () {
                                        if (nts.uk.ui.windows.getShared('isSomethingChanged')) {
                                            // Reload output setting list.
                                            self.loadAllOutputSetting();
                                        }
                                    });
                                };
                                /**
                                 * Print selected Employee.
                                 */
                                ScreenModel.prototype.printSelectedEmployee = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    // Validate
                                    self.validate();
                                    if ($('.nts-input').ntsError('hasError')) {
                                        return;
                                    }
                                    var command = self.toJsObject();
                                    a.service.saveAsPdf(command).done(function () {
                                        dfd.resolve();
                                    }).fail(function (res) {
                                        nts.uk.ui.dialog.alert(res.message);
                                    });
                                };
                                /**
                                 * Output text.
                                 */
                                ScreenModel.prototype.outputText = function () {
                                    var self = this;
                                    // Validate
                                    self.validate();
                                    if ($('.nts-input').ntsError('hasError')) {
                                        return;
                                    }
                                };
                                /**
                                 * Collect data from model then convert to JsObject.
                                 */
                                ScreenModel.prototype.toJsObject = function () {
                                    var self = this;
                                    var command = {};
                                    command.outputFormatType = self.selectedOutputFormat();
                                    command.outputSettingCode = self.selectedOutputSetting();
                                    command.isVerticalLine = self.isVerticalLine();
                                    command.isHorizontalLine = self.isHorizontalRuledLine();
                                    command.outputLanguage = self.selectedOutputLanguage();
                                    command.pageBreakSetting = self.selectedpageBreakSetting();
                                    command.startDate = self.startYearMonth();
                                    command.endDate = self.endYearMonth();
                                    command.isNormalMonth = self.isUsuallyAMonth();
                                    command.isPreliminaryMonth = self.isPreliminaryMonth();
                                    if (self.isDepartmentHierarchy()) {
                                        command.hierarchy = self.selectedHierachy();
                                    }
                                    return command;
                                };
                                /**
                                * Clear all input errors.
                                */
                                ScreenModel.prototype.clearErrors = function () {
                                    if (nts.uk.ui._viewModel) {
                                        $('#start-ym').ntsError('clear');
                                        $('#end-ym').ntsError('clear');
                                    }
                                };
                                /**
                                * Validate all inputs.
                                */
                                ScreenModel.prototype.validate = function () {
                                    $('#start-ym').ntsEditor('validate');
                                    $('#end-ym').ntsEditor('validate');
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            /**
                             *  Class SelectionModel.
                             */
                            var SelectionModel = (function () {
                                function SelectionModel(code, name) {
                                    this.code = code;
                                    this.name = name;
                                }
                                return SelectionModel;
                            }());
                            viewmodel.SelectionModel = SelectionModel;
                        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
                    })(a = qpp007.a || (qpp007.a = {}));
                })(qpp007 = view.qpp007 || (view.qpp007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qpp007.a.vm.js.map