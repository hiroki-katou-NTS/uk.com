module nts.uk.pr.view.qsi013.a.viewmodel {
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import errors = nts.uk.ui.errors;
    import block = nts.uk.ui.block;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import model = nts.uk.pr.view.qsi013.share.model;

    export class ScreenModel {
        ccg001ComponentOption: GroupOption;
        startDate: KnockoutObservable<string> = ko.observable('');
        startDateJp: KnockoutObservable<string> = ko.observable('');
        endDate: KnockoutObservable<string> = ko.observable('');
        endDateJp: KnockoutObservable<string> = ko.observable('');
        filingDate: KnockoutObservable<string> = ko.observable('');
        filingDateJp: KnockoutObservable<string> = ko.observable('');
        selectedRuleCode: KnockoutObservable<string> = ko.observable('0');

        officeInformations: KnockoutObservableArray<ItemModel> = ko.observableArray(getBusinessDivision());
        businessArrSymbols: KnockoutObservableArray<ItemModel> = ko.observableArray(getBussEsimateClass());
        outputOrders: KnockoutObservableArray<ItemModel> = ko.observableArray(getSocialInsurOutOrder());
        printPersonNumbers: KnockoutObservableArray<ItemModel> = ko.observableArray(getPersonalNumClass());
        submittedNames: KnockoutObservableArray<ItemModel> = ko.observableArray(getSubNameClass());
        insuredNumbers: KnockoutObservableArray<ItemModel> = ko.observableArray(getInsurPersonNumDivision());
        textPersonNumbers: KnockoutObservableArray<ItemModel> = ko.observableArray(getTextPerNumberClass());
        outputFormats: KnockoutObservableArray<ItemModel> = ko.observableArray(getOutputFormatClass());
        lineFeedCodes: KnockoutObservableArray<ItemModel> = ko.observableArray(getLineFeedCode());


        simpleValue: KnockoutObservable<string> = ko.observable('');
        columns: KnockoutObservableArray<any> = ko.observableArray();
        items: KnockoutObservableArray<any> = ko.observableArray();
        currentCodeList: KnockoutObservableArray<any> = ko.observableArray();
        /* kcp005 */
        baseDate: any;
        listComponentOption: any;
        selectedCode: KnockoutObservable<string>;
        multiSelectedCode: KnockoutObservableArray<string>;
        isShowAlreadySet: KnockoutObservable<boolean>;
        alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
        isDialog: KnockoutObservable<boolean>;
        isShowNoSelectRow: KnockoutObservable<boolean>;
        isMultiSelect: KnockoutObservable<boolean>;
        isShowWorkPlaceName: KnockoutObservable<boolean>;
        isShowSelectAllButton: KnockoutObservable<boolean>;
        disableSelection : KnockoutObservable<boolean>;
        employeeList: KnockoutObservableArray<UnitModel>;

        socInsurNotiCreSet : KnockoutObservable<SocInsurNotiCreSet> = ko.observable(new SocInsurNotiCreSet({
                officeInformation: 0,
                printPersonNumber: 0,
                businessArrSymbol: 0,
                outputOrder: 0,
                submittedName: 0,
                insuredNumber: 0,
                fdNumber: null,
                textPersonNumber: 0,
                outputFormat: 0,
                lineFeedCode: 0
                }));
        constructor() {
            let self = this;
            this.loadKCP005();
            this.loadCCG001();

            this.columns = ko.observableArray([
                { headerText: 'コード', key: 'id', width: 100, hidden: true },
                { headerText: '名称', key: 'code', width: 150},
                { headerText: '説明', key: 'businessName', width: 150 },
                { headerText: '説明1', key: 'workplaceName', width: 150}
            ]);

            self.startDate.subscribe((data) =>{
                self.startDateJp(" (" + nts.uk.time.dateInJapanEmpire(data) + ")");
            });

            self.endDate.subscribe((data) =>{
                self.endDateJp(" (" + nts.uk.time.dateInJapanEmpire(data) + ")");
            });

            self.filingDate.subscribe((data)=>{
                self.filingDateJp(" (" + nts.uk.time.dateInJapanEmpire(data) + ")");
            });
        }

        initScreen(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            block.invisible();

            service.getSocialInsurNotiCreateSet().done(function(data: ISocInsurNotiCreSet) {
                if(data != null) {
                    self.socInsurNotiCreSet(data);
                }
            }).fail(function(result) {
                dialog.alertError(result.errorMessage);
                dfd.reject();
            });
            block.clear();
            return dfd.promise();
        }

        openBScreen() {
            modal("/view/qsi/013/b/index.xhtml").onClosed(() => {

            });
        }

        loadKCP005(){
            let self = this;
            self.baseDate = ko.observable(new Date());
            self.selectedCode = ko.observable('1');
            self.multiSelectedCode = ko.observableArray(['0', '1', '4']);
            self.isShowAlreadySet = ko.observable(false);
            self.alreadySettingList = ko.observableArray([
                {code: '1', isAlreadySetting: true},
                {code: '2', isAlreadySetting: true}
            ]);
            self.isDialog = ko.observable(false);
            self.isShowNoSelectRow = ko.observable(false);
            self.isMultiSelect = ko.observable(true);
            self.isShowWorkPlaceName = ko.observable(false);
            self.isShowSelectAllButton = ko.observable(false);
            self.disableSelection = ko.observable(false);

            this.employeeList = ko.observableArray<UnitModel>([
                { code: '1', name: 'Angela Baby', workplaceName: 'HN' },
                { code: '2', name: 'Xuan Toc Do', workplaceName: 'HN' },
                { code: '3', name: 'Park Shin Hye', workplaceName: 'HCM' },
                { code: '4', name: 'Vladimir Nabokov', workplaceName: 'HN' }
            ]);
            self.listComponentOption = {
                isShowAlreadySet: self.isShowAlreadySet(),
                isMultiSelect: self.isMultiSelect(),
                listType: ListType.EMPLOYEE,
                employeeInputList: self.employeeList,
                selectType: SelectType.SELECT_BY_SELECTED_CODE,
                selectedCode: self.selectedCode,
                isDialog: self.isDialog(),
                isShowNoSelectRow: self.isShowNoSelectRow(),
                alreadySettingList: self.alreadySettingList,
                isShowWorkPlaceName: self.isShowWorkPlaceName(),
                isShowSelectAllButton: self.isShowSelectAllButton(),
                disableSelection : self.disableSelection()
            };
            $('#component-items-list').ntsListComponent(self.listComponentOption);
        }

        exportFile(exportPDF: any): void {
            let self = this;
            let data: any = {
                socialInsurNotiCreateSet: {
                    officeInformation: self.socInsurNotiCreSet().officeInformation(),
                    fdNumber: self.socInsurNotiCreSet().fdNumber(),
                    printPersonNumber: self.socInsurNotiCreSet().printPersonNumber(),
                    businessArrSymbol: self.socInsurNotiCreSet().businessArrSymbol(),
                    outputOrder: self.socInsurNotiCreSet().outputOrder(),
                    submittedName: self.socInsurNotiCreSet().submittedName(),
                    insuredNumber: self.socInsurNotiCreSet().insuredNumber(),
                    fdNumber: self.socInsurNotiCreSet().fdNumber(),
                    textPersonNumber: self.socInsurNotiCreSet().textPersonNumber(),
                    outputFormat: self.socInsurNotiCreSet().outputFormat(),
                    lineFeedCode: self.socInsurNotiCreSet().lineFeedCode()
                },
                startDate: moment.utc(self.startDate(), "YYYY/MM/DD"),
                endDate: moment.utc(self.endDate(), "YYYY/MM/DD")
            };

            if(exportPDF == 0) {
                nts.uk.ui.block.grayout();
                service.exportFilePDF(data).done(function() {
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error);
                }).always(function() {
                    nts.uk.ui.block.clear();
                });
            }

            if(exportPDF == 1) {
                nts.uk.ui.block.grayout();
                service.exportFileCSV(data).done(function() {
                }).fail(function(error) {
                nts.uk.ui.dialog.alertError(error);
                }).always(function() {
                nts.uk.ui.block.clear();
                });
            }

        }

        loadCCG001(){
            let self = this;
            self.ccg001ComponentOption = {
                /** Common properties */
                showEmployeeSelection: true,
                showQuickSearchTab: false,
                showAdvancedSearchTab: true,
                showBaseDate: false,
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
                showJobTitle: true,
                showWorktype: true,
                isMutipleCheck: true,
                /**
                 * Self-defined function: Return data from CCG001
                 * @param: data: the data return from CCG001
                 */
                returnDataFromCcg001: function(data: Ccg001ReturnedData) {
                    self.employeeInputList(self.employeeList(data.listEmployee));
                }
            }

            $('#com-ccg001').ntsGroupComponent(self.ccg001ComponentOption);

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
        code: string;
        name?: string;
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
    export function getBusinessDivision(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('Enum_BusinessDivision_OUTPUT_COMPANY_NAME')),
            new ItemModel(1, getText('ENUM_BusinessDivision_OUTPUT_SIC_INSURES')),
            new ItemModel(2, getText('ENUM_BusinessDivision_DO_NOT_OUTPUT'))
        ];
    }
    export function getBussEsimateClass(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('Enum_BussEsimateClass_HEAL_INSUR_OFF_ARR_SYMBOL')),
            new ItemModel(1, getText('Enum_BussEsimateClass_EMPEN_ESTAB_REARSIGN'))
        ];
    }
    export function getSocialInsurOutOrder(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('Enum_SocialInsurOutOrder_HEAL_INSUR_NUMBER_ORDER')),
            new ItemModel(1, getText('Enum_SocialInsurOutOrder_WELF_AREPEN_NUMBER_ORDER')),
            new ItemModel(2, getText('Enum_SocialInsurOutOrder_HEAL_INSUR_NUMBER_UNION_ORDER')),
            new ItemModel(3, getText('Enum_SocialInsurOutOrder_ORDER_BY_FUND')),
            new ItemModel(4, getText('Enum_SocialInsurOutOrder_HEAL_INSUR_OFF_ARR_SYMBOL')),
            new ItemModel(5, getText('Enum_SocialInsurOutOrder_EMPLOYEE_CODE_ORDER')),
            new ItemModel(6, getText('Enum_SocialInsurOutOrder_EMPLOYEE_KANA_ORDER')),
            new ItemModel(7, getText('Enum_SocialInsurOutOrder_INSURED_PER_NUMBER_ORDER'))
        ];
    }

    export function getPersonalNumClass(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('Enum_PersonalNumClass_OUTPUT_PER_NUMBER')),
            new ItemModel(1, getText('Enum_PersonalNumClass_OUTPUT_BASIC_PER_NUMBER')),
            new ItemModel(2, getText('Enum_PersonalNumClass_OUTPUT_BASIC_PEN_NOPER')),
            new ItemModel(3, getText('Enum_PersonalNumClass_DO_NOT_OUTPUT'))
        ];
    }

    export function getSubNameClass(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('Enum_SubNameClass_PERSONAL_NAME')),
            new ItemModel(1, getText('Enum_SubNameClass_REPORTED_NAME'))
        ];
    }


    export function getInsurPersonNumDivision(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('Enum_InsurPersonNumDivision_DO_NOT_OUPUT')),
            new ItemModel(1, getText('Enum_InsurPersonNumDivision_OUTPUT_HEAL_INSUR_NUM')),
            new ItemModel(2, getText('Enum_InsurPersonNumDivision_OUTPUT_THE_WELF_PENNUMBER')),
            new ItemModel(3, getText('Enum_InsurPersonNumDivision_OUTPUT_HEAL_INSUR_UNION')),
            new ItemModel(4, getText('Enum_InsurPersonNumDivision_OUTPUT_THE_FUN_MEMBER'))
        ];
    }

    export function getTextPerNumberClass(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('Enum_TextPerNumberClass_OUTPUT_NUMBER')),
            new ItemModel(1, getText('Enum_TextPerNumberClass_OUPUT_BASIC_PEN_NUMBER')),
            new ItemModel(2, getText('Enum_TextPerNumberClass_OUTPUT_BASIC_NO_PERSONAL'))
        ];
    }

    export function getOutputFormatClass(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('Enum_OutputFormatClass_PEN_OFFICE')),
            new ItemModel(1, getText('Enum_OutputFormatClass_HEAL_INSUR_ASSO')),
            new ItemModel(2, getText('Enum_OutputFormatClass_OUTPUT_THE_WELF_PEN'))
        ];
    }

    export function getLineFeedCode(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('Enum_LineFeedCode_ADD')),
            new ItemModel(1, getText('Enum_LineFeedCode_DO_NOT_ADD')),
            new ItemModel(2, getText('Enum_LineFeedCode_E_GOV'))
        ];
    }

    export class ItemModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    export interface ISocInsurNotiCreSet {
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
    }

    export class SocInsurNotiCreSet {
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
    }

}
