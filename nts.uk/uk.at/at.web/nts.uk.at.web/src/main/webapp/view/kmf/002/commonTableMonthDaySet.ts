module nts.uk.at.view.kmf002 {
    
    import service = nts.uk.at.view.kmf002.e.service;
    
    export module viewmodel {
        
        var path: any = {
                findFirstMonth: "basic/company/beginningmonth/find"
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
            
            constructor(){
                let _self = this;
                
                _self.visibleInfoSelect = ko.observable(false);
                _self.infoSelect1 = ko.observable("");
                _self.infoSelect2 = ko.observable("");
                _self.infoSelect3 = ko.observable("");
                
                _self.fiscalYear = ko.observable(moment().format('YYYY'));
                _self.arrMonth = ko.observableArray([]);
                
                $.when(_self.findFirstMonth()).done(function(data: any) {
                    if (_.isEmpty(data)) {
                        data.startMonth = 0;
                    }
                    for (let i=data.startMonth; i<=12; i++) {
                        _self.arrMonth.push({'month': ko.observable(i), 'day': ko.observable(0), 'enable': ko.observable(true)});
                    }
                    
                    for (let i=1; i<data.startMonth; i++) {
                        _self.arrMonth.push({'month': ko.observable(i), 'day': ko.observable(0), 'enable': ko.observable(true)});
                    } 
                });
                
                // Define styles
               _self.cssRangerY = [];
               _self.cssRangerYM = {};
               _self.cssRangerYMD = {};
                
                _.forEach(_self.arrMonth(), function(newValue: any) {
                    newValue.day.subscribe(function(newValue) {
                    });
                });   
            }
            
            private findFirstMonth() :JQueryPromise<any> {
                return nts.uk.request.ajax("com", path.findFirstMonth); 
            }
       }      
    }
}