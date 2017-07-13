module nts.uk.at.view.ksm004.c.viewmodel {
    export class ScreenModel {
        year: KnockoutObservable<number>;
        // Grid list
        allHolidays: KnockoutObservableArray<HolidayInfo>;
        filterHolidays: KnockoutObservableArray<HolidayInfo>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentCode: KnockoutObservable<any>;
        //text editor
        date: KnockoutObservable<string>;
        name: KnockoutObservable<string>;

        // TitleMenu Details
        selectedHolidayInfo: KnockoutObservable<HolidayInfo>;
        isCreate: KnockoutObservable<boolean>;
        constructor() {
            var self = this;
            self.year = ko.observable(null);
            self.year.subscribe((newValue) => {
                self.findHolidayInfoByYear(newValue);
            });

            //Grid List
            self.allHolidays = ko.observableArray([]);
            self.filterHolidays = ko.observableArray([]);
            self.currentCode = ko.observable(null);
            self.currentCode.subscribe((value: string) => {
                self.findHolidayInfo(value);
            });
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KSM004_23"), key: 'date', width: 100 },
                { headerText: nts.uk.resource.getText("KSM004_24"), key: 'name', width: 150 }
            ]);
            // text editor
            self.date = ko.observable("");
            self.name = ko.observable("");

            // Holiday Details
            self.selectedHolidayInfo = ko.observable(null);
            self.selectedHolidayInfo.subscribe((value: HolidayInfo) => {
                self.date(value.date);
                self.name(value.name);
            });
            self.isCreate = ko.observable(null);
            self.isCreate.subscribe((value) => {
                self.changeInitMode(value);
            });
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
                return item.date.toString().substr(0, 4) == year.toString();
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

        /** Init Mode */
        private changeInitMode(isCreate: boolean) {
            var self = this;
            if (isCreate === true) {
                self.currentCode(null);
            }
        }

        /** Create Button Click */
        createButtonClick() {
            var self = this;
            self.isCreate(true);
            self.currentCode(null);
            self.selectedHolidayInfo(new HolidayInfo("", ""));
            $("#date").focus();

        }
        /** Get Selected HolidayInfo */
        private findHolidayInfo(value: any) {
            var self = this;
            var selectedHolidayInfo = _.find(self.filterHolidays(), (item) => {
                return item.date == value;
            });
            if (selectedHolidayInfo !== undefined) {
                self.isCreate(false);
                self.selectedHolidayInfo(new HolidayInfo(selectedHolidayInfo.date, selectedHolidayInfo.name));

            }
            else {
                self.isCreate(true);
                self.selectedHolidayInfo(new HolidayInfo("", ""));
            }
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