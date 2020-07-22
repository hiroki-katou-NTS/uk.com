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
		companyDesignation?: boolean;
	}

	// employee mode param
	export interface EmployeeModeParam {
		mode: 'employee';
		company: CodeNameData;
		employee?: CodeNameData;
		passwordRequired?: boolean;
	}

	// finger mode param
	export interface FingerVeinModeParam {
		mode: 'fingerVein';
		company: CodeNameData;
		employee?: CodeNameData;
	}

	export type MODE = null | 'admin' | 'employee' | 'fingerVein';

	export const LOGINDATA = 'KDP003F_LOGINDATA';

	export const API = {
		LOGIN_ADMIN: 'ctx/sys/gateway/kdp/login/adminmode',
		LOGIN_EMPLOYEE: 'ctx/sys/gateway/kdp/login/employeemode',
		COMPANIES: '/ctx/sys/gateway/kdp/login/getLogginSetting'
	};

	@bean()
	export class Kdp003FViewModel extends ko.ViewModel {
		mode: KnockoutObservable<MODE> = ko.observable(null);

		model: Model = new Model();

		listCompany: KnockoutObservableArray<CompanyItem> = ko.observableArray([]);

		constructor(private params?: AdminModeParam | EmployeeModeParam | FingerVeinModeParam) {
			super();
		}

		public created() {
			const vm = this;
			const { params } = vm;

			if (!params) {
				vm.params = {} as any;
			}

			vm.$window.storage(LOGINDATA)
				.then((data: ModelData) => {
					if (data) {
						if (data.companyId) {
							vm.model.companyId(data.companyId);
						}

						if (data.companyCode) {
							vm.model.companyCode(data.companyCode);
							vm.model.companyName(data.companyName);
							_.extend(vm.params, {
								companyDesignation: true
							});
						}

						if (data.companyName) {
							vm.model.companyName(data.companyName);
						}

						if (data.employeeId) {
							vm.model.employeeId(data.employeeId);
						}

						if (data.employeeCode) {
							vm.model.employeeCode(data.employeeCode);
						}

						if (data.employeeName) {
							vm.model.employeeName(data.employeeName);
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
				const { company, employee } = params as EmployeeModeParam;

				if (company) {
					if (company.id) {
						model.companyId(company.id);
					}

					if (company.code) {
						model.companyCode(company.code);
					}

					if (company.name) {
						model.companyName(company.name);
					}
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
			const { mode } = vm.params as AdminModeParam;
			const { passwordRequired } = vm.params as EmployeeModeParam;
			const model: ModelData = ko.toJS(vm.model);
			const { password, companyCode } = model;

			if (passwordRequired === false) {
				_.omit(model, ['password']);
			}

			if (mode === 'admin' && !model.companyId && !model.companyName) {
				return vm.$dialog.error({ messageId: 'Msg_301' });
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
							vm.$window
								.storage(LOGINDATA, _.chain(model).clone().omit(['password']).value());

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