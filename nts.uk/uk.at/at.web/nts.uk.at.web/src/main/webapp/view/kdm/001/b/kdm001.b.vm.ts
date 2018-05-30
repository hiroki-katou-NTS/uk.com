module nts.uk.at.view.kdm001.b.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import modal = nts.uk.ui.windows.sub.modal;
    export class ScreenModel {
        screenItem: KnockoutObservable<ScreenItem>;

        constructor() {
            let self = this;
            self.screenItem = ko.observable(new ScreenItem());
            self.initSubstituteDataList();
            self.screenItem().periodOptionItem = ko.observableArray([
                new ItemModel(0, getText("KDM001_4")),
                new ItemModel(1, getText("KDM001_5"))
            ]);
            self.screenItem().selectedPeriodItem = ko.observable(0);
            self.screenItem().dateValue = ko.observable({});
            //_____CCG001________
            self.screenItem().listEmployeeKCP009 = ko.observableArray([]);
            self.screenItem().showinfoSelectedEmployee = ko.observable(false);
            self.screenItem().ccgcomponent = {
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
                    self.screenItem().showinfoSelectedEmployee(true);
                    self.screenItem().listEmployeeKCP009(data.listEmployee);
                    self.convertEmployeeCcg01ToKcp009(data.listEmployee);
                }
            }
            $('#ccgcomponent').ntsGroupComponent(self.screenItem().ccgcomponent);
            self.screenItem().selectedItem.subscribe(x => {
                if (!self.screenItem().isOnStartUp) {
                    self.screenItem().selectedEmployee = _.find(self.screenItem().listEmployee, item => { return item.employeeId === x; });
                    self.getSubstituteDataList(self.getSearchCondition());
                }
                self.screenItem().isOnStartUp = false;
            });
        }
        openConfirmScreen() {
            let self = this, data;
            data = {
                workplaceCode: self.screenItem().selectedEmployee.workplaceCode,
                workplaceName: self.screenItem().selectedEmployee.workplaceName,
                employeeCode: self.screenItem().selectedEmployee.employeeCode,
                employeeName: self.screenItem().selectedEmployee.employeeName,
                periodRange: self.screenItem().dateValue,
                substituteData: self.screenItem().subData,
                totalRemainHours: self.screenItem().dispTotalRemainHours()
            };
            nts.uk.request.jump("/view/kdr/003/a/index.xhtml", { 'param': data });
        }
        openNewSubstituteData() {
            let self = this;
            setShared('KDM001_I_PARAMS', { selectedEmployee: self.screenItem().selectedEmployee, closure: self.screenItem().closureEmploy });
            modal("/view/kdm/001/i/index.xhtml").onClosed(function() {
                let resParam = getShared("KDM001_I_PARAMS_RES");
                if (resParam) {
                    let isDataChanged = resParam.isChanged;
                    if (isDataChanged) {
                        self.getSubstituteDataList(self.getSearchCondition());
                    }
                }
                $('#substituteDataGrid').focus();
            });
        }
        goToScreenA() {
            nts.uk.request.jump("/view/kdm/001/a/index.xhtml");
        }
        filterByPeriod() {
            let self = this;
            self.getSubstituteDataList(self.getSearchCondition());
            $('#substituteDataGrid').focus();
        }
        getSubstituteDataList(searchCondition: any) {
            let self = this;
            service.getExtraHolidayData(searchCondition).done(function(result) {
                self.screenItem().closureEmploy = result.closureEmploy;
                self.screenItem().listExtractData = result.extraData;
                self.convertToDisplayList();
                self.updateSubstituteDataList();
            }).fail(function(result) {
                dialog.alertError(result.errorMessage);
            });
        }
        getSearchCondition() {
            let self = this, startDate, endDate, searchCondition = null;
            startDate = moment.utc(self.screenItem().dateValue().startDate, 'YYYY/MM/DD').toISOString();
            endDate = moment.utc(self.screenItem().dateValue().endDate, 'YYYY/MM/DD').toISOString();
            searchCondition = null;
            if (self.screenItem().selectedPeriodItem() == 1) {
                searchCondition = { searchMode: 1, employeeId: self.screenItem().selectedEmployee.employeeId, startDate: startDate, endDate: endDate };
            } else {
                searchCondition = { searchMode: 0, employeeId: self.screenItem().selectedEmployee.employeeId, startDate: null, endDate: null };
            }
            return searchCondition;
        }
        convertToDisplayList() {
            let self = this;
            let totalRemain = 0, dayOffDate, unUse, remain, expired, remainDays, listData = [];
            _.forEach(self.screenItem().listExtractData, data => {
                dayOffDate = data.dayOffDate;
                remain = data.remain;
                expired = data.expired;
                totalRemain += remain;
                if (remain != 0) {
                    remain = remain.toFixed(1) + getText('KDM001_27');
                }
                if (expired != 0) {
                    expired = expired.toFixed(1) + getText('KDM001_27');
                }
                if (data.unknowDate == 1) {
                    dayOffDate += '※';
                }
                if (data.type == 0) {
                    unUse = data.unUsedDays;
                    if (unUse != 0) {
                        unUse = unUse.toFixed(1) + getText('KDM001_27');
                    }
                    listData.push(new SubstitutedData(data.id, dayOffDate, unUse, data.linked == 1 ? '有' : "", null, null, null, remain, expired, data.linked));
                } else {
                    remainDays = data.remainDays;
                    if (remainDays != 0) {
                        remainDays = remainDays.toFixed(1) + getText('KDM001_27');
                    }
                    listData.push(new SubstitutedData(data.comDayOffID, null, null, null, dayOffDate, remainDays + getText('KDM001_27'), data.linked == 1 ? '有' : "", remain, expired, data.linked));
                }
            });
            if (self.screenItem().listExtractData.length == 0) {
                dialog.alertError({ messageId: 'Msg_726' });
            }
            self.screenItem().subData = listData;
            self.screenItem().dispTotalRemainHours(totalRemain + getText('KDM001_27'));
        }
        initSubstituteDataList() {
            let self = this;
            self.showSubstiteDataGrid();

        }
        updateSubstituteDataList() {
            let self = this;
            $("#substituteDataGrid").igGrid("dataSourceObject", self.screenItem().subData).igGrid("dataBind");
            self.disableLinkedData();
        }

        showSubstiteDataGrid() {
            let self = this;
            $("#substituteDataGrid").ntsGrid({
                height: '520px',
                name: 'Grid name',
                dataSource: self.screenItem().subData,
                primaryKey: 'id',
                virtualization: true,
                hidePrimaryKey: true,
                rowVirtualization: true,
                virtualizationMode: 'continuous',
                columns: [
                    { headerText: 'ID', key: 'id', dataType: 'string', width: '0px', hidden: true },
                    { headerText: 'linked', key: 'isLinked', dataType: 'string', width: '0px', hidden: true },
                    { headerText: getText('KDM001_33'), template: '<div style="float:right"> ${substituedWorkingDate} </div>', key: 'substituedWorkingDate', dataType: 'string', width: '120px' },
                    { headerText: getText('KDM001_9'), template: '<div style="float:right"> ${substituedWorkingHours} </div>', key: 'substituedWorkingHours', dataType: 'string', width: '100px' },
                    { headerText: getText('KDM001_124'), key: 'substituedWorkingPeg', dataType: 'string', width: '100px' },
                    { headerText: getText('KDM001_34'), template: '<div style="float:right"> ${substituedHolidayDate} </div>', key: 'substituedHolidayDate', dataType: 'string', width: '120px' },
                    { headerText: getText('KDM001_11'), template: '<div style="float:right"> ${substituteHolidayHours} </div>', key: 'substituteHolidayHours', dataType: 'string', width: '100px' },
                    { headerText: getText('KDM001_124'), key: 'substituedHolidayPeg', dataType: 'string', width: '100px' },
                    { headerText: getText('KDM001_37'), template: '<div style="float:right"> ${remainHolidayHours} </div>', key: 'remainHolidayHours', dataType: 'string', width: '100px' },
                    { headerText: getText('KDM001_20'), template: '<div style="float:right"> ${expiredHolidayHours} </div>', key: 'expiredHolidayHours', dataType: 'string', width: '100px' },
                    { headerText: '', key: 'link', dataType: 'string', width: '85px', unbound: true, ntsControl: 'ButtonPegSetting' },
                    { headerText: '', key: 'edit', dataType: 'string', width: '55px', unbound: true, ntsControl: 'ButtonCorrection' }
                ],
                features: [
                    {
                        name: 'Paging',
                        type: "local",
                        pageSize: 14
                    }
                ],
                ntsControls: [
                    { name: 'ButtonPegSetting', text: getText('KDM001_22'), click: function(value) { self.pegSetting(value) }, controlType: 'Button' },
                    { name: 'ButtonCorrection', text: getText('KDM001_23'), click: function(value) { self.doCorrection(value) }, controlType: 'Button' }
                ]
            });
        }
        disableLinkedData() {
            let self = this;
            if (self.screenItem().subData) {
                for (let data of self.screenItem().subData) {
                    if (data.isLinked == 1) {
                        $("#substituteDataGrid").ntsGrid("disableNtsControlAt", data.id, "edit", 'Button');
                    }
                }
            }
        }
        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred(), searchCondition;
            searchCondition = { employeeId: null, stateDate: null, endDate: null };
            service.getInfoEmLogin().done(function(loginerInfo) {
                service.getSubsitutionData(searchCondition).done(function(result) {
                    let wkHistory = result.wkHistory;
                    self.screenItem().closureEmploy = result.extraHolidayManagementDataDto.closureEmploy;
                    self.screenItem().listEmployee = [];
                    self.screenItem().selectedEmployee = new EmployeeInfo(loginerInfo.sid, loginerInfo.employeeCode, loginerInfo.employeeName, wkHistory.workplaceId, wkHistory.workplaceCode, wkHistory.workplaceName);
                    self.screenItem().listEmployee.push(self.screenItem().selectedEmployee);
                    self.screenItem().employeeInputList.push(new EmployeeKcp009(loginerInfo.sid,
                        loginerInfo.employeeCode, loginerInfo.employeeName, wkHistory.workplaceName, wkHistory.wkpDisplayName));
                    self.screenItem().listExtractData = result.extraHolidayManagementDataDto.extraData;
                    self.convertToDisplayList();
                    self.updateSubstituteDataList();
                    self.screenItem().dispTotalExpiredDate = result.compenSettingEmpExpiredDate;
                    self.initKCP009();
                    self.disableLinkedData();
                    dfd.resolve();
                }).fail(function(result) {
                    dialog.alertError(result.errorMessage);
                    dfd.reject();
                });
            }).fail(function(result) {
                dialog.alertError(result.errorMessage);
                dfd.reject();
            });
            return dfd.promise();
        }
        initKCP009() {
            let self = this;
            //_______KCP009_______
            // Initial listComponentOption
            self.screenItem().listComponentOption = {
                systemReference: self.screenItem().systemReference(),
                isDisplayOrganizationName: self.screenItem().isDisplayOrganizationName(),
                employeeInputList: self.screenItem().employeeInputList,
                targetBtnText: self.screenItem().targetBtnText,
                selectedItem: self.screenItem().selectedItem,
                tabIndex: self.screenItem().tabindex
            };
            $('#emp-component').ntsLoadListComponent(self.screenItem().listComponentOption);
        }

        convertEmployeeCcg01ToKcp009(dataList: EmployeeSearchDto[]): void {
            let self = this;
            self.screenItem().employeeInputList([]);
            self.screenItem().listEmployee = [];
            _.each(dataList, function(item) {
                self.screenItem().listEmployee.push(new EmployeeInfo(item.employeeId, item.employeeCode, item.employeeName, item.workplaceId, item.workplaceCode, item.workplaceName));
                self.screenItem().employeeInputList.push(new EmployeeKcp009(item.employeeId, item.employeeCode, item.employeeName, item.workplaceName, ""));
            });
            $('#emp-component').ntsLoadListComponent(self.screenItem().listComponentOption);
            if (dataList.length == 0) {
                self.screenItem().selectedItem('');
            } else {
                let item = self.findIdSelected(dataList, self.screenItem().selectedItem());
                let sortByEmployeeCode = _.orderBy(dataList, ["employeeCode"], ["asc"]);
                if (item == undefined) self.screenItem().selectedItem(sortByEmployeeCode[0].employeeId);
            }
        }

        findIdSelected(dataList: Array<any>, selectedItem: string): any {
            return _.find(dataList, function(obj) {
                return obj.employeeId == selectedItem;
            })
        }
        pegSetting(value) {
            let self = this, rowDataInfo;
            if (value.substituedWorkingDate.length > 0) {
                rowDataInfo = _.find(self.screenItem().listExtractData, x => {
                    return x.id === value.id;
                });
                setShared('KDM001_J_PARAMS', { row: rowDataInfo, selectedEmployee: self.screenItem().selectedEmployee, closure: self.screenItem().closureEmploy });
                modal("/view/kdm/001/j/index.xhtml").onClosed(function() {
                    let resParam = getShared("KDM001_J_PARAMS_RES");
                    if (resParam) {
                        let isDataChanged = resParam.isChanged; 
                        if (isDataChanged) {
                            self.getSubstituteDataList(self.getSearchCondition());
                        }
                    }

                });
            } else {
                rowDataInfo = _.find(self.screenItem().listExtractData, x => {
                    return x.comDayOffID === value.id;
                });
                setShared('KDM001_K_PARAMS', { row: rowDataInfo, selectedEmployee: self.screenItem().selectedEmployee, closure: self.screenItem().closureEmploy });
                modal("/view/kdm/001/k/index.xhtml").onClosed(function() {
                    let resParam = getShared("KDM001_K_PARAMS_RES");
                    if (resParam) {
                        let isDataChanged = resParam.isChanged; 
                        if (isDataChanged) {
                            self.getSubstituteDataList(self.getSearchCondition());
                        }
                    }
                });
            }
        }
        doCorrection(value) {
            let self = this, rowDataInfo;
            if (value.substituedWorkingDate.length > 0) {
                rowDataInfo = _.find(self.screenItem().listExtractData, x => {
                    return x.id === value.id;
                });
                setShared('KDM001_L_PARAMS', { row: rowDataInfo, selectedEmployee: self.screenItem().selectedEmployee, closure: self.screenItem().closureEmploy });
                modal("/view/kdm/001/l/index.xhtml").onClosed(function() {
                    let resParam = getShared("KDM001_L_PARAMS_RES");
                    if (resParam) {
                        let isDataChanged = resParam.isChanged; 
                        if (isDataChanged) {
                            self.getSubstituteDataList(self.getSearchCondition());
                        }
                    }
                });
            } else {
                rowDataInfo = _.find(self.screenItem().listExtractData, x => {
                    return x.comDayOffID === value.id;
                });
                setShared('KDM001_M_PARAMS', { row: rowDataInfo, selectedEmployee: self.screenItem().selectedEmployee, closure: self.screenItem().closureEmploy });
                modal("/view/kdm/001/m/index.xhtml").onClosed(function() {
                    let resParam = getShared("KDM001_M_PARAMS_RES");
                    if (resParam) {
                        let isDataChanged = resParam.isChanged; 
                        if (isDataChanged) {
                            self.getSubstituteDataList(self.getSearchCondition());
                        }
                    }
                });
            }
        }
    }

    export class ScreenItem {
        periodOptionItem: KnockoutObservableArray<ItemModel>;
        selectedPeriodItem: KnockoutObservable<number>;
        dateValue: KnockoutObservable<any>;
        dispTotalRemainHours: KnockoutObservable<string>;
        dispTotalExpiredDate: KnockoutObservable<string>;
        closureEmploy: any;
        selectedEmployee: EmployeeInfo;
        listExtractData: Array<any>;
        subData: Array<any>;
        listEmployee: Array<EmployeeInfo>;
        leaveSettingExpiredDate: string;
        compenSettingEmpExpiredDate: string
        //_____CCG001________
        ccgcomponent: GroupOption;
        listEmployeeKCP009: KnockoutObservableArray<EmployeeSearchDto>;
        showinfoSelectedEmployee: KnockoutObservable<boolean>;
        baseDate: KnockoutObservable<Date>;
        //___________KCP009______________
        employeeInputList: KnockoutObservableArray<EmployeeKcp009>;
        systemReference: KnockoutObservable<number>;
        isDisplayOrganizationName: KnockoutObservable<boolean>;
        targetBtnText: string;
        listComponentOption: ComponentOption;
        selectedItem: KnockoutObservable<string>;
        isOnStartUp: boolean;
        tabindex: number;

        constructor() {
            this.dispTotalRemainHours = ko.observable(null);
            this.dispTotalExpiredDate = ko.observable(null);
            this.subData = null;
            this.employeeInputList = ko.observableArray([]);
            this.systemReference = ko.observable(SystemType.EMPLOYMENT);
            this.isDisplayOrganizationName = ko.observable(false);
            this.targetBtnText = getText("KCP009_3");
            this.selectedItem = ko.observable(null);
            this.isOnStartUp = true;
            this.tabindex = -1;
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

