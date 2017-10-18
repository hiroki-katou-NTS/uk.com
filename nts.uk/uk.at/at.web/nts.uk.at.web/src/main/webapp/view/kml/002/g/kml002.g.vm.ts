module nts.uk.at.view.kml002.g.viewmodel {
    export class ScreenModel {
        unitPriceItems: KnockoutObservableArray<any>;
        uPCd: KnockoutObservable<number>;
        radioMethod: KnockoutObservableArray<any>;
        selectedMethod: KnockoutObservable<number>;
        attrLabel: KnockoutObservable<String>;
        itemNameLabel: KnockoutObservable<String>;
        
        constructor() {
            var self = this;
            
            var data = nts.uk.ui.windows.getShared("KML002_A_DATA");
            
            self.attrLabel = ko.observable(data.attribute);
            self.itemNameLabel = ko.observable(data.itemName);
            
            self.unitPriceItems = ko.observableArray([
                { uPCd: 0, uPName: nts.uk.resource.getText("KML002_53") },
                { uPCd: 1, uPName: nts.uk.resource.getText("KML002_54") },
                { uPCd: 2, uPName: nts.uk.resource.getText("KML002_55") },
                { uPCd: 3, uPName: nts.uk.resource.getText("KML002_56") },
                { uPCd: 4, uPName: nts.uk.resource.getText("KML002_57") }
            ]);
            
            self.uPCd = ko.observable(0);
            
            self.radioMethod = ko.observableArray([
                { id: 0, name: nts.uk.resource.getText("KML002_62") },
                { id: 1, name: nts.uk.resource.getText("KML002_63") },
                { id: 2, name: nts.uk.resource.getText("KML002_64") }
            ]);
            
            self.selectedMethod = ko.observable(0); 
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
}