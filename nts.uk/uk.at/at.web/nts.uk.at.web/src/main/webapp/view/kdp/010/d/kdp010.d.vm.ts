module nts.uk.at.view.kdp010.d {
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import info = nts.uk.ui.dialog.info;
    export module viewmodel {
        export class ScreenModel {
            suppressStampBtnOption: KnockoutObservableArray<any> = ko.observableArray([
                { id: 1, name: getText("KDP010_202") },
                { id: 0, name: getText("KDP010_203") }
            ]);
            useTopMenuLinkOption: KnockoutObservableArray<any> = ko.observableArray([
                { id: 1, name: getText("KDP010_271") },
                { id: 0, name: getText("KDP010_272") }
            ]);
            goOutArtOption: KnockoutObservableArray<any> = ko.observableArray([
                { id: 0, name: getText("KDP010_112") },
                { id: 1, name: getText("KDP010_113") },
                { id: 2, name: getText("KDP010_114") },
                { id: 3, name: getText("KDP010_115") }
            ]);
            portalStampSettings = new PortalStampSettings();
            constructor(){
                let self = this;
            }
            
            start(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                block.invisible();
                service.getData().done(function(data) {
                    if (data) {
                        console.log(data);
                        self.portalStampSettings.update(data);
                    }
                    dfd.resolve();
                }).fail(function (res) {
                    info({ messageId: res.messageId });
                }).always(function () {
                    block.clear();
                });
                return dfd.promise();
            }
            
            getDefaultValueButtonType(buttonPositionNo: number){
                let self = this;
                
            }
            
            save(){
                let self = this;
                block.invisible();
                service.save(ko.toJS(self.portalStampSettings)).done(function(data) {
                    info({ messageId: "Msg_15"});
                }).fail(function (res) {
                    info({ messageId: res.messageId });
                }).always(function () {
                    block.clear();
                });
            }
        }
        
        class ButtonNameSet{
            buttonName: KnockoutObservable<string>;
            textColor: KnockoutObservable<string> = ko.observable("#000000");
            constructor(buttonPositionNo: number){
                let self = this;
                if(buttonPositionNo == 1){
                    self.buttonName = ko.observable(getText("KDP010_212"));    
                }else if(buttonPositionNo == 2){
                    self.buttonName = ko.observable(getText("KDP010_213"));    
                }else if(buttonPositionNo == 3){
                    self.buttonName = ko.observable(getText("KDP010_214"));    
                }else if(buttonPositionNo == 4){
                    self.buttonName = ko.observable(getText("KDP010_215"));    
                }
            }
            update(param: any){
                let self = this;
                self.buttonName(param.buttonName);
                self.textColor(param.textColor);
            }
        }
        
        class ButtonDisSet{
            backGroundColor: KnockoutObservable<string> = ko.observable("#ffffff");
            buttonNameSet: any;
            constructor(buttonPositionNo: number){
                let self = this;
                self.buttonNameSet = new ButtonNameSet(buttonPositionNo)
            }
            update(param: any){
                let self = this;
                self.backGroundColor(param.backGroundColor);
                self.buttonNameSet.update(param.buttonNameSet);
            }
        }
        
        class ButtonSettings {
            buttonPositionNo: number;
            buttonDisSet: any;
            buttonType: any;
            usrArt: number = 1;
            audioType: number = 0;
            goOutArt: KnockoutObservable<number> = ko.observable(null);
            constructor(buttonPositionNo: number){
                let self = this;
                self.buttonPositionNo = buttonPositionNo;
                self.buttonDisSet = new ButtonDisSet(self.buttonPositionNo);
                if(self.buttonPositionNo == 1){
                    self.buttonType = {
                        stampType: { changeClockArt: 0, changeCalArt: 0, setPreClockArt: 0, changeHalfDay: false, goOutArt: null },
                        reservationArt: 0
                    }
                }else if(self.buttonPositionNo == 2){
                    self.buttonType = {
                        stampType: { changeClockArt: 1, changeCalArt: 0, setPreClockArt: 0, changeHalfDay: false, goOutArt: null },
                        reservationArt: 0
                    }
                }else if(self.buttonPositionNo == 3){
                    self.goOutArt(0);
                    self.buttonType = {
                        stampType: { changeClockArt: 4, changeCalArt: 0, setPreClockArt: 0, changeHalfDay: false, goOutArt: ko.observable(self.goOutArt())},
                        reservationArt: 0
                    }
                }else if(self.buttonPositionNo == 4){
                    self.buttonType = {
                        stampType: { changeClockArt: 5, changeCalArt: 0, setPreClockArt: 0, changeHalfDay: false, goOutArt: null },
                        reservationArt: 0
                    }
                }
            }
            update(param: any){
                let self = this;
                if(param){
                    self.buttonDisSet.update(param.buttonDisSet);
                    if(param.buttonPositionNo == 1 && param.buttonType.stampType){
                        self.goOutArt(param.buttonType.stampType.goOutArt);
                    }
                }
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
        
        class PortalStampSettings {
            displaySettingsStampScreen = new DisplaySettingsStampScreen();
            suppressStampBtn: KnockoutObservable<number> = ko.observable(0);
            useTopMenuLink: KnockoutObservable<number> = ko.observable(0);
            buttonSettings: any;
            constructor(){
                let self = this;
                let buttonSettingsArray = [];
                for(let i = 1; i <= 4; i++){
                    buttonSettingsArray.push(new ButtonSettings(i));
                }
                self.buttonSettings = buttonSettingsArray;
            }
            update(data?:any){
                let self = this;
                if(data){
                    self.displaySettingsStampScreen.update(data.displaySettingsStampScreen);
                    self.suppressStampBtn(data.suppressStampBtn);
                    self.useTopMenuLink(data.useTopMenuLink);
                    _.forEach(self.buttonSettings, function(buttonSetting) {
                        let setting = _.find(data.buttonSettings, function(item) { return item.buttonPositionNo == buttonSetting.buttonPositionNo; });
                        if(setting){
                            buttonSetting.update(setting);
                        }
                    });
                }
            }
        }  
    }
}