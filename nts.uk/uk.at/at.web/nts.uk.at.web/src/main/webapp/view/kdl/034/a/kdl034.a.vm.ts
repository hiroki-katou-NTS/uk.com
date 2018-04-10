module nts.uk.at.view.kdl034.a {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog = nts.uk.ui.dialog;
    import ApplicationDto = nts.uk.at.view.kaf000.b.viewmodel.model.ApplicationDto;
    export module viewmodel {
        export class ScreenModel {
            listApprover: KnockoutObservableArray<Approver> = ko.observableArray([]);
            errorFlag: number = -1;
            selectedApproverId: KnockoutObservable<string> = ko.observable(null);
            returnReason: KnockoutObservable<string> = ko.observable("");
            version: number = 0;
            applicationContent: ApplicationDto;
            appID: string = "0b5dc40d-37a6-43cc-b6af-e8fdeece973e";
            constructor() {
                var self = this;
            }
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                nts.uk.ui.block.invisible();
                service.getAppInfoByAppID(self.appID).done(function(result) {
                    if (result) {
                        let applicant = result.applicant;
                        let approvalFrame = result.approvalFrameDtoForRemand;
                        let listApprover: Array<Approver> = [];
                        self.version = result.version;
                        self.errorFlag = result.errorFlag;
                        applicant = new Approver(applicant.pid, applicant.pname, null, null, result.applicantPosition, "");
                        listApprover.push(applicant);
                        approvalFrame.forEach(function(approvalState) {
                            approvalState.listApprover.forEach(function(approver) {
                                listApprover.push(new Approver(approver.approverID, approver.approverName, approver.phaseOrder, approvalState.approvalReason, approver.jobtitle, approver.representerName));
                            });
                        });
                        self.listApprover(listApprover);
                        self.selectedApproverId(applicant.pid);
                    }
                    dfd.resolve();
                }).fail(function(res: any) {
                    dfd.reject();
                    dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds }).then(function() {
                        nts.uk.request.jump("../test/index.xhtml");
                    });
                }).always(function(res: any){
                    nts.uk.ui.block.clear();
                });
                nts.uk.ui.windows.close();
                return dfd.promise();
            }

            submitAndCloseDialog() {
                var self = this;
                dialog.confirm({ messageId: "Msg_384"}).ifYes(() => {
                        //nts.uk.ui.block.invisible();
                        let currentApprover = _.find(self.listApprover(), x => { return x.id === self.selectedApproverId() });
                        let command = {
                            appID: self.appID,
                            version: self.version,
                            order: currentApprover.phaseOrder,
                            returnReason: self.returnReason()
                        }
                        nts.uk.at.view.kdl034.a.service.remand(command)
                            .done(function(result){
                                dialog.info({ messageId: "Msg_392", messageParams: result });
                                setShared("KDL034_PARAM", {"returnReason": self.returnReason});
                            }).fail(function(res){
                                dialog.alertError (res.errorMessage);
                            }).always(function(){
                                nts.uk.ui.block.clear();
                                nts.uk.ui.windows.close();
                            });
                }).ifNo(() => {
                    
                });
                
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
            constructor(id: string, name: string, phaseOrder: number, approvalReason: string, jobTitle: string, representerName: string) {
                this.id = id;
                this.name = name;
                this.phaseOrder = phaseOrder;
                this.approvalReson = approvalReason;
                this.jobTitle = jobTitle;
                if (_.isEmpty(phaseOrder)) {
                    this.dispApprover = "申請者：　" + jobTitle + "　" + name;
                } else if (phaseOrder == 2) {
                    this.dispApprover = "フェーズ" + phaseOrder + "の承認者：　" + jobTitle + "　" + name + "　" + representerName;
                } else {
                    this.dispApprover = "フェーズ" + phaseOrder + "の承認者：　" + jobTitle + "　" + name;
                }
            }

        }
        interface KDL034_PARAM {
            listApprover: KnockoutObservableArray<Approver>;
        }
    }
}