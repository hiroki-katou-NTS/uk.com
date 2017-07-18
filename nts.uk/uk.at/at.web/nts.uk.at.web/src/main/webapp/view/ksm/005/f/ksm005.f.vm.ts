module nts.uk.at.view.ksm005.f {

    import MonthlyPatternDto = service.model.MonthlyPatternDto;
    import MonthlyPatternSettingDto = service.model.MonthlyPatternSettingDto;
    export module viewmodel {

        export class ScreenModel {
            columnMonthlyPatterns: KnockoutObservableArray<NtsGridListColumn>;
            lstMonthlyPattern: KnockoutObservableArray<MonthlyPatternDto>;
            selectMonthlyPattern: KnockoutObservable<string>;                  


           constructor() {
               var self = this;
               self.columnMonthlyPatterns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KSM005_13"), key: 'code', width: 100 },
                    { headerText: nts.uk.resource.getText("KSM005_14"), key: 'name', width: 150 }
                ]);
                var monthlyPatterns: MonthlyPatternDto[] = [];
                self.lstMonthlyPattern = ko.observableArray(monthlyPatterns);
                self.selectMonthlyPattern = ko.observable('');
           }
            /**
             * start page when init data
             */
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                service.findAllMonthlyPattern().done(function(data) {
                    self.lstMonthlyPattern(data);
                    self.selectMonthlyPattern(data[0].code);
                    dfd.resolve(self);
                });
                return dfd.promise();
            }
            /**
             * call service add MonthlyPatternSetting
             */
            public saveMonthlyPatternSetting(): void{
                var self = this;
                var dto : MonthlyPatternSettingDto;
                dto = {employeeId: "0001", monthlyPatternCode: self.selectMonthlyPattern()};
                service.addMonthlyPatternSetting(dto).done(function(){
                   console.log('YES'); 
                });
            }
        
        }

    }
}