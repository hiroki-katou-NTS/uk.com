module nts.uk.at.view.cps013.e {
    import getText = nts.uk.resource.getText;
    import kibanTimer = nts.uk.ui.sharedvm.KibanTimer;
    export module viewmodel {
        export class ScreenModel {
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

            // disable gridlist
            error: KnockoutObservable<boolean> = ko.observable(false);
            constructor() {
                var self = this;
                self.elapseTime.start();
                self.mode = ko.observable(false);
                self.isComplete = ko.observable(false);

                self.columns = ko.observableArray([
                    { headerText: getText('KFP001_40'), key: 'employeeCode', width: 150 },
                    { headerText: getText('KFP001_41'), key: 'bussinessName', width: 200 },
                    { headerText: getText('KFP001_42'), key: 'clsCategoryCheck', width: 200 },
                    { headerText: getText('KFP001_43'), key: 'categoryName', width: 200 },
                    { headerText: getText('KFP001_43'), key: 'error', width: 350, formatter: makeIcon},
                    { headerText: '', key: 'employeeId', width: 1, hirren: true },
                    { headerText: '', key: 'categoryId', width: 1, hirren: true },
                    { headerText: '', key: 'GUID', width: 1, hirren: true },
                ]);
                self.errorMessageInfo.subscribe((value)=>{
                    if(value.length){
                        self.error(true);
                        nts.uk.ui.windows.getSelf().setWidth(1170);
                        nts.uk.ui.windows.getSelf().setHeight(610);
                    }
                });
            }
            // dataShare từ màn A.
            start(dataShare: IDataShare) {
                var self = this;
                $(".closebutton").focus();
                //system date
                if (dataShare !== undefined) {
                    //method execute
                    service.executeCheck(dataShare).done(res => {
                        self.taskId(res.id);
                        self.startTime(moment.utc(dataShare.startDateTime).format("YYYY/MM/DD HH:mm:ss"));
                        self.peopleCount(nts.uk.resource.getText("KFP001_23", [dataShare.peopleCount]));
                        nts.uk.deferred.repeat(conf => conf
                            .task(() => {
                                return nts.uk.request.asyncTask.getInfo(self.taskId()).done(info => {
                                                                        self.aggCreateCount(self.getAsyncData(info.taskDatas, "aggCreateCount").valueAsNumber);
                                    self.aggCreateTotal(self.getAsyncData(info.taskDatas, "aggCreateTotal").valueAsNumber);
                                    self.aggCreateStatus(self.getAsyncData(info.taskDatas, "aggCreateStatus").valueAsString);

                                    if (!info.pending && !info.running) {
                                        self.isComplete(true);

                                        // End count time
                                        self.elapseTime.end();

                                        // Get EndTime from server, fallback to client
                                        self.endTime(self.getAsyncData(info.taskDatas, "endTime").valueAsString);

                                        // DailyCreate
                                        self.aggCreateStatus(self.getAsyncData(info.taskDatas, "aggCreateStatus").valueAsString);
                                        self.aggCreateHasError(self.getAsyncData(info.taskDatas, "aggCreateHasError").valueAsString);
                                       
                                        // check bien nay ====
                                        //self.errorMessageInfo(self.getAsyncData(info.taskDatas, "listError").listError);
                                        // Get Log data
                                        self.bindingDataToGrid(info.taskDatas);
                                        //self.enableCancelTask(false);
                                        
                                    }
                                });
                            })
                            .while(info => info.pending || info.running)
                            .pause(1000)
                        );
                    });
                }
            }
                
           private bindingDataToGrid(data: Array<any>): void {
                var self = this;

                let data_employeeId = [];
                let data_categoryId = [];
                let data_employeeCode = [];
                let data_bussinessName = [];
                let data_clsCategoryCheck = [];
                let data_categoryName = [];
                let data_error = [];

                _.forEach(data, item => {
                    if (item.key.substring(0, 5) === "error") {
                        data_error.push(item);
                    } else if (item.key.substring(0, 10) === "employeeId") {
                        data_employeeId.push(item);
                    } else if (item.key.substring(0, 10) === "categoryId") {
                        data_categoryId.push(item);
                    } else if (item.key.substring(0, 10) === "employeeCo") {
                        data_employeeCode.push(item);
                    } else if (item.key.substring(0, 10) === "bussinessN") {
                        data_bussinessName.push(item);
                    } else if (item.key.substring(0, 10) === "clsCategor") {
                        data_clsCategoryCheck.push(item);
                    } else if (item.key.substring(0, 10) === "categoryNa") {
                        data_categoryName.push(item);
                    }
                });

                let sorted_employeeId = _.sortBy(data_employeeId, function(t) { return parseInt(t.key.replace("employeeId", "")) });
                let sorted_categoryId = _.sortBy(data_categoryId, function(t) { return parseInt(t.key.replace("categoryId", "")) });
                let sorted_employeeCode = _.sortBy(data_employeeCode, function(t) { return parseInt(t.key.replace("employeeCode", "")) });
                let sorted_bussinessName = _.sortBy(data_bussinessName, function(t) { return parseInt(t.key.replace("bussinessName", "")) });
                let sorted_clsCategoryCheck = _.sortBy(data_clsCategoryCheck, function(t) { return parseInt(t.key.replace("clsCategoryChec", "")) });
                let sorted_categoryName = _.sortBy(data_categoryName, function(t) { return parseInt(t.key.replace("categoryNam", "")) });
                let sorted_error = _.sortBy(data_error, function(t) { return parseInt(t.key.replace("error", "")) });
                let errs = [];
                for (let i = 0; i < sorted_employeeId.length; i++) {

                    var errorInfo = {
                        employeeId: sorted_employeeId[i].valueAsString,
                        categoryId: sorted_categoryId[i].valueAsString,
                        employeeCode: sorted_employeeCode[i].valueAsString,
                        bussinessName: sorted_bussinessName[i].valueAsString,
                        clsCategoryCheck: sorted_clsCategoryCheck[i].valueAsString,
                        categoryName: sorted_categoryName[i].valueAsString,
                        error: sorted_error[i].valueAsString
                    };
                    
                    errs.push(new PersonInfoErrMessageLog(errorInfo));
                }
               
               self.errorMessageInfo(errs);

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
                return result || { valueAsString: "", valueAsNumer: 0, valueAsBoolean: false};
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

            stop() {
                let self = this;
                service.stopExecute(self.dataFromD());
                self.enableCancelTask(false);
                nts.uk.request.asyncTask.requestToCancel(self.taskId());
                // End count time
                self.elapseTime.end();
            }
        }
    }
    export interface PersonInfoErrMessageLogDto {
        employeeId: string;
        categoryId: string;
        employeeCode: string;
        bussinessName: string;
        clsCategoryCheck: string;
        categoryName: string;
        error: string;
    }

    export class PersonInfoErrMessageLog {
        GUID: string;
        employeeId: string;
        categoryId: string;
        employeeCode: string;
        bussinessName: string;
        clsCategoryCheck: string;
        categoryName: string;
        error: string;
        constructor(data: PersonInfoErrMessageLogDto) {
            this.GUID = nts.uk.util.randomId();
            this.employeeId = data.employeeId;
            this.categoryId = data.categoryId;
            this.employeeCode = data.employeeCode;
            this.bussinessName = data.bussinessName;
            this.clsCategoryCheck = data.clsCategoryCheck;
            this.categoryName = data.categoryName;
            this.error = data.error;
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

    interface IDataShare {
        perInfoCheck: boolean,
        masterCheck: boolean,
        scheduleMngCheck: boolean,
        dailyPerforMngCheckL: boolean,
        monthPerforMngCheck: boolean,
        payRollMngCheck: boolean,
        bonusMngCheck: boolean,
        yearlyMngCheck: boolean,
        monthCalCheck: boolean,
        peopleCount: number,
        startDateTime: Date,
    }

    function makeIcon(value, row) {
        if (value == '1')
            return '<img style="margin-left: 15px; width: 20px; height: 20px;" />';
        return '<div>' + '<div class="limit-custom">'+ value +'</div>'+ '<div style = "display: inline-block; position: relative;">'+'<button tabindex = "0" class="open-dialog-i">'+ nts.uk.resource.getText("CPS013_31")+'</button>'+'</div>'+ '</div>';
    }
}
