module nts.uk.at.view.kmf002.c {
    
    export module viewmodel {
        export class ScreenModel {
        
            textScreenA: KnockoutObservable<string>; 

            constructor(){
                let _self = this;
                _self.textScreenA = ko.observable(_.random(0, 100));
              
            
            }
            
            /**
             * init default data when start page
             */
            public start_page(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                var _self = this;
//               alert("OK_C_start page");
                _self.textScreenA(_.random(0, 100));
                dfd.resolve();
                return dfd.promise();
            }
            
            public screenC(): void{
               alert("OK_C function screen"); 
            }
       }      
    }
}