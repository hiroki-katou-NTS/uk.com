/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kaf021.a {
    import textFormat = nts.uk.text.format;

    const API = {
        INIT: 'screen/at/kaf021/init',
    };

    @bean()
    class ViewModel extends ko.ViewModel {
        ccg001ComponentOption: GroupOption = null;
        empSearchItems: Array<EmployeeSearchDto> = [];

        appTypes: Array<AppType> = [];
        appTypeSelected: KnockoutObservable<AppTypeEnum> = ko.observable(null);

        items: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedCode: KnockoutObservable<string> = ko.observable("");


        API = {
            getListWorkCycleDto: 'screen/at/ksm003/a/get',
        };

        constructor() {
            super();
            const vm = this;
            vm.ccg001ComponentOption = <GroupOption>{
                /** Common properties */
                systemType: 1,
                showEmployeeSelection: false,
                showQuickSearchTab: true,
                showAdvancedSearchTab: true,
                showBaseDate: true,
                showClosure: true,
                showAllClosure: false,
                showPeriod: false,
                periodFormatYM: true,
                maxPeriodRange: 'oneMonth',

                /** Required parameter */
                baseDate: moment.utc().toISOString(),
                periodStartDate: null,
                periodEndDate: null,
                inService: true,
                leaveOfAbsence: true,
                closed: true,
                retirement: false,

                /** Quick search tab options */
                showAllReferableEmployee: true,
                showOnlyMe: true,
                showSameWorkplace: true,
                showSameWorkplaceAndChild: true,

                /** Advanced search properties */
                showEmployment: true,
                showDepartment: false,
                showWorkplace: true,
                showClassification: true,
                showJobTitle: true,
                showWorktype: false,
                isMutipleCheck: true,
                //tabindex: 6,
                showOnStart: false,

                /**
                 * Self-defined function: Return data from CCG001
                 * @param: data: the data return from CCG001
                 */
                returnDataFromCcg001: function (data: Ccg001ReturnedData) {
                    vm.empSearchItems = data.listEmployee;
                    vm.fetchData();
                }
            }

            vm.appTypes.push(new AppType(AppTypeEnum.CURRENT_MONTH, textFormat("{0}月度", 1)));
            vm.appTypes.push(new AppType(AppTypeEnum.NEXT_MONTH,  textFormat("{0}月度", 2)));
            vm.appTypes.push(new AppType(AppTypeEnum.YEARLY, this.$i18n("KAF021_4")));
        }

        created() {
            const vm = this;
            $('#com-ccg001').ntsGroupComponent(vm.ccg001ComponentOption);
            //vm.loadMGrid();
            vm.initData();

            vm.appTypeSelected.subscribe((value: AppTypeEnum) => {
                console.log(value)
            })
        }

        mounted() {
            const vm = this;

            
        }

        initData(): JQueryPromise<any> {
            const vm = this,
            dfd = $.Deferred();

            vm.$ajax(API.INIT, (data: any) => {
                console.log(data)
            })

            dfd.resolve();
            return dfd.promise();
        }

        fetchData(): JQueryPromise<any> {
            const vm = this,
                dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        nextScreen(){

        }
    }

    interface GroupOption {
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

    interface Ccg001ReturnedData {
        baseDate: string; // 基準日
        closureId?: number; // 締めID
        periodStart: string; // 対象期間（開始)
        periodEnd: string; // 対象期間（終了）
        listEmployee: Array<EmployeeSearchDto>; // 検索結果
    }

    interface EmployeeSearchDto {
        employeeId: string;
        employeeCode: string;
        employeeName: string;
        affiliationCode: string;
        affiliationId: string;
        affiliationName: string;
    }

    class AppType{
        value: AppTypeEnum;
        name: string;

        constructor (value: AppTypeEnum, name: string){
            this.value = value;
            this.name = name;
        }
    }

    enum AppTypeEnum{
        CURRENT_MONTH = 1,
        NEXT_MONTH = 2,
        YEARLY = 3
    }
}
