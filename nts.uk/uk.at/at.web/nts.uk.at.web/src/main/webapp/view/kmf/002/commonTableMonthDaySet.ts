module nts.uk.at.view.kmf002 {
    
    import service = nts.uk.at.view.kmf002.e.service;
    
    export module viewmodel {
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
                for (let i=4; i<=12; i++) {
                    _self.arrMonth.push({'month': ko.observable(i), 'day': ko.observable(''), 'enable': ko.observable(true)});
                }
                
                for (let i=1; i<=3; i++) {
                    _self.arrMonth.push({'month': ko.observable(i), 'day': ko.observable(''), 'enable': ko.observable(true)});
                }
                
                // Define styles
               _self.cssRangerY = [];
               _self.cssRangerYM = {};
               _self.cssRangerYMD = {};
                
                _.forEach(_self.arrMonth(), function(newValue) {
                    newValue.day.subscribe(function(newValue) {
                    });
                });   
            }
       }      
    }
}