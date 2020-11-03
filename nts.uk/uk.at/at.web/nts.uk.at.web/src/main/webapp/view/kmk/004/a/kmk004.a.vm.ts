/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004 {
	export module a {
		@bean()
		export class ViewModel extends ko.ViewModel {
			public flexWorkManaging: KnockoutObservable<boolean> = ko.observable(false);
			public useDeformedLabor: KnockoutObservable<boolean> = ko.observable(false);

            create(){
            }

			mounted() {
			}
		}
	}
}
