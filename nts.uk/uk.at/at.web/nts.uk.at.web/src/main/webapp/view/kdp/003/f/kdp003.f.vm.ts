/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.kdp003.f {
	export interface Kdp003FCodeNameData {
		id?: string;
		code?: string;
		name?: string;
	}

	// admin mode param
	export interface Kdp003FAdminModeParam {
		mode: 'admin';
		companyDesignation?: boolean;
	}

	// employee mode param
	export interface Kdp003FEmployeeModeParam {
		mode: 'employee';
		company: Kdp003FCodeNameData;
		employee?: Kdp003FCodeNameData;
		passwordRequired?: boolean;
	}

	// finger mode param
	export interface Kdp003FFingerVeinModeParam {
		mode: 'fingerVein';
		company: Kdp003FCodeNameData;
		employee?: Kdp003FCodeNameData;
	}

	export type KDP003F_MODE = null | 'admin' | 'employee' | 'fingerVein';

	export const KDP003F_LOGINDATA = 'KDP003F_LOGINDATA';

	export const KDP003F_VM_API = {
		LOGIN_ADMIN: 'ctx/sys/gateway/kdp/login/adminmode',
		LOGIN_EMPLOYEE: 'ctx/sys/gateway/kdp/login/employeemode'
	};

	@bean()
	export class Kdp003FViewModel extends ko.ViewModel {
		mode: KnockoutObservable<KDP003F_MODE> = ko.observable(null);

		model: Kdp003FModel = new Kdp003FModel();

		listCompany: KnockoutObservableArray<Kdp003FCompanyItem> = ko.observableArray([]);

		constructor(private params?: Kdp003FAdminModeParam | Kdp003FEmployeeModeParam | Kdp003FFingerVeinModeParam) {
			super();
		}

		public created() {
			const vm = this;
			const { params } = vm;

			if (!params) {
				vm.params = {} as any;
			}

			vm.$window.storage(KDP003F_LOGINDATA)
				.then((data: Kdp003FModelData) => {
					if (data) {
						if (data.companyId) {
							vm.model.companyId(data.companyId);
						}

						if (data.companyCode) {
							vm.model.companyCode(data.companyCode);

							_.extend(vm.params, {
								companyDesignation: true
							});
						}

						if (data.employeeId) {
							vm.model.employeeId(data.employeeId);
						}

						if (data.employeeCode) {
							vm.model.employeeCode(data.employeeCode);
						}
					}
				})
				// get mode from params or set default
				.always(() => vm.mode(vm.params.mode || 'admin'));
		}

		public mounted() {
			const vm = this;
			const { model, params } = vm;

			if (params) {
				const { company, employee } = params as Kdp003FEmployeeModeParam;

				if (company) {
					model.companyId(company.id);
					model.companyCode(company.code);
					model.companyName(company.name);
				}

				if (employee) {
					model.employeeId(employee.id);
					model.employeeCode(employee.code);
					model.employeeName(employee.name);
				}
			}
		}

		public submitLogin() {
			const vm = this;
			const { params } = vm;
			const { LOGIN_ADMIN, LOGIN_EMPLOYEE } = KDP003F_VM_API;

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
			const { passwordRequired } = vm.params as Kdp003FEmployeeModeParam;
			const model: Kdp003FModelData = ko.toJS(vm.model);
			const { password } = model;

			if (passwordRequired === false) {
				_.omit(model, ['password']);
			}

			vm.$blockui('show')
				.then(() => vm.$ajax(api, model))
				.then((response: Kdp003FTimeStampLoginData) => {
					const { successMsg } = response;

					if (!!successMsg) {
						return vm.$dialog
							.info({ messageId: successMsg })
							.then(() => response);
					}

					return response;
				})
				.then((response: Kdp003FTimeStampLoginData) => {
					vm.$window
						.storage(KDP003F_LOGINDATA, _.chain(model).clone().omit(['password']).value());

					return response;
				})
				.then((response: Kdp003FTimeStampLoginData) => {
					_.extend(response.em, {
						password
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
		}

		cancelLogin() {
			const vm = this;

			vm.$window.close();
		}
	}

	export interface Kdp003FCompanyItem {
		companyId: string;
		companyCode: string;
		companyName: string;
		contractCd?: string;
		icCardStamp?: boolean
		selectUseOfName?: boolean;
		fingerAuthStamp?: boolean;
	}

	export interface Kdp003FEmployeeData {
		companyId: string;
		personalId: string;
		employeeId: string;
		employeeCode: string;
	}

	export interface Kdp003FTimeStampLoginData {
		showChangePass: boolean;
		msgErrorId: string;
		showContract: boolean;
		result: boolean;
		em: Kdp003FEmployeeData;
		successMsg: string;
		errorMessage: string;
	}

	export interface Kdp003FModelData {
		companyId: string;
		companyCode: string;
		employeeId: string;
		employeeCode: string;
		password: string;
	}

	export class Kdp003FModel {
		companyId: KnockoutObservable<string> = ko.observable('');
		companyCode: KnockoutObservable<string> = ko.observable('');
		companyName: KnockoutObservable<string> = ko.observable('');

		employeeId: KnockoutObservable<string> = ko.observable('');
		employeeCode: KnockoutObservable<string> = ko.observable('');
		employeeName: KnockoutObservable<string> = ko.observable('');

		password: KnockoutObservable<string> = ko.observable('');

		constructor(params?: Kdp003FModelData) {
			const model = this;

			if (params) {
				model.companyCode(params.companyCode);
			}
		}
	}
}