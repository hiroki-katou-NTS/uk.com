module qpp009.a.viewmodel {
    export class ScreenModel {
        targetYear: KnockoutObservable<number>;
        outputDivisions: KnockoutObservableArray<SelectionModel>;
        selectedDivision: KnockoutObservable<string>;
        detailItemsSetting: KnockoutObservable<DetailItemsSetting>;
        printSetting: KnockoutObservable<PrintSetting>;
        yearMonth: KnockoutObservable<string>;
        
        constructor() {
            this.targetYear = ko.observable(2016);
            this.outputDivisions = ko.observableArray<SelectionModel>([
                    new SelectionModel('UsuallyMonth', '通常月'),
                    new SelectionModel('PreliminaryMonth', '予備月')
                ]);
            this.selectedDivision = ko.observable('UsuallyMonth');
            this.detailItemsSetting = ko.observable(new DetailItemsSetting());
            this.printSetting = ko.observable(new PrintSetting());
            this.yearMonth=ko.observable('2016/12');
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
        public printData() {
            service.printService(this).done(function() {              
            })
            .fail(function(res) {
                nts.uk.ui.dialog.alert(res.message);
            })
        }
        
    }
    
    /**
     * Class detail items setting.
     */
    export class DetailItemsSetting {
        isPrintDetailItem: KnockoutObservable<boolean>;
        isPrintTotalOfDepartment: KnockoutObservable<boolean>;
        isPrintDepHierarchy: KnockoutObservable<boolean>;
        isCalculateTotal: KnockoutObservable<boolean>;
        AccumulatedLevelList: KnockoutObservableArray<SelectionDto>;
        selectedLevels: KnockoutObservableArray<number>;
        
        constructor() {
            this.isPrintDetailItem = ko.observable(false);
            this.isPrintTotalOfDepartment = ko.observable(true);
            this.isPrintDepHierarchy = ko.observable(true);
            this.isCalculateTotal = ko.observable(true);
            this.AccumulatedLevelList = ko.observableArray<SelectionDto>([
                    new SelectionDto(1, '1階層'),new SelectionDto(2, '2階層'),new SelectionDto(3, '3階層'),
                    new SelectionDto(4, '4階層'), new SelectionDto(5, '5階層'), new SelectionDto(6, '6階層'),
                    new SelectionDto(7, '7階層'), new SelectionDto(8, '8階層'), new SelectionDto(9, '9階層')
                ]);
            this.selectedLevels = ko.observableArray<number>([1, 2, 3, 8, 9]);
        }
    }
    
    /**
     * Class print setting.
     */
    export class PrintSetting {
        specifyBreakPageList: KnockoutObservableArray<SelectionDto>;
        selectedBreakPageCode: KnockoutObservable<number>;
        specifyBreakPageHierarchyList: KnockoutObservableArray<SelectionDto>;
        selectedBreakPageHierarchyCode: KnockoutObservable<number>;
        use2000yenSelection: KnockoutObservableArray<SelectionDto>;
        selectedUse2000yen: KnockoutObservable<number>;
        isBreakPageByAccumulated: KnockoutObservable<boolean>;
        
        constructor() {
            this.specifyBreakPageList = ko.observableArray<SelectionDto>([
                    new SelectionDto(1, 'なし'),
                    new SelectionDto(2, '社員毎'),
                    new SelectionDto(3, '部門ごと'),
                    new SelectionDto(4, '部門階層'),
                ])
            this.selectedBreakPageCode = ko.observable(1);
            this.specifyBreakPageHierarchyList = ko.observableArray<SelectionDto>([
                    new SelectionDto(1, '1'),new SelectionDto(2, '2'),new SelectionDto(3, '3'),
                    new SelectionDto(4, '4'), new SelectionDto(5, '5'), new SelectionDto(6, '6'),
                    new SelectionDto(7, '7'), new SelectionDto(8, '8'), new SelectionDto(9, '9')
                ]);
            this.selectedBreakPageHierarchyCode = ko.observable(1);
            this.use2000yenSelection = ko.observableArray<SelectionDto>([
                    new SelectionDto(1, '含める'),
                    new SelectionDto(0, '含めない'),
                ]);
            this.selectedUse2000yen = ko.observable(0);
            var self = this;
            this.isBreakPageByAccumulated = ko.computed(function(){
               return self.selectedBreakPageCode() == 4;
            });
        }
    }
    
    export class SalaryChartResultViewModel{
        empCode: string;
        empName: string;
        paymentAmount: number;
        empDesignation: string;
        depDesignation: string;
        totalDesignation: string;
        depCode: string;
        depName: string;
        depPath: string;
        constructor(empCode: string, empName: string, paymentAmount: number, empDesignation: string, 
        depDesignation: string, totalDesignation: string, depCode: string, depName: string, depPath: string){
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
    export class SelectionDto{
        code: number;
        name: string;
        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }     
    }
}