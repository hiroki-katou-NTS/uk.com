/// <reference path="../../../lib/nittsu/viewcontext.d.ts" />

const template = `
<div id="com-ccg001"></div>
<div class="sidebar-content-header">
	<span class="title" data-bind="text: $i18n('KMP001_1')"></span>
	<button data-bind="text: $i18n('KMP001_4')"></button>
	<button class="proceed" data-bind="text: $i18n('KMP001_5')"></button>
	<button class="danger" data-bind="text: $i18n('KMP001_6')"></button>
	<button data-bind="text: $i18n('KMP001_7')"></button>
</div>
<div>
</div>`;

@component({
	name: 'view-a',
	template
})
class ViewA extends ko.ViewModel {
	public params!: Params;

	created(params: Params) {
		this.params = params;
	}

	mounted() {
		const dataFormate = 'YYYY/MM/DD';

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
				returnDataFromCcg001: function(data: any) {

				}
			});
	}
}