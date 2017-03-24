var qpp009;
(function (qpp009) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.targetYear = ko.observable(2016);
                    this.outputDivisions = ko.observableArray([
                        new SelectionModel('UsuallyMonth', '通常月'),
                        new SelectionModel('PreliminaryMonth', '予備月')
                    ]);
                    this.selectedDivision = ko.observable('UsuallyMonth');
                    this.detailItemsSetting = ko.observable(new DetailItemsSetting());
                    this.printSetting = ko.observable(new PrintSetting());
                    this.yearMonth = ko.observable('2016/12');
                }
                ScreenModel.prototype.start = function () {
                    var dfd = $.Deferred();
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.printData = function () {
                    a.service.printService(this).done(function () {
                    })
                        .fail(function (res) {
                        nts.uk.ui.dialog.alert(res.message);
                    });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var DetailItemsSetting = (function () {
                function DetailItemsSetting() {
                    this.isPrintDetailItem = ko.observable(false);
                    this.isPrintTotalOfDepartment = ko.observable(true);
                    this.isPrintDepHierarchy = ko.observable(true);
                    this.isCalculateTotal = ko.observable(true);
                    this.AccumulatedLevelList = ko.observableArray([
                        new SelectionDto(1, '1階層'), new SelectionDto(2, '2階層'), new SelectionDto(3, '3階層'),
                        new SelectionDto(4, '4階層'), new SelectionDto(5, '5階層'), new SelectionDto(6, '6階層'),
                        new SelectionDto(7, '7階層'), new SelectionDto(8, '8階層'), new SelectionDto(9, '9階層')
                    ]);
                    this.selectedLevels = ko.observableArray([1, 2, 3, 8, 9]);
                }
                return DetailItemsSetting;
            }());
            viewmodel.DetailItemsSetting = DetailItemsSetting;
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
                        new SelectionDto(2, '含めない'),
                    ]);
                    this.selectedUse2000yen = ko.observable(1);
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
