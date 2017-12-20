module nts.uk.at.view.kmk013.b {
    export module viewmodel {
        export class ScreenModel {
            itemList1: KnockoutObservableArray<any>;
            itemList2: KnockoutObservableArray<any>;
            itemList3: KnockoutObservableArray<any>;
            selectedId1: KnockoutObservable<number>;
            selectedId2: KnockoutObservable<number>;
            selectedId3: KnockoutObservable<number>;
            enable: KnockoutObservable<boolean>;
            enableTime1: KnockoutObservable<boolean>;
            enableTime2: KnockoutObservable<boolean>;
            enableTime3: KnockoutObservable<boolean>;
            readonly: KnockoutObservable<boolean>;
            timeOfDay: KnockoutObservable<number>;
            time: KnockoutObservable<number>;
            time2: KnockoutObservable<number>;
            inline: KnockoutObservable<boolean>;
            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;

            itemListB3: KnockoutObservableArray<any>;
            selectedIdsB3: KnockoutObservableArray<number>;
            enableB3: KnockoutObservable<boolean>;
            //B5 inner
            selectedValueB54: KnockoutObservable<any>;
            selectedValueB59: KnockoutObservable<any>;
            selectedValueB515: KnockoutObservable<any>;
            enableB1: KnockoutObservable<boolean>;
            enableB57: KnockoutObservable<boolean>;
            enableB512: KnockoutObservable<boolean>;
            enableB513: KnockoutObservable<boolean>;
            enableB514: KnockoutObservable<boolean>;
            enableB518: KnockoutObservable<boolean>;
            enableB519: KnockoutObservable<boolean>;
            enableB520: KnockoutObservable<boolean>;
            enableB521: KnockoutObservable<boolean>;
            itemListB59: KnockoutObservableArray<any>;
            selectedIdB59: KnockoutObservableArray<number>;
            //B6
            itemListB64: KnockoutObservableArray<any>;
            selectedValueB64: KnockoutObservable<any>;
            enableB67: KnockoutObservable<boolean>;
            enableB68: KnockoutObservable<boolean>;
            enableB69: KnockoutObservable<boolean>;
            enableB610: KnockoutObservable<boolean>;
            enableB611: KnockoutObservable<boolean>;
            selectedValueB612: KnockoutObservable<any>;
            enableB615: KnockoutObservable<boolean>;
            enableB616: KnockoutObservable<boolean>;
            enableB617: KnockoutObservable<boolean>;
            enableB618: KnockoutObservable<boolean>;
            enableB619: KnockoutObservable<boolean>;
            enableB620: KnockoutObservable<boolean>;
            //B7
            itemListB74:KnockoutObservableArray<any>;
            selectedValueB74: KnockoutObservable<any>;
            enableB77: KnockoutObservable<boolean>;
            itemListB78:KnockoutObservableArray<any>;
            selectedIdB78:KnockoutObservable<any>;
            enableB712: KnockoutObservable<boolean>;
            enableB713: KnockoutObservable<boolean>;
            enableB714: KnockoutObservable<boolean>;
            selectedValueB715:KnockoutObservable<any>;
            enableB718: KnockoutObservable<boolean>;
            enableB719: KnockoutObservable<boolean>;
            enableB720: KnockoutObservable<boolean>;
            enableB721: KnockoutObservable<boolean>;
            enableB722: KnockoutObservable<boolean>;
            constructor() {
                var self = this;
                self.itemList1 = ko.observableArray([
                    new BoxModel(1, nts.uk.resource.getText('KMK013_5')),
                    new BoxModel(2, nts.uk.resource.getText('KMK013_6')),
                ]);
                self.itemList2 = ko.observableArray([
                    new BoxModel(1, nts.uk.resource.getText('KMK013_9')),
                    new BoxModel(2, nts.uk.resource.getText('KMK013_10')),
                ]);
                self.itemList3 = ko.observableArray([
                    new BoxModel(1, nts.uk.resource.getText('KMK013_13')),
                    new BoxModel(2, nts.uk.resource.getText('KMK013_14')),
                ]);
                self.selectedId1 = ko.observable(1);
                self.selectedId2 = ko.observable(1);
                self.selectedId3 = ko.observable(1);

                self.enable = ko.observable(true);
                self.enableTime1 = ko.observable(true);
                self.enableTime2 = ko.observable(true);
                self.enableTime3 = ko.observable(true);
                self.readonly = ko.observable(false);

                self.timeOfDay = ko.observable(0);
                self.time = ko.observable(0);
                self.inline = ko.observable(true);
                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: nts.uk.resource.getText("KMK013_25"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: nts.uk.resource.getText("KMK013_26"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-3', title: nts.uk.resource.getText("KMK013_27"), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
                ]);
                self.selectedTab = ko.observable('tab-1');
                self.itemListB3 = ko.observableArray([
                    new BoxModel(1, nts.uk.resource.getText('KMK013_22')),
                    new BoxModel(2, nts.uk.resource.getText('KMK013_23')),
                    new BoxModel(3, nts.uk.resource.getText('KMK013_24'))
                ]);
                self.selectedIdsB3 = ko.observableArray([1, 2]);
                self.enableB3 = ko.observable(true);
                //B5 inner
                self.enableB1 = ko.observable(true);
                self.selectedValueB54 = ko.observable('1');
                self.selectedValueB59 = ko.observable('1');
                self.selectedValueB515 = ko.observable('1');
                self.enableB57 = ko.observable(true);
                self.enableB512 = ko.observable(true);
                self.enableB513 = ko.observable(true);
                self.enableB514 = ko.observable(true);
                self.enableB518 = ko.observable(true);
                self.enableB519 = ko.observable(true);
                self.enableB520 = ko.observable(true);
                self.enableB521 = ko.observable(true);
                self.itemListB59 = ko.observableArray([
                    new BoxModel(1, nts.uk.resource.getText('KMK013_36')),
                    new BoxModel(2, nts.uk.resource.getText('KMK013_37')),
                ]);
                self.selectedIdB59 = ko.observableArray([1]);

                //B6 inner
                self.selectedValueB64 = ko.observable('1');
                self.selectedValueB59 = ko.observable('1');
                self.selectedValueB515 = ko.observable('1');
                self.enableB67 = ko.observable(true);
                self.enableB68 = ko.observable(true);
                self.enableB69 = ko.observable(true);
                self.enableB610 = ko.observable(true);
                self.enableB611 = ko.observable(true);
                self.selectedValueB612 = ko.observable('1');
                self.enableB615 = ko.observable(true);
                self.enableB616 = ko.observable(true);
                self.enableB617 = ko.observable(true);
                self.enableB618 = ko.observable(true);
                self.enableB619 = ko.observable(true);
                self.enableB620 = ko.observable(true);

                self.itemListB64 = ko.observableArray([
                    new BoxModel(1, nts.uk.resource.getText('KMK013_36')),
                    new BoxModel(2, nts.uk.resource.getText('KMK013_37')),
                ]);
                //B7
                self.selectedValueB74 = ko.observable('1');
                self.itemListB74 = ko.observableArray([
                    new BoxModel(1, nts.uk.resource.getText('KMK013_36')),
                    new BoxModel(2, nts.uk.resource.getText('KMK013_37')),
                ]);
                self.itemListB78=ko.observableArray([
                    new BoxModel(1, nts.uk.resource.getText('KMK013_36')),
                    new BoxModel(2, nts.uk.resource.getText('KMK013_37')),
                ]);
                self.selectedIdB78=ko.observable('1');
                self.enableB77=ko.observable(true);
                self.enableB712=ko.observable(true);
                self.enableB713=ko.observable(true);
                self.enableB714=ko.observable(true);
                self.selectedValueB715 =ko.observable('1');
                self.enableB718=ko.observable(true);
                self.enableB719=ko.observable(true);
                self.enableB720=ko.observable(true);
                self.enableB721=ko.observable(true);
                self.enableB722=ko.observable(true);
            
            }
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }

        }
        class BoxModel {
            id: number;
            name: string;
            constructor(id, name) {
                var self = this;
                self.id = id;
                self.name = name;
            }
        }
    }
}