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
        isSendToApplicant: KnockoutObservable<number> = ko.observable(0);
        appType: number = 1;
        prePostAtr: number = 0;
        sendValue: KnockoutObservable<number> = ko.observable(1);
        approvalPhaseState1: KnockoutObservableArray<ApprovalPhaseState> = ko.observableArray([]);
        applicantObj: KnockoutObservable<any> = ko.observable(null);
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
                        let sidLogin = result.sidLogin;
                        let applicantID = result.application.applicantSID;
                        let empName = result.empName;
                        let isMail = false;
                        if(!nts.uk.util.isNullOrEmpty(result.applicantMail)){//TH co mail
                            empName = result.empName + '(@)';
                            isMail = true;
                        }
                        self.applicantObj({empName: empName, isMail: isMail});
                        self.isSendToApplicant(sidLogin != applicantID && self.applicantObj().isMail ? 1 : 0);
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
                                // list frame > 1
                                if (phase.listApprovalFrame.length >1 ) {
                                         _.each(frame.listApprover, function(approver){//for by approver
                                            //fill approver
                                            let showButton = approver.approverMail.length >0 ? 1 : 0;
                                            listApproverDto.push(new Approver(approver.approverID, 
                                                    approver.approverMail.length >0 ? approver.approverName + '(@)' : approver.approverName, 
                                                    approver.approverMail, showButton, sidLogin));
                                            //check agent
                                            if(approver.representerID != '' && approver.representerName != ''){
                                                //fill agent
                                                let showButton1 = approver.agentMail.length >0 && approver.representerID != result.sIdLogin ? 1 : 0
                                                listApproverDto.push(new Approver(approver.representerID, 
                                                    approver.agentMail.length >0 ? approver.representerName + '(@)' : approver.representerName, 
                                                    approver.agentMail, showButton1, sidLogin));
                                            }
                                        });
                                  let af = new ApprovalFrame(frame.phaseOrder, frame.frameOrder, frame.approvalAtrName,listApproverDto);
                                    // sort by nameApprover;
                                    af.nameApprover = listApproverDto[0].dispApproverName;
                                  listFrameDto.push(af);  
                                    // case  approver group
                                    // list frame = 1
                                }else {
                                        
                                        _.each(frame.listApprover, (approver, index ) => {
                                            //fill approver
                                            let showButton = approver.approverMail.length >0 ? 1 : 0;
                                            listApproverDto = [];
                                            listApproverDto.push(new Approver(approver.approverID, 
                                                    approver.approverMail.length >0 ? approver.approverName + '(@)' : approver.approverName, 
                                                    approver.approverMail, showButton, sidLogin));
                                            //check agent
                                            if(approver.representerID != '' && approver.representerName != ''){
                                                //fill agent
                                                let showButton1 = approver.agentMail.length >0 && approver.representerID != result.sIdLogin ? 1 : 0
                                                listApproverDto.push(new Approver(approver.representerID, 
                                                    approver.agentMail.length >0 ? approver.representerName + '(@)' : approver.representerName, 
                                                    approver.agentMail, showButton1, sidLogin));
                                            }
                                           let af = new ApprovalFrame(frame.phaseOrder, index +1 , frame.approvalAtrName,listApproverDto);
                                           af.nameApprover = approver.approverName;
                                           listFrameDto.push(af);
                                           
                                        });
                                }
                                
                               
                                
                      
                            });
                            listPhaseDto.push(new ApprovalPhaseState(phase.phaseOrder, listFrameDto));
                        });
                        // sort list approval
                        if(listPhaseDto != undefined && listPhaseDto.length != 0) {
                            listPhaseDto.forEach((el) => {
                                if(el.listApprovalFrame != undefined && el.listApprovalFrame.length != 0) {
                                    //sort by listApprovalFrame
                                        el.listApprovalFrame = _.orderBy(el.listApprovalFrame, ['nameApprover'],['asc']);
                                    // set again approver 
                                    _.each(el.listApprovalFrame, (item, index) => {
                                        if (index+1 != item.frameOrder ) {
                                            item.frameOrder = index +1;    
                                        }
                                    });
                                    
                                }
                            });  
                        }
                        
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
        getApproverLabel(index) {
            if(index <=9){
                return nts.uk.resource.getText("KAF000_9",[(index)+'']);    
            }
            return ""; 
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
            // sort list approval
                        if(self.approvalRootState() != undefined && self.approvalRootState().length != 0) {
                            self.approvalRootState().forEach((el) => {
                                if(el.listApprovalFrame != undefined && el.listApprovalFrame.length != 0) {
                                        el.listApprovalFrame.forEach((el1) =>{
                                               if(el1.listApprover != undefined && el1.listApprover.length != 0) {
                                                   el1.listApprover = _.orderBy(el1.listApprover, ['nameApprover'],['asc']);                                   
                                               }
                                        });
                                }
                            });  
                        }
            
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
                if(self.isSendToApplicant() == 0){
                    //エラーメッセージ（Msg_14）
                    dialog.alertError({ messageId: "Msg_14" });
                    return;
                }
            }
            //申請者にメール送信かチェックする
            let applicantID = '';
            if (self.isSendToApplicant() == 1) {//チェックあり
                //申請者をループ対象に追加する
//                listSendMail.push(self.applicant().employeeID);
                applicantID = self.applicant().employeeID;
            }
            let command = {
                'mailContent': ko.toJS(self.mailContent),
                'application': ko.toJS(self.application),
                'sendMailOption': listSendMail,
                'applicantID' : applicantID,
                'sendMailApplicaint': self.isSendToApplicant() == 1 ? true : false
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
                if (res.messageId == 'Msg_1309') {//エラーメッセージを表示する（Msg_1309）
                    dialog.alertError({ messageId: res.messageId });
                } else {
                    //Msg1057
                    dialog.alertError({ messageId: res.messageId }).then(() => {
                        nts.uk.ui.windows.close();
                    });;
                }
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
        nameApprover: string;
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
        constructor(id: string, name: string, mail: string, isSend: number, sidLogin: string) {
            this.id = id;
            this.dispApproverName = name;
            this.mail = mail;
            this.isSend = sidLogin == id ? ko.observable(0) : ko.observable(isSend);
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

