/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.p {

		@bean()
		export class ViewModel extends ko.ViewModel {
			day: KnockoutObservable<string>;
			week: KnockoutObservable<string>;
			itemListP3_5: KnockoutObservableArray<ItemModel>;
			selectedCode: KnockoutObservable<string>;
			isEnable: KnockoutObservable<boolean>;
			isEditable: KnockoutObservable<boolean>;

			create() {
				var vm = this;
				vm.itemListP3_5 = ko.observableArray([
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

				vm.day = ko.observable('');
				vm.week = ko.observable('');
				vm.selectedCode = ko.observable('1');
				vm.isEnable = ko.observable(true);
				vm.isEditable = ko.observable(false);
			
			}

			mounted() {
				const vm = this;
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
