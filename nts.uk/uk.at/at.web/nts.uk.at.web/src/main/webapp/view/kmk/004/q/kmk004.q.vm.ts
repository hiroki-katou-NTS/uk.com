/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kmk004.q {

	@bean()
	export class ViewModel extends ko.ViewModel {

		year: KnockoutObservable<string> = ko.observable();
		//startYear: string;
		endYear: string;
		paramYears: Array<number>;

		register() {
			let vm = this;
			if (nts.uk.ui.errors.hasError()) { return; }

			if (vm.paramYears.indexOf(Number(vm.year())) != -1) {
				vm.$dialog.error({ messageId: 'Msg_2104' });
				return;

			} else {
				vm.$window.close({
					year: vm.year()
				});
			}
		}

		close() {
			let vm = this;
			vm.$window.close();
		}

		created(params?: IParams) {
			let vm = this;
			vm.paramYears = params.years;
			vm.year((params && params.years.length) ? String(_.max(params.years) + 1) : moment().format("YYYY"));
			//vm.startYear = vm.year();
			vm.endYear = vm.defEnyear(params.startDate);
		}

		defEnyear(startDate: number) {
			let start = startDate ? startDate : 202004;
			return Number(start % 100) > 1 ? '9998' : '9999';
		}

		mounted() {
			$('#year-picker').focus();
		}
	}

	interface IParams {
		years: Array<number>;
		startDate: number;
	}

}
