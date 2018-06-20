module nts.uk.at.view.kmk015.a {
    import OptionalItemHeaderDto = service.model.WorkType;
    import SaveVacationHistoryCommand = service.model.SaveVacationHistoryCommand;
    import History = service.model.History;
    import SaveHistory = service.model.SaveHistory;

    export module viewmodel {

        export class ScreenModel {
            columns: any;
            columnsHistory: any;
            numberDay: KnockoutObservable<number>;
            selectedCode: KnockoutObservable<string>;
            selectedCodeHistory: KnockoutObservable<string>;
            listWorkType: KnockoutObservableArray<OptionalItemHeaderDto>;
            nameWorkType: KnockoutObservable<string>;
            timeHistory: KnockoutObservable<string>;
            listHistory: KnockoutObservableArray<any>;
            startTime: KnockoutObservable<moment.Moment>;
            endTime: KnockoutObservable<moment.Moment>;
            startforC: KnockoutObservable<moment.Moment>;
            endforC: KnockoutObservable<moment.Moment>;
            isCreated: KnockoutObservable<boolean>;
            historyId: KnockoutObservable<string>;
            isEnable: KnockoutObservable<boolean>;
            isEnableNumber: KnockoutObservable<boolean>;
            isSubmit: KnockoutObservable<boolean>;


            constructor() {
                let self = this;
                self.listWorkType = ko.observableArray([]);
                self.numberDay = ko.observable(0);
                self.isCreated = ko.observable(false);
                self.isEnable = ko.observable(true);
                self.isEnableNumber = ko.observable(true);
                self.isSubmit = ko.observable(true);

                self.selectedCode = ko.observable('');
                self.historyId = ko.observable('');
                self.selectedCodeHistory = ko.observable('');
                self.startTime = ko.observable(moment());
                self.endTime = ko.observable(moment());
                self.startforC = ko.observable(moment());
                self.endforC = ko.observable(moment());
                self.nameWorkType = ko.observable('');
                self.timeHistory = ko.observable('');
                self.listHistory = ko.observableArray([]);
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KMK015_3'), key: 'workTypeCode', width: 40 },
                    { headerText: nts.uk.resource.getText('KMK015_4'), key: 'name', width: 180 },
                    {
                        headerText: nts.uk.resource.getText('KMK015_5'), key: 'abolishAtr', width: 50,
                        formatter: used => {
                            if (used == 1) {
                                return '<div style="text-align: center;max-height: 18px;">'
                                    + '<i class="icon icon-78"></i></div>';
                            }
                            return '';
                        }
                    }
                ]);
                self.columnsHistory = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KMK015_12'), key: 'historyId', hidden: true },
                    { headerText: nts.uk.resource.getText('KMK015_12'), key: 'time', width: 270 },
                ]);
                self.selectedCode.subscribe(code => {
                    //clear Error
                    nts.uk.ui.errors.clearAll();
                    
                    self.listWorkType().forEach(function(item) {
                        if (item.workTypeCode == code) { self.nameWorkType(item.name); }
                    });
                    if (code) {
                        service.getHistoryByWorkType(code).done(data => {
                            //clear list history
                            self.listHistory.removeAll();

                            if (!nts.uk.util.isNullOrEmpty(data)){
                                //push listHistory
                                self.addList(data);
                                
                                //set focus 
                                self.selectedCodeHistory(data[0].historyId);
                            }
                            
                            //set Enable
                            if (nts.uk.util.isNullOrEmpty(self.listHistory()) 
                                || nts.uk.util.isNullOrEmpty(self.selectedCodeHistory())) {
                                this.newMode();
                            } else {
                                self.isEnable(true);
                                self.isEnableNumber(true);
                            }
                        });
                    } else {
                        self.listHistory.removeAll();
                        this.newMode();
                    }
                    
                    if (!nts.uk.util.isNullOrEmpty(self.timeHistory())){
                        self.isEnableNumber(true);
                    } else {
                        self.isEnableNumber(false);
                    }
                    
                });

                if (nts.uk.util.isNullOrEmpty(self.listHistory())) {
                    self.timeHistory(null);
                }

                self.selectedCodeHistory.subscribe(code => {
                    //clear Error
                    nts.uk.ui.errors.clearAll();
                   
                    self.listHistory().forEach(function(item) {
                        if (item.historyId == code) {
                            self.historyId(code);
                            self.timeHistory(item.time);
                            self.numberDay(item.maxDay);
                            self.startforC(item.startItem);
                            self.endforC(item.endItem);
                        }
                    });
                });
                
                //set always subscribe
                self.selectedCodeHistory.extend({ notify: 'always' });
            }
            
            /**
             * set NewMode
             */
            private newMode() {
                let self = this;
                self.numberDay(null);
                self.timeHistory(null);
                self.isEnable(false);
                self.isEnableNumber(false);
            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();

                // block ui
                nts.uk.ui.block.invisible();

                self.initialize().done(() => dfd.resolve());

                return dfd.promise();
            }

            /**
             * Initialization
             */
            public initialize(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();

                // get list worktype
                service.findListWorkType().done(res => {
                    self.listWorkType(res);

                    // Select first item
                    self.selectedCode(res[0].workTypeCode);
                    self.nameWorkType(res[0].name);

                    //get List History
                    service.getHistoryByWorkType(self.selectedCode()).done(data => {
                        //clear list history
                        self.listHistory.removeAll();
                        
                        //push listHistory
                        self.addList(data);
                        
                        if (!nts.uk.util.isNullOrEmpty(data)){
                            //set focus 
                            self.selectedCodeHistory(data[0].historyId);
                        }
                    });
                    
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert({ messageId: res.messageId }).then(function() {
                        nts.uk.request.jump("com", "view/ccg/008/a/index.xhtml");
                    });
                })
                    .always(() => nts.uk.ui.block.clear()); // clear block ui.

                return dfd.promise();
            }

            //open dialog B
            OpenDialogB() {
                let self = this;

                if (self.listHistory().length >= 19) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_976" });
                    return;
                }

                nts.uk.ui.windows.sub.modal('/view/kmk/015/b/index.xhtml').onClosed(function(): any {
                    //view all code of selected item 
                    var childData = nts.uk.ui.windows.getShared('childData');
                    if (childData) {
                        self.isCreated(childData.isCreated);
                        self.timeHistory(childData.timeHistory);
                        self.startTime(childData.start);
                        self.endTime(childData.end);
                    }
                    
                    if (!nts.uk.util.isNullOrEmpty(self.timeHistory())){
                        self.isEnableNumber(true);
                    } else {
                        self.isEnableNumber(false);
                    }
                    
                    //set numberDay
                    if (self.isCreated()){
                        self.numberDay(null);
                        
                        if (!nts.uk.util.isNullOrEmpty(self.timeHistory()) || !self.isEnableNumber()){
                            self.isSubmit(true);
                        } else {
                            self.isSubmit(false);
                        }
                    }
                })
            }


            //open dialog C
            OpenDialogC() {
                let self = this;

                if (nts.uk.util.isNullOrEmpty(self.selectedCode())) {
                    nts.uk.ui.dialog.alertError({ messageId: "Please Choose History!" });
                    return;
                }

                let workTypeCodes = self.selectedCode();
                nts.uk.ui.windows.setShared('parentCodes', {
                    workTypeCodes: workTypeCodes,
                    startTime: self.startforC(),
                    endTime: self.endforC()
                }, true);

                nts.uk.ui.windows.sub.modal('/view/kmk/015/c/index.xhtml').onClosed(function(): any {
                    //view all code of selected item 
                    var childData = nts.uk.ui.windows.getShared('childData');
                    if (childData) {
                        self.isCreated(childData.isCreated);
                        self.timeHistory(childData.timeHistory);
                        self.startforC(childData.start);
                        self.endforC(childData.end);
                    }
                })
            }

            /**
             * Submit.
             */
            public submit() {
                let self = this;
                let dfd = $.Deferred<void>();
                
                if (!self.isEnableNumber()){
                    return;
                }
                
                $("#number-1").ntsEditor("validate");
                
                if (nts.uk.ui.errors.hasError()) {
                    return;                   
                }
                
                if (nts.uk.util.isNullOrEmpty(self.timeHistory())) {
                    nts.uk.ui.dialog.alertError("Please Setup History!");
                    return;
                }
                
                let historyId = "";
                let history: SaveHistory;

                //check isNewMode
                if (!self.isCreated()) {
                    historyId = self.historyId();
                    history = new SaveHistory(historyId, new Date(self.startforC().format("YYYY/MM/DD")), new Date(self.endforC().format("YYYY/MM/DD")));
                } else {
                    history = new SaveHistory(historyId, new Date(self.startTime().format("YYYY/MM/DD")), new Date(self.endTime().format("YYYY/MM/DD")));                   
                }
                
                //Add command
                let command: SaveVacationHistoryCommand = new SaveVacationHistoryCommand(self.isCreated(), self.selectedCode(), self.numberDay(), history);

                // Loading, block ui.
                nts.uk.ui.block.invisible();
                service.insertHistory(command).done(function() {
                    //OK
                    nts.uk.ui.dialog.info({ messageId: 'Msg_15' });
                    //clear list
                    self.listHistory.removeAll();
                    //get ListHistory
                    service.getHistoryByWorkType(self.selectedCode()).done(data => {
                        //push listHistory
                        self.addList(data);

                        //focus new history
                        data.forEach(function(item) {
                            if (moment(moment(self.startTime()).format("YYYY/MM/DD")).isSame(moment(item.startDate))) {
                                self.selectedCodeHistory(item.historyId);
                            }
                        });

                        self.isCreated(false);
                        self.isEnable(true);

                        dfd.resolve();
                    }).fail(function(res) { nts.uk.ui.dialog.alertError(res) });
                }).fail(function(res) { nts.uk.ui.dialog.alertError(res) });

                //clear blockUI
                nts.uk.ui.block.clear();

                return dfd.promise();
            }

            /**
             * Remove
             */
            public removeHistory(): void {
                let self = this;
                let isLastIndex = false;
                //confirm Delete
                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(() => {
                    //get index
                    let index = _.findIndex(self.listHistory(), ['historyId', self.selectedCodeHistory()]);
                    if (self.listHistory().length - 1 == index) {
                        isLastIndex = true;
                    }
                    //add command
                    var command: any = {};
                    command.historyId = self.historyId();
                    command.workTypeCode = self.selectedCode();

                    //Remove history
                    service.removeVacationHistory(command).done(() => {
                        nts.uk.ui.dialog.info({ messageId: 'Msg_16' });

                        //clear list
                        self.listHistory.removeAll();

                        //Get listHistory
                        service.getHistoryByWorkType(self.selectedCode()).done(data => {
                            //push listHistory
                            self.addList(data);

                            //focus new history
                            if (!nts.uk.util.isNullOrEmpty(self.listHistory())) {
                                //check lastIndex
                                if (isLastIndex) {
                                    self.selectedCodeHistory(self.listHistory()[index - 1].historyId);
                                } else {
                                    self.selectedCodeHistory(self.listHistory()[index].historyId);
                                }
                            } else {
                                this.newMode();
                            }
                        }).fail(function(res) { nts.uk.ui.dialog.alertError(res) });

                    }).fail((res: any) => {
                        nts.uk.ui.dialog.bundledErrors(res);
                    });
                });
            }

            /**
             * Add listHistory
             */
            private addList(data: Array<any>): void {
                let self = this;
                data.forEach(function(item) {
                    //push listHistory
                    self.listHistory.push({
                        historyId: item.historyId,
                        time: item.startDate + ' ~ ' + item.endDate,
                        maxDay: item.maxDay,
                        startItem: item.startDate,
                        endItem: item.endDate
                    });
                });
            }
        }
    }
}