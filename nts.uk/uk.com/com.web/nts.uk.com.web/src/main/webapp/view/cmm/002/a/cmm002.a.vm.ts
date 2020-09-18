module cmm002.a {
	import block = nts.uk.ui.block;
	import getText = nts.uk.resource.getText;
	import getMessage = nts.uk.resource.getMessage;
    import dialog = nts.uk.ui.dialog;

    export class ViewModel {
        accessLimitUseAtr =  ko.observable(0);
		accessLimitUseAtrList = [
            { value: 0, name: getText("KDP010_163") },
            { value: 1, name: getText("KDP010_164") }
        ];
        allowedIPaddressList = ko.observableArray([]);
        allowedIPAddressNew =  ko.observable(new AllowedIPAddress());
        allowedIPAddressCurrent = null;
		newMode = true;
		
        constructor() {
            
        }

        /** start page */
        start() {
			let self = this;            
            let dfd = $.Deferred<any>();
			block.invisible();
            service.start().done((data) => {
                self.accessLimitUseAtr(data.accessLimitUseAtr);
				let tg = [];
				_.forEach(data.allowedIPaddress, (item) => {
					tg.push(new AllowedIPAddressDto(item));
				});
				self.allowedIPaddressList(tg);
                dfd.resolve();
            }).fail(function(error) {
                dfd.reject();
                dialog.alertError({ messageId: error.messageId });
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }

    }

	class AllowedIPAddressDto {
        startAddress: any;
		ipInputType: number;
		endAddress: any;
		comment: string;
		id: string;
        constructor(param : any) {
			let self = this;
			self.startAddress = param.startAddress;
			self.ipInputType = param.ipInputType;
			self.endAddress = param.endAddress;
			self.comment = param.comment;
			self.id = 	param.startAddress.ip1 + '.' +  
					  	param.startAddress.ip2 + '.' + 
					  	param.startAddress.ip3 + '.' + 
					  	param.startAddress.ip4
			if(param.ipInputType === 1){
				self.id += '～' +
						param.endAddress.ip1 + '.' +  
						param.endAddress.ip2 + '.' + 
						param.endAddress.ip3 + '.' + 
						param.endAddress.ip4
			}
        }
    }

    class AllowedIPAddress {
        startAddress = ko.observable(new IPAddressSetting());
		ipInputType = ko.observable(0);
		endAddress = ko.observable(new IPAddressSetting());
		comment = ko.observable('');
        constructor(param? : any) {
			let self = this;
			if(param){
				self.startAddress(new IPAddressSetting(param.startAddress));
				self.ipInputType(param.ipInputType);
				self.endAddress(new IPAddressSetting(param.endAddress));
				self.comment(param.comment);
			}
        }
    }

    class IPAddressSetting {
        ip1 = ko.observable('');
        ip2 = ko.observable('');
		ip3 = ko.observable('');
		ip4 = ko.observable('');
        constructor(obj? : any) {
			let self = this;
			if(obj){
				self.ip1 = ko.observable(obj.ip1);
	            self.ip2 = ko.observable(obj.ip2);
				self.ip3 = ko.observable(obj.ip3);
				self.ip4 = ko.observable(obj.ip4);	
			}
        }
    }

}