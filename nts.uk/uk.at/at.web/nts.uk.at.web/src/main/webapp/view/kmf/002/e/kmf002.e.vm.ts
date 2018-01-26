module nts.uk.at.view.kmf002.e {
    
    import service = nts.uk.at.view.kmf002.e.service;
    
    export module viewmodel {
        export class ScreenModel {
            commonTableMonthDaySet: KnockoutObservable<any>;
            
            constructor(){
                let _self = this;
                _self.commonTableMonthDaySet = new nts.uk.at.view.kmf002.viewmodel.CommonTableMonthDaySet();
                _self.commonTableMonthDaySet.fiscalYear.subscribe(function(newValue) {
                    // change year
                    $.when(_self.start_page()).done(function() {
                    });  
                });
            }
            
            public save(): void {
               let _self = this;
//               var dfd = $.Deferred<void>();
                service.save(_self.commonTableMonthDaySet.fiscalYear(), _self.commonTableMonthDaySet.arrMonth()).done((data) => {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                });
            }
            
            public deleteObj(): void {
                let _self = this;
                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                    service.remove(_self.commonTableMonthDaySet.fiscalYear()).done((data) => {
                     $.when(_self.start_page()).done(function() {
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                    });  
                });
                }).ifNo(() => {
                }).ifCancel(() => {
                }).then(() => {
                });   
            }
            
            /**
             * init default data when start page
             */
            public start_page(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                var _self = this;
                service.find(_self.commonTableMonthDaySet.fiscalYear()).done((data) => {
                    if (typeof data === "undefined") {
                        /** 
                         *   create value null for prepare create new 
                        **/
                        _.forEach(_self.commonTableMonthDaySet.arrMonth(), function(value) {
                            value.day('');
                        });
                    } else {
                        for (let i=0; i<data.publicHolidayMonthSettings.length; i++) {
                            _self.commonTableMonthDaySet.arrMonth()[i].day(data.publicHolidayMonthSettings[i].inLegalHoliday);
                        }
                    }
                    dfd.resolve();
                });
                
                service.findFirstMonth().done((data) => {
                    dfd.resolve();
                });
                
                nts.uk.ui.errors.clearAll();
            
                return dfd.promise();
            }
            
            
       }      
    }
}