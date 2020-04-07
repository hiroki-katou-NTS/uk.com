module nts.uk.at.view.kdp002.a {
    
    export module viewmodel {
        
        export class ScreenModel {
            dataScreenT: KnockoutObservable<any> = ko.observable({});
            
            constructor() {
                let self = this;
                
            }

            public startPage(): JQueryPromise<void>  {
               let self = this;
               var dfd = $.Deferred<void>();

                service.startPage().done((res) => {
                    console.log(res);
                    self.dataScreenT(res.stampToSuppress);
                    dfd.resolve();
                }); 

                return dfd.promise();

               }

            public openKDP002A() {
                nts.uk.ui.windows.sub.modal('/view/kdp/002/a/index.xhtml').onClosed(function (): any {
                    
                });          
            }

            public openKDP002T() {
                let self = this;
                nts.uk.ui.windows.setShared('KDP010_2T', self.dataScreenT(), true);
                nts.uk.ui.windows.sub.modal('/view/kdp/002/t/index.xhtml').onClosed(function (): any {
                    
                });          
            }

        }
        
    }
}