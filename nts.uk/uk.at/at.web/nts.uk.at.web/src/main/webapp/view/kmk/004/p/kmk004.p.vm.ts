/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.p {

	@bean()
	export class ViewModel extends ko.ViewModel {
		day: KnockoutObservable<string> = ko.observable('');
		week: KnockoutObservable<string> = ko.observable('');
		itemListP3_3: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
		selectedP3_3: KnockoutObservable<number> = ko.observable(0);
		itemListP3_5: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
		selectedP3_5: KnockoutObservable<number> = ko.observable(1);
		itemListP3_7: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
		selectedP3_7: KnockoutObservable<number> = ko.observable(1);
		checkedP3_8: KnockoutObservable<boolean> = ko.observable(true);
		checkedP4_2: KnockoutObservable<boolean> = ko.observable(true);
		checkedP4_3: KnockoutObservable<boolean> = ko.observable(true);

		constructor() {
			super();
			var vm = this;
			vm.itemListP3_3 = ko.observableArray<ItemModel>([
				new ItemModel('0', vm.$i18n("KMK004_313")),
				new ItemModel('1', vm.$i18n("KMK004_314"))
			]);
			vm.itemListP3_5([
				new ItemModel('1', '1月'),
				new ItemModel('2', '2月'),
				new ItemModel('3', '3月'),
				new ItemModel('4', '4月'),
				new ItemModel('5', '5月'),
				new ItemModel('6', '6月'),
				new ItemModel('7', '7月'),
				new ItemModel('8', '8月'),
				new ItemModel('9', '8月'),
				new ItemModel('10', '10月'),
				new ItemModel('11', '11月'),
				new ItemModel('12', '12月')
			]);
			vm.itemListP3_7([
				new ItemModel('1', '1ヶ月'),
				new ItemModel('2', '2ヶ月'),
				new ItemModel('3', '3ヶ月'),
				new ItemModel('4', '4ヶ月'),
				new ItemModel('5', '5ヶ月'),
				new ItemModel('6', '6ヶ月'),
				new ItemModel('7', '7ヶ月'),
				new ItemModel('8', '8ヶ月'),
				new ItemModel('9', '9ヶ月'),
				new ItemModel('10', '10ヶ月'),
				new ItemModel('11', '11ヶ月'),
				new ItemModel('12', '12ヶ月')
			]);
		}

		mounted() {
			const vm = this;
		}

		register() { 
			
		}

		close() {
			const vm = this;
			vm.$window.close();
		}
	}

	class ItemModel {
		code: string;
		name: string;

		constructor(code: string, name: string) {
			this.code = code;
			this.name = name;
		}
	}
}
