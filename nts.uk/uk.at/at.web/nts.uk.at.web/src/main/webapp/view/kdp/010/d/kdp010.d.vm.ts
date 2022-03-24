module nts.uk.at.view.kdp010.d {
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
			locationInfoUseOption: KnockoutObservableArray<any> = ko.observableArray([
                { id: 1, name: getText("KDP010_245") },
                { id: 0, name: getText("KDP010_246") }
            ]);
			areaLimitAtrOption: KnockoutObservableArray<any> = ko.observableArray([
                { id: 2, name: getText("KDP010_239") },
                { id: 1, name: getText("KDP010_249") },
                { id: 0, name: getText("KDP010_248") }
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
                let self = this;
                block.grayout();
                ajax(paths.save, ko.toJS(self.settingsSmartphoneStamp)).done(function() {
                    info({ messageId: "Msg_15"});
                }).fail(function (res: any) {
                    error({ messageId: res.messageId });
                }).always(function () {
                    block.clear();
                });
            }
            
            openIDialog() {
                let self = this;
                nts.uk.ui.windows.sub.modal("/view/kdp/010/j/index.xhtml").onClosed(() => {
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
        
        class SettingsSmartphoneStamp {
            displaySettingsStampScreen = new DisplaySettingsStampScreen();
            pageLayoutSettings: KnockoutObservableArray<any> = ko.observableArray([]);
            buttonEmphasisArt: KnockoutObservable<number> = ko.observable(0);
            googleMap: KnockoutObservable<number> = ko.observable(0);
			locationInfoUse: KnockoutObservable<number> = ko.observable(0);
			areaLimitAtr: KnockoutObservable<number> = ko.observable(0);
			mapAddres: string;
			areaLimitAtrEnable: KnockoutObservable<boolean> = ko.computed(():boolean => {
				return this.locationInfoUse() == 1;
			});
            constructor(){
				let self = this;
				self.locationInfoUse.subscribe((value: number) => {
					if(value == 0){
						self.areaLimitAtr(0); 	
					}
				});	
			}
            update(data?:any){
                let self = this;
                if(data){
                    self.displaySettingsStampScreen.update(data.displaySettingsStampScreen);
                    self.pageLayoutSettings(data.pageLayoutSettings || []);
                    self.buttonEmphasisArt(data.buttonEmphasisArt || 0);    
                    self.locationInfoUse(_.get(data, 'stampingAreaRestriction.useLocationInformation', 0));
					self.areaLimitAtr(_.get(data, 'stampingAreaRestriction.stampingAreaLimit', 0));
                    if(data.googleMap != undefined && data.googleMap != null){
                        self.googleMap(data.googleMap);
                    }
					self.mapAddres = data.mapAddres || "https://www.google.co.jp/maps/place/";
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