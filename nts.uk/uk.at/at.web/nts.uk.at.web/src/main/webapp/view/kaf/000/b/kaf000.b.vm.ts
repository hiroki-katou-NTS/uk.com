module nts.uk.at.view.kaf000.b.viewmodel {
    
    export abstract class ScreenModel {

        // Metadata
        appID: KnockoutObservable<string>;
        appType: KnockoutObservable<number>;
        
        /**
         * List
         */
        //listPhase
        listPhase: KnockoutObservableArray<model.AppApprovalPhase>;
        //listPhaseID
        listPhaseID: Array<String>;
        //list appID 
        listReasonByAppID: KnockoutObservableArray<String>;


        /**
         * value obj 
         */
        listReasonToApprover: KnockoutObservable<String>;
        reasonApp: KnockoutObservable<String>;

        dataApplication: KnockoutObservable<model.OutputGetAllDataApp>;

        //application
        objApp: KnockoutObservable<model.ApplicationDto>;
        inputDetail: KnockoutObservable<model.InputGetDetailCheck>;

        //obj input
        inputMessageDeadline: KnockoutObservable<model.InputMessageDeadline>;
        //obj output message deadline
        outputMessageDeadline: KnockoutObservable<model.OutputMessageDeadline>;
        //obj DetailedScreenPreBootModeOutput
        outputDetailCheck :  KnockoutObservable<model.DetailedScreenPreBootModeOutput>;
        /**
         * enable button
         */
        //enable Approve
        enableApprove : KnockoutObservable<boolean>;
        //enable Deny
        enableDeny : KnockoutObservable<boolean>;
        //enable Release
        enableRelease : KnockoutObservable<boolean>;
        //enable Registration
        enableRegistration :KnockoutObservable<boolean>;
        //enable enableDelete
        enableDelete : KnockoutObservable<boolean>;
        //enable enableCancel
        enableCancel: KnockoutObservable<boolean>;
        
        /**
         * visible
         */
        //visible Approval
        visibleApproval : KnockoutObservable<boolean>;
        //visible Denial
        visibleDenial : KnockoutObservable<boolean>;
        
        
        constructor(appType: number) {
            let self = this;
            // Metadata
            self.appID =ko.observable("000"); 
            //__viewContext.transferred.value.appID;
            self.appType = ko.observable(appType);
            
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
            self.objApp = (null);
            self.inputDetail = ko.observable(new model.InputGetDetailCheck("000", "2022/01/01"));
            self.outputDetailCheck = ko.observable(null);

            //obj input get message deadline 
            self.inputMessageDeadline = ko.observable(new model.InputMessageDeadline("000000000000-0005", null, 1, null));
            //obj input get message deadline 
            self.outputMessageDeadline = ko.observable(null);
            
            /**
             * enable
             */
            self.enableApprove = ko.observable(true);
            self.enableDeny = ko.observable(false);
            self.enableRelease = ko.observable(true);
            self.enableRegistration = ko.observable(false);
            self.enableDelete = ko.observable(true);
            self.enableCancel = ko.observable(true);
            /**
             * visible
             */
            self.visibleApproval = ko.observable(false)
            self.visibleDenial = ko.observable(false)
        }
                
        abstract update(): any;

        start(): JQueryPromise<any> {

            let self = this;
            let dfd = $.Deferred();
            let dfdMessageDeadline = self.getMessageDeadline(self.inputMessageDeadline());
            let dfdAllReasonByAppID = self.getAllReasonByAppID("000");
            let dfdAllDataByAppID = self.getAllDataByAppID("000");
            let dfdGetDetailCheck = self.getDetailCheck(self.inputDetail());
            

            $.when( dfdAllReasonByAppID,dfdAllDataByAppID,dfdGetDetailCheck).done((dfdAllReasonByAppIDData,dfdAllDataByAppIDData,dfdGetDetailCheckData) => {
                
                //self.checkDisplayStart();
                dfd.resolve();
            });
            return dfd.promise();
        }   
        // check display start
        checkDisplayStart(){
            let self = this;
            if(self.outputDetailCheck() != null){
                if(self.outputDetailCheck().user ==1 || self.outputDetailCheck().user ==2){
                    //b1_7
                    self.enableRelease(true);
                    
                    
                    if(self.outputDetailCheck().authorizableFlags == true){
                        //例：ログイン者の承認区分が未承認
                        if(self.outputDetailCheck().approvalATR == 0){
                            self.enableApprove(true);
                            self.enableDeny(true);
                            self.visibleApproval(false);
                            self.visibleDenial(false);     
                        }
                        
                        //ログイン者の承認区分が承認済
                        if(self.outputDetailCheck().approvalATR == 1){
                            self.enableApprove(false);
                            self.enableDeny(true);
                            self.visibleApproval(true);
                            self.visibleDenial(false);        
                        }
                        
                        //例：ログイン者の承認区分が否認
                        if(self.outputDetailCheck().approvalATR == 2){
                            self.enableApprove(true);
                            self.enableDeny(false);     
                            self.visibleApproval(false);
                            self.visibleDenial(true);   
                        }
                    }else{
                        self.enableApprove(false);
                        self.enableDeny(false);
                        self.visibleApproval(false);
                        self.visibleDenial(false);   
                    }
                }     
            }
            
            
            if(self.outputDetailCheck().user ==0 || self.outputDetailCheck().user ==1|| self.outputDetailCheck().user ==99){
                //b1_8
                self.enableRegistration(true)
                //b1_12  
                self.enableDelete(true)  
                
                if(true){
                    //b1_13
                }
            }
            
        } // end checkDisplayStart
        
        //check checkDisplayAction
        checkDisplayAction(){
            let self = this;
            if(self.outputDetailCheck() != null){
                if(self.outputDetailCheck().authorizableFlags ==true && self.outputDetailCheck().alternateExpiration ==false){
                    self.enableApprove(true);//b1_4
                    self.enableDeny(true);//b1_5
                    self.visibleApproval(true); //b1_14
                    self.visibleDenial(true);  //b1_15
                }
            
                //b1_7
                //ログイン者の承認区分：承認済、否認 
                if(self.outputDetailCheck().approvalATR ==1 || self.outputDetailCheck().approvalATR ==2){
                    self.enableRelease(true);
                }
                //ログイン者の承認区分：未承認                      
                if(self.outputDetailCheck().approvalATR ==0){
                     self.enableRelease(false);
                }
                
                
                if(self.outputDetailCheck().reflectPlanState ==0 ||self.outputDetailCheck().reflectPlanState ==1){
                    //b1_8
                    self.enableRegistration(true)
                    //b1_12  
                    self.enableDelete(true) 
                }
                
                if(self.outputDetailCheck().reflectPlanState ==4){
                    //b1_13
                    self.enableCancel(true) 
                }        
            }    
        
        }

        // getMessageDeadline
        getMessageDeadline(inputMessageDeadline) {
            let self = this;
            let dfd = $.Deferred<any>();
            service.getMessageDeadline(inputMessageDeadline).done(function(data) {
                self.outputMessageDeadline(data);
                dfd.resolve(data);
            });
            return dfd.promise();
        }

        //getAll data by App ID
        getAllDataByAppID(appID) {
            let self = this;
            let dfd = $.Deferred<any>();
            service.getAllDataByAppID(appID).done(function(data) {
//                let temp = data.listOutputPhaseAndFrame;
//                _.forEach(temp, function(phase) {
//                    let listApproveAcceptedForView = [];
//                    //con cai clone la de clone ra 1 array moi, tranh bi anh huong array goc(tạo ra 1 bản sao)
//                    // _.sortBy sắp xếp theo dispOrder,
//                    let frameTemp = _.sortBy(_.clone(phase.listApprovalFrameDto), ['dispOrder']);
//                    for (var i = 0; i < frameTemp.length; i++) {
//                        let frame = frameTemp[i];
//                        let sameOrder = _.filter(phase.listApproveAcceptedDto, function(f) {
//                            return frame["dispOrder"] === f["dispOrder"];
//                        });
//                        let approverSID = "";
//                        _.forEach(sameOrder, function(so) {
//                            approverSID += (nts.uk.util.isNullOrEmpty(approverSID) ? "" : ", ") + so["approverSID"];
//                        });
//                        frame["approverSID2"] = approverSID;
//                        listApproveAcceptedForView.push(frame);
//                    }
//                    phase["listApproveAcceptedForView"] = listApproveAcceptedForView;
//                });
//                data.listOutputPhaseAndFrame = temp;
                self.dataApplication(data);
                dfd.resolve(data);
            });
            return dfd.promise();
        }
        //get all reason by app ID
        getAllReasonByAppID(appID) {
            let self = this;
            let dfd = $.Deferred<any>();
            service.getAllReasonByAppID(appID).done(function(data) {
                self.listReasonByAppID(data);
                if(self.listReasonByAppID().length>0){
                    self.reasonApp(self.listReasonByAppID()[0].toString());
                    for (let i = 1; i < self.listReasonByAppID().length; i++) {
                        self.listReasonToApprover(
                            self.listReasonToApprover().toString() + self.listReasonByAppID()[i].toString() + "\n"
                        );
                    }
                }
                dfd.resolve(data);
            });
            return dfd.promise();
        }
        //get detail check 
        getDetailCheck(inputGetDetail) {
            let self = this;
            let dfd = $.Deferred<any>();
            service.getDetailCheck(inputGetDetail).done(function(data) {
                //
                self.outputDetailCheck(data);
                dfd.resolve(data);
            });
            return dfd.promise();
        }
        
        /**
         *  btn Approve
         */
        btnApprove(){
            let self = this;
            let dfd = $.Deferred<any>();
            service.approveApp(self.appID()).done(function() {
                dfd.resolve();
            });
            return dfd.promise();
        }
         /**
         *  btn Deny
         */
        btnDeny(){
            let self = this;
            let dfd = $.Deferred<any>();
            service.denyApp(self.appID()).done(function() {
                dfd.resolve();
            });
            return dfd.promise();
        }
        
         /**
         *  btn Release
         */
        btnRelease(){
            let self = this;
            let dfd = $.Deferred<any>();
            nts.uk.ui.dialog.confirm({ messageId: 'Msg_28' }).ifYes(function () {
                service.releaseApp(self.appID()).done(function() {
                    dfd.resolve();
                });
            });
            return dfd.promise();
        }
        
        /**
         *  btn Registration
         */
        btnRegistration(){
            
        }
        /**
         *  btn References 
         */
        btnReferences(){
            let self = this;
            // send (Cid,Eid,date) in screen KDL004
            //nts.uk.request.jump("/view/kdl/004/a/index.xhtml");
        }
        /**
         *  btn SendEmail 
         */
        btnSendEmail(){
             let self = this;
            // send (Cid, appId , content, Eid, date) in screen KDL030
            //nts.uk.request.jump("/view/kdl/030/a/index.xhtml");
        }
        /**
         *  btn Delete 
         */
        btnDelete(){
            let self = this;
            let dfd = $.Deferred<any>();
            nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function () {
                service.deleteApp(self.appID()).done(function() {
                    dfd.resolve();
                });
            });   
            return dfd.promise();
        }
        /**
         *  btn Cancel 
         */
        btnCancel(){
            let self = this;
            let dfd = $.Deferred<any>();
            nts.uk.ui.dialog.confirm({ messageId: 'Msg_249' }).ifYes(function () {
                service.cancelApp(self.appID()).done(function() {
                    nts.uk.ui.dialog.alert({ messageId: "Msg_224" });
                    dfd.resolve();
                });
            });
            return dfd.promise();
        }
        
        
    }

    export module model {
        //class OutputGetAllDataApp
        export class OutputGetAllDataApp {
            applicationDto: ApplicationDto;
            listOutputPhaseAndFrame: Array<OutputPhaseAndFrame>;
            constructor(applicationDto: ApplicationDto,
                listOutputPhaseAndFrame: Array<OutputPhaseAndFrame>) {
                this.applicationDto = applicationDto;
                this.listOutputPhaseAndFrame = listOutputPhaseAndFrame;
            }
        }//end class OutputGetAllDataApp

        //class Application 
        export class ApplicationDto {
            applicationID: String;
            prePostAtr: number;
            inputDate: Date;
            enteredPersonSID: String;
            reversionReason: String;
            applicationDate: Date;
            applicationReason: String;
            applicationType: number;
            applicantSID: String;
            reflectPlanScheReason: number;
            reflectPlanTime: Date;
            reflectPlanState: number;
            reflectPlanEnforce: number;
            reflectPerScheReason: number;
            reflectPerTime: Date;
            reflectPerState: number;
            reflectPerEnforce: number;
            startDate: Date;
            endDate: Date;
            constructor(
                applicationID: String,
                prePostAtr: number,
                inputDate: Date,
                enteredPersonSID: String,
                reversionReason: String,
                applicationDate: Date,
                applicationReason: String,
                applicationType: number,
                applicantSID: String,
                reflectPlanScheReason: number,
                reflectPlanTime: Date,
                reflectPlanState: number,
                reflectPlanEnforce: number,
                reflectPerScheReason: number,
                reflectPerTime: Date,
                reflectPerState: number,
                reflectPerEnforce: number,
                startDate: Date,
                endDate: Date) {
                this.applicationID = applicationID;
                this.prePostAtr = prePostAtr;
                this.inputDate = inputDate;
                this.enteredPersonSID = enteredPersonSID;
                this.reversionReason = reversionReason;
                this.applicationDate = applicationDate;
                this.applicationReason = applicationReason;
                this.applicationType = applicationType;
                this.applicantSID = applicantSID;
                this.reflectPlanScheReason = reflectPlanScheReason;
                this.reflectPlanTime = reflectPlanTime;
                this.reflectPlanState = reflectPlanState;
                this.reflectPlanEnforce = reflectPlanEnforce;
                this.reflectPerScheReason = reflectPerScheReason;
                this.reflectPerTime = reflectPerTime;
                this.reflectPerState = reflectPerState;
                this.reflectPerEnforce = reflectPerEnforce;
                this.startDate = startDate;
                this.endDate = endDate;
            }
        }//end class Application


        //class OutputPhaseAndFrame 
        export class OutputPhaseAndFrame {
            appApprovalPhase: AppApprovalPhase;
            listApprovalFrame: Array<ApprovalFrame>;
            constructor(
                appApprovalPhase: AppApprovalPhase,
                listApprovalFrame: Array<ApprovalFrame>) {
                this.appApprovalPhase = appApprovalPhase;
                this.listApprovalFrame = listApprovalFrame;
            }
        }//end class OutputPhaseAndFrame


        // class AppApprovalPhase
        export class AppApprovalPhase {
            appID: String;
            phaseID: String;
            approvalForm: number;
            dispOrder: number;
            approvalATR: number;
            constructor(appID: String, phaseID: String, approvalForm: number, dispOrder: number, approvalATR: number) {
                this.appID = appID;
                this.phaseID = phaseID;
                this.approvalForm = approvalForm;
                this.dispOrder = dispOrder;
                this.approvalATR = approvalATR;
            }
        }

        // class ApprovalFrame
        export class ApprovalFrame {
            frameID : String;
            phaseID: String;
            dispOrder:number;
            listApproveAccepted: Array<ApproveAccepted>;
            constructor(frameID : String,phaseID: String, dispOrder: number,listApproveAccepted: Array<ApproveAccepted>) {
                this.frameID = frameID;
                this.phaseID = phaseID;
                this.dispOrder = dispOrder;
                this.listApproveAccepted = listApproveAccepted;
                
            }
        }//end class frame  

        //class ApproveAccepted
        export class ApproveAccepted {
            appAccedtedID : String;
            frameID: String;
            approverSID: String;
            approvalATR: number;
            confirmATR: number;
            approvalDate: String;
            reason: String;
            representerSID: String;
        }//end class ApproveAccepted

        //class InputGetDetailCheck 
        export class InputGetDetailCheck {
            applicationID: String;
            baseDate: String ;
            constructor(applicationID: String,
                baseDate: String) {
                this.applicationID = applicationID;
                this.baseDate = baseDate;

            }
        }//end class InputGetDetailCheck


        //class DetailedScreenPreBootModeOutput
        export class DetailedScreenPreBootModeOutput {
            user: number;
            reflectPlanState: number;
            authorizableFlags: boolean;
            approvalATR: number;
            alternateExpiration: boolean;
            constructor(user: number,
                reflectPlanState: number,
                authorizableFlags: boolean,
                approvalATR: number,
                alternateExpiration: boolean) {
                this.user = user;
                this.reflectPlanState = reflectPlanState;
                this.authorizableFlags = authorizableFlags;
                this.approvalATR = approvalATR;
                this.alternateExpiration = alternateExpiration;
            }
        }//end class DetailedScreenPreBootModeOutput


        //class InputMessageDeadline
        export class InputMessageDeadline {
            companyID: String;
            workplaceID: String;
            appType: number;
            appDate: Date;
            constructor(companyID: String, workplaceID: String, appType: number, appDate: Date) {
                this.companyID = companyID;
                this.workplaceID = workplaceID;
                this.appType = appType;
                this.appDate = appDate;
            }

        }//end class InputMessageDeadline

        //class outputMessageDeadline
        export class OutputMessageDeadline {
            message: String;
            deadline: String;
            constructor(message: String, deadline: String) {
                this.message = message;
                this.deadline = deadline;
            }
        }// end class outputMessageDeadline
    }
}