module nts.uk.at.ksm008.b {

    @bean()
    export class KSM008BViewModel extends ko.ViewModel {
        // CCG001
        ccg001ComponentOption: GroupOption;

        // KCP005 start
        listComponentOption: any;
        baseDate = ko.observable(new Date());
        selectedCode = ko.observable('1');
        multiSelectedCode = ko.observableArray(['0', '1', '4']);
        isShowAlreadySet = ko.observable(true);
        alreadySettingList = ko.observableArray([
            {code: '1', isAlreadySetting: true},
            {code: '2', isAlreadySetting: true}
        ]);
        isDialog = ko.observable(false);
        isShowNoSelectRow = ko.observable(false);
        isMultiSelect = ko.observable(false);
        isShowWorkPlaceName = ko.observable(false);
        isShowSelectAllButton = ko.observable(false);
        disableSelection = ko.observable(false);

        // KCP005 end
        // KCP005 start
        componentOption: any;
        // KCP005 end

        API = {
            initscreen: 'screen/at/ksm/008/getinit'
        }
        constructor(params: any) {
            super();
            this.declareCCG001();
            this.declareKCP005();
        }

        created() {
            const vm = this;
            $('#com-ccg001').ntsGroupComponent(vm.ccg001ComponentOption);
            $('#kcp005-component').ntsListComponent(vm.listComponentOption);
            $('#kcp005-select').ntsListComponent(vm.componentOption);
        }
        
        mounted() {
        }


        declareCCG001() {
            const vm = this;
            vm.ccg001ComponentOption = {
                /** Common properties */
                systemType: 1,
                showEmployeeSelection: true,
                showQuickSearchTab: true,
                showAdvancedSearchTab: true,
                showBaseDate: true,
                showClosure: true,
                showAllClosure: true,
                showPeriod: true,
                periodFormatYM: false,

                /** Required parameter */
                baseDate: new Date(),
                periodStartDate: new Date(),
                periodEndDate: new Date(),
                inService: true,
                leaveOfAbsence: true,
                closed: true,
                retirement: true,

                /** Quick search tab options */
                showAllReferableEmployee: true,
                showOnlyMe: true,
                showSameDepartment: true,
                showSameDepartmentAndChild: true,
                showSameWorkplace: true,
                showSameWorkplaceAndChild: true,

                /** Advanced search properties */
                showEmployment: true,
                showDepartment: true,
                showWorkplace: true,
                showClassification: true,
                showJobTitle: true,
                showWorktype: true,
                isMutipleCheck: true,

                /**
                 * vm-defined function: Return data from CCG001
                 * @param: data: the data return from CCG001
                 */
                returnDataFromCcg001: function (data: Ccg001ReturnedData) {
                    vm.applyKCP005ContentSearch(data.listEmployee);
                }
            }
        }

        public applyKCP005ContentSearch(dataList: EmployeeSearchDto[]): void {
            const vm = this;
            const employeeSearchs: UnitModel[] = [];
            for (var employeeSearch of dataList) {
                var employee: UnitModel = {
                    code: employeeSearch.employeeCode,
                    name: employeeSearch.employeeName,
                };
                employeeSearchs.push(employee);
            }
            vm.listComponentOption.employeeInputList(employeeSearchs);
        }

        declareKCP005() {
            const vm = this;
            vm.listComponentOption = {
                isShowAlreadySet: vm.isShowAlreadySet(),
                isMultiSelect: vm.isMultiSelect(),
                listType: ListType.EMPLOYEE,
                employeeInputList: ko.observableArray<UnitModel>([
                    {id: '1a', code: '1', name: 'Angela Babykasjgdkajsghdkahskdhaksdhasd', workplaceName: 'HN'},
                    {id: '2b', code: '2', name: 'Xuan Toc Doaslkdhasklhdlashdhlashdl', workplaceName: 'HN'},
                    {id: '3c', code: '3', name: 'Park Shin Hye', workplaceName: 'HCM'},
                    {id: '3d', code: '4', name: 'Vladimir Nabokov', workplaceName: 'HN'}
                ]),
                selectType: SelectType.SELECT_BY_SELECTED_CODE,
                selectedCode: vm.selectedCode,
                isDialog: vm.isDialog(),
                isShowNoSelectRow: vm.isShowNoSelectRow(),
                alreadySettingList: vm.alreadySettingList,
                isShowWorkPlaceName: vm.isShowWorkPlaceName(),
                isShowSelectAllButton: vm.isShowSelectAllButton(),
                disableSelection: vm.disableSelection(),
                maxRows: 15
            }
            vm.componentOption = {
                isShowAlreadySet: false,
                isMultiSelect: vm.isMultiSelect(),
                listType: ListType.EMPLOYEE,
                employeeInputList: ko.observableArray<UnitModel>([
                    {id: '1a', code: '1', name: 'Angela Babykasjgdkajsghdkahskdhaksdhasd', workplaceName: 'HN'},
                    {id: '2b', code: '2', name: 'Xuan Toc Doaslkdhasklhdlashdhlashdl', workplaceName: 'HN'},
                    {id: '3c', code: '3', name: 'Park Shin Hye', workplaceName: 'HCM'},
                    {id: '3d', code: '4', name: 'Vladimir Nabokov', workplaceName: 'HN'}
                ]),
                selectType: SelectType.NO_SELECT,
                selectedCode: vm.selectedCode,
                isDialog: vm.isDialog(),
                isShowNoSelectRow: vm.isShowNoSelectRow(),
                alreadySettingList: vm.alreadySettingList,
                isShowWorkPlaceName: vm.isShowWorkPlaceName(),
                isShowSelectAllButton: vm.isShowSelectAllButton(),
                disableSelection: vm.disableSelection()
            }
        }

        openDialogCDL009() {
            const vm = this;
            const params = {selectedIds: [], isMultiple: false, baseDate: new Date(), target: TargetClassification.WORKPLACE};
            vm.$window
                .storage('CDL009Params', params)
                .then(() => vm.$window.modal('com', '/view/cdl/009/a/index.xhtml'))
                .then(() => vm.$window.storage('CDL009Output'))
                .then((data: string | string[]) => {
                    // if (data != '') {
                    //     vm.employee.employeeId(ko.toJS(data));




                    // }
                });
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

    export interface Ccg001ReturnedData {
        baseDate: string; // 基準日
        closureId?: number; // 締めID
        periodStart: string; // 対象期間（開始)
        periodEnd: string; // 対象期間（終了）
        listEmployee: Array<EmployeeSearchDto>; // 検索結果
    }

    export interface EmployeeSearchDto {
        employeeId: string;
        employeeCode: string;
        employeeName: string;
        workplaceId: string;
        workplaceName: string;
    }

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

    /**
     * Class TargetClassification
     */
    export class TargetClassification {
        static WORKPLACE = 1;
        static DEPARTMENT = 2;
    }

}