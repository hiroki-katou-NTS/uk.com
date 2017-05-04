module ccg030.b.viewmodel {
      export class ScreenModel {
    
        constructor() {
               var self = this;            
         
           
        }
        
        startPage(): JQueryPromise<any> {
            var self = this;
            
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }
          closeDialog(): any {
            nts.uk.ui.windows.close();
        }
    }

}