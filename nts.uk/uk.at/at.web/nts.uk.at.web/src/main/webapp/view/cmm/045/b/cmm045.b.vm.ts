 /// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module cmm045.b.viewmodel {
	import vmbase = cmm045.shr.vmbase;

    @bean()
    class Cmm045BViewModel extends ko.ViewModel {
		applicantName: KnockoutObservable<string> = ko.observable("");
		appName: KnockoutObservable<string> = ko.observable("");
		appDate: KnockoutObservable<string> = ko.observable("");
		isPreError: KnockoutObservable<boolean> = ko.observable(true);
		topComment: KnockoutObservable<string> = ko.observable("");
		appContent: KnockoutObservable<string> = ko.observable("");
		bottomComment: KnockoutObservable<string> = ko.observable("");
		isMulti: KnockoutObservable<boolean> = ko.observable(true);
				
        created() {
			const vm = this;
			let params = nts.uk.ui.windows.getShared('CMM045B_PARAMS');
			vm.applicantName(params.applicantName);
			vm.appName(params.appName);
			vm.appDate(moment(params.appDate,'YYYY/MM/DD').format('YYYY/MM/DD(dd)'));
			vm.isMulti(params.isMulti);
			vm.appContent(_.escape(params.appContent).replace(/\n/g, '<br/>'));
			if(params.opBackgroundColor=='bg-pre-application-excess') {
				vm.topComment(_.escape(vm.$i18n('CMM045_72')).replace(/\n/g, '<br/>'));
				vm.bottomComment(vm.$i18n('CMM045_73'));
				vm.isPreError(true);
			} else {
				vm.topComment(_.escape(vm.$i18n('CMM045_74')).replace(/\n/g, '<br/>'));
				vm.bottomComment(vm.$i18n('CMM045_75'));
				vm.isPreError(false);
			}
			vm.$nextTick(() => {
				$("#cmm045-b-btn-confirm").focus();
			});
        }

		confirm() {
			const vm = this;
			nts.uk.ui.windows.setShared('CMM045B_RESULT', vmbase.ConfirmDialog.CONFIRM);
			vm.$window.close({});
		}

		notConfirm() {
			const vm = this;
			nts.uk.ui.windows.setShared('CMM045B_RESULT', vmbase.ConfirmDialog.NOT_CONFIRM);
			vm.$window.close({});
		}
		
		confirmAll() {
			const vm = this;
			nts.uk.ui.windows.setShared('CMM045B_RESULT', vmbase.ConfirmDialog.CONFIRM_ALL);
			vm.$window.close({});
		}
		
		notConfirmAll() {
			const vm = this;
			nts.uk.ui.windows.setShared('CMM045B_RESULT', vmbase.ConfirmDialog.NOT_CONFIRM_ALL);
			vm.$window.close({});
		}
    }

    const API = {
		getEmploymentConfirmInfo: "at/request/application/approvalstatus/getEmploymentConfirmInfo",
		activeConfirm: "screen/at/kdl006/save"
    }
}