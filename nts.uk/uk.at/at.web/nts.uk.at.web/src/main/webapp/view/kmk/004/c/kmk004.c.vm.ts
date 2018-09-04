module nts.uk.at.view.kmk004.c {
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
        import StatutoryWorktimeSettingDto = nts.uk.at.view.kmk004.shared.model.StatutoryWorktimeSettingDto;
        import MonthlyCalSettingDto = nts.uk.at.view.kmk004.shared.model.MonthlyCalSettingDto;
        import WorktimeSetting = nts.uk.at.view.kmk004.shr.worktime.setting.viewmodel.WorktimeSetting;
        
        export class ScreenModel {
            
            tabs: KnockoutObservableArray<NtsTabPanelModel>;
            
            worktimeVM: WorktimeSettingVM.ScreenModel;
            usageUnitSetting: UsageUnitSetting;
            
            // Employment list component.
            employmentComponentOption: any;
            alreadySettingEmployments: KnockoutObservableArray<any>;
            selectedEmploymentCode: KnockoutObservable<string>;
            
            isLoading: KnockoutObservable<boolean>;
            
            employmentCode: KnockoutObservable<string>;
            employmentName: KnockoutObservable<string>;
            
            startMonth: KnockoutObservable<number>;
            
            constructor() {
                let self = this;
                self.isLoading = ko.observable(true);
                
                self.usageUnitSetting = new UsageUnitSetting();
                // Datasource.
                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: nts.uk.resource.getText("KMK004_3"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: nts.uk.resource.getText("KMK004_4"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-3', title: nts.uk.resource.getText("KMK004_5"), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                
                self.worktimeVM = new WorktimeSettingVM.ScreenModel();
                // Employment list component.
                self.alreadySettingEmployments = ko.observableArray([]);
                self.selectedEmploymentCode = ko.observable('');
                self.setEmploymentComponentOption();
                
                self.employmentCode = ko.observable('');
                self.employmentName = ko.observable('');
                
                self.worktimeVM.groupYear.subscribe(val => {
                    // Validate
                    if ($('#worktimeYearPicker').ntsError('hasError')) {
                        self.clearError();
                        // Reset year if has error.
                        self.worktimeVM.groupYear(new Date().getFullYear());
                        return;
                    } else {
                        self.loadEmploymentSetting(self.selectedEmploymentCode());
                    }
                });
            }
                
            public startPage(): JQueryPromise<void> {
                nts.uk.ui.block.invisible();
                let self = this;
                let dfd = $.Deferred<void>();
                
                self.isLoading(true);

                // Load component.
                Common.loadUsageUnitSetting().done((res: UsageUnitSettingDto) => {
                    self.usageUnitSetting.employee(res.employee);
                    self.usageUnitSetting.employment(res.employment);
                    self.usageUnitSetting.workplace(res.workPlace);
                    
                    self.worktimeVM.initialize().done(() => {
                        WorktimeSettingVM.getStartMonth().done((month) => {
                            self.startMonth = ko.observable(month);
                            
                            dfd.resolve();
                        }).fail(() => {
                            nts.uk.ui.block.clear();
                        });
                    }).fail(() => {
                        nts.uk.ui.block.clear();
                    });
                }).fail(() => {
                    nts.uk.ui.block.clear();
                });
                return dfd.promise();
            }
            
            public postBindingProcess(): void {
                let self = this;
                
                $('#list-employment').ntsListComponent(this.employmentComponentOption).done(() => {
                    self.isLoading(false);
    
                    // Force to reload.
//                    if (self.employmentWTSetting.employmentCode() === self.selectedEmploymentCode()) {
//                        self.loadEmploymentSetting(self.selectedEmploymentCode());
//                    }
                    $('#employmentYearPicker').focus();
                    // Set already setting list.
                    self.setAlreadySettingEmploymentList();
                    self.worktimeVM.worktimeSetting.selectedTab('tab-1');
                    
                    
                    self.loadEmploymentSetting(self.selectedEmploymentCode()); // load setting for initial selection
                    self.setEmploymentName(self.selectedEmploymentCode());
                    
                    // subscribe to furture selection
                    self.selectedEmploymentCode.subscribe(code => {
                        self.loadEmploymentSetting(code);
                        self.setEmploymentName(code);
                    });
                    
                    ko.applyBindingsToNode($('#lblEmploymentCode')[0], { text: self.employmentCode });
                    ko.applyBindingsToNode($('#lblEmploymentName')[0], { text: self.employmentName });
                    
                    self.worktimeVM.postBindingHandler();
                }).always(() => {
                    nts.uk.ui.block.clear();
                });
            }
            
            /**
             * Load employment setting.
             */
            public loadEmploymentSetting(code?: string): void {
                let self = this;
                                
                let year = self.worktimeVM.worktimeSetting.normalSetting().year();
                if(nts.uk.util.isNullOrEmpty(year)) {
                    return;
                }
                
                if (nts.uk.text.isNullOrEmpty(code)) {
                    self.resetFieldsToNewMode();
                    return;
                }
                
                nts.uk.ui.block.invisible();
                service.findEmploymentSetting(self.worktimeVM.worktimeSetting.normalSetting().year(), code)
                    .done(function(data) {
                        // Clear Errors
                        self.clearError();
                        let resultData: WorktimeSettingDto = data;
                        // update mode.
                        // Check condition: ドメインモデル「会社別通常勤務労働時間」を取得する
                        if (!nts.uk.util.isNullOrEmpty(data.statWorkTimeSetDto) 
                        && !nts.uk.util.isNullOrEmpty(data.statWorkTimeSetDto.regularLaborTime)) {
                            let isModeNew = false;
                            
                            if (nts.uk.util.isNullOrEmpty(data.statWorkTimeSetDto.normalSetting)) {
                                resultData.statWorkTimeSetDto.normalSetting = new WorktimeNormalDeformSettingDto();
                                
                                isModeNew = true;
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
                            
                            // Sort month.
                            self.worktimeVM.worktimeSetting.sortMonth(self.worktimeVM.startMonth());
                            
                            self.worktimeVM.isNewMode(isModeNew);
                        }
                        else {
                            self.resetFieldsToNewMode();
                        }
                    }).always(() => {
                        nts.uk.ui.block.clear();
                    });
            }
            
            
            private resetFieldsToNewMode(): void {
                let self = this;
                
                // new mode.
                self.worktimeVM.isNewMode(true);
                let newSetting = new WorktimeSettingDto();
                // Reserve selected year.
                newSetting.updateYear(self.worktimeVM.worktimeSetting.normalSetting().year());
                // Update Full Data
                self.worktimeVM.worktimeSetting.updateFullData(ko.toJS(newSetting));
                
                // Sort month.
                self.worktimeVM.worktimeSetting.sortMonth(self.worktimeVM.startMonth());
            }
            
            /**
             * Set employment name
             */
            private setEmploymentName(code: string): void {
                let self = this;
                let list = $('#list-employment').getDataList();
                let empt = _.find(list, item => item.code == code);
                if (empt) {
                    self.employmentName(empt.name);
                    self.employmentCode(empt.code);
                    return;
                }
                self.employmentName('');
                self.employmentCode('');
            }
            
            /**
             * Add alreadySetting employment.
             */
            private addAlreadySettingEmloyment(code: string): void {
                let self = this;
                let l = self.alreadySettingEmployments().filter(i => code == i.code);
                if (l[0]) {
                    return;
                }
                self.alreadySettingEmployments.push({ code: code, isAlreadySetting: true });
            }
            
            /**
             * Set the already setting employment list.
             */
            private setAlreadySettingEmploymentList(): void {
                let self = this;
                // TODO: service.findAllEmploymentSetting(self.employmentWTSetting.year()).done(listCode => {
                service.findAllEmploymentSetting().done(listEmpl => {
                    self.alreadySettingEmployments(_.map(listEmpl, function(item) {
                        return { code: item.employmentCode, isAlreadySetting: true };
                    }));
                });
            }
            
            /**
             * Save employment setting.
             */
            public saveEmployment(): void {
                let self = this;
                // Validate
                if (self.hasError()) {
                    return;
                }
                
                let saveCommand: EmploymentWorktimeSettingDtoSaveCommand = new EmploymentWorktimeSettingDtoSaveCommand();
                saveCommand.updateData(self.selectedEmploymentCode(), self.worktimeVM.worktimeSetting);
                nts.uk.ui.block.grayout();
                service.saveEmploymentSetting(ko.toJS(saveCommand)).done(() => {
                    self.worktimeVM.isNewMode(false);
                    self.addAlreadySettingEmloyment(self.selectedEmploymentCode());
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                }).fail(error => {
                    nts.uk.ui.dialog.alertError(error);
                }).always(() => nts.uk.ui.block.clear());
            }
            
            /**
             * Remove employment setting.
             */
            public removeEmployment(): void {
                let self = this;
                if ($('#employmentYearPicker').ntsError('hasError')) {
                    return;
                }
                let emplCode = self.selectedEmploymentCode();
                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function() {
                    let command = { year: self.worktimeVM.worktimeSetting.normalSetting().year(), employmentCode: emplCode }
                    nts.uk.ui.block.grayout();
                    service.removeEmploymentSetting(command).done((res) => {
                    
                        // new mode.
                        self.worktimeVM.isNewMode(true);
                        let newSetting = new WorktimeSettingDto();
                        
                        // Reserve selected year.
                        newSetting.updateYear(self.worktimeVM.worktimeSetting.normalSetting().year()); 
                        
                        if (res.wtsettingCommonRemove) {
                            self.removeAlreadySettingEmployment(emplCode);
                            //Update Full Data
                            self.worktimeVM.worktimeSetting.updateFullData(ko.toJS(newSetting));
                        } else {
                            newSetting.statWorkTimeSetDto.regularLaborTime.updateData(self.worktimeVM.worktimeSetting.normalWorktime());
                            newSetting.statWorkTimeSetDto.transLaborTime.updateData(self.worktimeVM.worktimeSetting.deformLaborWorktime());
                            self.worktimeVM.worktimeSetting.updateDataDependOnYear(newSetting.statWorkTimeSetDto);
                        }
                        self.worktimeVM.worktimeSetting.sortMonth(self.worktimeVM.startMonth());
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                    }).fail(error => {
                        nts.uk.ui.dialog.alertError(error);
                    }).always(() => {
                        self.clearError();
                        nts.uk.ui.block.clear();
                    });
                }).ifNo(function() {
                    nts.uk.ui.block.clear();
                    return;
                })
            }
            
            /**
             * Remove alreadySetting employment.
             */
            private removeAlreadySettingEmployment(code: string): void {
                let self = this;
                let ase = self.alreadySettingEmployments().filter(i => code == i.code)[0];
                self.alreadySettingEmployments.remove(ase);
            }
            
            /**
             * Set employment component option.
             */
            private setEmploymentComponentOption(): void {
                let self = this;
                self.employmentComponentOption = {
                    isShowAlreadySet: true, // is show already setting column.
                    isMultiSelect: false, // is multiselect.
                    isShowNoSelectRow: false, // selected nothing.
                    listType: 1, // employment list.
                    selectType: 3, // select first item.
                    maxRows: 12, // maximum rows can be displayed.
                    selectedCode: this.selectedEmploymentCode,
                    isDialog: false,
                    alreadySettingList: self.alreadySettingEmployments
                };
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
                    // Clear error inputs
                    $('.nts-input').ntsError('clear');
                }
            }
        } // ----- end ScreenModel
        
        class EmploymentStatutoryWorktimeSettingDto extends StatutoryWorktimeSettingDto {
            employmentCode: string;
        }
        
        class EmploymentMonthlyCalSettingDto extends MonthlyCalSettingDto {
            employmentCode: string;
        }
        
        export class EmploymentWorktimeSettingDtoSaveCommand {

            /** The save com stat work time set command. */
            saveStatCommand: EmploymentStatutoryWorktimeSettingDto;
    
            /** The save com flex command. */
            saveMonthCommand: EmploymentMonthlyCalSettingDto;
    
            constructor() {
                let self = this;
                self.saveStatCommand = new EmploymentStatutoryWorktimeSettingDto();
                self.saveMonthCommand = new EmploymentMonthlyCalSettingDto();
            }
    
            public updateYear(year: number): void {
                let self = this;
                self.saveStatCommand.year = year;
            }
    
            public updateData(employmentCode: string, model: WorktimeSetting): void {
                let self = this;
                self.saveStatCommand.employmentCode = employmentCode;
                self.saveStatCommand.updateData(model);
                self.saveMonthCommand.employmentCode = employmentCode;
                self.saveMonthCommand.updateData(model);
            }
        }
    }
}