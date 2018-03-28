module nts.uk.at.view.kmk004.d {
    export module viewmodel {
        import WorktimeSettingVM = nts.uk.at.view.kmk004.shr.worktime.setting.viewmodel;
        
        export class ScreenModel {
            
            tabs: KnockoutObservableArray<NtsTabPanelModel>;
            baseDate: KnockoutObservable<Date>;
            
            isNewMode: KnockoutObservable<boolean>;
            isLoading: KnockoutObservable<boolean>;
            
            workplaceWTSetting: WorkPlaceWTSetting;
            worktimeSetting: WorktimeSettingVM.ScreenModel;
            
            workplaceCode: KnockoutObservable<string>;
            workplaceName: KnockoutObservable<string>;
            
            workplaceComponentOption: any;
            selectedWorkplaceId: KnockoutObservable<string>;
            alreadySettingWorkplaces: KnockoutObservableArray<any>;
            
            // Start month.
            startMonth: KnockoutObservable<number>;
            
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
                
                self.worktimeSetting = new WorktimeSettingVM.ScreenModel();
                self.workplaceWTSetting = new WorkPlaceWTSetting();
                self.alreadySettingWorkplaces = ko.observableArray([]);
                self.selectedWorkplaceId = ko.observable('');
                self.setWorkplaceComponentOption();
                self.selectedWorkplaceId.subscribe(code => {
                    if (code) {
                        self.loadWorkplaceSetting(code);
                    }
                });
                
                self.workplaceWTSetting.workplaceCode.subscribe(function(v) {
                    self.workplaceCode(v);
                });
                self.workplaceWTSetting.workplaceName.subscribe(function(v) {
                    self.workplaceName(v);
                });
            }
            
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<void>();
                
                nts.uk.ui.block.invisible();
                self.isLoading(true);
                self.workplaceWTSetting.year(self.worktimeSetting.companyWTSetting.year());
                
                self.worktimeSetting.initialize().done(() => {
                    WorktimeSettingVM.getStartMonth().done((month) => {
                        self.startMonth = ko.observable(month);
                        
                        dfd.resolve();
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

                    // Force to reload.
                    if (self.workplaceWTSetting.workplaceId() === self.selectedWorkplaceId()) {
                        self.loadWorkplaceSetting(self.selectedWorkplaceId());
                    }
                    
                    $('#workplaceYearPicker').focus();
                    // Set already setting list.
                    self.setAlreadySettingWorkplaceList();
                    self.workplaceWTSetting.selectedTab('tab-1');
                    
                    
                    ko.applyBindingsToNode($('#lblWorkplaceCode')[0], { text: self.workplaceCode });
                    ko.applyBindingsToNode($('#lblWorkplaceName')[0], { text: self.workplaceName });
                })
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
                    selectType: 2, // select first item.
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
            public loadWorkplaceSetting(id?: string): void {
                let self = this;
                let currentSetting = self.workplaceWTSetting;
                let request;
                // workplaceId changed.
                if (id) {
                    request = { year: currentSetting.year(), workplaceId: id };
                }
                // Year changed. workplaceId is unchanged
                else {
                    request = { year: currentSetting.year(), workplaceId: currentSetting.workplaceId() };
                    // Reload alreadySetting list.
                    self.setAlreadySettingWorkplaceList();
                }
                service.findWorkplaceSetting(request)
                    .done(function(data) {
                        self.clearError();
                        // update mode.
                        if (data) {
                            self.isNewMode(false);
                            self.workplaceWTSetting.updateData(data);
                        }
                        // new mode.
                        else {
                            self.isNewMode(true);
                            let newSetting = new WorkPlaceWTSetting();
                            // reserve selected year.
                            newSetting.year(currentSetting.year());
                            self.workplaceWTSetting.updateData(ko.toJS(newSetting));
                        }
                        // Set code + name + id.
                        let tree = $('#list-workplace').getDataList();
                        self.workplaceWTSetting.workplaceId(request.workplaceId);
                        self.setWorkplaceCodeName(tree, request.workplaceId);
                        // Sort month.
                        self.workplaceWTSetting.sortMonth(self.startMonth());
                    });
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
                    let workplace = self.workplaceWTSetting;
                    let command = { year: workplace.year(), workplaceId: workplace.workplaceId() }
                    service.removeWorkplaceSetting(command).done(() => {
                        self.isNewMode(true);
                        self.removeAlreadySettingWorkplace(workplace.workplaceId());
                        // Reserve current code + name + year + id.
                        let newSetting = new WorkPlaceWTSetting();
                        newSetting.year(workplace.year());
                        newSetting.workplaceId(workplace.workplaceId());
                        newSetting.workplaceCode(workplace.workplaceCode());
                        newSetting.workplaceName(workplace.workplaceName());
                        self.workplaceWTSetting.updateData(ko.toJS(newSetting));
                        // Sort month.
                        self.workplaceWTSetting.sortMonth(self.startMonth());
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
                        self.workplaceWTSetting.workplaceCode(data.code);
                        self.workplaceWTSetting.workplaceName(data.name);
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
                service.findAllWorkplaceSetting(self.workplaceWTSetting.year()).done(listId => {
                    self.alreadySettingWorkplaces(_.map(listId, function(id) {
                        return { workplaceId: id, isAlreadySetting: true };
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
                service.saveWorkplaceSetting(ko.toJS(self.workplaceWTSetting)).done(() => {
                    self.isNewMode(false);
                    self.addAlreadySettingWorkplace(self.workplaceWTSetting.workplaceId());
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                }).fail(error => {
                    nts.uk.ui.dialog.alertError(error);
                });
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
                    if ($('#workplaceYearPicker').ntsError('hasError')) {
                        self.workplaceWTSetting.year(new Date().getFullYear());
                    }
                    // Clear error inputs
                    $('.nts-input').ntsError('clear');
                }
            }
        } // ----- end Screen Model
        
        export class WorkPlaceWTSetting {
            deformationLaborSetting: DeformationLaborSetting;
            flexSetting: FlexSetting;
            normalSetting: NormalSetting;
            year: KnockoutObservable<number>;
            workplaceId: KnockoutObservable<string>;
            workplaceCode: KnockoutObservable<string>;
            workplaceName: KnockoutObservable<string>;
            selectedTab: KnockoutObservable<string>;

            constructor() {
                let self = this;
                self.selectedTab = ko.observable('tab-1');
                self.workplaceId = ko.observable('');
                self.workplaceCode = ko.observable('');
                self.workplaceName = ko.observable('');
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