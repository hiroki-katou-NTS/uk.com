/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

@bean()
class ViewModel extends ko.ViewModel {
	
	itemList: KnockoutObservableArray<any> = ko.observableArray([]);
	selectedCode: KnockoutObservable<string> = ko.observable('');

	created(params: any) {

		var self = this;
		self.itemList([{
			code: '1'
		},{
			code: '2'
		}]);
		
		self.selectedCode = ko.observable('1');
	}

	mounted() {
	}
	
	closeDialog() {
		const vm = this;
		vm.$window.close;
	}
}