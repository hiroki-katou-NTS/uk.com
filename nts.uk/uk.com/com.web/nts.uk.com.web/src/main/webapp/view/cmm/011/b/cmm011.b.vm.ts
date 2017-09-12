module nts.uk.com.view.cmm011.b {
    export module viewmodel {
        
        import WorkplaceHistory = base.WorkplaceHistoryAbstract;
        import IHistory = nts.uk.com.view.cmm011.base.IHistory;
        
        export class ScreenModel {
            
            workplaceHistory: KnockoutObservable<WorkplaceHistoryModel>;
            startDate: KnockoutObservable<string>;
            
            constructor() {
                let self = this;
                self.workplaceHistory = ko.observable(new WorkplaceHistoryModel());
                self.startDate = ko.observable("2017/09/08");
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
                let self = this;
                
                self.init();
            }
            
            init() {
                let self = this;
                let lstWpkHistory: Array<IHistory> = [
                    {workplaceId: "ABC1", historyId: "ABC1", startDate: "2015/04/01", endDate: "9999/12/31"},
                    {workplaceId: "ABC2", historyId: "ABC2", startDate: "2015/04/01", endDate: "9999/12/31"},
                    {workplaceId: "ABC3", historyId: "ABC3", startDate: "2015/04/01", endDate: "9999/12/31"},
                    {workplaceId: "ABC4", historyId: "ABC4", startDate: "2015/04/01", endDate: "9999/12/31"}
                ]
                self.lstWpkHistory(lstWpkHistory);
                self.selectFirst();
            }
        }
    }
}