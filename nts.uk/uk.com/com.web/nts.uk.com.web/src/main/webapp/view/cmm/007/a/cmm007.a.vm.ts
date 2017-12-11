module nts.uk.com.view.cmm007.a {
    export module viewmodel {
        export class ScreenModel {
            
            constructor(){
                let _self = this;
            }
            
            public start_page(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                let _self = this;
                
                dfd.resolve();
                
                return dfd.promise();
            }
            
            /**
             * on select tab handle
             */
            public onSelectTabB(): void {
                $('#com_person').focus();
                $("#sidebar").ntsSideBar("init", {
                    active: SideBarTabIndex.FIRST,
                    activate: (event, info) => {
                       $('#com_person').focus();
                        let _self = this;
                        _self.removeErrorMonitor();
                    }
                });
            }
            
             /**
             * on select tab handle
             */
            public onSelectTabC(): void {
                $("#sidebar").ntsSideBar("init", {
                    active: SideBarTabIndex.SECOND,
                    activate: (event, info) => {
                       $('#tempAbsenceNo7').focus();
                        let _self = this;
                        _self.removeErrorMonitor();
                    }
                });
            }
            
             /**
             * on select tab handle
             */
            public onSelectTabD(): void {
                 $("#sidebar").ntsSideBar("init", {
                    active: SideBarTabIndex.THIRD,
                    activate: (event, info) => {
                       $('#plan_year_hd_frame1').focus();
                        let _self = this;
                        _self.removeErrorMonitor();
                    }
                });   
            }
            
             /**
             * on select tab handle
             */
            public onSelectTabE(): void {
                 $("#sidebar").ntsSideBar("init", {
                    active: SideBarTabIndex.FOURTH,
                    activate: (event, info) => {
                      $('#overtime_work_name1').focus();
                        let _self = this;
                        _self.removeErrorMonitor();
                    }
                }); 
            }
            
             /**
             * on select tab handle
             */
            public onSelectTabG(): void {
                 $("#sidebar").ntsSideBar("init", {
                    active: SideBarTabIndex.FIFTH,
                    activate: (event, info) => {
                      $('#work_day_off_name1').focus();
                        let _self = this;
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
    
    module SideBarTabIndex {
        export const FIRST = 0;                        
        export const SECOND = 1;
        export const THIRD = 2;
        export const FOURTH = 3;
        export const FIFTH = 4;
    }
}