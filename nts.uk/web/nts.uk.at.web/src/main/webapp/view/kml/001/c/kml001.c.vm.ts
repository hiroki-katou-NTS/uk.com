module nts.uk.at.view.kml001.c {
    export module viewmodel {
        import servicebase = kml001.shr.servicebase;
        import vmbase = kml001.shr.vmbase;
        export class ScreenModel {
            copyDataFlag: KnockoutObservable<boolean>;
            lastestStartDate: KnockoutObservable<string>;
            newStartDate: KnockoutObservable<string>;
            size: KnockoutObservable<number>;
            textKML001_47: KnockoutObservable<string>;
            constructor() {
                var self = this;
                self.copyDataFlag = ko.observable(true);
                self.lastestStartDate = ko.observable(nts.uk.ui.windows.getShared('lastestStartDate'));
                self.newStartDate = ko.observable(null);
                self.newStartDate.subscribe(function(value){
                    if((value != "") && (value != null)) {
                        if(self.errorStartDate(self.newStartDate().substring(0,10).replace('-','/').replace('-','/'))) $("#startDateInput-input").ntsError('set', {messageId:"Msg_65"});  
                        else $("#startDateInput-input").ntsError('clear');
                    } else {
                        $("#startDateInput-input").ntsError('set', {messageId:"Msg_65"});    
                    }     
                });
                self.size = ko.observable(nts.uk.ui.windows.getShared('size'));
                self.textKML001_47 = ko.observable(nts.uk.resource.getText('KML001_47',[self.lastestStartDate()]));
            }
            
            /**
             * check error on new input date
             */
            errorStartDate(input: string): boolean {
                var self = this;
                return ((input == "")|| (input == null) || vmbase.ProcessHandler.validateDateInput(input,self.lastestStartDate()));     
            }
            
            /**
             * process parameter and close dialog 
             */
            submitAndCloseDialog(): void {
                var self = this;
                if((self.newStartDate() == "") || (self.newStartDate() == null)) $("#startDateInput-input").ntsError('set', {messageId:"Msg_102"}); 
                else if(self.errorStartDate(self.newStartDate().substring(0,10).replace('-','/').replace('-','/'))) $("#startDateInput-input").ntsError('set', {messageId:"Msg_102"}); 
                else {
                    nts.uk.ui.windows.setShared('newStartDate', self.newStartDate().substring(0,10).replace('-','/').replace('-','/'));
                    nts.uk.ui.windows.setShared('copyDataFlag', self.copyDataFlag());
                    nts.uk.ui.windows.close(); 
                }
            }
            
            /**
             * close dialog and do nothing
             */
            closeDialog(): void {
                $("#startDateInput-input").ntsError('clear');
                nts.uk.ui.windows.close();   
            }
        }
    }
}