module nts.uk.com.view.kwr002.a {
    import blockUI = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import getMsg = nts.uk.resource.getMessage;
    import isNullOrUndefined = nts.uk.util.isNullOrUndefined;

    export module viewModel {
        export class ScreenModel {
            ccgcomponent: GroupOption;
            exportDto: KnockoutObservable<ExportDto>;
            enableSave: KnockoutObservable<boolean>;
            baseDate: KnockoutObservable<Date>;
            periodStartDate: KnockoutObservable<moment.Moment>;
            employeeList: KnockoutObservableArray<UnitModel>;
            listEmployee: KnockoutObservableArray<Employee>;
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

            lstPersonComponentOption: ComponentOption;

            selectedEmployeeCode: KnockoutObservableArray<string>;
            alreadySettingPersonal: KnockoutObservableArray<UnitAlreadySettingModel>;

            comboboxName: string;

            closureId: KnockoutObservable<number>;

            // radio button group A7_2
            selectedCodeA8_5: KnockoutObservable<number> = ko.observable(0);

            // switch button A9_3 A9_4
            dataZeroDisplayType: KnockoutObservableArray<any> = ko.observableArray([
                { code: 0, name: nts.uk.resource.getText("KWR002_23") },
                { code: 1, name: nts.uk.resource.getText("KWR002_24") }
            ]);
            selectedDataZeroDisplayType: KnockoutObservable<number> = ko.observable(0);

            selectedDataDisplayItemType: KnockoutObservable<number> = ko.observable(0);
            itemListTypePageBrake: KnockoutObservableArray<ItemModel>;

            enableA8_3: KnockoutObservable<boolean> = ko.observable(false);
            enableA8_8: KnockoutObservable<boolean> = ko.observable(false);
            // authority for work performance
            enableAuthority: KnockoutObservable<boolean> = ko.observable(true);

            freeSettingLst: KnockoutObservableArray<AttendanceRecordExportSettingDto>;
            selectedCodeA8_8: KnockoutObservable<string> = ko.observable('');
            dataTranferScreenB: DataScreenB;
            companyId: string = '';
            roleId: string = '';
            employeeId: string = '';


            constructor() {
                let self = this;
                self.comboboxName = nts.uk.resource.getText("KWR002_19");
                //let currentDate = self.getCurrentDay(new Date());
                self.enable = ko.observable(true);
                self.permission = ko.observable(true);
                self.startDateString = ko.observable("");
                self.endDateString = ko.observable("");
                self.dateValue = ko.observable("");
                self.exportDto = ko.observable<ExportDto>();
                self.listEmployee = ko.observableArray<Employee>([]);
                self.startDateString.subscribe(function(value) {
                    self.dateValue().startDate = value;
                    self.dateValue.valueHasMutated();
                });

                self.endDateString.subscribe(function(value) {
                    self.dateValue().endDate = value;
                    self.dateValue.valueHasMutated();
                });

                self.attendanceRecordList = ko.observableArray([]);
                self.freeSettingLst = ko.observableArray([]);

                self.selectedCode = ko.observable(null);

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

                self.selectedEmployeeCode = ko.observableArray([]);
                self.alreadySettingPersonal = ko.observableArray([]);

                self.ccgcomponent = {

                    tabindex: -1,
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
//                    periodStartDate: self.dateValue().startDate, // 対象期間開始日
//                    periodEndDate: self.dateValue().endDate, // 対象期間終了日
                    dateRangePickerValue: self.dateValue,
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
                    showWorktype: false, // 勤種条件
                    isMutipleCheck: true, // 選択モード

                    /** Return data */
                    returnDataFromCcg001: function(data: Ccg001ReturnedData) {
                        //画面項目「A7_3」を更新する
                        self.dateValue().startDate = moment(data.periodStart).format("YYYY/MM/DD");
                        self.dateValue().endDate = moment(data.periodEnd).format("YYYY/MM/DD");
                        self.dateValue.valueHasMutated();
                        //                        self.selectedEmployee(data.listEmployee);
                        self.applyKCP005ContentSearch(data.listEmployee);
                        self.listEmployee(data.listEmployee);
                        self.closureId(data.closureId);
                    }
                }

                // self.currentCodeList.subscribe(function (employeeId: any) {
                //     let employee: any;
                //     self.selectedEmployee.removeAll();
                //     for (let i of employeeId) {
                //         employee = self.findByIdEmployee(i);
                //         self.selectedEmployee.push(employee);
                //     }
                // });

                self.closureId = ko.observable(0);
                self.enableA8_3(true);
                self.selectedCodeA8_5.subscribe((value) => {
                    self.enableA8_3(value === 0);
                    self.enableA8_8(value === 1);
                })

            }

            public start_page(): JQueryPromise<any> {

                blockUI.invisible();
                const vm = this;
                let dfd = $.Deferred();

                // Start component
                $('#ccgcomponent').ntsGroupComponent(vm.ccgcomponent).done(() => {
                    vm.employeeList = ko.observableArray<UnitModel>([]);
                    vm.applyKCP005ContentSearch([]);
                    // Load employee list component
                    $('#employee-search').ntsListComponent(vm.lstPersonComponentOption).done(function() {
                        $(".icon.icon-searchbox").remove();
                        $(".caret-right.caret-background.bg-green").removeClass();

                    }).then(() => {
                        setTimeout(() => {
                            $(".nts-searchbbox-wrapper").hide();
                        }, 200);

                    });
                });
                service.getPermission().done((result) => {
                    if (result == true) {
                        vm.permission(false);
                    }
                });

                $.when(vm.getDataCharateristic()).done((dataCharacteristic: any) => {
                    let isExist = !(_.isUndefined(dataCharacteristic) || _.isNull(dataCharacteristic));
                    service.getAllAttendanceRecExpSet().done((wrapper: AttendanceRecordExportSettingWrapperDto) => {
                        if (wrapper.standardSettingLst.length === 0) {
                            vm.attendanceRecordList([]);
                        } else {
                            var sortArray = _.orderBy(wrapper.standardSettingLst, [e => Number(e.code)], ['asc']);
                            _.map(sortArray, (item) => {
                                item.code = _.padStart(item.code, 2, '0');
                            });
                            vm.attendanceRecordList(sortArray);
                            vm.selectedCode(sortArray[0].code);
                        }

                        if (wrapper.freeSettingLst.length === 0) {
                            vm.freeSettingLst([]);
                        } else {
                            var sortArray = _.orderBy(wrapper.freeSettingLst, [e => Number(e.code)], ['asc']);
                            _.map(sortArray, (item) => {
                                item.code = _.padStart(item.code, 2, '0');
                            });
                            vm.freeSettingLst(sortArray);
                            vm.selectedCodeA8_8(sortArray[0].code);
                        }

                        vm.selectedCodeA8_5(wrapper.isFreeSetting ? ItemSelectionType.FREE_SETTING : ItemSelectionType.STANDARD_SETTING);
                        if (wrapper.isFreeSetting) {
                            if (vm.freeSettingLst().length > 0 && isExist) {
                                vm.renewDataPage();
                            }
                        } else {
                            if (vm.attendanceRecordList().length > 0 && isExist) {
                                vm.renewDataPage();
                            }
                        }
                        vm.enableA8_3(!wrapper.isFreeSetting);
                        vm.enableA8_8(wrapper.isFreeSetting);

                        dfd.resolve();
                    });
                });

                service.getClosureMonth().done((dto) => {
                    const startMonth = dto.currentMonth;
                    const endMonth = dto.currentMonth;
                    const parsedStart = startMonth.slice(0, 4) + '/' + startMonth.slice(4);
                    const parsedEnd = endMonth.slice(0, 4) + '/' + endMonth.slice(4);
                    vm.dateValue({
                        startDate : parsedStart,
                        endDate : parsedEnd
                    })
                    vm.dateValue.valueHasMutated();
                });

                //   ログイン社員の就業帳票の権限を取得する (Get the authority of work report of logged in employee)
                service.getAuthorityOfWorkPerformance().done((dto: any) => {
                    if (_.isNil(dto)) {
                        vm.enableAuthority(false);
                    } else {
                        vm.enableAuthority(dto.freeSetting);
                        vm.companyId = dto.companyId;
                        vm.roleId = dto.roleId;
                        vm.employeeId = dto.employeeId;
                    }
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
                        affiliationName: employeeSearch.affiliationName
                    };
                    employeeSearchs.push(employee);
                }
                self.employeeList(employeeSearchs);
                self.lstPersonComponentOption = {
                    tabindex: 3,
                    isShowAlreadySet: false,
                    isMultiSelect: true,
                    listType: ListType.EMPLOYEE,
                    employeeInputList: self.employeeList,
                    selectType: SelectType.SELECT_ALL,
                    selectedCode: self.selectedEmployeeCode,
                    isDialog: false,
                    isShowNoSelectRow: false,
                    alreadySettingList: self.alreadySettingPersonal,
                    isShowWorkPlaceName: true,
                    isShowSelectAllButton: false,
                    isSelectAllAfterReload: true,
                    maxWidth: 480,
                    maxRows: 20
                };
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
            // public getAllEmployeeIdBySearch(): string[] {
            //     let self = this;
            //     let employeeIds: string[] = [];
            //     for (let employeeSelect of self.employeeList()) {
            //         employeeIds.push(self.findEmployeeIdByCode(employeeSelect.code));
            //     }
            //     return employeeIds;
            // }

            /**
             * find employee id in selected
             */
            public findEmployeeByCode(employeeCode: string): Employee {
                let self = this;
                for (let employee of self.listEmployee()) {
                    if (employee.employeeCode === employeeCode) {
                        return employee;
                    }
                }
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
            public findByIdEmployee(employeeId: string): Employee {
                let employee: Employee;
                let self = this;
                for (let employeeSelect of self.listEmployee()) {
                    if (employeeSelect.employeeId === employeeId) {
                        employee = employeeSelect;
                        break;
                    }
                }
                return employee;
            }

            /**
             * convert string to date
             */
            private toDate(strDate: string): Date {
                return moment(strDate, 'YYYY/MM/DD').toDate();
            }

            public print() {
                // mode = 1 for export file excel
                let self = this;
                self.saveAttendanceRecordCondition().done(() => {
                    let companyId: string = __viewContext.user.companyId;
                    let userId: string = __viewContext.user.employeeId;
                    service.restoreCharacteristic(companyId,userId).done((data: any) => {
                        const code = self.selectedCodeA8_5() === 0 ? self.selectedCode() : self.selectedCodeA8_8();
                        if (self.selectedEmployeeCode().length <= 0) {
                            nts.uk.ui.dialog.alertError({ messageId: "Msg_1310" });
                            return;
                        }
                        //「お待ちください」を表示する
                        let attendanceRecordOutputConditionsDto = new AttendanceRecordOutputConditionsDto(
                            self.selectedCodeA8_5()
                            , self.selectedCode()
                            , self.selectedCodeA8_8()
                            , companyId
                            , userId
                            , 0
                            , self.filterLayoutId(self.selectedCodeA8_8(),ItemSelectionType.FREE_SETTING)
                            , self.filterLayoutId(self.selectedCode(),ItemSelectionType.STANDARD_SETTING));
                        nts.uk.ui.block.grayout();
                        self.exportDto(new ExportDto(
                                self.findEmployeeIdsByCodes(self.selectedEmployeeCode()),
                                self.toDate(self.dateValue().startDate),
                                self.toDate(self.dateValue().endDate),
                                code,
                                1,
                                self.closureId(),
                                self.selectedCodeA8_5(),
                                attendanceRecordOutputConditionsDto));
                        service.exportService(self.exportDto()).done((response: any) => {
                            if (response.taskDatas.length > 0) {
                                nts.uk.ui.dialog.error({ messageId: "Msg_1269", messageParams: [response.taskDatas[0].valueAsString] });
                            }
                            nts.uk.ui.block.clear();
                        }).fail((res: any) => {
                            nts.uk.ui.block.clear();
                            nts.uk.ui.dialog.error(res);
                        });
                    })
                }).fail((err:any) => {
                    nts.uk.ui.dialog.alertError({ messageId: err.message, messageParams: null})
                }).always(() => nts.uk.ui.block.clear());

            }

            public exportExcel() {
                // mode = 2 for export file excel
                let self = this;
                self.saveAttendanceRecordCondition().done(() => {
                    let companyId: string = __viewContext.user.companyId;
                    let userId: string = __viewContext.user.employeeId;
                    service.restoreCharacteristic(companyId, userId).done((data: any) => {
                        const code: any = self.selectedCodeA8_5() === ItemSelectionType.STANDARD_SETTING ? self.selectedCode() : self.selectedCodeA8_8();
                        //「お待ちください」を表示する
                        let attendanceRecordOutputConditionsDto = new AttendanceRecordOutputConditionsDto(
                            self.selectedCodeA8_5()
                            , self.selectedCode()
                            , self.selectedCodeA8_8()
                            , companyId
                            , userId
                            , 0
                            , self.filterLayoutId(self.selectedCodeA8_8(),ItemSelectionType.FREE_SETTING)
                            , self.filterLayoutId(self.selectedCode(),ItemSelectionType.STANDARD_SETTING));

                        if (self.selectedEmployeeCode().length <= 0) {
                            nts.uk.ui.dialog.alertError({ messageId: "Msg_1310" });
                            return;
                        }

                        nts.uk.ui.block.grayout();
                        self.exportDto(new ExportDto(
                            self.findEmployeeIdsByCodes(self.selectedEmployeeCode()),
                            self.toDate(self.dateValue().startDate),
                            self.toDate(self.dateValue().endDate),
                            code,
                            2,
                            self.closureId(),
                            self.selectedCodeA8_5(),
                            attendanceRecordOutputConditionsDto));
                        service.exportService(self.exportDto()).done((response: any) => {
                            if (response.taskDatas.length > 0) {
                                nts.uk.ui.dialog.error({ messageId: "Msg_1269", messageParams: [response.taskDatas[0].valueAsString] });
                            }
                            nts.uk.ui.block.clear();
                        }).fail((res: any) => {
                            nts.uk.ui.block.clear();
                            nts.uk.ui.dialog.error(res);
                        });
                    });
                }).fail((err:any) => {
                    nts.uk.ui.dialog.alertError({ messageId: err.message, messageParams: null});
                }).always(() => nts.uk.ui.block.clear());
            }


            private findEmployeeIdsByCodes(employeeCodes: string[]): Employee[] {
                let self = this;
                let employeeIds: string[] = [];
                let employee: Employee[] = [];
                for (let employeeCode of employeeCodes) {
                    let emp = self.findEmployeeByCode(employeeCode);
                    if (isNullOrUndefined(emp)) continue;
                    employee.push(emp);
                }
                return employee;
            }

            /**
             * showMessageError
             */
            public showMessageError(res: any) {
                let dfd = $.Deferred<any>();

                // check error business exception
                if (!res.businessException) {
                    return;
                }

                nts.uk.ui.dialog.bundledErrors(res);
                // show error message
                //                if (Array.isArray(res.errors)) {
                //                    nts.uk.ui.dialog.bundledErrors(res);
                //                } else {
                //                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
                //                }
            }

            public openBDialog(): JQueryPromise<any> {
                const vm = this;
                var dfd = $.Deferred();
                blockUI.invisible();

                vm.dataTranferScreenB = new DataScreenB(vm.selectedCodeA8_5()
                                    , vm.companyId
                                    , vm.employeeId
                                    , ''
                                    , vm.selectedCodeA8_5() === ItemSelectionType.STANDARD_SETTING ? vm.selectedCode() : vm.selectedCodeA8_8());
                setShared("currentARESSelectCode",vm.selectedCodeA8_5() === ItemSelectionType.STANDARD_SETTING ? vm.selectedCode() : vm.selectedCodeA8_8());
                setShared("dataTranferScreenB", vm.dataTranferScreenB);
                nts.uk.ui.windows.sub.modal("/view/kwr/002/b/index.xhtml").onClosed(function() {
                    service.getAllAttendanceRecExpSet().then((result: AttendanceRecordExportSettingWrapperDto) => {
                        let ARESCode = getShared("currentARESCode");
                        if (vm.selectedCodeA8_5() === ItemSelectionType.STANDARD_SETTING) {
                            // for standard setting
                            if (result.standardSettingLst && result.standardSettingLst.length > 0) {
                                let sortArray = _.orderBy(result.standardSettingLst, [e => Number(e.code)], ['asc']);
                                _.map(sortArray, (item) => {
                                    item.code = _.padStart(item.code, 2, '0');
                                });
                                vm.attendanceRecordList(sortArray);
                                if (ARESCode === undefined) {
                                    vm.selectedCode(sortArray[0].code);
                                }
                                else {
                                    vm.selectedCode(ARESCode);
                                }
                                vm.enableSave(true);
                            } else {
                                vm.attendanceRecordList(null);
                                vm.selectedCode(null);
                                vm.selectedCodeA8_8(null);
                                vm.enableSave(false);
                            }
                        }

                        // for free setting
                        if (!result.freeSettingLst || result.freeSettingLst.length === 0) {
                            vm.freeSettingLst(null);
                            vm.selectedCodeA8_8(null);
                            // vm.enableSave(false);
                        } else {
                            let sortArray = _.orderBy(result.freeSettingLst, [e => Number(e.code)], ['asc']);
                            _.map(sortArray, (item) => {
                                item.code = _.padStart(item.code, 2, '0');
                            });
                            vm.freeSettingLst(sortArray);
                            if (ARESCode === undefined) {
                                vm.selectedCodeA8_8(sortArray[0].code);
                            }
                            else {
                                vm.selectedCodeA8_8(ARESCode);
                            }
                            vm.enableSave(true);
                        }
                        dfd.resolve();
                    });
                });
                nts.uk.ui.block.clear();

                return dfd.promise();
            }

            public getDataCharateristic(): JQueryPromise<any> {
                const dfd = $.Deferred<any>();
                let companyId: string = __viewContext.user.companyId;
                let userId: string = __viewContext.user.employeeId;

                $.when(service.restoreCharacteristic(companyId, userId)).done(function(data: AttendanceRecordOutputConditionsDto) {
                    if (_.isUndefined(data)) {
                        let attendanceRecordOutputConditionsDto = new AttendanceRecordOutputConditionsDto(
                            ItemSelectionType.STANDARD_SETTING
                            , ''
                            , ''
                            , companyId
                            , userId
                            , 0
                            , ''
                            , '');
                        service.saveCharacteristic(companyId, userId, attendanceRecordOutputConditionsDto);
                    }
                    dfd.resolve(data);
                });
                return dfd.promise();
            }

            private renewDataPage(): void {
                let self = this;
                let companyId: string = __viewContext.user.companyId;
                let userId: string = __viewContext.user.employeeId;
                service.restoreCharacteristic(companyId, userId).done(function(data: any) {
                    let condition: AttendanceRecordOutputConditionsDto = data;
                    self.selectedCode(condition.standardSelectionCode);
                    self.selectedCodeA8_8(condition.freeSelectionCode);
                    self.selectedDataZeroDisplayType(condition.zeroDisplayType);
                    self.selectedCodeA8_5(condition.selectionType);
                })
            }

            private saveAttendanceRecordCondition(): JQueryPromise<void> {
                const self = this;
                const dfd = $.Deferred<void>();
                let companyId: string = __viewContext.user.companyId;
                let userId: string = __viewContext.user.employeeId;
                let attendanceRecordOutputConditionsDto;
                service.restoreCharacteristic(companyId, userId).done((data: any) => {
                    if (_.isUndefined(data)) {
                        attendanceRecordOutputConditionsDto = new AttendanceRecordOutputConditionsDto(
                            ItemSelectionType.STANDARD_SETTING
                            , ''
                            , ''
                            , companyId
                            , userId
                            , self.selectedDataZeroDisplayType()
                            , ''
                            , '');
                    } else {
                        attendanceRecordOutputConditionsDto = new AttendanceRecordOutputConditionsDto(
                            self.selectedCodeA8_5()
                            , self.selectedCode()
                            , self.selectedCodeA8_8()
                            , companyId
                            , userId
                            , self.selectedDataZeroDisplayType()
                            , self.filterLayoutId(self.selectedCode(),ItemSelectionType.STANDARD_SETTING)
                            , self.filterLayoutId(self.selectedCodeA8_8(),ItemSelectionType.FREE_SETTING)
                        );
                    }
                    self.selectedDataZeroDisplayType = data.zeroDisplayType;
                    service.saveCharacteristic(companyId, userId, attendanceRecordOutputConditionsDto);
                    dfd.resolve(data);
                });
                return dfd.promise();
            }

            private filterLayoutId(code: string, type: number): string {
                const vm = this;
                let layoutId: string = '';
                let obj: any;
                if (type === ItemSelectionType.STANDARD_SETTING) {
                    obj = _.filter(vm.attendanceRecordList(), {"code": code});
                    layoutId = _.isEmpty(obj) ? "" : obj[0].layoutId;
                } else {
                    obj = _.filter(vm.freeSettingLst(), {"code": code});
                    layoutId = _.isEmpty(obj) ? "" : obj[0].layoutId;
                }
                return layoutId;
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

        export class AttendanceRecordExportSettingDto {
            code: string;
            name: string;

            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }

        export class AttendanceRecordExportSettingWrapperDto {
            isFreeSetting: boolean;
            freeSettingLst: AttendanceRecordExportSettingDto[];
            standardSettingLst: AttendanceRecordExportSettingDto[];
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
            startDate: Date;
            endDate: Date;
            layout: string;
            mode: number;
            closureId: number;
            selectionType: number;
            condition : AttendanceRecordOutputConditionsDto

            constructor(employeeList: Array<Employee>, startDate: Date, endDate: Date, layout: string, mode: number, closureId: number, selectionType: number,condition: AttendanceRecordOutputConditionsDto) {
                this.employeeList = employeeList;
                this.startDate = startDate;
                this.endDate = endDate;
                this.layout = layout;
                this.mode = mode;
                this.closureId = closureId;
                this.selectionType = selectionType;
                this.condition = condition;
            }
        }

        class ItemModel {
            code: string;
            name: string;

            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }

        class ItemSelectionType {
            static STANDARD_SETTING = 0;
            static FREE_SETTING = 1;
        }

        class DataScreenB {
            selectionType: number; //設定区分（自由設定）
            companyId: string; //会社ID
            employeeId: string; //社員ID
            selectedOutputLayoutId: string; //選択出力レイアウトID
            selectedCode: string;// 選択コード

            constructor(selectionType: number, companyId: string,employeeId: string,selectedOutputLayoutId: string,selectedCode: string) {
                this.selectionType = selectionType;
                this.companyId = companyId;
                this.employeeId = employeeId;
                this.selectedOutputLayoutId = selectedOutputLayoutId;
                this.selectedCode = selectedCode;
            }
        }

        class AttendanceRecordOutputConditionsDto {
            //	項目選択区分
            selectionType: number;
            //	定型選択_コード
            standardSelectionCode: string;
            //	自由設定_コード
            freeSelectionCode: string;
            // 	会社ID
            companyId: string;
            // 	ユーザID
            userId: string;
            //	ゼロ表示区分
            zeroDisplayType: number;
            //	自由設定_出力レイアウトID
            freeSettingLayoutId: string;
            //	定型選択_出力レイアウトID
            standardSelectionLayoutId: string;

            constructor(selectionType: number
                , standardSelectionCode: string
                , freeSelectionCode: string
                , companyId: string
                , userId: string
                , zeroDisplayType: number
                , freeSettingLayoutId: string
                , standardSelectionLayoutId: string) {
                this.selectionType = selectionType;
                this.standardSelectionCode = standardSelectionCode;
                this.freeSelectionCode = freeSelectionCode;
                this.companyId = companyId;
                this.userId = userId;
                this.zeroDisplayType = zeroDisplayType;
                this.freeSettingLayoutId = freeSettingLayoutId;
                this.standardSelectionLayoutId = standardSelectionLayoutId;
            }
        }
    }
}