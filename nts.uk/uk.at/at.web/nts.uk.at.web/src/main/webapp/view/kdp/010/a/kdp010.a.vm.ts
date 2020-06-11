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
            optionHighlight: KnockoutObservableArray<any> = ko.observableArray([
                { id: 1, name: nts.uk.resource.getText("KDP010_163") },
                { id: 0, name: nts.uk.resource.getText("KDP010_164") }
            ]);
            selectedHighlight: KnockoutObservable<number> = ko.observable(0);
            optionHighlight2: KnockoutObservableArray<any> = ko.observableArray([
                { id: 1, name: nts.uk.resource.getText("KDP010_166") },
                { id: 0, name: nts.uk.resource.getText("KDP010_167") }
            ]);
            selectedHighlight2: KnockoutObservable<number> = ko.observable(0);
            constructor(){
                let self = this;
            }
         }   
    }
}