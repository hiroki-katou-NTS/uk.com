/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kdp003.a {
	import f = nts.uk.at.kdp003.f;
	import k = nts.uk.at.kdp003.k;
	import share = nts.uk.at.view.kdp.share;

	const API = {
		NAME: '/sys/portal/webmenu/username',
		SETTING: '/at/record/stamp/management/personal/startPage',
		HIGHTLIGHT: 'at/record/stamp/management/personal/stamp/getHighlightSetting',
		LOGIN_ADMIN: 'ctx/sys/gateway/kdp/login/adminmode',
		LOGIN_EMPLOYEE: 'ctx/sys/gateway/kdp/login/employeemode',
		COMPANIES: '/ctx/sys/gateway/kdp/login/getLogginSetting',
		FINGER_STAMP_SETTING: 'at/record/stamp/finger/get-finger-stamp-setting',
		CONFIRM_STAMP_INPUT: '/at/record/stamp/employment/system/confirm-use-of-stamp-input',
		EMPLOYEE_LIST: '/at/record/stamp/employment/in-workplace',
		REGISTER: '/at/record/stamp/employment/system/register-stamp-input',
		NOW: '/server/time/now'
	};

	const DIALOG = {
		F: '/view/kdp/003/f/index.xhtml',
		K: '/view/kdp/003/k/index.xhtml',
		S: '/view/kdp/003/s/index.xhtml',
		KDP002_B: '/view/kdp/002/b/index.xhtml',
		KDP002_C: '/view/kdp/002/c/index.xhtml',
		KDP002_T: '/view/kdp/002/t/index.xhtml'
	};

	const KDP003_SAVE_DATA = 'loginKDP003';

	@bean()
	export class ViewModel extends ko.ViewModel {
		// Message
		// logingin: undefined
		// login false: has messageId
		// login success: null
		message: KnockoutObservable<f.Message | string | null | undefined | false> = ko.observable(undefined);

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
			nameSelectArt: ko.observable(false),
			baseDate: ko.observable(null)
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

		fingerStampSetting: KnockoutObservable<FingerStampSetting> = ko.observable(DEFAULT_SETTING);

		created() {
			const vm = this;

			// show or hide stampHistoryButton
			vm.message.subscribe((value) => {
				vm.showClockButton.company(value === null);
			});

			vm.$ajax('at', API.NOW)
				.then((c) => {
					const date = moment(c, 'YYYY-MM-DDTHH:mm:ss.zzzZ').toDate();

					vm.employeeData.baseDate(date);
				});

			// reload employee list after change baseDate
			vm.employeeData.baseDate.subscribe((d: Date) => {
				if (!_.isDate(d)) {
					return;
				}

				vm.$window.storage(KDP003_SAVE_DATA)
					.then((data: undefined | StorageData) => {
						if (data) {
							vm.loadEmployees(data);
						}
					});
			});
		}

		mounted() {
			const vm = this;
			const { storage } = vm.$window;

			$(window).trigger('resize');

			return $.Deferred()
				.resolve(true)
				.then(() => storage(KDP003_SAVE_DATA))
				.then((storageData: undefined | StorageData) => {
					if (storageData !== undefined) {
						return vm.$ajax('at', API.FINGER_STAMP_SETTING)
							.then((data: FingerStampSetting) => {
								if (data) {
									vm.fingerStampSetting(data);
								}
							})
							.then(() => storageData);
					}

					return storageData;
				})
				.then((storageData: undefined | StorageData) => {
					if (storageData === undefined) {
						return vm.$window.modal('at', DIALOG.F, { mode: 'admin' })
							.then((loginData: f.TimeStampLoginData) => ({
								loginData
							})) as JQueryPromise<LoginData>;
					}

					// data login by storage
					const {
						CCD,
						CID,
						PWD,
						SCD,
						SID
					} = storageData as StorageData;

					const loginParams: f.ModelData = {
						contractCode: '000000000000',
						companyCode: CCD,
						companyId: CID,
						employeeCode: SCD,
						employeeId: SID,
						password: PWD,
						passwordInvalid: false,
						isAdminMode: true,
						runtimeEnvironmentCreate: true
					};

					// auto login by storage data of preview login
					// <<ScreenQuery>> 打刻管理者でログインする
					return vm.$ajax('at', API.COMPANIES)
						.then((data: f.CompanyItem[]) => {
							if (!data.length || _.every(data, d => d.selectUseOfName === false)) {
								// note: ログイン失敗(打刻会社一覧が取得できない場合)
								vm.setMessage({ messageId: 'Msg_1527' });

								// UI[F2]  打刻使用可能会社の取得と判断 
								vm.showClockButton.setting(false);

								return {
									storageData
								};
							}

							vm.showClockButton.setting(true);

							return vm.$ajax('at', API.LOGIN_ADMIN, loginParams)
								.then((loginData: f.TimeStampLoginData) => ({
									loginData,
									storageData
								}));
						}) as JQueryPromise<LoginData>;
				})
				.then((data: LoginData) => {
					// if dialog f return data (first login)
					if (data.loginData && !data.loginData.msgErrorId && !data.loginData.errorMessage && !data.storageData) {
						const { loginData } = data;
						const params = { multiSelect: true };

						return vm.$window
							.modal('at', DIALOG.K, params)
							.then((workplaceData: k.Return) => ({
								loginData,
								workplaceData
							})) as JQueryPromise<LoginData>;
					}

					return data;
				})
				.then((data: LoginData) => {
					// if not return full data (first login)
					if (!data.storageData && (!data.loginData || !data.workplaceData)) {
						vm.setMessage({ messageId: 'Msg_1647' });

						return false;
					}

					const { loginData, storageData, workplaceData } = data;

					if (loginData.msgErrorId) {
						vm.setMessage({ messageId: loginData.msgErrorId });

						return false;
					}

					if (loginData.errorMessage) {
						vm.setMessage(loginData.errorMessage);

						return false;
					}

					// if login & select workspace success
					const { em } = loginData;

					if (workplaceData) {
						const { selectedId } = workplaceData;

						const storeData = {
							CCD: em.companyCode,
							CID: em.companyId,
							PWD: em.password,
							SCD: em.employeeCode,
							SID: em.employeeId,
							WKLOC_CD: '',
							WKPID: _.isString(selectedId) ? [selectedId] : selectedId
						};

						return storage(KDP003_SAVE_DATA, storeData);
					}

					return storageData;
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
				});
		}

		// ※画面起動「※起動1」より再度実行(UI処理[A5])
		// <<ScreenQuery>> 打刻入力(氏名選択)の設定を取得する
		private loadData(storage: StorageData) {
			const vm = this;
			const clearState = () => {
				vm.setMessage({ messageId: 'KDP002_2' });

				// clear tabs button
				vm.buttonPage.tabs([]);
				// remove employee list
				vm.employeeData.nameSelectArt(false);
			};

			if (!_.isObject(storage)) {
				return;
			}

			// <<Command>> 打刻入力を利用できるかを確認する
			return $.Deferred()
				.resolve(ko.toJS(vm.fingerStampSetting))
				.then((data: FingerStampSetting) => {
					if (!data.stampSetting || !data.stampResultDisplay) {
						return vm.$ajax('at', API.FINGER_STAMP_SETTING)
							.then((data: FingerStampSetting) => {
								if (data) {
									vm.fingerStampSetting(data);
								}

								return data;
							});
					}

					return data;
				})
				.then((data: FingerStampSetting) => {
					if (data) {
						const { stampSetting, stampResultDisplay } = data;

						if (!stampResultDisplay) {
							// UI[A6] 打刻利用失敗時のメッセージについて 
							// note: 打刻オプション未購入
							vm.setMessage({ messageId: 'Msg_1644' });
							return;
						}

						if (stampSetting) {
							const { nameSelectArt } = stampSetting;

							// update interval for display datetime
							vm.$date.interval(stampSetting.correctionInterval * 60000);

							// clear message and show screen
							vm.message(null);

							// binding tabs data
							vm.buttonPage.tabs(stampSetting.pageLayouts);

							// show employee list
							vm.employeeData.nameSelectArt(!!nameSelectArt);
						} else {
							clearState();
						}
					} else {
						clearState();
					}
				})
				.then(() => ({
					departure: false,
					goingToWork: false,
					goOut: false,
					turnBack: false
				}))
				.then((data: share.StampToSuppress) => vm.buttonPage.stampToSuppress(data))
				// <<ScreenQuery>>: 打刻入力(氏名選択)で社員の一覧を取得する
				.then(() => vm.loadEmployees(storage)) as JQueryPromise<any>;
		}

		private loadEmployees(storage: StorageData) {
			const vm = this;
			const { baseDate } = ko.toJS(vm.employeeData) as EmployeeListData;

			if (!baseDate) {
				return;
			}

			const params = {
				baseDate: moment(baseDate).toISOString(),
				companyId: (storage || {}).CID || '',
				workplaceIds: (storage || {}).WKPID || []
			};

			return vm.$ajax('at', API.EMPLOYEE_LIST, params)
				.then((data: Employee[]) => {
					vm.employeeData.employees(data);
				}) as JQueryPromise<any>;
		}

		setting() {
			const vm = this;
			const { storage } = vm.$window;

			if (!!ko.unwrap(vm.message)) {
				vm.message(false);
			}

			storage(KDP003_SAVE_DATA)
				.then((data: StorageData) => {
					const mode = 'admin';
					const companyId = (data || {}).CID;

					return vm.$window.modal('at', DIALOG.F, { mode, companyId });
				})
				// check storage data
				.then((loginData: undefined | f.TimeStampLoginData) => {
					if (loginData === undefined) {
						return {};
					} else {
						const params = { multiSelect: true };

						return vm.$window.modal('at', DIALOG.K, params)
							.then((workplaceData: undefined | k.Return) => {
								return {
									loginData,
									workplaceData
								};
							}) as JQueryPromise<LoginData>;
					}
				})
				.then((data: LoginData) => {
					if (!data.loginData || !data.workplaceData) {
						return storage(KDP003_SAVE_DATA)
							.then((data: undefined | StorageData) => {
								if (_.isNil(data)) {
									vm.setMessage({ messageId: 'Msg_1647' });

									return false;
								}

								// reload with old data
								return data;
							});
					}

					const { loginData, workplaceData } = data;

					if (loginData.msgErrorId) {
						vm.setMessage({ messageId: loginData.msgErrorId });

						return false;
					}

					if (loginData.errorMessage) {
						vm.setMessage(loginData.errorMessage);

						return false;
					}

					// if login & select workspace success
					const { em } = loginData;
					const { selectedId } = workplaceData;

					const storageData = {
						CCD: em.companyCode,
						CID: em.companyId,
						PWD: em.password,
						SCD: em.employeeCode,
						SID: em.employeeId,
						WKLOC_CD: '',
						WKPID: _.isString(selectedId) ? [selectedId] : selectedId
					};

					return storage(KDP003_SAVE_DATA, storageData) as JQueryPromise<StorageData>;
				})
				.then((data: false | StorageData) => {
					// if login and storage data success
					if (data) {
						// data login by storage
						const {
							CCD,
							CID,
							PWD,
							SCD,
							SID
						} = data;

						const loginParams: f.ModelData = {
							contractCode: '000000000000',
							companyCode: CCD,
							companyId: CID,
							employeeCode: SCD,
							employeeId: SID,
							password: PWD,
							passwordInvalid: false,
							isAdminMode: true,
							runtimeEnvironmentCreate: true
						};

						// login again (wtf?????)
						return vm.$ajax('at', API.LOGIN_ADMIN, loginParams)
							.then(() => vm.$ajax('at', API.FINGER_STAMP_SETTING))
							.then((data: FingerStampSetting) => {
								if (data) {
									vm.fingerStampSetting(data);
								}
							})
							.then(() => vm.loadData(data)) as JQueryPromise<any>;
					}
				})
				// show message from login data (return by f dialog)
				.fail((message: { messageId: string }) => {
					vm.message(message);
				});
		}

		stampHistory() {
			const vm = this;
			const data: EmployeeListData = ko.toJS(vm.employeeData);
			const { selectedId, employees, nameSelectArt } = data;
			const employee = _.find(employees, (e) => e.employeeId === selectedId);

			if (selectedId === undefined && nameSelectArt) {
				return vm.$dialog.error({ messageId: 'Msg_1646' });
			}

			vm.$window
				.storage(KDP003_SAVE_DATA)
				.then((data: StorageData) => {
					// login by employeeCode
					// <mode> 一覧にない社員で打刻する
					return vm.$window.modal('at', DIALOG.F, {
						mode: 'employee',
						companyId: data.CID,
						employee: employee ? { id: employee.employeeId, code: employee.employeeCode, name: employee.employeeName } : null
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

						return vm.$window.modal('at', DIALOG.S, { employeeId: data.em.employeeId });
					}
				});
		}

		stampButtonClick(btn: share.ButtonSetting, layout: share.PageLayout) {
			const vm = this;
			const { buttonPage, employeeData } = vm;
			const { selectedId, employees, nameSelectArt } = ko.toJS(employeeData) as EmployeeListData;
			const reloadSetting = () =>
				$.Deferred()
					.resolve(true)
					.then(() => ({
						departure: false,
						goingToWork: false,
						goOut: false,
						turnBack: false
					}))
					.then((data: any) => {
						const oldData = ko.unwrap(buttonPage.stampToSuppress);

						if (!_.isEqual(data, oldData)) {
							buttonPage.stampToSuppress(data);
						} else {
							buttonPage.stampToSuppress.valueHasMutated();
						}
					});

			// case: 社員一覧(A2)を選択していない場合
			if (selectedId === undefined && nameSelectArt === true) {
				return vm.$dialog.error({ messageId: 'Msg_1646' });
			}

			// case: 社員一覧(A2)を選択している場合(社員を選択) || 社員一覧(A2)を選択している場合(固有部品：PA4)、又は社員一覧が表示されていない場合
			return vm.$window.storage(KDP003_SAVE_DATA)
				.then((data: StorageData) => {
					const params: f.EmployeeModeParam | f.FingerVeinModeParam = {
						mode: selectedId || nameSelectArt === true ? 'employee' : 'fingerVein',
						companyId: (data || {}).CID
					};

					if (selectedId) {
						const employee = _.find(employees, (e) => e.employeeId === selectedId);

						if (employee) {
							const { employeeId, employeeCode, employeeName } = employee;

							_.extend(params, {
								employee: {
									id: employeeId,
									code: employeeCode,
									name: employeeName
								}
							});
						}
					}

					return vm.$window.modal('at', DIALOG.F, params);
				})
				.then((data: f.TimeStampLoginData) => {
					if (data && !data.msgErrorId && !data.errorMessage) {

						if (data.em) {
							const mode: number = 1;
							const { employeeId, employeeCode } = data.em;
							const fingerStampSetting = ko.unwrap(vm.fingerStampSetting);
							const employeeInfo = { mode, employeeId, employeeCode };
							// shorten name
							const { modal, storage } = vm.$window;

							if (fingerStampSetting) {
								vm.$ajax(API.REGISTER, {
									employeeId,
									dateTime: moment(vm.$date.now()).format(),
									stampButton: {
										pageNo: layout.pageNo,
										buttonPositionNo: btn.btnPositionNo
									},
									refActualResult: {
										cardNumberSupport: '',
										workLocationCD: '',
										workTimeCode: '',
										overtimeDeclaration: {
											overTime: 0,
											overLateNightTime: 0
										}
									}
								}).then(() => {
									const { stampResultDisplay } = fingerStampSetting;
									const { displayItemId, notUseAttr } = stampResultDisplay || { displayItemId: [], notUseAttr: 0 } as StampResultDisplay;
									const { USE } = NotUseAtr;

									vm.playAudio(btn.audioType);

									if (notUseAttr === USE && [share.ChangeClockArt.WORKING_OUT].indexOf(btn.changeClockArt) > -1) {
										return storage('KDP010_2C', displayItemId)
											.then(() => storage('infoEmpToScreenC', employeeInfo))
											.then(() => modal('at', DIALOG.KDP002_C)) as JQueryPromise<any>;
									} else {
										const { stampSetting } = fingerStampSetting;
										const { resultDisplayTime } = stampSetting;

										return storage('resultDisplayTime', resultDisplayTime)
											.then(() => storage('infoEmpToScreenB', employeeInfo))
											.then(() => modal('at', DIALOG.KDP002_B)) as JQueryPromise<any>;
									}
								})
									.fail((message: BussinessException) => {
										const { messageId, parameterIds } = message;

										vm.$dialog.error({ messageId, messageParams: parameterIds });
									});
							}
						}

						return data;
					}

					return null;
				})
				// always relead setting (color & type of all button in tab)
				.always(reloadSetting);
		}

		playAudio(type: 0 | 1 | 2) {
			const oha = '../../share/voice/0_oha.mp3';
			const otsu = '../../share/voice/1_otsu.mp3';

			if (type !== 0) {
				new Audio(type === 1 ? oha : otsu).play();
			}
		}

		setMessage(message: string | f.Message) {
			const vm = this;

			if (!ko.unwrap(vm.message)) {
				vm.message(message);
			}
		}
	}

	@handler({
		bindingName: 'kdp-error',
		validatable: true,
		virtual: false
	})
	export class MessageErrorBindingHandler implements KnockoutBindingHandler {
		update(element: any, valueAccessor: () => KnockoutObservable<f.Message | string | null | undefined>, __: KnockoutAllBindingsAccessor, vm: nts.uk.ui.vm.ViewModel): void {
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

	interface LoginData {
		loginData?: f.TimeStampLoginData;
		workplaceData?: k.Return;
		storageData?: StorageData;
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

	export interface FingerStampSetting {
		stampResultDisplay: StampResultDisplay;
		stampSetting: StampSetting;
	}

	interface StampResultDisplay {
		companyId: string;
		displayItemId: number[];
		notUseAttr: NotUseAtr;
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

	enum NotUseAtr {
		/** The use. */
		USE = 1,

		/** The not use. */
		NOT_USE = 0
	}

	enum AuthcMethod {

		// 0:ID認証
		ID_AUTHC = 0,

		// 1:ICカード認証
		IC_CARD_AUTHC = 1,

		// 2:静脈認証
		VEIN_AUTHC = 2,

		// 3:外部認証
		EXTERNAL_AUTHC = 3
	}

	enum StampMeans {
		NAME_SELECTION = 0, // 0:氏名選択
		FINGER_AUTHC = 1,   // 1:指認証打刻
		IC_CARD = 2,        // 2:ICカード打刻
		INDIVITION = 3,     //  3:個人打刻
		PORTAL = 4,         // 4:ポータル打刻
		SMART_PHONE = 5,    // 5:スマホ打刻
		TIME_CLOCK = 6,     // 6:タイムレコーダー打刻
		TEXT = 7,           // 7:テキスト受入
		RICOH_COPIER = 8    // 8:リコー複写機打刻
	}

	enum CanEngravingUsed {
		// 0 利用できる
		AVAILABLE = 0,
		// 1 打刻オプション未購入
		NOT_PURCHASED_STAMPING_OPTION = 1,
		// 2 打刻機能利用不可
		ENGTAVING_FUNCTION_CANNOT_USED = 2,
		// 3 打刻カード未登録
		UNREGISTERED_STAMP_CARD = 3
	}

	interface BussinessException {
		atTime: string;
		businessException: boolean;
		messageId: string;
		parameterIds: string[];
	}

	const DEFAULT_SETTING: FingerStampSetting = {
		stampSetting: null,
		stampResultDisplay: null
	};
}
