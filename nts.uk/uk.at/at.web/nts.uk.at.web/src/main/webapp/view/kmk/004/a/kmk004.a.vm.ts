module nts.uk.at.view.kmk004.a {
    export module viewmodel {
        import Common = nts.uk.at.view.kmk004.shared.model;
        import UsageUnitSetting = nts.uk.at.view.kmk004.shared.model.UsageUnitSetting;
        import UsageUnitSettingDto =  nts.uk.at.view.kmk004.e.service.model.UsageUnitSettingDto
        import WorktimeSettingVM = nts.uk.at.view.kmk004.shr.worktime.setting.viewmodel;
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
        import WorktimeSettingDtoSaveCommand = nts.uk.at.view.kmk004.shared.model.WorktimeSettingDtoSaveCommand;
        import WorktimeNormalDeformSettingDto = nts.uk.at.view.kmk004.shared.model.WorktimeNormalDeformSettingDto;
        import WorktimeFlexSetting1Dto = nts.uk.at.view.kmk004.shared.model.WorktimeFlexSetting1Dto;
        
        export class ScreenModel {
            
            worktimeVM: WorktimeSettingVM.ScreenModel;
            usageUnitSetting: UsageUnitSetting;
            companyWTSetting: CompanyWTSetting;
            
            constructor() {
                let self = this;
                
                
                self.worktimeVM = new WorktimeSettingVM.ScreenModel();
                
                // Data model.
                self.usageUnitSetting = new UsageUnitSetting();
                self.companyWTSetting = new CompanyWTSetting();
                
                self.worktimeVM.worktimeSetting.normalSetting().year.subscribe(val => {
                    // Validate
                    if ($('#worktimeYearPicker').ntsError('hasError')) {
                        return;
                    } else {
                        self.worktimeVM.worktimeSetting.updateYear(val);
                        self.loadCompanySettingNewest();
                    }
                });
            }
            
            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                nts.uk.ui.block.invisible();
                let self = this;
                let dfd = $.Deferred<void>();
                
                self.loadUsageUnitSetting().done(() => {
                    self.worktimeVM.initialize().done(() => {
                        self.worktimeVM.isLoading(true);
                        self.loadCompanySettingNewest().done(() => {
                            // Update flag.
                            self.worktimeVM.isLoading(false);
                            
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
            
            private loadUsageUnitSetting(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                Common.loadUsageUnitSetting().done((res: UsageUnitSettingDto) => {
                    self.usageUnitSetting.employee(res.employee);
                    self.usageUnitSetting.employment(res.employment);
                    self.usageUnitSetting.workplace(res.workPlace);
                    
                    dfd.resolve();
                });
                return dfd.promise();
            }
            
            public saveCompanySettingNewest(): void {
                let self = this;
                // Validate
                if (self.worktimeVM.hasError()) {
                    return;
                }
                
                let saveCommand: WorktimeSettingDtoSaveCommand = new WorktimeSettingDtoSaveCommand();
                saveCommand.updateData(self.worktimeVM.worktimeSetting);
                service.saveCompanySetting(ko.toJS(saveCommand)).done(() => {
                    self.worktimeVM.isNewMode(false);
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
                    service.findCompanySetting(self.worktimeVM.worktimeSetting.normalSetting().year()).done(function(data: WorktimeSettingDto) {
                        // Clear Errors
                        self.clearError();
                        let resultData: WorktimeSettingDto = data;
                        // update mode.
                        // Check condition: ドメインモデル「会社別通常勤務労働時間」を取得する
                        if (!nts.uk.util.isNullOrEmpty(data.statWorkTimeSetDto) 
                        && !nts.uk.util.isNullOrEmpty(data.statWorkTimeSetDto.regularLaborTime)) {
                            self.worktimeVM.isNewMode(false);
                            if (nts.uk.util.isNullOrEmpty(data.statWorkTimeSetDto.normalSetting)) {
                                resultData.statWorkTimeSetDto.normalSetting = new WorktimeNormalDeformSettingDto();
                            }
                            if (nts.uk.util.isNullOrEmpty(data.statWorkTimeSetDto.flexSetting)) {
                                resultData.statWorkTimeSetDto.flexSetting = new WorktimeFlexSetting1Dto();
                            }
                            if (nts.uk.util.isNullOrEmpty(data.statWorkTimeSetDto.deforLaborSetting)) {
                                resultData.statWorkTimeSetDto.deforLaborSetting = new WorktimeNormalDeformSettingDto();
                            }
                            // Update Full Data
                            self.worktimeVM.worktimeSetting.updateFullData(resultData);
                            self.worktimeVM.worktimeSetting.updateYear(data.statWorkTimeSetDto.year);
                        }
                        else {
                            // new mode.
                            self.worktimeVM.isNewMode(true);
                            let newSetting = new WorktimeSettingDto();
                            // Reserve selected year.
                            newSetting.updateYear(self.worktimeVM.worktimeSetting.normalSetting().year());
                            // Update Full Data
                            self.worktimeVM.worktimeSetting.updateFullData(ko.toJS(newSetting));
                        }
                        // Sort month.
                        self.worktimeVM.worktimeSetting.sortMonth(self.worktimeVM.startMonth());
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
                if ($('#worktimeYearPicker').ntsError('hasError')) {
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
                        self.companyWTSetting.sortMonth(self.worktimeVM.startMonth());
                        self.worktimeVM.isNewMode(true);
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
             * Clear all errors.
             */
            private clearError(): void {
                let self = this;
                if (nts.uk.ui._viewModel) {
                    // Reset year if has error.
                    if ($('#worktimeYearPicker').ntsError('hasError')) {
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
                    $('#worktimeYearPicker').focus();
                });
            }
        } // --- end ScreenModel
        
        export class CompanyWTSetting {
            deformationLaborSetting: DeformationLaborSetting;
            flexSetting: FlexSetting;
            normalSetting: NormalSetting;
            year: KnockoutObservable<number>;
            selectedTab: KnockoutObservable<string>;
            // Update
    
            constructor() {
                let self = this;
                self.selectedTab = ko.observable('tab-1');
                self.year = ko.observable(new Date().getFullYear());
                self.deformationLaborSetting = new DeformationLaborSetting();
                self.flexSetting = new FlexSetting();
                self.normalSetting = new NormalSetting();
            }
    
            public updateData(dto: any): void {
                let self = this;
                self.year(dto.year);
                self.normalSetting.updateData(dto.normalSetting);
                self.deformationLaborSetting.updateData(dto.deformationLaborSetting);
                self.flexSetting.updateData(dto.flexSetting);
            }
            public sortMonth(startMonth: number): void {
                let self = this;
                self.normalSetting.statutorySetting.sortMonth(startMonth);
                self.deformationLaborSetting.statutorySetting.sortMonth(startMonth);
                self.flexSetting.sortMonth(startMonth);
            }
        }
    }
}