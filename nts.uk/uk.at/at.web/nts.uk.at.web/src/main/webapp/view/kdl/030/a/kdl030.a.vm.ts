module nts.uk.at.view.kdl030.a.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import ApplicationDto = nts.uk.at.view.kaf000.b.viewmodel.model.ApplicationDto;
    export class ScreenModel {
        applicant: any = ko.observable(null);
        application: any = ko.observable(null);
        mailContent: KnockoutObservable<String> = ko.observable(null);
        approvalRootState: KnockoutObservableArray<ApprovalPhaseState> = ko.observableArray([]);
        appID: string = "0b5dc40d-37a6-43cc-b6af-e8fdeece973e";
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
        }
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            nts.uk.ui.block.invisible();

            service.getApplicationForSendByAppID(self.appID).done(function(result) {
                if (result) {
                    let listApprovalPhase: any = result.approvalRoot.approvalRootState.listApprovalPhaseState;
                    let index = 0;
                    self.mailContent(result.approvalTemplate.content);
                    self.applicant(ko.toJS(result.applicant));
                    self.application(ko.toJS(result.application));
                    for (let i = 0; i < listApprovalPhase.length; i++) {
                        for (let listApprovalFrame = listApprovalPhase[i].listApprovalFrame, j = 0; j < listApprovalFrame.length; j++) {
                            for (let listApprover = listApprovalFrame[j].listApprover, k = 0; k < listApprover.length; k++) {
                                let sMail = result.listApprover[index].smail;
                                let employeeId = result.listApprover[index].employeeId;
                                listApprover[k]['sMail'] = sMail;
                                listApprover[k]['isSend'] = 1;
                                index++;
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
            nts.uk.ui.windows.close();
            return dfd.promise();
        }
        sendMail() {
            var self = this;
            let listSendMail: Array<SendMailOption> = [];
            if (self.isSendToApplicant()) {
                listSendMail.push(new SendMailOption(self.applicant().employeeId, self.applicant().pname, self.applicant().smail));
            }
            let listApprovalPhase = ko.toJS(self.approvalRootState);
            _.forEach(listApprovalPhase, x => {
                _.forEach(x.listApprovalFrame, y => {
                    _.forEach(y.listApprover, z => {
                        listSendMail.push(new SendMailOption(z.approverID, z.approverName, z.sMail));
                    });
                });
            });

            if (listSendMail.length > 0) {
                let command = {
                    'mailContent': ko.toJS(self.mailContent),
                    'application': ko.toJS(self.application),
                    'sendMailOption': listSendMail
                };
                var dfd = $.Deferred();
                service.sendMail(command).done(function(result) {
                    // TO DO
                    if (result) {
                        // 成功
                        let successList: Array<string> = [];
                        // 送信失敗 
                        let failedList: Array<string> = [];
                        _.forEach(result, (x, index) => {
                            // メール送信時のエラーチェック
                            if (x == 0) {
                                successList.push(command.sendMailOption[index].employeName);
                            } else  {
                                failedList.push(command.sendMailOption[index].employeName);
                            }
                        });
                        self.handleSendMailResult(successList, failedList);
                        nts.uk.ui.windows.close();
                    }
                    dfd.resolve();
                }).fail(function(res: any) {
                    dialog.alertError(res.errorMessage);
                    nts.uk.ui.windows.close();
                }).always(function(res: any) {
                    nts.uk.ui.block.clear();
                });

            } else {
                dialog.alertError({ messageId: "Msg_14" });
            }
        }
        cancel() {

        }

        handleSendMailResult(successList, failedList) {
            let numOfSuccess = successList.length;
            let numOfFailed = failedList.length
            let sucessListAsStr = "";
            if (numOfSuccess !=0 ){
                let successListAsStr = "";
                _.forEach(successList, employeeName =>{
                    successListAsStr += employeeName + "\n";
                });
                dialog.info({message:successListAsStr, messageId: "Msg_207" });
            }
            if (numOfFailed != 0){
                let failedListAsStr = "";
                _.forEach(failedList, employeeName =>{
                    failedListAsStr += employeeName + "\n";
                });
                dialog.alertError({message:failedListAsStr, messageId: "Msg_657" });
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
    export class SendMailOption {
        employeeID: string;
        employeeName: string;
        sMail: string;
        constructor(employeeID: string, employeeName: string, sMail: string) {
            this.employeeID = employeeID;
            this.employeeName = employeeName;
            this.sMail = employeeID + "@nts.com";
        }
    }
}

