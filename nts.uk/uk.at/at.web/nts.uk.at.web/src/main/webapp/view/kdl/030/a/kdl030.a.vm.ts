module nts.uk.at.view.kdl030.a.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import getMessage = nts.uk.resource.getMessage;
    import ApplicationDto = nts.uk.at.view.kaf000.b.viewmodel.model.ApplicationDto;
    export class ScreenModel {
        applicant: any = ko.observable(null);
        application: any = ko.observable(null);
        mailContent: KnockoutObservable<String> = ko.observable(null);
        approvalRootState: KnockoutObservableArray<ApprovalPhaseState> = ko.observableArray([]);
        appID: string = "";
        optionList: KnockoutObservableArray<any> = ko.observableArray([]);
        isSendToApplicant: KnockoutObservable<boolean> = ko.observable(true);
        appType: number = 1;
        prePostAtr: number = 0;
        sendValue: KnockoutObservable<number> = ko.observable(1);
        approvalPhaseState1: KnockoutObservableArray<ApprovalPhaseState> = ko.observableArray([]);
        constructor() {
            var self = this;
            self.optionList = ko.observableArray([
                new ItemModel(1, getText('KDL030_26')),
                new ItemModel(0, getText('KDL030_27'))
            ]);
            let param = getShared("KDL030_PARAM");
            self.appID = param.appID;
        }
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            nts.uk.ui.block.invisible();
            if (!_.isEmpty(self.appID)){
                service.getApplicationForSendByAppID(self.appID).done(function(result) {
                    if (result) {//check data
                        let listApprovalPhase = result.listApprovalPhaseStateDto
                        self.mailContent(result.mailTemplate);
                        self.applicant(ko.toJS({employeeID: result.application.applicantSID, smail: result.applicantMail}));
                        self.application(ko.toJS(result.application));
                        //list Phase
                        let listPhaseDto: Array<ApprovalPhaseState> = [];
                        _.each(listApprovalPhase, function(phase){//for by phase
                            //list frame
                            let listFrameDto: Array<ApprovalFrame> = [];
                            _.each(phase.listApprovalFrame, function(frame){//for by frame
                                //list approver
                                let listApproverDto: Array<Approver> = [];
                                _.each(frame.listApprover, function(approver){//for by approver
                                //fill approver
                                    let showButton = approver.approverMail.length >0 && approver.approverID != result.sidLogin ? 1 : 0;
                                    listApproverDto.push(new Approver(approver.approverID, 
                                            approver.approverMail.length >0 ? approver.approverName + '(@)' : approver.approverName, 
                                            approver.approverMail, showButton));
                                    //check agent
                                    if(approver.representerID != '' && approver.representerName != ''){
                                        //fill agent
                                        let showButton1 = approver.agentMail.length >0 && approver.representerID != result.sIdLogin ? 1 : 0
                                        listApproverDto.push(new Approver(approver.representerID, 
                                            approver.agentMail.length >0 ? approver.representerName + '(@)' : approver.representerName, 
                                            approver.agentMail, showButton1));
                                    }
                                });
                                listFrameDto.push(new ApprovalFrame(frame.phaseOrder, frame.frameOrder, frame.approvalAtrName,listApproverDto));
                            });
                            listPhaseDto.push(new ApprovalPhaseState(phase.phaseOrder, listFrameDto));
                        });
                        self.approvalRootState(listPhaseDto);
                    }
                    dfd.resolve();
                }).fail(function(res: any) {
                    dfd.reject();
                    dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds }).then(function() {
                    });
                }).always(function(res: any) {
                    nts.uk.ui.block.clear();
                });
                return dfd.promise();
            }
        }
        // アルゴリズム「メール送信」を実行する
        sendMail() {
            var self = this;
            //validate
            $(".A4_2").trigger("validate");
            if (nts.uk.ui.errors.hasError()){
                return;
            }
            let listSendMail: Array<String> = [];
            _.forEach(self.approvalRootState(), x => {
                _.forEach(x.listApprovalFrame, y => {
                    _.forEach(y.listApprover, z => {
                        if (z.isSend() == 1)
                        listSendMail.push(z.id);
                    });
                });
            });
            //送信対象者リストの「メール送信」をチェックする
            if(listSendMail.length == 0){//「送信する」の承認者が０人の場合
//            EA修正履歴 No.2819
//            #101767
                //申請者にメール送信かチェックする
                if(!self.isSendToApplicant()){
                    //エラーメッセージ（Msg_14）
                    dialog.alertError({ messageId: "Msg_14" });
                    return;
                }
            }
            //申請者にメール送信かチェックする
            let applicantID = '';
            if (self.isSendToApplicant()) {//チェックあり
                //申請者をループ対象に追加する
//                listSendMail.push(self.applicant().employeeID);
                applicantID = self.applicant().employeeID;
            }
            let command = {
                'mailContent': ko.toJS(self.mailContent),
                'application': ko.toJS(self.application),
                'sendMailOption': listSendMail,
                'applicantID' : applicantID,
                'sendMailApplicaint': self.isSendToApplicant()
            };
            nts.uk.ui.block.invisible();
            service.sendMail(command).done(function(result) {
                nts.uk.ui.block.clear();
                // TO DO
                if (result) {
                    // 成功
                    let successList: Array<string> = [];
                    // 送信失敗 
                    let failedList: Array<string> = [];
                    // メール送信時のエラーチェック
                    if (result.successList) {
                        _.forEach(result.successList, x => {
                            successList.push(x);
                        });
                    }
                    if (result.errorList) {
                        _.forEach(result.errorList, x => {
                            failedList.push(x);
                        });
                    }
                    setShared("KDL030_PARAM_RES", command);
                    self.handleSendMailResult(successList, failedList);
                }
            }).fail(function(res: any) {
                nts.uk.ui.block.clear();
                //Msg1057
                dialog.alertError({ messageId: res.messageId}).then(() =>{
                    nts.uk.ui.windows.close();
                });;
            });

        }
        cancel() {
            nts.uk.ui.windows.close(); 
        }

        handleSendMailResult(successList, failedList) {
            let numOfSuccess = successList.length;
            let numOfFailed = failedList.length
            let sucessListAsStr = "";
            //送信出来た人があったかチェックする
            //送信できた人なし
            if (numOfSuccess > 0) {//送信できた人あり
                //情報メッセージ（Msg_207）を画面表示する
                dialog.info({messageId: "Msg_207" }).then(() =>{
                    //アルゴリズム「送信・送信後チェック」で溜め込んだ社員名があったかチェックする
                    if(numOfFailed > 0){//溜め込んだ社員名無しあり
                        //エラーメッセージ（Msg_651）と溜め込んだ社員名をエラーダイアログに出力する
                        dialog.alertError({ message: getMessage('Msg_651') + "\n" + failedList.join('\n'), messageId: "Msg_651" }).then(() =>{
                            nts.uk.ui.windows.close();
                        });
                    }else{
                        nts.uk.ui.windows.close();
                    }
                });
            }
        }
    }

    export class ApprovalPhaseState {
        phaseOrder: number;
        listApprovalFrame: Array<ApprovalFrame>;
        constructor(phaseOrder: number, listApprovalFrame: Array<ApprovalFrame>){
            this.phaseOrder = phaseOrder;
            this.listApprovalFrame = listApprovalFrame;
        }
    }
    export class ApprovalFrame {
        phaseOrder: number;
        frameOrder: number;
        approvalAtrName: string;
        listApprover: Array<Approver>;
        constructor(phaseOrder: number, frameOrder: number, approvalAtrName: string,listApprover: Array<Approver>) {
            this.phaseOrder = phaseOrder;
            this.frameOrder = frameOrder;
            this.approvalAtrName = approvalAtrName;
            this.listApprover = listApprover;
        }
    }
    export class Approver {
        id: string;
        dispApproverName: string;
        mail: string;
        isSend: KnockoutObservable<number>;;
        showButton: boolean;
        constructor(id: string, name: string, mail: string, isSend: number) {
            this.id = id;
            this.dispApproverName = name;
            this.mail = mail;
            this.isSend = ko.observable(isSend);
            this.showButton = isSend == 0 ? false : true;
        }
    }
    export class ItemModel {
        code: KnockoutObservable<number>;
        name: KnockoutObservable<string>;
        dispCode: number;
        dispName: string;
        constructor(code: number, name: string) {
            this.dispCode = code;
            this.dispName = name;
            this.code = ko.observable(code);
            this.name = ko.observable(name);
        }
    }
}

