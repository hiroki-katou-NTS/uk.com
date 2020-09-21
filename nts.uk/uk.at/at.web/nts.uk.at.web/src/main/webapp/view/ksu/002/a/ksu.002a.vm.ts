/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.ui.at.ksu002.a {
	@bean()
	export class ViewModel extends ko.ViewModel {
		baseDate = ko.observable({ begin: new Date(2020, 8, 14), finish: new Date(2020, 9, 4) });
		
		created() {

		}

		mounted() {

		}
	}
}