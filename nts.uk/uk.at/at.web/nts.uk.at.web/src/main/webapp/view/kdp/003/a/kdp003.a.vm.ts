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
		COMPANIES: '/ctx/sys/gateway/kdp/login/getLogginSetting',
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
			selectedId: ko.observable(undefined),
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

			vm.$ajax('at', API.COMPANIES)
				.then((data: f.CompanyItem[]) => {
					// UI[F2]  打刻使用可能会社の取得と判断 
					if (data.every(e => e.fingerAuthStamp === false)) {
						vm.showClockButton.setting(false);
					} else {
						vm.showClockButton.setting(true);
					}

					return vm.$window.storage(KDP003_SAVE_DATA);
				})
				// get storage save preview login data
				.then((data: StorageData) => {
					if (!data) {
						// <<ScreenQuery>> 打刻管理者でログインする
						const showLoginDialog = () => vm.$window
							.modal('at', DIALOG.F, { mode: 'admin' });

						// if not has storage (first login)
						// open f dialog and login
						return vm.$blockui('show').then(showLoginDialog);
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
						} else {
							if (!loginData.msgErrorId) {
								vm.message(loginData.errorMessage);
							} else {
								vm.message({ messageId: loginData.msgErrorId });
							}
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
						// <<ScreenQuery>> 打刻管理者でログインする
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

								// return data from storage
								return vm.$window.storage(KDP003_SAVE_DATA, storage);
							});
					}
				})
				.then((data: false | StorageData) => {
					// if login and storage data success
					if (data) {
						return vm.loadData(data);
					}
				})
				// show message from login data (return by f dialog)
				.fail((message: { messageId: string }) => {
					vm.message(message);
					vm.$dialog.error(message);
				})
				.always(() => vm.$blockui('clear'));
		}

		// ※画面起動「※起動1」より再度実行(UI処理[A5])
		// <<ScreenQuery>> 打刻入力(氏名選択)の設定を取得する 
		loadData(data: StorageData) {
			const vm = this;
			const clearState = () => {
				if (!ko.unwrap(vm.message)) {
					vm.message({ messageId: 'KDP002_2' });
				}

				// clear tabs button
				vm.buttonPage.tabs([]);
				// remove employee list
				vm.employeeData.employeeAuthcUseArt(false);
			};

			// <<Command>> 打刻入力を利用できるかを確認する
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
				.then((data: share.StampToSuppress) => vm.buttonPage.stampToSuppress(data))
				// <<ScreenQuery>>: 打刻入力(氏名選択)で社員の一覧を取得する
				.then(() => { }) as JQueryDeferred<any>;
		}

		setting() {
			const vm = this;

			vm.$window.storage(KDP003_SAVE_DATA)
				.then((data: StorageData) => {
					return vm.$window
						.modal('at', DIALOG.F, {
							mode: 'admin',
							companyId: (data || {}).CID
						});
				})
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
					if (state === false || !!state.msgErrorId || !!state.errorMessage) {
						debugger;
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
								// return data from storage
								return vm.$window.storage(KDP003_SAVE_DATA, storage);
							});
					}
				})
				.then((data: false | StorageData) => {
					// if login and storage data success
					if (data) {
						return vm.loadData(data);
					}
				})
				// show message from login data (return by f dialog)
				.fail((message: { messageId: string }) => {
					vm.message(message);
					vm.$dialog.error(message);
				});
		}

		stampHistory() {
			const vm = this;
			const data: EmployeeListData = ko.toJS(vm.employeeData);
			const { selectedId, employees } = data;
			const employee = _.find(employees, (e) => e.id === selectedId);

			vm.$window
				.storage(KDP003_SAVE_DATA)
				.then((data: StorageData) => {
					// login by employeeCode
					// <mode> 一覧にない社員で打刻する
					return vm.$window.modal('at', DIALOG.F, {
						mode: 'employee',
						companyId: data.CID,
						employee: employee || { code: data.SCD }
					});
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
			const { buttonPage, employeeData } = vm;
			const { selectedId, employees } = ko.toJS(employeeData) as EmployeeListData;
			const reloadSetting = () => vm.$ajax('at', API.HIGHTLIGHT)
				.then((data: any) => {
					const oldData = ko.unwrap(buttonPage.stampToSuppress);

					if (!_.isEqual(data, oldData)) {
						buttonPage.stampToSuppress(data);
					} else {
						buttonPage.stampToSuppress.valueHasMutated();
					}
				});

			// case: 社員一覧(A2)を選択していない場合
			if (selectedId === undefined) {
				return vm.$dialog.error({ messageId: 'Msg_1646' });
			}

			// case: 社員一覧(A2)を選択している場合(社員を選択) || 社員一覧(A2)を選択している場合(固有部品：PA4)、又は社員一覧が表示されていない場合
			return vm.$window.storage(KDP003_SAVE_DATA)
				.then((data: StorageData) => {
					const params = {
						mode: selectedId ? 'employee' : 'fingerVein',
						companyId: (data || {}).CID || vm.$user.companyId
					};

					if (selectedId) {
						const employee = _.find(employees, (e) => e.id === selectedId);

						if (employee) {
							_.extend(params, { employee });
						}
					}

					return vm.$window.modal('at', DIALOG.F, params);
				})
				.then((data: f.TimeStampLoginData) => {
					console.log(data);
				})
				// always relead setting (color & type of all button in tab)
				.always(reloadSetting);
		}
	}

	@handler({
		bindingName: 'kdp-error',
		validatable: true,
		virtual: false
	})
	export class MessageErrorBindingHandler implements KnockoutBindingHandler {
		update(element: any, valueAccessor: () => KnockoutObservable<f.Message | string | null | undefined>, __: KnockoutAllBindingsAccessor, vm: ComponentViewModel): void {
			const $el = $(element);
			const msg = ko.unwrap(valueAccessor());

			if (!msg) {
				$el.html('');
			} else {
				if (_.isString(msg)) {
					$el.html(vm.$i18n(msg));
				} else {
					$el.html(vm.$i18n.message(msg.messageId, msg.messageParams));
				}
			}
		}
	}

	interface StorageData {
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
