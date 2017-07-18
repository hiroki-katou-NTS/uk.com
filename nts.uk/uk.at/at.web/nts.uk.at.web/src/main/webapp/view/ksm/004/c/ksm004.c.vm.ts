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
                self.selectHolidayByIndex(0);
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
                self.date(moment(value.date + self.year(), "MM月DD日YYYY").format('YYYY/MM/DD'));
                self.holidayName(value.holidayName);
            });
            self.isCreate = ko.observable(null);
            self.isCreate.subscribe((value) => {
                self.changeInitMode(value);
            });
        }

        /** Start Page */
        startPage(): JQueryPromise<any> {
            var dfd = this.getAllData();
            var self = this;
            block.invisible();
            dfd.done(() => {
                block.clear();
                self.year(2000);
                self.selectHolidayByIndex(0);
            });
            return dfd;
        }

        /** Get all data from server */
        private getAllData(): JQueryPromise<any> {
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

        private reloadData(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.getAllData().done(() => {
                self.findPublicHolidayByYear(self.year());
                dfd.resolve();
            });
            return dfd.promise();
        }

        private findPublicHolidayByYear(year: number) {
            var self = this;
            var filterHolidays = _.filter(self.allHolidays(), (item) => {
                return item.date.toString().substr(0, 4) == year.toString();
            }).map((holiday) => {
                return { date: moment(holiday.date, 'YYYYMMDD').format('MM月DD日'), holidayName: holiday.holidayName };
            });
            self.filterHolidays(filterHolidays);
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
                        self.selectByCode(publicHoliday.date);
                    });
                }).fail((res) => {
                    nts.uk.ui.dialog.alert({ messageId: "Msg_132" });
                }).always(() => {
                });
            }
            else {
                service.updatePublicHoliday(publicHoliday).done((data) => {
                    nts.uk.ui.dialog.alert({ messageId: "Msg_15" });
                    self.reloadData().done(() => {
                        self.selectByCode(publicHoliday.date);
                    });
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
                    service.deletePublicHoliday(self.date()).done(() => {
                        var index = _.findIndex(self.filterHolidays(), (item) => {
                            return item.date == self.currentCode();
                        });
                        index = _.min([self.filterHolidays().length - 2, index]);
                        self.reloadData().done(() => {
                            self.selectHolidayByIndex(index);
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
        /** Cancle Dialog */
        cancel_Dialog(): any {
            nts.uk.ui.windows.close();
        }
        /*OpenDialog Rename holiday by Google API (KSM 004 E) */
        holidayData() {
            var self = this;
            nts.uk.ui.windows.setShared("currentCode", self.currentCode());
            nts.uk.ui.windows.setShared("filterHoliday", self.filterHolidays());
            nts.uk.ui.windows.sub.modal("/view/ksm/004/e/index.xhtml", { title: nts.uk.resource.getText("Googleデータ確認") }).onClosed(() => {
            });
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