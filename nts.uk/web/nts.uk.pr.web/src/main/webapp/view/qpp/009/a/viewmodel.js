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
                ScreenModel.prototype.print = function () {
                    alert('print report');
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var DetailItemsSetting = (function () {
                function DetailItemsSetting() {
                    this.isPrintDetailItem = ko.observable(false);
                    this.isPrintTotalDepartment = ko.observable(false);
                    this.isPrintDepHierarchy = ko.observable(false);
                    this.isCalculateTotal = ko.observable(false);
                }
                return DetailItemsSetting;
            }());
            viewmodel.DetailItemsSetting = DetailItemsSetting;
            var PrintSetting = (function () {
                function PrintSetting() {
                    this.specifyBreakPageList = ko.observableArray([
                        new SelectionModel('None', 'なし'),
                        new SelectionModel('EveryEmp', '社員毎'),
                        new SelectionModel('EveryDep', '部門ごと'),
                        new SelectionModel('EveryDepHierarchy', '部門階層'),
                    ]);
                    this.selectedBreakPageCode = ko.observable('None');
                    this.specifyBreakPageHierarchyList = ko.observableArray([
                        new SelectionModel('1', '1'), new SelectionModel('2', '2'), new SelectionModel('3', '3'),
                        new SelectionModel('4', '4'), new SelectionModel('5', '5'), new SelectionModel('6', '6'),
                        new SelectionModel('7', '7'), new SelectionModel('8', '8'), new SelectionModel('9', '9')
                    ]);
                    this.selectedBreakPageHierarchyCode = ko.observable('1');
                    this.use2000yenSelection = ko.observableArray([
                        new SelectionModel('Include', '含める'),
                        new SelectionModel('NotInclude', '含めない'),
                    ]);
                    this.selectedUse2000yen = ko.observable('NotInclude');
                    var self = this;
                    this.isBreakPageHierarchy = ko.computed(function () {
                        return self.selectedBreakPageCode() == 'EveryDepHierarchy';
                    });
                }
                return PrintSetting;
            }());
            viewmodel.PrintSetting = PrintSetting;
            var SelectionModel = (function () {
                function SelectionModel(code, name) {
                    this.code = code;
                    this.name = name;
                }
                return SelectionModel;
            }());
            viewmodel.SelectionModel = SelectionModel;
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qpp009.a || (qpp009.a = {}));
})(qpp009 || (qpp009 = {}));
//# sourceMappingURL=viewmodel.js.map