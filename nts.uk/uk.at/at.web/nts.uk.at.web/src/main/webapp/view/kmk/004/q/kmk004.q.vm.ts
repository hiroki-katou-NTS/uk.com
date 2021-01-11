/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kmk004.q {


	const API = {

	};

	@bean()
	export class ViewModel extends ko.ViewModel {

		year: KnockoutObservable<string> = ko.observable();
		startYear: string;
		endYear: string;

		register() {
			let vm = this;
			if (nts.uk.ui.errors.hasError()) { return; }
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
			vm.endYear = vm.defEnyear(params.startDate);;
		}

		defEnyear(startDate: number) {
			let start = startDate ? startDate : 202004;
			return Number(start % 100) > 1 ? '9998' : '9999';
		}

		mounted() {

		}
	}

	interface IParams {
		years: Array<number>;
		startDate: number;
	}

}
