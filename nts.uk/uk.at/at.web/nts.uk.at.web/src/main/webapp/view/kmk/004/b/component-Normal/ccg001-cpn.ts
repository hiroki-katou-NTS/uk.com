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
					baseDate: ko.observable(moment().format(DATE_FORMAT)),
					periodStartDate: ko.observable(moment.utc('1900/01/01', DATE_FORMAT).format(DATE_FORMAT)),
					periodEndDate: ko.observable(moment.utc('9999/12/31', DATE_FORMAT).format(DATE_FORMAT)),
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
					* Self-defined function: Return data from CCG001
					* @param: data: the data return from CCG001
					*/
					returnDataFromCcg001: function (data: any) {

						vm.params.employees([]);

						const employees = data.listEmployee
							.map((m: any) => ({
								workplaceName: m.affiliationName,
								code: m.employeeCode,
								name: m.employeeName,
								id: m.employeeId
							}));

						vm.params.employees(employees);
					}
				});
		}
	}

	export interface IEmployee {
		workplaceName: String;
		code: String;
		id: String;
		name: String;
	}
}