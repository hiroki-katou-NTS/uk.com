var qpp009;
(function (qpp009) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            class ScreenModel {
                constructor() {
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
                /**
                 * Start srceen.
                 */
                start() {
                    var dfd = $.Deferred();
                    dfd.resolve();
                    return dfd.promise();
                }
                /**
                 * Print report.
                 */
                printData() {
                    a.service.printService(this).done(function () {
                    })
                        .fail(function (res) {
                        nts.uk.ui.dialog.alert(res.message);
                    });
                }
            }
            viewmodel.ScreenModel = ScreenModel;
            /**
             * Class detail items setting.
             */
            class DetailItemsSetting {
                constructor() {
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
            }
            viewmodel.DetailItemsSetting = DetailItemsSetting;
            /**
             * Class print setting.
             */
            class PrintSetting {
                constructor() {
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
            }
            viewmodel.PrintSetting = PrintSetting;
            class SalaryChartResultViewModel {
                constructor(empCode, empName, paymentAmount, empDesignation, depDesignation, totalDesignation, depCode, depName, depPath) {
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
            }
            viewmodel.SalaryChartResultViewModel = SalaryChartResultViewModel;
            /**
         * Class 出力区分.
         */
            class SelectionModel {
                constructor(code, name) {
                    this.code = code;
                    this.name = name;
                }
            }
            viewmodel.SelectionModel = SelectionModel;
            class SelectionDto {
                constructor(code, name) {
                    this.code = code;
                    this.name = name;
                }
            }
            viewmodel.SelectionDto = SelectionDto;
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qpp009.a || (qpp009.a = {}));
})(qpp009 || (qpp009 = {}));
