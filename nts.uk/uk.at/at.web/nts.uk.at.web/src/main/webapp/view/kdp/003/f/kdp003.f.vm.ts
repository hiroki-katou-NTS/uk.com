/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.at.kdp003.f {
	export interface CodeNameData {
		id?: string;
		code?: string;
		name?: string;
	}

	// admin mode param
	export interface AdminModeParam {
		mode: 'admin';
		companyId: string;
		companyDesignation?: boolean;
	}

	// employee mode param
	export interface EmployeeModeParam {
		mode: 'employee';
		companyId: string;
		employee?: CodeNameData;
		passwordRequired?: boolean;
	}

	// finger mode param
	export interface FingerVeinModeParam {
		mode: 'fingerVein';
		companyId: string;
		employee?: CodeNameData;
	}

	export type MODE = null | 'admin' | 'employee' | 'fingerVein';

	export const LOGINDATA = 'KDP003F_LOGINDATA';

	export const API = {
		LOGIN_ADMIN: 'ctx/sys/gateway/kdp/login/adminmode',
		LOGIN_EMPLOYEE: 'ctx/sys/gateway/kdp/login/employeemode',
		COMPANIES: '/ctx/sys/gateway/kdp/login/getLogginSetting',
		CONFIRM_STAMP_INPUT: 'at/record/stamp/employment_system/confirm_use_of_stamp_input'
	};

	@bean()
	export class Kdp003FViewModel extends ko.ViewModel {
		mode: KnockoutObservable<MODE> = ko.observable(null);
		message: KnockoutObservable<Message | null> = ko.observable(null);

		model: Model = new Model();

		listCompany: KnockoutObservableArray<CompanyItem> = ko.observableArray([]);

		constructor(private params?: AdminModeParam | EmployeeModeParam | FingerVeinModeParam) {
			super();
		}

		public created() {
			const vm = this;
			const { params, model } = vm;

			if (!params) {
				vm.params = {} as any;
			} else if (params) {
				const { companyId, employee } = params as EmployeeModeParam;

				if (companyId) {
					model.companyId(companyId);
				}

				if (employee) {
					if (employee.id) {
						model.employeeId(employee.id);
					}

					if (employee.code) {
						model.employeeCode(employee.code);
					}

					if (employee.name) {
						model.employeeName(employee.name);
					}
				}
			}

			model.companyId
				.subscribe((id: string) => {
					const dataSources: CompanyItem[] = ko.toJS(vm.listCompany);

					vm.message(null);

					if (dataSources.length) {
						const exist = _.find(dataSources, (item: CompanyItem) => item.companyId === id);
						const clear = () => {
							model.companyCode('');
							model.companyName('');

							vm.message({ messageId: 'Msg_301' });
						};

						if (exist) {
							const update = () => {
								model.companyCode(exist.companyCode);
								model.companyName(exist.companyName);
							};

							// update companyId by subscribe companyCode
							switch (name) {
								case 'KDP001':
									break;
								default:
								case 'KDP002':
									break;
								case 'KDP003':
									if (exist.selectUseOfName === false) {
										clear();
									} else {
										update();
									}
									break;
								case 'KDP004':
									if (exist.fingerAuthStamp === false) {
										clear();
									} else {
										update();
									}
									break;
								case 'KDP005':
									if (exist.icCardStamp === false) {
										clear();
									} else {
										update();
									}
									break;
							}

							// UI[A6]  打刻利用失敗時のメッセージについて
							if (!ko.unwrap(vm.message)) {
								const query = (stampMeans: StampMeans) => vm.$ajax('at', API.CONFIRM_STAMP_INPUT, { stampMeans });
								const authen = (data: ConfirmStampInput) => {
									if (data.used === CanEngravingUsed.NOT_PURCHASED_STAMPING_OPTION) {
										// UI[A6]  打刻オプション未購入 
										vm.message({ messageId: 'Msg_1644' });
									} else if (data.used === CanEngravingUsed.UNREGISTERED_STAMP_CARD) {
										// UI[A6]  打刻カード未登録
										vm.message({ messageId: 'Msg_1619' });
									} else if (data.used === CanEngravingUsed.ENGTAVING_FUNCTION_CANNOT_USED) {
										// UI[A6]  打刻機能利用不可
										const messageParams = [];

										switch (name) {
											case 'KDP001':
												messageParams.push(vm.$i18n('KDP001_1'));
												break;
											default:
											case 'KDP002':
												messageParams.push(vm.$i18n('KDP002_1'));
												break;
											case 'KDP003':
												messageParams.push(vm.$i18n('KDP002_2'));
												break;
											case 'KDP004':
												messageParams.push(vm.$i18n('KDP002_3'));
												break;
											case 'KDP005':
												messageParams.push(vm.$i18n('KDP002_4'));
												break;
										}

										vm.message({
											messageId: 'Msg_1645',
											messageParams
										});
									} else {
										vm.message(null);
									}
								};

								switch (name) {
									case 'KDP001':
										query(StampMeans.PORTAL)
											.then(authen);
										break;
									default:
									case 'KDP002':
										query(StampMeans.INDIVITION)
											.then(authen);
										break;
									case 'KDP003':
										query(StampMeans.NAME_SELECTION)
											.then(authen);
										break;
									case 'KDP004':
										query(StampMeans.FINGER_AUTHC)
											.then(authen);
										break;
									case 'KDP005':
										query(StampMeans.IC_CARD)
											.then(authen);
										break;
								}
							}
						}
					}
				});
		}

		public mounted() {
			const vm = this;
			const { model, params } = vm;

			vm.$blockui('show')
				.then(() => vm.$ajax(API.COMPANIES))
				.then((data: CompanyItem[]) => {
					const companyId = ko.toJS(params.companyId);
					const exist: CompanyItem = _.find(data, (c) => c.companyId === companyId); // || (companyId ? null : _.head(data));

					vm.listCompany(data);

					if (exist) {
						if (ko.unwrap(model.companyId) !== exist.companyId) {
							model.companyId(exist.companyId);
						} else {
							model.companyId.valueHasMutated();
						}
					} else {
						if (params.mode === 'admin' && !data.length) {
							vm.$dialog
								.error({ messageId: 'Msg_1527' })
								.then(() => vm.$window.close({ msgErrorId: 'Msg_1527' }));
						} else {
							model.companyId.valueHasMutated();
						}
					}
				})
				// get mode from params or set default
				.always(() => {
					vm.$blockui('clear').then(() => vm.mode(vm.params.mode || 'admin'));
				});
		}

		public submitLogin() {
			const vm = this;
			const { params } = vm;
			const { LOGIN_ADMIN, LOGIN_EMPLOYEE } = API;

			switch (params.mode) {
				default:
				case 'admin':
					vm.loginAdmin(LOGIN_ADMIN);
					break;
				case 'employee':
				case 'fingerVein':
					vm.loginAdmin(LOGIN_EMPLOYEE);
					break;
			}
		}

		loginAdmin(api: string) {
			const vm = this;
			const { passwordRequired } = vm.params as EmployeeModeParam;
			const model: ModelData = ko.toJS(vm.model);
			const { password, companyCode } = model;

			const message = ko.unwrap(vm.message);

			if (message) {
				return vm.$dialog.error(message);
			}

			if (passwordRequired === false) {
				_.omit(model, ['password']);
			}

			vm.$validate()
				.then((valid: boolean) => {
					if (!valid) {
						return;
					}

					vm.$blockui('show')
						.then(() => vm.$ajax(api, model))
						.then((response: TimeStampLoginData) => {
							const { successMsg } = response;

							if (!!successMsg) {
								return vm.$dialog
									.info({ messageId: successMsg })
									.then(() => response);
							}

							return response;
						})
						.then((response: TimeStampLoginData) => {
							_.extend(response.em, {
								password,
								companyCode
							});

							vm.$window.close(response);
						})
						.fail((response: any) => {
							const { message, messageId } = response;

							if (!messageId) {
								vm.$dialog.error(message);
							} else {
								vm.$dialog.error({ messageId });
							}
						})
						.always(() => vm.$blockui('clear'));

				});
		}

		cancelLogin() {
			const vm = this;

			vm.$window.close();
		}
	}

	export interface Message {
		messageId: string;
		messageParams?: string[];
	}

	interface ConfirmStampInput {
		systemDate: string;
		used: CanEngravingUsed;
	}

	interface CommanStampInput {
		stampMeans: StampMeans;
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

	export interface CompanyItem {
		companyId: string;
		companyCode: string;
		companyName: string;
		contractCd?: string;
		icCardStamp?: boolean
		selectUseOfName?: boolean;
		fingerAuthStamp?: boolean;
	}

	export interface EmployeeData {
		companyId: string;
		companyCode?: string;
		personalId: string;
		employeeId: string;
		employeeCode: string;
		password?: string;
	}

	export interface TimeStampLoginData {
		showChangePass: boolean;
		msgErrorId: string;
		showContract: boolean;
		result: boolean;
		em: EmployeeData;
		successMsg: string;
		errorMessage: string;
	}

	export interface ModelData {
		companyId: string;
		companyCode: string;
		companyName?: string;
		employeeId: string;
		employeeCode: string;
		employeeName?: string;
		password?: string;
	}

	export class Model {
		companyId: KnockoutObservable<string> = ko.observable('');
		companyCode: KnockoutObservable<string> = ko.observable('');
		companyName: KnockoutObservable<string> = ko.observable('');

		employeeId: KnockoutObservable<string> = ko.observable('');
		employeeCode: KnockoutObservable<string> = ko.observable('');
		employeeName: KnockoutObservable<string> = ko.observable('');

		password: KnockoutObservable<string> = ko.observable('');

		constructor(params?: ModelData) {
			const model = this;

			if (params) {
				model.companyCode(params.companyCode);
			}
		}
	}
}