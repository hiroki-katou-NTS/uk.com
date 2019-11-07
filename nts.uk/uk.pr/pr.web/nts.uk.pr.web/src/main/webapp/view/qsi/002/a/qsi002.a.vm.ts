module nts.uk.pr.view.qsi002.a.viewmodel {

    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    export class ScreenModel {
        ccg001ComponentOption: GroupOption;
        employeeInputList: KnockoutObservableArray<EmployeeModel>;

        //kcp009
        systemReference: KnockoutObservable<number>;
        isDisplayOrganizationName: KnockoutObservable<boolean>;
        targetBtnText: string;
        baseDate: KnockoutObservable<Date>;
        listComponentOption: ComponentOption;
        selectedItem: KnockoutObservable<string>;
        tabindex: number;
        //
        //switch
        simpleValue: KnockoutObservable<string>;
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        //datepicker
        baseDate1: KnockoutObservable<moment.Moment>;
        japanBaseDate: KnockoutObservable<string>;
        //combobox
        itemList: KnockoutObservableArray<ItemModel>;
        selectedCode: KnockoutObservable<string>;

        listEmployee: KnockoutObservableArray<any>;



        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        //grid
        columns: KnockoutObservableArray<any>;
        items: KnockoutObservableArray<ItemModelGrid>;
        currentCodeList: KnockoutObservableArray<any>;

        socialInsurOutOrder: KnockoutObservableArray<ItemModel>;
        selectedSocialInsurOutOrder: KnockoutObservable<string>;
        insurPersonNumDivision: KnockoutObservableArray<ItemModel>;
        selectedInsurPersonNumDivision: KnockoutObservable<string>;
        bussEsimateClass: KnockoutObservableArray<ItemModel>;
        selectedBussEsimateClass: KnockoutObservable<string>;
        businessDivision: KnockoutObservableArray<ItemModel>;
        selectedBusinessDivision:  KnockoutObservable<string>;
        personalNumClass: KnockoutObservableArray<ItemModel>;
        selectedPersonalNumClass: KnockoutObservable<string>;


        //kcp005

        listComponentOptionKCP005: any;
        selectedCodeKCP005: KnockoutObservable<string>  = ko.observable('');
        multiSelectedCode: KnockoutObservableArray<string>;
        isShowAlreadySet: KnockoutObservable<boolean>;
        alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
        isDialog: KnockoutObservable<boolean>;
        isShowNoSelectRow: KnockoutObservable<boolean>;
        isMultiSelect: KnockoutObservable<boolean>;
        isShowWorkPlaceName: KnockoutObservable<boolean>;
        isShowSelectAllButton: KnockoutObservable<boolean>;
        disableSelection : KnockoutObservable<boolean>;

        employeeListKCP005: KnockoutObservableArray<UnitModel>;
        employees: any;

        constructor() {
            block.invisible();
            let self = this;
            self.loadCCG001();
            //init switch
            self.simpleValue = ko.observable("123");
            self.roundingRules = ko.observableArray([
                { code: '0', name: nts.uk.resource.getText('QSI002_A222_10') },
                { code: '1', name: nts.uk.resource.getText('QSI002_A222_11') }
            ]);
            self.selectedRuleCode = ko.observable(0);
            //init datepicker
            self.baseDate1 = ko.observable(moment());

            self.japanBaseDate = ko.observable("("+nts.uk.time.dateInJapanEmpire(moment.utc(self.baseDate1()).format("YYYYMMDD")).toString()+")");
            self.baseDate1.subscribe(e => {
                self.japanBaseDate("("+nts.uk.time.dateInJapanEmpire(moment.utc(self.baseDate1()).format("YYYYMMDD")).toString()+")");
            });
            //init combobox
            self.itemList = ko.observableArray([
                new ItemModel('1', '基本給'),
                new ItemModel('2', '役職手当'),
                new ItemModel('3', '基本給ながい文字列ながい文字列ながい文字列')
            ]);

            self.socialInsurOutOrder = ko.observableArray(getSocialInsurOutOrder());
            self.insurPersonNumDivision = ko.observableArray(getInsurPersonNumDivision());
            self.bussEsimateClass = ko.observableArray(getBussEsimateClass());
            self.businessDivision = ko.observableArray(getBusinessDivision());
            self.personalNumClass = ko.observableArray(getPersonalNumClass());

            self.selectedSocialInsurOutOrder = ko.observable('0');
            self.selectedInsurPersonNumDivision = ko.observable('0');
            self.selectedBussEsimateClass =ko.observable('0');
            self.selectedBusinessDivision = ko.observable('0');
            self.selectedPersonalNumClass = ko.observable('0');

            self.selectedCode = ko.observable('1');
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);
            //grid

            this.columns = ko.observableArray([
                { headerText: 'コード', key: 'id', width: 100, hidden: true },
                { headerText: '名称', key: 'code', width: 150},
                { headerText: '説明', key: 'businessName', width: 150 },
                { headerText: '説明1', key: 'workplaceName', width: 150}
            ]);

            this.items = ko.observableArray([]);

            this.currentCodeList = ko.observableArray([]);

            self.listEmployee = ko.observableArray([]);


            //loadpage
            self.loadPage();
            self.loadKCP005();

            block.clear();
        }

        //KCP005
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

            self.employeeListKCP005 = self.listEmployee;
            self.listComponentOptionKCP005 = {
                isShowAlreadySet: self.isShowAlreadySet(),
                isMultiSelect: self.isMultiSelect(),
                listType: ListType.EMPLOYEE,
                employeeInputList: self.employeeListKCP005,
                selectType: SelectType.SELECT_ALL,
                selectedCode: self.selectedCodeKCP005,
                isDialog: self.isDialog(),
                isShowNoSelectRow: self.isShowNoSelectRow(),
                alreadySettingList: self.alreadySettingList,
                isShowWorkPlaceName: self.isShowWorkPlaceName(),
                isShowSelectAllButton: self.isShowSelectAllButton(),
                disableSelection : self.disableSelection(),
                showOptionalColumn: false,
                optionalColumnName: nts.uk.resource.getText('KSM005_18'),
                optionalColumnDatasource: ko.observableArray([]),
                maxRows: 16
            };

            $('#kcp005').ntsListComponent(self.listComponentOptionKCP005);
        }

        loadPage(){
            let self = this;
            block.invisible();


            service.getSocialInsurNotiCreateSetById().done(e =>{
                if(e){
                    self.selectedBusinessDivision(e.officeInformation);
                    self.selectedBussEsimateClass(e.businessArrSymbol);
                    self.selectedSocialInsurOutOrder(e.outputOrder);
                    self.selectedPersonalNumClass(e.printPersonNumber);
                    self.selectedInsurPersonNumDivision(e.insuredNumber);
                    self.selectedRuleCode(e.submittedName);
                }

                let data: any = {
                    officeInformation: self.selectedBusinessDivision(),
                    businessArrSymbol: self.selectedBussEsimateClass(),
                    outputOrder: self.selectedSocialInsurOutOrder(),
                    printPersonNumber: self.selectedPersonalNumClass(),
                    insuredNumber: self.selectedInsurPersonNumDivision(),
                    submittedName: self.selectedRuleCode(),
                    fdNumber: e.fdNumber,
                    textPersonNumber: e.textPersonNumber,
                    outputFormat: e.outputFormat,
                    lineFeedCode: e.lineFeedCode
                };

                service.index(data).done(e =>{
                    block.clear()
                }).fail(e =>{
                    block.clear()
                });
            }).fail(e =>{

            });

            block.clear()
        }

        exportPDF(){
            let self = this;
            block.invisible();
            let data: any;
            service.getSocialInsurNotiCreateSetById().done(e =>{
                if(e) {
                    data = {
                        socialInsurNotiCreateSetDto: new SocialInsurNotiCreateSetDto(
                            Number(self.selectedBusinessDivision()),
                            Number(self.selectedBussEsimateClass()),
                            Number(self.selectedSocialInsurOutOrder()),
                            Number(self.selectedPersonalNumClass()),
                            self.selectedRuleCode(),
                            Number(self.selectedInsurPersonNumDivision()),
                            e.fdNumber,
                            e.textPersonNumber,
                            e.outputFormat,
                            e.lineFeedCode
                        ),
                        listEmpId: self.getListEmployee(self.selectedCodeKCP005(),self.employees),
                        date: moment.utc(self.baseDate1(), "YYYY/MM/DD")
                    };
                } else {
                    data = {
                        socialInsurNotiCreateSetDto: new SocialInsurNotiCreateSetDto(
                            Number(self.selectedBusinessDivision()),
                            Number(self.selectedBussEsimateClass()),
                            Number(self.selectedSocialInsurOutOrder()),
                            Number(self.selectedPersonalNumClass()),
                            self.selectedRuleCode(),
                            Number(self.selectedInsurPersonNumDivision())
                        ),
                        listEmpId: self.getListEmployee(self.selectedCodeKCP005(),self.employees),
                        date: moment.utc(self.baseDate1(), "YYYY/MM/DD")
                    };
                }

                service.exportPDF(data).done(e =>{

                }).fail(e =>{
                    if(e){
                        nts.uk.ui.dialog.alertError(e);
                    }
                }).always(e =>{
                    block.clear();
                });


            }).fail(e =>{

            });


        }

        openScreenB(){
            nts.uk.ui.windows.sub.modal("/view/qsi/002/b/index.xhtml").onClosed( ()=> {

            });
        }

        getListEmployee(empCode: Array, listEmp: Array){
            let listEmployee: any = [];
            _.each(empCode, (item) =>{
                let emp = _.find(listEmp, function(itemEmp) { return itemEmp.employeeCode === item; });
                listEmployee.push(emp.employeeId);
            });
            return listEmployee;
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
                tabindex: 4,
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
                    self.createGridList(data.listEmployee);
                    self.employees = data.listEmployee;
                    self.loadKCP005();

                }
            }

            $('#com-ccg001').ntsGroupComponent(self.ccg001ComponentOption);

        }

        createGridList(data) {
            let self = this;
            self.listEmployee([]);
            _.each(data, data => {
                self.listEmployee.push(new UnitModel(data.employeeCode, data.employeeName, data.workplaceName,false));
            });

        }

        createEmployeeModel(data){
            let self = this;
            let listEmployee = [];
            _.each(data, data => {
                listEmployee.push({
                    id: data.employeeId,
                    code: data.employeeCode,
                    businessName: data.employeeName,
                    workplaceName: data.workplaceName
                });

            });

            return listEmployee;
        }

    }

    class UnitModel {
        code: string;
        name?: string;
        workplaceName?: string;
        isAlreadySetting?: boolean;

        constructor(code:string, name: string, workplaceName: string, isAlreadySetting: boolean){
            this.code = code;
            this.name = name;
            this.workplaceName = workplaceName;
            this.isAlreadySetting = isAlreadySetting;
        }
    }
    // Note: Defining these interfaces are optional
    export interface GroupOption {
        /** Common properties */
        showEmployeeSelection?: boolean; // 検索タイプ
        systemType: number; // システム区分
        showQuickSearchTab?: boolean; // クイック検索
        showAdvancedSearchTab?: boolean; // 詳細検索
        showBaseDate?: boolean; // 基準日利用
        showClosure?: boolean; // 就業締め日利用
        showAllClosure?: boolean; // 全締め表示
        showPeriod?: boolean; // 対象期間利用
        periodFormatYM?: boolean; // 対象期間精度
        maxPeriodRange?: string; // 最長期間
        showSort?: boolean; // 並び順利用
        nameType?: number; // 氏名の種類

        /** Required parameter */
        baseDate?: any; // 基準日 KnockoutObservable<string> or string
        periodStartDate?: any; // 対象期間開始日 KnockoutObservable<string> or string
        periodEndDate?: any; // 対象期間終了日 KnockoutObservable<string> or string
        dateRangePickerValue?: KnockoutObservable<any>;
        inService: boolean; // 在職区分
        leaveOfAbsence: boolean; // 休職区分
        closed: boolean; // 休業区分
        retirement: boolean; // 退職区分

        /** Quick search tab options */
        showAllReferableEmployee?: boolean; // 参照可能な社員すべて
        showOnlyMe?: boolean; // 自分だけ
        showSameDepartment?: boolean; //同じ部門の社員
        showSameDepartmentAndChild?: boolean; // 同じ部門とその配下の社員
        showSameWorkplace?: boolean; // 同じ職場の社員
        showSameWorkplaceAndChild?: boolean; // 同じ職場とその配下の社員

        /** Advanced search properties */
        showEmployment?: boolean; // 雇用条件
        showDepartment?: boolean; // 部門条件
        showWorkplace?: boolean; // 職場条件
        showClassification?: boolean; // 分類条件
        showJobTitle?: boolean; // 職位条件
        showWorktype?: boolean; // 勤種条件
        isMutipleCheck?: boolean; // 選択モード

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
        employeeName: string;
        affiliationId: string; // departmentId or workplaceId based on system type
        affiliationName: string; // departmentName or workplaceName based on system type
    }
    export interface Ccg001ReturnedData {
        baseDate: string; // 基準日
        closureId?: number; // 締めID
        periodStart: string; // 対象期間（開始)
        periodEnd: string; // 対象期間（終了）
        listEmployee: Array<EmployeeSearchDto>; // 検索結果
    }

    export interface ComponentOption {
        systemReference: SystemType;
        isDisplayOrganizationName: boolean;
        employeeInputList: KnockoutObservableArray<EmployeeModel>;
        targetBtnText: string;
        selectedItem: KnockoutObservable<string>;
        tabIndex: number;
        baseDate?: KnockoutObservable<Date>;
    }
    export interface EmployeeModel {
        id: string;
        code: string;
        businessName: string;
        depName?: string;
        workplaceName?: string;
    }
    export class SystemType {
        static EMPLOYMENT = 1;
        static SALARY = 2;
        static PERSONNEL = 3;
        static ACCOUNTING = 4;
        static OH = 6;
    }
    export class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    //社会保険出力順

    export function getSocialInsurOutOrder(): Array<ItemModel> {
        return [
            new ItemModel('0', getText('Enum_SocialInsurOutOrder_DIVISION_EMP_ORDER')),
            new ItemModel('1', getText('Enum_SocialInsurOutOrder_EMPLOYEE_CODE_ORDER')),
            new ItemModel('2', getText('Enum_SocialInsurOutOrder_EMPLOYEE_KANA_ORDER')),
            new ItemModel('3', getText('Enum_SocialInsurOutOrder_HEAL_INSUR_NUMBER_ORDER')),
            new ItemModel('4', getText('Enum_SocialInsurOutOrder_WELF_AREPEN_NUMBER_ORDER')),
            new ItemModel('5', getText('Enum_SocialInsurOutOrder_HEAL_INSUR_NUMBER_UNION_ORDER')),
            new ItemModel('6', getText('Enum_SocialInsurOutOrder_ORDER_BY_FUND')),
            new ItemModel('7', getText('Enum_SocialInsurOutOrder_INSURED_PER_NUMBER_ORDER'))
        ];
    }

    //被保険者整理番号区分

    export function getInsurPersonNumDivision(): Array<ItemModel> {
        return [
            new ItemModel('0', getText('Enum_InsurPersonNumDivision_DO_NOT_OUPUT')),
            new ItemModel('1', getText('Enum_InsurPersonNumDivision_OUTPUT_HEAL_INSUR_NUM')),
            new ItemModel('2', getText('Enum_InsurPersonNumDivision_OUTPUT_THE_WELF_PENNUMBER')),
            new ItemModel('3', getText('Enum_InsurPersonNumDivision_OUTPUT_HEAL_INSUR_UNION')),
            new ItemModel('4', getText('Enum_InsurPersonNumDivision_OUTPUT_THE_FUN_MEMBER'))
        ];
    }

    //事業所整理記号区分
    export function getBussEsimateClass(): Array<ItemModel> {
        return [
            new ItemModel('0', getText('Enum_BussEsimateClass_HEAL_INSUR_OFF_ARR_SYMBOL')),
            new ItemModel('1', getText('Enum_BussEsimateClass_EMPEN_ESTAB_REARSIGN'))
        ];
    }


    export function getBusinessDivision(): Array<ItemModel> {
        return [
            new ItemModel('0', getText('Enum_BusinessDivision_OUTPUT_COMPANY_NAME')),
            new ItemModel('1', getText('Enum_BusinessDivision_OUTPUT_SIC_INSURES')),
            new ItemModel('2', getText('Enum_BusinessDivision_DO_NOT_OUTPUT')),
            new ItemModel('3', getText('Enum_BusinessDivision_DO_NOT_OUTPUT_BUSINESS'))
        ];
    }

    export function getPersonalNumClass(): Array<ItemModel> {
        return [
            new ItemModel('0', getText('Enum_PersonalNumClass_OUTPUT_PER_NUMBER')),
            new ItemModel('1', getText('Enum_PersonalNumClass_OUTPUT_BASIC_PER_NUMBER')),
            new ItemModel('2', getText('Enum_PersonalNumClass_OUTPUT_BASIC_PEN_NOPER')),
            new ItemModel('3', getText('Enum_PersonalNumClass_DO_NOT_OUTPUT'))
        ];
    }

    class ItemModelGrid {
        id: string;
        code: string;
        businessName: string;
        workplaceName: string;
        constructor(id: string, code: string, businessName: string, workplaceName: string) {
            this.id = id;
            this.code = code;
            this.businessName = businessName;
            this.workplaceName = workplaceName;
        }
    }

    export class SocialInsurNotiCreateSetDto{
        /**
         * 事業所情報
         */
        officeInformation: number;

        /**
         * 事業所整理記号
         */
        businessArrSymbol: number;

        /**
         * 出力順
         */
        outputOrder: number;
        /**
         * 印刷個人番号
         */
        printPersonNumber: number;
        /**
         * 提出氏名区分
         */
        submittedName: number;

        /**
         * 被保険者整理番号
         */
        insuredNumber: number;

        /**
         * FD番号
         */
        fdNumber: number;

        /**
         * テキスト個人番号
         */
        textPersonNumber: number;

        /**
         * 出力形式
         */
        outputFormat: number;

        /**
         * 改行コード
         */
        lineFeedCode: number;

        constructor(officeInformation: number,
                    businessArrSymbol: number,
                    outputOrder: number,
                    printPersonNumber: number,
                    submittedName: number,
                    insuredNumber: number,
                    fdNumber: number,
                    textPersonNumber: number,
                    outputFormat: number,
                    lineFeedCode: number){

            this.officeInformation = officeInformation;
            this.businessArrSymbol = businessArrSymbol;
            this.outputOrder = outputOrder;
            this.printPersonNumber = printPersonNumber;
            this.submittedName = submittedName;
            this.insuredNumber = insuredNumber;
            this.fdNumber = fdNumber;
            this.textPersonNumber = textPersonNumber;
            this.outputFormat = outputFormat;
            this.lineFeedCode = lineFeedCode;


        }
    }

    //CKP005

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

    export interface UnitAlreadySettingModel {
        code: string;
        isAlreadySetting: boolean;
    }

}