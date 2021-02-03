module nts.uk.at.view.kml001.d {
    export module viewmodel {
        import servicebase = kml001.shr.servicebase;
        import vmbase = kml001.shr.vmbase;
        export class ScreenModel extends ko.ViewModel {
            isUpdate: KnockoutObservable<boolean>;
            deleteAble: KnockoutObservable<boolean>;
            size: number;
            personCostList: KnockoutObservableArray<any>;
            currentPersonCost: KnockoutObservable<vmbase.PersonCostCalculation>;
            isLast: KnockoutObservable<boolean>;
            beforeStartDate: KnockoutObservable<string>;
            onedayAfterBeforeStartDate: KnockoutObservable<string>;
            currentEndDate: KnockoutObservable<string>;
            newStartDate: KnockoutObservable<string>;
            constructor() {
                super();
                var self = this;
                /* self.personCostList = ko.observableArray(
                    _.map(nts.uk.ui.windows.getShared('personCostList'), (item : vmbase.PersonCostCalculationInterface) => { return vmbase.ProcessHandler.fromObjectPerconCost(item); })
                ); */

                self.personCostList = ko.observableArray(nts.uk.ui.windows.getShared('personCostList'));
                self.currentPersonCost = ko.observable(
                    vmbase.ProcessHandler.fromObjectPerconCost(nts.uk.ui.windows.getShared('currentPersonCost'))
                );

                self.size = _.size(self.personCostList());
                self.isLast = ko.observable((_.findIndex(self.personCostList(), function (o) { return self.currentPersonCost().startDate() == o.startDate; }) == 0) ? true : false);
                self.deleteAble = ko.observable((self.isLast() && (self.size > 1))); // can delete when item is last and list have more than one item
                self.beforeStartDate = ko.observable((self.size > 1) ? self.personCostList()[1].startDate : "1900/01/01"); //ver9
                self.onedayAfterBeforeStartDate = ko.observable(moment(self.beforeStartDate()).add(0, 'days').format('YYYY/MM/DD'));//ver9, old verion: before add(1, 'days')
                self.currentEndDate = ko.observable(self.currentPersonCost().endDate());
                self.newStartDate = ko.observable(self.currentPersonCost().startDate());
                self.isUpdate = ko.observable(self.deleteAble() ? false : true);
            }

            /**
             * update/delete data when no error and close dialog
             */
            submitAndCloseDialog(): void {
                nts.uk.ui.block.invisible();
                var self = this;
                if (self.isUpdate()) {
                    if (!vmbase.ProcessHandler.validateDateRange(self.newStartDate(), vmbase.ProcessHandler.getOneDayAfter(self.beforeStartDate()), self.currentEndDate())) {
                        $("#startDateInput").ntsError('set', { messageId: "Msg_65" }); //D2_2
                        nts.uk.ui.block.clear();
                    } else {
                        //self.currentPersonCost().startDate(self.newStartDate());
                        self.currentPersonCost().startDate(moment.utc(self.newStartDate(), 'YYYY-MM-DD').toISOString()); //ver9                        
                        self.currentPersonCost().premiumSets([]);
                        servicebase.personCostCalculationUpdate(vmbase.ProcessHandler.toObjectPersonCost(self.currentPersonCost()))
                            .done(function (res: Array<any>) {
                                self.$dialog.info({ messageId: 'Msg_15' }).then(() => {
                                    nts.uk.ui.windows.setShared('isEdited', 0);
                                    nts.uk.ui.block.clear();
                                    nts.uk.ui.windows.close();
                                });
                            })
                            .fail(function (res) {
                                nts.uk.ui.dialog.alertError(res.message).then(function () { nts.uk.ui.block.clear(); });
                            });
                    }
                } else {
                    if (self.deleteAble()) {
                        nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function () { //ver 9 change from Msg_128 -> 18
                            servicebase.personCostCalculationDelete(vmbase.ProcessHandler.toObjectPersonCost(self.currentPersonCost()))
                                .done(function (res: Array<any>) {
                                    self.$dialog.info({ messageId: 'Msg_16' }).then(() => {
                                        nts.uk.ui.windows.setShared('isEdited', 1);
                                        nts.uk.ui.block.clear();
                                        nts.uk.ui.windows.close();
                                    });
                                })
                                .fail(function (res) {
                                    nts.uk.ui.dialog.alertError({ messageId: res.messageId }).then(function () { nts.uk.ui.block.clear(); });
                                });
                        }).ifNo(function () {
                            nts.uk.ui.block.clear();
                        });
                    } else {
                        //$("#startDateInput").ntsError('set', {messageId:"Msg_128"});
                        nts.uk.ui.block.clear();
                    }
                }
            }

            /**
             * close dialog and do nothing
             */
            closeDialog(): void {
                $("#startDateInput").ntsError('clear');
                nts.uk.ui.windows.close();
            }
        }
    }
}