 module nts.uk.com.view.ccg013.i.viewmodel {  
    export class ScreenModel {    
        textColor: KnockoutObservable<string>;
        bgColor: KnockoutObservable<string>;
        nameMenuBar: KnockoutObservable<string>;
        
        constructor() {
            var self = this;
            
            self.textColor = ko.observable('');
            self.bgColor = ko.observable('');
            self.nameMenuBar = ko.observable('');
            
            //Get data and fill to popup
            var menuBar = nts.uk.ui.windows.getShared("CCG013I_MENU_BAR");
            
            if(menuBar != undefined){
                self.nameMenuBar(menuBar.menuText);
                self.textColor(menuBar.textColor);
                self.bgColor(menuBar.backgroundColor);                
            }
        }
        
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            
            dfd.resolve();   
            return dfd.promise();
        }
        
        /**
         * Pass data to main screen
         * Close the popup
         */
        submit() {
            var self = this;
            
            //Set data
            var menuBar = {
                menuText: self.nameMenuBar(),
                textColor: self.textColor(),
                backgroundColor: self.bgColor()    
            }
            
            nts.uk.ui.windows.setShared("CCG013I_MENU_BAR", menuBar);
            
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