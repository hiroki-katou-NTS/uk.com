module nts.uk.at.view.kdm001.b.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import modal = nts.uk.ui.windows.sub.modal;
    export class ScreenModel {

        periodOptionItem: KnockoutObservableArray<ItemModel>;
        selectedPeriodItem: KnockoutObservable<number>;
        dateValue: KnockoutObservable<any>;
        startDateString: KnockoutObservable<string>;
        endDateString: KnockoutObservable<string>;
        dispTotalRemainHours: KnockoutObservable<string> = ko.observable(null);
        dispTotalExpiredHours: KnockoutObservable<string> = ko.observable(null);
        closureEmploy: any;
        selectedEmployee: any;
        listLeaveData: any;
        listCompensatoryData: any;
        listLeaveComDayOffManagement: any;
        listEmployee: Array<EmployeeInfo>;
        leaveSettingExpiredDate: string;
        compenSettingEmpExpiredDate: string
        //_____CCG001________
        ccgcomponent: GroupOption;
        listEmployeeKCP009: KnockoutObservableArray<EmployeeSearchDto>;
        showinfoSelectedEmployee: KnockoutObservable<boolean>;
        baseDate: KnockoutObservable<Date> = ko.observable(new Date());
        //___________KCP009______________
        employeeInputList: KnockoutObservableArray<EmployeeKcp009> = ko.observableArray([]);
        systemReference: KnockoutObservable<number> = ko.observable(SystemType.EMPLOYMENT);
        isDisplayOrganizationName: KnockoutObservable<boolean> = ko.observable(false);
        targetBtnText: string = getText("KCP009_3");
        listComponentOption: ComponentOption;
        selectedItem: KnockoutObservable<string> = ko.observable(null);
        isOnStartUp: boolean = true;
        tabindex: number = -1;
        substituteData: any
        igGridOption: any = null;
        subData: any = null;
        constructor() {
            var self = this;
            self.periodOptionItem = ko.observableArray([
                new ItemModel(0, getText("KDM001_4")),
                new ItemModel(1, getText("KDM001_5"))
            ]);
            self.selectedPeriodItem = ko.observable(0);
            self.startDateString = ko.observable("");
            self.endDateString = ko.observable("");
            self.dateValue = ko.observable({});

            self.startDateString.subscribe(function(value) {
                self.dateValue().startDate = value;
                self.dateValue.valueHasMutated();
            });

            self.endDateString.subscribe(function(value) {
                self.dateValue().endDate = value;
                self.dateValue.valueHasMutated();
            });
            self.updateSubstituteDataList();
            //_____CCG001________
            self.listEmployeeKCP009 = ko.observableArray([]);
            self.showinfoSelectedEmployee = ko.observable(false);
            self.ccgcomponent = {
                /** Common properties */
                systemType: 1,
                showEmployeeSelection: false,
                showQuickSearchTab: true,
                showAdvancedSearchTab: true,
                showBaseDate: false,
                showClosure: false,
                showAllClosure: false,
                showPeriod: false,
                periodFormatYM: false,

                /** Required parameter */
                baseDate: moment().toISOString(),
                periodStartDate: moment().toISOString(),
                periodEndDate: moment().toISOString(),
                inService: true,
                leaveOfAbsence: true,
                closed: true,
                retirement: true,

                /** Quick search tab options */
                showAllReferableEmployee: true,
                showOnlyMe: true,
                showSameWorkplace: true,
                showSameWorkplaceAndChild: true,

                /** Advanced search properties */
                showEmployment: true,
                showWorkplace: true,
                showClassification: true,
                showJobTitle: true,
                showWorktype: true,
                isMutipleCheck: true,

                /**
                * Self-defined function: Return data from CCG001
                * @param: data: the data return from CCG001
                */
                returnDataFromCcg001: function(data: Ccg001ReturnedData) {
                    self.showinfoSelectedEmployee(true);
                    self.listEmployeeKCP009(data.listEmployee);
                    self.convertEmployeeCcg01ToKcp009(data.listEmployee);
                }
            }
            $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent);
            self.selectedItem.subscribe(x => {
                self.selectedEmployee = _.find(self.listEmployee, item => { return item.employeeId === x; });
                if (!self.isOnStartUp) {
                    let searchCondition = { employeeId: x, stateDate: null, endDate: null };
                    service.getExtraHolidayData(searchCondition).done(function(result) {
                        self.closureEmploy = result.closureEmploy;
                        self.listLeaveComDayOffManagement = result.listLeaveComDayOffManagement;
                        self.listCompensatoryData = result.listCompensatoryData;
                        self.listLeaveData = result.listLeaveData;
                    }).fail(function(result) {
                        dialog.alertError(result.errorMessage);
                    });
                }
                self.isOnStartUp = false;
                //TODO
                // CALL SERVICE
                // RELOAD SUBSTITUTE DATA TABLE
            });
        }
        openNewSubstituteData() {
            var self = this;
            setShared('KDM001_I_PARAMS', { selectedEmployee: self.selectedEmployee, closure: self.closureEmploy });
            modal("/view/kdm/001/i/index.xhtml").onClosed(function() {
                //TODO
                $('#substituteDataGrid').focus();
            });
        }
        filterByPeriod() {
            var self = this;
        }
        updateSubstituteDataList() {
            var self = this;
            let substituteDataArray: Array<SubstitutedData> = [];
            for (var i = 0; i < 500; i++) {
                var date1 = i + '/1/2018';
                var date2 = i + '/1/2018';
                var hours = Math.floor(Math.random() * 8) + 1 + "";
                var isLinked = i % 4 == 0 ? 1 : 0 + "";
                substituteDataArray.push(new SubstitutedData(i + "", i % 2 == 0 ? date1 : null, i % 2 == 0 ? hours : null, i % 2 == 0 ? "基" : "", i % 2 == 1 ? date2 : null, i % 2 == 1 ? hours : null, i % 2 == 1 ? "基" : "", "0.5", "0.5", 0));
            }
            self.subData = substituteDataArray;
            self.showSubstiteDataGrid();
        }

        
        convertToDisplayList(){
            var self = this;
            let substituteDataArray: Array<SubstitutedData> = [];
            _.forEach(self.listLeaveData, function(data) {
                substituteDataArray.push(new SubstitutedData(data.id, data.dayOffDate, data.occurredDays, "基", null, null, null, "0.5", "0.5", 0));
            });
        }
        startPage(): JQueryPromise<any> {
            let self = this;
            var dfd = $.Deferred();
            let searchCondition = { employeeId: null, stateDate: null, endDate: null };
            service.getSubsitutionData(searchCondition).done(function(result) {
                let wkHistory = result.wkHistory;
                let employeeInfo = result.extraHolidayManagementDataDto.sempHistoryImport;
                let extraHolidayData = result.extraHolidayManagementDataDto;
                self.closureEmploy = result.extraHolidayManagementDataDto.closureEmploy;
                self.selectedEmployee = new EmployeeKcp009(employeeInfo.sid,
                    employeeInfo.employeeCode, employeeInfo.employeeName, wkHistory.workplaceId, wkHistory.workPlaceCode);
                self.employeeInputList.push(self.selectedEmployee);
                self.listLeaveComDayOffManagement = extraHolidayData.listLeaveComDayOffManagement;
                self.listCompensatoryData = extraHolidayData.listCompensatoryData;
                self.listLeaveData = extraHolidayData.listLeaveData;
                self.leaveSettingExpiredDate = result.leaveSettingExpiredDate;
                self.compenSettingEmpExpiredDate = result.compenSettingEmpExpiredDate;
                self.initKCP009();
            }).fail(function(result) {
                dialog.alertError(result.errorMessage);
            });

            dfd.resolve();
            return dfd.promise();
        }
        initKCP009() {
            let self = this;
            //_______KCP009_______
            // Initial listComponentOption
            self.listComponentOption = {
                systemReference: self.systemReference(),
                isDisplayOrganizationName: self.isDisplayOrganizationName(),
                employeeInputList: self.employeeInputList,
                targetBtnText: self.targetBtnText,
                selectedItem: self.selectedItem,
                tabIndex: self.tabindex
            };
            $('#emp-component').ntsLoadListComponent(self.listComponentOption);
        }

        convertEmployeeCcg01ToKcp009(dataList: EmployeeSearchDto[]): void {
            let self = this;
            self.employeeInputList([]);
            self.listEmployee = [];
            _.each(dataList, function(item) {
                self.listEmployee.push(new EmployeeInfo(item.employeeId, item.employeeCode, item.employeeName, item.workplaceId, item.workplaceCode));
                self.employeeInputList.push(new EmployeeKcp009(item.employeeId, item.employeeCode, item.employeeName, item.workplaceName, ""));
            });
            $('#emp-component').ntsLoadListComponent(self.listComponentOption);
            if (dataList.length == 0) {
                self.selectedItem('');
            } else {
                let item = self.findIdSelected(dataList, self.selectedItem());
                let sortByEmployeeCode = _.orderBy(dataList, ["employeeCode"], ["asc"]);
                if (item == undefined) self.selectedItem(sortByEmployeeCode[0].employeeId);
            }
        }

        findIdSelected(dataList: Array<any>, selectedItem: string): any {
            return _.find(dataList, function(obj) {
                return obj.employeeId == selectedItem;
            })
        }
        showSubstiteDataGrid() {
            var self = this;
            $("#substituteDataGrid").ntsGrid({
                height: '405px',
                dataSource: this.subData,
                primaryKey: 'id',
                virtualization: true,
                hidePrimaryKey: true,
                rowVirtualization: true,
                virtualizationMode: 'continuous',
                //                            enter: 'right',
                columns: [
                    { headerText: 'ID', key: 'id', dataType: 'number', width: '50px', hidden: true },
                    { headerText: getText('KDM001_33'), template: '<div style="float:right"> ${substituedWorkingDate} </div>', key: 'substituedWorkingDate', dataType: 'string', width: '120px' },
                    { headerText: getText('KDM001_9'), template: '<div style="float:right"> ${substituedWorkingHours} </div>', key: 'substituedWorkingHours', dataType: 'string', width: '100px' },
                    { headerText: getText('KDM001_124'), key: 'substituedWorkingPeg', dataType: 'string', width: '100px' },
                    { headerText: getText('KDM001_34'), template: '<div style="float:right"> ${substituedHolidayDate} </div>', key: 'substituedHolidayDate', dataType: 'string', width: '120px' },
                    { headerText: getText('KDM001_11'), template: '<div style="float:right"> ${substituteHolidayHours} </div>', key: 'substituteHolidayHours', dataType: 'string', width: '100px' },
                    { headerText: getText('KDM001_124'), key: 'substituedHolidayPeg', dataType: 'string', width: '100px' },
                    { headerText: getText('KDM001_37'), template: '<div style="float:right"> ${remainHolidayHours} </div>', key: 'remainHolidayHours', dataType: 'string', width: '100px' },
                    { headerText: getText('KDM001_20'), template: '<div style="float:right"> ${expiredHolidayHours} </div>', key: 'expiredHolidayHours', dataType: 'string', width: '100px' },
                    { headerText: '', key: 'pegSetting', dataType: 'string', width: '80px', unbound: true, ntsControl: 'PegSettingButton' },
                    { headerText: '', key: 'correction', dataType: 'string', width: '80px', unbound: true, ntsControl: 'CorrectionButton' }
                ],
                features: [{ name: 'Resizing' },
                    {
                        name: 'Paging',
                        type: "local",
                        pageSize: 10
                    }
                ],
                ntsControls: [
                    { name: 'PegSettingButton', text: getText('KDM001_22'), click: function(value) { self.pegSetting(value); }, controlType: 'Button' },
                    { name: 'CorrectionButton', text: getText('KDM001_23'), click: function(value) { self.doCorrection(value); }, controlType: 'DeleteButton', enable: true }]
            });
        }
        pegSetting(value) {
            var self = this;
            setShared('PEGSETTING_PARAMS', { row: value, selectedEmployee: self.selectedEmployee, closure: self.closureEmploy });
            if (value.substituedWorkingDate.length > 0) {
                modal("/view/kdm/001/j/index.xhtml").onClosed(function() {
                    //location.reload();
                });
            } else {
                modal("/view/kdm/001/k/index.xhtml").onClosed(function() {
                    //location.reload();
                });
            }
            $("#substituteDataGrid").ntsGrid("updateRow", value.id, { substituedWorkingDate: 'TEST' });
            $("#substituteDataGrid").ntsGrid("enableNtsControlAt", value.id, 'correction', 'Button');
        }
        doCorrection(value) {
            var self = this;
            setShared('CORRECTION_PARAMS', { row: value, selectedEmployee: self.selectedEmployee, closure: self.closureEmploy });
            if (value.substituedWorkingDate.length > 0) {
                modal("/view/kdm/001/l/index.xhtml").onClosed(function() {
                    //location.reload();
                });
            } else {
                modal("/view/kdm/001/m/index.xhtml").onClosed(function() {
                    //location.reload();
                });
            }
        }
    }
    export class SubstitutedData {
        id: string;
        substituedWorkingDate: string;
        substituedWorkingHours: string;
        substituedWorkingPeg: string;
        substituedHolidayDate: string;
        substituteHolidayHours: string;
        substituedHolidayPeg: string;
        remainHolidayHours: string;
        expiredHolidayHours: string;
        isLinked: number;
        constructor(id: string, substituedWorkingDate: string, substituedWorkingHours: string, substituedWorkingPeg: string,
            substituedHolidayDate: string, substituteHolidayHours: string, substituedHolidayPeg: string, remainHolidayHours: string,
            expiredHolidayHours: string, isLinked: number) {
            this.id = id;
            this.substituedWorkingDate = substituedWorkingDate;
            this.substituedWorkingHours = substituedWorkingHours;
            this.substituedWorkingPeg = substituedWorkingPeg;
            this.substituedHolidayDate = substituedHolidayDate;
            this.substituteHolidayHours = substituteHolidayHours;
            this.substituedHolidayPeg = substituedHolidayPeg;
            this.remainHolidayHours = remainHolidayHours;
            this.expiredHolidayHours = expiredHolidayHours;
            this.isLinked = isLinked;
        }
    }
    
    export class EmployeeInfo {
        employeeId: string;
        employeeCode: string;
        employeeName: string;
        workplaceId: string;
        workplaceCode: string;
        constructor(employeeId: string, employeeCode: string, employeeName: string, workplaceId: string, workplaceCode: string) {
            this.employeeId = employeeId;
            this.employeeCode = employeeCode;
            this.employeeName = employeeName;
            this.workplaceId = workplaceId;
            this.workplaceCode = workplaceCode;
        }
    }
    export class ItemModel {
        value: number;
        name: string;
        constructor(value: number, name: string) {
            this.value = value;
            this.name = name;
        }
    }

    //__________KCP009_________
    export interface ComponentOption {
        systemReference: SystemType;
        isDisplayOrganizationName: boolean;
        employeeInputList: KnockoutObservableArray<EmployeeKcp009>;
        targetBtnText: string;
        selectedItem: KnockoutObservable<string>;
        tabIndex: number;
    }
    export class EmployeeKcp009 {
        id: string;
        code: string;
        businessName: string;
        workplaceName: string;
        depName: string;
        constructor(id: string, code: string, businessName: string, workplaceName: string, depName: string) {
            this.id = id;
            this.code = code;
            this.businessName = businessName;
            this.workplaceName = workplaceName;
            this.depName = depName;
        }
    }

    export class SystemType {
        static EMPLOYMENT = 1;
        static SALARY = 2;
        static PERSONNEL = 3;
        static ACCOUNTING = 4;
        static OH = 6;
    }

    //Interfaces
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
        isInDialog?: boolean;

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

    export interface EmployeeSearchDto {
        employeeId: string;
        employeeCode: string;
        employeeName: string;
        workplaceId: string;
        workplaceCode: string;
        workplaceName: string;
    }

    export interface Ccg001ReturnedData {
        baseDate: string; // 基準日
        closureId?: number; // 締めID
        periodStart: string; // 対象期間（開始)
        periodEnd: string; // 対象期間（終了）
        listEmployee: Array<EmployeeSearchDto>; // 検索結果
    }
}

