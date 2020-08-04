/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

const template = `
<div class="sidebar-content-header">
	<span class="title" data-bind= "text: $i18n('KMP001_3')"></span>
	<button class="proceed" data-bind= "text: $i18n('KMP001_5')"></button>
</div>
<div class="view-kmp">
	<div class="list-component float-left">
		<div class="layout-date">
			<table>
				<tbody>
					<tr>
						<td>
							<div data-bind="ntsFormLabel: { text: $i18n('KMP001_14'), required: true }"></div>
						</td>
						<td>
							<div 
								tabindex="1"
								id="daterangepicker"
								style="margin-left: 20px"
								data-bind="
									ntsDateRangePicker: { 
										require: true,
										showNextPrevious: true,
										value: dateRange, 
										maxRange: 'oneMonth'
									}"/>		
						</td>
						<td>
							<button 
								style="margin-left: 20px" 
								class="caret-bottom" 
								data-bind= "
									text: $i18n('KMP001_15'), 
									click: getAllData">
							</button>
						</td>
					<tr>
				</tbody>
			</table>
		</div>
		<div class="caret-right caret-background bg-green" style="padding: 10px;">
			<div style="width: 540px"
				data-bind="ntsSearchBox: {
					label: $i18n('KMP001_22'),
					searchMode: 'filter',
					targetKey: 'stampNumber',
					comId: 'card-list', 
					items: items,
					selected: stampNumber,
					selectedKey: 'stampNumber',
					fields: ['stampNumber', 'stampAtr'],
					mode: 'igGrid'
					}"></div>
			<div>
				<table id="card-list" 
					data-bind="ntsGridList: {
						height: 300,
						options: items,
						optionsValue: 'stampNumber',
						columns: [
				            { headerText: $i18n('KMP001_22'), prop: 'stampNumber', width: 180 },
				            { headerText: $i18n('KMP001_27'), prop: 'infoLocation', width: 160 },
				            { headerText: $i18n('KMP001_28'), prop: 'stampAtr', width: 80 },
	 						{ headerText: $i18n('KMP001_29'), prop: 'stampDatetime', width: 145 }
				        ],
						multiple: false,
						enable: true,
						value: stampNumber
					}">
				</table>
			</div>
		</div>
	</div>
	<div class="model-component float-left ">
		<table class="table_content">
			<tbody>
				<tr>
					<td class="label-column-left">
						<div id="td-bottom">
							<div style="border: 0; font-size: 18px" data-bind="ntsFormLabel: { text: $i18n('KMP001_22')}"></div>
						</div>
					</td>
					<td class="data">
						<div id="td-bottom">0000000000004</div>
					</td>
				</tr>
				<tr>
					<td class="label-column-left">
						<div id="td-bottom">
							<div data-bind="ntsFormLabel: { text: $i18n('KMP001_9'), required: true }"></div>
							<button data-bind="text: $i18n('KMP001_26')">Normal</button>
						</div>
					</td>
					<td class="data">
						<div id="td-bottom">00000002</div>
						<div style="margin-left: 15px; margin-bottom:15px;">日通　社員2</div>
					</td>
				</tr>
				<tr>
					<td class="label-column-left">
						<div id="td-bottom">
							<div style="border: 0; font-size: 18px" data-bind="ntsFormLabel: { text: $i18n('KMP001_20') }"></div>
						</div>
					</td>
				</tr>
				<tr>
					<td class="label-column-left">
						<div id="td-bottom">
							<div style="border: 0; font-size: 18px" data-bind="ntsFormLabel: { text: $i18n('KMP001_21') }"></div>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
`;

interface Params {

}

interface DateRange {
	startDate?: string;
	endDate?: string;
}

const API = {
	GET_STAMPCARD: 'screen/pointCardNumber/getAllCardUnregister/'
};

@component({
	name: 'view-c',
	template
})

class ViewCComponent extends ko.ViewModel {

	public params!: Params;

	public items: KnockoutObservableArray<IStampCard> = ko.observableArray();
	public stampNumber: KnockoutObservable<string> = ko.observable('');

	dateRange: KnockoutObservable<DateRange> = ko.observable({});

	created(params: Params) {
		const vm = this;

		vm.params = params;

		vm.dateRange
			.subscribe((dr: DateRange) => {
			});
	}

	public getAllData() {
		const vm = this;
		const { startDate, endDate } = ko.toJS(vm.dateRange);

		/*vm.$ajax(API.GET_STAMPCARD + startDate + endDate)
			.then((data: any[]) => {
				console.log(data);
			});
			*/
			
		const start = moment.utc(startDate, "YYYY/MM/DD").format("YYYY-MM-DD");
		const end = moment.utc(startDate, "YYYY/MM/DD").format("YYYY-MM-DD");
		
		vm.$ajax(API.GET_STAMPCARD + start + "/" + end)
		.then((data: IStampCardC[]) => {
				console.log(data);
			});
	}
}

interface IStampCardC {
	stampNumber: string;
	infoLocation: string;
	stampAtr: string;
	stampDatetime: Date;
}

class StampCardC {
	stampNumber = ko.observable('');
	infoLocation = ko.observable('');
	stampAtr = ko.observable('');
	stampDatetime = ko.observable(null);

	constructer(params?: IModel) {
		const self = this;

		if (params) {
			if (params.stampNumber) {
				self.stampNumber(params.stampNumber);
			}
			self.update(params);
		}
	}

	update(params: IModel) {
		const self = this;
	}

	clear() {
		const self = this;
		self.stampNumber('');
	}
}