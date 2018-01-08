module nts.uk.at.view.kal004.tab2.viewModel {
    
    export class ScreenModel {
        listAlarmPerSetting: KnockoutObservableArray<any>;
        constructor() {
            
        }
        private OpenDialog(): void {
             nts.uk.ui.windows.sub.modal("../b/index.xhtml").onClosed(() => {
                console.log("success!");
            }); 
        }
    }
}