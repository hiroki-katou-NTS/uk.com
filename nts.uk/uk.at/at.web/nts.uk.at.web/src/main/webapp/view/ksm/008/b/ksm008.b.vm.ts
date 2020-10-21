module nts.uk.at.ksm008.b {

    @bean()
    export class KSM008BViewModel extends ko.ViewModel {
        // CCG001
        ccg001ComponentOption: GroupOption;

        // KCP005 start
        listComponentOption: any;
        baseDate = ko.observable(new Date());
        selectedCode = ko.observable('');
        multiSelectedCode = ko.observableArray([]);
        isShowAlreadySet = ko.observable(true);
        alreadySettingList = ko.observableArray([]);
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

        employeeList: KnockoutObservableArray<UnitModel> = ko.observableArray([]);
        transferCode: string;

        selectedEmployeeName: KnockoutObservable<string> = ko.observable(null);

        alarmCheckSet: KnockoutObservable<string> = ko.observable(null);
        alarmCondition: KnockoutObservableArray<string> = ko.observableArray([]);

        simultanceList: KnockoutObservableArray<UnitModel> = ko.observableArray([]);

        screenMode: any;

        constructor(params: any) {
            super();

            const vm = this;

            if (params) {
                vm.transferCode = params;
            } else {
                vm.transferCode = "01";
            }

            vm.declareCCG001();
            vm.declareKCP005();
            vm.initEmployeeList();
        }

        created() {
            const vm = this;


        }
        
        mounted() {
            const vm = this;

            let param = {
                code: vm.transferCode
            };

            $('#kcp005-component').ntsListComponent(vm.listComponentOption);
            $('#kcp005-select').ntsListComponent(vm.componentOption);
            $('#com-ccg001').ntsGroupComponent(vm.ccg001ComponentOption).done(() => {
                vm.$blockui("grayout");
                vm.$ajax(API.initscreen, param).done((res) => {
                    if (res) {

                        if (res.personInfos.length > 0) {
                            let lstEmployee = _.map(res.personInfos, function (item: any) {
                                return {id: item.employeeID, code: item.employeeCode, name: item.businessName, workplaceName: ''};
                            });
                            vm.employeeList(lstEmployee);
                            vm.selectedCode(lstEmployee[0].code);
                        }

                        let { alarmCheck } = res;

                        if (alarmCheck) {

                            let lstCondition = alarmCheck.explanationList;

                            vm.alarmCheckSet(vm.transferCode + " " + alarmCheck.conditionName);

                            if (lstCondition && lstCondition.length) {
                                vm.alarmCondition(lstCondition);
                            }

                        }
                    }
                }).fail(err => {
                    vm.$dialog.error(err);
                }).always(() => vm.$blockui('clear'));
            });

            vm.selectedCode.subscribe(value => {
                if (value) {
                    let selectedItem = _.filter(vm.employeeList(), function (item) {
                        return item.code == value;
                    });
                    if (selectedItem && selectedItem.length) {
                        vm.selectedCode(selectedItem[0].code);
                        vm.selectedEmployeeName(selectedItem[0].name);

                        vm.getListSimultaneous(selectedItem[0].id);
                    }
                }
            });

            vm.employeeList.subscribe(value => {
               if (value && value.length) {
                   let lstSid = _.map(value, i => i.id);
                   vm.$blockui("grayout");
                   vm.$ajax(API.checkSimultaneousSet, {sids : lstSid}).done(res => {
                      if (res) {
                          let lstAlreadySet = _.map(res, function (item: any) {
                              let currentCode = _.filter(value, i => i.id == item.sid);
                              if (currentCode && currentCode.length) {
                                  return {code: currentCode[0].code, isAlreadySetting: item.display};
                              }
                          });
                          vm.alreadySettingList(lstAlreadySet);
                      }
                   }).fail(err => {
                       vm.$dialog.error(err);
                   }).always(() => vm.$blockui('clear'));
               }
            });
        }
        
        removeSelectedListSimultaneous() {
            const vm = this;

            let lstSimultance = _.clone(vm.simultanceList());

            _.each(vm.multiSelectedCode(), function (item: any) {
               _.remove(lstSimultance, o => o.code === item);
            });

            vm.simultanceList(lstSimultance);
        }

        getListSimultaneous(code: string) {
            const vm = this;

            vm.$blockui("grayout");
            vm.$ajax(API.getSimultaneous, { code : code }).done((res: any) => {
               if (res) {
                   let lstEmployee = _.map(res, function (item: any) {
                       return {id: item.employeeID, code: item.employeeCode, name: item.businessName, workplaceName: ''};
                   });
                   vm.multiSelectedCode([]);
                   vm.simultanceList(lstEmployee);
                   vm.screenMode = MODE.EDIT_MODE;
               } else {
                   vm.simultanceList([]);
                   vm.screenMode = MODE.NEW_MODE;
               };
            }).fail((err) => {
                vm.$dialog.error(err);
            }).always(() => vm.$blockui('clear'));
        }

        register() {
            const vm = this;

            let selectedEmployee = _.filter(vm.employeeList(), i => i.code === vm.selectedCode())[0];

            let lstSimultaneous = _.map(vm.simultanceList(), function (item: any) {
                return item.id;
            });
            let data = {
                sid: selectedEmployee.id,
                empMustWorkTogetherLst: lstSimultaneous
            };
            let apiString = vm.screenMode == MODE.NEW_MODE ? API.register : API.update;

            vm.$blockui("grayout");
            vm.$ajax(apiString, data).done(() => {
                vm.$dialog.info({messageId: "Msg_15"}).then(() => {
                    vm.selectedCode.valueHasMutated();
                });
            }).fail(err => {
                vm.$dialog.error(err);
            }).always(() => vm.$blockui('clear'));

        }

        remove() {
            const vm = this;

            let selectedEmployee = _.filter(vm.employeeList(), i => i.code === vm.selectedCode())[0];
            vm.$blockui("grayout");
            vm.$ajax(API.delete, {sid : selectedEmployee.id}).done((res) => {
                vm.$dialog.info({messageId: "Msg_16"}).then(() => {
                   vm.selectedCode.valueHasMutated();
                });
            }).fail((err) => {
                vm.$dialog.error(err);
            }).always(() => vm.$blockui('clear'));
        }

        public applyKCP005ContentSearch(dataList: EmployeeSearchDto[]): void {
            const vm = this;
            let employeeSearchs: UnitModel[] = _.map(dataList, function (data: any) {
                return {id: data.employeeId, code: data.employeeCode, name: data.employeeName, workplaceName: ''}
            });
            vm.employeeList(employeeSearchs);
            if (employeeSearchs && employeeSearchs.length > 0) {
                vm.selectedCode(employeeSearchs[0].code);
            };
        }

        initEmployeeList() {
            const vm = this;
            vm.listComponentOption = {
                isShowAlreadySet: true,
                isMultiSelect: false,
                listType: ListType.EMPLOYEE,
                employeeInputList: vm.employeeList,
                selectType: SelectType.SELECT_BY_SELECTED_CODE,
                selectedCode: vm.selectedCode,
                isDialog: vm.isDialog(),
                isShowNoSelectRow: false,
                alreadySettingList: vm.alreadySettingList,
                isShowWorkPlaceName: false,
                isShowSelectAllButton: false,
                disableSelection: false,
                maxRows: 12
            };
        }

        declareKCP005() {
            const vm = this;

            vm.componentOption = {
                isShowAlreadySet: false,
                isMultiSelect: true,
                listType: ListType.EMPLOYEE,
                employeeInputList: vm.simultanceList,
                selectType: SelectType.NO_SELECT,
                selectedCode: vm.multiSelectedCode,
                isDialog: true,
                isShowNoSelectRow: false,
                alreadySettingList: vm.alreadySettingList,
                isShowWorkPlaceName: false,
                isShowSelectAllButton: false,
                disableSelection: false,
                maxRows: 10
            }
        }

        declareCCG001() {
            const vm = this;
            vm.ccg001ComponentOption = <GroupOption>{
                /** Common properties */
                systemType: 2,
                showEmployeeSelection: false,
                showQuickSearchTab: false,
                showAdvancedSearchTab: true,
                showBaseDate: true,
                showClosure: false,
                showAllClosure: false,
                showPeriod: false,
                periodFormatYM: false,

                /** Required parameter */
                baseDate: vm.$date.now().toISOString(),
                periodStartDate: null,
                periodEndDate: null,
                inService: true,
                leaveOfAbsence: true,
                closed: true,
                retirement: false,

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
                showWorktype: false,
                isMutipleCheck: true,

                /**
                 * Self-defined function: Return data from CCG001
                 * @param: data: the data return from CCG001
                 */
                returnDataFromCcg001: function (data: Ccg001ReturnedData) {
                    vm.applyKCP005ContentSearch(data.listEmployee);
                }
            }
        }

        openDialogCDL009() {
            const vm = this;

            let togetherEmployee = ko.toJS(vm.multiSelectedCode());

            const params = {
                isMultiple: true,
                baseDate: moment(new Date()).toDate(),
                target: TargetClassification.WORKPLACE,
                selectedIds: togetherEmployee
            };
            vm.$window
                .storage('CDL009Params', params)
                .then(() => vm.$window.modal('com', '/view/cdl/009/a/index.xhtml'))
                .then(() => vm.$window.storage('CDL009Output'))
                .then((data) => {
                    if (data && data.length) {
                        vm.$ajax(API.getEmpInfo, { sids: data}).done((res) => {
                            let lstEmployee = _.map(res, function (item: any) {
                                return {id: item.employeeID, code: item.employeeCode, name: item.businessName, workplaceName: ''};
                            });
                            vm.multiSelectedCode([]);
                            vm.simultanceList(lstEmployee);
                        }).fail((err) => {
                            vm.$dialog.error(err);
                        })
                    }
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

    interface AlarmCheck {
        code: string;
        name: string;
        subConditionList: Array<any>
    }

    /**
     * Class TargetClassification
     */
    export class TargetClassification {
        static WORKPLACE = 1;
        static DEPARTMENT = 2;
    }

    export interface UnitModel {
        id?: string;
        code: string;
        name?: string;
        workplaceName?: string;
        isAlreadySetting?: boolean;
        optionalColumn?: any;
    }

    interface PersonInfo {
        employeeID: string;
        employeeCode: string;
        BusinessName: string;
    }

    const API = {
        initscreen: 'screen/at/ksm008/b/init',
        getSimultaneous: 'screen/at/ksm008/b/getSimultaneousDips',
        register: 'at/schedule/alarm/worktogether/register',
        update: 'at/schedule/alarm/worktogether/update',
        delete: 'at/schedule/alarm/worktogether/delete',
        checkSimultaneousSet: 'at/schedule/alarm/worktogether/checkDisplay',
        getEmpInfo: "screen/at/ksm008/b/getEmployeeInfo"
    }

    const enum MODE {
        NEW_MODE,
        EDIT_MODE
    }

}