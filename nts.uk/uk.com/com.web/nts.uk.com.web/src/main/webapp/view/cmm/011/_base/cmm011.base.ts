module nts.uk.com.view.cmm011 {
    export module base {

        export abstract class WorkplaceHistoryAbstract {

            lstWpkHistory: KnockoutObservableArray<IHistory>;
            selectedWpkHistory: KnockoutObservable<string>;
            
            constructor() {
                let self = this;
                self.lstWpkHistory = ko.observableArray([]);
                self.selectedWpkHistory = ko.observable(null);
                
                // lstWpkHistory subscribe
                self.lstWpkHistory.subscribe((newLstHistory) => {
                    self.fillTextDisplay();
                });
            }
            
            public selectFirst() {
                let self = this;
                self.selectedWpkHistory(self.lstWpkHistory()[0].historyId);
            }
            
            private fillTextDisplay() {
                let self = this;
                _.forEach(self.lstWpkHistory(), (item: IHistory) => {
                    item.textDisplay = item.startDate + " " + nts.uk.resource.getText("CMM011_26") + " " + item.endDate;
                })
            }
        }
        
        export interface IHistory {
            workplaceId: string;
            historyId: string;
            startDate: string;
            endDate: string;
            textDisplay?: string;
        }
        
        export interface IWorkplace {
            code: string;
            name: string;
        }
        
        export class CreationWorkplaceType {
            static CREATE_ON_TOP = 1;
            static CREATE_BELOW = 2;
            static CREATE_TO_CHILD = 3;
        }
    }
}