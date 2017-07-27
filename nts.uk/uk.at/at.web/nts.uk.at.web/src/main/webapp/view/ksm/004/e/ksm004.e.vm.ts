module nts.uk.at.view.ksm004.e.viewmodel {
    //Google API
    var CALENDAR_ID = "japanese__ja@holiday.calendar.google.com";
    var API_KEY = "AIzaSyDGAO8P6_nowiXAZ3rgHE44ZRPgP_E_qus";

    export class ScreenModel {
        //Grid List
        filterHolidays: KnockoutObservableArray<PublicHoliday>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentCode: KnockoutObservable<any>;
        //check box
        checkOverwritting: KnockoutObservable<boolean>;
        year: KnockoutObservable<any>;

        constructor() {
            var self = this;
            //Grid List
            self.filterHolidays = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: '', key: 'id', width: 85, hidden: true },
                { headerText: nts.uk.resource.getText("KSM004_30"), key: 'date', width: 85, template: "{{if ((${holidayName} != '') && (${holidayName} != ${holidayNamebyGoogle})) }}<div class='duplicate-row'>${date}</div>{{else}} ${date} {{/if}}" },
                { headerText: nts.uk.resource.getText("KSM004_31"), key: 'holidayName', width: 240, template: "{{if ((${holidayName} != '') && (${holidayName} != ${holidayNamebyGoogle})) }}<div class='duplicate-row'>${holidayName}</div>{{else}} ${holidayName} {{/if}}" },
                { headerText: nts.uk.resource.getText("KSM004_32"), key: 'holidayNamebyGoogle', width: 240, template: "{{if ((${holidayName} != '') && (${holidayName} != ${holidayNamebyGoogle})) }}<div class='duplicate-row'>${holidayNamebyGoogle}</div>{{else}} ${holidayNamebyGoogle} {{/if}}" }
            ]);
            self.currentCode = ko.observable(null);
            //Check box
            self.checkOverwritting = ko.observable(false);
            //Year
            self.year = ko.observable(null);
            self.year = nts.uk.ui.windows.getShared("yearPicker");
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            var publicHoliday = nts.uk.ui.windows.getShared("filterHoliday");
            
            $.getJSON("https://www.googleapis.com/calendar/v3/calendars/" + CALENDAR_ID + "/events?key=" + API_KEY).done((lstHolidayGG) => {
                for (let id = 0; id < lstHolidayGG.items.length; id++) {
                    self.filterHolidays.push(new PublicHoliday(id, moment(lstHolidayGG.items[id].start.date, 'YYYY-MM-DD').format('MM月DD日'), '', lstHolidayGG.items[id].summary));
                };
            }).then(() => {
                _.forEach(publicHoliday, function(item: PublicHoliday) {
                    var dupplicateItem = _.find(self.filterHolidays(), (ggItem) => {
                        return ggItem.date == item.date;
                    });
                    if (dupplicateItem) {
                        dupplicateItem.holidayName = item.holidayName;
                    }
                });
                dfd.resolve();
            });
            return dfd.promise();
        }
        /** Cancel Dialog */
        cancel_Dialog(): any {
            nts.uk.ui.windows.close();
        }
        submit(){
            var self = this;
            
            }
    }
    export class PublicHoliday {
        id: number;
        date: string;
        holidayName: string;
        holidayNamebyGoogle: string
        constructor(id: number, date: string, holidayName: string, holidayNamebyGoogle: string) {
            this.id = id;
            this.date = date;
            this.holidayName = holidayName;
            this.holidayNamebyGoogle = holidayNamebyGoogle;
        }
    }
}