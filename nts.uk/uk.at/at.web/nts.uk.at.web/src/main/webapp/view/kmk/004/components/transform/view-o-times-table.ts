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
					<td><input class = "col-3", data-bind="ntsTimeEditor: {value: time, inputFormat: 'time', enable: enable,}" /></td>
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
					<td><input class = "col-3", data-bind="ntsTimeEditor: {value: time, inputFormat: 'time', enable: enable,}" /></td>
				</tr>
				<tr>
					<td><div data-bind="ntsMultiCheckBox: {
							options: itemList,
							optionsValue: 'id',
							optionsText: 'name',
							value: selectedIds,
							enable: enable}"></div></td>
					<td>6月度</td>
					<td><input class = "col-3", data-bind="ntsTimeEditor: {value: time, inputFormat: 'time', enable: enable,}" /></td>
				</tr>
				<tr>
					<td><div data-bind="ntsMultiCheckBox: {
							options: itemList,
							optionsValue: 'id',
							optionsText: 'name',
							value: selectedIds,
							enable: enable}"></div></td>
					<td>7月度</td>
					<td><input class = "col-3", data-bind="ntsTimeEditor: {value: time, inputFormat: 'time', enable: enable,}" /></td>
				</tr>
				<tr>
					<td><div data-bind="ntsMultiCheckBox: {
							options: itemList,
							optionsValue: 'id',
							optionsText: 'name',
							value: selectedIds,
							enable: enable}"></div></td>
					<td>8月度</td>
					<td><input class = "col-3", class = "col-3" data-bind="ntsTimeEditor: {value: time, inputFormat: 'time', enable: enable,}" /></td>
				</tr>
				<tr>
					<td><div data-bind="ntsMultiCheckBox: {
							options: itemList,
							optionsValue: 'id',
							optionsText: 'name',
							value: selectedIds,
							enable: enable}"></div></td>
					<td>9月度</td>
					<td ><input class = "col-3", data-bind="ntsTimeEditor: {value: time, inputFormat: 'time', enable: enable,}" /></td>
				</tr>
				<tr>
					<td><div data-bind="ntsMultiCheckBox: {
							options: itemList,
							optionsValue: 'id',
							optionsText: 'name',
							value: selectedIds,
							enable: enable}"></div></td>
					<td>10月度</td>
					<td><input class = "col-3", data-bind="ntsTimeEditor: {value: time, inputFormat: 'time', enable: enable,}" /></td>
				</tr>
				<tr>
					<td><div data-bind="ntsMultiCheckBox: {
							options: itemList,
							optionsValue: 'id',
							optionsText: 'name',
							value: selectedIds,
							enable: enable}"></div></td>
					<td>11月度</td>
					<td><input class = "col-3", data-bind="ntsTimeEditor: {value: time, inputFormat: 'time', enable: enable,}" /></td>
				</tr>
				<tr>
					<td><div data-bind="ntsMultiCheckBox: {
							options: itemList,
							optionsValue: 'id',
							optionsText: 'name',
							value: selectedIds,
							enable: enable}"></div></td>
					<td>12月度</td>
					<td><input class = "col-3", data-bind="ntsTimeEditor: {value: time, inputFormat: 'time', enable: enable,}" /></td>
				</tr>
				<tr>
					<td><div data-bind="ntsMultiCheckBox: {
							options: itemList,
							optionsValue: 'id',
							optionsText: 'name',
							value: selectedIds,
							enable: enable}"></div></td>
					<td>1月度</td>
					<td><input class = "col-3", data-bind="ntsTimeEditor: {value: time, inputFormat: 'time', enable: enable,}" /></td>
				</tr>
				<tr>
					<td><div data-bind="ntsMultiCheckBox: {
							options: itemList,
							optionsValue: 'id',
							optionsText: 'name',
							value: selectedIds,
							enable: enable}"></div></td>
					<td>2月度</td>
					<td><input class = "col-3", data-bind="ntsTimeEditor: {value: time, inputFormat: 'time', enable: enable,}" /></td>
				</tr>
				<tr>
					<td><div data-bind="ntsMultiCheckBox: {
							options: itemList,
							optionsValue: 'id',
							optionsText: 'name',
							value: selectedIds,
							enable: enable}"></div></td>
					<td>3月度</td>
					<td><input class = "col-3", data-bind="ntsTimeEditor: {value: time, inputFormat: 'time', enable: enable,}" /></td>
				</tr>
				<tr>
					<td><div data-bind="ntsMultiCheckBox: {
							options: itemList,
							optionsValue: 'id',
							optionsText: 'name',
							value: selectedIds,
							enable: enable}"></div></td>
					<td>計</td>
					<td></td>
				</tr>
			</table>
			
        <style type="text/css" rel="stylesheet">
			.view-o-times-table td:nth-child(2)  {
				background: #E0F59E;
				border: 1px solid #AAAAAA;
				padding: 0 3px 0 3px;
			}
			
			.view-o-times-table td:nth-child(3)  {
				padding: 3px;
				display: block;
    			width: 100px;
			}
			
			.view-o-times-table td {
				text-align: center;
			}
			
			.view-o-times-table th {
				padding: 3px;
				background: #97D155;
			}
			
			.view-o-times-table {
                padding: 15px;
                border: 1px solid #AAAAAA;
                border-radius: 15px;
				margin: 15px;
            }

            .view-o-times-table table {
                border-collapse: collapse;
            }

			.view-o-times-table tr, .view-o-times-table th {
                border: 1px solid #AAAAAA;
				text-align: center;
            }
			
			.col-3 {
				width: 65px;
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
		time: KnockoutObservable<string>;

		columns: any;
		created() {
			const vm = this;

			vm.itemList = ko.observableArray([
				new Time(1, ''),

			]);
			vm.selectedIds = ko.observableArray([]);
			vm.enable = ko.observable(false);
			vm.time = ko.observable("");
		}

	}

	class Time {
		id: number;
		name: string;
		constructor(id: number, name: string) {
			var self = this;
			self.id = id;
			self.name = name;
		}

	}
}