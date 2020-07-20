/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kdp003.a {
	import f = nts.uk.at.kdp003.f;
	import k = nts.uk.at.kdp003.k;

	const API = {
		SETTING: 'at/record/stamp/management/personal/startPage',
		HIGHTLIGHT: 'at/record/stamp/management/personal/stamp/getHighlightSetting'
	};

	type STATE = 'LOGING_IN' | 'LOGIN_FAIL' | 'LOGIN_SUCCESS';

	@bean()
	export class KDP003AViewModel extends ko.ViewModel {
		state: KnockoutObservable<STATE> = ko.observable('LOGING_IN');

		tabs: KnockoutObservableArray<any> = ko.observableArray([]);
		stampToSuppress: KnockoutObservable<any> = ko.observable({});

		created() {
			const vm = this;

			vm.$window.storage('KDP003')
				.then((data: StorageData) => {
					if (!data) {
						return vm.$window.storage('KDP003', {
							CID: '',
							CCD: '000000000000',
							SID: '',
							SCD: '',
							PWD: '',
							WKPID: '',
							WKLOC_CD: ''
						})
							.then(() => vm.$window.storage('KDP003'));
					}

					return data;
				}).then((data: StorageData) => {
					console.log(data);
				});

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

		mounted() {
			const vm = this;

			vm.$window
				.modal('at', '/view/kdp/003/f/index.xhtml', { mode: 'admin' })
				.then((data: f.TimeStampLoginData) => {
					if (!data) {
						vm.state('LOGIN_FAIL');
						return false;
					} else {
						if (data.msgErrorId || data.errorMessage) {
							// login faild
							vm.state('LOGIN_FAIL');
							return false;
						} else {
							// login success
							vm.state('LOGIN_SUCCESS');
							return true;
						}
					}
				})
				.then((state: boolean) => {
					if (state) {
						return vm.$window.modal('at', '/view/kdp/003/k/index.xhtml');
					} else {
						return null;
					}
				})
				.then((data: null | k.Return) => {
					console.log(data);
				});
		}

		setting() {
			const vm = this;

			vm.$window
				.modal('/view/kdp/003/f/index.xhtml', { mode: 'admin' })
				.then((data: f.TimeStampLoginData) => {
					console.log(data);
				});
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

		stampButtonClick(btn: any, layout: any) {
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


	export interface StorageData {
		CID: string;
		CCD: string;
		SID: string;
		SCD: string;
		PWD: string;
		WKPID: string;
		WKLOC_CD: string;
	}
}
