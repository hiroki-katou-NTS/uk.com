module nts.uk.at.view.kmk004.d {
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
            baseDate: KnockoutObservable<Date>;
            
            isLoading: KnockoutObservable<boolean>;
            
            usageUnitSetting: UsageUnitSetting;
            
            worktimeVM: WorktimeSettingVM.ScreenModel;
            workplaceCode: KnockoutObservable<string>;
            workplaceName: KnockoutObservable<string>;
            
            workplaceComponentOption: any;
            selectedWorkplaceId: KnockoutObservable<string>;
            alreadySettingWorkplaces: KnockoutObservableArray<any>;
            
            // Start month.
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
                self.baseDate = ko.observable(new Date());
                
                self.worktimeVM = new WorktimeSettingVM.ScreenModel();
                self.alreadySettingWorkplaces = ko.observableArray([]);
                self.selectedWorkplaceId = ko.observable('');
                self.setWorkplaceComponentOption();
                self.workplaceCode = ko.observable('');
                self.workplaceName = ko.observable('');
                
                self.worktimeVM.groupYear.subscribe(val => {
                    // Validate
                    if ($('#worktimeYearPicker').ntsError('hasError')) {
                        self.clearError();
                        // Reset year if has error.
                        self.worktimeVM.groupYear(new Date().getFullYear());
                        return;
                    } else {
                        self.loadWorkplaceSetting();
                    }
                });
            }
            
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<void>();
                
                nts.uk.ui.block.invisible();
                self.isLoading(true);
                
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
                // Load component.
                $('#list-workplace').ntsTreeComponent(this.workplaceComponentOption).done(() => {
                    self.isLoading(false);

                    $('#workplaceYearPicker').focus();
                    // Set already setting list.
                    self.setAlreadySettingWorkplaceList();
                    
//                    let wkpId = self.selectedWorkplaceId();
                    self.loadWorkplaceSetting();
                    
                    self.selectedWorkplaceId.subscribe((v) => {
                        if (nts.uk.text.isNullOrEmpty(v)) {
                            self.workplaceCode('');
                            self.workplaceName('');
                        };
                        self.loadWorkplaceSetting();
                    });
                    ko.applyBindingsToNode($('#lblWorkplaceCode')[0], { text: self.workplaceCode });
                    ko.applyBindingsToNode($('#lblWorkplaceName')[0], { text: self.workplaceName });
                    
                    self.worktimeVM.postBindingHandler();
                }).always(() => {
                    nts.uk.ui.block.clear();
                });
            }
            
            /**
             * Set workplace component option.
             */
            private setWorkplaceComponentOption(): void {
                let self = this;
                self.workplaceComponentOption = {
                    isShowAlreadySet: true, // is show already setting column.
                    isMultiSelect: false, // is multiselect.
                    isShowSelectButton: false, // Show button select all and selected sub parent
                    treeType: 1, // workplace tree.
                    selectType: 3, // select first item.
                    maxRows: 12, // maximum rows can be displayed.
                    selectedWorkplaceId: self.selectedWorkplaceId,
                    baseDate: self.baseDate,
                    isDialog: false,
                    alreadySettingList: self.alreadySettingWorkplaces,
                    systemType: 2
                };
            }
            
            /**
             * Load workplace setting.
             */
            public loadWorkplaceSetting(): void {
                let self = this;
                                
                let year = self.worktimeVM.worktimeSetting.normalSetting().year();
                if(nts.uk.util.isNullOrEmpty(year)) {
                    return;
                }
                
                let wpkId = self.selectedWorkplaceId();
                if (nts.uk.text.isNullOrEmpty(wpkId)) {
                    self.resetFieldsToNewMode();
                    return;
                }
                nts.uk.ui.block.invisible();
                self.setWorkplaceCodeName( $('#list-workplace').getDataList(), wpkId);
                service.findWorkplaceSetting(self.worktimeVM.worktimeSetting.normalSetting().year(), wpkId)
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
             * Remove workplace setting.
             */
            public removeWorkplace(): void {
                let self = this;
                if ($('#workplaceYearPicker').ntsError('hasError')) {
                    return;
                }
                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function() {
                    let command = { year: self.worktimeVM.worktimeSetting.normalSetting().year(), workplaceId: self.selectedWorkplaceId() }
                    nts.uk.ui.block.grayout();
                    service.removeWorkplaceSetting(command).done((res) => {

                        // new mode.
                        self.worktimeVM.isNewMode(true);
                        let newSetting = new WorktimeSettingDto();
                        // Reserve selected year.
                        newSetting.updateYear(self.worktimeVM.worktimeSetting.normalSetting().year());

                        if (res.wtsettingCommonRemove) {
                            self.removeAlreadySettingWorkplace(self.selectedWorkplaceId());

                            // Update Full Data
                            self.worktimeVM.worktimeSetting.updateFullData(ko.toJS(newSetting));
                        } else {
                            newSetting.statWorkTimeSetDto.regularLaborTime.updateData(self.worktimeVM.worktimeSetting.normalWorktime());
                            newSetting.statWorkTimeSetDto.transLaborTime.updateData(self.worktimeVM.worktimeSetting.deformLaborWorktime());
                            self.worktimeVM.worktimeSetting.updateDataDependOnYear(newSetting.statWorkTimeSetDto);
                        }
                        self.setWorkplaceCodeName($('#list-workplace').getDataList(), '');

                        // Sort month.
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
             * Remove alreadySetting workplace.
             */
            private removeAlreadySettingWorkplace(id: string): void {
                let self = this;
                let asw = self.alreadySettingWorkplaces().filter(i => id == i.workplaceId)[0];
                self.alreadySettingWorkplaces.remove(asw);
            }
            
            /**
             * Set workplace code + name.
             */
            private setWorkplaceCodeName(treeData: Array<any>, workPlaceId: string) {
                let self = this;
                for (let data of treeData) {
                    // Found!
                    if (data.workplaceId == workPlaceId) {
                        self.workplaceCode(data.code);
                        self.workplaceName(data.name);
                    }
                    
                    // Continue to find in childs.
                    if (data.childs.length > 0) {
                        this.setWorkplaceCodeName(data.childs, workPlaceId);
                    }
                }
            }
            
            /**
             * Set the already setting workplace list.
             */
            private setAlreadySettingWorkplaceList(): void {
                let self = this;
                service.findAllWorkplaceSetting().done(listWpl => {
                    self.alreadySettingWorkplaces(_.map(listWpl, function(data) {
                        return { workplaceId: data.wkpId, isAlreadySetting: true };
                    }));
                });
            }
            
            /**
             * Save workplace setting.
             */
            public saveWorkplace(): void {
                let self = this;
                // Validate
                if (self.hasError()) {
                    return;
                }
                
                let saveCommand: WorkspaceWorktimeSettingDtoSaveCommand = new WorkspaceWorktimeSettingDtoSaveCommand();
                saveCommand.updateData(self.selectedWorkplaceId(), self.worktimeVM.worktimeSetting);
                nts.uk.ui.block.grayout();
                service.saveWorkplaceSetting(ko.toJS(saveCommand)).done(() => {
                    self.worktimeVM.isNewMode(false);
                    self.addAlreadySettingWorkplace(self.selectedWorkplaceId());
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                }).fail(error => {
                    nts.uk.ui.dialog.alertError(error);
                }).always(() => nts.uk.ui.block.clear());
            }
            
            /**
             * Add alreadySetting workplace.
             */
            private addAlreadySettingWorkplace(id: string): void {
                let self = this;
                let l = self.alreadySettingWorkplaces().filter(i => id == i.workplaceId);
                if (l[0]) {
                    return;
                }
                self.alreadySettingWorkplaces.push({ workplaceId: id, isAlreadySetting: true });
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
        } // ----- end Screen Model
        
        class WorkspaceStatutoryWorktimeSettingDto extends StatutoryWorktimeSettingDto {
            workplaceId: string;
        }
        
        class WorkspaceMonthlyCalSettingDto extends MonthlyCalSettingDto {
            workplaceId: string;
        }
        
        export class WorkspaceWorktimeSettingDtoSaveCommand {

            /** The save com stat work time set command. */
            saveStatCommand: WorkspaceStatutoryWorktimeSettingDto;
    
            /** The save com flex command. */
            saveMonthCommand: WorkspaceMonthlyCalSettingDto;
    
            constructor() {
                let self = this;
                self.saveStatCommand = new WorkspaceStatutoryWorktimeSettingDto();
                self.saveMonthCommand = new WorkspaceMonthlyCalSettingDto();
            }
    
            public updateYear(year: number): void {
                let self = this;
                self.saveStatCommand.year = year;
            }
    
            public updateData(workplaceId: string, model: WorktimeSetting): void {
                let self = this;
                self.saveStatCommand.workplaceId = workplaceId;
                self.saveStatCommand.updateData(model);
                self.saveMonthCommand.workplaceId = workplaceId;
                self.saveMonthCommand.updateData(model);
            }
        }
    }
}