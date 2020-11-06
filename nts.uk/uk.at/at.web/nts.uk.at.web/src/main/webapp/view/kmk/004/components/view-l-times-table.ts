/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004 {

	interface Params {
	}

	const template = `
        <div class="timesTable">
            <table id="single-list"
					data-bind="ntsGridList: {								
					height: 255,
					dataSource: data,
					primaryKey: 'code',
					columns: columns,
					multiple: false,
					value: currentCode,
					enable: true
				}"></table>
        </div>
        <style type="text/css" rel="stylesheet">
        </style>
        <style type="text/css" rel="stylesheet" data-bind="html: $component.style"></style>
    `;

	@component({
		name: 'view-l-times-table',
		template
	})

	class TimesTable extends ko.ViewModel {

		itemList: KnockoutObservableArray<IListTimes> = ko.observableArray([]);
		currentCode = ko.observable();
		columns: any;
		created() {
			const vm = this;
			const data: IListTimes[] = [{ month: '4月度', time: '177:08' }
				, { month: '5月度', time: '177:08' }
				, { month: '6月度', time: '177:08' }
				, { month: '7月度', time: '177:08' }
				, { month: '8月度', time: '177:08' }
				, { month: '9月度', time: '177:08' }
				, { month: '10月度', time: '177:08' }
				, { month: '11月度', time: '177:08' }
				, { month: '12月度', time: '177:08' }
				, { month: '1月度', time: '177:08' }
				, { month: '2月度', time: '177:08' }
				, { month: '3月度', time: '177:08' }];

			vm.itemList(data);

			vm.columns = ko.observableArray([
				{ headerText: "月度", key: 'month', width: 180 },
				{ headerText: "法定労働時間", key: 'time', width: 50 }
			]);

		}

	}

	export interface IListTimes {
		month: string;
		time: string;
	}

	// export class ListYear {
	//     status: KnockoutObservable<boolean> = ko.observable(true);
	//     statusValue: KnockoutObservable<string> = ko.observable('');
	//     value: KnockoutObservable<string> = ko.observable('');

	//     public create(params?: IListYear) {
	//         const self = this;

	//         if (params) {
	//             self.status(params.status);
	//             self.value(params.value);
	//             if(params.status) {
	//                 self.statusValue('*');
	//             }
	//         }
	//     }
	// }
}
