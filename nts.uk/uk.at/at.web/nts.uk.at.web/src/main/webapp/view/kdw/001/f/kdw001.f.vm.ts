module nts.uk.at.view.kdw001.f {
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {
            enable: KnockoutObservable<boolean>;
            required: KnockoutObservable<boolean>;
            dateValue: KnockoutObservable<any>;
            startDateString: KnockoutObservable<any>;
            endDateString: KnockoutObservable<any>;
            //table
            columns: Array<any>;//nts.uk.ui.NtsGridListColumn
            currentSelectedRow: KnockoutObservable<any>;

            //InputEmpCalAndSumByDate
            inputEmpCalAndSumByDate: KnockoutObservable<model.InputEmpCalAndSumByDate>;
            //obj EmpCalAndSumExeLog 
            empCalAndSumExeLog: KnockoutObservableArray<model.EmpCalAndSumExeLog>;

            constructor() {
                let self = this;
                //
                self.enable = ko.observable(true);
                self.required = ko.observable(true);
                self.dateValue = ko.observable({});
                self.dateValue().startDate = moment.utc().subtract(1, "y").add(1, "d").format("YYYY/MM/DD");
                self.dateValue().endDate = moment.utc().format("YYYY/MM/DD");
                self.startDateString = ko.observable('');
                self.endDateString = ko.observable(new Date());
                //table
                self.currentSelectedRow = ko.observable(null);

                //inputEmpCalAndSumByDate (startDate and endDate)
                self.inputEmpCalAndSumByDate = ko.observable(
                    new model.InputEmpCalAndSumByDate(self.dateValue().startDate, self.dateValue().endDate));
                //obj EmpCalAndSumExeLog
                self.empCalAndSumExeLog = ko.observableArray([]);

                self.columns = [
                    { headerText: getText('KDW001_73'), key: 'executionDate', width: 100 },
                    { headerText: getText('KDW001_74'), key: 'empCalAndSumExecLogID', width: 120 },
                    { headerText: getText('KDW001_75'), key: 'caseSpecExeContentID', width: 100 },
                    { headerText: getText('KDW001_76'), key: 'processingMonthName', width: 150 },
                    { headerText: getText('KDW001_77'), key: 'executedMenuName', width: 200 },
                    { headerText: getText('KDW001_78'), key: 'executedMenuName', width: 160 },
                    {
                        headerText: getText('KDW001_79'), key: 'executionStatus', width: 100,
                        template: '<button data-bind="click :openDialogI">参照</button>',
                        columnCssClass: "colStyleButton",
                    }
                ];
            }

            /**
             * functiton start page
             */
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                //get all EmpCalAndSumExeLog by date
                let dfdAllEmpCalAndSumExeLog = self.getAllEmpCalAndSumExeLog(self.inputEmpCalAndSumByDate());

                $.when(dfdAllEmpCalAndSumExeLog).done((dfdAllEmpCalAndSumExeLogData) => {

                    dfd.resolve();
                });

                return dfd.promise();
            }//end start page

            /**
             * function get all EmpCalAndSumExeLog by startDate and endDate
             */
            getAllEmpCalAndSumExeLog(inputEmpCalAndSumByDate: model.InputEmpCalAndSumByDate) {
                let self = this;
                let dfd = $.Deferred<any>();
                service.getAllEmpCalAndSumExeLog(inputEmpCalAndSumByDate).done(function(data: Array<model.IEmpCalAndSumExeLog>) {
                    //_.sortBy(self.empCalAndSumExeLog(data), 'executionDate');
                    data = _.orderBy(data, ['executionDate'], ['desc']);
                    let temp = [];
                    _.each(data, (value) => {
                        temp.push(new model.EmpCalAndSumExeLog(value));
                    });
                    self.empCalAndSumExeLog(temp);
                    dfd.resolve(data);
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
                });
                return dfd.promise();
            }//end function getAllEmpCalAndSumExeLog

            //button search
            search() {
                let self = this;
                self.inputEmpCalAndSumByDate(new model.InputEmpCalAndSumByDate(self.dateValue().startDate, self.dateValue().endDate));
                self.getAllEmpCalAndSumExeLog(self.inputEmpCalAndSumByDate());
            }

            //open dialog I
            openDialogI() {
                var empCalAndSumExecLogID = this.currentSelectedRow();
                nts.uk.ui.windows.setShared("openI", this.currentSelectedRow());
                nts.uk.ui.windows.sub.modal("/view/kdw/001/i/index.xhtml");
            }
        }//end screenModel
    }//end viewmodel

    //module model
    export module model {
        export interface IEmpCalAndSumExeLog {
            empCalAndSumExecLogID: string;
            caseSpecExeContentID: string;
            employeeID: string;
            executedMenu: number;
            executedMenuName: string;
            executedMenuJapan: string;
            executionStatus: number;
            executionDate: string;
            processingMonth: number;
            processingMonthName: string;
            closureID: number;
            executionLogs: Array<IExecutionLog>;
        }

        export interface IExecutionLog {
            empCalAndSumExecLogID: string;
            caseSpecExeContentID: string;
            employeeID: string;
            executedLogID: string;
            existenceError: number;
            executeContenByCaseID: number;
            executionContent: number;
            executionTime: IExecutionTime;
            processStatus: number;
            calExeSetInfor: CalExeSettingInfor;
            objectPeriod: ObjectPeriod;
        }

        export interface IExecutionTime {
            startTime: string;
            endTime: string;
        }

        /**
         * class EmpCalAndSumExeLog
         */
        export class EmpCalAndSumExeLog {
            empCalAndSumExecLogID: string;
            caseSpecExeContentID: string;
            employeeID: string;
            executedMenu: number;
            executedMenuName: string;
            executedMenuJapan: string;
            executionStatus: number;
            executionDate: string;
            processingMonth: number;
            processingMonthName: string;
            closureID: number;
            executionLogs: Array<ExecutionLog>;
            constructor(data: IEmpCalAndSumExeLog) {
                this.empCalAndSumExecLogID = data.empCalAndSumExecLogID;
                this.caseSpecExeContentID = data.caseSpecExeContentID;
                this.employeeID = data.employeeID;
                this.executedMenu = data.executedMenu;
                if (data.executedMenu == 0) {
                    this.executedMenuName = "詳細実行";
                } else {
                    this.executedMenuName = "domain4";
                }

                if (data.executedMenu == 0) {
                    this.executedMenuJapan = "選択して実行";
                } else {
                    this.executedMenuJapan = "ケース別実行";
                }
                this.executionStatus = data.executionStatus;
                this.executionDate = data.executionDate;
                this.processingMonth = data.processingMonth;
                this.processingMonthName = data.processingMonth + "月度";
                this.closureID = data.closureID;
                this.executionLogs = data.executionLogs;
            }

        }//end class EmpCalAndSumExeLog

        /**
         * class ExecutionLog
         */
        export class ExecutionLog {
            empCalAndSumExecLogID: string;
            caseSpecExeContentID: string;
            employeeID: string;
            executedLogID: string;
            existenceError: number;
            executeContenByCaseID: number;
            executionContent: number;
            executionTime: ExecutionTime;
            processStatus: number;
            calExeSetInfor: CalExeSettingInfor;
            objectPeriod: ObjectPeriod;
            constructor(data: IExecutionLog) {
                this.empCalAndSumExecLogID = data.empCalAndSumExecLogID;
                this.caseSpecExeContentID = data.caseSpecExeContentID;
                this.employeeID = data.employeeID;
                this.executedLogID = data.executedLogID;
                this.existenceError = data.existenceError;
                this.executeContenByCaseID = data.executeContenByCaseID;
                this.executionContent = data.executionContent;
                this.executionTime = new ExecutionTime(data.executionTime);
                this.processStatus = data.processStatus;
                this.calExeSetInfor = data.calExeSetInfor;
                this.objectPeriod = data.objectPeriod;
            }
        }//end class ExecutionLog
        /**
         * class ExecutionTime
         */
        export class ExecutionTime {
            startTime: string;
            endTime: string;
            timeSpan: string;
            constructor(data: IExecutionTime) {
                var momentStart = moment.utc(data.startTime, "YYYY/MM/DD HH:mm:ss");
                var momentEnd = moment.utc(data.endTime, "YYYY/MM/DD HH:mm:ss");
                
                this.startTime = momentStart.format("YYYY/MM/DD HH:mm:ss");
                this.endTime = momentEnd.format("YYYY/MM/DD HH:mm:ss");
                var ms = moment(momentEnd,"DD/MM/YYYY HH:mm:ss").diff(moment(momentStart,"DD/MM/YYYY HH:mm:ss"));
                var d = moment.duration(ms);
                this.timeSpan = Math.floor(d.asHours()) + moment.utc(ms).format(":mm:ss");
            }
        }//end class ExecutionTime

        /**
         * class CalExeSettingInfor
         */
        export class CalExeSettingInfor {
            executionContent: number;
            executionType: number;
            constructor(executionContent: number, executionType: number) {
                this.executionContent = executionContent;
                this.executionType = executionType;
            }//end class ExecutionTime
        }//end class CalExeSettingInfor

        /**
         * class ObjectPeriod
         */
        export class ObjectPeriod {
            startDate: string;
            endDate: string;
            constructor(startDate: string, endDate: string) {
                this.startDate = moment.utc(startDate, "YYYY/MM/DD").toISOString();
                this.endDate = moment.utc(endDate, "YYYY/MM/DD").toISOString();
            }
        }//end class ObjectPeriod

        /**
         * class InputEmpCalAndSumByDate 
         */
        export class InputEmpCalAndSumByDate {
            startDate: string;
            endDate: string;
            constructor(startDate: string, endDate: string) {
                this.startDate = moment.utc(startDate, "YYYY/MM/DD").toISOString();
                this.endDate = moment.utc(endDate, "YYYY/MM/DD").toISOString();
            }
        }//end class InputEmpCalAndSumByDate

    }//end module model

}//end module
