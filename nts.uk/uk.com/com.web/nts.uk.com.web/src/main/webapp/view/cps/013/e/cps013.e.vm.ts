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
            numberEmpChecked: KnockoutObservable<number> = ko.observable(0);
            countEmp: KnockoutObservable<number> = ko.observable(0);
            isComplete: KnockoutObservable<boolean> = ko.observable(false);
            statusCheck: KnockoutObservable<string> = ko.observable("");
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
            dataShare: KnockoutObservable<IDataShare>= ko.observableArray();

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
                    self.dataShare(dataShare);
                    //method execute
                    service.executeCheck(dataShare).done(res => {
                        self.taskId(res.id);
                        nts.uk.deferred.repeat(conf => conf
                            .task(() => {
                                return nts.uk.request.asyncTask.getInfo(self.taskId()).done(info => {
                                    self.startTime(self.getAsyncData(info.taskDatas, "startTime").valueAsString);
                                    self.numberEmpChecked(self.getAsyncData(info.taskDatas, "numberEmpChecked").valueAsNumber);
                                    self.countEmp(self.getAsyncData(info.taskDatas, "countEmp").valueAsNumber);
                                    self.statusCheck(self.getAsyncData(info.taskDatas, "statusCheck").valueAsString);

                                    if (!info.pending && !info.running) {
                                        self.isComplete(true);

                                        // End count time
                                        self.elapseTime.end();

                                        // Get EndTime from server, fallback to client
                                        self.endTime(self.getAsyncData(info.taskDatas, "endTime").valueAsString);

                                        self.bindingDataToGrid(info.taskDatas);
                                        console.log("lít bug" + info.taskDatas);
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
            
            exportCsv(): void {
                var self = this;
                let info = self.errorMessageInfo();
                var listError = []; 
                _.forEach(info, function(row) {
                    let data = {
                        employeeCode: row.employeeCode,
                        employeeName: row.bussinessName,
                        checkAtr: row.clsCategoryCheck,
                        categoryName: row.categoryName,
                        contentError: row.error
                    };
                    listError.push(data);
                });
                
                nts.uk.request.exportFile('com', 'person/consistency/check/report/print/error', listError)
                .done(data => {})
                .fail((mes) => {});
            }
            
            RecheckTheSameConditions() : void {
               var self = this;
               self.errorMessageInfo([]);
               let conditions = self.dataShare();
               self.elapseTime.start();
               self.startTime('');
               self.numberEmpChecked(0);
               self.countEmp(0);
               self.statusCheck('');
               self.endTime('');
               self.start(conditions);
            }
            
            cancelTask(): void {
                var self = this;
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
            
                
           private bindingDataToGrid(data: Array<any>): void {
                var self = this;

                let  data_employeeId = [],
                     data_categoryId = [],
                     data_employeeCode = [],
                     data_bussinessName = [],
                     data_clsCategoryCheck = [],
                     data_categoryName = [],
                     data_error = [],
                     errs = [];

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

                /*let sorted_employeeId = _.sortBy(data_employeeId, function(t) { return parseInt(t.key.replace("employeeId", "")) });
                let sorted_categoryId = _.sortBy(data_categoryId, function(t) { return parseInt(t.key.replace("categoryId", "")) });
                let sorted_employeeCode = _.sortBy(data_employeeCode, function(t) { return parseInt(t.key.replace("employeeCode", "")) });
                let sorted_bussinessName = _.sortBy(data_bussinessName, function(t) { return parseInt(t.key.replace("bussinessName", "")) });
                let sorted_clsCategoryCheck = _.sortBy(data_clsCategoryCheck, function(t) { return parseInt(t.key.replace("clsCategoryChec", "")) });
                let sorted_categoryName = _.sortBy(data_categoryName, function(t) { return parseInt(t.key.replace("categoryNam", "")) });
                let sorted_error = _.sortBy(data_error, function(t) { return parseInt(t.key.replace("error", "")) });
                let errs = []; */
               
                for (let i = 0; i < data_employeeId.length; i++) {

                    let tagKey = data_employeeId[i].key.substring(10, 15);
                    let empId = data_employeeId[i].valueAsString;
                    let ctgId = (_.filter(data_categoryId, function(o) { return o.key.substring(10, 15) == tagKey; }))[0].valueAsString;
                    let employeeCode = (_.filter(data_employeeCode, function(o) { return o.key.substring(12, 17) == tagKey; }))[0].valueAsString;
                    let bussinessName = (_.filter(data_bussinessName, function(o) { return o.key.substring(13, 18) == tagKey; }))[0].valueAsString;
                    let clsCategoryCheck = (_.filter(data_clsCategoryCheck, function(o) { return o.key.substring(16, 21) == tagKey; }))[0].valueAsString;
                    let categoryName = (_.filter(data_categoryName, function(o) { return o.key.substring(12, 17) == tagKey; }))[0].valueAsString;
                    let error = (_.filter(data_error, function(o) { return o.key.substring(5, 10) == tagKey; }))[0].valueAsString;
                    
                    
                    var errorInfo = {
                        employeeId: empId,
                        categoryId: ctgId,
                        employeeCode: employeeCode,
                        bussinessName: bussinessName,
                        clsCategoryCheck: clsCategoryCheck,
                        categoryName: categoryName,
                        error: error
                    };
                    
                    errs.push(new PersonInfoErrMessageLog(errorInfo));
                }
               
               // order 
               self.errorMessageInfo(_.sortBy(errs, [function(o) { return o.employeeCode; }]));

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
    export interface EmployeInfoErrorDataSourceDto {
        employeeCode: string;
        employeeName: string;
        checkAtr: string;
        categoryName: string;
        contentError: string;
    }
    
    export class EmployeInfoErrorDataSource {
        employeeCode: string;
        employeeName: string;
        checkAtr: string;
        categoryName: string;
        contentError: string;
        constructor(data: EmployeInfoErrorDataSourceDto) {
            this.employeeCode = data.employeeCode;
            this.employeeName = data.employeeName;
            this.checkAtr = data.checkAtr;
            this.categoryName = data.categoryName;
            this.contentError = data.contentError;
        }
    }

    function makeIcon(value, row) {
        if (value == '1')
            return '<img style="margin-left: 15px; width: 20px; height: 20px;" />';
        
        return '<div>' + '<div class="limit-custom">' + value + '</div>' + '<div style = "display: inline-block; position: relative;">' + '<button tabindex = "0" class="open-dialog-i" onclick="jumtoCPS001A(\'' + row.employeeId + '\', \'' + row.categoryId + '\')">' + nts.uk.resource.getText("CPS013_31") + '</button>' + '</div>' + '</div>';
    }
}

function jumtoCPS001A(employeeId: string, categoryId: string) {
    nts.uk.request.jumpToNewWindow('com', '/view/cps/001/a/index.xhtml', { employeeId, categoryId });
}

 