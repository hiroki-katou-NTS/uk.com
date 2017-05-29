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
            selectedMaxManageSemiVacation: KnockoutObservable<string>;
            maxDayReferenceList: KnockoutObservableArray<EnumertionModel>;
            selectedMaxNumberSemiVacation: KnockoutObservable<string>;
            maxNumberCompany: KnockoutObservable<string>;
            maxGrantDay: KnockoutObservable<string>;
            maxRemainingDay: KnockoutObservable<string>;
            numberYearRetain: KnockoutObservable<string>;
            enableMaxNumberCompany: KnockoutObservable<boolean>;
            
            permissionList: KnockoutObservableArray<EnumertionModel>;
            selectedPermission: KnockoutObservable<string>;
            preemptionPermitList: KnockoutObservableArray<EnumertionModel>;
            selectedPreemptionPermit: KnockoutObservable<string>;
            
            displayDivisionList: KnockoutObservableArray<EnumertionModel>;
            selectedNumberRemainingYearly: KnockoutObservable<string>;
            selectedNextAnunalVacation: KnockoutObservable<string>;
            
            selectedTimeManagement: KnockoutObservable<string>;
            vacationTimeUnitList: KnockoutObservableArray<EnumertionModel>;
            enableTimeUnit: KnockoutObservable<boolean>;
            selectedVacationTimeUnit: KnockoutObservable<string>;
            selectedManageUpperLimitDayVacation: KnockoutObservable<string>;
            enableManageUpperLimit: KnockoutObservable<boolean>;
            selectedMaxDayVacation: KnockoutObservable<string>;
            timeMaxNumberCompany: KnockoutObservable<string>;
            isEnoughTimeOneDay: KnockoutObservable<boolean>;
            
            constructor() {
                let self = this;
                
                self.textEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    width: "50px",
                    textalign: "right"
                }));
                // 年休の管理
                self.manageDistinctList = ko.observableArray([
                    {code: "YES", name: "管理する"},
                    {code: "NO", name: "管理しない"}
                ]);
                self.selectedAnnualManage = ko.observable(ManageDistinct.YES);
                self.enableAnnualVacation = ko.observable(true);
                
                // 年次有給休暇の扱い
                self.selectedAddAttendanceDay = ko.observable(ManageDistinct.YES);
                self.selectedMaxManageSemiVacation = ko.observable(ManageDistinct.YES);
                self.maxDayReferenceList = ko.observableArray([
                    {code: "CompanyUniform", name: "会社一律"},
                    {code: "ReferAnnualGrantTable", name: "年休付与テーブルを参照"}
                ]);
                self.selectedMaxNumberSemiVacation = ko.observable(MaxDayReference.CompanyUniform);
                self.maxNumberCompany = ko.observable("");
                self.maxGrantDay = ko.observable("");
                self.maxRemainingDay = ko.observable("");
                self.numberYearRetain = ko.observable("");
                self.enableMaxNumberCompany = ko.observable(true);
                
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
                self.selectedNextAnunalVacation = ko.observable(DisplayDivision.Indicate);
                
                // 時間年休
                self.selectedTimeManagement = ko.observable(ManageDistinct.YES);
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
                self.timeMaxNumberCompany = ko.observable("");
                self.isEnoughTimeOneDay = ko.observable(true);
                
                // SUBSCRIBE
                self.selectedAnnualManage.subscribe(function(value) {
                    self.enableAnnualVacation(value == ManageDistinct.YES);
                    self.clearError();
                    self.initInput();
                });
                
                self.selectedMaxNumberSemiVacation.subscribe(function(value) {
                    self.enableMaxNumberCompany(value == MaxDayReference.CompanyUniform
                        && self.enableAnnualVacation());
                    if (value != MaxDayReference.CompanyUniform) {
                        $('#max-number-company').ntsError('clear');
                        self.maxNumberCompany("");
                        
                        $('#time-max-day-company').ntsError('clear');
                        self.timeMaxNumberCompany("");
                    }
                });
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
            
            private save(): void {
                let self = this;
                let dfd = $.Deferred();
                if (!self.validate()) {
                    return;
                }
                let command = self.toJsObject();
                service.save(command).done(function() {
                    self.loadSetting().done(function(res) {
                        // Msg_15
                        nts.uk.ui.dialog.alert("年休設定の登録");
//                        nts.uk.ui.dialog.alert(nts.uk.resource.getMessage('Msg_15'));
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
            
            private backSetupVacation(): void {
                nts.uk.request.jump("/view/kmf/001/a/index.xhtml", {});
            }
            
            private toJsObject(): any {
                let self = this;
                let command: any = {};
                command.annualManage = self.selectedAnnualManage();
                
                let setting: any = {};
                setting.addAttendanceDay = self.selectedAddAttendanceDay();
                setting.maxManageSemiVacation = self.selectedMaxManageSemiVacation();
                setting.maxNumberSemiVacation = self.selectedMaxNumberSemiVacation();
                setting.maxNumberCompany = self.maxNumberCompany();
                setting.maxGrantDay = self.maxGrantDay();
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
            
            private initInput(): void {
                let self = this;
                self.maxNumberCompany("");
                self.maxGrantDay("");
                self.maxRemainingDay("");
                self.numberYearRetain("");
                self.timeMaxNumberCompany("");
            }
            
            private validate(): boolean {
                let self = this;
                if (self.enableAnnualVacation()) {
                    if (self.enableMaxNumberCompany()) {
                        $('#max-number-company').ntsEditor('validate');
                    }
                    $('#max-grant-day').ntsEditor('validate');
                    $('#max-remaining-day').ntsEditor('validate');
                    $('#number-year-retain').ntsEditor('validate');
                    $('#time-max-day-company').ntsEditor('validate');
                }
                if ($('.nts-input').ntsError('hasError')) {
                    return false;
                }
                return true;
            }
            
            private clearError(): void {
                if (!$('.nts-input').ntsError('hasError')) {
                    return;
                }
                $('#max-number-company').ntsError('clear');
                $('#max-grant-day').ntsError('clear');
                $('#max-remaining-day').ntsError('clear');
                $('#number-year-retain').ntsError('clear');
                $('#time-max-day-company').ntsError('clear');
            }
        }
    }
}