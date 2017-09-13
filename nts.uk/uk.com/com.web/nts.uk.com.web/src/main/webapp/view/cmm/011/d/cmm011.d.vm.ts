module nts.uk.com.view.cmm011.d {
    export module viewmodel {
        
        import WorkplaceHistory = base.WorkplaceHistoryAbstract;
        import IHistory = nts.uk.com.view.cmm011.base.IHistory;
        
        export class ScreenModel {
            
            workplaceHistory: KnockoutObservable<WorkplaceHistoryModel>;
            startDate: KnockoutObservable<string>;
            
            constructor() {
                let self = this;
                self.workplaceHistory = ko.observable(new WorkplaceHistoryModel());
                self.startDate = ko.observable(null);
            }
            
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                
                dfd.resolve();
                
                return dfd.promise();
            }
            
            public execution() {
                let self = this;
                
                if (!self.validate()) {
                    return;
                }
                // TODO: save database
//                let startDateLatest: string = nts.uk.ui.windows.getShared("StartDateLatestHistory");
                
                // valid start date
//                let latestDate: Date = new Date(startDateLatest);
//                let inputDate: Date = new Date(self.startDate());
//                if (inputDate <= latestDate) {
//                    nts.uk.ui.dialog.alertError({ messageId: "Msg_102" }).then(() => { 
//                        nts.uk.ui.windows.close();
//                    });
//                    return;
//                }
                nts.uk.ui.windows.close();
            }
            
            private validate(): boolean {
                let self = this;
                
                self.clearError();
                
                $('#start-date').ntsEditor('validate');
                
                if ($('.nts-input').ntsError('hasError')) {
                    return false;
                }
                return true;
            }
            
            private clearError() {
                $('#start-date').ntsError('clear');
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
    }
}