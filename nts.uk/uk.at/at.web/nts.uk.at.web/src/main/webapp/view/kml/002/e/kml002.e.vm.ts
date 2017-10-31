module nts.uk.at.view.kml002.e.viewmodel {
    export class ScreenModel {
        columns: KnockoutObservable<any>;
        currentCodeListB: KnockoutObservableArray<any>;
        itemsB: KnockoutObservableArray<ItemModel>;
        checked: KnockoutObservable<boolean>;
        rightItemcolumns: KnockoutObservable<any>;
        currentRightCodeListB: KnockoutObservableArray<any>;
        rightItemsB: KnockoutObservableArray<NewItemModel>;
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
        
        constructor() {
            var self = this;
            
            var data = nts.uk.ui.windows.getShared("KML002_A_DATA");
            
            self.attrLabel = ko.observable(data.attribute);
            self.itemNameLabel = ko.observable(data.itemName);
            
            self.itemsB = ko.observableArray([]);
            self.rightItemsB = ko.observableArray([]);
            
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KML002_7"), prop: 'name', width: 180, formatter: _.escape }
            ]);
            
            self.currentCodeListB = ko.observableArray([]);
            
            self.rightItemcolumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KML002_36"), prop: 'adOrSub', width: 80 },
                { headerText: nts.uk.resource.getText("KML002_7"), prop: 'name', width: 160, formatter: _.escape }
            ]);
            
            self.currentRightCodeListB = ko.observableArray([]);
            
            self.checked = ko.observable(true);
            
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
            
            if(self.selectedMethod() == 0) {
                $('.method-a').show();
                $('.method-b').hide();
            } else {
                $('.method-a').hide();
                $('.method-b').show();
            }
            
            self.selectedMethod.subscribe(function(value) {
                if(value == 0){
                    $('.method-a').show();
                    $('.method-b').hide();
                } else {
                    $('.method-a').hide();
                    $('.method-b').show();
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
        }

        /**
         * Start page.
         */
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            
            
            
            dfd.resolve();
            return dfd.promise();
        }
        
        submit() {
            var self = this;
            
        }
        
        cancel() {
            var self = this;
            
        }
    }
    
    class ItemModel {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;       
        }
    } 
    
    class NewItemModel {
        code: string;
        adOrSub: string;
        name: string;
        constructor(code: string, adOrSub: string, name: string) {
            this.code = code;
            this.adOrSub = adOrSub;
            this.name = name;       
        }
    } 
}