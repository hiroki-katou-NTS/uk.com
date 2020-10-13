/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />




@bean()
class KTG005BViewModel extends ko.ViewModel {


	simpleValue: KnockoutObservable<String> = ko.observable("あなたの申請状況");

	constructor() {
		super();
	}

	created(params: any) {

		let vm = this;
		$("#item_table").ntsFixedTable({ height: 164, width: 350 });

	}



}
