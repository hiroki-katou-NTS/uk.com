var qpp009;
(function (qpp009) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.outputDivisions = ko.observableArray([
                        new SelectionModel(0, '通常月'),
                        new SelectionModel(1, '予備月')
                    ]);
                    self.selectedDivision = ko.observable(0);
                    self.detailItemsSetting = ko.observable(new DetailItemsSetting());
                    self.printSetting = ko.observable(new PrintSetting());
                    self.yearMonth = ko.observable(parseInt(nts.uk.time.formatDate(new Date(), 'yyyyMM')));
                    self.japanYearmonth = ko.computed(function () {
                        return '(' + nts.uk.time.yearmonthInJapanEmpire(self.yearMonth()).toString() + ')';
                    });
                    self.detailLabel = ko.observable("※ " + nts.uk.time.formatDate(new Date(), "yyyy/MM/dd")
                        + " の部門構成で集計します.");
                }
                /**
                 * Start srceen.
                 */
                ScreenModel.prototype.start = function () {
                    var dfd = $.Deferred();
                    dfd.resolve();
                    return dfd.promise();
                };
                /**
                 * Print report.
                 */
                ScreenModel.prototype.printData = function () {
                    var self = this;
                    self.clearAllError();
                    // Validate
                    if (self.validate()) {
                        return;
                    }
                    // Print Report
                    a.service.printService(this).done(function () {
                    })
                        .fail(function (res) {
                        nts.uk.ui.dialog.alert(res.message);
                    });
                };
                ScreenModel.prototype.validate = function () {
                    var self = this;
                    var hasError = false;
                    // Validate year month
                    $('#date-picker').ntsEditor('validate');
                    if (self.detailItemsSetting().isPrintDepHierarchy()
                        && self.detailItemsSetting().selectedLevels().length < 1) {
                        $('#hierarchy-content').ntsError('set', '1~9階層 が選択されていません。');
                        hasError = true;
                    }
                    if (self.detailItemsSetting().isPrintDepHierarchy()
                        && self.detailItemsSetting().selectedLevels().length > 5) {
                        $('#hierarchy-content').ntsError('set', '部門階層が正しく指定されていません。');
                        hasError = true;
                    }
                    if (!self.detailItemsSetting().isPrintDepHierarchy() && self.printSetting().selectedBreakPageCode() == 4) {
                        $('#specify-break-page-select').ntsError('set', '設定が正しくありません。');
                        hasError = true;
                    }
                    if (self.detailItemsSetting().isPrintDepHierarchy() && self.printSetting().selectedBreakPageCode() == 4
                        && self.detailItemsSetting().selectedLevels().indexOf(self.printSetting().selectedBreakPageHierarchyCode()) < 0) {
                        $('#specify-break-page-hierarchy-select').ntsError('set', '設定が正しくありません。');
                        hasError = true;
                    }
                    // TODO: Validation relate to employee list.
                    return hasError || $('.nts-input').ntsError('hasError');
                };
                ScreenModel.prototype.clearAllError = function () {
                    $('#date-picker').ntsError('clear');
                    $('#hierarchy-content').ntsError('clear');
                    $('#specify-break-page-select').ntsError('clear');
                    $('#specify-break-page-hierarchy-select').ntsError('clear');
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            /**
             * Class detail items setting.
             */
            var DetailItemsSetting = (function () {
                function DetailItemsSetting() {
                    this.isPrintDetailItem = ko.observable(true);
                    this.isPrintTotalOfDepartment = ko.observable(false);
                    this.isPrintDepHierarchy = ko.observable(false);
                    this.isCalculateTotal = ko.observable(true);
                    this.AccumulatedLevelList = ko.observableArray([
                        new SelectionDto(1, '1階層'), new SelectionDto(2, '2階層'), new SelectionDto(3, '3階層'),
                        new SelectionDto(4, '4階層'), new SelectionDto(5, '5階層'), new SelectionDto(6, '6階層'),
                        new SelectionDto(7, '7階層'), new SelectionDto(8, '8階層'), new SelectionDto(9, '9階層')
                    ]);
                    this.selectedLevels = ko.observableArray([]);
                }
                return DetailItemsSetting;
            }());
            viewmodel.DetailItemsSetting = DetailItemsSetting;
            /**
             * Class print setting.
             */
            var PrintSetting = (function () {
                function PrintSetting() {
                    this.specifyBreakPageList = ko.observableArray([
                        new SelectionDto(1, 'なし'),
                        new SelectionDto(2, '社員毎'),
                        new SelectionDto(3, '部門ごと'),
                        new SelectionDto(4, '部門階層'),
                    ]);
                    this.selectedBreakPageCode = ko.observable(1);
                    this.specifyBreakPageHierarchyList = ko.observableArray([
                        new SelectionDto(1, '1'), new SelectionDto(2, '2'), new SelectionDto(3, '3'),
                        new SelectionDto(4, '4'), new SelectionDto(5, '5'), new SelectionDto(6, '6'),
                        new SelectionDto(7, '7'), new SelectionDto(8, '8'), new SelectionDto(9, '9')
                    ]);
                    this.selectedBreakPageHierarchyCode = ko.observable(1);
                    this.use2000yenSelection = ko.observableArray([
                        new SelectionDto(1, '含める'),
                        new SelectionDto(0, '含めない'),
                    ]);
                    this.selectedUse2000yen = ko.observable(0);
                    var self = this;
                    this.isBreakPageByAccumulated = ko.computed(function () {
                        return self.selectedBreakPageCode() == 4;
                    });
                }
                return PrintSetting;
            }());
            viewmodel.PrintSetting = PrintSetting;
            var SalaryChartResultViewModel = (function () {
                function SalaryChartResultViewModel(empCode, empName, paymentAmount, empDesignation, depDesignation, totalDesignation, depCode, depName, depPath) {
                    this.empCode = empCode;
                    this.empName = empName;
                    this.paymentAmount = paymentAmount;
                    this.empDesignation = empDesignation;
                    this.depDesignation = depDesignation;
                    this.totalDesignation = totalDesignation;
                    this.depCode = depCode;
                    this.depName = depName;
                    this.depPath = depPath;
                }
                return SalaryChartResultViewModel;
            }());
            viewmodel.SalaryChartResultViewModel = SalaryChartResultViewModel;
            /**
         * Class 出力区分.
         */
            var SelectionModel = (function () {
                function SelectionModel(code, name) {
                    this.code = code;
                    this.name = name;
                }
                return SelectionModel;
            }());
            viewmodel.SelectionModel = SelectionModel;
            var SelectionDto = (function () {
                function SelectionDto(code, name) {
                    this.code = code;
                    this.name = name;
                }
                return SelectionDto;
            }());
            viewmodel.SelectionDto = SelectionDto;
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qpp009.a || (qpp009.a = {}));
})(qpp009 || (qpp009 = {}));
//# sourceMappingURL=qpp009.a.vm.js.map