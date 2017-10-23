module nts.uk.at.view.kml002.a.viewmodel {
    export class ScreenModel {
        settingItems: KnockoutObservableArray<SettingItemModel>;
        settingColumns: KnockoutObservable<any>;
        singleSelectedCode: KnockoutObservable<any>;
        code: KnockoutObservable<string>;
        editMode: KnockoutObservable<boolean>;
        name: KnockoutObservable<string>;
        useCls: KnockoutObservableArray<any>;
        useClsSelected: any;
        workSchedule: KnockoutObservableArray<any>;
        workScheduleSelected: any;
        units: KnockoutObservableArray<any>;
        unitSelected: any;
        calculatorItems: KnockoutObservableArray<CalculatorItem>;
        cbxAttribute: KnockoutObservableArray<any>;
        itemName: KnockoutObservable<string>;
        methods: KnockoutObservableArray<any>;
        cbxDisplayAtr: KnockoutObservableArray<any>;
        cbxTotal: KnockoutObservableArray<any>;
        cbxRounding: KnockoutObservableArray<any>;
        cbxFraction: KnockoutObservableArray<any>;
        allSelectedItems: KnockoutObservable<boolean>;
        
        constructor() {
            var self = this;
            
            self.allSelectedItems = ko.observable(false);
            
            self.settingItems = ko.observableArray([]);
            
            self.settingColumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KML002_6"), prop: 'code', width: 50 },
                { headerText: nts.uk.resource.getText("KML002_7"), prop: 'name', width: 200, formatter: _.escape }
            ]);
            
            self.singleSelectedCode = ko.observable("");
            
            self.code = ko.observable("");
            self.editMode = ko.observable(true);  
            self.name = ko.observable(""); 
            
            //A6_3 + A6_4
            self.useCls = ko.observableArray([
                { code: '0', name: "利用する" },
                { code: '1', name: "利用しない" }
            ]);
            
            self.useClsSelected = ko.observable(0); 
            
            //A3_10 + A3_11
            self.workSchedule = ko.observableArray([
                { code: '0', name: "含める" },
                { code: '1', name: "含めない" }
            ]);
            
            self.workScheduleSelected = ko.observable(0); 
            
            //A3_6 + A3_7
            self.units = ko.observableArray([
                { code: '0', name: "日別" },
                { code: '1', name: "時間帯別" }
            ]);
            
            self.unitSelected = ko.observable(0); 
            
            self.calculatorItems = ko.observableArray([]);
            
            self.cbxAttribute = ko.observableArray([
                { attrCode: 0, attrName: "金額" },
                { attrCode: 1, attrName: "時間" },
                { attrCode: 2, attrName: "人数" },
                { attrCode: 3, attrName: "数値" },
                { attrCode: 4, attrName: "平均単価" }
            ]);
            
            self.itemName = ko.observable("");
            
            self.methods = ko.observableArray([
                { methodCode: 0, methodName: nts.uk.resource.getText("KML002_25") },
                { methodCode: 1, methodName: nts.uk.resource.getText("KML002_26") }
            ]);
            
            self.cbxDisplayAtr = ko.observableArray([
                { displayAttrCode: 0, displayAttrName: nts.uk.resource.getText("KML002_21") },
                { displayAttrCode: 1, displayAttrName: nts.uk.resource.getText("KML002_22") }
            ]);
            
            self.cbxTotal = ko.observableArray([
                { totalCode: 0, totalName: nts.uk.resource.getText("KML002_22") },
                { totalCode: 1, totalName: nts.uk.resource.getText("KML002_23") }
            ]);
            
            self.cbxRounding = ko.observableArray([
                { roundingCode: 0, roundingName: nts.uk.resource.getText("KML002_23") },
                { roundingCode: 1, roundingName: nts.uk.resource.getText("KML002_24") }
            ]);
            
            self.cbxFraction = ko.observableArray([
                { fractionCode: 0, fractionName: nts.uk.resource.getText("KML002_24") },
                { fractionCode: 1, fractionName: nts.uk.resource.getText("KML002_25") }
            ]);
            
            $('#popup-area').ntsPopup({
                position: {
                    my: 'left top',
                    at: 'left bottom',
                    of: $('#toggle-popup')
                },
                dismissible: false
            });
            
            // Show or hide
            $('#toggle-popup').click(function () {
                $(this).siblings('#popup-area').ntsPopup('toggle');              
            });
            
            self.allSelectedItems.subscribe(function(value) {
                var items = [];
                if(value == true) {
                    for(var i = 0; i < self.calculatorItems().length; i++) {
                        var item : ICalculatorItem = {
                            isChecked: true,
                            itemCd: self.calculatorItems()[i].itemCd(),
                            attribute: self.calculatorItems()[i].attribute(),
                            itemName: self.calculatorItems()[i].itemName(),
                            settingMethod: self.calculatorItems()[i].settingMethod(),
                            formula: self.calculatorItems()[i].formula(),
                            displayAtr: self.calculatorItems()[i].displayAtr(),
                            total: self.calculatorItems()[i].total(),
                            rounding: self.calculatorItems()[i].rounding(),
                            fraction: self.calculatorItems()[i].fraction(),
                            order: self.calculatorItems()[i].order()
                        };
                        
                        items.push(new CalculatorItem(item));
                    }
                    
                    self.calculatorItems([]);
                    self.calculatorItems(items);
                } else {
                    //Just remove check all checkbox
                    var flag = self.checkSelectedItems();
                    
                    if(!flag) {
                        //Remove all selected checkbox
                        for(var i = 0; i < self.calculatorItems().length; i++) {
                            var item : ICalculatorItem = {
                                isChecked: false,
                                itemCd: self.calculatorItems()[i].itemCd(),
                                attribute: self.calculatorItems()[i].attribute(),
                                itemName: self.calculatorItems()[i].itemName(),
                                settingMethod: self.calculatorItems()[i].settingMethod(),
                                formula: self.calculatorItems()[i].formula(),
                                displayAtr: self.calculatorItems()[i].displayAtr(),
                                total: self.calculatorItems()[i].total(),
                                rounding: self.calculatorItems()[i].rounding(),
                                fraction: self.calculatorItems()[i].fraction(),
                                order: self.calculatorItems()[i].order()
                            };
                            
                            items.push(new CalculatorItem(item));
                        }
                        
                        self.calculatorItems([]);
                        self.calculatorItems(items);
                    }
                }
            });  
            
            //Bind data to from when user select item on grid
            self.singleSelectedCode.subscribe(function(value) {
                // clear all error
                nts.uk.ui.errors.clearAll();
                
                if(value.length > 0){
                    service.getVerticalCalSetByCode(value).done(function(data) {
                        var items = [];
                        
                        self.editMode(false);
                        self.code(data.verticalCalCd);
                        self.name(data.verticalCalName);
                        self.unitSelected(data.unit);
                        self.useClsSelected(data.useAtr);
                        self.workScheduleSelected(data.assistanceTabulationAtr);  
                        
                        for(var i = 0; i < data.verticalCalItems.length; i++) {
                            var item : ICalculatorItem = {
                                isChecked: false,
                                itemCd: data.verticalCalItems[i].itemId,
                                attribute: data.verticalCalItems[i].attributes,
                                itemName: data.verticalCalItems[i].itemName,
                                settingMethod: data.verticalCalItems[i].calculateAtr,
                                formula: "",
                                displayAtr: data.verticalCalItems[i].displayAtr,
                                total: 0,
                                rounding: data.verticalCalItems[i].rounding,
                                fraction: 0,
                                order: data.verticalCalItems[i].itemId
                            };
                            
                            items.push(new CalculatorItem(item));
                        }
                        
                        self.calculatorItems([]);
                        self.calculatorItems(items); 
                    }).fail(function(res) {
                          
                    });
                }
            });    
        }

        /**
         * Start page.
         */
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            
            $.when(self.getData()).done(function() {
                                
                if (self.settingItems().length > 0) {
                    self.singleSelectedCode(self.settingItems()[0].code);
                }
                
                self.bindCalculatorItems();
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
            self.settingItems([]);
            service.findAllVerticalCalSet().done(function(data) {
                _.forEach(data, function(item) {
                    self.settingItems.push(new SettingItemModel(item.verticalCalCd, item.verticalCalName));
                });
                
                dfd.resolve(data);
            }).fail(function(res) {
                dfd.reject(res);    
            });
            
            return dfd.promise();
        }
        
        /**
         * Check selected items to set value for all selected checkbox.
         */
        checkSelectedItems() {
            var self = this;
            var selectedItems = 0;
            
            for(var i = 0; i < self.calculatorItems().length; i++) {
                if(self.calculatorItems()[i].isChecked()) {
                    selectedItems = selectedItems + 1;
                }
            }
            
            if(selectedItems < self.calculatorItems().length && selectedItems > 0) {
                return true;
            } else {
                return false;
            }
        }
        
        /**
         * Bind calculator items by vertical cal set.
         */
        bindCalculatorItems() {
            var self = this;
            
            for(var i = 0; i < 1; i++) {
                var item : ICalculatorItem = {
                    isChecked: false,
                    itemCd: i + 1,
                    attribute: 0,
                    itemName: '',
                    settingMethod: 0,
                    formula: 'A + B + C',
                    displayAtr: 0,
                    total: 0,
                    rounding: 0,
                    fraction: 0,
                    order: i + 1
                };
                
                self.calculatorItems.push(new CalculatorItem(item));    
            }    
        }
        
        /**
         * Clear form data to new mode.
         */
        newBtn() {
            var self = this;
            
            self.singleSelectedCode("");
            self.code("");
            self.editMode(true);
            self.name("");
            self.unitSelected(0);
            self.useClsSelected(0);
            self.workScheduleSelected(0);
            self.calculatorItems([]);
        }
        
        /**
         * Add or Update data to db.
         */
        registrationBtn() {
            var self = this;
            
            // clear all error
            nts.uk.ui.errors.clearAll();
            
            // validate
            $(".input-code").trigger("validate");
            $(".input-name").trigger("validate");
            
            if (nts.uk.ui.errors.hasError()) {
                return;    
            }
            
            var code = self.code();
            var name = self.name();
            var unit = self.unitSelected();
            var useAtr = self.useClsSelected();
            var assistanceTabulationAtr = self.workScheduleSelected();
            var verticalCalItems = new Array<VerticalCalItemDto>();
            
            for(var i = 0; i < self.calculatorItems().length; i++) {
                var item = {
                    verticalCalCd: code,
                    itemId: self.calculatorItems()[i].itemCd(),
                    itemName: self.calculatorItems()[i].itemName(),
                    calculateAtr: self.calculatorItems()[i].settingMethod(),
                    displayAtr: self.calculatorItems()[i].displayAtr(),
                    cumulativeAtr: self.calculatorItems()[i].fraction(),
                    attributes: self.calculatorItems()[i].attribute(),
                    rounding: self.calculatorItems()[i].rounding(),
                    dispOrder: self.calculatorItems()[i].order()
                };
                
                verticalCalItems.push(item);
            }
            
            var data = new VerticalSettingDto(code, name, unit, useAtr, assistanceTabulationAtr, verticalCalItems);
            
            service.addVerticalCalSet(data).done(function() {
                nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                self.getData();
                self.singleSelectedCode(data.verticalCalCd);
            }).fail(function(error) {
                nts.uk.ui.dialog.alertError(error.message);
            }).always(function() {
                nts.uk.ui.block.clear();      
            });
        }
        
        /**
         * Open setting dialog.
         */
        settingBtn() {
            var self = this;
            
        }
        
        /**
         * Delete vertical cal set.
         */
        deleteBtn() {
            var self = this;
            
            let count = 0;
            for (let i = 0; i <= self.settingItems().length; i++){
                if(self.settingItems()[i].code == self.singleSelectedCode()){
                    count = i;
                    break;
                }
            }
            
            service.deleteVerticalCalSet(self.singleSelectedCode()).done(function() {
                self.getData().done(function(){
                    // if number of item from list after delete == 0 
                    if(self.settingItems().length==0){
                        self.newBtn();
                        return;
                    }
                    // delete the last item
                    if(count == ((self.settingItems().length))){
                        self.singleSelectedCode(self.settingItems()[count-1].code);
                        return;
                    }
                    // delete the first item
                    if(count == 0 ){
                        self.singleSelectedCode(self.settingItems()[0].code);
                        return;
                    }
                    // delete item at mediate list 
                    else if(count > 0 && count < self.settingItems().length){
                        self.singleSelectedCode(self.settingItems()[count].code);    
                        return;
                    }
                });
                
                nts.uk.ui.dialog.info({ messageId: "Msg_16" });
            }).fail(function(error) {
                nts.uk.ui.dialog.alertError(error.message);
            }).always(function() {
                nts.uk.ui.block.clear();      
            });
        }
        
        /**
         * Add new calculator item.
         */
        addLineBtn() {
            var self = this;
            
            var item : ICalculatorItem = {
                isChecked: false,
                itemCd: self.calculatorItems().length + 1,
                attribute: 0,
                itemName: '',
                settingMethod: 0,
                formula: 'A + B + C',
                displayAtr: 0,
                total: 0,
                rounding: 0,
                fraction: 0,
                order: self.calculatorItems().length + 1
            };
            
            self.calculatorItems.push(new CalculatorItem(item));
        }
        
        /**
         * Delete calculator item.
         */
        deleteLineBtn() {
            var self = this;
            var selectedItems = [];
            
            for(var i = 0; i < self.calculatorItems().length; i++) {
                if(!self.calculatorItems()[i].isChecked()) {
                    var item : ICalculatorItem = {
                        isChecked: self.calculatorItems()[i].isChecked(),
                        itemCd: i + 1,
                        attribute: self.calculatorItems()[i].attribute(),
                        itemName: self.calculatorItems()[i].itemName(),
                        settingMethod: self.calculatorItems()[i].settingMethod(),
                        formula: self.calculatorItems()[i].formula(),
                        displayAtr: self.calculatorItems()[i].displayAtr(),
                        total: self.calculatorItems()[i].total(),
                        rounding: self.calculatorItems()[i].rounding(),
                        fraction: self.calculatorItems()[i].fraction(),
                        order: i + 1
                    };
                    
                    selectedItems.push(new CalculatorItem(item));
                }
            }
            
            self.calculatorItems([]);
            
            for(var i = 0; i < selectedItems.length; i++) {
                var newItem : ICalculatorItem = {
                    isChecked: selectedItems[i].isChecked(),
                    itemCd: i + 1,
                    attribute: selectedItems[i].attribute(),
                    itemName: selectedItems[i].itemName(),
                    settingMethod: selectedItems[i].settingMethod(),
                    formula: selectedItems[i].formula(),
                    displayAtr: selectedItems[i].displayAtr(),
                    total: selectedItems[i].total(),
                    rounding: selectedItems[i].rounding(),
                    fraction: selectedItems[i].fraction(),
                    order: i + 1
                };
                
                self.calculatorItems.push(new CalculatorItem(newItem));
            }
            
            self.allSelectedItems(false);
        }
        
        /**
         * Get selected calculator items.
         */
        getSelectedCalculatorItems() {
            var self = this;
            var selectedItems = [];
            
            for(var i = 0; i < self.calculatorItems().length; i++) {
                if(self.calculatorItems()[i].isChecked()) {
                    selectedItems.push(self.calculatorItems()[i]);
                }
            }
            
            return selectedItems;
        }
        
        /**
         * Move up calculator item.
         */
        upBtn() {
            var self = this;
            
        }
        
        /**
         * Move down calculator item.
         */
        downBtn() {
            var self = this;
            
        }
        
        /**
         * Pass data to dialog.
         */
        passDataToDialogs(itemCd: number, attribute: number, itemName: string) {
            var self = this;
            var attrValue = "";
            
            if(attribute == 0) {
                attrValue = "金額";
            } else if(attribute == 1) {
                attrValue = "時間";
            } else if(attribute == 2) {
                attrValue = "人数";
            } else if(attribute == 3) {
                attrValue = "数値";
            } else if(attribute == 4) {
                attrValue = "平均単価";
            }
            
            var data = {
                verticalCalCd: self.code(),
                itemId: itemCd,
                attribute: attrValue,
                itemName: itemName
            };
            
            nts.uk.ui.windows.setShared("KML002_A_DATA", data);
        }
        
        /**
         * Check conditions to open dialog.
         */
        openDialog(itemCd: number, settingMethod: number, attribute: number, itemName: string) {
            var self = this;

            if(settingMethod == 1) {
                self.passDataToDialogs(itemCd, attribute, itemName);            
                nts.uk.ui.windows.sub.modal("/view/kml/002/b/index.xhtml").onClosed(() => {
                    
                }); 
            } else {
                if(attribute == 0) {
                    self.passDataToDialogs(itemCd, attribute, itemName);            
                    nts.uk.ui.windows.sub.modal("/view/kml/002/e/index.xhtml").onClosed(() => {
                        
                    }); 
                } else if(attribute == 1) {
                    self.passDataToDialogs(itemCd, attribute, itemName);
                    nts.uk.ui.windows.sub.modal("/view/kml/002/c/index.xhtml").onClosed(() => {
                    
                    }); 
                } else if(attribute == 2) {
                    self.passDataToDialogs(itemCd, attribute, itemName);
                    nts.uk.ui.windows.sub.modal("/view/kml/002/d/index.xhtml").onClosed(() => {
                    
                    }); 
                } else if(attribute == 3) {
                    self.passDataToDialogs(itemCd, attribute, itemName);
                    nts.uk.ui.windows.sub.modal("/view/kml/002/f/index.xhtml").onClosed(() => {
                    
                    }); 
                } else if(attribute == 4) {
                    self.passDataToDialogs(itemCd, attribute, itemName);
                    nts.uk.ui.windows.sub.modal("/view/kml/002/g/index.xhtml").onClosed(() => {
                    
                    }); 
                }
            }
        }
    }
    
    export class SettingItemModel {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;       
        }
    } 
    
    class VerticalSettingDto {
        verticalCalCd: string;
        verticalCalName: string;
        unit: number;
        useAtr: number;
        assistanceTabulationAtr: number; 
        verticalCalItems: Array<VerticalCalItemDto>;  
        
        constructor(verticalCalCd: string, verticalCalName: string, unit: number, useAtr: number, assistanceTabulationAtr: number, verticalCalItems: Array<VerticalCalItemDto>) {
            this.verticalCalCd = verticalCalCd;
            this.verticalCalName = verticalCalName;     
            this.unit = unit;
            this.useAtr = useAtr; 
            this.assistanceTabulationAtr = assistanceTabulationAtr;  
            this.verticalCalItems = verticalCalItems;
        }
    }
    
    class VerticalCalItemDto {
        verticalCalCd: string;
        itemId: string;
        itemName: string;
        calculateAtr: number;
        displayAtr: number;
        cumulativeAtr: number;
        attributes: number;
        rounding: number;
        dispOrder: number;
        
        constructor(verticalCalCd: string, itemId: string, itemName: string, calculateAtr: number, displayAtr: number, 
                cumulativeAtr: number, attributes: number, rounding: number, dispOrder: number) {
            this.verticalCalCd = verticalCalCd;
            this.itemId = itemId;     
            this.itemName = itemName;
            this.calculateAtr = calculateAtr;
            this.displayAtr = displayAtr;  
            this.cumulativeAtr = cumulativeAtr;  
            this.attributes = attributes;
            this.rounding = rounding;
            this.dispOrder = dispOrder;
        }
    }
    
    export class CalculatorItem {
        isChecked: KnockoutObservable<boolean>;
        itemCd: KnockoutObservable<string>;
        attribute: KnockoutObservable<number>;
        itemName: KnockoutObservable<string>;
        settingMethod: KnockoutObservable<number>;
        formula: KnockoutObservable<string>;
        displayAtr: KnockoutObservable<number>;
        total: KnockoutObservable<number>;
        rounding: KnockoutObservable<number>;
        fraction: KnockoutObservable<number>;
        order: KnockoutObservable<number>;
        
        constructor(param: ICalculatorItem) {
            var self = this;
            self.isChecked = ko.observable(param.isChecked);
            self.itemCd = ko.observable(param.itemCd);
            self.attribute = ko.observable(param.attribute);
            self.itemName = ko.observable(param.itemName);
            self.settingMethod = ko.observable(param.settingMethod);
            self.formula = ko.observable(param.formula);
            self.displayAtr = ko.observable(param.displayAtr);
            self.total = ko.observable(param.total);
            self.rounding = ko.observable(param.rounding);
            self.fraction = ko.observable(param.fraction);
            self.order = ko.observable(param.order);
            
            self.isChecked.subscribe(function(value) {
                if(!value) {
                    nts.uk.ui._viewModel.content.viewmodelA.allSelectedItems(false);
                }
            });    
        }
    }
        
    export interface ICalculatorItem {
        isChecked: boolean;
        itemCd: string;
        attribute: number;
        itemName: string;
        settingMethod: number;
        formula: string;
        displayAtr: number;
        total: number;
        rounding: number;
        fraction: number;
        order: number;
    }
}