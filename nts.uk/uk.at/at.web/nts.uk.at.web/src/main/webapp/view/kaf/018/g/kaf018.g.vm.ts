 /// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf018.g.viewmodel {
	import EmpConfirmInfo = nts.uk.at.view.kaf018.f.viewmodel.EmpConfirmInfo;

    @bean()
    class Kaf018GViewModel extends ko.ViewModel {
		dataSource1: Array<EmpDateConfirmContent> = [];
		dataSource2: Array<EmpDateConfirmContent> = [];
		startDate: string;
		endDate: string;
		currentEmpInfo: KnockoutObservable<EmpConfirmInfo> = ko.observable(null);
		empInfoLst: Array<EmpConfirmInfo> = [];
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
		
		created(params: KAF018GParam) {
			const vm = this;
			vm.dataSource1.push(new EmpDateConfirmContent("1"));
			vm.dataSource1.push(new EmpDateConfirmContent("2"));
			for(let i=1; i<=18; i++) {
				vm.dataSource2.push(new EmpDateConfirmContent(i.toString()));	
			}
			vm.$blockui('show');
			vm.startDate = params.startDate;
			vm.endDate = params.endDate;
			vm.empInfoLst = params.empInfoLst;
			vm.currentEmpInfo(_.find(vm.empInfoLst, o => o.empID == params.currentEmpID));
			vm.refreshDataSource().then(() => {
				$("#kaf018-e-cancel-btn").focus();	
			});
		}
		
		createIggrid1() {
			const vm = this;
			$("#eGrid1").igGrid({
				height: 66,
				width: window.innerWidth - 40,
				dataSource: vm.dataSource1,
				primaryKey: 'rootID',
				primaryKeyDataType: 'string',
				hidePrimaryKey: true,
				virtualization: true,
				virtualizationMode: 'continuous',
				dataRendered: () => {
					// vm.updateCellStyles();
				},
				rendered: () => {
					vm.$nextTick(() => {
						vm.$blockui('hide');
					});  
			    },
				columns: [
					{ headerText: "", key: 'rootID', width: 1, hidden: true },
					{ headerText: vm.$i18n('KAF018_385'), key: 'dateStr', width: 180 },
					{ headerText: vm.$i18n('KAF018_383'), key: 'confirmStt', width: 150 },
					{ headerText: vm.$i18n('KAF018_384'), key: 'apprStt', width: 150 },
					{ headerText: vm.$i18n('KAF018_388'), key: 'approvalStatus', width: 150 },
					{ headerText: vm.$i18n('KAF018_389'), key: 'phase1', width: 250 },
					{ headerText: vm.$i18n('KAF018_390'), key: 'phase2', width: 250 },
					{ headerText: vm.$i18n('KAF018_391'), key: 'phase3', width: 250 },
					{ headerText: vm.$i18n('KAF018_392'), key: 'phase4', width: 250 },
					{ headerText: vm.$i18n('KAF018_393'), key: 'phase5', width: 267 }
				],
				features: [
					{
						name: 'ColumnFixing', 
						fixingDirection: 'left',
						showFixButtons: false,
						columnSettings: [
							{ columnKey: 'dateStr', isFixed: true },
							{ columnKey: 'confirmStt', isFixed: true },
							{ columnKey: 'apprStt', isFixed: true },
							{ columnKey: 'approvalStatus', isFixed: true }
						]
					}
				]
			});
		}
		
		createIggrid2() {
			const vm = this;
			$("#eGrid2").igGrid({
				height: 434,
				width: window.innerWidth - 40,
				dataSource: vm.dataSource2,
				primaryKey: 'rootID',
				primaryKeyDataType: 'string',
				hidePrimaryKey: true,
				virtualization: true,
				virtualizationMode: 'continuous',
				dataRendered: () => {
					// vm.updateCellStyles();
				},
				rendered: () => {
					vm.$nextTick(() => {
						vm.$blockui('hide');
					});  
			    },
				columns: [
					{ headerText: "", key: 'rootID', width: 1, hidden: true },
					{ headerText: vm.$i18n('KAF018_385'), key: 'dateStr', width: 180 },
					{ headerText: vm.$i18n('KAF018_383'), key: 'confirmStt', width: 150 },
					{ headerText: vm.$i18n('KAF018_384'), key: 'apprStt', width: 150 },
					{ headerText: vm.$i18n('KAF018_388'), key: 'approvalStatus', width: 150 },
					{ headerText: vm.$i18n('KAF018_389'), key: 'phase1', width: 250 },
					{ headerText: vm.$i18n('KAF018_390'), key: 'phase2', width: 250 },
					{ headerText: vm.$i18n('KAF018_391'), key: 'phase3', width: 250 },
					{ headerText: vm.$i18n('KAF018_392'), key: 'phase4', width: 250 },
					{ headerText: vm.$i18n('KAF018_393'), key: 'phase5', width: 267 }
				],
				features: [
					{
						name: 'ColumnFixing', 
						fixingDirection: 'left',
						showFixButtons: false,
						columnSettings: [
							{ columnKey: 'dateStr', isFixed: true },
							{ columnKey: 'confirmStt', isFixed: true },
							{ columnKey: 'apprStt', isFixed: true },
							{ columnKey: 'approvalStatus', isFixed: true }
						]
					}
				]
			});
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
			vm.createIggrid1();
			vm.createIggrid2();
			return vm.$ajax(API.getApprSttStartByEmpDate, wsParam).done((data: Array<any>) => {
//				vm.dataSource = _.map(data, o => new EmpDateContent(o, vm));
//				vm.createIggrid();
			});
		}
    }

	export interface KAF018GParam {
		empInfoLst: Array<EmpConfirmInfo>;
		startDate: string;
		endDate: string;
		currentEmpID: string;
	}
	
	class EmpDateConfirmContent {
		rootID: string;
		dateStr: string;
		confirmStt: number;
		apprStt: number;
		approvalStatus: string;
		phase1: string;
		phase2: string;
		phase3: string;
		phase4: string;
		phase5: string;
		constructor(dateStr: string) {
			this.dateStr = dateStr;	
		}
	}

    const API = {
		getApprSttStartByEmpDate: "at/screen/application/approvalstatus/getApprSttStartByEmpDate",
    }
}