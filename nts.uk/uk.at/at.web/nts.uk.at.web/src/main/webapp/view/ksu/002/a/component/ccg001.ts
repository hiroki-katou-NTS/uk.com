/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.ui.at.ksu002.a {
    const template = `
		<div id="com-ccg001">
		</div>
	`;

    const DATE_FORMAT = 'YYYY/MM/DD';

    interface Params {
        employees: KnockoutObservableArray<EmployeeModel>;
    }

    @component({
        name: 'ccg001',
        template
    })

    export class CCG001 extends ko.ViewModel {

        employees: KnockoutObservableArray<EmployeeModel>;

        constructor(params: Params) {
            super();
            const vm = this;

            vm.employees = params.employees;

            $('#com-ccg001')
                .ntsGroupComponent({
                    /** Common properties */
                    systemType: 2, //システム区分	
                    showEmployeeSelection: false,
                    showQuickSearchTab: true, //クイック検索
                    showAdvancedSearchTab: true,
                    showBaseDate: true, //基準日利用
                    showClosure: false, //就業締め日利用
                    showAllClosure: false, //全締め表示
                    showPeriod: false, //対象期間利用
                    periodFormatYM: true, //対象期間精度
                    maxPeriodRange: 'oneMonth', //最長期間

                    /** Required parameter */
                    baseDate: ko.observable(moment().format(DATE_FORMAT)),
                    periodStartDate: ko.observable(moment.utc('1900/01/01', DATE_FORMAT).format(DATE_FORMAT)),
                    periodEndDate: ko.observable(moment.utc('9999/12/31', DATE_FORMAT).format(DATE_FORMAT)),
                    inService: true,
                    leaveOfAbsence: true,
                    closed: true,
                    retirement: true,

                    /** Quick search tab options */
                    showAllReferableEmployee: true, //参照可能な社員すべて
                    showOnlyMe: true, //自分だけ
                    showSameDepartment: false,
                    showSameDepartmentAndChild: false,
                    showSameWorkplace: true, //同じ職場の社員
                    showSameWorkplaceAndChild: true, //同じ職場とその配下の社員

                    /** Advanced search properties */
                    showEmployment: true, //雇用条件
                    showDepartment: false,
                    showWorkplace: true, //職場条件
                    showClassification: true, //分類条件
                    showJobTitle: true, //職位条件
                    showWorktype: true, //勤種条件
                    isMutipleCheck: false, //選択モード

                    /**
                    * Self-defined function: Return data from CCG001
                    * @param: data: the data return from CCG001Ï
                    */
                    returnDataFromCcg001: function (data: any) {
                        // vm.params.baseDate(moment.utc(vm.$date.today(), DATE_FORMAT).format("YYYY-MM-DD"));

                        const outEmployees = data.listEmployee
                            .map((m: any) => ({
                                id: m.employeeId,
                                code: m.employeeCode,
                                businessName: m.employeeName
                            }));

                        vm.employees(outEmployees);
                    }
                });
        }
    }
}