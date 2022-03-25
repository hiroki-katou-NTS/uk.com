module nts.uk.at.view.kdp010.g {
    import block = nts.uk.ui.block;
    import info = nts.uk.ui.dialog.info;
    import error = nts.uk.ui.dialog.error;
	import ajax = nts.uk.request.ajax;
	import setShared = nts.uk.ui.windows.setShared;
	
    export module viewmodel {
		const paths: any = {
	        getData: "at/record/stamp/timestampinputsetting/settingsusingembossing/get",
	        save: "at/record/stamp/timestampinputsetting/settingsusingembossing/save"
	    }
        export class ScreenModel {
            settingsUsingEmbossing = new SettingsUsingEmbossing();
			hasFocus: KnockoutObservable<boolean> = ko.observable(false);
            constructor(){}
            start(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                block.grayout();
                ajax("at", paths.getData).done(function(data: any) {
                    if (data) {
                        self.settingsUsingEmbossing.update(data);
                    }
                    dfd.resolve();
 					$(document).ready(function() {
                    	self.hasFocus(true);
                	});
                }).fail(function (res: any) {
                    error({ messageId: res.messageId });
                }).always(function () {
                    block.clear();
                });
                return dfd.promise();
            }
            save(){
                let self = this;
                block.grayout();
                ajax("at", paths.save, ko.toJS(self.settingsUsingEmbossing)).done(function() {
                    setShared("KDP010G", true);
					info({ messageId: "Msg_15"}).then(()=>{
						self.closeDialog();
					});
                }).fail(function (res: any) {
                    error({ messageId: res.messageId });
                }).always(function () {
                    block.clear();
                });
            }
			closeDialog(){
				nts.uk.ui.windows.close();
			}
            
        }
        
        export class SettingsUsingEmbossing {
            name_selection: KnockoutObservable<boolean> = ko.observable(false);
            finger_authc: KnockoutObservable<boolean> = ko.observable(false);
            ic_card: KnockoutObservable<boolean> = ko.observable(false);
            indivition: KnockoutObservable<boolean> = ko.observable(false);
            portal: KnockoutObservable<boolean> = ko.observable(false);
            smart_phone: KnockoutObservable<boolean> = ko.observable(false);
			ricohStamp: KnockoutObservable<boolean> = ko.observable(false);
            constructor(){}
            update(data?:any){
                let self = this;
                if(data){
                    self.name_selection(data.name_selection);
                    //self.finger_authc(data.finger_authc);
                    self.ic_card(data.ic_card);
                    self.indivition(data.indivition);
                    self.portal(data.portal);
                    self.smart_phone(data.smart_phone);
					//self.ricohStamp(data.ricohStamp);
                }
            }
        }
    }
}