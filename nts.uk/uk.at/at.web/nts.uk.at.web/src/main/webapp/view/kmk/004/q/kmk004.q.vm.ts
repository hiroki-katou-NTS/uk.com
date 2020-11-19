/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kmk004.q {


	const API = {

	};

	@bean()
	export class ViewModel extends ko.ViewModel {

		year: KnockoutObservable<number> ;
		register() {

		}

		close() {
			let vm = this;
			vm.$window.close();
		}

		created(params?: IParams) {
			let vm = this;
			if (params && params.years.length) {
				vm.year = ko.observable(_.max(params.years) + 1);
			} else {
				vm.year = ko.observable(parseInt(moment().format("YYYY")));
			}
		}

		mounted() {

		}
	}

	interface IParams {
		years: Array<number>;
	}

}
