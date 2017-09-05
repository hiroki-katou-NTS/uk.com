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
        constructor(){
            var self = this;
            /**
             * List
             */
            
            self.listPhase = ko.observableArray([]);
            self.listPhaseID = [];
            self.listFrame = ko.observableArray([]);
        }
        
        start(): JQueryPromise<any> {
            
            let self = this;
            var dfd = $.Deferred();
            //alert("sdfds");
            //self.getAllPhase();
            var dfdAllPhase = self.getAllPhase();
            $.when(dfdAllPhase).done((dfdAllPhaseData)=>{
//                self.listPhase(dfdAllPhaseData);
//                self.listFrame(dfdAllFrameData);
                //alert("ccc");    
                 dfd.resolve(); 
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
        getAllFrame(listPhaseID){
            var self = this;
            var dfd = $.Deferred<any>();
                service.getAllFrameByPhaseID(listPhaseID).done(function(data){
                    self.listFrame(data);
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
            constructor(phaseID : String,dispOrder : number,approverSID : String,approvalATR : number,confirmATR : number){
                this.phaseID = ko.observable(phaseID);
                this.dispOrder = ko.observable(dispOrder);
                this.approverSID  = ko.observable(approverSID);
                this.approvalATR = ko.observable(approvalATR); 
                this.confirmATR = ko.observable(confirmATR);
                
            }
        }//end class frame   
        
    }
}