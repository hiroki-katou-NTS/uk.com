module nts.uk.at.view.kdl030.a.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import ApplicationDto = nts.uk.at.view.kaf000.b.viewmodel.model.ApplicationDto;
    export class ScreenModel {
        mailContent: KnockoutObservable<String> = ko.observable(null);
        approvalRootState: any = ko.observableArray([]);
        appID: string = "03d4c33f-a20a-4002-8d0e-f5af490f4924";
        optionList: KnockoutObservableArray<any> = ko.observableArray([]);
        isHasMail: KnockoutObservableArray<number> = ko.observableArray([]);
        selectedSendMail: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        appType: number = 1;
        prePostAtr: number = 0;
        sendValue: KnockoutObservable<number> = ko.observable(1);
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
                    self.approvalRootState(ko.mapping.fromJS(result.approvalRoot.approvalRootState.listApprovalPhaseState)());
                    let i = 0;
                    _.forEach(result.listApprover, x =>{
                        self.isHasMail.push(i%2==0 ? 0 : 1);
                        self.selectedSendMail.push(new ItemModel(i%2==0 ? 0 : 1, "xxx"));
                        i++;
                    });
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
        sendMail(){
            var self = this;
            let x = self.selectedSendMail;
        }
        cancel(){
            
        }
        
    }
    
    export class ApprovalFrame{
        phaseOrder: number;
        frameOrder: number;
        approvalAtr: string;
        listApprover: Array<Approver>;
        constructor(phaseOrder: number, frameOrder: number, approvalAtr: string, listApprover: Array<Approver>){
            this.phaseOrder = phaseOrder;
            this.frameOrder = frameOrder;
            this.approvalAtr = approvalAtr;
            this.listApprover = listApprover;
        }
    }
    export class Approver{
        approverID: string;
        approverName: string;
        representerID: string;
        representerName: string;
        constructor(approverID: string ,approverName: string, representerID: string, representerName: string){
            this.approverID = approverID;
            this.approverName = approverName;
            this.representerID = representerID;
            this.representerName = representerName;
        }    
    }
    export class ItemModel{
        code: KnockoutObservable<number>;
        name: KnockoutObservable<string>;
        dispCode: number;
        dispName: string;
        constructor(code: number, name: string){
            this.dispCode = code;
            this.dispName = name;
            this.code = ko.observable(code);
            this.name = ko.observable(name);
        }
    }
}

