module nts.uk.at.view.kmk013.n {
    export module viewmodel {
        export class ScreenModel {
            enable: KnockoutObservable<boolean>;
            readonly: KnockoutObservable<boolean>;
            timeOfDay: KnockoutObservable<number>;
            start: KnockoutObservable<number>;
            end: KnockoutObservable<number>;
            isCreated: KnockoutObservable<boolean>;
            data = {};
            constructor() {
                var self = this;
                self.enable = ko.observable(true);
                self.readonly = ko.observable(false);
                
                self.start = ko.observable(0);
                self.end = ko.observable(60);
                
                self.isCreated = ko.observable(false);
            }
            startPage() {
                var self = this;
                var dfd = $.Deferred();
                self.initData().done(() => {
                    dfd.resolve();
                });
                return dfd.promise();
            }
            initData() : JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                service.findByCompanyId().done(arr => {
                    let data = arr[0];
                    if(data != null) {
                        self.start(data.start);
                        self.end(data.end);
                        self.isCreated(true);
                    }
                    dfd.resolve();
                });
                return dfd.promise();
            }
            saveData(): void {
                let self = this;
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
    }
}