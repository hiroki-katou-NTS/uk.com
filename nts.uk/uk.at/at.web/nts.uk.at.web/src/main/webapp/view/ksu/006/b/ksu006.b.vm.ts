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
                    { headerText: nts.uk.resource.getText("KSU006_208"), key: 'acceptedDate', width: 130, formatter: _.escape},
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
                            nts.uk.ui.windows.getSelf().setSize(self.isGreaterThanTenError() ? 650 : 620, 920);
                            $('#donwloadError').focus();
                        });
                    }
                });
            }

            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<void>();
                self.findTaskId().done(() => {
                    dfd.resolve();
                });
                return dfd.promise();
            }
            
            private findTaskId(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                // get extract condition
                let extractCondition: any = nts.uk.ui.windows.getShared("ExtractCondition");
                
                // set status in-complete
                self.status(nts.uk.resource.getText("KSU006_216"));
                
                // find task id
                service.executeImportFile(extractCondition).then(function(res: any) {
                    self.executeId(res.executeId);
                    $('#stopExecute').focus();
                    self.updateState(res.taskId);
                }).done(function(res: any) {
                    dfd.resolve(res);
                }).fail(function(res: any) {
                    nts.uk.ui.dialog.alertError(res.message);
                });
                return dfd.promise();
            }
            
            private updateState(taskId: string) {
                let self = this;
                let dfd = $.Deferred();
                
                // start count time
                $('.countdown').downCount();
                
                nts.uk.deferred.repeat(conf => conf
                .task(() => {
                    nts.uk.request.specials.getAsyncTaskInfo(taskId).done(function(res: any) {
                        if (res.running || res.succeeded || res.failed) {
                            _.forEach(res.taskDatas, item => {
                                if (item.key == 'TOTAL_RECORD') {
                                    self.totalRecord(item.valueAsNumber);
                                }
                                if (item.key == 'SUCCESS_CNT') {
                                    self.numberSuccess(item.valueAsNumber);
                                }
                                if (item.key == 'FAIL_CNT') {
                                    self.numberFail(item.valueAsNumber);
                                }
                            });
                        }
                        if (res.succeeded || res.failed) {
                            self.isDone(true);
                            self.status(nts.uk.resource.getText("KSU006_217"));
                            
                            // end count time
                            $('.countdown').stopCountDown();
                            if (res.error) {
                                nts.uk.ui.dialog.alertError(res.error.message);
                            }
                            if (res.succeeded) {
                                $('#closeDialog').focus();
                            }
                        }
                        dfd.resolve(res);
                    });
                    return dfd.promise();
                }).while(info => {
                    return info.pending || info.running;
                })
                .pause(1000))
            }
            
            public downloadDetailError() {
                let self = this;
                nts.uk.ui.block.grayout();
                service.downloadDetailError(self.executeId()).done(function() {
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
