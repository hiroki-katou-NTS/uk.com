module nts.uk.at.view.kdp010.a {
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import info = nts.uk.ui.dialog.info;
    import error = nts.uk.ui.dialog.error;
    import viewModelscreenB = nts.uk.at.view.kdp010.b.viewmodel;
    import viewModelscreenC = nts.uk.at.view.kdp010.c.viewmodel;
    import viewModelscreenD = nts.uk.at.view.kdp010.d.viewmodel;
    import viewModelscreenF = nts.uk.at.view.kdp010.f.viewmodel;
    import viewModelscreenE = nts.uk.at.view.kdp010.e.viewmodel;
    
    export module viewmodel {
        export class ScreenModel {
            viewModelA = new ScreenModelA();
            viewModelB = new viewModelscreenB.ScreenModel();
            viewModelC = new viewModelscreenC.ScreenModel();
            viewModelD = new viewModelscreenD.ScreenModel();
            viewModelF: any;
            viewModelE = new viewModelscreenE.ScreenModel();
            controlTab = new viewModelscreenF.SettingsUsingEmbossing();
            
            constructor(){
                let self = this;
                self.viewModelF = new viewModelscreenF.ScreenModel(self.bindingTab);
            }
            
            public startPage(): JQueryPromise<any> {
                let self = this;
                var dfd = $.Deferred<any>();
                self.viewModelF.start().done(() => {
                    if(self.controlTab.name_selection() || self.controlTab.finger_authc() || self.controlTab.ic_card()){
                        self.viewModelA.start().done(()=>{
                            dfd.resolve();
                        });                            
                    }else if(self.controlTab.indivition()){
                        self.viewModelB.start().done(()=>{
                            dfd.resolve();
                            $("#sidebar").ntsSideBar("active", 1);
                        });
                    }else if(self.controlTab.smart_phone()){
                        self.viewModelC.start().done(()=>{
                            dfd.resolve();
                            $("#sidebar").ntsSideBar("active", 2);
                        });
                    }else if(self.controlTab.portal()){
                        self.viewModelD.start().done(()=>{
                            dfd.resolve();
                            $("#sidebar").ntsSideBar("active", 3);
                        });
                    }else{
                        dfd.resolve();
                        $("#sidebar").ntsSideBar("active", 4);
                    }
                });
                return dfd.promise();
            }
            
            public bindingTab(settingsUsingEmbossing?: any){
                if(settingsUsingEmbossing){
                    __viewContext.vm.controlTab.update(settingsUsingEmbossing);
                }
            }
            
            /**
             * on select tab handle
             */
            public selectScreenA(): void {
                $("#sidebar").ntsSideBar("init", {
                    activate: (event, info) => {
                        let self = this;
                        self.viewModelA.start();
                        self.removeErrorMonitor();
                    }
                });
            }
            
            public selectScreenB(): void {
                $("#sidebar").ntsSideBar("init", {
                    activate: (event, info) => {
                        let self = this;
                        self.viewModelB.start();
                        self.removeErrorMonitor();
                    }
                });
            }
            
            public selectScreenC(): void {
                $("#sidebar").ntsSideBar("init", {
                    activate: (event, info) => {
                        let self = this;
                        self.viewModelC.start();
                        self.removeErrorMonitor();
                    }
                });
            }
            
            public selectScreenD(): void {
                $("#sidebar").ntsSideBar("init", {
                    activate: (event, info) => {
                        let self = this;
                        self.viewModelD.start();
                        self.removeErrorMonitor();
                    }
                });
            }
            
            public selectScreenF(): void {
                $("#sidebar").ntsSideBar("init", {
                    activate: (event, info) => {
                        let self = this;
                        self.viewModelF.start(); 
                        self.removeErrorMonitor();
                    }
                });
            }
            
            public selectScreenE(): void {
                $("#sidebar").ntsSideBar("init", {
                    activate: (event, info) => {
                        let self = this;
                        self.viewModelE.start();
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
            stampSetCommunal = new StampSetCommunal();
            constructor(){
                let self = this;
            }
            start(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                block.grayout();
                service.getData().done(function(data) {
                    if (data) {
                        self.stampSetCommunal.update(data);
                    }
                    dfd.resolve();
                    $(document).ready(function() {
                        $('#a-serverCorrectionInterval').focus();
                    });
                }).fail(function (res) {
                    error({ messageId: res.messageId });
                }).always(function () {
                    block.clear();
                });
                return dfd.promise();
            }
            
            checkSetStampPageLayout(){
                let self = this;
                block.grayout();
                service.getData().done(function(data) {
                    self.stampSetCommunal.lstStampPageLayout(data? data.lstStampPageLayout : []);
                }).fail(function (res) {
                    error({ messageId: res.messageId });
                }).always(function () {
                    block.clear();
                });
            }
            
            save(){
                let self = this;
                block.grayout();
                service.save(ko.toJS(self.stampSetCommunal)).done(function(data) {
                    info({ messageId: "Msg_15"});
                }).fail(function (res) {
                    error({ messageId: res.messageId });
                }).always(function () {
                    block.clear();
                });
            }
            
            openGDialog() {
                let self = this;
                nts.uk.ui.windows.setShared('STAMP_MEANS', 0);
                nts.uk.ui.windows.sub.modal("/view/kdp/010/g/index.xhtml").onClosed(() => {
                    self.checkSetStampPageLayout();   
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
                    self.lstStampPageLayout(data.lstStampPageLayout);
                }
            }
        }
    }
}