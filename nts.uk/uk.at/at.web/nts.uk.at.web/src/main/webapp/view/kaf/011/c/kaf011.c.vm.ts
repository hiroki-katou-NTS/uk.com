module nts.uk.at.view.kaf011.c.viewmodel {
	import windows = nts.uk.ui.windows;
   	import ajax = nts.uk.request.ajax;
	import block = nts.uk.ui.block;
	import dialog = nts.uk.ui.dialog;
	import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;
	
	export class KAF011C {
		displayInforWhenStarting: any;
		appDate: KnockoutObservable<string>;
		reasonTypeItemLst: KnockoutObservableArray<any> = ko.observableArray([]);
		appStandardReasonCD: KnockoutObservable<number> = ko.observable();
		appReason: KnockoutObservable<string> = ko.observable("");
		appDispInfoStartupOutput: any = ko.observable();
		appType: KnockoutObservable<number> = ko.observable(AppType.COMPLEMENT_LEAVE_APPLICATION);
		constructor(){
			let self = this;
			let dataTransfer = windows.getShared('KAF011C');
			self.displayInforWhenStarting = dataTransfer;
			let reasonTypeItemLst: any = dataTransfer.appDispInfoStartup.appDispInfoNoDateOutput.reasonTypeItemLst;
			self.appDispInfoStartupOutput(dataTransfer.appDispInfoStartup);
			self.appDate = ko.observable(dataTransfer.abs.application.appDate);
			if(reasonTypeItemLst){
				self.reasonTypeItemLst(reasonTypeItemLst);	
			}
			self.appDate.subscribe((value:any) => {
				block.invisible();
				ajax('at/request/application/holidayshipment/changeDateScreenC',{appDateNew: new Date(value), displayInforWhenStarting: self.displayInforWhenStarting}).then((data: any) =>{
					self.displayInforWhenStarting.appDispInfoStartup.appDispInfoWithDateOutput = data.appDispInfoStartup.appDispInfoWithDateOutput;
					self.appDispInfoStartupOutput(data.appDispInfoStartup);
				}).fail((fail: any) => {
					dialog.error({ messageId: fail.messageId});
				}).always(() => {
                    block.clear();
                });
			});
			
			
		}
		
		
		triggerValidate(): boolean{
			$('.nts-input').trigger("validate");
			$('input').trigger("validate");
			return !nts.uk.ui.errors.hasError();
		}
		public handleMailResult(result: any, vm: any): any {
			let dfd = $.Deferred();
			if(_.isEmpty(result.autoFailServer)) {
				if(_.isEmpty(result.autoSuccessMail)) {
					if(_.isEmpty(result.autoFailMail)) {
						dfd.resolve(true);
					} else {
						vm.$dialog.error({ messageId: 'Msg_768', messageParams: [_.join(result.autoFailMail, ',')] }).then(() => {
				        	dfd.resolve(true);
				        });	
					}
				} else {
					vm.$dialog.info({ messageId: 'Msg_392', messageParams: [_.join(result.autoSuccessMail, ',')] }).then(() => {
						if(_.isEmpty(result.autoFailMail)) {
							dfd.resolve(true);	
						} else {
							vm.$dialog.error({ messageId: 'Msg_768', messageParams: [_.join(result.autoFailMail, ',')] }).then(() => {
					        	dfd.resolve(true);
					        });	
						}
			        });	
				}	
			} else {
				vm.$dialog.error({ messageId: 'Msg_1057' }).then(() => {
		        	dfd.resolve(true);
		        });
			}
			return dfd.promise();
		}
		
		save(){
			let self = this;
			if(self.triggerValidate()){
				if(self.displayInforWhenStarting.rec){
					self.displayInforWhenStarting.rec.applicationInsert = self.displayInforWhenStarting.rec.applicationUpdate = self.displayInforWhenStarting.rec.application;	
				}
				if(self.displayInforWhenStarting.abs){
					self.displayInforWhenStarting.abs.applicationInsert = self.displayInforWhenStarting.abs.applicationUpdate = self.displayInforWhenStarting.abs.application;
				}
				block.invisible();
				ajax('at/request/application/holidayshipment/saveChangeDateScreenC',{appDateNew: new Date(self.appDate()), displayInforWhenStarting: self.displayInforWhenStarting, appReason: self.appReason(), appStandardReasonCD: self.appStandardReasonCD()}).then((data: any) =>{
					dialog.info({ messageId: "Msg_15"}).then(()=>{
						nts.uk.request.ajax("at", "at/request/application/reflect-app", data.reflectAppIdLst);
						return self.handleMailResult(data, self).then(() => {
							nts.uk.ui.windows.setShared('KAF011C_RESLUT', { appID: data.appIDLst[0] });
							self.closeDialog();
                            return true;
						});
					});
				}).fail((fail: any) => {
					dialog.error({ messageId: fail.messageId, messageParams: fail.parameterIds});
				}).always(() => {
	                block.clear();
	            });
				
			}
		}
		closeDialog(){
			windows.close();
		}
		
		
	}
	
    __viewContext.ready(function() {
		const vm = new KAF011C()
    	__viewContext.bind(vm);
		
		
		$("#recAppDate").focus();
    });
}