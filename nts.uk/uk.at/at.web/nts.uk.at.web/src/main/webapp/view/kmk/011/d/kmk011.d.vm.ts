module nts.uk.at.view.kmk011.d {
    
    import viewModelScreenE = nts.uk.at.view.kmk011.e.viewmodel;
    
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import blockUI = nts.uk.ui.block;
  
    export module viewmodel {

        export class ScreenModel {
            screenE: KnockoutObservable<any>;
            
            constructor() {
                var _self = this;
                _self.screenE = ko.observable(new viewModelScreenE.ScreenModel());
            }
            
            public start_page(typeStart: number) : JQueryPromise<any> {
                let _self = this;
                var dfd = $.Deferred<any>();
                
                // load all
                if (typeStart == SideBarTabIndex.FIRST) {
                    nts.uk.ui.errors.clearAll()
                    blockUI.grayout();
                    $.when(_self.fillData(), _self.findAllManageUseUnit()).done(function() {
                        dfd.resolve(_self);
                        blockUI.clear();
                    });    
                } else {
                    // Process for screen D (Mother of all screen)
                    nts.uk.ui.errors.clearAll()
                    blockUI.grayout();
                    $.when(_self.screenE().start_page()).done(function() {
                        dfd.resolve(_self);    
                        blockUI.clear();
                    });
                }
                
                dfd.resolve();
                return dfd.promise();
            }
            
            /**
             * on select tab handle
             */
            
            public onSelectTabOne(): void {
                $("#sidebar").ntsSideBar("init", {
                    active: SideBarTabIndex.FIRST,
                    activate: (event: any, info: any) => {
                        let _self = this;
                        _self.start_page(SideBarTabIndex.FIRST);
                    }
                });
            }
            
            public onSelectTabTwo(): void {
                let _self = this;
                if (_self.isDisableTab() == false) {
                    $("#sidebar").ntsSideBar("init", {
                        active: SideBarTabIndex.SECOND,
                        activate: (event: any, info: any) => {
                            _self.start_page(SideBarTabIndex.SECOND);
                        }
                    });
                }
            }
            
            private fillData() : JQueryPromise<any> {
                let _self = this;
                var dfd = $.Deferred<any>();
                
                dfd.resolve();
                return dfd.promise();
            }
            
            private findAllManageUseUnit() : JQueryPromise<any> {
                let _self = this;
                var dfd = $.Deferred<any>();
                
                dfd.resolve();
                return dfd.promise();
            }
            
            private isDisableTab() : boolean {
                return false;    
            }
        }
        
        export enum SideBarTabIndex {
            FIRST = 0,
            SECOND = 1,
        }
        
    }
}