module nts.uk.at.view.kmf002 {
    
    import service = nts.uk.at.view.kmf002.e.service;
    
    export module viewmodel {
        
        var path: any = {
                findFirstMonth: "at/shared/holidaysetting/companycommon/getFirstMonth",
                findHolidayConfig: "at/shared/holidaysetting/config/find"
            };
        
        export class CommonTableMonthDaySet { 
        
            cssRangerY: any;
            cssRangerYM: any;
            cssRangerYMD: any;

            inLegalHoliday: KnockoutObservable<number>;
            publicHDManageYear: KnockoutObservable<number>;
            month: KnockoutObservable<number>;
            
            dateString: KnockoutObservable<string>;
            date: KnockoutObservable<any>;
            yearMonth: KnockoutObservable<number>;
            year: KnockoutObservable<number>;
            fiscalYear: KnockoutObservable<string>;
            arrMonth: KnockoutObservableArray<any>;
            
            visibleInfoSelect: KnockoutObservable<boolean>;
            infoSelect1: KnockoutObservable<string>;
            infoSelect2: KnockoutObservable<string>;
            infoSelect3: KnockoutObservable<string>;
            isPeriod: KnockoutObservable<boolean>;
            
            constructor(){
                let self = this;
                
                self.visibleInfoSelect = ko.observable(false);
                self.infoSelect1 = ko.observable("");
                self.infoSelect2 = ko.observable("");
                self.infoSelect3 = ko.observable("");
                
                self.fiscalYear = ko.observable(moment().format('YYYY'));
                self.arrMonth = ko.observableArray([]);

                self.isPeriod = ko.observable(true);
                
                $.when(self.findFirstMonth(), self.getHolidayConfig()).done(function(data: any, data1: any) {
                    if (_.isNull(data.startMonth)) {
                            data.startMonth = 1;
                        }
                    for (let i=data.startMonth; i<=12; i++) {
                        self.arrMonth.push({'month': ko.observable(i), 'day': ko.observable(0), 'enable': ko.observable(true)});
                    }
                    
                    for (let i=1; i<data.startMonth; i++) {
                        self.arrMonth.push({'month': ko.observable(i), 'day': ko.observable(0), 'enable': ko.observable(true)});
                    }
                    if(!_.isUndefined(data1) && !_.isNull(data1) && !_.isEmpty(data1)){
                        self.isPeriod(data1.publicHolidayPeriod === 1 ? true: false);
                    }                    
                });
                
                // Define styles
               self.cssRangerY = [];
               self.cssRangerYM = {};
               self.cssRangerYMD = {};
                
                _.forEach(self.arrMonth(), function(newValue: any) {
                    newValue.day.subscribe(function(newValue) {
                    });
                });   
            }
            
            private findFirstMonth() :JQueryPromise<any> {
                return nts.uk.request.ajax("at", path.findFirstMonth); 
            }

            public getHolidayConfig() : JQueryPromise<any> {
                return nts.uk.request.ajax("at", path.findHolidayConfig);
            }
       }      

    }
}