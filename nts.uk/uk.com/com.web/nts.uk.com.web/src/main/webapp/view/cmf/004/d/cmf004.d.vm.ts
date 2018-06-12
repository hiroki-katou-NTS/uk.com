module nts.uk.com.view.cmf004.d {
    export module viewmodel {
        import close = nts.uk.ui.windows.close;
        import getText = nts.uk.resource.getText;
        import setShared = nts.uk.ui.windows.setShared;
        import getShared = nts.uk.ui.windows.getShared;
        export class ScreenModel {

            fileName: KnockoutObservable<string> = ko.observable('');
            fileId: KnockoutObservable<string> = ko.observable('');
            password: KnockoutObservable<string> = ko.observable('');

            fileNameUpload: KnockoutObservable<string>;
            timeLabel: KnockoutObservable<string>;
            statusLabel: KnockoutObservable<string>;
            statusUpload: KnockoutObservable<string>;
            statusDecom: KnockoutObservable<string>;
            statusCheck: KnockoutObservable<string>;
            constructor() {
                let self = this;
                self.fileNameUpload = ko.observable("File Name Upload");
                self.timeLabel = ko.observable("00:00:05");
                self.statusLabel = ko.observable("Status Label");
                self.statusUpload = ko.observable("Status Upload");
                self.statusDecom = ko.observable("Status Upload");
                self.statusCheck = ko.observable("Status Check");
                let fileInfo = getShared("CMF004_D_PARAMS");
                if (fileInfo) {
                    self.fileId(fileInfo.fileId);
                    self.fileName(fileInfo.fileName);
                    self.password(fileInfo.password);
                }
            }
            
            startPage(): JQueryPromise<any> {
                let self = this, dfd = $.Deferred();
                let fileInfo = {
                    fileId: self.fileId(),
                    fileName: self.fileName(), 
                    password: self.password()
                };
                nts.uk.com.view.cmf004.d.service.extractData(fileInfo).done(function(result){
                    dfd.resolve();
                }).fail(function(result){
                    dfd.reject();
                }); 
                return dfd.promise();
            }
             
            closeUp() {
            
            }
            continueProcessing(){
        
            } 
        }
    }
}