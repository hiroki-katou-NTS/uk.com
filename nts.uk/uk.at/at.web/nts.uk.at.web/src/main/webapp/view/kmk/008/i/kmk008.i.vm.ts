/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk008.i {

	const KMK008_ARUS_I: string = '36AppRegUnitSetting';

	@bean()
	export class ScreenModel extends ko.ViewModel {

		useWorkPlace: KnockoutObservable<boolean> = ko.observable(true);


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

			$('.chk_I13').focus();
		}

		submitAndCloseDialog() {
			let vm = this;
			//36申請登録単位設定
			vm.$window.storage(KMK008_ARUS_I, { WorkPlace: vm.useWorkPlace() });
			vm.$window.close();
			return false;
		}

		closeDialog() {
			let vm = this;
			vm.$window.storage(KMK008_ARUS_I, null);
			vm.$window.close();
			return false;
		}
	}
}