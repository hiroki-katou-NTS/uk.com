module nts.uk.at.view.kdm001.b.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import modal = nts.uk.ui.windows.sub.modal;
    import getDecimal = nts.uk.ntsNumber.getDecimal;
    export class ScreenModel {

        periodOptionItem: KnockoutObservableArray<ItemModel>;
        selectedPeriodItem: KnockoutObservable<number>;
        dateValue: KnockoutObservable<any>;
        dispTotalRemainHours: KnockoutObservable<string> = ko.observable(null);
        dispTotalExpiredDate: KnockoutObservable<string> = ko.observable(null);
        closureEmploy: any;
        selectedEmployee: any;
        listLeaveData: Array<any>;
        listCompensatoryData: Array<any>;
        listLeaveComDayOffManagement: Array<any>;
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
        subData: Array<any> = null;
        constructor() {
            var self = this;
            self.periodOptionItem = ko.observableArray([
                new ItemModel(0, getText("KDM001_4")),
                new ItemModel(1, getText("KDM001_5"))
            ]);
            self.selectedPeriodItem = ko.observable(0);
            self.dateValue = ko.observable({});
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
                if (!self.isOnStartUp) {
                    self.selectedEmployee = _.find(self.listEmployee, item => { return item.employeeId === x; });
                    let searchCondition = { employeeId: x, stateDate: null, endDate: null };
                    self.getSubstituteDataList(searchCondition);
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
            let startDate = moment.utc(self.dateValue().startDate, 'YYYY/MM/DD').toISOString();
            let endDate = moment.utc(self.dateValue().endDate, 'YYYY/MM/DD').toISOString();
            let searchCondition = null;
            if (self.selectedPeriodItem() == 1) {
                searchCondition = {searchMode: 1, employeeId: self.selectedEmployee.employeeId, startDate: startDate, endDate: endDate };
            } else {
                searchCondition = {searchMode: 0, employeeId: self.selectedEmployee.employeeId, startDate: null, endDate: null };
            }
            self.getSubstituteDataList(searchCondition);
        }
        getSubstituteDataList(searchCondition: any) {
            var self = this;
            service.getExtraHolidayData(searchCondition).done(function(result) {
                self.closureEmploy = result.closureEmploy;
                self.listLeaveComDayOffManagement = result.listLeaveComDayOffManagement;
                self.listCompensatoryData = result.listCompensatoryData;
                self.listLeaveData = result.listLeaveData;
                self.convertToDisplayList();
                self.updateSubstituteDataList();
                if (self.listLeaveData == null && self.listCompensatoryData == null) {
                    dialog.alertError({ messageId: "Msg_726" });
                }
            }).fail(function(result) {
                dialog.alertError(result.errorMessage);
            });
        }
        convertToDisplayList() {
            var self = this;
            let isLinked = 0, substituteDataArray: Array<SubstitutedDataInfo> = [], today = new Date(), remain = null, expired = null, totalRemain = 0;
            _.forEach(self.listLeaveData, data => {
                remain = null
                expired = null;
                isLinked = (_.find(self.listLeaveComDayOffManagement, x => { return x.leaveID === data.id; })) != null ? 1 : 0;
                if (new Date(data.dayOffDate) >= today) {
                    totalRemain += data.unUsedDays;
                    remain = data.unUsedDays.toFixed(1) + getText('KDM001_27');
                } else {
                    expired = data.unUsedDays.toFixed(1) + getText('KDM001_27');
                }
                substituteDataArray.push(new SubstitutedDataInfo(data.id, 0, data.dayOffDate, data.occurredDays.toFixed(1) + getText('KDM001_27'), remain, expired, isLinked));
            });
            _.forEach(self.listCompensatoryData, data => {
                remain = null
                expired = null;
                isLinked = (_.find(self.listLeaveComDayOffManagement, x => { return x.comDayOffID === data.comDayOffID; })) != null ? 1 : 0;
                if (new Date(data.dayOffDate) >= today) {
                    totalRemain += data.remainDays;
                    remain = data.remainDays.toFixed(1) + getText('KDM001_27');
                } else {
                    expired = data.remainDays.toFixed(1) + getText('KDM001_27');
                }
                substituteDataArray.push(new SubstitutedDataInfo(data.comDayOffID, 1, data.dayOffDate, data.requireDays.toFixed(1) + getText('KDM001_27'), remain, expired, isLinked));
            });
            substituteDataArray.sort(function(x, y) {
                return (new Date(x.workingDate) <= new Date(y.workingDate)) ? -1 : 1;
            });
            self.subData = [];
            _.forEach(substituteDataArray, data => {
                if (data.dataType == 0) {
                    self.subData.push(new SubstitutedData(data.id, data.workingDate, data.workingHours, data.isLinked == 1 ? getText('KDM001_124') : "", null, null, null, data.remainHours, data.expiredHours, data.isLinked));
                } else {
                    self.subData.push(new SubstitutedData(data.id, null, null, null, data.workingDate, data.workingHours, data.isLinked == 1 ? getText('KDM001_124') : "", data.remainHours, data.expiredHours, data.isLinked));
                }
            });
            self.dispTotalRemainHours(totalRemain + getText('KDM001_124'));
        }
        initSubstituteDataList() {
            let self = this;
            if (self.listLeaveData == null && self.listCompensatoryData == null) {
                dialog.alertError({ messageId: "Msg_726" });
            } else {
                self.convertToDisplayList();
            }
            self.showSubstiteDataGrid();
            self.disableLinkedData();
        }
        updateSubstituteDataList() {
            var self = this;
            $("#substituteDataGrid").igGrid("dataSourceObject", self.subData).igGrid("dataBind");
            self.disableLinkedData();
        }
        disableLinkedData(){
            var self = this;
            if (self.subData){
                for(let data of self.subData){
                    if (data.isLinked == 1){
                        $("#substituteDataGrid").ntsGrid("disableNtsControlAt", data.id, "correction", 'Button');
                    }
                }
            }
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
                self.listEmployee = [];
                self.selectedEmployee = new EmployeeInfo(wkHistory.employeeId, 'code', 'name', wkHistory.workplaceId, wkHistory.workplaceCode, wkHistory.workplaceName);
                self.listEmployee.push(self.selectedEmployee);
                self.employeeInputList.push(new EmployeeKcp009(employeeInfo.sid,
                    employeeInfo.employeeCode, employeeInfo.employeeName, wkHistory.workplaceName, wkHistory.wkpDisplayName));
                self.listCompensatoryData = extraHolidayData.listCompensatoryData;
                self.listLeaveData = extraHolidayData.listLeaveData;
                self.listLeaveComDayOffManagement = extraHolidayData.listLeaveComDayOffManagement;
                self.initSubstituteDataList();
                self.dispTotalExpiredDate = result.leaveSettingExpiredDate;
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
                self.listEmployee.push(new EmployeeInfo(item.employeeId, item.employeeCode, item.employeeName, item.workplaceId, item.workplaceCode, item.workplaceName));
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
                dataSource: self.subData,
                primaryKey: 'id',
                virtualization: true,
                hidePrimaryKey: true,
                rowVirtualization: true,
                virtualizationMode: 'continuous',
                columns: [
                    { headerText: 'ID', key: 'id', dataType: 'string', width: '50px', hidden: true },
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
                        pageSize: 14
                    }
                ],
                ntsControls: [
                    { name: 'PegSettingButton', text: getText('KDM001_22'), click: function(value) { self.pegSetting(value); }, controlType: 'Button' },
                    { name: 'CorrectionButton', text: getText('KDM001_23'), click: function(value) { self.doCorrection(value); }, controlType: 'Button', enable: true }]
            });
        }
        pegSetting(value) {
            var self = this;
            if (value.substituedWorkingDate.length > 0) {
                let rowDataInfo = _.find(self.listLeaveData, x => {
                    return x.id === value.id;
                });
                setShared('KDM001_J_PARAMS', { row: rowDataInfo, selectedEmployee: self.selectedEmployee, closure: self.closureEmploy });
                modal("/view/kdm/001/j/index.xhtml").onClosed(function() {

                    //location.reload();
                });
            } else {
                let rowDataInfo = _.find(self.listCompensatoryData, x => {
                    return x.comDayOffID === value.id;
                });
                setShared('KDM001_K_PARAMS', { row: rowDataInfo, selectedEmployee: self.selectedEmployee, closure: self.closureEmploy });
                modal("/view/kdm/001/k/index.xhtml").onClosed(function() {
                    //location.reload();
                });
            }
        }
        doCorrection(value) {
            var self = this;
            if (value.substituedWorkingDate.length > 0) {
                let rowDataInfo = _.find(self.listLeaveData, x => {
                    return x.id === value.id;
                });
                setShared('KDM001_L_PARAMS', { row: rowDataInfo, selectedEmployee: self.selectedEmployee, closure: self.closureEmploy });
                modal("/view/kdm/001/l/index.xhtml").onClosed(function() {
                    //location.reload();
                });
            } else {
                let rowDataInfo = _.find(self.listCompensatoryData, x => {
                    return x.comDayOffID === value.id;
                });
                setShared('KDM001_M_PARAMS', { row: rowDataInfo, selectedEmployee: self.selectedEmployee, closure: self.closureEmploy });
                modal("/view/kdm/001/m/index.xhtml").onClosed(function() {

                    //location.reload();
                });
            }
        }
    }

    export class SubstitutedDataInfo {
        id: string;
        dataType: number;
        workingDate: string;
        workingHours: string;
        remainHours: string;
        expiredHours: string;
        isLinked: number;
        constructor(id: string, dataType: number, workingDate: string, workingHours: string, remainHours: string, expiredHours: string, isLinked: number) {
            this.id = id;
            this.dataType = dataType;
            this.workingDate = workingDate;
            this.workingHours = workingHours;
            this.remainHours = remainHours;
            this.expiredHours = expiredHours;
            this.isLinked = isLinked;
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
        workplaceName: string;
        constructor(employeeId: string, employeeCode: string, employeeName: string, workplaceId: string, workplaceCode: string, workplaceName: string) {
            this.employeeId = employeeId;
            this.employeeCode = employeeCode;
            this.employeeName = employeeName;
            this.workplaceId = workplaceId;
            this.workplaceCode = workplaceCode;
            this.workplaceName = workplaceName;
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

