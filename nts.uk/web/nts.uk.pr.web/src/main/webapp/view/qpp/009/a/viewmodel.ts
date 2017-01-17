module qpp009.a.viewmodel {
    export class ScreenModel {
        targetYear: KnockoutObservable<number>;
        outputDivisions: KnockoutObservableArray<SelectionModel>;
        selectedDivision: KnockoutObservable<string>;
        detailItemsSetting: KnockoutObservable<DetailItemsSetting>;
        printSetting: KnockoutObservable<PrintSetting>;
        
        constructor() {
            this.targetYear = ko.observable(2016);
            this.outputDivisions = ko.observableArray([
                    new SelectionModel('UsuallyMonth', '通常月'),
                    new SelectionModel('PreliminaryMonth', '予備月')
                ]);
            this.selectedDivision = ko.observable('UsuallyMonth');
            this.detailItemsSetting = ko.observable(new DetailItemsSetting());
            this.printSetting = ko.observable(new PrintSetting());
        }
        
        /**
         * Start srceen.
         */
        public start(): JQueryPromise<any>{
            var dfd = $.Deferred<any>();
            dfd.resolve();
            return dfd.promise();
        }
        
        /**
         * Print report.
         */
        public print() {
            alert('print report');
        }
        
    }
    
    /**
     * Class detail items setting.
     */
    export class DetailItemsSetting {
        isPrintDetailItem: KnockoutObservable<boolean>;
        isPrintTotalDepartment: KnockoutObservable<boolean>;
        isPrintDepHierarchy: KnockoutObservable<boolean>;
        isCalculateTotal: KnockoutObservable<boolean>;
        
        constructor() {
            this.isPrintDetailItem = ko.observable(false);
            this.isPrintTotalDepartment = ko.observable(false);
            this.isPrintDepHierarchy = ko.observable(false);
            this.isCalculateTotal = ko.observable(false);
        }
    }
    
    /**
     * Class print setting.
     */
    export class PrintSetting {
        specifyBreakPageList: KnockoutObservableArray<SelectionModel>;
        selectedBreakPageCode: KnockoutObservable<string>;
        specifyBreakPageHierarchyList: KnockoutObservableArray<SelectionModel>;
        selectedBreakPageHierarchyCode: KnockoutObservable<string>;
        use2000yenSelection: KnockoutObservableArray<SelectionModel>;
        selectedUse2000yen: KnockoutObservable<string>;
        isBreakPageHierarchy: KnockoutObservable<boolean>;
        
        constructor() {
            this.specifyBreakPageList = ko.observableArray([
                    new SelectionModel('None', 'なし'),
                    new SelectionModel('EveryEmp', '社員毎'),
                    new SelectionModel('EveryDep', '部門ごと'),
                    new SelectionModel('EveryDepHierarchy', '部門階層'),
                ])
            this.selectedBreakPageCode = ko.observable('None');
            this.specifyBreakPageHierarchyList = ko.observableArray([
                    new SelectionModel('1', '1'),new SelectionModel('2', '2'),new SelectionModel('3', '3'),
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
            this.isBreakPageHierarchy = ko.computed(function(){
               return self.selectedBreakPageCode() == 'EveryDepHierarchy';
            });
        }
    }
        /**
     * Class 出力区分.
     */
    export class SelectionModel {
        code: string;
        name: string;
        
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}