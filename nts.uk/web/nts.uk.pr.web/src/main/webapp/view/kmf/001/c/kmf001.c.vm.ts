module nts.uk.pr.view.kmf001.c {
    export module viewmodel {
        
        import EnumertionModel = service.model.EnumerationModel;
        
        export class ScreenModel {
            
            manageStatus: KnockoutObservableArray<EnumertionModel>;
            selectedAnnualManage: KnockoutObservable<number>;
            selectedAddAttendanceDay: KnockoutObservable<number>;
            selectedMaxSemiVacation: KnockoutObservable<number>;
            permissionList: KnockoutObservableArray<EnumertionModel>;
            selectedPermission: KnockoutObservable<number>;
            yearRoundRemainingList: KnockoutObservableArray<EnumertionModel>;
            selectedNumberRemainingOfYear: KnockoutObservable<number>;
            selectedNextGrantAnunalVacation: KnockoutObservable<number>;
            yearVacationPriorityList: KnockoutObservableArray<EnumertionModel>;
            selectedYearVacation: KnockoutObservable<number>;
            selectedTimeManagement: KnockoutObservable<number>;
            selectedManageUpperLimitDayVacation: KnockoutObservable<number>;
            
            enableAnnualVacation: KnockoutObservable<boolean>;
            
            constructor() {
                let self = this;
                self.manageStatus = ko.observableArray([
                    {value: 0, name: "管理する"},
                    {value: 1, name: "管理しない"}
                ]);
                self.selectedAnnualManage = ko.observable(0);
                self.selectedAddAttendanceDay = ko.observable(0);
                self.selectedMaxSemiVacation = ko.observable(0);
                self.permissionList = ko.observableArray([
                    {value: 0, name: "許可する"},
                    {value: 1, name: "許可しない"}
                ]);
                self.selectedPermission = ko.observable(0);
                self.yearRoundRemainingList = ko.observableArray([
                    {value: 0, name: "表示する"},
                    {value: 1, name: "表示しない"}
                ]);
                self.selectedNumberRemainingOfYear = ko.observable(0);
                self.selectedNextGrantAnunalVacation = ko.observable(0);
                
                self.yearVacationPriorityList = ko.observableArray([
                    {value: 0, name: "先入れ先し　"}
                ]);
                self.selectedYearVacation = ko.observable(0);
                self.selectedTimeManagement = ko.observable(0);
                self.selectedManageUpperLimitDayVacation = ko.observable(0);
                
                self.enableAnnualVacation = ko.computed(function () {
                    if (self.selectedAnnualManage() == 0) {
                        return true;
                    }
                    return false;
                }, self);
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