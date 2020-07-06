/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

interface Kdp003FCodeNameData {
	code?: string;
	name?: string;
}

// admin mode param
interface Kdp003FAdminModeParam {
	mode: 'admin';
	companyDesignation?: boolean;
}

// employee mode param
interface Kdp003FEmployeeModeParam {
	mode: 'employee';
	company: Kdp003FCodeNameData;
	employee?: Kdp003FCodeNameData;
	passwordRequired?: boolean;
}

// finger mode param
interface Kdp003FFingerVeinModeParam {
	mode: 'fingerVein';
	company: Kdp003FCodeNameData;
	employee?: Kdp003FCodeNameData;
}

type KDP003F_MODE = 'admin' | 'employee' | 'fingerVein';

const KDP003F_VM_API = {
	LOGIN: 'ctx/sys/gateway/kdp/login/adminmode'
};

@bean()
class Kdp003FViewModel extends ko.ViewModel {
	mode: KnockoutObservable<KDP003F_MODE> = ko.observable('admin');

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

		// get mode from params or set default
		vm.mode(params.mode || 'admin');
	}

	public mounted() {
		const vm = this;
		const { model, params } = vm;

		if (params) {
			const { company, employee } = params as Kdp003FEmployeeModeParam;

			if (company) {
				model.companyCode(company.code);
				model.companyName(company.name);
			}

			if (employee) {
				model.employeeCode(employee.code);
				model.employeeName(employee.name);
			}


		}
	}

	public submitLogin() {
		const vm = this;
		const model: Kdp003FModelData = ko.toJS(vm.model);

		vm.$blockui('show')
			.then(() => vm.$ajax(KDP003F_VM_API.LOGIN, model))
			.done((response: Kdp003FTimeStampLoginData) => {
				if (!!response.successMsg) {
					vm.$dialog
						.info({ messageId: response.successMsg })
						.then(() => vm.doSuccessLogin(response));
				} else {
					vm.doSuccessLogin(response);
				}
			}).fail((response: any) => {
				if (!response.messageId) {
					vm.$dialog.error(response.message);
				} else {
					vm.$dialog.error({ messageId: response.messageId });
				}
			})
			.always(() => {
				vm.$blockui('clear');
			});
	}

	private doSuccessLogin(messError?: Kdp003FTimeStampLoginData) {
		const vm = this;
		const model: Kdp003FModelData = ko.toJS(vm.model);

		_.omit(model, ['password', 'companyId']);

		vm.$window
			.storage('form3LoginInfo', model)
			.then(() => {
				vm.$window.close();
				// vm.$jump('com', '/view/ccg/008/a/index.xhtml', { screen: 'login' });
			});
	}

	public cancelLogin() {
		const vm = this;

		vm.$window.close();
	}
} 

interface Kdp003FCompanyItem {
	companyId: string;
	companyCode: string;
	companyName: string;
	contractCd?: string;
	icCardStamp?: boolean
	selectUseOfName?: boolean;
	fingerAuthStamp?: boolean;
}

interface Kdp003FEmployeeData {
	companyId: string;
	personalId: string;
	employeeId: string;
	employeeCode: string;
}

interface Kdp003FTimeStampLoginData {
	showChangePass: boolean;
	msgErrorId: string;
	showContract: boolean;
	result: boolean;
	em: Kdp003FEmployeeData;
	successMsg: string;
	errorMessage: string;
}

interface Kdp003FModelData {
	companyId: string;
	companyCode: string;
	employeeCode: string;
	password: string;
}

class Kdp003FModel {
	companyId: KnockoutObservable<string> = ko.observable('');
	companyCode: KnockoutObservable<string> = ko.observable('');
	companyName: KnockoutObservable<string> = ko.observable('');

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
