/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.a {
	@bean()
	export class ViewModel extends ko.ViewModel {
		public flexWorkManaging: KnockoutObservable<boolean> = ko.observable(false);
		public useDeformedLabor: KnockoutObservable<boolean> = ko.observable(false);

		create() {
		}

		mounted() {
		}

		openDialog() {
			const vm = this;
			vm.$window
				.modal('/view/kmk/004/s/index.xhtml');
		}

		openViewB() {
			const vm = this;
			vm.$jump('/view/kmk/004/b/index.xhtml');
		}

		openViewG() {
			const vm = this;
			vm.$jump('/view/kmk/004/g/index.xhtml');
		}

		openViewL() {
			const vm = this;
			vm.$jump('/view/kmk/004/l/index.xhtml');
		}
	}
}
