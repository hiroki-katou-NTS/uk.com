module nts.uk.at.view.kaf000_ref.b.component8.viewmodel {

    @component({
        name: 'kaf000-b-component8',
        template: '/nts.uk.at.web/view/kaf_ref/000/b/component8/index.html'
    })
    class Kaf000BComponent8ViewModel extends ko.ViewModel {
        approvalRootState: KnockoutObservableArray<any>;
        appDispInfoStartupOutput: any;
        created(params: any) {
            const vm = this;
            vm.approvalRootState = ko.observableArray([]);
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            
            vm.approvalRootState(ko.mapping.fromJS(vm.appDispInfoStartupOutput().appDetailScreenInfo.approvalLst)());
            
            vm.appDispInfoStartupOutput.subscribe(value => {
            	vm.approvalRootState(ko.mapping.fromJS(value.appDetailScreenInfo.approvalLst)());
            });
            
        }
    
        mounted() {
            const vm = this;
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
                return loopApprover.approverInListOrder();    
            }
            return _.findIndex(loopPhase.listApprovalFrame(), o => o == loopFrame);    
        }
        
        frameCount(listFrame) {
            const vm = this;   
            if(_.size(listFrame) > 1) { 
                return _.size(listFrame);
            }
            return _.chain(listFrame).map(o => vm.approverCount(o.listApprover())).value()[0];        
        }
        
        approverCount(listApprover) {
            return _.chain(listApprover).countBy().values().value()[0];     
        }
        
        getApproverAtr(approver) {
            if(approver.approvalAtrName() !='未承認'){
                if(approver.representerName().length > 0){
                    if(approver.representerMail().length > 0){
                        return approver.representerName() + '(@)';
                    } else {
                        return approver.representerName();
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
            const vm = this,
                index = vm.getFrameIndex(loopPhase, loopFrame, loopApprover);
            return vm.$i18n("KAF000_9",[index+'']);    
        }
    }
}