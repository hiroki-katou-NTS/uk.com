module nts.uk.at.view.ksm004.c.viewmodel {
    export class ScreenModel {
        year: KnockoutObservable<number>;
        // Grid list
        allHolidays: KnockoutObservableArray<HolidayInfo>;
        filterHolidays: KnockoutObservableArray<HolidayInfo>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentCode: KnockoutObservable<any>;
        //text editor
        simpleValue1: KnockoutObservable<string>;
        holidayName: KnockoutObservable<string>;
        constructor() {
            var self = this;
            self.year = ko.observable(null); 
            self.year.subscribe((newValue) => {
                self.findHolidayInfoByYear(newValue);
            });
            //Grid List
            self.allHolidays = ko.observableArray([]);
            self.filterHolidays = ko.observableArray([]);
            self.currentCode = ko.observable();
            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'date', width: 100 },
                { headerText: '名称', key: 'name', width: 150 }
            ]);
            // text editor
            self.simpleValue1 = ko.observable("");
            self.holidayName = ko.observable("");
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            service.getHolidayByDate().done(function(listHolidayInfo: Array<viewmodel.HolidayInfo>) {
                listHolidayInfo = _.orderBy(listHolidayInfo, ["date"], ["asc"]);
                self.allHolidays(listHolidayInfo);
                self.year(2000);
                self.selectHolidayByIndex(0);
                dfd.resolve();
            }).fail(function(error) {
                dfd.fail();
                alert(error.message);
            });
            return dfd.promise();
        }
        
        private findHolidayInfoByYear(year: number) {
            var self = this;
            var filterHolidays = _.filter(self.allHolidays(), (item) => {
                return item.date.toString().substr(0,4) == year.toString();
            });
            self.filterHolidays(filterHolidays);
            self.selectHolidayByIndex(0);
        }
        

        /** Select Holiday by Index: Start & Delete case */
        private selectHolidayByIndex(index: number) {
            var self = this;
            var selectHolidayByIndex = _.nth(self.filterHolidays(), index);
            if (selectHolidayByIndex !== undefined)
                self.currentCode(selectHolidayByIndex.date);
            else
                self.currentCode(null);
        }
        
    }
    export class HolidayInfo {
        date: string;
        name: string;

        constructor(date: string, name: string) {
            this.date = date;
            this.name = name;

        }
    }
} 