/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kmr001.a {
	const API = {
		SETTING: 'at/record/stamp/management/personal/startPage',
		HIGHTLIGHT: 'at/record/stamp/management/personal/stamp/getHighlightSetting'
	};

	@bean()
	export class KMR001AViewModel extends ko.ViewModel {
		tabs: KnockoutObservableArray<any> = ko.observableArray([]);
		stampToSuppress: KnockoutObservable<any> = ko.observable({});

		created() {
			const vm = this;

			vm.$blockui('show')
				.then(() => vm.$ajax('at', API.SETTING))
				.then((data: any) => {
					if (data) {
						if (data.stampSetting) {
							vm.tabs(data.stampSetting.pageLayouts);
						}

						if (data.stampToSuppress) {
							vm.stampToSuppress(data.stampToSuppress);
						}
					}
				})
				.fail((res) => {
					vm.$dialog.error({ messageId: res.messageId })
						.then(() => vm.$jump("com", "/view/ccg/008/a/index.xhtml"));
				})
				.always(() => vm.$blockui('clear'));

			_.extend(window, { vm });
		}

		setting() {

		}

		company() {
		}

	}
}
