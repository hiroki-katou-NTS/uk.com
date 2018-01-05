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
        messageArea : KnockoutObservable<boolean> = ko.observable(true);
        
        errorFlag: number = 0;
        errorMsg: string = '';
        
        approvalRootState: any = ko.observableArray([]);
        
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
        start( sid: any, employmentRootAtr: any,appType: any,standardDate: any): JQueryPromise<any> {
            let self = this;
            self.objApprovalRootInput().sid = sid;
            self.objApprovalRootInput().employmentRootAtr =employmentRootAtr;
            self.objApprovalRootInput().appType = appType;
            self.objApprovalRootInput().standardDate = standardDate;
            
            self.appType(appType);
            
            let dfd = $.Deferred();
            
            //Call approval list
            self.getAppDataDate(appType, standardDate, true).done(function() {
                dfd.resolve(); 
            });
            return dfd.promise();
        }
        
        getAppDataDate(appType: number, appDate: string, isStartup: boolean): JQueryPromise<any> {
            var self = this;
            let dfd = $.Deferred<any>();
            nts.uk.at.view.kaf000.a.service.getAppDataDate({
                appTypeValue: appType,
                appDate: appDate,
                isStartup: isStartup
            }).done((data)=>{
                self.approvalRootState(ko.mapping.fromJS(data.listApprovalPhaseStateDto)());
                let deadlineMsg = data.outputMessageDeadline;
                if(!nts.uk.text.isNullOrEmpty(deadlineMsg.message)){
                    self.reasonOutputMessFull(self.reasonOutputMess + deadlineMsg.message);    
                }
                if(!nts.uk.text.isNullOrEmpty(deadlineMsg.deadline)){
                    self.reasonOutputMessDealineFull(self.reasonOutputMessDealine + deadlineMsg.deadline);
                }
                if(nts.uk.text.isNullOrEmpty(deadlineMsg.message) && nts.uk.text.isNullOrEmpty(deadlineMsg.deadline)){
                    self.messageArea(false);
                }else{
                    self.messageArea(true);
                }
                dfd.resolve();
            }).fail((res)=>{
                dfd.reject(res);    
            });            
            return dfd.promise();
        }
    }
    
    
}