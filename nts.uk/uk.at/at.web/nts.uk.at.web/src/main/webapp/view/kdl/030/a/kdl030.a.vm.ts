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
                    if (result) {
                        let listApprovalPhase: any = result.listApprovalPhaseStateDto;
                        self.mailContent(result.mailTemplate);
                        self.applicant(ko.toJS({employeeID: result.application.applicantSID, smail: result.applicantMail}));
                        self.application(ko.toJS(result.application));
                        for (let i = 0; i < listApprovalPhase.length; i++) {
                            for (let listApprovalFrame = listApprovalPhase[i].listApprovalFrame, j = 0; j < listApprovalFrame.length; j++) {
                                for (let listApprover = listApprovalFrame[j].listApprover, k = 0; k < listApprover.length; k++) {
                                    if (listApprover[k].representerName.length > 0){
                                        listApprover[k].approverName += '(' + listApprover[k].representerName + ')';
                                    } else if (listApprover[k].approverMail.length >0){
                                        listApprover[k].approverName += '(@)';
                                        listApprover[k]['isSend'] = 1;
                                    } else {
                                        listApprover[k]['isSend'] = 0;
                                    }
                                    listApprover[k]['dispApproverName'] = listApprover[k].approverName;
                                }
                            }
                        }
                        self.approvalRootState(ko.mapping.fromJS(listApprovalPhase)());
                    }
                    dfd.resolve();
                }).fail(function(res: any) {
                    dfd.reject();
                    dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds }).then(function() {
                        nts.uk.request.jump("../test/index.xhtml");
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
            let listSendMail: Array<String> = [];
            if (self.isSendToApplicant()) {
                listSendMail.push(self.applicant().employeeID);
            }
            let listApprovalPhase = ko.toJS(self.approvalRootState);
            _.forEach(listApprovalPhase, x => {
                _.forEach(x.listApprovalFrame, y => {
                    _.forEach(y.listApprover, z => {
                        if (z.isSend == 1)
                        listSendMail.push(z.approverID);
                    });
                });
            });

            if (listSendMail.length > 0) {
                let command = {
                    'mailContent': ko.toJS(self.mailContent),
                    'application': ko.toJS(self.application),
                    'sendMailOption': listSendMail
                };
                nts.uk.ui.block.invisible();
                service.sendMail(command).done(function(result) {
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
                    dialog.alertError(res.errorMessage);
                }).always(function(res: any) {
                    nts.uk.ui.block.clear();
                });

            } else {
                dialog.alertError({ messageId: "Msg_14" });
            }
        }
        cancel() {
            nts.uk.ui.windows.close(); 
        }

        handleSendMailResult(successList, failedList) {
            let numOfSuccess = successList.length;
            let numOfFailed = failedList.length
            let sucessListAsStr = "";
            if (numOfSuccess != 0 && numOfFailed ==0) {
                dialog.info({ message: getMessage('Msg_207') + "\n" + successList.join('\n'), messageId: "Msg_207" }).then(() =>{
                    nts.uk.ui.windows.close();
                });
            } else if (numOfFailed != 0 && numOfSuccess ==0) {
                dialog.alertError({ message: getMessage('Msg_651') + "\n" + failedList.join('\n'), messageId: "Msg_657" }).then(() =>{
                    nts.uk.ui.windows.close();
                });
            } else {
                dialog.info({ message: getMessage('Msg_207') + "\n" + successList.join('\n'), messageId: "Msg_207" }).then(()=>{
                    dialog.alertError({ message: getMessage('Msg_651') + "\n" + failedList.join('\n'), messageId: "Msg_657" }).then(() =>{
                        nts.uk.ui.windows.close();
                    });
                });
            }
        }
    }


    export class ApprovalPhaseState {
        phaseOrder: number;
        approvalAtr: string;
        listApprovalFrame: Array<ApprovalFrame>;
    }
    export class ApprovalFrame {
        phaseOrder: number;
        frameOrder: number;
        approvalAtr: string;
        listApprover: Array<Approver>;
        approverID: string;
        approverName: string;
        representerID: string;
        representerName: string;
        approvalReason: string;
        constructor(phaseOrder: number, frameOrder: number, approvalAtr: string, listApprover: Array<Approver>) {
            this.phaseOrder = phaseOrder;
            this.frameOrder = frameOrder;
            this.approvalAtr = approvalAtr;
            this.listApprover = listApprover;
        }
    }
    export class Approver {
        approverID: string;
        approverName: string;
        representerID: string;
        representerName: string;
        mail: string;
        constructor(approverID: string, approverName: string, representerID: string, representerName: string, mail: string) {
            this.approverID = approverID;
            this.approverName = approverName;
            this.representerID = representerID;
            this.representerName = representerName;
            this.mail = mail;
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

