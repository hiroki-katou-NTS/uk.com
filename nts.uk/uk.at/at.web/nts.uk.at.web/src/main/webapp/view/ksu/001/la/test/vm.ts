module test {
    export module viewmodelTest{
        export class ScreenModel {
            currentScreen: any = null;          
           
            public startPage():JQueryPromise<void>{
                let self = this;
                var dfd = $.Deferred<void>();
                dfd.resolve();
                return dfd.promise();
            }

            public openDialogLA(): void {
                let self = this;
                self.currentScreen = nts.uk.ui.windows.sub.modeless("/view/ksu/001/la/index.xhtml");
            }   
            public openDialogU(): void {
                let self = this;
                self.currentScreen = nts.uk.ui.windows.sub.modeless("/view/ksu/001/u/index.xhtml");
            }   
        }
    }    
}