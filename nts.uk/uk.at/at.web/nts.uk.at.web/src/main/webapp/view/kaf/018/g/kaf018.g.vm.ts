 /// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf018.g.viewmodel {
	import EmpConfirmInfo = nts.uk.at.view.kaf018.f.viewmodel.EmpConfirmInfo;
	import ClosureItem = nts.uk.at.view.kaf018.a.viewmodel.ClosureItem;
	import ApprovalPhaseStateImport_New = nts.uk.at.view.kaf018.share.model.ApprovalPhaseStateImport_New;

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
		params: KAF018GParam = null;
		
		created(params: KAF018GParam) {
			const vm = this;
			vm.params = params;
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
			let wkpID = vm.params.currentWkpID,
				startDate = vm.startDate,
				endDate = vm.endDate,
				empID = vm.currentEmpInfo().empID,
				apprSttComfirmSet = vm.params.apprSttComfirmSet,
				yearMonth = vm.params.closureItem.processingYm,
				closureId = vm.params.closureItem.closureId,
				closureDay = vm.params.closureItem.closureDay,
				lastDayOfMonth = vm.params.closureItem.lastDayOfMonth,
				wsParam = { wkpID, startDate, endDate, empID, apprSttComfirmSet, yearMonth, closureId, closureDay, lastDayOfMonth };
			vm.$blockui('show');
			return vm.$ajax(API.getConfirmApprSttByEmpMonthDay, wsParam).done((data: ApprSttConfirmEmpMonthDay) => {
				vm.createMonthDataSource(data);
				vm.createDayDataSource(data);
			});
		}
		
		createMonthDataSource(apprSttConfirmEmpMonthDay: ApprSttConfirmEmpMonthDay) {
			const vm = this;
			let monthDataSource: Array<EmpDateConfirmContent> = [];
			let empDateConfirmContent: EmpDateConfirmContent = new EmpDateConfirmContent('');
			empDateConfirmContent.rootID = apprSttConfirmEmpMonthDay.approvalRootStateMonth.rootStateID;
			empDateConfirmContent.dateStr = moment(apprSttConfirmEmpMonthDay.approvalRootStateMonth.date,'YYYY/MM/DD').format('M/D(ddd)');
			empDateConfirmContent.confirmStt = apprSttConfirmEmpMonthDay.monthConfirm ? vm.$i18n('KAF018_533') : vm.$i18n('KAF018_534');
			empDateConfirmContent.apprStt = apprSttConfirmEmpMonthDay.monthApproval == 0 ? vm.$i18n('KAF018_535') :
				apprSttConfirmEmpMonthDay.monthApproval == 1 ? vm.$i18n('KAF018_536') :	vm.$i18n('KAF018_537');
			let phase1 = _.find(apprSttConfirmEmpMonthDay.approvalRootStateMonth.listApprovalPhaseState, (phase: any) => phase.phaseOrder==1);
			let phase2 = _.find(apprSttConfirmEmpMonthDay.approvalRootStateMonth.listApprovalPhaseState, (phase: any) => phase.phaseOrder==2);
			let phase3 = _.find(apprSttConfirmEmpMonthDay.approvalRootStateMonth.listApprovalPhaseState, (phase: any) => phase.phaseOrder==3);
			let phase4 = _.find(apprSttConfirmEmpMonthDay.approvalRootStateMonth.listApprovalPhaseState, (phase: any) => phase.phaseOrder==4);
			let phase5 = _.find(apprSttConfirmEmpMonthDay.approvalRootStateMonth.listApprovalPhaseState, (phase: any) => phase.phaseOrder==5);
			empDateConfirmContent.approvalStatus = this.getPhaseStatusStr(phase1) + this.getPhaseStatusStr(phase2) + this.getPhaseStatusStr(phase3) +
				this.getPhaseStatusStr(phase4) + this.getPhaseStatusStr(phase5);
			empDateConfirmContent.phase1 = this.getPhaseApprover(_.find(apprSttConfirmEmpMonthDay.monthApprovalLst, o => o.phaseOrder==1));
			empDateConfirmContent.phase2 = this.getPhaseApprover(_.find(apprSttConfirmEmpMonthDay.monthApprovalLst, o => o.phaseOrder==2));
			empDateConfirmContent.phase3 = this.getPhaseApprover(_.find(apprSttConfirmEmpMonthDay.monthApprovalLst, o => o.phaseOrder==3));
			empDateConfirmContent.phase4 = this.getPhaseApprover(_.find(apprSttConfirmEmpMonthDay.monthApprovalLst, o => o.phaseOrder==4));
			empDateConfirmContent.phase5 = this.getPhaseApprover(_.find(apprSttConfirmEmpMonthDay.monthApprovalLst, o => o.phaseOrder==5));
			monthDataSource.push(empDateConfirmContent);
			vm.dataSource1 = monthDataSource;
			vm.createIggrid1();
		}
		
		createDayDataSource(apprSttConfirmEmpMonthDay: ApprSttConfirmEmpMonthDay) {
			const vm = this;
			let dayDataSource: Array<EmpDateConfirmContent> = [];
			_.forEach(apprSttConfirmEmpMonthDay.approvalRootStateDayLst, item => {
				let empDateConfirmContent: EmpDateConfirmContent = new EmpDateConfirmContent('');
				empDateConfirmContent.rootID = item.rootStateID;
				empDateConfirmContent.dateStr = moment(item.date,'YYYY/MM/DD').format('M/D(ddd)');
				let dailyConfirmItem = _.find(apprSttConfirmEmpMonthDay.listDailyConfirm, o => o.targetDate==item.date);
				if(dailyConfirmItem) {
					empDateConfirmContent.confirmStt = item.personConfirm ? vm.$i18n('KAF018_533') : vm.$i18n('KAF018_534');
					empDateConfirmContent.apprStt = item.bossConfirm ? vm.$i18n('KAF018_535') : vm.$i18n('KAF018_537');
				}
				let phase1 = _.find(item.listApprovalPhaseState, (phase: any) => phase.phaseOrder==1);
				let phase2 = _.find(item.listApprovalPhaseState, (phase: any) => phase.phaseOrder==2);
				let phase3 = _.find(item.listApprovalPhaseState, (phase: any) => phase.phaseOrder==3);
				let phase4 = _.find(item.listApprovalPhaseState, (phase: any) => phase.phaseOrder==4);
				let phase5 = _.find(item.listApprovalPhaseState, (phase: any) => phase.phaseOrder==5);
				empDateConfirmContent.approvalStatus = this.getPhaseStatusStr(phase1) + this.getPhaseStatusStr(phase2) + this.getPhaseStatusStr(phase3) +
					this.getPhaseStatusStr(phase4) + this.getPhaseStatusStr(phase5);
				let dayApproval = apprSttConfirmEmpMonthDay.dayApprovalMap[item.date];
				if(dayApproval) {
					empDateConfirmContent.phase1 = this.getPhaseApprover(_.find(dayApproval, o => o.phaseOrder==1));
					empDateConfirmContent.phase2 = this.getPhaseApprover(_.find(dayApproval, o => o.phaseOrder==2));
					empDateConfirmContent.phase3 = this.getPhaseApprover(_.find(dayApproval, o => o.phaseOrder==3));
					empDateConfirmContent.phase4 = this.getPhaseApprover(_.find(dayApproval, o => o.phaseOrder==4));
					empDateConfirmContent.phase5 = this.getPhaseApprover(_.find(dayApproval, o => o.phaseOrder==5));	
				}
				dayDataSource.push(empDateConfirmContent);
			});
			vm.dataSource2 = dayDataSource;
			vm.createIggrid2();
		}
		
		getPhaseStatusStr(phase: any) {
			if(phase) {
				switch(phase.approvalAtrValue) {
					case ApprovalPhaseStateImport_New.UNAPPROVED:
						return "－";
					case ApprovalPhaseStateImport_New.APPROVED:
						return "〇";
					case ApprovalPhaseStateImport_New.DENIAL:
						return "×";
					case ApprovalPhaseStateImport_New.REMAND:
						return "－";
					case ApprovalPhaseStateImport_New.ORIGINAL_REMAND:
						return "－";
					default: 
						return " ";
				}
			} else {
				return " ";
			}	
		}
		
		getPhaseApprover(phase: any) {
			const vm = this;
			if(phase) {
				let str = '';
				str = phase.empName;
				if(phase.countRemainApprover) {
					str += vm.$i18n('KAF018_531', [phase.countRemainApprover]);
				}
				return str;	
			} else {
				return "";
			}
		}
    }

	export interface KAF018GParam {
		empInfoLst: Array<EmpConfirmInfo>;
		closureItem: ClosureItem;
		startDate: string;
		endDate: string;
		currentWkpID: string;
		apprSttComfirmSet: any;
		currentEmpID: string;
	}
	
	interface ApprSttConfirmEmpMonthDay {
		monthConfirm: boolean;
		monthApproval: number;
		approvalRootStateMonth: any;
		monthApprovalLst: Array<any>;
		listDailyConfirm: Array<any>;
		approvalRootStateDayLst: Array<any>;
		dayApprovalMap: any
	}
	
	class EmpDateConfirmContent {
		rootID: string;
		dateStr: string;
		confirmStt: string;
		apprStt: string;
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
		getConfirmApprSttByEmpMonthDay: "at/request/application/approvalstatus/getConfirmApprSttByEmpMonthDay",
    }
}