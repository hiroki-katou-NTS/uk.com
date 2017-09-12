module nts.uk.com.view.cmm011.f {
    export module viewmodel {
        
        import Workplace = base.IWorkplace;
        import CreationType = base.CreationWorkplaceType;
        
        export class ScreenModel {
            
            workplaceCode: KnockoutObservable<string>;
            workplaceName: KnockoutObservable<string>;
            
            itemList: KnockoutObservableArray<BoxModel>;
            selectedValBox: KnockoutObservable<number>;
            
            constructor() {
                let self = this;
                
                self.workplaceCode = ko.observable(null);
                self.workplaceName = ko.observable(null);
                
                self.itemList = ko.observableArray([
                    new BoxModel(CreationType.CREATE_ON_TOP, nts.uk.resource.getText("CMM011_33", ['Com_Workplace'])),
                    new BoxModel(CreationType.CREATE_BELOW, nts.uk.resource.getText("CMM011_34", ['Com_Workplace'])),
                    new BoxModel(CreationType.CREATE_TO_CHILD, nts.uk.resource.getText("CMM011_35", ['Com_Workplace']))
                ]);
                self.selectedValBox = ko.observable(1);
            }
            
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                
                let workplaceInfor: Workplace = nts.uk.ui.windows.getShared("WorkplaceInfor");
                self.workplaceCode(workplaceInfor.code);
                self.workplaceCode(workplaceInfor.name);
                
                dfd.resolve();
                
                return dfd.promise();
            }
            
            public execution() {
                let self = this;
                nts.uk.ui.windows.setShared("CreatedWorkplaceCondition", self.selectedValBox());
                nts.uk.ui.windows.close();
            }
            
            public close() {
                nts.uk.ui.windows.close();
            }
            
            public showMessageError(res: any) {
                if (res.businessException) {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
                }
            }
        }
        
        class BoxModel {
            value: number;
            name: string;
            constructor(value: number, name: string){
                let self = this;
                self.value = value;
                self.name = name;
            }
        }
    }
}