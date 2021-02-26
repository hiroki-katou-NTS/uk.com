module nts.uk.at.view.ksu005.c {
    import getText = nts.uk.resource.getText;
    import setShare = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    const Paths = {        
        COPY_SCHEDULE_TABLE_OUTPUT_SETTING:"ctx/at/schedule/scheduletable/copy"
    };

    @bean()
    class Ksu005cViewModel extends ko.ViewModel {
        currentScreen: any = null;
        copySourceCode: KnockoutObservable<string> = ko.observable('');
        copySourceName: KnockoutObservable<string> = ko.observable('');
        newCode:KnockoutObservable<string> = ko.observable('');
        newName:KnockoutObservable<string> = ko.observable('');
      
        constructor() {
            super();
            const self = this;            
            self.loadData();
            $('#outputSettingCopyCode').focus();
        }

        loadData(): void {
            const self = this;
            let data = getShared('dataShareKSU005b');
            $('#outputSettingCopyCode').focus(); 
            self.copySourceCode(data.copySourceCode);
            self.copySourceName(data.copySourceName);
            
        }

        copy(): void {
            const self = this;                       
            if (self.validateAll()) {
                return;
            }

            let request: any = {
				copySourceCode : self.copySourceCode(),
                newCode : self.newCode(),
                newName: self.newName()
			}
            self.$blockui('invisible');  
            self.$ajax(Paths.COPY_SCHEDULE_TABLE_OUTPUT_SETTING, request).done(() => {
                self.$dialog.info({messageId: "Msg_15"}).then(function() {
                    self.closeDialog();
                    setShare('dataShareKSU005c', self.newCode());
                });                 

            })
            .always(() => {
                self.$blockui('hide');
            });
            self.$blockui('hide');
        }

        private validateAll(): boolean {
            $('#outputSettingCopyCode').ntsEditor('validate');
            $('#outputSettingCopyName').ntsEditor('validate');         
            if (nts.uk.ui.errors.hasError()) {                    
                return true;
            }
            return false;
        }
        closeDialog(): void {
            const vm = this;
            vm.$window.close();
        }
    }
}