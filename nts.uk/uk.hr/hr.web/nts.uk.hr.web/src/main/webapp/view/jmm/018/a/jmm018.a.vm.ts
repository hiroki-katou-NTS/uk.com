module nts.uk.com.view.jmm018.a {
    import viewModelTabB = nts.uk.com.view.jmm018.b.viewmodel;
    
    export module viewmodel {
        export class ScreenModel {
            
            systemDefine: KnockoutObservable<any>;
            
            constructor(){
                let _self = this;
                _self.systemDefine = ko.observable(new viewModelTabB.ScreenModel());
            }
            
            public start_page(): JQueryPromise<any> {
                
                var dfd = $.Deferred<any>();
                
                let _self = this;
                
                $.when(
                    _self.systemDefine().start_page()).done(function() {
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
            
            /**
            *   remove all alert error all tab
            **/
            public removeErrorMonitor(): void {
                $('.nts-input').ntsError('clear');    
            }
       }    
    }
    
}