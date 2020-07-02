module nts.uk.at.view.kdp004.a {

	import ajax = nts.uk.request.ajax;

	export module service {
		let url = {
			startPage: 'at/record/stamp/finger/get-finger-stamp-setting',
			stampInput: 'at/record/stamp/finger/get-finger-stamp-setting',
			confirmUseOfStampInput: 'at/record/stamp/employment_system/confirm_use_of_stamp_input',
			loginAdminMode: 'ctx/sys/gateway/kdp/login/adminmode',
			loginEmployeeMode: 'ctx/sys/gateway/kdp/login/employeemode',
			fingerAuth: '',
		}

		export function startPage(): JQueryPromise<any> {
			return ajax("at", url.startPage);
		}

		export function stampInput(data): JQueryPromise<any> {
			return ajax("at", url.stampInput, data);
		}

		export function confirmUseOfStampInput(data): JQueryPromise<any> {
			return ajax("at", url.confirmUseOfStampInput, data);
		}

		export function login(isAdmin, data) {
			return ajax("at", isAdmin ? url.loginAdminMode : url.loginEmployeeMode, data);
		}

		export function fingerAuth() {
			return ajax("at", url.fingerAuth);
		}
	}

}

