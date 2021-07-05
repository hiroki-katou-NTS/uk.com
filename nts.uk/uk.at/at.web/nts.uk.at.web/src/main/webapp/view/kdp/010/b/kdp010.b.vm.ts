module nts.uk.at.view.kdp010.b {
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import info = nts.uk.ui.dialog.info;
    import error = nts.uk.ui.dialog.error;
	import ajax = nts.uk.request.ajax;
	import viewModelscreenG = nts.uk.at.view.kdp010.g.viewmodel;
    
    module viewmodel {
        const paths: any = {
	        getData: "at/record/stamp/timestampinputsetting/stampsetcommunal/get",
        	save: "at/record/stamp/timestampinputsetting/stampsetcommunal/save"
	    }
        export class ScreenModel {
            nameSelectArtOption: KnockoutObservableArray<any> = ko.observableArray([
                { id: 1, name: getText("KDP010_163") },
                { id: 0, name: getText("KDP010_164") }
            ]);
            passwordRequiredArtOption: KnockoutObservableArray<any> = ko.observableArray([
                { id: 1, name: getText("KDP010_166") },
                { id: 0, name: getText("KDP010_167") }
            ]);
            employeeAuthcUseArtOption: KnockoutObservable<any> = ko.observable({ id: 1, name: getText("KDP010_170") });
            employeeAuthcUseArtOption2: KnockoutObservable<any> = ko.observable({ id: 0, name: getText("KDP010_172") });
            stampSetCommunal = new StampSetCommunal();

			screenModelG = new viewModelscreenG.ScreenModel();
            constructor(){
            }
            start(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
				block.grayout();
				$.when(self.getData(), self.screenModelG.start()).done(function() {
					dfd.resolve();
					$(document).ready(function() {
                        $('#a-serverCorrectionInterval').focus();
                    });
				}).always(function() {
                    block.clear();
                });
                return dfd.promise();
            }

			getData(): JQueryPromise<any> {
				let self = this;
                let dfd = $.Deferred();
                block.grayout();
                ajax("at", paths.getData).done(function(data: any) {
                    if (data) {
                        self.stampSetCommunal.update(data);
                    }
                    dfd.resolve();
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
                 ajax("at", paths.getData).done(function(data: any) {
                    if(data){
                        self.stampSetCommunal.lstStampPageLayout(data.lstStampPageLayout || []);
                    }
                }).fail(function (res: any) {
                    error({ messageId: res.messageId });
                }).always(function () {
                    block.clear();
                });
            }
            
            save(){
                let self = this;
                block.grayout();
                ajax("at", paths.save, ko.toJS(self.stampSetCommunal)).done(function() {
                    info({ messageId: "Msg_15"});
                }).fail(function (res: any) {
                    error({ messageId: res.messageId });
                }).always(function () {
                    block.clear();
                });
            }
            
            openHDialog() {
                let self = this;
                nts.uk.ui.windows.setShared('STAMP_MEANS', 0);
                nts.uk.ui.windows.sub.modal("/view/kdp/010/h/index.xhtml").onClosed(() => {
                    self.checkSetStampPageLayout();   
                });
            }
        }
        
        class SettingDateTimeClorOfStampScreen {
            textColor: KnockoutObservable<string> = ko.observable("#7F7F7F");
            constructor(){}
            update(data?: any){
                let self = this;
                if(data){
                    self.textColor(data.textColor);
                }
            }
        }
        
        class DisplaySettingsStampScreen {
            serverCorrectionInterval: KnockoutObservable<number> = ko.observable(10);
            resultDisplayTime: KnockoutObservable<number> = ko.observable(3);
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
        
        class StampSetCommunal {
            displaySetStampScreen = new DisplaySettingsStampScreen();
            nameSelectArt: KnockoutObservable<number> = ko.observable(0);
            passwordRequiredArt: KnockoutObservable<number> = ko.observable(1);
            employeeAuthcUseArt: KnockoutObservable<number> = ko.observable(0);
            authcFailCnt: KnockoutObservable<number> = ko.observable(1);
            required: KnockoutObservable<boolean> = ko.observable(false);
            lstStampPageLayout = ko.observableArray([]);
            constructor(){
                let self = this;
                self.employeeAuthcUseArt.subscribe((newValue) => {
                    if(newValue == 1){
                        self.required(true);
                    }else{
                        self.required(false);
                    }
                    $('#numberAuthenfailures').ntsError('check'); 
                });    
            }
            update(data?:any){
                let self = this;
                if(data){
                    self.displaySetStampScreen.update(data.displaySetStampScreen);
                    self.nameSelectArt(data.nameSelectArt);
                    self.passwordRequiredArt(data.passwordRequiredArt);
                    self.employeeAuthcUseArt(data.employeeAuthcUseArt);
                    self.authcFailCnt(data.authcFailCnt);
                    self.lstStampPageLayout(data.lstStampPageLayout || []);
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