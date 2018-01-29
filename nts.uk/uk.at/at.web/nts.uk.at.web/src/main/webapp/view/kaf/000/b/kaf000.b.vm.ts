module nts.uk.at.view.kaf000.b.viewmodel {
    import vmbase =  nts.uk.at.view.kaf002.shr.vmbase;
    import shrvm = nts.uk.at.view.kaf000.shr;
    export abstract class ScreenModel {

        // Metadata
        appID: KnockoutObservable<string>;
        appType: KnockoutObservable<number>;
        //listPhase
        listPhase: KnockoutObservableArray<shrvm.model.AppApprovalPhase> = ko.observableArray([]);
        //listPhaseID
        listPhaseID: Array<string>;
        //list appID 
        listReasonByAppID: KnockoutObservableArray<string> = ko.observableArray([]);

        /**InputCommonData
         * value obj 
         */
        reasonToApprover: KnockoutObservable<string> = ko.observable('');
        reasonAppMess : string = nts.uk.resource.getText('KAF000_1');
        reasonAppMessDealine : string = nts.uk.resource.getText('KAF000_2');
        messageDeadlineTop: KnockoutObservable<string> = ko.observable('');
        messageDeadlineBottom: KnockoutObservable<string> = ko.observable('');
        reasonApp: KnockoutObservable<string> = ko.observable('');
        inputCommonData : KnockoutObservable<model.InputCommonData> = ko.observable(null);
        dataApplication: KnockoutObservable<model.ApplicationDto> = ko.observable(null);
        //application
        inputDetail: KnockoutObservable<model.InputGetDetailCheck>;
        //obj input
        //obj output message deadline
        outputMessageDeadline: KnockoutObservable<shrvm.model.OutputMessageDeadline> = ko.observable(null);
        //obj DetailedScreenPreBootModeOutput
        outputDetailCheck: KnockoutObservable<model.DetailedScreenPreBootModeOutput> = ko.observable(null);
        //obj InputCommandEvent
        inputCommandEvent: KnockoutObservable<model.InputCommandEvent>;
        //enable enableBefore
        enableBefore: KnockoutObservable<boolean> = ko.observable(true);
        //enable enableAfter
        enableAfter: KnockoutObservable<boolean> = ko.observable(true);
        //listAppID
        listAppMeta: Array<any>;
        //item InputCommandEvent
        appReasonEvent: KnockoutObservable<string>= ko.observable('');
        approvalList: Array<vmbase.AppApprovalPhase> = [];
        displayButtonControl: KnockoutObservable<model.DisplayButtonControl> = ko.observable(new model.DisplayButtonControl());
        
        approvalRootState: any = ko.observableArray([]);
        empEditable: KnockoutObservable<boolean> = ko.observable(true);
        
        constructor(listAppMetadata: Array<shrvm.model.ApplicationMetadata>, currentApp: shrvm.model.ApplicationMetadata) {
            let self = this;
            //reason input event
            // Metadata
            self.listAppMeta = listAppMetadata;
            self.appType = ko.observable(currentApp.appType);
            self.appID = ko.observable(currentApp.appID);
            self.inputCommandEvent = ko.observable(new model.InputCommandEvent(0, self.appID(), self.appReasonEvent()));
            //application
            self.inputDetail = ko.observable(new model.InputGetDetailCheck(self.appID(), "2022/01/01"));

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
            let dfd = $.Deferred<any>();
            nts.uk.at.view.kaf000.b.service.getAppDataDate({
                appTypeValue: self.appType(),
                appDate: baseDate,
                isStartup: true,
                appID: self.appID()})
            .done((data)=>{
                self.inputCommandEvent().version = data.applicationDto.version;
                self.dataApplication(data.applicationDto);
                self.appType(data.applicationDto.applicationType);
                self.approvalRootState(ko.mapping.fromJS(data.listApprovalPhaseStateDto)());
                let deadlineMsg = data.outputMessageDeadline;
                if(!nts.uk.text.isNullOrEmpty(deadlineMsg.message)){
                    self.messageDeadlineTop(self.reasonAppMess + deadlineMsg.message);    
                }
                if(!nts.uk.text.isNullOrEmpty(deadlineMsg.deadline)){
                    self.messageDeadlineBottom(self.reasonAppMessDealine + deadlineMsg.deadline);
                }
                if(nts.uk.text.isNullOrEmpty(deadlineMsg.message) && nts.uk.text.isNullOrEmpty(deadlineMsg.deadline)){
                    self.displayButtonControl().displayMessageArea(true);
                }else{
                    self.displayButtonControl().displayMessageArea(false);
                }
                self.getDetailCheck(self.inputDetail());
                nts.uk.ui.block.clear();    
                dfd.resolve();       
            })
            .fail(()=>{
                nts.uk.ui.block.clear(); 
                dfd.reject(); 
            });
            
            return dfd.promise();
        }   //end start
        // check display start 表示するか非表示するか  ※5 
        checkDisplayStart() {
            let self = this;
            if (self.outputDetailCheck() != null) {
                //※5
                let user = self.outputDetailCheck().user;
                switch(user){
                    case 0: self.empEditable(true); break;
                    case 1: self.empEditable(false); break;
                    case 2: self.empEditable(true); break;
                    default: self.empEditable(false);    
                }
                
                switch(user){                    
                    case 1:{ //承認者 
                        //登録  ×
                        self.displayButtonControl().displayUpdate(true);
                        //承認, 否認, 差し戻し, 【承認】, 【否認】  補足②
                        self.isFlagApproval();
                        //解除  ○
                        self.displayButtonControl().displayRelease(false);
                        //削除  ×
                        self.displayButtonControl().displayDelete(true);
                        //取消 ×
                        self.displayButtonControl().displayCancel(true);
                        //承認コメント ○
                        self.displayButtonControl().displayReturnReason(false);
                        break;
                    }
                    case 0: {//申請本人&承認者
                        //登録  ○
                        self.displayButtonControl().displayUpdate(false);
                        //承認, 否認, 差し戻し, 【承認】, 【否認】  補足②
                        self.isFlagApproval();
                        //解除 ○
                        self.displayButtonControl().displayRelease(false);
                        //削除 ○
                        self.displayButtonControl().displayDelete(false);
                        //取消 ※6
                        self.displayButtonControl().displayCancel(self.isLoginAndApprover());
                        //承認コメント  ○
                        self.displayButtonControl().displayReturnReason(false);
                        break;
                    }
                    default: {//その他 99, 申請本人 2
                        //登録  ○
                        self.displayButtonControl().displayUpdate(false);
                        //承認  ×
                        self.displayButtonControl().displayApproval(true);
                        //否認  × 
                        self.displayButtonControl().displayDeny(true);
                        //差し戻し ×   
                        self.displayButtonControl().displayRemand(true);
                        //解除  ×
                        self.displayButtonControl().displayRelease(true);
                        //削除 ○
                        self.displayButtonControl().displayDelete(false);
                        //取消  ※6        
                        self.displayButtonControl().displayCancel(this.isLoginAndApprover());
                        //【承認】  ×
                        self.displayButtonControl().displayApprovalLabel(true);
                        //【否認】  ×
                        self.displayButtonControl().displayDenyLabel(true);
                        //承認コメント ×
                        self.displayButtonControl().displayReturnReason(true);  
                    }
                            
                }    
            }

        } // end checkDisplayStart
        //補足1
        //条件：「申請利用設定」．備考に内容なし &&  「申請締切設定」．利用区分が利用しない  &&  「事前の受付制限」．利用区分が利用しない  &&  「事後の受付制限」．未来日許可しないがfalse
        isShowMessage(){
            let self = this;
            if(nts.uk.text.isNullOrEmpty(self.messageDeadlineTop)
             || nts.uk.text.isNullOrEmpty(self.messageDeadlineBottom)){
                
            }else{
                this.displayButtonControl().displayMessageArea(false);
            }
        }
        
        //補足2
        ////承認できるフラグがtrueの場合、ログイン者の承認区分：（未承認、承認済、否認）
        isFlagApproval(){
            let self = this;
            let isAuthorizableFlags = self.outputDetailCheck().authorizableFlags;
            if(isAuthorizableFlags){
                let approvalATR = self.outputDetailCheck().approvalATR;
                switch(approvalATR){
                    case 0: {//ログイン者の承認区分が未承認
                        //承認 〇
                        self.displayButtonControl().displayApproval(false);
                        //否認〇
                        self.displayButtonControl().displayDeny(false);
                        //差し戻し〇
                        self.displayButtonControl().displayRemand(false);
                        //【承認】  ×
                        self.displayButtonControl().displayApprovalLabel(true);
                        //【否認】 ×
                        self.displayButtonControl().displayDenyLabel(true);
                        break;
                    }
                    case 1:{//ログイン者の承認区分が承認済
                        //承認 ×  
                        self.displayButtonControl().displayApproval(true);
                        //否認〇
                        self.displayButtonControl().displayDeny(false);
                        //差し戻し〇
                        self.displayButtonControl().displayRemand(false);
                        //【承認】  〇
                        self.displayButtonControl().displayApprovalLabel(false);
                        //【否認】 ×
                        self.displayButtonControl().displayDenyLabel(true);
                        break;
                    }
                    case 2: {//ログイン者の承認区分が否認
                        //承認 〇    
                        self.displayButtonControl().displayApproval(false);
                        //否認×   
                        self.displayButtonControl().displayDeny(true);
                        //差し戻し〇
                        self.displayButtonControl().displayRemand(false);
                        //【承認】  ×   
                        self.displayButtonControl().displayApprovalLabel(true);
                        //【否認】 〇   
                        self.displayButtonControl().displayDenyLabel(false);
                        break;    
                    }
                        
                }
            }
            //※8
            if(nts.uk.text.isNullOrEmpty(self.reasonApp())){
                self.displayButtonControl().displayReturnReasonPanel(true);
            }else{
                self.reasonApp(self.reasonAppMess + '　' + self.reasonApp());
                self.displayButtonControl().displayReturnReasonPanel(false);
            }
                
        }
        //※6 
        isLoginAndApprover(){
            let self = this;
            //ドメインモデル「申請」．入力者 == ログイン者社員ID                                                                      
            //ドメインモデル「申請」．申請者 == ログイン者社員ID                                                                      \
            if(self.outputDetailCheck().loginInputOrApproval){
                return false;    
            }else{//その以外
                return true;
            }   
        }

        //check checkDisplayAction　活性するか非活性するか ※7   
        checkDisplayAction() {
            let self = this;
            let Status = {NOTREFLECTED: 0, // 未反映
                            WAITREFLECTION: 1, //反映待ち
                            REFLECTED: 2, //反映済
                            WAITCANCEL: 3, //取消待ち
                            CANCELED: 4, //取消済
                            REMAND: 5,//差し戻し
                            DENIAL: 6, //否認
                            PASTAPP: 99 //過去申請 
                            };
            if (self.outputDetailCheck() != null) {
                //利用者
                let user = self.outputDetailCheck().user;
                //ステータス
                let reflectPlanState = self.outputDetailCheck().reflectPlanState;
                //承認できるフラグ
                let authorizableFlags = self.outputDetailCheck().authorizableFlags;
                //代行期限切れフラグ
                let alternateExpiration = self.outputDetailCheck().alternateExpiration;
                // 
                let approvalATR = self.outputDetailCheck().approvalATR;
                //利用者が『承認者』, 利用者が『本人&承認者』
                if(user == 1 || user == 0){
                    //過去申請, 反映済, 取消待ち, 取消済
                    if(reflectPlanState == Status.PASTAPP
                        || reflectPlanState == Status.REFLECTED
                        || reflectPlanState == Status.WAITCANCEL
                        || reflectPlanState == Status.CANCELED){
                        self.setAllEnableFalse();
                        //利用者が『本人&承認者』
                        if(user == 0){
                            //登録  ×
                            self.displayButtonControl().enableUpdate(false);
                            //削除 ×
                            self.displayButtonControl().enableDelete(false);
                            
                            if(reflectPlanState == Status.REFLECTED){
                                //取消  ○   ×
                                self.displayButtonControl().enableCancel(true);    
                            }else{
                                //取消    ×
                                self.displayButtonControl().enableCancel(false);    
                            }
                        }
                    }else if (reflectPlanState == Status.DENIAL
                        || reflectPlanState == Status.WAITREFLECTION
                        || reflectPlanState == Status.NOTREFLECTED
                        || reflectPlanState == Status.REMAND){//否認/反映待ち/未反映/差し戻し
                        //取消   ×
                        self.displayButtonControl().enableCancel(false);
                        if(reflectPlanState == Status.NOTREFLECTED
                            ||reflectPlanState == Status.REMAND){
                            //登録 ○
                            self.displayButtonControl().enableUpdate(true);
                            //削除  ○
                            self.displayButtonControl().enableDelete(true);    
                        }else{
                            //登録  ×
                            self.displayButtonControl().enableUpdate(false);
                            //削除    ×
                            self.displayButtonControl().enableDelete(false);  
                        }
                        //承認できるフラグ(false)
                        if(!authorizableFlags){
                            self.setAllEnableFalse();
                        }else{
                            //代行期限切れフラグ(true)
                            if(alternateExpiration){
                                self.setAllEnableFalse();
                                //※9
                                //ログイン者の承認区分：承認済、否認    
                                if(approvalATR == 1||approvalATR == 2){
                                    self.displayButtonControl().enableRelease(true);    
                                }else if (approvalATR == 0){//ログイン者の承認区分：未承認
                                    self.displayButtonControl().enableRelease(false);
                                }
                            }else{//代行期限切れフラグ(false)
                                 //承認
                                self.displayButtonControl().enableApproval(true);
                                //否認
                                self.displayButtonControl().enableDeny(true);
                                //差し戻し
                                self.displayButtonControl().enableRemand(true);
                                //承認コメント
                                self.displayButtonControl().enableMessageComment(true);
                                //解除//※9
                                //ログイン者の承認区分：承認済、否認    
                                if(approvalATR == 1||approvalATR == 2){
                                    self.displayButtonControl().enableRelease(true);    
                                }else if (approvalATR == 0){//ログイン者の承認区分：未承認
                                    self.displayButtonControl().enableRelease(false);
                                }
                            }
                        }
                    }
                }else{//利用者が『本人』、又は『その他』
                    //反映済
                    if(reflectPlanState　== Status.REFLECTED){
                        //取消   〇
                        self.displayButtonControl().enableCancel(true);
                    }
                    if(reflectPlanState == Status.NOTREFLECTED || reflectPlanState == Status.REMAND){//未反映 , 差し戻し
                        //登録 ○
                        self.displayButtonControl().enableUpdate(true);
                        //削除  ○
                        self.displayButtonControl().enableDelete(true);  
                        //差し戻し理由  ○
                        self.displayButtonControl().enableReturnReason(true);
                    }
                }
            }
        }

        //
        setAllEnableFalse(){
            let self = this;
            //承認
            self.displayButtonControl().enableApproval(false);
            //否認
            self.displayButtonControl().enableDeny(false);
            //差し戻し
            self.displayButtonControl().enableRemand(false);
            //解除
            self.displayButtonControl().enableRelease(false);
            //承認コメント
            self.displayButtonControl().enableMessageComment(false);    
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
         * btn before　←
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
                return new shrvm.model.ApplicationMetadata(self.listAppMeta[index - 1].appID, self.listAppMeta[index -1].appType, self.listAppMeta[index -1].appDate);
            }
            return null;
        }
        
        /**
         * btn after　→
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
                return new shrvm.model.ApplicationMetadata(self.listAppMeta[index+1].appID, self.listAppMeta[index + 1].appType, self.listAppMeta[index +1].appDate);
            }
            return null;
        }
        
        /**
         *  btn Approve　承認
         */
        btnApprove() {
            nts.uk.ui.block.invisible();
            let self = this;
            self.inputCommonData(new model.InputCommonData(self.dataApplication(),self.reasonToApprover()));
            service.approveApp(self.inputCommonData()).done(function(data) {
                nts.uk.ui.dialog.alert({ messageId: 'Msg_220' }).then(function() {
                    if (!nts.uk.util.isNullOrUndefined(data)) {
                        nts.uk.ui.dialog.info({ messageId: 'Msg_392',messageParams: [data]  }).then(()=>{
                            location.reload();    
                        });
                    } else {
                        location.reload();        
                    }
                });
            }).fail(function(res: any) {
                nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds }).then(function(){nts.uk.ui.block.clear();}); 
            });
        }
        /**
        *  btn Deny　否認
        */
        btnDeny() {
            nts.uk.ui.block.invisible();
            let self = this;
            self.inputCommonData(new model.InputCommonData(self.dataApplication(),self.reasonToApprover()));
            service.denyApp(self.inputCommonData()).done(function(data) {
                nts.uk.ui.dialog.alert({ messageId: 'Msg_222' }).then(function() {
                    if (!nts.uk.util.isNullOrUndefined(data)) {
                        nts.uk.ui.dialog.info({ messageId: 'Msg_392', messageParams: [data] }).then(()=>{
                            location.reload();    
                        });
                    } else {
                        location.reload();    
                    }
                });
            }).fail(function(res: any) {
                nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds }).then(function() { nts.uk.ui.block.clear(); });
            }); 
        }

        /**
        *  btn Release //解除
        */
        btnRelease() {
            nts.uk.ui.block.invisible();
            let self = this;
            self.inputCommonData(new model.InputCommonData(self.dataApplication(),self.reasonToApprover()));
            nts.uk.ui.dialog.confirm({ messageId: 'Msg_248' }).ifYes(function() {
                service.releaseApp(self.inputCommonData()).done(function() {
                    nts.uk.ui.dialog.info({ messageId: 'Msg_221'}).then(()=>{
                        location.reload();    
                    });
                }).fail(function(res: any) {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds }).then(function() { nts.uk.ui.block.clear(); });
                }); 
            }).ifNo(()=>{
                nts.uk.ui.block.clear();        
            });
        }

        /**
         *  btn Registration　登録
         */
        btnRegistration() {

        }
        /**
         *  btn References 実績参照
         */
        btnReferences() {
            let self = this;
            // send (Cid,Eid,date) in screen KDL004
            //nts.uk.request.jump("/view/kdl/004/a/index.xhtml");
        }
        /**
         *  btn SendEmail メール送信
         */
        btnSendEmail() {
            let self = this;
            // send (Cid, appId , content, Eid, date) in screen KDL030
            //nts.uk.request.jump("/view/kdl/030/a/index.xhtml");
        }
        /**
         *  btn Delete 削除
         */
        btnDelete() {
            nts.uk.ui.block.invisible();
            let self = this;
            self.inputCommandEvent(new model.InputCommandEvent(self.inputCommandEvent().version, self.appID(), self.appReasonEvent()));
            nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function() {
                service.deleteApp(self.inputCommandEvent()).done(function(data) {
                    nts.uk.ui.dialog.alert({ messageId: 'Msg_16' }).then(function() {
                        //kiểm tra list người xác nhận, nếu khác null thì show info 392
                        if (!nts.uk.util.isNullOrEmpty(data)) {
                            nts.uk.ui.dialog.info({ messageId: 'Msg_392', messageParams: [data] }).then(function(){
                                //self.setScreenAfterDelete();    
                            });
                        }
//                        else{
//                            //self.setScreenAfterDelete();
//                        }
                         nts.uk.request.jump("/view/kaf/000/test/index.xhtml");
                    });
                }).fail(function(res: any) {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds }).then(function() {
                        nts.uk.request.jump("../test/index.xhtml");
                    }); 
                }); 
            }).ifNo(function(){
                nts.uk.ui.block.clear();    
            });
        }
        setScreenAfterDelete(){
            let self = this;    
            //lấy vị trí appID vừa xóa trong listAppID
            let index = _.findIndex(self.listAppMeta, ["appID", self.appID()]);
            if (index > -1 && index != self.listAppMeta.length-1) {
                //xóa appID vừa xóa trong list
                self.appID(self.listAppMeta[index+1].appID);
            }
            
            self.listAppMeta.splice(index, 1);
            //if list # null    
            if (self.listAppMeta.length == 0) {
                //nếu list null thì trả về màn hình mẹ
                nts.uk.request.jump("/view/kaf/000/test/index.xhtml");
            }
            
            if(self.listAppMeta.length == 1){
                nts.uk.request.jump("at", "/view/kaf/000/b/index.xhtml", { 
                    'listAppMeta': self.listAppMeta, 
                    'currentApp': new shrvm.model.ApplicationMetadata(self.listAppMeta[0].appID, self.listAppMeta[0].appType, self.listAppMeta[0].appDate)
                }); 
                return;
            }
            //nếu vị trí vừa xóa khác vị trí cuối
            if (index != self.listAppMeta.length) {
                //gán lại appId mới tại vị trí chính nó
                //self.btnAfter();
                nts.uk.request.jump("at", "/view/kaf/000/b/index.xhtml", { 
                    'listAppMeta': self.listAppMeta, 
                    'currentApp': new shrvm.model.ApplicationMetadata(self.listAppMeta[index].appID, self.listAppMeta[index].appType, self.listAppMeta[index].appDate)
                });
            } else {
                //nếu nó ở vị trí cuối thì lấy appId ở vị trí trước nó
//                self.btnBefore();
                nts.uk.request.jump("at", "/view/kaf/000/b/index.xhtml", { 
                    'listAppMeta': self.listAppMeta, 
                    'currentApp': new shrvm.model.ApplicationMetadata(self.listAppMeta[self.listAppMeta.length -1].appID, self.listAppMeta[self.listAppMeta.length -1].appType, self.listAppMeta[self.listAppMeta.length -1].appDate)
                });
            }
            
        }
        
        /**
         *  btn Cancel 
         */
        btnCancel() {
            nts.uk.ui.block.invisible();
            let self = this;
            self.inputCommandEvent(new model.InputCommandEvent(self.inputCommandEvent().version, self.appID(), self.appReasonEvent()));
            nts.uk.ui.dialog.confirm({ messageId: 'Msg_249' }).ifYes(function() {
                service.cancelApp(self.inputCommandEvent()).done(function() {
                    nts.uk.ui.dialog.alert({ messageId: "Msg_224" }).then(()=>{
                        location.reload();            
                    });
                }).fail(function(res: any) {
                    if(res.optimisticLock == true){
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_197" }).then(function(){
                            location.reload();
                        });    
                    } else {
                        nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds }).then(function(){nts.uk.ui.block.clear();}); 
                    }
                }); 
            }).ifNo(function(){
                nts.uk.ui.block.clear();    
            });
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
            version: number;
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
                version: number,
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
                this.version = version;
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
            loginInputOrApproval: boolean;
            constructor(user: number,
                reflectPlanState: number,
                authorizableFlags: boolean,
                approvalATR: number,
                alternateExpiration: boolean,
                loginInputOrApproval: boolean) {
                this.user = user;
                this.reflectPlanState = reflectPlanState;
                this.authorizableFlags = authorizableFlags;
                this.approvalATR = approvalATR;
                this.alternateExpiration = alternateExpiration;
                this.loginInputOrApproval = loginInputOrApproval;
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
            version: number;
            appId: string;
            applicationReason: string;
            constructor(version: number, appId: string, applicationReason: string) {
                this.version = version;
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
            displayMessageArea: KnockoutObservable<boolean>;
            enableMessageComment: KnockoutObservable<boolean>;
            constructor(){
                this.displayUpdate = ko.observable(true); 
                this.enableUpdate = ko.observable(false);
                this.displayApproval = ko.observable(true);
                this.enableApproval = ko.observable(false);
                this.displayDeny = ko.observable(true);
                this.enableDeny = ko.observable(false);
                this.displayRemand = ko.observable(true);
                this.enableRemand = ko.observable(false);
                this.displayRelease = ko.observable(true);
                this.enableRelease = ko.observable(false);
                this.displayDelete = ko.observable(true);
                this.enableDelete = ko.observable(false);
                this.displayCancel = ko.observable(true);
                this.enableCancel = ko.observable(false);
                this.displayApprovalLabel = ko.observable(true);
                this.displayDenyLabel = ko.observable(true);
                this.displayReturnReasonPanel = ko.observable(true);
                this.displayReturnReasonLabel = ko.observable(true);
                this.displayReturnReason = ko.observable(true);   
                this.enableReturnReason = ko.observable(true);  
                this.displayMessageArea = ko.observable(true);
                this.enableMessageComment = ko.observable(false);
                
            }
        }
        
    }
}