module nts.uk.at.view.ksm005.f {

    import MonthlyPatternDto = service.model.MonthlyPatternDto;
    export module viewmodel {

        export class ScreenModel {
            yearMonth: KnockoutObservable<number>;                  


           constructor() {
               var self = this;
               self.yearMonth = ko.observable(201705);
           }
        
        }

    }
}