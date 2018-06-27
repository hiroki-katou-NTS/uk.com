module nts.uk.com.view.cmf004.i.viewmodel {
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import alertError = nts.uk.ui.dialog;
    import block = nts.uk.ui.block;
    import close = nts.uk.ui.windows.close;
    import dialog = nts.uk.ui.dialog;
    export class ScreenModel {
        elapsedTime: KnockoutObservable<string> = ko.observable("00:00:00");
        //I2_1
        statusProcess: KnockoutObservable<string> = ko.observable('');
        numberCategory: KnockoutObservable<string> = ko.observable('');
        numberTotalCategory: KnockoutObservable<string> = ko.observable('');
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

        //
        timeStart: any;
        // interval 1000ms request to server
        interval: any;
        constructor() {
            let self = this;
            self.timeStart = new Date();

            

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
                recoverySourceCode: self.recoverySourceCode,
                recoverySourceName: self.recoverySourceName,
                supplementaryExplanation: self.supplementaryExplanation,
                recoveryMethodOptions: self.recoveryMethodOptions

            };
            console.log(paramRestore);
            service.performDataRecover(paramRestore).done((res) => {

            }).fail((err) => {
            });
            return dfd.promise();
        }

        //// 1秒おきに下記を実行
        //データ保存監視処理: 
        startFollow() {
            let self = this;
            self.interval = setInterval(self.followProsessing, 1000, self);
        }

        public followProsessing(self): void {
            let recoveryProcessingId = self.recoveryProcessingId;

            service.followProsess(recoveryProcessingId).done(function(res: any) {
                let recoveryProcessing = res;

                // 経過時間＝現在時刻－開始時刻
                let startTime = self.timeStart;
                let timeNow = new Date();
                let result = moment.utc(moment(timeNow, "HH:mm:ss").diff(moment(startTime, "HH:mm:ss"))).format("HH:mm:ss");
                self.elapsedTime(result);
                //init I2_1
                self.statusProcess(self.getStatusEnum(recoveryProcessing.operatingCondition));
                self.numberCategory(recoveryProcessing.categoryCnt);
                self.numberTotalCategory(recoveryProcessing.categoryTotalCount);
                self.employeeProcess(recoveryProcessing.processTargetEmpCode);
                self.datetimeProcess(recoveryProcessing.recoveryDate);
                self.numberError(recoveryProcessing.errorCount);
                //init I4_1
                self.code(self.recoverySourceCode);
                self.saveName(self.recoverySourceName);
                self.recoveryName('保存セット名称 A_2016/06/15 13:15:20.zip');
                
                // update mode when end: DONE, INTERRUPTION_END, ABNORMAL_TERMINATION
                // 完了, 中断終了, 異常終了
                if ((recoveryProcessing.operatingCondition == 3) || (recoveryProcessing.operatingCondition == 1) || (recoveryProcessing.operatingCondition == 5)) {
                    // stop auto request to server
                    clearInterval(self.interval);
                }

            }).fail(function(res: any) {
                console.log("followProsessing fail");
            });
        }

        operatingCondition(){
            
        }

        // close popup
        close(): void {
            nts.uk.ui.windows.close();
        }
        
        public getStatusEnum(value): string {
                if (value && value === 0) {
                    return getText('Enum_OperatingCondition_INPROGRESS');
                } else if (value && value === 1) {
                    return getText('Enum_OperatingCondition_INTERRUPTION_END');
                } else if (value && value === 2) {
                    return getText('Enum_OperatingCondition_DELETING');
                } else if (value && value === 3) {
                    return getText('Enum_OperatingCondition_DONE');
                } else if (value && value === 4) {
                    return getText('Enum_OperatingCondition_INPREPARATION');
                } else if (value && value === 5) {
                    return getText('Enum_OperatingCondition_ABNORMAL_TERMINATION');
                } 
            }
    }
}


