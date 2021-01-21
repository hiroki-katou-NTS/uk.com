module nts.uk.at.view.kaf000.a.viewmodel{
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import shrvm = nts.uk.at.view.kaf000.shr;
    export class ScreenModel{
        /**
         * List
         */
        //listPhaseID
        listPhaseID: Array<String>;
        //List  Approval Root 
        listApprovalRoot :  KnockoutObservableArray<Array<shrvm.model.ApprovalRootOutput>> = ko.observableArray([]);
         //Item  Approval Root 
        approvalRoot :  KnockoutObservableArray<shrvm.model.ApprovalRootOutput> = ko.observableArray([]);
        
        /**
         * obj input
         */
        //obj 
        
        objApprovalRootInput : KnockoutObservable<shrvm.model.ObjApprovalRootInput>;
        
        //obj output message deadline
        outputMessageDeadline : KnockoutObservable<shrvm.model.OutputMessageDeadline> = ko.observable(null);
        
        //app type
        appType : KnockoutObservable<number> = ko.observable(0);
        
        approvalList: Array<shrvm.model.AppApprovalPhase> = [];
        reasonOutputMess : string = nts.uk.resource.getText('KAF000_1');
        reasonOutputMessFull: KnockoutObservable<string> = ko.observable('');
        reasonOutputMessDealine : string = nts.uk.resource.getText('KAF000_2');
        reasonOutputMessDealineFull: KnockoutObservable<string> = ko.observable('');
        messageArea : KnockoutObservable<boolean> = ko.observable(false);
        
        errorFlag: number = 0;
        errorMsg: string = '';
        
        approvalRootState: any = ko.observableArray([]);
        messFullDisp: KnockoutObservable<boolean> = ko.observable(false);
        messDealineFullDisp: KnockoutObservable<boolean> = ko.observable(false);
        
        constructor(){
            let self = this;
            let baseDate = new Date();
            let date = baseDate.getFullYear + "/" + baseDate.getMonth + "/" + baseDate.getDate;
            self.objApprovalRootInput = ko.observable(new shrvm.model.ObjApprovalRootInput("", 1,1, date))
        }
        /**
         *
           sid 社員ID（申請本人の社員ID）
           employmentRootAtr 就業ルート区分
           subjectRequest 対象申請
           baseDate 基準日
           workplaceID 
         */
        start( sid: any, employmentRootAtr: any,appType: any,standardDate: any, overtimeAtr: any): JQueryPromise<any> {
            let self = this;
            self.objApprovalRootInput().sid = sid;
            self.objApprovalRootInput().employmentRootAtr =employmentRootAtr;
            self.objApprovalRootInput().appType = appType;
            self.objApprovalRootInput().standardDate = standardDate;
            
            self.appType(appType);
            
            let dfd = $.Deferred();
            //Call approval list
            self.getAppDataDate(appType, standardDate, true,sid, overtimeAtr).done(function(data) {
                dfd.resolve(data); 
            }).fail((res)=>{
                nts.uk.ui.dialog.alertError({ messageId: res.messageId }).then(function(){
                    nts.uk.ui.block.clear();
                });  
            });
            return dfd.promise();
        }
        
        getAppDataDate(appType: number, appDate: string, isStartup: boolean, employeeID: string, overtimeAtr: any): JQueryPromise<any> {
            var self = this;
            let dfd = $.Deferred<any>();
            nts.uk.ui.block.invisible();
            nts.uk.at.view.kaf000.a.service.getAppDataDate({
                appTypeValue: appType,
                appDate: appDate,
                isStartup: isStartup,
                employeeID: employeeID,
                overtimeAtrParam: overtimeAtr
            }).done((data)=>{
                if(appType != 1 && appType != 2 && appType != 10) {
                    self.initData(data);    
                }
                let deadlineMsg = data.outputMessageDeadline;
                if(!nts.uk.text.isNullOrEmpty(deadlineMsg.message)){
                    self.reasonOutputMessFull(deadlineMsg.message.replace(/\n/ig, '<br/>'));    
                }
                if(!_.isEmpty(self.reasonOutputMessFull())){
                    self.messFullDisp(true);    
                }
                if(!nts.uk.text.isNullOrEmpty(deadlineMsg.deadline)){
                    self.reasonOutputMessDealineFull(deadlineMsg.deadline);
                }
                if(!_.isEmpty(self.reasonOutputMessDealineFull())){
                    self.messDealineFullDisp(true);    
                }
                self.messageArea(deadlineMsg.chkShow);
                if(self.appType() == 0){
                     if(deadlineMsg.chkShow) {  
                        $('#message_ct').removeClass();
                        $('#message_ct').addClass("message");
                    } else {
                        $('#message_ct').removeClass();
                        $('#message_ct').addClass("message_none");
                    }
                }
                nts.uk.ui.block.clear(); 
                dfd.resolve(data);
            }).fail((res)=>{
                nts.uk.ui.block.clear(); 
                dfd.reject(res);    
            });            
            return dfd.promise();
        }
        
        isFirstIndexFrame(loopPhase, loopFrame, loopApprover) {
            let self = this;
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
            let self = this;
            if(_.size(loopFrame.listApprover()) > 1) {
                return loopApprover.approverInListOrder();     
            }
            return loopFrame.frameOrder(); 
        }
        
        frameCount(listFrame) {
            let self = this;    
            let listExist = _.filter(listFrame, x => _.size(x.listApprover()) > 0);
            if(_.size(listExist) > 1) { 
                return _.size(listExist);
            }
            return _.chain(listExist).map(o => self.approverCount(o.listApprover())).value()[0];        
        }
        
        approverCount(listApprover) {
            let self = this;
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
        
        public initData(data) {
            let self = this;
            $('#listApproverRootState').ntsError('clear');
            if(!nts.uk.util.isNullOrEmpty(data.errorFlag)){
                if(data.errorFlag==0){
                    if(!nts.uk.util.isNullOrEmpty(data.listApprovalPhaseStateDto)){
                        
                        // sort list approval
//                        if(data.listApprovalPhaseStateDto != undefined && data.listApprovalPhaseStateDto.length != 0) {
//                            data.listApprovalPhaseStateDto.forEach((el) => {
//                                if(el.listApprovalFrame != undefined && el.listApprovalFrame.length != 0) {
//                                        el.listApprovalFrame.forEach((el1) =>{
//                                               if(el1.listApprover != undefined && el1.listApprover.length != 0) {
//                                                  el1.listApprover = _.orderBy(el1.listApprover, ['approverName'],['asc']);                                   
//                                               }
//                                        });
//                                        if(el.listApprovalFrame.length > 1) {
//                                            let arrayTemp = [];
//                                            arrayTemp.push(el.listApprovalFrame[0]);
//                                            if(el.listApprovalFrame[0].listApprover.length == 0) {   
//                                                _.orderBy(el.listApprovalFrame.slice(1, el.listApprovalFrame.length), ['listApprover[0].approverName'], ['asc'])
//                                                .forEach(i => arrayTemp.push(i));      
//                                                el.listApprovalFrame = arrayTemp;
//                                            }else {
//                                                el.listApprovalFrame = _.orderBy(el.listApprovalFrame, ['listApprover[0].approverName'], ['asc']);
//                                                
//                                            }
//                                            let frameOrderTemp = 0;
//                                            el.listApprovalFrame.forEach((el1, index) =>{
//                                                if(el1.listApprover.length != 0) {
//                                                    frameOrderTemp++;
//                                                }
//                                                el1.frameOrder = frameOrderTemp;
//                                            });
//                                        }
//                                }
//                            });  
//                        }
                        self.approvalRootState(ko.mapping.fromJS(data.listApprovalPhaseStateDto)());    
                    }
                }
                let msgID = "";
                switch(data.errorFlag){
                    case 1:
                        msgID = "Msg_324";
                        break;
                    case 2: 
                        msgID = "Msg_238";
                        break;
                    case 3:
                        msgID = "Msg_237";
                        break;
                    default: 
                }  
                if(!nts.uk.util.isNullOrEmpty(msgID)) {
                    nts.uk.ui.dialog.alertError({ messageId: msgID }).then(function(){
                        if(!nts.uk.util.isNullOrUndefined(data.isSystemDate) && data.isSystemDate == 0) {
                            nts.uk.request.jump("com", "view/ccg/008/a/index.xhtml");            
                        }
                        nts.uk.ui.block.clear();
                    });    
                }
            }    
        }    
        
        getPhaseLabel(phaseOrder) {
            let self = this;
            switch(phaseOrder) {
                case 1: return nts.uk.resource.getText("KAF000_4"); 
                case 2: return nts.uk.resource.getText("KAF000_5"); 
                case 3: return nts.uk.resource.getText("KAF000_6"); 
                case 4: return nts.uk.resource.getText("KAF000_7"); 
                case 5: return nts.uk.resource.getText("KAF000_8");    
                default: return "";
            }                 
        }
        
        getApproverLabel(loopPhase, loopFrame, loopApprover) {
            let self = this,
                index = self.getFrameIndex(loopPhase, loopFrame, loopApprover);
            return nts.uk.resource.getText("KAF000_9",[index+'']);    
        }
    }
    
    
}