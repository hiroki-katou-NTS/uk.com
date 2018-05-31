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
            hasSelected: KnockoutObservable<boolean>;
            nameWorkType: KnockoutObservable<string>;
            timeHistory: KnockoutObservable<string>;
            listHistory: KnockoutObservableArray<any>;
            startTime: KnockoutObservable<moment.Moment>;
            endTime: KnockoutObservable<moment.Moment>;


            constructor() {
                let self = this;
                self.listWorkType = ko.observableArray([]);
                self.hasSelected = ko.observable(true);
                self.numberDay = ko.observable(0);

                self.selectedCode = ko.observable('');
                self.selectedCodeHistory = ko.observable('');
                self.startTime = ko.observable(moment());
                self.endTime = ko.observable(moment());
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
                    { headerText: nts.uk.resource.getText('KMK015_12'), key: 'historyId', hidden:true },
                    { headerText: nts.uk.resource.getText('KMK015_12'), key: 'time', width: 200 },
                ]);
                self.selectedCode.subscribe(code => {
                    self.listWorkType().forEach(function(item) {
                        if (item.workTypeCode == code) { self.nameWorkType(item.name); }
                    });
                });
                
                if (nts.uk.util.isNullOrEmpty(self.listHistory())){
                        self.timeHistory(null);
                }
                
                self.selectedCodeHistory.subscribe(code => {
                    self.listHistory().forEach(function(item) {
                        if (item.historyId == code) { self.timeHistory(item.time); self.numberDay(item.maxDay)}
                    });
                });
                
                //set always subscribe
                self.selectedCodeHistory.extend({ notify: 'always' });
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
                        data.forEach(function(item) {
                            self.listHistory.push({
                                historyId: item.historyId,
                                time: item.startDate + ' ~ ' + item.endDate,
                                maxDay: item.maxDay
                            });
                        });
                        
                        //set focus 
                        self.selectedCodeHistory(data[0].historyId);
                    });
                    dfd.resolve();
                }).fail(function (res) {nts.uk.ui.dialog.alert(res.message)})
                .always(() => nts.uk.ui.block.clear()); // clear block ui.



                return dfd.promise();
            }

            //open dialog 00B 
            OpenDialogB() {
                let self = this;
                let workTypeCodes = self.selectedCode();
                nts.uk.ui.windows.setShared('parentCodes', {
                    workTypeCodes: workTypeCodes,
                }, true);

                nts.uk.ui.windows.sub.modal('/view/kmk/015/b/index.xhtml').onClosed(function(): any {
                    //view all code of selected item 
                    var childData = nts.uk.ui.windows.getShared('childData');
                    if (childData) {
                        self.timeHistory(childData.timeHistory);
                        self.startTime(childData.start);
                        self.endTime(childData.end);
                    }
                })
            }

            /**
             * Submit.
             */
            public submit() {
                let self = this;
                let dfd = $.Deferred<void>();

                //Add command
                let history: SaveHistory = new SaveHistory("", new Date(self.startTime().format("YYYY/MM/DD")), new Date(self.endTime().format("YYYY/MM/DD")));
                let command: SaveVacationHistoryCommand = new SaveVacationHistoryCommand(true, self.selectedCode(), self.numberDay(), history);

                // Loading, block ui.
                nts.uk.ui.block.invisible();
                service.insertHistory(command).done(function(){
                    //clear list
                    self.listWorkType.removeAll;
                    //get ListHistory
                    service.getHistoryByWorkType(self.selectedCode()).done(data => {
                        data.forEach(function(item) {
                            //push listHistory
                            self.listHistory.push({
                                historyId: item.historyId,
                                time: item.startDate + ' ~ ' + item.endDate,
                                maxDay: item.maxDay
                            });
                            
                            //focus new history
                            if (self.startTime().isSame(moment(item.startDate))){
                                self.selectedCodeHistory(item.historyId);
                            }
                        });
                    }).fail(function (res) {nts.uk.ui.dialog.alert(res.message)});
                    dfd.resolve();
                }).fail(function (res) {nts.uk.ui.dialog.alert(res.message)});

                //clear blockUI
                nts.uk.ui.block.clear();

                return dfd.promise();
            }
        }
    }
}