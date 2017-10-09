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
        //obj output message deadline
        outputMessageDeadline: KnockoutObservable<model.OutputMessageDeadline>;
        //obj DetailedScreenPreBootModeOutput
        outputDetailCheck :  KnockoutObservable<model.DetailedScreenPreBootModeOutput>;
        //obj InputCommandEvent
        inputCommandEvent :  KnockoutObservable<model.InputCommandEvent>;
        
        
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
         //enable enableBefore
        enableBefore : KnockoutObservable<boolean>;
        //enable enableAfter
        enableAfter: KnockoutObservable<boolean>;
        
        /**
         * visible
         */
        //visible Approval
        visibleApproval : KnockoutObservable<boolean>;
        //visible Denial
        visibleDenial : KnockoutObservable<boolean>;
        
        //listAppID
        listAppId : Array<any>;
        
        
        //item InputCommandEvent
        appReasonEvent : KnockoutObservable<String>;
        
        constructor(appType: number) {
            let self = this;
            //reason input event
            self.appReasonEvent = ko.observable('123');
            
            self.listAppId = [];
            //__viewContext.transferred.value.appID;
            self.appType = ko.observable(appType);
            
            __viewContext.transferred.ifPresent(value => {
                    self.listAppId = value.listAppId;    
                    return {};
                });
            
            // Metadata
            self.appID =ko.observable(self.listAppId[0]); 
            self.inputCommandEvent = ko.observable(new model.InputCommandEvent(self.appID(),self.appReasonEvent()));
            
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
            self.inputDetail = ko.observable(new model.InputGetDetailCheck(self.appID(), "2022/01/01"));
            self.outputDetailCheck = ko.observable(null);

            //obj output get message deadline 
            self.outputMessageDeadline = ko.observable(null);
            
            /**
             * enable
             */
            self.enableApprove = ko.observable(true);
            self.enableDeny = ko.observable(false);
            self.enableRelease = ko.observable(true);
            self.enableRegistration = ko.observable(true);
            self.enableDelete = ko.observable(true);
            self.enableCancel = ko.observable(true);
            self.enableBefore = ko.observable(true);
            self.enableAfter = ko.observable(true);
            /**
             * visible
             */
            self.visibleApproval = ko.observable(false)
            self.visibleDenial = ko.observable(false)
            
            
        }
                
        abstract update(): any;

        start(appType,baseDate): JQueryPromise<any> {
            let self = this;
            
            self.appType(appType);
            
            self.inputDetail().baseDate =baseDate;
            let dfd = $.Deferred();
            let dfdMessageDeadline = self.getMessageDeadline(self.appType());
            let dfdAllReasonByAppID = self.getAllReasonByAppID(self.appID());
            let dfdAllDataByAppID = self.getAllDataByAppID(self.appID());
            let dfdGetDetailCheck = self.getDetailCheck(self.inputDetail());
            if(self.appID() ==  self.listAppId[0]){
                self.enableBefore(false);    
            }else{
                self.enableBefore(true);
            }
            
            if(self.appID() ==  self.listAppId[self.listAppId.length-1]){
                self.enableAfter(false);    
            }else{
                self.enableAfter(true);
            }
            

            $.when( dfdAllReasonByAppID,dfdAllDataByAppID,dfdGetDetailCheck).done((dfdAllReasonByAppIDData,dfdAllDataByAppIDData,dfdGetDetailCheckData) => {
                
                //self.checkDisplayStart();
                dfd.resolve();
            });
            return dfd.promise();
        }   //end start
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
                    self.listReasonToApprover('');
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
         * btn before
         */
        btnBefore(){
            let self = this;
            let index = self.listAppId.indexOf(self.appID());
            if(index !=0){
                self.appID(self.listAppId[index-1]);
                self.start(self.appType(),
                           self.inputDetail().baseDate);
            }
        }
        /**
         * btn after
         */
        btnAfter(){
            let self = this;
            let index = self.listAppId.indexOf(self.appID());
            if(index != self.listAppId.length-1){
                self.appID(self.listAppId[index+1]);
                
                
                self.start(self.appType(),
                           self.inputDetail().baseDate);
            }
        }
        /**
         *  btn Approve
         */
        btnApprove(){
            let self = this;
            self.inputCommandEvent(new model.InputCommandEvent(self.appID(),self.appReasonEvent()));
            let dfd = $.Deferred<any>();
            service.approveApp(self.inputCommandEvent()).done(function() {
                dfd.resolve();
            });
            return dfd.promise();
        }
         /**
         *  btn Deny
         */
        btnDeny(){
            let self = this;
            self.inputCommandEvent(new model.InputCommandEvent(self.appID(),self.appReasonEvent()));
            let dfd = $.Deferred<any>();
            service.denyApp(self.inputCommandEvent()).done(function() {
                dfd.resolve();
            });
            return dfd.promise();
        }
        
         /**
         *  btn Release
         */
        btnRelease(){
            let self = this;
            self.inputCommandEvent(new model.InputCommandEvent(self.appID(),self.appReasonEvent()));
            let dfd = $.Deferred<any>();
            nts.uk.ui.dialog.confirm({ messageId: 'Msg_28' }).ifYes(function () {
                service.releaseApp(self.inputCommandEvent()).done(function() {
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
            self.inputCommandEvent(new model.InputCommandEvent(self.appID(),self.appReasonEvent()));
            let dfd = $.Deferred<any>();
            nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function () {
                service.deleteApp(self.inputCommandEvent()).done(function(data) {
                    
                    nts.uk.ui.dialog.alert({messageId : 'Msg_16'}).then(function(){
                        //kiểm tra list người xác nhận, nếu khác null thì show info 392
                        if(data.length !=0){
                            nts.uk.ui.dialog.info({messageId : 'Msg_392'});    
                        }
                    });
                    //lấy vị trí appID vừa xóa trong listAppID
                    let index = self.listAppId.indexOf(self.appID());
                    if (index > -1) {
                        //xóa appID vừa xóa trong list
                        self.listAppId.splice(index, 1);
                    }
                    //nếu vị trí vừa xóa khác vị trí cuối
                    if(index !=self.listAppId.length-1){
                        //gán lại appId mới tại vị trí chính nó
                        self.appID(self.listAppId[index]);
                    }else{
                        //nếu nó ở vị trí cuối thì lấy appId ở vị trí trước nó
                        self.appID(self.listAppId[index-1]);
                    }
                    //if list # null    
                    if(self.listAppId.length!=0)
                    {
                        self.start(self.appType(),
                           self.inputDetail().baseDate);
                    }else{ //nếu list null thì trả về màn hình mẹ
                        nts.uk.request.jump("/view/kaf/000/test/index.xhtml");    
                    }
                    
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
            self.inputCommandEvent(new model.InputCommandEvent(self.appID(),self.appReasonEvent()));
            let dfd = $.Deferred<any>();
            nts.uk.ui.dialog.confirm({ messageId: 'Msg_249' }).ifYes(function () {
                service.cancelApp( self.inputCommandEvent()).done(function() {
                    nts.uk.ui.dialog.alert({ messageId: "Msg_224" })
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

        //class outputMessageDeadline
        export class OutputMessageDeadline {
            message: String;
            deadline: String;
            constructor(message: String, deadline: String) {
                this.message = message;
                this.deadline = deadline;
            }
        }// end class outputMessageDeadline
        
        //class InputCommandEvent
        export class InputCommandEvent{
            appId : String;
            applicationReason : String;
            constructor(appId : String,applicationReason : String){
                this.appId = appId;
                this.applicationReason = applicationReason;
            }
        }
    }
}