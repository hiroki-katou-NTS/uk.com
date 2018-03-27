module nts.uk.at.view.kmk004.a {
    export module viewmodel {
        
        import WorktimeSettingVM = nts.uk.at.view.kmk004.shr.worktime.setting.viewmodel;
        
        export class ScreenModel {
            
            worktimeSetting: WorktimeSettingVM.ScreenModel;
            
            constructor() {
                let self = this;
                
                self.worktimeSetting = new WorktimeSettingVM.ScreenModel();
            }
            
            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                nts.uk.ui.block.invisible();
                let self = this;
                let dfd = $.Deferred<void>();
                
                self.worktimeSetting.initialize().done(() => {
                    dfd.resolve();
                });
                return dfd.promise();
            }
            
        } // --- end ScreenModel
        
    }
}