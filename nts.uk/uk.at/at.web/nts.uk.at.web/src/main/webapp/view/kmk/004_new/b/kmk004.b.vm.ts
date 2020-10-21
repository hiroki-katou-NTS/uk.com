/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004_new {
	export module b {
		@bean()
		export class ViewModel extends ko.ViewModel {
            
            tabs: KnockoutObservableArray<string> = ko.observableArray([]);

            create(){
            }

			mounted() {
				const vm = this;

				vm.tabs(['KMP001_1', 'KMP001_2', 'KMP001_3']);
			}
		}
	}
}
