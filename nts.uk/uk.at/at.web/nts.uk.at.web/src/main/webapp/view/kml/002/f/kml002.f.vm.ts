module nts.uk.at.view.kml002.f.viewmodel {
    export class ScreenModel {
        columns: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        allItem: KnockoutObservableArray<ItemModel>;
        items: KnockoutObservableArray<ItemModel>;
        categoryItems: KnockoutObservableArray<any>;
        catCode: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;
        rightItemcolumns: KnockoutObservable<any>;
        currentRightCodeList: KnockoutObservableArray<any>;
        rightItems: KnockoutObservableArray<NewItemModel>;
        attrLabel: KnockoutObservable<String>;
        itemNameLabel: KnockoutObservable<String>;
        enableReturn: KnockoutObservable<boolean>;
        unitSelect: KnockoutObservable<any>;
        listBudget: KnockoutObservableArray<any>;
        currentData: any;
        constructor() {
            var self = this;

            var data = nts.uk.ui.windows.getShared("KML002_A_DATA");
            
            self.currentData = data.numerical;
            self.attrLabel = ko.observable(data.attribute);
            self.itemNameLabel = ko.observable(data.itemName);
            self.unitSelect = ko.observable(data.unit);
            self.items = ko.observableArray([]);
            self.allItem = ko.observableArray([]);
            
            self.rightItems = ko.observableArray([]);
            
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KML002_7"), prop: 'code', width: 50, hidden: true },
                { headerText: nts.uk.resource.getText("KML002_7"), prop: 'name', width: 200, formatter: _.escape }
            ]);

            self.currentCodeList = ko.observableArray([]);
            self.listBudget = ko.observableArray([]);
            self.categoryItems = ko.observableArray([
                { catCode: 0, catName: nts.uk.resource.getText("KML002_29") },
                { catCode: 1, catName: nts.uk.resource.getText("KML002_32") }
            ]);

            self.rightItemcolumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KML002_7"), prop: 'code', width: 50, hidden: true },
                { headerText: nts.uk.resource.getText("KML002_36"), prop: 'operatorAtr', width: 80 },
                { headerText: nts.uk.resource.getText("KML002_7"), prop: 'name', width: 200, formatter: _.escape }
            ]);

            self.currentRightCodeList = ko.observableArray([]);

            self.catCode = ko.observable(0);

            self.enable = ko.observable(true);

            if (self.catCode() === 0) {
                self.enable(true);
            } else {
                self.enable(false);
            }
            
            self.catCode.subscribe(function(value) {
                if (value == 0) {
                    self.enable(true);
                    var temp = [];

                    for (var i = 0; i < self.allItem().length; i++) {
                        if (self.allItem()[i].itemType == 0 || self.allItem()[i].itemType == 1) {
                            temp.push(new ItemModel(self.allItem()[i].code, self.allItem()[i].name, self.allItem()[i].itemType));
                        }
                    }

                    self.items.removeAll();
                    self.items(temp);
                } else {
                    self.enable(false);
                    self.allItem(self.items());
                    var temp = [];

                    for (var i = 0; i < self.allItem().length; i++) {
                        if (self.allItem()[i].itemType == 2) {
                            temp.push(new ItemModel(self.allItem()[i].code, self.allItem()[i].name, self.allItem()[i].itemType));
                        }
                    }

                    self.items.removeAll();
                    self.items(temp);
                }
            });

            self.enableReturn = ko.observable(true);

            if (self.rightItems().length >= 1) {
                self.enableReturn(true);
            } else {
                self.enableReturn(false);
            }
        }

        /**
         * Start page.
         */
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

            $.when(self.getData()).done(function() {

                if (self.allItem().length > 0) {
                    self.items(self.allItem());
                }
                
                var data = nts.uk.ui.windows.getShared("KML002_A_DATA");
                
                if(data.numerical.length > 0) {
                    _.forEach(data.numerical, function(item, index){
                        var currentItem = _.find(self.items(), function(o) { return o.code == item.externalBudgetCd; });
                        
                        self.rightItems.push({
                            code: index.toString(),
                            realCode: item.externalBudgetCd,
                            operatorAtr: item.operatorAtr == 0 ? nts.uk.resource.getText("KML002_37") : nts.uk.resource.getText("KML002_38"),
                            name: currentItem.name,
                            order: item.dispOrder
                        });
                    });                
                }
                
                if (self.rightItems().length >= 1) {
                    self.enableReturn(true);
                } else {
                    self.enableReturn(false);
                }

                dfd.resolve();
            }).fail(function(res) {
                dfd.reject(res);
            });

            dfd.resolve();

            return dfd.promise();
        }

        /**
         * Get data from db.
         */
        getData(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.allItem([]);

            var data = nts.uk.ui.windows.getShared("KML002_A_DATA");
            let array = [];
            let i = 2;
            let param = {
                budgetAtr: 3,
                unitAtr: self.unitSelect()
            }
            service.getByAtr(param).done((lst) => {
                let sortedData = _.orderBy(lst, ['externalBudgetCode'], ['asc']);
                _.map(sortedData, function(item: any) {
                    array.push({
                        id: i,
                        code: item.externalBudgetCode,
                        name: item.externalBudgetName + nts.uk.resource.getText("KML002_44")
                    })
                    i = i + 1;
                });

                let sortedLst = _.orderBy(array, ['id'], ['asc']);
                self.items(sortedLst);
                self.listBudget(sortedLst);
                dfd.resolve();
            })

            return dfd.promise();
        }

        /**
         * Addition function.
         */
        addition() {
            let self = this;

            if (self.currentCodeList().length + self.rightItems().length > 100) {
                nts.uk.ui.dialog.info({ messageId: "Msg_195" });
            } else {
                _.forEach(self.currentCodeList(), function(item) {
                    var items = _.find(self.items(), function(o) { return o.code == item; });

                    let i = self.rightItems().length;

                    self.rightItems.push({
                        code: i.toString(),
                        realCode: items.code,
                        operatorAtr: nts.uk.resource.getText("KML002_37"),
                        name: items.name,
                        order: self.rightItems().length + 1
                    });

                    i = i + 1;
                });
            }

            if (self.rightItems().length >= 1) {
                self.enableReturn(true);
            } else {
                self.enableReturn(false);
            }
        }

        /**
         * Subtraction function.
         */
        subtraction() {
            let self = this;

            if (self.currentCodeList().length + self.rightItems().length > 100) {
                nts.uk.ui.dialog.info({ messageId: "Msg_195" });
            } else {
                _.forEach(self.currentCodeList(), function(item) {
                    var items = _.find(self.items(), function(o) { return o.code == item; });

                    let i = self.rightItems().length;

                    self.rightItems.push({
                        code: i.toString(),
                        realCode: items.code,
                        operatorAtr: nts.uk.resource.getText("KML002_38"),
                        name: items.name,
                        order: self.rightItems().length + 1
                    });

                    i = i + 1;
                });
            }

            if (self.rightItems().length >= 1) {
                self.enableReturn(true);
            } else {
                self.enableReturn(false);
            }
        }

        /**
         * Return items.
         */
        returnItems() {
            let self = this;
            let array = [];

            self.rightItems(_.filter(self.rightItems(), function(item) {
                return _.indexOf(self.currentRightCodeList(), item.code) < 0;
            }));

            for (let i = 0; i < self.rightItems().length; i++) {
                self.rightItems()[i].code = i.toString();
            }

            self.currentRightCodeList.removeAll();
            self.rightItems(self.rightItems());

            if (self.rightItems().length >= 1) {
                self.enableReturn(true);
            } else {
                self.enableReturn(false);
            }
        }

        /**
         * Submit data to A screen.
         */
        submit() {
            nts.uk.ui.block.invisible();
            var self = this;
            var formNumer = [];
            var data = nts.uk.ui.windows.getShared("KML002_A_DATA");
             _.forEach(self.rightItems(), function(item, index){
                var formNumerical = {
                    verticalCalCd: data.verticalCalCd,
                    verticalCalItemId: data.itemId,
                    externalBudgetCd: item.realCode,
                    operatorAtr: item.operatorAtr == nts.uk.resource.getText("KML002_37") ? 0 : 1,
                    dispOrder: index,
                    name: item.name
                }
                formNumer.push(formNumerical);
             });

            nts.uk.ui.windows.setShared("KML002_F_DATA", formNumer);
            nts.uk.ui.block.clear();
            nts.uk.ui.windows.close();
            
        }

        /**
         * Close dialog.
         */
        cancel() {
            nts.uk.ui.windows.close();
        }
    }

    class ItemModel {
        code: string;
        name: string;
        itemType: number;
        constructor(code: string, name: string, itemType: number) {
            this.code = code;
            this.name = name;
            this.itemType = itemType;
        }
    }

    class NewItemModel {
        code: string;
        realCode: string;
        operatorAtr: string;
        name: string;
        order: number;
        constructor(code: string, realCode: string, operatorAtr: string, name: string, order: number) {
            this.code = code;
            this.realCode = realCode;
            this.operatorAtr = operatorAtr;
            this.name = name;
            this.order = order;
        }
    }
    export enum GrantPeriodicMethod {
        /** 0- 日次の勤怠項目 **/
        DAILY = 0,
        /** 1- 予定項目 **/
        SCHEDULE,
        /** 2- 外部予算実績項目 **/
        EXTERNAL
    }
}