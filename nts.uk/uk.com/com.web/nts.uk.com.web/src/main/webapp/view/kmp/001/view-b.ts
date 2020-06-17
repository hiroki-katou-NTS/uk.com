/// <reference path="../../../lib/nittsu/viewcontext.d.ts" />

const template = `<div class="sidebar-content-header">
	<span class="title" data-bind= "text: $i18n('KMP001_2')"></span>
	<button class="proceed" data-bind= "text: $i18n('KMP001_5')"></button>
	<button class="danger" data-bind= "text: $i18n('KMP001_6')"></button>
</div>
<div class="view-kmp">
	<div class="float-left list-component">
		<div style="width: 480px"
			data-bind="ntsSearchBox: {
				label: $i18n('KMP001_22'),
				searchText: $i18n('KMP001_23'),
				searchMode: 'filter',
				targetKey: 'code',
				comId: 'card-list', 
				items: items,
				selected: currentCode,
				selectedKey: 'code',
				fields: ['name', 'code','code1'],
				mode: 'igGrid'
				}"></div>
		<div class="caret-right caret-background bg-green" style="padding: 10px;">
			<table id="card-list" 
				data-bind="ntsGridList: {
					height: 300,
					options: items,
					optionsValue: 'code',
					columns: [
			            { headerText: $i18n('KMP001_22'), prop: 'code', width: 180 },
			            { headerText: $i18n('KMP001_8'), prop: 'code1', width: 130 },
			            { headerText: $i18n('KMP001_9'), prop: 'name', width: 150 }
			        ],
					multiple: false,
					enable: true,
					value: currentCode
				}">
			</table>
		</div>
	</div>
	<div class="float-left model-component">
		<table>
			<tbody>
				<tr>
					<td class="label-column">
						<div style="border: 0; font-size: 18px" data-bind="ntsFormLabel: { text: $i18n('KMP001_22') }"></div>
					</td>
					<td class="data">
						<div>0000000000002</div>
					</td>
				</tr>
				<tr>
					<td class="label-column">
						<div data-bind="ntsFormLabel: { text: $i18n('KMP001_9'), required: true }"></div>
						<button data-bind="text: $i18n('KMP001_26')">Normal</button>
					</td>
					<td class="data">
						<div>00000002</div>
						<div style="margin-left: 10px">日通　社員2</div>
					</td>
				</tr>
				<tr>
					<td class="label-column">
						<div style="border: 0; font-size: 18px" data-bind="ntsFormLabel: { text: $i18n('KMP001_20') }"></div>
					</td>
					<td class="data">
						<div>2000/01/01</div>
					</td>
				</tr>
				<tr>
					<td class="label-column">
						<div style="border: 0; font-size: 18px" data-bind="ntsFormLabel: { text: $i18n('KMP001_21') }"></div>
					</td>
					<td class="data">
					<div>2000/01/01</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
`;

interface Params {
	
}

@component({
	name: 'view-b',
	template
})
class ViewBComponent extends ko.ViewModel {
	public params!: Params;

	public items: KnockoutObservableArray<any> = ko.observableArray([
		{ code: '001', code1: '001',  name: 'Nittsu', startDate: '2000/01/01', endDate: '2000/01/01'},
		{ code: '002', code1: '002',  name: 'Nittsu', startDate: '2000/01/01', endDate: '2000/01/01'},
		{ code: '003', code1: '003', name: 'Nittsu', startDate: '2000/01/01', endDate: '2000/01/01' },
		{ code: '004', code1: '004', name: 'Nittsu', startDate: '2000/01/01', endDate: '2000/01/01' },
		{ code: '005', code1: '005', name: 'Nittsu', startDate: '2000/01/01', endDate: '2000/01/01' }
	]);
	public currentCode: KnockoutObservable<string> = ko.observable('');

	created(params: Params) {
		this.params = params;
	}
}