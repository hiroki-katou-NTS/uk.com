 module nts.uk.com.view.ccg013.e.viewmodel {  
    export class ScreenModel {  
        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;  
        allowOverwrite: KnockoutObservable<boolean>;  
        currentWebMenu: KnockoutObservable<any>;
        
        constructor() {
            var self = this;
            
            self.code = ko.observable('');
            self.name = ko.observable('');
            self.allowOverwrite = ko.observable(false);
            self.currentWebMenu = ko.observable(null);
        }
        
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
             
            self.currentWebMenu(nts.uk.ui.windows.getShared("CCG013E_COPY"));
            
            dfd.resolve();   
            return dfd.promise();
        }
        
        /**
         * Pass data to main screen
         * Close the popup
         */
        submit() {
            var self = this;
            var code = self.code();
            var name = self.name();
            var allowOverwrite = self.allowOverwrite();
            
            //Get data by code
            
            
            self.closeDialog();
        }
        
        /**
         * Click on button i1_9
         * Close the popup
         */
        closeDialog() {
            nts.uk.ui.windows.close();
        }
    }
 }