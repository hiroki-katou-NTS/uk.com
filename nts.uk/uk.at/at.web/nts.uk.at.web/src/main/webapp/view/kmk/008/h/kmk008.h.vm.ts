/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk008.h {

	const KMK008_CUUS_H: string = 'CloseUsageUnitSettings';

	@bean()
	export class ScreenModel extends ko.ViewModel {

		useEmployment: KnockoutObservable<boolean>  = ko.observable(true);
		useWorkPlace: KnockoutObservable<boolean>  = ko.observable(true);
		useClass: KnockoutObservable<boolean>  = ko.observable(true);

		constructor() {
			super();
			const vm = this;			
		}

		created() {
			const vm = this;
			_.extend(window, { vm });
		}

		mounted() {
			let vm = this;

			$('.chk_H13').focus();
		}

		submitAndCloseDialog() {
			let vm = this;
			//36申請登録単位設定
			vm.$window.storage(KMK008_CUUS_H, {
				Employment: vm.useEmployment(),
				WorkPlace: vm.useWorkPlace(),
				Class: vm.useWorkPlace(),
			});
			vm.$window.close();
			return false;
		}

		closeDialog() {
			let vm = this;
			vm.$window.storage(KMK008_CUUS_H, null);
			vm.$window.close();
			return false;
		}
	}
}