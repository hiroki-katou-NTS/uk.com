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
<div class="view-kmp">
	<div class="list-component float-left viewa">
		<div class="caret-right caret-background bg-green" style="padding: 10px;">
			<table id="can-id-de-lam-gi" data-bind="ntsGridList: {
						height: 300,
						options: items,
						optionsValue: 'code',
						columns: [
				            { headerText: $i18n('KMP001_8'), prop: 'code', width: 100 },
				            { headerText: $i18n('KMP001_9'), prop: 'code1', width: 130 },
				            { headerText: $i18n('KMP001_10'), prop: 'name', width: 80 },
	 						{ headerText: $i18n('KMP001_11'), prop: 'startDate', width: 70 }
				        ],
						multiple: false,
						enable: true
					}">
			</table>
		</div>
	</div>
	<div class="float-left model-component">
		<table>
			<tbody>
				<tr>
					<td class="label-column-a">
						<div data-bind="text: $component$i18n('KMP001_8')"></div>
					</td>
					<td>
						<div>0000000000002</div>
					</td>
				</tr>
				<tr>
					<td class="label-column-a">
						<div data-bind="text: $component$i18n('KMP001_9')"></div>
					</td>
					<td>
						<div>日通　社員１</div>
					</td>
				</tr>
				<tr>
					<td class="label-column-a">
					<div data-bind="text: $component$i18n('KMP001_20')"></div>
					</td>
					<td>
						<div>2000/01/01</div>
					</td>
				</tr>
				<tr>
					<td class="label-column-a">
						<div data-bind="text: $component$i18n('KMP001_21')"></div>
					</td>
					<td>
					<div>2000/01/01</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
<div>
`;

interface Params {

}

@component({
	name: 'view-a',
	template
})
class ViewA extends ko.ViewModel {
	public params!: Params;

	public items: KnockoutObservableArray<any> = ko.observableArray([
		{ code: '001', code1: '001', name: 'Nittsu', startDate: '○' },
		{ code: '002', code1: '002', name: 'Nittsu', startDate: '○' },
		{ code: '003', code1: '003', name: 'Nittsu', startDate: '○' },
		{ code: '004', code1: '004', name: 'Nittsu', startDate: '○' },
		{ code: '005', code1: '005', name: 'Nittsu', startDate: '○' }
	]);

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