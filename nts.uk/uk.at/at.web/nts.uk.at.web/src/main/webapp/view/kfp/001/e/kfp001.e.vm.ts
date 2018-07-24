module nts.uk.at.view.kfp001.e {
    import getText = nts.uk.resource.getText;
    import kibanTimer = nts.uk.ui.sharedvm.KibanTimer;
    export module viewmodel {
        export class ScreenModel {
            aggrFrameCode: KnockoutObservable<string>;
            optionalAggrName: KnockoutObservable<string>;
            startDate: KnockoutObservable<string>;
            endDate: KnockoutObservable<string>;
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
            logId: KnockoutObservable<string> = ko.observable("");
            columns: KnockoutObservableArray<any>;
            peopleCount: KnockoutObservable<string> = ko.observable('0');
            presenceOfError: KnockoutObservable<string> = ko.observable('');
            executionStatus: KnockoutObservable<string> = ko.observable('');

            //
            errorMessageInfo: KnockoutObservableArray<PersonInfoErrMessageLog> = ko.observableArray([]);
            currentCode: KnockoutObservable<any> = ko.observable();

            //
            startDateTime: KnockoutObservable<string>;
            endDateTime: KnockoutObservable<string>;

            //enable enableCancelTask
            enableCancelTask: KnockoutObservable<boolean> = ko.observable(true);
            dataFromD: KnockoutObservable<string>;

            constructor() {
                var self = this;
                self.elapseTime.start();
                self.aggrFrameCode = ko.observable('');
                self.optionalAggrName = ko.observable('');
                self.startDate = ko.observable('');
                self.endDate = ko.observable('');
                self.dataFromD = ko.observable('');
                self.mode = ko.observable(false);

                self.columns = ko.observableArray([
                    { headerText: getText('KFP001_39'), key: 'no', width: 50 },
                    { headerText: getText('KFP001_40'), key: 'personCode', width: 70 },
                    { headerText: getText('KFP001_41'), key: 'personName', width: 210 },
                    { headerText: getText('KFP001_42'), key: 'disposalDay', width: 130 },
                    { headerText: getText('KFP001_43'), key: 'messageError', width: 180 },
                    { headerText: '', key: 'GUID', width: 1, hirren: true },
                ]);

            }
            start(dataD: any) {
                var self = this;
                let dataE = nts.uk.ui.windows.getShared("KFP001_DATAE");
                let dataExc = nts.uk.ui.windows.getShared("KFP001_DATA_EXC");
                $(".closebutton").focus();
                //system date
                if (dataD !== undefined) {
                    //method execute
                    self.dataFromD(dataD.anyPeriodAggrLogId);
                    service.executeAggr(dataD.anyPeriodAggrLogId).done(res => {
                        self.startDate(moment.utc(dataE.aggrPeriodCommand.startDate).format("YYYY/MM/DD"));
                        self.endDate(moment.utc(dataE.aggrPeriodCommand.endDate).format("YYYY/MM/DD"));
                        self.executeId(dataD.anyPeriodAggrLogId);
                        self.logId(dataE.targetCommand.executionEmpId)
                        self.taskId(res.id);
                        self.aggrFrameCode(dataE.aggrPeriodCommand.aggrFrameCode);
                        self.optionalAggrName(dataE.aggrPeriodCommand.optionalAggrName);

                        self.startTime(moment.utc(dataD.startDateTime).format("YYYY/MM/DD HH:mm:ss"));
                        self.peopleCount(nts.uk.resource.getText("KFP001_23", [dataE.aggrPeriodCommand.peopleNo]));
                        nts.uk.deferred.repeat(conf => conf
                            .task(() => {
                                return nts.uk.request.asyncTask.getInfo(self.taskId()).done(info => {
                                    //self.enableCancelTask(true);
                                    // DailyCreate
                                    self.aggCreateCount(self.getAsyncData(info.taskDatas, "aggCreateCount").valueAsNumber);
                                    self.aggCreateTotal(self.getAsyncData(info.taskDatas, "aggCreateTotal").valueAsNumber);
                                    self.aggCreateStatus(self.getAsyncData(info.taskDatas, "aggCreateStatus").valueAsString);

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
                                        self.enableCancelTask(false);
                                    }

                                    //self.enableCancelTask(false);
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

                service.getErrorInfos(self.executeId()).done((result) => {
                    let errs = [];
                    _.forEach(result, function(sRes) {
                        errs = [];
                        var errorMess = {
                            personCode: sRes.employeeCode,
                            personName: sRes.employeeName,
                            disposalDay: sRes.procDate,
                            messageError: sRes.errorMessage
                        };
                        for (let i = 0; i < result.length; i++) {
                            errorMess["no"] = i + 1;
                            errs.push(new PersonInfoErrMessageLog(errorMess));
                        }

                    });
                    self.errorMessageInfo(errs);
                })
                service.getErrorMessageInfo(self.logId()).done((res) => {

                })
            }

            private getAsyncData(data: Array<any>, key: string): any {
                var result = _.find(data, (item) => {
                    return item.key == key;
                });
                return result || { valueAsString: "", valueAsNumber: 0, valueAsBoolean: false };
            }



            cancelTask(): void {
                var self = this;
                service.stopExecute(self.dataFromD());
                self.enableCancelTask(false);
                nts.uk.request.asyncTask.requestToCancel(self.taskId());
                // End count time
                self.elapseTime.end();
                //nts.uk.ui.windows.close();
            }

            closeDialog(): void {
                nts.uk.ui.windows.close();
            }


        }
    }
    export interface PersonInfoErrMessageLogDto {
        no: number;
        personCode: string;
        personName: string;
        disposalDay: string;
        messageError: number;
    }
    export class PersonInfoErrMessageLog {
        GUID: string;
        no: number;
        personCode: string;
        personName: string;
        disposalDay: string;
        messageError: number;
        constructor(data: PersonInfoErrMessageLogDto) {
            this.no = data.no;
            this.GUID = nts.uk.util.randomId();
            this.personCode = data.personCode;
            this.personName = data.personName;
            this.disposalDay = data.disposalDay;
            this.messageError = data.messageError;
        }
    }
    export enum ExecutionStatus {
        // 0:完了
        Done = 0,
        // 1:完了（エラーあり）
        DoneWitdError = 1,
        // 2:中断終了
        EndOfInterruption = 2,
        // 3:処理中 
        Processing = 3,
        // 4:中断開始
        StartOfInterruption = 4,
        // 5:実行中止
        StopExecution = 5
    }
    export enum PresenceOfError {

        // 0:エラーあり
        Error = 0,
        // 1:エラーなし
        NoError = 1

    }
}
