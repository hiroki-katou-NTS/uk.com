module nts.uk.com.view.cmm011.b {
    export module viewmodel {
        
        import WorkplaceHistory = base.WorkplaceHistoryAbstract;
        import IHistory = nts.uk.com.view.cmm011.base.IHistory;
        
        export class ScreenModel {
            
            workplaceHistory: KnockoutObservable<WorkplaceHistoryModel>;
            
            constructor() {
                let self = this;
                self.workplaceHistory = ko.observable(new WorkplaceHistoryModel());
            }
            
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                
                dfd.resolve();
                
                return dfd.promise();
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
    }
}