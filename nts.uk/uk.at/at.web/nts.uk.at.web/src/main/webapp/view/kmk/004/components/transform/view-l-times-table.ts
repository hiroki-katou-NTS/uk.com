/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004 {

	interface Params {
	}

	const template = `
            <table class="times-table">
				<tr>
					<th data-bind="i18n: 'KMK004_304'">月度</td>
					<th>法定労働時間</td>
				</tr>
				<tr>
					<td>4月度</td>
					<td><input class = "col-3", data-bind="ntsTimeEditor: {value: time, inputFormat: 'time', enable: enable,}" /></td>
				</tr>
				<tr>
					<td>5月度</td>
					<td><input class = "col-3", data-bind="ntsTimeEditor: {value: time, inputFormat: 'time', enable: enable,}" /></td>
				</tr>
				<tr>
					<td>6月度</td>
					<td><input class = "col-3", data-bind="ntsTimeEditor: {value: time, inputFormat: 'time', enable: enable,}" /></td>
				</tr>
				<tr>
					<td>7月度</td>
					<td><input class = "col-3", data-bind="ntsTimeEditor: {value: time, inputFormat: 'time', enable: enable,}" /></td>
				</tr>
				<tr>
					<td>8月度</td>
					<td><input class = "col-3", data-bind="ntsTimeEditor: {value: time, inputFormat: 'time', enable: enable,}" /></td>
				</tr>
				<tr>
					<td>9月度</td>
					<td><input class = "col-3", data-bind="ntsTimeEditor: {value: time, inputFormat: 'time', enable: enable,}" /></td>
				</tr>
				<tr>
					<td>10月度</td>
					<td><input class = "col-3", data-bind="ntsTimeEditor: {value: time, inputFormat: 'time', enable: enable,}" /></td>
				</tr>
				<tr>
					<td>11月度</td>
					<td><input class = "col-3", data-bind="ntsTimeEditor: {value: time, inputFormat: 'time', enable: enable,}" /></td>
				</tr>
				<tr>
					<td>12月度</td>
					<td><input class = "col-3", data-bind="ntsTimeEditor: {value: time, inputFormat: 'time', enable: enable,}" /></td>
				</tr>
				<tr>
					<td>1月度</td>
					<td><input class = "col-3", data-bind="ntsTimeEditor: {value: time, inputFormat: 'time', enable: enable,}" /></td>
				</tr>
				<tr>
					<td>2月度</td>
					<td><input class = "col-3", data-bind="ntsTimeEditor: {value: time, inputFormat: 'time', enable: enable,}" /></td>
				</tr>
				<tr>
					<td>3月度</td>
					<td><input class = "col-3", data-bind="ntsTimeEditor: {value: time, inputFormat: 'time', enable: enable,}" /></td>
				</tr>
				<tr>
					<td>計</td>
					<td>2091:18</td>
				</tr>
			</table>
			
        <style type="text/css" rel="stylesheet">
			.times-table td:nth-child(1)  {
				background: #E0F59E;
				border: 1px solid #AAAAAA;
			}
			
			.times-table td {
				text-align: center;
				padding: 3px 0 3px 0;
			}
			
			.times-table th {
				padding: 6px;
				background: #97D155;
			}
			
			.times-table {
                padding: 15px;
                border: 1px solid #AAAAAA;
                border-radius: 15px;
				margin-left: 30px;
            }


            .times-table table {
                border-collapse: collapse;
            }

			.times-table tr, .times-table th {
                border: 1px solid #AAAAAA;
				text-align: center;
            }

			.times-table td:nth-child(3)  {
				padding: 3px;
				display: block;
    			width: 100px;
			}
			.col-3 {
				width: 55px;
				height: 12px;
			}
        </style>
        <style type="text/css" rel="stylesheet" data-bind="html: $component.style"></style>
    `;

	@component({
		name: 'view-l-times-table',
		template
	})

	class TimesTable extends ko.ViewModel {
		enable: KnockoutObservable<boolean>;
		time: KnockoutObservable<string>;
		columns: any;
		created() {
			const vm = this;
			vm.enable = ko.observable(true);
			vm.time = ko.observable("00:00");
		}

	}

	export interface IListTimes {
		month: string;
		time: string;
	}

}
