module kaf000.a.viewmodel{
    export class ScreenModel{
        /**
         * List
         */
        //listPhase
        listPhase: KnockoutObservableArray<model.AppApprovalPhase>;
        //listPhaseID
        listPhaseID: Array<String>;
        //listFrame
        listFrame: KnockoutObservableArray<model.ApprovalFrame>;
        //listFrameByListPhaseId
        listFrameByListPhase: KnockoutObservableArray<model.ApprovalFrame>;
        //listFrameByListPhaseId
        listFrameByListPhase1: KnockoutObservableArray<Array<model.ApprovalFrame>>;
        //list appID 
        listAppID : Array<String>;
        //List  Approval Root 
        listApprovalRoot :  KnockoutObservableArray<Array<model.ApprovalRootOutput>>;
        
        /**
         * obj input
         */
        
        objApprovalRootInput : KnockoutObservable<model.ObjApprovalRootInput>;
        constructor(){
            var self = this;
            /**
             * List
             */
            
            self.listPhase = ko.observableArray([]);
            self.listPhaseID = [];
            self.listFrame = ko.observableArray([]);
            self.listFrameByListPhase = ko.observableArray([]);
            self.listFrameByListPhase1 = ko.observableArray([]);
            self.listAppID = [];
            self.listApprovalRoot = ko.observableArray([]);
            //obj input approval root
            self.objApprovalRootInput = ko.observable(new model.ObjApprovalRootInput('000000000000-0001','90000000-0000-0000-0000-000000000001',1,1,new Date('2019-11-02 00:00:00')));
            
        }
        
        start(): JQueryPromise<any> {
            
            let self = this;
            var dfd = $.Deferred();
            //alert("sdfds");
            //self.getAllPhase();
            var dfdAllPhase = self.getAllPhase();
            var dfdAllApprovalRoot = self.getAllApprovalRoot();
            $.when(dfdAllPhase,dfdAllApprovalRoot).done((dfdAllPhaseData,dfdAllApprovalRootDât)=>{
                self.getAllFrameByListPhaseId1(self.listPhaseID);
                 dfd.resolve(); 
            });
            return dfd.promise();
        }
        
        //get all listApprovalRoot
        getAllApprovalRoot(){
            var self = this;
            var dfd = $.Deferred<any>();
            service.getDataApprovalRoot(self.objApprovalRootInput()).done(function(data){
                self.listApprovalRoot(data);
                dfd.resolve(data);    
            });
            return dfd.promise();
            
        }
        
        //getAllPhase
        getAllPhase(){
            let appID='000';
            var self = this;
            var dfd = $.Deferred<any>();
            service.getAllPhaseByAppID(appID).done(function(data){
                self.listPhase(data);
                for(var i = 0;i<self.listPhase().length;i++){
                    
                        self.listPhaseID.push(self.listPhase()[i].phaseID);    
                    }
                dfd.resolve(data);    
            });
            return dfd.promise();
        }
        //getAllFrame
        getAllFrame(phaseID){
            var self = this;
            var dfd = $.Deferred<any>();
                service.getAllFrameByPhaseID(phaseID).done(function(data){
                    self.listFrame(data);
                    dfd.resolve(data);    
                }); 
            return dfd.promise();
        }
        
        //get all frame by list phase ID
        getAllFrameByListPhaseId(listPhaseID){
            var self = this;
            var dfd = $.Deferred<any>();
                service.getAllFrameByListPhaseId(listPhaseID).done(function(data){
                    self.listFrameByListPhase(data);
                    dfd.resolve(data);    
                }); 
            return dfd.promise();
        }
         //get all frame by list phase ID 1
        getAllFrameByListPhaseId1(listPhaseID){
            var self = this;
            var dfd = $.Deferred<any>();
                service.getAllFrameByListPhaseId1(listPhaseID).done(function(data){
                    self.listFrameByListPhase1(data);
                    dfd.resolve(data);    
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
            constructor(sid : String,approvalPhaseId : String,
                        isConfirmPerson : boolean,orderNumber : number){
                this.sid = ko.observable(sid);
                this.approvalPhaseId = ko.observable(approvalPhaseId);
                this.isConfirmPerson  = ko.observable(isConfirmPerson);
                this.orderNumber = ko.observable(orderNumber); 
                
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
                        approvalAtr : number,confirmPerson : number,){
                this.approvalPhaseId = ko.observable(approvalPhaseId);
                this.approverId  = ko.observable(approverId);
                this.jobTitleId = ko.observable(jobTitleId); 
                this.employeeId = ko.observable(employeeId);
                this.orderNumber = ko.observable(orderNumber);
                this.approvalAtr = ko.observable(approvalAtr); 
                this.confirmPerson = ko.observable(confirmPerson);
        }//end class ApproverImport
        
        //class ObjApprovalRootInput    
        export class ObjApprovalRootInput{
            cid : String;
            sid : String;
            employmentRootAtr : number;
            appType : number;
            standardDate :  Date;
            constructor (cid : String,
                        sid : String,employmentRootAtr : number,
                        appType : number,standardDate : Date){
                this.cid  = cid;
                this.sid = sid; 
                this.employmentRootAtr =employmentRootAtr;
                this.appType = appType;
                this.standardDate = standardDate; 
            }
        }//end class ObjApprovalRootInput
        
    }
}