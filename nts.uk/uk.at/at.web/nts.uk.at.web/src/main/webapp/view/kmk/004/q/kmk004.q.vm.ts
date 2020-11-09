/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kmk004.q {


	const API = {

	};

	@bean()
	export class ViewModel extends ko.ViewModel {

		year: KnockoutObservable<number> = ko.observable(parseInt(moment().format("YYYY")));

		updateMode: KnockoutObservable<boolean> = ko.observable(false);

		register() {

		}

		close() {
			let vm = this;
			vm.$window.close();
		}

		created(params?: IParams) {
			let vm = this;
			if (params) {

				vm.year(_.max(params.years) + 1);
				vm.updateMode(true);

			} else {
				vm.updateMode(false);
			}
		}

		mounted() {

		}
	}

	interface IParams {
		years: Array<number>;
		workTypeMode: 'REGULAR_WORK' | 'FLEX' | 'UNUSUAL_WORK';
		screenMode: 'COMPANY' | 'WORK_PLACE' | 'EMPLOYMENT' | 'EMPLOYEE';
	}

}
