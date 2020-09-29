/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />


@bean()
class KDP004GViewModel extends ko.ViewModel {

	hasFocus: KnockoutObservable<boolean> = ko.observable(false);
	displayLoginBtn: KnockoutObservable<boolean> = ko.observable(false);
	errorMessage: KnockoutObservable<string> = ko.observable('');

	created(params: any) {
		let vm = this;
		vm.$window.storage('ModelGParam').then((param: any) => {
			vm.displayLoginBtn(param.displayLoginBtn);
			vm.errorMessage(vm.$i18n.message(param.errorMessage));
		});
	}
	
	mounted() {
		const vm = this;
		
		vm.hasFocus(true);
	}

	public closeDialog(actionName: string) {
		let vm = this;
		vm.$window.close({ actionName: actionName });
	}
}