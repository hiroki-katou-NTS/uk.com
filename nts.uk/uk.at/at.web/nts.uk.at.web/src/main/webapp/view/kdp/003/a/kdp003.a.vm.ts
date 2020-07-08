/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kdp003.a {
	const API = {
		SETTING: 'at/record/stamp/management/personal/startPage',
		HIGHTLIGHT: 'at/record/stamp/management/personal/stamp/getHighlightSetting'
	};

	@bean()
	export class KDP003AViewModel extends ko.ViewModel {
		tabs: KnockoutObservableArray<any> = ko.observableArray([]);
		stampToSuppress: KnockoutObservable<any> = ko.observable({});

		created() {
			const vm = this;

			vm.$ajax('at', API.SETTING)
				.then((data: any) => {
					if (data) {
						if (data.stampSetting) {
							vm.tabs(data.stampSetting.pageLayouts);
						}

						if (data.stampToSuppress) {
							vm.stampToSuppress(data.stampToSuppress);
						}
					}
				});

			_.extend(window, { vm });
		}

		setting() {
			const vm = this;

			vm.$window.modal('/view/kdp/003/f/index.xhtml', { mode: 'admin' });

			console.log(vm);
		}

		company() {
			const vm = this;

			vm.playAudio();

			// vm.$window.modal('/view/kdp/003/f/index.xhtml', { mode: 'employee' });

			console.log(vm);
		}


		playAudio() {
			const vm = this;

			const audio: HTMLAudioElement = document.createElement('audio');
			const source: HTMLSourceElement = document.createElement('source');

			source.src = 'https://www.w3schools.com/TAGS/horse.ogg';
			audio.append(source);

			audio.play();
		}

		stampButtonClick(btn: any) {
			const vm = this;

			vm.$ajax('at', API.HIGHTLIGHT)
				.then((data: any) => {
					const oldData = ko.unwrap(vm.stampToSuppress);

					if (!_.isEqual(data, oldData)) {
						vm.stampToSuppress(data);
					} else {
						vm.stampToSuppress.valueHasMutated();
					}
				});
		}
	}
}
