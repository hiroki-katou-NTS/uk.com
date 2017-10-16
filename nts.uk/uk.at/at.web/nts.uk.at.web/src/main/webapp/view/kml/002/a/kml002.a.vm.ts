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
        selectedItem: KnockoutObservable<boolean>;
        
        constructor() {
            var self = this;
            
            self.allSelectedItems = ko.observable(false);
            self.selectedItem = ko.observable(false);
            
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
                { displayAttrCode: 1, displayAttrName: nts.uk.resource.getText("KML002_21") }
            ]);
            
            self.cbxTotal = ko.observableArray([
                { totalCode: 0, totalName: nts.uk.resource.getText("KML002_22") },
                { totalCode: 1, totalName: nts.uk.resource.getText("KML002_22") }
            ]);
            
            self.cbxRounding = ko.observableArray([
                { roundingCode: 0, roundingName: nts.uk.resource.getText("KML002_23") },
                { roundingCode: 1, roundingName: nts.uk.resource.getText("KML002_23") }
            ]);
            
            self.cbxFraction = ko.observableArray([
                { fractionCode: 0, fractionName: nts.uk.resource.getText("KML002_24") },
                { fractionCode: 1, fractionName: nts.uk.resource.getText("KML002_24") }
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
                if(value == true) {
                    self.selectedItem(true);
                } else {
                    self.selectedItem(false);
                }
            });  
        }

        /**
         * Start page.
         */
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            
            self.bindCalculatorItems();
            
            dfd.resolve();
            return dfd.promise();
        }
        
        bindCalculatorItems() {
            var self = this;
            
            for(var i = 0; i < 1; i++) {
                var item : ICalculatorItem = {
                    itemCd: i,
                    attribute: 0,
                    itemName: 'test 0' + i,
                    settingMethod: 0,
                    formula: '{0} + {1} + {2}',
                    displayAtr: 0,
                    total: 0,
                    rounding: 0,
                    fraction: 0,
                };
                
                self.calculatorItems.push(new CalculatorItem(item));    
            }    
        }
        
        newBtn() {
            var self = this;
            
        }
        
        registrationBtn() {
            var self = this;
            
        }
        
        settingBtn() {
            var self = this;
            
        }
        
        deleteBtn() {
            var self = this;
            
        }
        
        addLineBtn() {
            var self = this;
            
            var item : ICalculatorItem = {
                itemCd: self.calculatorItems().count() + 1,
                attribute: 0,
                itemName: 'test 0' + self.calculatorItems().count() + 1,
                settingMethod: 0,
                formula: '{0} + {1} + {2}',
                displayAtr: 0,
                total: 0,
                rounding: 0,
                fraction: 0,
            };
            
            self.calculatorItems.push(new CalculatorItem(item));
        }
        
        deleteLineBtn() {
            var self = this;
            
        }
        
        formulaSettingDialog() {
            var self = this;
            
        }
        
        upBtn() {
            var self = this;
            
        }
        
        downBtn() {
            var self = this;
            
        }
        
        passDataToDialogs() {
            var self = this;
            
            var data = {
                verticalCalCd: self.code(),
                itemId: "01",
                attribute: "1",
                itemName: "Test 01"
            };
            
            nts.uk.ui.windows.setShared("KML002_A_DATA", data);
        }
        
        openDialog(conditionNo: number) {
            var self = this;

            if(conditionNo == 1) {
                self.passDataToDialogs;            
                nts.uk.ui.windows.sub.modal("/view/kml/002/b/index.xhtml").onClosed(() => {
                    
                }); 
            } else if(conditionNo == 2) {
                self.passDataToDialogs();
                nts.uk.ui.windows.sub.modal("/view/kml/002/c/index.xhtml").onClosed(() => {
                
                }); 
            } else if(conditionNo == 3) {
                self.passDataToDialogs();
                nts.uk.ui.windows.sub.modal("/view/kml/002/d/index.xhtml").onClosed(() => {
                
                }); 
            } else if(conditionNo == 4) {
                self.passDataToDialogs();
                nts.uk.ui.windows.sub.modal("/view/kml/002/e/index.xhtml").onClosed(() => {
                
                }); 
            } else if(conditionNo == 5) {
                self.passDataToDialogs();
                nts.uk.ui.windows.sub.modal("/view/kml/002/f/index.xhtml").onClosed(() => {
                
                }); 
            } else if(conditionNo == 6) {
                self.passDataToDialogs();
                nts.uk.ui.windows.sub.modal("/view/kml/002/g/index.xhtml").onClosed(() => {
                
                }); 
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
    
    export class CalculatorItem {
        itemCd: KnockoutObservable<string>;
        attribute: KnockoutObservable<number>;
        itemName: KnockoutObservable<string>;
        settingMethod: KnockoutObservable<number>;
        formula: KnockoutObservable<string>;
        displayAtr: KnockoutObservable<number>;
        total: KnockoutObservable<number>;
        rounding: KnockoutObservable<number>;
        fraction: KnockoutObservable<number>;
        
        constructor(param: ICalculatorItem) {
            var self = this;
            self.itemCd = ko.observable(param.itemCd);
            self.attribute = ko.observable(param.attribute);
            self.itemName = ko.observable(param.itemName);
            self.settingMethod = ko.observable(param.settingMethod);
            self.formula = ko.observable(param.formula);
            self.displayAtr = ko.observable(param.displayAtr);
            self.total = ko.observable(param.total);
            self.rounding = ko.observable(param.rounding);
            self.fraction = ko.observable(param.fraction);
        }
    }
        
    export interface ICalculatorItem {
        itemCd: string;
        attribute: number;
        itemName: string;
        settingMethod: number;
        formula: string;
        displayAtr: number;
        total: number;
        rounding: number;
        fraction: number;
    }
}