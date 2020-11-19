 /// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf018.e.viewmodel {
	import EmpInfo = nts.uk.at.view.kaf018.d.viewmodel.EmpInfo;
	import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;

	@bean()
	class Kaf018EViewModel extends ko.ViewModel {
		appNameLst: Array<any> = [];
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
			vm.$blockui('show');
			vm.appNameLst = params.appNameLst;
			vm.startDate = params.startDate;
			vm.endDate = params.endDate;
			vm.empInfoLst = params.empInfoLst;
			vm.currentEmpInfo(_.find(vm.empInfoLst, o => o.empID == params.currentEmpID));
			vm.refreshDataSource();
		}
		
		createIggrid() {
			const vm = this;
			$("#eGrid").igGrid({
				height: 501,
				width: window.innerWidth - 40,
				dataSource: vm.dataSource,
				primaryKey: 'appID',
				primaryKeyDataType: 'string',
				hidePrimaryKey: true,
				virtualization: true,
				virtualizationMode: 'continuous',
				dataRendered: () => {
					vm.updateCellStyles();
				},
				rendered: () => {
					vm.$nextTick(() => {
						vm.$blockui('hide');
					});  
			    },
				columns: [
					{ headerText: "", key: 'appID', width: 1, hidden: true },
					{ headerText: vm.$i18n('KAF018_385'), key: 'dateStr', width: 180 },
					{ headerText: vm.$i18n('KAF018_383'), key: 'appType', width: 150 },
					{ headerText: vm.$i18n('KAF018_384'), key: 'prePostAtr', width: 70 },
					{ headerText: vm.$i18n('KAF018_386'), key: 'content', width: 600 },
					{ headerText: vm.$i18n('KAF018_387'), key: 'reflectedState', dataType: 'number', width: 150, formatter: (value: number) => vm.getReflectedStateText(value) },
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
				]
			});
		}
		
		getReflectedStateText(value: number) {
			const vm = this;
			let text = '';
			switch(value) {
				case ReflectedState.NOTREFLECTED:
					text = vm.$i18n('KAF018_522');
					break;
				case ReflectedState.WAITREFLECTION:
					text = vm.$i18n('KAF018_523');
					break;
				case ReflectedState.REFLECTED:
					text = vm.$i18n('KAF018_524');
					break;
				case ReflectedState.CANCELED:
					text = vm.$i18n('KAF018_527');
					break;
				case ReflectedState.REMAND:
					text = vm.$i18n('KAF018_526');
					break;
				case ReflectedState.DENIAL:
					text = vm.$i18n('KAF018_525');
					break;
				default:
					break;
			}		
			return text;
		}
		
		updateCellStyles() {
			const vm = this;
			_.forEach(vm.dataSource, (item: EmpDateContent) => {
				switch(item.reflectedState) {
					case ReflectedState.NOTREFLECTED:
						$('#eGrid').igGrid("cellById", item.appID, "reflectedState").removeClass();
						$('#eGrid').igGrid("cellById", item.appID, "reflectedState").addClass('kaf018-e-bg-unapproved-application kaf018-e-text-unapproved-application');
						break;
					case ReflectedState.WAITREFLECTION:
						$('#eGrid').igGrid("cellById", item.appID, "reflectedState").removeClass();
						$('#eGrid').igGrid("cellById", item.appID, "reflectedState").addClass('kaf018-e-bg-application-approved');
						break;
					case ReflectedState.REFLECTED:
						$('#eGrid').igGrid("cellById", item.appID, "reflectedState").removeClass();
						$('#eGrid').igGrid("cellById", item.appID, "reflectedState").addClass('kaf018-e-bg-application-reflected');
						break;
					case ReflectedState.CANCELED:
						$('#eGrid').igGrid("cellById", item.appID, "reflectedState").removeClass();
						$('#eGrid').igGrid("cellById", item.appID, "reflectedState").addClass('kaf018-e-bg-canceled-application');
						break;
					case ReflectedState.REMAND:
						$('#eGrid').igGrid("cellById", item.appID, "reflectedState").removeClass();
						$('#eGrid').igGrid("cellById", item.appID, "reflectedState").addClass('kaf018-e-bg-remanded-application');
						break;
					case ReflectedState.DENIAL:
						$('#eGrid').igGrid("cellById", item.appID, "reflectedState").removeClass();
						$('#eGrid').igGrid("cellById", item.appID, "reflectedState").addClass('kaf018-e-bg-denial-application');
						break;
					default:
						break;
				}			
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
			vm.$ajax(API.getApprSttStartByEmpDate, wsParam).done((data: Array<ApprSttEmpDateContentDto>) => {
				vm.dataSource = _.map(data, o => new EmpDateContent(o, vm));
				vm.createIggrid();
			});
		}
	}
	
	export interface KAF018EParam {
		empInfoLst: Array<EmpInfo>;
		startDate: string;
		endDate: string;
		currentEmpID: string;
		appNameLst: Array<any>;
	}
	
	class EmpDateContent {
		appID: string;
		dateStr: string;
		appType: string;
		prePostAtr: string;
		content: string;
		reflectedState: number;
		approvalStatus: string;
		phase1: string;
		phase2: string;
		phase3: string;
		phase4: string;
		phase5: string;
		
		constructor(apprSttEmpDateContentDto: ApprSttEmpDateContentDto, vm: any) {
			this.appID = apprSttEmpDateContentDto.application.appID;
			if(moment(apprSttEmpDateContentDto.application.opAppEndDate,'YYYY/MM/DD').diff(moment(apprSttEmpDateContentDto.application.opAppStartDate,'YYYY/MM/DD'), 'days')==0) {
				this.dateStr = moment(apprSttEmpDateContentDto.application.appDate,'YYYY/MM/DD').format('M/D(ddd)');
			} else {
				this.dateStr = moment(apprSttEmpDateContentDto.application.opAppStartDate,'YYYY/MM/DD').format('M/D(ddd)') + 
								vm.$i18n('KAF018_394') + 
								moment(apprSttEmpDateContentDto.application.opAppEndDate,'YYYY/MM/DD').format('M/D (ddd)');
			}
			switch(apprSttEmpDateContentDto.application.appType) {
				case AppType.OVER_TIME_APPLICATION:
	            case AppType.STAMP_APPLICATION:
				default:
					let appNameInfo = _.find(vm.appNameLst, (o: any) => o.appType == apprSttEmpDateContentDto.application.appType);
					if(appNameInfo) {
						this.appType = appNameInfo.appName;
					}
			}
			if(apprSttEmpDateContentDto.application.prePostAtr==0) {
            	this.prePostAtr = vm.$i18n('KAF000_47');
            } else {
                this.prePostAtr = vm.$i18n('KAF000_48');
            }
			this.content = apprSttEmpDateContentDto.content;
			this.reflectedState = apprSttEmpDateContentDto.reflectedState;
			this.approvalStatus = "";
			let phase1 = _.find(apprSttEmpDateContentDto.phaseApproverSttLst, o => o.phaseOrder==1);
			if(phase1) {
				switch(phase1.approvalAtr) {
					case ApprovalPhaseStateImport_New.UNAPPROVED:
						this.approvalStatus += "－";
						break;
					case ApprovalPhaseStateImport_New.APPROVED:
						this.approvalStatus += "〇";
						break;
					case ApprovalPhaseStateImport_New.DENIAL:
						this.approvalStatus += "×";
						break;
					case ApprovalPhaseStateImport_New.REMAND:
						this.approvalStatus += "－";
						break;
					case ApprovalPhaseStateImport_New.ORIGINAL_REMAND:
						this.approvalStatus += "－";
						break;
					default: 
						break;
				}
				this.phase1 = phase1.empName;
				if(phase1.countRemainApprover) {
					this.phase1 += vm.$i18n('KAF018_531', [phase1.countRemainApprover]);
				}
				
			} else {
				this.approvalStatus += " ";
			}
			let phase2 = _.find(apprSttEmpDateContentDto.phaseApproverSttLst, o => o.phaseOrder==2);
			if(phase2) {
				switch(phase2.approvalAtr) {
					case ApprovalPhaseStateImport_New.UNAPPROVED:
						this.approvalStatus += "－";
						break;
					case ApprovalPhaseStateImport_New.APPROVED:
						this.approvalStatus += "〇";
						break;
					case ApprovalPhaseStateImport_New.DENIAL:
						this.approvalStatus += "×";
						break;
					case ApprovalPhaseStateImport_New.REMAND:
						this.approvalStatus += "－";
						break;
					case ApprovalPhaseStateImport_New.ORIGINAL_REMAND:
						this.approvalStatus += "－";
						break;
					default: 
						break;
				}
				this.phase2 = phase2.empName;
				if(phase2.countRemainApprover) {
					this.phase2 += vm.$i18n('KAF018_531', [phase2.countRemainApprover]);
				}
			} else {
				this.approvalStatus += " ";
			}
			let phase3 = _.find(apprSttEmpDateContentDto.phaseApproverSttLst, o => o.phaseOrder==3);
			if(phase3) {
				switch(phase3.approvalAtr) {
					case ApprovalPhaseStateImport_New.UNAPPROVED:
						this.approvalStatus += "－";
						break;
					case ApprovalPhaseStateImport_New.APPROVED:
						this.approvalStatus += "〇";
						break;
					case ApprovalPhaseStateImport_New.DENIAL:
						this.approvalStatus += "×";
						break;
					case ApprovalPhaseStateImport_New.REMAND:
						this.approvalStatus += "－";
						break;
					case ApprovalPhaseStateImport_New.ORIGINAL_REMAND:
						this.approvalStatus += "－";
						break;
					default: 
						break;
				}
				this.phase3 = phase3.empName;
				if(phase3.countRemainApprover) {
					this.phase3 += vm.$i18n('KAF018_531', [phase3.countRemainApprover]);
				}
			} else {
				this.approvalStatus += " ";
			}
			let phase4 = _.find(apprSttEmpDateContentDto.phaseApproverSttLst, o => o.phaseOrder==4);
			if(phase4) {
				switch(phase4.approvalAtr) {
					case ApprovalPhaseStateImport_New.UNAPPROVED:
						this.approvalStatus += "－";
						break;
					case ApprovalPhaseStateImport_New.APPROVED:
						this.approvalStatus += "〇";
						break;
					case ApprovalPhaseStateImport_New.DENIAL:
						this.approvalStatus += "×";
						break;
					case ApprovalPhaseStateImport_New.REMAND:
						this.approvalStatus += "－";
						break;
					case ApprovalPhaseStateImport_New.ORIGINAL_REMAND:
						this.approvalStatus += "－";
						break;
					default: 
						break;
				}
				this.phase4 = phase4.empName;
				if(phase4.countRemainApprover) {
					this.phase4 += vm.$i18n('KAF018_531', [phase4.countRemainApprover]);
				}
			} else {
				this.approvalStatus += " ";
			}
			let phase5 = _.find(apprSttEmpDateContentDto.phaseApproverSttLst, o => o.phaseOrder==5);
			if(phase5) {
				switch(phase5.approvalAtr) {
					case ApprovalPhaseStateImport_New.UNAPPROVED:
						this.approvalStatus += "－";
						break;
					case ApprovalPhaseStateImport_New.APPROVED:
						this.approvalStatus += "〇";
						break;
					case ApprovalPhaseStateImport_New.DENIAL:
						this.approvalStatus += "×";
						break;
					case ApprovalPhaseStateImport_New.REMAND:
						this.approvalStatus += "－";
						break;
					case ApprovalPhaseStateImport_New.ORIGINAL_REMAND:
						this.approvalStatus += "－";
						break;
					default: 
						break;
				}
				this.phase5 = phase5.empName;
				if(phase5.countRemainApprover) {
					this.phase5 += vm.$i18n('KAF018_531', [phase5.countRemainApprover]);
				}
			} else {
				this.approvalStatus += " ";
			}
		}
	}
	
	interface ApprSttEmpDateContentDto {
		application: any;
		content: string;
		reflectedState: ReflectedState;
		phaseApproverSttLst: Array<PhaseApproverStt>;
	}
	
	interface PhaseApproverStt {
		/**
		 * フェーズ
		 */
		phaseOrder: number;
		/**
		 * 社員名
		 */
		empName: string;
		/**
		 * 人数
		 */
		countRemainApprover: number;
		approvalAtr: ApprovalPhaseStateImport_New;
	}
	
	enum ReflectedState {
		/** 未反映 */
		NOTREFLECTED = 0,
		/** 反映待ち */
		WAITREFLECTION = 1,
		/** 反映済 */
		REFLECTED = 2,
		/** 取消済 */
		CANCELED = 3,
		/** 差し戻し */
		REMAND = 4,
		/** 否認 */
		DENIAL = 5
	}
	
	enum ApprovalPhaseStateImport_New {
		/** 0:未承認 */
		UNAPPROVED = 0,
		/** 1:承認済 */
		APPROVED = 1,
		/** 2:否認 */
		DENIAL = 2,
		/** 3:差し戻し */
		REMAND = 3,
		/** 4:本人差し戻し */
		ORIGINAL_REMAND = 4
	}
	
	class CellState {
        rowKey: string;
        columnKey: string;
        state: Array<any>
        constructor(rowKey: string, columnKey: string, state: Array<any>) {
            this.rowKey = rowKey;
            this.columnKey = columnKey;
            this.state = state;
        }
    }

	const API = {
		getApprSttStartByEmpDate: "at/request/application/approvalstatus/getApprSttStartByEmpDate",
	}
}