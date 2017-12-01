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
            
            public onSelectTabB(): void {
                 $('#com_person').focus();
            }
            
            public onSelectTabC(): void {
                 $('#c3_15').focus();
            }
            
            public onSelectTabD(): void {
                 $('#plan_year_hd_frame1').focus();
            }
            
            public onSelectTabE(): void {
                 $('#overtime_work_name1').focus();
            }
            
            public onSelectTabG(): void {
                 $('#work_day_off_name1').focus();
            }
       }      
    }
}