/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004 {

	interface Params {
	}

	const template = `
            <table class="view-o-times-table">
				<tr>
					<th></th>
					<th data-bind="i18n: 'KMK004_304'">月度</td>
					<th>法定労働時間</td>
				</tr>
				<tr>
					<td>
						<div data-bind="ntsMultiCheckBox: {
							options: itemList,
							optionsValue: 'id',
							optionsText: 'name',
							value: selectedIds,
							enable: enable}"></div>
					</td>
					<td>4月度</td>
					<td><span>177:08</span></td>
				</tr>
				<tr>
					<td>
						<div data-bind="ntsMultiCheckBox: {
							options: itemList,
							optionsValue: 'id',
							optionsText: 'name',
							value: selectedIds,
							enable: enable}"></div>
					</td>
					<td>5月度</td>
					<td><span>177:08</span></td>
				</tr>
				<tr>
					<td><div data-bind="ntsMultiCheckBox: {
							options: itemList,
							optionsValue: 'id',
							optionsText: 'name',
							value: selectedIds,
							enable: enable}"></div></td>
					<td>6月度</td>
					<td><span>177:08</span></td>
				</tr>
				<tr>
					<td><div data-bind="ntsMultiCheckBox: {
							options: itemList,
							optionsValue: 'id',
							optionsText: 'name',
							value: selectedIds,
							enable: enable}"></div></td>
					<td>7月度</td>
					<td><span>177:08</span></td>
				</tr>
				<tr>
					<td><div data-bind="ntsMultiCheckBox: {
							options: itemList,
							optionsValue: 'id',
							optionsText: 'name',
							value: selectedIds,
							enable: enable}"></div></td>
					<td>8月度</td>
					<td><span>177:08</span></td>
				</tr>
				<tr>
					<td><div data-bind="ntsMultiCheckBox: {
							options: itemList,
							optionsValue: 'id',
							optionsText: 'name',
							value: selectedIds,
							enable: enable}"></div></td>
					<td>9月度</td>
					<td><span>177:08</span></td>
				</tr>
				<tr>
					<td><div data-bind="ntsMultiCheckBox: {
							options: itemList,
							optionsValue: 'id',
							optionsText: 'name',
							value: selectedIds,
							enable: enable}"></div></td>
					<td>10月度</td>
					<td><span>177:08</span></td>
				</tr>
				<tr>
					<td><div data-bind="ntsMultiCheckBox: {
							options: itemList,
							optionsValue: 'id',
							optionsText: 'name',
							value: selectedIds,
							enable: enable}"></div></td>
					<td>11月度</td>
					<td><span>177:08</span></td>
				</tr>
				<tr>
					<td><div data-bind="ntsMultiCheckBox: {
							options: itemList,
							optionsValue: 'id',
							optionsText: 'name',
							value: selectedIds,
							enable: enable}"></div></td>
					<td>12月度</td>
					<td><span>177:08</span></td>
				</tr>
				<tr>
					<td><div data-bind="ntsMultiCheckBox: {
							options: itemList,
							optionsValue: 'id',
							optionsText: 'name',
							value: selectedIds,
							enable: enable}"></div></td>
					<td>1月度</td>
					<td><span>177:08</span></td>
				</tr>
				<tr>
					<td><div data-bind="ntsMultiCheckBox: {
							options: itemList,
							optionsValue: 'id',
							optionsText: 'name',
							value: selectedIds,
							enable: enable}"></div></td>
					<td>2月度</td>
					<td><span>177:08</span></td>
				</tr>
				<tr>
					<td><div data-bind="ntsMultiCheckBox: {
							options: itemList,
							optionsValue: 'id',
							optionsText: 'name',
							value: selectedIds,
							enable: enable}"></div></td>
					<td>3月度</td>
					<td><span>177:08</span></td>
				</tr>
				<tr>
					<td><div data-bind="ntsMultiCheckBox: {
							options: itemList,
							optionsValue: 'id',
							optionsText: 'name',
							value: selectedIds,
							enable: enable}"></div></td>
					<td>計</td>
					<td>2091:18</td>
				</tr>
			</table>
			
        <style type="text/css" rel="stylesheet">
			.view-o-times-table td:nth-child(2)  {
				background: #E0F59E;
				border: 1px solid #AAAAAA;
			}
			
			.view-o-times-table td {
				text-align: center;
			}
			
			.view-o-times-table th {
				padding: 3px;
				background: #97D155;
			}
			
			.view-o-times-table span {
				border: 1px solid #AAAAAA;
				padding: 0 10px 0 10px;
  				border-radius: 5px;
			}
			
			.view-o-times-table {
                padding: 15px;
                border: 1px solid #AAAAAA;
                border-radius: 15px;
				width: 190px;
				margin: 15px;
            }

            .view-o-times-table table {
                border-collapse: collapse;
            }

			.view-o-times-table tr, .view-o-times-table th {
                border: 1px solid #AAAAAA;
				text-align: center;
            }
        </style>
        <style type="text/css" rel="stylesheet" data-bind="html: $component.style"></style>
    `;

	@component({
		name: 'view-o-times-table',
		template
	})

	class TimesTable extends ko.ViewModel {
		itemList: KnockoutObservableArray<Time>;
		selectedIds: KnockoutObservableArray<number>;
		enable: KnockoutObservable<boolean>;

		columns: any;
		created() {
			const vm = this;
			
			vm.itemList = ko.observableArray([
            new Time(1, ''),
           
        ]);
			vm.selectedIds = ko.observableArray([]);
        	vm.enable = ko.observable(true);
			
		}

	}

	class Time {
		id: number;
		name: string;
		constructor(id:number, name:string) {
			var self = this;
			self.id = id;
			self.name = name;
		}

	}
}