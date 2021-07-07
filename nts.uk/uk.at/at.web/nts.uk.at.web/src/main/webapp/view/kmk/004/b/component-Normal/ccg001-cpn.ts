/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {
	const template = `
		<div id="com-ccg001">
		</div>
	`;

	const DATE_FORMAT = 'YYYY/MM/DD';

	interface Params {
		selectedCode: KnockoutObservable<string>;
		employees: KnockoutObservableArray<IEmployee>;
		isReload: KnockoutObservable<string>;
	}

	@component({
		name: 'ccg001',
		template
	})

	export class CCG001Component extends ko.ViewModel {

		public params!: Params;
		public selectedCode: KnockoutObservable<string> = ko.observable('');
		public isReload: KnockoutObservable<string> = ko.observable('');

		created(params: Params) {
			const vm = this;
			vm.params = params;
			vm.selectedCode = params.selectedCode;
			vm.isReload = params.isReload;
			const date = moment(new Date()).toDate();

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
					baseDate: date,
					periodStartDate: date,
					periodEndDate: date,
					inService: true,
					leaveOfAbsence: true,
					closed: true,
					retirement: true,

					/** Quick search tab options */
					showAllReferableEmployee: true,
					showOnlyMe: true,
					showSameDepartment: false,
					showSameDepartmentAndChild: false,
					showSameWorkplace: true,
					showSameWorkplaceAndChild: true,

					/** Advanced search properties */
					showEmployment: true,
					showDepartment: false,
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
								id: m.employeeId,
								isAlreadySetting: m.isAlreadySetting
							}));

						vm.params.employees(employees);

						vm.selectedCode(data.listEmployee[0].employeeCode);
						if (employees.length > 300) {
							setTimeout(() => {
								vm.isReload.valueHasMutated();
							}, 100);
						}

					}
				})
				.done(() => {
					$("#ccg001-btn-search-drawer").focus();
				});
		}
	}

	export interface IEmployee {
		workplaceName: string;
		code: string;
		id: string;
		name: string;
		isAlreadySetting: boolean;
	}

	export class Employee {
		id: KnockoutObservable<string> = ko.observable('');
		code: KnockoutObservable<string> = ko.observable('');
		name: KnockoutObservable<string> = ko.observable('');
		workplaceName: KnockoutObservable<string> = ko.observable('');
		isAlreadySetting: KnockoutObservable<boolean> = ko.observable(false);
		nameSynthetic: KnockoutObservable<string> = ko.observable('');

		constructor(params?: IEmployee) {
			this.update(params);
		}

		public update(params?: IEmployee) {
			if (params) {
				this.id(params.id);
				this.code(params.code);
				this.name(params.name);
				this.workplaceName(params.workplaceName);
				this.isAlreadySetting(params.isAlreadySetting);
				this.nameSynthetic(params.code + ' ' + params.name);
			}
		}

		public updateStatus(isAlreadySetting: boolean) {
			this.isAlreadySetting(isAlreadySetting);
		}
	}
}