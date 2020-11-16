/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {
	const template = `
		<div id="com-ccg001">
		</div>
	`;

	const DATE_FORMAT = 'YYYY/MM/DD';

	interface Params {
		employees: KnockoutObservableArray<IEmployee>;
	}

	@component({
		name: 'ccg001',
		template
	})

	export class CCG001Component extends ko.ViewModel {

		public params!: Params;

		created(params: Params) {
			const vm = this;
			vm.params = params;

			$('#com-ccg001')
				.ntsGroupComponent({
					/** Common properties */
					systemType: 2, //システム区分	
					showEmployeeSelection: true,
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
					showWorktype: false, //勤種条件
					isMutipleCheck: true, //選択モード

					/**
					* Self-defined function: Return data from CCG001
					* @param: data: the data return from CCG001
					*/
					returnDataFromCcg001: function(data: any) {

						vm.params.employees([]);

						const employees = data.listEmployee
							.map((m: IEmployee) => ({
								affiliationCode: m.affiliationCode,
								affiliationId: m.affiliationId,
								affiliationName: m.affiliationName,
								code: m.employeeCode,
								name: m.employeeName,
								employeeId: m.employeeId
							}));

						vm.params.employees(employees);
					}
				});
		}
	}

	export interface IEmployee {
		affiliationCode: String;
		affiliationId: String;
		affiliationName: String;
		employeeCode: String;
		employeeId: String;
		employeeName: String;
	}
}