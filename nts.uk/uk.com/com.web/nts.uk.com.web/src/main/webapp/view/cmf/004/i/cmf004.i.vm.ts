module nts.uk.com.view.cmf004.i.viewmodel {
    export class ScreenModel {
        elapsedTime     : KnockoutObservable<string> = ko.observable('');
         //I2_1
        statusProcess   : KnockoutObservable<string> = ko.observable('');
        numberCategory  : KnockoutObservable<string> = ko.observable('');
        employeeProcess : KnockoutObservable<string> = ko.observable('');
        datetimeProcess : KnockoutObservable<string> = ko.observable('');
        numberError     : KnockoutObservable<string> = ko.observable('');
        //I4_1
        code            : KnockoutObservable<string> = ko.observable('');
        saveName        : KnockoutObservable<string> = ko.observable('');
        recoveryName    : KnockoutObservable<string> = ko.observable('');

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
        }

        start(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            dfd.resolve(self);
            return dfd.promise();
        }

        // close popup
        close(): void {
            nts.uk.ui.windows.close();
        }
    }
}