module nts.uk.at.view.kdw001.i {
    import modelkdw001f = nts.uk.at.view.kdw001.f.model;
    export module viewmodel {
        export class ScreenModel {
            //obj ExecutionLog
            empCalAndSumExecLogID: string;
            empCalAndSumExecLog: KnockoutObservable<modelkdw001f.EmpCalAndSumExeLog>;
            processingMonthName: KnockoutObservable<string>;
            executedMenuName: string;
            listExecutionLog: KnockoutObservableArray<modelkdw001f.ExecutionLog>;
            objectPeriod: KnockoutObservable<modelkdw001f.ObjectPeriod>;
            periodStartDate: KnockoutObservable<string>;
            periodEndDate: KnockoutObservable<string>;
            timeSpan: KnockoutObservable<string>;
            executionTime: KnockoutObservable<modelkdw001f.ExecutionTime>;
            exeStartTime: string;
            exeEndTime: string;
            //nameclosure
            nameClosure: string;
            //listSid
            listSid: Array<string>;


            processingMonth: KnockoutObservable<number>;
            //TargetPerson 
            listTargetPerson: KnockoutObservableArray<model.TargetPerson>;
            listPerson: Array<string>;
            numberPerson: KnockoutObservable<number>;
            numberPersonErr: KnockoutObservable<number>;
            //InputErrMessageInfoByID
            inputErrMessageInfoByID: KnockoutObservable<model.InputErrMessageInfoByID>;
            listErrMessageInfo: KnockoutObservableArray<model.ErrMessageInfo>;
            constructor() {
                let self = this;
                //table 
                let param = nts.uk.ui.windows.getShared("openI");
                if (param != null) {
                    self.empCalAndSumExecLogID = param.empCalAndSumExecLogID;
                    self.nameClosure = param.nameClosure;
                    self.executedMenuName = param.executedMenuName;
                }
                self.empCalAndSumExecLog = ko.observable(null);
                self.processingMonthName = ko.observable('');
                self.listExecutionLog = ko.observableArray([]);
                self.objectPeriod = ko.observable(null);
                //start - end
                self.periodStartDate = ko.observable('');
                self.periodEndDate = ko.observable('');
                self.timeSpan = ko.observable('');
                self.executionTime = ko.observable(null);
                self.exeStartTime = '';
                self.exeEndTime = '';
                self.processingMonth = ko.observable(0);

                //list sid
                self.listSid = [];

                //TargetPerson
                self.listTargetPerson = ko.observableArray([]);
                self.listPerson = [];
                self.numberPerson = ko.observable(0);
                self.numberPersonErr = ko.observable(0);
                // inputErrMessageInfoByID
                self.inputErrMessageInfoByID = ko.observable(null);
                self.listErrMessageInfo = ko.observableArray([]);
            }

            startPage(): JQueryPromise<any> {

                let self = this;
                let dfd = $.Deferred();

                let dfdGetListTargetPersonByEmpId = self.getListTargetPersonByEmpId(self.empCalAndSumExecLogID);
                let dfdGetAllErrMessageInfoByEmpID = self.getAllErrMessageInfoByEmpID(self.empCalAndSumExecLogID);
                $.when(dfdGetListTargetPersonByEmpId, dfdGetAllErrMessageInfoByEmpID)
                    .done((dfdGetListTargetPersonByEmpIdData, dfdGetAllErrMessageInfoByEmpIDData) => {
                        self.getByEmpCalAndSumExeLogId(self.empCalAndSumExecLogID);
                        dfd.resolve();
                    });
                return dfd.promise();
            }//end start page

            /**
             *function getByEmpCalAndSumExeLogId 
             */
            getByEmpCalAndSumExeLogId(empCalAndSumExeLogId: string) {
                let self = this;
                let dfd = $.Deferred();
                service.getByEmpCalAndSumExeLogId(empCalAndSumExeLogId).done(function(data: modelkdw001f.IEmpCalAndSumExeLog): any {
                    self.empCalAndSumExecLog(new modelkdw001f.EmpCalAndSumExeLog(data));
                    self.processingMonthName(self.empCalAndSumExecLog().processingMonthName);
                    self.processingMonth(self.empCalAndSumExecLog().processingMonth % 100);
                    //date
                    let sortData: Array<modelkdw001f.IExecutionLog> = _.sortBy(data.executionLogs, ['executionContent'], ['desc']);
                    self.listExecutionLog(_.map(sortData, (value) => {
                        let temp = [];
                        for (let i = 0; i < self.listErrMessageInfo().length; i++) {
                            if (self.listErrMessageInfo()[i].executionContent == value.executionContent) {
                                temp.push(self.listErrMessageInfo()[i]);
                            }
                        }
                        var numberPersonErr = _.chain(temp)
                            .groupBy("employeeID")
                            .toPairs()
                            .value();
                        value.numberPersonErr = numberPersonErr.length;
                        return new modelkdw001f.ExecutionLog(value);
                    }));
                    if (self.listExecutionLog().length > 0) {
                        self.objectPeriod(self.listExecutionLog()[0].objectPeriod);
                        self.periodStartDate(self.objectPeriod().startDate);
                        self.periodEndDate(self.objectPeriod().endDate);
                        if (self.listExecutionLog()[0].executionContentName == "月別集計" &&
                            (self.listExecutionLog()[0].executionContentName != "日別計算"
                                || self.listExecutionLog()[0].executionContentName != "承認結果反映"
                                || self.listExecutionLog()[0].executionContentName != "日別作成")) {
                            $("#period-date").hide();
                        } else {
                            $("#period-date").show();
                        }
                    }
                    dfd.resolve();
                    //self.empCalAndSumExecLog(data[0]);
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res).then(function() { nts.uk.ui.block.clear(); });
                });

                return dfd.promise();

            }//end function  getByEmpCalAndSumExeLogId

            /**
             *  function get list TargetPerson by EmpCalAndSumExeLogId
             */
            getListTargetPersonByEmpId(empCalAndSumExeLogId: string) {
                let self = this;
                let dfd = $.Deferred();
                service.getListTargetPersonByEmpId(empCalAndSumExeLogId).done(function(data) {
                    _.each(data, (value) => {
                        if (self.listSid.indexOf(value.employeeId) == -1)
                            self.listSid.push(value.employeeId);
                    });
                    self.listTargetPerson(data);
                    self.numberPerson(self.listTargetPerson().length);
                    self.getListPersonInforLog(self.listSid).done(function() {
                        dfd.resolve();
                    });

                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res).then(function() { nts.uk.ui.block.clear(); });
                });
                return dfd.promise();

            }//end function getListTargetPersonByEmpId

            /**
             * get all person info 
             */
            getListPersonInforLog(listSid: Array<string>) {
                let self = this;
                let dfd = $.Deferred<any>();
                service.getListPersonInforLog(listSid).done(function(data) {


                    _.each(self.listTargetPerson(), (value) => {
                        _.find(data, function(person) {
                            if (value.employeeId == person.employeeId) {
                                value.personCode = person.employeeCode;
                                value.personName = person.namePerson;
                            }
                        });
                    });
                    self.listTargetPerson.valueHasMutated();
                    dfd.resolve(data);
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res).then(function() { nts.uk.ui.block.clear(); });
                });
                return dfd.promise();

            }

            /**
             * getAllErrMessageInfoByEmpID
             */
            getAllErrMessageInfoByEmpID(empCalAndSumExeLogId: string) {
                let self = this;
                let dfd = $.Deferred();
                service.getAllErrMessageInfoByEmpID(empCalAndSumExeLogId).done(function(data) {
                    self.listErrMessageInfo(data);
                    dfd.resolve();
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res).then(function() { nts.uk.ui.block.clear(); });
                });
                return dfd.promise();
            }
            /**
             *open dialog G 
             */
            openDialogG(execution: modelkdw001f.ExecutionLog) {
                let self = this;
                let param = {
                    //・就業計算と集計実行ログID
                    empCalAndSumExecLogID: self.empCalAndSumExecLogID,
                    executionContentName: execution.executionContentName,
                    executionContent: execution.executionContent,
                    //・社員ID（list）  ・従業員の実行状況
                    listTargetPerson: self.listTargetPerson(),
                    //・実行開始日時
                    executionStartTime: execution.executionTime.startTime,
                    //・対象期間
                    objectPeriod: execution.objectPeriod,
                    //・選択した締め
                    nameClosue: self.nameClosure,
                    //・処理月
                    processingMonth: self.processingMonth()
                };
                if (self.listTargetPerson().length > 0) {
                    nts.uk.ui.windows.setShared("openG", param);
                    nts.uk.ui.windows.sub.modal("/view/kdw/001/g/index.xhtml");
                }
            }

            /**
             *open dialog H 
             */
            openDialogH(execution: modelkdw001f.ExecutionLog) {
                let self = this;
                let param = {
                    //・就業計算と集計実行ログID
                    empCalAndSumExecLogID: self.empCalAndSumExecLogID,
                    executionContentName: execution.executionContentName,
                    executionContent: execution.executionContent,
                    //・社員ID（list）  ・従業員の実行状況
                    listTargetPerson: self.listTargetPerson(),
                    //・実行開始日時
                    executionStartTime: execution.executionTime.startTime,
                    //・対象期間
                    objectPeriod: execution.objectPeriod,
                    //・選択した締め
                    nameClosue: self.nameClosure,
                    //・処理月
                    processingMonth: self.processingMonth()
                };
                if (execution.numberPersonErr > 0) {
                    nts.uk.ui.windows.setShared("openH", param);
                    nts.uk.ui.windows.sub.modal("/view/kdw/001/h/index.xhtml");
                }
            }

            closeDialog(): void {
                nts.uk.ui.windows.close();
            }
        }//end screenModel
    }//end module viewmodel    

    //module model
    export module model {
        /**
         * class TargetPerson
         */
        export class TargetPerson {
            employeeId: string;
            empCalAndSumExecLogId: string;
            personCode: string;
            personName: string;
            state: Array<ComplStateOfExeContents>;
            constructor(employeeId: string, empCalAndSumExecLogId: string, state: Array<ComplStateOfExeContents>,
                personCode: string,
                personName: string) {
                this.employeeId = employeeId;
                this.empCalAndSumExecLogId = empCalAndSumExecLogId;
                this.state = state;
                this.personCode = personCode;
                this.personName = personName;
            }
        }//end class TargetPerson

        /**
         * class ComplStateOfExeContents
         */
        export class ComplStateOfExeContents {
            executionContent: number;
            status: number;
            statusName: string;
            constructor(executionContent: number, status: number, statusName: string) {
                this.executionContent = executionContent;
                this.status = status;
                this.statusName = statusName;
            }
        }//end class ComplStateOfExeContents

        /**
         * class ErrMessageInfo
         */
        export class ErrMessageInfo {
            employeeID: string;
            empCalAndSumExecLogID: string;
            resourceID: string;
            executionContent: number;
            disposalDay: string;
            messageError: string;
            constructor(employeeID: string, empCalAndSumExecLogID: string, resourceID: string,
                executionContent: number, disposalDay: string, messageError: string) {
                this.employeeID = employeeID;
                this.empCalAndSumExecLogID = empCalAndSumExecLogID;
                this.resourceID = resourceID;
                this.executionContent = executionContent;
                this.disposalDay = disposalDay;
                this.messageError = messageError;
            }
        }//end class ErrMessageInfoDto



        /**
        * class InputErrMessageInfoByID
        */
        export class InputErrMessageInfoByID {
            empCalAndSumExecLogID: string;
            executionContent: number;
            constructor(empCalAndSumExecLogID: string, executionContent: number) {
                this.empCalAndSumExecLogID = empCalAndSumExecLogID;
                this.executionContent = executionContent;
            }
        }//end class InputErrMessageInfoByID


    }//end module model
}//end module
