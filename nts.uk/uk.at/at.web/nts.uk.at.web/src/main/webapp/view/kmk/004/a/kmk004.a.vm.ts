/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.a {


	@bean()
	export class ViewModel extends ko.ViewModel {
		public flexWorkManaging: KnockoutObservable<boolean> = ko.observable(true);
		public useDeformedLabor: KnockoutObservable<boolean> = ko.observable(true);

		create() {
			const vm = this;
			debugger;

			vm.$blockui('invisible')
				.then(() => vm.$ajax('screen/at/kmk004/viewA/init'))
				.then((data: any) => {
					console.log(data);
					debugger;
				})
				.then(() => vm.$blockui('clear'));

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
