module nts.uk.pr.view.kmf001.c {
    export module viewmodel {
        
        import EnumertionModel = service.model.EnumerationModel;
        
        export class ScreenModel {
            
            manageDistinctList: KnockoutObservableArray<EnumertionModel>;
            selectedAnnualManage: KnockoutObservable<number>;
            enableAnnualVacation: KnockoutObservable<boolean>;
            
            selectedAddAttendanceDay: KnockoutObservable<number>;
            selectedMaxManageSemiVacation: KnockoutObservable<number>;
            maxDayReferenceList: KnockoutObservableArray<EnumertionModel>;
            selectedMaxNumberSemiVacation: KnockoutObservable<number>;
            enableMaxNumberCompany: KnockoutObservable<boolean>;
            
            permissionList: KnockoutObservableArray<EnumertionModel>;
            selectedPermission: KnockoutObservable<number>;
            preemptionPermitList: KnockoutObservableArray<EnumertionModel>;
            selectedPreemptionPermit: KnockoutObservable<number>;
            
            displayDivisionList: KnockoutObservableArray<EnumertionModel>;
            selectedNumberRemainingYearly: KnockoutObservable<number>;
            selectedNextAnunalVacation: KnockoutObservable<number>;
            
            selectedTimeManagement: KnockoutObservable<number>;
            vacationTimeUnitList: KnockoutObservableArray<EnumertionModel>;
            enableTimeUnit: KnockoutObservable<boolean>;
            selectedVacationTimeUnit: KnockoutObservable<number>;
            selectedManageUpperLimitDayVacation: KnockoutObservable<number>;
            enableManageUpperLimit: KnockoutObservable<boolean>;
            selectedMaxDayVacation: KnockoutObservable<number>;
            
            constructor() {
                let self = this;
                // 年休の管理
                self.manageDistinctList = ko.observableArray([
                    {value: 0, name: "管理する"},
                    {value: 1, name: "管理しない"}
                ]);
                self.selectedAnnualManage = ko.observable(0);
                self.enableAnnualVacation = ko.computed(function () {
                    return self.selectedAnnualManage() == 0;
                }, self);
                
                // 年次有給休暇の扱い
                self.selectedAddAttendanceDay = ko.observable(0);
                self.selectedMaxManageSemiVacation = ko.observable(0);
                self.maxDayReferenceList = ko.observableArray([
                    {value: 0, name: "会社一律"},
                    {value: 1, name: "年休付与テーブルを参照"}
                ]);
                self.selectedMaxNumberSemiVacation = ko.observable(0);
                self.enableMaxNumberCompany = ko.computed(function() {
                    return self.selectedMaxNumberSemiVacation() == 0 && self.enableAnnualVacation();
                }, self);
                
                // 年休取得の設定
                self.permissionList = ko.observableArray([
                    {value: 0, name: "許可する"},
                    {value: 1, name: "許可しない"}
                ]);
                self.selectedPermission = ko.observable(0);
                self.preemptionPermitList = ko.observableArray([
                    {value: 0, name: "先入れ先出し"},
                    {value: 1, name: "後入れ先出し"}
                ]);
                self.selectedPreemptionPermit = ko.observable(0);
                
                // 表示設定
                self.displayDivisionList = ko.observableArray([
                    {value: 0, name: "表示する"},
                    {value: 1, name: "表示しない"}
                ]);
                self.selectedNumberRemainingYearly = ko.observable(0);
                self.selectedNextAnunalVacation = ko.observable(0);
                
                // 時間年休
                self.selectedTimeManagement = ko.observable(0);
                self.enableTimeUnit = ko.computed(function() {
                    return self.selectedTimeManagement() == 0 && self.enableAnnualVacation();
                }, self);
                self.vacationTimeUnitList = ko.observableArray([
                    {value: 0, name: "1分"},
                    {value: 1, name: "15分"},
                    {value: 2, name: "30分"},
                    {value: 3, name: "1時間"},
                    {value: 4, name: "2時間"}
                ]);
                self.selectedVacationTimeUnit = ko.observable(0);
                self.enableManageUpperLimit = ko.computed(function() {
                    return self.enableAnnualVacation() && self.enableMaxNumberCompany()
                    && self.enableTimeUnit();
                }, self);
                self.selectedManageUpperLimitDayVacation = ko.observable(0);
                self.selectedMaxDayVacation = ko.observable(0);
            }
            
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                dfd.resolve();
                return dfd.promise();
            }
        }
        
        export class AnnualPaidLeaveModel {
            
        }
    }
}