 /// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf018.g.viewmodel {
	import EmpConfirmInfo = nts.uk.at.view.kaf018.f.viewmodel.EmpConfirmInfo;
	import ClosureItem = nts.uk.at.view.kaf018.a.viewmodel.ClosureItem;
	import ApprovalPhaseStateImport_New = nts.uk.at.view.kaf018.share.model.ApprovalPhaseStateImport_New;

    @bean()
    class Kaf018GViewModel extends ko.ViewModel {
		dataSourceMonth: Array<EmpDateConfirmContent> = [];
		dataSourceDay: Array<EmpDateConfirmContent> = [];
		startDate: string;
		endDate: string;
		currentEmpInfo: KnockoutObservable<EmpConfirmInfo> = ko.observable(null);
		empInfoLst: Array<EmpConfirmInfo> = [];
		columnIggridMonth: Array<any> = [];
		columnIggridDay: Array<any> = [];
		columnIggridMonthFixed: Array<any> = [];
		columnIggridDayFixed: Array<any> = [];
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
			vm.columnIggridMonth = [
				{ headerText: "", key: 'rootID', width: 1, hidden: true },
				{ headerText: vm.$i18n('KAF018_422'), key: 'dateStr', width: 180 }
			];
			vm.columnIggridDay = [
				{ headerText: "", key: 'rootID', width: 1, hidden: true },
				{ headerText: vm.$i18n('KAF018_431'), key: 'dateStr', width: 180 }
			];
			vm.columnIggridMonthFixed = [{ columnKey: 'dateStr', isFixed: true }];
			vm.columnIggridDayFixed = [{ columnKey: 'dateStr', isFixed: true }];
			if(vm.params.apprSttComfirmSet.usePersonConfirm || vm.params.apprSttComfirmSet.monthlyIdentityConfirm) {
				vm.columnIggridMonth.push({ headerText: vm.$i18n('KAF018_423'), key: 'confirmStt', width: 150 });
				vm.columnIggridDay.push({ headerText: vm.$i18n('KAF018_432'), key: 'confirmStt', width: 150 });
				vm.columnIggridMonthFixed.push({ columnKey: 'confirmStt', isFixed: true });
				vm.columnIggridDayFixed.push({ columnKey: 'confirmStt', isFixed: true });
			}
			if(vm.params.apprSttComfirmSet.useBossConfirm || vm.params.apprSttComfirmSet.monthlyConfirm) {
				vm.columnIggridMonth.push({ headerText: vm.$i18n('KAF018_424'), key: 'apprStt', width: 150 });
				vm.columnIggridDay.push({ headerText: vm.$i18n('KAF018_433'), key: 'apprStt', width: 150 });
				vm.columnIggridMonthFixed.push({ columnKey: 'apprStt', isFixed: true });
				vm.columnIggridDayFixed.push({ columnKey: 'apprStt', isFixed: true });
			}
			vm.columnIggridMonth = _.concat(vm.columnIggridMonth, [
				{ headerText: vm.$i18n('KAF018_425'), key: 'approvalStatus', width: 150, formatter: vm.createApprovalStatus },
				{ headerText: vm.$i18n('KAF018_426'), key: 'phase5', width: 400 },
				{ headerText: vm.$i18n('KAF018_427'), key: 'phase4', width: 400 },
				{ headerText: vm.$i18n('KAF018_428'), key: 'phase3', width: 400 },
				{ headerText: vm.$i18n('KAF018_429'), key: 'phase2', width: 400 },
				{ headerText: vm.$i18n('KAF018_430'), key: 'phase1', width: 417 }	
			]);
			vm.columnIggridDay = _.concat(vm.columnIggridDay, [
				{ headerText: vm.$i18n('KAF018_434'), key: 'approvalStatus', width: 150, formatter: vm.createApprovalStatus },
				{ headerText: vm.$i18n('KAF018_435'), key: 'phase5', width: 400 },
				{ headerText: vm.$i18n('KAF018_436'), key: 'phase4', width: 400 },
				{ headerText: vm.$i18n('KAF018_437'), key: 'phase3', width: 400 },
				{ headerText: vm.$i18n('KAF018_438'), key: 'phase2', width: 400 },
				{ headerText: vm.$i18n('KAF018_439'), key: 'phase1', width: 417 }
			]);
			vm.columnIggridMonthFixed.push({ columnKey: 'approvalStatus', isFixed: true });
			vm.columnIggridDayFixed.push({ columnKey: 'approvalStatus', isFixed: true });
			vm.refreshDataSource().then(() => {
				$("#kaf018-e-cancel-btn").focus();	
			});
		}
		
		createApprovalStatus(value: any) {
			if(value) {
				return value.replace(/ /g, '&nbsp;');	
			}
			return "";
		}
		
		createIggridMonth() {
			const vm = this;
			$("#eGrid1").igGrid({
				height: 66,
				width: window.innerWidth - 40,
				dataSource: vm.dataSourceMonth,
				primaryKey: 'dateStr',
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
				columns: vm.columnIggridMonth,
				features: [
					{
						name: 'ColumnFixing', 
						fixingDirection: 'left',
						showFixButtons: false,
						columnSettings: vm.columnIggridMonthFixed
					}
				]
			});
		}
		
		createIggridDay() {
			const vm = this;
			$("#eGrid2").igGrid({
				height: 434,
				width: window.innerWidth - 40,
				dataSource: vm.dataSourceDay,
				primaryKey: 'dateStr',
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
				columns: vm.columnIggridDay,
				features: [
					{
						name: 'ColumnFixing', 
						fixingDirection: 'left',
						showFixButtons: false,
						columnSettings: vm.columnIggridDayFixed
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
				vm.createMonthDataSource(data, yearMonth);
				vm.createDayDataSource(data);
			});
		}
		
		createMonthDataSource(apprSttConfirmEmpMonthDay: ApprSttConfirmEmpMonthDay, yearMonth: any) {
			const vm = this;
			let monthDataSource: Array<EmpDateConfirmContent> = [];
			if(!_.isNull(apprSttConfirmEmpMonthDay.monthConfirm) || !_.isNull(apprSttConfirmEmpMonthDay.monthApproval)) {
				let empDateConfirmContent: EmpDateConfirmContent = new EmpDateConfirmContent('');
				empDateConfirmContent.dateStr = moment(yearMonth,'YYYYMM').format('M') + vm.$i18n('KAF018_532');
				if(!_.isNull(apprSttConfirmEmpMonthDay.monthConfirm)) {
					empDateConfirmContent.confirmStt = apprSttConfirmEmpMonthDay.monthConfirm ? vm.$i18n('KAF018_534') : vm.$i18n('KAF018_533');	
				}
				if(!_.isNull(apprSttConfirmEmpMonthDay.monthApproval)) {
					empDateConfirmContent.apprStt = apprSttConfirmEmpMonthDay.monthApproval == 0 ? vm.$i18n('KAF018_535') :
						apprSttConfirmEmpMonthDay.monthApproval == 1 ? vm.$i18n('KAF018_536') :	vm.$i18n('KAF018_537');	
				}
				if(!_.isNull(apprSttConfirmEmpMonthDay.approvalRootStateMonth)) {
					let phase1 = _.find(apprSttConfirmEmpMonthDay.approvalRootStateMonth.listApprovalPhaseState, (phase: any) => phase.phaseOrder==1);
					let phase2 = _.find(apprSttConfirmEmpMonthDay.approvalRootStateMonth.listApprovalPhaseState, (phase: any) => phase.phaseOrder==2);
					let phase3 = _.find(apprSttConfirmEmpMonthDay.approvalRootStateMonth.listApprovalPhaseState, (phase: any) => phase.phaseOrder==3);
					let phase4 = _.find(apprSttConfirmEmpMonthDay.approvalRootStateMonth.listApprovalPhaseState, (phase: any) => phase.phaseOrder==4);
					let phase5 = _.find(apprSttConfirmEmpMonthDay.approvalRootStateMonth.listApprovalPhaseState, (phase: any) => phase.phaseOrder==5);
					empDateConfirmContent.approvalStatus = this.getPhaseStatusStr(phase1) + this.getPhaseStatusStr(phase2) + this.getPhaseStatusStr(phase3) +
						this.getPhaseStatusStr(phase4) + this.getPhaseStatusStr(phase5);
				}
				empDateConfirmContent.phase1 = this.getPhaseApprover(_.find(apprSttConfirmEmpMonthDay.monthApprovalLst, o => o.phaseOrder==1));
				empDateConfirmContent.phase2 = this.getPhaseApprover(_.find(apprSttConfirmEmpMonthDay.monthApprovalLst, o => o.phaseOrder==2));
				empDateConfirmContent.phase3 = this.getPhaseApprover(_.find(apprSttConfirmEmpMonthDay.monthApprovalLst, o => o.phaseOrder==3));
				empDateConfirmContent.phase4 = this.getPhaseApprover(_.find(apprSttConfirmEmpMonthDay.monthApprovalLst, o => o.phaseOrder==4));
				empDateConfirmContent.phase5 = this.getPhaseApprover(_.find(apprSttConfirmEmpMonthDay.monthApprovalLst, o => o.phaseOrder==5));
				monthDataSource.push(empDateConfirmContent);
			}
			vm.dataSourceMonth = monthDataSource;
			vm.createIggridMonth();
		}
		
		createDayDataSource(apprSttConfirmEmpMonthDay: ApprSttConfirmEmpMonthDay) {
			const vm = this;
			let dayDataSource: Array<EmpDateConfirmContent> = [];
			_.forEach(apprSttConfirmEmpMonthDay.listDailyConfirm, item => {
				if(!_.isNull(item.personConfirm) || _.isNull(item.bossConfirm)) {
					let empDateConfirmContent: EmpDateConfirmContent = new EmpDateConfirmContent('');
					empDateConfirmContent.dateStr = moment(item.targetDate,'YYYY/MM/DD').format('M/D(ddd)');
					if(!_.isNull(item.personConfirm)) {
						empDateConfirmContent.confirmStt = item.personConfirm ? vm.$i18n('KAF018_534') : vm.$i18n('KAF018_533');
					}
					if(!_.isNull(item.bossConfirm)) {
						empDateConfirmContent.apprStt = item.bossConfirm == 0 ? vm.$i18n('KAF018_535') :
							item.bossConfirm == 1 ? vm.$i18n('KAF018_536') : vm.$i18n('KAF018_537');	
					}
					let approvalRootStateDay = _.find(apprSttConfirmEmpMonthDay.approvalRootStateDayLst, o => o.date==item.targetDate);
					if(approvalRootStateDay) {
						let phase1 = _.find(approvalRootStateDay.listApprovalPhaseState, (phase: any) => phase.phaseOrder==1);
						let phase2 = _.find(approvalRootStateDay.listApprovalPhaseState, (phase: any) => phase.phaseOrder==2);
						let phase3 = _.find(approvalRootStateDay.listApprovalPhaseState, (phase: any) => phase.phaseOrder==3);
						let phase4 = _.find(approvalRootStateDay.listApprovalPhaseState, (phase: any) => phase.phaseOrder==4);
						let phase5 = _.find(approvalRootStateDay.listApprovalPhaseState, (phase: any) => phase.phaseOrder==5);
						empDateConfirmContent.approvalStatus = this.getPhaseStatusStr(phase1) + this.getPhaseStatusStr(phase2) + this.getPhaseStatusStr(phase3) +
							this.getPhaseStatusStr(phase4) + this.getPhaseStatusStr(phase5);	
					}
					let dayApproval = apprSttConfirmEmpMonthDay.dayApprovalMap[item.targetDate];
					if(dayApproval) {
						empDateConfirmContent.phase1 = this.getPhaseApprover(_.find(dayApproval, o => o.phaseOrder==1));
						empDateConfirmContent.phase2 = this.getPhaseApprover(_.find(dayApproval, o => o.phaseOrder==2));
						empDateConfirmContent.phase3 = this.getPhaseApprover(_.find(dayApproval, o => o.phaseOrder==3));
						empDateConfirmContent.phase4 = this.getPhaseApprover(_.find(dayApproval, o => o.phaseOrder==4));
						empDateConfirmContent.phase5 = this.getPhaseApprover(_.find(dayApproval, o => o.phaseOrder==5));	
					}
					dayDataSource.push(empDateConfirmContent);	
				}
			});
			vm.dataSourceDay = dayDataSource;
			vm.createIggridDay();
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