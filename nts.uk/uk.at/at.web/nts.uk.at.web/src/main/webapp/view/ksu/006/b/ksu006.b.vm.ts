module nts.uk.pr.view.ksu006.b {
    export module viewmodel {

        export class ScreenModel {

            status: KnockoutObservable<string>; //#KSU006_216 または #KSU006_217（画面モードに従う）
            
            totalRecord: KnockoutObservable<number>;
            totalRecordDisplay: KnockoutObservable<string>;//KSU006_218
            
            numberSuccess: KnockoutObservable<number>;
            numberSuccessDisplay: KnockoutObservable<string>;//KSU006_219
            
            numberFail: KnockoutObservable<number>;
            numberFailDisplay: KnockoutObservable<string>;//KSU006_220
            
            isHasError: KnockoutObservable<boolean>;
            isDone: KnockoutObservable<boolean>;
            
            dataError: KnockoutObservableArray<any>;
            listColumn: KnockoutObservableArray<any>;
            rowSelected: KnockoutObservable<string>;
            
            constructor() {
                let self = this;
                self.status = ko.observable(nts.uk.resource.getText("KSU006_216"));
                
                self.totalRecord = ko.observable(null);
                self.totalRecordDisplay = ko.computed(() => {
                    return nts.uk.resource.getText("KSU006_218", [self.totalRecord()]);
                });
                self.numberSuccess = ko.observable(0);
                self.numberSuccessDisplay = ko.computed(() => {
                    return nts.uk.resource.getText("KSU006_219", [self.numberSuccess()]);
                });
                self.numberFail = ko.observable(0);
                self.numberFailDisplay = ko.computed(() => {
                    return nts.uk.resource.getText("KSU006_220", [self.numberFail()]);
                });
                self.isDone = ko.observable(false);
                self.isHasError = ko.computed(() => {
                    if (self.numberFail() != 0 && self.isDone()) {
                        return true;
                    }
                    return false;
                });
                
                self.dataError = ko.observableArray([]);
                self.listColumn = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KSU006_210"), key: 'lineNo', width: 80, dataType: 'number'},
                    { headerText: nts.uk.resource.getText("KSU006_211"), key: 'columnNo', width: 80, dataType: 'number'},
                    { headerText: nts.uk.resource.getText("KSU006_207"), key: 'wpkCode', width: 150, dataType: 'string'},
                    { headerText: nts.uk.resource.getText("KSU006_208"), key: 'date', width: 80},
                    { headerText: nts.uk.resource.getText("KSU006_209"), key: 'value', width: 100},
                    { headerText: nts.uk.resource.getText("KSU006_212"), key: 'content', width: 300, dataType: 'string'}
                ]);
                self.rowSelected = ko.observable('');
            }

            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<void>();
                self.totalRecord(nts.uk.ui.windows.getShared("totalRecord"));
                dfd.resolve();
                return dfd.promise();
            }
            
            public getTextButton(): string {
                if (this.isDone()) {
                    return nts.uk.resource.getText("KSU006_215");
                }
                return nts.uk.resource.getText("KSU006_214");
            }
            
            public closeDialog() {
                nts.uk.ui.windows.close();
            }
        }
    }
}
