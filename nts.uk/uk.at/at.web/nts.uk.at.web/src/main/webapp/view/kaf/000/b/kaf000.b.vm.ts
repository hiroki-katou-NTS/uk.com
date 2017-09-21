module kaf000.b.viewmodel{
    import kaf002 = nts.uk.at.view.kaf002;
    export class ScreenModel{
        
        cm = new kaf002.cm.viewmodel.ScreenModel();
        
        /**
         * List
         */
        //listPhase
        listPhase: KnockoutObservableArray<model.AppApprovalPhase>;
        //listPhaseID
        listPhaseID: Array<String>;
        //list appID 
        listReasonByAppID : KnockoutObservableArray<String>;
        
        
        
        /**
         * value obj 
         */
        listReasonToApprover : KnockoutObservable<String>;
        reasonApp :KnockoutObservable<String>;
        
        dataApplication  : KnockoutObservable<model.OutputGetAllDataApp>;
        
        //application
        objApp : KnockoutObservable<model.Application>;
        inputDetail : KnockoutObservable<model.InputGetDetailCheck>;
        outputDetail : KnockoutObservable<model.DetailedScreenPreBootModeOutput>;
       
        
        
        constructor(){
            var self = this;
            /**
             * List
             */
            
            self.listPhase = ko.observableArray([]);
            self.listReasonByAppID = ko.observableArray([]);
            /**
             * value obj
             */
            self.listReasonToApprover = ko.observable('');
            self.reasonApp = ko.observable('');
            self.dataApplication = ko.observable(null);
            //application
            self.objApp= ko.observable(null);
            self.inputDetail= ko.observable(null);
            self.outputDetail= ko.observable(null);
            
            
        }
        
        start(): JQueryPromise<any> {
            
            let self = this;
            var dfd = $.Deferred();
            var dfdAllReasonByAppID = self.getAllReasonByAppID("000");
            var dfdAllDataByAppID = self.getAllDataByAppID("000");
            //var dfdGetDetailCheck = self.getDetailCheck(self.inputDetail());
            
            $.when(dfdAllReasonByAppID,dfdAllDataByAppID).done((dfdAllReasonByAppIDData,dfdAllDataByAppIDData)=>{
                //self.listReasonByAppID(data);
                
                 dfd.resolve(); 
            });
            return dfd.promise();
        }
        
        //getAll data by App ID
        getAllDataByAppID(appID){
            var self = this;
            var dfd = $.Deferred<any>();
            service.getAllDataByAppID(appID).done(function(data){
                self.dataApplication(data);
                self.objApp(self.dataApplication().application);
                self.inputDetail(new model.InputGetDetailCheck(null,new Date()));
                
                dfd.resolve(data);    
            });
            return dfd.promise();
        }
         //get all reason by app ID
        getAllReasonByAppID(appID){
            var self = this;
            var dfd = $.Deferred<any>();
                service.getAllReasonByAppID(appID).done(function(data){
                    self.listReasonByAppID(data);
                    self.reasonApp(self.listReasonByAppID()[0].toString());
                    for(var i = 1;i<self.listReasonByAppID().length;i++){
                    self.listReasonToApprover(
                        self.listReasonToApprover().toString() + self.listReasonByAppID()[i].toString() + "\n"
                        );  
                    }
                    
                    dfd.resolve(data);    
                }); 
            return dfd.promise();
        }
         //get detail check 
        getDetailCheck(inputGetDetail){
            var self = this;
            var dfd = $.Deferred<any>();
                service.getDetailCheck(inputGetDetail).done(function(data){
                    //
                    dfd.resolve(data);    
                }); 
            return dfd.promise();
        }
    }
    
    export module model {
        //class OutputGetAllDataApp
        export class OutputGetAllDataApp{
            application : Application;
            listOutputPhaseAndFrame : Array<OutputPhaseAndFrame>;
            constructor( application : Application,
            listOutputPhaseAndFrame : Array<OutputPhaseAndFrame> ){
                this.application = application;
                this.listOutputPhaseAndFrame = listOutputPhaseAndFrame;
            }
        }//end class OutputGetAllDataApp
        
        //class Application 
        export class Application{
            applicationID : String;
            prePostAtr :number;
            inputDate : Date;
            enteredPersonSID : String;
            reversionReason :String;
            applicationDate : Date;
            applicationReason : String;
            applicationType : number;
            applicantSID : String;
            reflectPlanScheReason : number;
            reflectPlanTime : Date;
            reflectPlanState : number;
            reflectPlanEnforce: number;
            reflectPerScheReason : number;
            reflectPerTime : Date;
            reflectPerState : number;
            reflectPerEnforce : number;
            startDate : Date;
            endDate : Date;
            constructor(
            applicationID : String,
            prePostAtr :number,
            inputDate : Date,
            enteredPersonSID : String,
            reversionReason :String,
            applicationDate : Date,
            applicationReason : String,
            applicationType : number,
            applicantSID : String,
            reflectPlanScheReason : number,
            reflectPlanTime : Date,
            reflectPlanState : number,
            reflectPlanEnforce: number,
            reflectPerScheReason : number,
            reflectPerTime : Date,
            reflectPerState : number,
            reflectPerEnforce : number,
            startDate : Date,
            endDate : Date){
                this.applicationID  = applicationID;
                this.prePostAtr  = prePostAtr;
                this.inputDate  = inputDate;
                this.enteredPersonSID  = enteredPersonSID;
                this.reversionReason  = reversionReason;
                this.applicationDate  = applicationDate;
                this.applicationReason  = applicationReason;
                this.applicationType  = applicationType;
                this.applicantSID  = applicantSID;
                this.reflectPlanScheReason  = reflectPlanScheReason;
                this.reflectPlanTime  = reflectPlanTime;
                this.reflectPlanState  = reflectPlanState;
                this.reflectPlanEnforce = reflectPlanEnforce;
                this.reflectPerScheReason  = reflectPerScheReason;
                this.reflectPerTime  = reflectPerTime;
                this.reflectPerState = reflectPerState;
                this.reflectPerEnforce = reflectPerEnforce;
                this.startDate = startDate;
                this.endDate = endDate;
            }
        }//end class Application
        
        
        //class OutputPhaseAndFrame 
        export class OutputPhaseAndFrame{
            appApprovalPhase : AppApprovalPhase;
            listApprovalFrame : Array<ApprovalFrame>;
            constructor(
            appApprovalPhase : AppApprovalPhase,
            listApprovalFrame : Array<ApprovalFrame>){
                this.appApprovalPhase = appApprovalPhase;
                this.listApprovalFrame = listApprovalFrame;
            }
        }//end class OutputPhaseAndFrame
        
        
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
        }//end class frame   
        //class InputGetDetailCheck 
        export class InputGetDetailCheck{
            application : Application;
            baseDate : Date;
            constructor(application : Application,
            baseDate : Date){
                this.application = application;
                this.baseDate = baseDate;
                
            }
        }//end class InputGetDetailCheck
        
        
        //class DetailedScreenPreBootModeOutput
        export class DetailedScreenPreBootModeOutput{
            user : number;
            reflectPlanState :number;
            authorizableFlags:boolean;
            approvalATR:number;
            alternateExpiration:boolean;
            constructor (user : number,
                    reflectPlanState :number,
                    authorizableFlags :boolean,
                    approvalATR :number,
                    alternateExpiration :boolean){
                this.user = user;
                this.reflectPlanState = reflectPlanState;
                this.authorizableFlags = authorizableFlags;
                this.approvalATR = approvalATR;
                this.alternateExpiration = alternateExpiration;
            }
        }//end class DetailedScreenPreBootModeOutput
    }
}