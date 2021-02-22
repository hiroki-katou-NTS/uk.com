module nts.uk.at.view.ksu005.c {
    import getText = nts.uk.resource.getText;
    import getShared = nts.uk.ui.windows.getShared;

    const Paths = {        
        COPY_SCHEDULE_TABLE_OUTPUT_SETTING:"ctx/at/schedule/scheduletable/copy"
    };

    @bean()
    class Ksu005cViewModel extends ko.ViewModel {
        copySourceCode: KnockoutObservable<string> = ko.observable('');
        copySourceName: KnockoutObservable<string> = ko.observable('');
        newCode:KnockoutObservable<string> = ko.observable('');
        newName:KnockoutObservable<string> = ko.observable('');
      
        constructor(params: any) {
            super();
            const self = this;
            // self.copySourceCode = ko.observable("03");
            // self.copySourceName = ko.observable("切り捨て");
            // self.newCode = ko.observable("04");
            // self.newName = ko.observable("切り捨て04");
            self.loadData();
        }

        loadData(): void {
            const self = this;
            let data = getShared('dataShareKSU005b');
            self.copySourceCode(data.copySourceCode);
            self.copySourceName(data.copySourceName);
        }

        copy(): void {
            const self = this;
            self.$blockui('invisible');
            // if (self.validateAll()) {
            //     return;
            // }

            let request: any = {
				copySourceCode : self.copySourceCode(),
                newCode : self.newCode(),
                newName: self.newName()
			}
            self.$ajax(Paths.COPY_SCHEDULE_TABLE_OUTPUT_SETTING, request).done(() => {
                self.$dialog.info({messageId: "Msg_15"});
                self.$blockui('hide');
            })
            .always(() => {
                self.$blockui('hide');
            });
        }
        closeDialog(): void {
            const vm = this;
            vm.$window.close();
        }
    }
}