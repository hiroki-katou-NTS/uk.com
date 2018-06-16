module nts.uk.com.view.cmf004.d {
    export module viewmodel {
        import block = nts.uk.ui.block;
        import close = nts.uk.ui.windows.close;
        import getText = nts.uk.resource.getText;
        import setShared = nts.uk.ui.windows.setShared;
        import getShared = nts.uk.ui.windows.getShared;
        export class ScreenModel {
            // interval 1000ms request to server
            interval: any;

            fileName: KnockoutObservable<string> = ko.observable('');
            fileId: KnockoutObservable<string> = ko.observable('');
            password: KnockoutObservable<string> = ko.observable('');
            processingId: string = nts.uk.util.randomId();
            timeLabel: KnockoutObservable<string>;
            statusLabel: KnockoutObservable<string>;
            statusUpload: KnockoutObservable<string>;
            statusDecom: KnockoutObservable<string>;
            statusCheck: KnockoutObservable<string>;
            constructor() {
                let self = this;
                self.fileNameUpload = ko.observable("File Name Upload");
                self.timeLabel = ko.observable("00:00:00");
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
                    processingId: self.processingId,
                    fileId: self.fileId(),
                    fileName: self.fileName(),
                    password: self.password()
                };
                service.extractData(fileInfo).done(function(result) {
                    let self = this;
                    dfd.resolve();
                    block.invisible();
                    self.taskId = result.id;
                    // 1秒おきに下記を実行
                    nts.uk.deferred.repeat(conf => conf
                        .task(() => {
                            return nts.uk.request.asyncTask.getInfo(self.taskId).done(function(res: any) {
                                // update state on screen
                                if (res.running || res.succeeded || res.cancelled) {
                                    //Update status
                                    let status = res.taskDatas[0].valueAsString;
                                    
                                    if (res.running) {
                                        // 経過時間＝現在時刻－開始時刻
//                                        self.timeNow = new Date();
//                                        let over = (self.timeNow.getSeconds() + self.timeNow.getMinutes() * 60 + self.timeNow.getHours() * 60) - (self.timeStartt.getSeconds() + self.timeStartt.getMinutes() * 60 + self.timeStartt.getHours() * 60);
//                                        let time = new Date(null);
//                                        time.setSeconds(over); // specify value for SECONDS here
//                                        let result = time.toISOString().substr(11, 8);
//
//                                        self.timeLabel(result);
                                    }
                                    if (res.succeeded) {
                                        block.clear();
                                    }
                                }
                            });
                        }).while(infor => {
                            return infor.pending || infor.running;
                        }).pause(1000));
                }).fail(function(result) {
                    dfd.reject();
                });
                return dfd.promise();
            }

            closeUp() {

            }
            continueProcessing() {

            }
        }
    }
}