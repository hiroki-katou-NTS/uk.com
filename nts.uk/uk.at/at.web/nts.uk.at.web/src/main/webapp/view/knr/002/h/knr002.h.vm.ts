module knr002.h {
    import blockUI = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import getMsg = nts.uk.resource.getMessage;
    import isNullOrUndefined = nts.uk.util.isNullOrUndefined;

    export module viewmodel {
        export class ScreenModel {
            ccgcomponent: GroupOption;       
            baseDate: KnockoutObservable<Date>;
            listEmployee: KnockoutObservableArray<ExportEmployeeSearchDto >;
            currentCodeList: KnockoutObservableArray<string>;
            attendanceRecordList: KnockoutObservableArray<AttendanceRecordExportSettingDto>;
            selectedCode: KnockoutObservable<string>;
            selectedEmployee: KnockoutObservableArray<any>;
            lstPersonComponentOption: any;
            selectedEmployeeCode: KnockoutObservableArray<string>;
            alreadySettingPersonal: KnockoutObservableArray<any>;
            employeeSearchedList: KnockoutObservableArray<ExportEmployeeSearchDto >;
            columns: KnockoutObservableArray<any>;

            constructor() {
                let self = this;
                self.listEmployee = ko.observableArray<ExportEmployeeSearchDto >([]);


                self.attendanceRecordList = ko.observableArray([]);

                self.selectedCode = ko.observable(null);
                self.currentCodeList = ko.observableArray([]);
                self.selectedEmployee = ko.observableArray([]);

                self.baseDate = ko.observable(new Date());
                
                self.employeeSearchedList = ko.observableArray<ExportEmployeeSearchDto >([]);
                

                self.searchByEmploymentType([]);

                self.selectedEmployeeCode = ko.observableArray([]);
                self.alreadySettingPersonal = ko.observableArray([]);

                self.columns = ko.observableArray([
                    { headerText: '', key: 'employeeId', width: 0, hidden: true },
                    { headerText: getText("KNR002_182"), key: 'employeeCode', width: 60 },
                    { headerText: getText("KNR002_183"), key: 'employeeName', width: 150 },
                    { headerText: getText("KNR002_184"), key: 'affiliationName', width: 150 }
                ]);

                self.ccgcomponent = {
                    /** Common properties */
                    systemType: 1, // システム区分
                    showEmployeeSelection: false, // 検索タイプ
                    showQuickSearchTab: true, // クイック検索
                    showAdvancedSearchTab: true, // 詳細検索
                    showBaseDate: false, // 基準日利用
                    showClosure: true, // 就業締め日利用
                    showAllClosure: true, // 全締め表示
                    showPeriod: true, // 対象期間利用
                    periodFormatYM: true, // 対象期間精度

                    /** Required parameter */
                    baseDate: self.baseDate().toISOString(), // 基準日
                    // periodStartDate: self.dateValue().startDate, // 対象期間開始日
                    // periodEndDate: self.dateValue().endDate, // 対象期間終了日
                    // dateRangePickerValue: self.dateValue,
                    inService: true, // 在職区分
                    leaveOfAbsence: true, // 休職区分
                    closed: true, // 休業区分
                    retirement: false, // 退職区分

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
                    showWorktype: false, // 勤種条件
                    isMutipleCheck: true, // 選択モード                   

                    /** Return data */
                    returnDataFromCcg001: function(data: Ccg001ReturnedData) {
                        self.searchByEmploymentType(data.listEmployee);
                        self.listEmployee(data.listEmployee);
                        console.log(data);
                        console.log(self.searchByEmploymentType(data.listEmployee));
                    }
                }
            }

            public startPage(): JQueryPromise<any> {

                blockUI.invisible();
                let self = this;
                let dfd = $.Deferred();

                // Start component
                $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent).done(() => {
                    self.employeeSearchedList = ko.observableArray<ExportEmployeeSearchDto >([]);
                    self.searchByEmploymentType([]);
                    // Load employee list component
                });

                service.getAllAttendanceRecExpSet().done(function(listAttendance) {
                    if (listAttendance === undefined || listAttendance.length == 0) {
                        self.attendanceRecordList();
                      
                    } else {
                     
                        var sortArray = _.orderBy(listAttendance, [e => Number(e.code)], ['asc']);
                        _.map(sortArray, (item) => {
                            item.code = _.padStart(item.code, 2, '0');
                        });
                        self.attendanceRecordList(sortArray);
                    }

                    dfd.resolve();
                });
                $('#ccgcomponent').focus();
                blockUI.clear();
                return dfd.promise();
            }



            /**
             * apply ccg001
             */
            private searchByEmploymentType(dataList: ExportEmployeeSearchDto []): void {
                let self = this;
                self.employeeSearchedList([]);
                let employeeSearchs: ExportEmployeeSearchDto [] = [];
                for (let employeeSearch of dataList) {
                    let employee: ExportEmployeeSearchDto  = {
                        employeeId: employeeSearch.employeeId,
                        employeeCode: employeeSearch.employeeCode,
                        employeeName: employeeSearch.employeeName,
                        affiliationName: employeeSearch.affiliationName
                    };
                    employeeSearchs.push(employee);
                }
                self.employeeSearchedList(employeeSearchs);
            }
            /**
             * cancel_Dialog
             */
            private cancel_Dialog(): any {
                let self = this;
                nts.uk.ui.windows.close();
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

            /** Data returned */
            returnDataFromCcg001: (data: Ccg001ReturnedData) => void;
        }

        export interface Ccg001ReturnedData {
            baseDate: string; // 基準日
            closureId?: number; // 締めID
            periodStart: string; // 対象期間（開始)
            periodEnd: string; // 対象期間（終了）
            listEmployee: Array<ExportEmployeeSearchDto >; // 検索結果
        }

        export class ExportEmployeeSearchDto  {
            employeeId: string;
            employeeCode: string;
            employeeName: string;
            affiliationName: string;

            constructor(employeeId: string, employeeCode: string, employeeName: string, affiliationName: string) {
                this.employeeId = employeeId;
                this.employeeCode = employeeCode;
                this.employeeName = employeeName;
                this.affiliationName = affiliationName;
            }
        }
    }
}