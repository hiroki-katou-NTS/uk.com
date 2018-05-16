module nts.uk.at.view.kdm001.k {
    export module viewmodel {
        export class ScreenModel {
            items: KnockoutObservableArray<ItemModel>;
            columns: KnockoutObservableArray<NtsGridListColumn>;
            currentCode: KnockoutObservable<any>;
            currentCodeList: KnockoutObservableArray<any>;

            workCode: KnockoutObservable<string> = ko.observable('');
            workPlaceName: KnockoutObservable<string> = ko.observable('');
            employeeCode: KnockoutObservable<string> = ko.observable('');
            employeeName: KnockoutObservable<string> = ko.observable('');
            dateHoliday: KnockoutObservable<any> = ko.observable('');
            numberDay: KnockoutObservable<any> = ko.observable('');
            residualDay: KnockoutObservable<any> = ko.observable('');
            constructor() {
                var self = this;
                self.columns = ko.observableArray([
                    { headerText: 'コード', key: 'code', width: 100, hidden: true },
                    { headerText: nts.uk.resource.getText("KDM001_95"), key: 'date', width: 100 },
                    { headerText: nts.uk.resource.getText("KDM001_96"), key: 'useNumberDay', width: 100 }
                ]);
                self.items = ko.observableArray([]);
                self.initScreen();
                self.currentCode = ko.observable();
                self.currentCodeList = ko.observableArray([]);

            }

            public initScreen(): void {
                var self = this;
                self.workCode('100');
                self.workPlaceName('営業部');
                self.employeeCode('A000001');
                self.employeeName('日通　太郎');
                self.dateHoliday('2016/10/2');
                self.numberDay('0.5日');
                self.residualDay('0日');

                for (let i = 1; i < 100; i++) {
                    self.items.push(new ItemModel('00' + i, "2010/1/10", "1.0F"));
                }



            }
        }

        class ItemModel {
            code: string;
            date: string;
            useNumberDay: string;
            constructor(code: string, date: string, useNumberDay?: string) {
                this.code = code;
                this.date = date;
                this.useNumberDay = useNumberDay;

            }
        }
    }
}