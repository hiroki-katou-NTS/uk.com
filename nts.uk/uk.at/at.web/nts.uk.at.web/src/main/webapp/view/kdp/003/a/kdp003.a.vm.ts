/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kdp003.a {
	import f = nts.uk.at.kdp003.f;
	import k = nts.uk.at.kdp003.k;
	import share = nts.uk.at.view.kdp.share;

	const API = {
		SETTING: 'at/record/stamp/management/personal/startPage',
		HIGHTLIGHT: 'at/record/stamp/management/personal/stamp/getHighlightSetting',
		LOGIN_ADMIN: 'ctx/sys/gateway/kdp/login/adminmode',
		LOGIN_EMPLOYEE: 'ctx/sys/gateway/kdp/login/employeemode',
		FINGER_STAMP_SETTING: 'at/record/stamp/finger/get-finger-stamp-setting'
	};

	const KDP003_SAVE_DATA = 'KDP003_DATA';

	type STATE = 'LOGING_IN' | 'LOGIN_FAIL' | 'LOGIN_SUCCESS';

	@bean()
	export class ViewModel extends ko.ViewModel {
		state: KnockoutObservable<STATE> = ko.observable('LOGING_IN');

		employeeData: EmployeeListParam = {
			employees: ko.observableArray([]),
			selectedId: ko.observable(null),
			employeeAuthcUseArt: ko.observable(true)
		};

		buttonPage: {
			tabs: KnockoutObservableArray<share.PageLayout>;
			stampToSuppress: KnockoutObservable<share.StampToSuppress>;
		} = {
				tabs: ko.observableArray([]),
				stampToSuppress: ko.observable({
					departure: false,
					goingToWork: false,
					goOut: false,
					turnBack: false
				})
			};

		mounted() {
			const vm = this;

			vm.$blockui('show')
				.then(() => vm.$ajax('at', API.FINGER_STAMP_SETTING))
				.then((data: FingerStampSetting) => {
					if (data) {
						const { stampSetting } = data;

						if (stampSetting) {
							const { employeeAuthcUseArt } = stampSetting;
							vm.buttonPage.tabs(stampSetting.pageLayouts);

							vm.employeeData.employeeAuthcUseArt(!!employeeAuthcUseArt);
						}
					}
				})
				.then(() => vm.$ajax('at', API.HIGHTLIGHT))
				.then((data: share.StampToSuppress) => vm.buttonPage.stampToSuppress(data))
				.then(() => vm.$window.storage(KDP003_SAVE_DATA))
				.then((data: StorageData) => {
					if (!data) {
						// first login
						return vm.$window
							.modal('at', '/view/kdp/003/f/index.xhtml', { mode: 'admin' });
					} else {
						// if data login exist
						return data;
					}
				})
				.then((data: f.TimeStampLoginData | StorageData) => {
					if (!data) {
						vm.state('LOGIN_SUCCESS');
						vm.employeeData.selectedId(null);
						return false;
					} else if (_.has(data, 'em')) {
						const loginData = data as f.TimeStampLoginData;

						if (loginData.msgErrorId || loginData.errorMessage) {
							// login faild
							vm.state('LOGIN_FAIL');
							return false;
						} else {
							// login success
							vm.state('LOGIN_SUCCESS');

							const storageData: StorageData = {
								CID: loginData.em.companyId,
								CCD: loginData.em.companyCode,
								PWD: loginData.em.password,
								SCD: loginData.em.employeeCode,
								SID: loginData.em.employeeId,
								WKLOC_CD: '',
								WKPID: []
							};

							return vm.$window
								.storage(KDP003_SAVE_DATA, storageData)
								.then(() => true);
						}
					} else {
						const {
							CCD,
							CID,
							PWD,
							SCD,
							SID
						} = data as StorageData;

						const loginData: f.ModelData = {
							companyCode: CCD,
							companyId: CID,
							employeeCode: SCD,
							employeeId: SID,
							password: PWD
						};

						// auto login
						return vm.$ajax(API.LOGIN_ADMIN, loginData)
							.then((data: f.TimeStampLoginData) => {
								if (!data || data.errorMessage || data.msgErrorId) {
									vm.state('LOGIN_FAIL');
								} else {
									vm.state('LOGIN_SUCCESS');
								}

								return !!data;
							})
							.fail(() => false);
					}
				})
				// check storage data
				.then((state: boolean) => {
					if (!state) {
						return null;
					} else {
						return vm.$window.storage(KDP003_SAVE_DATA)
							.then((data: StorageData) => {
								if (data && data.WKPID) {
									return { selectedId: data.WKPID };
								}

								// if not exist workplaceID
								return vm.$window.modal('at', '/view/kdp/003/k/index.xhtml');
							});
					}
				})
				.then((data: null | k.Return) => {
					if (!data) {
						vm.state('LOGIN_FAIL');

						return false;
					} else {
						return vm.$window.storage(KDP003_SAVE_DATA)
							// update workplaceId to storage
							.then((storage: StorageData) => {
								if (storage) {
									if (_.isArray(data.selectedId)) {
										storage.WKPID = data.selectedId;
									} else {
										storage.WKPID = [data.selectedId];
									}
								}
								vm.$window.storage(KDP003_SAVE_DATA, storage);
							})
							// return data from storage
							.then(() => vm.$window.storage(KDP003_SAVE_DATA));
					}
				})
				.then((data: false | StorageData) => {
					// if login and storage data success
					if (data) {
						vm.loadData(data);
					}
				})
				.fail((res) => vm.$dialog
					.error({ messageId: res.messageId })
					.then(() => vm.state('LOGIN_FAIL')))
				.always(() => vm.$blockui('clear'));
		}

		// 打刻入力(氏名選択)で社員の一覧を取得する
		loadData(data: StorageData) {
			const vm = this;
			console.log(data);
		}

		setting() {
			const vm = this;

			vm.$window
				.modal('/view/kdp/003/f/index.xhtml', { mode: 'admin' })
				.then((data: f.TimeStampLoginData) => {
					if (data) {
						vm.$window.storage(KDP003_SAVE_DATA)
							.then((storage: StorageData) => {
								if (storage) {
									storage.CCD = data.em.companyCode;
									storage.CID = data.em.companyId;
									storage.PWD = data.em.password;
									storage.SCD = data.em.employeeCode;
									storage.SID = data.em.employeeId;
									storage.WKLOC_CD = '';
									storage.WKPID = [];
								}
								else {
									storage = {
										CCD: data.em.companyCode,
										CID: data.em.companyId,
										PWD: data.em.password,
										SCD: data.em.employeeCode,
										SID: data.em.employeeId,
										WKLOC_CD: '',
										WKPID: []
									};
								}

								vm.$window.storage(KDP003_SAVE_DATA, storage);
							});

						return vm.$window.modal('at', '/view/kdp/003/k/index.xhtml');
					}

					return null;
				})
				.then((data: null | k.Return) => {
					if (!data) {
						return false;
					} else {
						return vm.$window.storage(KDP003_SAVE_DATA)
							// update workplaceId to storage
							.then((storage: StorageData) => {
								if (storage) {
									if (_.isArray(data.selectedId)) {
										storage.WKPID = data.selectedId;
									} else {
										storage.WKPID = [data.selectedId];
									}
								}
								vm.$window.storage(KDP003_SAVE_DATA, storage);
							})
							// return data from storage
							.then(() => vm.$window.storage(KDP003_SAVE_DATA));
					}
				})
				.then((data: false | StorageData) => {
					// if login and storage data success
					if (data) {
						vm.loadData(data);
					}
				});
		}

		stampHistory() {
			const vm = this;

			vm.$window
				.storage(KDP003_SAVE_DATA)
				.then((data: StorageData) => {
					return vm.$window.modal('/view/kdp/003/f/index.xhtml', {
						mode: 'employee',
						company: { id: data.CID },
						employee: { id: data.SID, code: data.SCD }
					});
				})
				.then((data: f.TimeStampLoginData) => {
					if (data) {
						return vm.$window.modal('/view/kdp/003/s/index.xhtml');
					}
				});
		}

		stampButtonClick(btn: share.ButtonSetting, layout: share.PageLayout) {
			const vm = this;
			const { buttonPage } = vm;

			vm.$ajax('at', API.HIGHTLIGHT)
				.then((data: any) => {
					const oldData = ko.unwrap(buttonPage.stampToSuppress);

					if (!_.isEqual(data, oldData)) {
						buttonPage.stampToSuppress(data);
					} else {
						buttonPage.stampToSuppress.valueHasMutated();
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
		WKPID: string[];
		WKLOC_CD: string;
	}

	interface FingerStampSetting {
		stampResultDisplay: StampResultDisplay;
		stampSetting: StampSetting;
	}

	interface StampResultDisplay {
		companyId: string;
		displayItemId: number[];
		notUseAttr: number;
	}

	interface StampSetting {
		authcFailCnt: number;
		backGroundColor: string;
		buttonEmphasisArt: boolean;
		cid: string;
		correctionInterval: number;
		employeeAuthcUseArt: boolean;
		historyDisplayMethod: number;
		nameSelectArt: boolean;
		pageLayouts: share.PageLayout[];
		passwordRequiredArt: boolean;
		resultDisplayTime: number;
		textColor: string;
	}
}
