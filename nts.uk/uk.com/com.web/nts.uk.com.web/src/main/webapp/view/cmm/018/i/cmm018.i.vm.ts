module nts.uk.com.view.cmm018.i {
    export module viewmodel {
        import vmbase = cmm018.shr.vmbase;
        import getShared = nts.uk.ui.windows.getShared;
        import setShared = nts.uk.ui.windows.setShared;
        export class ScreenModel {
            copyDataFlag: KnockoutObservable<boolean>;
            lastStartDate: KnockoutObservable<string>;
            beginStartDate: KnockoutObservable<string>;
            newStartDate: KnockoutObservable<string>;
            size: KnockoutObservable<number>;
            item: KnockoutObservable<string> = ko.observable('項目移送');
            constructor() {
                var self = this;
                self.copyDataFlag = ko.observable(true);
                let a = getShared('lastestStartDate')|| '2017-05-06'
                self.lastStartDate = ko.observable(a);
                self.beginStartDate = ko.observable(vmbase.ProcessHandler.getOneDayAfter(self.lastStartDate()));
                self.newStartDate = ko.observable(null);
                self.size = ko.observable(nts.uk.ui.windows.getShared('size'));
            }
            
            /**
             * process parameter and close dialog 
             */
            submitAndCloseDialog(): void {
                var self = this;
                if(!vmbase.ProcessHandler.validateDateInput(self.newStartDate(),self.beginStartDate())){
                    $("#startDateInput").ntsError('set', {messageId:"Msg_102"});
                } else {
                    setShared('newStartDate', self.newStartDate());
                    setShared('copyDataFlag', self.copyDataFlag());
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