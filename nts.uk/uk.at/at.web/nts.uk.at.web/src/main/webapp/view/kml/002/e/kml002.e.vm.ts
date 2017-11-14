module nts.uk.at.view.kml002.e.viewmodel {
    export class ScreenModel {
        columns: KnockoutObservable<any>;
        columnsAmount: KnockoutObservable<any>;
        checked: KnockoutObservable<boolean>;
        checkedAmount: KnockoutObservable<boolean>;
        rightItemcolumns: KnockoutObservable<any>;
        rightItemcolumnsAmount: KnockoutObservable<any>;
        methods: KnockoutObservableArray<any>;
        selectedMethod: any;
        categoryItems: KnockoutObservableArray<any>;
        catCode: KnockoutObservable<number>;
        unitPriceItems: KnockoutObservableArray<any>;
        uPCd: KnockoutObservable<number>;
        roundingItems: KnockoutObservableArray<any>;
        roundingCd: KnockoutObservable<number>;
        processingList: KnockoutObservableArray<any>;
        selectedProcessing: KnockoutObservable<number>;
        attrLabel: KnockoutObservable<String>;
        itemNameLabel: KnockoutObservable<String>;

        currentCodeList: KnockoutObservableArray<any>;
        currentCodeListAmount: KnockoutObservableArray<any>;
        allItem: KnockoutObservableArray<ItemModel>;
        items: KnockoutObservableArray<ItemModel>;
        itemsAmount: KnockoutObservableArray<ItemModel>;
        allItemAmount: KnockoutObservableArray<ItemModel>;
        enable: KnockoutObservable<boolean>;
        currentRightCodeList: KnockoutObservableArray<any>;
        currentRightCodeListAmount: KnockoutObservableArray<any>;
        rightItems: KnockoutObservableArray<NewItemModel>;
        rightItemsAmount: KnockoutObservableArray<NewItemModel>;
        enableReturn: KnockoutObservable<boolean>;
        enableReturnAmount: KnockoutObservable<boolean>;
        unitSelect: KnockoutObservable<any>;
        listBudget: KnockoutObservableArray<any>;

        constructor() {
            var self = this;

            var data = nts.uk.ui.windows.getShared("KML002_A_DATA");

            self.attrLabel = ko.observable(data.attribute);
            self.itemNameLabel = ko.observable(data.itemName);
            self.unitSelect = ko.observable(data.unit);
            self.items = ko.observableArray([]);
            self.itemsAmount = ko.observableArray([]);
            self.rightItems = ko.observableArray([]);
            self.rightItemsAmount = ko.observableArray([]);
            self.listBudget = ko.observableArray([]);
            self.enable = ko.observable(true);
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KML002_7"), prop: 'code', width: 50, hidden: true },
                { headerText: nts.uk.resource.getText("KML002_7"), prop: 'name', width: 180, formatter: _.escape }
            ]);
            self.columnsAmount = ko.observableArray([
                { headerText: nts.uk.resource.getText("KML002_7"), prop: 'code', width: 50, hidden: true },
                { headerText: nts.uk.resource.getText("KML002_7"), prop: 'name', width: 180, formatter: _.escape }
            ]);

            self.currentCodeList = ko.observableArray([]);
            self.currentCodeListAmount = ko.observableArray([]);
            self.rightItemcolumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KML002_7"), prop: 'code', width: 50, hidden: true },
                { headerText: nts.uk.resource.getText("KML002_36"), prop: 'operatorAtr', width: 80 },
                { headerText: nts.uk.resource.getText("KML002_7"), prop: 'name', width: 160, formatter: _.escape }
            ]);
            self.rightItemcolumnsAmount = ko.observableArray([
                { headerText: nts.uk.resource.getText("KML002_7"), prop: 'code', width: 50, hidden: true },
                { headerText: nts.uk.resource.getText("KML002_36"), prop: 'operatorAtr', width: 80 },
                { headerText: nts.uk.resource.getText("KML002_7"), prop: 'name', width: 160, formatter: _.escape }
            ]);

            self.currentRightCodeList = ko.observableArray([]);
            self.currentRightCodeListAmount = ko.observableArray([]);
            self.allItem = ko.observableArray([]);
            self.allItemAmount = ko.observableArray([]);
            self.checked = ko.observable(true);
            self.checkedAmount = ko.observable(true);

            self.methods = ko.observableArray([
                { code: '0', name: nts.uk.resource.getText("KML002_50") },
                { code: '1', name: nts.uk.resource.getText("KML002_58") }
            ]);

            self.selectedMethod = ko.observable(0);

            self.categoryItems = ko.observableArray([
                { catCode: 0, catName: nts.uk.resource.getText("KML002_29") },
                { catCode: 1, catName: nts.uk.resource.getText("KML002_32") }
            ]);

            self.catCode = ko.observable(0);

            if (self.selectedMethod() == 0) {
                $('.method-a').show();
                $('.method-b').hide();
            } else {
                $('.method-a').hide();
                $('.method-b').show();
            }

            self.selectedMethod.subscribe(function(value) {
                if (value == 0) {
                    $('.method-a').show();
                    $('.method-b').hide();
                    self.formulaTimeUnit();
                    if (self.allItem().length > 0) {
                        self.displayItemsRuleAmount(_.clone(self.allItem()), self.checked());
                    }
                } else {
                    $('.method-a').hide();
                    $('.method-b').show();
                    self.getData();
                }
            });

            self.unitPriceItems = ko.observableArray([
                { uPCd: 0, uPName: nts.uk.resource.getText("KML002_53") },
                { uPCd: 1, uPName: nts.uk.resource.getText("KML002_54") },
                { uPCd: 2, uPName: nts.uk.resource.getText("KML002_55") },
                { uPCd: 3, uPName: nts.uk.resource.getText("KML002_56") },
                { uPCd: 4, uPName: nts.uk.resource.getText("KML002_57") }
            ]);

            self.uPCd = ko.observable(0);

            self.roundingItems = ko.observableArray([
                { roundingCd: 0, roundingName: nts.uk.resource.getText("KML002_53") },
                { roundingCd: 1, roundingName: nts.uk.resource.getText("KML002_54") },
                { roundingCd: 2, roundingName: nts.uk.resource.getText("KML002_55") },
                { roundingCd: 3, roundingName: nts.uk.resource.getText("KML002_56") },
                { roundingCd: 4, roundingName: nts.uk.resource.getText("KML002_54") },
                { roundingCd: 5, roundingName: nts.uk.resource.getText("KML002_55") },
                { roundingCd: 6, roundingName: nts.uk.resource.getText("KML002_56") },
                { roundingCd: 7, roundingName: nts.uk.resource.getText("KML002_57") }
            ]);

            self.roundingCd = ko.observable(0);

            self.processingList = ko.observableArray([
                { code: '0', name: nts.uk.resource.getText("Enum_Rounding_Down") },
                { code: '1', name: nts.uk.resource.getText("Enum_Rounding_Up") }
            ]);

            self.selectedProcessing = ko.observable(0);

            if (self.catCode() === 0) {
                self.enable(true);
            } else {
                self.enable(false);
            }

            var devChange = false;

            self.checked.subscribe(function(value) {
                if (!devChange) {
                    nts.uk.ui.dialog.confirm({ messageId: "Msg_194" }).ifYes(() => {
                        devChange = false;

                        self.displayItemsRule(self.allItem(), self.catCode(), value);
                        self.rightItemsAmount.removeAll();
                        $("#treegridItems").ntsGridList('deselectAll');
                    }).ifNo(() => {
                        devChange = true;

                        if (value) {
                            self.checked(false);
                            return;
                        } else {
                            self.checked(true);
                            return;
                        }
                    })
                }

                devChange = false;
            });

            self.checkedAmount.subscribe(function(value) {
                if (!devChange) {
                    nts.uk.ui.dialog.confirm({ messageId: "Msg_194" }).ifYes(() => {
                        devChange = false;

                        self.displayItemsRule(self.allItemAmount(), self.catCode(), value);
                        self.rightItemsAmount.removeAll();
                        $("#treegridItemsB").ntsGridList('deselectAll');
                    }).ifNo(() => {
                        devChange = true;

                        if (value) {
                            self.checked(false);
                            return;
                        } else {
                            self.checked(true);
                            return;
                        }
                    })
                }

                devChange = false;
            });

            self.catCode.subscribe(function(value) {
                if (!devChange) {
                    nts.uk.ui.dialog.confirm({ messageId: "Msg_193" }).ifYes(() => {
                        devChange = false;

                        if (value == 0) {
                            self.displayItemsRule(self.allItemAmount(), value, self.checkedAmount());
                            self.rightItemsAmount.removeAll();
                        } else {
                            self.items(_.filter(self.allItemAmount(), ['itemType', GrantPeriodicMethod.EXTERNAL]));
                            self.rightItemsAmount.removeAll();
                        }

                        $("#treegridItemsAmount").ntsGridList('deselectAll');
                    }).ifNo(() => {
                        devChange = true;

                        if (value == 0) {
                            self.catCode(1);
                            return;
                        } else {
                            self.catCode(0);
                            return;
                        }
                    })
                }

                devChange = false;
            });


            self.enableReturn = ko.observable(true);
            self.enableReturnAmount = ko.observable(true);

            if (self.rightItems().length >= 1 || self.rightItemsAmount().length >= 1) {
                self.enableReturn(true);
                self.enableReturnAmount(true);
            } else {
                self.enableReturn(false);
                self.enableReturnAmount(true);
            }
        }

        /**
         * Start page.
         */
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            if (self.selectedMethod() == 0) {
                $.when(self.formulaTimeUnit()).done(function() {

                    if (self.allItem().length > 0) {
                        self.displayItemsRuleAmount(_.clone(self.allItem()), self.checked());
                    }
                }).fail(function(res) {
                    dfd.reject(res);
                });


            } else {
                $.when(self.getData()).done(function() {

                    if (self.allItemAmount().length > 0) {
                        self.displayItemsRule(_.clone(self.allItemAmount()), self.catCode(), self.checkedAmount());
                    }
                }).fail(function(res) {
                    dfd.reject(res);
                });
            };
            dfd.resolve();
            return dfd.promise();
        }

        submit() {
            var self = this;
            var data = {
                dataList: self.rightItems(),
                dataAmount: self.rightItemsAmount(),
                calMethodAtr: self.selectedMethod(),
                roundingTime: self.roundingCd(),
                roundingAtr: self.selectedProcessing(),
                unitPrice: self.uPCd(),
                actualDisplayAtrAmount: self.checked(),
                actualDisplayAtr: self.checkedAmount(),
                categoryIndicator: self.catCode()
            }
            nts.uk.ui.windows.setShared("KML002_E_DATA", data);
            nts.uk.ui.windows.close();
        }

        cancel() {
            var self = this;
            nts.uk.ui.windows.close();

        }
        getData(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

            var data = nts.uk.ui.windows.getShared("KML002_A_DATA");
            let array = [];
            let i = 2;
            let param = {
                budgetAtr: 1,
                // received from mother screen 0: day or 1: time
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
                self.itemsAmount(sortedLst);
                self.listBudget(sortedLst);
                dfd.resolve();
            })

            return dfd.promise();
        }
        formulaTimeUnit(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.allItem([]);

            var data = nts.uk.ui.windows.getShared("KML002_A_DATA");
            var dailyAttendanceAtrs = [];
            dailyAttendanceAtrs.push(3);
            dailyAttendanceAtrs.push(5);
            var param = {
                dailyAttendanceItemAtrs: dailyAttendanceAtrs,
                scheduleAtr: data.attributeId,
                budgetAtr: data.attributeId,
                unitAtr: UseAtr.DO_NOT_USE
            };
            service.getDailyItems(param).done(function(data) {
                let temp = [];
                let items = _.sortBy(data, ['companyId', 'dispOrder']);

                _.forEach(items, function(item: service.BaseItemsDto) {
                    var name = item.itemName + nts.uk.resource.getText("KML002_43");
                    temp.push(new ItemModel(item.id, name, item.itemType));
                });

                self.allItem(temp);

                dfd.resolve(data);
            }).fail(function(res) {
                dfd.reject(res);
            });

            return dfd.promise();
        }
        getDataRight() {
            var self = this;
            var data = nts.uk.ui.windows.getShared("KML002_A_DATA");
            _.forEach(data, function(res: NewItemModel) {

                self.rightItemsAmount.push(new NewItemModel(res.code, res.operatorAtr, res.name, res.id));
            });
        }

        /**
         * Display items rule.
         */
        displayItemsRule(allItems: any, category: number, display: boolean) {
            let self = this;
            let temp = [];
            if (category == CategoryIndicator.EXTERNAL_BUDGET_RECORD_ITEMS && display) {
                self.items(_.filter(allItems, ['itemType', GrantPeriodicMethod.SCHEDULE]));
            } else if (category == CategoryIndicator.EXTERNAL_BUDGET_RECORD_ITEMS && !display) {
                self.items(_.filter(allItems, function(item: ItemModel) {
                    return item.itemType == GrantPeriodicMethod.DAILY || item.itemType == GrantPeriodicMethod.SCHEDULE;
                }));
            }
        }

        displayItemsRuleAmount(allItemAmount: any, display: boolean) {
            let self = this;
            let temp = [];

            if (display) {
                self.itemsAmount(_.filter(allItemAmount, ['itemType', GrantPeriodicMethod.SCHEDULE]));
            } else {
                self.itemsAmount(_.filter(allItemAmount, function(item: ItemModel) {
                    return item.itemType == GrantPeriodicMethod.DAILY
                        || item.itemType == GrantPeriodicMethod.SCHEDULE;
                }));
            }
        }


        /**
         * Addition function.
         */
        addition() {
            let self = this;

            if (self.currentCodeList().length + self.rightItems().length > 100) {
                nts.uk.ui.dialog.info({ messageId: "Msg_195" });
            } else {
                _.forEach(self.currentCodeList(), function(sonCute) {
                    var item = _.find(self.items(), function(o) { return o.code == sonCute; });

                    let i = self.rightItems().length;

                    self.rightItems.push({
                        code: i.toString(),
                        operatorAtr: nts.uk.resource.getText("KML002_37"),
                        name: item.name,
                        id: i + 1
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
                _.forEach(self.currentCodeList(), function(sonCute) {
                    var item = _.find(self.items(), function(o) { return o.code == sonCute; });

                    let i = self.rightItems().length;

                    self.rightItems.push({
                        code: i.toString(),
                        operatorAtr: nts.uk.resource.getText("KML002_38"),
                        name: item.name,
                        id: i + 1
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
 * Addition function.
 */
        additionAmount() {
            let self = this;

            if (self.currentCodeListAmount().length + self.rightItemsAmount().length > 100) {
                nts.uk.ui.dialog.info({ messageId: "Msg_195" });
            } else {
                _.forEach(self.currentCodeListAmount(), function(sonCute) {
                    var item = _.find(self.itemsAmount(), function(o) { return o.code == sonCute; });

                    let i = self.rightItemsAmount().length;

                    self.rightItemsAmount.push({
                        code: i.toString(),
                        operatorAtr: nts.uk.resource.getText("KML002_37"),
                        name: item.name,
                        id: i + 1
                    });

                    i = i + 1;
                });
            }

            if (self.rightItemsAmount().length >= 1) {
                self.enableReturnAmount(true);
            } else {
                self.enableReturnAmount(false);
            }
        }

        /**
         * Subtraction function.
         */
        subtractionAmount() {
            let self = this;

            if (self.currentCodeListAmount().length + self.rightItemsAmount().length > 100) {
                nts.uk.ui.dialog.info({ messageId: "Msg_195" });
            } else {
                _.forEach(self.currentCodeListAmount(), function(sonCute) {
                    var item = _.find(self.itemsAmount(), function(o) { return o.code == sonCute; });

                    let i = self.rightItemsAmount().length;

                    self.rightItemsAmount.push({
                        code: i.toString(),
                        operatorAtr: nts.uk.resource.getText("KML002_38"),
                        name: item.name,
                        id: i + 1
                    });

                    i = i + 1;
                });
            }

            if (self.rightItemsAmount().length >= 1) {
                self.enableReturnAmount(true);
            } else {
                self.enableReturnAmount(false);
            }
        }

        /**
         * Return items.
         */
        returnItemsAmount() {
            let self = this;
            let array = [];

            self.rightItemsAmount(_.filter(self.rightItemsAmount(), function(item) {
                return _.indexOf(self.currentRightCodeListAmount(), item.code) < 0;
            }));

            for (let i = 0; i < self.rightItemsAmount().length; i++) {
                self.rightItemsAmount()[i].code = i.toString();
            }

            self.currentRightCodeListAmount.removeAll();
            self.rightItemsAmount(self.rightItems());

            if (self.rightItemsAmount().length >= 1) {
                self.enableReturnAmount(true);
            } else {
                self.enableReturnAmount(false);
            }
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
    export class NewItemModel {
        code: string;
        operatorAtr: string;
        name: string;
        id: any
        constructor(code: string, operatorAtr: string, name: string, id: any) {
            this.code = code;
            this.operatorAtr = operatorAtr;
            this.name = name;
            this.id = id;
        }
    }
    export interface IFormulaTimeUnit {
        verticalCalCd?: String;
        verticalCalItemId?: String;
        roundingTime?: number;
        roundingAtr?: number;
        unitPrice?: number;
        actualDisplayAtr?: number;


    }

    export enum GrantPeriodicMethod {
        /* 0- 日次の勤怠項目 */
        DAILY = 0,
        /* 1- 予定項目 */
        SCHEDULE,
        /* 2- 外部予算実績項目 */
        EXTERNAL
    }
    export enum DailyAttendanceAtr {
        /* 0- 金額 */
        DAILY = 3,
        /* 1- 時間 */
        SCHEDULE = 5
    }

    export enum CategoryIndicator {
        /* 0- 外部予算実績項目 */
        EXTERNAL_BUDGET_RECORD_ITEMS = 0,
        /* 1- 勤怠項目 */
        ATTENDANCE_ITEM
    }

    export enum UseAtr {
        /** 0- 利用しない **/
        DO_NOT_USE = 0,
        /** 1- 利用する **/
        USE
    }

    export enum CalMethodAtr {
        /** 0- 金額項目 **/
        AMOUNT_ITEM = 0,
        /** 1- 時間項目×単価 **/
        TIME_ITEM_X_UNIT_PRICE
    }
    export enum RoundingTime {
        /** 0- 1分 **/
        ONE_MINUTE = 0,
        /** 1- 5分 **/
        FIVE_MINS = 1,
        /** 2- 6分 **/
        SIX_MINS = 2,
        /** 3- 10分 **/
        TEN_MINS = 3,
        /** 4- 15分 **/
        FIFTEEN_MINS = 4,
        /** 4- 20分 **/
        TWENTY_MINS = 5,
        /** 4- 30分 **/
        THIRTY_MINS = 6,
        /** 4- 60分 **/
        SIXTY_MINS = 7
    }
    export enum UnitPrice {

        /** 0- 単価0 **/
        UNIT_PRICE_1 = 0,
        /** 1- 単価2 **/
        UNIT_PRICE_2 = 1,
        /** 2- 単価3 **/
        UNIT_PRICE_3 = 2,
        /** 3- 契約単価 **/
        CONTRACT = 3,
        /** 4-基準単価 **/
        STANDARD = 4
    }
}