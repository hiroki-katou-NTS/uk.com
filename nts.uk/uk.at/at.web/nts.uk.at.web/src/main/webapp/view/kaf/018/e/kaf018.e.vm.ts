 /// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf018.e.viewmodel {
	import EmpInfo = nts.uk.at.view.kaf018.d.viewmodel.EmpInfo;

	@bean()
	class Kaf018EViewModel extends ko.ViewModel {
		dataSource: Array<EmpDateContent> = [];
		startDate: string;
		endDate: string;
		currentEmpInfo: KnockoutObservable<EmpInfo> = ko.observable(null);
		empInfoLst: Array<EmpInfo> = [];
		enableBack: KnockoutObservable<boolean> = ko.pureComputed(() => {
			const vm = this;
			let index = _.indexOf(vm.empInfoLst, vm.currentEmpInfo())
			if(index > 0) {
				return true;
			} else {
				return false;
			}	
		});
		enableNext: KnockoutObservable<boolean> = ko.pureComputed(() => {
			const vm = this;
			let index = _.indexOf(vm.empInfoLst, vm.currentEmpInfo());
			if(index < vm.empInfoLst.length-1) {
				return true;
			} else {
				return false;
			}
		});
		
		created(params: KAF018EParam) {
			const vm = this;
			vm.startDate = params.startDate;
			vm.endDate = params.endDate;
			vm.empInfoLst = params.empInfoLst;
			vm.currentEmpInfo(_.head(vm.empInfoLst));
			$("#dpGrid").igGrid({
				height: 501,
				width: screen.availWidth - 70,
				dataSource: vm.dataSource,
				virtualization: true,
				virtualizationMode: 'continuous',
				dataRendered: () => {
					vm.$nextTick(() => {
						vm.$blockui('hide');
					});
				},
				rendered: () => {
					vm.$nextTick(() => {
						vm.$blockui('show');
					});
			    },
				columns: [
					{ headerText: vm.$i18n('KAF018_385'), key: 'dateStr', width: 150 },
					{ headerText: vm.$i18n('KAF018_383'), key: 'appType', width: 150 },
					{ headerText: vm.$i18n('KAF018_384'), key: 'prePostAtr', width: 100 },
					{ headerText: vm.$i18n('KAF018_386'), key: 'content', width: 600 },
					{ headerText: vm.$i18n('KAF018_387'), key: 'reflectedState', width: 150 },
					{ headerText: vm.$i18n('KAF018_388'), key: 'approvalStatus', width: 150 },
					{ headerText: vm.$i18n('KAF018_389'), key: 'phase1', width: 250 },
					{ headerText: vm.$i18n('KAF018_390'), key: 'phase2', width: 250 },
					{ headerText: vm.$i18n('KAF018_391'), key: 'phase3', width: 250 },
					{ headerText: vm.$i18n('KAF018_392'), key: 'phase4', width: 250 },
					{ headerText: vm.$i18n('KAF018_393'), key: 'phase5', width: 250 }
				],
				features: [
					{
						name: 'ColumnFixing', 
						fixingDirection: 'left',
						showFixButtons: false,
						columnSettings: [
							{ columnKey: 'dateStr', isFixed: true },
							{ columnKey: 'appType', isFixed: true },
							{ columnKey: 'prePostAtr', isFixed: true }
						]
					}
				],
			});
			vm.refreshDataSource();
		}

		close() {
			const vm = this;
			vm.$window.close({});
		}
		
		back() {
			const vm = this;
			let index = _.indexOf(vm.empInfoLst, vm.currentEmpInfo());
			if (index > 0) {
				vm.currentEmpInfo(vm.empInfoLst[index - 1]);
				vm.refreshDataSource();
			}
		}

		next() {
			const vm = this;
			let index = _.indexOf(vm.empInfoLst, vm.currentEmpInfo());
			if (index < (vm.empInfoLst.length-1)) {
				vm.currentEmpInfo(vm.empInfoLst[index + 1]);
				vm.refreshDataSource();
			}
		}
		
		refreshDataSource() {
			const vm = this;
			let empID = vm.currentEmpInfo().empID,
				startDate = vm.startDate,
				endDate = vm.endDate,
				wsParam = { empID, startDate, endDate };
			vm.$blockui('show');
			vm.$ajax(API.getApprSttStartByEmpDate, wsParam).done((data) => {
				vm.dataSource = data;
				$("#dpGrid").igGrid("option", "dataSource", vm.dataSource);
			});
		}
	}
	
	export interface KAF018EParam {
		empInfoLst: Array<EmpInfo>;
		startDate: string;
		endDate: string;
	}
	
	interface EmpDateContent {
		dateStr: string;
		appType: number;
		prePostAtr: number;
		content: string;
		reflectedState: string;
		approvalStatus: string;
		phase1: string;
		phase2: string;
		phase3: string;
		phase4: string;
		phase5: string;
	}

	const API = {
		getApprSttStartByEmpDate: "at/request/application/approvalstatus/getApprSttStartByEmpDate",
	}
}