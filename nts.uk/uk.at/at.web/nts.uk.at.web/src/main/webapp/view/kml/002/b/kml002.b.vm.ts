module nts.uk.at.view.kml002.b.viewmodel {
    export class ScreenModel {
        radioForm: KnockoutObservableArray<any>;
        selectedLeftId: KnockoutObservable<number>;
        selectedRightId: KnockoutObservable<number>;
        projectItem: KnockoutObservableArray<any>;
        selectedItem1: KnockoutObservable<any>;
        selectedItem2: KnockoutObservable<any>;
        numerical1: KnockoutObservable<number>;
        numerical2: KnockoutObservable<number>;
        operatorItem: KnockoutObservableArray<any>;
        selectedOperator: KnockoutObservable<any>;
        enableProjectItem1: KnockoutObservable<boolean>;
        enableProjectItem2: KnockoutObservable<boolean>;
        enableNumerical1: KnockoutObservable<boolean>;
        enableNumerical2: KnockoutObservable<boolean>;
        
        constructor() {
            var self = this;
            
            self.radioForm = ko.observableArray([
                { id: 0, name: nts.uk.resource.getText("KML002_25") },
                { id: 1, name: nts.uk.resource.getText("KML002_152") }
            ]);
            
            self.selectedLeftId = ko.observable(0);            
            self.selectedRightId = ko.observable(0);
            
            self.projectItem = ko.observableArray([
                { itemCode: 0, itemName: '123456789012345' },
                { itemCode: 1, itemName: '123456789012345' }
            ]);
            
            self.selectedItem1 = ko.observable(0);
            self.selectedItem2 = ko.observable(0);
            
            self.numerical1 = ko.observable(0);
            self.numerical2 = ko.observable(0);
            
            self.operatorItem = ko.observableArray([
                { operatorCode: 0, operatorName: 'X' },
                { operatorCode: 1, operatorName: ':' }
            ]);
            
            self.selectedOperator = ko.observable(0);
            
            self.enableProjectItem1 = ko.observable(true);
            self.enableProjectItem2 = ko.observable(true);
            self.enableNumerical1 = ko.observable(false);
            self.enableNumerical2 = ko.observable(false);
            
            self.selectedLeftId.subscribe(function(value) {
                if(value == 0){
                    self.enableProjectItem1(true);
                    self.enableNumerical1(false);
                } else {
                    self.enableProjectItem1(false);
                    self.enableNumerical1(true);
                }
            });  
            
            self.selectedRightId.subscribe(function(value) {
                if(value == 0){
                    self.enableProjectItem2(true);
                    self.enableNumerical2(false);
                } else {
                    self.enableProjectItem2(false);
                    self.enableNumerical2(true);
                }
            }); 
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