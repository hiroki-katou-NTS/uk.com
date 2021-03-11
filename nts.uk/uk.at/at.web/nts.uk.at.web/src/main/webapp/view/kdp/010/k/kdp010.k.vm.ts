module nts.uk.at.view.kdp010.k {
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import info = nts.uk.ui.dialog.info;
    import error = nts.uk.ui.dialog.error;
	import ajax = nts.uk.request.ajax;
	
    export module viewmodel {
		const paths: any = {
	        getData: "at/record/stamp/timestampinputsetting/settingssmartphonestamp/get",
        	save: "at/record/stamp/timestampinputsetting/settingssmartphonestamp/save"
	    }
        export class ScreenModel {
            settingsSmartphoneStamp = new SettingsSmartphoneStamp();
            buttonEmphasisArtOption: KnockoutObservableArray<any> = ko.observableArray([
                { id: 1, name: getText("KDP010_241") },
                { id: 0, name: getText("KDP010_242") }
            ]);
            googleMapOption: KnockoutObservableArray<any> = ko.observableArray([
                { id: 1, name: getText("KDP010_187") },
                { id: 0, name: getText("KDP010_188") }
            ]);
            constructor(){}
            
            start(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                block.grayout();
                ajax(paths.getData).done(function(data: any) {
                    if (data) {
                        self.settingsSmartphoneStamp.update(data);
                    }
                    dfd.resolve();
                    $(document).ready(function() {
                        $('#c-serverCorrectionInterval').focus();
                    });
                }).fail(function (res: any) {
                    error({ messageId: res.messageId });
                }).always(function () {
                    block.clear();
                });
                return dfd.promise();
            }
            
            checkSetStampPageLayout(){
                let self = this;
                block.grayout();
                ajax(paths.getData).done(function(data: any) {
                    if (data) {
                        self.settingsSmartphoneStamp.pageLayoutSettings(data.pageLayoutSettings || []);
                    }
                }).fail(function (res: any) {
                    error({ messageId: res.messageId });
                }).always(function () {
                    block.clear();
                });
            }
            
            save(){
                
            }
            
            openIDialog() {
                
            }
        }
        class SettingDateTimeClorOfStampScreen {
            textColor: KnockoutObservable<string> = ko.observable("#ffffff");
            backgroundColor: KnockoutObservable<string> = ko.observable("#0033cc");
            constructor(){}
            update(data?: any){
                let self = this;
                if(data){
                    self.textColor(data.textColor);
                    self.backgroundColor(data.backgroundColor);
                }
            }
        }
        
        class DisplaySettingsStampScreen {
            serverCorrectionInterval: KnockoutObservable<number> = ko.observable(10);
            resultDisplayTime: KnockoutObservable<number> = ko.observable(3);
			passWord: KnockoutObservable<string> = ko.observable('');
            settingDateTimeColor = new SettingDateTimeClorOfStampScreen();
            constructor(){}
            update(data?:any){
                let self = this;
                if(data){
                    self.serverCorrectionInterval(data.serverCorrectionInterval);
                    self.resultDisplayTime(data.resultDisplayTime);
                    self.settingDateTimeColor.update(data.settingDateTimeColor);
                }
            }
        }
        
        class SettingsSmartphoneStamp {
            displaySettingsStampScreen = new DisplaySettingsStampScreen();
            pageLayoutSettings: KnockoutObservableArray<any> = ko.observableArray([]);
            buttonEmphasisArt: KnockoutObservable<number> = ko.observable(0);
            googleMap: KnockoutObservable<number> = ko.observable(0);
            constructor(){}
            update(data?:any){
                let self = this;
                if(data){
                    self.displaySettingsStampScreen.update(data.displaySettingsStampScreen);
                    self.pageLayoutSettings(data.pageLayoutSettings || []);
                    if(data.buttonEmphasisArt){
                        self.buttonEmphasisArt(data.buttonEmphasisArt);    
                    }
                    if(data.googleMap != undefined && data.googleMap != null){
                        self.googleMap(data.googleMap);
                    }
                }
            }
        }   
    }
	__viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}