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
            wkTypeCode: KnockoutObservable<string>;
            wkTypeName: KnockoutObservable<string>;
            multilineeditorErr: any;
            multilineeditorAlarm: any;
            itemDivergenceTime: KnockoutObservable<DivergenceTimeErrAlarmMsg>;
            settingMode: KnockoutObservable<boolean>;
            constructor() {
                let self = this;
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KMK011_4'), key: 'divergenceTimeNo', width: 60 },
                    { headerText: nts.uk.resource.getText('KMK011_5'), key: 'divergenceTimeName', formatter: _.escape, width: 200 }
                ]);
                self.dataSource = ko.observableArray([]);
                self.currentCode = ko.observable(1);
                self.wkTypeCode = ko.observable("");
                self.wkTypeName = ko.observable("");
                
                let mode: number = nts.uk.ui.windows.getShared('settingMode');
                
                
                if (mode == SettingMode.WORKTYPE){
                    self.wkTypeCode =  ko.observable(nts.uk.ui.windows.getShared('wkTypeCode'));
                    self.wkTypeName =  ko.observable(nts.uk.ui.windows.getShared('wkTypeName'));
                    self.settingMode = ko.observable(true);
                } else {
                    self.settingMode = ko.observable(false);
                }

                
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
                self.currentCode.subscribe((codeChanged) => {
                    if (nts.uk.text.isNullOrEmpty(codeChanged)){
                        self.multilineeditorErr.errorMessage('');
                        self.multilineeditorAlarm.alarmMessage('');
                    } else {
                        let mode: number = nts.uk.ui.windows.getShared('settingMode');
                
                        if (mode == SettingMode.COMPANY){
                                self.findByDivTimeNo(self.currentCode()).done((itemDivTime: DivergenceTimeErrAlarmMsg) => {
                                if (nts.uk.text.isNullOrEmpty(itemDivTime)){ 
                                        
                                    } else {
                                    self.itemDivergenceTime(itemDivTime);
                                        self.multilineeditorErr.errorMessage(itemDivTime.errorMessage);
                                        self.multilineeditorAlarm.alarmMessage(itemDivTime.alarmMessage);
                                }
                                });
                        } else {
                                self.findByWorkTypeDivTimeNo(self.currentCode(), self.wkTypeCode()).done((itemDivTime: DivergenceTimeErrAlarmMsg) => {
                                    if (!nts.uk.text.isNullOrEmpty(itemDivTime)){
                                       
                                    }  else {
                                    self.itemDivergenceTime(itemDivTime);
                                        self.multilineeditorErr.errorMessage(itemDivTime.errorMessage);
                                        self.multilineeditorAlarm.alarmMessage(itemDivTime.alarmMessage);
                                }                          
                                });
                            }
                        }                                                                                   
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
                        let mode: number = nts.uk.ui.windows.getShared('settingMode');
                
                        if (mode == SettingMode.COMPANY){
                            self.findByDivTimeNo(self.currentCode()).done((itemDivTime: DivergenceTimeErrAlarmMsg) => {
                            if (nts.uk.text.isNullOrEmpty(itemDivTime)){
                                        
                                    }  else {
                                    self.itemDivergenceTime(itemDivTime);
                                        self.multilineeditorErr.errorMessage(itemDivTime.errorMessage);
                                        self.multilineeditorAlarm.alarmMessage(itemDivTime.alarmMessage);
                                }
                            });
                        } else {
                            self.findByWorkTypeDivTimeNo(self.currentCode(), self.wkTypeCode()).done((itemDivTime: DivergenceTimeErrAlarmMsg) => {
                            if (nts.uk.text.isNullOrEmpty(itemDivTime)){
                                        
                                    }  else {
                                    self.itemDivergenceTime(itemDivTime);
                                        self.multilineeditorErr.errorMessage(itemDivTime.errorMessage);
                                        self.multilineeditorAlarm.alarmMessage(itemDivTime.alarmMessage);
                                }
                            });
                        }
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
            
            private findByWorkTypeDivTimeNo(divergenceTimeNo: number, workTypeCode: string): JQueryPromise<any> {
                let self = this;
                var dfd = $.Deferred<any>();

                service.findByWorkTypeDivergenceTimeNo(divergenceTimeNo, workTypeCode).done(function(itemDivergenceTime: DivergenceTimeErrAlarmMsg) {
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
                    let mode: number = nts.uk.ui.windows.getShared('settingMode');
                
                        if (mode == SettingMode.WORKTYPE){
                            var data = new DivergenceTimeErrAlarmMsg(divergenceTimeNo, self.wkTypeCode(), "", alarmMessage, errorMessage);
                             service.saveWorkTypeDivTimeErrAlarmMsg(data).done(() => {
                               nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                               dfd.resolve();
                               nts.uk.ui.windows.close();
                                  });
                          
                            });
                        } else {
                            var data = new DivergenceTimeErrAlarmMsg(divergenceTimeNo, "", "", alarmMessage, errorMessage);
                             service.saveDivTimeErrAlarmMsg(data).done(() => {
                               nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                               dfd.resolve();
                               nts.uk.ui.windows.close();
                                  });
                             });
                        }
                    
                }

                return dfd.promise();
            }
        }
        
        export enum SettingMode {
            COMPANY = 0,
            WORKTYPE = 1
        }
    }
}
