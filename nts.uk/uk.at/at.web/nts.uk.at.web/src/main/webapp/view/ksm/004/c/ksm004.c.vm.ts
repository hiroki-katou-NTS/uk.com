module nts.uk.at.view.ksm004.c.viewmodel {
    import block = nts.uk.ui.block;
    export class ScreenModel {
        year: KnockoutObservable<number>;
        // Grid list
        allHolidays: KnockoutObservableArray<PublicHoliday>;
        filterHolidays: KnockoutObservableArray<PublicHoliday>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentCode: KnockoutObservable<any>;
        //text editor
        date: KnockoutObservable<string>;
        holidayName: KnockoutObservable<string>;

        // TitleMenu Details
        selectedPublicHoliday: KnockoutObservable<PublicHoliday>;
        isCreate: KnockoutObservable<boolean>;
        constructor() {
            var self = this;
            self.year = ko.observable(null);
            self.year.subscribe((newValue) => {
                self.findPublicHolidayByYear(newValue);
            });

            //Grid List
            self.allHolidays = ko.observableArray([]);
            self.filterHolidays = ko.observableArray([]);
            self.currentCode = ko.observable(null);
            self.currentCode.subscribe((value: string) => {
                self.findPublicHoliday(value);
            });
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KSM004_23"), key: 'date', width: 85 },
                { headerText: nts.uk.resource.getText("KSM004_24"), key: 'holidayName', width: 240 }
            ]);
            // text editor
            self.date = ko.observable("");
            self.holidayName = ko.observable("");

            // Holiday Details
            self.selectedPublicHoliday = ko.observable(null);
            self.selectedPublicHoliday.subscribe((value: PublicHoliday) => {
                self.date(value.date);
                self.holidayName(value.holidayName);
            });
            self.isCreate = ko.observable(null);
            self.isCreate.subscribe((value) => {
                self.changeInitMode(value);
            });
        }



        /** Start Page */
        startPage(): JQueryPromise<any> {
            var dfd = this.reloadData();
            var self = this;
            block.invisible();
            dfd.done(() => {
                block.clear();
                self.year(2000);
                self.selectHolidayByIndex(0);
            });
            return dfd;
        }






        /*    startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                service.getHolidayByDate().done(function(listPublicHoliday: Array<viewmodel.PublicHoliday>) {
                    listPublicHoliday = _.orderBy(listPublicHoliday, ["date"], ["asc"]);
                    self.allHolidays(listPublicHoliday);
                    self.year(2000);
                    self.selectHolidayByIndex(0);
                    dfd.resolve();
                }).fail(function(error) {
                    dfd.fail();
                    alert(error.message);
                });          return dfd.promise();
            }  */

        /** Reload data from server */
        private reloadData(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            /** Get list TitleMenu*/
            service.getHolidayByDate().done(function(listPublicHoliday: Array<viewmodel.PublicHoliday>) {
                listPublicHoliday = _.orderBy(listPublicHoliday, ["date"], ["asc"]);
                self.allHolidays(listPublicHoliday);
                if (listPublicHoliday.length > 0) {
                    self.isCreate(false);
                }
                else {
                    self.findPublicHoliday(null);
                    self.isCreate(true);
                }
                dfd.resolve();
            }).fail(function(error) {
                dfd.fail();
                alert(error.message);
            });
            return dfd.promise();
        }

        private findPublicHolidayByYear(year: number) {
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

        /** Select Holiday by Code: Create & Update case*/
        private selectByCode(code: string) {
            this.currentCode(code);
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
            self.selectedPublicHoliday(new PublicHoliday("", ""));
            $("#date").focus();

        }
        /** Registry Button Click */
        registryButtonClick() {
            var self = this;
            $(".nts-input").trigger("validate");
            var publicHoliday = {
                date: self.date(),
                holidayName: self.holidayName()
            };
            if (self.isCreate() === true) {

                service.createPublicHoliday(publicHoliday).done((data) => {
                    nts.uk.ui.dialog.alert({ messageId: "Msg_15" });
                    self.reloadData().done(() => {
                    }); 
                }).fail((res) => {
                    nts.uk.ui.dialog.alert({ messageId: "Msg_132" });
                }).always(() => {
                });
            }
            else {
                service.updatePublicHoliday(publicHoliday).done((data) => {
                    nts.uk.ui.dialog.alert({ messageId: "Msg_15" });
                    console.log(data);
                    self.reloadData();
                }).always(() => {
                });
            }
        }
        
         /**Delete Button Click */
        removeTitleMenu() {
            var self = this;
            if (self.currentCode() !== null) {
                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(function() {
                    block.invisible();
                    service.deletePublicHoliday(self.currentCode()).done(() => {
                        self.reloadData().done(() => {
                            nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                        });
                    }).fail((res) => {
                        nts.uk.ui.dialog.alertError({ messageId: res.messageId });
                    }).always(() => {
                        block.clear();
                    });
                })
            }
        }
        /** Get Selected PublicHoliday */
        private findPublicHoliday(value: any) {
            var self = this;
            var selectedPublicHoliday = _.find(self.filterHolidays(), (item) => {
                return item.date == value;
            });
            if (selectedPublicHoliday !== undefined) {
                self.isCreate(false);
                self.selectedPublicHoliday(new PublicHoliday(selectedPublicHoliday.date, selectedPublicHoliday.holidayName));

            }
            else {
                self.isCreate(true);
                self.selectedPublicHoliday(new PublicHoliday("", ""));
            }
        }

    }
    export class PublicHoliday {
        date: string;
        holidayName: string;

        constructor(date: string, holidayName: string) {
            this.date = date;
            this.holidayName = holidayName;

        }
    }
} 