module nts.uk.at.view.kmk013.n {
    export module viewmodel {
        export class ScreenModel {
            start: KnockoutObservable<number>;
            end: KnockoutObservable<number>;
            isCreated: KnockoutObservable<boolean>;
            data = {};
            constructor() {
                let self = this;
                
                self.start = ko.observable(0);
                self.end = ko.observable(60);
                
                self.isCreated = ko.observable(false);
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
                
                if (!$('#start').ntsError('hasError') && !$('#end').ntsError('hasError')) {
                    let data = self.data;
                    data.start = self.start();
                    data.end = self.end();
                    if (self.start() >= self.end()){
                        nts.uk.ui.dialog.info({ messageId: 'Msg_1022' });
                    } else if (self.isCreated()){
                        service.update(data).done(
                            () => {
                                nts.uk.ui.dialog.info({ messageId: 'Msg_15' });
                            }
                        );
                    } else {
                        service.save(data).done(
                            () => {
                                nts.uk.ui.dialog.info({ messageId: 'Msg_15' });
                            }
                        );
                    }
                }
            }
            saveEnabled = ko.computed(() => {
                let self = this;
                $('#start').ntsError('check');
                $('#end').ntsError('check');
                
                if (!$('#start').ntsError('hasError') && !$('#end').ntsError('hasError')) {
                    return true;
                } else return false;
            });
        }
    }
}