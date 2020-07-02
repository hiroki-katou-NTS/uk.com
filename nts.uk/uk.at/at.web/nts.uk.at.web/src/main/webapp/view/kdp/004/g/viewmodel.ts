/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />


@bean()
class KDP004GViewModel extends ko.ViewModel {

	retry: KnockoutObservable<number> = ko.observable(0);
	errorMessage: KnockoutObservable<string> = ko.observable('');

	created(params: any) {
		let vm = this;
		vm.$window.storage('ModelGParam').then((param: any) => {
			vm.retry(param.retry);
			vm.errorMessage(param.errorMessage);
		});
	}

	public closeDialog(actionName: string) {
		let vm = this;
		vm.$window.storage('actionName', actionName);
		nts.uk.ui.windows.close();
	}
}