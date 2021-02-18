/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.at.view.ktg026.a.viewmodel {
	@bean()
	export class ViewModel extends ko.ViewModel {

		constructor(private params: any) {
			super();
		}

		mounted() {
			const vm = this;

			vm.$window.size(400, 450);
		}

		/**
		* 閉じる
		*/
		onCloseWindow(): void {
			const vm = this;

			vm.$window.close();
		}
	}
}