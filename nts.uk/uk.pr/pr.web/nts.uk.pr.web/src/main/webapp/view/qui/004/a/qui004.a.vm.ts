module nts.uk.pr.view.qui004.a.viewmodel {

    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText
    import model = nts.uk.pr.view.qui004.share.model;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import errors = nts.uk.ui.errors;

    export class ScreenModel {

        empInsOutOrder: KnockoutObservableArray<model.ItemModel>;
        officeCls: KnockoutObservableArray<model.ItemModel>;
        officeClsTxt: KnockoutObservableArray<model.ItemModel>;
        submitNameCls: KnockoutObservableArray<model.ItemModel>;
        printCfg: KnockoutObservableArray<model.ItemModel>;
        lineFeedCodeCls: KnockoutObservableArray<model.ItemModel>;

        screenMode: number;
        ccg001ComponentOption: GroupOption;
        startDate: KnockoutObservable<string> = ko.observable('');
        startDateJp: KnockoutObservable<string> = ko.observable('');
        endDate: KnockoutObservable<string> = ko.observable('');
        endDateJp: KnockoutObservable<string> = ko.observable('');
        filingDate: KnockoutObservable<string> = ko.observable('');
        filingDateJp: KnockoutObservable<string> = ko.observable('');

        /* kcp005 */
        baseDate: any;
        listComponentOption: any;
        selectedCode: KnockoutObservableArray<string> = ko.observableArray([]);
        multiSelectedCode: KnockoutObservableArray<string>;
        isShowAlreadySet: KnockoutObservable<boolean>;
        alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
        isDialog: KnockoutObservable<boolean>;
        isShowNoSelectRow: KnockoutObservable<boolean>;
        isMultiSelect: KnockoutObservable<boolean>;
        isShowWorkPlaceName: KnockoutObservable<boolean>;
        isShowSelectAllButton: KnockoutObservable<boolean>;
        disableSelection: KnockoutObservable<boolean>;
        employeeList: KnockoutObservableArray<UnitModel> = ko.observableArray<UnitModel>([]);

        empInsReportSetting: KnockoutObservable<EmpInsReportSetting> = ko.observable(new EmpInsReportSetting({
            submitNameAtr: 0,
            outputOrderAtr: 0,
            myNumberClsAtr: 0,
            officeClsAtr: 1,
            nameChangeClsAtr: 0
        }));

        empInsReportTxtSetting: KnockoutObservable<EmpInsReportTxtSetting> = ko.observable(new EmpInsReportTxtSetting({
            lineFeedCode: 0,
            officeAtr: 1,
            fdNumber: null
        }));



        constructor() {
            let self = this;

            self.empInsOutOrder = ko.observableArray([
                new model.ItemModel(0, getText('Enum_EmpInsOutOrder_INSURANCE_NUMBER')),
                new model.ItemModel(1, getText('Enum_EmpInsOutOrder_DEPARTMENT_EMPLOYEE')),
                new model.ItemModel(2, getText('Enum_EmpInsOutOrder_EMPLOYEE_CODE')),
                new model.ItemModel(3, getText('Enum_EmpInsOutOrder_EMPLOYEE'))
            ]);

            self.officeCls = ko.observableArray([
                new model.ItemModel(1, getText('Enum_OfficeCls_OUTPUT_COMPANY')),
                new model.ItemModel(0, getText('Enum_OfficeCls_OUPUT_LABOR_OFFICE')),
                new model.ItemModel(2, getText('Enum_OfficeCls_DO_NOT_OUTPUT'))
            ]);

            self.officeClsTxt = ko.observableArray([
                new model.ItemModel(1, getText('Enum_OfficeCls_OUTPUT_COMPANY')),
                new model.ItemModel(0, getText('Enum_OfficeCls_OUPUT_LABOR_OFFICE'))
            ]);

            self.submitNameCls = ko.observableArray([
                new model.ItemModel(0, getText("QUI004_A222_18")),
                new model.ItemModel(1, getText("QUI004_A222_19"))
            ]);

            self.printCfg = ko.observableArray([
                new model.ItemModel(1, getText("Enum_PrinfCtg_PRINT")),
                new model.ItemModel(0, getText("Enum_prinfCtg_DO_NOT_PRINT"))
            ]);

            self.lineFeedCodeCls = ko.observableArray([
                new model.ItemModel(0, getText("QUI004_A222_37")),
                new model.ItemModel(1, getText("QUI004_A222_38")),
                new model.ItemModel(2, getText("QUI004_A222_39"))
            ]);

            let today = new Date();
            let start = new Date();
            start.setMonth(start.getMonth() - 1);
            start.setDate(start.getDate() + 1);
            let mmStart = start.getMonth() + 1;
            let dStart = start.getDate();
            let mmEnd = today.getMonth() + 1;
            let dEnd = today.getDate();
            let yyyyS = start.getFullYear();
            let yyyyE = today.getFullYear();
            self.startDate(yyyyS + "/" + mmStart + "/" + dStart);
            self.endDate(yyyyE + "/" + mmEnd + "/" + dEnd);
            self.filingDate(yyyyE + "/" + mmEnd + "/" + dEnd);

            self.startDate.subscribe((data) => {
                if (nts.uk.util.isNullOrEmpty(data)) {
                    return;
                }
                self.startDateJp("(" + nts.uk.time.dateInJapanEmpire(moment.utc(data).format("YYYYMMDD")).toString() + ")");
            });

            self.endDate.subscribe((data) => {
                if (nts.uk.util.isNullOrEmpty(data)) {
                    return;
                }
                self.endDateJp("(" + nts.uk.time.dateInJapanEmpire(moment.utc(data).format("YYYYMMDD")).toString() + ")");
            });

            self.filingDate.subscribe((data) => {
                if (nts.uk.util.isNullOrEmpty(data)) {
                    return;
                }
                self.filingDateJp(" (" + nts.uk.time.dateInJapanEmpire(moment.utc(data).format("YYYYMMDD")).toString() + ")");
            });


            self.loadKCP005();
            self.loadCCG001();
            self.initScreen();
        }

        exportPDF() {
            let self = this;
            if (self.validate()) {
                return;
            }
            let listEmployeeId = self.getListEmpId(self.selectedCode(), self.employeeList()).map(i => i.id);

            let data: any = {
                empInsReportSettingCommand: {
                    submitNameAtr: self.empInsReportSetting().submitNameAtr(),
                    outputOrderAtr: self.empInsReportSetting().outputOrderAtr(),
                    officeClsAtr: self.empInsReportSetting().officeClsAtr(),
                    myNumberClsAtr: self.empInsReportSetting().myNumberClsAtr(),
                    nameChangeClsAtr: self.empInsReportSetting().nameChangeClsAtr()
                },
                empInsReportTxtSettingCommand: {
                    officeAtr: self.empInsReportTxtSetting().officeAtr(),
                    fdNumber: self.empInsReportTxtSetting().fdNumber(),
                    lineFeedCode: self.empInsReportTxtSetting().lineFeedCode()
                },
                employeeIds: listEmployeeId,
                startDate: moment.utc(self.startDate(), "YYYY/MM/DD"),
                endDate: moment.utc(self.endDate(), "YYYY/MM/DD"),
                fillingDate: moment.utc(self.filingDate(), "YYYY/MM/DD")
            };
            nts.uk.ui.block.grayout();
            service.exportFilePDF(data).done()
                .fail(function(error) {
                    nts.uk.ui.dialog.alertError(error);
                }).always(() => {
                    nts.uk.ui.block.clear();
                });
        }

        exportCSV() {
            let self = this;
            if (self.validate()) {
                return;
            }
            let dfd = $.Deferred();
            let listEmployeeId = self.getListEmpId(self.selectedCode(), self.employeeList()).map(i => i.id);

            let data: any = {
                empInsReportSettingCommand: {
                    submitNameAtr: self.empInsReportSetting().submitNameAtr(),
                    outputOrderAtr: self.empInsReportSetting().outputOrderAtr(),
                    officeClsAtr: self.empInsReportSetting().officeClsAtr(),
                    myNumberClsAtr: self.empInsReportSetting().myNumberClsAtr(),
                    nameChangeClsAtr: self.empInsReportSetting().nameChangeClsAtr()
                },
                empInsReportTxtSettingCommand: {
                    officeAtr: self.empInsReportTxtSetting().officeAtr(),
                    fdNumber: self.empInsReportTxtSetting().fdNumber(),
                    lineFeedCode: self.empInsReportTxtSetting().lineFeedCode()
                },
                employeeIds: listEmployeeId,
                startDate: moment.utc(self.startDate(), "YYYY/MM/DD"),
                endDate: moment.utc(self.endDate(), "YYYY/MM/DD"),
                fillingDate: moment.utc(self.filingDate(), "YYYY/MM/DD")
            };
            nts.uk.ui.block.grayout();
            service.exportFileCSV(data)
                .fail((error) => {
                    dialog.alertError(error);
                    dfd.reject();
                }).always(() => {
                    nts.uk.ui.block.clear();
                });
        }

        openCScreen() {
            let self = this;
            let params = {
                employeeList: self.getListEmpId(self.selectedCode(), self.employeeList())
            };
            setShared("QUI004_PARAMS_A", params);
            modal("/view/qui/004/c/index.xhtml");
        }

        initScreen(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();

            $.when(service.getReportSetting(), service.getReportTxtSetting())
                .done((reportSetting: IEmpInsReportSetting, reportTxtSetting: IEmpInsReportTxtSetting) => {
                    if (reportSetting && reportTxtSetting) {
                        this.screenMode = ScreenMode.UPDATE_MODE;
                        self.empInsReportSetting(new EmpInsReportSetting(reportSetting));
                        self.empInsReportTxtSetting(new EmpInsReportTxtSetting(reportTxtSetting));
                    } else {
                        this.screenMode = ScreenMode.NEW_MODE;
                    }
                    dfd.resolve();
                }).fail(function(result) {
                    dialog.alertError(result.errorMessage);
                    dfd.reject();
                });
            return dfd.promise();
        }

        loadKCP005() {
            let self = this;
            self.baseDate = ko.observable(new Date());
            self.multiSelectedCode = ko.observableArray(['0', '1', '4']);
            self.isShowAlreadySet = ko.observable(false);
            self.alreadySettingList = ko.observableArray([
                { code: '1', isAlreadySetting: true },
                { code: '2', isAlreadySetting: true }
            ]);
            self.isDialog = ko.observable(true);
            self.isShowNoSelectRow = ko.observable(false);
            self.isMultiSelect = ko.observable(true);
            self.isShowWorkPlaceName = ko.observable(true);
            self.isShowSelectAllButton = ko.observable(false);
            self.disableSelection = ko.observable(false);

            self.listComponentOption = {
                isShowAlreadySet: self.isShowAlreadySet(),
                isMultiSelect: self.isMultiSelect(),
                listType: ListType.EMPLOYEE,
                employeeInputList: self.employeeList,
                selectType: SelectType.SELECT_ALL,
                selectedCode: self.selectedCode,
                isDialog: self.isDialog(),
                isShowNoSelectRow: self.isShowNoSelectRow(),
                alreadySettingList: self.alreadySettingList,
                isShowWorkPlaceName: self.isShowWorkPlaceName(),
                isShowSelectAllButton: self.isShowSelectAllButton(),
                disableSelection: self.disableSelection(),
                maxRows: 14
            };
            $('#component-items-list').ntsListComponent(self.listComponentOption);
        }

        loadCCG001() {
            let self = this;
            self.ccg001ComponentOption = {
                /** Common properties */
                showEmployeeSelection: false,
                showQuickSearchTab: true,
                showAdvancedSearchTab: true,
                showBaseDate: false,
                showClosure: false,
                showAllClosure: true,
                showPeriod: false,
                periodFormatYM: false,
                tabindex: 7,
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
                showSameWorkplace: false,
                showSameWorkplaceAndChild: false,

                /** Advanced search properties */
                showEmployment: true,
                showWorkplace: true,
                showClassification: true,
                showDepartment: true,
                showJobTitle: true,
                showWorktype: false,
                isMutipleCheck: true,
                /**
                 * Self-defined function: Return data from CCG001
                 * @param: data: the data return from CCG001
                 */
                returnDataFromCcg001: function(data: Ccg001ReturnedData) {
                    self.employeeList(self.setEmployee(data.listEmployee));
                    self.loadKCP005();
                }
            };

            $('#com-ccg001').ntsGroupComponent(self.ccg001ComponentOption);

        }

        setEmployee(item) {
            let listEmployee = [];
            _.each(item, (item) => {
                let employee: Employee = new Employee(item.employeeId,
                    item.employeeCode,
                    item.employeeName,
                    item.workplaceName);
                listEmployee.push(employee);
            });
            return listEmployee;
        }

        getListEmpId(empCode: Array<string>, listEmp: Array<any>) {
            let listEmpId = [];
            _.each(empCode, (item) => {
                let emp = _.find(listEmp, function(itemEmp) {
                    return itemEmp.code == item;
                });
                listEmpId.push(emp);
            });
            return listEmpId;
        }

        startDateStyle() {
            let self = this;
            return self.startDateJp().length > 13 ? "width:130px; display: inline-block;" : "width:130px; display:inline";
        }
        endDateStyle() {
            let self = this;
            return self.endDateJp().length > 13 ? "width:130px; display: inline-block;" : "width:130px; display:inline";
        }

        validate() {
            errors.clearAll();
            $(".nts-input").trigger("validate");
            return errors.hasError();
        }
    }

    export interface GroupOption {
        /** Common properties */
        showEmployeeSelection?: boolean; // ???????????????
        showQuickSearchTab?: boolean; // ??????????????????
        showAdvancedSearchTab?: boolean; // ????????????
        showBaseDate?: boolean; // ???????????????
        showClosure?: boolean; // ?????????????????????
        showAllClosure?: boolean; // ???????????????
        showPeriod?: boolean; // ??????????????????
        periodFormatYM?: boolean; // ??????????????????
        isInDialog?: boolean;
        tabindex: number;

        /** Required parameter */
        baseDate?: string; // ?????????
        periodStartDate?: string; // ?????????????????????
        periodEndDate?: string; // ?????????????????????
        inService: boolean; // ????????????
        leaveOfAbsence: boolean; // ????????????
        closed: boolean; // ????????????
        retirement: boolean; // ????????????

        /** Quick search tab options */
        showAllReferableEmployee?: boolean; // ??????????????????????????????
        showOnlyMe?: boolean; // ????????????
        showSameWorkplace?: boolean; // ?????????????????????
        showSameWorkplaceAndChild?: boolean; // ????????????????????????????????????

        /** Advanced search properties */
        showEmployment?: boolean; // ????????????
        showWorkplace?: boolean; // ????????????
        showClassification?: boolean; // ????????????
        showJobTitle?: boolean; // ????????????
        showDepartment?: boolean;
        showWorktype?: boolean; // ????????????
        isMutipleCheck?: boolean; // ???????????????
        isTab2Lazy?: boolean;

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
        baseDate: string; // ?????????
        closureId?: number; // ??????ID
        periodStart: string; // ?????????????????????)
        periodEnd: string; // ????????????????????????
        listEmployee: Array<EmployeeSearchDto>; // ????????????
    }

    /*kcp005*/
    export class ListType {
        static EMPLOYMENT = 1;
        static Classification = 2;
        static JOB_TITLE = 3;
        static EMPLOYEE = 4;
    }

    export interface UnitModel {
        id?: string;
        code: string;
        name: string;
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

    export class Employee {
        id: string;
        code: string;
        name: string;
        workplaceName: string;

        constructor(employeeId: string, employeeCode: string, employeeName: string, workplaceName: string) {
            this.id = employeeId;
            this.code = employeeCode;
            this.name = employeeName;
            this.workplaceName = workplaceName;
        }
    }

    export interface IEmpInsReportSetting {
        submitNameAtr: number;
        outputOrderAtr: number;
        officeClsAtr: number;
        myNumberClsAtr: number;
        nameChangeClsAtr: number;
    }

    export class EmpInsReportSetting {
        submitNameAtr: KnockoutObservable<number>;
        outputOrderAtr: KnockoutObservable<number>;
        officeClsAtr: KnockoutObservable<number>;
        myNumberClsAtr: KnockoutObservable<number>;
        nameChangeClsAtr: KnockoutObservable<number>;

        constructor(params: IEmpInsReportSetting) {
            this.submitNameAtr = ko.observable(params.submitNameAtr);
            this.outputOrderAtr = ko.observable(params.outputOrderAtr);
            this.officeClsAtr = ko.observable(params.officeClsAtr);
            this.myNumberClsAtr = ko.observable(params.myNumberClsAtr);
            this.nameChangeClsAtr = ko.observable(params.nameChangeClsAtr);
        }
    }

    export interface IEmpInsReportTxtSetting {
        officeAtr: number;
        fdNumber: number;
        lineFeedCode: number;
    }

    export class EmpInsReportTxtSetting {
        officeAtr: KnockoutObservable<number>;
        fdNumber: KnockoutObservable<number>;
        lineFeedCode: KnockoutObservable<number>;
        constructor(params: IEmpInsReportTxtSetting) {
            this.officeAtr = ko.observable(params.officeAtr);
            this.fdNumber = ko.observable(params.fdNumber);
            this.lineFeedCode = ko.observable(params.lineFeedCode);
        }
    }

    export class ScreenMode {
        static NEW_MODE = 1;
        static UPDATE_MODE = 2;
    }
}