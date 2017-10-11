module nts.uk.at.view.kaf000.a.viewmodel{
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel{
        /**
         * List
         */
        //listPhaseID
        listPhaseID: Array<String>;
        //List  Approval Root 
        listApprovalRoot :  KnockoutObservableArray<Array<model.ApprovalRootOutput>>;
         //Item  Approval Root 
        approvalRoot :  KnockoutObservableArray<model.ApprovalRootOutput>;
        
        /**
         * obj input
         */
        //obj 
        objApprovalRootInput : KnockoutObservable<model.ObjApprovalRootInput>;
        
        //obj output message deadline
        outputMessageDeadline : KnockoutObservable<model.OutputMessageDeadline>;
        
        //
        appType : KnockoutObservable<number>;
        constructor(){
            let self = this;

            /**
             * List
             */
            
            self.listApprovalRoot = ko.observableArray([]);
            //item approval root
            self.approvalRoot = ko.observableArray([]);
            //obj input approval root (new model.ObjApprovalRootInput('90000000-0000-0000-0000-000000000005',1,1,'2018/01/01'))
            self.objApprovalRootInput = ko.observable(new model.ObjApprovalRootInput("", 1,1,new Date());
            // app ID
            self.appType = ko.observable(0);
            //obj input get message deadline 
            self.outputMessageDeadline = ko.observable(null);
        }
        /**
         *
           sid 社員ID（申請本人の社員ID）
           employmentRootAtr 就業ルート区分
           subjectRequest 対象申請
           baseDate 基準日
           workplaceID 
         */
        start( sid, employmentRootAtr,appType,standardDate): JQueryPromise<any> {
            let self = this;
            self.objApprovalRootInput().sid=sid;
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
                dfd.resolve(data);    
            }).fail(function (res: any){
                    nts.uk.ui.dialog.alertError(res.message).then(function(){nts.uk.ui.block.clear();});
            }); 
            return dfd.promise();
            
        }
         // getMessageDeadline
        getMessageDeadline(appType){
            let self = this;
            let dfd = $.Deferred<any>();
                nts.uk.at.view.kaf000.a.service.getMessageDeadline(appType).done(function(data){
                    self.outputMessageDeadline(data);
                    dfd.resolve(data);    
                }).fail(function (res: any){
                    nts.uk.ui.dialog.alertError(res.message).then(function(){nts.uk.ui.block.clear();});
                }); 
            return dfd.promise();
        }
    }
    
    export module model {
        //       AppApprovalPhase
        // class AppApprovalPhase
        export class AppApprovalPhase{
            appID : KnockoutObservable<String>;
            phaseID : KnockoutObservable<String>;
            approvalForm : KnockoutObservable<number>;
            dispOrder : KnockoutObservable<number>;
            approvalATR : KnockoutObservable<number>;    
            constructor(appID : String,phaseID : String,approvalForm : number,dispOrder : number,approvalATR : number){
                this.appID  = ko.observable(appID);
                this.phaseID = ko.observable(phaseID);
                this.approvalForm = ko.observable(approvalForm);
                this.dispOrder = ko.observable(dispOrder);
                this.approvalATR = ko.observable(approvalATR); 
            }
        } 
        
        // class ApprovalFrame
        export class ApprovalFrame{
            phaseID : KnockoutObservable<String>;
            dispOrder : KnockoutObservable<number>;
            approverSID : KnockoutObservable<String>;
            approvalATR : KnockoutObservable<number>;
            confirmATR : KnockoutObservable<number>;
            approvalDate :  KnockoutObservable<String>;
            reason : KnockoutObservable<String>;
            representerSID : KnockoutObservable<String>;
            constructor(phaseID : String,dispOrder : number,approverSID : String,approvalATR : number,
                    confirmATR : number,approvalDate : String,reason: String,representerSID: String){
                this.phaseID = ko.observable(phaseID);
                this.dispOrder = ko.observable(dispOrder);
                this.approverSID  = ko.observable(approverSID);
                this.approvalATR = ko.observable(approvalATR); 
                this.confirmATR = ko.observable(confirmATR);
                this.approvalDate = ko.observable(approvalDate);
                this.reason = ko.observable(reason);
                this.representerSID = ko.observable(representerSID);
            }
        }//end class ApprovalFrame   
        
        export class ApprovalRootOutput{
            workplaceId : KnockoutObservable<String>;
            approvalId : KnockoutObservable<String>;
            employeeId : KnockoutObservable<String>;
            historyId : KnockoutObservable<String>;
            applicationType : KnockoutObservable<number>;
            startDate :  KnockoutObservable<String>;
            endDate : KnockoutObservable<String>;
            branchId : KnockoutObservable<String>;
            anyItemApplicationId : KnockoutObservable<String>;
            confirmationRootType : KnockoutObservable<number>;
            employmentRootAtr : KnockoutObservable<number>;
            beforeApprovers : KnockoutObservableArray<ApprovalPhaseImport>;
            afterApprovers : KnockoutObservableArray<ApprovalPhaseOutput>;
            errorFlag : KnockoutObservable<number>;
            constructor(workplaceId : String,approvalId : String,
                        employeeId : String,historyId : String,
                        applicationType : number,startDate : String,
                        endDate: String,branchId: String,
                        anyItemApplicationId : String,confirmationRootType : number,employmentRootAtr :number,
                        beforeApprovers : Array<ApprovalPhaseImport>,afterApprovers : Array<ApprovalPhaseOutput>,
                        errorFlag : number){
                this.workplaceId = ko.observable(workplaceId);
                this.approvalId = ko.observable(approvalId);
                this.employeeId  = ko.observable(employeeId);
                this.historyId = ko.observable(historyId); 
                this.applicationType = ko.observable(applicationType);
                this.startDate = ko.observable(startDate);
                this.endDate = ko.observable(endDate);
                this.branchId = ko.observable(branchId);
                this.anyItemApplicationId = ko.observable(anyItemApplicationId);
                this.confirmationRootType = ko.observable(confirmationRootType);
                this.employmentRootAtr = ko.observable(employmentRootAtr);
                this.beforeApprovers = ko.observableArray(beforeApprovers);
                this.afterApprovers = ko.observableArray(afterApprovers);
                this.errorFlag = ko.observable(errorFlag);
            }
        }//end class ApprovalRootOutput
        
        //class ApprovalPhaseOutput
        export class ApprovalPhaseOutput{
            branchId : KnockoutObservable<String>;
            approvalPhaseId : KnockoutObservable<String>;
            approvalForm : KnockoutObservable<number>;
            browsingPhase : KnockoutObservable<number>;
            orderNumber : KnockoutObservable<number>;
            approvers :  KnockoutObservableArray<ApproverInfo>;
            constructor(branchId : String,approvalPhaseId : String,
                        approvalForm : number,browsingPhase : number,
                        orderNumber : number,approvers : Array<ApproverInfo>){
                this.branchId = ko.observable(branchId);
                this.approvalPhaseId = ko.observable(approvalPhaseId);
                this.approvalForm  = ko.observable(approvalForm);
                this.browsingPhase = ko.observable(browsingPhase); 
                this.orderNumber = ko.observable(orderNumber);
                this.approvers = ko.observableArray(approvers);
                
            }
        }//end class ApprovalPhaseOutput
        
        //class ApproverInfo
        export class ApproverInfo{
            sid : KnockoutObservable<String>;
            approvalPhaseId : KnockoutObservable<String>;
            isConfirmPerson : KnockoutObservable<boolean>;
            orderNumber : KnockoutObservable<number>;
            name : KnockoutObservable<String>;
            constructor(sid : String,approvalPhaseId : String,
                        isConfirmPerson : boolean,orderNumber : number,name : String){
                this.sid = ko.observable(sid);
                this.approvalPhaseId = ko.observable(approvalPhaseId);
                this.isConfirmPerson  = ko.observable(isConfirmPerson);
                this.orderNumber = ko.observable(orderNumber); 
                this.name = ko.observable(name); 
                
            }
        }//end class ApproverInfo
        
        //class ApprovalPhaseImport
        export class ApprovalPhaseImport{
            branchId : KnockoutObservable<String>;
            approvalPhaseId : KnockoutObservable<String>;
            approvalForm : KnockoutObservable<number>;
            browsingPhase : KnockoutObservable<number>;
            orderNumber : KnockoutObservable<number>;
            approverDtos :  KnockoutObservableArray<ApproverImport>;
            constructor(branchId : String,approvalPhaseId : String,
                        approvalForm : number,browsingPhase : number,
                        orderNumber : number,approverDtos : Array<ApproverImport>){
                this.branchId = ko.observable(branchId);
                this.approvalPhaseId = ko.observable(approvalPhaseId);
                this.approvalForm  = ko.observable(approvalForm);
                this.browsingPhase = ko.observable(browsingPhase); 
                this.orderNumber = ko.observable(orderNumber);
                this.approverDtos = ko.observableArray(approverDtos);
                
            }
            
        }//end class ApprovalPhaseImport
        
        //class ApproverImport
        export class ApproverImport{
            approvalPhaseId : KnockoutObservable<String>;
            approverId : KnockoutObservable<String>;
            jobTitleId : KnockoutObservable<String>;
            employeeId : KnockoutObservable<String>;
            orderNumber : KnockoutObservable<number>;
            approvalAtr : KnockoutObservable<number>;
            confirmPerson : KnockoutObservable<number>;
            constructor(
                        approvalPhaseId : String,
                        approverId : String,jobTitleId : String,
                        employeeId : String,orderNumber : number,
                        approvalAtr : number,confirmPerson : number){
                this.approvalPhaseId = ko.observable(approvalPhaseId);
                this.approverId  = ko.observable(approverId);
                this.jobTitleId = ko.observable(jobTitleId); 
                this.employeeId = ko.observable(employeeId);
                this.orderNumber = ko.observable(orderNumber);
                this.approvalAtr = ko.observable(approvalAtr); 
                this.confirmPerson = ko.observable(confirmPerson);
                }
        }//end class ApproverImport
        
        //class ObjApprovalRootInput    
        export class ObjApprovalRootInput{
            sid : String;
            employmentRootAtr : number;
            appType : number;
            standardDate :  String;
            constructor (
                        sid : String,employmentRootAtr : number,
                        appType : number,standardDate : String){
                this.sid = sid; 
                this.employmentRootAtr =employmentRootAtr;
                this.appType = appType;
                this.standardDate = standardDate; 
            }
        }//end class ObjApprovalRootInput
        
        
        //class outputMessageDeadline
        export class OutputMessageDeadline{
            message : String;
            deadline : String;
            constructor(message : String,deadline : String){
                this.message = message;
                this.deadline = deadline;
            }
        }// end class outputMessageDeadline
        
    }
}