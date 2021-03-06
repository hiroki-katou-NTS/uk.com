module nts.uk.pr.view.qsi003.a.viewmodel {
    import model = nts.uk.pr.view.qsi003.share.model;
    import dialog = nts.uk.ui.dialog;
    import setShared = nts.uk.ui.windows.setShared;
    import modal = nts.uk.ui.windows.sub.modal;
    export class ScreenModel {
        ccg001ComponentOption: GroupOption;
        employeeInputList: KnockoutObservableArray<EmployeeModel>;
        constraint: string = 'LayoutCode';

        //kcp009
        systemReference: KnockoutObservable<number>;
        isDisplayOrganizationName: KnockoutObservable<boolean>;
        targetBtnText: string;
        baseDate: KnockoutObservable<Date>;
        /*listComponentOption: ComponentOption;*/
        selectedItem: KnockoutObservable<string>;
        selectedCode: KnockoutObservable<string>  = ko.observable('');
        tabindex: number;
        //
        //switch
        simpleValue: KnockoutObservable<string>;
        roundingRules: KnockoutObservableArray<any>;
        personTarget: any;
        //datepicker
        date: KnockoutObservable<string> = ko.observable('');
        datePicker: KnockoutObservable<string>  = ko.observable('');
        //combobox
        itemList: KnockoutObservableArray<ItemModel>;
        addressOutputClass: KnockoutObservable<number> = ko.observable(0);
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;

        //start kcp 005
        listComponentOption: any;
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

        //get data enum
        notificationTarget: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNotificationTarget());
        addressOutClass: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getAddressOutClass());

        constructor() {
            let self = this;
            self.date.subscribe((data)=>{
                if(data) {
                    self.datePicker(" (" + nts.uk.time.dateInJapanEmpire(moment.utc(data).format("YYYYMMDD")).toString() + ")");
                }
            });
            this.getRomajiNameNoti();
            let today = new Date();
            let dd = today.getDate();
            let ms = today.getMonth()+1;
            let yyyy = today.getFullYear();
            self.date(yyyy + "/" + ms + "/" + dd);
            this.loadCCG001();
            this.loadKCP005();
            //init switch
            self.personTarget = ko.observable(0);
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);
        }

        openBScreen() {
            let self = this;
            let params = {
                employeeList: self.getListEmpId(self.selectedCode(), self.employeeList())
            };
            setShared("QSI003_PARAMS_B", params);
            modal("/view/qsi/003/b/index.xhtml");
        }

        getRomajiNameNoti(){
            var self = this;
            nts.uk.pr.view.qsi003.a.service.getRomajiNameNotiCreSettingById().done(function (data: any) {
                if(data){
                    self.addressOutputClass(data.addressOutputClass);
                }
            });
        }

        getListEmpId(empCode: Array, listEmp: Array){
            let listEmpId =[];
            _.each(empCode, (item) =>{
                let emp = _.find(listEmp, function(itemEmp) { return itemEmp.code == item; });
                listEmpId.push(emp);
            });
            return listEmpId;
        }

        getListEmpIds(empCode: Array, listEmp: Array){
            let listEmpId =[];
            _.each(empCode, (item) =>{
                let emp = _.find(listEmp, function(itemEmp) { return itemEmp.code == item; });
                listEmpId.push(emp.id);
            });
            return listEmpId;
        }

        exportData(){
            var self = this;
            let romajiNameNotiCreSetCommand: any = {
                date : moment.utc(self.date(),"YYYY/MM/DD" ),
                personTarget : self.personTarget(),
                addressOutputClass : self.addressOutputClass(),
                empIds: self.getListEmpIds(self.selectedCode(), self.employeeList())
            };
            nts.uk.ui.block.grayout();
            nts.uk.pr.view.qsi003.a.service.exportData(romajiNameNotiCreSetCommand).done( function() {

            }).fail(error => {
                dialog.alertError(error);
            }).always(function() {
                nts.uk.ui.block.clear();
            });
        }

        /*KCP005*/
        loadKCP005(){
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
                disableSelection : self.disableSelection(),
                maxRows: 16
            };
            $('#component-items-list').ntsListComponent(self.listComponentOption);
        }

        /* CCG001 */
        loadCCG001(){
            let self = this;
            self.ccg001ComponentOption = {
                /** Common properties */
                systemType: 1,
                showEmployeeSelection: false,
                showQuickSearchTab: true,
                showAdvancedSearchTab: true,
                showBaseDate: true,
                showClosure: false,
                showAllClosure: false,
                showPeriod: false,
                periodFormatYM: false,
                tabindex: 5,
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
            }

            $('#com-ccg001').ntsGroupComponent(self.ccg001ComponentOption);

        }

        setEmployee(item){
            let listEmployee = [];
            _.each(item, (item) => {
                let employee: Employee = new Employee(
                    item.employeeId,
                    item.employeeCode,
                    item.employeeName);
                listEmployee.push(employee);
            });
            return listEmployee;
        }

        createEmployeeModel(data){
            let self = this;
            let listEmployee = [];
            _.each(data, data => {
                listEmployee.push({
                    id: data.employeeId,
                    code: data.employeeCode,
                    name: data.employeeName
                });

            });

            return listEmployee;
        }
    }

    export class Employee {
        id: string;
        code: string;
        name: string;

        constructor(employeeId: string, employeeCode: string, employeeName: string) {
            this.id = employeeId;
            this.code = employeeCode;
            this.name = employeeName;
        }
    }
    // Note: Defining these interfaces are optional
    export interface GroupOption {
        /** Common properties */
        showEmployeeSelection?: boolean; // ???????????????
        systemType: number; // ??????????????????
        showQuickSearchTab?: boolean; // ??????????????????
        showAdvancedSearchTab?: boolean; // ????????????
        showBaseDate?: boolean; // ???????????????
        showClosure?: boolean; // ?????????????????????
        showAllClosure?: boolean; // ???????????????
        showPeriod?: boolean; // ??????????????????
        periodFormatYM?: boolean; // ??????????????????
        maxPeriodRange?: string; // ????????????
        showSort?: boolean; // ???????????????
        nameType?: number; // ???????????????

        /** Required parameter */
        baseDate?: any; // ????????? KnockoutObservable<string> or string
        periodStartDate?: any; // ????????????????????? KnockoutObservable<string> or string
        periodEndDate?: any; // ????????????????????? KnockoutObservable<string> or string
        dateRangePickerValue?: KnockoutObservable<any>;
        inService: boolean; // ????????????
        leaveOfAbsence: boolean; // ????????????
        closed: boolean; // ????????????
        retirement: boolean; // ????????????

        /** Quick search tab options */
        showAllReferableEmployee?: boolean; // ??????????????????????????????
        showOnlyMe?: boolean; // ????????????
        showSameDepartment?: boolean; //?????????????????????
        showSameDepartmentAndChild?: boolean; // ????????????????????????????????????
        showSameWorkplace?: boolean; // ?????????????????????
        showSameWorkplaceAndChild?: boolean; // ????????????????????????????????????

        /** Advanced search properties */
        showEmployment?: boolean; // ????????????
        showDepartment?: boolean; // ????????????
        showWorkplace?: boolean; // ????????????
        showClassification?: boolean; // ????????????
        showJobTitle?: boolean; // ????????????
        showWorktype?: boolean; // ????????????
        isMutipleCheck?: boolean; // ???????????????

        /** Optional properties */
        isInDialog?: boolean;
        showOnStart?: boolean;
        isTab2Lazy?: boolean;
        tabindex?: number;

        /** Data returned */
        returnDataFromCcg001: (data: Ccg001ReturnedData) => void;
    }
    export interface EmployeeSearchDto {
        employeeId: string;
        employeeCode: string;
        employeeName: string; // departmentName or workplaceName based on system type
    }
    export interface Ccg001ReturnedData {
        baseDate: string; // ?????????
        closureId?: number; // ??????ID
        periodStart: string; // ?????????????????????)
        periodEnd: string; // ????????????????????????
        listEmployee: Array<EmployeeSearchDto>; // ????????????
    }

   /* export interface ComponentOption {
        systemReference: SystemType;
        isDisplayOrganizationName: boolean;
        employeeInputList: KnockoutObservableArray<EmployeeSearchDto>;
        targetBtnText: string;
        selectedItem: KnockoutObservable<string>;
        tabIndex: number;
        /!*baseDate?: KnockoutObservable<Date>;*!/
    }*/
    export interface EmployeeModel {
        id: string;
        code: string;
        businessName: string;
    }
    export class SystemType {
        static SELECT_BY_SELECTED_CODE = 1;
        static SELECT_ALL = 2;
        static SELECT_FIRST_ITEM = 3;
        static NO_SELECT = 4;
    }
    export class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    export class ListType {
        static EMPLOYMENT = 1;
        static Classification = 2;
        static JOB_TITLE = 3;
        static EMPLOYEE = 4;
    }
    export interface UnitAlreadySettingModel {
        code: string;
        isAlreadySetting: boolean;
    }

    export class SelectType {
        static SELECT_BY_SELECTED_CODE = 1;
        static SELECT_ALL = 2;
        static SELECT_FIRST_ITEM = 3;
        static NO_SELECT = 4;
    }
    export interface UnitModel {
        id: string;
        code: string;
        name: string;
        workplaceName?: string;
        isAlreadySetting?: boolean;
    }
}