module nts.uk.at.view.kdp010.a {
    import viewModelscreenB = nts.uk.at.view.kdp010.b.viewmodel;
    import viewModelscreenE = nts.uk.at.view.kdp010.e.viewmodel;
    
    export module viewmodel {
        export class ScreenModel {
            viewmodelA: KnockoutObservable<any>;
            viewmodelB: KnockoutObservable<any>;
            viewmodelE: KnockoutObservable<any>;
            
            constructor(){
                let self = this;
                self.viewmodelA = ko.observable(new ViewmodelA());
                self.viewmodelB = ko.observable(new viewModelscreenB.ScreenModel());
                self.viewmodelE = ko.observable(new viewModelscreenE.ScreenModel());
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
                        self.removeErrorMonitor();
                    }
                });
            }
            
             public selectScreenB(): void {
                $("#sidebar").ntsSideBar("init", {
                    activate: (event, info) => {
                        let self = this;
                        self.viewmodelB().start();
                        self.removeErrorMonitor();
                    }
                });
            }
            
            public selectScreenE(): void {
                $("#sidebar").ntsSideBar("init", {
                    activate: (event, info) => {
                        let self = this;
                        self.viewmodelE().start();
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
        export class ViewmodelA {
            correcValue: KnockoutObservable<number> = ko.observable(10);
            letterColors: KnockoutObservable<string> = ko.observable("#ffffff");
            backgroundColors: KnockoutObservable<string> = ko.observable("#0033cc");
            stampValue: KnockoutObservable<number> = ko.observable(3);
            employeeListDisplayOption: KnockoutObservableArray<any> = ko.observableArray([
                { id: 1, name: nts.uk.resource.getText("KDP010_163") },
                { id: 0, name: nts.uk.resource.getText("KDP010_164") }
            ]);
            employeeListDisplay: KnockoutObservable<number> = ko.observable(0);
            enterPasswordAtLoginOption: KnockoutObservableArray<any> = ko.observableArray([
                { id: 1, name: nts.uk.resource.getText("KDP010_166") },
                { id: 0, name: nts.uk.resource.getText("KDP010_167") }
            ]);
            enterPasswordAtLogin: KnockoutObservable<number> = ko.observable(1);
            authenticationFailurePasswordInputOption: KnockoutObservable<any> = ko.observable({ id: 1, name: nts.uk.resource.getText("KDP010_171") });
            authenticationFailurePasswordInputOption2: KnockoutObservable<any> = ko.observable({ id: 0, name: nts.uk.resource.getText("KDP010_172") });
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