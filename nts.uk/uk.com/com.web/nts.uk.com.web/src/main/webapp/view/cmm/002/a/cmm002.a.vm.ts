module nts.uk.com.view.cmm002.a {
	import block = nts.uk.ui.block;
	import getText = nts.uk.resource.getText;
    import dialog = nts.uk.ui.dialog;
	import errors = nts.uk.ui.errors;

    export class ViewModel {
        accessLimitUseAtr =  ko.observable(0);
		accessLimitUseAtrList = _.orderBy(__viewContext.enums.NotUseAtr,['value'], ['desc'])
		ipInputTypeList = _.orderBy(__viewContext.enums.IPAddressRegistrationFormat,['value'], ['asc']);
		allowedIPAddressList = ko.observableArray([]);
        allowedIPAddress =  new AllowedIPAddress();
        selectedAllowedIPAddress = ko.observable('');
		columns = ko.observableArray([
                { headerText: getText('CMM002_5'), key: 'id', width: 150 }
		]);
        constructor() {
            let self = this;
			self.selectedAllowedIPAddress.subscribe(value =>{
				if(value != ""){
					self.allowedIPAddress.update(_.find(self.allowedIPAddressList(), ['id', value]));
					$('input').ntsError('check');
				}else{
					self.allowedIPAddress.update(ko.toJS(new AllowedIPAddress()));
				}
			});
        }

        /** start page */
        public start(): JQueryPromise<void> {
			let self = this;            
            let dfd = $.Deferred<any>();
            self.getData().done(() => {
				if(self.allowedIPAddressList().length != 0){
					self.selectedAllowedIPAddress(self.allowedIPAddressList()[0].id);					
				}
                dfd.resolve();
            }).fail(() => {
                dfd.reject();
            });
            return dfd.promise();
        }

        /** Get Data */
        public getData(): JQueryPromise<void> {
			let self = this;            
            let dfd = $.Deferred<any>();
			block.invisible();
            service.getData().done((data: AccessRestrictions) => {
                self.accessLimitUseAtr(data.accessLimitUseAtr);
				let tg = [];
				_.forEach(data.allowedIPaddress, (item) => {
					tg.push(new AllowedIPAddressDto(item));
				});
				self.allowedIPAddressList(_.orderBy(tg, ['startAddress.ip1','startAddress.ip2','startAddress.ip3','startAddress.ip4','endAddress.ip1','endAddress.ip2','endAddress.ip3','endAddress.ip4'], ['asc','asc','asc','asc','asc','asc','asc','asc']));
                dfd.resolve();
            }).fail(function(error: any) {
                dfd.reject();
                dialog.alertError({ messageId: error.messageId });
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }

		public newIp():void{
			let self = this;
			self.selectedAllowedIPAddress('');
			self.selectedAllowedIPAddress.valueHasMutated();
			errors.clearAll();
		}
		
		public save():void{
			let self = this;
			$('input').ntsError('check');
			if(!errors.hasError()) {
				if(self.selectedAllowedIPAddress() == ''){
					block.invisible();
					let param = {
						accessLimitUseAtr: self.accessLimitUseAtr(),
						allowedIPAddressNew: ko.toJS(self.allowedIPAddress)
					};
		            service.add(param).done(() => {
		            	self.getData().done(()=>{
							self.selectedAllowedIPAddress(new AllowedIPAddressDto(ko.toJS(self.allowedIPAddress)).id);
						});
						dialog.info({ messageId: "Msg_15" });
					}).fail(function(error: any) {
		                dialog.alertError({ messageId: error.messageId });
						block.clear();
		            });
				} else {
					block.invisible();
					let param = {
						accessLimitUseAtr: self.accessLimitUseAtr(),
						allowedIPAddressNew: ko.toJS(self.allowedIPAddress),
						allowedIPAddressOld: _.find(self.allowedIPAddressList(), ['id', self.selectedAllowedIPAddress()]) 
					};
		            service.update(param).done(() => {
		            	self.getData().done(()=>{
							self.selectedAllowedIPAddress(new AllowedIPAddressDto(ko.toJS(self.allowedIPAddress)).id);
						});
						dialog.info({ messageId: "Msg_15" });
					}).fail(function(error: any) {
		                dialog.alertError({ messageId: error.messageId });
						block.clear();
		            });
				}
			}
		}
		
		public del():void{
			let self = this;
			if(self.selectedAllowedIPAddress() != ''){
				dialog.confirm({ messageId: 'Msg_18' }).ifYes(function() {
                    block.invisible();
					let index = _.findIndex(self.allowedIPAddressList(), ['id', self.selectedAllowedIPAddress()]);
		            service.del(_.find(self.allowedIPAddressList(), ['id', self.selectedAllowedIPAddress()])).done(() => {
						self.getData().done(()=>{
							if(self.allowedIPAddressList().length == 0){
								self.selectedAllowedIPAddress('');
							}else{
								if(index < 1){
									self.selectedAllowedIPAddress(self.allowedIPAddressList()[0].id);
								}else{
									self.selectedAllowedIPAddress(self.allowedIPAddressList()[index - 1].id);
								}
							}
						});
						dialog.info({ messageId: "Msg_16" });
					}).fail(function(error: any) {
		                dialog.alertError({ messageId: error.messageId });
		            }).always(() => {
						block.clear();
					});
                });
			}
		}
    }

	interface AccessRestrictions{
		/** アクセス制限機能管理区分  */
		accessLimitUseAtr: number;
		/** 許可IPアドレス  */
		allowedIPaddress: Array<AllowedIPAddressDto>;
	}

	class AllowedIPAddressDto {
		/** 開始アドレス */        
		startAddress: any;
		/** IPアドレスの登録形式 */
		ipInputType: number;
		/** 終了アドレス */
		endAddress: any;
		/** 備考 */
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
		/** IPアドレスの登録形式 */
		ipInputType = ko.observable(0);      
		/** 開始アドレス */     
		startAddress = new IPAddressSetting();	
		/** 終了アドレス */
		endAddress = new IPAddressSetting();
		/** 備考 */
		comment = ko.observable('');
        constructor() {
			let self = this;
			self.ipInputType.subscribe(item =>{
				if(item == 0){
                    $('.endIP input').ntsError('clear'); 
				}
			});
		}
		update(data? : any) :void {
			let self = this;
			if(data){
				self.ipInputType(data.ipInputType);
				self.startAddress.update(data.startAddress);
				self.endAddress.update(data.endAddress);
				self.comment(data.comment);
			}
        }
    }

    class IPAddressSetting {
		/** IPアドレス1 */
        ip1 = ko.observable('');
		/** IPアドレス2 */        
		ip2 = ko.observable('');
		/** IPアドレス3 */
		ip3 = ko.observable('');
		/** IPアドレス4 */
		ip4 = ko.observable('');
		
    	constructor() {}
		update(data? : any) :void {
			let self = this;
			self.ip1(data?data.ip1:'');
            self.ip2(data?data.ip2:'');
			self.ip3(data?data.ip3:'');
			self.ip4(data?data.ip4:'');	
        }
    }

}
