module nts.uk.at.view.kdp010.c {
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import info = nts.uk.ui.dialog.info;
    export module viewmodel {
        export class ScreenModel {
            settingsSmartphoneStamp: any = new SettingsSmartphoneStamp();
            buttonEmphasisArtOption: KnockoutObservableArray<any> = ko.observableArray([
                { id: 1, name: getText("KDP010_241") },
                { id: 0, name: getText("KDP010_242") }
            ]);
            googleMapOption: KnockoutObservableArray<any> = ko.observableArray([
                { id: 1, name: getText("KDP010_187") },
                { id: 0, name: getText("KDP010_188") }
            ]);
            constructor(){
                let self = this;
            }
            
            start(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                block.grayout();
                service.getData().done(function(data) {
                    if (data) {
                        self.settingsSmartphoneStamp.update(data);
                    }
                    dfd.resolve();
                }).fail(function (res) {
                    info({ messageId: res.messageId });
                }).always(function () {
                    block.clear();
                });
                return dfd.promise();
            }
            
            checkSetStampPageLayout(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                block.grayout();
                service.getData().done(function(data) {
                    if (data) {
                        self.pageLayoutSettings(data.pageLayoutSettings.length > 0);
                    }
                    dfd.resolve();
                }).fail(function (res) {
                    info({ messageId: res.messageId });
                }).always(function () {
                    block.clear();
                });
                return dfd.promise();
            }
            
            save(){
                let self = this;
                block.grayout();
                service.save(ko.toJS(self.settingsSmartphoneStamp)).done(function(data) {
                    info({ messageId: "Msg_15"});
                }).fail(function (res) {
                    info({ messageId: res.messageId });
                }).always(function () {
                    block.clear();
                });
            }
            
            openIDialog() {
                nts.uk.ui.windows.sub.modal("/view/kdp/010/i/index.xhtml").onClosed(() => {
                });
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
            settingDateTimeColor: any = new SettingDateTimeClorOfStampScreen();
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
            displaySettingsStampScreen: any = new DisplaySettingsStampScreen();
            pageLayoutSettings: KnockoutObservableArray<any> = ko.observableArray([]);
            buttonEmphasisArt: KnockoutObservable<number> = ko.observable(0);
            googleMap: KnockoutObservable<number> = ko.observable(0);
            constructor(){
                let self = this;
            }
            update(data?:any){
                let self = this;
                if(data){
                    self.displaySettingsStampScreen.update(data.displaySettingsStampScreen);
                    self.pageLayoutSettings(data.pageLayoutSettings);
                    self.buttonEmphasisArt(data.buttonEmphasisArt);
                    if(data.googleMap != undefined && data.googleMap != null){
                        self.googleMap(data.googleMap);
                    }
                }
            }
        }   
    }
}