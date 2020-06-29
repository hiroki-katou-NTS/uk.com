module nts.uk.at.view.kdp010.a {
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import info = nts.uk.ui.dialog.info;
    import viewModelscreenB = nts.uk.at.view.kdp010.b.viewmodel;
    import viewModelscreenC = nts.uk.at.view.kdp010.c.viewmodel;
    import viewModelscreenD = nts.uk.at.view.kdp010.d.viewmodel;
    import viewModelscreenF = nts.uk.at.view.kdp010.f.viewmodel;
    import viewModelscreenE = nts.uk.at.view.kdp010.e.viewmodel;
    
    export module viewmodel {
        export class ScreenModel {
            viewModelA: KnockoutObservable<any>;
            viewModelB: KnockoutObservable<any>;
            viewModelC: KnockoutObservable<any>;
            viewModelD: KnockoutObservable<any>;
            viewModelF: KnockoutObservable<any>;
            viewModelE: KnockoutObservable<any>;
            
            constructor(){
                let self = this;
                self.viewModelA = ko.observable(new ScreenModelA());
                self.viewModelB = ko.observable(new viewModelscreenB.ScreenModel());
                self.viewModelC = ko.observable(new viewModelscreenC.ScreenModel());
                self.viewModelD = ko.observable(new viewModelscreenD.ScreenModel());
                self.viewModelF = ko.observable(new viewModelscreenF.ScreenModel());
                self.viewModelE = ko.observable(new viewModelscreenE.ScreenModel());
            }
            
            public startPage(): JQueryPromise<any> {
                let self = this;
                var dfd = $.Deferred<any>();
                self.viewModelA().start().done(()=>{
                    dfd.resolve();
                });
                return dfd.promise();
            }
            
            /**
             * on select tab handle
             */
            public selectScreenA(): void {
                $("#sidebar").ntsSideBar("init", {
                    activate: (event, info) => {
                        let self = this;
                        self.viewModelA().start();
                        self.removeErrorMonitor();
                    }
                });
            }
            
            public selectScreenB(): void {
                $("#sidebar").ntsSideBar("init", {
                    activate: (event, info) => {
                        let self = this;
                        self.viewModelB().start();
                        self.removeErrorMonitor();
                    }
                });
            }
            
            public selectScreenC(): void {
                $("#sidebar").ntsSideBar("init", {
                    activate: (event, info) => {
                        let self = this;
//                        self.viewModelC().start();
                        self.removeErrorMonitor();
                    }
                });
            }
            
            public selectScreenD(): void {
                $("#sidebar").ntsSideBar("init", {
                    activate: (event, info) => {
                        let self = this;
//                        self.viewModelD().start();
                        self.removeErrorMonitor();
                    }
                });
            }
            
            public selectScreenF(): void {
                $("#sidebar").ntsSideBar("init", {
                    activate: (event, info) => {
                        let self = this;
//                        self.viewModelF().start(); 
                        self.removeErrorMonitor();
                    }
                });
            }
            
            public selectScreenE(): void {
                $("#sidebar").ntsSideBar("init", {
                    activate: (event, info) => {
                        let self = this;
                        self.viewModelE().start();
                        self.removeErrorMonitor();
                    }
                });
            }
            
            /**
            *   remove all alert error all tab
            **/
            public removeErrorMonitor(): void {
                $('.nts-input').ntsError('clear');    
            }
        }
        export class ScreenModelA {
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
            stampSetCommunal: any = new StampSetCommunal();
            lstStampPageLayout: KnockoutObservable<boolean> = ko.observable(false);
            constructor(){
                let self = this;
            }
            start(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                block.invisible();
                service.getData().done(function(data) {
                    if (data) {
                        self.stampSetCommunal.update(data);
                        self.lstStampPageLayout(data.lstStampPageLayout.length > 0);
                    }
                    dfd.resolve();
                    $('#correc-input').focus();
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
                block.invisible();
                service.getData().done(function(data) {
                    if (data) {
                        self.lstStampPageLayout(data.lstStampPageLayout.length > 0);
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
                block.invisible();
                service.save(ko.toJS(self.stampSetCommunal)).done(function(data) {
                    info({ messageId: "Msg_15"});
                }).fail(function (res) {
                    info({ messageId: res.messageId });
                }).always(function () {
                    block.clear();
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
        
        class StampSetCommunal {
            displaySetStampScreen: any = new DisplaySettingsStampScreen();
            nameSelectArt: KnockoutObservable<number> = ko.observable(0);
            passwordRequiredArt: KnockoutObservable<number> = ko.observable(1);
            employeeAuthcUseArt: KnockoutObservable<number> = ko.observable(0);
            authcFailCnt: KnockoutObservable<number> = ko.observable(1);
            required: KnockoutObservable<boolean> = ko.observable(false);
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
                }
            }
        }
    }
}