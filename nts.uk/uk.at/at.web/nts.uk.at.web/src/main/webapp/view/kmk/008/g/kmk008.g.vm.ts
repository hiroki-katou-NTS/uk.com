module nts.uk.at.view.kmk008.g {
    export module viewmodel {
        export class ScreenModel {
            tabs: KnockoutObservableArray<NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;

            items: KnockoutObservableArray<ItemModel>;
            columns: KnockoutObservableArray<NtsGridListColumn>;
            currentCode: KnockoutObservable<any>;

            selectedCode: KnockoutObservable<string>

            items2: KnockoutObservableArray<ItemModel>;
            currentCode2: KnockoutObservable<any>;

            isNewMode: KnockoutObservable<boolean>;
            isUpdateMode: KnockoutObservable<boolean>;

            constructor() {
                let self = this;
                self.isNewMode = ko.observable(true);
                self.isUpdateMode = ko.observable(false);
                self.isNewMode.subscribe(function(val) {
                    self.isUpdateMode(!val);
                });

                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: '年度', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: '年月', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                self.selectedTab = ko.observable('tab-1');

                self.items = ko.observableArray([]);
                self.items2 = ko.observableArray([]);

                self.columns = ko.observableArray([
                    { headerText: '年度', key: 'year', width: 100 },
                    { headerText: 'エラー', key: 'error', width: 150 },
                    { headerText: 'アラーム', key: 'alarm', width: 150 }
                ]);
                self.currentCode = ko.observable();
                self.currentCode2 = ko.observable();

                self.selectedCode = ko.observable("0000000001");

            }
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();

                service.getMonth(self.selectedCode()).done(function(monthData: Array<model.MonthDto>) {
                    if (monthData.length > 0) {
                        self.items2.push(new ItemModel(monthData.yearMonthValue, monthData.errorOneMonth, monthData.alarmOneMonth));
                    } else {
                        self.items2.push(new ItemModel("", "", ""));
                    }
                });

                service.getYear(self.selectedCode()).done(function(yearData: Array<model.YearDto>) {
                    if (yearData.length > 0) {
                        self.item.push(new ItemModel(yearData.yearValue, yearData.errorOneYear, yearData.alarmOneYear));
                    } else {
                        self.items.push(new ItemModel("", "", ""));
                    }
                });

                dfd.resolve();
                return dfd.promise();
            }

            setNewMode() {
                var self = this;
                self.isNewMode(true);
            }
        }

        export class ItemModel {
            year: string;
            error: string;
            alarm: string;
            constructor(year: string, error: string, alarm: string) {
                this.year = year;
                this.error = error;
                this.alarm = alarm;
            }
        }

    }
}
