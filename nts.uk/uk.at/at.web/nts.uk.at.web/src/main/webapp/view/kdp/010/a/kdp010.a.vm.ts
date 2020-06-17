module nts.uk.at.view.kdp010.a {
    import getText = nts.uk.resource.getText;
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
                dfd.resolve();
                return dfd.promise();
            }
            
            /**
             * on select tab handle
             */
            public selectScreenA(): void {
                $("#sidebar").ntsSideBar("init", {
                    activate: (event, info) => {
                        let self = this;
                        //self.startPage();
                        self.viewModelA();
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
            correcValue: KnockoutObservable<number> = ko.observable(10);
            letterColors: KnockoutObservable<string> = ko.observable("#ffffff");
            backgroundColors: KnockoutObservable<string> = ko.observable("#0033cc");
            stampValue: KnockoutObservable<number> = ko.observable(3);
            employeeListDisplayOption: KnockoutObservableArray<any> = ko.observableArray([
                { id: 1, name: getText("KDP010_163") },
                { id: 0, name: getText("KDP010_164") }
            ]);
            employeeListDisplay: KnockoutObservable<number> = ko.observable(0);
            enterPasswordAtLoginOption: KnockoutObservableArray<any> = ko.observableArray([
                { id: 1, name: getText("KDP010_166") },
                { id: 0, name: getText("KDP010_167") }
            ]);
            enterPasswordAtLogin: KnockoutObservable<number> = ko.observable(1);
            authenticationFailurePasswordInputOption: KnockoutObservable<any> = ko.observable({ id: 1, name: getText("KDP010_170") });
            authenticationFailurePasswordInputOption2: KnockoutObservable<any> = ko.observable({ id: 0, name: getText("KDP010_172") });
            authenticationFailurePasswordInput: KnockoutObservable<number> = ko.observable(0);
            numberAuthenfailures: KnockoutObservable<number> = ko.observable(1);
            required: KnockoutObservable<boolean> = ko.observable(false);
            constructor(){
                let self = this;
                self.authenticationFailurePasswordInput.subscribe((newValue) => {
                    if(newValue == 1){
                        self.required(true);
                    }else{
                        self.required(false);
                    }
                    $('#numberAuthenfailures').ntsError('check'); 
                });
            }
         }   
    }
}