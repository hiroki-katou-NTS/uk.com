module nts.uk.at.view.kdp005.h {
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import error = nts.uk.ui.dialog.error;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal = nts.uk.ui.windows.sub.modal;
    
	export module viewmodel {
		export class ScreenModel {
            value = ko.observable('');
            notify = ko.observable(getText('KDP005_7'));
            color = ko.observable('');
            inforAuthent = ko.observable(getText('KDP005_4'));
            typeAuthent: number;
			constructor() {
				let self = this;
                self.typeAuthent = getShared('TypeAuthent');
                self.value.subscribe(function(newValue) {
                    if(newValue){
                        self.color('#0033cc');    
                        self.notify(getText('KDP005_5'));
                        self.inforAuthent(getText(''));
                    }else{
                        self.color('#ff0000');
                        self.notify(getText('KDP005_6'));
                        self.inforAuthent(getText('KDP005_4'));
                    }
                });
			}
            
			public proceed() {
				let self = this;
                self.closeDialog();
//                block.grayout();
//                service.proceed().done(function() {
//                    info({ messageId: "Msg_15" }).then(()=>{
//                    });
//                }).fail(function (res) {
//                    error({ messageId: res.messageId });
//                }).always(function () {
//                    block.clear();
//                });
                modal('at', '/view/kdp/005/i/index.xhtml');
			}
            
            public closeDialog(): void {
				nts.uk.ui.windows.close();
			}
        }
    }
}