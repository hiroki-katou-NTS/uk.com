module nts.uk.at.view.kml002.c.viewmodel {
    export class ScreenModel {
        columns: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        allItem: KnockoutObservableArray<ItemModel>;
        items: KnockoutObservableArray<ItemModel>;
        categoryItems: KnockoutObservableArray<any>;
        catCode: KnockoutObservable<number>;
        checked: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        rightItemcolumns: KnockoutObservable<any>;
        currentRightCodeList: KnockoutObservableArray<any>;
        rightItems: KnockoutObservableArray<NewItemModel>;
        attrLabel: KnockoutObservable<String>;
        itemNameLabel: KnockoutObservable<String>;
        enableReturn: KnockoutObservable<boolean>;
        currentData: any;
        tblHeigh: KnockoutObservable<number>;
        
        constructor() {
            var self = this;
            
            var data = nts.uk.ui.windows.getShared("KML002_A_DATA");
            
            self.currentData = data.formTime;
            
            self.attrLabel = ko.observable(data.attribute);
            self.itemNameLabel = ko.observable(data.itemName);
            
            self.items = ko.observableArray([]);
            self.allItem = ko.observableArray([]);
            self.rightItems = ko.observableArray([]);
            
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KML002_7"), prop: 'code', width: 50, hidden: true },
                { headerText: nts.uk.resource.getText("KML002_7"), prop: 'name', width: 200, formatter: _.escape }
            ]);
            
            self.currentCodeList = ko.observableArray([]);
            
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
            
            self.checked = ko.observable(false);
            self.enable = ko.observable(true);
            
            if(data.unit == 1) {
                self.catCode(1);
                $('.cat-lbl').hide();
                $('.categories').hide();
                $('.cbx-checked').hide();
            } else {
                $('.cat-lbl').show();
                $('.categories').show();
                $('.cbx-checked').show();
            }
            
            var devChange = false;
            
            // Bind data to display on Dialog
            if(self.currentData != null) {
                self.catCode(self.currentData.categoryIndicator);
                self.catCode.valueHasMutated();
                self.checked(self.currentData.actualDisplayAtr == 0 ? false : true);
                
                self.bindData(self.currentData.lstFormTimeFunc);
            }
            
            self.checked.subscribe(function(value) {
                if(!devChange){
                    if(self.rightItems().length > 0) {
                        nts.uk.ui.dialog.confirm({ messageId: "Msg_194" }).ifYes(() => { 
                            devChange = false;
                            
                            self.displayItemsRule(self.allItem(), self.catCode(), value);
                            self.rightItems.removeAll();
                            $("#treegridItems").ntsGridList('deselectAll');
                        }).ifNo(() => { 
                            devChange = true;
                            
                            if(value) {
                                self.checked(false);
                                return;
                            } else {
                                self.checked(true);
                                return;
                            }
                        });
                    } else {
                        devChange = false;
                            
                        self.displayItemsRule(self.allItem(), self.catCode(), value);
                        self.rightItems.removeAll();
                        $("#treegridItems").ntsGridList('deselectAll');
                    }                              
                }
                
                devChange = false;
            }); 
            
            self.catCode.subscribe(function(value) {
                if(!devChange){
                    if(self.rightItems().length > 0) {
                        nts.uk.ui.dialog.confirm({ messageId: "Msg_193" }).ifYes(() => { 
                            devChange = false;
                            
                            if(value == 0) {
                                self.enable(true);
                                self.displayItemsRule(self.allItem(), value, self.checked());
                                self.rightItems.removeAll();
                            } else {
                                self.enable(false);
                                self.items(_.filter(self.allItem(), ['itemType', GrantPeriodicMethod.EXTERNAL]));
                                self.rightItems.removeAll();
                            }
                            
                            $("#treegridItems").ntsGridList('deselectAll');
                        }).ifNo(() => { 
                            devChange = true;
                            
                            if(value == 0) {
                                self.catCode(1);
                                self.enable(false);
                                return;
                            } else {
                                self.catCode(0);
                                return;
                            }
                        });
                    } else {
                        devChange = false;
                            
                        if(value == 0) {
                            self.enable(true);
                            self.displayItemsRule(self.allItem(), value, self.checked());
                            self.rightItems.removeAll();
                        } else {
                            self.enable(false);
                            self.items(_.filter(self.allItem(), ['itemType', GrantPeriodicMethod.EXTERNAL]));
                            self.rightItems.removeAll();
                        }
                        
                        $("#treegridItems").ntsGridList('deselectAll');
                    }                    
                }
                
                devChange = false;
            }); 
            
            self.enableReturn = ko.observable(true);
            
            if(self.rightItems().length >= 1) {
                self.enableReturn(true);
            } else {
                self.enableReturn(false);
            }
            
            if(self.catCode() === 0) {
                self.enable(true);
            } else {
                self.enable(false);
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
                    self.displayItemsRule(_.clone(self.allItem()), self.catCode(), self.checked());
                }
                
                self.bindData(self.currentData.lstFormTimeFunc);
                
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
            var dailyAttendanceAtrs = [];
            dailyAttendanceAtrs.push(5);
            var param = {
                dailyAttendanceItemAtrs: dailyAttendanceAtrs ,
                scheduleAtr: data.attributeId,
                budgetAtr: data.attributeId,
                unitAtr: data.unit
            };
            service.getDailyItems(param).done(function(data) {
                let temp = [];
                let items = _.orderBy(data, ['itemType'], ['desc']);
                
                _.forEach(items, function(item: service.BaseItemsDto) {
                    var name = "";
                    
                    if(item.itemType == 0) {
                        name = item.itemName + nts.uk.resource.getText("KML002_43");
                    } else if(item.itemType == 1) {
                        name = item.itemName + nts.uk.resource.getText("KML002_42");
                    } else if(item.itemType == 2) {
                        name = item.itemName + nts.uk.resource.getText("KML002_44");
                    }
                    
                    temp.push(new ItemModel(item.id, name, item.itemType));
                });
                
                self.allItem(temp);
                
                dfd.resolve(data);
            }).fail(function(res) {
                dfd.reject(res);    
            });
            
            return dfd.promise();
        }
        
        /**
         * Bind data from DB to dialog.
         */
        bindData(lstFormTimeFunc: any) {
            var self = this;
            self.rightItems.removeAll();
            
            _.forEach(lstFormTimeFunc, function(item) {
                var itemCd = "";
                var realCd = "";
                
                if(item.attendanceItemId != null) {
                    itemCd = item.attendanceItemId + item.dispOrder;
                    realCd = item.attendanceItemId
                } else if(item.externalBudgetCd != null) {
                    itemCd = item.externalBudgetCd + item.dispOrder;
                    realCd = item.externalBudgetCd
                } else if(item.presetItemId != null) {
                    itemCd = item.presetItemId + item.dispOrder;
                    realCd = item.presetItemId
                }  
                
                var getItemByCd = _.find(self.allItem(), function(o) { return o.code.slice(0, -1) == realCd; });
                var dataType = 0;
                
                if(item.presetItemId != null) {
                    dataType = GrantPeriodicMethod.SCHEDULE;
                } else if (item.attendanceItemId != null) {
                    dataType = GrantPeriodicMethod.DAILY;
                } else if (item.externalBudgetCd != null) {
                    dataType = GrantPeriodicMethod.EXTERNAL;
                }
                
                var itemData = {
                    code: itemCd,
                    trueCode: realCd,
                    itemType: dataType,
                    operatorAtr: item.operatorAtr == 0 ? nts.uk.resource.getText("KML002_37") : nts.uk.resource.getText("KML002_38"),
                    name: getItemByCd != null ? getItemByCd.name : "",
                    order: item.dispOrder
                };
                
                self.rightItems.push(itemData);
            });
        }
        
        /**
         * Display items rule.
         */
        displayItemsRule(allItems: any, category: number, display: boolean) {
            let self = this;
            let temp = [];
            
            if(category == 0 && display) {
                self.items(_.filter(allItems, ['itemType', GrantPeriodicMethod.SCHEDULE]));
            } else if (category == 0 && !display) {
                self.items(_.filter(allItems, function(item: ItemModel) {
                    return item.itemType == GrantPeriodicMethod.DAILY || item.itemType == GrantPeriodicMethod.SCHEDULE;
                }));
            } else {
                self.items(_.filter(allItems, ['itemType', GrantPeriodicMethod.EXTERNAL]));
            }
        }
        
        /**
         * Addition function.
         */
        addition() {
            var t0 = performance.now();
            let self = this;
            
            if(self.currentCodeList().length + self.rightItems().length > 100) {
                nts.uk.ui.dialog.info({ messageId: "Msg_195" });
            } else {
                _.forEach(self.currentCodeList(), function(item){
                    var item = _.find(self.items(), function(o) { return o.code == item.toString(); });
                    
                    let i = self.rightItems().length;
                    
                    self.rightItems.push({
                        code: i.toString(),
                        trueCode: item.code.slice(0, -1),
                        itemType: item.itemType,
                        operatorAtr: nts.uk.resource.getText("KML002_37"),
                        name: item.name,  
                        order: self.rightItems().length + 1  
                    });
                    
                    i = i + 1;
                });
            }
            
            if(self.rightItems().length >= 1) {
                self.enableReturn(true);
            } else {
                self.enableReturn(false);
            }
             var t1 = performance.now();
            console.log("Selection process " + (t1 - t0) + " milliseconds.");
        }
        
        /**
         * Subtraction function.
         */
        subtraction() {
            let self = this;
            
            if(self.currentCodeList().length + self.rightItems().length > 100) {
                nts.uk.ui.dialog.info({ messageId: "Msg_195" });
            } else {
                _.forEach(self.currentCodeList(), function(item){
                    var item = _.find(self.items(), function(o) { return o.code == item.toString(); });
                    
                    let i = self.rightItems().length;
                    
                    self.rightItems.push({
                        code: i.toString(),
                        trueCode: item.code.slice(0, -1),
                        itemType: item.itemType,
                        operatorAtr: nts.uk.resource.getText("KML002_38"),
                        name: item.name,
                        order: self.rightItems().length + 1
                    });
                    
                    i = i + 1;
                });
            }
            
            if(self.rightItems().length >= 1) {
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
            let array =[];

            self.rightItems(_.filter(self.rightItems(), function(item) {
                return _.indexOf(self.currentRightCodeList(), item.code) < 0;
            }));
            
            for(let i = 0; i < self.rightItems().length; i++){
                self.rightItems()[i].code = i.toString();    
            }
            
            self.currentRightCodeList.removeAll();
            self.rightItems(self.rightItems());
            
            if(self.rightItems().length >= 1) {
                self.enableReturn(true);
            } else {
                self.enableReturn(false);
            }
        }
        
        /**
         * Submit data to A screen.
         */
        submit() {
            var self = this;
            var data = nts.uk.ui.windows.getShared("KML002_A_DATA");
            
            var formTimeFunc = [];
            
            for(var i = 0; i < self.rightItems().length; i++) {
                var item = {
                    verticalCalCd: data.verticalCalCd,
                    verticalCalItemId: data.itemId,
                    externalBudgetCd: self.rightItems()[i].itemType == GrantPeriodicMethod.EXTERNAL ? self.rightItems()[i].trueCode : null,
                    attendanceItemId: self.rightItems()[i].itemType == GrantPeriodicMethod.DAILY ? self.rightItems()[i].trueCode : null,
                    presetItemId: self.rightItems()[i].itemType == GrantPeriodicMethod.SCHEDULE ? self.rightItems()[i].trueCode : null,
                    operatorAtr: self.rightItems()[i].operatorAtr == nts.uk.resource.getText("KML002_37") ? 0 : 1,
                    dispOrder: self.rightItems()[i].order,
                    name: self.rightItems()[i].name
                };
                
                formTimeFunc.push(item);
            }
            
            var tranferData = {
                verticalCalCd: data.verticalCalCd,
                verticalCalItemId: data.itemId,
                categoryIndicator: self.catCode(),
                actualDisplayAtr: self.checked() ? 1 : 0,
                lstFormTimeFunc: formTimeFunc
            };
            
            nts.uk.ui.windows.setShared("KML002_C_DATA", tranferData);
            
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
        operatorAtr: string;
        name: string;
        order: number;
        constructor(code: string, operatorAtr: string, name: string, order: number) {
            this.code = code;
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