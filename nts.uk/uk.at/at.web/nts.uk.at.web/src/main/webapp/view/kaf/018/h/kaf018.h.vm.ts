 /// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf018.h.viewmodel {
	import ApprSttExecutionDto = nts.uk.at.view.kaf018.b.viewmodel.ApprSttExecutionDto;
	import ClosureItem = nts.uk.at.view.kaf018.a.viewmodel.ClosureItem;

    @bean()
    class Kaf018HViewModel extends ko.ViewModel {
		closureItem: ClosureItem;
		startDate: string;
		endDate: string;
		wkpInfo: ApprSttExecutionDto;
		confirmMode: KnockoutObservable<boolean> = ko.observable(true);
		empLstStr: KnockoutObservable<string> = ko.observable("");
		confirmEmp: string;
		confirmDate: string;
		
        created(params: KAF018HParam) {
			const vm = this;
			vm.closureItem = params.closureItem;
			vm.startDate = params.startDate;
			vm.endDate = params.endDate;
			vm.wkpInfo = params.wkpInfo;
			vm.confirmMode(params.displayConfirm);
			vm.confirmEmp = params.confirmEmp;
			vm.confirmDate = params.confirmDate;
			vm.$blockui('show');
			vm.$ajax(`${API.getEmploymentConfirmInfo}/${params.wkpInfo.wkpID}`).done((data: any) => {
				if(!_.isEmpty(data)) {
					vm.empLstStr(_.chain(data).map((o: any) => o.sname).join(vm.$i18n('KAF018_504')).value());		
				}
			}).always(() => {
				if(vm.confirmMode()) {
					$('#kaf018-release').focus();
				} else {
					$('#kaf018-confirm').focus();
				}
				vm.$blockui('hide');	
			});
        }

		activeConfirm() {
			const vm = this;
			let	wsParam = 
			{
				workPlaceId: vm.wkpInfo.wkpID,
				closureId: vm.closureItem.closureId,
				currentMonth: vm.closureItem.processingYm,
				confirmEmployment: vm.confirmMode()
			};
			vm.$blockui('show');
			vm.$ajax(API.activeConfirm, [wsParam]).done(() => {
				vm.$window.close({isActiveConfirm: true});
			}).always(() => vm.$blockui('hide'));
		}

		close() {
			const vm = this;
			vm.$window.close({});
		}
    }

	export interface KAF018HParam {
		closureItem: ClosureItem;
		startDate: string;
		endDate: string;
		wkpInfo: ApprSttExecutionDto;
		displayConfirm: boolean;
		confirmEmp: string;
		confirmDate: string;
	}

    const API = {
		getEmploymentConfirmInfo: "at/request/application/approvalstatus/getEmploymentConfirmInfo",
		activeConfirm: "screen/at/kdl006/save"
    }
}