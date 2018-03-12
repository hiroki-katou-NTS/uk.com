module nts.uk.at.view.kdl034.a {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    export module viewmodel {
        export class ScreenModel {
            listApprover: KnockoutObservableArray<Approver> = ko.observableArray([]);
            currentApprover: KnockoutObservable<Approver> = ko.observable(new Approver("","",null));
            returnReason: KnockoutObservable<string> = ko.observable("");
            version: number = 0;
            appID: string = "";
            constructor() {
                var self = this;
                let kdl034_param = getShared("KDL034_PARAM");
            }
            
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                nts.uk.ui.block.invisible();
                return dfd.promise();
            }
            
            submitAndCloseDialog(){
                var self = this;
                let command = {
                    appID: self.appID,
                    version: self.version,
                    order: self.currentApprover().phaseOrder,
                    returnReason: self.returnReason    
                }
                nts.uk.at.view.kdl034.a.service.remand(command)
                .done(()=>{
                    
                }).fail(()=>{
                    
                });
                nts.uk.ui.windows.close();     
            }
            
            closeDialog(){
                nts.uk.ui.windows.close();    
            }
        }
        
        export class Approver {
            id: string;
            name: string;
            phaseOrder: number;
            constructor(id: string, name: string, phaseOrder: number){
                this.id = id;
                this.name = name;
                this.phaseOrder = phaseOrder;
            }
        }
        
        interface KDL034_PARAM {
            listApprover: KnockoutObservableArray<Approver>;   
        }
    }
}