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
		
        created(params: KAF018HParam) {
			const vm = this;
			vm.closureItem = params.closureItem;
			vm.startDate = params.startDate;
			vm.endDate = params.endDate;
			vm.wkpInfo = params.wkpInfo;
        }

		activeConfirm() {
			const vm = this;
			vm.confirmMode(!vm.confirmMode());
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
	}

    const API = {
		activeConfirm: "screen/at/kdl006/save"
    }
}