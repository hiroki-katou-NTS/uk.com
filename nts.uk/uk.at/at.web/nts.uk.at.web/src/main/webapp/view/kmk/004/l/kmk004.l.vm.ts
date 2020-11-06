/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004 {
	import share = nts.uk.at.view.kmk004;
	export module l {

		@bean()
		export class ViewModel extends ko.ViewModel {

			public tabs: KnockoutObservableArray<string> = ko.observableArray([]);
			public tabSetting: share.SettingParam = new share.SettingParam();

			create() {
			}

			mounted() {
				const vm = this;
				vm.tabs(['Com_Company', 'Com_Workplace', 'Com_Person', 'Com_Employment']);
			}
		}
	}
}
