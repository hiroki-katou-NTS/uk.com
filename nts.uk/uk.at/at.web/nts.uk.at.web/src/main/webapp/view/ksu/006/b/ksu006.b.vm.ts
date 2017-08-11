module nts.uk.pr.view.ksu006.b {
    export module viewmodel {

        import ErrorModel = service.model.ErrorModel;
        
        export class ScreenModel {
            status: KnockoutObservable<string>; //#KSU006_216 または #KSU006_217（画面モードに従う）
            totalRecord: KnockoutObservable<number>;
            totalRecordDisplay: KnockoutObservable<string>;//KSU006_218
            numberSuccess: KnockoutObservable<number>;
            numberSuccessDisplay: KnockoutObservable<string>;//KSU006_219
            numberFail: KnockoutObservable<number>;
            numberFailDisplay: KnockoutObservable<string>;//KSU006_220
            hasError: KnockoutObservable<boolean>;
            isDone: KnockoutObservable<boolean>;
            executeId: KnockoutObservable<string>;
            dataError: KnockoutObservableArray<ErrorModel>;
            listColumn: KnockoutObservableArray<NtsGridListColumn>;
            rowSelected: KnockoutObservable<string>;
            isGreaterThanTenError: KnockoutObservable<boolean>;
            
            constructor() {
                let self = this;
                self.status = ko.observable(nts.uk.resource.getText("KSU006_216"));
                
                self.totalRecord = ko.observable(0);
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
                self.hasError = ko.observable(false);
                self.executeId = ko.observable('');
                self.dataError = ko.observableArray([]);
                self.listColumn = ko.observableArray([
                    { headerText: "", key: 'order', dataType: "number", hidden: true, formatter: _.escape},
                    { headerText: nts.uk.resource.getText("KSU006_210"), key: 'lineNo', width: 80, formatter: _.escape},
                    { headerText: nts.uk.resource.getText("KSU006_211"), key: 'columnNo', width: 80, formatter: _.escape},
                    { headerText: nts.uk.resource.getText("KSU006_207"), key: 'wpkCode', width: 150, formatter: _.escape},
                    { headerText: nts.uk.resource.getText("KSU006_208"), key: 'acceptedDate', width: 80, formatter: _.escape},
                    { headerText: nts.uk.resource.getText("KSU006_209"), key: 'actualValue', width: 100, formatter: _.escape},
                    { headerText: nts.uk.resource.getText("KSU006_212"), key: 'errorContent', width: 300, formatter: _.escape}
                ]);
                self.rowSelected = ko.observable('');
                self.isGreaterThanTenError = ko.observable(false);
                
                // subscribe
                self.isDone.subscribe((state) => {
                    if (state) {
                        if (self.numberFail() <= 0) {
                            return;
                        }
                        self.loadDetailError().done(() => {
                            self.hasError(true);
                            nts.uk.ui.windows.getSelf().setSize(self.isGreaterThanTenError() ? 650 : 620, 870);
                        });
                    }
                });
            }

            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<void>();
                dfd.resolve();
                return dfd.promise();
            }
            
            public downloadDetailError() {
                let self = this;
                nts.uk.ui.block.grayout();
                service.downloadDetailError(self.executeId()).done(function() {
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                }).always(function(res) {
                    nts.uk.ui.block.clear();
                });
            }
            
            public stopImporting() {
                // TODO: interrupt process import then close dialog
                nts.uk.ui.windows.close();
            }
            
            public closeDialog() {
                nts.uk.ui.windows.close();
            }
            
            private loadDetailError(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                service.findErrors(self.executeId()).done(function(res: Array<ErrorModel>) {
                    if (res) {
                        self.dataError(res);
                        if (res.length > 10) {
                            self.isGreaterThanTenError(true);
                            self.dataError(res.slice(0, 10));
                        }
                        for (let i = 0; i < self.dataError().length; i++) {
                            self.dataError()[i].order = i;
                        }
                    }
                    dfd.resolve();
                }).fail((res: any) => {
                    nts.uk.ui.dialog.alertError(res.message);
                });
                return dfd.promise();
            }
        }
    }
}
