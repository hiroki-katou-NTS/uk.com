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
                    active: 0,
                    activate: (event, info) => {
                       $('#com_person').focus();
                    }
                });
            }
            
             /**
             * on select tab handle
             */
            public onSelectTabC(): void {
                $("#sidebar").ntsSideBar("init", {
                    active: 1,
                    activate: (event, info) => {
                       $('#c3_15').focus();
                    }
                });
            }
            
             /**
             * on select tab handle
             */
            public onSelectTabD(): void {
                 $("#sidebar").ntsSideBar("init", {
                    active: 2,
                    activate: (event, info) => {
                       $('#plan_year_hd_frame1').focus();
                    }
                });   
            }
            
             /**
             * on select tab handle
             */
            public onSelectTabE(): void {
                 $("#sidebar").ntsSideBar("init", {
                    active: 3,
                    activate: (event, info) => {
                      $('#overtime_work_name1').focus();
                    }
                }); 
            }
            
             /**
             * on select tab handle
             */
            public onSelectTabG(): void {
                 $("#sidebar").ntsSideBar("init", {
                    active: 4,
                    activate: (event, info) => {
                      $('#work_day_off_name1').focus();
                    }
                });
            }
       }      
    }
}