module nts.uk.at.view.kdw001.e.viewmodel {
    import getText = nts.uk.resource.getText;
    import shareModel = nts.uk.at.view.kdw001.share.model;
    import kibanTimer = nts.uk.ui.sharedvm.KibanTimer;

    export class ScreenModel {
        
        numberEmployee: KnockoutObservable<number> = ko.observable(0);
        
        // Time data
        isComplete: KnockoutObservable<boolean> = ko.observable(false);
        taskId: KnockoutObservable<string> = ko.observable("");
        startTime: KnockoutObservable<string> = ko.observable("");
        endTime: KnockoutObservable<string> = ko.observable("");
        elapseTime: kibanTimer = new kibanTimer('elapseTime');
        empCalAndSumExecLogID: KnockoutObservable<string> = ko.observable("");

        // dailyCreate data
        dailyCreateCount: KnockoutObservable<number> = ko.observable(0);
        dailyCreateTotal: KnockoutObservable<number> = ko.observable(0);
        dailyCreateStatus: KnockoutObservable<string> = ko.observable("");
        dailyCreateHasError: KnockoutObservable<string> = ko.observable("");
        
        // daily calculation
        dailyCalculateCount : KnockoutObservable<number> = ko.observable(0);
        dailyCalculateStatus : KnockoutObservable<string> = ko.observable("");
        dailyCalculateHasError : KnockoutObservable<string> = ko.observable("");
        
        // monthly aggregation data
        monthlyAggregateCount: KnockoutObservable<number> = ko.observable(0);
        monthlyAggregateStatus: KnockoutObservable<string> = ko.observable("");
        monthlyAggregateHasError: KnockoutObservable<string> = ko.observable("");

        // Period Date
        startPeriod: KnockoutObservable<string> = ko.observable("");
        endPeriod: KnockoutObservable<string> = ko.observable("");

        // Combo box 
        executionContents: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedExeContent: KnockoutObservable<any> = ko.observable(null);
        contents: any;

        // GridList
        errorMessageInfo: KnockoutObservableArray<shareModel.PersonInfoErrMessageLog> = ko.observableArray([]);
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any> = ko.observable();

        //enable enableCancelTask
        enableCancelTask: KnockoutObservable<boolean> = ko.observable(true);

        //disappear
        visibleDailiCreate: KnockoutObservable<boolean> = ko.observable(false);
        visibleDailiCalculation: KnockoutObservable<boolean> = ko.observable(false);
        visibleApproval: KnockoutObservable<boolean> = ko.observable(false);
        visibleMonthly: KnockoutObservable<boolean> = ko.observable(false);

        constructor() {
            var self = this;
            self.elapseTime.start();
            
            self.numberEmployee(0);

            self.columns = ko.observableArray([
                { headerText: getText('KDW001_33'), key: 'personCode', width: 110 },
                { headerText: getText('KDW001_35'), key: 'personName', width: 150 },
                { headerText: getText('KDW001_36'), key: 'disposalDay', width: 150 },
                { headerText: getText('KDW001_37'), key: 'messageError', width: 290 },
                { headerText: '', key: 'GUID', width: 1, hirren: true },
            ]);

            self.selectedExeContent.subscribe((value) => {
                self.getLogData();
            });
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            var params: shareModel.executionProcessingCommand = nts.uk.ui.windows.getShared("KDWL001E");
            self.startPeriod(params.periodStartDate);
            self.endPeriod(params.periodEndDate);
            self.numberEmployee(params.lstEmployeeID.length);

            service.insertData(params).done((res: shareModel.AddEmpCalSumAndTargetCommandResult) => {
                self.empCalAndSumExecLogID(res.empCalAndSumExecLogID);
                if (params.dailyCreation == false) {
                    _.remove(res.enumComboBox, function(n) {
                        return n.value == 0;
                    });
                    self.visibleDailiCreate(true);
                }
                if (params.dailyCalClass == false) {
                    _.remove(res.enumComboBox, function(n) {
                        return n.value == 1;
                    });
                    self.visibleDailiCalculation(true);
                }
                if (params.refApprovalresult == false) {
                    _.remove(res.enumComboBox, function(n) {
                        return n.value == 2;
                    });
                    self.visibleApproval(true);
                }
                if (params.monthlyAggregation == false) {
                    _.remove(res.enumComboBox, function(n) {
                        return n.value == 3;
                    });
                    self.visibleMonthly(true);
                }

                self.contents = res.enumComboBox;
                //self.executionContents(res.enumComboBox);
                self.startTime(moment.utc(res.startTime).format("YYYY/MM/DD HH:mm:ss"));
                self.startAsyncTask();
                dfd.resolve();
            });

            return dfd.promise();
        }

        exportLog(): void {
            var self = this;
            if (self.errorMessageInfo().length > 0)
                service.saveAsCsv(self.errorMessageInfo());
        }

        cancelTask(): void {
            var self = this;
            nts.uk.request.asyncTask.requestToCancel(self.taskId());
            self.enableCancelTask(false);
            self.elapseTime.end();
        }

        closeDialog(): void {
            nts.uk.ui.windows.close();
        }

        private startAsyncTask(): void {
            var self = this;
            var data: shareModel.CheckProcessCommand = {
                empCalAndSumExecLogID: self.empCalAndSumExecLogID(),
                periodStartDate: self.startPeriod(),
                periodEndDate: self.endPeriod()
            };
            service.checkTask(data).done(res => {
                self.taskId(res.id);
                self.repeatCheckAsyncResult();
            });
        }

        private repeatCheckAsyncResult(): void {
            var self = this;
            
                        self.enableCancelTask(true);
            nts.uk.deferred.repeat(conf => conf
                .task(() => {
                    return nts.uk.request.asyncTask.getInfo(self.taskId()).done(info => {
                        // DailyCreate
                        self.dailyCreateCount(self.getAsyncData(info.taskDatas, "dailyCreateCount").valueAsNumber);
                        self.dailyCreateTotal(self.getAsyncData(info.taskDatas, "dailyCreateTotal").valueAsNumber);
                        
                        // daily calculation
                        self.dailyCalculateCount(self.getAsyncData(info.taskDatas, "dailyCalculateCount").valueAsNumber);
                        
                        // monthly aggregation 
                        self.monthlyAggregateCount(self.getAsyncData(info.taskDatas, "monthlyAggregateCount").valueAsNumber);                        

                        if (!info.pending && !info.running) {
                            self.isComplete(true);
                            self.executionContents(self.contents);
                            self.selectedExeContent(self.executionContents().length > 0 ? self.executionContents()[0].value : null);

                            // End count time
                            self.elapseTime.end();
                            
                            // Get EndTime from server, fallback to client
                            self.endTime(self.getAsyncData(info.taskDatas, "endTime").valueAsString);
//                            if (nts.uk.text.isNullOrEmpty(endTime))
//                                endTime = moment.utc().add(9,"h").format("YYYY/MM/DD HH:mm:ss")
//                            self.endTime(endTime);

                            // DailyCreate
                            self.dailyCreateStatus(self.getAsyncData(info.taskDatas, "dailyCreateStatus").valueAsString);
                            self.dailyCreateHasError(self.getAsyncData(info.taskDatas, "dailyCreateHasError").valueAsString);
                            
                            // daily calculation
                            self.dailyCalculateStatus(self.getAsyncData(info.taskDatas, "dailyCalculateStatus").valueAsString);
                            self.dailyCalculateHasError(self.getAsyncData(info.taskDatas, "dailyCalculateHasError").valueAsString);
                            
                            // monthly aggregation
                            self.monthlyAggregateHasError(self.getAsyncData(info.taskDatas, "monthlyAggregateHasError").valueAsString);
                            self.monthlyAggregateStatus(self.getAsyncData(info.taskDatas, "monthlyAggregateStatus").valueAsString);
                            
                            // Get Log data
                            self.getLogData();
                            
                            self.enableCancelTask(false);
                        }
                    });
                })
                .while(info => info.pending || info.running)
                .pause(1000)
            );
        }

        private getAsyncData(data: Array<any>, key: string): any {
            var result = _.find(data, (item) => {
                return item.key == key;
            });
            return result || { valueAsString: "", valueAsNumber: 0, valueAsBoolean: false };
        }

        private getLogData(): void {
            var self = this;
            var params = {
                empCalAndSumExecLogID: self.empCalAndSumExecLogID(),
                executionContent: self.selectedExeContent()
            };
            service.getErrorMessageInfo(params).done((res) => {
                self.errorMessageInfo(res);
            });
        }

    }

    class Gridlist {
        empCD: string;
        code: string;
        disposalDay: string;
        errContents: string;
        constructor(empCD: string, code: string, disposalDay: string, errContents: string) {
            this.empCD = empCD;
            this.code = code;
            this.disposalDay = disposalDay;
            this.errContents = errContents;
        }
    }

}