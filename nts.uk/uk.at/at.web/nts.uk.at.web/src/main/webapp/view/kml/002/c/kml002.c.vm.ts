module nts.uk.at.view.kml002.c.viewmodel {
    export class ScreenModel {
        columns: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        items: KnockoutObservableArray<ItemModel>;
        categoryItems: KnockoutObservableArray<any>;
        catCode: KnockoutObservable<number>;
        checked: KnockoutObservable<boolean>;
        rightItemcolumns: KnockoutObservable<any>;
        currentRightCodeList: KnockoutObservableArray<any>;
        rightItems: KnockoutObservableArray<NewItemModel>;
        
        constructor() {
            var self = this;
            
            self.items = ko.observableArray([]);
            self.rightItems = ko.observableArray([]);
            
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KML002_7"), prop: 'name', width: 180, formatter: _.escape }
            ]);
            
            self.currentCodeList = ko.observableArray([]);
            
            self.categoryItems = ko.observableArray([
                { catCode: 0, catName: nts.uk.resource.getText("KML002_29") },
                { catCode: 1, catName: nts.uk.resource.getText("KML002_32") }
            ]);
            
            self.rightItemcolumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KML002_36"), prop: 'adOrSub', width: 80 },
                { headerText: nts.uk.resource.getText("KML002_7"), prop: 'name', width: 160, formatter: _.escape }
            ]);
            
            self.currentRightCodeList = ko.observableArray([]);
            
            self.catCode = ko.observable(0);
            
            self.checked = ko.observable(true);
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