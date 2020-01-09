module nts.uk.com.view.jmm018.a {
    import viewModelTabB = nts.uk.com.view.jmm018.b.viewmodel;
    import viewModelTab2 = nts.uk.com.view.jmm018.tabb.viewmodel;
    
    export module viewmodel {
        export class ScreenModel {
            
            eventManage: KnockoutObservable<any>;
            screenModelTab2: KnockoutObservable<any>;
            
            constructor(){
                let _self = this;
                _self.eventManage = ko.observable(new viewModelTabB.ScreenModel());
                _self.screenModelTab2 = ko.observable(new viewModelTab2.ScreenModel());
            }
            
            public start_page(): JQueryPromise<any> {
                
                var dfd = $.Deferred<any>();
                
                let _self = this;
                
                $.when(_self.eventManage().start_page(), _self.screenModelTab2().start()).done(function() {
                        dfd.resolve(_self);
                    });
                
                return dfd.promise();
            }
            
            /**
             * on select tab handle
             */
            public onSelectTabB(): void {
                $("#sidebar").ntsSideBar("init", {
                    activate: (event, info) => {
                        let _self = this;
                        _self.start_page();
                        _self.removeErrorMonitor();
                    }
                });
            }
            
             public onSelectTab2(): void {
                $("#sidebar").ntsSideBar("init", {
                    activate: (event, info) => {
                        let _self = this;
                        _self.start_page();
                        _self.removeErrorMonitor();
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