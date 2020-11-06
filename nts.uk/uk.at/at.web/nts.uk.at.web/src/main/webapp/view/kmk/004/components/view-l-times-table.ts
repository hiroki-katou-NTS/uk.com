/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004 {

	interface Params {
	}

	const template = `
            <table class="times-table">
				<tr>
					<th>月度</td>
					<th>法定労働時間</td>
				</tr>
				<tr>
					<td>4月度</td>
					<td><span>177:08</span></td>
				</tr>
				<tr>
					<td>5月度</td>
					<td><span>177:08</span></td>
				</tr>
				<tr>
					<td>6月度</td>
					<td><span>177:08</span></td>
				</tr>
				<tr>
					<td>7月度</td>
					<td><span>177:08</span></td>
				</tr>
				<tr>
					<td>8月度</td>
					<td><span>177:08</span></td>
				</tr>
				<tr>
					<td>9月度</td>
					<td><span>177:08</span></td>
				</tr>
				<tr>
					<td>10月度</td>
					<td><span>177:08</span></td>
				</tr>
				<tr>
					<td>11月度</td>
					<td><span>177:08</span></td>
				</tr>
				<tr>
					<td>12月度</td>
					<td><span>177:08</span></td>
				</tr>
				<tr>
					<td>1月度</td>
					<td><span>177:08</span></td>
				</tr>
				<tr>
					<td>2月度</td>
					<td><span>177:08</span></td>
				</tr>
				<tr>
					<td>3月度</td>
					<td><span>177:08</span></td>
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
				padding: 3px;
			}
			
			tr, th, td {
				text-align: center;
			}
			
			.times-table th {
				padding: 3px;
				background: #97D155;
			}
			
			.times-table {
				width: 240px;
			}
			
			.times-table span {
				border: 1px solid #AAAAAA;
				padding: 0 10px 0 10px;
  				border-radius: 5px;
			}
			
			.times-table {
                padding: 15px;
                border: 1px solid #AAAAAA;
                border-radius: 15px;
				width: 170px;
				margin: 15px;
            }

            .times-table table {
                border-collapse: collapse;
            }

			.times-table tr, .times-table th {
                border: 1px solid #AAAAAA;
            }
        </style>
        <style type="text/css" rel="stylesheet" data-bind="html: $component.style"></style>
    `;

	@component({
		name: 'view-l-times-table',
		template
	})

	class TimesTable extends ko.ViewModel {

		columns: any;
		created() {
			const vm = this;
		}

	}

	export interface IListTimes {
		month: string;
		time: string;
	}

}
