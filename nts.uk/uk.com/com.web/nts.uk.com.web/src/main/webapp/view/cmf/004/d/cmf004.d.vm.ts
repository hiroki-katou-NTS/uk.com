module nts.uk.com.view.cmf004.d {
    import close = nts.uk.ui.windows.close;
    export module viewmodel {
        export class ScreenModel {
            
            fileNameUpload: KnockoutObservable<string>;
            timeLabel: KnockoutObservable<string>;
            statusLabel: KnockoutObservable<string>;
            statusUpload: KnockoutObservable<string>;
            statusDecom: KnockoutObservable<string>;
            statusCheck: KnockoutObservable<string>;
            constructor() {
                var self = this ;
                self.fileNameUpload = ko.observable("File Name Upload");
                self.timeLabel = ko.observable("00:00:05");
                self.statusLabel = ko.observable("Status Label");
                self.statusUpload = ko.observable("Status Upload");
                self.statusDecom = ko.observable("Status Upload");
                self.statusCheck = ko.observable("Status Check");
            }
            
            
           closeUp() {
            close();
           }
                
        }
    }
}