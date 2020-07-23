/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kdp003.a {
	import f = nts.uk.at.kdp003.f;
	import k = nts.uk.at.kdp003.k;
	import share = nts.uk.at.view.kdp.share;

	const API = {
		NAME: '/sys/portal/webmenu/username',
		SETTING: 'at/record/stamp/management/personal/startPage',
		HIGHTLIGHT: 'at/record/stamp/management/personal/stamp/getHighlightSetting',
		LOGIN_ADMIN: 'ctx/sys/gateway/kdp/login/adminmode',
		LOGIN_EMPLOYEE: 'ctx/sys/gateway/kdp/login/employeemode',
		FINGER_STAMP_SETTING: 'at/record/stamp/finger/get-finger-stamp-setting'
	};

	const DIALOG = {
		F: '/view/kdp/003/f/index.xhtml',
		K: '/view/kdp/003/k/index.xhtml',
		S: '/view/kdp/003/s/index.xhtml'
	};

	const KDP003_SAVE_DATA = 'KDP003_DATA';

	@bean()
	export class ViewModel extends ko.ViewModel {
		// Message
		// logingin: undefined
		// login false: has messageId
		// login success: null
		message: KnockoutObservable<f.Message | string | null | undefined> = ko.observable(undefined);

		// setting for button A3
		showClockButton: {
			setting: KnockoutObservable<boolean>;
			company: KnockoutObservable<boolean>;
		} = {
				setting: ko.observable(true),
				company: ko.observable(true)
			};

		// data option for list employee A2
		employeeData: EmployeeListParam = {
			employees: ko.observableArray([]),
			selectedId: ko.observable(null),
			employeeAuthcUseArt: ko.observable(true)
		};

		// data option for tabs button A5
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

			// get storage save preview login data
			vm.$window.storage(KDP003_SAVE_DATA)
				.then((data: StorageData) => {
					const showLogin = () => vm.$window
						.modal('at', DIALOG.F, { mode: 'admin' });

					if (!data) {
						// if not has storage (first login)
						// open f dialog and login
						return vm.$blockui('show').then(showLogin).fail(() => false);
					} else {
						// if data login exist (next login)
						// return exist data get from storage
						return data;
					}
				})
				.then((data: f.TimeStampLoginData | StorageData) => {
					if (_.has(data, 'em')) {
						// data login by f dialog
						const loginData = data as f.TimeStampLoginData;

						if (!loginData.msgErrorId && !loginData.errorMessage) {
							// storage successful login data
							const storageData: StorageData = {
								CID: loginData.em.companyId,
								CCD: loginData.em.companyCode,
								PWD: loginData.em.password,
								SCD: loginData.em.employeeCode,
								SID: loginData.em.employeeId,
								WKLOC_CD: '',
								WKPID: []
							};

							vm.$window.storage(KDP003_SAVE_DATA, storageData);
						}

						return loginData;
					} else {
						// data login by storage
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

						// auto login by storage data of preview login
						return vm.$ajax('at', API.LOGIN_ADMIN, loginData).fail(() => false);
					}
				})
				// check storage data
				.then((state: false | f.TimeStampLoginData) => {
					if (state === false || state.msgErrorId || state.errorMessage) {
						if (state !== false) {
							if (state.msgErrorId) {
								vm.message({
									messageId: state.msgErrorId
								});
							} else {
								vm.message(state.errorMessage);
							}
						}
						return false;
					} else {
						return vm.$window.storage(KDP003_SAVE_DATA)
							.then((data: StorageData) => {
								if (data && data.WKPID.length) {
									return { selectedId: data.WKPID };
								}

								// if not exist workplaceID
								return vm.$window.modal('at', DIALOG.K);
							});
					}
				})
				.then((data: false | k.Return) => {
					if (data === false) {
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
						return vm.loadData(data);
					}
				})
				// show message from login data (return by f dialog)
				.fail((message: { messageId: string }) => vm.$dialog.error(message))
				.always(() => vm.$blockui('clear'));

			_.extend(window, { vm });
		}

		loadData(data: StorageData) {
			const vm = this;
			const clearState = () => {
				vm.message({
					messageId: 'KDP002_2'
				});
				// clear tabs button
				vm.buttonPage.tabs([]);
				// remove employee list
				vm.employeeData.employeeAuthcUseArt(false);
			};


			// <<ScreenQuery>>: 打刻入力(氏名選択)で社員の一覧を取得する
			return vm.$ajax('at', API.FINGER_STAMP_SETTING)
				.then((data: FingerStampSetting) => {
					if (data) {
						const { stampSetting } = data;

						if (stampSetting) {
							const { employeeAuthcUseArt } = stampSetting;

							// clear message and show screen
							vm.message(null);

							// binding tabs data
							vm.buttonPage.tabs(stampSetting.pageLayouts);

							// show employee list
							vm.employeeData.employeeAuthcUseArt(!!employeeAuthcUseArt);
						} else {
							clearState();
						}
					} else {
						clearState();
					}
				})
				.then(() => vm.$ajax('at', API.HIGHTLIGHT))
				.then((data: share.StampToSuppress) => vm.buttonPage.stampToSuppress(data)) as any;
		}

		setting() {
			const vm = this;

			vm.$window
				.modal('at', DIALOG.F, { mode: 'admin' })
				.then((data: f.TimeStampLoginData) => {
					if (data) {
						// update or save login data to storage
						vm.$window.storage(KDP003_SAVE_DATA)
							.then((storage: StorageData) => {
								if (storage) {
									storage.CCD = data.em.companyCode;
									storage.CID = data.em.companyId;
									storage.PWD = data.em.password;
									storage.SCD = data.em.employeeCode;
									storage.SID = data.em.employeeId;
								} else {
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

						return data;
					}

					return false;
				})
				// check storage data
				.then((state: false | f.TimeStampLoginData) => {
					if (state === false || state.msgErrorId || state.errorMessage) {
						if (state !== false) {
							if (state.msgErrorId) {
								vm.message({
									messageId: state.msgErrorId
								});
							} else {
								vm.message(state.errorMessage);
							}
						}
						return false;
					} else {
						return vm.$window.modal('at', DIALOG.K);
					}
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
						return vm.loadData(data);
					}
				})
				// show message from login data (return by f dialog)
				.fail((message: { messageId: string }) => vm.$dialog.error(message));
		}

		stampHistory() {
			const vm = this;
			const data: EmployeeListData = ko.toJS(vm.employeeData);
			const { selectedId, employees } = data;
			const employee = _.find(employees, (e) => e.id === selectedId);
			const openDialogF = (params: f.EmployeeModeParam) => vm.$window.modal('at', DIALOG.F, params);

			vm.$window
				.storage(KDP003_SAVE_DATA)
				.then((data: StorageData) => {
					const mode = 'employee';

					if (employee) {
						return openDialogF({
							mode,
							employee,
							companyId: data.CID
						});
					} else {
						// self login
						if (data.SID === vm.$user.employeeId) {
							return vm.$ajax('com', API.NAME)
								.then((name: any) => {
									return openDialogF({
										mode,
										companyId: data.CID,
										employee: {
											id: data.SID,
											code: data.SCD,
											name
										}
									});
								}) as any;
						} else {
							// login by employeeCode
							// <mode> 一覧にない社員で打刻する
							return openDialogF({
								mode,
								companyId: data.CID,
								employee: { code: data.SCD }
							});
						}
					}
				})
				.then((data: f.TimeStampLoginData) => {
					if (data) {
						if (data.msgErrorId) {
							return vm.$dialog.error({ messageId: data.msgErrorId });
						}

						if (data.errorMessage) {
							return vm.$dialog.error(data.errorMessage);
						}

						return vm.$window.modal(DIALOG.S);
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
