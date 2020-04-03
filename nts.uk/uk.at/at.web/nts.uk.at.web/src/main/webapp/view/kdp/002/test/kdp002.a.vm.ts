module nts.uk.at.view.kdp002.a {
    
    export module viewmodel {
        
        export class ScreenModel {
            public startPage(): JQueryPromise<void>  {
               let self = this;
               var dfd = $.Deferred<void>();

                service.startPage().done((res) => {
                    console.log(res);
                    dfd.resolve();
                }); 

                return dfd.promise();

               }

            public openKDP002A() {
                nts.uk.ui.windows.sub.modal('/view/kdp/002/a/index.xhtml').onClosed(function (): any {
                    
                });          
           }

        }
        
    }
}