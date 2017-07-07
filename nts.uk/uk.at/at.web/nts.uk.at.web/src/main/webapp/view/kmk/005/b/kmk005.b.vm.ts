module nts.uk.at.view.kmk005.b {
    export module viewmodel {
        export class ScreenModel {
            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;
            timeItemSpecList: KnockoutObservableArray<TimeItem>;
            timeItemList: KnockoutObservableArray<TimeItem>;

            constructor() {
                var self = this;

                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: nts.uk.resource.getText('KMK005_17'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: nts.uk.resource.getText('KMK005_18'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                self.selectedTab = ko.observable('tab-1');
                self.timeItemList = ko.observableArray([]);
                self.timeItemSpecList = ko.observableArray([]);


            }


            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();

                service.getListSpecialBonusPayTimeItem().done(function(item: Array<any>) {
                    if (item === undefined || item.length == 0) {
                        for (i = 0; i < 10; i++) {
                            self.timeItemSpecList.push(
                                new TimeItem(
                                    "持定加給" + (i + 1), 1, nts.uk.resource.getText("KMK005_" + (22 + i)), 1, ""
                                ));
                        }
                    } else {
                        item.forEach(function(item) {
                            self.timeItemSpecList.push(new TimeItem(item.timeItemName, item.useAtr, item.timeItemNo, item.timeItemTypeAtr, item.timeItemId));
                        })

                    }
                }).fail(function(res) {
                    window.alert("fail");
                });

                service.getListBonusPTimeItem().done(function(item: Array<any>) {
                    if (item === undefined || item.length == 0) {
                        for (i = 0; i < 10; i++) {
                            self.timeItemList.push(
                                new TimeItem(
                                    "加給" + (i + 1), 1, nts.uk.resource.getText("KMK005_" + (22 + i)), 0, ""
                                ));
                        }
                    } else {
                        item.forEach(function(item) {
                            self.timeItemList.push(new TimeItem(item.timeItemName, item.useAtr, item.timeItemNo, item.timeItemTypeAtr, item.timeItemId));
                        })

                    }
                }).fail(function(res) {
                    window.alert("fail");
                });

                dfd.resolve();

                return dfd.promise();
            }
            //content
            /**
            * save data and close dialog
            */
            submitAndCloseDialog(): void {
                var self = this;

                let bonusPayTimeItemListCommand = [];
                // var checkUseExist = false;
                let lstUseArt = [];

                ko.utils.arrayForEach(self.timeItemList(), function(item) {
                    /*    if (item.useAtr() == 1) {
                            checkUseExist = true;
                        }
                        */
                    lstUseArt.push(item.useAtr() == 1 ? true : false);
                    bonusPayTimeItemListCommand.push(ko.mapping.toJS(item));
                });

                let bonusPayTimeItemSpecListCommand = [];

                ko.utils.arrayForEach(self.timeItemSpecList(), function(item) {
                    /*    if (item.useAtr() == 1) {
                            checkUseExist = true;
                        }
                        */
                    lstUseArt.push(item.useAtr() == 1 ? true : false);
                    bonusPayTimeItemSpecListCommand.push(ko.mapping.toJS(item));
                });

                service.checkUseArt(lstUseArt).done(function() {
                    service.getListBonusPTimeItem().done(function(res: Array<any>) {
                        if (res === undefined || res.length == 0) {
                            service.addListBonusPayTimeItem(bonusPayTimeItemListCommand);
                            service.addListBonusPayTimeItem(bonusPayTimeItemSpecListCommand);
                        } else {
                            //  if (checkUseExist) {
                            service.updateListBonusPayTimeItem(bonusPayTimeItemListCommand);
                            service.updateListBonusPayTimeItem(bonusPayTimeItemSpecListCommand);
                            //  } else {
                            //  nts.uk.ui.dialog.alertError({ messageId: "Msg_131" });
                            // }
                        }
                    })

                })
                    .fail(function(res) {
                        nts.uk.ui.dialog.alertError({ messageId: res.message });
                    });;




                self.closeDialog();
            }

            /**
             * close dialog and do nothing
             */
            closeDialog(): void {
                nts.uk.ui.windows.close();
            }
        }

        export class TimeItem {
            timeItemName: KnockoutObservable<string>;
            useAtr: KnockoutObservable<number>;
            timeItemNo: KnockoutObservable<number>;
            timeItemTypeAtr: KnockoutObservable<number>;
            timeItemId: KnockoutObservable<string>;
            constructor(timeItemName: string, useAtr: number, timeItemNo: number, timeItemTypeAtr: number, timeItemId: string) {
                var self = this;
                self.timeItemName = ko.observable(timeItemName);
                self.useAtr = ko.observable(useAtr);
                self.timeItemNo = ko.observable(timeItemNo);
                self.timeItemTypeAtr = ko.observable(timeItemTypeAtr);
                self.timeItemId = ko.observable(timeItemId);
            }
        }


    }
}