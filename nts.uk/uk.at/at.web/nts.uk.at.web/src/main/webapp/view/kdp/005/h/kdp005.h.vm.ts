module nts.uk.at.view.kdp005.h {
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    
	export module viewmodel {
		export class ScreenModel {
            value = ko.observable('');
            notify = ko.observable(getText('KDP005_7'));
            color = ko.observable('');
            inforAuthent = ko.observable(getText('KDP005_4'));
			constructor() {
				let self = this;
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
                $(document).ready(function() {
                    $('#iCCard').focus();
                });
            }
			public proceed() {
				let self = this;
                setShared('ICCard', self.value());
                self.closeDialog();
			}
            
            public closeDialog(): void {
				nts.uk.ui.windows.close();
			}
        }
    }
}