module nts.uk.at.view.ksu007.a {

    import ScheduleBatchCorrectSettingSave = service.model.ScheduleBatchCorrectSettingSave;
    import Ccg001ReturnedData = nts.uk.com.view.ccg.share.ccg.service.model.Ccg001ReturnedData;
    import EmployeeSearchDto = nts.uk.com.view.ccg.share.ccg.service.model.EmployeeSearchDto;
    import GroupOption = nts.uk.com.view.ccg.share.ccg.service.model.GroupOption;

    export module viewmodel {
        export class ScreenModel {
                        
            ccgcomponent: GroupOption;
            systemTypes: KnockoutObservableArray<any>;

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

            selectedImplementAtrCode: KnockoutObservable<number>;
            checkReCreateAtrAllCase: KnockoutObservable<boolean>;
            checkReCreateAtrOnlyUnConfirm: KnockoutObservable<boolean>;
            checkProcessExecutionAtrRebuild: KnockoutObservable<boolean>;
            checkProcessExecutionAtrReconfig: KnockoutObservable<boolean>;
            checkCreateMethodAtrPersonalInfo: KnockoutObservable<boolean>;
            checkCreateMethodAtrPatternSchedule: KnockoutObservable<boolean>;
            checkCreateMethodAtrCopyPastSchedule: KnockoutObservable<boolean>;
            resetWorkingHours: KnockoutObservable<boolean>;
            resetDirectLineBounce: KnockoutObservable<boolean>;
            resetMasterInfo: KnockoutObservable<boolean>;
            resetTimeChildCare: KnockoutObservable<boolean>;
            resetAbsentHolidayBusines: KnockoutObservable<boolean>;
            resetTimeAssignment: KnockoutObservable<boolean>;
            workTypeInfo: KnockoutObservable<string>;
            workTypeCode: KnockoutObservable<string>;
            workTimeInfo: KnockoutObservable<string>;
            workTimeCode: KnockoutObservable<string>;
            scheduleBatchCorrectSettingInfo: KnockoutObservable<ScheduleBatchCorrectSetting>;
            
            periodDate: KnockoutObservable<any>;
            copyStartDate: KnockoutObservable<Date>;
            startDateString: KnockoutObservable<string>;
            endDateString: KnockoutObservable<string>;

            lstLabelInfomation: KnockoutObservableArray<string>;
            infoCreateMethod: KnockoutObservable<string>;
            infoPeriodDate: KnockoutObservable<string>;
            lengthEmployeeSelected: KnockoutObservable<string>;
            
            // Employee tab
            lstPersonComponentOption: any;
            selectedEmployeeCode: KnockoutObservableArray<string>;
            employeeName: KnockoutObservable<string>;
            employeeList: KnockoutObservableArray<UnitModel>;
            alreadySettingPersonal: KnockoutObservableArray<UnitAlreadySettingModel>;
            ccgcomponentPerson: GroupOption;

            //for control field
            isReCreate: KnockoutObservable<boolean>;
            isReSetting: KnockoutObservable<boolean>;
            constructor() {
                var self = this;

                self.systemTypes = ko.observableArray([
                    { name: 'システム管理者', value: 1 }, // PERSONAL_INFORMATION
                    { name: '就業', value: 2 } // EMPLOYMENT
                ]);
                self.selectedEmployee = ko.observableArray([]);

                // initial ccg options
                self.setDefaultCcg001Option();
                
                // Init component.
                self.reloadCcg001();
                
                self.periodFormatYM.subscribe(item => {
                    if (item){
                        self.showClosure(true);    
                    }
                });
                
                self.startDateString = ko.observable("");
                self.endDateString = ko.observable("");
                self.selectedEmployeeCode = ko.observableArray([]);
                self.alreadySettingPersonal = ko.observableArray([]);
                
                self.periodDate = ko.observable({});
                self.checkReCreateAtrOnlyUnConfirm = ko.observable(false);
                self.checkReCreateAtrAllCase = ko.observable(true);
                self.checkProcessExecutionAtrRebuild = ko.observable(true);
                self.checkProcessExecutionAtrReconfig = ko.observable(false);
                self.resetWorkingHours = ko.observable(false);
                self.resetDirectLineBounce = ko.observable(false);
                self.resetMasterInfo = ko.observable(false);
                self.resetTimeChildCare = ko.observable(false);
                self.resetAbsentHolidayBusines = ko.observable(false);
                self.resetTimeAssignment = ko.observable(false);
                self.checkCreateMethodAtrPersonalInfo = ko.observable(true);
                self.checkCreateMethodAtrPatternSchedule = ko.observable(false);
                self.checkCreateMethodAtrCopyPastSchedule = ko.observable(false);
                self.copyStartDate = ko.observable(new Date());
                
                self.scheduleBatchCorrectSettingInfo = ko.observable(new ScheduleBatchCorrectSetting());
                self.workTypeInfo = ko.observable('');
                self.workTypeCode = ko.observable('');
                self.workTimeInfo = ko.observable('');
                self.workTimeCode = ko.observable('');
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
                self.isSelectAllEmployee = ko.observable(false);
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
                self.systemType = ko.observable(2);
                self.showClosure = ko.observable(false); // 就業締め日利用
                self.showBaseDate = ko.observable(true); // 基準日利用
                self.showAllClosure = ko.observable(true); // 全締め表示
                self.showPeriod = ko.observable(false); // 対象期間利用
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
                        self.applyKCP005ContentSearch(data.listEmployee);
                    }
                }
                //$('#ccgcomponent').ntsGroupComponent(self.ccgcomponent);
            }
            
            /**
             * save to client service ScheduleBatchCorrectSetting by employeeId
            */
            private saveScheduleBatchCorrectSettingByEmployeeId(employeeId: string, data: ScheduleBatchCorrectSetting): void {
                nts.uk.characteristics.save("PersonalSchedule_" + employeeId, data);
            }
            
            /**
             * save to client service ScheduleBatchCorrectSetting
            */
            private saveScheduleBatchCorrectSetting(data: ScheduleBatchCorrectSetting): void {
                var self = this;
                var user: any = __viewContext.user;
                self.saveScheduleBatchCorrectSettingByEmployeeId(user.employeeId, data);
            }

            /**
             * find by client service ScheduleBatchCorrectSetting by employee
            */
            private findScheduleBatchCorrectSettingByEmployeeId(employeeId: string): JQueryPromise<ScheduleBatchCorrectSetting> {
                return nts.uk.characteristics.restore("PersonalSchedule_" + employeeId);
            }

            /**
             * find by client service ScheduleBatchCorrectSetting
            */
            private findScheduleBatchCorrectSetting(): JQueryPromise<ScheduleBatchCorrectSetting> {
                var self = this;
                var user: any = __viewContext.user;
                return self.findScheduleBatchCorrectSettingByEmployeeId(user.employeeId);
            }
            
            /**
           * start page data 
           */
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();

                dfd.resolve(self);
                return dfd.promise();
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
                self.lstPersonComponentOption = {
                    isShowAlreadySet: false,
                    isMultiSelect: true,
                    listType: ListType.EMPLOYEE,
                    employeeInputList: self.employeeList,
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,
                    selectedCode: self.selectedEmployeeCode,
                    isDialog: false,
                    isShowNoSelectRow: false,
                    alreadySettingList: self.alreadySettingPersonal,
                    isShowWorkPlaceName: true,
                    isShowSelectAllButton: true,
                    maxWidth: 550,
                    maxRows: 15
                };

            }
            
            private validate(): boolean {
                var self = this;
                let dfd = $.Deferred<void>();
                $('.nts-input').ntsEditor('validate');
                if ($('.nts-input').ntsError('hasError'))
                    return false;
                return true;
            }
            
            /**
             * function call service save ScheduleBatchCorrectSetting by button is click
             */
            private saveScheduleBatchCorrectSettingProcess() {
                var self = this;
                if (!self.validate())
                    return;
                
                //return if has error
                if (nts.uk.ui.errors.hasError()) {
                    return;
                }                
                nts.uk.ui.block.invisible();
                service.checkScheduleBatchCorrectSettingSave(self.collecData()).done(function() {
                    nts.uk.ui.block.clear();                
                    var user: any = __viewContext.user;
                    var employeeId: string = user.employeeId;
                    
                    //get info スケジュール一括修正設定 in screen                    
                    var scheduleBatchCorrectSetting = new ScheduleBatchCorrectSetting();
                    
                    scheduleBatchCorrectSetting.employeeId = employeeId;                    
                    scheduleBatchCorrectSetting.startDate = self.periodDate().startDate;
                    scheduleBatchCorrectSetting.endDate = self.periodDate().endDate;
                    scheduleBatchCorrectSetting.worktypeCode = self.workTypeInfo();
                    scheduleBatchCorrectSetting.worktimeCode = self.workTimeInfo();
                      
                    self.scheduleBatchCorrectSettingInfo = ko.observable(scheduleBatchCorrectSetting);
                    self.saveScheduleBatchCorrectSetting(self.scheduleBatchCorrectSettingInfo());
                                        
                    nts.uk.ui.windows.setShared('inputKSU007', self.collecData());
                    nts.uk.ui.windows.sub.modal("/view/ksu/007/b/index.xhtml");
                }).fail(function(error) {
                    nts.uk.ui.block.clear();
                    nts.uk.ui.dialog.alertError(error);
                });
            }

            /**
             * convert string to date
             */
            private toDate(strDate: string): Date {
                return moment(strDate, 'YYYY/MM/DD').toDate();
            }
            
            /**
             * function collect data 
             */
            private collecData(): ScheduleBatchCorrectSettingSave {
                var self = this;
                var user: any = __viewContext.user;
                var dto: ScheduleBatchCorrectSettingSave = {
                    worktypeCode: self.workTypeCode(),
                    employeeId: user.employeeId,
                    endDate: self.toDate(self.periodDate().endDate),
                    startDate: self.toDate(self.periodDate().startDate),
                    worktimeCode: self.workTimeCode(),
                    employeeIds: self.findEmployeeIdsByCodes(self.selectedEmployeeCode())
                };
                return dto;
            }
            /**
             * function open dialog KDL003
             */
            private openDialogKDL003(): void {
                var self = this;
                // set update data input open dialog kdl003
                nts.uk.ui.windows.setShared('parentCodes', {
                    workTypeCodes: [],
                    selectedWorkTypeCode: self.scheduleBatchCorrectSettingInfo().worktypeCode,
                    workTimeCodes: [],
                    selectedWorkTimeCode: self.scheduleBatchCorrectSettingInfo().worktimeCode
                }, true);

                nts.uk.ui.windows.sub.modal('/view/kdl/003/a/index.xhtml').onClosed(function(): any {
                    //view all code of selected item 
                    var childData = nts.uk.ui.windows.getShared('childData');
                    if (childData) {
                        self.workTypeInfo(childData.selectedWorkTypeCode + ' ' + childData.selectedWorkTypeName);
                        self.workTypeCode(childData.selectedWorkTypeCode);
                        self.workTimeCode(childData.selectedWorkTimeCode);
                        self.workTimeInfo(childData.selectedWorkTimeCode + ' ' + childData.selectedWorkTimeName);
                    }            
                });
            }
            
            /**
             * update selected employee kcp005 => detail
             */
            private findByCodeEmployee(employeeCode: string): UnitModel {
                var employee: UnitModel;
                var self = this;
                for (var employeeSelect of self.employeeList()) {
                    if (employeeSelect.code === employeeCode) {
                        employee = employeeSelect;
                        break;
                    }
                }
                return employee;
            }
            
            /**
             * find employee id in selected
             */
            private findEmployeeIdByCode(employeeCode: string): string {
                var self = this;
                var employeeId = '';
                for (var employee of self.selectedEmployee()) {
                    if (employee.employeeCode === employeeCode) {
                        employeeId = employee.employeeId;
                    }
                }
                return employeeId;
            }
            /**
             * find employee code in selected
             */
            private findEmployeeCodeById(employeeId: string): string {
                var self = this;
                var employeeCode = '';
                for (var employee of self.selectedEmployee()) {
                    if (employee.employeeId === employeeId) {
                        employeeCode = employee.employeeCode;
                    }
                }
                return employeeCode;
            }
            
            /**
             * find employee id in selected
             */
            private findEmployeeIdsByCodes(employeeCodes: string[]): string[] {
                var self = this;
                var employeeIds: string[] = [];
                for (var employeeCode of employeeCodes) {
                    employeeIds.push(self.findEmployeeIdByCode(employeeCode));
                }
                return employeeIds;
            }

        }
        

        // スケジュール一括修正設定
        export class ScheduleBatchCorrectSetting {
            // 勤務種類
            worktypeCode: string;

            // 社員ID
            employeeId: string;

            // 終了日
            endDate: string;

            // 開始日
            startDate: string;

            // 就業時間帯
            worktimeCode: string;

            constructor() {
                var self = this;
                self.worktypeCode = '';
                self.employeeId = '';
                self.endDate = '';
                self.startDate = '';
                self.worktimeCode = '';
            }
        }
        

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

        export interface UnitAlreadySettingModel {
            code: string;
            isAlreadySetting: boolean;
        }

        export interface EmployeeSearchDto {
            employeeId: string;

            employeeCode: string;

            employeeName: string;

            workplaceCode: string;

            workplaceId: string;

            workplaceName: string;
        }

        export interface GroupOption {
            baseDate?: KnockoutObservable<Date>;
            
            periodStartDate?: KnockoutObservable<Date>;
            
            periodEndDate?: KnockoutObservable<Date>;
            
            inService: boolean;
            
            leaveOfAbsence: boolean;
            
            closed: boolean;
            
            retirement: boolean;
            
            // クイック検索タブ
            isQuickSearchTab: boolean;
            // 参照可能な社員すべて
            isAllReferableEmployee: boolean;
            //自分だけ
            isOnlyMe: boolean;
            //おなじ部門の社員
            isEmployeeOfWorkplace: boolean;
            //おなじ＋配下部門の社員
            isEmployeeWorkplaceFollow: boolean;


            // 詳細検索タブ
            isAdvancedSearchTab: boolean;
            //複数選択 
            isMutipleCheck: boolean;

            //社員指定タイプ or 全社員タイプ
            isSelectAllEmployee: boolean;

            onSearchAllClicked: (data: EmployeeSearchDto[]) => void;

            onSearchOnlyClicked: (data: EmployeeSearchDto) => void;

            onSearchOfWorkplaceClicked: (data: EmployeeSearchDto[]) => void;

            onSearchWorkplaceChildClicked: (data: EmployeeSearchDto[]) => void;

            onApplyEmployee: (data: EmployeeSearchDto[]) => void;
        }
    }
}