/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kmr003.a {

    import validation = nts.uk.ui.validation;
    import parseTime = nts.uk.time.parseTime;

    const API = {
        RESERVATION_CORRECTION: 'screen/at/record/reservation/bento_modify/reservationCorrection'
    };

    @bean()
    export class KMR003AViewModel extends ko.ViewModel {
        date: KnockoutObservable<string> = ko.observable(moment().format('YYYY/MM/DD'));

        ccg001ComponentOption: GroupOption = null;

        empSearchItems: KnockoutObservableArray<EmployeeSearchDto> = ko.observableArray([]);
        employeeList: KnockoutObservableArray<any> = ko.observableArray([]);
        receptionNames: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedReception: KnockoutObservable<any> = ko.observable();
        receptionHours1: KnockoutObservable<ReservationRecTime> = ko.observable(null);
        receptionHours2: KnockoutObservable<ReservationRecTime> = ko.observable(null);
        closingTime: KnockoutObservable<string> = ko.observable();
        orderMngAtr: KnockoutObservable<boolean> = ko.observable(false);
        isEnableRegister: KnockoutObservable<boolean> = ko.observable(true);

        listComponentOption: any = {
            isShowAlreadySet: false,
            isMultiSelect: false,
            listType: 4,
            employeeInputList: this.employeeList,
            selectType: 1,
            selectedCode: ko.observable(),
            isDialog: false,
            isShowNoSelectRow: false,
            isShowWorkPlaceName: true,
            isShowSelectAllButton: false,
            disableSelection : false
        }

        constructor() {
            super();
            const vm = this;
            vm.ccg001ComponentOption = <GroupOption>{
                /** Common properties */
                systemType: 2,
                showEmployeeSelection: true,
                showQuickSearchTab: true,
                showAdvancedSearchTab: true,
                showBaseDate: true,
                showClosure: false,
                showAllClosure: false,
                showPeriod: false,
                periodFormatYM: null,

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
                showSameWorkplace: true,
                showSameWorkplaceAndChild: true,

                /** Advanced search properties */
                showEmployment: true,
                showDepartment: false,
                showWorkplace: true,
                showClassification: true,
                showJobTitle: true,
                showWorktype: true,
                isMutipleCheck: null,
                tabindex: 6,
                showOnStart: false,

                /**
                 * Self-defined function: Return data from CCG001
                 * @param: data: the data return from CCG001
                 */
                returnDataFromCcg001: function (data: Ccg001ReturnedData) {
                    vm.empSearchItems(data.listEmployee);
                }
            }
        }

        created(param: any) {
            const vm = this;
            vm.empSearchItems.subscribe((value) => {
                if (value) {
                    vm.employeeList(_.map(vm.empSearchItems(), x => {return { id: x.employeeId, code: x.employeeCode, name: x.employeeName, affiliationName: x.affiliationName  }}));
                }
            })
            $('#com-ccg001').ntsGroupComponent(vm.ccg001ComponentOption);

            
            vm.selectedReception.subscribe((value) => {
                if (value == 1 && vm.receptionHours1()) {
                    vm.closingTime(nts.uk.time.format.byId("Time_Short_HM", vm.receptionHours1().startTime) + '~' + nts.uk.time.format.byId("Time_Short_HM", vm.receptionHours1().endTime) );
                }
                if (value == 2 && vm.receptionHours2()) {
                    vm.closingTime(nts.uk.time.format.byId("Time_Short_HM", vm.receptionHours2().startTime) + '~' + nts.uk.time.format.byId("Time_Short_HM", vm.receptionHours2().endTime) );
                }
            })

            if (param) {
                vm.employeeList(param.employeeList);
                vm.date(param.correctionDate);
            }
            
            vm.$blockui('show');
            vm.$ajax(API.RESERVATION_CORRECTION).done((res) => {
                if (res) {
                    let nameLst: any[] = [];
                    // vm.receptionNames.removeAll();
                    let reservationRecTimeZoneLst = res.reservationRecTimeZoneLst;
                    let receptionName1: any[] = _.filter(reservationRecTimeZoneLst, x => x.frameNo === 1);
                    let receptionName2: any[] = _.filter(reservationRecTimeZoneLst, x => x.frameNo === 2);
                    
                    if (receptionName1.length > 0) {
                        vm.receptionHours1(receptionName1[0].receptionHours);
                        nameLst.push({ id: '1', name: receptionName1[0].receptionHours.receptionName });
                    }
                    
                    if (res.receptionTimeZone2Use && receptionName2.length > 0) {
                        vm.receptionHours2(receptionName2[0].receptionHours);
                        nameLst.push({ id: '2', name: receptionName2[0].receptionHours.receptionName });
                    }
                    
                    vm.receptionNames(nameLst);
                    if (param) {
                        if (param.selectedReception) {
                            vm.selectedReception(param.selectedReception);
                        } else {
                            vm.selectedReception('1');
                        }
                    } else {
                        vm.selectedReception('1');
                    }
                    vm.orderMngAtr(res.correctionContent.orderMngAtr);
                }
            }).fail((err) => {
                if (err) {
                    if (err.messageId == 'Msg_2254') {
                        vm.isEnableRegister(false);
                    }
                    vm.$dialog.error({messageId: err.messageId, messageParams: err.parameterIds});
                }
            }).always(() => vm.$blockui('hide'));

        }
        
        mounted() {
            const vm = this;
            $('#grid').ntsListComponent(vm.listComponentOption);
        }

        gotoScreenB() {
            const vm = this;

            if (vm.employeeList().length > 0) {
                let param = {
                    systemDate: moment().format('YYYY/MM/DD'), 
                    orderMngAtr: vm.orderMngAtr(), 
                    correctionDate: vm.date(), 
                    receptionNames: vm.receptionNames(), 
                    selectedReception: vm.selectedReception(),
                    receptionHours1: vm.receptionHours1(), 
                    receptionHours2: vm.receptionHours2(), 
                    empIds: _.map(vm.employeeList(), x => x.id)
                }
                vm.$jump("../b/index.xhtml", param);
            } else {
                vm.$dialog.error({ messageId: 'Msg_2261' });
            }

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

    interface ReservationRecTime {
        receptionName: string;
        startTime: number;
        endTime: number;
    }
}
