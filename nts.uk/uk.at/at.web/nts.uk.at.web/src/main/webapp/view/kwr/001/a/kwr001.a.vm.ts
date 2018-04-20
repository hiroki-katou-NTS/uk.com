module nts.uk.at.view.kwr001.a {
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
            openScreenB () {
                var self = this;
                nts.uk.ui.windows.setShared('KWR001_B', self.data(), true);
                nts.uk.ui.windows.sub.modal('/view/kwr/001/b/index.xhtml').onClosed(function(): any {
                    nts.uk.ui.windows.getShared('KWR001_B');
                });
            }
            openScreenC () {
                var self = this;
                nts.uk.ui.windows.setShared('KWR001_C', self.data(), true);
                nts.uk.ui.windows.sub.modal('/view/kwr/001/c/index.xhtml').onClosed(function(): any {
                    nts.uk.ui.windows.getShared('KWR001_C');
                });
            }
        }
    }
}