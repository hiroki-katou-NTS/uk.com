module nts.uk.at.view.kdm001.a.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    export class ScreenModel {
        closureID: string;
        selectedEmployeeObject: any;
        periodOptionItem: KnockoutObservableArray<ItemModel>;
        selectedPeriodItem: KnockoutObservable<number>;
        dateValue: KnockoutObservable<any>;
        startDateString: KnockoutObservable<string>;
        endDateString: KnockoutObservable<string>;
        dispTotalRemain: KnockoutObservable<string> = ko.observable(null);
        expirationDate: KnockoutObservable<string> = ko.observable(null);
        newDataDisable: KnockoutObservable<boolean> = ko.observable(false);
        //_____CCG001________
        ccgcomponent: GroupOption;
        selectedEmployee: KnockoutObservableArray<EmployeeSearchDto>;
        showinfoSelectedEmployee: KnockoutObservable<boolean>;
        baseDate: KnockoutObservable<Date> = ko.observable(new Date());
        //___________KCP009______________
        employeeInputList: KnockoutObservableArray<EmployeeKcp009> = ko.observableArray([]);
        systemReference: KnockoutObservable<number> = ko.observable(SystemType.EMPLOYMENT);
        isDisplayOrganizationName: KnockoutObservable<boolean> = ko.observable(false);
        targetBtnText: string = getText("KCP009_3");
        listComponentOption: ComponentOption;
        selectedItem: KnockoutObservable<string> = ko.observable(null);
        tabindex: number = -1;
        compositePayOutSubMngData: KnockoutObservableArray<CompositePayOutSubMngData>;

        constructor() {
            let self = this;

            self.compositePayOutSubMngData = ko.observableArray([]);
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

            //_____CCG001________
            self.selectedEmployee = ko.observableArray([]);
            self.showinfoSelectedEmployee = ko.observable(false);
            self.ccgcomponent = {
                /** Common properties */
                systemType: 2,//就業　sửa theo mã bug 102240
                showEmployeeSelection: true,
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
                    self.selectedEmployee(data.listEmployee);
                    self.convertEmployeeCcg01ToKcp009(data.listEmployee);
                }
            }

            self.selectedItem.subscribe(x => {
                self.updateDataList(false);
            });

            self.selectedPeriodItem.subscribe(x => {
                if (x == 0) {
                    nts.uk.ui.errors.clearAll();
                }
            });

            $("#compositePayOutSubMngDataGrid").ntsGrid({
                height: '520px',
                name: 'Grid name',
                dataSource: self.compositePayOutSubMngData(),
                primaryKey: 'id',
                virtualization: true,
                hidePrimaryKey: true,
                rowVirtualization: true,
                virtualizationMode: 'continuous',
                columns: [
                    { headerText: 'ID', key: 'id', dataType: 'string', width: '0px', hidden: true },
                    { headerText: 'linked', key: 'isLinked', dataType: 'string', width: '0px', hidden: true },
                    { headerText: '', key: 'stateAtr', dataType: 'Number', width: '0px', hidden: true },
                    { headerText: '', key: 'unUsedDays', dataType: 'Number', width: '0px', hidden: true },
                    { headerText: '', key: 'remainDays', dataType: 'Number', width: '0px', hidden: true },
                    { headerText: '', key: 'expiredDate', dataType: 'string', width: '0px', hidden: true },
                    { headerText: '', key: 'unknownDatePayout', dataType: 'string', width: '0px', hidden: true },
                    { headerText: '', key: 'unknownDateSub', dataType: 'string', width: '0px', hidden: true },
                    { headerText: '', key: 'occurredDaysText', dataType: 'string', width: '0px', hidden: true },
                    { headerText: '', key: 'requiredDaysText', dataType: 'string', width: '0px', hidden: true },
                    { headerText: '', key: 'unUsedDaysInGridText', dataType: 'string', width: '0px', hidden: true },
                    { headerText: '', key: 'expriedDaysInGridText', dataType: 'string', width: '0px', hidden: true },
                    { headerText: '', key: 'dataType', dataType: 'string', width: '0px', hidden: true },
                    { headerText: getText('KDM001_8'), template: '<div style="float:right"> ${dayoffDatePyout} </div>', key: 'dayoffDatePyout', dataType: 'string', width: '120px' },
                    { headerText: getText('KDM001_9'), template: '<div style="float:right"> ${occurredDays}${occurredDaysText} </div>', key: 'occurredDays', dataType: 'string', width: '86px' },
                    { headerText: getText('KDM001_124'), key: 'payoutTied', dataType: 'string', width: '86px' },
                    { headerText: getText('KDM001_10'), template: '<div style="float:right"> ${dayoffDateSub} </div>', key: 'dayoffDateSub', dataType: 'string', width: '120px' },
                    { headerText: getText('KDM001_11'), template: '<div style="float:right"> ${requiredDays}${requiredDaysText} </div>', key: 'requiredDays', dataType: 'string', width: '86px' },
                    { headerText: getText('KDM001_124'), key: 'subTied', dataType: 'string', width: '86px' },
                    { headerText: getText('KDM001_12'), template: '<div style="float:right"> ${unUsedDaysInGrid}${unUsedDaysInGridText} </div>', key: 'unUsedDaysInGrid', dataType: 'string', width: '86px' },
                    { headerText: getText('KDM001_13'), template: '<div style="float:right"> ${expriedDaysInGrid}${expriedDaysInGridText} </div>', key: 'expriedDaysInGrid', dataType: 'string', width: '86px' },
                    { headerText: getText('KDM001_14'), formatter: getLawAtr, key: 'lawAtr', dataType: 'string', width: '100px' },
                    { headerText: '', key: 'link', dataType: 'string', width: '85px', unbound: true, ntsControl: 'ButtonPegSetting' },
                    { headerText: '', key: 'edit', dataType: 'string', width: '55px', unbound: true, ntsControl: 'ButtonCorrection' }
                ],
                features: [
                    {
                        name: 'Paging',
                        type: "local",
                        pageSize: 14
                    },
                    {
                        name: "Resizing",
                        columnSettings: [
                            { columnKey: "id", allowResizing: false },
                            { columnKey: "dayoffDatePyout", allowResizing: false },
                            { columnKey: "occurredDays", allowResizing: false },
                            { columnKey: "payoutTied", allowResizing: false },
                            { columnKey: "dayoffDateSub", allowResizing: false },
                            { columnKey: "requiredDays", allowResizing: false },
                            { columnKey: "subTied", allowResizing: false },
                            { columnKey: "unUsedDaysInGrid", allowResizing: false },
                            { columnKey: "expriedDaysInGrid", allowResizing: false },
                            { columnKey: "lawAtr", allowResizing: false },
                            { columnKey: "link", allowResizing: false },
                            { columnKey: "edit", allowResizing: false },
                        ],
                    }
                ],
                ntsControls: [
                    { name: 'ButtonPegSetting', text: getText('KDM001_22'), click: function(value) { self.pegSetting(value) }, controlType: 'Button' },
                    { name: 'ButtonCorrection', text: getText('KDM001_23'), click: function(value) { self.doCorrection(value) }, controlType: 'Button' }
                ]
            });
        }

        openNewSubstituteData() {
            let self = this;
            setShared('KDM001_D_PARAMS', { selectedEmployee: self.selectedEmployeeObject, closureId: self.closureID });

            modal("/view/kdm/001/d/index.xhtml").onClosed(function() {
                let params = getShared('KDM001_A_PARAMS');

                if (params.isSuccess) {
                    self.updateDataList(false);
                }

                $('#compositePayOutSubMngDataGrid').focus();
            });
        }

        goToKDR004() {
            let self = this;
            setShared('KDM001_PARAMS', {
                selectedEmployee: self.selectedEmployeeObject, closureId: self.closureID, dateRange: self.dateValue(),
                selectedPeriodItem: self.selectedPeriodItem(), dispTotalRemain: self.dispTotalRemain(), expirationDate: self.expirationDate()
            });
            nts.uk.request.jump("/view/kdr/004/a/index.xhtml");
        }

        goToScreenB() {
            nts.uk.request.jump("/view/kdm/001/b/index.xhtml");
        }

        clickGetDataList() {
            let self = this;

            self.updateDataList(true);
            
            $('#compositePayOutSubMngDataGrid').focus();
        }

        updateDataList(isShowMsg) {
            let self = this;
            let empId = self.selectedItem();
            let isPeriod = self.selectedPeriodItem() == 0 ? false : true;
            let startDate = isPeriod ? moment.utc(self.dateValue().startDate, 'YYYY/MM/DD').format('YYYY-MM-DD') : null;
            let endDate = isPeriod ? moment.utc(self.dateValue().endDate, 'YYYY/MM/DD').format('YYYY-MM-DD') : null;

            if (isPeriod) {
                $("#daterangepickerA .ntsDatepicker").trigger("validate");
            }

            if (!nts.uk.ui.errors.hasError()) {
                service.getFurikyuMngDataExtraction(empId, startDate, endDate, isPeriod).done(function(res: any) {
                    if (res.haveEmploymentCode && (res.closureID != null) && (res.closureID != "")) {
                        let arrayResponse = res.compositePayOutSubMngData;
                        let compositePayOutSubMngDataArray: Array<CompositePayOutSubMngData> = [];
                        for (let i = 0; i < arrayResponse.length; i++) {
                            compositePayOutSubMngDataArray.push(new CompositePayOutSubMngData(arrayResponse[i]));
                        }

                        // update data to view
                        self.compositePayOutSubMngData = ko.observableArray(compositePayOutSubMngDataArray);
                        self.dispTotalRemain(res.numberOfDayLeft);
                        self.expirationDate(self.getExpirationTime(res.expirationDate));
                        self.closureID = res.closureID;
                        self.newDataDisable(false);

                        if (isShowMsg && arrayResponse.length == 0) {
                            dialog.alertError({ messageId: "Msg_725" });
                        }

                        // update grid
                        $("#compositePayOutSubMngDataGrid").igGrid("dataSourceObject", self.compositePayOutSubMngData()).igGrid("dataBind");

                        // disable edit button
                        _.forEach(self.compositePayOutSubMngData(), function(value) {
                            let rowId = value.id;

                            if (value.isLinked) {
                                $("#compositePayOutSubMngDataGrid").ntsGrid("disableNtsControlAt", rowId, 'edit', 'Button');
                            } else {
                                $("#compositePayOutSubMngDataGrid").ntsGrid("enableNtsControlAt", rowId, "edit", 'Button');
                            }
                        });
                    } else {
                        // update data to view
                        self.dispTotalRemain(0);
                        self.expirationDate("");
                        self.closureID = "";
                        self.newDataDisable(true);

                        // update grid
                        self.compositePayOutSubMngData = ko.observableArray([]);
                        $("#compositePayOutSubMngDataGrid").igGrid("dataSourceObject", self.compositePayOutSubMngData()).igGrid("dataBind");

                        // add dialog
                        dialog.alertError({ messageId: "Msg_1306" });
                    }

                    if (_.isNil(res.swkpHistImport)) {
                        self.selectedEmployeeObject = {
                            employeeId: res.personEmpBasicInfoImport.employeeId, employeeCode: res.personEmpBasicInfoImport.employeeCode, employeeName: res.personEmpBasicInfoImport.businessName,
                            workplaceId: "", workplaceCode: "", workplaceName: ""
                        };
                    } else {
                        self.selectedEmployeeObject = {
                            employeeId: res.personEmpBasicInfoImport.employeeId, employeeCode: res.personEmpBasicInfoImport.employeeCode, employeeName: res.personEmpBasicInfoImport.businessName,
                            workplaceId: res.swkpHistImport.workplaceId, workplaceCode: res.swkpHistImport.workplaceCode, workplaceName: res.swkpHistImport.workplaceName
                        };
                    }
                }).fail(function(res: any) {
                    console.log(res);
                });

                $('#compositePayOutSubMngDataGrid').focus();
            } else {
                service.getFurikyuMngDataExtraction(empId, startDate, endDate, false).done(function(res: any) {
                    if (!res.haveEmploymentCode || (res.closureID == null) || (res.closureID == "")) {
                        // update data to view
                        self.dispTotalRemain(0);
                        self.expirationDate("");
                        self.closureID = "";
                        self.newDataDisable(true);

                        // update grid
                        self.compositePayOutSubMngData = ko.observableArray([]);
                        $("#compositePayOutSubMngDataGrid").igGrid("dataSourceObject", self.compositePayOutSubMngData()).igGrid("dataBind");

                        // add dialog
                        dialog.alertError({ messageId: "Msg_1306" });
                    } else {
                        self.newDataDisable(false);
                    }

                    if (_.isNil(res.swkpHistImport)) {
                        self.selectedEmployeeObject = {
                            employeeId: res.personEmpBasicInfoImport.employeeId, employeeCode: res.personEmpBasicInfoImport.employeeCode, employeeName: res.personEmpBasicInfoImport.businessName,
                            workplaceId: "", workplaceCode: "", workplaceName: ""
                        };
                    } else {
                        self.selectedEmployeeObject = {
                            employeeId: res.personEmpBasicInfoImport.employeeId, employeeCode: res.personEmpBasicInfoImport.employeeCode, employeeName: res.personEmpBasicInfoImport.businessName,
                            workplaceId: res.swkpHistImport.workplaceId, workplaceCode: res.swkpHistImport.workplaceCode, workplaceName: res.swkpHistImport.workplaceName
                        };
                    }
                }).fail(function(res: any) {
                    console.log(res);
                });
            }
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            block.invisible();

            service.getInfoEmLogin().done(function(emp) {
                service.getWpName().done(function(wp) {
                    if (wp == null || wp.workplaceId == null || wp.workplaceId == "") {
                        dialog.alertError({ messageId: "Msg_504" }).then(() => {
                            nts.uk.request.jump("com", "/view/ccg/008/a/index.xhtml");
                        });
                    } else {
                        self.selectedEmployeeObject = {
                            employeeId: emp.sid, employeeCode: emp.employeeCode, employeeName: emp.employeeName,
                            workplaceId: wp.workplaceId, workplaceCode: wp.code, workplaceName: wp.name
                        };
                        self.employeeInputList.push(new EmployeeKcp009(emp.sid,
                            emp.employeeCode, emp.employeeName, wp.name, wp.name));
                        self.initKCP009();
                        dfd.resolve();
                    }
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
            self.listComponentOption = {
                systemReference: self.systemReference(),
                isDisplayOrganizationName: self.isDisplayOrganizationName(),
                employeeInputList: self.employeeInputList,
                targetBtnText: self.targetBtnText,
                selectedItem: self.selectedItem,
                tabIndex: self.tabindex
            };
            $('#emp-componentA').ntsLoadListComponent(self.listComponentOption);
        }

        convertEmployeeCcg01ToKcp009(dataList: EmployeeSearchDto[]): void {

            // set data for KCP009 screen B
            if (!($("#tabpanel-2").hasClass("disappear"))) {
                var screenBModel = __viewContext.viewModel.viewmodelB;
                screenBModel.convertEmployeeCcg01ToKcp009(dataList);
            } else {
                let self = this;
                self.employeeInputList([]);
                _.each(dataList, function(item) {
                    self.employeeInputList.push(new EmployeeKcp009(item.employeeId, item.employeeCode, item.employeeName, item.workplaceName, ""));
                });
                $('#emp-componentA').ntsLoadListComponent(self.listComponentOption);
                if (dataList.length == 0) {
                    self.selectedItem('');
                } else {
                    let item = self.findIdSelected(dataList, self.selectedItem());
                    let sortByEmployeeCode = _.orderBy(dataList, ["employeeCode"], ["asc"]);
                    if (item == undefined) self.selectedItem(sortByEmployeeCode[0].employeeId);
                }

            }
        }

        findIdSelected(dataList: Array<any>, selectedItem: string): any {
            return _.find(dataList, function(obj) {
                return obj.employeeId == selectedItem;
            })
        }

        pegSetting(value) {
            let self = this;
            let selectedRowData = value;
            if (value.dataType == 1) {
                if (value.dayoffDateSub.length < 2) value.dayoffDateSub = "";
                setShared('KDM001_EFGH_PARAMS', { rowValue: value, selectedEmployee: self.selectedEmployeeObject, closureId: self.closureID });
                modal("/view/kdm/001/f/index.xhtml").onClosed(function() {
                    let params = getShared('KDM001_A_PARAMS');
                    if (params.isSuccess) {
                        self.updateDataList(false);
                    }
                });
            } else {
                if (value.dayoffDatePyout.length < 2) value.dayoffDatePyout = "";
                setShared('KDM001_EFGH_PARAMS', { rowValue: value, selectedEmployee: self.selectedEmployeeObject, closureId: self.closureID });
                modal("/view/kdm/001/e/index.xhtml").onClosed(function() {
                    let params = getShared('KDM001_A_PARAMS');
                    if (params.isSuccess) {
                        self.updateDataList(false);
                    }
                });
            }
        }

        doCorrection(value) {
            let self = this;
            let selectedRowData = value;
            if (value.dataType == 0) {
                if (value.dayoffDatePyout.length < 2) value.dayoffDatePyout = "";
                setShared('KDM001_EFGH_PARAMS', { rowValue: value, selectedEmployee: self.selectedEmployeeObject, closureId: self.closureID });
                modal("/view/kdm/001/g/index.xhtml").onClosed(function() {
                    let params = getShared('KDM001_A_PARAMS');

                    if (params.isSuccess) {
                        self.updateDataList(false);
                    }
                });
            } else {
                if (value.dayoffDateSub.length < 2) value.dayoffDateSub = "";
                setShared('KDM001_EFGH_PARAMS', { rowValue: value, selectedEmployee: self.selectedEmployeeObject, closureId: self.closureID });
                modal("/view/kdm/001/h/index.xhtml").onClosed(function() {
                    let params = getShared('KDM001_A_PARAMS');

                    if (params.isSuccess) {
                        self.updateDataList(false);
                    }
                });
            }
        }

        getExpirationTime(value) {
            if (value == 0) {
                return getText("Enum_ExpirationTime_THIS_MONTH");
            } else if (value == 1) {
                return getText("Enum_ExpirationTime_UNLIMITED");
            } else if (value == 2) {
                return getText("Enum_ExpirationTime_END_OF_YEAR");
            } else if (value == 3) {
                return getText("Enum_ExpirationTime_ONE_MONTH");
            } else if (value == 4) {
                return getText("Enum_ExpirationTime_TWO_MONTH");
            } else if (value == 5) {
                return getText("Enum_ExpirationTime_THREE_MONTH");
            } else if (value == 6) {
                return getText("Enum_ExpirationTime_FOUR_MONTH");
            } else if (value == 7) {
                return getText("Enum_ExpirationTime_FIVE_MONTH");
            } else if (value == 8) {
                return getText("Enum_ExpirationTime_SIX_MONTH");
            } else if (value == 9) {
                return getText("Enum_ExpirationTime_SEVEN_MONTH");
            } else if (value == 10) {
                return getText("Enum_ExpirationTime_EIGHT_MONTH");
            } else if (value == 11) {
                return getText("Enum_ExpirationTime_NINE_MONTH");
            } else if (value == 12) {
                return getText("Enum_ExpirationTime_TEN_MONTH");
            } else if (value == 13) {
                return getText("Enum_ExpirationTime_ELEVEN_MONTH");
            } else if (value == 14) {
                return getText("Enum_ExpirationTime_ONE_YEAR");
            } else if (value == 15) {
                return getText("Enum_ExpirationTime_TWO_YEAR");
            } else if (value == 16) {
                return getText("Enum_ExpirationTime_THREE_YEAR");
            } else if (value == 17) {
                return getText("Enum_ExpirationTime_FOUR_YEAR");
            } else if (value == 18) {
                return getText("Enum_ExpirationTime_FIVE_YEAR");
            } else {
                return "";
            }
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
        workplaceName: string;
    }

    export interface Ccg001ReturnedData {
        baseDate: string; // 基準日
        closureId?: number; // 締めID
        periodStart: string; // 対象期間（開始)
        periodEnd: string; // 対象期間（終了）
        listEmployee: Array<EmployeeSearchDto>; // 検索結果
    }

    export class CompositePayOutSubMngData {
        id: string;
        payoutId: string;
        cID: string;
        sID: string;
        unknownDatePayout: boolean;
        dayoffDatePyout: string;
        expiredDate: string;
        lawAtr: number;
        occurredDays: string;
        unUsedDays: number;
        stateAtr: number;
        payoutTied: string;
        subOfHDID: string;
        unknownDateSub: boolean;
        dayoffDateSub: string;
        requiredDays: string;
        remainDays: number;
        subTied: string;
        
        //add to fill grid A4_2_5
        unUsedDaysInGrid: string;

        //add to fill grid A4_2_6
        expriedDaysInGrid: string;

        //add to check enable button
        isLinked: boolean;
        dataType: number = 1;
        //add to set '日' after day number
        occurredDaysText: string;
        requiredDaysText: string;
        unUsedDaysInGridText: string;
        expriedDaysInGridText: string;
        
        constructor(params) {
            this.payoutId = params.payoutId;
            if (params.payoutId) this.dataType = 0;
            this.cID = params.cid;
            this.sID = params.sid;
            this.unknownDatePayout = params.unknownDatePayout;
            let dayOffPayout = "";
            if (params.dayoffDatePyout) {
                dayOffPayout = params.dayoffDatePyout;
            }
            this.dayoffDatePyout = params.unknownDatePayout ? dayOffPayout + "※" : dayOffPayout;
            this.expiredDate = params.expiredDate;
            this.lawAtr = params.lawAtr;
            this.unUsedDays = params.unUsedDays;
            this.stateAtr = params.stateAtr;
            this.payoutTied = params.payoutTied ? getText("KDM001_130") : "";
            this.subOfHDID = params.subOfHDID;
            this.unknownDateSub = params.unknownDateSub;
            let dayoffDateSub = "";
            if (params.dayoffDateSub) {
                dayoffDateSub = params.dayoffDateSub;
            }
            this.dayoffDateSub = params.unknownDateSub ? dayoffDateSub + "※" : dayoffDateSub;
            this.remainDays = params.remainDays;
            this.subTied = params.subTied ? getText("KDM001_130") : "";

            if (params.occurredDays != null) {
                if (params.occurredDays > 0) {
                    this.occurredDaysText = getText("KDM001_27");
                    this.occurredDays = params.occurredDays.toFixed(1);
                } else {
                    this.occurredDays = "" + params.occurredDays;
                }
            }

            if (params.requiredDays != null) {
                if (params.requiredDays > 0) {
                    this.requiredDaysText = getText("KDM001_27");
                    this.requiredDays = params.requiredDays.toFixed(1);
                } else {
                    this.requiredDays = "" + params.requiredDays;
                }
            }

            if ((params.payoutId != null) && (params.payoutId != "")) {
                this.id = params.payoutId;

                if ((this.stateAtr !== 2) && moment.utc(params.expiredDate, 'YYYY/MM/DD').diff(moment.utc(moment.utc().format('YYYY/MM/DD'), 'YYYY/MM/DD')) >= 0) {
                    this.unUsedDaysInGrid = "" + params.unUsedDays;
                    this.expriedDaysInGrid = "0";
                    if (params.unUsedDays > 0) {
                        this.unUsedDaysInGridText = getText("KDM001_27");
                        this.unUsedDaysInGrid = params.unUsedDays.toFixed(1);
                    }
                } else {
                    this.unUsedDaysInGrid = "0";
                    this.expriedDaysInGrid = "" + params.unUsedDays;
                    if (params.unUsedDays > 0) {
                        this.expriedDaysInGridText = getText("KDM001_27");
                        this.expriedDaysInGrid = params.unUsedDays.toFixed(1);
                    }
                }
            } else if ((params.subOfHDID != null) && (params.subOfHDID != "")) {
                this.id = params.subOfHDID;
                this.unUsedDaysInGrid = "" + (params.remainDays * (-1));
                this.expriedDaysInGrid = "0";
                if (params.remainDays > 0) {
                    this.unUsedDaysInGridText = getText("KDM001_27");
                    this.unUsedDaysInGrid = (params.remainDays * (-1)).toFixed(1);
                }
            }

            this.isLinked = (params.payoutTied || params.subTied) ? true : false;
        }
    }

    function getLawAtr(value, row) {
        if (value && value === '0') {
            return getText("Enum_HolidayAtr_STATUTORY_HOLIDAYS");
        } else if (value && value === '1') {
            return getText("Enum_HolidayAtr_NON_STATUTORY_HOLIDAYS");
        } else if (value && value === '2') {
            return getText("Enum_HolidayAtr_PUBLIC_HOLIDAY");
        } else {
            return '';
        }
    }
}

