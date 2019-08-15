module nts.uk.pr.view.qsi013.a.viewmodel {
    import service = nts.uk.pr.view.qsi013.a.service;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import errors = nts.uk.ui.errors;
    import block = nts.uk.ui.block;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        ccg001ComponentOption: GroupOption;
        startDate: KnockoutObservable<string> = ko.observable('');
        startDateJp: KnockoutObservable<string> = ko.observable('');
        endDate: KnockoutObservable<string> = ko.observable('');
        endDateJp: KnockoutObservable<string> = ko.observable('');
        filingDate: KnockoutObservable<string> = ko.observable('');
        filingDateJp: KnockoutObservable<string> = ko.observable('');
        selectedRuleCode: KnockoutObservable<string> = ko.observable('0');
        rules: KnockoutObservableArray<ItemModel> = ko.observableArray(getRule());
        simpleValue: KnockoutObservable<string> = ko.observable('');
        columns: KnockoutObservableArray<any> = ko.observableArray();
        items: KnockoutObservableArray<any> = ko.observableArray();
        currentCodeList: KnockoutObservableArray<any> = ko.observableArray();
        socInsurNotiCreSet : KnockoutObservable<SocInsurNotiCreSet> = ko.observable(new SocInsurNotiCreSet({
                officeInformation: 0,
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

    export function getRule(): Array<ItemModel> {
        return [
            new ItemModel('0', getText('QSI013_18')),
            new ItemModel('1', getText('QSI013_19'))
        ];
    }

    export class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    export interface ISocInsurNotiCreSet {
        officeInformation: number;
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
