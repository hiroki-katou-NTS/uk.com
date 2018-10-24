module nts.uk.at.view.kmf002.e {
    
    import service = nts.uk.at.view.kmf002.e.service;
    import blockUI = nts.uk.ui.block;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    
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
                    if (!nts.uk.ui.errors.hasError()) {
                        $.when(_self.start_page()).done(function(data: any) {
                        });    
                    }  
                });
            }
            
            public save(): void {
                let _self = this;
                if (!nts.uk.ui.errors.hasError()) {
                    blockUI.invisible();
                    _self.enableSave(false);
                    service.save(_self.commonTableMonthDaySet().fiscalYear(), _self.commonTableMonthDaySet().arrMonth()).done((data) => {
                        _self.enableDelete(true);
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => {
                            _self.enableSave(true);
                        });
                    }).always(()=> blockUI.clear());
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
            
            /**
             * init default data when start page
             */
            public start_page(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                var _self = this;
                $.when(service.find(_self.commonTableMonthDaySet().fiscalYear()), service.findFirstMonth()).done(function(data: any, data2: any) {
                    if (typeof data === "undefined") {
                        /** 
                         *   create value null for prepare create new 
                        **/
                        _self.commonTableMonthDaySet().arrMonth.removeAll();
                        for (let i=data2.startMonth-1; i<12; i++) {
                            _self.commonTableMonthDaySet().arrMonth.push({'month': ko.observable(i+1), 'day': ko.observable(0), 'enable': ko.observable(true)});    
                        }
                        for (let i=0; i<data2.startMonth-1; i++) {
                            _self.commonTableMonthDaySet().arrMonth.push({'month': ko.observable(i+1), 'day': ko.observable(0), 'enable': ko.observable(true)});    
                        } 
                        _self.enableDelete(false);
                    } else {
                        _self.commonTableMonthDaySet().arrMonth.removeAll();
                        for (let i=data2.startMonth-1; i<12; i++) {
                            _self.commonTableMonthDaySet().arrMonth.push({'month': ko.observable(data.publicHolidayMonthSettings[i].month), 
                                                                          'day': ko.observable(data.publicHolidayMonthSettings[i].inLegalHoliday), 
                                                                          'enable': ko.observable(true)});    
                        }
                        for (let i=0; i<data2.startMonth-1; i++) {
                            _self.commonTableMonthDaySet().arrMonth.push({'month': ko.observable(data.publicHolidayMonthSettings[i].month), 
                                                                          'day': ko.observable(data.publicHolidayMonthSettings[i].inLegalHoliday), 
                                                                          'enable': ko.observable(true)});    
                        } 
                        _self.enableDelete(true);
                    }
                    dfd.resolve();       
                });
                return dfd.promise();
            }
       }      
    }
}