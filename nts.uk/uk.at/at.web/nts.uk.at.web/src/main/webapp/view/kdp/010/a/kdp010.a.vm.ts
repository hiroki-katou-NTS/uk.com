module nts.uk.at.view.kdp010.a {
    import viewModelscreenB = nts.uk.at.view.kdp010.b.viewmodel;
    import viewModelscreenE = nts.uk.at.view.kdp010.e.viewmodel;
    
    export module viewmodel {
        export class ScreenModel {
            viewmodelB: KnockoutObservable<any>;
            viewmodelE: KnockoutObservable<any>;
            
            constructor(){
                let self = this;
                self.viewmodelB = ko.observable(new viewModelscreenB.ScreenModel());
                self.viewmodelE = ko.observable(new viewModelscreenE.ScreenModel());
            }
            
            public startPage(): JQueryPromise<any> {
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
                        self.startPage();
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
    }
}