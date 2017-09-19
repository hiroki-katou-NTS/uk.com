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
            listColumn: KnockoutObservableArray<any>;
            rowSelected: KnockoutObservable<string>;
            isGreaterThanTenError: KnockoutObservable<boolean>;
            taskId: KnockoutObservable<string>;
            
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
                self.taskId = ko.observable('');
                
                // subscribe
                self.isDone.subscribe((state) => {
                    if (!state) {
                        return;
                    }
                    if (self.numberFail() <= 0) {
                        return;
                    }
                    self.loadDetailError().done(() => {
                        self.hasError(true);
                        nts.uk.ui.windows.getSelf().setSize(self.isGreaterThanTenError() ? 650 : 620, 920);
                        $('#donwloadError').focus();
                    });
                });
            }

            /**
             * startPage
             */
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<void>();
                dfd.resolve();
                return dfd.promise();
            }
            
            /**
             * execute
             */
            public execute() {
                let self = this;
                // get extract condition
                let extractCondition: any = nts.uk.ui.windows.getShared("ExtractCondition");
                
                // set status in-complete
                self.status(nts.uk.resource.getText("KSU006_216"));
                
                // find task id
                service.executeImportFile(extractCondition).done(function(res: any) {
                    $('#stopExecute').focus();
                    self.executeId(res.executeId);
                    self.taskId(res.taskInfor.id);
                    
                    // updateState
                    self.updateState();
                }).fail(function(res: any) {
                    self.showMessageError(res);
                });
            }
            
            /**
             * updateState
             */
            private updateState() {
                let self = this;
                // start count time
                $('.countdown').startCount();
                
                nts.uk.deferred.repeat(conf => conf
                .task(() => {
                    return nts.uk.request.asyncTask.getInfo(self.taskId()).done(function(res: any) {
                        // update state on screen
                        if (res.running || res.succeeded || res.cancelled) {
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
                        // finish task
                        if (res.succeeded || res.failed || res.cancelled) {
                            self.isDone(true);
                            self.status(nts.uk.resource.getText("KSU006_217"));
                            
                            // end count time
                            $('.countdown').stopCount();
                            if (res.error) {
                                self.showMessageError(res.error);
                            }
                            if (res.succeeded) {
                                $('#closeDialog').focus();
                            }
                        }
                    });
                }).while(infor => {
                    return infor.pending || infor.running;
                }).pause(1000));
            }
            
            /**
             * downloadDetailError
             */
            public downloadDetailError() {
                let self = this;
                nts.uk.ui.block.grayout();
                service.downloadDetailError(self.executeId()).done(function() {
                }).fail(function(res: any) {
                    self.showMessageError(res);
                }).always(function() {
                    nts.uk.ui.block.clear();
                });
            }
            
            /**
             * stopImporting
             */
            public stopImporting() {
                let self = this;
                if (nts.uk.text.isNullOrEmpty(self.taskId())) {
                    return;
                }
                // interrupt process import then close dialog
                nts.uk.request.asyncTask.requestToCancel(self.taskId());
            }
            
            /**
             * closeDialog
             */
            public closeDialog() {
                nts.uk.ui.windows.close();
            }
            
            /**
             * loadDetailError
             */
            private loadDetailError(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                service.findErrors(self.executeId()).done(function(res: Array<ErrorModel>) {
                    if (!res) {
                        return;
                    }
                    self.dataError(res);
                    if (res.length > 10) {
                        self.isGreaterThanTenError(true);
                        self.dataError(res.slice(0, 10));
                    }
                    for (let i = 0; i < self.dataError().length; i++) {
                        self.dataError()[i].order = i;
                    }
                    dfd.resolve();
                }).fail((res: any) => {
                    self.showMessageError(res);
                });
                return dfd.promise();
            }
            
            /**
             * showMessageError
             */
            private showMessageError(res: any) {
                if (res.businessException) {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
                }
            }
        }
    }
}
