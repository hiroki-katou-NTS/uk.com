module nts.uk.at.view.kaf000.b.viewmodel {
    import vmbase =  nts.uk.at.view.kaf002.shr.vmbase;
    import shrvm = nts.uk.at.view.kaf000.shr;
    export abstract class ScreenModel {

        // Metadata
        appID: KnockoutObservable<string>;
        appType: KnockoutObservable<number>;

        /**
         * List
         */
        //listPhase
        listPhase: KnockoutObservableArray<shrvm.model.AppApprovalPhase>;
        //listPhaseID
        listPhaseID: Array<string>;
        //list appID 
        listReasonByAppID: KnockoutObservableArray<string>;

        /**InputCommonData
         * value obj 
         */
        reasonToApprover: KnockoutObservable<string>;
        reasonApp: KnockoutObservable<string>;
        inputCommonData : KnockoutObservable<model.InputCommonData>;

        dataApplication: KnockoutObservable<model.ApplicationDto>;

        //application
        inputDetail: KnockoutObservable<model.InputGetDetailCheck>;

        //obj input
        //obj output message deadline
        outputMessageDeadline: KnockoutObservable<shrvm.model.OutputMessageDeadline>;
        //obj DetailedScreenPreBootModeOutput
        outputDetailCheck: KnockoutObservable<model.DetailedScreenPreBootModeOutput>;
        //obj InputCommandEvent
        inputCommandEvent: KnockoutObservable<model.InputCommandEvent>;


        /**
         * enable button
         */
        //enable Approve
        enableApprove: KnockoutObservable<boolean>;
        //enable Deny
        enableDeny: KnockoutObservable<boolean>;
        //enable Release
        enableRelease: KnockoutObservable<boolean>;
        //enable Registration
        enableRegistration: KnockoutObservable<boolean>;
        //enable enableDelete
        enableDelete: KnockoutObservable<boolean>;
        //enable enableCancel
        enableCancel: KnockoutObservable<boolean>;
        //enable enableBefore
        enableBefore: KnockoutObservable<boolean>;
        //enable enableAfter
        enableAfter: KnockoutObservable<boolean>;

        /**
         * visible
         */
        //visible Approval
        visibleApproval: KnockoutObservable<boolean>;
        //visible Denial
        visibleDenial: KnockoutObservable<boolean>;

        //listAppID
        listAppMeta: Array<any>;


        //item InputCommandEvent
        appReasonEvent: KnockoutObservable<string>;
        
        approvalList: Array<vmbase.AppApprovalPhase> = [];
        
        displayButtonControl: KnockoutObservable<model.DisplayButtonControl> = ko.observable(new model.DisplayButtonControl());

        constructor(listAppMetadata: Array<shrvm.model.ApplicationMetadata>, currentApp: shrvm.model.ApplicationMetadata) {
            let self = this;
            //reason input event
            self.appReasonEvent = ko.observable('');
            // Metadata
            self.listAppMeta = listAppMetadata;
            self.appType = ko.observable(currentApp.appType);
            self.appID = ko.observable(currentApp.appID);

            self.inputCommandEvent = ko.observable(new model.InputCommandEvent(self.appID(), self.appReasonEvent()));

            /**
             * List
             */
            self.listPhase = ko.observableArray([]);
            self.listReasonByAppID = ko.observableArray([]);
            /**
             * value obj
             */
            self.reasonToApprover = ko.observable('');
            self.reasonApp = ko.observable('');
            self.dataApplication = ko.observable(null);
            self.inputCommonData = ko.observable(null);
            //application
            self.inputDetail = ko.observable(new model.InputGetDetailCheck(self.appID(), "2022/01/01"));
            self.outputDetailCheck = ko.observable(null);

            //obj output get message deadline 
            self.outputMessageDeadline = ko.observable(null);

            /**
             * enable
             */
            self.enableApprove = ko.observable(true);
            self.enableDeny = ko.observable(true);
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

            if (self.appID() == self.listAppMeta[0].appID) {
                self.enableBefore(false);
            } else {
                self.enableBefore(true);
            }

            if (self.appID() == self.listAppMeta[self.listAppMeta.length - 1].appID) {
                self.enableAfter(false);
            } else {
                self.enableAfter(true) ;
            }
        }

        abstract update(): any;

        start(baseDate: any): JQueryPromise<any> {
            nts.uk.ui.block.invisible();
            let self = this;
            
            self.inputDetail().baseDate = baseDate;
            let dfd = $.Deferred();
            //let dfdMessageDeadline = self.getMessageDeadline(self.appType());
            let dfdAllReasonByAppID = self.getAllReasonByAppID(self.appID());
            let dfdAllDataByAppID = self.getAllDataByAppID(self.appID());

            $.when(dfdAllReasonByAppID, dfdAllDataByAppID).done((dfdAllReasonByAppIDData, dfdAllDataByAppIDData) => {
                // let data = self.model.ApplicationMetadata(self.listAppMeta[index - 1].appID, self.listAppMeta[index - 1].appType, self.listAppMeta[index - 1].appDate);
                let data = new shrvm.model.ApplicationMetadata(self.dataApplication().applicationID, self.dataApplication().applicationType, new Date(self.dataApplication().applicationDate));
                self.getDetailCheck(self.inputDetail());
                self.getMessageDeadline(data);
                nts.uk.ui.block.clear();
                dfd.resolve();
            });
            return dfd.promise();
        }   //end start
        // check display start
        checkDisplayStart() {
            let self = this;
            if (self.outputDetailCheck() != null) {
                //check 利用者
                let user = self.outputDetailCheck().user;
                switch(user){
                    case 2: {
                        // 利用者 = 申請本人
                        self.displayButtonControl().displayUpdate(true);
                        self.displayButtonControl().displayDelete(true);
                        self.displayButtonControl().displayCancel(true);    
                        break;
                    }
                    case 1: {
                        // 利用者 = 承認者
                        // check ログイン者の承認区分
                        let approvalATR = self.outputDetailCheck().approvalATR;
                        switch(approvalATR){
                            case 1: {
                                // ログイン者の承認区分 = 承認済
                                self.displayButtonControl().displayDeny(true);
                                self.displayButtonControl().displayRemand(true);
                                self.displayButtonControl().displayApprovalLabel(!self.displayButtonControl().displayApproval()&&true);
                                break;  
                            }     
                            case 2: {
                                // ログイン者の承認区分 = 否認
                                self.displayButtonControl().displayApproval(true);
                                self.displayButtonControl().displayRemand(true);
                                self.displayButtonControl().displayDenyLabel(!self.displayButtonControl().displayDeny()&&true);  
                                break;      
                            }
                            case 3: {
                                // ログイン者の承認区分 = 差し戻し
                                self.displayButtonControl().displayApproval(true);
                                self.displayButtonControl().displayDeny(true);
                                self.displayButtonControl().displayRemand(true);
                                break; 
                            }
                            default: {
                                // ログイン者の承認区分 = 未承認
                                self.displayButtonControl().displayApproval(true);
                                self.displayButtonControl().displayDeny(true);
                                self.displayButtonControl().displayRemand(true);
                            }  
                        }
                        self.displayButtonControl().displayRelease(true);
                        self.displayButtonControl().displayReturnReasonLabel(true);
                        self.displayButtonControl().displayReturnReason(true);
                        break;
                    }
                    case 0: {
                        // 利用者 = 申請本人&承認者
                        // check ログイン者の承認区分
                        let approvalATR = self.outputDetailCheck().approvalATR;
                        switch(approvalATR){
                            case 1: {
                                // ログイン者の承認区分 = 承認済
                                self.displayButtonControl().displayDeny(true);
                                self.displayButtonControl().displayRemand(true);
                                self.displayButtonControl().displayApprovalLabel(!self.displayButtonControl().displayApproval()&&true); 
                                break;  
                            }     
                            case 2: {
                                // ログイン者の承認区分 = 否認
                                self.displayButtonControl().displayApproval(true);
                                self.displayButtonControl().displayRemand(true);
                                self.displayButtonControl().displayDenyLabel(!self.displayButtonControl().displayDeny()&&true);  
                                break;      
                            }
                            case 3: {
                                // ログイン者の承認区分 = 差し戻し
                                self.displayButtonControl().displayApproval(true);
                                self.displayButtonControl().displayDeny(true);
                                self.displayButtonControl().displayRemand(true); 
                                break; 
                            }
                            default: {
                                // ログイン者の承認区分 = 未承認
                                self.displayButtonControl().displayApproval(true);
                                self.displayButtonControl().displayDeny(true);
                                self.displayButtonControl().displayRemand(true); 
                            }  
                        }
                        self.displayButtonControl().displayRelease(true);
                        self.displayButtonControl().displayReturnReasonLabel(true);
                        self.displayButtonControl().displayReturnReason(true);
                        self.displayButtonControl().displayUpdate(true);
                        self.displayButtonControl().displayDelete(true);
                        self.displayButtonControl().displayCancel(true); 
                        break;
                    }
                    default: {
                        // 利用者 = その他
                        self.displayButtonControl().displayUpdate(true);
                        self.displayButtonControl().displayDelete(true);
                    }   
                }
            }

        } // end checkDisplayStart

        //check checkDisplayAction
        checkDisplayAction() {
            let self = this;
            if (self.outputDetailCheck() != null) {
                //check 利用者
                let user = self.outputDetailCheck().user;
                switch(user){
                    case 1: {
                        // 利用者 = 承認者
                        // check ステータス
                        let reflectPlanState = self.outputDetailCheck().reflectPlanState;
                        if(reflectPlanState==6||reflectPlanState==5||reflectPlanState==0||reflectPlanState==1){
                            // 否認/反映待ち/未反映/差し戻し                                         
                            let authorizableFlags = self.outputDetailCheck().authorizableFlags;
                            if(authorizableFlags){
                                // 承認できるフラグ(true)           
                                let alternateExpiration = self.outputDetailCheck().alternateExpiration; 
                                if(alternateExpiration){
                                    // 代行期限切れフラグ(true)   
                                    let approvalATR = self.outputDetailCheck().approvalATR;       
                                    if(approvalATR == 1 || approvalATR == 2){
                                        // ログイン者の承認区分：承認済、否認                                                                        
                                        self.displayButtonControl().enableRelease(true);    
                                    }    
                                } else {
                                    // 代行期限切れフラグ(false)    
                                    let approvalATR = self.outputDetailCheck().approvalATR;       
                                    if(approvalATR == 1 || approvalATR == 2){
                                        // ログイン者の承認区分：承認済、否認                                                                        
                                        self.displayButtonControl().enableRelease(true);
                                    }
                                    self.displayButtonControl().enableApproval(true);
                                    self.displayButtonControl().enableDeny(true);
                                    self.displayButtonControl().enableRemand(true);
                                    self.displayButtonControl().displayReturnReasonPanel(true);
                                    self.displayButtonControl().displayReturnReasonLabel(true);
                                    self.displayButtonControl().displayReturnReason(true);
                                    self.displayButtonControl().enableReturnReason(true); 
                                }    
                            }        
                        }
                        break;
                    }
                    case 0: {
                        // 利用者 = 申請本人&承認者
                        // check ステータス
                        let reflectPlanState = self.outputDetailCheck().reflectPlanState;
                        if(reflectPlanState==6||reflectPlanState==5||reflectPlanState==0||reflectPlanState==1){
                            // 否認/反映待ち/未反映/差し戻し                                         
                            let authorizableFlags = self.outputDetailCheck().authorizableFlags;
                            if(authorizableFlags){
                                // 承認できるフラグ(true)           
                                let alternateExpiration = self.outputDetailCheck().alternateExpiration; 
                                if(alternateExpiration){
                                    // 代行期限切れフラグ(true)   
                                    let approvalATR = self.outputDetailCheck().approvalATR;       
                                    if(approvalATR == 1 || approvalATR == 2){
                                        // ログイン者の承認区分：承認済、否認                                                                        
                                        self.displayButtonControl().enableRelease(true);    
                                    }    
                                } else {
                                    // 代行期限切れフラグ(false)    
                                    let approvalATR = self.outputDetailCheck().approvalATR;       
                                    if(approvalATR == 1 || approvalATR == 2){
                                        // ログイン者の承認区分：承認済、否認                                                                        
                                        self.displayButtonControl().enableRelease(true); 
                                    }
                                    self.displayButtonControl().enableApproval(true);
                                    self.displayButtonControl().enableDeny(true);
                                    self.displayButtonControl().enableRemand(true);
                                    self.displayButtonControl().displayReturnReasonPanel(true);
                                    self.displayButtonControl().displayReturnReasonLabel(true);
                                    self.displayButtonControl().displayReturnReason(true);
                                    self.displayButtonControl().enableReturnReason(true);
                                }    
                            }    
                            if(reflectPlanState == 0 || reflectPlanState == 1){
                                self.displayButtonControl().enableUpdate(true);
                                self.displayButtonControl().enableDelete(true);      
                            }   
                        } else if(reflectPlanState == 4 ){
                            self.displayButtonControl().enableCancel(true); 
                        }
                        break;
                    }
                    default: {
                        // 利用者 = 申請本人 || その他
                        let reflectPlanState = self.outputDetailCheck().reflectPlanState;
                        if(reflectPlanState == 0 || reflectPlanState == 1){
                            self.displayButtonControl().enableUpdate(true);
                            self.displayButtonControl().enableDelete(true); 
                            self.displayButtonControl().displayReturnReasonPanel(true);        
                        } else if(reflectPlanState == 4){
                            self.displayButtonControl().enableCancel(true);
                        }
                    }   
                } 
            }
        }

        // getMessageDeadline
        getMessageDeadline(inputMessageDeadline: any) {
            let self = this;
            let dfd = $.Deferred<any>();
            service.getMessageDeadline(inputMessageDeadline).done(function(data) {
                self.outputMessageDeadline(data);
                dfd.resolve(data);
            }).fail(function(res: any) {
                dfd.reject();
                nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
            }); 
            return dfd.promise();
        }

        //getAll data by App ID
        getAllDataByAppID(appID: any) {
            let self = this;
            let dfd = $.Deferred<any>();
            service.getAllDataByAppID(appID).done(function(data) {
                self.dataApplication(data);
                self.appType(data.applicationType);
                let listPhase = self.dataApplication().listPhase; 
                let approvalList = [];
                for(let x = 1; x <= listPhase.length; x++){
                    let phaseLoop = listPhase[x-1];
                    let appPhase = new vmbase.AppApprovalPhase(
                        phaseLoop.phaseID,
                        phaseLoop.approvalForm,
                        phaseLoop.dispOrder,
                        phaseLoop.approvalATR,
                        []); 
                    for(let y = 1; y <= phaseLoop.listFrame.length; y++){
                        let frameLoop = phaseLoop.listFrame[y-1];
                        let appFrame = new vmbase.ApprovalFrame(
                            frameLoop.frameID,
                            frameLoop.dispOrder,
                            []);
                        for(let z = 1; z <= frameLoop.listApproveAccepted.length; z++){
                            let acceptedLoop = frameLoop.listApproveAccepted[z-1];
                            let appAccepted = new vmbase.ApproveAccepted(
                                acceptedLoop.appAcceptedID,
                                acceptedLoop.approverSID,
                                acceptedLoop.approvalATR,
                                acceptedLoop.confirmATR,
                                acceptedLoop.approvalDate,
                                acceptedLoop.reason,
                                acceptedLoop.representerSID);
                            appFrame.listApproveAccepted.push(appAccepted);
                        }
                        appPhase.listFrame.push(appFrame);   
                    };
                    approvalList.push(appPhase);    
                };
                self.approvalList = approvalList;
                dfd.resolve(data);
            }).fail(function(res: any) {
                dfd.reject();
                nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
            }); 
            return dfd.promise();
        }
        //get all reason by app ID
        getAllReasonByAppID(appID: string) {
            let self = this;
            let dfd = $.Deferred<any>();
            service.getAllReasonByAppID(appID).done(function(data) {
                self.listReasonByAppID(data);
                if (self.listReasonByAppID().length > 0) {
                    self.reasonApp(self.listReasonByAppID()[0].toString());
                    if(self.listReasonByAppID().length > 1){
                        self.reasonToApprover(self.listReasonByAppID()[1].toString());    
                    }                    
                }
                dfd.resolve(data);
            }).fail(function(res: any) {
                dfd.reject();
                nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
            }); ;
            return dfd.promise();
        }
        //get detail check 
        getDetailCheck(inputGetDetail: any) {
            let self = this;
            let dfd = $.Deferred<any>();
            service.getDetailCheck(inputGetDetail).done(function(data) {
                self.outputDetailCheck(data);
                self.checkDisplayStart();
                self.checkDisplayAction();
                dfd.resolve(data);
            }).fail(function(res: any) {
                dfd.reject();
                nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
            });
            return dfd.promise();
        }
        /**
         * btn before
         */
        btnBefore() {
            let self = this;
            var prevAppInfo = self.getPrevAppInfo();
            nts.uk.request.jump("at", "/view/kaf/000/b/index.xhtml", { 'listAppMeta': self.listAppMeta, 'currentApp': prevAppInfo });
        }
        
        private getPrevAppInfo(): shrvm.model.ApplicationMetadata {
            let self = this;
            let index = _.findIndex(self.listAppMeta, ["appID", self.appID()]);
            if (index > 0) {
                return new shrvm.model.ApplicationMetadata(self.listAppMeta[index - 1].appID, self.listAppMeta[index - 1].appType, self.listAppMeta[index - 1].appDate);
            }
            return null;
        }
        
        /**
         * btn after
         */
        btnAfter() {
            let self = this;
            var nextAppInfo = self.getNextAppInfo();
            nts.uk.request.jump("at", "/view/kaf/000/b/index.xhtml", { 'listAppMeta': self.listAppMeta, 'currentApp': nextAppInfo });
        }
        
        private getNextAppInfo(): shrvm.model.ApplicationMetadata {
            let self = this;
            let index = _.findIndex(self.listAppMeta, ["appID", self.appID()]);
            if (index < self.listAppMeta.length - 1) {
                return new shrvm.model.ApplicationMetadata(self.listAppMeta[index + 1].appID, self.listAppMeta[index + 1].appType, self.listAppMeta[index + 1].appDate);
            }
            return null;
        }
        
        /**
         *  btn Approve
         */
        btnApprove() {
            let self = this;
            self.inputCommonData(new model.InputCommonData(self.dataApplication(),self.reasonToApprover()));
            let dfd = $.Deferred<any>();
            service.approveApp(self.inputCommonData()).done(function(data) {
                self.getAllDataByAppID(self.appID()).done(function(value){
                    nts.uk.ui.dialog.alert({ messageId: 'Msg_220' }).then(function() {
                        if (!data) {
                            nts.uk.ui.dialog.info({ messageId: 'Msg_392' });
                        }
                    });
                });
                dfd.resolve();
            }).fail(function(res: any) {
                dfd.reject();
                nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
            });
            return dfd.promise();
        }
        /**
        *  btn Deny
        */
        btnDeny() {
            let self = this;
            let dfd = $.Deferred<any>();
            self.inputCommonData(new model.InputCommonData(self.dataApplication(),self.reasonToApprover()));
            service.denyApp(self.inputCommonData()).done(function(data) {
                self.getAllDataByAppID(self.appID()).done(function(value){
                    nts.uk.ui.dialog.alert({ messageId: 'Msg_222' }).then(function() {
                        if (!data) {
                            nts.uk.ui.dialog.info({ messageId: 'Msg_392' });
                        }
                    });
                });
                dfd.resolve();
           }).fail(function(res: any) {
                dfd.reject();
                nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
            }); 
                
            return dfd.promise();
        }

        /**
        *  btn Release
        */
        btnRelease() {
            let self = this;
            self.inputCommonData(new model.InputCommonData(self.dataApplication(),self.reasonToApprover()));
            let dfd = $.Deferred<any>();
            nts.uk.ui.dialog.confirm({ messageId: 'Msg_28' }).ifYes(function() {
                service.releaseApp(self.inputCommonData()).done(function() {
                    self.getAllDataByAppID(self.appID()).done(function(value){
                        
                    });
                    dfd.resolve();
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
                }); 
            });
            return dfd.promise();
        }

        /**
         *  btn Registration
         */
        btnRegistration() {

        }
        /**
         *  btn References 
         */
        btnReferences() {
            let self = this;
            // send (Cid,Eid,date) in screen KDL004
            //nts.uk.request.jump("/view/kdl/004/a/index.xhtml");
        }
        /**
         *  btn SendEmail 
         */
        btnSendEmail() {
            let self = this;
            // send (Cid, appId , content, Eid, date) in screen KDL030
            //nts.uk.request.jump("/view/kdl/030/a/index.xhtml");
        }
        /**
         *  btn Delete 
         */
        btnDelete() {
            let self = this;
            self.inputCommandEvent(new model.InputCommandEvent(self.appID(), self.appReasonEvent()));
            let dfd = $.Deferred<any>();
            nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function() {
                service.deleteApp(self.inputCommandEvent()).done(function(data) {

                    nts.uk.ui.dialog.alert({ messageId: 'Msg_16' }).then(function() {
                        //kiểm tra list người xác nhận, nếu khác null thì show info 392
                        if (!nts.uk.util.isNullOrUndefined(data)) {
                            if(!nts.uk.util.isNullOrEmpty(data.result)){
                                let strMail = "";
                                _.forEach(data.result, function(value) {
                                      strMail = value + '<br/>';
                                });
                                nts.uk.ui.dialog.info({ messageId: 'Msg_392', messageParams: strMail });
                            }
                        }
                        //lấy vị trí appID vừa xóa trong listAppID
                        let index = _.findIndex(self.listAppMeta, ["appID", self.appID()]);
                        if (index > -1 && index != self.listAppMeta.length-1) {
                            //xóa appID vừa xóa trong list
                            self.appID(self.listAppMeta[index+1].appID);
                        }
                        
                        self.listAppMeta.splice(index, 1);
                        if(self.listAppMeta.length == 1){
                            nts.uk.request.jump("at", "/view/kaf/000/b/index.xhtml", { 
                                'listAppMeta': self.listAppMeta, 
                                'currentApp': new shrvm.model.ApplicationMetadata(self.listAppMeta[0].appID, self.listAppMeta[0].appType, self.listAppMeta[0].appDate)
                            }); 
                            return;
                        }
                        //nếu vị trí vừa xóa khác vị trí cuối
                        if (index != self.listAppMeta.length - 1) {
                            //gán lại appId mới tại vị trí chính nó
                            self.btnAfter();
                        } else {
                            //nếu nó ở vị trí cuối thì lấy appId ở vị trí trước nó
                            self.btnBefore();
                        }
                        
                        //if list # null    
                        if (self.listAppMeta.length == 0) {
                            //nếu list null thì trả về màn hình mẹ
                            nts.uk.request.jump("/view/kaf/000/test/index.xhtml");
                        }
                    });
                    
            
                    dfd.resolve();
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
                }); 
            });
            return dfd.promise();
        }
        /**
         *  btn Cancel 
         */
        btnCancel() {
            let self = this;
            self.inputCommandEvent(new model.InputCommandEvent(self.appID(), self.appReasonEvent()));
            let dfd = $.Deferred<any>();
            nts.uk.ui.dialog.confirm({ messageId: 'Msg_249' }).ifYes(function() {
                service.cancelApp(self.inputCommandEvent()).done(function() {
                    nts.uk.ui.dialog.alert({ messageId: "Msg_224" })
                    dfd.resolve();
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
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
            applicationID: string;
            prePostAtr: number;
            inputDate: string;
            enteredPersonSID: string;
            reversionReason: string;
            applicationDate: string;
            applicationReason: string;
            applicationType: number;
            applicantSID: string;
            reflectPlanScheReason: number;
            reflectPlanTime: string;
            reflectPlanState: number;
            reflectPlanEnforce: number;
            reflectPerScheReason: number;
            reflectPerTime: string;
            reflectPerState: number;
            reflectPerEnforce: number;
            startDate: string;
            endDate: string;
            listPhase: Array<shrvm.model.AppApprovalPhase>;
            constructor(
                applicationID: string,
                prePostAtr: number,
                inputDate: string,
                enteredPersonSID: string,
                reversionReason: string,
                applicationDate: string,
                applicationReason: string,
                applicationType: number,
                applicantSID: string,
                reflectPlanScheReason: number,
                reflectPlanTime: string,
                reflectPlanState: number,
                reflectPlanEnforce: number,
                reflectPerScheReason: number,
                reflectPerTime: string,
                reflectPerState: number,
                reflectPerEnforce: number,
                startDate: string,
                endDate: string,
                listPhase: Array<shrvm.model.AppApprovalPhase>) {
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
                this.listPhase = listPhase;
            }
        }//end class Application


        //class OutputPhaseAndFrame 
        export class OutputPhaseAndFrame {
            appApprovalPhase: shrvm.model.AppApprovalPhase;
            listApprovalFrame: Array<shrvm.model.ApprovalFrame>;
            constructor(
                appApprovalPhase: shrvm.model.AppApprovalPhase,
                listApprovalFrame: Array<shrvm.model.ApprovalFrame>) {
                this.appApprovalPhase = appApprovalPhase;
                this.listApprovalFrame = listApprovalFrame;
            }
        }//end class OutputPhaseAndFrame

        //class InputGetDetailCheck 
        export class InputGetDetailCheck {
            applicationID: string;
            baseDate: string;
            constructor(applicationID: string,
                baseDate: string) {
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

        
        
        //class InputApprove
        export class InputCommonData {
            applicationDto: ApplicationDto;
            memo: string;
            constructor(applicationDto : ApplicationDto, memo : string) {
                this.applicationDto = applicationDto;
                this.memo = memo;
            }
        }

        //class InputCommandEvent
        export class InputCommandEvent {
            appId: string;
            applicationReason: string;
            constructor(appId: string, applicationReason: string) {
                this.appId = appId;
                this.applicationReason = applicationReason;
            }
        }
        
        export class DisplayButtonControl {
            // B1-8 Update
            displayUpdate: KnockoutObservable<boolean>;
            enableUpdate: KnockoutObservable<boolean>;
            
            // B1-4 Approval
            displayApproval: KnockoutObservable<boolean>;
            enableApproval: KnockoutObservable<boolean>;
            
            // B1-5 Deny
            displayDeny: KnockoutObservable<boolean>;
            enableDeny: KnockoutObservable<boolean>;
            
            // B1-6 Remand
            displayRemand: KnockoutObservable<boolean>;
            enableRemand: KnockoutObservable<boolean>;
            
            // B1-7 Release
            displayRelease: KnockoutObservable<boolean>;
            enableRelease: KnockoutObservable<boolean>;
            
            // B1-12 Delete
            displayDelete: KnockoutObservable<boolean>;  
            enableDelete: KnockoutObservable<boolean>;
            
            // B1-13 Cancel
            displayCancel: KnockoutObservable<boolean>;
            enableCancel: KnockoutObservable<boolean>;
            
            // B1-14 ApprovalLabel
            displayApprovalLabel: KnockoutObservable<boolean>;
            
            // B1-15 DenyLabel
            displayDenyLabel: KnockoutObservable<boolean>;
            
            // B3-1
            displayReturnReasonPanel: KnockoutObservable<boolean>;
            
            // B4-1 
            displayReturnReasonLabel: KnockoutObservable<boolean>;
            
            // B4-2 
            displayReturnReason: KnockoutObservable<boolean>;
            enableReturnReason: KnockoutObservable<boolean>;
            
            constructor(){
                this.displayUpdate = ko.observable(false); 
                this.enableUpdate = ko.observable(false);
                this.displayApproval = ko.observable(false);
                this.enableApproval = ko.observable(false);
                this.displayDeny = ko.observable(false);
                this.enableDeny = ko.observable(false);
                this.displayRemand = ko.observable(false);
                this.enableRemand = ko.observable(false);
                this.displayRelease = ko.observable(false);
                this.enableRelease = ko.observable(false);
                this.displayDelete = ko.observable(false);
                this.enableDelete = ko.observable(false);
                this.displayCancel = ko.observable(false);
                this.enableCancel = ko.observable(false);
                this.displayApprovalLabel = ko.observable(false);
                this.displayDenyLabel = ko.observable(false);
                this.displayReturnReasonPanel = ko.observable(false);
                this.displayReturnReasonLabel = ko.observable(false);
                this.displayReturnReason = ko.observable(false);   
                this.enableReturnReason = ko.observable(false);  
            }
        }
        
    }
}