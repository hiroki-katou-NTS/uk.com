/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kmr001.a {
	const API = {
	};
	const PATH = {
		REDIRECT : '/view/ccg/008/a/index.xhtml'
	}

	@bean()
	export class KMR001AViewModel extends ko.ViewModel {
		tabs: KnockoutObservableArray<any> = ko.observableArray([]);
		stampToSuppress: KnockoutObservable<any> = ko.observable({});

		created() {
			const vm = this;
			_.extend(window, { vm });
		}

		setting() {

		}

		company() {
		}

	}
}
