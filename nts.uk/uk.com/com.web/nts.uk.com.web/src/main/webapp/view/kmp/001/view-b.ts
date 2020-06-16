/// <reference path="../../../lib/nittsu/viewcontext.d.ts" />



const template = `<div class="sidebar-content-header">
	<span class="title" data-bind= "text: $i18n('KMP001_2')"></span>
	<button class="proceed" data-bind= "text: $i18n('KMP001_5')"></button>
	<button class="danger" data-bind= "text: $i18n('KMP001_6')"></button>
</div>
<div>
	<div style="width: 500px"
		data-bind="ntsSearchBox: {
			searchMode: 'filter',
			targetKey: 'code',
			comId: 'card-list', 
			items: items,
			selected: currentCode,
			selectedKey: 'code',
			fields: ['name', 'code'],
			mode: 'igGrid'
		}" />
					
	<table id="card-list"
		data-bind="ntsGridList: {
			height: 320,
			options: items,
			optionsValue: 'code',
			columns: [
	            { headerText: 'コード', prop: 'code', width: 150 },
	            { headerText: '名称', prop: 'name', width: 130 },
	            { headerText: '説明', prop: 'description', width: 50 }
	        ],
			multiple: false,
			enable: true,
			value: currentCode
		}"></table>
</div>
<div data-bind="text: currentCode"></div>
`;

@component({
	name: 'view-b',
	template
})
class ViewBComponent extends ko.ViewModel {
	public params!: Params;

	public items: KnockoutObservableArray<any> = ko.observableArray([
		{ code: '001', name: 'Abc', description: 'Nittsu', other1: 'xxz', other2: 'xxy' },
		{ code: '002', name: 'Abc', description: 'Nittsu', other1: 'xxz', other2: 'xxy' },
		{ code: '003', name: 'Abc', description: 'Nittsu', other1: 'xxz', other2: 'xxy' },
		{ code: '004', name: 'Abc', description: 'Nittsu', other1: 'xxz', other2: 'xxy' }
	]);
	public currentCode: KnockoutObservable<string> = ko.observable('');

	created(params: Params) {
		this.params = params;
	}

	mounted() {
		const vm = this;

		vm.items.push({ code: '005', name: 'Abc', description: 'Nittsu', other1: 'xxz', other2: 'xxy' });
	}
}
