module kaf000.a.viewmodel{
    export class ScreenModel{
        /**
         * List
         */
        //listPhase
        listPhase: KnockoutObservableArray<AppApprovalPhase>;
        //listFrame
        listFrame: KnockoutObservableArray<ApprovalFrame>;
        constructor(){
            var self = this;
            /**
             * List
             */
            
            self.listPhase = ko.observableArray([]);
            self.listFrame = ko.observableArray([]);
        }
        
        start(): JQueryPromise<any> {
            let self = this;
            alert("sdfds");
            self.getAllPhase();
            
            alert("sdfd1");
            self.getAllFrame();
            
        }
        
        //getAllPhase
        getAllPhase(){
            let appID='000';
            var self = this;
            var dfd = $.Deferred<any>();
            service.getAllPhaseByAppID(appID).done(function(data){
                self.listPhase(data);
                dfd.resolve(data);    
            });
            return dfd.promise();
        }
        //getAllFrame
        getAllFrame(){
            let phaseID = 'P000';
            var self = this;
            var dfd = $.Deferred<any>();
            service.getAllFrameByPhaseID(phaseID).done(function(data){
                self.listFrame(data);
                dfd.resolve(data);    
            });
            return dfd.promise();
        }
        
    }
    
    export module model {
        
        // class AppApprovalPhase
        export class AppApprovalPhase{
            appID : String;
            phaseID : String;
            approvalForm : number;
            dispOrder : number;
            approvalATR : number;    
            constructor(appID : String,phaseID : String,approvalForm : number,dispOrder : number,approvalATR : number){
                this.appID  = appID;
                this.phaseID = phaseID;
                this.approvalForm = approvalForm;
                this.dispOrder = dispOrder;
                this.approvalATR = approvalATR; 
            }
        } 
        
        // class ApprovalFrame
        export class ApprovalFrame{
            phaseID : String;
            dispOrder : number;
            approverSID : String;
            approvalATR : number;
            confirmATR : number;    
            constructor(phaseID : String,dispOrder : number,approverSID : String,approvalATR : number,confirmATR : number){
                this.phaseID = phaseID;
                this.dispOrder = dispOrder;
                this.approverSID  = approverSID;
                this.approvalATR = approvalATR; 
                this.confirmATR = confirmATR;
                
            }
        }//end class frame   
        
    }
}