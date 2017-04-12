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
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    this.isUsuallyAMonth = ko.observable(true);
                                    this.isPreliminaryMonth = ko.observable(true);
                                    this.startYearMonth = ko.observable('2016/12');
                                    this.endYearMonth = ko.observable('2017/03');
                                    this.enable = ko.observable(true);
                                    this.selectedOutputFormat = ko.observable('1');
                                    this.outputFormatType = ko.observableArray([
                                        new SelectionModel('1', '明細一覧表'),
                                        new SelectionModel('2', '明細累計表')
                                    ]);
                                    this.outputItemSetting = ko.observableArray([]);
                                    this.selectedOutputSetting = ko.observable('1');
                                    this.isVerticalLine = ko.observable(true);
                                    this.isHorizontalRuledLine = ko.observable(true);
                                    this.pageBreakSetting = ko.observableArray([
                                        new SelectionModel('1', 'なし'),
                                        new SelectionModel('2', '社員毎'),
                                        new SelectionModel('3', '部門毎'),
                                        new SelectionModel('4', '部門階層')
                                    ]);
                                    this.selectedpageBreakSetting = ko.observable('1');
                                    this.departmentHierarchyList = ko.observableArray([
                                        new SelectionModel('1', '1'), new SelectionModel('2', '2'), new SelectionModel('3', '3'),
                                        new SelectionModel('4', '4'), new SelectionModel('5', '5'), new SelectionModel('6', '6'),
                                        new SelectionModel('7', '7'), new SelectionModel('8', '8'), new SelectionModel('9', '9')
                                    ]);
                                    this.selectedHierachy = ko.observable('1');
                                    this.outputLanguage = ko.observableArray([
                                        new SelectionModel('1', '日本語'),
                                        new SelectionModel('2', '英語')
                                    ]);
                                    this.selectedOutputLanguage = ko.observable('1');
                                    var self = this;
                                    this.isDepartmentHierarchy = ko.computed(function () {
                                        return self.selectedpageBreakSetting() == '4';
                                    });
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
                                ScreenModel.prototype.loadAllOutputSetting = function () {
                                    var dfd = $.Deferred();
                                    var self = this;
                                    a.service.findAllSalaryOutputSetting().done(function (data) {
                                        self.outputItemSetting(data);
                                        dfd.resolve();
                                    }).fail(function (res) {
                                        nts.uk.ui.dialog.alert(res.message);
                                        dfd.reject();
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.openPrintSettingDialog = function () {
                                    // Set parent value
                                    nts.uk.ui.windows.setShared("data", "nothing");
                                    nts.uk.ui.windows.sub.modal("/view/qpp/007/b/index.xhtml", { title: "印刷設定", dialogClass: 'no-close' }).onClosed(function () {
                                        // Get child value
                                        var returnValue = nts.uk.ui.windows.getShared("childData");
                                    });
                                };
                                ScreenModel.prototype.openSalaryOuputSettingDialog = function () {
                                    nts.uk.ui.windows.sub.modal("/view/qpp/007/c/index.xhtml", { title: "出力項目の設定", dialogClass: 'no-close' }).onClosed(function () {
                                        // Get child value
                                    });
                                };
                                ScreenModel.prototype.saveAsPdf = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    var command = {};
                                    a.service.saveAsPdf(command).done(function () {
                                        dfd.resolve();
                                    }).fail(function (res) {
                                        nts.uk.ui.dialog.alert(res.message);
                                    });
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            /**
                             *  Class Page Break setting
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
