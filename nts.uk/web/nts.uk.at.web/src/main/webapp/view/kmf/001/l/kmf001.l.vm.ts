module nts.uk.pr.view.kmf001.l {
    export module viewmodel {
        
        import EnumertionModel = service.model.EnumerationModel;
        
        export class ScreenModel {
            textEditorOption: KnockoutObservable<any>;
            
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
            enableTimeUnit: KnockoutObservable<boolean>;
            selectedVacationTimeUnit: KnockoutObservable<number>;
            selectedManageUpperLimitDayVacation: KnockoutObservable<number>;
            enableManageUpperLimit: KnockoutObservable<boolean>;
            selectedMaxDayVacation: KnockoutObservable<number>;
            timeMaxNumberCompany: KnockoutObservable<string>;
            requiredTimeMaxNumberCompany: KnockoutObservable<boolean>;
            isEnoughTimeOneDay: KnockoutObservable<boolean>;
            
            constructor() {
                let self = this;
                self.textEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    width: "50px",
                    textalign: "right"
                }));
                // 年休の管理
                self.manageDistinctList = ko.observableArray([]);
                self.selectedAnnualManage = ko.observable(1);
                self.enableAnnualVacation = ko.computed(function() {
                    return self.selectedAnnualManage() == 1;
                }, self);
                
                // 年次有給休暇の扱い
                self.selectedAddAttendanceDay = ko.observable(1);
                self.selectedMaxManageSemiVacation = ko.observable(1);
                self.maxDayReferenceList = ko.observableArray([]);
                self.selectedMaxNumberSemiVacation = ko.observable(0);
                self.maxNumberCompany = ko.observable("");
                self.requiredMaxNumberCompany = ko.computed(function() {
                    return self.enableAnnualVacation() && self.selectedMaxManageSemiVacation() == 1;
                });
                self.maxGrantDay = ko.observable("");
                self.maxRemainingDay = ko.observable("");
                self.numberYearRetain = ko.observable("");
                self.enableMaxNumberCompany = ko.computed(function() {
                    return self.selectedMaxNumberSemiVacation() == 0 && self.enableAnnualVacation();
                }, self);

                
                // 年休取得の設定
                self.permissionList = ko.observableArray([]);
                self.selectedPermission = ko.observable(1);
                self.preemptionPermitList = ko.observableArray([]);
                self.selectedPreemptionPermit = ko.observable(1);
                
                // 表示設定
                self.displayDivisionList = ko.observableArray([]);
                self.selectedNumberRemainingYearly = ko.observable(1);
                self.selectedNextAnunalVacation = ko.observable(1);
                
                // 時間年休
                self.selectedTimeManagement = ko.observable(1);
                self.vacationTimeUnitList = ko.observableArray([]);
                self.enableTimeUnit = ko.computed(function() {
                    return self.selectedTimeManagement() == 1 && self.enableAnnualVacation();
                }, self);
                self.selectedVacationTimeUnit = ko.observable(1);
                self.enableManageUpperLimit = ko.computed(function() {
                    return self.enableAnnualVacation() && self.enableMaxNumberCompany()
                    && self.enableTimeUnit();
                }, self);
                self.selectedMaxDayVacation = ko.observable(1);
                self.selectedManageUpperLimitDayVacation = ko.observable(1);
                self.timeMaxNumberCompany = ko.observable("");
                self.requiredTimeMaxNumberCompany = ko.computed(function() {
                    return self.enableAnnualVacation() && self.selectedManageUpperLimitDayVacation() == 1;
                });
                self.isEnoughTimeOneDay = ko.observable(true);
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
                        nts.uk.ui.dialog.alert(nts.uk.resource.getMessage('Msg_15'));
                        dfd.resolve();
                    });
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
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
            
            private validate(): boolean {
                let self = this;
                self.clearError();
                if (self.enableAnnualVacation()) {
                    if (self.requiredMaxNumberCompany()
                        || (self.requiredMaxNumberCompany() == false && self.maxNumberCompany())) {
                        $('#max-number-company').ntsEditor('validate');
                    }
                    $('#max-grant-day').ntsEditor('validate');
                    $('#max-remaining-day').ntsEditor('validate');
                    $('#number-year-retain').ntsEditor('validate');
                    if (self.requiredTimeMaxNumberCompany()
                        || (!self.requiredTimeMaxNumberCompany() && self.timeMaxNumberCompany())) {
                        $('#time-max-day-company').ntsEditor('validate');
                    }
                } else {
                    if (self.maxNumberCompany()) {
                        $('#max-number-company').ntsEditor('validate');
                    }
                    if (self.maxGrantDay()) {
                        $('#max-grant-day').ntsEditor('validate');
                    }
                    if (self.maxRemainingDay()) {
                        $('#max-remaining-day').ntsEditor('validate');
                    }
                    if (self.numberYearRetain()) {
                        $('#number-year-retain').ntsEditor('validate');
                    }
                    if (self.timeMaxNumberCompany()) {
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