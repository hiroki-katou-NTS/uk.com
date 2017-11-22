module nts.uk.com.view.cmm007.c {
    import MailServerFindDto = model.SampleDto;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import blockUI = nts.uk.ui.block;
    
    export module viewmodel {
        export class ScreenModel {
            
            constructor(){
                let _self = this;
                
            }
            
             /**
             * init default data when start page
             */
            public start_page(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                var _self = this;
                
                dfd.resolve();
                return dfd.promise();
            }
       }
           
    }
}