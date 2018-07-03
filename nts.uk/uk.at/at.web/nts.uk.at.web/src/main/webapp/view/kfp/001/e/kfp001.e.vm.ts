module nts.uk.at.view.kfp001.e {
    import getText = nts.uk.resource.getText;
    import kibanTimer = nts.uk.ui.sharedvm.KibanTimer;
    export module viewmodel {
        export class ScreenModel {
            aggrFrameCode: KnockoutObservable<string>;
            optionalAggrName: KnockoutObservable<string>;
            startDate: KnockoutObservable<string>;
            endDate: KnockoutObservable<string>;
            peopleNo: KnockoutObservable<number>;
            mode: KnockoutObservable<boolean>;
            startTime: KnockoutObservable<string> = ko.observable("");
            endTime: KnockoutObservable<string> = ko.observable("");
            elapseTime: kibanTimer = new kibanTimer('elapseTime');
            taskId: KnockoutObservable<string> = ko.observable("");
            executeId: KnockoutObservable<string> = ko.observable("");
            aggCreateCount: KnockoutObservable<number> = ko.observable(0);
            aggCreateTotal: KnockoutObservable<number> = ko.observable(0);
            isComplete: KnockoutObservable<boolean> = ko.observable(false);
            aggCreateStatus: KnockoutObservable<string> = ko.observable("");
            aggCreateHasError: KnockoutObservable<string> = ko.observable("");

            //
            startDateTime: KnockoutObservable<string>;
            endDateTime: KnockoutObservable<string>;

            //enable enableCancelTask
            enableCancelTask: KnockoutObservable<boolean> = ko.observable(true);

            constructor() {
                var self = this;
                self.elapseTime.start();
                self.aggrFrameCode = ko.observable('');
                self.optionalAggrName = ko.observable('');
                self.startDate = ko.observable('');
                self.endDate = ko.observable('');
                self.peopleNo = ko.observable(0);
                self.mode = ko.observable(false);

            }
            start(dataD: any) {
                var self = this;
                let dataE = nts.uk.ui.windows.getShared("KFP001_DATAE");

                //system date
                if (dataD !== undefined) {
                    //method execute
                    service.executeAggr(dataD.anyPeriodAggrLogId).done(res => {
                        self.startDate(moment.utc(dataE.aggrPeriodCommand.startDate).format("YYYY/MM/DD"));
                        self.endDate(moment.utc(dataE.aggrPeriodCommand.endDate).format("YYYY/MM/DD"));
                        self.peopleNo(dataE.aggrPeriodCommand.peopleNo);
                        self.executeId(dataD.anyPeriodAggrLogId);
                        self.taskId(res.id);
                        self.startTime(moment.utc(dataD.startDateTime).format("YYYY/MM/DD HH:mm:ss"));
                        nts.uk.deferred.repeat(conf => conf
                            .task(() => {
                                return nts.uk.request.asyncTask.getInfo(self.taskId()).done(info => {
                                    // DailyCreate
                                    self.aggCreateCount(self.getAsyncData(info.taskDatas, "aggCreateCount").valueAsNumber);
                                    self.aggCreateTotal(self.getAsyncData(info.taskDatas, "aggCreateTotal").valueAsNumber);


                                    if (!info.pending && !info.running) {
                                        self.isComplete(true);
                                        //content executing
                                        //  self.executionContents(self.contents);
                                        // self.selectedExeContent(self.executionContents().length > 0 ? self.executionContents()[0].value : null);

                                        // End count time
                                        self.elapseTime.end();

                                        // Get EndTime from server, fallback to client
                                        self.endTime(self.getAsyncData(info.taskDatas, "endTime").valueAsString);
                                        //                            if (nts.uk.text.isNullOrEmpty(endTime))
                                        //                                endTime = moment.utc().add(9,"h").format("YYYY/MM/DD HH:mm:ss")
                                        //                            self.endTime(endTime);

                                        // DailyCreate
                                        self.aggCreateStatus(self.getAsyncData(info.taskDatas, "aggCreateStatus").valueAsString);
                                        self.aggCreateHasError(self.getAsyncData(info.taskDatas, "aggCreateHasError").valueAsString);

                                        // Get Log data
                                        self.getLogData();
                                    }
                                    self.enableCancelTask(false);
                                });
                            })
                            .while(info => info.pending || info.running)
                            .pause(1000)
                        );
                    });
                }

            }

            private getLogData(): void {
                var self = this;
                var params = {
                    executeId: self.executeId()
                    //                    executionContent: self.selectedExeContent()
                };
                //                service.getErrorMessageInfo(params).done((res) => {
                //                    self.errorMessageInfo(res);
                //                });
            }

            private getAsyncData(data: Array<any>, key: string): any {
                var result = _.find(data, (item) => {
                    return item.key == key;
                });
                return result || { valueAsString: "", valueAsNumber: 0, valueAsBoolean: false };
            }

            cancelTask(): void {
                var self = this;
                nts.uk.request.asyncTask.requestToCancel(self.taskId());
                nts.uk.ui.windows.close();
            }

            closeDialog(): void {
                nts.uk.ui.windows.close();
            }


        }
    }
}
