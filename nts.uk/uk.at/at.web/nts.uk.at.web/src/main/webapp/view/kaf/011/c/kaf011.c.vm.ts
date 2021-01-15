module nts.uk.at.view.kaf011.c.viewmodel {
   	import ajax = nts.uk.request.ajax;
	import windows = nts.uk.ui.windows;
	let dataTransfer = windows.getShared('KAF011C');
	
	export class KAF011C {
		displayInforWhenStarting: any;
		appDate: KnockoutObservable<string> = ko.observable("");
		reasonTypeItemLst: KnockoutObservableArray<any> = ko.observableArray([]);
		appStandardReasonCD: KnockoutObservable<number> = ko.observable();
		appReason: KnockoutObservable<string> = ko.observable("");
		constructor(dataTransfer: any){
			let self = this;
			self.displayInforWhenStarting = dataTransfer;
			let appReasonStandardLst: any = _.find(dataTransfer.appDispInfoStartup.appDispInfoNoDateOutput.appReasonStandardLst, {'applicationType':10});
			self.appDate(dataTransfer.abs.application.appDate);
			if(appReasonStandardLst){
				self.reasonTypeItemLst(appReasonStandardLst.reasonTypeItemLst);	
			}
			self.appDate.subscribe((value:any) => {
				
			});
		}
		save(){
			windows.setShared("KAF011C_RESLUT", moment(this.appDate()).format('YYYY/MM/DD'));
			this.closeDialog();
		}
		closeDialog(){
			windows.close();
		}
	}
	
	let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        __viewContext.bind(new KAF011C(dataTransfer));
    });
}