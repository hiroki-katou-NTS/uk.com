module nts.uk.at.view.kmk008.k {
    export module viewmodel {
        export class ScreenModel {
            date: KnockoutObservable<number>;
            items: KnockoutObservableArray<ItemModel>;
            columns2: KnockoutObservableArray<NtsGridListColumn>;
            currentCode: KnockoutObservable<any>;

            yearValue: KnockoutObservable<string>;
            errorOneYear: KnockoutObservable<string>;
            alarmOneYear: KnockoutObservable<string>;
            count: number = 4;

            constructor() {
                let self = this;
                self.date = ko.observable('');
                this.items = ko.observableArray([]);
                self.yearValue = ko.observable('');
                self.errorOneYear = ko.observable('');
                self.alarmOneYear = ko.observable('');

                this.columns2 = ko.observableArray([
                    { headerText: '年度', key: 'name', width: 150 }
                ]);

                this.currentCode = ko.observable();
            }

            startPage(isReload: boolean): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                self.selectedCode = ko.observable("0000000001");

                service.getYear(self.selectedCode()).done(function(yearData: Array<model.YearDto>) {
                    if (yearData) {
                        self.item.push(new ItemModel(yearData.yearValue, yearData.errorOneYear, yearData.alarmOneYear));
                    } else {
                        self.items.push(new ItemModel("", "", ""));
                    }
                });

                dfd.resolve();
                return dfd.promise();
            }

            registerYear() {
                var self = this;
                let command = {
                    yearValue: self.yearValue(),
                    errorOneYear: self.errorOneYear(),
                    alarmOneYear: self.alarmOneYear()
                };

                service.addYear(command).done(function() {
                    self.startPage(true);
                })
            }
        }

        export class ItemModel {
            yearValue: number;
            errorOneYear: string;
            alarmOneYear: string;
            constructor(yearValue: number, errorOneYear: string, alarmOneYear: string) {
                this.yearValue = yearValue;
                this.errorOneYear = errorOneYear;
                this.alarmOneYear = alarmOneYear;
            }

        }
    }
}
