module nts.uk.at.view.kaf000.a.viewmodel{
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import shrvm = nts.uk.at.view.kaf000.shr.viewmodel;
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
            let dfdMessageDeadline = self.getMessageDeadline(self.appType());
            let dfdAllApprovalRoot = self.getAllApprovalRoot();
            $.when(dfdMessageDeadline,dfdAllApprovalRoot).done((dfdMessageDeadlineData,dfdAllApprovalRootData)=>{
//                self.getAllFrameByListPhaseId1(self.listPhaseID);
                 dfd.resolve(); 
            });
            return dfd.promise();
        }
        
        
        //get all listApprovalRoot
        getAllApprovalRoot(){
            let self = this;
            let dfd = $.Deferred<any>();
            nts.uk.at.view.kaf000.a.service.getDataApprovalRoot(self.objApprovalRootInput()).done(function(data){
                self.listApprovalRoot(data);
                if(self.listApprovalRoot !=null && self.listApprovalRoot().length>0 ){
                    self.approvalRoot(self.listApprovalRoot()[0]);
                }
                let listPhase = self.approvalRoot().beforeApprovers; 
                let approvalList = [];
                for(let x = 1; x <= listPhase.length; x++){
                    let phaseLoop = listPhase[x-1];
                    let appPhase = new model.AppApprovalPhase(
                        "",
                        "",
                        phaseLoop.approvalForm,
                        x,
                        0,
                        []); 
                    for(let y = 1; y <= phaseLoop.approvers.length; y++){
                        let frameLoop = phaseLoop.approvers[y-1];
                        let appFrame = new model.ApprovalFrame(
                            "",
                            y,
                            []);
                        let appAccepted = new model.ApproveAccepted(
                            "",
                            frameLoop.sid,
                            0,
                            frameLoop.confirmPerson ? 1 : 0,
                            "",
                            "",
                            frameLoop.sid);
                        appFrame.listApproveAccepted.push(appAccepted);
                        appPhase.listFrame.push(appFrame);   
                    };
                    approvalList.push(appPhase);    
                };
                self.approvalList = approvalList;
                dfd.resolve(data);    
            }).fail(function (res: any){
                    nts.uk.ui.dialog.alertError(res.message).then(function(){nts.uk.ui.block.clear();});
            }); 
            return dfd.promise();
            
        }
         // getMessageDeadline
        getMessageDeadline(appType: any){
            let self = this;
            let dfd = $.Deferred<any>();
            let baseDate = new Date();
            let data = new model.ApplicationMetadata("", self.appType(), baseDate);
            nts.uk.at.view.kaf000.a.service.getMessageDeadline(appType).done(function(data){
                self.outputMessageDeadline(data);
                dfd.resolve(data);    
            }).fail(function (res: any){
                nts.uk.ui.dialog.alertError(res.message).then(function(){nts.uk.ui.block.clear();});
            }); 
            return dfd.promise();
        }
    }
    
    
}