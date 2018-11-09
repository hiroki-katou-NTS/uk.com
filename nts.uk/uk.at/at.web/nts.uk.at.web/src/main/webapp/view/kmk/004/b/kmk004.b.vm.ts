module nts.uk.at.view.kmk004.b {
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
        import GroupOption = nts.uk.com.view.ccg.share.ccg.service.model.GroupOption;
        import EmployeeSearchDto = nts.uk.com.view.ccg.share.ccg.service.model.EmployeeSearchDto;
        import Ccg001ReturnedData = nts.uk.com.view.ccg.share.ccg.service.model.Ccg001ReturnedData;
        
        
        export class ScreenModel {
            
            tabs: KnockoutObservableArray<NtsTabPanelModel>;
            
            worktimeVM: WorktimeSettingVM.ScreenModel;
            
            isLoading: KnockoutObservable<boolean>;
            
            usageUnitSetting: UsageUnitSetting;
            
            startMonth: KnockoutObservable<number>;
            
            selectedEmployeeId: KnockoutObservable<string>;
            displayEmployeeCode: KnockoutObservable<string>;
            displayEmployeeName: KnockoutObservable<string>;
            
            // Employee tab
            lstPersonComponentOption: any;
            selectedEmployeeCode: KnockoutObservable<string>;
            employeeName: KnockoutObservable<string>;
            employeeList: KnockoutObservableArray<UnitModel>;
            alreadySettingPersonal: KnockoutObservableArray<any>;
            ccgcomponentPerson: GroupOption;
            
            ccgcomponent: GroupOption;
            // Options
            isQuickSearchTab: KnockoutObservable<boolean>;
            isAdvancedSearchTab: KnockoutObservable<boolean>;
            isAllReferableEmployee: KnockoutObservable<boolean>;
            isOnlyMe: KnockoutObservable<boolean>;
            isEmployeeOfWorkplace: KnockoutObservable<boolean>;
            isEmployeeWorkplaceFollow: KnockoutObservable<boolean>;
            isMutipleCheck: KnockoutObservable<boolean>;
            isSelectAllEmployee: KnockoutObservable<boolean>;
            periodStartDate: KnockoutObservable<moment.Moment>;
            periodEndDate: KnockoutObservable<moment.Moment>;
            baseDate: KnockoutObservable<moment.Moment>;
            selectedEmployee: KnockoutObservableArray<EmployeeSearchDto>;
            showEmployment: KnockoutObservable<boolean>; // 雇用条件
            showWorkplace: KnockoutObservable<boolean>; // 職場条件
            showClassification: KnockoutObservable<boolean>; // 分類条件
            showJobTitle: KnockoutObservable<boolean>; // 職位条件
            showWorktype: KnockoutObservable<boolean>; // 勤種条件
            inService: KnockoutObservable<boolean>; // 在職区分
            leaveOfAbsence: KnockoutObservable<boolean>; // 休職区分
            closed: KnockoutObservable<boolean>; // 休業区分
            retirement: KnockoutObservable<boolean>; // 退職区分
            systemType: KnockoutObservable<number>;
            showClosure: KnockoutObservable<boolean>; // 就業締め日利用
            showBaseDate: KnockoutObservable<boolean>; // 基準日利用
            showAllClosure: KnockoutObservable<boolean>; // 全締め表示
            showPeriod: KnockoutObservable<boolean>; // 対象期間利用
            periodFormatYM: KnockoutObservable<boolean>; // 対象期間精度
            
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
                self.selectedEmployee = ko.observableArray([]);
                self.selectedEmployeeCode = ko.observable('');
                self.alreadySettingPersonal = ko.observableArray([]);
                
                self.selectedEmployeeId = ko.observable('');
                self.displayEmployeeCode = ko.observable('');
                self.displayEmployeeName = ko.observable('');
                
                // initial ccg options
                self.setDefaultCcg001Option();
                
                // Init component.
                self.reloadCcg001();
                
                self.selectedEmployeeCode.subscribe(function(code){
                    if (code != null && code.length > 0) {
                        let empt = _.find(self.selectedEmployee(), item => item.employeeCode == code);
                        if (empt) {
                            self.selectedEmployeeId(empt.employeeId);
                            self.displayEmployeeCode(code);
                            self.displayEmployeeName(empt.employeeName);
                            self.worktimeVM.isNewMode( !(_.find(self.alreadySettingPersonal(), item => item.code == code ) != null) );
                            return;
                        }
                    }
                    self.selectedEmployeeId('');
                    self.displayEmployeeCode('');
                    self.displayEmployeeName('');
                    self.worktimeVM.isNewMode(true);
                });
                
                self.worktimeVM.groupYear.subscribe(val => {
                    // Validate
                    if ($('#worktimeYearPicker').ntsError('hasError')) {
                        self.clearError();
                        // Reset year if has error.
                        self.worktimeVM.groupYear(new Date().getFullYear());
                        return;
                    } else {
                        self.loadEmployeeSetting();
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
                
                $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent).done(function() {
                    self.employeeList = ko.observableArray<UnitModel>([]);
                    self.applyKCP005ContentSearch([]);
                    
                    // Load employee list component
                    $('#list-employee').ntsListComponent(self.lstPersonComponentOption).done(function() {
                        if(self.employeeList().length <= 0){
                            $('#ccg001-btn-search-drawer').trigger('click');  
                        }
                        
                        self.isLoading(false);
                        
                        self.loadEmployeeSetting(); // load setting for initial selection
                        
                        // subscribe to furture selection
                        self.selectedEmployeeId.subscribe(function(Id) {
                            self.loadEmployeeSetting();
                        });
                        
                        ko.applyBindingsToNode($('#lblEmployeeCode')[0], { text: self.displayEmployeeCode });
                        ko.applyBindingsToNode($('#lblEmployeeName')[0], { text: self.displayEmployeeName });
                        
                        self.worktimeVM.postBindingHandler();
                    });
                });
            }
            
            /**
             * Set default ccg001 options
             */
            public setDefaultCcg001Option(): void {
                let self = this;
                self.isQuickSearchTab = ko.observable(true);
                self.isAdvancedSearchTab = ko.observable(true);
                self.isAllReferableEmployee = ko.observable(true);
                self.isOnlyMe = ko.observable(true);
                self.isEmployeeOfWorkplace = ko.observable(true);
                self.isEmployeeWorkplaceFollow = ko.observable(true);
                self.isMutipleCheck = ko.observable(true);
                self.isSelectAllEmployee = ko.observable(true);
                self.baseDate = ko.observable(moment());
                self.periodStartDate = ko.observable(moment());
                self.periodEndDate = ko.observable(moment());
                self.showEmployment = ko.observable(true); // 雇用条件
                self.showWorkplace = ko.observable(true); // 職場条件
                self.showClassification = ko.observable(true); // 分類条件
                self.showJobTitle = ko.observable(true); // 職位条件
                self.showWorktype = ko.observable(true); // 勤種条件
                self.inService = ko.observable(true); // 在職区分
                self.leaveOfAbsence = ko.observable(true); // 休職区分
                self.closed = ko.observable(true); // 休業区分
                self.retirement = ko.observable(true); // 退職区分
                self.systemType = ko.observable(2); // 就業
                self.showClosure = ko.observable(true); // 就業締め日利用
                self.showBaseDate = ko.observable(true); // 基準日利用
                self.showAllClosure = ko.observable(true); // 全締め表示
                self.showPeriod = ko.observable(true); // 対象期間利用
                self.periodFormatYM = ko.observable(false); // 対象期間精度
            }
            
            /**
             * Reload component CCG001
             */
            public reloadCcg001(): void {
                var self = this;
                var periodStartDate, periodEndDate : string;
                if (self.showBaseDate()){
                    periodStartDate = moment(self.periodStartDate()).format("YYYY-MM-DD");
                    periodEndDate = moment(self.periodEndDate()).format("YYYY-MM-DD");
                } else {
                    periodStartDate = moment(self.periodStartDate()).format("YYYY-MM");
                    periodEndDate = moment(self.periodEndDate()).format("YYYY-MM"); // 対象期間終了日
                }
                
                if (!self.showBaseDate() && !self.showClosure() && !self.showPeriod()){
                    nts.uk.ui.dialog.alertError("Base Date or Closure or Period must be shown!" );
                    return;
                }
                self.ccgcomponent = {
                    /** Common properties */
                    systemType: self.systemType(), // システム区分
                    showEmployeeSelection: self.isSelectAllEmployee(), // 検索タイプ
                    showQuickSearchTab: self.isQuickSearchTab(), // クイック検索
                    showAdvancedSearchTab: self.isAdvancedSearchTab(), // 詳細検索
                    showBaseDate: self.showBaseDate(), // 基準日利用
                    showClosure: self.showClosure(), // 就業締め日利用
                    showAllClosure: self.showAllClosure(), // 全締め表示
                    showPeriod: self.showPeriod(), // 対象期間利用
                    periodFormatYM: self.periodFormatYM(), // 対象期間精度

                    /** Required parameter */
                    baseDate: moment(self.baseDate()).format("YYYY-MM-DD"), // 基準日
                    periodStartDate: periodStartDate, // 対象期間開始日
                    periodEndDate: periodEndDate, // 対象期間終了日
                    inService: self.inService(), // 在職区分
                    leaveOfAbsence: self.leaveOfAbsence(), // 休職区分
                    closed: self.closed(), // 休業区分
                    retirement: self.retirement(), // 退職区分

                    /** Quick search tab options */
                    showAllReferableEmployee: self.isAllReferableEmployee(), // 参照可能な社員すべて
                    showOnlyMe: self.isOnlyMe(), // 自分だけ
                    showSameWorkplace: self.isEmployeeOfWorkplace(), // 同じ職場の社員
                    showSameWorkplaceAndChild: self.isEmployeeWorkplaceFollow(), // 同じ職場とその配下の社員

                    /** Advanced search properties */
                    showEmployment: self.showEmployment(), // 雇用条件
                    showWorkplace: self.showWorkplace(), // 職場条件
                    showClassification: self.showClassification(), // 分類条件
                    showJobTitle: self.showJobTitle(), // 職位条件
                    showWorktype: self.showWorktype(), // 勤種条件
                    isMutipleCheck: self.isMutipleCheck(), // 選択モード

                    /** Return data */
                    returnDataFromCcg001: function(data: Ccg001ReturnedData) {
                        self.selectedEmployee(data.listEmployee);
                        
                        service.findAlreadySetting().done((settingData) => {
                            let listCode = _.map(self.selectedEmployee(), item => {
                                if ( _.find(settingData, it => { return it.employeeId == item.employeeId; } )) return item.employeeCode;
                                return null; 
                            });
                            listCode = _.filter(listCode, item => { return item != null });
                            let listSelected = [];
                            
                            _.each(listCode, item => {
                                listSelected.push( { code: item, isAlreadySetting: true } );
                            });
                            
                            self.alreadySettingPersonal(listSelected);
                            self.applyKCP005ContentSearch(self.selectedEmployee());
                        });
                    }
                }
            }
            
            /**
            * apply ccg001 search data to kcp005
            */
            public applyKCP005ContentSearch(dataList: EmployeeSearchDto[]): void {
                var self = this;
                self.employeeList([]);
                var employeeSearchs: UnitModel[] = [];
                for (var employeeSearch of dataList) {
                    var employee: UnitModel = {
                        code: employeeSearch.employeeCode,
                        name: employeeSearch.employeeName,
                        workplaceName: employeeSearch.workplaceName
                    };
                    employeeSearchs.push(employee);
                }
                self.employeeList(employeeSearchs);
                self.selectedEmployeeCode(employeeSearchs.length > 0 ? employeeSearchs[0].code : '');
                self.lstPersonComponentOption = {
                    isShowAlreadySet: true,
                    isMultiSelect: false,
                    listType: ListType.EMPLOYEE,
                    employeeInputList: self.employeeList,
                    selectType: SelectType.SELECT_FIRST_ITEM,
                    selectedCode: self.selectedEmployeeCode,
                    isDialog: false,
                    isShowNoSelectRow: false,
                    alreadySettingList: self.alreadySettingPersonal,
                    isShowWorkPlaceName: true,
                    isShowSelectAllButton: true,
                    maxWidth: 385,
                    maxRows: 15
                };
            }
            
            private loadEmployeeSetting(): void {
                let self = this;
                
                let year = self.worktimeVM.worktimeSetting.normalSetting().year();
                if(nts.uk.util.isNullOrEmpty(year)) {
                    return;
                }
                
                let empId = self.selectedEmployeeId();
                if (nts.uk.text.isNullOrEmpty(empId)) {
                    self.resetFieldsToNewMode();
                    return;
                }
                
                nts.uk.ui.block.invisible();
                service.findEmployeeSetting(self.worktimeVM.worktimeSetting.normalSetting().year(), empId).done((data) => {
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
            
            public saveEmployeeSetting(): void {
                let self = this;
                // Validate
                if (self.worktimeVM.hasError()) {
                    return;
                }
                nts.uk.ui.block.invisible();
                
                let saveCommand: EmployeeWorktimeSettingDtoSaveCommand = new EmployeeWorktimeSettingDtoSaveCommand();
                saveCommand.updateData(self.selectedEmployeeId(), self.worktimeVM.worktimeSetting);
                
                service.saveEmployeeSetting(ko.toJS(saveCommand)).done(() => {
                    self.worktimeVM.isNewMode(false);
                    self.addAlreadySettingPersonal(self.displayEmployeeCode());
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                }).fail(error => {
                    nts.uk.ui.dialog.alertError(error);
                }).always(() => {
                    nts.uk.ui.block.clear();
                });
            }
            
            public removeEmployeeSetting(): void {
                let self = this;
                nts.uk.ui.block.invisible();
                
                let sid = self.selectedEmployeeId();
                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function() {
                    service.removeEmployeeSetting({ year: self.worktimeVM.worktimeSetting.normalSetting().year(), sid: sid }).done((res) => {
                        
                        // new mode.
                        self.worktimeVM.isNewMode(true);
                        let newSetting = new WorktimeSettingDto();
                        // Reserve selected year.
                        newSetting.updateYear(self.worktimeVM.worktimeSetting.normalSetting().year());
                        
                        //check common is remove
                        if (res.wtsettingCommonRemove) {
                            self.removeAlreadySettingPersonal(self.displayEmployeeCode());
                            
                            // Update Full Data
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
                        nts.uk.ui.block.clear();
                    });
                }).ifNo(function() {
                    nts.uk.ui.block.clear();
                    return;
                })
            }
            
            /**
             * Add alreadySetting employment.
             */
            private addAlreadySettingPersonal(code:string): void {
                let self = this;
                let l = self.alreadySettingPersonal().filter(i => code == i.code);
                if (l[0]) {
                    return;
                }
                self.alreadySettingPersonal.push({ code: code, isAlreadySetting: true });
            }
            
            /**
             * Remove alreadySetting employment.
             */
            private removeAlreadySettingPersonal(code: string): void {
                let self = this;
                let ase = self.alreadySettingPersonal().filter(i => code == i.code)[0];
                self.alreadySettingPersonal.remove(ase);
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
        } // --- end ScreenModel
        
        export class ListType {
            static EMPLOYMENT = 1;
            static Classification = 2;
            static JOB_TITLE = 3;
            static EMPLOYEE = 4;
        }

        export interface UnitModel {
            code: string;
            name?: string;
            workplaceName?: string;
            isAlreadySetting?: boolean;
        }

        export class SelectType {
            static SELECT_BY_SELECTED_CODE = 1;
            static SELECT_ALL = 2;
            static SELECT_FIRST_ITEM = 3;
            static NO_SELECT = 4;
        }
 
        class EmployeeStatutoryWorktimeSettingDto extends StatutoryWorktimeSettingDto {
            employeeId: string;
        }
        
        class EmployeeMonthlyCalSettingDto extends MonthlyCalSettingDto {
            employeeId: string;
        }
        
        export class EmployeeWorktimeSettingDtoSaveCommand {

            /** The save com stat work time set command. */
            saveStatCommand: EmployeeStatutoryWorktimeSettingDto;
    
            /** The save com flex command. */
            saveMonthCommand: EmployeeMonthlyCalSettingDto;
    
            constructor() {
                let self = this;
                self.saveStatCommand = new EmployeeStatutoryWorktimeSettingDto();
                self.saveMonthCommand = new EmployeeMonthlyCalSettingDto();
            }
    
            public updateYear(year: number): void {
                let self = this;
                self.saveStatCommand.year = year;
            }
    
            public updateData(employeeId: string, model: WorktimeSetting): void {
                let self = this;
                self.saveStatCommand.employeeId = employeeId;
                self.saveStatCommand.updateData(model);
                self.saveMonthCommand.employeeId = employeeId;
                self.saveMonthCommand.updateData(model);
            }
        }
    }
}