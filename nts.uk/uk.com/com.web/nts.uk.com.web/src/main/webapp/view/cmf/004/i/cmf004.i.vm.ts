module nts.uk.com.view.cmf004.i {

    import getShared = nts.uk.ui.windows.getShared;
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {
            elapsedTime: KnockoutObservable<string>;
             //I2_1
            statusProcess: KnockoutObservable<string>;
            numberCategory: KnockoutObservable<string>;
            employeeProcess: KnockoutObservable<string>;
            datetimeProcess: KnockoutObservable<string>;
            numberError: KnockoutObservable<string>;
            //I4_1
            code: KnockoutObservable<string>;
            saveName: KnockoutObservable<string>;
            recoveryName: KnockoutObservable<string>;
            // dialog mode
            dialogMode: KnockoutObservable<string>;
            
            constructor() {
                let self = this;
                self.elapsedTime = ko.observable('00:05:12');
                //init I2_1
                self.statusProcess = ko.observable('テキスト');
                self.numberCategory = ko.observable('19/33 テキスト');
                self.employeeProcess = ko.observable('ID000000145');
                self.datetimeProcess = ko.observable('2017/08/02');
                self.numberError = ko.observable('2件');
                //init I4_1
                self.code = ko.observable('001');
                self.saveName = ko.observable('保存セット名称 A');
                self.recoveryName = ko.observable('保存セット名称 A_2016/06/15 13:15:20.zip');

            }

            start(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();               
                dfd.resolve(self);
                return dfd.promise();
            }

            // close popup
            public close(): void {
                nts.uk.ui.windows.close();
            }
            
        }




    }
}