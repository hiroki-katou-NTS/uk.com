module nts.uk.pr.view.kmf001.c {
    export module viewmodel {
        
        import EnumertionModel = service.model.EnumerationModel;
        
        export class ScreenModel {
            numberEditorOption: KnockoutObservable<any>;
            
            halfIntegerEditorOption: KnockoutObservable<any>;
            
            manageDistinctList: KnockoutObservableArray<EnumertionModel>;
            selectedAnnualManage: KnockoutObservable<number>;
            enableAnnualVacation: KnockoutObservable<boolean>;
            
            selectedAddAttendanceDay: KnockoutObservable<number>;
            selectedMaxManageSemiVacation: KnockoutObservable<number>;
            maxDayReferenceList: KnockoutObservableArray<EnumertionModel>;
            selectedMaxNumberSemiVacation: KnockoutObservable<number>;
            maxNumberCompany: KnockoutObservable<string>;
            requiredMaxNumberCompany: KnockoutObservable<boolean>;
            maxGrantDay: KnockoutObservable<string>;
            maxRemainingDay: KnockoutObservable<string>;
            numberYearRetain: KnockoutObservable<string>;
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
            enableTimeSetting: KnockoutObservable<boolean>;
            selectedVacationTimeUnit: KnockoutObservable<number>;
            selectedManageUpperLimitDayVacation: KnockoutObservable<number>;
            selectedMaxDayVacation: KnockoutObservable<number>;
            timeMaxNumberCompany: KnockoutObservable<string>;
            requiredTimeMaxNumberCompany: KnockoutObservable<boolean>;
            enableTimeMaxNumberCompany: KnockoutObservable<boolean>;
            isEnoughTimeOneDay: KnockoutObservable<boolean>;
            
            // Data backup
            dataBackup: KnockoutObservable<any>;
            
            constructor() {
                let self = this;
                self.numberEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                    width: "50px",
                    textalign: "right"
                }));
                self.halfIntegerEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                    width: '50px',
                    textalign: 'right',
                    decimallength: 1
                }));
                // 年休の管理
                self.manageDistinctList = ko.observableArray([]);
                self.selectedAnnualManage = ko.observable(0);
                self.enableAnnualVacation = ko.computed(function() {
                    return self.selectedAnnualManage() == 1;
                }, self);
                
                // 年次有給休暇の扱い
                self.selectedAddAttendanceDay = ko.observable(0);
                self.selectedMaxManageSemiVacation = ko.observable(0);
                self.maxDayReferenceList = ko.observableArray([]);
                self.selectedMaxNumberSemiVacation = ko.observable(0);
                self.maxNumberCompany = ko.observable("");
                self.enableMaxNumberCompany = ko.computed(function() {
                    return self.selectedMaxNumberSemiVacation() == 0 && self.enableAnnualVacation();
                }, self);
                self.requiredMaxNumberCompany = ko.computed(function() {
                    return self.enableMaxNumberCompany() && self.selectedMaxManageSemiVacation() == 1;
                });
                self.maxGrantDay = ko.observable("");
                self.maxRemainingDay = ko.observable("");
                self.numberYearRetain = ko.observable("");
                
                // 年休取得の設定
                self.permissionList = ko.observableArray([]);
                self.selectedPermission = ko.observable(0);
                self.preemptionPermitList = ko.observableArray([]);
                self.selectedPreemptionPermit = ko.observable(0);
                
                // 表示設定
                self.displayDivisionList = ko.observableArray([]);
                self.selectedNumberRemainingYearly = ko.observable(0);
                self.selectedNextAnunalVacation = ko.observable(0);
                
                // 時間年休
                self.selectedTimeManagement = ko.observable(0);
                self.vacationTimeUnitList = ko.observableArray([]);
                self.enableTimeSetting = ko.computed(function() {
                    return self.selectedTimeManagement() == 1 && self.enableAnnualVacation();
                }, self);
                self.selectedVacationTimeUnit = ko.observable(0);
                self.selectedMaxDayVacation = ko.observable(0);
                self.selectedManageUpperLimitDayVacation = ko.observable(0);
                self.timeMaxNumberCompany = ko.observable("");
                self.requiredTimeMaxNumberCompany = ko.computed(function() {
                    return self.enableTimeSetting() && self.selectedManageUpperLimitDayVacation() == 1;
                });
                self.enableTimeMaxNumberCompany = ko.computed(function() {
                    return self.enableTimeSetting() && self.selectedMaxDayVacation() == 0;
                });
                self.isEnoughTimeOneDay = ko.observable(true);
                
                // Data backup
                self.dataBackup = ko.observable(null);
            }
            
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                $.when(self.loadManageDistinctEnums(), self.loadApplyPermissionEnums(), self.loadPreemptionPermitEnums(),
                        self.loadDisplayDivisionEnums(), self.loadTimeUnitEnums(), self.loadMaxDayReferenceEnums()).done(function() {
                    self.loadSetting();
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
                    self.loadSetting().done(function() {
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                        dfd.resolve();
                    });
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                });
            }
            
            private loadSetting(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                service.findSetting().done(function(res: any) {
                    if (res) {
                        self.initUI(res);
                    }
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                });
                return dfd.promise();
            }
            
            private backSetupVacation(): void {
                nts.uk.request.jump("/view/kmf/001/a/index.xhtml", {});
            }
            
            private toJsObject(): any {
                let self = this;
                let dataBackup = self.dataBackup();
                if (!dataBackup) {
                    dataBackup = self.defaultValue();
                }
                let command: any = {};
                
                command.annualManage = self.selectedAnnualManage();
                
                // Annual Setting
                command.addAttendanceDay = self.enableAnnualVacation() ? self.selectedAddAttendanceDay() : dataBackup.addAttendanceDay;
                command.maxManageSemiVacation = self.enableAnnualVacation() ? self.selectedMaxManageSemiVacation() : dataBackup.maxManageSemiVacation;
                command.maxNumberSemiVacation = self.enableAnnualVacation() ? self.selectedMaxNumberSemiVacation() : dataBackup.maxNumberSemiVacation;
                command.maxNumberCompany = self.enableMaxNumberCompany() ? self.maxNumberCompany() : dataBackup.maxNumberCompany;
                command.maxGrantDay = self.enableAnnualVacation() ? self.maxGrantDay() : dataBackup.maxGrantDay;
                command.maxRemainingDay = self.enableAnnualVacation() ? self.maxRemainingDay() : dataBackup.maxRemainingDay;
                command.numberYearRetain = self.enableAnnualVacation() ? self.numberYearRetain() : dataBackup.numberYearRetain;
                command.preemptionAnnualVacation = self.enableAnnualVacation() ? self.selectedPermission() : dataBackup.preemptionAnnualVacation;
                command.preemptionYearLeave = self.enableAnnualVacation() ? self.selectedPreemptionPermit() : dataBackup.preemptionYearLeave;
                command.remainingNumberDisplay = self.enableAnnualVacation() ? self.selectedNumberRemainingYearly() : dataBackup.remainingNumberDisplay;
                command.nextGrantDayDisplay = self.enableAnnualVacation() ? self.selectedNextAnunalVacation() : dataBackup.nextGrantDayDisplay;
                
                // Time Leave Setting
                command.timeManageType = self.enableAnnualVacation() ? self.selectedTimeManagement() : dataBackup.timeManageType;
                command.timeUnit = self.enableTimeSetting() ? self.selectedVacationTimeUnit() : dataBackup.timeUnit;
                command.manageMaxDayVacation = self.enableTimeSetting() ? self.selectedManageUpperLimitDayVacation() : dataBackup.manageMaxDayVacation;
                command.reference = self.enableTimeSetting() ? self.selectedMaxDayVacation() : dataBackup.reference;
                command.maxTimeDay = self.enableTimeMaxNumberCompany() ? self.timeMaxNumberCompany() : dataBackup.maxTimeDay;
                command.isEnoughTimeOneDay = self.enableTimeMaxNumberCompany() ? self.isEnoughTimeOneDay() : dataBackup.isEnoughTimeOneDay;
                
                return command;
            }
            
            private initUI(res: any): any {
                let self = this;
                
                // backup
                self.dataBackup(res);
                
                // set UI
                self.selectedAnnualManage(res.annualManage);
                
                // Annual Setting
                self.selectedAddAttendanceDay(res.addAttendanceDay);
                self.selectedMaxManageSemiVacation(res.maxManageSemiVacation);
                self.selectedMaxNumberSemiVacation(res.maxNumberSemiVacation);
                self.maxNumberCompany(res.maxNumberCompany);
                self.maxGrantDay(res.maxGrantDay);
                self.maxRemainingDay(res.maxRemainingDay);
                self.numberYearRetain(res.numberYearRetain);
                self.selectedPermission(res.preemptionAnnualVacation);
                self.selectedPreemptionPermit(res.preemptionYearLeave);
                self.selectedNumberRemainingYearly(res.remainingNumberDisplay);
                self.selectedNextAnunalVacation(res.nextGrantDayDisplay);
                
                // Time Leave Setting
                self.selectedTimeManagement(res.timeManageType);
                self.selectedVacationTimeUnit(res.timeUnit);
                self.selectedManageUpperLimitDayVacation(res.manageMaxDayVacation);
                self.selectedMaxDayVacation(res.reference);
                self.timeMaxNumberCompany(res.maxTimeDay);
                self.isEnoughTimeOneDay(res.isEnoughTimeOneDay);
            }
            
            private defaultValue(): any {
                let backup: any = {};
                backup.annualManage = 0;
                // Annual Setting
                backup.addAttendanceDay = 0;
                backup.maxManageSemiVacation = 0;
                backup.maxNumberSemiVacation = 0;
                backup.maxNumberCompany = '';
                backup.maxGrantDay = '';
                backup.maxRemainingDay = '';
                backup.numberYearRetain = '';
                backup.preemptionAnnualVacation = 0;
                backup.preemptionYearLeave = 0;
                backup.remainingNumberDisplay = 0;
                backup.nextGrantDayDisplay = 0;
                
                // Time Leave Setting
                backup.timeManageType = 0;
                backup.timeUnit = 0;
                backup.manageMaxDayVacation = 0;
                backup.reference = 0;
                backup.maxTimeDay = '';
                backup.isEnoughTimeOneDay = true;
                
                return backup;
            }
            
            private validate(): boolean {
                let self = this;
                self.clearError();
                if (self.enableAnnualVacation()) {
                    if (self.enableMaxNumberCompany() && (self.requiredMaxNumberCompany()
                        || (self.requiredMaxNumberCompany() == false && self.maxNumberCompany()))) {
                        $('#max-number-company').ntsEditor('validate');
                    }
                    $('#max-grant-day').ntsEditor('validate');
                    $('#max-remaining-day').ntsEditor('validate');
                    $('#number-year-retain').ntsEditor('validate');
                    if (self.enableTimeMaxNumberCompany() && (self.requiredTimeMaxNumberCompany()
                        || (!self.requiredTimeMaxNumberCompany() && self.timeMaxNumberCompany()))) {
                        $('#time-max-day-company').ntsEditor('validate');
                    }
                }
                if ($('.nts-input').ntsError('hasError')) {
                    return false;
                }
                return true;
            }
            
            private clearError(): void {
                $('#max-number-company').ntsError('clear');
                $('#max-grant-day').ntsError('clear');
                $('#max-remaining-day').ntsError('clear');
                $('#number-year-retain').ntsError('clear');
                $('#time-max-day-company').ntsError('clear');
            }
            
            // find enumeration ManageDistinct
            private loadManageDistinctEnums(): JQueryPromise<Array<EnumertionModel>> {
                let self = this;
                let dfd = $.Deferred();
                service.findManageDistinct().done(function(res: Array<EnumertionModel>) {
                    self.manageDistinctList(res);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
                return dfd.promise();
            }
            
            // find enumeration ApplyPermission
            private loadApplyPermissionEnums(): JQueryPromise<Array<EnumertionModel>> {
                let self = this;
                let dfd = $.Deferred();
                service.findApplyPermission().done(function(res: Array<EnumertionModel>) {
                    self.permissionList(res);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
                return dfd.promise();
            }
            
            // find enumeration PreemptionPermit
            private loadPreemptionPermitEnums(): JQueryPromise<Array<EnumertionModel>> {
                let self = this;
                let dfd = $.Deferred();
                service.findPreemptionPermit().done(function(res: Array<EnumertionModel>) {
                    self.preemptionPermitList(res);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
                return dfd.promise();
            }
            
            // find enumeration DisplayDivision
            private loadDisplayDivisionEnums(): JQueryPromise<Array<EnumertionModel>> {
                let self = this;
                let dfd = $.Deferred();
                service.findDisplayDivision().done(function(res: Array<EnumertionModel>) {
                    self.displayDivisionList(res);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
                return dfd.promise();
            }
            
            // find enumeration TimeUnit
            private loadTimeUnitEnums(): JQueryPromise<Array<EnumertionModel>> {
                let self = this;
                let dfd = $.Deferred();
                service.findTimeUnit().done(function(res: Array<EnumertionModel>) {
                    self.vacationTimeUnitList(res);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
                return dfd.promise();
            }
            
            // find enumeration MaxDayReference
            private loadMaxDayReferenceEnums(): JQueryPromise<Array<EnumertionModel>> {
                let self = this;
                let dfd = $.Deferred();
                service.findMaxDayReference().done(function(res: Array<EnumertionModel>) {
                    self.maxDayReferenceList(res);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
                return dfd.promise();
            }
        }
    }
}