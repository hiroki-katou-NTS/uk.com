module nts.uk.com.view.cmf004.i.viewmodel {
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import alertError = nts.uk.ui.dialog;
    export class ScreenModel {
        elapsedTime: KnockoutObservable<string> = ko.observable('');
        //I2_1
        statusProcess: KnockoutObservable<string> = ko.observable('');
        numberCategory: KnockoutObservable<string> = ko.observable('');
        employeeProcess: KnockoutObservable<string> = ko.observable('');
        datetimeProcess: KnockoutObservable<string> = ko.observable('');
        numberError: KnockoutObservable<string> = ko.observable('');
        //I4_1
        code: KnockoutObservable<string> = ko.observable('');
        saveName: KnockoutObservable<string> = ko.observable('');
        recoveryName: KnockoutObservable<string> = ko.observable('');
        recoverySourceCode: KnockoutObservable<string> = ko.observable('');

        //Send to Service
        recoveryProcessingId: KnockoutObservable<string> = ko.observable('');
        employeeList: KnockoutObservableArray<any> = ko.observableArray([]);
        recoveryCategoryList: KnockoutObservableArray<any> = ko.observableArray([]);
        recoveryFile: KnockoutObservable<string> = ko.observable('');
        recoverySourceName: KnockoutObservable<string> = ko.observable('');
        supplementaryExplanation: KnockoutObservable<string> = ko.observable('');
        recoveryMethodOptions: KnockoutObservable<string> = ko.observable('');

        constructor() {
            let self = this;
            self.elapsedTime('00:05:12');
            //init I2_1
            self.statusProcess('テキスト');
            self.numberCategory('19/33 テキスト');
            self.employeeProcess('ID000000145');
            self.datetimeProcess('2017/08/02');
            self.numberError('2件');
            //init I4_1
            self.code('001');
            self.saveName('保存セット名称 A');
            self.recoveryName('保存セット名称 A_2016/06/15 13:15:20.zip');

            if (getShared("CMF004IParams")) {
                let recoveryInfo = getShared("CMF004IParams");
                if (recoveryInfo) {
                    let self = this;
                    self.recoveryProcessingId = recoveryInfo.recoveryProcessingId,
                        self.employeeList = recoveryInfo.employeeList,
                        self.recoveryCategoryList = recoveryInfo.recoveryCategoryList,
                        self.recoveryFile = recoveryInfo.recoveryFile,
                        self.recoverySourceCode = recoveryInfo.recoverySourceCode,
                        self.recoverySourceName = recoveryInfo.recoverySourceName,
                        self.supplementaryExplanation = recoveryInfo.supplementaryExplanation,
                        self.recoveryMethodOptions = recoveryInfo.recoveryMethodOptions
                }
            }

        }

        start(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            dfd.resolve(self);
            let paramRestore = {
                recoveryProcessingId: self.recoveryProcessingId,
                employeeList: self.employeeList,
                recoveryCategoryList: self.recoveryCategoryList,
                recoveryFile: self.recoveryFile,
                //recoverySourceCode: self.recoverySourceCode,
                //recoverySourceName: self.recoverySourceName,
                //supplementaryExplanation: self.supplementaryExplanation,
                recoveryMethodOptions: self.recoveryMethodOptions

            };
            service.performDataRecover(paramRestore).done((res) => {
                console.log(paramRestore);
                if ((res) && (res != "")) {
                    //// 1秒おきに下記を実行
                    nts.uk.deferred.repeat(conf => conf
                        .task(() => {
                            return service.performDataRecover(res).done(function(res: any) {
                                // update state on screen
                                let status;
                                if (res.taskDatas.length > 0) {
                                    
                                }
                                if (res.succeeded || res.failed) {
                                    
                                    //block.clear();
                                }
                                if (res.running) {
                                    // 経過時間＝現在時刻－開始時刻
                                    
                                }
                            });
                        }).while(infor => {
                            return infor.pending || infor.running;
                        }).pause(1000));
                }
            }).fail((err) => {
            });


            return dfd.promise();
        }

        // close popup
        close(): void {
            nts.uk.ui.windows.close();
        }
    }
}