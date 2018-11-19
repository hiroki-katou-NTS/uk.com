module nts.uk.at.view.kdl034.a {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import getMessage = nts.uk.resource.getMessage;
    import ApplicationDto = nts.uk.at.view.kaf000.b.viewmodel.model.ApplicationDto;
    export module viewmodel {
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
                var self = this;
                let param = getShared("KDL034_PARAM");
                self.appID = param.appID;
                self.version = param.version;
            }
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                nts.uk.ui.block.invisible();
                if (!_.isEmpty(self.appID)){
                    service.getAppInfoByAppID(self.appID).done(function(result) {
                        if (result) {
                            let applicant = result.applicant;
                            let approvalFrame = result.approvalFrameDtoForRemand;
                            let listApprover: Array<Approver> = [];
                            self.errorFlag = result.errorFlag;
                            self.sName = applicant.pname;
                            applicant = new Approver(applicant.pid, applicant.pname, null, null, result.applicantPosition, false);
                            listApprover.push(applicant);
                            approvalFrame.forEach(function(approvalState) {
                                approvalState.listApprover.forEach(function(approver) {
                                    //TH approver
                                    listApprover.push(new Approver(approver.approverID, approver.approverName, 
                                        approvalState.phaseOrder, approvalState.approvalReason, approver.jobtitle, false));
                                    ////check TH agent
                                    if(approver.representerID != ''){
                                        listApprover.push(new Approver(approver.representerID, approver.representerName, 
                                            approvalState.phaseOrder, approvalState.approvalReason, approver.jobtitleAgent, true));
                                    }
                                });
                            });
                            self.listApprover(listApprover);
                            self.selectedApproverId(applicant.pid);
                        }
                        dfd.resolve();
                    }).fail(function(res: any) {
                    }).always(function(res: any){
                        nts.uk.ui.block.clear();
                    });
                }
                return dfd.promise();
            }

            submitAndCloseDialog() {
                var self = this;
                dialog.confirm({ messageId: "Msg_384"}).ifYes(() => {
                        nts.uk.ui.block.invisible();
                        let idSelected = self.selectedApproverId().split('__');
                        let currentApprover = _.find(self.listApprover(), x => { return x.id === idSelected[0] &&  x.phaseOrder+"" === idSelected[1]});
                        let command = {
                            appID: self.appID,
                            version: self.version,
                            order: currentApprover.phaseOrder,//差し戻し先
                            remandReason: self.returnReason(),//差し戻しコメント
                            applicaintName: self.sName//申請本人の名前
                        }
                        nts.uk.at.view.kdl034.a.service.remand(command) 
                            .done(function(result){
                                nts.uk.ui.block.clear();
                                let successList: Array<string> = [];
                                let failedList: Array<string> = [];
                                if (result){
                                        // メール送信時のエラーチェック
                                    if (result.successList){
                                        _.forEach(result.successList, x => {
                                            successList.push(x);
                                        });
                                    }
                                    if (result.errorList){
                                        _.forEach(result.errorList, x => {
                                            failedList.push(x);
                                        });
                                    }
                                }
                                setShared("KDL034_PARAM_RES", command);
                                //情報メッセージ（Msg_223）
                                dialog.info({ messageId: "Msg_223"}).then(()=>{
                                    self.handleSendMailResult(successList, failedList);
                                });
                            }).fail(function(res){
                                nts.uk.ui.block.clear();
                                //エラーメッセージ(Msg_197) - sai version
                                dialog.alertError ({messageId: res.messageId}).then(()=>{
                                    nts.uk.ui.windows.close();
                                });
                            });
                    
                }).ifNo(() => {
                });
            }
            handleSendMailResult(successList, failedList) {
                let numOfSuccess = successList.length;
                let numOfFailed = failedList.length
                let sucessListAsStr = "";
                if (numOfSuccess ==0 && numOfFailed == 0){
                    nts.uk.ui.windows.close();
                }else if (numOfSuccess !=0 && numOfFailed == 0){
                    let msg = getMessage('Msg_392');
                    dialog.info({message: msg.replace("{0}", successList.join('\n')), messageId: "Msg_392"}).then(()=>{
                        nts.uk.ui.windows.close();
                    });
                } else if (numOfSuccess ==0 && numOfFailed != 0){
                    dialog.alertError({message: getMessage('Msg_769')+"\n"+failedList.join('\n'), messageId: "Msg_769"}).then(()=>{
                        nts.uk.ui.windows.close();
                    });
                } else {
                    let msg = getMessage('Msg_392');
                    dialog.info({message: msg.replace("{0}", successList.join('\n')), messageId: "Msg_392"}).then(()=>{
                        dialog.alertError({message: getMessage('Msg_769')+"\n"+failedList.join('\n'), messageId: "Msg_769"}).then(()=>{
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
            approvalReson: string;
            jobTitle: string;
            dispApprover: string;
            idAndPhase: string;
            constructor(id: string, name: string, phaseOrder: number, approvalReason: string, jobTitle: string, agent: boolean) {
                this.id = id;
                this.name = name;
                this.phaseOrder = phaseOrder;
                this.approvalReson = approvalReason;
                this.jobTitle = jobTitle;
                if (_.isNull(phaseOrder)) {//申請者
                    this.dispApprover = "申請者：　" + jobTitle + "　" +  name;
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
}