/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />


@bean()
class KDP004AViewModel extends ko.ViewModel {
	systemDate: KnockoutObservable<any> = ko.observable(moment.utc());

	pika: KnockoutObservable<any> = ko.observable('layout-1');

	tabs = {
		tabs: ko.observableArray([{
			id: '1',
			title: 'レイアウト１'
		}, {
			id: '2',
			title: 'レイアウト2'
		}, {
			id: '3',
			title: 'レイアウト3'
		},{
			id: '4',
			title: 'レイアウト4'
		},{
			id: '5',
			title: 'レイアウト5'
		}]),
		selected: ko.observable('1')
	};

	layoutsSetting: KnockoutObservable<any> = ko.observable(
		{ layout1Title: ko.observable('layout-1'), layout1Visible: ko.observable(true) }
	);

	created(params: any) {
		let vm = this;

	}
}