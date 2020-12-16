module nts.uk.at.view.kdl034.a.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import getMessage = nts.uk.resource.getMessage;
    import ApplicationDto = nts.uk.at.view.kaf000.b.viewmodel.model.ApplicationDto;
    import block = nts.uk.ui.block;
    export class ScreenModel {
        listApprover: KnockoutObservableArray<Approver> = ko.observableArray([]);
        errorFlag: number = -1;
        selectedApproverId: KnockoutObservable<string> = ko.observable(null);
        returnReason: KnockoutObservable<string> = ko.observable("");
        version: number = 0;
        applicationContent: ApplicationDto;
        appID: Array<string> = [];
        sName: string = '';
        constructor() {
            let self = this;
            let param = getShared("KDL034_PARAM");
            self.appID = [param.appID];
            self.version = param.version;
        }
        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            block.invisible();
            if (_.isEmpty(self.appID)) {
                return dfd.promise();;
            }
            service.getAppInfoByAppID(self.appID).done(function(result) {
                block.clear();
                if (!result) {
                    return dfd.promise();;
                }
                let approvalFrame = _.sortBy(result.lstApprover, ['phaseOrder']);
                let listApprover: Array<Approver> = [];
                self.sName = result.applicantName;
                let applicant = new Approver(result.applicantId, result.applicantName, null, result.applicantJob, false);
                listApprover.push(applicant);
                approvalFrame.forEach(function(approver) {
                    if(result.phaseLogin == 0 || approver.phaseOrder > result.phaseLogin){
                        listApprover.push(new Approver(approver.approverID, approver.approverName,
                        approver.phaseOrder, approver.jobTitle, approver.agentFlag));
                    }
                });
                self.listApprover(listApprover);
                self.selectedApproverId(result.applicantId);
                dfd.resolve();
            }).fail(function(res: any) {
                block.clear();
            });
            return dfd.promise();
        }

        submitAndCloseDialog() {
            let self = this;
            dialog.confirm({ messageId: "Msg_384" }).ifYes(() => {
                block.invisible();
                let idSelected = self.selectedApproverId().split('__');
                let currentApprover = _.find(self.listApprover(), x => { return x.id === idSelected[0] && x.phaseOrder + "" === idSelected[1] });
                let command = {
                    appID: self.appID,
                    version: self.version,
                    order: currentApprover.phaseOrder,//差し戻し先
                    remandReason: self.returnReason(),//差し戻しコメント
                    applicaintName: self.sName//申請本人の名前
                }
                service.remand(command).done(function(result) {
                    block.clear();
                    let successList: Array<string> = [];
                    let failedList: Array<string> = [];
                    if (result) {
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
                    }
                    setShared("KDL034_PARAM_RES", command);
                    //情報メッセージ（Msg_223）
                    dialog.info({ messageId: "Msg_223" }).then(() => {
                        self.handleSendMailResult(successList, failedList);
                    });
                }).fail(function(res) {
                    block.clear();
                    //エラーメッセージ(Msg_197) - sai version
                    dialog.alertError({ messageId: res.messageId }).then(() => {
                        nts.uk.ui.windows.close();
                    });
                });
            }).ifNo(() => {});
        }
        
        handleSendMailResult(successList, failedList) {
            let numOfSuccess = successList.length;
            let numOfFailed = failedList.length
            let sucessListAsStr = "";
            if (numOfSuccess == 0 && numOfFailed == 0) {
                nts.uk.ui.windows.close();
            } else if (numOfSuccess != 0 && numOfFailed == 0) {
                let msg = getMessage('Msg_392');
                dialog.info({ message: msg.replace("{0}", successList.join('\n')), messageId: "Msg_392" }).then(() => {
                    nts.uk.ui.windows.close();
                });
            } else if (numOfSuccess == 0 && numOfFailed != 0) {
                dialog.alertError({ message: getMessage('Msg_769') + "\n" + failedList.join('\n'), messageId: "Msg_769" }).then(() => {
                    nts.uk.ui.windows.close();
                });
            } else {
                let msg = getMessage('Msg_392');
                dialog.info({ message: msg.replace("{0}", successList.join('\n')), messageId: "Msg_392" }).then(() => {
                    dialog.alertError({ message: getMessage('Msg_769') + "\n" + failedList.join('\n'), messageId: "Msg_769" }).then(() => {
                        nts.uk.ui.windows.close();
                    });
                });
            }
        }
        closeDialog() {
            nts.uk.ui.windows.close();
        }

    }
    export class Applicant {
        id: string;
        name: string;
        constructor(id: string, name: string) {
            this.id = id;
            this.name = name;
        }
    }

    export class Approver {
        id: string;
        name: string;
        phaseOrder: number;
        jobTitle: string;
        dispApprover: string;
        idAndPhase: string;
        constructor(id: string, name: string, phaseOrder: number, jobTitle: string, agent: boolean) {
            this.id = id;
            this.name = name;
            this.phaseOrder = phaseOrder;
            this.jobTitle = jobTitle;
            if (_.isNull(phaseOrder)) {//申請者
                this.dispApprover = "申請者：　" + jobTitle + "　" + name;
            } else {//フェーズ
                let nameDis = agent == true ? name + ' ' + '代行' : name;
                this.dispApprover = "フェーズ" + phaseOrder + "の承認者：　" + jobTitle + "　" + nameDis;
            }
            this.idAndPhase = id + "__" + phaseOrder;
        }

    }
    interface KDL034_PARAM {
        listApprover: KnockoutObservableArray<Approver>;
    }
}