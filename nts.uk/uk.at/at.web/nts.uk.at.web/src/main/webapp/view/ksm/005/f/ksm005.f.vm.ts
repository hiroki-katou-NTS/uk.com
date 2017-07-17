module nts.uk.at.view.ksm005.f {

    import MonthlyPatternDto = service.model.MonthlyPatternDto;
    export module viewmodel {

        export class ScreenModel {
            columnMonthlyPatterns: KnockoutObservableArray<NtsGridListColumn>;
            lstMonthlyPattern: KnockoutObservableArray<MonthlyPatternDto>;
            selectMonthlyPattern: KnockoutObservable<string>;                  


           constructor() {
               var self = this;
               self.columnMonthlyPatterns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KSM005_13"), key: 'monthlyPatternCode', width: 100 },
                    { headerText: nts.uk.resource.getText("KSM005_14"), key: 'monthlyPatternName', width: 150 }
                ]);
                var monthlyPatterns: MonthlyPatternDto[] = [];
                var monthlyPatternDto001: MonthlyPatternDto = { monthlyPatternCode: '001', monthlyPatternName: '001' };
                var monthlyPatternDto002: MonthlyPatternDto = { monthlyPatternCode: '002', monthlyPatternName: '002' };
                var monthlyPatternDto003: MonthlyPatternDto = { monthlyPatternCode: '003', monthlyPatternName: '003' };
                var monthlyPatternDto004: MonthlyPatternDto = { monthlyPatternCode: '004', monthlyPatternName: '004' };
                var monthlyPatternDto005: MonthlyPatternDto = { monthlyPatternCode: '005', monthlyPatternName: '005' };
                var monthlyPatternDto006: MonthlyPatternDto = { monthlyPatternCode: '006', monthlyPatternName: '006' };
                var monthlyPatternDto007: MonthlyPatternDto = { monthlyPatternCode: '007', monthlyPatternName: '007' };
                var monthlyPatternDto008: MonthlyPatternDto = { monthlyPatternCode: '008', monthlyPatternName: '008' };
                var monthlyPatternDto009: MonthlyPatternDto = { monthlyPatternCode: '009', monthlyPatternName: '009' };
                var monthlyPatternDto010: MonthlyPatternDto = { monthlyPatternCode: '010', monthlyPatternName: '010' };
                monthlyPatterns.push(monthlyPatternDto001);
                monthlyPatterns.push(monthlyPatternDto002);
                monthlyPatterns.push(monthlyPatternDto003);
                monthlyPatterns.push(monthlyPatternDto004);
                monthlyPatterns.push(monthlyPatternDto005);
                monthlyPatterns.push(monthlyPatternDto006);
                monthlyPatterns.push(monthlyPatternDto007);
                monthlyPatterns.push(monthlyPatternDto008);
                monthlyPatterns.push(monthlyPatternDto009);
                monthlyPatterns.push(monthlyPatternDto010);
                self.lstMonthlyPattern = ko.observableArray(monthlyPatterns);
                self.selectMonthlyPattern = ko.observable(monthlyPatternDto001.monthlyPatternCode);
           }
        
        }

    }
}