module nts.uk.at.view.ksu007.a {

    import ScheduleBatchCorrectSettingSave = service.model.ScheduleBatchCorrectSettingSave;

    export module viewmodel {
        export class ScreenModel {
                        
            // setup ccg001
            ccgcomponent: GroupOption;

            // Options
            baseDate: KnockoutObservable<Date>;
            selectedEmployee: KnockoutObservableArray<EmployeeSearchDto>;

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
            
            periodDate:KnockoutObservable<any>;
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

                self.startDateString = ko.observable("");
                self.endDateString = ko.observable("");
                self.selectedEmployee = ko.observableArray([]);
                self.selectedEmployeeCode = ko.observableArray([]);
                self.alreadySettingPersonal = ko.observableArray([]);
                self.baseDate = ko.observable(new Date());
                
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
                self.ccgcomponent = {
                    baseDate: self.baseDate,
                    //Show/hide options
                    isQuickSearchTab: true,
                    isAdvancedSearchTab: true,
                    isAllReferableEmployee: true,
                    isOnlyMe: true,
                    isEmployeeOfWorkplace: true,
                    isEmployeeWorkplaceFollow: true,
                    isMutipleCheck: true,
                    isSelectAllEmployee: true,
                    /**
                    * @param dataList: list employee returned from component.
                    * Define how to use this list employee by yourself in the function's body.
                    */
                    onSearchAllClicked: function(dataList: EmployeeSearchDto[]) {
                        self.selectedEmployee(dataList);
                        self.applyKCP005ContentSearch(dataList);
                    },
                    onSearchOnlyClicked: function(data: EmployeeSearchDto) {
                        var dataEmployee: EmployeeSearchDto[] = [];
                        dataEmployee.push(data);
                        self.selectedEmployee(dataEmployee);
                        self.applyKCP005ContentSearch(dataEmployee);
                    },
                    onSearchOfWorkplaceClicked: function(dataList: EmployeeSearchDto[]) {
                        self.selectedEmployee(dataList);
                        self.applyKCP005ContentSearch(dataList);
                    },
                    onSearchWorkplaceChildClicked: function(dataList: EmployeeSearchDto[]) {
                        self.selectedEmployee(dataList);
                        self.applyKCP005ContentSearch(dataList);
                    },
                    onApplyEmployee: function(dataEmployee: EmployeeSearchDto[]) {
                        self.applyKCP005ContentSearch(dataEmployee);
                    }

                }
                self.scheduleBatchCorrectSettingInfo = ko.observable(new ScheduleBatchCorrectSetting());
                self.workTypeInfo = ko.observable('');
                self.workTypeCode = ko.observable('');
                self.workTimeInfo = ko.observable('');
                self.workTimeCode = ko.observable('');
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
            
            /**
             * function call service save ScheduleBatchCorrectSetting by button is click
             */
            private saveScheduleBatchCorrectSettingProcess() {
                var self = this;
                nts.uk.ui.block.invisible();
                service.checkScheduleBatchCorrectSettingSave(self.collecData()).done(function() {
                    nts.uk.ui.block.clear();
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