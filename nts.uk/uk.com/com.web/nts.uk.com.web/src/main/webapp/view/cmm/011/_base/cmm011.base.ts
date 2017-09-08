module nts.uk.com.view.cmm011 {
    export module base {

        export abstract class WorkplaceHistoryAbstract {

            lstWpkHistory: KnockoutObservableArray<IHistory>;
            selectedWpkHistory: KnockoutObservable<string>;
            
            constructor() {
                let self = this;
                self.lstWpkHistory = ko.observableArray([]);
                self.selectedWpkHistory = ko.observable(null);
            }
            
            public selectFirst() {
                let self = this;
                self.selectedWpkHistory(self.lstWpkHistory()[0].historyId);
            }
            
        }
        
        export interface IHistory {
            historyId: string;
            startDate: string;
            endDate: string;
            textDisplay?: string;
        }
    }
}