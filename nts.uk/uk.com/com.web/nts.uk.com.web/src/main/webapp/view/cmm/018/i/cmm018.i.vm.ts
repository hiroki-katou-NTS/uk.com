module nts.uk.com.view.cmm018.i {
    export module viewmodel {
        import vmbase = cmm018.shr.vmbase;
        import getShared = nts.uk.ui.windows.getShared;
        import setShared = nts.uk.ui.windows.setShared;
        export class ScreenModel {
            copyDataFlag: KnockoutObservable<boolean>;
            beginStartDate: KnockoutObservable<string>;
            newStartDate: KnockoutObservable<string>;
            item: KnockoutObservable<string>;
            dataSource: vmbase.IData_Param;
            constructor() {
                var self = this;
                self.copyDataFlag = ko.observable(true);
                self.dataSource = getShared('CMM018I_PARAM')||
                        {name: 'Hatake Kakashi',startDate: '2021-11-02', startDateOld: '9999-12-31', check: 1, mode: 0};
                self.item = ko.observable(self.dataSource.name);
                self.beginStartDate = ko.observable(self.dataSource.startDate);
                self.newStartDate = ko.observable(null);
            }
            
            /**
             * process parameter and close dialog 
             */
            submitAndCloseDialog(): void {
                var self = this;
                if(!vmbase.ProcessHandler.validateDateInput(self.newStartDate(),self.beginStartDate())){
                    $("#startDateInput").ntsError('set', {messageId:"Msg_153"});
                    return;
                }
                let data: vmbase.IData = new vmbase.IData(self.newStartDate(), self.beginStartDate(), self.dataSource.check, self.dataSource.mode, self.copyDataFlag());
                setShared('CMM018I_DATA', data);
                console.log(data);
                nts.uk.ui.windows.close(); 
            }
            
            /**
             * close dialog and do nothing
             */
            closeDialog(): void {
                $("#startDateInput").ntsError('clear');
                setShared('CMM018I_DATA', null);
                nts.uk.ui.windows.close();   
            }

        }

    }
}