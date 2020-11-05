module nts.uk.at.view.kdm001.b.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;

    export class ScreenModel {
        periodOptionItem: KnockoutObservableArray<ItemModel>;
        selectedPeriodItem: KnockoutObservable<number>;
        dateValue: KnockoutObservable<any>;
        dispTotalRemainHours: KnockoutObservable<string> = ko.observable(null);
        totalRemainingNumber: string = '';
        dispExpiredDate: KnockoutObservable<string> = ko.observable(null);
        closureEmploy: any;
        selectedEmployee: EmployeeInfo;
        listExtractData: Array<any>;
        subData: Array<any> = null;
        listEmployee: Array<EmployeeInfo>;
        leaveSettingExpiredDate: string;
        compenSettingEmpExpiredDate: string
        isHaveError: KnockoutObservable<boolean> = ko.observable(false);
        unknowEmployeeInfo = false;
        expiredDateText: string;
        //_____CCG001________
        ccgcomponent: GroupOption;
        listEmployeeKCP009: KnockoutObservableArray<EmployeeSearchDto>;
        showinfoSelectedEmployee: KnockoutObservable<boolean>;
        baseDate: KnockoutObservable<Date>;
        //___________KCP009______________
        employeeInputList: KnockoutObservableArray<EmployeeKcp009> = ko.observableArray([]);
        systemReference: KnockoutObservable<number> = ko.observable(SystemType.EMPLOYMENT);
        isDisplayOrganizationName: KnockoutObservable<boolean> = ko.observable(false);
        targetBtnText: string = getText("KCP009_3");
        listComponentOption: ComponentOption;
        selectedItem: KnockoutObservable<string> = ko.observable(null);
        isOnStartUp: boolean = true;
        tabindex: number = -1;
        /** A4_3  凡例 */        
        legendOptions: any;
        startDate: string = '';
        endDate: string = '';

        constructor() {
            let self = this;
            self.initSubstituteDataList();
            self.periodOptionItem = ko.observableArray([
                new ItemModel(0, getText("KDM001_4")),
                new ItemModel(1, getText("KDM001_152"))
            ]);
            self.selectedPeriodItem = ko.observable(0);
            self.dateValue = ko.observable({});
            //_____CCG001________
            self.listEmployeeKCP009 = ko.observableArray([]);
            self.showinfoSelectedEmployee = ko.observable(false);
            self.ccgcomponent = {
                /** Common properties */
                systemType: 2,
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
            /** A4_3  凡例 */ 
            self.legendOptions = {											
                // name: '#[KDM001_153]',
                items: [
                    { labelText: nts.uk.resource.getText("KDM001_154") },
                    { labelText: nts.uk.resource.getText("KDM001_155") },
                    { labelText: nts.uk.resource.getText("KDM001_156") }
                ]
            };
            self.selectedItem.subscribe(x => {
                if (!self.isOnStartUp) {
                    self.selectedEmployee = _.find(self.listEmployee, item => { return item.employeeId === x; });
                    if (!self.selectedEmployee){
                        self.selectedEmployee = new EmployeeInfo(x, "", "", "", "", "");
                        self.unknowEmployeeInfo = true;
                    } else {
                        self.unknowEmployeeInfo = false;
                    }
                    self.getSubstituteDataList(self.getSearchCondition());
                }
                self.isOnStartUp = false;
            });
            self.selectedPeriodItem.subscribe(x => {
                if (x == 0){
                    self.startPage()
                    block.clear();
                    nts.uk.ui.errors.clearAll();
                }
                else if (x == 1){
                    self.getSubstituteDataList(self.getSearchCondition());
                    block.clear();
                    nts.uk.ui.errors.clearAll();
                }    
            });
        }

        openConfirmScreen() {
            let self = this, data;
            data = {
                workplaceCode: self.selectedEmployee.workplaceCode,
                workplaceName: self.selectedEmployee.workplaceName,
                employeeCode: self.selectedEmployee.employeeCode,
                employeeName: self.selectedEmployee.employeeName,
                periodRange: self.dateValue,
                substituteData: self.subData,
                totalRemainHours: self.dispTotalRemainHours()
            };
            nts.uk.request.jump("/view/kdr/003/a/index.xhtml", { 'param': data });
        }

        openNewSubstituteData() {
            let self = this;
            setShared('KDM001_I_PARAMS', { selectedEmployee: self.selectedEmployee, closure: self.closureEmploy });
            modal("/view/kdm/001/i/index.xhtml").onClosed(function() {
                let resParam = getShared("KDM001_I_PARAMS_RES");
                if (resParam) {
                    let isDataChanged = resParam.isChanged;
                    if (isDataChanged) {
                        self.selectedPeriodItem(1);
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
            if (!nts.uk.ui.errors.hasError()) {
                self.getSubstituteDataList(self.getSearchCondition(),true);
                $('#substituteDataGrid').focus();
            }
        }

        getSubstituteDataList(searchCondition: any, isShowMsg?: boolean) {
            let self = this;
            if (self.selectedPeriodItem() == 1){
                $("#daterangepicker .ntsDatepicker").trigger("validate");
            }
            if (!nts.uk.ui.errors.hasError()) {
                service.getExtraHolidayData(searchCondition).done(function(result) {
                    
                    if (self.unknowEmployeeInfo){ 
                        if (result.wkHistory){
                            self.selectedEmployee.workplaceId = result.wkHistory.workplaceId;
                            self.selectedEmployee.workplaceCode = result.wkHistory.workplaceCode;
                            self.selectedEmployee.workplaceName = result.wkHistory.workplaceName;
                            self.selectedEmployee.employeeCode = result.employeeCode;
                            self.selectedEmployee.employeeName = result.employeeName;
                        }
                    }
                    if (result.closureEmploy && result.sempHistoryImport){
                        self.closureEmploy = result.closureEmploy;
                        self.listExtractData = result.remainingData;
                        self.startDate = result.startDate;
                        self.endDate = result.endDate;
                        self.convertToDisplayList(isShowMsg);
                        self.updateSubstituteDataList();
                        self.isHaveError(false);
                        self.dispExpiredDate(result.dispExpiredDate);
                    } else {
                        self.subData = [];
                        self.updateSubstituteDataList();
                        self.dispTotalRemainHours('0' + getText('KDM001_27'));
                        self.dispExpiredDate('');
                        self.isHaveError(true);
                        dialog.alertError({messageId: 'Msg_1306'});
                    }
                    self.totalRemainingNumber = result.totalRemainingNumber;
                }).fail(function(result) {
                    if (result.messageId && result.messageId === 'Msg_1731') {
                        self.isHaveError(true);
                    }
                    self.subData = [];
                    self.updateSubstituteDataList();
                    dialog.alertError({ messageId: result.messageId, messageParams: result.parameterIds })
                });
            }
        }

        getSearchCondition() {
            let self = this;
            let searchCondition = null;
            if (self.selectedPeriodItem() == 1) {
                searchCondition = { searchMode: 1, employeeId: self.selectedEmployee.employeeId, startDate: null, endDate: null };
            } else {
                searchCondition = { searchMode: 0, employeeId: self.selectedEmployee.employeeId, startDate: null, endDate: null };
            }
            return searchCondition;
        }

        convertToDisplayList(isShowMsg?: boolean) {
            let self = this;
            let totalRemain = 0, dayOffDate, remain, expired, listData = [];
            _.forEach(self.listExtractData, data => {
                dayOffDate = data.dayOffDate;
                remain = data.remain;
                expired = data.expired;
                if (data.type ==1 ){
                    remain = remain * -1;
                    expired = expired * -1;
                }
                totalRemain += remain + expired;

                if (data.unknownDate == 1) {
                    if (!dayOffDate){
                        dayOffDate = '';
                    } else dayOffDate += '';
                   
                }

                let substituedexpiredDate = '';
                if (data.deadLine === '') {
                    substituedexpiredDate = '';
                } else if (moment.utc(self.startDate).isSameOrBefore(moment.utc(data.deadLine))
                    && moment.utc(self.endDate).isSameOrAfter(moment.utc(data.deadLine))) {
                    substituedexpiredDate = getText('KDM001_161', [data.deadLine]);
                } else if (moment.utc(self.startDate).isSameOrAfter(moment.utc(data.deadLine)) && data.usedDay > 0) {
                    substituedexpiredDate = getText('KDM001_162', [data.deadLine]);
                } else {
                    substituedexpiredDate = getText('KDM001_163', [data.deadLine]);
                }

                data.occurrenceHour = data.occurrenceHour === 0 ? '' : data.occurrenceHour;
                data.digestionTimes = data.digestionTimes === 0 ? '' : data.digestionTimes;
                data.usedTime = data.usedTime === 0 ? '' : data.usedTime;
                data.remainingHours = data.remainingHours === 0 ? '' : data.remainingHours;

                listData.push(new SubstitutedData(
                    `${data.occurrenceId}${data.digestionId}`,
                    data.occurrenceId,
                    data.digestionId,
                    !_.isEmpty(data.occurrenceId) && data.occurrenceId !== 0 && _.isEmpty(data.accrualDate) ? getText('KDM001_160') : data.accrualDate, // B4_1_1
                    data.occurrenceDay === 0 ? '' : data.occurrenceDay + getText('KDM001_27') + data.occurrenceHour, //B4_2_2
                    null,
                    substituedexpiredDate, //B4_4_2
                    !_.isEmpty(data.digestionId) && data.digestionId !== 0 && _.isEmpty(data.digestionDay) ? getText('KDM001_160') : data.digestionDay, //B4_2_3
                    data.digestionDays > 0 ? data.digestionDays + getText('KDM001_27') + data.digestionTimes : data.digestionTimes, //B4_2_4
                    null,
                    data.dayLetf > 0 ? data.dayLetf + getText('KDM001_27') + data.remainingHours : data.remainingHours, //B4_2_5
                    data.usedDay === 0 && data.usedTime === '' ? '' : data.usedDay + getText('KDM001_27') + data.usedTime, //B4_2_6
                    null,
                    null,
                    data.mergeCell
                ));

            });
            if (isShowMsg && self.listExtractData.length === 0) {
                dialog.alertError({ messageId: 'Msg_726' });
            }
            self.subData = listData;
            console.log("Data 1: ",self.subData);
            
            self.dispTotalRemainHours(self.totalRemainingNumber + getText('KDM001_27'));
        }

        initSubstituteDataList() {
            let self = this;
            self.showSubstiteDataGrid();

        }

        updateSubstituteDataList() {
            let self = this;
            $("#substituteDataGrid").igGrid("dataSourceObject", self.subData).igGrid("dataBind");
            self.disableLinkedData();
        }

        showSubstiteDataGrid() {
            let self = this;
            $("#substituteDataGrid").ntsGrid({
            	height: window.innerHeight - 400 + 'px',
                dataSource: self.subData,
                primaryKey: 'id',
                virtualization: true,
                rowVirtualization: true,
                virtualizationMode: 'continuous',
                columns: [
                    { headerText: 'ID', key: 'id', dataType: 'string', width: '0px', hidden: true },
                    { headerText: 'linked', key: 'isLinked', dataType: 'string', width: '0px', hidden: true },
                    { headerText: 'dataType', key: 'dataType', dataType: 'string', width: '0px', hidden: true },
                    { headerText: 'occurrenceId', key: 'occurrenceId', dataType: 'string', width: '0px', hidden: true },
                    { headerText: 'digestionId', key: 'digestionId', dataType: 'string', width: '0px', hidden: true },
                    { headerText: 'mergeCell', key: 'mergeCell', dataType: 'string', width: '0px', hidden: true },
                    { headerText: 'substituedexpiredDate', key: 'substituedexpiredDate', dataType: 'string', width: '0px',  hidden: true },
                    { headerText: getText('KDM001_33') + ' ' + getText('KDM001_157'), template: '<div style="float:left"> ${substituedWorkingDate} ${substituedexpiredDate} </div>', key: 'substituedWorkingDate', dataType: 'string', width: '210px' },
                    { headerText: getText('KDM001_9'), template: '<div style="float:right"> ${substituedWorkingHours} </div>', key: 'substituedWorkingHours', dataType: 'string', width: '102px' },
                    { headerText: getText('KDM001_34'), template: '<div style="float:left"> ${substituedHolidayDate} </div>', key: 'substituedHolidayDate', dataType: 'string', width: '120px' },
                    { headerText: getText('KDM001_11'), template: '<div style="float:right"> ${substituteHolidayHours} </div>', key: 'substituteHolidayHours', dataType: 'string', width: '102px' },
                    { headerText: getText('KDM001_37'), template: '<div style="float:right"> ${remainHolidayHours} </div>', key: 'remainHolidayHours', dataType: 'string', width: '102px' },
                    { headerText: getText('KDM001_20'), template: '<div style="float:right"> ${expiredHolidayHours} </div>', key: 'expiredHolidayHours', dataType: 'string', width: '102px' },              
                    { headerText: '', key: 'delete', dataType: 'string', width: '55px', unbound: true, ntsControl: 'ButtonCorrection' }
                ],
                features: [
                    {
                        name: 'CellMerging',
                        mergeType: 'physical',
                        columnSettings: [
                            {
                                columnKey: 'substituedWorkingDate',
                                mergeOn: 'always',
                                mergeStrategy: (prevRec: any, curRec: any, columnKey: any) =>
                                    this.isMergeStrategy(prevRec, curRec, columnKey)
                            },
                            {
                                columnKey: 'substituedWorkingHours',
                                mergeOn: 'always',
                                mergeStrategy: (prevRec: any, curRec: any, columnKey: any) =>
                                    this.isMergeStrategy(prevRec, curRec, columnKey)
                            },
                            {
                                columnKey: 'substituedHolidayDate',
                                mergeOn: 'always',
                                mergeStrategy: (prevRec: any, curRec: any, columnKey: any) =>
                                    this.isMergeStrategy(prevRec, curRec, columnKey)
                            },
                            {
                                columnKey: 'substituteHolidayHours',
                                mergeOn: 'always',
                                mergeStrategy: (prevRec: any, curRec: any, columnKey: any) =>
                                    this.isMergeStrategy(prevRec, curRec, columnKey)
                            },
                            {
                                columnKey: 'remainHolidayHours',
                                mergeOn: 'always',
                                mergeStrategy: (prevRec: any, curRec: any, columnKey: any) =>
                                    prevRec['mergeCell'] === curRec['mergeCell'] && prevRec[columnKey] === curRec[columnKey]
                            },
                            {
                                columnKey: 'expiredHolidayHours',
                                mergeOn: 'always',
                                mergeStrategy: (prevRec: any, curRec: any, columnKey: any) =>
                                    prevRec['mergeCell'] === curRec['mergeCell'] && prevRec[columnKey] === curRec[columnKey]
                            },
                            {
                                columnKey: 'delete',
                                mergeOn: 'always',
                                mergeStrategy: (prevRec: any, curRec: any) => prevRec['mergeCell'] === curRec['mergeCell']
                            }
                        ]
                    },
                    {
                        name: 'Paging',
                        type: "local",
                        pageSize: 100,
                        pageSizeList : [15, 50, 100]
                    },
                ],
                ntsControls: [                
                    { name: 'ButtonCorrection', text: getText('KDM001_100'), click: (value: any) => { self.deleteHolidaySetting(value) }, controlType: 'Button' }
                ]
                
            });
            
        }

        isMergeStrategy(prevRec: any, curRec: any, columnKey: any): boolean {
            if ($.type(prevRec[ columnKey ]) === 'string' &&
                $.type(curRec[ columnKey ]) === 'string' &&
                prevRec[ 'mergeCell' ] === curRec[ 'mergeCell' ] &&
                !_.isEmpty(prevRec[ columnKey ])) {
                    return prevRec[ columnKey ].toLowerCase() === curRec[ columnKey ].toLowerCase();
            }
            return false;
        }

        disableLinkedData() {
            let self = this;
            if (self.subData) {
                for (let data of self.subData) {
                    if (data.isLinked == 1) {
                        $("#substituteDataGrid").ntsGrid("disableNtsControlAt", data.id, "edit", 'Button');
                    } else {
                        $("#substituteDataGrid").ntsGrid("enableNtsControlAt", data.id, "edit", 'Button');
                    }
                }
            }
        }

        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred(), searchCondition;
            block.invisible();
            service.getInfoEmLogin().done(loginerInfo => {
                service.getWpName().then((wp: any) => {
                    if (!_.find(self.employeeInputList(), item => item.id === loginerInfo.sid)) {
                        self.employeeInputList.push(new EmployeeKcp009(loginerInfo.sid,
                            loginerInfo.employeeCode, loginerInfo.employeeName, wp.name, wp.name));

                        self.selectedEmployee = {
                            employeeId: loginerInfo.sid, employeeCode: loginerInfo.employeeCode, employeeName: loginerInfo.employeeName,
                            workplaceId: wp.workplaceId, workplaceCode: wp.code, workplaceName: wp.name
                        };
                    }
                    setTimeout(() => {
                        self.initKCP009();
                    }, 10);
                    dfd.resolve();
                });
                const employeeId = self.selectedEmployee ? self.selectedEmployee.employeeId : null;
                searchCondition = { searchMode: self.selectedPeriodItem(), employeeId: employeeId };
                service.getExtraHolidayData(searchCondition).done(result => {
                    if (result.closureEmploy && result.sempHistoryImport) {
                        let wkHistory = result.wkHistory;
                        self.closureEmploy = result.closureEmploy;
                        self.listEmployee = [];
                        self.selectedEmployee = new EmployeeInfo(loginerInfo.sid, loginerInfo.employeeCode, loginerInfo.employeeName, wkHistory.workplaceId, wkHistory.workplaceCode, wkHistory.workplaceName);
                        self.listEmployee.push(self.selectedEmployee);
                        if (!_.find(self.employeeInputList(), item => item.id === loginerInfo.sid))
                            self.employeeInputList.push(new EmployeeKcp009(loginerInfo.sid,
                                loginerInfo.employeeCode, loginerInfo.employeeName, wkHistory.workplaceName, wkHistory.wkpDisplayName));
                        self.listExtractData = result.remainingData;
                        self.totalRemainingNumber = result.totalRemainingNumber;
                        self.startDate = result.startDate;
                        self.endDate = result.endDate;
                        self.convertToDisplayList(true);
                        self.updateSubstituteDataList();
                        self.isHaveError(false);
                        if (result.dispExpiredDate){
                            self.dispExpiredDate(result.dispExpiredDate);
                        }
                        self.disableLinkedData();
                    }else{
                        self.subData = [];
                        self.updateSubstituteDataList();
                        self.isHaveError(true);
                        dialog.alertError({messageId: 'Msg_1306'});
                        self.dispTotalRemainHours('0' + getText('KDM001_27'));
                    }
                    dfd.resolve();
                }).fail(function(result) {
                    self.subData = [];
                    self.updateSubstituteDataList();
                    if (result.messageId && result.messageId === 'Msg_1731') {
                        self.isHaveError(true);
                    }
                    
                    dialog.alertError({ messageId: result.messageId, messageParams: result.parameterIds }).then(() => block.clear());
                    dfd.reject();
                });
            }).fail(function(result) {
                dialog.alertError({ messageId: result.messageId, messageParams: result.parameterIds }).then(() => block.clear());
                dfd.reject();
            });

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
        
        pegSetting(value: any) {
            let self = this, rowDataInfo;
            if (value.dataType == 0) {
                rowDataInfo = _.find(self.listExtractData, x => {
                    return x.id === value.id;
                });
                setShared('KDM001_J_PARAMS', { row: rowDataInfo, selectedEmployee: self.selectedEmployee, closure: self.closureEmploy });
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
                rowDataInfo = _.find(self.listExtractData, x => {
                    return x.comDayOffID === value.id;
                });
                setShared('KDM001_K_PARAMS', { row: rowDataInfo, selectedEmployee: self.selectedEmployee, closure: self.closureEmploy });
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

        // B4_2_9 削除
        deleteHolidaySetting(value: any): void {
            block.invisible();
            //確認メッセージ（Msg_18）を表示する
            dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                let self = this, rowDataInfo
                if (value.dataType == 0) {
                    rowDataInfo = _.find(self.listExtractData, x => {
                        return x.id === value.id;
                    });
                } else {
                    rowDataInfo = _.find(self.listExtractData, x => {
                        return x.comDayOffID === value.id;
                    });
                }
                let command = {
                        leaveId: value.occurrenceId,
                        comDayOffID: value.digestionId
                };
                service.deleteHolidaySetting(command)
                    .then(() => dialog.info({ messageId: "Msg_16" }).then(() => self.getSubstituteDataList(self.getSearchCondition(), true)))
                    .fail(error => {
                        dialog.alertError(error);
                        self.getSubstituteDataList(self.getSearchCondition(), true);
                    })
                    .always(() => block.clear());
            })
            .then(() => block.clear());
        }

        /**
        * closeDialog
        */
        public closeDialog(): void {
            nts.uk.ui.windows.close();
        }
    }


    export class SubstitutedData {
        id: string;
        occurrenceId: string;
        digestionId: string;
        substituedWorkingDate: string;
        substituedWorkingHours: string;
        substituedWorkingPeg: string;
        substituedexpiredDate: string;
        substituedHolidayDate: string;
        substituteHolidayHours: string;
        substituedHolidayPeg: string;
        remainHolidayHours: string;
        expiredHolidayHours: string;
        isLinked: number;
        dataType: number;
        mergeCell: number;
        constructor(id: string, occurrenceId: string, digestionId: string, substituedWorkingDate: string, substituedWorkingHours: string, substituedWorkingPeg: string, substituedexpiredDate: string,
            substituedHolidayDate: string, substituteHolidayHours: string, substituedHolidayPeg: string, remainHolidayHours: string,
            expiredHolidayHours: string, isLinked: number, dataType: number, mergeCell: number) {
            this.id = id;
            this.occurrenceId = occurrenceId;
            this.digestionId = digestionId;
            this.substituedWorkingDate = substituedWorkingDate;
            this.substituedWorkingHours = substituedWorkingHours;
            this.substituedWorkingPeg = substituedWorkingPeg;
            this.substituedexpiredDate = substituedexpiredDate;
            this.substituedHolidayDate = substituedHolidayDate;
            this.substituteHolidayHours = substituteHolidayHours;
            this.substituedHolidayPeg = substituedHolidayPeg;
            this.remainHolidayHours = remainHolidayHours;
            this.expiredHolidayHours = expiredHolidayHours;
            this.isLinked = isLinked;
            this.dataType = dataType;
            this.mergeCell = mergeCell;
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
