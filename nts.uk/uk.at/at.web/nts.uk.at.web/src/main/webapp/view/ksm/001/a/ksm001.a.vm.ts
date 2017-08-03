module nts.uk.at.view.ksm001.a {

    import TargetYearDto = service.model.TargetYearDto;

    export module viewmodel {

        export class ScreenModel {
            lstTargetYear: KnockoutObservableArray<TargetYearDto>;
            isCompanySelected: KnockoutObservable<boolean>;
            selectedTargetYear: KnockoutObservable<string>;
            

            constructor() {
                var self = this;
                self.lstTargetYear = ko.observableArray([]);
                self.isCompanySelected = ko.observable(true);
                self.selectedTargetYear = ko.observable('');
            }
            /**
            * start page data 
            */
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                var arrTargetYear: TargetYearDto[] = [];
                var targetOne : TargetYearDto = {code: '2017', name: 2017};
                var targetTwo : TargetYearDto = {code: '2018', name: 2018};
                arrTargetYear.push(targetOne);
                arrTargetYear.push(targetTwo);
                self.lstTargetYear(arrTargetYear);
                self.selectedTargetYear('2017');
                dfd.resolve(self);
                return dfd.promise();
            }
            
            
        }
    }
}