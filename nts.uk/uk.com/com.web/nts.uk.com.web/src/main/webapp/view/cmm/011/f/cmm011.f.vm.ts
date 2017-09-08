module nts.uk.com.view.cmm011.f {
    export module viewmodel {
        
        import WorkplaceHistory = base.WorkplaceHistoryAbstract;
        import IHistory = nts.uk.com.view.cmm011.base.IHistory;
        
        export class ScreenModel {
            
            workplaceHistory: KnockoutObservable<WorkplaceHistoryModel>;
            workplaceCode: KnockoutObservable<string>;
            workplaceName: KnockoutObservable<string>;
            
            itemList: KnockoutObservableArray<BoxModel>;
            selectedValBox: KnockoutObservable<number>;
            
            constructor() {
                let self = this;
                self.workplaceHistory = ko.observable(new WorkplaceHistoryModel());
                self.workplaceCode = ko.observable("abadasdas");
                self.workplaceName = ko.observable("abadasdas");
                
                self.itemList = ko.observableArray([
                    new BoxModel(1, nts.uk.resource.getText("CMM011_33", ['Com_Workplace'])),
                    new BoxModel(2, nts.uk.resource.getText("CMM011_34", ['Com_Workplace'])),
                    new BoxModel(3, nts.uk.resource.getText("CMM011_35", ['Com_Workplace']))
                ]);
                self.selectedValBox = ko.observable(1);
            }
            
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                
                dfd.resolve();
                
                return dfd.promise();
            }
            
            public execution() {
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
        
        class WorkplaceHistoryModel extends WorkplaceHistory {
            
            constructor() {
                super();
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