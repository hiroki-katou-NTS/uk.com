module nts.uk.com.view.cmm011 {
    export module base {

        /**
         * WorkplaceHistoryAbstract
         */
        export abstract class WorkplaceHistoryAbstract {

            lstWpkHistory: KnockoutObservableArray<IHistory>;
            selectedHistoryId: KnockoutObservable<string>;
            
            constructor() {
                let self = this;
                self.lstWpkHistory = ko.observableArray([]);
                self.selectedHistoryId = ko.observable(null);
                
                // lstWpkHistory subscribe
                self.lstWpkHistory.subscribe((newLstHistory) => {
                    if (!newLstHistory || newLstHistory.length < 1) {
                        return;
                    }
                    
                    // set name display
                    self.fillTextDisplay();
                    
                    // set selected first
//                    self.selectFirst();
                });
            }
            
            /**
             * selectFirst
             */
            public selectFirst() {
                let self = this;
                self.selectedHistoryId(self.lstWpkHistory()[0].historyId);
            }
            
            /**
             * getSelectedHistoryByHistId
             */
            public getSelectedHistoryByHistId(): IHistory {
                let self = this;
                return self.lstWpkHistory().filter(item => item.historyId == self.selectedHistoryId())[0];
            }
            
            /**
             * fillTextDisplay
             */
            private fillTextDisplay() {
                let self = this;
                _.forEach(self.lstWpkHistory(), (item: IHistory) => {
                    item.textDisplay = item.startDate + " " + nts.uk.resource.getText("CMM011_26") + " " + item.endDate;
                });
            }
            
            /**
             * findHistIdByDate
             */
            public findHistIdByDate(start: string, end: string): string {
                let self = this;
                let histId: string = null;
                if (self.lstWpkHistory().length <= 0) {
                    return histId;
                }
                let result: Array<IHistory> = self.lstWpkHistory().filter(item => item.startDate == start
                    && item.endDate == end);
                if (result.length > 0) {
                    histId = result[0].historyId;
                }
                return histId;
            }
            
            /**
             * isSelectedLatestHistory
             */
            public isSelectedLatestHistory() {
                let self = this;
                if (self.lstWpkHistory().length <= 0) {
                    return false;
                }
                return self.lstWpkHistory().filter(item => item.historyId == self.selectedHistoryId()
                    && item.endDate == "9999/12/31").length > 0;
            }
        }
        
        /**
         * IHistory
         */
        export interface IHistory {
            workplaceId?: string;
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