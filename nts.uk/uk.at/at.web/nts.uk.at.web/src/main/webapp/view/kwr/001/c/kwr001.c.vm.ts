module nts.uk.at.view.kwr001.c {
    export module viewmodel {
        export class ScreenModel {
            data: KnockoutObservable<number>;
            
            constructor() {
                var self = this;
                self.data = ko.observable(1);
            }
            
            public startPage(): JQueryPromise<void>  {
                var dfd = $.Deferred<void>();
                var self = this;
                return dfd.promise();
            }
            openScreenD () {
                var self = this;
                nts.uk.ui.windows.setShared('KWR001_D', self.data(), true);
                nts.uk.ui.windows.sub.modal('/view/kwr/001/d/index.xhtml').onClosed(function(): any {
                    nts.uk.ui.windows.getShared('KWR001_D');
                });
            }
            
            saveData(): void {
               
            }
        }
    }
}