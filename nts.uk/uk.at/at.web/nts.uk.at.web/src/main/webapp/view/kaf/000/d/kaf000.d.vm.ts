 /// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf000.d.viewmodel {

    @bean()
    class Kaf000DViewModel extends ko.ViewModel {
		approvalRootState: KnockoutObservableArray<any>;
        appDispInfoStartupOutput: any;
		isAgentMode: boolean;
		approvalRootDisp: KnockoutObservable<boolean>;
		indexApprover: number = 0;
		
        created(params: KAF000DParam) {
			const vm = this;
			vm.approvalRootState = ko.observableArray([]);
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
			vm.isAgentMode = params.isAgentMode ? params.isAgentMode : false;
			if(!_.isEmpty(params.approvalRootState)) {
	            vm.approvalRootState(ko.mapping.fromJS(params.approvalRootState)());
	        } else {
	            vm.approvalRootState([]);
	        }
			vm.approvalRootDisp = ko.observable(!vm.isAgentMode || _.size(vm.appDispInfoStartupOutput.appDispInfoNoDateOutput.employeeInfoLst)<=1);
        }

		mounted() {
			const vm = this;
			$('#closeDialogD').focus();
		}

		isFirstIndexFrame(loopPhase, loopFrame, loopApprover) {
            if(_.size(loopFrame.listApprover()) > 1) {
                return _.findIndex(loopFrame.listApprover(), o => o == loopApprover) == 0;
            }
            let firstIndex = _.chain(loopPhase.listApprovalFrame()).filter(x => _.size(x.listApprover()) > 0).orderBy(x => x.frameOrder()).first().value().frameOrder();
            let approver = _.find(loopPhase.listApprovalFrame(), o => o == loopFrame);
            if(approver) {
                return approver.frameOrder() == firstIndex;
            }
            return false;
        }

        getFrameIndex(loopPhase, loopFrame, loopApprover) {
            if(_.size(loopFrame.listApprover()) > 1) {
                return _.findIndex(loopFrame.listApprover(), o => o == loopApprover) + 1;
            }
            return _.findIndex(loopPhase.listApprovalFrame(), o => o == loopFrame) + 1;
        }

        frameCount(listFrame) {
            const vm = this;
            let listExist = _.filter(listFrame, x => _.size(x.listApprover()) > 0);
            if(_.size(listExist) > 1) {
                return _.size(listExist);
            }
            return _.chain(listExist).map(o => vm.approverCount(o.listApprover())).value()[0];
        }

        approverCount(listApprover) {
            return _.chain(listApprover).countBy().values().value()[0];
        }

        getApproverAtr(approver) {
            if(approver.approvalAtrName() !='未承認'){
                if(approver.agentName().length > 0){
                    if(approver.agentMail().length > 0){
                        return approver.agentName() + '(@)';
                    } else {
                        return approver.agentName();
                    }
                } else {
                    if(approver.approverMail().length > 0){
                        return approver.approverName() + '(@)';
                    } else {
                        return approver.approverName();
                    }
                }
            } else {
                var s = '';
                s = s + approver.approverName();
                if(approver.approverMail().length > 0){
                    s = s + '(@)';
                }
                if(approver.representerName().length > 0){
                    if(approver.representerMail().length > 0){
                        s = s + '(' + approver.representerName() + '(@))';
                    } else {
                        s = s + '(' + approver.representerName() + ')';
                    }
                }
                return s;
            }
        }

        getPhaseLabel(phaseOrder) {
            const vm = this;
            switch(phaseOrder) {
                case 1: return vm.$i18n("KAF000_4");
                case 2: return vm.$i18n("KAF000_5");
                case 3: return vm.$i18n("KAF000_6");
                case 4: return vm.$i18n("KAF000_7");
                case 5: return vm.$i18n("KAF000_8");
                default: return "";
            }
        }

        getApproverLabel(loopPhase, loopFrame, loopApprover) {
            const vm = this;
			let indexBefore = _.chain(vm.approvalRootState()).filter(o => o.phaseOrder() < loopPhase.phaseOrder()).map(o => vm.frameCount(o.listApprovalFrame())).sum().value(),
           		index = indexBefore + vm.getFrameIndex(loopPhase, loopFrame, loopApprover);
            // case group approver
            if(_.size(loopFrame.listApprover()) > 1) {
                index++;
            }
           	return vm.$i18n("KAF000_9",[index+'']);
        }

		getApproverLabelByIndex() {
			const vm = this;
			vm.indexApprover++;
			return vm.$i18n("KAF000_9",[vm.indexApprover+'']);
		}

		getApprovalDateFormat(loopApprover) {
			const vm = this;
			if(_.isNull(loopApprover.approvalDate()) || _.isUndefined(loopApprover.approvalDate()) || _.isEmpty(loopApprover.approvalDate())) {
				return '';
			}
			return moment(loopApprover.approvalDate()).format('YYYY/MM/DD HH:mm');
		}
		
		close() {
			const vm = this;
			vm.$window.close();	
		}
		
		getApprovalColor(approvalAtrValue: number) {
			switch(approvalAtrValue) {
				case 1: return '#BFEA60';
				case 2: return '#FD4D4D';
				case 3: return '#FD4D4D';
				case 4: return '#FD4D4D';
				default: return '';
			}
		}
		
		getApprovalReason(approvalReason: string) {
			if(approvalReason) {
				return approvalReason.replace(/\n/g, "\<br/>");
			}
			return '';
		}
    }

	export interface KAF000DParam {
		appDispInfoStartupOutput: any;
		isAgentMode: boolean;
		approvalRootState: Array<any>;
	}

    const API = {
		getEmploymentConfirmInfo: "at/request/application/approvalstatus/getEmploymentConfirmInfo",
		activeConfirm: "screen/at/kdl006/save"
    }
}