module nts.uk.at.view.kmk011.f {
    
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    
    export module viewmodel {
        export class ScreenModel {
            //date range
            enableDateRange: KnockoutObservable<boolean>;
            requiredDateRange: KnockoutObservable<boolean>;
            dateValue: KnockoutObservable<any>;
            startDateString: KnockoutObservable<string>;
            endDateString: KnockoutObservable<string>;
            
            // radio group
            itemListRadio: KnockoutObservableArray<any>;
            selectedId: KnockoutObservable<number>;
            enableRadio: KnockoutObservable<boolean>;
            
            constructor(){
                let _self = this;
                
                //date range
                _self.enableDateRange = ko.observable(true);
                _self.requiredDateRange = ko.observable(true);
                
                _self.startDateString = ko.observable("");
                _self.endDateString = ko.observable("");
                _self.dateValue = ko.observable({});
                
                _self.startDateString.subscribe(function(value){
                    _self.dateValue().startDate = value;
                    _self.dateValue.valueHasMutated();        
                });
                
                _self.endDateString.subscribe(function(value){
                    _self.dateValue().endDate = value;   
                    _self.dateValue.valueHasMutated();      
                });
                
                //radio group
                _self.itemListRadio = ko.observableArray([
                    new BoxModel(1, nts.uk.resource.getText("KMK011_73")),
                    new BoxModel(2, nts.uk.resource.getText("KMK011_29"))
                ]);
                _self.selectedId = ko.observable(1);
                _self.enableRadio = ko.observable(true);
                
            }
            
            public start_page() : JQueryPromise<any> {
                let _self = this;
                var dfd = $.Deferred<any>();
                
                dfd.resolve();
                return dfd.promise();
            }
            
            public execution() : void {
                 let _self = this;
            }
            
            // close modal
            public close() : void {
                nts.uk.ui.windows.close();    
            }
        }
        
        export class BoxModel {
            id: number;
            name: string;
            constructor(id: number, name: string){
                var self = this;
                self.id = id;
                self.name = name;
            }
        }
    }
}