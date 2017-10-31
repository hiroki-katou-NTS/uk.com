module nts.uk.at.view.kdw001.i {
    import modelkdw001f = nts.uk.at.view.kdw001.f.model;
    export module viewmodel {
        export class ScreenModel {
            //table
            itemList: KnockoutObservableArray<any>;
            //obj ExecutionLog
            empCalAndSumExecLogID: string;
            empCalAndSumExecLog: KnockoutObservable<modelkdw001f.EmpCalAndSumExeLog>;
            processingMonthName: KnockoutObservable<string>;
            executedMenuJapan: KnockoutObservable<string>;
            listExecutionLog: KnockoutObservableArray<modelkdw001f.ExecutionLog>;
            objectPeriod: KnockoutObservable<modelkdw001f.ObjectPeriod>;
            periodStartDate: KnockoutObservable<string>;
            periodEndDate: KnockoutObservable<string>;
            timeSpan: KnockoutObservable<string>;
            //TargetPerson 
            listTargetPerson: KnockoutObservableArray<model.TargetPerson>;
            numberPerson: KnockoutObservable<number>;

            constructor() {
                let self = this;
                //table 
                self.itemList = ko.observableArray([]);
                self.empCalAndSumExecLogID = nts.uk.ui.windows.getShared("openI");
                self.empCalAndSumExecLog = ko.observable(null);
                self.processingMonthName = ko.observable('');
                self.executedMenuJapan = ko.observable('');
                self.listExecutionLog = ko.observableArray([]);
                self.objectPeriod = ko.observable(null);
                //start - end
                self.periodStartDate = ko.observable('');
                self.periodEndDate = ko.observable('');
                self.timeSpan = ko.observable('');


                //TargetPerson
                self.listTargetPerson = ko.observableArray([]);
                self.numberPerson = ko.observable(0);

                for (let i = 1; i <= 5; i++) {
                    self.itemList.push({
                        column1: "2016/11/06 10:49:16 (" + i + ")",
                        column2: "2016/11/06 10:50:16 (" + i + ")",
                        column3: "00:0" + i + ":00",
                        column4: "実行内容 " + i,
                        column5: "実行種別" + i,
                        column6: "実行詳細内容" + i,
                        column7: i + i + "人",
                        column8: i + "人"
                    });
                }
            }

            startPage(): JQueryPromise<any> {

                let self = this;
                let dfd = $.Deferred();
                let dfdGetEmpCalAndSumExeLog = self.getByEmpCalAndSumExeLogId(self.empCalAndSumExecLogID);
                let dfdGetListTargetPersonByEmpId = self.getListTargetPersonByEmpId(self.empCalAndSumExecLogID);
                $.when(dfdGetEmpCalAndSumExeLog, dfdGetListTargetPersonByEmpId).done((dfdGetEmpCalAndSumExeLogData, dfdGetListTargetPersonByEmpIdData) => {

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
                service.getByEmpCalAndSumExeLogId(empCalAndSumExeLogId).done(function(data: modelkdw001f.EmpCalAndSumExeLog): any {
                    self.empCalAndSumExecLog(new modelkdw001f.EmpCalAndSumExeLog(data));
                    self.processingMonthName(self.empCalAndSumExecLog().processingMonthName);
                    self.executedMenuJapan(self.empCalAndSumExecLog().executedMenuJapan);
                    //date
                    let sortData: Array<modelkdw001f.IExecutionLog> = _.sortBy(data[0].executionLogs, ['objectPeriod.startDate'], ['desc']);
                    self.listExecutionLog(_.map(sortData, (value) => {
                        return new modelkdw001f.ExecutionLog(value);
                    }));
                    if (self.listExecutionLog().length > 0) {
                        self.objectPeriod(self.listExecutionLog()[0].objectPeriod);
                        self.periodStartDate(self.objectPeriod().startDate);
                        self.periodEndDate(self.objectPeriod().endDate);
                    }
                    dfd.resolve();
                    //self.empCalAndSumExecLog(data[0]);
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
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
                    self.listTargetPerson(data);
                    self.numberPerson(self.listTargetPerson().length);
                    dfd.resolve();
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
                });
                return dfd.promise();

            }//end function getListTargetPersonByEmpId

            /**
             *open dialog G 
             */
            openDialogG() {
                nts.uk.ui.windows.sub.modal("/view/kdw/001/g/index.xhtml");
            }

            /**
             *open dialog H 
             */
            openDialogH() {
                nts.uk.ui.windows.sub.modal("/view/kdw/001/h/index.xhtml");
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
            state: ComplStateOfExeContents;
            constructor(employeeId: string, empCalAndSumExecLogId: string, state: ComplStateOfExeContents) {
                this.employeeId = employeeId;
                this.empCalAndSumExecLogId = empCalAndSumExecLogId;
                this.state = state;
            }
        }//end class TargetPerson

        /**
         * class ComplStateOfExeContents
         */
        export class ComplStateOfExeContents {
            executionContent: number;
            status: number;
            constructor(executionContent: number, status: number) {
                this.executionContent = executionContent;
                this.status = status;
            }
        }//end class ComplStateOfExeContents

    }//end module model
}//end module
