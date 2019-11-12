module nts.uk.pr.view.qui004.a.viewmodel {

    import dialog = nts.uk.ui.dialog;
    import model = nts.uk.pr.view.qui004.share.model;

    export class ScreenModel {

        item_list: KnockoutObservableArray<model.ItemModel>;
        item_list_one: KnockoutObservableArray<model.ItemModel>;
        item_list_two: KnockoutObservableArray<model.ItemModel>;
        item_list_three: KnockoutObservableArray<model.ItemModel>;

        screenMode: KnockoutObservable<model.SCREEN_MODE> = ko.observable(null);
        ccg001ComponentOption: GroupOption;
        startDate: KnockoutObservable<string> = ko.observable('');
        startDateJp: KnockoutObservable<string> = ko.observable('');
        endDate: KnockoutObservable<string> = ko.observable('');
        endDateJp: KnockoutObservable<string> = ko.observable('');
        filingDate: KnockoutObservable<string> = ko.observable('');
        filingDateJp: KnockoutObservable<string> = ko.observable('');

        submittedNames: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getSubNameClass());
        outputOrders: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getSocialInsurOutOrder());
        changedName: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getChangedName());
        newLine:  KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNewLine());
        selectedCode: KnockoutObservable<any> = ko.observable(1);
        submittedName: KnockoutObservable<any> = ko.observable(0);

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

        /*socInsurNotiCreSet : KnockoutObservable<SocInsurNotiCreSet> = ko.observable(new SocInsurNotiCreSet({
            officeInformation: 0,
            printPersonNumber: 3,
            businessArrSymbol: 0,
            outputOrder: 1,
            submittedName: 0,
            insuredNumber: 0,
            fdNumber: null,
            textPersonNumber: 0,
            outputFormat: 0,
            lineFeedCode: 0
        }));
*/
        constructor() {
            let self = this;

            self.item_list = ko.observableArray([
                new model.ItemModel(0, '基本給'),
                new model.ItemModel(1, '役職手当')
            ]);

            self.item_list_one = ko.observableArray([
                new model.ItemModel(0, '個人名'),
                new model.ItemModel(1, '届出氏名')
            ]);

            self.item_list_two = ko.observableArray([
                new model.ItemModel(0, '印字する'),
                new model.ItemModel(1, '印字しない')
            ]);

            self.item_list_three = ko.observableArray([
                new model.ItemModel(0, '付加する'),
                new model.ItemModel(1, '付加しない'),
                new model.ItemModel(2, 'e-Gov')
            ]);

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

            self.filingDate.subscribe((data)=>{
                if(nts.uk.util.isNullOrEmpty(data)){
                    return;
                }
                self.filingDateJp(" (" + nts.uk.time.dateInJapanEmpire(data) + ")");
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
            self.filingDate(yyyyE + "/" + mmEnd  + "/" + dEnd);
            self.loadKCP005();
            self.loadCCG001();

            self.initScreen();
        }

        initScreen(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();

            /*service.getSocialInsurNotiCreateSet().done(function(data: ISocInsurNotiCreSet) {
                if(data != null) {
                    self.socInsurNotiCreSet().officeInformation(data.officeInformation);
                    self.socInsurNotiCreSet().printPersonNumber(data.printPersonNumber);
                    self.socInsurNotiCreSet().businessArrSymbol(data.businessArrSymbol);
                    self.socInsurNotiCreSet().outputOrder(data.outputOrder);
                    self.socInsurNotiCreSet().submittedName(data.submittedName);
                    self.socInsurNotiCreSet().insuredNumber(data.insuredNumber);
                    self.socInsurNotiCreSet().fdNumber(data.fdNumber == null ? "0" : data.fdNumber);
                    self.socInsurNotiCreSet().textPersonNumber(data.textPersonNumber == null ? 0 : data.textPersonNumber);
                    self.socInsurNotiCreSet().outputFormat(data.outputFormat == null ? 0 : data.outputFormat);
                    self.socInsurNotiCreSet().lineFeedCode(data.lineFeedCode == null ? 0 : data.lineFeedCode);
                    self.screenMode(model.SCREEN_MODE.UPDATE);
                } else {
                    self.screenMode(model.SCREEN_MODE.NEW);
                }
            }).fail(function(result) {
                dialog.alertError(result.errorMessage);
                dfd.reject();
            });
*/
            dfd.resolve();
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
            return self.startDateJp().length > 13 ?  "width:140px; display: inline-block;" : "width:140px; display:inline";
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

    /*export interface ISocInsurNotiCreSet {
        officeInformation: number;
        printPersonNumber: number;
        businessArrSymbol: number;
        outputOrder: number;
        submittedName: number;
        insuredNumber: number;
        fdNumber: string;
        textPersonNumber:number;
        outputFormat: number;
        lineFeedCode: number;
    }*/

    /*export class SocInsurNotiCreSet {
        officeInformation: KnockoutObservable<number>;
        printPersonNumber: KnockoutObservable<number>;
        businessArrSymbol: KnockoutObservable<number>;
        outputOrder: KnockoutObservable<number>;
        submittedName: KnockoutObservable<number>;
        insuredNumber: KnockoutObservable<number>;
        fdNumber: KnockoutObservable<string>;
        textPersonNumber: KnockoutObservable<number>;
        outputFormat: KnockoutObservable<number>;
        lineFeedCode: KnockoutObservable<number>;

        constructor(params: ISocInsurNotiCreSet) {
            this.officeInformation = ko.observable(params.officeInformation);
            this.printPersonNumber = ko.observable(params.printPersonNumber);
            this.businessArrSymbol = ko.observable(params.businessArrSymbol);
            this.outputOrder = ko.observable(params.outputOrder);
            this.submittedName = ko.observable(params.submittedName);
            this.insuredNumber = ko.observable(params.insuredNumber);
            this.fdNumber = ko.observable(params ? params.fdNumber : null);
            this.textPersonNumber = ko.observable(params ? params.textPersonNumber : null);
            this.outputFormat = ko.observable(params ? params.outputFormat : null);
            this.lineFeedCode = ko.observable(params ? params.lineFeedCode : null);
        }
    }*/

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