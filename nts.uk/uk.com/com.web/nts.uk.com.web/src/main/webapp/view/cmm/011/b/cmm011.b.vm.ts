module nts.uk.com.view.cmm011.b {
    export module viewmodel {
        
        import WorkplaceHistory = base.WorkplaceHistoryAbstract;
        import IHistory = nts.uk.com.view.cmm011.base.IHistory;
        
        export class ScreenModel {
            
            
            workplaceHistory: KnockoutObservable<WorkplaceHistoryModel>;
            startDate: KnockoutObservable<string>;
            
            constructor() {
                let self = this;
                
                self.workplaceHistory = ko.observable(new WorkplaceHistoryModel(self));
                self.startDate = ko.observable(null);
            }
            
            /**
             * startPage
             */
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                
                dfd.resolve();
                
                return dfd.promise();
            }
            
            /**
             * execution
             */
            public execution() {
                nts.uk.ui.windows.close();
            }
            
            /**
             * close
             */
            public close() {
                nts.uk.ui.windows.close();
            }
            
            /**
             * showMessageError
             */
            public showMessageError(res: any) {
                if (res.businessException) {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
                }
            }
        }
        
        /**
         * WorkplaceHistoryModel
         */
        class WorkplaceHistoryModel extends WorkplaceHistory {
            
            screenModel: ScreenModel;
            
               // mode
            isSelectionMode: KnockoutObservable<boolean>;
            isNewMode: KnockoutObservable<boolean>;
            isAddMode: KnockoutObservable<boolean>;
            isUpdateMode: KnockoutObservable<boolean>;
            
            constructor(screenModel: ScreenModel) {
                super();
                let self = this;
                
                self.screenModel = screenModel;
                
                // mode
                self.isSelectionMode = ko.observable(false);
                self.isNewMode = ko.observable(false);
                self.isAddMode = ko.observable(false);
                self.isUpdateMode = ko.observable(false); 
                
                self.init();
                
                // subscribe
                self.lstWpkHistory.subscribe(newList => {
                    
                    // list empty or null -> new mode
                    if (!newList || newList.length <= 0) {
                        self.isNewMode(true);
                    }
                });
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
            
            /**
             * addHistory
             */
            public addHistory() {
                let self = this;
                self.isAddMode(true);
            }
            
            /**
             * updateHistory
             */
            public updateHistory() {
                let self = this;
                self.isUpdateMode(true);
            }
            
            /**
             * removeHistory
             */
            public removeHistory() {
                let self = this;
            }
        }
    }
}