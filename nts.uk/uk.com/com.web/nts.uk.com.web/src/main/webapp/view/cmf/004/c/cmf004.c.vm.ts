module nts.uk.com.view.cmf004.c {
    export module viewmodel {
        import getText = nts.uk.resource.getText;
        import setShared = nts.uk.ui.windows.setShared;
        import getShared = nts.uk.ui.windows.getShared;
        export class ScreenModel {
            fileName: KnockoutObservable<string> = ko.observable('');
            fileId: KnockoutObservable<string> = ko.observable('');
            password: KnockoutObservable<string> = ko.observable('');

            constructor() {
                let self = this;
                let fileInfo = getShared('CMF004lParams');
                if (fileInfo) {
                    self.fileName(fileInfo.fileName);
                    self.fileId(fileInfo.fileId);
                }
            }

            private processing(): void {
                let self = this;
                let fileInfo = {
                    fileId: self.fileId(),
                    fileName: self.fileName(), 
                    password: self.password()
                };
                setShared("CMF004_D_PARAMS", {fileInfo: fileInfo, continuteProcessing: true});
                nts.uk.ui.windows.close();
            }
            
            private cancelProcessing(): void {
                setShared("CMF004_D_PARAMS", {continuteProcessing: false});
                nts.uk.ui.windows.close();
            }
        }
    }
}