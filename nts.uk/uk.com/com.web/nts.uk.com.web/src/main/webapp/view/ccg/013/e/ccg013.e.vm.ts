 module nts.uk.com.view.ccg013.e.viewmodel {  
    export class ScreenModel {  
        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;  
        allowOverwrite: KnockoutObservable<boolean>;  
        currentWebMenu: KnockoutObservable<any>;
        currentWebMenuCode: KnockoutObservable<string>;
        
        constructor() {
            var self = this;
            
            self.code = ko.observable('');
            self.name = ko.observable('');
            self.allowOverwrite = ko.observable(false);
            self.currentWebMenu = ko.observable(null);
            self.currentWebMenuCode = ko.observable('');
        }
        
        start() {
            var self = this;
             
            self.currentWebMenu(nts.uk.ui.windows.getShared("CCG013E_COPY"));
            self.currentWebMenuCode(self.currentWebMenu().webMenuCode());
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
                        
            var data = {
                currentWebMenuCode: self.currentWebMenuCode(),
                allowOverwrite: allowOverwrite,
                webMenuCode: code,
                webMenuName: name    
            }
            service.copy(data).done(function() {
                //    
            }).fail(function(error) {
                nts.uk.ui.dialog.alertError(error.message);
            });
            
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