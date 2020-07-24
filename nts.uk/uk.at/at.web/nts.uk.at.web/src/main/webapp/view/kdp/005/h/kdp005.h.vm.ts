module nts.uk.at.view.kdp005.h {
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    
	export module viewmodel {
        enum StatusDevice {
            PreparingToRead = 1 /**読み込み準備中*/,
            Readable = 2  /**読み込み可能*/,
            Unreadable = 3  /**読み込み不可*/,
            DeviceDoesNotExist = 4/**機器が存在しない*/
        }
        
		export class ScreenModel {
            value = ko.observable('');
            notify = ko.observable(getText('KDP005_7'));
            color = ko.observable('');
            inforAuthent = ko.observable('');
            statusDevice = ko.observable(StatusDevice.PreparingToRead);
			constructor() {
				let self = this;
                self.statusDevice.subscribe(function(newValue) {
                    if(newValue == StatusDevice.PreparingToRead) {
                        self.color('');    
                        self.notify(getText('KDP005_7'));
                        self.inforAuthent('');
                    }else if(newValue == StatusDevice.Readable) {
                        self.color('#0033cc');    
                        self.notify(getText('KDP005_5'));
                        self.inforAuthent('');
                    }else if(newValue == StatusDevice.Unreadable) {
                        self.color('#ff0000');
                        self.notify(getText('KDP005_6'));
                        self.inforAuthent('');
                    }else if(newValue == StatusDevice.DeviceDoesNotExist){
                        self.color('#ff0000');
                        self.notify(getText('KDP005_6'));
                        self.inforAuthent(getText('KDP005_4'));
                    }
                    self.decision();
                });
                self.value.subscribe(function(newValue) {
                    self.decision();
                });
                $(document).ready(function() {
                    $('#iCCard').focus();
                });
            }
            
            private decision(){
                let self = this; 
                if(!nts.uk.ui.errors.hasError() && self.value() != '' && self.statusDevice() == StatusDevice.Readable){
                     setShared('ICCard', self.value());
                     self.closeDialog();  
                }
            }     
            
            public closeDialog(): void {
				nts.uk.ui.windows.close();
			}
        }
    }
}