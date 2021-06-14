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
                self.selectedTab.subscribe((tabId) => {
                    if (tabId === "tab-1") {
                        $(document).ready(() => {
                            $($(".itemName[disabled!='disabled']")[0]).focus();
                        });
                    } else if (tabId === "tab-2") {
                        $(document).ready(() => {
                            $($(".specialItemName[disabled!='disabled']")[0]).focus();
                        });
                    }
                });
            }


            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();

                service.getListSpecialBonusPayTimeItem().done(function(item: Array<any>) {
                    if (item === undefined || item.length == 0) {
                        for (i = 0; i < 10; i++) {
                            self.timeItemSpecList.push(
                                new TimeItem(
                                    "", 1, nts.uk.resource.getText("KMK005_" + (22 + i)), 1, "" 
                                ));
                        }
                    } else {
                        let name = '#[KMK005_';
                        let i = 140;
                        item.forEach(function(item) {
                            self.timeItemSpecList.push(new TimeItem(item.timeItemName, item.useAtr, item.timeItemNo, item.timeItemTypeAtr, item.timeItemId ,  name + i++ + ']'));
                        });
                    }
                });
                service.getListBonusPTimeItem().done(function(item: Array<any>) {
                    if (item === undefined || item.length == 0) {
                        for (i = 0; i < 10; i++) {
                            self.timeItemList.push(
                                new TimeItem(
                                    "", 1, nts.uk.resource.getText("KMK005_" + (22 + i)), 0, ""
                                ));
                        }
                        $($(".itemName[disabled!='disabled']")[0]).focus();
                    } else {
                         let name = '#[KMK005_';
                        let i = 150;
                        item.forEach(function(item) {
                            self.timeItemList.push(new TimeItem(item.timeItemName, item.useAtr, item.timeItemNo, item.timeItemTypeAtr, item.timeItemId ,name + i++ + ']' ));
                        })
                        $($(".itemName[disabled!='disabled']")[0]).focus();
                    }
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
                $(".premiumName").trigger("validate");
                if (!nts.uk.ui.errors.hasError()) {
                    let bonusPayTimeItemListCommand = [];
                    let lstUseArt = [];

                    ko.utils.arrayForEach(self.timeItemList(), function(item) {
                        lstUseArt.push(item.useAtr() == 1 ? true : false);
                        bonusPayTimeItemListCommand.push(ko.mapping.toJS(item));
                    });

                    let bonusPayTimeItemSpecListCommand = [];

                    ko.utils.arrayForEach(self.timeItemSpecList(), function(item) {
                        lstUseArt.push(item.useAtr() == 1 ? true : false);
                        bonusPayTimeItemSpecListCommand.push(ko.mapping.toJS(item));
                    });

                    service.checkUseArt(lstUseArt).done(function() {
                        service.getListBonusPTimeItem().done(function(res: Array<any>) {
                            if (res === undefined || res.length == 0) {
                                service.addListBonusPayTimeItem(bonusPayTimeItemListCommand);
                                service.addListBonusPayTimeItem(bonusPayTimeItemSpecListCommand);
                            } else {
                                service.updateListBonusPayTimeItem(bonusPayTimeItemListCommand);
                                service.updateListBonusPayTimeItem(bonusPayTimeItemSpecListCommand);
                            }
                            self.closeDialog();
                        })

                    }).fail(function(res) {
                         nts.uk.ui.dialog.alertError({ messageId: res.messageId });
                    });
                }
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
            nameValidate: KnockoutObservable<string>;
            constructor(timeItemName: string, useAtr: number, timeItemNo: number, timeItemTypeAtr: number, timeItemId: string , nameValidate: string) {
                var self = this;
                self.timeItemName = ko.observable(timeItemName);
                self.useAtr = ko.observable(useAtr);
                self.timeItemNo = ko.observable(timeItemNo);
                self.timeItemTypeAtr = ko.observable(timeItemTypeAtr);
                self.timeItemId = ko.observable(timeItemId);
                self.nameValidate = ko.observable(nameValidate);
            }
        }


    }
}