/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

const template = `<div></div>`;

@component({
	name: 'ccg-component',
	template
})
class CcgComponent extends ko.ViewModel {
	employees!: KnockoutObservableArray<any>;
	
	
	public created(params: any) {
		const vm = this;
		
		vm.employees = params.employees || ko.observableArray([]);
	}
	
	public mounted() {
		const vm = this;
		const dataFormate = 'YYYY/MM/DD';
		
		$(vm.$el)
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
				baseDate: ko.observable(moment().format(dataFormate)),
				periodStartDate: ko.observable(moment.utc('1900/01/01', dataFormate).format(dataFormate)),
				periodEndDate: ko.observable(moment.utc('9999/12/31', dataFormate).format(dataFormate)),
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
					const employees = data.listEmployee
						.map(m => ({
							code: m.employeeCode,
							name: m.employeeName,
							joinDate: null,
							entireDate: null,
							cardNos: [{
								checked: false,
								no: '000001'
							}, {
								checked: false,
								no: '000002'
							}, {
								checked: false,
								no: '000003'
							}, {
								checked: false,
								no: '000004'
							}, {
								checked: false,
								no: '000005'
							}, {
								checked: false,
								no: '000006'
							}, {
								checked: false,
								no: '000007'
							}],
							config: false
						}));

					// xu ly lay casc thong tin lien quan toi code o day

					vm.employees(employees);
				}
			});
	}
}