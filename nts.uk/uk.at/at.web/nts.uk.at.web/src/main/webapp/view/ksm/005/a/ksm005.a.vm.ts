module nts.uk.at.view.ksm005.a {

    
    export module viewmodel {

        export class ScreenModel {
            constructor() {
                var self = this;
            }
            
             // 締め期間確認 
            /**
             * open dialog D
             */
            public openMasterSettingDialog(): void {
                nts.uk.request.jump("/view/ksm/005/b/index.xhtml");
            }
    
            /**
             * open dialog Assignment setting (init screen c)
             */
            public openAssignmentSettingDialog(): void {
                nts.uk.request.jump("/view/ksm/005/c/index.xhtml");
            }

        }
        

    }
}