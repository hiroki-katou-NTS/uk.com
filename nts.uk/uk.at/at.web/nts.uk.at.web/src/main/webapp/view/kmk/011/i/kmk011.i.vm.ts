module nts.uk.at.view.kmk011.i {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import blockUI = nts.uk.ui.block;
    import DivergenceTimeErrAlarmMsg = nts.uk.at.view.kmk011.i.model.DivergenceTimeErrAlarmMsg;

    export module viewmodel {
        export class ScreenModel {
            columns: KnockoutObservable<any>;
            dataSource: KnockoutObservableArray<DivergenceTimeErrAlarmMsg>;
            currentCode: KnockoutObservable<number>;
            //            alarmMessage: KnockoutObservable<string>;
            //            errorMessage: KnockoutObservable<string>;
            multilineeditorErr: any;
            multilineeditorAlarm: any;
            itemDivergenceTime: KnockoutObservable<DivergenceTimeErrAlarmMsg>;

            constructor() {
                let self = this;
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KMK011_4'), key: 'divergenceTimeNo', width: 60 },
                    { headerText: nts.uk.resource.getText('KMK011_5'), key: 'divergenceTimeName', formatter: _.escape, width: 200 }
                ]);
                self.dataSource = ko.observableArray([]);
                self.currentCode = ko.observable(1);

                self.multilineeditorErr = {
                    errorMessage: ko.observable(''),
                    option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                        resizeable: false,
                        width: "350px",
                        textalign: "left"
                    })),
                    required: ko.observable(true)
                };


                self.multilineeditorAlarm = {
                    alarmMessage: ko.observable(''),
                    option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                        resizeable: false,
                        width: "350px",
                        textalign: "left"
                    })),
                    required: ko.observable(true)
                };

                self.itemDivergenceTime = ko.observable(null);

                //subscribe currentCode
                self.currentCode.subscribe(function(codeChanged) {
                    self.multilineeditorErr.errorMessage('');
                    self.multilineeditorAlarm.alarmMessage('');
                    self.findByDivTimeNo(self.currentCode()).done((itemDivTime: DivergenceTimeErrAlarmMsg) => {
                        self.itemDivergenceTime(itemDivTime);
                        self.multilineeditorErr.errorMessage(itemDivTime.errorMessage);
                        self.multilineeditorAlarm.alarmMessage(itemDivTime.alarmMessage);
                    });
                });
            }

            public start_page(): JQueryPromise<any> {
                let self = this;
                var dfd = $.Deferred<any>();


                service.getAllDivTime().done(function(listDivTime: Array<DivergenceTimeErrAlarmMsg>) {
                    blockUI.clear();
                    if (listDivTime === undefined || listDivTime.length == 0) {
                        self.dataSource();
                    } else {
                        console.log(listDivTime);
                        self.dataSource(listDivTime);
                        let rdivTimeFirst = _.first(listDivTime);
                        self.currentCode(rdivTimeFirst.divergenceTimeNo);
                        self.findByDivTimeNo(self.currentCode()).done((itemDivTime: DivergenceTimeErrAlarmMsg) => {
                            self.itemDivergenceTime(itemDivTime);
                            self.multilineeditorErr.errorMessage(itemDivTime.errorMessage);
                            self.multilineeditorAlarm.alarmMessage(itemDivTime.alarmMessage);
                        });

                    }
                    dfd.resolve();
                })
                return dfd.promise();
            }

            private findByDivTimeNo(divergenceTimeNo: number): JQueryPromise<any> {
                let self = this;
                var dfd = $.Deferred<any>();

                service.findByDivergenceTimeNo(divergenceTimeNo).done(function(itemDivergenceTime: DivergenceTimeErrAlarmMsg) {
                    dfd.resolve(itemDivergenceTime);
                })
                return dfd.promise();
            }

            private closeDialog() {
                nts.uk.ui.windows.close();
            }

            private registrationErrMsg(): JQueryPromise<any> {
                let self = this;
                var dfd = $.Deferred<any>();

                var divergenceTimeNo = self.currentCode();
                var alarmMessage = self.multilineeditorAlarm.alarmMessage();
                var errorMessage = self.multilineeditorErr.errorMessage();

                if (nts.uk.text.isNullOrEmpty(alarmMessage) && nts.uk.text.isNullOrEmpty(errorMessage)) {
                    nts.uk.ui.dialog.info({ messageId: "Msg_1056" }).then(function() {
                        dfd.resolve();
                    });
                } else {
                    var data = new DivergenceTimeErrAlarmMsg(divergenceTimeNo, "", alarmMessage, errorMessage);
                    service.save(data).done(() => {
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                            dfd.resolve();
                            nts.uk.ui.windows.close();
                        });
                    });
                }

                return dfd.promise();
            }
        }
    }
}
