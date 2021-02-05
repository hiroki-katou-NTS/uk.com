module nts.uk.at.view.kaf011.c.viewmodel {
	import windows = nts.uk.ui.windows;
   	import ajax = nts.uk.request.ajax;
	import block = nts.uk.ui.block;
	import dialog = nts.uk.ui.dialog;
	
	export class KAF011C {
		displayInforWhenStarting: any;
		appDate: KnockoutObservable<string>;
		reasonTypeItemLst: KnockoutObservableArray<any> = ko.observableArray([]);
		appStandardReasonCD: KnockoutObservable<number> = ko.observable();
		appReason: KnockoutObservable<string> = ko.observable("");
		constructor(){
			let self = this;
			let dataTransfer = windows.getShared('KAF011C');
			self.displayInforWhenStarting = dataTransfer;
			let reasonTypeItemLst: any = dataTransfer.appDispInfoStartup.appDispInfoNoDateOutput.reasonTypeItemLst;
			self.appDate = ko.observable(dataTransfer.abs.application.appDate);
			if(reasonTypeItemLst){
				self.reasonTypeItemLst(reasonTypeItemLst);	
			}
			self.appDate.subscribe((value:any) => {
				block.invisible();
				ajax('at/request/application/holidayshipment/changeDateScreenC',{appDateNew: new Date(value), displayInforWhenStarting: self.displayInforWhenStarting}).then((data: any) =>{
					self.displayInforWhenStarting.appDispInfoStartup.appDispInfoWithDateOutput = data.appDispInfoStartup.appDispInfoWithDateOutput;
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
					windows.setShared("KAF011C_RESLUT", {appID: data});
					dialog.info({ messageId: "Msg_15"}).then(()=>{
						self.closeDialog();
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
    	__viewContext.bind(new KAF011C());
		$("#recAppDate").focus();
    });
}