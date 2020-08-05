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
		REGISTER: '/at/record/stamp/employment/system/register-stamp-input'
	};

	const DIALOG = {
		F: '/view/kdp/003/f/index.xhtml',
		K: '/view/kdp/003/k/index.xhtml',
		S: '/view/kdp/003/s/index.xhtml',
		KDP002_B: '/view/kdp/002/b/index.xhtml',
		KDP002_C: '/view/kdp/002/c/index.xhtml',
		KDP002_T: '/view/kdp/002/t/index.xhtml'
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
			nameSelectArt: ko.observable(false),
			baseDate: ko.observable(new Date())
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

		fingerStampSetting: KnockoutObservable<FingerStampSetting | null> = ko.observable(null);


		created() {
			const vm = this;

			// reload employee list after change baseDate
			vm.employeeData.baseDate.subscribe(() => {
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

			storage(KDP003_SAVE_DATA)
				.then((data: undefined | StorageData) => {
					if (data === undefined) {
						return vm.$window.modal('at', DIALOG.F, { mode: 'admin' }) as JQueryDeferred<f.TimeStampLoginData>;
					}
					// data login by storage
					const {
						CCD,
						CID,
						PWD,
						SCD,
						SID
					} = data as StorageData;

					const loginData: f.ModelData = {
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
							if (_.every(data, d => d.selectUseOfName === false)) {
								// note: ログイン失敗(打刻会社一覧が取得できない場合)
								vm.message({ messageId: 'Msg_1527' });

								// UI[F2]  打刻使用可能会社の取得と判断 
								vm.showClockButton.setting(false);

								return null;
							}

							vm.showClockButton.setting(true);

							return vm.$ajax('at', API.LOGIN_ADMIN, loginData);
						}) as JQueryDeferred<undefined | f.TimeStampLoginData>;
				})
				.then((data: null | undefined | f.TimeStampLoginData) => {
					if (data === null) {
						return false;
					} else if (data === undefined) {
						// note: ログイン失敗(キャンセル)
						vm.message({ messageId: 'Msg_1647' });

						return false;
					} else {
						// note: 利用不可モードで表示
						if (!data.msgErrorId && !data.errorMessage) {
							const { em } = data;

							return storage(KDP003_SAVE_DATA)
								.then((storeData: undefined | StorageData) => {
									// storage successful login data
									const storageData: StorageData = {
										CID: em.companyId,
										CCD: em.companyCode,
										PWD: em.password,
										SCD: em.employeeCode,
										SID: em.employeeId,
										WKLOC_CD: (storeData || {}).WKLOC_CD || '',
										WKPID: (storeData || {}).WKPID || []
									};

									return storage(KDP003_SAVE_DATA, storageData);
								})
								.then(() => true);
						} else {
							// note: ログイン失敗(打刻会社一覧が取得できない場合)
							if (!data.msgErrorId) {
								vm.message(data.errorMessage);
							} else {
								vm.message({ messageId: data.msgErrorId });
							}

							return false;
						}
					}
				})
				.then((data: boolean) => {
					if (data === false) {
						return null;
					}

					return storage(KDP003_SAVE_DATA)
						.then((data: StorageData) => {
							if (data.WKPID.length) {
								return { selectedId: data.WKPID };
							}

							// if not exist workplaceID
							return vm.$window.modal('at', DIALOG.K, { multiSelect: true });
						}) as JQueryDeferred<k.Return>;
				})
				.then((data: null | k.Return) => {
					if (data === null) {
						if (ko.unwrap(vm.message) === undefined) {
							vm.message({ messageId: 'Msg_1647' });
						}

						return false;
					}

					return storage(KDP003_SAVE_DATA)
						// update workplaceId to storage
						.then((storageData: StorageData) => {
							if (storage) {
								if (_.isArray(data.selectedId)) {
									storageData.WKPID = data.selectedId;
								} else {
									storageData.WKPID = [data.selectedId];
								}
							}

							// return data from storage
							return storage(KDP003_SAVE_DATA, storageData);
						});
				})
				.then((data: false | StorageData) => {
					if (data !== false) {
						const params: f.CommanStampInput = {
							companyId: data.CID,
							employeeCode: data.SCD,
							employeeId: data.SID,
							stampMeans: StampMeans.NAME_SELECTION
						};

						return vm.$ajax('at', API.CONFIRM_STAMP_INPUT, params)
							.then((stamp: f.ConfirmStampInput) => {
								if (stamp.used === CanEngravingUsed.ENGTAVING_FUNCTION_CANNOT_USED) {
									vm.message({
										messageId: 'Msg_1645',
										messageParams: [vm.$i18n('KDP002_2')]
									});

									return false;
								}

								return data;
							}) as JQueryDeferred<false | StorageData>;
					}

					return false;
				})
				.then((data: false | StorageData) => {
					if (data === false) {
						return false;
					}

					// if message is not set, clear it (show ui)
					if (ko.unwrap(vm.message) === undefined) {
						vm.message(null);
					}

					// login success and datas are valid
					return vm.loadData(data);
				})
				// show message from login data (return by f dialog)
				.fail((message: { messageId: string }) => {
					vm.message(message);
					vm.$dialog.error(message);
				});
		}

		// ※画面起動「※起動1」より再度実行(UI処理[A5])
		// <<ScreenQuery>> 打刻入力(氏名選択)の設定を取得する
		private loadData(storage: StorageData) {
			const vm = this;
			const clearState = () => {
				if (!ko.unwrap(vm.message)) {
					vm.message({ messageId: 'KDP002_2' });
				}

				// clear tabs button
				vm.buttonPage.tabs([]);
				// remove employee list
				vm.employeeData.nameSelectArt(false);
			};

			if (!_.isObject(storage)) {
				return;
			}

			// <<Command>> 打刻入力を利用できるかを確認する
			return vm.$ajax('at', API.FINGER_STAMP_SETTING)
				.then((data: FingerStampSetting) => {
					if (data) {
						const { stampSetting, stampResultDisplay } = data;

						if (!stampResultDisplay) {
							// UI[A6] 打刻利用失敗時のメッセージについて 
							// note: 打刻オプション未購入
							vm.message({ messageId: 'Msg_1644' });
							return;
						}

						vm.fingerStampSetting(data);

						if (stampSetting) {
							const { nameSelectArt } = stampSetting;

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
				.then(() => vm.$ajax('at', API.HIGHTLIGHT))
				.then((data: share.StampToSuppress) => vm.buttonPage.stampToSuppress(data))
				// <<ScreenQuery>>: 打刻入力(氏名選択)で社員の一覧を取得する
				.then(() => vm.loadEmployees(storage)) as JQueryDeferred<any>;
		}

		private loadEmployees(storage: StorageData) {
			const vm = this;
			const { baseDate } = ko.toJS(vm.employeeData) as EmployeeListData;
			const params = {
				baseDate: moment(baseDate).toISOString(),
				companyId: (storage || {}).CID || '',
				workplaceId: (storage.WKPID || [])[0] || ''
			};

			return vm.$ajax('at', API.EMPLOYEE_LIST, params)
				.then((data: Employee[]) => {
					vm.employeeData.employees(data);
				}) as JQueryDeferred<any>;
		}

		setting() {
			const vm = this;
			const { storage } = vm.$window;

			storage(KDP003_SAVE_DATA)
				.then((data: StorageData) => {
					const mode = 'admin';
					const companyId = (data || {}).CID;

					return vm.$window.modal('at', DIALOG.F, { mode, companyId });
				})
				.then((data: f.TimeStampLoginData) => {
					if (data) {
						const { em } = data;

						if (em) {
							// update or save login data to storage
							storage(KDP003_SAVE_DATA)
								.then((storageData: StorageData) => {
									if (storageData) {
										storageData.CCD = em.companyCode;
										storageData.CID = em.companyId;
										storageData.PWD = em.password;
										storageData.SCD = em.employeeCode;
										storageData.SID = em.employeeId;
									} else {
										storageData = {
											CCD: em.companyCode,
											CID: em.companyId,
											PWD: em.password,
											SCD: em.employeeCode,
											SID: em.employeeId,
											WKLOC_CD: '',
											WKPID: []
										};
									}

									storage(KDP003_SAVE_DATA, storageData);
								});
						}

						return data;
					}

					return false;
				})
				// check storage data
				.then((state: false | f.TimeStampLoginData) => {
					if (state === false || !!state.msgErrorId || !!state.errorMessage) {
						if (state !== false) {
							if (state.msgErrorId) {
								vm.message({
									messageId: state.msgErrorId
								});
							} else {
								vm.message(state.errorMessage);
							}
						} else {
							vm.message({
								messageId: 'Msg_1647'
							});
						}

						return false;
					} else {
						return vm.$window.modal('at', DIALOG.K, { multiSelect: true });
					}
				})
				.then((data: null | k.Return) => {
					if (!data) {
						return false;
					} else {
						return storage(KDP003_SAVE_DATA)
							// update workplaceId to storage
							.then((storageData: StorageData) => {
								if (storageData) {
									if (_.isArray(data.selectedId)) {
										storageData.WKPID = data.selectedId;
									} else {
										storageData.WKPID = [data.selectedId];
									}
								}
								// return data from storage
								return storage(KDP003_SAVE_DATA, storageData);
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
			const employee = _.find(employees, (e) => e.employeeId === selectedId);

			vm.$window
				.storage(KDP003_SAVE_DATA)
				.then((data: StorageData) => {
					// login by employeeCode
					// <mode> 一覧にない社員で打刻する
					return vm.$window.modal('at', DIALOG.F, {
						mode: 'employee',
						companyId: data.CID,
						employee: employee ? { code: employee.employeeCode, name: employee.employeeName } : { code: data.SCD }
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
			const { selectedId, employees, employeeAuthcUseArt } = ko.toJS(employeeData) as EmployeeListData;
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
			if (selectedId === undefined && employeeAuthcUseArt === true) {
				return vm.$dialog.error({ messageId: 'Msg_1646' });
			}

			// case: 社員一覧(A2)を選択している場合(社員を選択) || 社員一覧(A2)を選択している場合(固有部品：PA4)、又は社員一覧が表示されていない場合
			return vm.$window.storage(KDP003_SAVE_DATA)
				.then((data: StorageData) => {
					const params: f.EmployeeModeParam | f.FingerVeinModeParam = {
						mode: selectedId || employeeAuthcUseArt === true ? 'employee' : 'fingerVein',
						companyId: (data || {}).CID || vm.$user.companyId
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
									const { displayItemId, notUseAttr } = stampResultDisplay || { displayItemId: [], notUseAttr: 0 };
									const { USE } = NotUseAtr;
									const { WORKING_OUT, TEMPORARY_LEAVING } = share.ChangeClockArt;

									vm.playAudio(btn.audioType);

									if (notUseAttr === USE && [WORKING_OUT, TEMPORARY_LEAVING].indexOf(btn.changeClockArt) > -1) {
										return storage('KDP010_2C', displayItemId)
											.then(() => storage('infoEmpToScreenC', employeeInfo))
											.then(() => modal('at', DIALOG.KDP002_C)) as JQueryDeferred<any>;
									} else {
										const { stampSetting } = fingerStampSetting;
										const { resultDisplayTime } = stampSetting;

										return storage('resultDisplayTime', resultDisplayTime)
											.then(() => storage('infoEmpToScreenB', employeeInfo))
											.then(() => modal('at', DIALOG.KDP002_B)) as JQueryDeferred<any>;
									}
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
}
