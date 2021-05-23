module knr002.h {
    import blockUI = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import dialog = nts.uk.ui.dialog;


    export module viewmodel {
        export class ScreenModel {
            empInfoTerCode: string;
            ccgcomponent: GroupOption;       
            baseDate: KnockoutObservable<Date>;
            listEmployee: KnockoutObservableArray<ExportEmployeeSearchDto >;
            employeesList: KnockoutObservableArray<EmployeesDto>;
            employeesListVal: KnockoutObservableArray<ExportEmployeeSearchDto >;
            selectedCode: KnockoutObservable<string>;
            selectedEmployee: KnockoutObservableArray<any>;
            lstPersonComponentOption: any;
            selectedEmployeeCode: KnockoutObservableArray<string>;
            alreadySettingPersonal: KnockoutObservableArray<any>;
            employeeSearchedList: KnockoutObservableArray<ExportEmployeeSearchDto >;
            columns: KnockoutObservableArray<any>;
            isCancel: boolean;
            

            constructor() {
                let self = this;
                self.empInfoTerCode = '';
                self.isCancel = true;
                self.listEmployee = ko.observableArray<ExportEmployeeSearchDto >([]);


                self.employeesList = ko.observableArray([]);
                self.employeesListVal = ko.observableArray([]);

                self.selectedCode = ko.observable(null);
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
                    showBaseDate: true, // 基準日利用
                    showClosure: false, // 就業締め日利用
                    showAllClosure: true, // 全締め表示
                    showPeriod: false, // 対象期間利用
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
                    isInDialog: true,            

                    /** Return data */
                    returnDataFromCcg001: function(data: Ccg001ReturnedData) {
                        self.searchByEmploymentType(data.listEmployee);
                        self.listEmployee(data.listEmployee);
                        $('#swap-list-grid1 > tbody > tr:nth-child(1)').focus();
                    }
                };
            }

            public startPage(): JQueryPromise<any> {
                blockUI.invisible();
                let self = this;
                let dfd = $.Deferred();
                self.empInfoTerCode = getShared('KNR002H_empInfoTerCode');
                // Start component
                $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent).done(() => {
                    self.employeeSearchedList = ko.observableArray<ExportEmployeeSearchDto >([]);
                    self.searchByEmploymentType([]);
                    // Load employee list component
                }).then(() => {
                    $('#ccg001-btn-search-drawer').focus();
                });

                service.getEmployees(self.empInfoTerCode).done(data => {
                    if (data === undefined || data.length == 0) {
                        self.employeesList([]);
                        self.employeesListVal([]);
                    } else {                  
                       // var sortArray = _.orderBy(data, [e.employeeCode], ['asc']);
                       let keyMap: any = {};
                       _.forEach(data, e => {
                           keyMap[e.employeeId] = e;
                       });

                        self.employeesList(data);
                        let employeesListTemp = [];
                        for(let item of data){
                            let workplaceName = item.workplaceName? item.workplaceName: "";
                            let employee = new ExportEmployeeSearchDto(item.employeeId, item.employeeCode, item.businessName, workplaceName);
                            employeesListTemp.push(employee);
                        }
                        self.employeesListVal(_.sortBy(employeesListTemp, e => e.employeeCode));
                        //  self.employeesListVal(employeesListTemp);
                    }
                    dfd.resolve();
                });
                // setTimeout(() => {
                //     $('#ccg001-btn-search-drawer').focus();
                // }, 0);
                blockUI.clear();
                return dfd.promise();
            }

            /**
             * H6_1
             * 決定ボタン
             */
            private enter(): any{
                let self = this;
                setShared('KNR002H_isCancel', false); 
                if(!self.employeesListVal() || self.employeesListVal().length <= 0){
                    dialog.error({ messageId: "Msg_2023" }).then(() => {
                        // do something
                        blockUI.clear();
                    });
                } else {
                	   let registDto: any = {};
		               registDto.terminalCode = self.empInfoTerCode;
		               registDto.selectedEmpIds = self.employeesListVal().map(e => e.employeeId);
		               setShared('KNR002H_selectedList', self.employeesListVal().map(e => e.employeeId));
		               blockUI.invisible();
			           service.registSpecifiedEmps(registDto).done(() => {
                           dialog.info({ messageId: "Msg_15" }).then(() => {
                        	// do something
                           });
                       }).fail(error => {
                                dialog.error({messageId: error.messageId, messageParams: error.parameterIds});
                       }).always(() => {
                           blockUI.clear();
                       }); 
                    nts.uk.ui.windows.close();
                } 
            }

            /**
             * cancel_Dialog
             */
            private cancel_Dialog(): any {
                setShared('KNR002H_isCancel', true);
                nts.uk.ui.windows.close();
            }

            /**
             * apply ccg001 search data to load
             */
            private searchByEmploymentType(dataList: ExportEmployeeSearchDto []): void {
                let self = this;
                self.employeeSearchedList([]);
                let employeeSearchs: ExportEmployeeSearchDto [] = [];
                for (let employeeSearch of dataList) {
                    let affiliationNameSearch = employeeSearch.affiliationName ? employeeSearch.affiliationName : "";
                    let employee: ExportEmployeeSearchDto  = {
                        employeeId: employeeSearch.employeeId,
                        employeeCode: employeeSearch.employeeCode,
                        employeeName: employeeSearch.employeeName,
                        affiliationName: affiliationNameSearch
                    };
                    employeeSearchs.push(employee);
                }
                self.employeeSearchedList(employeeSearchs);
                $('#swap-list-grid1 > tbody > tr:nth-child(1)').focus();
            }
        }

        export class ListType {
            static EMPLOYMENT = 1;
            static Classification = 2;
            static JOB_TITLE = 3;
            static EMPLOYEE = 4;
        }

        export class SelectType {
            static SELECT_BY_SELECTED_CODE = 1;
            static SELECT_ALL = 2;
            static SELECT_FIRST_ITEM = 3;
            static NO_SELECT = 4;
        }

        export class EmployeesDto {
            employeeId: string;
            employeeCode: string;
            businessName: string;
            businessNameKana: string;
            workplaceName: string;

            constructor(employeeId: string, employeeCode: string, businessName: string, businessNameKana: string, workplaceName: string) {
                this.employeeId = employeeId;
                this.employeeCode = employeeCode;
                this.businessName = businessName;
                this.businessNameKana = businessNameKana;
                this.workplaceName = workplaceName;
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
            isInDialog: boolean;    // 

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