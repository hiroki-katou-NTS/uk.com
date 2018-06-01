module nts.uk.com.view.kwr002.a {
    import blockUI = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export module viewModel {
        export class ScreenModel {
            ccgcomponent: GroupOption;
            exportDto: KnockoutObservable<ExportDto>;
            enableSave: KnockoutObservable<boolean>;
            baseDate: KnockoutObservable<Date>;
            periodStartDate: KnockoutObservable<moment.Moment>;
            employeeList: KnockoutObservableArray<UnitModel>;
            //            alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
            //            listComponentOption: any;
            //            optionalColumnDatasource: KnockoutObservableArray<any>;

            //            enChange: KnockoutObservable<boolean>;

            columns: KnockoutObservableArray<any>;
            currentCode: KnockoutObservable<any>;
            currentCodeList: KnockoutObservableArray<string>;

            enable: KnockoutObservable<boolean>;
            dateValue: KnockoutObservable<any>;
            startDateString: KnockoutObservable<string>;
            endDateString: KnockoutObservable<string>;

            attendanceRecordList: KnockoutObservableArray<AttendanceRecordExportSettingDto>;
            selectedCode: KnockoutObservable<string>;

            selectedEmployee: KnockoutObservableArray<any>;
            employeeName: KnockoutObservable<string>;

            permission: KnockoutObservable<boolean>;

            constructor() {
                let self = this;
                let currentDate = self.getCurrentDay(new Date());
                self.enable = ko.observable(true);
                self.permission = ko.observable(true);
                self.startDateString = ko.observable("");
                self.endDateString = ko.observable("");
                self.dateValue = ko.observable({ startDate: currentDate, endDate: currentDate });
                self.exportDto = ko.observable<ExportDto>();

                self.startDateString.subscribe(function(value) {
                    self.dateValue().startDate = value;
                    self.dateValue.valueHasMutated();
                });

                self.endDateString.subscribe(function(value) {
                    self.dateValue().endDate = value;
                    self.dateValue.valueHasMutated();
                });

                self.attendanceRecordList = ko.observableArray([]);

                self.selectedCode = ko.observable('');

                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KWR002_13"), key: 'id', width: 140, hidden: true },
                    { headerText: nts.uk.resource.getText("KWR002_13"), key: 'code', width: 140 },
                    { headerText: nts.uk.resource.getText("KWR002_14"), key: 'name', width: 200 },
                    { headerText: nts.uk.resource.getText("KWR002_15"), key: 'workplaceName', width: 150 }
                ]);

                self.currentCode = ko.observable();
                self.currentCodeList = ko.observableArray([]);
                self.selectedEmployee = ko.observableArray([]);

                self.baseDate = ko.observable(new Date());
                self.periodStartDate = ko.observable(moment());
                self.employeeList = ko.observableArray<UnitModel>([]);
                //                self.alreadySettingList = ko.observableArray([]);
                self.applyKCP005ContentSearch([]);
                //                self.optionalColumnDatasource = ko.observableArray([]);

                //                self.enableSystemChange = ko.observable(false);

                self.enableSave = ko.observable(true);

                self.employeeName = ko.observable('');

                self.ccgcomponent = {

                    /** Common properties */
                    systemType: 1, // システム区分
                    showEmployeeSelection: false, // 検索タイプ
                    showQuickSearchTab: true, // クイック検索
                    showAdvancedSearchTab: true, // 詳細検索
                    showBaseDate: false, // 基準日利用
                    showClosure: false, // 就業締め日利用
                    showAllClosure: false, // 全締め表示
                    showPeriod: false, // 対象期間利用
                    periodFormatYM: true, // 対象期間精度

                    /** Required parameter */
                    baseDate: self.baseDate().toISOString(), // 基準日
                    periodStartDate: moment(self.periodStartDate()).format("YYYY-MM-DD"), // 対象期間開始日
                    periodEndDate: moment(self.periodStartDate()).format("YYYY-MM-DD"), // 対象期間終了日
                    inService: true, // 在職区分
                    leaveOfAbsence: true, // 休職区分
                    closed: true, // 休業区分
                    retirement: true, // 退職区分

                    /** Quick search tab options */
                    showAllReferableEmployee: true, // 参照可能な社員すべて
                    showOnlyMe: true, // 自分だけ
                    showSameWorkplace: true, // 同じ職場の社員
                    showSameWorkplaceAndChild: true, // 同じ職場とその配下の社員

                    /** Advanced search properties */
                    showEmployment: true, // 雇用条件
                    showWorkplace: true, // 職場条件
                    showClassification: true, // 分類条件
                    showJobTitle: true, // 職位条件
                    showWorktype: true, // 勤種条件
                    isMutipleCheck: true, // 選択モード                   

                    /** Return data */
                    returnDataFromCcg001: function(data: Ccg001ReturnedData) {
                        //                        self.selectedEmployee(data.listEmployee);
                        self.applyKCP005ContentSearch(data.listEmployee);
                    }
                }

                self.currentCodeList.subscribe(function(employeeId: any) {
                    let employee;
                    self.selectedEmployee.removeAll();
                    for (let i of employeeId) {
                        employee = self.findByIdEmployee(i);
                        self.selectedEmployee.push(employee);
                    }
                    console.log(self.selectedEmployee());
                });
            }

            public start_page(): JQueryPromise<any> {

                blockUI.invisible();
                let self = this;
                let dfd = $.Deferred();

                // Start component
                $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent);
                service.getPermission().done((result) => {
                    if (result == true) {
                        self.permission(false);
                    }
                });
                service.getAllAttendanceRecExpSet().done(function(listAttendance: Array<AttendanceRecordExportSettingDto>) {
                    if (listAttendance === undefined || listAttendance.length == 0) {
                        self.attendanceRecordList();
                        $('#print').attr("disabled", "disabled")
                        $('#exportExcel').attr("disabled", "disabled")
                    } else {
                        self.attendanceRecordList(listAttendance);
                        self.selectedCode = ko.observable(listAttendance[0].code);
                    }
                    dfd.resolve();
                })
                blockUI.clear();
                return dfd.promise();
            }

            public getCurrentDay(date: Date) {
                let month = date.getMonth() + 1;
                let year = date.getFullYear();
                return year + "/" + month;
            }

            /**
             * apply ccg001 search data to kcp005
             */
            public applyKCP005ContentSearch(dataList: Employee[]): void {
                let self = this;
                self.employeeList([]);
                let employeeSearchs: UnitModel[] = [];
                for (let employeeSearch of dataList) {
                    let employee: UnitModel = {
                        id: employeeSearch.employeeId,
                        code: employeeSearch.employeeCode,
                        name: employeeSearch.employeeName,
                        workplaceName: employeeSearch.workplaceName
                    };
                    employeeSearchs.push(employee);
                }
                self.employeeList(employeeSearchs);
            }


            /**
             * find employee code in selected
             */
            public findEmployeeCodeById(employeeId: string): string {
                let self = this;
                let employeeCode = '';
                for (let employee of self.selectedEmployee()) {
                    if (employee.employeeId === employeeId) {
                        employeeCode = employee.employeeCode;
                    }
                }
                return employeeCode;
            }

            /**
             * get all employee id by search data CCG out put 
             */
            public getAllEmployeeIdBySearch(): string[] {
                let self = this;
                let employeeIds: string[] = [];
                for (let employeeSelect of self.employeeList()) {
                    employeeIds.push(self.findEmployeeIdByCode(employeeSelect.code));
                }
                return employeeIds;
            }

            /**
             * find employee id in selected
             */
            public findEmployeeIdByCode(employeeCode: string): string {
                let self = this;
                let employeeId = '';
                for (let employee of self.selectedEmployee()) {
                    if (employee.employeeCode === employeeCode) {
                        employeeId = employee.employeeId;
                    }
                }
                return employeeId;
            }

            /**
             * update selected employee kcp005 => detail
             */
            public findByCodeEmployee(employeeCode: string): UnitModel {
                let employee: UnitModel;
                let self = this;
                for (let employeeSelect of self.employeeList()) {
                    if (employeeSelect.code === employeeCode) {
                        employee = employeeSelect;
                        break;
                    }
                }
                return employee;
            }

            /**
             * update selected employee kcp005 => detail
             */
            public findByIdEmployee(employeeId: string): UnitModel {
                let employee: UnitModel;
                let self = this;
                for (let employeeSelect of self.employeeList()) {
                    if (employeeSelect.id === employeeId) {
                        employee = employeeSelect;
                        break;
                    }
                }
                return employee;
            }

            public print() {
                // mode = 1 for export file excel
                let self = this;
                let startDate;
                let endDate;
                console.log(self.currentCodeList());
                if (self.selectedEmployee().length <= 0) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_1129" });
                    return;
                }
                self.exportDto(new ExportDto(self.selectedEmployee(), self.dateValue().startDate, self.dateValue().endDate, self.selectedCode(), 1));
                service.exportService(self.exportDto()).done(() => {

                }).fail((error) => {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_1269" });
                })
            }

            public exportExcel() {
                // mode = 2 for export file excel
                let self = this;
                if (self.selectedEmployee.length <= 0) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_1129" });
                    return;
                }

                self.exportDto(new ExportDto(self.selectedEmployee(), self.dateValue().startDate, self.dateValue().endDate, self.selectedCode(), 2));
                service.exportService(self.exportDto()).done(() => {

                }).fail((error) => {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_1269" });
                })
            }

            public openBDialog(): void {
                nts.uk.ui.windows.sub.modal("/view/kwr/002/b/index.xhtml").onClosed(function() {

                });
            }
        }

        export class AttendanceRecordExportSettingDto {
            code: string;
            name: string;

            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }

        export interface GroupOption {
            /** Common properties */
            showEmployeeSelection: boolean; // 検索タイプ
            systemType: number; // システム区分
            showQuickSearchTab: boolean; // クイック検索
            showAdvancedSearchTab: boolean; // 詳細検索
            showBaseDate: boolean; // 基準日利用
            showClosure: boolean; // 就業締め日利用
            showAllClosure: boolean; // 全締め表示
            showPeriod: boolean; // 対象期間利用
            periodFormatYM: boolean; // 対象期間精度

            /** Required parameter */
            baseDate?: string; // 基準日
            periodStartDate?: string; // 対象期間開始日
            periodEndDate?: string; // 対象期間終了日
            inService: boolean; // 在職区分
            leaveOfAbsence: boolean; // 休職区分
            closed: boolean; // 休業区分
            retirement: boolean; // 退職区分

            /** Quick search tab options */
            showAllReferableEmployee: boolean; // 参照可能な社員すべて
            showOnlyMe: boolean; // 自分だけ
            showSameWorkplace: boolean; // 同じ職場の社員
            showSameWorkplaceAndChild: boolean; // 同じ職場とその配下の社員

            /** Advanced search properties */
            showEmployment: boolean; // 雇用条件
            showWorkplace: boolean; // 職場条件
            showClassification: boolean; // 分類条件
            showJobTitle: boolean; // 職位条件
            showWorktype: boolean; // 勤種条件
            isMutipleCheck: boolean; // 選択モード
            // showDepartment: boolean; // 部門条件 not covered
            // showDelivery: boolean; not covered

            /** Data returned */
            returnDataFromCcg001: (data: Ccg001ReturnedData) => void;
        }

        export interface Ccg001ReturnedData {
            baseDate: string; // 基準日
            closureId?: number; // 締めID
            periodStart: string; // 対象期間（開始)
            periodEnd: string; // 対象期間（終了）
            listEmployee: Array<Employee>; // 検索結果
        }

        export class Employee {
            employeeId: string;
            employeeCode: string;
            employeeName: string;
            workplaceName: string;
            constructor(employeeId: string, employeeCode: string, employeeName: string, workplaceName: string) {
                this.employeeId = employeeId;
                this.employeeCode = employeeCode;
                this.employeeName = employeeName;
                this.workplaceName = workplaceName;
            }
        }

        export class ExportDto {
            employeeList: Array<Employee>;
            startDate: string;
            endDate: string;
            layout: string;
            mode: number;

            constructor(employeeList: Array<Employee>, startDate: string, endDate: string, layout: string, mode: number) {
                this.employeeList = employeeList;
                this.startDate = startDate;
                this.endDate = endDate;
                this.layout = layout;
                this.mode = mode;
            }
        }
    }
}