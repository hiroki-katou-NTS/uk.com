module nts.uk.com.view.cmf004.i.viewmodel {
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
        upplementaryExplanation: KnockoutObservable<string> = ko.observable('');
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
                    self.recoveryProcessingId = recoveryInfo.dataRecoveryProcessId,
                        self.employeeList = recoveryInfo.employeeList,
                        self.recoveryCategoryList = recoveryInfo.recoveryCategoryList,
                        self.recoveryFile = recoveryInfo.recoveryFile,
                        self.recoverySourceCode = recoveryInfo.recoverySourceCode,
                        self.recoverySourceName = recoveryInfo.recoverySourceName,
                        self.upplementaryExplanation = recoveryInfo.upplementaryExplanation,
                        self.recoveryMethodOptions = recoveryInfo.recoveryMethodOptions
                }
            }

        }

        start(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            dfd.resolve(self);
            let paramRestore = {
                recoveryProcessingId: self.recoveryProcessingId(),
                employeeList: self.employeeList(),
                recoveryCategoryList: self.recoveryCategoryList(),
                recoveryFile: self.recoveryFile(),
                recoverySourceCode: self.recoverySourceCode(),
                recoverySourceName: self.recoverySourceName(),
                upplementaryExplanation: self.upplementaryExplanation(),
                recoveryMethodOptions: self.recoveryMethodOptions()

            };
            service.performDataRecover(paramRestore).done((res) => {
                if ((res) && (res != "")) {

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