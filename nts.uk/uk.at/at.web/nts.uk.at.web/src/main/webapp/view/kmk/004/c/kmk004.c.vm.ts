module nts.uk.at.view.kmk004.c {
    export module viewmodel {
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
        
        export class ScreenModel {
            
            tabs: KnockoutObservableArray<NtsTabPanelModel>;
            
            employmentWTSetting: EmploymentWTSetting;
            worktimeSetting: WorktimeSettingVM.ScreenModel;
            
            // Employment list component.
            employmentComponentOption: any;
            alreadySettingEmployments: KnockoutObservableArray<any>;
            selectedEmploymentCode: KnockoutObservable<string>;
            
            isNewMode: KnockoutObservable<boolean>;
            isLoading: KnockoutObservable<boolean>;
            
            employmentCode: KnockoutObservable<string>;
            employmentName: KnockoutObservable<string>;
            
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
                
                self.worktimeSetting = new WorktimeSettingVM.ScreenModel();
                self.employmentWTSetting = new EmploymentWTSetting();
                // Employment list component.
                self.alreadySettingEmployments = ko.observableArray([]);
                self.selectedEmploymentCode = ko.observable('');
                self.setEmploymentComponentOption();
                self.selectedEmploymentCode.subscribe(code => {
                    if (code) {
                        self.loadEmploymentSetting(code);
                    }
                });
                
                self.employmentCode = ko.observable('');
                self.employmentName = ko.observable('');
                self.employmentWTSetting.employmentCode.subscribe(function(v) {
                    self.employmentCode(v);
                });
                self.employmentWTSetting.employmentName.subscribe(function(v) {
                    self.employmentName(v);
                });
            }
                
            public startPage(): JQueryPromise<void> {
                nts.uk.ui.block.invisible();
                let self = this;
                let dfd = $.Deferred<void>();
                
                self.isLoading(true);
                self.employmentWTSetting.year(self.worktimeSetting.companyWTSetting.year());

                // Load component.
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
                
                $('#list-employment').ntsListComponent(this.employmentComponentOption).done(() => {
                    self.isLoading(false);
    
                    // Force to reload.
                    if (self.employmentWTSetting.employmentCode() === self.selectedEmploymentCode()) {
                        self.loadEmploymentSetting(self.selectedEmploymentCode());
                    }
                    $('#employmentYearPicker').focus();
                    // Set already setting list.
                    self.setAlreadySettingEmploymentList();
                    self.employmentWTSetting.selectedTab('tab-1');
                    
                    ko.applyBindingsToNode($('#lblEmploymentCode')[0], { text: self.employmentCode });
                    ko.applyBindingsToNode($('#lblEmploymentName')[0], { text: self.employmentName });
                }).always(() => {
                    nts.uk.ui.block.clear();
                });
            }
            
            /**
             * Load employment setting.
             */
            public loadEmploymentSetting(code?: string): void {
                let self = this;
                let currentSetting = self.employmentWTSetting;
                let request;
                // Code changed.
                if (code) {
                    request = { year: currentSetting.year(), employmentCode: code };
                }
                // Year changed. Code is unchanged
                else {
                    request = { year: currentSetting.year(), employmentCode: currentSetting.employmentCode() };
                    // Reload alreadySetting list.
                    self.setAlreadySettingEmploymentList();
                }
                service.findEmploymentSetting(request)
                    .done(function(data) {
                        self.clearError();
                        // update mode.
                        if (data) {
                            self.isNewMode(false);
                            self.employmentWTSetting.updateData(data);
                        }
                        // new mode.
                        else {
                            self.isNewMode(true);
                            let newSetting = new EmploymentWTSetting();
                            // reserve selected year.
                            newSetting.year(currentSetting.year());
                            self.employmentWTSetting.updateData(ko.toJS(newSetting));
                        }
                        // Set code + name.
                        self.employmentWTSetting.employmentCode(request.employmentCode);
                        self.setEmploymentName(request.employmentCode);
                        // Sort month.
                        self.employmentWTSetting.sortMonth(self.startMonth());
                    });
            }
            
            /**
             * Set employment name
             */
            private setEmploymentName(code: string): void {
                let self = this;
                let list = $('#list-employment').getDataList();
                if (list) {
                    let empt = _.find(list, item => item.code == code);
                    self.employmentWTSetting.employmentName(empt.name);
                }
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
                service.findAllEmploymentSetting(self.employmentWTSetting.year()).done(listCode => {
                    self.alreadySettingEmployments(_.map(listCode, function(code) {
                        return { code: code, isAlreadySetting: true };
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
                service.saveEmploymentSetting(ko.toJS(self.employmentWTSetting)).done(() => {
                    self.isNewMode(false);
                    self.addAlreadySettingEmloyment(self.employmentWTSetting.employmentCode());
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                }).fail(error => {
                    nts.uk.ui.dialog.alertError(error);
                });
            }
            
            /**
             * Remove employment setting.
             */
            public removeEmployment(): void {
                let self = this;
                if ($('#employmentYearPicker').ntsError('hasError')) {
                    return;
                }
                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function() {
                    let empt = self.employmentWTSetting;
                    let command = { year: empt.year(), employmentCode: empt.employmentCode() }
                    service.removeEmploymentSetting(command).done(() => {
                        self.isNewMode(true);
                        self.removeAlreadySettingEmployment(empt.employmentCode());
                        // Reserve current code + name + year.
                        let newEmpt = new EmploymentWTSetting();
                        newEmpt.employmentCode(empt.employmentCode());
                        newEmpt.employmentName(empt.employmentName());
                        newEmpt.year(empt.year());
                        self.employmentWTSetting.updateData(ko.toJS(newEmpt));
                        // Sort month.
                        self.employmentWTSetting.sortMonth(self.startMonth());
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
                    if ($('#employmentYearPicker').ntsError('hasError')) {
                        self.employmentWTSetting.year(new Date().getFullYear());
                    }
                    // Clear error inputs
                    $('.nts-input').ntsError('clear');
                }
            }
        } // ----- end ScreenModel
        
        export class EmploymentWTSetting {
            deformationLaborSetting: DeformationLaborSetting;
            flexSetting: FlexSetting;
            normalSetting: NormalSetting;
            year: KnockoutObservable<number>;
            employmentCode: KnockoutObservable<string>;
            employmentName: KnockoutObservable<string>;
            selectedTab: KnockoutObservable<string>;

            constructor() {
                let self = this;
                self.selectedTab = ko.observable('tab-1');
                self.employmentCode = ko.observable('');
                self.employmentName = ko.observable('');
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