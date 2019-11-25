module nts.uk.pr.view.qui001.a.viewmodel {

    import model = nts.uk.pr.view.qui001.share.model;
    import dialog = nts.uk.ui.dialog;
    import errors = nts.uk.ui.errors;
    import block = nts.uk.ui.block;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import JapanDateMoment = nts.uk.time.JapanDateMoment;

    export class ScreenModel {

        screenMode: KnockoutObservable<model.SCREEN_MODE> = ko.observable(null);
        ccg001ComponentOption: GroupOption;
        startDate: KnockoutObservable<string> = ko.observable('');
        startDateJp: KnockoutObservable<string> = ko.observable('');
        endDate: KnockoutObservable<string> = ko.observable('');
        endDateJp: KnockoutObservable<string> = ko.observable('');
        fillingDate: KnockoutObservable<string> = ko.observable('');
        fillingDateJp: KnockoutObservable<string> = ko.observable('');

        officeInformations: KnockoutObservable<model.ItemModel> = ko.observable(model.getOfficeCls());
        outputOrders: KnockoutObservable<model.ItemModel> = ko.observable(model.getEmpInsOutOrder());
        printPersonNumbers: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.isPrintMyNum());
        submittedNames: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getSubNameClass());
        changedName: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getChangedName());
        newLine:  KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNewLine());

        empInsReportSetting: KnockoutObservable<EmpInsReportSetting> = ko.observable(new EmpInsReportSetting({
            submitNameAtr: 0,
            outputOrderAtr: 0,
            officeClsAtr: 0,
            myNumberClsAtr: 1,
            nameChangeClsAtr: 0
        }));

        empInsRptTxtSetting: KnockoutObservable<EmpInsRptTxtSetting> = ko.observable(new EmpInsRptTxtSetting({
            lineFeedCode: 0,
            officeAtr: 1,
            fdNumber: ""
        }));

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
        disableSelection : KnockoutObservable<boolean>;
        employeeList: KnockoutObservableArray<UnitModel> = ko.observableArray<UnitModel>([]);

        constructor() {
            let self = this;

            self.startDate.subscribe((data) =>{
                if(nts.uk.util.isNullOrEmpty(data)){
                    return;
                }
                self.startDateJp("(" + nts.uk.time.dateInJapanEmpire(data) + ")");
            });

            self.endDate.subscribe((data) =>{
                if(nts.uk.util.isNullOrEmpty(data)){
                    return;
                }
                self.endDateJp("(" + nts.uk.time.dateInJapanEmpire(data) + ")");
            });

            self.fillingDate.subscribe((data)=>{
                if(nts.uk.util.isNullOrEmpty(data)){
                    return;
                }
                self.fillingDateJp(" (" + nts.uk.time.dateInJapanEmpire(data) + ")");
            });

            let today  = new Date();
            let start = new Date();
            start.setMonth(start.getMonth() - 1);
            start.setDate(start.getDate() + 1);
            let mmStart = start.getMonth() + 1;
            let dStart = start.getDate();
            let mmEnd = today.getMonth() + 1;
            let dEnd = today.getDate();
            let yyyyS = start.getFullYear();
            let yyyyE = today.getFullYear();
            self.startDate(yyyyS + "/" +  mmStart + "/" + dStart);
            self.endDate(yyyyE + "/" + mmEnd  + "/" + dEnd);
            self.fillingDate(yyyyE + "/" + mmEnd  + "/" + dEnd);
            self.loadKCP005();
            self.loadCCG001();

            self.initScreen();
        }

        initScreen(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            block.invisible();
            $.when(service.getEmpInsReportSetting(), service.getEmpInsReportTxtSetting())
            .done((setting: IEmpInsReportSetting, txtSetting: IEmpInsRptTxtSetting) => {
                if (setting && txtSetting) {
                    self.empInsReportSetting(new EmpInsReportSetting(setting));
                    self.empInsRptTxtSetting(new EmpInsRptTxtSetting(txtSetting));
                    self.screenMode(model.SCREEN_MODE.UPDATE);
                }
                self.screenMode(model.SCREEN_MODE.NEW);

                dfd.resolve();
            }).fail(function (result) {
                dialog.alertError(result.errorMessage);
                dfd.reject();
            });
            return dfd.promise();
        }

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
                maxRows: 14
            };
            $('#component-items-list').ntsListComponent(self.listComponentOption);
        }

        loadCCG001(){
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
                tabindex: 9,
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

        setEmployee(item){
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

        getStyle(){
            let self = this;
            return self.startDateJp().length > 13 ?  "width:120px; display: inline-block;" : "width:120px; display:inline";
        }

        exportPDF() {
            let self = this;
            if (self.validate()) {
                return;
            }
            let empIds = self.getSelectedEmpIds(self.selectedCode(), self.employeeList());
            let data = {
                empInsReportSetting: ko.toJS(self.empInsReportSetting),
                empInsRptTxtSetting: ko.toJS(self.empInsRptTxtSetting),
                fillingDate: moment.utc(self.fillingDate(), "YYYY/MM/DD"),
                startDate:  moment.utc(self.startDate(), "YYYY/MM/DD"),
                endDate:  moment.utc(self.endDate(), "YYYY/MM/DD"),
                empIds: empIds
            };
            nts.uk.ui.block.grayout();
            service.exportPDF(data).done(() => {
            }).fail((error) => {
                nts.uk.ui.dialog.alertError(error);
            }).always(() => {
                nts.uk.ui.block.clear();
            });
        }
        openCScreen() {
            let self = this;
            let params = {
                employeeList: self.getListEmpId(self.selectedCode(), self.employeeList())
            };
            setShared("QUI001_PARAMS_A", params);
            modal("/view/qui/001/c/index.xhtml");
        }

        getListEmpId(empCode: Array, listEmp: Array){
            let listEmpId =[];
            _.each(empCode, (item) =>{
                let emp = _.find(listEmp, function(itemEmp) { return itemEmp.code == item; });
                listEmpId.push(emp);
            });
            return listEmpId;
        }

        exportCSV() {
            let self = this;
            let empIds = self.getSelectedEmpIds(self.selectedCode(), self.employeeList());
            if(empIds.length == 0) {
                dialog.alertError({ messageId: 'Msg_37' });
                return;
            }
            let data = {
                empInsReportSetting: ko.toJS(self.empInsReportSetting),
                empInsRptTxtSetting: ko.toJS(self.empInsRptTxtSetting),
                fillingDate: moment.utc(self.fillingDate(), "YYYY/MM/DD"),
                startDate:  moment.utc(self.startDate(), "YYYY/MM/DD"),
                endDate:  moment.utc(self.endDate(), "YYYY/MM/DD"),
                empIds: empIds
            };
            nts.uk.ui.block.grayout();
            service.exportCSV(data).done(() => {
            }).fail((error) => {
                nts.uk.ui.dialog.alertError(error);
            }).always(() => {
                nts.uk.ui.block.clear();
            });

        }

        validate() {
            errors.clearAll();
            $(".nts-input").trigger("validate");
            return errors.hasError();
        }

        getSelectedEmpIds(selectedCode: Array, employeeList: Array) {
            return _.map(_.filter(employeeList, e => _.includes(selectedCode, e.code)), e => e.id);
        }
    }

    export interface GroupOption {
        /** Common properties */
        showEmployeeSelection?: boolean; // 検索タイプ
        showQuickSearchTab?: boolean; // クイック検索
        showAdvancedSearchTab?: boolean; // 詳細検索
        showBaseDate?: boolean; // 基準日利用
        showClosure?: boolean; // 就業締め日利用
        showAllClosure?: boolean; // 全締め表示
        showPeriod?: boolean; // 対象期間利用
        periodFormatYM?: boolean; // 対象期間精度
        isInDialog?: boolean;
        tabindex: number;

        /** Required parameter */
        baseDate?: string; // 基準日
        periodStartDate?: string; // 対象期間開始日
        periodEndDate?: string; // 対象期間終了日
        inService: boolean; // 在職区分
        leaveOfAbsence: boolean; // 休職区分
        closed: boolean; // 休業区分
        retirement: boolean; // 退職区分

        /** Quick search tab options */
        showAllReferableEmployee?: boolean; // 参照可能な社員すべて
        showOnlyMe?: boolean; // 自分だけ
        showSameWorkplace?: boolean; // 同じ職場の社員
        showSameWorkplaceAndChild?: boolean; // 同じ職場とその配下の社員

        /** Advanced search properties */
        showEmployment?: boolean; // 雇用条件
        showWorkplace?: boolean; // 職場条件
        showClassification?: boolean; // 分類条件
        showJobTitle?: boolean; // 職位条件
        showDepartment?: boolean;
        showWorktype?: boolean; // 勤種条件
        isMutipleCheck?: boolean; // 選択モード
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
        baseDate: string; // 基準日
        closureId?: number; // 締めID
        periodStart: string; // 対象期間（開始)
        periodEnd: string; // 対象期間（終了）
        listEmployee: Array<EmployeeSearchDto>; // 検索結果
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

    export interface IEmpInsRptTxtSetting {
        lineFeedCode: number,
        officeAtr: number,
        fdNumber: string
    }

    export class EmpInsRptTxtSetting {
        lineFeedCode: KnockoutObservable<number>;
        officeAtr: KnockoutObservable<number>;
        fdNumber: KnockoutObservable<string>;

        constructor(params: IEmpInsRptTxtSetting) {
            this.lineFeedCode =  ko.observable(params.lineFeedCode);
            this.officeAtr =  ko.observable(params.officeAtr);
            this.fdNumber =  ko.observable(params.fdNumber);
        }
    }
}