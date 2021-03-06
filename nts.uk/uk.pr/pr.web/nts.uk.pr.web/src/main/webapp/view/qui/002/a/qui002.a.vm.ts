module nts.uk.pr.view.qui002.a.viewmodel {
    import dialog = nts.uk.ui.dialog;
    import errors = nts.uk.ui.errors;
    import block = nts.uk.ui.block;
    import model = nts.uk.pr.view.qsi013.share.model;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import JapanDateMoment = nts.uk.time.JapanDateMoment;


    export class ScreenModel {
        ccg001ComponentOption: GroupOption;
        startDate: KnockoutObservable<string> = ko.observable('');
        startDateJp: KnockoutObservable<string> = ko.observable('');
        endDate: KnockoutObservable<string> = ko.observable('');
        endDateJp: KnockoutObservable<string> = ko.observable('');
        filingDate: KnockoutObservable<string> = ko.observable('');
        filingDateJp: KnockoutObservable<string> = ko.observable('');

        outputOrders: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getEmpInsOutOrder());
        submitNameAtrs: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getSubmitNameAtr());
        officeClsAtrs: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getOfficeClsAtr());


        /* kcp005 */
        baseDate: any;
        listComponentOption: any;
        selectedCode: KnockoutObservable<string> = ko.observable('');
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
            officeClsAtr: 1,
            myNumberClsAtr: 0,
            nameChangeClsAtr: 0
        }));

        constructor() {
            let self = this;
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
            self.loadKCP005();
            self.loadCCG001();

            self.initScreen();
            $('#A222_14').focus()
        }

        initScreen(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            block.invisible();

            service.start().done(function (data: IEmpInsReportSetting) {
                if (data != null) {
                    self.empInsReportSetting().submitNameAtr(data.submitNameAtr);
                    self.empInsReportSetting().outputOrderAtr(data.outputOrderAtr);
                    self.empInsReportSetting().officeClsAtr(data.officeClsAtr);
                    self.empInsReportSetting().myNumberClsAtr(data.myNumberClsAtr);
                    self.empInsReportSetting().nameChangeClsAtr(data.nameChangeClsAtr);
                }

            }).fail(function (result) {
                dialog.alertError(result.errorMessage);
                dfd.reject();
            });
            block.clear();
            return dfd.promise();
        }

        getStyle() {
            let self = this;
            return self.startDateJp().length > 13 ? "width:140px; display: inline-block;" : "width:140px; display:inline";
        }


        getListEmployee(empCode: Array, listEmp: Array,emChangDate :Array) {
            let listEmployee: any = [];
            _.each(empCode, (item) => {
                let emp = {
                    id : '',
                    code : '',
                    name : '',
                    changeDate : ''
                };
                _.find(listEmp, function (itemEmp) {
                    if(itemEmp.code == item){
                        emp.code = itemEmp.code;
                        emp.id = itemEmp.id;
                        emp.name = itemEmp.name;
                        emp.changeDate = '';
                    }
                });
                _.find(emChangDate, function (itemEmpChangeDate) {
                    if(itemEmpChangeDate.employeeCode == item){
                        emp.changeDate = itemEmpChangeDate.changeDate;
                    }
                });
                listEmployee.push(emp);
            });
            return listEmployee;
        }

        loadKCP005() {
            let self = this;
            self.baseDate = ko.observable(new Date());
            self.multiSelectedCode = ko.observableArray(['0', '1', '4']);
            self.isShowAlreadySet = ko.observable(false);
            self.alreadySettingList = ko.observableArray([
                {code: '1', isAlreadySetting: true},
                {code: '2', isAlreadySetting: true}
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
                disableSelection: self.disableSelection()
            };
            $('#component-items-list').ntsListComponent(self.listComponentOption);
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

        exportFile(): void {

            let self = this;
            if (self.validate()) {
                return;
            }
            let employList = self.getListEmpId(self.selectedCode(), self.employeeList());
            let data: any = {
                empInsReportSettingExport: {
                    submitNameAtr: self.empInsReportSetting().submitNameAtr(),
                    outputOrderAtr: self.empInsReportSetting().outputOrderAtr(),
                    officeClsAtr: self.empInsReportSetting().officeClsAtr(),
                    myNumberClsAtr: self.empInsReportSetting().myNumberClsAtr(),
                    nameChangeClsAtr: self.empInsReportSetting().nameChangeClsAtr()
                },
                empIdChangDate: self.createListEmployyeeChangDate(getShared("QUI002_PARAMS_A"), employList),
                fillingDate: moment.utc(self.filingDate(), "YYYY/MM/DD")
            };
            nts.uk.ui.block.grayout();
            service.exportFilePDF(data).done(function () {
            }).fail(function (error) {
                nts.uk.ui.dialog.alertError(error);
            }).always(function () {
                $('#A222_14').focus();
                nts.uk.ui.block.clear();
            });


        }

        createListEmployyeeChangDate(params: Array, employList: Array) {
            let self = this;
            let listEmployee: any = [];
            _.each(employList, (item) => {
                let emp = _.find(params, function (itemEmp) {
                    return item == itemEmp.employeeId;
                });
                if(emp == undefined || emp == null){
                    listEmployee.push({
                        employeeId : item,
                        changeDate : ""
                    });
                }else{
                    listEmployee.push(emp);
                }

            });
            return listEmployee;
        }

        openScreenB() {
            let self = this;
            let employList = self.getListEmpId(self.selectedCode(), self.employeeList());
            if(employList.length == 0){
                dialog.info({ messageId: "Msg_684" });
                return;
            }
            let params = {
                employeeList: self.getListEmployee(self.selectedCode(), self.employeeList(),getShared("QUI002_PARAMS_A")),
            };
            setShared("QUI002_PARAMS_B", params);
            modal("/view/qui/002/b/index.xhtml");
        }

        getListEmpId(empCode: Array, listEmp: Array) {
            let listEmpId = [];
            _.each(empCode, (item) => {
                let emp = _.find(listEmp, function (itemEmp) {
                    return itemEmp.code == item;
                });
                listEmpId.push(emp.id);
            });
            return listEmpId;
        }

        validate() {
            errors.clearAll();
            $("#A2_4").trigger("validate");
            $("#A2_7").trigger("validate");
            $("#A4_4").trigger("validate");
            return errors.hasError();
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
                tabindex: 3,
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
                returnDataFromCcg001: function (data: Ccg001ReturnedData) {
                    self.employeeList(self.setEmployee(data.listEmployee));
                    self.loadKCP005();
                }
            };

            $('#com-ccg001').ntsGroupComponent(self.ccg001ComponentOption);

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

    //Enum


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

}
