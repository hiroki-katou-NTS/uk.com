module nts.uk.at.view.kmf002.e {
    
    import service = nts.uk.at.view.kmf002.e.service;
    import commonTableMonthDaySet1 = ;
    
    export module viewmodel {
        export class ScreenModel { 

            commonTableMonthDaySet1: KnockoutObservable<any>;
            
            
            constructor(){
                let _self = this;
                _self.commonTableMonthDaySet = new nts.uk.at.view.kmf002.viewmodel.CommonTableMonthDaySet();
                
                
                _self.commonTableMonthDaySet.fiscalYear.subscribe(function(newValue) {
                    // change year
                    $.when(_self.start_page()).done(function() {
                        console.log("find done");
                    });  
                });
                
            }
            
            public save(): void {
               let _self = this;
//               var dfd = $.Deferred<void>();
                service.save(_self.commonTableMonthDaySet.arrMonth()).done((data) => {
                    // TODO: show message 15 when success
                });
            }
            
            public deleteObj(): void {
                service.delete(_self.commonTableMonthDaySet.arrMonth()).done((data) => {
                     $.when(_self.start_page()).done(function() {
                        console.log("find done");
                    });  
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
                        // TODO: set value responsible in data into _self.arrMonth()
                    }
                    dfd.resolve();
                });
                
                service.findFirstMonth().done((data) => {
                    console.log(data);
                    dfd.resolve();
                });
            
            
                return dfd.promise();
            }
            
            
       }      
    }
}