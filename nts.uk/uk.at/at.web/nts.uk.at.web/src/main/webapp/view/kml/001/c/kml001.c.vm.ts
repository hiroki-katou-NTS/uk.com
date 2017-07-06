module nts.uk.at.view.kml001.c {
    export module viewmodel {
        import servicebase = kml001.shr.servicebase;
        import vmbase = kml001.shr.vmbase;
        export class ScreenModel {
            copyDataFlag: KnockoutObservable<boolean>;
            lastStartDate: KnockoutObservable<string>;
            beginStartDate: KnockoutObservable<string>;
            newStartDate: KnockoutObservable<string>;
            size: KnockoutObservable<number>;
            textKML001_47: KnockoutObservable<string>;
            constructor() {
                var self = this;
                self.copyDataFlag = ko.observable(true);
                self.lastStartDate = ko.observable(nts.uk.ui.windows.getShared('lastestStartDate'));
                self.beginStartDate = ko.observable(vmbase.ProcessHandler.getOneDayAfter(self.lastStartDate()));
                self.newStartDate = ko.observable(null);
                self.size = ko.observable(nts.uk.ui.windows.getShared('size'));
                self.textKML001_47 = ko.observable(nts.uk.resource.getText('KML001_47',[self.lastStartDate()]));
            }
            
            /**
             * process parameter and close dialog 
             */
            submitAndCloseDialog(): void {
                var self = this;
                if(!vmbase.ProcessHandler.validateDateInput(self.newStartDate(),self.beginStartDate())){
                    $("#startDateInput").ntsError('set', {messageId:"Msg_102"});
                } else {
                    nts.uk.ui.windows.setShared('newStartDate', self.newStartDate());
                    nts.uk.ui.windows.setShared('copyDataFlag', self.copyDataFlag());
                    nts.uk.ui.windows.close(); 
                }
            }
            
            /**
             * close dialog and do nothing
             */
            closeDialog(): void {
                $("#startDateInput").ntsError('clear');
                nts.uk.ui.windows.close();   
            }
        }
    }
}