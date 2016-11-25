module qmm019.e.viewmodel {
    
    export class ScreenModel {
        itemName: KnockoutObservable<string>;
        currentLayoutAtr: KnockoutObservable<number>
        selectedLayoutAtr: KnockoutObservable<number>;
        selectedCodes: KnockoutObservableArray<string>;
        isEnable: KnockoutObservable<boolean>;
        selectLayoutCode: KnockoutObservable<string>;
        
        
         // start function
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            dfd.resolve();
            
            
            // Return.
            return dfd.promise();    
        }
    }
}