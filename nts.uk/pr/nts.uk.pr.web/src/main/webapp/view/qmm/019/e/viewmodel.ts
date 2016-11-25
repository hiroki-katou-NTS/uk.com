module qmm019.e.viewmodel {
    
    export class ScreenModel {
        selectedLayoutAtr: KnockoutObservable<number>;
        selectLayoutCode: KnockoutObservable<string>;
        
        
         // start function
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            dfd.resolve();
            
            
            // Return.
            return dfd.promise();    
        }
        
        startDiaglo(): any{
                
        }
    }
}