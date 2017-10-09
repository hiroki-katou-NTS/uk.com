module nts.uk.com.view.cmm013 {
    
    export module base {

        /**
         * JobTitleHistoryAbstract
         */
        export abstract class JobTitleHistoryAbstract {

            listJobTitleHistory: KnockoutObservableArray<History>;
            selectedJobTitleHistory: KnockoutObservable<string>;
            
            constructor() {
                let _self = this;
                _self.listJobTitleHistory = ko.observableArray([]);
                _self.selectedJobTitleHistory = ko.observable(null);
                
                _self.listJobTitleHistory.subscribe((newListHistory) => {
                    _self.fillTextDisplay();
                });
            }
            
            /**
             * selectFirst
             */
            public selectFirst() {
                let _self = this;
                if (_self.listJobTitleHistory()[0]){
                    _self.selectedJobTitleHistory(_self.listJobTitleHistory()[0].historyId);
                }               
            }
            
            /**
             * getSelectedHistoryByWkpId
             */
            public getSelectedHistoryByWkpId(): History {
                let _self = this;
                return _self.listJobTitleHistory().filter(item => item.jobTitleId == _self.selectedJobTitleHistory())[0];
            }
            
            /**
             * getSelectedHistoryByHistId
             */
            public getSelectedHistoryByHistId(historyId :string): History {
                let _self = this;
                return _self.listJobTitleHistory().filter(item => item.historyId == historyId)[0];
            }
            
            /**
             * isWorkplaceHistoryLatest
             */
            public isWorkplaceHistoryLatest(): boolean {
                let _self = this;
                return _self.selectedJobTitleHistory() == _self.listJobTitleHistory()[0].jobTitleId;
            }
            
            /**
             * fillTextDisplay
             */
            private fillTextDisplay() {
                let _self = this;
                _.forEach(_self.listJobTitleHistory(), (item: History) => {
                    item.textDisplay = item.startDate + " " + nts.uk.resource.getText('CMM013_6') + " " + item.endDate;
                })
            }
            
            /**
             * isSelectedLatestHistory
             */
            public isSelectedLatestHistory() {
                let _self = this;
                if (_self.listJobTitleHistory().length > 0) {
                    return _self.selectedJobTitleHistory() == _self.listJobTitleHistory()[0].historyId;
                }
                return false;
            }
        }
        
        /**
         * History
         */
        export class History {
            
            jobTitleId: string;
            historyId: string;
            startDate: string;
            endDate: string;
            textDisplay: string;
            
            constructor(jobTitleId: string, historyId: string, startDate: string, endDate: string, textDisplay?: string) {
                this.jobTitleId = jobTitleId;
                this.historyId = historyId;
                this.startDate = startDate;
                this.endDate = endDate;
                this.textDisplay = textDisplay;
            }          
        }    
        
        /**
         * SequenceMaster
         */
        export class SequenceMaster {

            companyId: string;                        
            sequenceCode: string;
            sequenceName: string;
            order: number;
                        
            constructor(companyId: string, sequenceCode: string, sequenceName: string, order: number) {
                this.companyId = companyId;
                this.sequenceCode = sequenceCode;
                this.sequenceName = sequenceName;
                this.order = order;
            }    
        }
        
        /**
         * SequenceMaster save command
         */
        export class SequenceMasterSaveCommand {
            
            isCreateMode: boolean;
            sequenceMasterDto: SequenceMaster;
            
            constructor(isCreateMode: boolean, sequenceMasterDto: SequenceMaster) {
                this.isCreateMode = isCreateMode;
                this.sequenceMasterDto = sequenceMasterDto;
            }
        }
        
        /**
         * SequenceMaster remove command
         */
        export class SequenceMasterRemoveCommand {
            
            sequenceCode: string;
            
            constructor(sequenceCode: string) {
                this.sequenceCode = sequenceCode;
            }
        }
    }
}