module nts.uk.at.view.kmk004.shr.worktime.setting {
    export module viewmodel {
        import UsageUnitSettingService = nts.uk.at.view.kmk004.e.service;
        
        import DeformationLaborSetting = nts.uk.at.view.kmk004.shared.model.DeformationLaborSetting;
        import FlexSetting = nts.uk.at.view.kmk004.shared.model.FlexSetting;
        import FlexDaily = nts.uk.at.view.kmk004.shared.model.FlexDaily;
        import FlexMonth = nts.uk.at.view.kmk004.shared.model.FlexMonth;
        import NormalSetting = nts.uk.at.view.kmk004.shared.model.NormalSetting;
        import WorkingTimeSetting = nts.uk.at.view.kmk004.shared.model.WorkingTimeSetting;
        import Monthly = nts.uk.at.view.kmk004.shared.model.Monthly;
        import WorktimeSettingDto = nts.uk.at.view.kmk004.shared.model.WorktimeSettingDto;
        import WorktimeNormalDeformSetting = nts.uk.at.view.kmk004.shared.model.WorktimeNormalDeformSetting;
        import WorktimeFlexSetting1 = nts.uk.at.view.kmk004.shared.model.WorktimeFlexSetting1;
        import NormalWorktime = nts.uk.at.view.kmk004.shared.model.NormalWorktime;
        import FlexWorktimeAggrSetting = nts.uk.at.view.kmk004.shared.model.FlexWorktimeAggrSetting;
        import WorktimeSetting = nts.uk.at.view.kmk004.shared.model.WorktimeSetting;
        import WorktimeSettingDtoSaveCommand = nts.uk.at.view.kmk004.shared.model.WorktimeSettingDtoSaveCommand;
        import CompanyWTSetting = nts.uk.at.view.kmk004.shared.model.CompanyWTSetting;
        
        export class ScreenModel {
            
            tabs: KnockoutObservableArray<NtsTabPanelModel>;
            baseDate: KnockoutObservable<Date>;
            
            usageUnitSetting: UsageUnitSetting;
            companyWTSetting: CompanyWTSetting;
            // Start month.
            startMonth: KnockoutObservable<number>;
            
            isNewMode: KnockoutObservable<boolean>;
            isLoading: KnockoutObservable<boolean>;
            
            aggrSelectionItemList: KnockoutObservableArray<any>;// FlexAggregateMethod フレックス集計方法
            selectedAggrSelection: KnockoutObservable<number>;
            
            worktimeSetting: WorktimeSetting;
            
            constructor() {
                let self = this;
                self.isNewMode = ko.observable(true);
                self.isLoading = ko.observable(true);
                
                // Datasource.
                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: nts.uk.resource.getText("KMK004_3"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: nts.uk.resource.getText("KMK004_4"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-3', title: nts.uk.resource.getText("KMK004_5"), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                self.baseDate = ko.observable(new Date());
                
                self.worktimeSetting = new WorktimeSetting();
                
                // Data model.
                self.usageUnitSetting = new UsageUnitSetting();
                self.companyWTSetting = new CompanyWTSetting();
                
                // Update
                self.aggrSelectionItemList = ko.observableArray([
                    { id: 1, name: nts.uk.resource.getText("KMK004_51")},
                    { id: 2, name: nts.uk.resource.getText("KMK004_52")}
                ]);
                self.selectedAggrSelection = ko.observable(1);
                
                //==================UPDATE==================
                
            }
            
            /**
             * Initialize
             */
            public initialize(): JQueryPromise<void> {
                nts.uk.ui.block.invisible();
                let self = this;
                let dfd = $.Deferred<void>();
                
                self.loadUsageUnitSetting().done(() => {
                    viewmodel.getStartMonth().done((month) => {
                        self.startMonth = ko.observable(month);
                        
                        self.isLoading(true);
                        self.loadCompanySettingNewest().done(() => {
                            // Update flag.
                            self.isLoading(false);
                            $('#companyYearPicker').focus();
                            
                            dfd.resolve();
                        });
                    }).always(() => {
                        nts.uk.ui.block.clear();
                    });
                }).fail(() => {
                    nts.uk.ui.block.clear();
                });
                return dfd.promise();
            }
            
            public saveCompanySettingNewest(): void {
                let self = this;
                // Validate
                if (self.hasError()) {
                    return;
                }
                
                let saveCommand: WorktimeSettingDtoSaveCommand = new WorktimeSettingDtoSaveCommand();
                saveCommand.updateData(self.worktimeSetting);
                service.saveCompanySetting(ko.toJS(saveCommand)).done(() => {
                    self.isNewMode(false);
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                }).fail(error => {
                    nts.uk.ui.dialog.alertError(error);
                });
            }
            
            /**
             * LOAD WORKTIMESETTING (NEWEST)
             */
            public loadCompanySettingNewest(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
//                if (self.isCompanySelected()) {
                    // Find CompanySetting
                    service.findCompanySetting(self.worktimeSetting.normalSetting().year()).done(function(data: WorktimeSettingDto) {
                        // Clear Errors
                        self.clearError();
                        // update mode.
                        // Check condition: ドメインモデル「会社別通常勤務労働時間」を取得する
                        if (data.statWorkTimeSetDto && data.statWorkTimeSetDto.regularLaborTime) {
                            self.isNewMode(false);
                            // Update Full Data
                            self.worktimeSetting.updateFullData(data);
                            self.worktimeSetting.updateYear(data.statWorkTimeSetDto.year);
                        }
                        else {
                            // new mode.
                            self.isNewMode(true);
                            let newSetting = new WorktimeSettingDto();
                            // Reserve selected year.
                            newSetting.updateYear(self.worktimeSetting.normalSetting().year());
                            // Update Full Data
                            self.worktimeSetting.updateFullData(ko.toJS(newSetting));
                        }
                        // Sort month.
                        self.worktimeSetting.sortMonth(self.startMonth());
                        dfd.resolve();
                    });
//                }
                
                return dfd.promise();
            }
            
            /**
             * Remove company setting.
             */
            public removeCompanySetting(): void {
                let self = this;
                if ($('#companyYearPicker').ntsError('hasError')) {
                    return;
                }
                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function() {
                    let selectedYear = self.companyWTSetting.year();
                    let command = { year: selectedYear }
                    service.removeCompanySetting(command).done(() => {
                        // Reserve current year.
                        let newSetting = new CompanyWTSetting();
                        newSetting.year(selectedYear);
                        self.companyWTSetting.updateData(ko.toJS(newSetting));
                        // Sort month.
                        self.companyWTSetting.sortMonth(self.startMonth());
                        self.isNewMode(true);
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                    }).fail(error => {
                        nts.uk.ui.dialog.alertError(error);
                    }).always(() => {
                        self.clearError();
                    });
                }).ifNo(function() {
                    nts.uk.ui.block.clear();
                    return;
                })
            }
            
            /**
             * Load usage unit setting.
             */
            public loadUsageUnitSetting(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                UsageUnitSettingService.findUsageUnitSetting().done(function(res: UsageUnitSettingService.model.UsageUnitSettingDto) {
                    self.usageUnitSetting.employee(res.employee);
                    self.usageUnitSetting.employment(res.employment);
                    self.usageUnitSetting.workplace(res.workPlace);
                    dfd.resolve();
                });
                return dfd.promise();
            }
            
            /**
             * Set start month.
             */
            private setStartMonth(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                service.getStartMonth().done(res => {
                    if (res.startMonth) {
                        self.startMonth = ko.observable(res.startMonth);
                    } else {
                        // Default startMonth..
                        self.startMonth = ko.observable(1);
                    }
                    dfd.resolve();
                });
                return dfd.promise();
            }
            
            /**
             * Check validate all input.
             */
            private hasError(): boolean {
                return $('.nts-editor').ntsError('hasError');
            }
            
            /**
             * Clear all errors.
             */
            private clearError(): void {
                let self = this;
                if (nts.uk.ui._viewModel) {
                    // Reset year if has error.
                    if ($('#companyYearPicker').ntsError('hasError')) {
                        self.companyWTSetting.year(new Date().getFullYear());
                    }
                    // Clear error inputs
                    $('.nts-input').ntsError('clear');
                }
            }
            
            /**
             * Go to screen E (usage unit setting).
             */
            public gotoE(): void {
                let self = this;
                nts.uk.ui.windows.sub.modal("/view/kmk/004/e/index.xhtml").onClosed(() => {
                    self.loadUsageUnitSetting();
                    $('#companyYearPicker').focus();
                });
            }
            
            
        } // --- end ScreenModel
        
        export function getStartMonth(): JQueryPromise<number> {
            let self = this;
            let dfd = $.Deferred<number>();
            service.getStartMonth().done(res => {
                let month = 1;
                if (res.startMonth) {
                    month = res.startMonth;
                }
                dfd.resolve(month);
            });
            return dfd.promise();
        }
        
        export class UsageUnitSetting {
            employee: KnockoutObservable<boolean>;
            employment: KnockoutObservable<boolean>;
            workplace: KnockoutObservable<boolean>;

            constructor() {
                let self = this;
                self.employee = ko.observable(true);
                self.employment = ko.observable(true);
                self.workplace = ko.observable(true);
            }
        }
    }
}