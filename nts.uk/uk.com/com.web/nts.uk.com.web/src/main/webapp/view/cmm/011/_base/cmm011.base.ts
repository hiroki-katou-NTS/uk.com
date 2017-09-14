module nts.uk.com.view.cmm011 {
    export module base {

        /**
         * WorkplaceHistoryAbstract
         */
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
            
            /**
             * selectFirst
             */
            public selectFirst() {
                let self = this;
                self.selectedWpkHistory(self.lstWpkHistory()[0].historyId);
            }
            
            /**
             * getSelectedHistory
             */
            public getSelectedHistory(): IHistory {
                let self = this;
                return self.lstWpkHistory().filter(item => item.workplaceId == self.selectedWpkHistory())[0];
            }
            
            /**
             * isWorkplaceHistoryLatest
             */
            public isWorkplaceHistoryLatest(): boolean {
                let self = this;
                return self.selectedWpkHistory() == self.lstWpkHistory()[0].workplaceId;
            }
            
            /**
             * fillTextDisplay
             */
            private fillTextDisplay() {
                let self = this;
                _.forEach(self.lstWpkHistory(), (item: IHistory) => {
                    item.textDisplay = item.startDate + " " + nts.uk.resource.getText("CMM011_26") + " " + item.endDate;
                })
            }
        }
        
        /**
         * IHistory
         */
        export interface IHistory {
            workplaceId: string;
            historyId: string;
            startDate: string;
            endDate: string;
            textDisplay?: string;
        }
        
        /**
         * IWorkplace
         */
        export interface IWorkplace {
            code: string;
            name: string;
        }
        
        /**
         * CreationWorkplaceType
         */
        export class CreationWorkplaceType {
            static CREATE_ON_TOP = 1;
            static CREATE_BELOW = 2;
            static CREATE_TO_CHILD = 3;
        }
    }
}