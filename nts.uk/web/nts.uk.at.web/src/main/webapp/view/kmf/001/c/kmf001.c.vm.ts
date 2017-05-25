module nts.uk.pr.view.kmf001.c {
    export module viewmodel {
        
        import EnumertionModel = service.model.EnumerationModel;
        import ManageDistinct = service.model.ManageDistinct;
        import MaxDayReference = service.model.MaxDayReference;
        import ApplyPermission = service.model.ApplyPermission;
        import PreemptionPermit = service.model.PreemptionPermit;
        import DisplayDivision = service.model.DisplayDivision;
        import TimeUnit = service.model.TimeUnit;
        
        export class ScreenModel {
            
            textEditorOption: KnockoutObservable<any>;
            
            manageDistinctList: KnockoutObservableArray<EnumertionModel>;
            selectedAnnualManage: KnockoutObservable<string>;
            enableAnnualVacation: KnockoutObservable<boolean>;
            
            selectedAddAttendanceDay: KnockoutObservable<string>;
            selectedMaxManageSemiVacation: KnockoutObservable<number>;
            maxDayReferenceList: KnockoutObservableArray<EnumertionModel>;
            selectedMaxNumberSemiVacation: KnockoutObservable<number>;
            maxNumberCompany: KnockoutObservable<number>;
            maxGrantDay: KnockoutObservable<number>;
            maxRemainingDay: KnockoutObservable<number>;
            numberYearRetain: KnockoutObservable<number>;
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
            timeMaxNumberCompany: KnockoutObservable<number>;
            isEnoughTimeOneDay: KnockoutObservable<boolean>;
            
            constructor() {
                let self = this;
                
                self.textEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    width: "50px",
                    textmode: "text",
                    textalign: "right"
                }));
                // 年休の管理
                self.manageDistinctList = ko.observableArray([
                    {code: "YES", name: "管理する"},
                    {code: "NO", name: "管理しない"}
                ]);
                self.selectedAnnualManage = ko.observable(ManageDistinct.YES);
                self.enableAnnualVacation = ko.computed(function () {
                    return self.selectedAnnualManage() == ManageDistinct.YES;
                }, self);
                
                // 年次有給休暇の扱い
                self.selectedAddAttendanceDay = ko.observable(ManageDistinct.YES);
                self.selectedMaxManageSemiVacation = ko.observable(ManageDistinct.YES);
                self.maxDayReferenceList = ko.observableArray([
                    {code: "CompanyUniform", name: "会社一律"},
                    {code: "ReferAnnualGrantTable", name: "年休付与テーブルを参照"}
                ]);
                self.selectedMaxNumberSemiVacation = ko.observable(MaxDayReference.CompanyUniform);
                self.maxNumberCompany = ko.observable(null);
                self.maxGrantDay = ko.observable(null);
                self.maxRemainingDay = ko.observable(null);
                self.numberYearRetain = ko.observable(null);
                self.enableMaxNumberCompany = ko.computed(function() {
                    return self.selectedMaxNumberSemiVacation() == MaxDayReference.CompanyUniform
                    && self.enableAnnualVacation();
                }, self);
                
                // 年休取得の設定
                self.permissionList = ko.observableArray([
                    {code: "ALLOW", name: "許可する"},
                    {code: "NOT_ALLOW", name: "許可しない"}
                ]);
                self.selectedPermission = ko.observable(ApplyPermission.ALLOW);
                self.preemptionPermitList = ko.observableArray([
                    {code: "FIFO", name: "先入れ先出し"},
                    {code: "LIFO", name: "後入れ先出し"}
                ]);
                self.selectedPreemptionPermit = ko.observable(PreemptionPermit.FIFO);
                
                // 表示設定
                self.displayDivisionList = ko.observableArray([
                    {code: "Indicate", name: "表示する"},
                    {code: "Notshow", name: "表示しない"}
                ]);
                self.selectedNumberRemainingYearly = ko.observable(DisplayDivision.Indicate);
                self.selectedNextAnunalVacation = ko.observable(1);
                
                // 時間年休
                self.selectedTimeManagement = ko.observable(1);
                self.enableTimeUnit = ko.computed(function() {
                    return self.selectedTimeManagement() == ManageDistinct.YES && self.enableAnnualVacation();
                }, self);
                self.vacationTimeUnitList = ko.observableArray([
                    {code: "OneMinute", name: "1分"},
                    {code: "FifteenMinute", name: "15分"},
                    {code: "ThirtyMinute", name: "30分"},
                    {code: "OneHour", name: "1時間"},
                    {code: "TwoHour", name: "2時間"}
                ]);
                self.selectedVacationTimeUnit = ko.observable(TimeUnit.OneMinute);
                self.enableManageUpperLimit = ko.computed(function() {
                    return self.enableAnnualVacation() && self.enableMaxNumberCompany()
                    && self.enableTimeUnit();
                }, self);
                self.selectedManageUpperLimitDayVacation = ko.observable(ManageDistinct.YES);
                self.selectedMaxDayVacation = ko.observable(TimeUnit.OneMinute);
                self.timeMaxNumberCompany = ko.observable(null);
                self.isEnoughTimeOneDay = ko.observable(true);
            }
            
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                $.when(self.loadSetting()).done(function(res) {
                    if (res) {
                        self.initUI(res);
                    }
                    dfd.resolve();
                });
                return dfd.promise();
            }
            
            private update(): void {
                let self = this;
                let dfd = $.Deferred();
                let command = self.toJsObject();
                service.update(command).done(function() {
                    self.loadSetting().done(function(res) {
                        if (res) {
                            self.initUI(res);
                        }
                        dfd.resolve();
                    });
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
            }
            
            private loadSetting(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                service.find().done(function(res: any) {
                    if (res) {
                        self.initUI(res);
                    }
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
                return dfd.promise();
            }
            
            private toJsObject(): any {
                let self = this;
                let command: any = {};
                command.annualManage = self.selectedAnnualManage();
                
                let setting: any = {};
                setting.addAttendanceDay = self.selectedAddAttendanceDay();
                setting.maxManageSemiVacation = self.selectedMaxManageSemiVacation();
                setting.maxNumberSemiVacation = self.selectedMaxNumberSemiVacation();
                setting.maxNumberCompany = self.maxNumberCompany().toString();
                setting.maxGrantDay = self.maxGrantDay().toString();
                setting.maxRemainingDay = self.maxRemainingDay();
                setting.numberYearRetain = self.numberYearRetain();
                setting.preemptionAnnualVacation = self.selectedPermission();
                setting.preemptionYearLeave = self.selectedPreemptionPermit();
                setting.remainingNumberDisplay = self.selectedNumberRemainingYearly();
                setting.nextGrantDayDisplay = self.selectedNextAnunalVacation();
                setting.timeManageType = self.selectedTimeManagement();
                setting.timeUnit = self.selectedVacationTimeUnit();
                setting.manageMaxDayVacation = self.selectedManageUpperLimitDayVacation();
                setting.reference = self.selectedMaxDayVacation();
                setting.maxTimeDay = self.timeMaxNumberCompany();
                setting.isEnoughTimeOneDay = self.isEnoughTimeOneDay();
                command.setting = setting;
                return command;
            }
            
            private initUI(res: any): any {
                let self = this;
                self.selectedAnnualManage(res.annualManage);
                
                self.selectedAddAttendanceDay(res.setting.addAttendanceDay);
                self.selectedMaxManageSemiVacation(res.setting.maxManageSemiVacation);
                self.selectedMaxNumberSemiVacation(res.setting.maxNumberSemiVacation);
                self.maxNumberCompany(res.setting.maxNumberCompany);
                self.maxGrantDay(res.setting.maxGrantDay);
                self.maxRemainingDay(res.setting.maxRemainingDay);
                self.numberYearRetain(res.setting.numberYearRetain);
                self.selectedPermission(res.setting.preemptionAnnualVacation);
                self.selectedPreemptionPermit(res.setting.preemptionYearLeave);
                self.selectedNumberRemainingYearly(res.setting.remainingNumberDisplay);
                self.selectedNextAnunalVacation(res.setting.nextGrantDayDisplay);
                self.selectedTimeManagement(res.setting.timeManageType);
                self.selectedVacationTimeUnit(res.setting.timeUnit);
                self.selectedManageUpperLimitDayVacation(res.setting.manageMaxDayVacation);
                self.selectedMaxDayVacation(res.setting.reference);
                self.timeMaxNumberCompany(res.setting.maxTimeDay);
                self.isEnoughTimeOneDay(res.setting.isEnoughTimeOneDay);
            }
        }
    }
}