module nts.uk.at.view.kdp005.h {
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import device = nts.uk.devices;
    
	export module viewmodel {
		export class ScreenModel {
            value = ko.observable('');
            notify = ko.observable(getText('KDP005_21'));
            color = ko.observable('#ff0000');
            inforAuthent = ko.observable('');
            diplayBtnConnect = ko.observable(true);
			constructor() {
				let self = this;
                $(document).ready(function() {
                    $('#iCCard').focus();
                });
                self.connectICCard();
            }
            
            public connectICCard(){
                let self = this;
                device.felica((command: device.COMMAND, readyRead: boolean, cardNo: string) => {
                    self.value();
                    if(command === 'disconnect' || (command === 'status' && readyRead == false)){
                        self.color('#ff0000');
                        self.notify(getText('KDP005_6'));
                        self.inforAuthent(getText('KDP005_4'));
                        self.diplayBtnConnect(true);
                    }else if(command === 'status'){
                        if(readyRead){
                            self.color('#0033cc');
                            self.notify(getText('KDP005_5'));
                            self.inforAuthent('');
                            self.diplayBtnConnect(false);
                        }
                    }else if(command === 'close'){
                        self.color('#ff0000');
                        self.notify(getText('KDP005_21'));
                        self.inforAuthent('');
                        self.diplayBtnConnect(true);
                    }else if(command === 'connect'){
                        self.color('#0033cc');
                        self.notify(getText('KDP005_5'));
                        self.inforAuthent('');
                        self.diplayBtnConnect(false);
                    }else if(command === 'read'){
                        self.color('#0033cc');
                        self.notify(getText('KDP005_5'));
                        self.inforAuthent('');
                        self.diplayBtnConnect(false);
                        self.value(_.replace(cardNo, new RegExp("-","g"), ''));
                        self.decision();
                    }
                });    
            }
            
            private decision(){
                let self = this; 
                if(!nts.uk.ui.errors.hasError() && self.value() != ''){
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