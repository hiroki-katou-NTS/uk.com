/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kmk004.q {


	const API = {

	};

	@bean()
	export class ViewModel extends ko.ViewModel {

		year: KnockoutObservable<string> = ko.observable();
		startYear:string;

		register() {
			let vm = this;
			
			vm.$window.close({
				year: vm.year()
			});
		}

		close() {
			let vm = this;
			vm.$window.close();
		}

		created(params?: IParams) {
			let vm = this;
			
			vm.year((params && params.years.length) ? String(_.max(params.years) + 1) : moment().format("YYYY"));
			vm.startYear = vm.year();
		}

		mounted() {

		}
	}

	interface IParams {
		years: Array<number>;
	}

}
