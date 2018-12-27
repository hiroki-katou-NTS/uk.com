module nts.uk.at.view.kmk013.n {
    
    import blockUI = nts.uk.ui.block;
    
    export module viewmodel {
        export class ScreenModel {
            start: KnockoutObservable<number>;
            end: KnockoutObservable<number>;
            isCreated: KnockoutObservable<boolean>;
            timeEditor: any;
            data = {};
            saveEnabled: KnockoutObservable<boolean>;
            constructor() {
                let self = this;
                
                self.start = ko.observable(0);
                self.end = ko.observable(60);
                
                self.isCreated = ko.observable(false);
                
                self.timeEditor = {
                    value: ko.observable(60),
                    option: ko.mapping.fromJS(new nts.uk.ui.option.TimeEditorOption({
                        inputFormat: 'time'
                    }))
                }
                self.saveEnabled = ko.observable(true);
                
                self.start.subscribe(function(value) {
                    $('#start').ntsError('check');
                    $('#end').ntsError('check');
                    
                    if (!$('#start').ntsError('hasError') && !$('#end').ntsError('hasError')) {
                        self.saveEnabled(true);
                    } else self.saveEnabled(false);
                });
                self.end.subscribe(function(value) {
                    $('#start').ntsError('check');
                    $('#end').ntsError('check');
                    
                    if (!$('#start').ntsError('hasError') && !$('#end').ntsError('hasError')) {
                        self.saveEnabled(true);
                    } else self.saveEnabled(false);
                });
            }
            startPage() {
                let self = this;
                let dfd = $.Deferred();
                self.initData().done(() => {
                    dfd.resolve();
                });
                return dfd.promise();
            }
            initData() : JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                service.findByCompanyId().done(arr => {
                    let dt = arr[0];
                    if(dt != null) {
                        self.start(dt.startTime);
                        self.end(dt.endTime);
                        self.isCreated(true);
                    }
                    dfd.resolve();
                });
                return dfd.promise();
            }
            saveData(): void {
                let self = this;
                $('#start').ntsError('check');
                $('#end').ntsError('check');
                blockUI.grayout();
                
                if (!$('#start').ntsError('hasError') && !$('#end').ntsError('hasError')) {
                    let data: any = self.data;
                    data.start = self.start();
                    data.end = self.end();
                    if (self.start() >= self.end()){
                        nts.uk.ui.dialog.info({ messageId: 'Msg_1022' });
                        blockUI.clear();        
                    } else if (self.isCreated()){
                        service.update(data).done(
                            () => {
                                blockUI.clear();
                                nts.uk.ui.dialog.info({ messageId: 'Msg_15' }).then(() => {
                                    $("#start").focus();
                                });
                            }
                        );
                    } else {
                        service.save(data).done(
                            () => {
                                blockUI.clear();
                                nts.uk.ui.dialog.info({ messageId: 'Msg_15' }).then(() => {
                                    $("#start").focus();
                                });
                            }
                        );
                    }
                }
            }
        }
    }
}