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
             * getSelectedHistoryByJobTitleId
             */
            public getSelectedHistoryByJobTitleId(): History {
                let _self = this;
                return _self.listJobTitleHistory().filter(item => item.jobTitleId == _self.selectedJobTitleHistory())[0];
            }
            
            /**
             * getSelectedHistoryByHistoryId
             */
            public getSelectedHistoryByHistoryId(historyId :string): History {
                let _self = this;
                return _self.listJobTitleHistory().filter(item => item.historyId == historyId)[0];
            }
            
            /**
             * isJobTitleHistoryLatest
             */
            public isJobTitleHistoryLatest(): boolean {
                let _self = this;
                return _self.selectedJobTitleHistory() == _self.listJobTitleHistory()[0].historyId;
            }
            
            /**
             * fillTextDisplay
             */
            private fillTextDisplay() {
                let _self = this;
                _.forEach(_self.listJobTitleHistory(), (item: History) => {
                    item.textDisplay = item.period.startDate + " ～ " + item.period.endDate;
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
            period: Period;
            textDisplay: string;
            
            constructor(jobTitleId: string, historyId: string, period: Period, textDisplay?: string) {
                this.jobTitleId = jobTitleId;
                this.historyId = historyId;
                this.period = period;
                this.textDisplay = textDisplay;
            }          
        }    
        
        /**
         * Period
         */
        export class Period {
            
            startDate: string;
            endDate: string;
            
            constructor(startDate: string, endDate: string){
                this.startDate = startDate;
                this.endDate = endDate;
            }
        }
        
        /**
         * JobTitle
         */
        export class JobTitle {
            
            companyId: string;
            jobTitleId: string;
            
            constructor(companyId: string, jobTitleId: string) {
                this.companyId = companyId;
                this.jobTitleId = jobTitleId;
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