module nts.uk.at.view.kmf002.e {
    
    import service = nts.uk.at.view.kmf002.e.service;
    export module viewmodel {
        export class ScreenModel {
            commonTableMonthDaySet: KnockoutObservable<nts.uk.at.view.kmf002.viewmodel.CommonTableMonthDaySet>;
            enableSave: KnockoutObservable<boolean>;
            enableDelete: KnockoutObservable<boolean>;
            
            constructor(){
                let _self = this;
                _self.enableSave = ko.observable(true);
                _self.enableDelete= ko.observable(true);
                _self.commonTableMonthDaySet = ko.observable(new nts.uk.at.view.kmf002.viewmodel.CommonTableMonthDaySet());
                _self.commonTableMonthDaySet().fiscalYear.subscribe(function(newValue) {
                    // change year
                    $.when(_self.start_page()).done(function() {
                    });  
                });
            }
            
            public save(): void {
                let _self = this;
//                _self.validateInput();
                if (!nts.uk.ui.errors.hasError()) {
                    service.save(_self.commonTableMonthDaySet().fiscalYear(), _self.commonTableMonthDaySet().arrMonth()).done((data) => {
                        _self.enableDelete(true);
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    });
                }
            }
            
            public deleteObj(): void {
                let _self = this;
                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                    service.remove(_self.commonTableMonthDaySet().fiscalYear()).done((data) => {
                     $.when(_self.start_page()).done(function() {
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                    });  
                });
                }).ifNo(() => {
                }).ifCancel(() => {
                }).then(() => {
                });   
            }
            
            private validateInput(): void {
                $('.validateInput').ntsEditor("validate");        
            }
            
            /**
             * init default data when start page
             */
            public start_page(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                var _self = this;
                service.find(_self.commonTableMonthDaySet().fiscalYear()).done((data) => {
                    if (typeof data === "undefined") {
                        /** 
                         *   create value null for prepare create new 
                        **/
                        _.forEach(_self.commonTableMonthDaySet().arrMonth(), function(value: any) {
                            value.day(0);
                        });
                        _self.enableDelete(false);
                    } else {
                        for (let i=0; i<data.publicHolidayMonthSettings.length; i++) {
                            _self.commonTableMonthDaySet().arrMonth()[i].day(data.publicHolidayMonthSettings[i].inLegalHoliday);
                        }
                        _self.enableDelete(true);
                    }
                    dfd.resolve();
                });
                
                nts.uk.ui.errors.clearAll();
            
                return dfd.promise();
            }
            
            
       }      
    }
}